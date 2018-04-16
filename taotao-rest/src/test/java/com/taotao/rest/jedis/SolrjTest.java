package com.taotao.rest.jedis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrjTest {
	/*
	 * 添加文档测试（修改同理，id是必须文档域，修改时候id一样就可以了）
	 */
	@Test
	public void addDocument() throws Exception {
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.159.130:8080/solr/");
		//创建文档
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", "test001");
		document.addField("item_title", "测试商品1");
		document.addField("item_price", 112556);
		//文档写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	/*
	 * 删除文档
	 */
	@Test
	public void deleteDocument() throws Exception {
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.159.130:8080/solr/");
		//删除文档通过域id
		solrServer.deleteById("test001");
		//批量删除通过id的list集合 List<String> ids
//		List<String> ids = new ArrayList<String>();
//		ids.add("test001");
//		solrServer.deleteById(ids);
		//通过查询条件删除(*:* 删除所有)
//		solrServer.deleteByQuery("*:*");
		//提交
		solrServer.commit();
	}
	/*
	 * 通过查询条件查询solr
	 */
	@Test
	public void queryDocument() throws Exception {
		//创建连接
		SolrServer solrServer = new HttpSolrServer("http://192.168.159.130:8080/solr/");
		//新建查询对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery("*:*");
		//执行查询
		QueryResponse response = solrServer.query(query);
		//从查询结果里取数据
		SolrDocumentList results = response.getResults();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
		}
	}
}
