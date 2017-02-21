package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.CacheSyncService;

@Controller
@RequestMapping("/cache/sync")
public class CacheSyncController {

	@Autowired
	private CacheSyncService cacheSyncService;

	@RequestMapping("/contentList/{contentCategoryId}")
	@ResponseBody
	public TaotaoResult syncContentList(@PathVariable long contentCategoryId) {
		TaotaoResult result = cacheSyncService.syncContentList(contentCategoryId);
		return result;
	}
	
	@RequestMapping("/itemCategoryList")
	@ResponseBody
	public TaotaoResult syncItemCategoryList(){
		TaotaoResult result = cacheSyncService.syncItemCategoryList();
		return result;
	}
}
