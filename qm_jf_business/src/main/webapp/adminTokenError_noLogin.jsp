<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户未登陆或登陆超时</title>
	<meta content="" name="keywords" />
	<meta content="" name="description" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="${contextPath}/resource/css/common.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/resource/scripts/plugin/easyui/themes/${skin}/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div class="Rmain" align="center">
		对不起，您当前为登陆或登陆超时，请重新登陆。
		<br/>
		点击  <a href="login.jsp">跳转登陆页面>></a> 自动跳转至登陆页面,或者页面 <span id="time_go">3</span> 秒钟后自动跳转
	</div>
</body>
<script language=javascript>
	function delayURL(url) {
		var delay=$("#time_go").html();
		//最后的innerHTML不能丢，否则delay为一个对象
		if(delay > 0){
			delay--;
			$("#time_go").html(delay);
		}else{
			window.top.location.href=url;
		}
		setTimeout("delayURL('" + url + "')", 1000);
		//此处1000毫秒即每一秒跳转一次
	}; 
	//初始化方法
	$(document).ready(function(){
		delayURL("login.jsp");
	});
</script>
</html>
