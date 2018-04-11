package com.taotao.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ContentService;
@Service
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT_REDIS_KEY}")
	private String INDEX_CONTENT_REDIS_KEY;
	@Override
	public List<TbContent> getContentList(long categoryId) {
		//从缓存中获取-但不能影响正常程序-有异常抛出
		try {
			String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, categoryId+"");
			if(!StringUtils.isEmpty(result)) {
				//把结果转成list
				List<TbContent> resultlist = JsonUtils.jsonToList(result, TbContent.class);
				return resultlist;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		//根据内容分类id查询内容列表
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		//向缓存中添加值-但不能影响正常程序-有异常抛出
		try {
			//把list转成字符串
			String catchString = JsonUtils.objectToJson(list);
			jedisClient.hset(INDEX_CONTENT_REDIS_KEY, categoryId+"", catchString);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
