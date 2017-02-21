package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

public interface CartService {
	/**
	 * Add item into cart
	 * 
	 * @param id
	 *            itemId
	 * @param quantity
	 * @param request
	 *            in order to get the cookie
	 * @param response
	 *            in order to get the coojie
	 * @return
	 */

	public TaotaoResult addCartItem(Long id, Integer quantity, HttpServletRequest request,
			HttpServletResponse response);

	/**
	 * Get the exist cart from cookie,in the form of List<CartItem>
	 * 
	 * @param request
	 *            in order to get the cookie
	 * @return If the cart is empty,return a empty list
	 */
	public List<CartItem> getCartItems(HttpServletRequest request);

	/**
	 * remove item from cart
	 * 
	 * @param id
	 *            itemId
	 * @param request
	 *            in order to get the cookie
	 * @param response
	 *            in order to get the cookie
	 * @return
	 */
	public TaotaoResult removeCartItem(Long id, HttpServletRequest request, HttpServletResponse response);
}
