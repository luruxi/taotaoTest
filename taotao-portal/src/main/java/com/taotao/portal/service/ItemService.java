package com.taotao.portal.service;

import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
	ItemInfo getItemById(long itemId);
	String getItemDesc(long itemId);
	String getItemParam(long itemId);
}