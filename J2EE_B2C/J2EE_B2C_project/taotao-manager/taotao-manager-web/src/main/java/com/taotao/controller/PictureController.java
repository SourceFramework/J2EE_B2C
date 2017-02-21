package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

/**
 * 图片上传控制器
 * @author mbc1996
 *
 */
@Controller
public class PictureController {
	@Autowired
	private PictureService pictureService;
	
	@RequestMapping("/pic/upload")
	public @ResponseBody String uploadPicture(MultipartFile uploadFile){
		Map resultMap = pictureService.uploadPicture(uploadFile);
		//考虑不同浏览器的兼容，此处返回json格式的string,采用jackson工具包
		String json = JsonUtils.objectToJson(resultMap);
		return json;
	} 
}
