<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<nav class="navbar navbar-expand-md bg-dark navbar-dark">
		  <a class="navbar-brand" href="/adminServer/logs/findAllLogs">大学生规划</a>
		 <!--  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
		    <span class="navbar-toggler-icon"></span>
		  </button>   -->
		  
		  <div class="collapse navbar-collapse" id="collapsibleNavbar" >
		    <ul class="navbar-nav">
		    	
		      <li class="nav-item">
		        <a class="nav-link" href="/adminServer/subject/subjectList">学科</a>
		      </li>
		     <li class="nav-item dropdown">
			      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
			        考研
			      </a>
			      <div class="dropdown-menu">
			        <a class="dropdown-item" href="/adminServer/master/masterDirectionList">研究方向</a>
			        <a class="dropdown-item" href="/adminServer/resource/resourceList?type=1">导师院校信息</a>
			        <a class="dropdown-item" href="/adminServer/resource/resourceList?type=2">历年院校录取信息</a>
			      </div>
		      </li>
		     <li class="nav-item dropdown">
			      <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
			        就业
			      </a>
			      <div class="dropdown-menu">
			        <a class="dropdown-item" href="/adminServer/job/jobDirectionList">就业方向</a>
			        <a class="dropdown-item" href="/adminServer/resource/resourceList?type=3">历年公司招聘信息</a>
			      </div>
		      </li>
		      
		      <li class="nav-item">
		        <a class="nav-link" href="#">大学</a>
		      </li>
		    
	          <li class="nav-item">
		        <a class="nav-link" href="#">出国</a>
		      </li>    
		    
	            
		    </ul>
		    
		    <li class="nav-item" style="float:right">
		        <a class="nav-link" style="color:white;" >${sessionScope.a_name }</a>    
	      	</li>
		    <li class="nav-item" style="float:right">
		        <a class="nav-link" href="/adminServer/admin/adminLogout">[退出]</a>
	      	</li>
	      <li class="nav-item" style="float: right">
		        <a class="nav-link" href="/adminServer/collegeintroduction/index.html" target="_blank">查看帮助</a>
	      </li>  
		  </div>  
		  
		  
		</nav>
