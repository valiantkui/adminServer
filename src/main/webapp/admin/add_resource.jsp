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
		
		
			$(function(){//默认页面加载完之后调用
				$("#title").blur(checkTitle);
				$("#intro").blur(checkIntro);
								
			});
			
			
		
	
			
			
			function checkTitle(){
				var title = $("#title").val();
				if(title.length<=20 && title.length>0){
					$("#title_message").text("格式正确");
					$("#title_message").css({"color":"green"});
					return true;
				}else{
					$("#title_message").text("格式错误");
					$("#title_message").css({"color":"red"});
					return false;
				}
			}
			function checkIntro(){
				var intro = $("#intro").val();
				if(intro.length<=100 && intro.length>0){
					$("#intro_message").text("格式正确");
					$("#intro_message").css({"color":"green"});
					return true;
				}else{
					$("#intro_message").text("格式错误");
					$("#intro_message").css({"color":"red"});	
					return false;
				}
			}
			
		
		
		
		</script>
		
		
	</head>
	<body>
		<%@ include file="nav.jsp" %>
		<div class="container">
			<center>
				<h2>
				 	<c:if test="${type == '1' }">
				 		<c:out value="导师院校信息"></c:out>
				 	</c:if>
				 	<c:if test="${type == '2' }">
				 		<c:out value="历年院校录取信息"></c:out>
				 	</c:if>
				 	<c:if test="${type == '3' }">
				 		<c:out value="历年公司招聘信息"></c:out>
				 	</c:if>
				 
				 </h2>
			 </center>
			
			<form action="/adminServer/resource/doAddResource" method="post" enctype="multipart/form-data" onsubmit="return (checkTitle()+checkIntro()==2);">
			 
			  <div class="form-group">
			    <label for="pwd">标题:</label> <span id="title_message"></span>
			    <input type="text" name="title" class="form-control" id="title" placeholder="长度不能超过20">
			  </div>
			  <div class="form-group">
			    <label for="pwd">简介:</label><span id="intro_message"></span>
			   <textarea class="form-control" rows="5" id="intro" name="intro" placeholder="字数不超过100"></textarea>
			  </div>
			  <div class="form-group">
			    <label for="pwd">类型:</label>
			    <input type="text" name="type" class="form-control" id="type" value="${type}" readonly="true">
			  </div>

			  <div class="form-group">
			    <label for="pwd">上传资源文件:</label>
			    <input type="file" class="form-control-file" name="file" id="file">
			  </div>
			  <button type="submit" class="btn btn-primary">Submit</button>
			</form>
		
		</div>
	
	</body>
</html>