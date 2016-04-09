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
<body>
<div align="center" >
	<h2> 群发通知</h2><br/>
	<form action="batchNoticeAction" method="post">
		通知标题&nbsp;&nbsp;<input type="text" name="title" style="width:250px;"/><br/><br/>
		发&nbsp;&nbsp;布&nbsp;&nbsp;方&nbsp;&nbsp;<input type="text" name="publisher" style="width:250px;" /><br/><br/>
		接&nbsp;&nbsp;收&nbsp;&nbsp;方&nbsp;&nbsp;&nbsp;<input type="radio" name="receiver" value="0" checked="checked" >&nbsp;&nbsp;学校&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="radio" name="receiver" value="1"  >App&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/><br/>
		<textarea name="content" rows="20" cols="80"></textarea>
		<br/><br/>
		<input class="btn btn-info" type="submit" value="确定发送">
	</form>
</div>

<div align="center">
	
</div>
</body>

</html>
