package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

/**
 * 
 * @author Administrator
 * 商品管理Service测试
 *
 */
//@Service注释方便applicationContext-service.xml扫描加载
@Service
public class ItemServiceImpl implements ItemService {
	//@Autowired注释方便为 Bean 的成员变量、方法入参或构造函数入参提供自动注入的功能。
	//注入mapper接口的代理对象
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getItemById(long itemId) {
		
		//根据主键查询
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
		
		//根据查询条件查询
		/*TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list != null && list.size()>0) {
			TbItem tbItem = list.get(0);
			return tbItem;
		}
		return null;*/
	}

	@Override
	public EuiDataGrid getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建一个返回值对象
		EuiDataGrid result = new EuiDataGrid();
		
		result.setRows(list);
		
		//获取total
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	/*
	 * 添加商品
	 */
	@Override
	public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception {
		//补全item元素，id，状态，创建和更新时间
		//id用
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		byte status = 1;//1正常 2下架 3删除
		item.setStatus(status);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);//添加商品基本信息
		TaotaoResult resultDesc = insertItemDesc(itemId,desc);//添加商品描述（单独表）
		if(resultDesc.getStatus()!=200) {
			throw new Exception();//抛出异常，操作回滚
		}
		TaotaoResult resultParam = insertItemParamItem(itemId, itemParam);//添加商品规格参数（单独表）
		if(resultParam.getStatus()!=200) {
			throw new Exception();//抛出异常，操作回滚
		}
		return TaotaoResult.ok();
	}
	/*
	 * 添加商品描述（单独表）
	 */
	private TaotaoResult insertItemDesc(long itemId,String desc) {
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	/*
	 * 添加商品规格参数（单独表）
	 */
	private TaotaoResult insertItemParamItem(long itemId,String itemParam) {
		TbItemParamItem tbItemParamItem = new TbItemParamItem();
		tbItemParamItem.setItemId(itemId);
		tbItemParamItem.setParamData(itemParam);
		tbItemParamItem.setCreated(new Date());
		tbItemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(tbItemParamItem);
		return TaotaoResult.ok();
	}
}
