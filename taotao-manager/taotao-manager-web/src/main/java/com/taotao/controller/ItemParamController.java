package com.taotao.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

/*
 * 商品分类
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/list")
	@ResponseBody
	public EuiDataGrid getItemCatList(HttpServletRequest request) {
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
		List<TbItemParam> data = itemParamService.getItemParamList(page,rows);
		long total = itemParamService.countAllList();
		EuiDataGrid result = new EuiDataGrid();
		result.setRows(data);
		result.setTotal(total);
		return result;
	}
	@RequestMapping(value="/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable long itemCatId) {
		return itemParamService.getItemParamByCid(itemCatId);
	}
	@RequestMapping(value="/save/{cid}")
	@ResponseBody
	public TaotaoResult insertItemParam(@PathVariable long cid,String paramData ) {
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		TaotaoResult result = itemParamService.insertItemParam(tbItemParam);
		return result;
	}
}
