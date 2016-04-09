<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
	<title>个人信息</title>
</head>
<%	
	String managerId=(String)session.getAttribute("userId");//登陆的管理员id
	SystemManager manager=new SystemManager();
	manager.set_id(new ObjectId(managerId));
	//manager.set_id(new ObjectId(managerId));
	BasicDBObject projection=new BasicDBObject();
	projection.put(StaticString.SystemManager_Name, 1);
	projection.put(StaticString.SystemManager_Phone, 1);
	projection.put(StaticString.SystemManager_Sex, 1);
	projection.put(StaticString.SystemManager_Email, 1);
	BasicDBObject query=CreateQueryFromBean.EqualObj(manager);
	MongoCursor<Document> mc=DaoImpl.GetSelectCursor(SystemManager.class, query, projection);
	Document document=null;
	if(mc.hasNext()){
		document=mc.next();
	}
	String name=(String)document.get(StaticString.SystemManager_Name);
	String phone=(String)document.get(StaticString.SystemManager_Phone);
	int sex=(Integer)document.get(StaticString.SystemManager_Sex);
	String mail=(String)document.get(StaticString.SystemManager_Email);
	
 %>
<body>
<div align="center" >
	<h2> 个人信息</h2><br/>
	
</div>
<div align="right" >	
	<input id="modifyPwd" align="right" class="btn btn-info" type="button" name="modifyPassword" value="修改密码" >
</div>
<div align="center">
	<form action="modifyPersonalInformation" method="post"  id="chechdetail">
		姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;<input type="text" name="name" value=<%=name %>><br/><br/>
		<%
			if(sex==0){
		 %>
		性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="sex" value="男" checked="checked" >男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="sex" value="女"  >女&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>
		<%} else{%>
		性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="sex" value="男"  >男&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input  type="radio" name="sex" value="女" checked="checked" >女&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>
		<%} %>
		手机号码&nbsp;&nbsp;<input type="text" name="phone" value=<%=phone %>><br/><br/>
		电子邮件&nbsp;&nbsp;<input type="text" name="mail" value=<%=mail %>><br/><br/>
		<input class="btn btn-info" type="submit" value="保存修改">
	</form>
</div>
</body>
<script type="text/javascript">
	$(function(){
		$("#modifyPwd").bind("click",function(){
			window.location.href="pwdModify.jsp";
		});
	});	
</script>
</html>
