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
		
		<script type="text/javascript">
	
		
			function deleteSubject(s_no){
				var isDelete = prompt("删除操作会将该学科所有相关的信息全部删掉。确定要删除吗？一旦删除不可撤销.如果要执意删除请输入yes");
				if(isDelete == "yes"){
					//确认要删除，则请求删除
					window.location = "/adminServer/subject/deleteSubjectByS_no?s_no="+s_no;
				}
			}
		
		</script>
	</head>
	<body>
		<%@ include file="nav.jsp" %>
		
		<div class="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-sm-12 col-md-12">
				<h3>专业</h3>
				
					<c:forEach items="${requestScope.professionList }" var="profession">
					    <a href="/adminServer/subject/subjectList?p_no=${profession.p_no}&p_name=${profession.name}" class="btn btn-light">${profession.name }</a>
				    </c:forEach>
				
			
			</div>	
			
			<br/><br/><br/><br/><br/><br/>
			<div class="col-lg-12 col-sm-12 col-md-12">
			
			
				<dt style="float: left;">当前专业：${requestScope.professionInfo}</dt>
				<a style=" float: right;" href="/adminServer/subject/addSubject"  class="btn btn-light">添加学科</a>
			
				<table class="table">
				    <thead>
				      <tr>
				        <th>学科编号</th>
				        <th>学科名</th>
				        <th>类型</th>
				        
				        <th>图片地址</th>
				        <th>操作</th>
				      </tr>
				    </thead>
				    <tbody>
				    
				    <c:forEach items="${requestScope.subjectList }" var="subject">
				      <tr>
				        <td>${subject.s_no }</td>
				        <td>${subject.name }</td>
				        <td>${subject.type }</td>
				       
				        <td>${subject.image_address }</td>
				        <td> 
				        	<span><a href="/adminServer/subject/updateSubject?s_no=${subject.s_no}">修改</a></span>
				         	<span><a href="#" onclick="deleteSubject(${subject.s_no})">删除</a></span>
			         	</td>
				      </tr>
				     </c:forEach>
				    </tbody>
				</table>
			</div>	
		</div>
		
		</div>	
		
	
	</body>
</html>