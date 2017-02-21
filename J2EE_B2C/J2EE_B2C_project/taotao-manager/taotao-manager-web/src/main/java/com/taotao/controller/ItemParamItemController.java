package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.service.ItemParamItemService;

/**
 * 商品规格参数表现层
 * 
 * @author mbc1996
 *
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;

	@RequestMapping(value = "/list")
	public @ResponseBody EasyUIDataGridResult getItemParamItemList(int page, int rows) {
		EasyUIDataGridResult result = itemParamItemService.getItemParamItemList(page, rows);
		return result;
	}
	
	@RequestMapping("/{itemId}")
	public String showItemParam(@PathVariable Long itemId, Model model) {
		String string = itemParamItemService.getItemParamItemByItemId(itemId);
		model.addAttribute("itemParam", string);
		return "item";
	}

}
