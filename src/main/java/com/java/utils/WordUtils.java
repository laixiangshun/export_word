package com.java.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.logging.LogFactory;
import org.springframework.util.Base64Utils;

import sun.misc.BASE64Encoder;
import biz.source_code.base64Coder.Base64Coder;
import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 到word的工具类
 * @author lailai
 *
 */
public class WordUtils {
	
	private Configuration configuration=null;
	public WordUtils(){
		configuration=new Configuration();
		configuration.setDefaultEncoding("utf-8");
	}
	public void ExportWord(Map<String, Object> datamap,String path){
		//FreeMarker支持多种模板装载方法。可以从servlet，classpath，数据库装载，  
	    //这里我们的模板是放在template包下面  
		System.out.println(WordUtils.class.getClassLoader().getResource("/template/"));
		configuration.setClassForTemplateLoading(WordUtils.class, "/template/");
		Template t=null;
		try {
			t=configuration.getTemplate("fctestpaper2.ftl");//添加插入图片处理
			File file=new File(path);
			Writer out=null;
			FileOutputStream fou=new FileOutputStream(file);
			OutputStreamWriter writer=new OutputStreamWriter(fou,"UTF-8");
			//这个地方对流的编码不可或缺，使用main（）单独调用时，应该可以，
			//但是如果是web请求导出时导出后word文档就会打不开，并且包XML文件错误。主要是编码格式不正确，无法解析。  
	        //out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
			out=new BufferedWriter(writer);
			t.process(datamap, out);//输出word文件
			out.close();
			writer.close();
			fou.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 对图片处理
	 * @param path
	 * @return
	 */
	public String getImageStr(String path){
		InputStream in=null;
		byte[] data=null;
		try {
			in=new FileInputStream(path);
			data=new byte[in.available()];
			in.read(data);
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		BASE64Encoder encoder=new BASE64Encoder();
		return encoder.encode(data);
	}
}
