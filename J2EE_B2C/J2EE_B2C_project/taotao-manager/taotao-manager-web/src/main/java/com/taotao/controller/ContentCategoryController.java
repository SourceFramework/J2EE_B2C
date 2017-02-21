package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/list")
	public @ResponseBody List<EasyUITreeNode> getContentCategoryList(
			@RequestParam(value = "id", defaultValue = "0") long parentId) {
		List<EasyUITreeNode> list = contentCategoryService.getContentCategoryList(parentId);
		return list;
	}

	@RequestMapping(value = "/create",method=RequestMethod.POST)
	public @ResponseBody TaotaoResult createContentCategory(long parentId,String Name){
		TaotaoResult result = contentCategoryService.createContentCategory(parentId, Name);
		return result;
	}

	@RequestMapping(value = "/delete",method=RequestMethod.POST)
	public @ResponseBody TaotaoResult deleteContentCategory(long id){
		TaotaoResult result = contentCategoryService.deleteContentCategory(id);
		return result;
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public @ResponseBody TaotaoResult renameContentCategory(long id, String name){
		TaotaoResult result = contentCategoryService.renameContentCategory(id, name);
		return result;
	}

}
