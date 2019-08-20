<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
<script type="text/javascript"
	src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/My97DatePicker/WdatePicker.js"></script>
</head>
<body class="easyui-layout" id="filter">
		<div data-options="region:'north',border:false" style="height:77px;">
		
		  <div class="searchColumn">
			<div class="keySearch">
				登录名： <input type="search" placeholder="登录名" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				 <input type="hidden" placeholder="用户名" id="fUserName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				日期：<input id="beginTime" name="beginTime" type="text" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',startDate:'%y-%M-%d 08:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" style="width:150px;"/>   
						至 
						<input id="endTime" name="endTime" type="text" class="Wdate"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',startDate:'%y-%M-%d 08:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" style="width:150px;"/>   
				<button class="grayBtn"
					onclick="merchantUserInfoComponent.userDataGrid.formQry()">查询</button>
				<button class="grayBtn"
					onclick="merchantUserInfoComponent.reset()">重置</button>
				<button class="grayBtn"
					onclick="merchantUserInfoComponent.clearLog()">清除记录</button>
			</div>
			<p class="shrink" onClick="resizeNorth();">收起搜索栏</p>
		  </div>
		</div>
		<div data-options="region:'center',border:false">
			<table id="userDataGrid">
			</table>
		</div>
</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script>
	var ajaxTools = new QM.ajax();
	
	//覆盖，目的：增加验证信息
	QM.dataGrid.prototype.formQry = function()
		{
			if($('#beginTime').val()!='' ){
				if($('#endTime').val()==''){
					QM.dialog.showFailedDialog("日期不能为空！");
					return;
				}
			}
			if($('#endTime').val()!='' ){
				if($('#beginTime').val()==''){
					QM.dialog.showFailedDialog("日期不能为空！");
					return;
				}
			}
			this.queryParam = this.qFn ? this.qFn() : {};
			$("#userDataGrid").datagrid('load', this.queryParam);
		};
	//覆盖end
	
	var merchantUserInfoComponent = {
		userDataGrid : null,
		getQueryParams : function() {
			var fUserCode = $.trim($("#fUserCode").val());
			var fUserName = $.trim($("#fUserName").val());
			var beginTime = $.trim($('#beginTime').val());
			var endTime = $.trim($('#endTime').val());
			return {
				"reqUrl" : "system",
				"reqMethod" : "queryLogList",
				"fUserCode" : fUserCode,
				"fUserName" : fUserName,
				"beginTime" : beginTime,
				"endTime" : endTime
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'F_ID',title : '日志编码',width : 50,sortable : true,align:'center',hidden:true}, 
				{field : 'F_USERCODE',title : '登录名',width :100,align:'center'}, 
				{field : 'F_USER_NAME',title : '用户名',width :100,align:'center'}, 
				{field : 'F_IP',title : 'IP地址',width : 100,align:'center'}, 
				{field : 'F_MENU',title : '操作菜单',width : 100,align:'center'}, 
				{field : 'F_DESCRIPTION',title : '操作描述',width : 100,align:'center'}, 
				{field : 'F_TIME',title : '操作时间',width : 120,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				}
			];
			var toolbars = {};
			
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "日志列表",
				singleSelect : false,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				sortOrder : 'asc',
				columns : [ columns ]
			}, toolbars, this.getQueryParams);
			this.userDataGrid.init();
			$(".datagrid-header-check input").hide();
		},
		clearLog : function(){
			var fUserCode = $.trim($("#fUserCode").val());
			var fUserName = $.trim($("#fUserName").val());
			var beginTime = $.trim($('#beginTime').val());
			var endTime = $.trim($('#endTime').val());
			if($.trim(fUserCode)=='' && $.trim(fUserName)=='' && $.trim(beginTime)=='' && $.trim(endTime)==''){
				top.QM.dialog.showConfirmDialog("清除后将无法恢复，确定清空所有日志记录吗？",function(r){
						if(r){
							merchantUserInfoComponent.clearFun(fUserCode,fUserName,beginTime,endTime);
						}
				});
			}else{
				if($('#beginTime').val()!='' ){
					if($('#endTime').val()==''){
						QM.dialog.showFailedDialog("日期不能为空！");
						return;
					}
				}
				if($('#endTime').val()!='' ){
					if($('#beginTime').val()==''){
						QM.dialog.showFailedDialog("日期不能为空！");
						return;
					}
				}
				top.QM.dialog.showConfirmDialog("清除后将无法恢复，确定清除查询后的日志记录吗？",function(r){
						if(r){
							merchantUserInfoComponent.clearFun(fUserCode,fUserName,beginTime,endTime);
						}
				});
			}
		},
		clearFun : function(fUserCode,fUserName,beginTime,endTime){
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl"      : "system",
					"reqMethod"   : "deleteLog",
					"fUserCode"    :  fUserCode,
					"fUserName"    :  fUserName,
					"beginTime"    :  beginTime,
					"endTime"    :  endTime
				},
				success : function(ret)
				{
					if(ret.a == GLOBAL_INFO.SUCCESS_CODE)
					{
						QM.dialog.showSuccessDialog("操作成功！");
						merchantUserInfoComponent.userDataGrid.formQry();
					}
					else
					{
						QM.dialog.showFailedDialog("操作失败！");
					}
				}
			});
		},
		reset : function(){
			$("#fUserName").val('');
			$("#beginTime").val('');
			$("#endTime").val('');
		}
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
