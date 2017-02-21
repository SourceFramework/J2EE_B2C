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
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

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

	@Value("${ORDER_ID_KEY}")
	private String ORDER_ID_KEY;
	@Value("${BASE_VAL_OF_ORDER_ID}")
	private String BASE_VAL_OF_ORDER_ID;
	@Value("${ORDER_ITEM_ID_KEY}")
	private String ORDER_ITEM_ID_KEY;

	@Override
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping) {
		try {
			// generate order id by redis 'incr' command
			String value = jedisClient.get(ORDER_ID_KEY);
			if (StringUtils.isBlank(value)) {
				jedisClient.set(ORDER_ID_KEY, BASE_VAL_OF_ORDER_ID);
			}
			String orderId = jedisClient.incr(ORDER_ID_KEY) + "";
			// Complete TbOrder attribute
			order.setOrderId(orderId);
			// order status : 1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
			order.setStatus(1);
			Date date = new Date();
			order.setCreateTime(date);
			order.setUpdateTime(date);
			// 0：未评价 1：已评价
			order.setBuyerRate(0);
			orderMapper.insert(order);
			// Complete TbOrderItem attribute
			for (TbOrderItem orderItem : orderItemList) {
				String orderItemId = jedisClient.incr(ORDER_ITEM_ID_KEY) + "";
				orderItem.setId(orderItemId);
				orderItem.setOrderId(orderId);
				orderItemMapper.insert(orderItem);
			}
			// Complete TbOrderShipping attribute
			orderShipping.setCreated(date);
			orderShipping.setUpdated(date);
			orderShipping.setOrderId(orderId);
			orderShippingMapper.insert(orderShipping);
			
			return TaotaoResult.ok(orderId);
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(400, "Fail to create order.");
		}	
	}

}
