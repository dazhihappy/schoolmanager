<%@page import="com.dao.DaoImpl"%>
<%@page import="com.mongodb.BasicDBObject"%>
<%@page import="com.dao.CreateQueryFromBean"%>
<%@page import="com.dao.CreateAndQuery"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="bean.InActivity"%>
<%@page import="bean.UserCount"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>活动按月统计</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

	<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../js/highcharts.js"></script>
	<script type="text/javascript" src="../js/exporting.js"></script>
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
	
  </head>
  <%
  		int NUM = 12; //一次显示天数
  		int year =0;//年份
  		ArrayList<UserCount> userCounts  = new ArrayList<UserCount>(); 		
  		if(request.getAttribute("activityCount")!=null){
  			userCounts = (ArrayList<UserCount>)request.getAttribute("activityCount");
  			String time = userCounts.get(0).getName();
  			String []times =time.split("-");
  			year =Integer.parseInt(times[0]);
  		}else{	//初始化
  			Calendar calendar1 = Calendar.getInstance();
  			Calendar calendar2 = Calendar.getInstance();
  			year = calendar1.get(Calendar.YEAR); // 获取当前年份 			
  			InActivity inActivity1 = new InActivity();
  			InActivity inActivity2 = new InActivity();
  			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
  			for(int i = 0; i< NUM ;i++ ){
  				calendar1.set(year, i, 1, 0, 0, 0);
  				calendar2.set(year, i+1, 1, 0, 0, 0);
  				inActivity1.setReleaseTime(calendar1.getTime());
  				inActivity2.setReleaseTime(calendar2.getTime());
  				CreateAndQuery andQuery=new CreateAndQuery();//条件合并
  				andQuery.Add(CreateQueryFromBean.GtObj(inActivity1));
				andQuery.Add(CreateQueryFromBean.LtObj(inActivity2));
				BasicDBObject query=andQuery.GetResult();
				userCounts.add(new UserCount(sdf.format(calendar1.getTime()), (int)DaoImpl.GetSelectCount(InActivity.class, query)));
  			}
  		}
  		String names ="[";
		String counts = "[";
		for (int i = 0; i < userCounts.size(); i++) {
			names +="'"+ userCounts.get(i).getName()+"',";//横坐标
			counts +=userCounts.get(i).getCount()+",";//纵坐标
		} 
		names += "]";
		counts += "]";
   %>  
  <body>
  	<div id="year" hidden="true"><%=year %></div>
  	<div>
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="注册用户" id="enrollStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="用户参与度" id="activeStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活动创建" id="activity"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活跃度" id="active"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="开放学校" id="openSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="表统计" id="table"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="通知统计" id="announcement"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 
  	<hr/>
  		<button class="btn btn-info" type="button" onclick="location.href='activityday.jsp'">日活动创建量</button>
  		<button class="btn btn-info" type="button" onclick="location.href='activitymonth.jsp'">月活动创建量</button>
  	</div>
    <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
    <div align="center">
    	 <button class="btn btn-success" type="button" id="before">上一页</button>&nbsp;&nbsp;
    	 <button class="btn btn-success" type="button" id="after">下一页</button>
    </div>
  </body>
  <script type="text/javascript">
  		$(function(){
  			$("#before").bind("click",function(){
  				location.href="getActivityDataByMonth?type=month&page=1&time="+$("#year").text();
  			});
  			
  			$("#after").bind("click",function(){
  				location.href="getActivityDataByMonth?type=month&page=2&time="+$("#year").text();
  			});
  			$("#enrollStudent").bind("click",function(){
			window.location.href="enrollStudent.jsp";
		});
		
		$("#activeStudent").bind("click",function(){
			window.location.href="activeStudent.jsp";
		});
		
		$("#active").bind("click",function(){
			window.location.href="dateactive.jsp";
		});
		
		$("#activity").bind("click",function(){
			window.location.href="activityday.jsp";
		});
		
		$("#openSchool").bind("click",function(){
			window.location.href="openSchool.jsp";
		});
		
		$("#table").bind("click",function(){
			window.location.href="tablebyday.jsp";
		});
		
		$("#announcement").bind("click",function(){
			window.location.href="announcementbyday.jsp";
		});
  		});
  
  
		$(function () {
		    $("#container").highcharts({
		        title: {
		            text: "日活动发布量",
		            x: -20 //center
		        },
		        xAxis: {
		            categories: <%=names%>
		        },
		        yAxis: {
		            title: {
		                text: "数量(个)"
		            },
		            plotLines: [{
		                value: 0,
		                width: 1,
		                color: "#808080"
		            }]
		        },
		        tooltip: {
		            valueSuffix: "个"
		        },
		        legend: {
		            layout: "vertical",
		            align: "right",
		            verticalAlign: "middle",
		            borderWidth: 0
		        },
		        series: [{
		            name: "月创建活动量",
		            data: <%=counts%>
		        }],
		        credits: {
	          		enabled:false
				}
		    });
		});
	</script>
</html>
