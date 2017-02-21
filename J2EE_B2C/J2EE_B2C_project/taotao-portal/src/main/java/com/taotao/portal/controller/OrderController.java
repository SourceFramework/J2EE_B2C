package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/order-cart", method = RequestMethod.GET)
	public String showOrderCartPage(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> cartItemList = orderService.getCartItems(request, response);
		model.addAttribute("cartList", cartItemList);
		return "order-cart";
	}

	@RequestMapping(value = "/create")
	public String showOrderPage(Order order, Model model) {
		
		TaotaoResult taotaoResult = orderService.createOrder(order);
		
		if(taotaoResult.getStatus() == 200){
			model.addAttribute("orderId", taotaoResult.getData());
			model.addAttribute("payment", order.getPayment());
			model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
			return "success";
		}else{
			model.addAttribute("message", taotaoResult.getMsg());
			return "error/exception";			
		}
	}
}
