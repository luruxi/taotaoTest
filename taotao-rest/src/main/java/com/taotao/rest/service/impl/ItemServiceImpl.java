package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
/*
 * 获取商品信息
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	//注入缓存dao
	@Autowired
	private JedisClient jedisClient;
	
	//从spring容器加载的属性配置中取值
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${REDIS_ITEM_EXPIRE}")
	private Integer REDIS_ITEM_EXPIRE;

	
	/*
	 * 获取商品基本信息通过商品id
	 */
	@Override
	public TaotaoResult getItemBaseInfo(long itemId) {
		//从缓存中取
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":base");
			//判断json是否有值
			if(!StringUtils.isBlank(json)) {
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				//更新过期时间
				//jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base", REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(item);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//从数据库查询
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		//将查询结果存到缓存中(需要设置过期时间--hashKey不能设置过期时间 )
		try {
			//设置键值
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":base", JsonUtils.objectToJson(item));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":base", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	/*
	 * 获取商品描述通过商品id
	 */
	@Override
	public TaotaoResult getItemDesc(long itemId) {
		//从缓存中取
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":desc");
			//判断json是否有值
			if(!StringUtils.isBlank(json)) {
				TbItemDesc item = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				//更新过期时间
				//jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc", REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(item);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//从数据库查询
		TbItemDesc item = itemDescMapper.selectByPrimaryKey(itemId);
		//将查询结果存到缓存中(需要设置过期时间--hashKey不能设置过期时间 )
		try {
			//设置键值
			jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc", JsonUtils.objectToJson(item));
			//设置过期时间
			jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc", REDIS_ITEM_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.ok(item);
	}

	/*
	 * 获取商品规格参数通过商品id
	 */
	@Override
	public TaotaoResult getItemParam(long itemId) {
		//从缓存中取
		try {
			String json = jedisClient.get(REDIS_ITEM_KEY+":"+itemId+":param");
			//判断json是否有值
			if(!StringUtils.isBlank(json)) {
				TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
				//更新过期时间
				//jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param", REDIS_ITEM_EXPIRE);
				return TaotaoResult.ok(paramItem);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//创建查询条件
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
//		List<TbItemParamItem> list = itemParamItemMapper.selectByExample(example);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		//从列表里取
		if(list!=null && list.size()>0) {
			TbItemParamItem paramItem = list.get(0);

			//将查询结果存到缓存中(需要设置过期时间--hashKey不能设置过期时间 )
			try {
				//设置键值
				jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":param", JsonUtils.objectToJson(paramItem));
				//设置过期时间
				jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":param", REDIS_ITEM_EXPIRE);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return TaotaoResult.ok(paramItem);
		}
		
		return TaotaoResult.build(400, "此商品无规格");
	}

}
