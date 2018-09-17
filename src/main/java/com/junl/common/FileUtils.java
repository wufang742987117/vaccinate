package com.junl.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class FileUtils {
	
	
	/**
	 * 获取当前服务器根目录(TOMCAT)  D:\\tomcat 1.1\\
	 * @return
	 */
	public static String getServerRootPath()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		String path = serverPath.substring(0, serverPath.indexOf("webapps"));
		return path;
	}
	
	/**
	 * 获取当前项目在服务器的根目录(TOMCAT)  D:\\tomcat 1.1\\webapps\\项目名\\
	 * @return
	 */
	public static String getProjectServerRootPath()
	{
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		return serverPath;
	}
	
	
	
	/**
	 * 上传文件
	 * @param fileServerPath   文件位置
	 * @param fileName		        文件名  带后缀的
	 * @param inputStream	        输入流
	 * @throws IOException
	 */
	public static void copyInputStreamToFile(String fileServerPath,String fileName,InputStream inputStream) throws IOException
	{
		File file = new File(fileServerPath);
		//判断目录是否存在 
		if(!file.isDirectory())
		{
			//不存在则创建该目录  创建文件
			file.mkdirs();
		}
		// 文件读写
		FileOutputStream outputStream = new FileOutputStream(new File(fileServerPath+"/"+fileName));
		byte buffer[]=new byte[1024];
		int tmp = 0;
		while((tmp = inputStream.read(buffer))!=-1){
            for(int i = 0;i < tmp;i++)
            	outputStream.write(buffer[i]);        
        }
		inputStream.close();
		outputStream.close();
	}

}
