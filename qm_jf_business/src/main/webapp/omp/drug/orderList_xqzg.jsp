<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
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
<%
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			Date firstDayOfMonth = calendar.getTime();  
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			Date lastDayOfMonth = calendar.getTime();
			request.setAttribute("beginTime", format.format(firstDayOfMonth));
			request.setAttribute("endTime", format.format(lastDayOfMonth));
			
		 %>
<body class="easyui-layout" id="filter">
		<div data-options="region:'north',border:false" style="height:77px;">
		
		  <div class="searchColumn">
			<div class="keySearch">
				业务员： <input type="search" placeholder="客户名称" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry();merchantUserInfoComponent.totalTc();');"/>
				日期：<input id="beginTime" name="beginTime" type="text" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" style="width:150px;"/>   
						至 
					<input id="endTime" name="endTime" type="text" class="Wdate"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true})" style="width:150px;"/>   
				
				<button class="grayBtn"
					onclick="merchantUserInfoComponent.userDataGrid.formQry();merchantUserInfoComponent.totalTc();">查询</button>
			</div>
			<p class="shrink" onClick="resizeNorth();">收起搜索栏</p>
		  </div>
		</div>
		<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false"  style="height:40px;">
					<div style="padding-left:10px;padding-top:0px;padding-bottom:10px;font-size:18px;font-weight:bold;">
						<span id="totalTc"></span><span>;</span>
						<span id="totalMoney"></span>
					</div>
				</div>
				<div data-options="region:'center',border:false">
					<div style="width:100%;height:100%;">
						<table id="userDataGrid"></table>  
					</div>
				</div>
			</div>
		</div>
</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
document.write("<script type='text/javascript' src='${contextPath}/resource/scripts/qm_util.js?v=${js_version}'></script>"); 
<script>
	var ajaxTools = new QM.ajax();
	var merchantUserInfoComponent = {
		userDataGrid : null,
		getQueryParams : function() {
			var fName = $("#fUserCode").val();
			var beginTime = $('#beginTime').val();
			var endTime = $('#endTime').val();
			return {
				"reqUrl" : "order",
				"reqMethod" : "getList_zg",
				"beginTime" : beginTime,
				"endTime" : endTime,
				"fName" : fName
			}
		},	
		init : function() {
			// 总提成 start
			merchantUserInfoComponent.totalTc();
			// 总提成 end
			var columns = [ 
				{align:'center',checkbox : true},
				{field : 'fId', title: '订单号', align:'center'},
				{field : 'fTax',title : '是否含税',width :100,align:'center',
					formatter: function(value,row,index){
						return fTax2Zh(value);
					}
				},
				{field : 'fCustomerName',title : '客户名称',width : 100,align:'center'}, 
				{field : 'fSaleUserName',title : '业务员',width : 100,align:'center'}, 
				{field : 'fSaleTime',title : '下单时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				 
				{field : 'fState',title : '订单状态',width :100,align:'center',
					formatter: function(value,row,index){
						return fState2Zh(value);
					}
				},
				{field : 'fXqTc_Money',title : '小区主管提成',width :100,align:'center',
					formatter: function(value,row,index){
						if (!value) {
							return "无";
						} else {
							return value+"元";
						}
					}
				},
				{field : 'fXq_Money',title : '销售额',width :100,align:'center',
					formatter: function(value,row,index){
						if (!value) {
							return "无";
						} else {
							return value+"元";
						}
					}
				},
				
				{field : 'parentId',title : '订单详细',width :100,align:'center',
					formatter: function(value,row,index){
						var fId = row['fId'];
						var fTax = row['fTax'];
						var fSaleUserId = row['fSaleUserId'];
						return '<a  href="javascript:void(0);" style="color:blue;" onclick="merchantUserInfoComponent.showDetail(\''+fId+'\',\' ' +fTax+ ' \' , \'' + fSaleUserId +'\');">订单详细</a>';
					}
				}
			];
			var toolbars = {};
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "订单列表",
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
							+"&fName=" + selRow.fName
							+"&fAddress=" + selRow.fAddress
							+"&fPhone=" + selRow.fPhone
							+"&fTax=" + selRow.fTax
							+"&fTownship=" + selRow.fTownship
							+"&fYaofang=" + selRow.fYaofang
							+"&fCustomerId=" + selRow.fCustomerId
							+"&fPolicyIntro=" + selRow.fPolicyIntro
							,
				"queryJson" : {
					"fCode" : selRow.fId
				}
			};
		},
		totalTc : function() {
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "order",
					"reqMethod" : "getTc_Total",
					"beginTime" : $('#beginTime').val(),
					"endTime" : $('#endTime').val(),
					"fName" : $("#fUserCode").val()
				},
				success : function(ret)
				{	
					if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
						
						$("#totalTc").html("提成总额：" + ret.d['total'] + " 元");
						$("#totalMoney").html("销售总额：" + ret.d['totalMoney'] + " 元");
					}else {
						
					}
				}
			});
		}
		,
		deleteMerUser : function (fCode) {
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "order",
					"reqMethod" : "delete",
					"fId" : fCode
				},
				success : function(ret)
				{	
					if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
						
						QM.dialog.showSuccessDialog("操作成功");
						merchantUserInfoComponent.userDataGrid.formQry();
					}else {
						QM.dialog.showFailedDialog("操作失败");
					}
				}
			});
		},
		updateOrder : function (fId, fState) {
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "order",
					"reqMethod" : "edit",
					"fId" : fId,
					"fState" : fState
				},
				success : function(ret)
				{	
					if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
						
						QM.dialog.showSuccessDialog("操作成功");
						merchantUserInfoComponent.userDataGrid.formQry();
					}else {
						QM.dialog.showFailedDialog("操作失败");
					}
				}
			});
		},
		showDetail : function(fId, fTax, fSaleUserId){
			console.log(fSaleUserId);
			var url;
			if(fTax == 0 || fTax == 3){//工业票
				url = "/omp/zhuguan/orderDetail_NoTax.jsp?fId="+fId + "&fSaleUserId=" + fSaleUserId;
			}else{
				url = "/omp/zhuguan/orderDetail_HasTax.jsp?fId="+fId + "&fSaleUserId=" + fSaleUserId;
			}
			QM.dialog.openWin({"title":"订单明细"},
				GLOBAL_INFO.CONTEXTPATH + url,
			function(ret) {

			});
		},
		showIntro : function(fId, fTax){
			var url = "/omp/drug/showIntro.jsp?fId="+fId + "&fType=" + fTax;
			
			QM.dialog.openWin({"title":"备注"},
				GLOBAL_INFO.CONTEXTPATH + url,
			function(ret) {

			});
		}
	}
	$(document).ready(function() {
		$("#beginTime").val("${beginTime}" + " 00:00:00");
		$("#endTime").val("${endTime}" + " 23:59:59");
		merchantUserInfoComponent.init();
	});
</script>
</html>
