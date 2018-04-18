package com.taotao.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
/*
 * 商品搜索service
 */
@Service
public class SearchServiceImpl implements SearchService {
	@Value("${SEARCH_BASE_URL}")
	private String SEARCH_BASE_URL;
	@Override
	public SearchResult search(String queryString, int page) {
		//调用taotao-search的服务器
		//查询参数
		Map<String,String> param = new HashMap<>();
		param.put("q",queryString);
		param.put("page", page+"");
		try {
			//调用服务
			String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
			//把字符串转换成java对象
			//List<SearchResult> list = JsonUtils.jsonToList(json, SearchResult.class);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
			if(taotaoResult != null && taotaoResult.getStatus()==200) {
				SearchResult result = (SearchResult) taotaoResult.getData();
				return result;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
