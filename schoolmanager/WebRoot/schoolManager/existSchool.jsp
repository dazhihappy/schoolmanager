<%@page import="java.sql.Date"%>
<%@page import="org.bson.types.ObjectId"%>
<%@page import="bean.SchoolInfo"%>
<%@page import="org.bson.Document"%>
<%@page import="com.dao.DaoImpl"%>
<%@page import="com.dao.CreateQueryFromBean"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="staticData.StaticString"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="bean.School"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>school</title>    
 	<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
 	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/existschoollist.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>    
    
    <link rel="stylesheet" type="text/css" href="../js/jquery-ui.min.css">
    <script type="text/javascript" src="../js/jquery-ui-1.9.2.custom.min.js"></script>    
  </head>
  <%
 		int limit =10;//一页显示10所学校
 		int pageCount =1; //总页数
 		int pageNum =0;//当前页数
  			 		
  		if(request.getAttribute("schoolInfos")==null){
  			int count =(int) DaoImpl.GetSelectCount(School.class, new BasicDBObject());
  			if(count%limit==0){
  				pageCount = count/limit;
  			}else{
  				pageCount = count/limit+1;
  			}
  			System.out.println("pageCount:"+count);
  			request.setAttribute("pageCount", pageCount);
  			pageNum+=1;
  			request.setAttribute("pageNum", pageNum);  			
  			BasicDBObject sort = new BasicDBObject();//时间倒叙
  			sort.put(StaticString.School_OpenTime, -1);
  			BasicDBObject projection = new BasicDBObject();//显示数据
  			projection.put(StaticString.School_id, 1);
  			projection.put(StaticString.School_Name, 1);
  			projection.put(StaticString.School_OpenTime, 1);
  			projection.put("State", 1);
  			projection.put(StaticString.School_Tel, 1);
  			projection.put(StaticString.School_AddressP, 1);
  			projection.put(StaticString.School_AddressC, 1);
  			MongoCursor<Document> cursor = DaoImpl.GetSelectCursor(School.class, new BasicDBObject(),sort, limit, projection);
  			ArrayList<SchoolInfo> schoolInfos = new ArrayList<SchoolInfo>();
  			Document document = null;
  			while(cursor.hasNext()){
  				document = cursor.next();
  				SchoolInfo info = new SchoolInfo();
  				info.set_id(document.getObjectId("_id"));
  				info.setDate(document.getDate("OpenTime"));
  				info.setName(document.getString("Name"));
  				info.setState(document.getBoolean("State"));
  				info.setTel(document.getString("Tel"));
				info.setProvince(document.getString("AddressP"));
				info.setCity(document.getString("AddressC"));
  				schoolInfos.add(info);
  			}
  			request.setAttribute("schoolInfos",schoolInfos);
  		}
   %>
  <body> 
  <div id="content">
  	 <input type="button" class="btn btn-info" style="width:100px" height="35px" value="现有开放学校" id="existSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 <input type="button" class="btn btn-info" style="width:100px" height="35px" value="录入新学校" id="addSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 <input type="button" class="btn btn-info" style="width:100px" height="35px" value="新增地区" id="addArea"/>&nbsp;&nbsp;&nbsp;&nbsp; 		
     <hr>
     <h2 align="center">查看学校</h2><hr>
     <table class="table" id="schoolTable">
		<thead>
			<tr>
				<th>序号</th>
				<th>学校名称</th>
				<th>开通时间</th>
				<th>电话</th>
				<th>省份</th>
				<th>城市</th>
				<th>状态</th>
				<th>编辑</th>
			</tr>
		</thead>
		<tbody>
			<s:bean name="utils.MyComparator" var="mycomparator"></s:bean>
			<s:sort comparator="#mycomparator" source="#request.schoolInfos" var="sortlist"></s:sort>
			<s:iterator status="st" value="#attr.sortlist" var ="schinfo">			 
				<tr  <s:if test="#st.odd">class='success'</s:if> >
					<td><s:property value="#st.index+1"/></td>
					<td title="点击编辑管理员" class="edit" id="${_id}" ><s:property value="name"/></td>
					<td><s:date name="date" format="yyyy-MM-dd"/></td>
					<td><s:property value="tel"/></td>
					<td><s:property value="province"/></td>
					<td><s:property value="city"/></td>
					<s:if test="state">
						<td>开通服务状态</td>
						<td><button class="btn btn-success service" type="button">暂停服务</button></td>	
					</s:if>
					<s:else>
						<td>暂停服务状态</td>
						<td><button class="btn btn-info service" type="button" >开通服务</button></td>
					</s:else>
				</tr>				
			</s:iterator>							
		</tbody>
	 </table>
	 <script type="text/javascript">
	 	$(function(){
	 		$(".service").bind("click",function(){
	 			var tag =confirm("确认更改?");
	 			if(tag===true){
		 			var state = false; //开通-true;关闭-false
		 			var butt =$(this);
		 			var schoolId = butt.parent().parent().find("td:eq(1)").attr("id");
		 			if(butt.text()==="暂停服务"){
		 				//alert(state+ " "+schoolId); //传后台
		 				$.get("changeService",{"schoolId":schoolId,"state":state},function(data){
		 					if(data=="true"){
		 						butt.text("开通服务").removeClass("btn-success").addClass("btn-info");
		 						butt.parent().parent().find("td:eq(6)").text("暂停服务状态");
		 					}
		 				});
		 			}else{
		 				state = true;
		 				//alert(state+ " "+schoolId); //传后台
		 				$.get("changeService",{"schoolId":schoolId,"state":state},function(data){
		 					if(data=="true"){
		 						butt.text("暂停服务").removeClass("btn-info").addClass("btn-success");
		 						butt.parent().parent().find("td:eq(6)").text("开通服务状态");
		 					}
		 				});		 				
		 			}
	 			}
	 		});
	 	});
	 </script>
	 <!-- 分页 -->
	 <div align="right">
	 	<s:if test="#request.pageNum>1">
	 		<a class="pre">上一页</a>
	 	</s:if>
	 	&nbsp;<span id="pageNum"><s:property value="#request.pageNum"/></span>/<span id="pageCount"><s:property value="#request.pageCount"/></span>&nbsp;
	 	<s:if test="#request.pageNum<#request.pageCount">
	 		<a class="nex">下一页</a>
	 	</s:if>
	 </div>	 
	</div> 
	<div id="editSchoolWrapper" hidden="true">
		<div id="editSchool">
			<table>
				<tbody>
					<tr><td colspan="2"><span id=""></span>管理员</td></tr>
					<tr>
						<td><label for="userId">用户名</label></td>
						<td><input type="text" name="userId" id="userId" ></td>
					</tr>
					<tr>
						<td><label for="password">密码</label></td>
						<td><input type="text" name="password" id="password" ></td>
					</tr>
					<tr>
						<td><button class="btn btn-success" type="button" id="cancel">取消</button></td>
						<td><button class="btn btn-success" type="button" id="save">保存</button></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>	 
  </body>
  <script type="text/javascript">
  	$(function(){	
  		 //点击学校名字编辑		
  		 $(".edit").bind("click",function(){
  		 	var butt = $(this);
  		 	$.get("showManager",{"schoolId":butt.attr("id")},function(data){
  		 		//alert(data);
  		 		var str = new Array();
  		 		str = data.split("~");
  		 		$("#userId").val(str[0]);
  		 		$("#password").val(str[1]);
  		 		var title = $("#editSchool table tr:eq(0) td:eq(0) span");
  		 		title.text(butt.text());
  		 		title.attr("id",butt.attr("id"));
  		 		$("#editSchoolWrapper").fadeIn("fast");
  		 	});
  		 	
  		 });
  		 
  		 //cancel
  		 $("#cancel").bind("click",function(){
  		 	$("#editSchoolWrapper").fadeOut("fast");
  		 });
  		 
  		 //save
  		 $("#save").bind("click",function(){
  		 	var par = $(this).parent().parent().parent("tbody");
  			var schoolId = par.find("tr:eq(0) td:eq(0) span").attr("id");
  			var userId = par.find("tr:eq(1) td:eq(1) input").val();
  			var password = par.find("tr:eq(2) td:eq(1) input").val();
  			//alert(schoolId+" "+userId+" "+password);
  			$.post("changeManagerInfo",{"schoolId":schoolId,"userId":userId,"password":password},function(data){
  				if(data=="true"){
  					$("#editSchoolWrapper").fadeOut("fast");
  				}else{
  					alert("更新失败");
  				}
  			});
  		 });
  		 
  		 $("td").tooltip();
  	});
  	
  	$(function(){
  		//上一页
  		$(".pre").bind("click",function(){
  			var schoolId = $("#schoolTable tbody tr:first td:eq(1)").attr("id");
  			var tag=1; //上一页
  			var pageCount =$("#pageCount").text();
  			var pageNum =$("#pageNum").text();
  			//alert(schoolId+" "+pageCount+" "+pageNum);
  		  	location.href="changePage?schoolId="+schoolId+"&tag="+tag+"&pageCount="+pageCount+"&pageNum="+pageNum;	
  		});
  		
  		//下一页
  		$(".nex").bind("click",function(){
  			var schoolId = $("#schoolTable tbody tr:last td:eq(1)").attr("id");
  			var tag=2; //上一页
  			var pageCount =$("#pageCount").text();
  			var pageNum =$("#pageNum").text();
  			//alert(schoolId+" "+pageCount+" "+pageNum);
  			//location.href="changePage.action?schoolId="+schoolId+"&tag="+tag+"&pageCount="+pageCount+"&pageNum="+pageNum;
  			location.href="changePage?schoolId="+schoolId+"&tag="+tag+"&pageCount="+pageCount+"&pageNum="+pageNum;
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
</html>
