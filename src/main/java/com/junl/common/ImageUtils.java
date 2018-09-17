package com.junl.common;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageUtils {
	
	
	/**
	 *  根据文字生成透明图片水印
	 * @param waterMarkStr  水印文字
	 * @param imagePath		水印图片存放位置
	 * @param font			水印文字字体
	 * @param artWorkImage	添加水印的图片
	 * @throws IOException 
	 */
	public static void generratorImageByStr(String waterMarkStr,String imagePath,Font font,BufferedImage artWorkImage) throws IOException
	{
		//两边的边距 32+32  
		int LRWidth = 34;
		//根据字符串的字体   获取相应的宽高
		Map<String,Object> map = getWidthAndHeightByStrAndFont(waterMarkStr,font,artWorkImage,LRWidth);
		//（水印图片宽度）
		int ImageWidth = (map.get("width")!=null?Integer.valueOf(map.get("width").toString()):0);
		//上下的边距 本身默认 上+下=15  （水印图片高度）
		int ImageHeight = (map.get("height")!=null?Integer.valueOf(map.get("height").toString()):0);
		//每行的字符
		List<String> waterMarkStrList =  (List<String>) map.get("waterMarkStrList");
		//一行的高度
		int oneRowHeight = Integer.valueOf(map.get("oneRowHeight").toString());
		//生成图片
		BufferedImage image = new BufferedImage(ImageWidth, ImageHeight, BufferedImage.TYPE_INT_RGB);
		//得到画笔
		Graphics2D graphics = image.createGraphics();
		//-----------------------------------先设置样式在  画图
		//设置透明背景
		image = graphics.getDeviceConfiguration().createCompatibleImage(ImageWidth, ImageHeight, Transparency.TRANSLUCENT);
		graphics.dispose();
		//----------------------------
		//重新获取  图片背景为透明的画笔
		graphics = image.createGraphics();
		//设置字体
		graphics.setFont(font);
		//设置画笔颜色
		graphics.setColor(Color.RED);
		//设置画笔的粗细
		graphics.setStroke(new BasicStroke(1f));
		
		//在什么位置写入waterMarkStr { x = 左边的坐标    y = 下面的坐标   }  左下角的坐标     一个draw  是一行
		for (int i = 0; i < waterMarkStrList.size(); i++) {
			graphics.drawString(waterMarkStrList.get(i), LRWidth/2, oneRowHeight*(i+1)-15/2);
		}
		graphics.dispose();
		//-----------------------------------
		ImageIO.write(image, "png", new File(imagePath));
	}
	
	
	/**
	 * 
	 * 根据字符串和字体得到宽高（如果长度大于图片一半的长度换行）    高默认上下共有15间距   
	 * 
	 * @param waterMarkStr
	 * @param font
	 * @param artWorkImage  原图（上传的图片）
	 * @param LRWidth  		水印图片两边的间距
	 * @return key: width  height  waterMarkStrList（存放没一行的字符）   oneRowHeight(每一行的高度)
	 */
	public static Map<String,Object> getWidthAndHeightByStrAndFont(String waterMarkStr,Font font,BufferedImage artWorkImage,int LRWidth)
	{
		@SuppressWarnings("restriction")
		FontMetrics fm = sun.font.FontDesignMetrics.getMetrics(font);
		//是否要换行
		boolean isC = false;
		String tmpStr = "";
		//存放没行的字符串
		List<String> returnWaterMarkStrList = new ArrayList<String>();
		//一共多少行
		int lineCount = 1;
		//根据图片宽度实现字符换行
		if(waterMarkStr != null && !waterMarkStr.equals(""))
		{
			char[] c = waterMarkStr.toCharArray();
			for (int i = 0; i < c.length; i++) {
				char tmp = c[i];
				int tmpSzie = 0;
				//判断是否是中文
				if(i != (c.length-1))
				{
					if(Character.isLetter(c[i+1]))
					{
						tmpSzie = 32;
					}
					else
					{
						tmpSzie = 17;
					}
				}
				//先判断  是否要换行
			 	if(artWorkImage.getWidth()-LRWidth < fm.stringWidth(tmpStr)+tmpSzie)
			    {
			    	returnWaterMarkStrList.add(tmpStr);
			    	isC = true;
			    	lineCount++;
			    	tmpStr = "";
			    }
			    else if(i == (c.length-1))
			    {
			    	tmpStr += tmp;
			    	returnWaterMarkStrList.add(tmpStr);
			    }
			    tmpStr += tmp;
			} 
			
		}
		int width = 0;
		int height = 0;
		if(isC)
		{
			width = artWorkImage.getWidth();
			height = fm.getHeight()*lineCount;
		}
		else
		{
			width = fm.stringWidth(waterMarkStr)+LRWidth;
			height = fm.getHeight();
		}
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("width", width);
		returnMap.put("height", height);
		returnMap.put("waterMarkStrList", returnWaterMarkStrList);
		returnMap.put("oneRowHeight", fm.getHeight());
		return returnMap;
	}
	
	
	/**
	 * 	自定义字体
	 * @param fontPath  字体路径  项目的相对路径
	 * @return
	 * @throws FontFormatException
	 * @throws IOException
	 */
	public static Font customFont(String fontPath) throws FontFormatException, IOException
	{
			//自定义字体的路径
		  File file = new File(fontPath);
		  FileInputStream aixing = new FileInputStream(file);
		  Font font = Font.createFont(Font.TRUETYPE_FONT, aixing);
		  Font dynamicFontPt = font.deriveFont(30f);//设置字体大小
		  return dynamicFontPt;
	}

}
