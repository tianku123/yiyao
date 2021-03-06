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
				药品名称： <input type="search" placeholder="药品名称" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				仓库： <input type="search" placeholder="仓库" id="fCityName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				
				<button class="grayBtn"
					onclick="merchantUserInfoComponent.userDataGrid.formQry()">查询</button>
				<button class="grayBtn" onclick="merchantUserInfoComponent.exportA()">导出</button>
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
				"reqUrl" : "drug",
				"reqMethod" : "getList",
				"fName" : fName,
				"fCityName" : fCityName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : true},
				{field : 'fDepartmentName',title : '部门',width : 100,align:'center'}, 
				{field : 'fName',title : '药品名称',width : 100,align:'center'}, 
				{field : 'fSpecification',title : '药品规格',width : 100,align:'center'}, 
				{field : 'fAddress',title : '产地',width :100,align:'center'},
				{field : 'fState',title : '状态',width :100,align:'center',
					formatter: function(value,row,index){
						if(value=='0'){
							return "入库";
						}else if(value=='2'){
							return "当月剩余";
						}else if(value=='3'){
							return "上月剩余";
						}
					}
				},
				{field : 'fNumber',title : '库存',width :100,align:'center'},
				{field : 'fBatchNumber',title : '批号',width :100,align:'center'},
				{field : 'fExpiryDate',title : '效期',width :100,align:'center'},
				{field : 'fPrice',title : '价格',width :100,align:'center'},
				{field : 'fGongyePrice',title : '工业票价',width :100,align:'center'},
				{field : 'fBuyingPrice',title : '税率',width :100,align:'center'},
				{field : 'fWareHouseName',title : '仓库名称',width :100,align:'center'},
				{field : 'fCompanyName',title : '公司',width :100,align:'center'},
				{field : 'fTime',title : '结转时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				{field : 'fBalanceTime',title : '入库时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				}
			];
			var toolbars = {};
			toolbars.btns = [ "ADD", "EDIT" ];
			toolbars.urls = {
				"ADD" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/drug/addDrug.jsp",
				"EDIT" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/drug/editDrug.jsp"
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
				title : "药品库存列表",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				sortOrder : 'asc',
				fitColumns : false,
				columns : [ columns ]
			}, toolbars, this.getQueryParams);
			this.userDataGrid.init();
			$(".datagrid-header-check input").hide();
		},
		getPKConds : function(selRow) {	
			return {
				"queryStr" : "fId=" + selRow.fId
							+"&fName=" + encodeURI(selRow.fName)
							+"&fAddress=" + encodeURI(selRow.fAddress)
							+"&fSpecification=" + selRow.fSpecification
							+"&fBatchNumber=" + selRow.fBatchNumber
							+"&fExpiryDate=" + selRow.fExpiryDate
							+"&fNumber=" + selRow.fNumber
							+"&fPrice=" + selRow.fPrice
							+"&fGongyePrice=" + selRow.fGongyePrice
							+"&fWareHouseId=" + selRow.fWareHouseId
							+"&fDrugOnlyId=" + selRow.fDrugOnlyId
							+"&fBuyingPrice=" + selRow.fBuyingPrice
							+"&fCompanyId=" + selRow.fCompanyId
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
					"reqUrl" : "drug",
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
		},
		exportA : function(){
			
			location.href = "${ctx}system/exportDrug.do";
		}
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
