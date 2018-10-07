<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="/adminServer/css/bootstrap.min.css" />
		<script type="text/javascript" src="/adminServer/js/jquery-3.2.1.js" ></script>
		<script type="text/javascript" src="/adminServer/js/popper.min.js" ></script>
		<script type="text/javascript" src="/adminServer/js/bootstrap.js" ></script>
		<title>Insert title here</title>
		<script>
		
		var b1 = false;
		var b2 = false;
		var b3 = false;
		
			$(function(){//默认页面加载完之后调用
				$("#s_no").blur(checkS_no);
				$("#name").blur(checkName);
				$("#intro").blur(checkIntro);
								
			});
			
			
			function checkS_no(){
				var s_no = $("#s_no").val();
				var reg = /^[0-9]+$/;
				
				if(reg.test(s_no) && s_no.length<=11){
					$("#s_no_message").text("编号格式正确使用");
	        		$("#s_no_message").css({"color":"green"});
				}else{
					$("#s_no_message").text("格式不正确");
	        		$("#s_no_message").css({"color":"red"});
	        		b1 = false;	
	        		return;
				}		
			    $.ajax({
			    	url: "/adminServer/subject/checkS_no",
			    	type: "post",
			    	data: "s_no="+s_no,
			    	dataType: "text",
			    	error: function(result){
			    		alert("cuowu"+result);
			    	},  	
			    	success: function(result){		
			        	if(result == 0){//说明账号不存在
			        		$("#s_no_message").text("编号可以使用");
			        		$("#s_no_message").css({"color":"green"});
			        		b1 = true;
			        	}else{
			        		$("#s_no_message").text("编号已存在");
			        		$("#s_no_message").css({"color":"red"});
			        		b1 = false;
			        	}
			    }});
			}
			
			
			function checkName(){
				var name = $("#name").val();
			
				if(name.length<=20 && name.length>0){
					$("#name_message").text("格式正确");
					$("#name_message").css({"color":"green"});
					b2 = true;
				}else{
					$("#name_message").text("格式错误");
					$("#name_message").css({"color":"red"});
					b2 = false;
				}
			}
			function checkIntro(){
				var intro = $("#intro").val();
				
				if(intro.length<=500 && intro.length>0){
					$("#intro_message").text("格式正确");
					$("#intro_message").css({"color":"green"});
					b3 = true;
				}else{
					$("#intro_message").text("格式错误");
					$("#intro_message").css({"color":"red"});	
					b3 = false;
				}
			}
			
		
		
		
		
		</script>
		
		
	</head>
	<body>
		<%@ include file="nav.jsp" %>
		<div class="container">
			<center><h3>添加学科</h3></center>
			
			<form action="/adminServer/subject/doAddSubject" method="post" enctype="multipart/form-data" onsubmit="return ((b1+b2+b3) == 3);">
			  <div class="form-group">
			    <label for="email">学科编号:</label><span id="s_no_message"></span>
			    <input type="text" name="s_no" class="form-control" id="s_no" placeholder="请输入长度不超过11的数字">
			  </div>
			  <div class="form-group">
			    <label for="pwd">学科名:</label> <span id="name_message"></span>
			    <input type="text" name="name" class="form-control" id="name" placeholder="长度不能超过20">
			  </div>
			  <div class="form-group">
			    <label for="pwd">类型:</label><span id="s_no_message"></span>
			    <input type="text" name="type" class="form-control" id="type" value="学科" readonly="true">
			  </div>
			  <div class="form-group">
			    <label for="pwd">简介:</label><span id="intro_message"></span>
			   <textarea class="form-control" rows="5" id="intro" name="intro" placeholder="字数不超过100"></textarea>
			  </div>

			  <div class="form-group">
			    <label for="pwd">上传图片:</label>
			    <input type="file" class="form-control-file" name="file" id="file">
			  </div>
			  
		      <div class="form-group">
			    <label for="pwd">所属专业:</label><br>
			    
			    <c:forEach items="${requestScope.professionList}" var="profession">
			    
				  	<div class="form-check form-check-inline">
					  <label class="form-check-label">
					    <input type="checkbox" class="form-check-input" name="profession" value="${profession.p_no}">
					    ${profession.name}
					  </label>
				    </div>
			    </c:forEach>
			  	
			  
			  </div>
		      <div class="form-group">
			    <label for="pwd">所需基础学科:</label><br>
			    
			    <c:forEach items="${requestScope.subjectList}" var="subject">
			    
				  	<div class="form-check form-check-inline">
					  <label class="form-check-label">
					    <input type="checkbox" class="form-check-input" name="base_subject" value="${subject.s_no}">
					    ${subject.name}
					  </label>
				    </div>
			    </c:forEach>
			  	
			  
			  </div>
			  
			  <button type="submit" class="btn btn-primary">Submit</button>
			</form>
		
		</div>
	
	</body>
</html>