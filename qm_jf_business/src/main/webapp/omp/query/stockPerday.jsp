<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>每日库存</title>
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
				日期：<input id="beginTime" name="beginTime" type="text" class="Wdate"  onFocus="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false,readOnly:true})" style="width:150px;"/>   
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
	src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js"></script>
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
				"reqMethod" : "salesSummary",
				"fType" : "admin",
				"beginTime" : beginTime,
				"fName" : fName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fName',title : '药品名称',width : 200,align:'center'}, 
				{field : 'fSpecification',title : '规格',width : 200,align:'center'},
				{field : 'fAddress',title : '产地',width : 200,align:'center'},
				{field : 'lastMonthStock_in',title : '上月剩余库存',width : 100,align:'center'},
				{field : 'thisMonthStock_in',title : '本月入库',width : 100,align:'center'},
				{field : 'thisMonthStock_surplus',title : '本月剩余库存',width : 100,align:'center',
					formatter: function(value,row,index){
						return "<span style='color:green'>" + value + "</span>";
					}
				},
				{field : 'costMoney',title : '本月进货金额',width : 100,align:'center'},
				{field : 'lastMonthMoney',title : '上月结余进货金额',width : 100,align:'center'},
				{field : 'salesNumber',title : '本月销售',width : 100,align:'center'},
				{field : 'xlTotal',title : '本月销售合计(计算后结果)',width : 150,align:'center'},
				{field : 'day01',title : '1号',width : 100,align:'center'},
				{field : 'day02',title : '2号',width : 100,align:'center'},
				{field : 'day03',title : '3号',width : 100,align:'center'},
				{field : 'day04',title : '4号',width : 100,align:'center'},
				{field : 'day05',title : '5号',width : 100,align:'center'},
				{field : 'day06',title : '6号',width : 100,align:'center'},
				{field : 'day07',title : '7号',width : 100,align:'center'},
				{field : 'day08',title : '8号',width : 100,align:'center'},
				{field : 'day09',title : '9号',width : 100,align:'center'},
				{field : 'day10',title : '10号',width : 100,align:'center'},
				{field : 'day11',title : '11号',width : 100,align:'center'},
				{field : 'day12',title : '12号',width : 100,align:'center'},
				{field : 'day13',title : '13号',width : 100,align:'center'},
				{field : 'day14',title : '14号',width : 100,align:'center'},
				{field : 'day15',title : '15号',width : 100,align:'center'},
				{field : 'day16',title : '16号',width : 100,align:'center'},
				{field : 'day17',title : '17号',width : 100,align:'center'},
				{field : 'day18',title : '18号',width : 100,align:'center'},
				{field : 'day19',title : '19号',width : 100,align:'center'},
				{field : 'day20',title : '20号',width : 100,align:'center'},
				{field : 'day21',title : '21号',width : 100,align:'center'},
				{field : 'day22',title : '22号',width : 100,align:'center'},
				{field : 'day23',title : '23号',width : 100,align:'center'},
				{field : 'day24',title : '24号',width : 100,align:'center'},
				{field : 'day25',title : '25号',width : 100,align:'center'},
				{field : 'day26',title : '26号',width : 100,align:'center'},
				{field : 'day27',title : '27号',width : 100,align:'center'},
				{field : 'day28',title : '28号',width : 100,align:'center'},
				{field : 'day29',title : '29号',width : 100,align:'center'},
				{field : 'day30',title : '30号',width : 100,align:'center'},
				{field : 'day31',title : '31号',width : 100,align:'center'},
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
			var fName = $("#fUserCode").val();
			var beginTime = $('#beginTime').val();
			location.href = "${ctx}system/exportSalesSummary.do"
				+ "?fName=" + fName
				+ "&beginTime=" + beginTime
				+ "&fType=admin"
			;
		}
	}
	$(document).ready(function() {
		$("#beginTime").val("${now}");
		merchantUserInfoComponent.init();
	});
</script>
</html>
