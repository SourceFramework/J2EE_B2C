package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CategoryResult;
import com.taotao.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping(value = "/itemcat/all", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE
			+ ";charset=utf-8")
	public @ResponseBody String getItemCatList(String callback) {
		CategoryResult result = itemCatService.getItemCatList();
		String json = JsonUtils.objectToJson(result);
		String returnValue = callback + "(" + json + ");";
		return returnValue;
	}
}
