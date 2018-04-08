package com.taotao.service;

import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	//获取分类内容列表
	EuiDataGrid getContentList(long categoryId,Integer page,Integer rows);
	//新建分类内容
	TaotaoResult insertContent(TbContent content);
}