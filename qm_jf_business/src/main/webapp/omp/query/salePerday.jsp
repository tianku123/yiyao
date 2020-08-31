<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
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
<%
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
			String now = format.format(new Date());
			request.setAttribute("now", now);
		 %>
</head>
<body class="easyui-layout" id="filter">
		<div data-options="region:'north',border:false" style="height:77px;">
		
		  <div class="searchColumn">
			<div class="keySearch">
				业务员： <input type="search" placeholder="业务员" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				日期：<input id="beginTime" name="beginTime" type="text" class="Wdate"  onFocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" style="width:150px;"/>   
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
			var beginTime = $('#beginTime').val();
			return {
				"reqUrl" : "order",
				"reqMethod" : "salePerday",
				"beginTime" : beginTime,
				"fName" : fName
			}
		},	
		init : function() {
			
			var columns = [ 
				
				{field : 'fUserName',title : '业务员',width : 100,align:'center'}, 
				{field : '01',title : '01',width : 100,align:'center'},
				{field : '02',title : '02',width : 100,align:'center'},
				{field : '03',title : '03',width : 100,align:'center'},
				{field : '04',title : '04',width : 100,align:'center'},
				{field : '05',title : '05',width : 100,align:'center'},
				{field : '06',title : '06',width : 100,align:'center'},
				{field : '07',title : '07',width : 100,align:'center'},
				{field : '08',title : '08',width : 100,align:'center'},
				{field : '09',title : '09',width : 100,align:'center'},
				{field : '10',title : '10',width : 100,align:'center'},
				{field : '11',title : '11',width : 100,align:'center'},
				{field : '12',title : '12',width : 100,align:'center'},
				{field : '13',title : '13',width : 100,align:'center'},
				{field : '14',title : '14',width : 100,align:'center'},
				{field : '15',title : '15',width : 100,align:'center'},
				{field : '16',title : '16',width : 100,align:'center'},
				{field : '17',title : '17',width : 100,align:'center'},
				{field : '18',title : '18',width : 100,align:'center'},
				{field : '19',title : '19',width : 100,align:'center'},
				{field : '20',title : '20',width : 100,align:'center'},
				{field : '21',title : '21',width : 100,align:'center'},
				{field : '22',title : '22',width : 100,align:'center'},
				{field : '23',title : '23',width : 100,align:'center'},
				{field : '24',title : '24',width : 100,align:'center'},
				{field : '25',title : '25',width : 100,align:'center'},
				{field : '26',title : '26',width : 100,align:'center'},
				{field : '27',title : '27',width : 100,align:'center'},
				{field : '28',title : '28',width : 100,align:'center'},
				{field : '29',title : '29',width : 100,align:'center'},
				{field : '30',title : '30',width : 100,align:'center'},
				{field : '31',title : '31',width : 100,align:'center'}
			];
			var toolbars = {};
			
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "药品库存列表",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				pagination : false,
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
							+"&fWareHouseId=" + selRow.fWareHouseId
							+"&fDrugOnlyId=" + selRow.fDrugOnlyId
							+"&fBuyingPrice=" + selRow.fBuyingPrice
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
		}
	}
	$(document).ready(function() {
		$("#beginTime").val("${now}");
		merchantUserInfoComponent.init();
	});
</script>
</html>
