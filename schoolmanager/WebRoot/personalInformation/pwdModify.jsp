<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<!DOCTYPE HTML>
<html >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
	<title>修改密码</title>
</head>
<%
	String managerId=(String)session.getAttribute("userId");//登陆的管理员id
	SystemManager manager=new SystemManager();
	//manager.set_id(new ObjectId("5653f75d9a72311ce86a2cbd"));
	manager.set_id(new ObjectId(managerId));
	BasicDBObject projection=new BasicDBObject();
	projection.put(StaticString.SystemManager_Pwd, 1);
	BasicDBObject query=CreateQueryFromBean.EqualObj(manager);
	MongoCursor<Document> mc=DaoImpl.GetSelectCursor(SystemManager.class, query, projection);
	Document document=null;
	if(mc.hasNext()){
		document=mc.next();
	}
	String pwd=(String)document.get(StaticString.SystemManager_Pwd);
	System.out.print(pwd);
 %>
<body>
<div align="center" >
	<h2> 修改密码</h2><br/>
	
</div>

<div align="center">

	<table  >
		<tr><td >原&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;<input type="password" name="oldpwd" id="opwd" /></td></tr>
		<tr><td>&nbsp;</td>
		
		<tr><td>新&nbsp;&nbsp;&nbsp;密&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;<input type="password" name="newpwd1" id="npwd1"/></td></tr>
		<tr><td>&nbsp;</td>		
		<tr><td>重复新密码&nbsp;<input type="password" name="newpwd2" id="npwd2"/></td></tr>
		<tr><td>&nbsp;</td>
		<tr><td><input  type="button" class="btn btn-info" value="保存修改" id="checkPwd" /></td></tr>
		<tr><td>&nbsp;</td>
	</table>
	
</div>
	<div style="display: none" id="pwd"><%=pwd %></div>
</body>
<script type="text/javascript">
	$(function(){
		$("#checkPwd").bind("click",function(){
			var flag=1;
			var oldpwd=document.getElementById("opwd").value;
			var pwd=$("#pwd").text();
			//alert(oldpwd+pwd);
			
			if(oldpwd==""){
				alert("请输入原密码");
				flag=0;
			}
				
			else if(!(oldpwd==pwd)){
				alert("原密码不正确请重新输入！");
				flag=0;
			}
			var newPwd1=document.getElementById("npwd1").value;
			var newPwd2=document.getElementById("npwd2").value;
			if(newPwd1==""){
				alert("请输入新密码");
				flag=0;
			}
			if(newPwd2==""){
				alert("重新输入新密码");
				flag=0;
			}
			if(newPwd1!=newPwd2){
				alert("两次输入的密码不一致，请重新输入！");
				flag=0;
			}
			if(flag==1){
			//alert("aaaaaaaa"+newPwd1);
				$.get("modifyPwd",{"newpwd":newPwd1},function(data){
					alert(data);
				});
			}
			
		});
	});
</script>
</html>