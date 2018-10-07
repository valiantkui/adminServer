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

@Controller   //���Ʋ�
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
		
		String p_no = request.getParameter("p_no");//��ȡp_no
		String p_name = request.getParameter("p_name");//��ȡp_no
		
		System.out.println("��ȡ��רҵ��ţ�"+p_no);
		
		List<Subject> subjectList = null;
		
		if(p_no != null) {//����ĳרҵ������ѧ��
			subjectList = subjectDao.findSubjectByP_no(p_no);
			request.setAttribute("professionInfo", p_name);
		}else {
			
			request.setAttribute("professionInfo", "ȫ��");
			subjectList =  subjectDao.findAllSubject();
		}
		List<Profession> professionList = professionDao.findAll();
		
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("professionList",professionList );
		
		return "subject";	//ת����subject.jsp
	}
	
	@RequestMapping(value="/checkS_no",produces="application/json;charset=utf-8")
	@ResponseBody
	public int checkS_no(@RequestParam("s_no") String s_no) {
		System.out.println("checkS_no()----"+s_no);
		Subject subject = subjectDao.findSubjectByS_no(s_no);
		if(subject == null) {//˵����ǰ��Ų�����
			return 0;
		}
		
		return 1;
	}
	
	
	@RequestMapping(value="/addSubject",produces="application/json;charset=utf-8")
	public String addSubject(HttpServletRequest request) {
		//��ȡ���е�ѧ��
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
		Logger.getLogger(SubjectController.class).info(a_id+"�������ѧ��");
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
			
			//�������Ի�ȡ��FileItem���͵ļ��Ͻ��з���,�ж�ÿ��FileItem�Ƿ����ļ������ݲ�ͬ��������ͬ�Ĵ���
			for(FileItem item:list)
			{
				//���item����ͨ����򣨼������ļ������
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
					//������ļ�����������������ļ����浽���ش���
					
					//1.��ȡ�ļ���
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
						byte[] bytes=new byte[1024*10];//�����СΪ10k
						int i=-1;
						while((i=in.read(bytes))!=-1)
						{
							out.write(bytes, 0, i);
						}
						
						System.out.print("�ϴ����");
						
						
						//pw.println("<center>�ϴ��ɹ�����</center>");
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
		
		
		//�����л�ȡ�����ݴ浽���ݿ�
		
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
		
		Logger.getLogger(SubjectController.class).info(a_id+"���ѧ��:"+s_no+"�ɹ�");
		return "redirect:/ok/add_subject_ok.jsp";
	}
	
	/**
	 * ת�����޸�ҳ��
	 */
	@RequestMapping(value="/updateSubject",produces="application/json;charset=utf-8")
	public String updateSubject(HttpServletRequest request) {
		//1.��ȡѧ�Ʊ��
		String s_no = request.getParameter("s_no");
		//2.��ѯ���йظ�ѧ����Ϣ
		
		Subject subject =	subjectDao.findSubjectByS_no(s_no);
		//3.��ѯ�����ѧ����ص�����רҵ
		
		List<Profession> professionList = professionDao.findAll();
		List<Profession> pfList = professionDao.findProfessionByS_no(s_no);
		
		System.out.println(pfList);
		
		//4.��ѯ�����ѧ����ص�ѧ��(ǰ��ѧ�ƣ�Ҳ����˵  ��ѧ�Ƶ��������ѧ��)
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
		Logger.getLogger(SubjectController.class).info(a_id+"�����޸�ѧ��");
		
		String s_no = null;
		String name = null;
		String type = null;
		String intro = null;
	
		
		List<String> subjectList = new ArrayList<>();
		List<String> professionList = new ArrayList<>();
		
		String imgAddress =  "";
		
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
			
			//�������Ի�ȡ��FileItem���͵ļ��Ͻ��з���,�ж�ÿ��FileItem�Ƿ����ļ������ݲ�ͬ��������ͬ�Ĵ���
			for(FileItem item:list)
			{
				//���item����ͨ����򣨼������ļ������
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
					//������ļ�����������������ļ����浽���ش���
					
					//1.��ȡ�ļ���
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
						byte[] bytes=new byte[1024*10];//�����СΪ10k
						int i=-1;
						while((i=in.read(bytes))!=-1)
						{
							out.write(bytes, 0, i);
						}
						
						System.out.print("�ϴ����");
						
						
						//pw.println("<center>�ϴ��ɹ�����</center>");
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
		
		
		//�����л�ȡ�����ݴ浽���ݿ�
		
		//String imageDB = subjectDao.findSubjectByS_no(s_no).getImage_address();
		
		Subject subject = new Subject();
		subject.setS_no(s_no);
		subject.setName(name);
		subject.setType(type);
		subject.setIntro(intro);
		subject.setImage_address(imgAddress);
		
		//Ϊ�˱���ʵ�֣��Ȱ����ѧ����ص�����ɾ������  p_s���ѧ�Ʊ��Ϊs_no�ģ�s_s��ĺ��ѧ�Ʊ��Ϊs_no�ģ�
		p_sDao.deleteByS_no(s_no);
		s_sDao.deleteByNextSubject(s_no);
		
		if(imgAddress == null || "".equals(imgAddress)) {
			//���Ϊ�գ�������û�û�и���ͼƬ������ʲ��޸�ͼƬƬ�����ݿ����
			
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
		
		Logger.getLogger(SubjectController.class).info(a_id+"�޸�ѧ�ƣ�"+s_no+"�ɹ�");
		return "redirect:/ok/update_subject_ok.jsp";
		
		
		
	}	
	@RequestMapping(value="/deleteSubjectByS_no",produces="application/json;charset=utf-8")
	public String  deleteSubjectByS_no(@RequestParam("s_no") String s_no,HttpServletRequest request) {
		System.out.println("deleteSubjectByS_no()");
		
		//�ܷ�֤������������ִ�гɹ���TODO
		p_sDao.deleteByS_no(s_no);
		s_sDao.deleteBySubject(s_no);
		subjectDao.deleteSubjectByS_no(s_no);
		
		HttpSession session = request.getSession();
		String a_id = (String)session.getAttribute("a_id");
		Logger.getLogger(SubjectController.class).info(a_id+"ɾ����ѧ�ƣ�"+s_no);
		
		//��������ת��ѧ�ƽ���,�ض���
		return "redirect:/subject/subjectList";
		
	}
	
	
	
	
}
