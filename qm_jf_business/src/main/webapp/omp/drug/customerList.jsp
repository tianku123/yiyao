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
				购货单位： <input type="search" placeholder="购货单位" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				客户区域： <input type="search" placeholder="客户区域" id="fCityName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				
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
			var fName = $("#fUserCode").val();
			var fCityName = $("#fCityName").val();
			return {
				"reqUrl" : "customer",
				"reqMethod" : "getList",
				"fName" : fName,
				"fCityName" : fCityName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : true},
				{field : 'fName',title : '经营者',width : 100,align:'center'}, 
				{field : 'fBirthday',title : '生日',width : 100,align:'center'}, 
				{field : 'fCityName',title : '所属区域',width : 100,align:'center'}, 
				{field : 'fUnit',title : '购货单位',width : 100,align:'center'}, 
				{field : 'fAddress',title : '购货地址',width :100,align:'center'},
				{field : 'fPhone',title : '联系电话',width :100,align:'center'},
				{field : 'fBeginTime',title : '委托书到期日期',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate8(value);
					}
				},
				{field : 'fEndTime',title : '许可证到期日期',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate8(value);
					}
				},
				{field : 'fTime',title : '录入日期',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate8(value);
					}
				},
				{field : 'fExpiryDate',title : '状态',width :100,align:'center',
					formatter: function(value,row,index){
						if(value==2){
							return "<span style='color:gray;'>过期</span>";
						}else if(value==1){
							return "<span style='color:green;'>正在合作</span>";
						}
						
					}
				},
				{field : 'fRemark',title : '备注',width :100,align:'center'}
			];
			var toolbars = {};
			toolbars.btns = [ "ADD", "EDIT" ];
			toolbars.urls = {
				"ADD" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/drug/addCustomer.jsp",
				"EDIT" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/drug/editCustomer.jsp"
			};
			toolbars.newBtns = [
				
 				{iconCls: 'icon-no',text:'删除',handler: function(){
						var selRows = $("#userDataGrid").datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							QM.dialog.showFailedDialog("请选择要删除的数据，只能选取单个数据删除！");
							return false;
						}else{	
							QM.dialog.showConfirmDialog("是否确定删除?", function(flg) {
								if(flg) {
									merchantUserInfoComponent.deleteMerUser(selRows[0].fId);
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
				title : "客户列表",
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
							+"&fName=" + selRow.fName
							+"&fAddress=" + selRow.fAddress
							+"&fUnit=" + selRow.fUnit
							+"&fPhone=" + selRow.fPhone
							+"&fBeginTime=" + formatDate8(selRow.fBeginTime)
							+"&fEndTime=" + formatDate8(selRow.fEndTime)
							+"&fRemark=" + selRow.fRemark
							+"&fCityId=" + selRow.fCityId
							+"&fBirthday=" + selRow.fBirthday
							,
				"queryJson" : {
					"fCode" : selRow.fId
				}
			};
		},
		deleteMerUser : function (fCode) {
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "customer",
					"reqMethod" : "delete",
					"fId" : fCode
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
