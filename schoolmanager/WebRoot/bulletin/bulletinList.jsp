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
<title>反馈列表</title>

</head>
<%!int pageNu=1; int pageCount=0; int pageSize=10;
    	ObjectId oIdlast=null;
    	ObjectId oIdfirst=null;
    	Boolean flag;
  %>
  <body> 
  <div align="center" >
  	<h2>公告通知</h2>
  </div>
   	<div align="right" >	
		<input id="batch"  class="btn btn-info" type="button"  value="群发通知" >
	</div>
  	
    <div align="center" >
    	<table class="table">
    	
    		<caption style="font-size:20px;color:#8A2BE2">公告通知列表</caption>
    	<tr>
    		<td width="10%">序号</td>
    		<td width="80%">公告摘要</td>
    		<td width="10%"></td>
    	</tr>
    	<% 	 	
    	//获取总数，计算总页数
    	BasicDBObject query=new BasicDBObject();
    	int noticeNum=(int)DaoImpl.GetSelectCount(SystemNotice.class, query);//请求数
    	if(noticeNum%pageSize==0){
    		pageCount=noticeNum/pageSize;
    	}else{	
    		pageCount=noticeNum/pageSize+1;}
        //判断是第几页
        if((request.getAttribute("pageNu"))==null){
        	pageNu=1;
        }else{
        	pageNu=(Integer)request.getAttribute("pageNu");
             
        }
        //获取数据
        ArrayList<Document> list=new ArrayList<Document>();
        if(pageNu==1){
        	BasicDBObject projection=new BasicDBObject();
			BasicDBObject q=new BasicDBObject();
			BasicDBObject sort=new BasicDBObject();
			sort.put(StaticString.SystemNotice_id, -1);
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(SystemNotice.class, q, sort,pageSize, projection);
        	while(mc.hasNext()){
        		list.add(mc.next());
        	}
        	flag=true;
        }else{
        		
        	BasicDBObject projection=new BasicDBObject();

        	SystemNotice sg=new SystemNotice();
        	int pageTag=(Integer)request.getAttribute("pageTag");
        	BasicDBObject q;
        	BasicDBObject sort=new BasicDBObject();
			
        	if(pageTag==0){
         	//上一页
        		oIdfirst=new ObjectId((String)request.getAttribute("oIdfirst"));
        		sg.set_id(oIdfirst);
        		q=CreateQueryFromBean.GtObj(sg);
        		sort.put(StaticString.SystemNotice_id, 1);
        		flag=false;
        	}
			else{
			//下一页
				oIdlast=new ObjectId((String)request.getAttribute("oIdlast"));
				sg.set_id(oIdlast);
				q=CreateQueryFromBean.LtObj(sg);
				sort.put(StaticString.SystemNotice_id, -1);
				flag=true;
			}
			
			MongoCursor<Document> mc=DaoImpl.GetSelectCursor(SystemNotice.class, q, sort,pageSize, projection);
        	while(mc.hasNext()){
        		list.add(mc.next());
        	}
        }
       
        if(list!=null&&list.size()!=0&&flag){
    	    for(int i=0;i<list.size();i++){
    	    	Document d=list.get(i);
    	    	if(i==(list.size()-1)){  
    	    		oIdlast=(ObjectId)d.get(StaticString.SystemNotice_id);
    	    		
    	    	}else if(i==0){
    	    		oIdfirst=(ObjectId)d.get(StaticString.SystemNotice_id);

    	    	}		
    	    	Date time=(Date)d.get(StaticString.SystemNotice_ReleaseTime);
    	    	String t= new SimpleDateFormat("yyyy-MM-dd").format(time);
    	    	int state=(Integer)d.get(StaticString.SystemNotice_Receiver);
    %>   
   	<tr <%if(i%2==0){%>class='success'<%} %>>
   		<td width="10%"><%=i+1 %></td>
   		<td width="80%">
	   		发布者:<%=d.get(StaticString.SystemNotice_Publisher) %>&nbsp;&nbsp;&nbsp;&nbsp;时间：<%=t %>&nbsp;&nbsp;&nbsp;&nbsp;
	   		接收者:
   			<%
   				if(state==0){
   			 %>
   			 学校<br/>
   			 <%
   			 }else{
   			  %>
   			   App<br/>
   			 <%} %>
   			 <%=d.get(StaticString.SystemNotice_Title)%>
   		</td>
		    
   		</td>
   		<td width="10%">
 			<a href="bulletinDetailAction?_id=<%=d.get("_id") %>">查看详情</a>
   		</td>
	    
	</tr>
	    <%
	    	}
    	}
    	else{
    	int index=0;
	    	for(int i=list.size()-1;i>=0;i--){
	    	index++;
    	    	Document d=list.get(i);
    	    	if(i==(list.size()-1)){  
    	    		oIdfirst=(ObjectId)d.get(StaticString.SystemNotice_id);
    	    		
    	    	}else if(i==0){
    	    		oIdlast=(ObjectId)d.get(StaticString.SystemNotice_id);

    	    	}		
    	    	Date time=(Date)d.get(StaticString.SystemNotice_ReleaseTime);
    	    	String t= new SimpleDateFormat("yyyy-MM-dd").format(time);
    	    	int state=(Integer)d.get(StaticString.SystemNotice_Receiver);
    %>   		
	 <tr <%if(i%2==0){%>class='success'<%} %>>
   		<td width="10%"><%=index %></td>
   		<td width="80%">
	   		发布者:<%=d.get(StaticString.SystemNotice_Publisher) %>&nbsp;&nbsp;&nbsp;&nbsp;时间：<%=t %>&nbsp;&nbsp;&nbsp;&nbsp;
	   		接收者:
   			<%
   				if(state==0){
   			 %>
   			 学校<br/>
   			 <%
   			 }else{
   			  %>
   			   App<br/>
   			 <%} %>
   			 <%=d.get(StaticString.SystemNotice_Title)%>
   		</td>
		    
   		</td>
   		<td width="10%">
 			<a href="bulletinDetailAction?_id=<%=d.get("_id") %>">查看详情</a>
   		</td>
	    
	</tr>    	
	    <%
		    	}
    	}
    %>	
    	
    	</table>
    	<br/>
  <table>
	    <tr><td>共<%=pageCount%>页&nbsp;&nbsp;&nbsp;&nbsp;</td><td>第<%=pageNu%>页</td>
	    	<%if(pageNu>1&&oIdfirst!=null){
	    	//上一页
	    		int pageTag=0;%>
	    	<td><a href="bulletinPreNext?pageNu=<%=pageNu-1%>&oIdfirst=<%=oIdfirst%>&pageTag=<%=pageTag%>">上一页</a>&nbsp;&nbsp;&nbsp;&nbsp;</td><%} %>	    	
	    	<%if(pageNu<pageCount&&oIdlast!=null) {
	  		//	下一页
	  			int pageTag=1;%>	  	
	    	<td><a href="bulletinPreNext?pageNu=<%=pageNu+1%>&oIdlast=<%=oIdlast%>&pageTag=<%=pageTag%>">下一页</a>&nbsp;&nbsp;&nbsp;&nbsp;</td><%} %> 	  
	    </tr>
	 </table>  
	       
    </div>    
  </body>
</html>
<script type="text/javascript">
	$(function(){
		$("#batch").bind("click",function(){
			window.location.href="bulletinBatch.jsp";
		});
	});
</script>
