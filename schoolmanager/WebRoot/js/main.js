// JavaScript Document
 function autoHeight(){
        var iframe = document.getElementById("Iframe");
        if(iframe.Document){//ie自有属性
            iframe.style.height = iframe.Document.documentElement.scrollHeight;
        }else if(iframe.contentDocument){//ie,firefox,chrome,opera,safari
            iframe.height = iframe.contentDocument.body.offsetHeight ;
        }
    }
	
     $(document).ready(function(){
    	 $("#list9").css("background","#72b6eb");
 		 $("#lists9").css("color","#FFFFFF");
     });
	
	$(function(){
		$(".LList").click(function(){
			$(this).css("background","#72b6eb");
			$(this).find("td").css("color","#FFFFFF");
			var LListindex = $(this).index();
			$(".LList").each(function(){
				if($(this).index() != LListindex){
					$(this).css("background","#FFFFFF");
					$(this).find("td").css("color","#222222");
				}
			});
		});
	});
	
	
	function changeiframeH(){
		$("#infinsertR").load(function() {
 		    var clientHeight = $("#infinsertR").contents().find("body").height();
 		    $("#infinsertR").css("height",clientHeight);
 		 });
	}
	//个人信息
	function changeToPersonalInformation(){
	    document.getElementById("infinsertR").src="../personalInformation/personDetail.jsp";
	}
	//举报管理
	function changeToReport(){
	    document.getElementById("infinsertR").src="../report/index.jsp";
	}
	//系统管理员
	function changeToSystemManager(){
	    document.getElementById("infinsertR").src="../sysManager/managerList.jsp";
	}
	//校方管理员
	function changeToSchoolManager(){
	    document.getElementById("infinsertR").src="../schoolManager/existSchool.jsp";
	}
	//数据管理
	function changeToDataManage(){
	    document.getElementById("infinsertR").src="../dataManage/commentManage.jsp";
	}
	//统计分析
	function changeToAnalysis(){
	    document.getElementById("infinsertR").src="../analysis/menu.jsp";
	}
	//反馈留言
	function changeToFeedback(){
	    document.getElementById("infinsertR").src="../feedback/feedbackList.jsp";
	}
	//公告通知
	function changeToBulletin(){
	    document.getElementById("infinsertR").src="../bulletin/bulletinList.jsp";
	}
	