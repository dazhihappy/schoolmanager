<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<title>处理反馈</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
   <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
  </head>
  
  <body >
  	<h2 align="center">反馈详情</h2>
  	<%
  		Document d=(Document)request.getAttribute("selectedSuggestion");
  		Date time=(Date)d.get(StaticString.Suggestion_ReleaseTime);
 	    String t= new SimpleDateFormat("yyyy-MM-dd").format(time);
  	 %>     	
  	<div align="center">
  	<ul style="list-style:none;font-size:20px">
  		<li><%=d.get(StaticString.Suggestion_From) %>&nbsp;&nbsp;&nbsp;&nbsp;反馈时间:<label><%=t %></label><br>
	    	<label><%=d.get(StaticString.Suggestion_Content)%></label>
	    </li>
	 </ul>
  	</div>
  	<br/>

    <div align="center" >
    	<textarea name="solution" style="width: 700px;height: 200px"></textarea><br/><br/>
    	<input type="button" class="btn btn-info" value="提交解决方案" id ="submit">  
    </div> 
    <div style="display:none" id="DealPerson"><%=session.getAttribute("userId") %></div>
    <div style="display:none" id="id"><%=d.get("_id") %></div>
    <script type="text/javascript">
    	$(function(){
    		$("#submit").bind("click",function(){
    			var solution =$("[name=solution]").val().trim();
    			var dealPerson =$("#DealPerson").text().trim();
    			var id =$("#id").text().trim();
    			if(solution==""){
    				alert("请输入解决方案");
    				$("[name=solution]").val("");
    			}else{
    			$.post("feedbackHandle",{solution:solution,dealPerson:dealPerson,_id:id},function(data){
    				if(data=="true"){
    					alert("处理成功");
    					window.location.href="feedbackList.jsp";
    				}else{
    					alert("处理失败");
    				}
    			});
    			}
    		});
    	});
    </script> 

  </body>
</html>
