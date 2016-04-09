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
	<title></title>

</head>

<body>
<div >
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="现有开放学校" id="existSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="录入新学校" id="addSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="新增地区" id="addArea"/>&nbsp;&nbsp;&nbsp;&nbsp; 		
 </div>
 <hr>
</body>

<script type="text/javascript">
	$(function(){
		$("#existSchool").bind("click",function(){
			window.location.href="existSchool.jsp";
		});
		$("#addSchool").bind("click",function(){
			window.location.href="addSchool.jsp";
		});
		$("#addArea").bind("click",function(){
			window.location.href="addArea.jsp";
		});	
	});
	
</script>
</html>