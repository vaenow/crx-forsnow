<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="css/css_all.css" type="text/css"/>
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<%
	String subDomain = "";
%>

<!--------- 留言版 ------------->
<script>
function flush() {
//	alert("adfadf");
//	setInterval(function fff(){ajax();}, 5000);
ajax();
//	autoAjax();
}
function ajax(){
//	alert("ajax--");
$('#vid_chat_content').html("<div class=message><center>留言数据 加载中...</center></div>");
	$.ajax({
		type : "get",
		url : "<%=request.getContextPath() + subDomain%>/discuss",
		data: {},
		dataType : "html",
		success : function(html){
			autoAjax();
			//document.getElementById("vid_chat_content").innerHTML="";
			document.getElementById("vid_chat_content").innerHTML=html;
			//$('#vid_chat_content').html(html);
			addListener_color();
			addListener_box();
			}
		});
}

function send_msg(){
	var msg_cnt = document.getElementById("msg_").value;
	document.getElementById("msg_").value="";
	if(msg_cnt=="") return null;
	$.ajax({
		type : "post",
		url : "<%=request.getContextPath() + subDomain%>/discuss",
		data: {"msg_cnt": msg_cnt},
		dataType : "html",
		error : function() {alert("请左上角 登录 后留言！\n");},
		success : function(){
			//alert(111);
			//ajax_2();
			}
		});
	}
function reply(name){
	document.getElementById("msg_").value="@"+name+" ";
	$('#msg_').focus();
	//$('#msg_').value(name);
}

var auto;
function autoAjax(){
	var i=5;
	auto = setInterval(function(){
	if(i<1) {
		ajax_2();
	}else{
		$('#autoFresh').attr("value", "刷新 "+i--);
	}
	},1000);
}
function ajax_2(){
	clearInterval(auto);
	$('#autoFresh').attr("value", "刷新 5");	
	ajax();
}
function ajax_3(){
	clearInterval(auto);
	$('#autoFresh').attr("value", "刷新 5");	
}
function addListener_color(){
	$(document).ready(function(){
		$('.message').each(function() {
		$(this).hover(
			function(){
				$(this).css("background", "PowderBlue");
			},
			function(){
				$(this).css("background", "#FFEFD5");
			});
		});
	});
}

/*
function chg(obj){
	obj.style.backgroundColor="PowderBlue";
}
function chg2(obj){
	obj.style.backgroundColor="#FFEFD5";
}

function chg_btn(obj){
	obj.style.backgroundColor="gray";
}
function chg_btn2(obj){
	obj.style.backgroundColor="";
}*/
$(document).ready(function(){
	$('.btns').each(function() {
	$(this).hover(
		function(){
			$(this).css("background", "gray");
			$(this).css("color", "white");
			$(this).css("cursor", "pointer");
		},
		function(){
			$(this).css("background", "white");
			$(this).css("color", "black");
		});
	});
});

/////////////////////////
////------键盘事件------///
////////////////////////
$(document).ready(function(){
	$(document).keydown(function(event){
		if(event.which == 13){
			send_msg();
		}		
	});
});
</script>
<!-- 
/////////////////////////
////------留言提示层-----///
////////////////////////
 -->
<style type="text/css">
<!--
#nav {
	width: 945px;
	background: #9e3323 url(WordPressThemesGallery/nav-bg.gif);
	float: left;
	height: 63px;
	padding: 0px 0px 0px 5px;
}

#top {
	background-image: url(WordPressThemesGallery/header-bg.gif);
	background-position: 0px -7px;
	float: left;
	width: 903px;
	padding: 20px 20px 0px 27px;
}

.link {
	position: relative;
}

.box {
	width: auto;
	height: auto;
	border: 1px solid #333;
	padding: 12px;
	text-align: left;
	position: absolute;
}
-->
</style>
<script type="text/javascript" language="javascript">
 //<div class='box'>test"+i+"</div>
  function addListener_box(){
		$(document).ready(function(){
			var j=0;
			$(".link").each(function(){
				var linkId = "link"+(++j);
				$(this).attr("id", linkId);
				$(this).css("float","left");
				var boxId = '.box';
				$(this).hover(
					function(){
						addBox(linkId);
						//ajax_details();
					},
					function(){
						$(boxId).remove();
						//$("#"+linkId).hover(
						//	function(){
						//		;
						//	},
						//	function(){
						//		$(boxId).remove();
						//});
				});
			});
		});
}

function addBox(linkId){
	var name = $("#"+linkId).html();
	var str_2 = "<div class='box' id='box_"+linkId+"' style='position: absolute;' >@"+name+"_"+linkId+"</div>";
	var str_1 = name;
	//$(linkId).html(str_1+str_2);
	document.getElementById(linkId).innerHTML= str_1 + str_2;
	$('.box').css({
		 color: "white",
		 width: "auto",
		 height: "auto",
		 border: "0px",
		 padding: "3px",
		 "text-align": "left",
		 "background-color": "WindowFrame"});
	name = name.replace(/<[^<>]+>/g, "");	//去掉name字符串中的html标记
	 getDetails("#box_"+linkId, name);
}
function getDetails(box_linkId, name){
	$(box_linkId).html(" 资料加载中...");
	$.ajax({
		type: "get",
		url: "<%=request.getContextPath()%>/info",
		data:{"name": name},
		dataType: "html",
		error: function(){$(box_linkId).html("暂时无法请求资料");},
		success: function(html){
			$(box_linkId).html(html);
			addCss();
		}
	});
}
function addCss(){
	$('.s_item').css({height:"20px", width: "40px", "float":"left", "text-align": "right"});
	$('.s_content').css({width: "55px", "float":"right", "text-align": "left"});
	$('.s_cell').css({height:"20px", width: "100px"});
	$('.s_all').css({width: "110px"});
}
function ajax_details(){
	$.ajax({
		type : "get",
		url : "<%=request.getContextPath() + subDomain%>/discuss",
			data : {},
			dataType : "html",
			success : function(html) {
				//document.getElementById("vid_chat_content").innerHTML=html;
				//$('#vid_chat_content').html(html);
			}
		});
	}
</script>
</head>
<body onload="flush();">
	<div id="vid_chat" style="overflow: hidden;">
		<div class="vid_chat_content" id="vid_chat_content" style="overflow:auto; ">
		<%--
		if(list!=null && !list.isEmpty()){
			for(int i=0; i<list.size(); i++){
				Discuss d = list.get(i); 	
				String name = d.getName();
				String message = d.getCount();
				String dttm = d.getTime().toString(); 
		%>
			<div class=message><a href="#"><%=name %></a>说: <%=message %> <div class=msg_dttm><%=dttm %></div></div>
		<%
			}
		}
		--%>
		</div>
		<div class="vid_chat_bottom">
			<input class=input_message name=msg id=msg_ />&nbsp;<input class='btns' type="button" value="发言" onclick="send_msg()" maxlength=100 />&nbsp;<input id='autoFresh' class='btns' type="button" value="刷新 5" onclick="ajax_2();"/>&nbsp;<input id='stopFresh' class='btns' type="button" value="停止" onclick="ajax_3();"/>
		</div>
	</div>
</body>
</html>