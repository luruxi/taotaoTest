package com.taotao.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	//@RequestMapping 配置url路径和方法的映射关系
	@RequestMapping("/item/{itemId}")
	//@ResponseBody 返回json格式数据
	@ResponseBody
	//@PathVariable 使用这个注解从请求路径中获取key value值
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	/*public EuiDataGrid getItemList(Integer page,Integer rows) {//page 和  rows  参数怎么传递的
		EuiDataGrid result = itemService.getItemList(page, rows);
		return result;
	}*/
	public EuiDataGrid getItemList(HttpServletRequest request) {//page 和  rows  参数怎么传递的
		String pageR = request.getParameter("page");
		String rowsR = request.getParameter("rows");
		Integer page = null;
		Integer rows = null;
		if(StringUtils.isNotEmpty(pageR)) {//StringUtils.isNotEmpty() 判断字符串不为 ""," ",null
			page = Integer.parseInt(pageR);
		}else {
			return null;
		}
		
		if(StringUtils.isNotEmpty(rowsR)) {
			rows = Integer.parseInt(rowsR);
		}else {
			return null;
		}
		
		EuiDataGrid result = itemService.getItemList(page, rows);
		if(result!=null) {
			System.out.println("-------------------"+result.getTotal());
		}
		return result;
	}
	
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item,String desc,String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item,desc,itemParams);
		return result;
	}
	
}