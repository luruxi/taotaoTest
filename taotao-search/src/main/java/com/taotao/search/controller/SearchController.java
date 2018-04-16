package com.taotao.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.search.pojo.SearchResult;
import com.taotao.search.service.SearchService;

@Controller
@RequestMapping("")
public class SearchController {
	@Autowired
	private SearchService searchService;
	
	/*
	 * 需要解决get请求乱码问题
	 */
	@RequestMapping(value = "/query",method=RequestMethod.GET)
	@ResponseBody
	public TaotaoResult search(@RequestParam("q")String query,
			@RequestParam(defaultValue="1")Integer page,
			@RequestParam(defaultValue="60")Integer rows) {
		if(StringUtils.isBlank(query)) {
			return TaotaoResult.build(400, "查询条件不能为空");
		}
		SearchResult result = null;
		try {
			query = new String(query.getBytes("iso8859-1"), "utf-8");
			result = searchService.search(query, page, rows);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		return TaotaoResult.ok(result);
	}
}
