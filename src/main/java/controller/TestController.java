package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@RequestMapping("/test1")
	// //ָ����ǰ�������ص���json�ַ���
	public String test1() {
		
		
		
		return "redirect:/admin/admin_login.jsp";
	}
	
	@RequestMapping("/test2")
	@ResponseBody
	public String test2() {
		return "zhengzhoudauxe";
	}
	
}
