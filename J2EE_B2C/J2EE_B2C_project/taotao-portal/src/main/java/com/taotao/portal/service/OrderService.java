package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;

public interface OrderService {
	/**
	 * Get cart items from cookie in order page
	 * @param request in order to access cookie
	 * @param response in order to access cookie
	 * @return
	 */
	public List<CartItem> getCartItems(HttpServletRequest request,HttpServletResponse response);
	/**
	 * Create order by call "taotao-order" Service
	 * @param order
	 * @return TaotaoResult with orderId
	 */
	public TaotaoResult createOrder(Order order);
}
