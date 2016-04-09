<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="staticData.StaticString"%>
<%@page import="com.dao.DaoImpl"%>
<%@page import="com.dao.CreateQueryFromBean"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="bean.SystemManager"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>applyMangerList</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">	
	<script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
   
  </head>
  <%
  	SystemManager systemManager = new SystemManager();
  	systemManager.setState(0);
  	
  	BasicDBObject query = CreateQueryFromBean.EqualObj(systemManager);
  	
  	BasicDBObject projection = new BasicDBObject();
  	projection.put(StaticString.SystemManager_Name, 1);
  	projection.put(StaticString.SystemManager_Phone,1);
  	projection.put(StaticString.SystemManager_Sex, 1);
  	projection.put(StaticString.SystemManager_Email, 1);
  	
  	  	
  	MongoCursor<Document> mongoCursor = DaoImpl.GetSelectCursor(SystemManager.class, query, projection);
  	ArrayList<Document> usrList = new ArrayList<Document>();
  	while (mongoCursor.hasNext()) {
  		usrList.add(mongoCursor.next());
  	}
   %>
  <body>
	   <div class="main">
	   		<div class="top">
	   			<input id="manager_apply" type="button"  class="btn btn-info" value="系统管理员申请" />
	   			<input id="manager_now" type="button"   class="btn btn-info" value="现有系统管理员" />
	   		</div>
			<hr/>
			 
			<div class="mid" style="font-size:20px;">
				正在申请管理员:
			</div>
			
			<div class="content">
				<table class="table">
					<tr>
						<th>姓名</th>
						<th>性别</th>
						<th>电话</th>
						<th>邮箱</th>
						<th>操作</th>
					</tr>
					<%
						if(usrList != null && usrList.size() != 0){
							for(int i = 0; i < usrList.size(); i++) {
								Document document = usrList.get(i);
							%>
								<tr <%if(i%2==0){%>class='success'<%} %>>
									<td><%=document.get("Name") %></td>
									<%
									if((Integer)document.get("Sex") == 0) {
									 %>
									<td>男</td>
									<%
									} else if((Integer)document.get("Sex") == 1) {
									 %>
									<td>女</td>
									<%
									} else {
									 %>
									<td>不明</td>
									<%} %>
									<td><%=document.get("Phone") %></td>
									<td><%=document.get("Email") %></td>
									<td>
										<input class="acceptApplyBut btn btn-info" type="button"   style="width:50px;" value="同意" />
										<input class="refuseApplyBut btn btn-info" type="button"  style="width:50px;" value="拒绝" />
									</td>
								</tr>
							<%}
						}
					 %>
					
				</table>
			</div>  		
	   </div>
  </body>
  <script type="text/javascript"> 
  
  $(function(){
  	$("#manager_now").click(function(){
  		window.location.href="<%=path%>/sysManager/managerList.jsp";
  	});
  });
  $(function(){
  	$("#manager_apply").click(function(){
  		window.location.reload();
  	});
  });
  
  $(function(){
  	$(".acceptApplyBut").bind("click",function(){
  		var actPhone = "";
  		var $tr = $(this).parent();
  		$.get("managerListAccept.action",{
  				"actPhone":$(this).parent().parent().find("td:eq(2)").html()
  			},function(data){
  				if(data == "success"){
  					$tr.html("已接受");
  					alert("接收成功！");
  				}
  				if(data == "error") {
  					alert("您没有权限接收！");
  				};
  			});
  		});
  	});
  
  $(function(){
  	$(".refuseApplyBut").bind("click",function(){
  		var actPhone = "";
  		var $tr = $(this).parent().parent();
  		$.get("managerListRefuse.action",{
  				"actPhone":$(this).parent().parent().find("td:eq(2)").html()
  			},function(data){
  				if(data == "success"){
  					$tr.remove();
  					alert("已经删除！");
  				}
  				if(data == "error") {
  					alert("您没有权限拒绝！");
  				};
  			});
  		});
  	});
  
  </script>
</html>
