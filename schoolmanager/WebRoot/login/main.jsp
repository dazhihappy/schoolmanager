<%@ page language="java" import="java.util.*,com.action.*" pageEncoding="utf-8" contentType="text/html; charset=utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>校园时光-后台管理</title>
<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
<link rel="stylesheet" href="../css/main.css" />
<script src="../js/main.js"></script>

</head>

<%
	String userName =  (String)session.getAttribute("userName");
 %>

<body onload="initlistscolor()">

<div id="inftop"></div>
<div id="infmid">
<div id="infheadL">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="20%" align="center"><img src="mainimg/logo_cut.png" width="50px" height="50px"/></td>
      <td width="80%"><span style="font-size:26px;;font-weight:bold;color:#333333;">校园时光后台管理系统</span></td>
    </tr>
  </table>
</div>
<div id="infheadR">
  <table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="55%" align="left"><span style="font-size:14px;color:#000000; font-weight:bold;">欢迎您，<%=userName%></span></td>
      <td width="20%" align="center"><span style="font-size:14px;color:#72b6eb;"><a href="login.jsp">退 出</a></span></td>
    </tr>
  </table>
</div>
</div>

<div id="inflay1">
</div>
<div id="infcontent1">
    <div id="infcontent2">
	    <div id="infinsertL">
	      <table width="100%" height="70%" border="0" cellspacing="0" cellpadding="0">
            <tr>
			  <td>&nbsp;&nbsp;</td>
              <td class="insertLtL" align="center"><img src="mainimg/gongneng.jpg" width="50%" height="50%"/></td>
              <td class="insertLtRT">功&nbsp;&nbsp;能</td>
            </tr>
            <tr  style="cursor:pointer;" class="LList">
			<td>&nbsp;&nbsp;</td>
              <td class="insertLtL">&nbsp;</td>
              <td class="insertLtRC" onclick="changeToPersonalInformation();">个人信息</td>
            </tr>
            <tr style="cursor:pointer;"  class="LList">
			<td>&nbsp;&nbsp;</td>
              <td class="insertLtL">&nbsp;</td>
              <td class="insertLtRC"  onclick="changeToSystemManager();">系统管理员</td>
            </tr>
			<tr style="cursor:pointer;"  class="LList">
			<td>&nbsp;&nbsp;</td>
              <td class="insertLtL">&nbsp;</td>
              <td class="insertLtRC"  onclick="changeToSchoolManager();">校方管理员</td>
            </tr>
         	<tr style="cursor:pointer;"  class="LList">
			<td>&nbsp;&nbsp;</td>
              <td class="insertLtL">&nbsp;</td>
              <td class="insertLtRC"  onclick="changeToDataManage();">数据管理</td>
            </tr>
            <tr style="cursor:pointer;"  class="LList">
			<td>&nbsp;&nbsp;</td>
              <td class="insertLtL">&nbsp;</td>
              <td class="insertLtRC"  onclick="changeToAnalysis();">统计分析</td>
            </tr>
            
            <tr style="cursor:pointer;"  class="LList">
			<td>&nbsp;&nbsp;</td>
              <td class="insertLtL">&nbsp;</td>
              <td class="insertLtRC"  onclick="changeToFeedback();">反馈留言</td>
            </tr>
            <tr style="cursor:pointer;"  class="LList">
			<td>&nbsp;&nbsp;</td>
              <td class="insertLtL">&nbsp;</td>
              <td class="insertLtRC"  onclick="changeToBulletin();">公告通知</td>
            </tr>
            
          </table>
		  
	    </div>
		<div>
		    <iframe src="../analysis/menu.jsp" id="infinsertR" frameborder="0" width="100%" height="98%" onload="autoHeight();" scrolling="auto"></iframe>
		</div>
	</div>
</div>

</body>
</html>