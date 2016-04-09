<%@page import="org.bson.types.ObjectId"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dao.CreateQueryFromBean"%>
<%@page import="com.dao.DaoImpl"%>
<%@page import="org.bson.Document"%>
<%@page import="com.mongodb.client.MongoCursor"%>
<%@page import="staticData.StaticString"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="bean.OutActivity"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>显示活动</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/outactivityshow.css">
  </head>		
  <%	
  		int limit = 10;//一次显示limit条
  		int tag = 1;//1下一页,0上一页 
  		int pageCount = 1; //总页数
  		int pageNum = 1;//当前页数
  		ArrayList<Document> outActivityList = new ArrayList<Document>();  		
  		outActivityList.clear();
  		//获取总数
  		int totalNum = (int)DaoImpl.GetSelectCount(OutActivity.class, CreateQueryFromBean.EqualObj(new OutActivity()));
  		if(totalNum%limit==0){
  			pageCount=totalNum/limit;
  			if(pageCount==0){pageCount=1;}
  		}else{
  			pageCount =totalNum/limit+1;
  		}
  		
  		if(request.getParameter("pageNum")!=null){
  			pageNum =Integer.parseInt((String)request.getParameter("pageNum"));
  		}
  		if(request.getParameter("tag")!=null){
  			tag = Integer.parseInt((String)request.getParameter("tag"));
  		}
  		
  		BasicDBObject projection = new BasicDBObject();//查找
  		projection.put(StaticString.OutActivity_id, 1);
  		projection.put(StaticString.OutActivity_Name, 1);
  		projection.put(StaticString.OutActivity_ReleaseTime, 1);  		
  		
  		OutActivity outActivity = new OutActivity();
  		if(tag==1&&request.getParameter("nexPageId")!=null){	//下一页
	  		outActivity.set_id(new ObjectId((String)request.getParameter("nexPageId")));
  		}else if(tag==0&&request.getParameter("prePageId")!=null){	//上一页
  			outActivity.set_id(new ObjectId((String)request.getParameter("prePageId")));
  		} else{}
  		  		
  		BasicDBObject sort = new BasicDBObject();
  		if(tag==1){	//下一页
  			sort.put(StaticString.OutActivity_ReleaseTime, -1); 		
  		}else{
  			sort.put(StaticString.OutActivity_ReleaseTime, 1);
  		}
  		MongoCursor<Document> cursor = null;
  		if(tag==1&&request.getParameter("nexPageId")==null){
  			 cursor= DaoImpl.GetSelectCursor(OutActivity.class, CreateQueryFromBean.EqualObj(new OutActivity()), sort, limit, projection);
  		}else if(tag==1&&request.getParameter("nexPageId")!=null){
  			 cursor= DaoImpl.GetSelectCursor(OutActivity.class,CreateQueryFromBean.LtObj(outActivity) , sort, limit, projection);	
  		}else{
  			cursor= DaoImpl.GetSelectCursor(OutActivity.class,CreateQueryFromBean.GtObj(outActivity) , sort, limit, projection);			
  		}
  		while(cursor.hasNext()){
  			outActivityList.add(cursor.next());
  		} 		
   %>
  <body>
    <div align="center" style="font-size: 25px;">校外活动 </div><hr>
    <div class="create"> 
    	<button class="btn btn-info btn-small" type="button" onclick="location.href='outactivitycreate.jsp'">创建活动</button>
    </div>
    <table class="table">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>					
			<%if(outActivityList!=null &&outActivityList.size()>0){
				if(tag==1){
				for(int i =0; i< outActivityList.size();i++){
	    			String time=new SimpleDateFormat("yyyy-MM-dd").format(outActivityList.get(i).get("ReleaseTime"));
			 %>
			<tr>
				<td><%=i+1%></td>
				<td class="shows"  myId="<%=outActivityList.get(i).get("_id")%>"><%=outActivityList.get(i).get("Name")%></td>
				<td><%=time %></td>
				<td>
					<button class="btn btn-info btn-small edit" type="button">编辑</button>
					<button class="btn btn-info btn-small delete" type="button">删除</button>
				</td>
			</tr>
			<%}}else{
				int j =1;
				for(int i =outActivityList.size()-1 ; i>=0 ;i--){
					String time=new SimpleDateFormat("yyyy-MM-dd").format(outActivityList.get(i).get("ReleaseTime"));
				%>
			<tr>
				<td><%=j++%></td>
				<td class="shows"  myId="<%=outActivityList.get(i).get("_id")%>"><%=outActivityList.get(i).get("Name")%></td>
				<td><%=time %></td>
				<td>
					<button class="btn btn-info btn-small edit" type="button">编辑</button>
					<button class="btn btn-info btn-small delete" type="button">删除</button>
				</td>
			</tr>
			<%}}}%>		
		</tbody>
	</table>
	<div class="page">
		<%if(pageNum>1){ %><span><a id="prePage">上一页</a></span><%}%>
		<span>第<span id="pagenum"><%=pageNum %></span>页/共<%=pageCount%>页</span>
		<%if(pageNum<pageCount){%><span><a id="nexPage">下一页</a></span><%} %>		
	</div>    
  </body>
  <script type="text/javascript">
  	$(function(){
  		//上一页
  		$("#prePage").bind("click",function(){
  			var prePageId = $("table tr:eq(1)").children("td:eq(1)").attr("myId");
  			//alert("上一页:"+prePageId);
  			var page =parseInt($("#pagenum").text()) -1;
  			location.href="outactivityshow.jsp?tag=0&pageNum="+page+"&prePageId="+prePageId;
  		});
  		
  		//下一页
  		$("#nexPage").bind("click",function(){
  			var nexPageId = $("table tr:last").children("td:eq(1)").attr("myId");
  			//alert("下一页:"+nexPageId);
  			var page = parseInt($("#pagenum").text())+1;
  			location.href="outactivityshow.jsp?tag=1&pageNum="+page+"&nexPageId="+nexPageId; 			
  		});
  	
  		//编辑
  		$(".edit").live("click",function(){
  			var outActivityId = $(this).parent().parent().find("td").eq(1).attr("myId");
  			location.href="outactivityedit.jsp?outActivityId="+outActivityId;
  		});
  		
  		//删除
  		$(".delete").live("click",function(){
  			var $tag = confirm("确认删除？");
  			if($tag==true){
  				var outActivityId = $(this).parent().parent().find("td").eq(1).attr("myId");
  				$parent = $(this).parent().parent();
  				$.get("deleteOutActivity",{"outActivityId":outActivityId},function(data){
  					if(data=="true"){
  						location.href="outactivityshow.jsp";
  					}
  				});
  			}
  		});
  		
  		//查看
  		$(".shows").live("click",function(){
  			var  outActivityId = $(this).attr("myId");
  			var time = $(this).parent().children("td").eq(2).text();
  			location.href="outactivitydetail.jsp?outActivityId="+outActivityId+"&time="+time;
  		});
  		
  		$("tr:odd").addClass("success");
  	});
  </script>
</html>
