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

		function deleteResource(r_no,type){
			var isDelete = prompt("删除操作会将该资源所有相关的信息全部删掉。确定要删除吗？一旦删除不可撤销.如果要执意删除请输入yes");
			if(isDelete == "yes"){
				//确认要删除，则请求删除
				window.location = "/adminServer/resource/deleteResource?r_no="+r_no+"&type="+type;
			}
		}
		</script>
	</head>
	<body>
		<%@ include file="nav.jsp" %>
		
		<div class="container-fluid">
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
		<div class="row">
			<div class="col-lg-12 col-sm-12 col-md-12">
				<a style=" float: right;" href="/adminServer/resource/addResource?type=${type}"  class="btn btn-light">添加资源</a>
				<table class="table">
				    <thead>
				      <tr>
				        <th>编号</th>
				        <th>标题</th>
				        <th>简介</th>
				        <th>类型</th>
				        <th>内容</th>
				        <th>操作</th>
				      </tr>
				    </thead>
				    <tbody>
				    <c:forEach items="${requestScope.resourceList }" var="resource">
				      <tr>
				        <td>${resource.r_no }</td>
				        <td>${resource.title }</td>
				        <td>${resource.intro }</td>
				        <td>${resource.type }</td>
				        <td><a href="${resource.path}">${resource.path}</a></td>
				        <td> 
				        	<span><a href="/adminServer/resource/updateResource?r_no=${resource.r_no}">修改</a></span>
				         	<span><a href="#" onclick="deleteResource(${resource.r_no},${type })">删除</a></span>
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