package com.taotao.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.utils.FtpUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.service.PictureService;
/*
 * 图片上传服务器
 */
@Service
public class PictureServiceImpl implements PictureService {
	/*从spring容器中加载配置文件*/
	@Value("${FTP_ADDRESS}")
	private String FTP_ADDRESS;
	@Value("${FTP_PORT}")
	private Integer FTP_PORT;
	@Value("${FTP_USERNAME}")
	private String FTP_USERNAME;
	@Value("${FTP_PASSWORD}")
	private String FTP_PASSWORD;
	@Value("${FTP_BASE_PATH}")
	private String FTP_BASE_PATH;
	@Value("${IMAGE_BASE_URL}")
	private String IMAGE_BASE_URL;
	
	@Override
	public Map uploadPicture(MultipartFile uploadFile){
		Map resultMap = new HashMap<>();
		try {
			//取原文件名
			String oldName = uploadFile.getOriginalFilename();
			//取原文件名的扩展名
			String extenName = oldName.substring(oldName.lastIndexOf('.'));
			//生成新的文件名
			String newName = IDUtils.genImageName();
			//新文件名加上原有文件扩展名
			newName = newName + extenName;
			String imagePath = new DateTime().toString("/yyyy/MM/DD");
			//图片上传
			boolean result = FtpUtil.uploadFile(FTP_ADDRESS, FTP_PORT, FTP_USERNAME, FTP_PASSWORD, FTP_BASE_PATH,
					imagePath, newName, uploadFile.getInputStream());
			//返回结果
			if(!result) {
				resultMap.put("error", 1);
				resultMap.put("message", "文件上传失败！");
				return resultMap;
			}
			resultMap.put("error", 0);
			resultMap.put("url", IMAGE_BASE_URL+imagePath+"/"+newName);
			return resultMap;
		}catch(Exception e) {
			e.getStackTrace();
			resultMap.put("error", 1);
			resultMap.put("message", "文件上传异常！");
			return resultMap;
		}
	}

}
