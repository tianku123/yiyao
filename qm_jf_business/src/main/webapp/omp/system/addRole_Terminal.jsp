<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>医药管理系统</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${contextPath}/resource/css/common.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/scripts/plugin/easyui/themes/${skin}/easyui.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/validate.js"></script>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		queryRoleByCode : {
			// 验证编号
			validator : function(value) {
				var result = true;
				var ajaxTools = new QM.ajax();
				ajaxTools.singleReq({
					async : false,
					data : {
						"reqUrl" : "role",
						"reqMethod" : "queryRoleById",
						"code" : value
					},
					success : function(ret) {
						if (ret.d != null) {
							result = false;
						} else {
							result = true;
						}
					}.bind(this)
				});
				return result;
			},
			message : '该编号已被占用'
		}
	});
</script>
</head>
<body class="easyui-layout">
		<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
			<input type="hidden" id="jbNum" name="jbNum" value=${ param.jbNum } /> <input type="hidden" name="reqUrl" id="reqUrl" value="merchantRoleInfo" />
			<input type="hidden" name="reqMethod" id="reqMethod" value="addRoleInfo" />
			<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
				<tr>
					<th width="15%"><label>角色编码：</label>
					</th>
					<td width="35%"><input id="fRoleCode" name="fRoleCode" type="text" autofocus="autofocus" class="easyui-validatebox"
						data-options="required:true,validType:['num','queryRoleByCode']" /></td>
					<th width="15%"><label>角色名称：</label>
					</th>
					<td width="35%"><input id="fRoleName" name="fRoleName" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
			</table>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
			<button class="orangeBtn" onclick="merchantRoleInfoComponent.comit();return false;">提交</button><button class="grayBtn" onclick="merchantRoleInfoComponent.close()">关闭</button>
		</div>
</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js"></script>
<script>
	var ajaxTools = new QM.ajax();
	var merchantRoleInfoComponent = {
		comit : function() {
			//获取参数
			var fRoleCode = $("#fRoleCode").val();
			var fRoleName = $("#fRoleName").val();
			
			var result = $("#fRoleCode").validatebox("isValid");
			if(result==false) {
	            return false;
			}
			var result = $("#fRoleName").validatebox("isValid");
			if(result==false || $.trim(fRoleName)=='') {
				QM.dialog.showFailedDialog("角色名称不能为空！");
	            return false;
			}
			
			ajaxTools.singleReq({
				data : {
					"reqUrl" : "role",
					"reqMethod" : "addRole",
					"fRoleCode" : fRoleCode,
					"fRoleName" : fRoleName,
					"fRoleType" : 1
				},
				success : function(ret) {
					if (ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
						QM.dialog.doWinCallback(true, ret);
					} else {
						QM.dialog.doWinCallback(false, ret);
					}
					
				}
			});
		},
		close : function() {
			QM.dialog.closeWin();
			
		}
	}
</script>
</html>
