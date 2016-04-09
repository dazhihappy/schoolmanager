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
	<title>新增地区</title>

</head>

<body>
	<input type="button" class="btn btn-info" style="width:100px" height="35px" value="现有开放学校" id="existSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 <input type="button" class="btn btn-info" style="width:100px" height="35px" value="录入新学校" id="addSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 <input type="button" class="btn btn-info" style="width:100px" height="35px" value="新增地区" id="addArea"/>&nbsp;&nbsp;&nbsp;&nbsp; 		
     <hr>
<div align="center" style="margin-top:100px;font-size:17px;">
	<span style="color: #6633FF;font-size:20px">增加省份和城市</span><br/><br/>
	省份：<input type="text" height="45px" name="province">&nbsp;&nbsp;&nbsp;&nbsp;城市：<input type="text"height="45px" name="city">&nbsp;&nbsp;
	<input type="button" class="btn btn-info" value="增加" id="addprocity">
	
</div>
<hr/>
<div align="center" style="font-size:17px;">
	<span style="color: #6633FF;font-size:20px">增加城市</span><br/><br/>
	<select id="spro">
		<option selected="selected" value="null">选择省份</option>
		<%
			BasicDBObject projection=new BasicDBObject();
			projection.put(StaticString.Province_Name, 1);
			MongoCursor<Document> mCursor=DaoImpl.GetSelectCursor(Province.class, new BasicDBObject(), projection);
		 	ArrayList<Document> provinceList=new ArrayList<Document>();
		 	while(mCursor.hasNext()){
		 		provinceList.add(mCursor.next());
		 	}
		 	
		 	for(int i=0;i<provinceList.size();i++){
		 		
		 		String provinceName=(String)provinceList.get(i).get(StaticString.Province_Name);
		 	%>
		 	<option value="<%=provinceName %>"><%=provinceName %></option>
		 	<%
		 	}
		 %>
	</select>
	&nbsp;&nbsp;&nbsp;&nbsp;城市：<input type="text" height="45px" name="scity">&nbsp;&nbsp;
	<input type="button" class="btn btn-info" value="增加" id="addcity">
	
</div>
</body>

<script type="text/javascript">
	$(function(){
		$("#addprocity").bind("click",function(){
			var name=document.getElementsByName("province")[0].value;
			var city=document.getElementsByName("city")[0].value;
			alert(name+city);
			$.get("addAreaAction",{"province":name,"city":city,"flag":1},function(data){
				if(data=="true"){
					alert("增加成功");
					window.location.href="addArea.jsp";
				}
				else
					alert("增加失败");
			});
		});
		
		$("#addcity").bind("click",function(){
			var name=document.getElementById("spro").value;
			var city=document.getElementsByName("scity")[0].value;
			if(name=="null")
				alert("请选择有效省份！");
			alert(name+city);
			$.get("addAreaAction",{"province":name,"city":city,"flag":0},function(data){
				if(data=="true"){
					alert("增加成功");
					window.location.href="addArea.jsp";
				}
				else
					alert("增加失败");
			});
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