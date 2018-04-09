package com.taotao.httpClient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientTest {
	@Test
	public void doGet() throws Exception {
		//创建HTTPclient对象（抽象类不用new）
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建get对象
		HttpGet get = new HttpGet("http://www.baidu.com");
		//设置请求头消息User-Agent
		//get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//获取响应结果的状态码
		int code = response.getStatusLine().getStatusCode();
		System.out.println(code);
		//获取响应结果
		HttpEntity entity = response.getEntity();
		//将返回结果的流文件转为字符串使用工具类EntityUtils
		String result = EntityUtils.toString(entity,"utf-8");//设置字符转换编码
		System.out.println(result);
		//关闭HTTPclient
		response.close();//关闭请求
		httpClient.close();//关闭httpClient
	}
	
	@Test
	public void doGetParam() throws Exception{
		//创建HTTPclient对象（抽象类不用new）
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建uri对象
		URIBuilder uriBuilder = new URIBuilder("http://www.sogou.com/web");
		//给uri对象添加参数
		uriBuilder.addParameter("query", "花千骨");
		//创建get对象
		HttpGet get = new HttpGet(uriBuilder.build());//uri 地址字符串， uri对象
		//设置请求头消息User-Agent
		//get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		//执行请求
		CloseableHttpResponse response = httpClient.execute(get);
		//获取响应结果的状态码
		int code = response.getStatusLine().getStatusCode();
		System.out.println(code);
		//获取响应结果
		HttpEntity entity = response.getEntity();
		//将返回结果的流文件转为字符串使用工具类EntityUtils
		String result = EntityUtils.toString(entity,"utf-8");//设置字符转换编码
		System.out.println(result);
		//关闭HTTPclient
		response.close();//关闭请求
		httpClient.close();//关闭httpClient
	}
	
	@Test
	public void doPost() throws Exception{
		//创建HTTPclient对象（抽象类不用new）
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建POST对象
		HttpPost post = new HttpPost("http://localhost:9092/httpclient/post.html");
		//设置请求头消息User-Agent
		//get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		//执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		//获取响应结果的状态码
		int code = response.getStatusLine().getStatusCode();
		System.out.println(code);
		//获取响应结果
		HttpEntity entity = response.getEntity();
		//将返回结果的流文件转为字符串使用工具类EntityUtils
		String result = EntityUtils.toString(entity,"utf-8");//设置字符转换编码
		System.out.println(result);
		//关闭HTTPclient
		response.close();//关闭请求
		httpClient.close();//关闭httpClient
	}
	
	@Test
	public void doPostParam() throws Exception{
		//创建HTTPclient对象（抽象类不用new）
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//创建POST对象
		HttpPost post = new HttpPost("http://localhost:9092/httpclient/post2.html");
		//创建请求参数--创建entity--模拟表单
		List<NameValuePair> kvlist = new ArrayList<>();
		kvlist.add(new BasicNameValuePair("username", "lsy"));
		kvlist.add(new BasicNameValuePair("password", "lsy123"));
		//将集合转成（包装成）实体entity
		StringEntity postEntity = new UrlEncodedFormEntity(kvlist,"utf-8");
		
		//设置请求头消息User-Agent
		//post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0"); // 设置请求头消息User-Agent
		//设置请求的报文头部的编码
		//post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8"));
		//设置期望服务端返回的编码
		//post.setHeader(new BasicHeader("Accept", "text/plain;charset=utf-8"));
		
		//解决中文乱码
		//postEntity.setContentEncoding("UTF-8");
		//postEntity.setContentType("text/plain");
		//post.setHeader("Accept", "application/json");
		//post.setHeader("Content-Type", "application/json");
		
		//解决中文乱码
		//post.addHeader("Content-type","application/json; charset=utf-8");
		//post.setHeader("Accept", "application/json");
		//post.setEntity(new StringEntity(jsonParam.toString(), Charset.forName("UTF-8")));
		
		//设置请求参数
		post.setEntity(postEntity);
		//执行请求
		CloseableHttpResponse response = httpClient.execute(post);
		//获取响应结果的状态码
		int code = response.getStatusLine().getStatusCode();
		System.out.println(code);
		//获取响应结果
		HttpEntity getEntity = response.getEntity();
		//将返回结果的流文件转为字符串使用工具类EntityUtils
		String result = EntityUtils.toString(getEntity,"utf-8");//设置字符转换编码
		System.out.println(result);
		//关闭HTTPclient
		response.close();//关闭请求
		httpClient.close();//关闭httpClient
	}
}
