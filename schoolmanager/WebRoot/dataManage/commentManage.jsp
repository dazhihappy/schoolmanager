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
	<title>评论管理</title>

</head>
<%!
	int pageNu=1;
	final int pageSize=10;
	ObjectId timefirst=null;
	ObjectId timelast=null;
	int pageTag=1;
	int pageCount;
	Boolean flag;//是否需要倒序输出
 %>
<body>
<div  >
  		<input type="button" class="btn btn-info" name="commentManage" value="评论管理" id="commentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" name="studentManage" value="学生管理" id="studentM"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" name="schoolManage" value="学校管理" id="schoolM"/>
	<hr/>
	<table class="table">
	<caption style="font-size:16px;">评论列表</caption>	
		<tr>
			<td align="center" style="width:10%;height:30px;">序号</td>
			<td align="center" style="width:10%;height:30px;">学生标识(ID)</td>
			<td align="center" style="width:50%;height:30px;">评论内容</td>
			<td align="center" style="width:20%;height:30px;">创建时间</td>
			<td align="center" style="width:10%;height:30px;">是否删除</td>
		</tr>
		<%
			//获取总数，计算总页数
	    	BasicDBObject query=new BasicDBObject();
	    	int commentNum=(int)DaoImpl.GetSelectCount(ActivityComment.class, query);//评论数
	    	if(commentNum%pageSize==0){
	    		pageCount=commentNum/pageSize;
	    	}else{	
	    		pageCount=commentNum/pageSize+1;}
	        //判断是第几页
	        if((request.getAttribute("pageNu"))==null){
	        	pageNu=1;
	        }else{
	        	pageNu=(Integer)request.getAttribute("pageNu");
	             
	        }
			
			//获取数据
			ArrayList<Document> list = new ArrayList<Document>();
			//投影
    		BasicDBObject projection=new BasicDBObject();
    		projection.put(StaticString.ActivityComment_StuId, 1);
    		projection.put(StaticString.ActivityComment_Content,1);
    		projection.put(StaticString.ActivityComment_OccurrenceTime, 1);
    		//获取数据
    		BasicDBObject sort=new BasicDBObject();
    		BasicDBObject q=new BasicDBObject();
    		ActivityComment activityComment=new ActivityComment();
    		MongoCursor<Document> mc=null;
    		
    		if(pageNu==1){
    			flag=true;
    			sort.put(StaticString.ActivityComment_id, -1);
    			//sort.put(StaticString.ActivityComment_OccurrenceTime, -1);//按时间降序
    			mc=DaoImpl.GetSelectCursor(ActivityComment.class, q,sort, pageSize,projection);
    		}
    		else{
    			pageTag=(Integer)request.getAttribute("pageTag");
	    		if(pageTag==0){
	         	//上一页
				timefirst=new ObjectId((String)request.getAttribute("timefirst"));
				activityComment.set_id(timefirst);
        		q=CreateQueryFromBean.GtObj(activityComment);      	
        		sort.put(StaticString.ActivityComment_id, 1);
        		
        		flag=false;
	        	}
				else{
				//下一页

					timelast=new ObjectId((String)request.getAttribute("timelast"));		
					activityComment.set_id(timelast);
					q=CreateQueryFromBean.LtObj(activityComment);					
					sort.put(StaticString.ActivityComment_id, -1);
					flag=true;
				}
        }
        mc=DaoImpl.GetSelectCursor(ActivityComment.class, q, sort,pageSize, projection);
       	while(mc.hasNext()){
       		list.add(mc.next());
       	}
        if(list!=null&&list.size()!=0&&flag){
    	    for(int i=0;i<list.size();i++){
    	    	Document d=list.get(i);
    	    	Date time = (Date) d.get(StaticString.ActivityComment_OccurrenceTime);
				String rtime = new SimpleDateFormat("yyyy-MM-dd").format(time);
    	    	if(i==(list.size()-1)){  		
    	    		timelast=(ObjectId)d.get(StaticString.ActivityComment_id);
    	    	}else if(i==0){
    	    		timefirst=(ObjectId)d.get(StaticString.ActivityComment_id);
    	    		
    	    	}
    	    	
		%>	
		<tr <%if(i%2==0){%>class='success'<%} %>>
			<td align="center" style="width:10%;height:5%;"><%=i+1%></td>
			<td align="center" style="width:10%;height:30px;"><%=d.get(StaticString.ActivityComment_StuId) %></td>
			<td align="center" style="width:50%;height:30px;"><%=d.get(StaticString.ActivityComment_Content) %></td>
			<td align="center" style="width:20%;height:30px;"><%=rtime %></td>
			<td align="center" style="width:10%;height:30px;"><a href="commentDeleteAction?activityCommentId=<%=d.get(StaticString.ActivityComment_id) %>" >删除</td>
		</tr>
		<%
	    	}
    	}
    	else{
    	    int index=0; //序号
	    	for(int i=list.size()-1;i>=0;i--){
	    	    index++;
    	    	Document d=list.get(i);
    	    	Date time = (Date) d.get(StaticString.ActivityComment_OccurrenceTime);
				String rtime = new SimpleDateFormat("yyyy-MM-dd").format(time);
    	  
    	    	if(i==(list.size()-1)){  
    	    		timefirst=(ObjectId)d.get(StaticString.ActivityComment_id);
    	    	}else if(i==0){
					timelast=(ObjectId)d.get(StaticString.ActivityComment_id);
    	    	}		
    	    	
    %>   
		<tr <%if(i%2==0){%>class='success'<%} %>>
			<td align="center" style="width:10%;height:5%;"><%=index%></td>
			<td align="center" style="width:10%;height:30px;"><%=d.get(StaticString.ActivityComment_StuId) %></td>
			<td align="center" style="width:50%;height:30px;"><%=d.get(StaticString.ActivityComment_Content) %></td>
			<td align="center" style="width:20%;height:30px;"><%=rtime %></td>
			<td align="center" style="width:10%;height:30px;"><a href="commentDeleteAction?activityCommentId=<%=d.get(StaticString.ActivityComment_id) %>" >删除</td>
		</tr>
		<%
			}
			}
		%>
	</table>
	<table>
		<tr>
			<td>共<%=pageCount%>页</td>
			<td>第<%=pageNu%>页</td>
			<%if(pageNu>1&&timefirst!=null){%>
			<td><a
				href="commentPreNextAction?pageNu=<%=pageNu-1%>&timefirst=<%=timefirst%>&pageTag=0">上一页</a></td>
			<% }%>
			<%if(pageNu<pageCount&&timelast!=null) {%>
			<td><a
				href="commentPreNextAction?pageNu=<%=pageNu+1%>&timelast=<%=timelast%>&pageTag=1">下一页</a></td>
			<% }%>
		</tr>
	</table>
	</div>
</div>
</body>
</html>
<script type="text/javascript">
	$(function(){
	
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