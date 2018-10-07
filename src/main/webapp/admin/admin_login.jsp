<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel="stylesheet" href="/adminServer/css/bootstrap.min.css" />
		<script type="text/javascript" src="/adminServer/js/jquery-3.2.1.js" ></script>
		<script type="text/javascript" src="/adminServer/js/popper.min.js" ></script>
		<script type="text/javascript" src="/adminServer/js/bootstrap.js" ></script>
		

		<style>
			div{
				/*  border: solid 1px;*/
			}
			.container{
				text-align: center;
				width: 100%;
				position: fixed;
				top: 200px;
				/* opacity:0.9;
  				filter:alpha(opacity=40); */
				
			}
			.login{
				position: fixed;
				left: 50%;
				right: 50%;
				margin-left: -220px;
				margin-right: -220px;
				height: 440px;
				width: 440px;
				
			}
			body{
				background-color:#2E8B57;
				
			}
			img{
				height: 100%;
				width: 100%;
				border: 0pxs;
			}
			.blur 
			{
				
				   opacity:6;
    				filter:alpha(opacity=100);
    				/*filter: opacity(70%);*/
    				/*-webkit-filter: opacity(160%);*/
				}
			
			
		</style>
		<script type="text/javascript">
			window.onkeydown=function(e){
				
				//alert(e.keyCode);
				//如果按下的是回车，则相当与按下登陆按钮
				if(e.keyCode==13){
					$("#login").click();
				}
			}
			
			//当页面加载完时执行
			$(function(){
				$("#login").bind("click",check_ajax);
			})
			
			//检测输入的id是否合理
			function check_id()
			{
				//商家的id不超过十个字符
				var id_input=$("#id").val();
				var reg =/^[0-9a-zA-Z]+$/;
				if(reg.test(id_input) && id_input.length<=11){
					
						return true;
				}else{
				
					$("#message").html("账号格式错误");
					$("#message").css("color","red");
					return false;
				}
			}
			
			//检测商家的姓名,只检测长度,不得超过10个字符
			function check_password(){
				var password=$("#password").val();
				if(password.length<=11 && password.length>0)
					{
						
						return true;
					}else{
						
						$("#message").html("密码格式错误");
						$("#message").css("color","red");
						return false;	
					}
			}
			//检测验证码是否输入
			
			function check_imgCode(){
				var imgCode=$("#imgCode").val();
				if(imgCode.length==0)
					{
						$("#message").html("请输入验证码");
						$("#message").css("color","red");
						return false;
					}
					return true;
			}
			
			function check_ajax()
			{
				var xmlhttp;
				if(window.XMLHttpRequest)
				{
					xmlhttp= new XMLHttpRequest();
				}else{
					xmlhttp= ActiveXObject("Mircosoft.XMLHTTP");
				}
				
				var message;
				xmlhttp.open("post","/adminServer/admin/checkAdminLogin",true);
				xmlhttp.onreadystatechange=function(){
					if(xmlhttp.readyState==4 && xmlhttp.status==200)
						{
							message=xmlhttp.responseText;
							if(message==1){
								document.getElementById("myform").submit();
								return;
							}
							$("#message").html(message);
							$("#message").css("color","red");
					}
				}
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				var id=$("#id").val();
				var password=$("#password").val();
				var code=$("#imgCode").val();
				xmlhttp.send("id="+id+"&password="+password+"&imgCode="+code);
			}
			
			
		
		</script>
	</head>
	<body>
		
	<!-- 	<img src="../img/login.jpg" class="blur" /> -->
		<div class="container">
			
		 <div class="login">
		 	
		 	
			<div class="card">
			    <div class="card-header">
			        <h3 class="panel-title">
			           管理员
			        </h3>
			    </div>
			    <div class="card-body">
			    	<!--form表单-->
			    	<form class="form-horizontal" id="myform" action="/adminServer/admin/adminLogin" role="form" >
						<div class="form-group row">
							<label for="firstname" class="col-sm-3 control-label">账号</label>
							<div class="col-sm-8">
							<input type="text" class="form-control" id="id" name="id"
									   placeholder="请输入账号">
							</div>
							<div class="col-sm-1">
								<!-- <span class="glyphicon glyphicon-remove " style="color: red"></span> -->
								
							</div>
						</div>
						<div class="form-group row">
							<label for="lastname" class="col-sm-3 control-label">密码</label>
							<div class="col-sm-8">
								<input type="password" class="form-control" id="password" name="password"
									   placeholder="请输入密码">
							</div>
							<div class="col-sm-1">
								<span></span>
							</div>
						</div>
						<div class="form-group row">
						
							<div class="col-sm-3" id="message" style="padding:0px;"> </div>
							<div class="col-sm-8">
									<button type="button" class="btn btn-primary btn-block" id="login">登录</button>
							</div>
						</div>
					</form>
			    </div>
			</div>
		 </div>
		</div>
	</body>
</html>
