<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>医药管理系统</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${contextPath}/resource/css/common.css" rel="stylesheet"
	type="text/css" />
<link
	href="${contextPath}/resource/scripts/plugin/easyui/themes/${skin}/easyui.css"
	rel="stylesheet" type="text/css" />
<link
	href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${contextPath}/resource/scripts/zTree_v3/css/zTreeStyle/zTreeStyle.css"
	type="text/css">
<script type="text/javascript"
	src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/validate.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false"
		style="background-color:#FFF; padding:10px;">
		<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
			<tr>
				<th width="20%"><label>发货类型：</label></th>
				<td width="20%"><select id="fahuoType">
						<option value="0">物流</option>
						<option value="1">自提</option>
						<option value="2">快递</option>
				</select></td>

				<th width="15%"><label>物流公司：</label></th>
				<td width="45%"><input id="wuliuName" name="wuliuName" type="text"
					autofocus="autofocus" class="easyui-validatebox"
					maxlength="50" /></td>
			</tr>
			<tr>
				<th width="20%"><label>备注：</label></th>
				<td width="80%" colspan=3><textarea id="fSaleIntro" name="fSaleIntro"
						rows="10" cols="80"></textarea></td>

			</tr>
		</table>
	</div>

	<div data-options="region:'south',border:false"
		style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
		<button class="orangeBtn"
			onclick="merchantUserInfoComponent.comit();return false;">提交</button>
		<button class="grayBtn" onclick="merchantUserInfoComponent.close()">关闭</button>
	</div>

</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js"></script>
<script>
	var ajaxTools = new QM.ajax();
	//管理门店编码，以.分割

	var merchantUserInfoComponent = {
		init : function() {

		},
		comit : function() {
			var fSaleIntro = $("#fSaleIntro").val();
			var fahuoType = $("#fahuoType").val();
			var wuliuName = $("#wuliuName").val();

			ajaxTools.singleReq({
				data : {
					"reqUrl" : "order",
					"reqMethod" : "commit2Finance",
					"fId" : "${param.fId}",
					"fState" : "${param.fState}",// 1、正常报单，10、政策报单
					"fSaleIntro" : fSaleIntro,
					"fSendType" : fahuoType, // 发货方式
					"fLogistics" : wuliuName // 物流公司名称
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
	$(function() {
		$("#fahuoType").change(function(){
			console.log($(this).val())
			if ($(this).val() != 0) {
				$("#wuliuName").attr("disabled", true);
			} else {
				$("#wuliuName").attr("disabled", false);
			}
		});
		merchantUserInfoComponent.init();
	});
</script>
</html>
