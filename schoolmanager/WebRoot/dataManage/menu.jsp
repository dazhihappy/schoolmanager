<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>数据管理菜单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body >
  	<div align="center" >
  		<input type="button" class="btn btn-info" style="width:100px" name="commentManage" value="评论管理" id="commentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info"style="width:100px" name="studentManage" value="学生管理" id="studentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button"class="btn btn-info" style="width:100px" name="schoolManage" value="学校管理" id="schoolM"/>
  	</div>
  </body>


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
</html>