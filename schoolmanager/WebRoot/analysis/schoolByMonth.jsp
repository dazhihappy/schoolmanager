<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ page import="com.mongodb.BasicDBObject,com.dao.*,bean.*,com.mongodb.client.MongoCursor,org.bson.Document" %>
<%@ page import="staticData.*,java.text.*,org.bson.types.ObjectId,java.util.Map.*" %>
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../js/highcharts.js"></script>
    <%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>	
   <title>学校开放量按月统计</title>
   </head>
 <div>
  		
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="注册用户" id="enrollStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="用户参与度" id="activeStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活动创建" id="activity"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活跃度" id="active"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="开放学校" id="openSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="表统计" id="table"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="通知统计" id="announcement"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 
  	<hr/>
   
  		  		 <input type="button" class="btn btn-info" width="100px"  value="按日统计" id="day"/>
  	 	<input type="button" class="btn btn-info" width="100px"  value="按月统计" id="month"/>
  	</div>
  	<%
	//获取值
	
		Map<String, Long> map  =(Map<String, Long>) request.getAttribute("increaseMap"); //日期增长集合
		Map<String, String> schoolMap  =(Map<String, String>) request.getAttribute("schoolMap");
	 	String month ="[";
	 	String num="[";
		 for(String key :map.keySet()){
	 		month +="'"+key+"',";
	 		num	+=map.get(key)+",";	
	 	}
	 	month=month.substring(0,month.length()-1)+"]";
	 	num=num.substring(0,num.length()-1)+"]";
	 	String[] months=month.split(",");
	 	String firstMonth=months[0].substring(2,months[0].length()-1);
	 	String lastMonth=months[months.length-1].substring(1,months[0].length()-2);
	 %>
	 <div id="firstMonth" style="display:none"><%=firstMonth %></div>
	 <div id="lastMonth" style="display:none"><%=lastMonth %></div>
	 
   <div id="container" align="center" "width:70%;">
  		
   </div>
   <input type="button" class="btn btn-success" style="width:100px;margin-left:40%" value="上一页" id="pre"/>
   <input type="button" class="btn btn-success" style="width:100px;margin-right:20%" align="center" value="下一页" id="next"/> 
   <br/><br/>
   
</html>

 <script  type="text/javascript">
	 $(function () { 
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
		
	 	$('#container').highcharts({
        title: {
            text: '学校开放量增长情况',
            x: -20 //center
        },
        subtitle: {
            text: '按月查询',
            x: -20
        },
        xAxis: {
            categories: <%=month%>
        },
        yAxis: {
            title: {
                text: '数量'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '所'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: '增加学校数',
            data:<%=num%>
        }],
        credits: {
	          		enabled:false
				}
    });
	   
		
		$("#day").bind("click",function(){
			window.location.href="schoolByDay";//一个action
		});
		
		$("#month").bind("click",function(){
			window.location.href="schoolByMonth";//一个action
		});
		
		//上一页
	   $("#pre").bind("click",function(){
	   		var  firstMonth=$("#firstMonth").text();
			window.location.href="schoolByMonthPre?firstMonth="+firstMonth;//一个action
		});
		
	   $("#next").bind("click",function(){
	  		var  lastMonth=$("#lastMonth").text();
			window.location.href="schoolByMonthNext?lastMonth="+lastMonth;//一个action
		});
	});
   </script>