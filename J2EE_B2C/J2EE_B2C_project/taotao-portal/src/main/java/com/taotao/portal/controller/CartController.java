package com.taotao.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@RequestMapping(value = "/add/{itemId}", method = RequestMethod.GET)
	public String addCartItem(@PathVariable long itemId, @RequestParam(defaultValue = "1") Integer quantity,
			HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult taotaoResult = cartService.addCartItem(itemId, quantity, request, response);
		if (taotaoResult.getStatus() == 200) {
			return "redirect:/cart/cart.html";
		}
		return "exception";
	}

	@RequestMapping(value = "/cart", method = RequestMethod.GET)
	public String showCart(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<CartItem> cartItemList = cartService.getCartItems(request);
		model.addAttribute("cartList", cartItemList);
		return "cart";
	}

	@RequestMapping(value = "/delete/{itemId}", method = RequestMethod.GET)
	public String removeCartItem(@PathVariable long itemId, HttpServletRequest request, HttpServletResponse response) {
		TaotaoResult taotaoResult = cartService.removeCartItem(itemId, request, response);
		if(taotaoResult.getStatus()==200){
			return "redirect:/cart/cart.html";
		}
		return "exception";
	}
}
