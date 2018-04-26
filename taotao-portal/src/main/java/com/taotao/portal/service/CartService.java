package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface CartService {
	TaotaoResult addCartItem(long itemId,int num, HttpServletRequest request, HttpServletResponse response);//商品id 数量
	List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
	TaotaoResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);
	TaotaoResult updateCartItemNum(long itemId,int num, HttpServletRequest request, HttpServletResponse response);
}
