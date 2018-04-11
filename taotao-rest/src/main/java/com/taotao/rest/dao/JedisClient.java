package com.taotao.rest.dao;

public interface JedisClient {
	String get(String key);//获取jedis键值--传键值--返回值
	String set(String key,String value);//设置jedis键值--传键值和值--返回
	String hget(String hkey,String key);//获取jedis得哈希键值--传哈希键值，键值--返回
	long hset(String hkey,String key,String value);//设置jedis得哈希键值--传哈希键值，键值，值--返回
	long incr(String key);//jedis键值自增--传键值--返回
	long expire(String key,int second);//设置jedis键值过期时间--传键值，过期时间--返回
	long ttl(String key);//查看jedis键值是否过期--传键值 --返回-1（没过期）-2（过期）
	long del(String key);//删除jedis键值--传键值--返回
	long hdel(String hkey,String key);//删除jedis哈希键值--传哈希键值，键值--返回
}
