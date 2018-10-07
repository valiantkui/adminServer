package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dao.ResourceDao;
import entity.D_s;
import entity.Direction;
import entity.P_d;
import tools.DoFileUpload;
import tools.Gloable;

@Controller
@RequestMapping("resource")
public class ResourceController {

	@Resource(name="resourceDao")
	private ResourceDao resourceDao;
	
	@RequestMapping("resourceList")
	public String resourceList(HttpServletRequest request) {
		
		String type = request.getParameter("type");
		String targetView = request.getParameter("targetView");
		
		List<Resource> resourceList = resourceDao.findResourceByType(type);
		
		request.setAttribute("resourceList", resourceList);
		request.setAttribute("type",type);
		
		
		return "resource_info";
	}
	
	/**
	 * 添加一个资源（其资源可以是  考研院校信息，历年院校录取信息，历年公司招聘信息），
	 * 
	 * @param request
	 * @return 返回到添加资源的视图
	 */
	@RequestMapping("addResource")
	public String addResource(HttpServletRequest request) {
		String type = request.getParameter("type");
		
		request.setAttribute("type", type);
		
		
		return "add_resource";
	}
	
	@RequestMapping("doAddResource")
	public String doAddResource(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"尝试添加资源");
		System.out.println("doAddMasterDirection()");
		String title = null;
		String type = null;
		String intro = "";
		 String filePath =  "";
		
		//1.创建一个文件解析工厂
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		//2.创建一个文件解析器
		ServletFileUpload upload=new ServletFileUpload(dfif);
		upload.setFileSizeMax(1024*1024*5);//允许上传的单个文件最大为5Mb
		upload.setSizeMax(1024*1024*10);//允许上传的总共文件大小最大为10mb
		
		//设置中文上传处理
		upload.setHeaderEncoding("UTF-8"); 
		try {
			//对request请求来的数据或文件进行分析
			//parseRequest方法返回一个FileItem类型的集合
			List<FileItem> list=upload.parseRequest(request);
			for(FileItem item:list)
			{
				//如果item是普通输入框（即不是文件输入框）
				if(item.isFormField())
				{
					//System.out.println(item.getFieldName()+"  :"+item.getString("utf-8"));
					switch(item.getFieldName())
					{	
						case "title":
							title=item.getString("utf-8");
							break;
						case "type":
							type=item.getString("utf-8");
							break;
						case "intro":
							intro=item.getString("utf-8");
							break;				
					}
				}
				else {		
					switch(item.getFieldName())
					{
						case "file":	
							if("1".equals(type)) {
								filePath = DoFileUpload.fileUpload(Gloable.masterCollegeInfoParentPath, item);
								break;
							}
							if("2".equals(type)) {
								filePath = DoFileUpload.fileUpload(Gloable.masterEnrollInfoParentPath, item);
								break;
							}
							if("3".equals(type)) {
								filePath = DoFileUpload.fileUpload(Gloable.jobRecruitmentInfoParentPath, item);
								break;
							}
							break;
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return "redirect:/ok/add_masterDirection_fail.jsp";
		}catch(Exception e )
		{
			e.printStackTrace();
			return "redirect:/ok/add_masterDirection_fail.jsp";
		}
		//将表单中获取的数据存到数据库
		
		entity.Resource resource = new entity.Resource();
		resource.setTitle(title);
		resource.setIntro(intro);
		resource.setType(type);
		resource.setPath(filePath);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String update_date = sdf.format(date);
		resource.setUpdate_date(update_date);
		
		resourceDao.addResource(resource);
		Logger.getLogger(SubjectController.class).info(a_id+"添加了资源:"+title);
		
		return "redirect:/ok/add_masterDirection_ok.jsp";
	}
	
	@RequestMapping("updateResource")
	public String updateResource(HttpServletRequest request) {
		int r_no = Integer.parseInt(request.getParameter("r_no"));
		//根据r_no，从数据库种查找其信息
		entity.Resource resource = resourceDao.findResourceByR_no(r_no);
		request.setAttribute("resource", resource);
		
		return "update_resource";
	}
	
	@RequestMapping("doUpdateResource")
	public String doUpdateResource(HttpServletRequest request) {
		System.out.println("doAddMasterDirection()");
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"尝试更新资源");
		String r_no = null;
		String title = null;
		String type = null;
		String intro = "";
		 String filePath =  "";
		
		//1.创建一个文件解析工厂
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		//2.创建一个文件解析器
		ServletFileUpload upload=new ServletFileUpload(dfif);
		upload.setFileSizeMax(1024*1024*5);//允许上传的单个文件最大为5Mb
		upload.setSizeMax(1024*1024*10);//允许上传的总共文件大小最大为10mb
		
		//设置中文上传处理
		upload.setHeaderEncoding("UTF-8"); 
		try {
			//对request请求来的数据或文件进行分析
			//parseRequest方法返回一个FileItem类型的集合
			List<FileItem> list=upload.parseRequest(request);
			for(FileItem item:list)
			{
				//如果item是普通输入框（即不是文件输入框）
				if(item.isFormField())
				{
					//System.out.println(item.getFieldName()+"  :"+item.getString("utf-8"));
					switch(item.getFieldName())
					{	
						case "r_no":
							r_no=item.getString("utf-8");
							break;
						case "title":
							title=item.getString("utf-8");
							break;
						case "type":
							type=item.getString("utf-8");
							break;
						case "intro":
							intro=item.getString("utf-8");
							break;				
					}
				}
				else {		
					switch(item.getFieldName())
					{
						case "file":	
							if("1".equals(type)) {
								filePath = DoFileUpload.fileUpload(Gloable.masterCollegeInfoParentPath, item);
								break;
							}
							if("2".equals(type)) {
								filePath = DoFileUpload.fileUpload(Gloable.masterEnrollInfoParentPath, item);
								break;
							}
							if("3".equals(type)) {
								filePath = DoFileUpload.fileUpload(Gloable.jobRecruitmentInfoParentPath, item);
								break;
							}
							break;
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return "redirect:/ok/fail.jsp";
		}catch(Exception e )
		{
			e.printStackTrace();
			return "redirect:/ok/fail.jsp";
		}
		//将表单中获取的数据存到数据库
		
		entity.Resource resource = resourceDao.findResourceByR_no(Integer.parseInt(r_no));
		resource.setTitle(title);
		resource.setIntro(intro);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String update_date = sdf.format(date);
		resource.setUpdate_date(update_date);
		if(filePath != null && !"".equals(filePath)) {
			resource.setPath(filePath);
			
		}
		
		resourceDao.updateResource(resource);
		Logger.getLogger(SubjectController.class).info(a_id+"更新了资源:"+title);
		return "redirect:/ok/ok.jsp";
	}
	
	@RequestMapping("deleteResource")
	public String deleteResource(HttpServletRequest request) {
		String r_no = request.getParameter("r_no");
		String type = request.getParameter("type");
		
		resourceDao.deleteResourceByR_no(Integer.parseInt(r_no));
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"删除了学科:"+r_no);
		return "redirect:/resource/resourceList?type="+type;
		
	}
}
