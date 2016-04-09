<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dao.CreateQueryFromBean"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.dao.DaoImpl"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="bean.OutActivity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>活动内容</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<base target="_blank" >
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>  
    <link rel="stylesheet" type="text/css" href="../css/outactivitydetail.css">  
  </head>
  <%!
  		Document detail = new Document();
   %>
  <%
  		OutActivity outActivity = new OutActivity();
  		outActivity.set_id(new ObjectId((String)request.getParameter("outActivityId")));
  		BasicDBObject query = CreateQueryFromBean.EqualObj(outActivity);
  		MongoCursor<Document> cursor = DaoImpl.GetSelectCursor(OutActivity.class, query, new BasicDBObject());
  		while(cursor.hasNext()){
  			detail = cursor.next();
  			break;
  		}
  		String time = request.getParameter("time");
  		Date date =detail.getDate("DeadLine");
  		String deadTime = new SimpleDateFormat("yyyy-MM-dd").format(date);
  		String [] times = detail.getString("RunTime").split("~");
  %>
  <body>  		
  	    
  	    <div class="content">
  	    	<div style="font-size: 25px"><%=detail.getString("Name") %></div><hr>
  	    	<%if(!detail.getString("ImgUrl").equals("noUrl")){ %>
  	    	<img alt="" src="<%=detail.getString("ImgUrl")%>">
  	    	<%} else{%>
  	    	<img alt="" src="../img/nothing.jpg">
  	    	<%} %>
  	    	<div style="margin-top: 2%;">
  	    		<%=detail.getString("Content") %>
  	    	</div>
  	    	<div style="margin-top: 2%;">
  	    		创建时间:<%=time %><br>
  	    		截止时间:<%=deadTime %><br>
  	    		开始时间:<%=times[0] %><br>
  	    		结束时间:<%=times[1] %><br>
  	    		活动类型:<%=detail.getString("Category") %><br>
  	    		组织方名称:<%=detail.getString("Organization") %><br>
  	    		<%if(!detail.getString("AttachmentName").equals("nothing")){ %>
  	    		附件:<a href="<%=detail.getString("AttachmentUrl")%>"><%=detail.getString("AttachmentName") %></a>
  	    		<%} %>
  	    	</div>
  	    </div> 	    
  </body>
</html>
