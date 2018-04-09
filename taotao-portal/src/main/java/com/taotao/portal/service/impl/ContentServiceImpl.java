package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbContent;
import com.taotao.portal.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_AD_URL}")
	private String REST_CONTENT_AD_URL;

	@Override
	public String getContentService() {
		//使用httpclient获取rest服务的数据
		String httpResult = HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_AD_URL);
		//把返回结果转换成TaotaoResult
		try {
			TaotaoResult result = TaotaoResult.formatToList(httpResult, TbContent.class);
			List<TbContent> list = (List<TbContent>) result.getData();
			//创建界面要求的pojo列表或者map
			List<Map> resultList = new ArrayList<>();
			for(TbContent content:list) {
				Map map = new HashMap<>();
				map.put("src", content.getPic());
				map.put("height", 240);
				map.put("width", 670);
				map.put("srcB", content.getPic2());
				map.put("heightB", 240);
				map.put("widthB", 550);
				map.put("href", content.getUrl());
				map.put("alt", content.getSubTitle());
				resultList.add(map);
			}
			return JsonUtils.objectToJson(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
