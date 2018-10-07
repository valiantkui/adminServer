package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.P_sDao;
import dao.ProfessionDao;
import dao.S_sDao;
import dao.SubjectDao;
import entity.P_s;
import entity.Profession;
import entity.S_s;
import entity.Subject;
import tools.Gloable;

@Controller   //控制层
@RequestMapping("/subject")		
public class SubjectController {	
	
	@Resource(name="professionDao")		
	private ProfessionDao professionDao;
	
	@Autowired
	@Qualifier("professionDao")
	private ProfessionDao professionDao2;

	@Resource(name="subjectDao")
	private SubjectDao subjectDao;

	@Resource(name="s_sDao")
	private S_sDao s_sDao;
	
	@Resource(name="p_sDao")
	private P_sDao p_sDao;
	
	
	@RequestMapping("/subjectList")
	public String subjectList(HttpServletRequest request)  {
		
		String p_no = request.getParameter("p_no");//获取p_no
		String p_name = request.getParameter("p_name");//获取p_no
		
		System.out.println("获取的专业编号："+p_no);
		
		List<Subject> subjectList = null;
		
		if(p_no != null) {//查找某专业的所有学科
			subjectList = subjectDao.findSubjectByP_no(p_no);
			request.setAttribute("professionInfo", p_name);
		}else {
			
			request.setAttribute("professionInfo", "全部");
			subjectList =  subjectDao.findAllSubject();
		}
		List<Profession> professionList = professionDao.findAll();
		
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("professionList",professionList );
		
		return "subject";	//转发到subject.jsp
	}
	
	@RequestMapping(value="/checkS_no",produces="application/json;charset=utf-8")
	@ResponseBody
	public int checkS_no(@RequestParam("s_no") String s_no) {
		System.out.println("checkS_no()----"+s_no);
		Subject subject = subjectDao.findSubjectByS_no(s_no);
		if(subject == null) {//说明当前编号不存在
			return 0;
		}
		
		return 1;
	}
	
	
	@RequestMapping(value="/addSubject",produces="application/json;charset=utf-8")
	public String addSubject(HttpServletRequest request) {
		//获取所有的学科
		List<Subject> subjectList = subjectDao.findAllSubject();
		request.setAttribute("subjectList", subjectList);
		
		List<Profession> professionList = professionDao.findAll();
		request.setAttribute("professionList", professionList);
		
		return "add_subject";
	}
	

	
	@RequestMapping(value="/doAddSubject",produces="application/json;charset=utf-8")
	public String doAddSubject(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"尝试添加学科");
		System.out.println("doAddSubject");
		String s_no = null;
		String name = null;
		String type = null;
		String intro = null;
		//String profession = null;
	//	String base_subject = null;
		
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
			
			//接下来对获取的FileItem类型的集合进行分析,判断每个FileItem是否是文件，根据不同类型做不同的处理
			for(FileItem item:list)
			{
				//如果item是普通输入框（即不是文件输入框）
				if(item.isFormField())
				{
					//System.out.println(item.getFieldName()+"  :"+item.getString("utf-8"));
					switch(item.getFieldName())
					{
						case "s_no":
							s_no=item.getString("utf-8");
							break;
						case "name":
							name=item.getString("utf-8");
							break;
						case "type":
							type=item.getString("utf-8");
							break;
						case "intro":
							intro=item.getString("utf-8").trim();
							break;
						case "profession":
							String profession=item.getString("utf-8");
							professionList.add(profession);
							break;
						case "base_subject":
							
							
							String subject=item.getString("utf-8");
							//System.out.println(subject);
							subjectList.add(subject);
							
							break;
					}
				}
				else {
					//如果是文件输入框，则将请求来的文件保存到本地磁盘
					
					//1.获取文件名
					//System.out.println(item.getName());
					
					if(item.getName()!="")
					{						
						String imgPath=Gloable.subjectParentPath;
						//String imgAddress="";
						String fileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);
						System.out.println(fileName);
						InputStream in=item.getInputStream();
						imgAddress=imgPath+s_no;
						File file=new File(imgAddress);
						if(!file.exists())
						{
							file.mkdirs();
						}						
						imgAddress=imgAddress+"//"+fileName;
						file=new File(imgAddress);
						file.createNewFile();
						FileOutputStream out=new FileOutputStream(file);
						byte[] bytes=new byte[1024*10];//缓冲大小为10k
						int i=-1;
						while((i=in.read(bytes))!=-1)
						{
							out.write(bytes, 0, i);
						}
						
						System.out.print("上传完毕");
						
						
						//pw.println("<center>上传成功！！</center>");
						in.close();
						out.close();
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return "redirect:/ok/add_subject_fail.jsp";
		}catch(Exception e )
		{
			e.printStackTrace();
			return "redirect:/ok/add_subject_fail.jsp";
		}
		
		
		//将表单中获取的数据存到数据库
		
		Subject subject = new Subject();
		subject.setS_no(s_no);
		subject.setName(name);
		subject.setType(type);
		subject.setIntro(intro);
		subject.setImage_address(imgAddress);
		
		subjectDao.addSubject(subject);
		S_s s_s = null;
		for(String pre_subject: subjectList)
		{
			s_s = new S_s();
			s_s.setPre_subject(pre_subject);
			s_s.setNext_subject(s_no);
			s_sDao.addS_s(s_s);
		}
		
		P_s p_s = null;
		for(String p_no:professionList) {
			p_s = new P_s();
			p_s.setP_no(p_no);
			p_s.setS_no(s_no);
			p_sDao.addP_s(p_s);
		}
		
		Logger.getLogger(SubjectController.class).info(a_id+"添加学科:"+s_no+"成功");
		return "redirect:/ok/add_subject_ok.jsp";
	}
	
