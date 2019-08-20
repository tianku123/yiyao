<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户密码修改</title>
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
<body class="easyui-layout">
	<form name="userPasswordModifyForm" id="userPasswordModifyForm" method="post" style="width:100%; height:100%; background-color:#FFF;">
		<div data-options="region:'center',border:false" style="background-color: #fff; padding:10px;">
	      <input type="hidden" name="reqUrl" id="reqUrl" value="user" /> 
		  <input type="hidden" name="reqMethod" id="reqMethod" value="modifyUserPasswd" />
	      <table cellpadding="0" cellspacing="0" width="100%" class="formTable">
	        <tr>
	          <th style="width:100px;"><label>您的旧密码：</label></th>
	          <td ><input type="password" autofocus class="width180 easyui-validatebox" data-options="required:true" name="oldPasswd" id="oldPasswd"/></td>
	        </tr>
	        <tr>
	          <th><label>输入新密码：</label></th>
	          <td><input type="password" class="width180 easyui-validatebox" data-options="required:true" name="newPasswd" id="newPasswd"/></td>
	        </tr>
	        <tr>
	          <th><label>新密码确认：</label></th>
	          <td><input type="password" class="width180 easyui-validatebox" data-options="required:true" name="comfiPasswd" id="comfiPasswd"/></td>
	        </tr>
	      </table>
	</div>
	<div data-options="region:'south',border:false" style="text-align: right;height: 50px;padding: 5px; background: #f4f4f4;border-top: 1px #ccc solid;">
	        <button class="orangeBtn" onclick="userPasswordVComponent.modifyPasswd();return false;">提交</button><button class="grayBtn" type="reset">重置</button>
	</div>
	    </form>
</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/md5.js"></script>
<script language=javascript>
var ajaxTools = new QM.ajax();
var userPasswordVComponent = {
	formTools : new QM.Form("userPasswordModifyForm"),
	//调用后台方法，获取数据
	modifyPasswd : function()
	{
		this.formTools.submitForm(function()
		{
			var newPasswd = $.trim($("#newPasswd").val());
			var comfiPasswd = $.trim($("#comfiPasswd").val());
			if(newPasswd == '' || comfiPasswd == ''){
				QM.dialog.showFailedDialog("\n\密码不能为空！","");
				return;
			}
			var oldPasswd = $("#oldPasswd").val();
			oldPasswd = hex_md5(oldPasswd).toUpperCase();
			if(oldPasswd != "${admin['fUserPwd']}".toUpperCase()){
				QM.dialog.showFailedDialog("\n\原始密码错误！","");
				return;
			}
			
			if(newPasswd != comfiPasswd){
				QM.dialog.showFailedDialog("\n\n两次密码输入不一致！","");
				return false;
			}
			return true;
		}); 
	 }
}
</script>
</html>
