package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
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
import org.springframework.web.bind.annotation.ResponseBody;

import dao.D_sDao;
import dao.DirectionDao;
import dao.P_dDao;
import dao.ProfessionDao;
import dao.SubjectDao;
import entity.D_s;
import entity.Direction;
import entity.P_d;
import entity.P_s;
import entity.Profession;
import entity.S_s;
import entity.Subject;
import tools.DoFileUpload;
import tools.Gloable;

@Controller
@RequestMapping(value="/master")
public class MasterController {
	
	@Resource(name="directionDao")
	private DirectionDao directionDao;

	@Resource(name="professionDao")
	private ProfessionDao professionDao;
	
	@Resource(name="subjectDao")
	private SubjectDao subjectDao;
	
	@Resource(name="p_dDao")
	private P_dDao p_dDao;
	
	@Resource(name="d_sDao")
	private D_sDao d_sDao;
	
	
	
	@RequestMapping(value="/checkD_no",produces="application/json;charset=utf-8")
	@ResponseBody
	public int checkD_no(@RequestParam("d_no") String d_no) {
		System.out.println("checkD_no()"+d_no);
		
		Direction direction = directionDao.findMasterDirectionByD_no(d_no);
		if(direction == null) {	//说明当前编号无人使用
			return 0;
		} 		
		return 1;
	}
	
	
	
	/**
	 * 查找出所有的方向
	 */
	@RequestMapping(value="/masterDirectionList",produces="application/json;charset=utf-8")
	public String masterDirection(HttpServletRequest request) {
		String p_no = request.getParameter("p_no");//获取p_no
		String p_name = request.getParameter("p_name");//获取p_name
		
		List<Direction> directionList = null;
		if(p_no != null) {//查找某专业的所有考研方向
			directionList = directionDao.findMasterDirectionByP_no(p_no);
			request.setAttribute("professionInfo", p_name);
		}else {
			
			request.setAttribute("professionInfo", "全部");
			directionList =  directionDao.findAllMasterDirection();
		}
		List<Profession> professionList = professionDao.findAll();
		
		request.setAttribute("directionList", directionList);
		request.setAttribute("professionList",professionList );
		
		return "master_direction";	//转发到subject.jsp
	}
	
	@RequestMapping(value="/addMasterDirection",produces="application/json;charset=utf-8")
	public String addMasterDirection(HttpServletRequest request) {
		
		//获取所有的学科
		List<Subject> subjectList = subjectDao.findAllSubject();
		request.setAttribute("subjectList", subjectList);
		
		List<Profession> professionList = professionDao.findAll();
		request.setAttribute("professionList", professionList);
		
		return "add_masterDirection";
	}
	@RequestMapping(value="/doAddMasterDirection",produces="application/json;charset=utf-8")
	public String doAddMasterDirection(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"尝试添加考研方向");
		System.out.println("doAddMasterDirection()");
		String d_no = null;
		String name = null;
		String type = null;
		String intro_link = "";
		String vista_link = "";
		
		List<String> subjectList = new ArrayList<>();
		List<String> professionList = new ArrayList<>();
		
