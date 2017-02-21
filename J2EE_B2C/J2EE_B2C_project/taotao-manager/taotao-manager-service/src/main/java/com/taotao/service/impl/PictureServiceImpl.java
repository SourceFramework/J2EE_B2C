package com.taotao.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {
	@Value("${FTP_ADDRESS}")
	private   String host;
	@Value("${FTP_PORT}")
	private   int    port;
	@Value("${FTP_USERNAME}")
	private   String username;
	@Value("${FTP_PASSWORD}")
	private   String password;
	@Value("${FTP_BASE_PATH}")
	private   String basePath;
	//图片服务器的基础路径
	@Value("${IMAGE_BASE_URL}")
	private   String image_base_url;

	/**
	 * 上传图片功能
	 */
	public Map uploadPicture(MultipartFile uploadFile) {
		Map resultMap = new HashMap<>();
		
		String oldName = uploadFile.getOriginalFilename();
		String filename = IDUtils.generateImageName();
		// 添加文件后缀
		filename += oldName.substring(oldName.lastIndexOf("."));
		String filePath = new DateTime().toString("/yyyy/MM/dd");
		try {
			boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, filename,
					uploadFile.getInputStream());
			if(!result){
				resultMap.put("error", 1);
				resultMap.put("message", "上传图片失败");
				return resultMap;
			}else{
				resultMap.put("error", 0);
				resultMap.put("url", image_base_url+filePath+"/"+filename);
				return resultMap;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			resultMap.put("error", 1);
			resultMap.put("message", "上传图片发生异常");
			return resultMap;
		}

	}

}
