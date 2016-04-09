<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>月活跃度</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="../js/highcharts.js"></script>
	
	<%--引入bootstrap--%>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>
  </head>
  <%
  		Calendar calendar = Calendar.getInstance();
  		int year = calendar.get(Calendar.YEAR)+1;
   %>
  <body>
  	<div hidden="true" id="year"><%=year %></div>
	<div>
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="注册用户" id="enrollStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="用户参与度" id="activeStudent"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活动创建" id="activity"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="活跃度" id="active"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="开放学校" id="openSchool"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="表统计" id="table"/>&nbsp;&nbsp;&nbsp;&nbsp;
  		<input type="button" class="btn btn-info" style="width:100px" height="35px" value="通知统计" id="announcement"/>&nbsp;&nbsp;&nbsp;&nbsp;
  	 
  	<hr/>

  	<div class="alert alert-info">
		<button type="button" class="close" data-dismiss="alert">×</button>
		<p align="center">提示! 初始显示今年的数据.如果月份还没到，则数据显示为0</p>
	</div>

  		<button class="btn btn-info" type="button" onclick="location.href='tablebyday.jsp'">日统计</button>
  		<button class="btn btn-info" type="button" onclick="location.href='tablebymonth.jsp'">月统计</button>
  	</div>
    <div id="container" style="min-width:800px;height:400px"></div><br>
    <div align="center">
    	 <button class="btn btn-success" type="button" id="before">上一页</button>&nbsp;&nbsp;
    	 <button class="btn btn-success" type="button" id="after">下一页</button>
    </div>
    
    <!-- 成功加载样式 -->
    <div align="center"  id="success" style="position: fixed;padding:1%;top: 25%;left:45%;   	
    	background-color: rgba(0,0,0,0.3);border-radius:10px;display:none;
    	font-family: cursive;color: white; font-size: 30px;">success</div>
  </body>
  <script type="text/javascript">
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
		
  			//before
  			$("#before").bind("click",function(){
  				var year = $("#year").text();
  				$.get("getTableData",{"page":1,"time":year,"type":"month"},function(data){  			
	  				var chart = $('#container').highcharts();
	  				var series=chart.series;            
	               	while(series.length > 0) {
	                    series[0].remove(false);
	                }               
	                chart.redraw();				
	  				chart.addSeries({
	  					name: year-1+"年创建表数",
	  					data: eval("["+data+"]"),      		
	            	});
	            	$("#year").text(year-1);
	            	
	            	$("#success").fadeIn("fast");
            		setInterval(function(){
            			$("#success").fadeOut("fast");
            		},3000);
            	});
  			});
  			//after
  			$("#after").bind("click",function(){
  				var year = $("#year").text();
  			  	$.get("getTableData",{"page":2,"time":year,"type":"month"},function(data){  			
	  				var chart = $('#container').highcharts();
	  				var series=chart.series;            
	               	while(series.length > 0) {
	                   series[0].remove(false);
	                }
	                var year1 = parseInt(year)+1;                
	                chart.redraw();				
	  				chart.addSeries({
	  					name: year1+"年创建表数",
	            		data: eval("["+data+"]"),
	            	});
	            	$("#year").text(year1);
	            	
	            	$("#success").fadeIn("fast");
            		setInterval(function(){
            			$("#success").fadeOut("fast");
            		},3000);
            	});
  			});
  		
  		
  			function rem(){
  				$(".close").parent().slideUp("fast");				
  			}		
  			setInterval(rem, 10000);
  		
  			$(".close").bind("click",function(){
  				$(this).parent().slideUp("fast");
  			});			
  		});
  
  		$(function(){			
  			$("#container").highcharts({
		        chart: {
		            type: "line"
		        },
		        title: {
		            text: "月创建表数"
		        },
		        xAxis: {
		            categories: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"]
		        },
		        yAxis: {
		            title: {
		                text: "数量(个)"
		            }
		        },
		        plotOptions: {
		            line: {
		                dataLabels: {
		                    enabled: true
		                },
		                enableMouseTracking: false
		            }
		        },
		        credits: {
	          		enabled:false
				}
		    });		
  		});
  		
  		//初始化
  		$(function(){
  			var year =$("#year").text();//year是当前的下一年
  			$.get("getTableData",{"page":1,"time":year,"type":"month"},function(data){		
	  			var chart = $('#container').highcharts();				
	  			chart.addSeries({
	  				name: "今年创建表数",
	            	data: eval("["+data+"]"),
	            });
	            $("#year").text(year-1);
            });
  		});
  </script>
</html>
