package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Value("${BASE_URL_ORDER}")
	private String BASE_URL_ORDER;
	@Value("${CREATE_ORDER}")
	private String CREATE_ORDER;

	@Override
	public List<CartItem> getCartItems(HttpServletRequest request, HttpServletResponse response) {
		String jsonData = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(!StringUtils.isBlank(jsonData)){
			List<CartItem> cartItemList = JsonUtils.jsonToList(jsonData, CartItem.class);
			return cartItemList;
		}
		return new ArrayList<CartItem>();
	}

	@Override
	public TaotaoResult createOrder(Order order) {
		try {
			String jsonData = JsonUtils.objectToJson(order);
			String json= HttpClientUtil.doPostJson(BASE_URL_ORDER + CREATE_ORDER , jsonData);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, String.class);
			if(taotaoResult != null && taotaoResult.getStatus() == 200){
				return taotaoResult;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return TaotaoResult.build(500, "Fail to create Order.");
	}

}
