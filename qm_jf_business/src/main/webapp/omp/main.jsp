<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>医药管理系统</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link
	href="${contextPath}/resource/scripts/plugin/easyui/themes/${skin}/easyui.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css"
	rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/css/common.css" rel="stylesheet"
	type="text/css" />

<script type="text/javascript"
	src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/bgImg.js"
	type="text/javascript"></script>
<%
    String userQXMenuStr = (String) request.getSession().getAttribute(
					"userQXMenu");
	if(userQXMenuStr==null){
		userQXMenuStr = "";
	}
%>

<style type="text/css">
	a.tabs-inner{
		width : 100px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	span.tabs-title{
		width : 100px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
</style>
</head>
<body class="easyui-layout">
	<div id="Bg" class="bgImg">
		<img id="Bg_img" src="../resource/images/bg.jpg"> <img
			id="Bg_img2" src="../resource/images/bg.jpg">
	</div>
	<!--end body background image-->
	<div data-options="region:'north',border:false" style="height:30px;" id="container">
		<div class="pageTop">
			<div class="logo">
				<%-- <a href="${contextPath}/omp/main.jsp" title="返回首页"><i></i>商户管理端</a> --%>
			</div>
			<ul class="topmenu">
				<li><a href="javascript:void(0);" class="user" id="username"></a>|</li>
				<!--  
				<li><a href="javascript:void(0);">公告</a>|</li>
				-->
				<li><a onclick="frameComponent.passWordModify();"
					href="javascript:void(0);">密码修改</a>|</li>
				<!-- <li><a href="javascript:void(0);">帮助</a>|</li>-->
				<li><a onclick="frameComponent.logout();"
					href="javascript:void(0);">退出</a></li>
			</ul>
			<br class="cb" />
		</div>
	</div>
	<!--end pageTop main-->
	<div id="leftNav" data-options="region:'west',title:'功能导航',border:false" style="width:200px; min-height:390px; z-index:9999; background:url(${contextPath}/resource/images/leftNav_bottom.gif) no-repeat bottom #f4f4f4; border-bottom:1px solid #D3D3D3;" >
		<div class="Lnav" id="mainMenuDiv" style="background:url(${contextPath}/resource/images/leftNav_bg.gif) repeat-y;">
			<%=userQXMenuStr%>
		</div>
	</div>
	<!--end left main-->
	<div id="homeTab" data-options="region:'center',border:false">
        <div id="tabsId"  fit="true" ></div>
	</div>
	<!--end right main-->
	<!--  
	<div data-options="region:'south',border:false" style="height:35px; background-color:#f4f4f4;">
		<div class="footer" >
			<p class="notice" style="width: 95%"><marquee direction=right></marquee></p>
			<p class="fullScreen">全屏</p>
			<br class="cb">
		</div>
	</div>-->
	<!--end footer main-->
	<!--end container-->
	<div id="win" class="easyui-window" title="Modal Window"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width:1000px;height:600px; z-index:9999;">
		<iframe id="winFrame" src="" style="width:100%;height:100%;"
			marginwidth="0" marginheight="0" scrolling="auto" frameborder="no"></iframe>
	</div>
	<div id="winOther" class="easyui-window" title="Modal Window"
		data-options="modal:true,closed:true,iconCls:'icon-save'"
		style="width:1200px;height:800px;">
		<iframe id="winOtherFrame" src="" style="width:100%;height:100%;"
			marginwidth="0" marginheight="0" scrolling="auto" frameborder="no"></iframe>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script language=javascript>
	
	var ajaxTools = new QM.ajax();
	var frameComponent = {
		init : function() {
			$("#tabsId").tabs({
				plain:true,
				tabHeight:34,
				onSelect : function() {
					//先隐藏所有刷新按钮
					$(".tabs-p-tool").hide();
					//显示当前选择标签的刷新按钮
					$(".tabs-selected .tabs-p-tool").show();
				}
			});
			$("#username").text("${sessionScope.admin.fUserName}");
			//frameComponent.onChangMainWin('订单管理', '${ctx}omp/drug/orderList.jsp');
		},
		onChangMainWin : function(tabName, mainUrl) {
			var objTabs = $("#tabsId");

			if (objTabs.tabs("exists", tabName)) {
				objTabs.tabs("select", tabName);
				var currTabFrame = $('.tabs-panels > .panel:visible > .panel-body > iframe').get(0);
				if(currTabFrame.contentWindow)
				{
				    currTabFrame.contentWindow.location = mainUrl;
				}
				else if(currTabFrame.contentDocument)
				{
				    currTabFrame.contentDocument.location = mainUrl;
				}
			} else {
				if (mainUrl == '') {
					mainUrl = '';
					return;
				} else if (mainUrl.indexOf("?") > -1) {
					mainUrl = mainUrl + "&r=" + Math.random();
				} else {
					mainUrl = mainUrl + "?r=" + Math.random();
				}
				this.onAddTabs(tabName, mainUrl);
			}
			$(".detailColumn").hide();
		},

		onAddTabs : function(strName, strUrl) {
			var objTabs = $("#tabsId");
			if (objTabs.tabs("exists", strName)) {
				objTabs.tabs("select", strName);
			} else {
				var content = '';
				if (strUrl == '') {
				} else {
					content = '<iframe src="' + strUrl + '" ';
					content = content + ' style="width:100%;height:100%;" ';
					content = content
							+ ' marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" ';
					content = content + ' ></iframe> ';
				}
				objTabs.tabs('add', {
					title : strName,
					content : content,
					tools:[
					{
						iconCls:'icon-reload',
						handler:function(){
							var pp = $('#tabsId').tabs('getSelected'); 
							pp.panel("refresh");   
						}
					}],
					selected : true,
					closable : (strName == "首页" ? false : true)
				});
			}
		},

		fullScreen : function() {
			this.toggleHead();
			//if($(".Lnav").is(":visible"))
			//{
			this.toggleNav();
			//}
		},

		toggleHead : function() {
			$(".pageTop").toggle();
		},

		toggleNav : function() {
			$(".Lnav").toggle();
			if ($(".Lnav").is(":visible")) {
				var RC_width = $(document).width() - 200;
				$(".RCont").css('width', RC_width + 'px');
				$(".tabs-header .tabs").css("margin-left", "0px");
			} else {
				$(".RCont").css('width', '100%');
				$(".tabs-header .tabs").css("margin-left", "200px");
			}

			this.resizeTab();
		},

		resizeTab : function() {
			var selectedTab = $('.tabs-selected').text();
			$("#tabsId").tabs({
				width : $("#tabsId").parent().width(),
				height : "auto"
			});
			$("#tabsId").tabs("select", selectedTab);
		},

		//退出登录
		logout : function() {
			QM.dialog.showConfirmDialog("确定退出吗",function(ret) {
				if(ret){
					ajaxTools.singleReq({
						data : {
							"reqUrl" : "login",
							"reqMethod" : "getLogout"
						},
						success : function(ret) {
							if ("0" == ret.a) {
								
									top.location.href = "${ctx}login.jsp";
								
								//去掉右上角的关闭
								$('.panel-tool-close').hide();
							}
						}
					});
				}else {
					return false;
				}
			});
		},
		//密码修改弹出框
		passWordModify : function() {
			QM.dialog.openWin({
				"title" : "密码修改"
			}, "${ctx}omp/admin/user_password_modify.jsp",
					function(ret) {
						if ("0" == ret.a) {
							QM.dialog.showSuccessDialog(ret.c);
							top.location.href = "${ctx}login.jsp";
						} else {
							QM.dialog.showFailedDialog(ret.c);
						}
					});
		}
	};

	$(document).ready(function() {
		$(window).resize(function() {
			frameComponent.resizeTab();
		});
		frameComponent.init();
		
		/*zc*/
		var liname = null;
		var timerLi = null;
		
    	$('.Lnav_list li').hover(function() {
            clearTimeout(timerLi);
            var curr = $(this);
            timerLi = setTimeout(function()
            {     
                $('.Lnav_list li').removeClass(liname);
                $('.Lnav_list li').find('div').hide();
	        	$('.layout-panel-west').css('width','1000px');
				liname = 'IEhack'+ curr.attr("class");
				curr.addClass(liname);
				curr.find('div').show();
				var height = curr.find('div').height();	
				var offset = curr.offset();//获取左侧菜单栏的位置	
				var winheight = $(document.body).height();//可视区域高度
				var button = offset.top + height+50;//菜单底部距离浏览器上方的值
				if(button>winheight) {
					var modifyTop = offset.top - (button - winheight );//如果放不下调整展示菜单栏top位置
				}else {
					var modifyTop = offset.top
				}
				curr.find('div').offset({ top: modifyTop });//调整位置
			}, 2);
	  	}, function() {
	  	    clearTimeout(timerLi);
	  	    var curr = $(this);
            timerLi = setTimeout(function(){
			  	curr.find('div').hide();
	            $('.layout-panel-west').css('width','200px');
		        curr.removeClass(liname);
		   }, 1);
		});
		
		$('.detailColumn').hover(function() {
            clearTimeout(timerLi);
		},function(){
		});
		//
		
		
	});
</script>
</html>