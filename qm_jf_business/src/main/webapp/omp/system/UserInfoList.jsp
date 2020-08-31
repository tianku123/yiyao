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
</head>
<body class="easyui-layout" id="filter">
		<div data-options="region:'north',border:false" style="height:77px;">
		
		  <div class="searchColumn">
			<div class="keySearch">
				用户编码： <input type="search" placeholder="用户编码" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				姓名： <input type="search" placeholder="姓名" id="fUserRealName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				<button class="grayBtn"
					onclick="merchantUserInfoComponent.userDataGrid.formQry()">查询</button>
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
	var merchantUserInfoComponent = {
		userDataGrid : null,
		getQueryParams : function() {
			var fUserCode = $("#fUserCode").val();
			var fUserRealName = $("#fUserRealName").val();
			return {
				"reqUrl" : "user",
				"reqMethod" : "queryUserList",
				"fUserCode" : fUserCode,
				"fUserName" : fUserRealName,
				"roleCode" : "${param.roleCode}"
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'F_ID', align:'center',checkbox : true},
				{field : 'F_USER_CODE',title : '登录名',width : 50,sortable : true,align:'center'}, 
				{field : 'F_USER_NAME',title : '姓名',width :100,align:'center'}, 
				{field : 'F_ROLE_NAME',title : '所属角色',width : 100,align:'center'}, 
				{field : 'F_CITY_NAME',title : '区域',width : 100,align:'center'}, 
				{field : 'F_DEPARTMENT_NAME',title : '部门',width : 100,align:'center'}, 
				{field : 'F_USER_ROLE_REL',title : '所属角色编码',hidden:true}, 
				{field : 'F_USER_TEL',title : '联系电话',width : 120,align:'center'},
				{field : 'F_USER_CREATTIME',title : '添加时间',width : 120,align:'center',
					formatter : function(value, row, index) {
							return formatDate14(value);
						}
					
				}
			];
			var toolbars = {};
			toolbars.btns = [ "ADD", "EDIT" ];
			toolbars.urls = {
				"ADD" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/system/addUser.jsp?roleCode="+"${param.roleCode}",
				"EDIT" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/system/editUser.jsp"
			};
			toolbars.newBtns = [
 				{iconCls: 'icon-no',text:'删除',handler: function(){
						var selRows = $("#userDataGrid").datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							QM.dialog.showFailedDialog("请选择要删除的用户，只能选取单个用户删除！");
							return false;
						}else{	
							QM.dialog.showConfirmDialog("是否确定删除?", function(flg) {
								if(flg) {
									if("${sessionScope.admin.fUserCode}"==selRows[0].F_USER_CODE) {
										QM.dialog.showFailedDialog("不能删除当前登录用户");
									}else if(selRows[0].F_USER_CODE=="admin") {
										QM.dialog.showFailedDialog("不能删除系统管理员");
									}else {
										merchantUserInfoComponent.deleteMerUser(selRows[0].F_ID);
									}
								}else {
									return false;
								}
							});
						}
				  	 }
				 }	,	
 				{iconCls: 'icon-reload',text:'密码重置',handler: function(){
						var selRows = $("#userDataGrid").datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							QM.dialog.showFailedDialog("请选择用户，只能选取单个用户！");
							return false;
						}else{	
							merchantUserInfoComponent.modifyPassword(selRows[0].F_ID);
						
						}
				  	 }
				 }	
			];
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "用户列表",
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
				"queryStr" : "fUserCode=" + selRow.F_USER_CODE+"&fUserName="
							+encodeURI(selRow.F_USER_NAME)+"&fUserTel="
							+(selRow.F_USER_TEL?selRow.F_USER_TEL:"")+"&roleCode="
							+selRow.F_USER_ROLE_REL
							+"&roleName="+selRow.F_ROLE_NAME
							+"&fId="+selRow.F_ID
							+"&fCityId="+selRow.F_CITY_ID
							+"&fDepartmentId="+selRow.F_DEPARTMENT_ID
							+"&fUserPwd="+selRow.F_USER_PWD,
				"queryJson" : {
					"fUserCode" : selRow.F_USER_CODE
				}
			};
		},
		deleteMerUser : function (fId) {
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "user",
					"reqMethod" : "deleteUser",
					"fId" : fId
				},
				success : function(ret)
				{	
					if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
						if(ret.d&&ret.d=="1") {
							QM.dialog.showFailedDialog("该用户为超级管理员,不能删除");
						}else {
							QM.dialog.showSuccessDialog("删除成功");
							merchantUserInfoComponent.userDataGrid.formQry();
						}
					}else {
						QM.dialog.showFailedDialog("删除失败");
					}
				}
			});
		},
		modifyPassword : function (fId) {
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "user",
					"reqMethod" : "modifyPassword",
					"fId" : fId
				},
				success : function(ret)
				{	
					if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
						QM.dialog.showSuccessDialog("重置成功，当前密码为：123456");
						merchantUserInfoComponent.userDataGrid.formQry();
					}else {
						QM.dialog.showFailedDialog("重置失败");
					}
				}
			});
		}
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
