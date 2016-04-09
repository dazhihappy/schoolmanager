<%--<%@page import="javax.persistence.Basic"--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
<title></title>

</head>

<body>
<div >
  		<input type="button" class="btn btn-info" name="commentManage" value="评论管理" id="commentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info"name="studentManage" value="学生管理" id="studentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button"class="btn btn-info" name="schoolManage" value="学校管理" id="schoolM"/>
	<hr/>
	学校管理
</div>
</body>
</html>
<script type="text/javascript">
	$(function(){
	
		$("#commentM").bind("click",function(){
			window.location.href="commentManage.jsp";
		});
		
		$("#studentM").bind("click",function(){
			window.location.href="studentManage.jsp";
		});
		
		$("#schoolM").bind("click",function(){
			window.location.href="schoolManage.jsp";
		});
	});
</script>