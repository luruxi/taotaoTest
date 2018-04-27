package com.taotao.portal.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
/*
 * 订单服务
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Value("${ORDER_BASE_URL}")
	private String ORDER_BASE_URL;
	@Value("${ORDER_CREATE_URL}")
	private String ORDER_CREATE_URL;
	/*
	 * 创建订单
	 */
	@Override
	public String createOrder(Order order) {
		//调用order服务创建订单
		String json = HttpClientUtil.doPostJson(ORDER_BASE_URL+ORDER_CREATE_URL, JsonUtils.objectToJson(order));
		//json结果转成对象
		TaotaoResult result = TaotaoResult.format(json);
		if(result.getStatus()==200) {
		 	Object orderId = result.getData();
			return orderId.toString();
		}
		return "";
	}

}
