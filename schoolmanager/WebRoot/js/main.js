// JavaScript Document
 function autoHeight(){
        var iframe = document.getElementById("Iframe");
        if(iframe.Document){//ie��������
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
	//������Ϣ
	function changeToPersonalInformation(){
	    document.getElementById("infinsertR").src="../personalInformation/personDetail.jsp";
	}
	//�ٱ�����
	function changeToReport(){
	    document.getElementById("infinsertR").src="../report/index.jsp";
	}
	//ϵͳ����Ա
	function changeToSystemManager(){
	    document.getElementById("infinsertR").src="../sysManager/managerList.jsp";
	}
	//У������Ա
	function changeToSchoolManager(){
	    document.getElementById("infinsertR").src="../schoolManager/existSchool.jsp";
	}
	//���ݹ���
	function changeToDataManage(){
	    document.getElementById("infinsertR").src="../dataManage/commentManage.jsp";
	}
	//ͳ�Ʒ���
	function changeToAnalysis(){
	    document.getElementById("infinsertR").src="../analysis/menu.jsp";
	}
	//��������
	function changeToFeedback(){
	    document.getElementById("infinsertR").src="../feedback/feedbackList.jsp";
	}
	//����֪ͨ
	function changeToBulletin(){
	    document.getElementById("infinsertR").src="../bulletin/bulletinList.jsp";
	}
	