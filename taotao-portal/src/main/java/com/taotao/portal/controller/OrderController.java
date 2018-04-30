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
import com.taotao.pojo.TbUser;
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
	public String createOrder(Order order, HttpServletRequest request, Model model) {
		try {
			//从redis-cookie里获取token，根据token通过sso系统单点信息里获取用户信息放到order里
			//之前点击去结算--走拦截器，通过token获取用户信息已经执行一遍了。想办法通过拦截器把用户信息传递过来
			//执行这个之前就执行拦截器，这里通过request对象传递用户信息，拦截器时候把用户信息放到request里，中转一下，这里获取request就会带着用户信息
			//从request中获取user用户信息
			TbUser user = (TbUser) request.getAttribute("user");
			//补全order中的用户信息
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			//调用服务
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
