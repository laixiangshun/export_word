package com.java.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.java.utils.WordUtils;

/**
 * 导出带图片的word
 * @author lailai
 *
 */
@Controller
public class exportWordController {

	@RequestMapping(value="/index.html",method=RequestMethod.GET)
	public String backHtml(HttpServletRequest request,HttpServletResponse response) throws Exception{
		return "index";
	}
	/**
	 * 导出word
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/expotrwors.html",method=RequestMethod.GET)
	public String exportWord(HttpServletRequest request,HttpServletResponse response,final Model model) throws Exception{
		final WordUtils word=new WordUtils();
		final Map<String, Object> dataMap=new HashMap<>();
		
		dataMap.put("title", "java做word导出功能");  
        dataMap.put("name", "张三");  
        dataMap.put("idCard", "222222222222222222");  
        dataMap.put("sex", "男"); 
        String path=this.getClass().getResource("/").getPath();
        System.out.println(this.getClass().getResource("/").getPath());
        dataMap.put("img", word.getImageStr(path+"img/Koala.jpg"));
        final String filepath=("C:/Users/lailai/Desktop/word.doc").replace("/", File.separator);
        File file=new File(filepath);
        if(file.exists()){
        	file.delete();
        }
        final ExecutorService executor=Executors.newCachedThreadPool();
        executor.execute(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				word.ExportWord(dataMap, filepath);
				// executor.shutdown();
			}
		});
       
       // word.ExportWord(dataMap, filepath);
        model.addAttribute("message","导出成功");
		return "HelloWorld";
	}
}
