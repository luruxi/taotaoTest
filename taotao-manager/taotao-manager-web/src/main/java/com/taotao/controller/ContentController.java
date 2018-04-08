package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	//获取分类内容列表
	@RequestMapping("/query/list")
	@ResponseBody
	public EuiDataGrid getContentList(HttpServletRequest request,long categoryId,Integer page,Integer rows) {
		System.out.println("categoryId:"+categoryId+"--page:"+page+"--rows:"+rows);
		EuiDataGrid result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
	
	//新建分类内容
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult insertContent(TbContent content) {
		TaotaoResult result = contentService.insertContent(content);
		return result;
	}
}
