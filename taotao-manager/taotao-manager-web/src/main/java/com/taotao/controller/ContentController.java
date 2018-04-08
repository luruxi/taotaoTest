package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.service.ContentService;

@Controller
@RequestMapping("/content/query")
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/list")
	public EuiDataGrid getContentList(HttpServletRequest request,long categoryId,Integer page,Integer rows) {
		System.out.println("categoryId:"+categoryId+"--page:"+page+"--rows:"+rows);
		EuiDataGrid result = contentService.getContentList(categoryId, page, rows);
		return result;
	}
}
