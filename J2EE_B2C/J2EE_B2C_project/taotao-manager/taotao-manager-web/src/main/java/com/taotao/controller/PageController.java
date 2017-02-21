package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面处理
 * @author mbc1996
 *
 */
@Controller
public class PageController {
	//打开首页
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	//首页的页面跳转
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page){
		return page;
	}
}
