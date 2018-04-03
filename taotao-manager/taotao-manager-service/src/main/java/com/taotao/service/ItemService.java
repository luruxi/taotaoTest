package com.taotao.service;

import com.taotao.common.pojo.EuiDataGrid;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(long itemId);
	EuiDataGrid getItemList(int page,int rows);
	TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception;
}
