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
				业务员： <input type="search" placeholder="姓名" id="fUserRealName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
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
	src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js"></script>
<script>
	var ajaxTools = new QM.ajax();
	var merchantUserInfoComponent = {
		userDataGrid : null,
		getQueryParams : function() {
			var fUserRealName = $("#fUserRealName").val();
			return {
				"reqUrl" : "zgywy",
				"reqMethod" : "getList",
				"fUserName" : fUserRealName,
				"fZhuGuanId" : "${param.fZhuGuanId}"
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'F_ID', align:'center',checkbox : true},
				{field : 'F_USER_NAME', title : '业务员',width :100, align:'center'}, 
				{field : 'F_DEPARTMENT_NAME', title : '部门',width :100, align:'center'}, 
				{field : 'F_TIME',title : '添加时间',width : 120,align:'center',
					formatter : function(value, row, index) {
							return formatDate14(value);
						}
					
				}
			];
			var toolbars = {};
			/* toolbars.btns = [ "ADD"];
			toolbars.urls = {
				"ADD" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/zhuguan/addZgYWY.jsp?fZhuGuanId="+"${param.fZhuGuanId}"
			}; */
			toolbars.newBtns = [
 				{iconCls: 'icon-add',text:'配置',handler: function(){
 						var fZhuGuanId = "${param.fZhuGuanId}";
						if (!fZhuGuanId || fZhuGuanId == 'daquzhuguan' || fZhuGuanId == 'xiaoquzhuguan') {
							QM.dialog.showFailedDialog("请在左侧选择主管！");
							return false;
						}
						var editUrl = "${contextPath}/omp/zhuguan/addZgYWY.jsp?fZhuGuanId="+fZhuGuanId;
							
						var options = {"title":"配置","height":"600"};
						top.QM.dialog.winCallback = function(result)
						{
							if(result && result.a == GLOBAL_INFO.SUCCESS_CODE)
							{
								top.QM.dialog.showSuccessDialog("操作成功.");
								$("#userDataGrid").datagrid('reload');
								$("#userDataGrid").datagrid('clearSelections');
							}
							else
							{
								top.QM.dialog.showFailedDialog("操作失败.");
							}
						};
						top.QM.dialog.openWin(options, editUrl);
				  	 }
				 },
 				{iconCls: 'icon-no',text:'删除',handler: function(){
						var selRows = $("#userDataGrid").datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							QM.dialog.showFailedDialog("请选择要删除的记录，只能选取单条记录删除！");
							return false;
						}else{	
							QM.dialog.showConfirmDialog("是否确定删除?", function(flg) {
								if(flg) {
									merchantUserInfoComponent.deleteMerUser(selRows[0].F_ID);
								}else {
									return false;
								}
							});
						}
				  	 }
				 }
			];
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "业务员列表",
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
					"reqUrl" : "zgywy",
					"reqMethod" : "delete",
					"fId" : fId
				},
				success : function(ret)
				{	
					if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
						QM.dialog.showSuccessDialog("删除成功");
						merchantUserInfoComponent.userDataGrid.formQry();
					}else {
						QM.dialog.showFailedDialog("删除失败");
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
