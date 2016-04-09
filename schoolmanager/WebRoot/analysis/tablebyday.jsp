<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>日活跃度</title>
    
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
  		int day = calendar.get(Calendar.DATE);
  		calendar.set(Calendar.DATE, day+1);
  		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
  		String time = sdf.format(calendar.getTime());
  		System.out.println("get Tomorrow:"+time); //获取明天
   %>
  <body>
  	<div id="tomorrowDate" hidden="true"><%=time %></div>
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
		<p align="center">提示! 初始显示今天之前7天的数据.</p>
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
            	$.get("getTableData",{"page":1,"time":$("#tomorrowDate").text(),"type":"day"},function(data){
            		var strs = new Array();
            		strs = data.split("~");
            		var xx = new Array();
            		xx = strs[0].split(",");
            		var xax ="";
            		$("#tomorrowDate").text(xx[0]);
            		for(var i =0;i< xx.length;i++){
            			if(xx[i]!=""){
            				xax +="\""+xx[i]+"\",";
            			}
            		}
            		var chart = $('#container').highcharts();
  					var series=chart.series;            
               		while(series.length > 0) {
                    	series[0].remove(false);
                	}               
                	chart.redraw();	
                	chart.xAxis[0].setCategories( 
                		eval("["+xax+"]")
		        	);		
  					chart.addSeries({
  						name: "日创建表数",
            			data: eval("["+strs[1]+"]")
            		});
            		
            		$("#success").fadeIn("fast");
            		setInterval(function(){
            			$("#success").fadeOut("fast");
            		},3000);
            	});
  			});
  			
  			//after
  			$("#after").bind("click",function(){
  				$.get("getTableData",{"page":2,"time":$("#tomorrowDate").text(),"type":"day"},function(data){
            		var strs = new Array();
            		strs = data.split("~");
            		var xx = new Array();
            		xx = strs[0].split(",");
            		var xax ="";
            		$("#tomorrowDate").text(xx[0]);
            		for(var i =0;i< xx.length;i++){
            			if(xx[i]!=""){
            				xax +="\""+xx[i]+"\",";
            			}
            		}           	
	  				var chart = $('#container').highcharts();
	  				var series=chart.series;            
	               	while(series.length > 0) {
	                    series[0].remove(false);
	                }
	                chart.redraw();
	                chart.xAxis[0].setCategories( 
						eval("["+xax+"]")			       
					);				
	  				chart.addSeries({
	  					name: "日创建表数",
	            		data: eval("["+strs[1]+"]")
	            	});
	            	
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
   
   		//初始化
  		$(function(){
  			var $time =$("#tomorrowDate").text();
  			$.get("getTableData",{"page":1,"time":$time,"type":"day"},function(data){
            	//alert(data);
            	var strs = new Array();
            	strs = data.split("~");
            	var xx = new Array();
            	xx = strs[0].split(",");
            	var xax ="";
            	$("#tomorrowDate").text(xx[0]);
            	for(var i =0;i< xx.length;i++){
            		if(xx[i]!=""){
            			xax +="\""+xx[i]+"\",";
            		}
            	}
            	var chart = $('#container').highcharts();
                chart.xAxis[0].setCategories( 
                	eval("["+xax+"]")
		        );		
  				chart.addSeries({
  					name: "日创建表数",
            		data: eval("["+strs[1]+"]")
            	});          	
            });
  		});	
   
  		$(function(){			
  			$("#container").highcharts({
		        chart: {
		            type: "line"
		        },
		        title: {
		            text: "日创建表数"
		        },
		        xAxis: {
		        	type :"datetime",
		            categories: ["2015/11/21", "2015/11/22", "2015/11/23", "2015/11/24", "2015/11/25", "2015/11/26", "2015/11/27"]
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
  </script>
</html>