		 String imgAddress =  "";
		
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
						case "d_no":
							d_no=item.getString("utf-8");
							break;
						case "name":
							name=item.getString("utf-8");
							break;
						case "type":
							type=item.getString("utf-8");
							break;
						case "profession":
							String profession=item.getString("utf-8");
							professionList.add(profession);
							break;
						case "base_subject":							
							String subject=item.getString("utf-8");
							subjectList.add(subject);
							break;
					}
				}
				else {		
					switch(item.getFieldName())
					{
						case "intro_link":	
							intro_link = DoFileUpload.fileUpload(Gloable.masterParentPath+d_no+"//",item);
							break;
						case "vista_link":
							vista_link = DoFileUpload.fileUpload(Gloable.masterParentPath+d_no+"//",item);
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
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String update_date = sdf.format(date);
		
		Direction direction = new Direction();
		direction.setD_no(d_no);
		direction.setName(name);
		direction.setType(type);
		direction.setIntro_link(intro_link);
		direction.setVista_link(vista_link);
		direction.setUpdate_date(update_date);
		
		directionDao.addDirection(direction);
		
		D_s d_s = null;
		for(String pre_subject: subjectList)
		{
			d_s = new D_s();
			d_s.setD_no(d_no);
			d_s.setS_no(pre_subject);
			d_sDao.addD_s(d_s);
		}
		
		P_d p_d = null;
		for(String p_no:professionList) {
			p_d = new P_d();
			p_d.setD_no(d_no);
			p_d.setP_no(p_no);
			p_dDao.addP_d(p_d);
		}
		
	
		Logger.getLogger(SubjectController.class).info(a_id+"添加了考研方向"+d_no);
		return "redirect:/ok/add_masterDirection_ok.jsp";
	}
	
	@RequestMapping(value="/updateMasterDirection",produces="application/json;charset=utf-8")
	public String updateMasterDirection(HttpServletRequest request) {
		//1.获取学科编号
				String d_no = request.getParameter("d_no");
				Direction direction = directionDao.findMasterDirectionByD_no(d_no);
				//3.查询出与该学科相关的所有专业			
				List<Profession> professionList = professionDao.findAll();
				List<Profession> pfList = professionDao.findProfessionByD_no(d_no);
				
				System.out.println(pfList);
				
				//4.查询出与该学科相关的学科(前继学科，也就是说  该学科的所需基础学科)
				List<Subject> subjectList = subjectDao.findAllSubject();
				List<Subject> sjList = subjectDao.findSubjectByD_no(d_no);
				
				//	TODO
				
				request.setAttribute("direction", direction);
				request.setAttribute("professionList", professionList);
				request.setAttribute("pfList", pfList);
				request.setAttribute("subjectList", subjectList);
				request.setAttribute("sjList", sjList);	
		return "update_masterDirection";
	}
	
	
	@RequestMapping(value="/doUpdateMasterDirection",produces="application/json;charset=utf-8")
	public String doUpdateMasterDirection(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"尝试更新考研方向");
		System.out.println("doUpdateMasterDirection()");
		String d_no = null;
		String name = null;
		String type = null;
		String intro_link = "";
		String vista_link = "";
		
		List<String> subjectList = new ArrayList<>();
		List<String> professionList = new ArrayList<>();
		 String imgAddress =  "";
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
			DoFileUpload doFileUpload = new DoFileUpload();
			for(FileItem item:list)
			{
				//如果item是普通输入框（即不是文件输入框）
				if(item.isFormField())
				{
					//System.out.println(item.getFieldName()+"  :"+item.getString("utf-8"));
					switch(item.getFieldName())
					{
						case "d_no":
							d_no=item.getString("utf-8");
							break;
						case "name":
							name=item.getString("utf-8");
							break;
						case "type":
							type=item.getString("utf-8");
							break;
						case "profession":
							String profession=item.getString("utf-8");
							professionList.add(profession);
							break;
						case "base_subject":							
							String subject=item.getString("utf-8");
							subjectList.add(subject);
							break;
					}
				}
				else {		
					switch(item.getFieldName())
					{
						case "intro_link":	
							intro_link = DoFileUpload.fileUpload(Gloable.masterParentPath+d_no+"//",item);
							break;
						case "vista_link":
							vista_link = DoFileUpload.fileUpload(Gloable.masterParentPath+d_no+"//",item);
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
		Direction direction = directionDao.findMasterDirectionByD_no(d_no);
		direction.setName(name);
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String update_date = sdf.format(date);
		direction.setUpdate_date(update_date);
		
		if(intro_link != null && ! "".equals(intro_link)) {
			direction.setIntro_link(intro_link);
		}
		if(vista_link != null && !"".equals(vista_link)) {
			direction.setVista_link(vista_link);
		}
		
		
		directionDao.updateDirection(direction);
		
		d_sDao.deleteByD_no(d_no);
		p_dDao.deleteByD_no(d_no);
		
		D_s d_s = null;
		for(String pre_subject: subjectList)
		{
			d_s = new D_s();
			d_s.setD_no(d_no);
			d_s.setS_no(pre_subject);
			d_sDao.addD_s(d_s);
		}
		
		P_d p_d = null;
		for(String p_no:professionList) {
			p_d = new P_d();
			p_d.setD_no(d_no);
			p_d.setP_no(p_no);
			p_dDao.addP_d(p_d);
		}
		Logger.getLogger(SubjectController.class).info(a_id+"更新了考研方向:"+d_no);
		
		return "redirect:/ok/add_masterDirection_ok.jsp";

	}
	
	@RequestMapping(value="/deleteDirectionByD_no",produces="application/json;charset=utf-8")
	public String deleteDirectionByD_no(@RequestParam("d_no") String d_no,HttpServletRequest request) {
		System.out.println("deleteDirectionByD_no()");
		//删除与该方向的所有内容（包括 d_s,p_d,direction表）
		
		p_dDao.deleteByD_no(d_no);
		
		d_sDao.deleteByD_no(d_no);
		
		directionDao.deleteByD_no(d_no);
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"删除了考研方向:"+d_no);
		return "redirect:/master/masterDirectionList";
	}

	
	
}
