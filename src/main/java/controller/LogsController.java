package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tools.Gloable;

@Controller
@RequestMapping("/logs")
public class LogsController {

	
	
	@RequestMapping("/findAllLogs")
	public String findAllLogs(HttpServletRequest request) {
		//1.读取本地的日志文件
		
		List<String> logList = new ArrayList<>();
		
		String path = Gloable.logsPath;
		File directory = new File(path);
		
		if(!directory.exists()) {
			Logger.getLogger(LogsController.class).info("找不到指定的日志路径");
			return "main";
		}else {
			
			File[] logFiles =  directory.listFiles();
			
			for(File f: logFiles) {
				logList.add(f.getName());
			}
			request.setAttribute("logList", logList);
			return "main";
		}
		
	}
	
	@RequestMapping("/loadLogByName")
	
	public void loadLogByName(HttpServletRequest request,HttpServletResponse response) {
		String logName = request.getParameter("logName");
		
		String path = Gloable.logsPath;
		
		//将file读取到  项目的缓冲下
		FileInputStream fis = null;
		OutputStream fos = null;
		try {
			File file = new File(path+logName);
			fis = new FileInputStream(file);
			
			
			//fos = new FileOutputStream("index.html");
			
			response.setContentType("text/html; charset=UTF-8");
			fos  = response.getOutputStream();
			byte[] bytes = new byte[1024*10];
			int i = -1;
			while((i=fis.read(bytes)) != -1) {
				fos.write(bytes, 0, i);;
			}
			
			fos.flush();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			//return "fail";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return "fail";
		} finally {
			
			try {
				if(fis != null) fis.close();
				if(fos != null) fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//return "ok";
		
	}
}
