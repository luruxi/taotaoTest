package com.taotao.order.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.taotao.order.dao.JedisClient;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

//不加注解，在配置文件中配置
public class JedisClientSingle implements JedisClient {
	//注入spring配置中的jedisPool
	@Autowired
	private JedisPool jedisPool;
	
	@Override
	public String get(String key) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get(key);
		//关闭jedis
		jedis.close();
		//关闭pool连接池
		//jedisPool.close();//连接池不能关闭其他地方还要用呢
		return result;
	}

	@Override
	public String set(String key, String value) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		String result = jedis.set(key,value);
		//关闭jedis
		jedis.close();
		return result;
	}

	@Override
	public String hget(String hkey, String key) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		String result = jedis.hget(hkey,key);
		//关闭jedis
		jedis.close();
		return result;
	}

	@Override
	public long hset(String hkey, String key, String value) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		long result = jedis.hset(hkey,key,value);
		//关闭jedis
		jedis.close();
		return result;
	}

	@Override
	public long incr(String key) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		long result = jedis.incr(key);
		//关闭jedis
		jedis.close();
		return result;
	}

	@Override
	public long expire(String key, int second) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		long result = jedis.expire(key,second);
		//关闭jedis
		jedis.close();
		return result;
	}

	@Override
	public long ttl(String key) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		long result = jedis.ttl(key);
		//关闭jedis
		jedis.close();
		return result;
	}

	@Override
	public long del(String key) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		long result = jedis.del(key);
		//关闭jedis
		jedis.close();
		return result;
	}

	@Override
	public long hdel(String hkey, String key) {
		//从连接池jedisPool对象中取出jedis对象
		Jedis jedis = jedisPool.getResource();
		long result = jedis.hdel(hkey,key);
		//关闭jedis
		jedis.close();
		return result;
	}

}
