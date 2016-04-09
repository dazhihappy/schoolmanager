<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>管理员登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/login.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
  </head>
  
  <body>
    <div class="loginDiv">   	
		<div class="title" style="font-size: 25px;">管理员登录</div>
		<div class="title"><input type="text" placeholder="用户名"></div><br>
    	<div class="title"><input type="password" placeholder="密码"></div><br>
    	<div class="title">
	    	<button class="btn btn-small btn-info" type="button" id="login">登录</button>	    	
	    	<button class="btn btn-small btn-info" type="button" id="register">注册</button>
    	</div>
    </div>
  </body>
  <script type="text/javascript">
	  	$(function(){
	  		//登录
	  		$("#login").bind("click",function(){
	  			var username = $("input:text").val();
	  			var password = $("input:password").val();
	  			//alert(username+password);
	  			if(username==""||password==""){
	  				alert("请输入完整信息");
	  			}else{
	  				$.post("userLogin",{"username":username,"password":password},function(data){
	  					if(data=="true"){
	  						location.href="main.jsp";
	  					}else{
	  						alert("用户名或密码错误或者您没有权限");
	  					}
	  				});
	  			}
	  		});
	  		
	  		//注册
	  		$("#register").bind("click",function(){
	  			location.href="register.jsp";
	  		});
	  	});
  	</script>
</html>
