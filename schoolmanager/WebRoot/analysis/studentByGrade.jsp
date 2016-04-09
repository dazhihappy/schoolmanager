<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId" %>
<!DOCTYPE HTML >
<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../js/highcharts.js"></script>
    <%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>	
   <title>学生年级分布</title>
   </head>
  <div >
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="注册用户" id="enrollStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="用户参与度" id="activeStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活动创建" id="activity"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活跃度" id="active"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="开放学校" id="openSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="表统计" id="table"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="通知统计" id="announcement"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 
  	<hr/>
   
  		 <%
  		Date now=new Date();
  		//String date = new SimpleDateFormat("EEEE-MMMM-dd-yyyy HH:mm:ss").format(now);
  		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(now);
  		long studentNum=DaoImpl.GetSelectCount(StudentInfo.class, new BasicDBObject());
  		
  	 %> 截止<%=date %><br/>
  		 注册用户总量：<span style="color:red;"><%=studentNum %>人</span><br/><br/>
  		 <input type="button" class="btn btn-info" width="100px"  value="按日统计" id="day"/>
  	 	<input type="button" class="btn btn-info" width="100px"  value="按月统计" id="month"/>
  	 	<input type="button" class="btn btn-info" width="100px"  value="年级分布" id="grade"/>
  	</div>
  	<%
	//获取值
	
		Map<Integer, Long> map  =(Map<Integer, Long>) request.getAttribute("gradeMap"); //年份集合
	 	String str ="";
	 	for(Integer key :map.keySet()){
	 		str +="['"+key+"',"+map.get(key)+"],";		
	 	}
	 %>
  	<div id="container" align="center"  >
    <script  type="text/javascript">
	
		var chart;
	    
	    $(document).ready(function () {
	    	
	    	// Build the chart
	        $('#container').highcharts({
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false
	            },
	            title: {
	                text: '学生年级分布情况'
	            },
	            tooltip: {
	        	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	            },
	            plotOptions: {
	                pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: false
	                    },
	                    showInLegend: true
	                }
	            },
	            series: [{
	                type: 'pie',
	                name: '年级比例',
	                data: [
	                    <%=str%>
	                ]
	            }],
	            credits: {
	          		enabled:false
				}
	        });
	    });
    </script>
  	</div>

   
</html>

 <script  type="text/javascript">

	$(function(){
	
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
		
		$("#day").bind("click",function(){
			window.location.href="studentByDay";//一个action
		});
		
		$("#month").bind("click",function(){
			window.location.href="studentByMonth";//一个action
		});
		
		$("#grade").bind("click",function(){
			//window.location.href="studentByGrade.jsp";
			window.location.href="studentByGrade";//一个action
		});
	 
	});
   </script>