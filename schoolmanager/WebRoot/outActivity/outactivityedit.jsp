<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dao.DaoImpl"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="com.dao.CreateQueryFromBean"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="org.bson.Document"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="bean.OutActivity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>创建活动</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/outactivityedit.css">
	
	<!-- 导入jquery datetimepicker -->
	<link type="text/css" href="../js/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
    <link type="text/css" href="../js/jquery-ui-timepicker-addon.css" rel="stylesheet" />
    <script type="text/javascript" src="../js/jquery-ui-1.8.17.custom.min.js"></script>
	<script type="text/javascript" src="../js/jquery-ui-timepicker-addon.js"></script>
    <script type="text/javascript" src="../js/jquery-ui-timepicker-zh-CN.js"></script>
	
	<!-- 导入ueditor -->
	<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
		
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
  		String []times = detail.getString("RunTime").split("~");
  		String startTime = times[0];
  		String endTime = times[1];
  %>
  <body>
    <h3 align="center">编辑活动</h3> <hr>
    <%if(!detail.getString("ImgUrl").equals("noUrl")){ %>
    <div class="picture"><img alt="" src="<%=detail.getString("ImgUrl")%>"></div>
    <%}else{ %>
    <div class="picture"><img alt="" src="../img/nothing.jpg"></div>	
    <%} %>
    <div id="error"><s:actionerror/></div>
    <form action="outActivityEdit" method="post" enctype="multipart/form-data"> 
    <table>
    	<tr style="display: none;"><td colspan="2"><input name="outActivityId" value="<%=detail.get("_id").toString() %>"></td></tr>
    	<tr><td>活动名称</td><td><input type="text" value="<%=detail.getString("Name") %>" name="name"></td></tr>
    	<tr><td>活动类别</td>
    		<td><select myType="<%=detail.getString("Category")%>" name="category">
    			<option>比赛</option>
    			<option>讲座</option>
    			<option>公益</option>
    			<option>其他</option>
    		</select></td>
    	</tr>
    	<tr><td>报名截止时间</td><td><input type="text" value="<%=deadTime%>" name="deadLine" class="ui_timepicker"></td></tr>
    	<tr><td>活动开始时间</td><td><input type="text" value="<%=startTime%>" name="startTime" class="ui_timepicker"></td></tr>
    	<tr><td>活动结束时间</td><td><input type="text" value="<%=endTime%>" name="endTime" class="ui_timepicker"></td></tr>
    	<tr><td>组织方名称</td><td><input type="text" value="<%=detail.getString("Organization")%>" name="organization"></td></tr>
    	<tr><td colspan="2">活动内容</td></tr>
    	<tr><td colspan="2">
    		<textarea name="textareacontent" style="display: none;"><%=detail.getString("Content")%></textarea>
    		<script id="editor" type="text/plain" style="width:100%;height:100%;" name="content" ></script> 
    	</td></tr>
    	<tr><td>修改图片</td><td><input type="file" name="img"></td></tr>
    	<tr><td>修改附件</td><td><input type="file" name="attachment"></td></tr>
    	<tr><td colspan="2">
    		<input class="btn btn-info btn-small" type="submit" style="width: 100%" value="保存">
    	</td></tr>
    </table>
    </form>
  </body>
  <script type="text/javascript">
  	var ue = UE.getEditor('editor');
  	$(function(){
   		var content = $("[name='textareacontent']").text();
   		ue.addListener("ready", function(){
   			ue.setContent(content);
   		}); 
    });
  	
  	$(function(){
  		$("select").find("option").each(function(){
  			if($(this).text()==$("select").attr("myType")){
  				$(this).attr("selected","selected");
  			}
  		});
  		$("input:submit").bind("click",function(){
  				if($("[name='name']").val()==""||$("[name='category']").val()==""||$("[name='content']").val()==""){
  					alert("请至少输入活动名称,活动类型,活动内容");
  					return false;
  				}else{
  					return true;
  				}
  		});
  		$(".ui_timepicker").datetimepicker({
	            showSecond: true,
	            timeFormat: 'hh:mm:ss',
	            stepHour: 1,
	            stepMinute: 1,
	            stepSecond: 1
        });
  	});
  </script>
</html>
