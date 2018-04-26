package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
/*
 * 接收商品id和数量 数量默认为1，调用service添加购物车
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
@Controller
@RequestMapping("/cart")
public class CartItemController {
	@Autowired
	private CartService cartService;
	//添加购物车
	@RequestMapping("/add/{itemId}")
	public String addCartItem(@PathVariable Long itemId, @RequestParam(defaultValue="1") Integer num, HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult result = cartService.addCartItem(itemId, num, request, response);
		return "redirect:/cart/cartSuccess.html";
	}
	//添加购物车成功跳转的controller
	@RequestMapping("/cartSuccess")
	public String cartSuccess() {
		return "cartSuccess";
	}
	//获取购物车信息列表
	@RequestMapping("/cart")
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> list = cartService.getCartItemList(request, response);
		model.addAttribute("cartList",list);
		return "cart";
	}
}
