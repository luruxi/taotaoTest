package com.taotao.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/*
 * 订单controller
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@RequestMapping("/order-cart")
	public String showOrderCart() {
		return "order-cart";
	}
}
