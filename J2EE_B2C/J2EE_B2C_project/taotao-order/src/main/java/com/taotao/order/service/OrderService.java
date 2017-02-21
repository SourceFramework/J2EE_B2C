package com.taotao.order.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.order.pojo.Order;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;

public interface OrderService {
	/**
	 * Create order. It needs transaction support
	 * 
	 * @param order
	 * @param orderItemList
	 * @param orderShipping
	 * @return TaotaoResult with order id
	 */
	public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> orderItemList, TbOrderShipping orderShipping);
}
