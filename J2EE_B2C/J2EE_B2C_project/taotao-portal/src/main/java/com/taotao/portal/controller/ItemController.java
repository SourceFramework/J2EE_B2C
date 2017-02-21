package com.taotao.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemBaseInfo;
import com.taotao.portal.service.ItemService;

@Controller
@RequestMapping("/item")
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/{itemId}")
	public String getItemBaseInfo(@PathVariable("itemId") long id, Model model) {
		ItemBaseInfo itemBaseInfo = itemService.getItemBaseInfo(id);
		model.addAttribute("item", itemBaseInfo);
		return "item";
	}

	@RequestMapping(value = "/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable("itemId") long id) {
		String itemDesc = itemService.getItemDesc(id);
		return itemDesc;
	}
	
	@RequestMapping(value="/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable("itemId") long id){
		String itemParam = itemService.getItemParam(id);
		return itemParam;
	}
}
