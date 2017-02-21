package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Value("${HTTPCLIENT_BASE_URL}")
	private String HTTPCLIENT_BASE_URL;
	@Value("${ITEM_BASE_INFO}")
	private String ITEM_BASE_INFO;

	@Override
	public TaotaoResult addCartItem(Long id, Integer quantity, HttpServletRequest request,
			HttpServletResponse response) {
		// get the exist cart
		List<CartItem> cartItemList = getCartItems(request);
		// A flag that if the item already exist
		boolean hasExist = false;
		// If the cart has included the item already,then just add the quantity
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getId().equals(id)) {
				cartItem.setQuantity(cartItem.getQuantity() + quantity);
				hasExist = true;
				break;
			}
		}
		// Access taotao-rest for item base info
		if (!hasExist) {
			String jsonData = HttpClientUtil.doGet(HTTPCLIENT_BASE_URL + ITEM_BASE_INFO + "/" + id);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(jsonData, TbItem.class);
			if (taotaoResult.getStatus() != 200) {
				return TaotaoResult.build(500, "Can't find the item.");
			}
			TbItem item = (TbItem) taotaoResult.getData();
			// new a CartItem pojo
			CartItem cartItem = new CartItem();
			cartItem.setId(item.getId());
			cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
			cartItem.setPrice(item.getPrice());
			cartItem.setQuantity(quantity);
			cartItem.setTitle(item.getTitle());
			// add it to the cart
			cartItemList.add(cartItem);
		}
		String json = JsonUtils.objectToJson(cartItemList);
		CookieUtils.setCookie(request, response, "TT_CART", json, true);
		return TaotaoResult.ok();
	}

	@Override
	public List<CartItem> getCartItems(HttpServletRequest request) {
		String cookieValue = CookieUtils.getCookieValue(request, "TT_CART", true);
		if (cookieValue != null) {
			List<CartItem> cartItemList = JsonUtils.jsonToList(cookieValue, CartItem.class);
			return cartItemList;
		}
		return new ArrayList<CartItem>();
	}

	@Override
	public TaotaoResult removeCartItem(Long id, HttpServletRequest request, HttpServletResponse response) {
		List<CartItem> cartItemList = getCartItems(request);
		for (CartItem cartItem : cartItemList) {
			if (cartItem.getId().equals(id)) {
				cartItemList.remove(cartItem);
				break;
			}
		}
		String json = JsonUtils.objectToJson(cartItemList);
		CookieUtils.setCookie(request, response, "TT_CART", json, true);
		return TaotaoResult.ok();
	}

}
