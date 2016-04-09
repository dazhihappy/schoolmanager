<%@ page language="java" import="java.util.Date" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE HTML>
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../js/highcharts.js"></script>	
   
   	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
   
   </head>
<body>
	<div>
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="注册用户" id="enrollStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="用户参与度" id="activeStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活动创建" id="activity"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活跃度" id="active"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="开放学校" id="openSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="表统计" id="table"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="通知统计" id="announcement"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 
  	<hr/>
   
   		<input type="button" class="btn btn-info" width="100px"  value="点赞参与" id="praise"/>
  	 	<input type="button" class="btn btn-info" width="100px"  value="评论参与" id="comment"/>
  	 	<input type="button" class="btn btn-info" width="100px" value="报名热情" id="signup"/>
  	 	<input type="button" class="btn btn-info" width="100px" value="分享行为" id="share"/>
  	</div>
  	
 </body>  
 <script  type="text/javascript">
	$(function(){
		$("#enrollStudent").bind("click",function(){
			window.location.href="enrollStudent.jsp";
		});
		
		$("#activeStudent").bind("click",function(){
			window.location.href="activeStudent.jsp";
		});
		
		$("#active").bind("click",function(){
			window.location.href="dateactive.jsp";
		});
		
		$("#activity").bind("click",function(){
			window.location.href="activityday.jsp";
		});
		
		$("#openSchool").bind("click",function(){
			window.location.href="openSchool.jsp";
		});
		
		$("#table").bind("click",function(){
			window.location.href="tablebyday.jsp";
		});
		
		$("#announcement").bind("click",function(){
			window.location.href="announcementbyday.jsp";
		});
		$("#praise").bind("click",function(){
			window.location.href="praiseAnalysis";//一个action
		});
		
		$("#comment").bind("click",function(){
			window.location.href="commentAnalysis";//一个action
		});
		
		$("#signup").bind("click",function(){
			window.location.href="signupAnalysis";//一个action
		});
		
		$("#share").bind("click",function(){
			window.location.href="shareAnalysis";//一个action
		});
	});
   </script>
   </html>