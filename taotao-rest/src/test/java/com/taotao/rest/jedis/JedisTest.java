package com.taotao.rest.jedis;

import java.util.HashSet;

import org.junit.Test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	@Test
	public void testJedis() {
		//创建jedis对象
		Jedis jedis = new Jedis("192.168.159.129",6379);
		//System.out.println(jedis.toString());
		//String strA = jedis.get("a");
		//System.out.println(strA);
		//调用jedis方法（与redis命令一致）
		jedis.set("key1", "1000");
		String string = jedis.get("key1");
		System.out.println(string);
		//关闭jedis
		jedis.close();
		
		//new Jedis("192.168.159.129", 6379);
	}
	
	/*
	 * 使用连接池
	 */
	@Test
	public void testJedisPool() {
		//创建pool连接池
		JedisPool jedisPool = new JedisPool("192.168.159.129",6379);
		//从连接池中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		//设置jedis参数
		jedis.set("key2", "1000");
		String string = jedis.get("key2");
		System.out.println(string);
		//关闭jedis
		jedis.close();
		//关闭pool连接池
		jedisPool.close();
	} 
	
	/*
	 * 集群版jedis
	 */
	@Test
	public void testJedisCluster() {
		//创建集群节点集合
		HashSet<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.159.129",7001));
		nodes.add(new HostAndPort("192.168.159.129",7002));
		nodes.add(new HostAndPort("192.168.159.129",7003));
		nodes.add(new HostAndPort("192.168.159.129",7004));
		nodes.add(new HostAndPort("192.168.159.129",7005));
		nodes.add(new HostAndPort("192.168.159.129",7006));
		//创建集群jedis
		JedisCluster jedisCluster = new JedisCluster(nodes);
		//设置集群jedis参数
		jedisCluster.set("key2", "1000");
		String string = jedisCluster.get("key2");
		System.out.println(string);
		//关闭集群jedis
		jedisCluster.close();
	}
}
