package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/baseInfo/{itemId}")
	@ResponseBody
	public TaotaoResult getItemBaseInfo(@PathVariable("itemId") long id){
		TaotaoResult result = itemService.getItemBaseInfo(id);
		return result;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable("itemId") long id){
		TaotaoResult result = itemService.getItemDesc(id);
		return result;
	}
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable("itemId") long id){
		TaotaoResult result = itemService.getItemParam(id);
		return result;
	}
}
