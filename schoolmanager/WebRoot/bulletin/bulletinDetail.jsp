<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>通知详情</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
   <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
  </head>
  
  <body >
  	<%
  		Document d=(Document)request.getAttribute("notice");
  		Date time=(Date)d.get(StaticString.SystemNotice_ReleaseTime);
 	    String t= new SimpleDateFormat("yyyy-MM-dd").format(time);
 	    int state=(Integer)d.get(StaticString.SystemNotice_Receiver);
  	 %>     	
  	<div align="center">
  		<h3><%=d.get(StaticString.SystemNotice_Title) %></h3>
  		发布者:<%=d.get(StaticString.SystemNotice_Publisher) %>&nbsp;&nbsp;&nbsp;&nbsp;时间:<%=t %>&nbsp;&nbsp;&nbsp;&nbsp;
  		接受者：
  		<%
   			if(state==0){
   		 %>
   			 学校<br/>
	 	<%
		 }else{
		  %>
		   App<br/>
		 <%} %><br>
	    <textarea rows="20" cols="80" readonly="readonly"><%=d.get(StaticString.SystemNotice_Content) %></textarea>
	    
	    <br/><br/>
	    <input type="button" id="back" class="btn btn-info"  value="返&nbsp;&nbsp;&nbsp;回"/>
  	</div>
  	
  </body>

<script type="text/javascript">
	$(function(){
		$("#back").bind("click",function(){
			window.location.href="bulletinList.jsp";
		});
	});
</script>
</html>