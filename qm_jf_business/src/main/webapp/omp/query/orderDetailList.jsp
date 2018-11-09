<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>每日订单</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${contextPath}/resource/css/common.css" rel="stylesheet"
	type="text/css" />
<link href="${contextPath}/resource/css/custom.css" rel="stylesheet"
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
			String now = format.format(new Date());
			request.setAttribute("beginTime", format.format(firstDayOfMonth));
			request.setAttribute("now", now);
		 %>
<body class="easyui-layout" id="filter">
		<div data-options="region:'north',border:false" style="height:120px;">
		
		  <div class="searchColumn">
			<div class="keySearch">
				订单号： <input type="search" placeholder="订单号" id="fId" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				药品名： <input type="search" placeholder="药品名" id="fDrugName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				购货单位： <input type="search" placeholder="购货单位" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				业务员： <input type="search" placeholder="业务员" id="fCustomName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				订单状态：<select id="fState">
					<option value="">全部</option>
					<option value="1">未审核</option>
					<option value="2">已审核</option>
					<option value="3">已发货</option>
				</select><br/>
				付款情况：<select id="fPaymentState">
					<option value="">全部</option>
					<option value="0">借款</option>
					<option value="1">已付款</option>
				</select>
				财务复核情况：<select id="fExamine">
					<option value="">全部</option>
					<option value="0">未复核</option>
					<option value="1">已复核</option>
				</select>
				是否含税：<select id="fTax">
					<option value="">全部</option>
					<option value="0">工业票</option>
					<option value="1">含税(增值税)</option>
					<option value="2">含税(普通)</option>
				</select>
				政策报单：<select id="fIsPolicy">
					<option value="">全部</option>
					<option value="0">否</option>
					<option value="1">是</option>
				</select>
				时间类型：<select id="fTimeType">
					<option value="1">下单时间</option>
					<option value="2">审核时间</option>
					<option value="3">发货时间</option>
				</select>
				<input id="beginTime" name="beginTime" type="text" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endTime\')}',startDate:'%y-%M-%d 00:00:00',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" style="width:150px;"/>   
						至 
					<input id="endTime" name="endTime" type="text" class="Wdate"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'beginTime\')}',startDate:'%y-%M-%d 23:59:59',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true})" style="width:150px;"/>   
				<br/>
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
			var fId = $("#fId").val();
			var fDrugName = $("#fDrugName").val();
			var fName = $("#fUserCode").val();
			var fCustomName = $("#fCustomName").val();
			var fState = $("#fState").val();
			var fPaymentState = $("#fPaymentState").val();
			var fExamine = $("#fExamine").val();
			var beginTimeVal = $("#beginTime").val();
			var endTimeVal = $("#endTime").val();
			var fTimeType = $("#fTimeType").val();
			var beginTime;
			var endTime;
			var financeBeginTime;
			var financeEndTime;
			var shipperBeginTime;
			var shipperEndTime;
			// 下单时间
			if (fTimeType == 1) {
				beginTime = beginTimeVal;
				endTime = endTimeVal;
			}
			// 财务审核时间
			if (fTimeType == 2) {
				financeBeginTime = beginTimeVal;
				financeEndTime = endTimeVal;
			}
			// 发货时间
			if (fTimeType == 3) {
				shipperBeginTime = beginTimeVal;
				shipperEndTime = endTimeVal;
			}
			var fTax = $("#fTax").val();
			var fIsPolicy = $("#fIsPolicy").val();
			return {
				"reqUrl" : "orderDetail",
				"reqMethod" : "getOrderDetailList",
				"fDrugName" : fDrugName,
				"fId" : fId,
				"fName" : fName,
				"fCustomName" : fCustomName,
				"fPaymentState" : fPaymentState,
				"fExamine" : fExamine,
				"beginTime" : beginTime,
				"endTime" : endTime,
				"financeBeginTime" : financeBeginTime,
				"financeEndTime" : financeEndTime,
				"shipperBeginTime" : shipperBeginTime,
				"shipperEndTime" : shipperEndTime,
				"fTax" : fTax,
				"fIsPolicy" : fIsPolicy,
				"fState" : fState
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'F_ID',title : '订单号',align:'center'},
				{field : 'F_TAX',title : '是否含税',width :100,align:'center',
					formatter: function(value,row,index){
						return fTax2Zh(value);
					}
				},
				{field : 'F_STATE',title : '订单状态',width :100,align:'center',
					formatter: function(value,row,index){
						return fState2Zh(value);
					}
				},
				{field : 'F_ISPOLICY',title : '政策报单',width :100,align:'center',
					formatter: function(value,row,index){
						if(value=='0'){
							return "否";
						}else if(value=='1'){
							return "<span style='color:yellow;'>是</span>";
						}
					}
				},
				{field : 'F_PAYMENT_STATE',title : '付款情况',width :100,align:'center',
					formatter: function(value,row,index){
						return fPaymentState2Zh(value);
					}
				}, 
				{field : 'F_EXAMINE',title : '复核',width :100,align:'center',
					formatter: function(value,row,index){
						if(value=='0'){
							return "<span style='color:red;'>未复核</span>";
						}else if(value=='1'){
							return "<span style='color:green;'>已复核</span>";
						}
					}
				},
				{field : 'F_CUSTOMER_NAME',title : '客户名称',width : 100,align:'center'}, 
				{field : 'F_NAME',title : '收货人',width : 100,align:'center'}, 
				{field : 'F_UNIT',title : '购货单位',width : 100,align:'center'}, 
				{field : 'F_ADDRESS',title : '收货地址',width : 100,align:'center'}, 
				{field : 'F_PHONE',title : '收货电话',width : 100,align:'center'}, 
				{field : 'F_SALE_USER_NAME',title : '业务员',width : 100,align:'center'},  
				{field : 'F_SALE_TIME',title : '下单时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				{field : 'F_FINANCE_USER_NAME',title : '财务',width : 100,align:'center'},  
				{field : 'F_FINANCE_TIME',title : '财务审批时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				{field : 'F_SHIPPER_USER_NAME',title : '发货员',width : 100,align:'center'}, 
				{field : 'F_SHIPPER_TIME',title : '发货时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				{field : 'F_EXPRESS_NAME',title : '快递公司',width : 100,align:'center'}, 
				{field : 'F_EXPRESS_ID',title : '快递单号',width : 100,align:'center'},
				{field : 'F_GUOJIFEI',title : '过票费',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'F_GAOKAIFEI',title : '高开费',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'F_MONEY_BUYINGPRICE',title : '成本金额',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'F_MONEY_NOTAX',title : '金额',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'F_MONEY',title : '计算后金额',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'F_XQ_TC_MONEY',title : '小区主管提成',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'F_DQ_TC_MONEY',title : '大区主管提成',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				}
				// 以下是订单详情部分
				,
				{field : 'F_DRUG_NAME',title : '药品名称',width :100,align:'center'}
				,
				{field : 'F_NUMBER',title : '销售数量',width :100,align:'center'}
				,
				{field : 'F_GUOJIFEI_DETAIL',title : '过票费',width :100,align:'center'}
				,
				{field : 'F_GAOKAIFEI_DETAIL',title : '高开费',width :100,align:'center'}
				,
				{field : 'F_MONEY_DETAIL',title : '计算后金额',width :100,align:'center'}
				,
				{field : 'F_MONEY_BUYINGPRICE_DETAIL',title : '成本金额',width :100,align:'center'}
				,
				{field : 'F_XQ_TC_MONEY_DETAIL',title : '小区提成',width :100,align:'center'}
				,
				{field : 'F_DQ_TC_MONEY_DETAIL',title : '大区提成',width :100,align:'center'}
			];
			var toolbars = {};
			
			toolbars.newBtns = [
				 
 				
 				{iconCls: 'icon-tip',text:'还款记录查询',handler: function(){
							var selRows = $("#userDataGrid").datagrid("getSelections");
							/*
							 *如果此条促销记录处于启用状态则不可编辑，目的是保持该促销的唯一性，如果可以编辑了则后期根据该促销id查询该促销时则可能不知道促销的是什么商品
							 *启用状态的促销只可以停用和删除，这是该促销终止
							 */
							 
							if(!selRows || selRows.length != 1)
							{	
								top.QM.dialog.showFailedDialog("请选择要修改的记录，只能选取单行修改！");
								return ;
							}
							/* if(selRows[0]['fPaymentState']!=0){
								top.QM.dialog.showFailedDialog("已还款！");
								return;
							} */
							
							var param = merchantUserInfoComponent.getPKConds(selRows[0]).queryStr;
							var editUrl = "${contextPath}/omp/repayment/repaymentList.jsp?fState=2";
							if(editUrl){
								if(editUrl.indexOf('?') != -1){
									editUrl = editUrl + "&" + param;
								}else{
									editUrl = editUrl + "?" + param;
								}
							} 
							var options = {"title":"还款记录"};
							top.QM.dialog.openWin(options, editUrl);
				  	 }
				 }
			];
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "订单详细列表",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				sortOrder : 'asc',
				fitColumns : false,
				columns : [ columns ],
				onLoadSuccess: function(data) {
					var mergeField = "F_ID,F_TAX,F_ISPOLICY,F_PAYMENT_STATE,F_STATE,F_CUSTOMER_NAME,F_NAME,F_UNIT,F_ADDRESS,F_PHONE,"+
					"F_SALE_USER_NAME,F_SALE_TIME,F_FINANCE_USER_NAME,F_FINANCE_TIME"
					+",F_SHIPPER_USER_NAME,F_SHIPPER_TIME,F_EXPRESS_NAME,F_EXPRESS_ID,F_EXAMINE," 
					+"F_GUOJIFEI,F_FANDIAN,F_GAOKAIFEI,F_MONEY_BUYINGPRICE,F_MONEY_NOTAX,F_MONEY,F_XQ_TC_MONEY,F_DQ_TC_MONEY"
					MergeCells_RuleField("userDataGrid", "F_ID", mergeField);
				}
			}, toolbars, this.getQueryParams);
			this.userDataGrid.init();
			$(".datagrid-header-check input").hide();
		},
		getPKConds : function(selRow) {	
			return {
				"queryStr" : "fId=" + selRow.fId
							+"&fName=" + selRow.fName
							,
				"queryJson" : {
					"fCode" : selRow.fId
				}
			};
		},
		showDetail : function(fId, fTax){
			var url;
			if(fTax == 0){//工业票
				url = "/omp/ticheng/orderDetail_NoTax.jsp?fId="+fId;
			}else{
				url = "/omp/ticheng/orderDetail_HasTax.jsp?fId="+fId;
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
		},
		exportA : function(){
			var fName = $("#fUserCode").val();
			var fCustomName = $("#fCustomName").val();
			var fState = $("#fState").val();
			var fPaymentState = $("#fPaymentState").val();
			var fExamine = $("#fExamine").val();
			var beginTime = $("#beginTime").val();
			var endTime = $("#endTime").val();
			var fTax = $("#fTax").val();
			var fIsPolicy = $("#fIsPolicy").val();
			location.href = "${ctx}system/exportOrderDetail.do"
				+ "?fName=" + fName
				+ "&fCustomName=" + fCustomName
				+ "&fState=" + fState
				+ "&fPaymentState=" + fPaymentState
				+ "&fExamine=" + fExamine
				+ "&beginTime=" + beginTime
				+ "&endTime=" + endTime
				+ "&fTax=" + fTax
				+ "&fIsPolicy=" + fIsPolicy
			;
		}
	}
	$(document).ready(function() {
		$("#beginTime").val("${beginTime}" + " 00:00:00")
		$("#endTime").val("${now}" + " 23:59:59")
		merchantUserInfoComponent.init();
	});
</script>
</html>
