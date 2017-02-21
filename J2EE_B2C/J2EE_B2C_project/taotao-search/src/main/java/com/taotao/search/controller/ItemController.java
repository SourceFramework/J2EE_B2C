package com.taotao.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.search.service.ItemService;

@Controller
@RequestMapping("/management")
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	
	@RequestMapping("/importAll")
	@ResponseBody
	public TaotaoResult importAllItems(){
		TaotaoResult result = itemService.importAllItems();
		return result;
	}
	
	@RequestMapping("/importOne/{itemId}")
	@ResponseBody
	public TaotaoResult importOneItem(@PathVariable(value="itemId") String id){
		TaotaoResult result = itemService.importOneItem(id);
		return result;
	}
}
