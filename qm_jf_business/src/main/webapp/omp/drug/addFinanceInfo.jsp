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
<link rel="stylesheet" href="${contextPath}/resource/scripts/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/validate.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		queryByCode : {
			// 验证编号
			validator : function(value) {
				var result = true;
				var ajaxTools = new QM.ajax();
				ajaxTools.singleReq({
					async : false,
					data : {
						"reqUrl" : "cardType",
						"reqMethod" : "queryBeanById",
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
			message : '该编号已被使用'
		}
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
		<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
			<tr>
				<th width="20%">
					<label>付款情况：</label>
				</th>
				<td width="30%">
					<select id="fPaymentState">
						<option value="1">已付款</option>
						<option value="0">借款</option>
						<option value="3">扫码付(信)</option>
						<option value="4">扫码付(邮)</option>
						<option value="5">直营借款</option>
					</select>
				</td>
				<th width="20%">
					<label>付款方式：</label>
				</th>
				<td width="30%">
					<select id="fPaymentSource">
						<option value="0">对公账号</option>
						<option value="1">对私</option>
						<option value="2">其他</option>
					</select>
				</td>
			</tr>
			
			<tr>
				<th width="20%">
					<label>备注：</label>
				</th>
				<td width="80%" colspan="3">
					<textarea id="fFinanceIntro" name="fFinanceIntro" rows="10" cols="80"></textarea>
				</td>
				
			</tr>
		</table>
	</div>

	<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
		<button class="orangeBtn" onclick="merchantUserInfoComponent.comit();return false;">提交</button><button class="grayBtn" onclick="merchantUserInfoComponent.close()">关闭</button>
	</div>

</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}?v=${js_version}"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script>
	var ajaxTools = new QM.ajax();
	//管理门店编码，以.分割

	var merchantUserInfoComponent = {
		init : function() {
			
		},
		comit : function() {
			var fPaymentState = $("#fPaymentState").val();
			var fPaymentSource = $("#fPaymentSource").val();
			var fFinanceIntro = $("#fFinanceIntro").val();
			if (!fPaymentState) {
				top.QM.dialog.showFailedDialog("请选择付款情况！");
			}
			if (fPaymentState == 0) {
				fPaymentSource = '';
			}
			ajaxTools.singleReq({
				data : {
					"reqUrl" : "order",
					"reqMethod" : "edit",
					"fId" : "${param.fId}",
					"fState" : "${param.fState}",
					"fFinanceIntro" : fFinanceIntro,
					"fPaymentSource" : fPaymentSource,
					"fPaymentState" : fPaymentState
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
		$("#fPaymentState").change(function(){
			var val = $(this).val();
			// 已付款
			if (val == 1) {
				$("#fPaymentSource").attr({disabled: false});
			} else {
				$("#fPaymentSource").attr({disabled: true});
			}
		});
		merchantUserInfoComponent.init();
	});
</script>
</html>