	/**
	 * 转发到修改页面
	 */
	@RequestMapping(value="/updateSubject",produces="application/json;charset=utf-8")
	public String updateSubject(HttpServletRequest request) {
		//1.获取学科编号
		String s_no = request.getParameter("s_no");
		//2.查询出有关该学科信息
		
		Subject subject =	subjectDao.findSubjectByS_no(s_no);
		//3.查询出与该学科相关的所有专业
		
		List<Profession> professionList = professionDao.findAll();
		List<Profession> pfList = professionDao.findProfessionByS_no(s_no);
		
		System.out.println(pfList);
		
		//4.查询出与该学科相关的学科(前继学科，也就是说  该学科的所需基础学科)
		List<Subject> subjectList = subjectDao.findAllSubject();
		List<Subject> sjList = subjectDao.findPreSubjectByS_no(s_no);
		
		//	TODO
		
		request.setAttribute("subject", subject);
		request.setAttribute("professionList", professionList);
		request.setAttribute("pfList", pfList);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("sjList", sjList);
		
		return "update_subject";
		
	}
	
	
	@RequestMapping(value="/doUpdateSubject",produces="application/json;charset=utf-8")
	public String doUpdateSubject(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"尝试修改学科");
		
		String s_no = null;
		String name = null;
		String type = null;
		String intro = null;
	
		
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
			
			//接下来对获取的FileItem类型的集合进行分析,判断每个FileItem是否是文件，根据不同类型做不同的处理
			for(FileItem item:list)
			{
				//如果item是普通输入框（即不是文件输入框）
				if(item.isFormField())
				{
					//System.out.println(item.getFieldName()+"  :"+item.getString("utf-8"));
					switch(item.getFieldName())
					{
						case "s_no":
							s_no=item.getString("utf-8");
							break;
						case "name":
							name=item.getString("utf-8");
							break;
						case "type":
							type=item.getString("utf-8");
							break;
						case "intro":
							intro=item.getString("utf-8").trim();
							break;
						case "profession":
							String profession=item.getString("utf-8");
							professionList.add(profession);
							break;
						case "base_subject":
							
							
							String subject=item.getString("utf-8");
							//System.out.println(subject);
							subjectList.add(subject);
							
							break;
					}
				}
				else {
					//如果是文件输入框，则将请求来的文件保存到本地磁盘
					
					//1.获取文件名
					//System.out.println(item.getName());
					
					if(item.getName()!="")
					{						
						String imgPath=Gloable.subjectParentPath;
						//String imgAddress="";
						String fileName=item.getName().substring(item.getName().lastIndexOf("\\")+1);
						System.out.println(fileName);
						InputStream in=item.getInputStream();
						imgAddress=imgPath+s_no;
						File file=new File(imgAddress);
						if(!file.exists())
						{
							file.mkdirs();
						}						
						imgAddress=imgAddress+"//"+fileName;
						file=new File(imgAddress);
						file.createNewFile();
						FileOutputStream out=new FileOutputStream(file);
						byte[] bytes=new byte[1024*10];//缓冲大小为10k
						int i=-1;
						while((i=in.read(bytes))!=-1)
						{
							out.write(bytes, 0, i);
						}
						
						System.out.print("上传完毕");
						
						
						//pw.println("<center>上传成功！！</center>");
						in.close();
						out.close();
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return "redirect:/ok/update_subject_fail.jsp";
		}catch(Exception e )
		{
			e.printStackTrace();
			return "redirect:/ok/update_subject_fail.jsp";
		}
		
		
		//将表单中获取的数据存到数据库
		
		//String imageDB = subjectDao.findSubjectByS_no(s_no).getImage_address();
		
		Subject subject = new Subject();
		subject.setS_no(s_no);
		subject.setName(name);
		subject.setType(type);
		subject.setIntro(intro);
		subject.setImage_address(imgAddress);
		
		//为了便于实现：先把与该学科相关的内容删掉（如  p_s表后学科编号为s_no的，s_s表的后继学科编号为s_no的）
		p_sDao.deleteByS_no(s_no);
		s_sDao.deleteByNextSubject(s_no);
		
		if(imgAddress == null || "".equals(imgAddress)) {
			//如果为空，则代表用户没有更改图片，则访问不修改图片片的数据库语句
			
			subjectDao.updateSubjectNotImage(subject);
		} else {
			subjectDao.updateSubject(subject);
		}
		
		//subjectDao.addSubject(subject);
		S_s s_s = null;
		for(String pre_subject: subjectList)
		{
			s_s = new S_s();
			s_s.setPre_subject(pre_subject);
			s_s.setNext_subject(s_no);
			s_sDao.addS_s(s_s);
		}
		
		P_s p_s = null;
		for(String p_no:professionList) {
			p_s = new P_s();
			p_s.setP_no(p_no);
			p_s.setS_no(s_no);
			p_sDao.addP_s(p_s);
		}
		
		Logger.getLogger(SubjectController.class).info(a_id+"修改学科："+s_no+"成功");
		return "redirect:/ok/update_subject_ok.jsp";
		
		
		
	}	
	@RequestMapping(value="/deleteSubjectByS_no",produces="application/json;charset=utf-8")
	public String  deleteSubjectByS_no(@RequestParam("s_no") String s_no,HttpServletRequest request) {
		System.out.println("deleteSubjectByS_no()");
		
		//能否保证三个操作都能执行成功？TODO
		p_sDao.deleteByS_no(s_no);
		s_sDao.deleteBySubject(s_no);
		subjectDao.deleteSubjectByS_no(s_no);
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"删除了学科："+s_no);
		
		//再重新跳转到学科界面,重定向
		return "redirect:/subject/subjectList";
		
	}
	
	
	
	
}
