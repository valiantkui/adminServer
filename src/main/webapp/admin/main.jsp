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
		
			function openLog(t){
				var logName = t.innerHTML;
				/*  $.ajax({
				    	url: "/adminServer/logs/loadLogByName",
				    	type: "post",
				    	data: "logName="+logName,
				    	dataType: "text",
				    	error: function(result){
				    		alert("cuowu"+result);
				    	},
				    	
				    	success: function(result){
				    		
				    		//alert(result);
				        	if(result == "ok"){//加载成功
				        		$("#iframe")[0].src="/adminServer/cache/index.html";
								$("#iframe").toggle();
				        	}else{
				        		alert("日志加载异常");
				        	}
				    }}); */
				
				    $("#iframe")[0].src="/adminServer/logs/loadLogByName?logName="+logName;
					$("#iframe").toggle();
				
				
			}
		
		</script>
		
		<style type="text/css">
		
			#left{
				width:20%;
				float: left;
			
			}
		
			#iframe{
				
				
				width: 70%;
				height: 600px;
				float: right;
				display: none;
				z_index: 99;
				background-color: gray;
			}
		
		</style>
	</head>
	<body>
		<%@ include file="nav.jsp" %>
		
		
		
		
		
		<div>
		
			<div class="list-group" id="left">
			<c:forEach items="${requestScope.logList}" var="log"> 
			
			    <a onclick="openLog(this)" class="list-group-item list-group-item-action">${log}</a>
			</c:forEach>
		  
		  	</div>
			<iframe id="iframe"  frameborder="1px"></iframe>
		
		</div>
	
	</body>
</html>