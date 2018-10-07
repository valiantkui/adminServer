<%@page import="entity.Profession"%>
<%@page import="java.util.List"%>
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
			$("#name").blur(checkName);
		});
		function checkName(){
			var name = $("#name").val();
			if(name.length<=20 && name.length>0){
				$("#name_message").text("格式正确");
				$("#name_message").css({"color":"green"});
				return true;
			}else{
				$("#name_message").text("格式错误");
				$("#name_message").css({"color":"red"});
				return false;
			}
		}
		</script>
	</head>
	<body>
		<%@ include file="nav.jsp" %>
		<div class="container">
			<center><h3>修改考研方向</h3></center>
			
			<form action="/adminServer/master/doUpdateMasterDirection" method="post" enctype="multipart/form-data" onsubmit="return ((0+checkName())==1);">
			  <div class="form-group">
			    <label for="email">方向编号:</label><span id="d_no_message"></span>
			    <input type="text" value="${direction.d_no}" name="d_no" readonly="true" class="form-control" id="d_no" placeholder="请输入长度不超过11的数字">
			  </div>
			  <div class="form-group">
			    <label for="pwd">方向名称:</label> <span id="name_message"></span>
			    <input type="text" value="${direction.name }" name="name" class="form-control" id="name" placeholder="长度不能超过20">
			  </div>
			  <div class="form-group">
			    <label for="pwd">类型:</label><span id="s_no_message"></span>
			    <input type="text" value="${direction.type }" name="type" class="form-control" id="type" value="学科" readonly="true">
			  </div>
		

			  <div class="form-group">
			    <label>上传简介:</label><span class="small" style="color: green;">如果不上传则不修改其内容</span>
			    <input type="file" class="form-control-file" name="intro_link">
			  </div>
			  <div class="form-group">
			    <label>上传前景介绍:</label><span class="small" style="color: green;">如果不上传则不修改其内容</span>
			    <input type="file" class="form-control-file" name="vista_link">
			  </div>
	
			  
		      <div class="form-group">
			    <label>所属专业:</label><br>
			    
			    <c:forEach items="${requestScope.professionList}" var="profession">
			    
				  	<div class="form-check form-check-inline">
					<c:set var="isChecked" value="false" />
					
					<c:forEach items="${requestScope.pfList}" var="pf">
						<c:if test="${pf.p_no == profession.p_no }">
							<c:set var="isChecked" value="true" />
						 </c:if> 
						
					</c:forEach>
				
					<c:if test="${isChecked eq true }">
				
						  <label class="form-check-label">
					   	 	<input type="checkbox" checked="true" class="form-check-input" name="profession" value="${profession.p_no}">
					    	${profession.name}
					  	  </label>
					</c:if>
				
								
					<c:if test="${! (isChecked eq true) }">  
						  <label class="form-check-label">
					   	 	<input type="checkbox"  class="form-check-input" name="profession" value="${profession.p_no}">
					    	${profession.name}
					  	  </label>
					</c:if>
				    </div>
			    </c:forEach>
			  	
			  
			  </div>
		      <div class="form-group">
			    <label for="pwd">所需基础学科:</label><br>
			    
			    <c:forEach items="${requestScope.subjectList}" var="subject">
			    
			    <c:set var="isChecked" value="false" />
			    
			    <c:forEach items="${requestScope.sjList }" var="sj">
			    	<c:if test="${sj.s_no == subject.s_no}">
					    <c:set var="isChecked" value="true" />
			    	</c:if>
			    </c:forEach>
			    
			    
				  <c:if test="${isChecked == true }">
				  	<div class="form-check form-check-inline">
					  <label class="form-check-label">
					  
					    <input type="checkbox" class="form-check-input" checked="true" name="base_subject" value="${subject.s_no}">
					    
					    ${subject.name}
					  </label>
				    </div>
				  </c:if>
				  <c:if test="${isChecked != true }">
				  	<div class="form-check form-check-inline">
					  <label class="form-check-label">
					    <input type="checkbox" class="form-check-input" name="base_subject" value="${subject.s_no}">
					    ${subject.name}
					  </label>
				    </div>
				  </c:if>
			    </c:forEach>
			  	
			  
			  </div>
			  
			  <button type="submit" class="btn btn-primary">Submit</button>
			</form>
		
		</div>
	
	</body>
</html>