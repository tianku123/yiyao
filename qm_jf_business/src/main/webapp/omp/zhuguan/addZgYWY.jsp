<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>医药库存管理</title>
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
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false" style="height:40px;">
		<div style="margin-left:20px;margin-top:3px;"> 
			名称： <input type="search" placeholder="名称" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
			<button class="grayBtn"
				onclick="merchantUserInfoComponent.userDataGrid.formQry()">查询</button>
		</div>
		
	</div>
	<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'north',border:false"  style="height:420px;">
				<div style="width:100%;height:100%;">
					<table id="userDataGrid"></table>  
				</div>
			</div>
			<div data-options="region:'center',border:false" style="height:40px;">
				<div id="department" style="padding-top:10px; padding-left:10px;"></div>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
		<button class="orangeBtn" onclick="merchantUserInfoComponent.commit();return false;">新增</button><button class="grayBtn" onclick="merchantUserInfoComponent.close();">关闭</button>
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
			var fUserCode = $("#fUserCode").val();
			return {
				"reqUrl" : "user",
				"reqMethod" : "queryYWYList",
				"fUserCode" : fUserCode,
				"fZhuGuanId" : "${param.fZhuGuanId}"
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'F_ID', align:'center',checkbox : true},
				{field : 'F_USER_CODE',title : '登录名',width : 50,sortable : true,align:'center'}, 
				{field : 'F_USER_NAME',title : '姓名',width :100,align:'center'}, 
				{field : 'F_ROLE_NAME',title : '所属角色',width : 100,align:'center'}, 
				{field : 'F_CITY_NAME',title : '区域',width : 100,align:'center'}, 
				{field : 'F_USER_ROLE_REL',title : '所属角色编码',hidden:true}, 
				{field : 'F_USER_TEL',title : '联系电话',width : 120,align:'center'},
				{field : 'F_USER_CREATTIME',title : '添加时间',width : 120,align:'center',
					formatter : function(value, row, index) {
							return formatDate14(value);
						}
					
				}
			];
			var toolbars = {};
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "用户列表",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				sortOrder : 'asc',
				columns : [ columns ],
				onClickRow: function(index, data) {
					merchantUserInfoComponent.onClickRow(data['F_ID']);
				},
			}, toolbars, this.getQueryParams);
			this.userDataGrid.init();
			$(".datagrid-header-check input").hide();
		},
		onClickRow : function(fId) {
			// 根据业务员获取业务员部门
			ajaxTools.singleReq({
					data : {
						"reqUrl" : "department",
						"reqMethod" : "getDepartmentByUserId",
						"userId" : fId
					},
					success : function(ret) {
						if (ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
							if (ret.d && ret.d.length>0) {
								var html = "";
								var obj;
								for (var i = 0; i < ret.d.length; i++) {
									obj = ret.d[i];
									html += '<label><input type="checkbox" name="department" value="' + obj['F_DEPARTMENT_ID'] + '">' + obj['F_NAME'] + '</label>';
								}
								$("#department").empty().append(html);
							} else {
								$("#department").empty().append("无部门");
							}
						} else {
								$("#department").empty().append("失败");
						}
						
					}
				});
		},
		commit : function(){
			var _arr = $('#userDataGrid').datagrid('getChecked');
			//开票价大于单价
			if(_arr == 'undefined' || _arr.length<=0){
				QM.dialog.showFailedDialog("请选择要添加的记录！");
			}else{
				// 业务员ids start
				var fYWYIds = '';
				for (var i = 0; i < _arr.length; i++) {
					fYWYIds += _arr[i]['F_ID'];
					if (i != _arr.length - 1) {
						fYWYIds += ",";
					}
				}
				var checks = $("input[name=department]:checked");
				var departments = '';
				if (checks.length == 0) {
					QM.dialog.showFailedDialog("请选择部门！");
					return;
				} else {
					for (var i = 0; i < checks.length; i++) {
						departments += checks[i]['value'];
						if (i != checks.length - 1) {
							departments += ",";
						}
					}
				}
				// 业务员ids end
				ajaxTools.singleReq({
					data : {
						"reqUrl" : "zgywy",
						"reqMethod" : "save",
						"fYWYIds" : fYWYIds,
						"departments" : departments,
						"fZhuGuanId" : "${param.fZhuGuanId}"
					},
					success : function(ret) {
						if (ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
							QM.dialog.doWinCallback(true, ret);
						} else {
							QM.dialog.doWinCallback(false, ret);
						}
						
					}
				});
			}
		},
		close : function(){
	       QM.dialog.closeWin();
		}
	}
	$(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
