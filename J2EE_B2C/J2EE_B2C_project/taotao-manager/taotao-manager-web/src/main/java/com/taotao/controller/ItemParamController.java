package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {

	@Autowired
	private ItemParamService itemParamService;

	@RequestMapping(value = "/query/itemcatid/{itemCatId}")
	public @ResponseBody TaotaoResult getItemParamByCid(@PathVariable Long itemCatId) {
		TaotaoResult result = itemParamService.getItemParamByCid(itemCatId);
		return result;
	}

	@RequestMapping(value = "/save/{itemCatId}")
	public @ResponseBody TaotaoResult createItemParam(String paramData, @PathVariable Long itemCatId) {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setParamData(paramData);
		itemParam.setItemCatId(itemCatId);
		TaotaoResult  result = itemParamService.createItemParam(itemParam);
		return result;
	}
}
