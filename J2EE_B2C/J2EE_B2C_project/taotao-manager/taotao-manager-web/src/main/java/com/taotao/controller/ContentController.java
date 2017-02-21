package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;

/**
 * 内容管理CMS 表现层
 * 
 * @author mbc1996
 *
 */
@Controller
@RequestMapping(value = "/content")
public class ContentController {
	@Autowired
	private ContentService contentService;

	@RequestMapping(value = "/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentListByCatId(int page, int rows, long categoryId) {
		EasyUIDataGridResult result = contentService.getContentListByCatId(page, rows, categoryId);
		return result;
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createContent(TbContent content) {
		TaotaoResult result = contentService.createContent(content);
		return result;
	}

}
