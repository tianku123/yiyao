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
				药品名称： <input type="search" placeholder="药品名称" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
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
			return {
				"reqUrl" : "order",
				"reqMethod" : "salesSummary",
				"fType" : "ywy",
				"beginTime" : "${now}",
				"fName" : fName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fName',title : '药品名称',width : 200,align:'center'}, 
				{field : 'fSpecification',title : '规格',width : 200,align:'center'},
				{field : 'fAddress',title : '产地',width : 200,align:'center'},
				{field : 'thisMonthStock_surplus',title : '剩余库存',width : 100,align:'center',
					formatter: function(value,row,index){
						return "<span style='color:green'>" + value + "</span>";
					}
				}
			];
			var toolbars = {};
			
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "药品库存列表",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				sortOrder : 'asc',
				pagination : false,
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
		exportA : function(){
			var fName = $("#fUserCode").val();
			var beginTime = $('#beginTime').val();
			location.href = "${ctx}system/exportSalesSummary.do"
				+ "?fName=" + fName
				+ "&beginTime=${now}"
				+ "&fType=ywy"
			;
		}
	}
	$(document).ready(function() {
		$("#beginTime").val("${now}");
		merchantUserInfoComponent.init();
	});
</script>
</html>
