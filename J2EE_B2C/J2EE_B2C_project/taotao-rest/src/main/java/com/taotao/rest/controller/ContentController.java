package com.taotao.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.pojo.TbContent;
import com.taotao.rest.service.ContentService;

/**
 * 
 * @author mbc1996
 *
 */
@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/list/{contentCategoryId}")
	public @ResponseBody TaotaoResult getContentListByCatId(
			@PathVariable(value="contentCategoryId") long categoryId) {
		try {
			List<TbContent> contentList = contentService.getContentListByCatId(categoryId);
			return TaotaoResult.ok(contentList);			
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
	}
}
