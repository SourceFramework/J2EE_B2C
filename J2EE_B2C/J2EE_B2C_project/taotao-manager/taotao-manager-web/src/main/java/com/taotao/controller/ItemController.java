package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	public @ResponseBody TbItem getItemById(@PathVariable long itemId) throws Exception {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}

	@RequestMapping("/item/list")
	
	public @ResponseBody EasyUIDataGridResult getItemList(int page, int rows) throws Exception {
		EasyUIDataGridResult itemList = itemService.getItemList(page, rows);
		return itemList;
	}
	
	/**
	 * 添加商品
	 * @param item 商品基本信息
	 * @param desc 商品详细描述
	 * @param itemParams 商品规格参数
	 * @return 自定义结果类型
	 * @throws Exception
	 */
	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	public @ResponseBody TaotaoResult createItem(TbItem item,String desc,String itemParams) throws Exception {
		TaotaoResult result = itemService.createItem(item,desc,itemParams);
		return result;
	}
	
	

}
