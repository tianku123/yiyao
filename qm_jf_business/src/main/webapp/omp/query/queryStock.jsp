<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>入库查询</title>
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
<%
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String now = format.format(new Date());
			request.setAttribute("now", now);
		 %>
<body class="easyui-layout" id="filter">
		<div data-options="region:'north',border:false" style="height:77px;">
		
		  <div class="searchColumn">
			<div class="keySearch">
				药品名称： <input type="search" placeholder="药品名称" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				仓库： <input type="search" placeholder="仓库" id="fCityName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				日期：<input id="beginTime" name="beginTime" type="text" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" style="width:150px;"/>   
						至 
					<input id="endTime" name="endTime" type="text" class="Wdate"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" style="width:150px;"/>   
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
			var endTime = $('#endTime').val();
			return {
				"reqUrl" : "drug",
				"reqMethod" : "queryStock",
				"beginTime" : beginTime,
				"endTime" : endTime,
				"fName" : fName,
				"fCityName" : fCityName
			}
		},	
		init : function() {
			
			var columns = [ 
				
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
				{field : 'fNumber',title : '剩余',width :100,align:'center'},
				{field : 'fNumberBak',title : '入库',width :100,align:'center'},
				{field : 'fBatchNumber',title : '批号',width :100,align:'center'},
				{field : 'fExpiryDate',title : '效期',width :100,align:'center'},
				{field : 'fPrice',title : '价格',width :100,align:'center'},
				{field : 'fWareHouseName',title : '仓库名称',width :100,align:'center'},
				{field : 'fTime',title : '入库时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
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
		$("#beginTime").val("${now}" + " 00:00:00")
		$("#endTime").val("${now}" + " 23:59:59")
		merchantUserInfoComponent.init();
	});
</script>
</html>
