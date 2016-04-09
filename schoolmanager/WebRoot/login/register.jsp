<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>管理员注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/register.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
  </head>
  
  <body>
  	<div class="registerDiv">
  		<div style="font-size: 25px;" class="title">管理员注册</div>
  		<div class="title"><input type="text" name="userId" placeholder="用户名"></div>
  		<div class="title"><input type="password" name="password" placeholder="密码"></div>
  		<div class="title"><input type="password" name="passwordSure" placeholder="密码"></div>
  		<div class="title"><input type="text" name="name" placeholder="真实姓名"></div>
  		<div class="title"><input type="text" name="email" placeholder="邮箱"></div>
  		<div class="title">
  			<label for="sex">性别</label>
  			<input type="radio" value="0" name="sex" checked="checked">男
  			<input type="radio" value="1" name="sex">女
  		</div>
  		<div class="title"><input type="submit" value="注册" class="btn btn-info"></div>
  	</div>
  </body>
  <script type="text/javascript">
  	$(function(){
  		$("input:submit").bind("click",function(){
  			var userId = $("[name='userId']").val();
  			var password = $("[name='password']").val();
  			var passwordSure =$("[name='passwordSure']").val();
  			var name = $("[name='name']").val();
  			var email = $("[name='email']").val();
  			var sex = $("[name='sex']").val();
  			//alert(userId+" "+password+" "+name+" "+email+" "+sex);
  			if(userId!=""&&password!=""&&name!=""&&email!=""&&sex!=""&&passwordSure!=""){
  				if(passwordSure==password){
	  				$.post("register",
	  					{"userId":userId,"password":password,"name":name,"email":email,"sex":sex},
	  					function(data){
	  						if(data=="true"){
	  							alert("注册成功");
	  							location.href="login.jsp";
	  						}
	  				});
  				}else{
  					alert("输入密码不一致");
  				}
  			}else{
  				alert("请输入完整信息");
  			}
  		});
  	});
  </script>
</html>
