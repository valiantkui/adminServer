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
		var b1 = false;
		
			$(function(){//默认页面加载完之后调用
				$("#d_no").blur(checkD_no);
				$("#name").blur(checkName);
			});		
			function checkD_no(){
				var d_no = $("#d_no").val();
				var reg = /^[0-9]+$/;
				if(reg.test(d_no) && d_no.length<=11){
					$("#d_no_message").text("格式正确");
	        		$("#d_no_message").css({"color":"green"});
				}else{
					$("#d_no_message").text("格式不正确");
	        		$("#d_no_message").css({"color":"red"});
	        		b1 = false;
	        		return;
				}
			    $.ajax({
			    	url: "/adminServer/master/checkD_no",
			    	type: "post",
			    	data: "d_no="+d_no,
			    	dataType: "text",
			    	error: function(result){
			    		alert("cuowu"+result);
			    		b1 = false;
			    	},
			    	
			    	success: function(result){
			    		
			    		//alert(result);
			        	if(result == 0){//说明账号不存在
			        		$("#d_no_message").text("编号可以使用");
			        		$("#d_no_message").css({"color":"green"});
			        		b1 = true;
			        	}else{
			        		$("#d_no_message").text("编号已存在");
			        		$("#d_no_message").css({"color":"red"});
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
				}
			}	
		</script>	
	</head>
	<body>
		<%@ include file="nav.jsp" %>
		<div class="container">
			<center><h3>添加就业方向</h3></center>
			
			<form action="/adminServer/job/doAddJobDirection" method="post" enctype="multipart/form-data" onsubmit="return ((b1+b2)==2);">
			  <div class="form-group">
			    <label for="email">方向编号:</label><span id="d_no_message"></span>
			    <input type="text" name="d_no" class="form-control" id="d_no" placeholder="请输入长度不超过11的数字">
			  </div>
			  <div class="form-group">
			    <label for="pwd">方向名称:</label> <span id="name_message"></span>
			    <input type="text" name="name" class="form-control" id="name" placeholder="长度不能超过20">
			  </div>
			  <div class="form-group">
			    <label for="pwd">类型:</label><span id="type_message"></span>
			    <input type="text" name="type" class="form-control" id="type" value="就业" readonly="true">
			  </div>
			  <div class="form-group">
			    <label for="pwd">上传简介:</label>
			    <input type="file" class="form-control-file" name="intro_link" id="intro_link">
			  </div>
			  <div class="form-group">
			    <label for="pwd">上传前景:</label>
			    <input type="file" class="form-control-file" name="vista_link" id="vista_link">
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