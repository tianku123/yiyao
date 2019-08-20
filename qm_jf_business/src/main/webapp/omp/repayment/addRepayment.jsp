<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<script type="text/javascript" src="${contextPath}/resource/scripts/My97DatePicker/WdatePicker.js"></script>
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
<%
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String now = format.format(new Date());
			request.setAttribute("now", now);
		 %>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:220px;">
		<table id="userDataGrid">
		</table>
	</div>
	<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
		<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
			<tr>
			
				<th width="20%">
					<label>结清：</label>
				</th>
				<td width="30%">
					<select id="fPayOff">
						<option value="0">未结清</option>
						<option value="1">结清</option>
					</select>
				</td>
				<th width="20%">
					<label>还款金额：</label>
				</th>
				<td width="30%">
						<input id="fMoney" name="fMoney" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" />
				</td>
			</tr>
			<tr>
			
				<th width="20%">
					<label>还款方式：</label>
				</th>
				<td width="30%">
					<select id="fSource">
						<option value="0">对公账号</option>
						<option value="1">对私</option>
						<option value="2">其他</option>
					</select>
				</td>
				<th width="20%">
					<label>还款日期：</label>
				</th>
				<td width="30%">
					<input id="fTime" name="fTime" type="text" class="Wdate"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})" style="width:150px;"/>   
				</td>
			</tr>
			<tr>
				<th width="20%">
					<label>备注：</label>
				</th>
				<td width="80%" colspan="3">
					<textarea id="fIntro" name="fIntro" rows="3" cols="80"></textarea>
				</td>
				
			</tr>
		</table>
	</div>

	<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
		<button class="orangeBtn" onclick="merchantUserInfoComponent.comit();return false;">提交</button><button class="grayBtn" onclick="merchantUserInfoComponent.close()">关闭</button>
	</div>

</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script>
	var ajaxTools = new QM.ajax();
	//管理门店编码，以.分割

	var merchantUserInfoComponent = {
		userDataGrid : null,
		getQueryParams : function() {
			return {
				"reqUrl" : "repayment",
				"reqMethod" : "getList",
				"fOrderId" : "${param.fId}"
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : true},
				{field : 'fMoney',title : '还款金额',width :100,align:'center'}, 
				{field : 'fSource',title : '还款方式',width :100,align:'center',
					formatter: function(value,row,index){
						if(value=='0'){
							return "对公账号";
						}else if(value=='1'){
							return "对私";
						}else if(value=='2'){
							return "其他";
						}
					}
				}, 
				{field : 'fIntro',title : '备注',width :100,align:'center'}, 
				{field : 'fTime',title : '还款时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				}
			];
			var toolbars = {};
			toolbars.newBtns = [
				
			];
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "还款记录",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				sortOrder : 'asc',
				columns : [ columns ]
			}, toolbars, this.getQueryParams);
			this.userDataGrid.init();
			$(".datagrid-header-check input").hide();
		},
		getPKConds : function(selRow) {	
			return {
				"queryStr" : "fId=" + selRow.fId
							+"&fMoney=" + selRow.fMoney
							+"&fSource=" + selRow.fSource
							+"&fIntro=" + selRow.fIntro
							,
				"queryJson" : {
					"fCode" : selRow.fId
				}
			};
		},
		comit : function() {
			var fIntro = $("#fIntro").val();
			var fTime = $("#fTime").val();
			var fSource = $("#fSource").val();
			var fMoney = $("#fMoney").val();
			var fPayOff = $("#fPayOff").val();
			
			if(!$("#fMoney").validatebox('isValid')){
				return;
			}
			ajaxTools.singleReq({
				data : {
					"reqUrl" : "repayment",
					"reqMethod" : "save",
					"fOrderId" : "${param.fId}",
					"fPayOff" : fPayOff,
					"fIntro" : fIntro,
					"fTime" : fTime,
					"fSource" : fSource,
					"fMoney" : fMoney
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
		$("#fTime").val("${now}");
		merchantUserInfoComponent.init();
	});
</script>
</html>
