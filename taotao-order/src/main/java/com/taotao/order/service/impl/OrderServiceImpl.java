package com.taotao.order.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
/*
 * 订单管理service
 */
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	
	@Value("${ORDER_GEN_KEY}")
	private String ORDER_GEN_KEY;//订单号生成KEY--用于redis
	@Value("${ORDER_INIT_ID}")
	private String ORDER_INIT_ID;//订单号生成KEY--订单初始值以免从1增长
	@Value("${ORDER_DETAIL_GEN_KEY}")
	private String ORDER_DETAIL_GEN_KEY;//订单明细生成KEY
	
	/*
	 * 创建新的订单
	 */
	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping) {
		//向订单表中插入记录-TbOrder
		//获取订单号--redis incr(每次自增1)，单线程不会出现重复，redis内存计算，速度快
		String string = jedisClient.get(ORDER_GEN_KEY);
		if(StringUtils.isBlank(string)) {
			jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
		}
		long orderId = jedisClient.incr(ORDER_GEN_KEY);
		//补全pojo
		order.setOrderId(orderId+"");
		//订单状态：1 未付款 2 已付款 3未发货 4已发货 5 交易成功 6交易关闭
		order.setStatus(1);//未付款
		order.setCreateTime(new Date());
		order.setUpdateTime(new Date());
		//评价状态：0 未评价 1已评价
		order.setBuyerRate(0);
		orderMapper.insert(order);
		//向订单-明细表中插入记录-TbOrderItem
		for (TbOrderItem orderItem : orderItemList) {
			//补全订单明细pojo
			//订单明细id
			long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
			orderItem.setId(orderDetailId+"");
			orderItem.setOrderId(orderId+"");
			//向订单明细里插入记录
			orderItemMapper.insert(orderItem);
		}
		//向订单-物流表中插入记录-TbOrderShipping
		//补全物流表pojo
		orderShipping.setOrderId(orderId+"");
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		//向订单物流表里插入记录
		orderShippingMapper.insert(orderShipping);
		return TaotaoResult.ok(orderId);
	}

}
