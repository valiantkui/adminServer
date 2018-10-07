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
	 * ���һ����Դ������Դ������  ����ԺУ��Ϣ������ԺУ¼ȡ��Ϣ�����깫˾��Ƹ��Ϣ����
	 * 
	 * @param request
	 * @return ���ص������Դ����ͼ
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
		Logger.getLogger(SubjectController.class).info(a_id+"���������Դ");
		System.out.println("doAddMasterDirection()");
		String title = null;
		String type = null;
		String intro = "";
		 String filePath =  "";
		
		//1.����һ���ļ���������
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		//2.����һ���ļ�������
		ServletFileUpload upload=new ServletFileUpload(dfif);
		upload.setFileSizeMax(1024*1024*5);//�����ϴ��ĵ����ļ����Ϊ5Mb
		upload.setSizeMax(1024*1024*10);//�����ϴ����ܹ��ļ���С���Ϊ10mb
		
		//���������ϴ�����
		upload.setHeaderEncoding("UTF-8"); 
		try {
			//��request�����������ݻ��ļ����з���
			//parseRequest��������һ��FileItem���͵ļ���
			List<FileItem> list=upload.parseRequest(request);
			for(FileItem item:list)
			{
				//���item����ͨ����򣨼������ļ������
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
		//�����л�ȡ�����ݴ浽���ݿ�
		
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
		Logger.getLogger(SubjectController.class).info(a_id+"�������Դ:"+title);
		
		return "redirect:/ok/add_masterDirection_ok.jsp";
	}
	
	@RequestMapping("updateResource")
	public String updateResource(HttpServletRequest request) {
		int r_no = Integer.parseInt(request.getParameter("r_no"));
		//����r_no�������ݿ��ֲ�������Ϣ
		entity.Resource resource = resourceDao.findResourceByR_no(r_no);
		request.setAttribute("resource", resource);
		
		return "update_resource";
	}
	
	@RequestMapping("doUpdateResource")
	public String doUpdateResource(HttpServletRequest request) {
		System.out.println("doAddMasterDirection()");
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"���Ը�����Դ");
		String r_no = null;
		String title = null;
		String type = null;
		String intro = "";
		 String filePath =  "";
		
		//1.����һ���ļ���������
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		//2.����һ���ļ�������
		ServletFileUpload upload=new ServletFileUpload(dfif);
		upload.setFileSizeMax(1024*1024*5);//�����ϴ��ĵ����ļ����Ϊ5Mb
		upload.setSizeMax(1024*1024*10);//�����ϴ����ܹ��ļ���С���Ϊ10mb
		
		//���������ϴ�����
		upload.setHeaderEncoding("UTF-8"); 
		try {
			//��request�����������ݻ��ļ����з���
			//parseRequest��������һ��FileItem���͵ļ���
			List<FileItem> list=upload.parseRequest(request);
			for(FileItem item:list)
			{
				//���item����ͨ����򣨼������ļ������
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
		//�����л�ȡ�����ݴ浽���ݿ�
		
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
		Logger.getLogger(SubjectController.class).info(a_id+"��������Դ:"+title);
		return "redirect:/ok/ok.jsp";
	}
	
	@RequestMapping("deleteResource")
	public String deleteResource(HttpServletRequest request) {
		String r_no = request.getParameter("r_no");
		String type = request.getParameter("type");
		
		resourceDao.deleteResourceByR_no(Integer.parseInt(r_no));
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"ɾ����ѧ��:"+r_no);
		return "redirect:/resource/resourceList?type="+type;
		
	}
}
