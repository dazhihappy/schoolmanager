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
<title>学生管理</title>

</head>
<%!
	ArrayList<Document> list=null;
 %>
<body>
<div>
  		<input type="button" class="btn btn-info" name="commentManage" value="评论管理" id="commentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info"name="studentManage" value="学生管理" id="studentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button"class="btn btn-info" name="schoolManage" value="学校管理" id="schoolM"/>
	<hr/>
	<span style="font-size:30px">学生管理</span>
</div>
<div align="center">
	<select id="choose" style="width:150px" onchange="check()">
		<option selected ="selected">查&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;条&nbsp;&nbsp;&nbsp;件</option>
		<option value="_id">学&nbsp;&nbsp;&nbsp;生&nbsp;&nbsp;&nbsp;&nbsp;I&nbsp;&nbsp;&nbsp;&nbsp;D</option>
		<option value="Phone">电&nbsp;&nbsp;&nbsp;话&nbsp;&nbsp;&nbsp;号&nbsp;&nbsp;&nbsp;码</option>
		<option value="IdCard">学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</option>
		<option value="Email">邮&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;箱</option>
	</select>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="hidden" name="choice" />
	查&nbsp;询&nbsp;内&nbsp;容<input type="text" name="content" width="200px"/>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="button" id="search" class="btn btn-info" value="查&nbsp;&nbsp;&nbsp;&nbsp;询" />
</div>
<div align="center" style="color:red;">
	--------------------------------------------------------------------------------------------------------------------------------------------------------<br/>
	提示：禁言代表随机重置学生电话即用户名，恢复时可将学生电话改为文本框中的内容使其暂时可以登录，如管理员的电话<br/>
	置换学号与置换邮箱后学生被禁言，且学号和邮箱分别为000000000，forbid@mail.com可以此查询<br/>
	--------------------------------------------------------------------------------------------------------------------------------------------------------


	<table class="table">
		<tr>
			<td >序号</td>
			<td >学生姓名</td>
			<td>电话</td>
			<td>学号</td>
			<td>邮箱</td>
			<td>操作</td>
		</tr>
		<%
			list=(ArrayList<Document>)request.getAttribute("studentList");
			if(list==null){
				//什么都不做
			}
			else if(list!=null&&list.size()<1){%>
			
				<tr rowspan="5"><td  colspan="6" style="text-align:center">查无此人</td></tr>
			<% }
			else if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					int index=i+1;
					
					Document d=list.get(i);			
		 %>
		<tr <%if(i%2==0){%>class='success'<%} %>>
			<td align="center" style="width:5%;height:20px;"><%= index%></td>
			<td align="center" style="width:10%;height:20px;"><%= d.get(StaticString.StudentInfo_Name)%></td>
			<td align="center" style="width:13%;height:20px;"><input type="text" name="changeablePhone" value="<%=d.get(StaticString.StudentInfo_Phone) %>"/></td>
			<td align="center" style="width:12%;height:20px;"><%= d.get(StaticString.StudentInfo_IdCard)%></td>
			<td align="center" style="width:25%;height:20px;"><%=d.get(StaticString.StudentInfo_Email) %></td>
			<td align="center" style="width:35%;height:20px;" id="<%= d.get(StaticString.StudentInfo_id)%>">
				<a href="forbidStudentAction?_id=<%= d.get(StaticString.StudentInfo_id)%>&tag=1" >禁言</a>&nbsp;&nbsp;
				<a href="forbidStudentAction?_id=<%= d.get(StaticString.StudentInfo_id)%>&tag=2" >置换学号</a>&nbsp;&nbsp;
				<a href="forbidStudentAction?_id=<%= d.get(StaticString.StudentInfo_id)%>&tag=3" >置换邮箱</a>&nbsp;&nbsp;
				<a href="#" name="recover">恢复</a>
			</td>
		</tr>
		<%	

		}
			}
		 %>
	</table>
</div>
</body>

<script type="text/javascript">
	//保存选择的条件
	function check(){
		var value=document.getElementById("choose").value;
		document.getElementsByName("choice").value=value;
		//alert(document.getElementsByName("choice").value);
	}
	$(function(){
		$("#search").bind("click",function(){
			var choice=document.getElementsByName("choice").value;
			var content=document.getElementsByName("content")[0].value;
			//alert(choice+" "+content);
				//$.get("searchStudentAction",{"choice":choice,"content":content});
			window.location.href="searchStudentAction?choice="+choice+"&content="+content;
		});
		
		$("a[name=recover]").bind("click",function(){
				var _id=$(this).closest("td").attr("id");
				var tempPhone=$(this).parent().parent().find("input[name=changeablePhone]").val();
				window.location.href="recoverStudentAction?tempPhone="+tempPhone+"&_id="+_id;
		});
	
	
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