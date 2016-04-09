<%@page import="com.dao.DaoImpl"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="staticData.StaticString"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="bean.Province"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>school</title>
	<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
 	<!-- 引入bootstrap -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="../css/addschool.css">
    
    <link rel="stylesheet" type="text/css" href="../js/jquery-ui.min.css">
    <script type="text/javascript" src="../js/jquery-ui-1.9.2.custom.min.js"></script>
	<script type="text/javascript" src="../js/jquery.validate.js"></script>
	
</head>
<body>
	<input type="button" class="btn btn-info" style="width:100px" height="35px" value="现有开放学校" id="existSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	<input type="button" class="btn btn-info" style="width:100px" height="35px" value="录入新学校" id="addSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	<input type="button" class="btn btn-info" style="width:100px" height="35px" value="新增地区" id="addArea"/>&nbsp;&nbsp;&nbsp;&nbsp; 		
    <hr>
     
	<form action="addSchool" id="schoolInformation" method="post">
		<div style="position: absolute;left: 12%;top: 60px;font-size: 20px;z-index: 1;">录入基本信息</div>
		<div id="basicinfo">
			<table>
				<tbody>
					<tr>
						<td><label for="schoolName">学校名称</label></td>
						<td><input type="text" name="schoolName" id="schoolName"></td>
					</tr>
					<tr>
						<td><label for="province">所在省市</label></td>
						<td>
							<!-- 待修改 -->
							<select name="province" id="province">
								<option selected="selected">请选择</option>
								<%
									BasicDBObject projection = new BasicDBObject();
									projection.put(StaticString.Province_Name, 1);
									MongoCursor<Document> cursor = DaoImpl.GetSelectCursor(Province.class, new BasicDBObject(), projection);
								 	while(cursor.hasNext()){
								 %>
								<option><%=cursor.next().getString("Name")%></option>
								<%} %>
							</select>
							<select name="city" id="city">
								<option selected="selected">请选择</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label for="phone">学校电话</label></td>
						<td><input type="text" name="phone" id="phone"></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div style="position: absolute;left: 12%;top: 280px;;font-size: 20px;z-index: 1;">录入管理员信息</div>
		<div id="manager">
			<table>
				<tbody>
					<tr>
						<td><label for="manageUserId">账户</label></td>
						<td><input type="text" name="manageUserId" id="manageUserId"></td>
					</tr>
					<tr style="display: none;">
						<td><label for="managerName">真实姓名</label></td>
						<td><input type="text" name="managerName" id="managerName" value="超级管理员"></td>
					</tr>
					<tr>
						<td><label for="managerPass">密码</label></td>
						<td><input type="password" name="managerPass" id="managerPass"></td>
					</tr>
					<tr>
						<td><label for="managerPassSure">确认密码</label></td>
						<td><input type="password" name="managerPassSure" id="managerPassSure"></td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div style="position: absolute;left: 12%;top: 500px;;font-size: 20px;z-index: 1;">录入专业</div>
		<div id="major">
			<span id="addMajor">+</span>
		</div>
		
		<div style="position: absolute;left: 12%;top: 720px;;font-size: 20px;z-index: 1;">录入积分类别</div>
		<div id="inActivityCategoty">
			<span id="addCategoty">+</span>
		</div>
		
		<div id="sub"><button type="submit" class="btn btn-info">确认</button></div>
	</form>
	<script type="text/javascript">
		$(function(){
			$("#province").bind("change",function(){
				var province = $(this).val();				
				$.post("getCity",{"name":province},function(data){
					//alert(data);
					var cities = new Array();
					cities = data.split("~");
					$("#city option").remove();
					for(var i =0 ;i<cities.length;i++){
						if(cities[i]!=""){
							$("#city").append("<option>"+cities[i]+"</option>");
						}
					}
				});
			});
		});
	
	
		$.validator.setDefaults({
			showErrors: function(map, list) {
				// there's probably a way to simplify this
				var focussed = document.activeElement;
				if (focussed && $(focussed).is("input")) {
					$(this.currentForm).tooltip("close", {
						currentTarget: focussed,
					}, true);
				}
				this.currentElements.removeAttr("title").removeClass("ui-state-highlight");
				$.each(list, function(index, error) {
					$(error.element).attr("title", error.message).addClass("ui-state-highlight");
				});
				if (focussed && $(focussed).is("input")) {
					$(this.currentForm).tooltip("open", {
						target: focussed,
					});
				}
			}
		});
		
		
		$(function(){
			//addMajor
			$("#addMajor").bind("click",function(){
				$(this).before("<input type='text' name='major' style='float:left;width:30%;margin:2px;'>");
			});
		
			//addCategoty
			$("#addCategoty").bind("click",function(){
				$(this).before("<input type='text' name='categoty' style='float:left;width:30%;margin:2px;'>");				
			});
		
		
			// 没有这个上边那段代码没有用哦~
			$("#schoolInformation").tooltip({
				show: false,
				hide: false
			});
			
			$("#schoolInformation").validate({
				rules:{
					schoolName:"required",
					province:"required",
					city:"required",
					phone:{
						required:true,
						digits:true,
						rangelength:[11,11]
					},
					managerName:"required",
					managerPass:{
						required:true,
						minlength:6,
						maxlength:16
					},
					managerPassSure:{
						required:true,
						equalTo:"#managerPass"
					},
					major:"required",
					categoty:"required",
					manageUserId:"required"
				},
				messages:{
					schoolName:"请输入学校名称",
					province:"请输入省",
					city:"请输入市",
					phone:{
						required:"请输入电话",
						digits:"请输入正确电话号",
						rangelength:"请输入正确电话号"
					},
					managerName:"请输入管理员姓名",
					managerPass:{
						required:"请输入密码",
						minlength:"请输入6到16位密码",
						maxlength:"请输入6到16位密码"
					},
					managerPassSure:{
						required:"请输入确认密码",
						equalTo:"两次输入密码不一致"
					},
					major:"请输入专业",
					categoty:"请输入活动类型",
					manageUserId:"请输入管理员ID"
				}
			});		
		});
		//跳转
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
</body>
</html>