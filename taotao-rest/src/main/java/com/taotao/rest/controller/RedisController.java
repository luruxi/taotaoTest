package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;

/*
 * 缓存同步
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {
	@Autowired
	private RedisService redisService;
	@RequestMapping("/content/{categoryId}")
	@ResponseBody
	public TaotaoResult contentCacheSync(@PathVariable long categoryId) {
		TaotaoResult result = redisService.syncContentRedis(categoryId);
		return result;
	}
}
