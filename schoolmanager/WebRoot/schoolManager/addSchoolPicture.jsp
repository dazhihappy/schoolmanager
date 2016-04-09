<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  <head>    
    <title>add school picture</title>   
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="../bootstrap/css/bootstrap.min.css">
   	<script type="text/javascript" src="../js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="../bootstrap/js/bootstrap.min.js"></script>   
    <script type="text/javascript" src="../js/jquery.validate.js"></script>
    <script type="text/javascript" src="../js/additional-methods.js"></script>
    <style type="text/css">
    	form{
    		margin-left: 20%;
    	}
    </style>    
  </head>
  
  <body>
    <h2 align="center">上传图片</h2><hr>
    <form action="uploadPic" id="upload" method="post" enctype="multipart/form-data">
	    <h4>
	    	<label for="webPic">上传网站LOGO:</label>
	    	<input type="file" name="webPic" id="webPic" class="required" accept="image/*">
	    </h4>
	    <h4>
	    	<label for="appPic">上传app图片:</label>
	    	<input type="file" name="appPic" id="appPic" class="required" accept="image/*">
	    </h4>
	    <input id="schoolId" name="schoolId" hidden="true" value="${requestScope.schoolId}">
	    <div><button type="submit" class="btn btn-info">确认</button></div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#upload").validate();
    	});
    </script>
  </body>
</html>
