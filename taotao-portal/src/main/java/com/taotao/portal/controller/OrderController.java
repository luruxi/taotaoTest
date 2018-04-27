package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.common.utils.ExceptionUtil;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
/*
 * 订单controller
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	@Autowired
	private CartService cartService;
	@Autowired
	private OrderService orderService;
	/*
	 * 跳转到订单确认页面--包含购物车信息列表
	 */
	@RequestMapping("/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> cartItemList = cartService.getCartItemList(request, response);
		model.addAttribute("cartList", cartItemList);
		return "order-cart";
	}
	
	/*
	 * 创建订单--成功跳转
	 */
	@RequestMapping("/create")
	public String createOrder(Order order, Model model) {
		try {
			String orderId = orderService.createOrder(order);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			model.addAttribute("message", ExceptionUtil.getStackTrace(e));
			model.addAttribute("message", "创建订单出错，请稍后重试！");
			//*********这里需要发邮件或者短信通知开发人员*********//
			return "error/exception";
		}
	}
}
