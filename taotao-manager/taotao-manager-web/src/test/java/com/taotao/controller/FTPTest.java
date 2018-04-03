package com.taotao.controller;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.taotao.common.utils.FtpUtil;

public class FTPTest {
	@Test
	public void testFtpClient() throws Exception {
		//1.创建ftpClient对象
		FTPClient ftpClient = new FTPClient();
		//2.创建ftp链接
		ftpClient.connect("192.168.159.128", 21);
		//3.登录ftp服务器，使用ftp用户名和密码
		ftpClient.login("admin", "admin");
		//4.上传文件
		//4.1读取本地文件
		FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\kpt.png"));
		//4.2设置上传的路径
		ftpClient.changeWorkingDirectory("/usr/local/nginx/html/images");
		//4.3ftp默认文件传输，图片二进制，需要修改上传文件的格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		//4.上传文件
		ftpClient.storeFile("hello.jpg", fileInputStream);//服务器端文件名，上传文件的inputStream输入流
		//5.关闭链接
		ftpClient.logout();
	}
	
	@Test
	public void testFtpUtil() throws Exception{
		//4.1读取本地文件
		FileInputStream fileInputStream = new FileInputStream(new File("C:\\Users\\Administrator\\Desktop\\kpt.png"));
		FtpUtil.uploadFile("192.168.159.128", 21, "admin", "admin", "/usr/local/nginx/html/images", "/2018/03/07", "hello.jpg", fileInputStream);
	}
}
