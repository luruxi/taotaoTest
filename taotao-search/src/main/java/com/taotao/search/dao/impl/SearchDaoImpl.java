package com.taotao.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.taotao.search.dao.SearchDao;
import com.taotao.search.pojo.Item;
import com.taotao.search.pojo.SearchResult;

/*
 * 商品搜索Dao
 */

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//新建返回结果对象
		SearchResult result = new SearchResult();
		//根据查询条件查询索引库
		QueryResponse response = solrServer.query(query);
		//获取查询结果
		SolrDocumentList solrDocumentList = response.getResults();
		//取查询结果总数量
		result.setRecordCount(solrDocumentList.getNumFound());
		//取高亮显示--从--response
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//取商品列表
		List<Item> itemList = new ArrayList<>();
		for (SolrDocument solrDocument : solrDocumentList) {
			//创建商品对象Item
			Item item = new Item();
			item.setId(Long.valueOf((String) solrDocument.get("id")));
			
			//取高亮显示结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if(list != null && list.size() > 0) {
				title = list.get(0);
			}else {
				title = (String) solrDocument.get("item_title");
			}
			
			item.setTitle(title);
			
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			//添加到商品列表中
			itemList.add(item);
		}
		result.setItemList(itemList);
		System.out.println(result.toString());
		return result;
	}

}
