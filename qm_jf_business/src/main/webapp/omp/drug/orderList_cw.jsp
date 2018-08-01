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
				业务员： <input type="search" placeholder="业务员" id="fCustomName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				订单状态：<select id="fState">
					<option value="1">未审核</option>
					<option value="2">已审核</option>
					<option value="3">已发货</option>
					<option value="4">退单</option>
					<option value="">全部</option>
				</select>
				付款情况：<select id="fPaymentState">
					<option value="">全部</option>
					<option value="0">借款</option>
					<option value="1">已付款</option>
				</select>
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

document.write("<script type='text/javascript' src='${contextPath}/resource/scripts/qm_util.js?${updateDate}'></script>"); 
<script>

	var ajaxTools = new QM.ajax();
	var merchantUserInfoComponent = {
		userDataGrid : null,
		getQueryParams : function() {
			var fName = $("#fUserCode").val();
			var fCustomName = $("#fCustomName").val();
			var fState = $("#fState").val();
			var fPaymentState = $("#fPaymentState").val();
			return {
				"reqUrl" : "order",
				"reqMethod" : "getList_cw",
				"fName" : fName,
				"fCustomName" : fCustomName,
				"fPaymentState" : fPaymentState,
				"fState" : fState
			}
		},	
		init : function() {
			
			var columns = [ 
				{align:'center',checkbox : true},
				{field : 'fId', title: '订单号', align:'center'},
				{field : 'parentId',title : '订单详细',width :100,align:'center',
					formatter: function(value,row,index){
						var fId = row['fId'];
						var fTax = row['fTax'];
						return '<a  href="javascript:void(0);" style="color:blue;" onclick="merchantUserInfoComponent.showDetail(\''+fId+'\',\' ' +fTax+ ' \');">订单详细</a>';
					}
				},
				{field : 'fTax',title : '是否含税',width :100,align:'center',
					formatter: function(value,row,index){
						return fTax2Zh(value);
					}
				},
				{field : 'isPolicy',title : '政策报单',width :100,align:'center',
					formatter: function(value,row,index){
						if(value=='0'){
							return "否";
						}else if(value=='1'){
							return "<span style='color:yellow;'>是</span>";
						}
					}
				},
				{field : 'fPaymentState',title : '付款情况',width :100,align:'center',
					formatter: function(value,row,index){
						return fPaymentState2Zh(value);
					}
				}, 
				{field : 'fState',title : '订单状态',width :100,align:'center',
					formatter: function(value,row,index){
						return fState2Zh(value);
					}
				},
				{field : 'fCustomerName',title : '客户名称',width : 100,align:'center'}, 
				{field : 'fName',title : '收货人',width : 100,align:'center'}, 
				{field : 'fUnit',title : '购货单位',width : 100,align:'center'}, 
				{field : 'fAddress',title : '收货地址',width : 100,align:'center'}, 
				{field : 'fPhone',title : '收货电话',width : 100,align:'center'}, 
				{field : 'fSaleUserName',title : '业务员',width : 100,align:'center'}, 
				{field : 'fSaleIntro',title : '业务员备注',width : 100,align:'center',
					formatter: function(value,row,index){
						var fId = row['fId'];
						var fTax = 1;//1:业务员备注，2:财务备注，3:发货备注
						return '<a title="' + value + '"  href="javascript:void(0);" style="color:blue;" onclick="merchantUserInfoComponent.showIntro(\''+fId+'\',\' ' +fTax+ ' \');">备注</a>';
					}
				
				}, 
				{field : 'fSaleTime',title : '下单时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				{field : 'fFinanceUserName',title : '财务',width : 100,align:'center'}, 
				{field : 'fFinanceIntro',title : '财务备注',width : 100,align:'center',
					formatter: function(value,row,index){
						var fId = row['fId'];
						var fTax = 2;//1:业务员备注，2:财务备注，3:发货备注
						return '<a title="' + value + '"  href="javascript:void(0);" style="color:blue;" onclick="merchantUserInfoComponent.showIntro(\''+fId+'\',\' ' +fTax+ ' \');">备注</a>';
					}
				}, 
				{field : 'fFinanceTime',title : '财务审批时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				{field : 'fShipperUserName',title : '发货员',width : 100,align:'center'}, 
				{field : 'fShipperIntro',title : '发货员备注',width : 100,align:'center',
					formatter: function(value,row,index){
						var fId = row['fId'];
						var fTax = 3;//1:业务员备注，2:财务备注，3:发货备注
						return '<a title="' + value + '"  href="javascript:void(0);" style="color:blue;" onclick="merchantUserInfoComponent.showIntro(\''+fId+'\',\' ' +fTax+ ' \');">备注</a>';
					}
				}, 
				{field : 'fShipperTime',title : '发货时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				},
				{field : 'fExpressName',title : '快递公司',width : 100,align:'center'}, 
				{field : 'fExpressId',title : '快递单号',width : 100,align:'center'},
				{field : 'fGuoJiFei',title : '过票费',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fFanDian',title : '返点',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fGaoKaiFei',title : '高开费',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fMoney_buyingPrice',title : '成本金额',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fMoney_noTax',title : '金额',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fMoney',title : '计算后金额',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fXqTc_Money',title : '小区主管提成',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fDqTc_Money',title : '大区主管提成',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				}
			];
			var toolbars = {};
			
			toolbars.newBtns = [
				 
 				{iconCls: 'icon-ok',text:'审批',handler: function(){
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
							if(selRows[0]['fState']!=1){
								top.QM.dialog.showFailedDialog("不可重复审批！");
								return;
							}
							
							var param = merchantUserInfoComponent.getPKConds(selRows[0]).queryStr;
							var editUrl = "${contextPath}/omp/drug/addFinanceInfo.jsp?fState=2";
							if(editUrl){
								if(editUrl.indexOf('?') != -1){
									editUrl = editUrl + "&" + param;
								}else{
									editUrl = editUrl + "?" + param;
								}
							} 
							var options = {"title":"填写备注"};
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
 				{iconCls: 'icon-cut',text:'还款',handler: function(){
							var selRows = $("#userDataGrid").datagrid("getSelections");
							/*
							 *如果此条促销记录处于启用状态则不可编辑，目的是保持该促销的唯一性，如果可以编辑了则后期根据该促销id查询该促销时则可能不知道促销的是什么商品
							 *启用状态的促销只可以停用和删除，这是该促销终止
							 */
							 
							if(!selRows || selRows.length != 1)
							{	
								top.QM.dialog.showFailedDialog("请选择要还款的记录，只能选取单行修改！");
								return ;
							}
							if(selRows[0]['fPaymentState']!=0){
								top.QM.dialog.showFailedDialog("未欠款！");
								return;
							}
							
							var param = merchantUserInfoComponent.getPKConds(selRows[0]).queryStr;
							var editUrl = "${contextPath}/omp/repayment/addRepayment.jsp";
							if(editUrl){
								if(editUrl.indexOf('?') != -1){
									editUrl = editUrl + "&" + param;
								}else{
									editUrl = editUrl + "?" + param;
								}
							}
							var options = {"title":"填写备注"};
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
				 },
 				 {iconCls: 'icon-cancel',text:'退单',handler: function(){
						var selRows = $("#userDataGrid").datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							QM.dialog.showFailedDialog("请选择要操作的数据，只能选取单个数据操作！");
							return false;
						}else{	
							//if(selRows[0].fState == 1){
								QM.dialog.showConfirmDialog("是否确定退单?", function(flg) {
									if(flg) {
										merchantUserInfoComponent.updateOrder(selRows[0].fId, 4);
									}else {
										return false;
									}
								});
							//}else{
							//	QM.dialog.showFailedDialog("不可重复提交！");
							//}	
						}
				  	 }
				 },
				 {iconCls: 'icon-edit',text:'修改订单价格',handler: function(){
						var selRows = $("#userDataGrid").datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							QM.dialog.showFailedDialog("请选择要操作的数据，只能选取单个数据操作！");
							return false;
						}else{	
							if(selRows[0]['isPolicy']==1){
								top.QM.dialog.showFailedDialog("政策报单，您无权修改！");
								return;
							}
							if(selRows[0]['fState']!=1){
								top.QM.dialog.showFailedDialog("只有未审核的订单才可修改价格！");
								return;
							}
							var fId = selRows[0]['fId'];
							var fTax = selRows[0]['fTax'];
							
							var url;
							if(fTax == 0 || fTax == 3){//工业票
								url = "/omp/drug/orderDetail_NoTax_EditPrice.jsp?fId="+fId + "&fTax=" + fTax;
							}else{
								url = "/omp/drug/orderDetail_HasTax_EditPrice.jsp?fId="+fId + "&fTax=" + fTax;
							}
							var options = {"title":"修改订单价格"};
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
							top.QM.dialog.openWin(options, GLOBAL_INFO.CONTEXTPATH + url);	
						}
				  	 }
				 },
				 {iconCls: 'icon-print',text:'打印',handler: function(){
							var selRows = $("#userDataGrid").datagrid("getSelections");
							/*
							 *如果此条促销记录处于启用状态则不可编辑，目的是保持该促销的唯一性，如果可以编辑了则后期根据该促销id查询该促销时则可能不知道促销的是什么商品
							 *启用状态的促销只可以停用和删除，这是该促销终止
							 */
							 
							if(!selRows || selRows.length != 1)
							{	
								top.QM.dialog.showFailedDialog("请选择记录，只能选取单行！");
								return ;
							}
							
							var param = merchantUserInfoComponent.getPKConds(selRows[0]).queryStr;
							var fTax = selRows[0]["fTax"];
							var editUrl;
							if(fTax==0){
								editUrl = "${contextPath}/omp/print/printOrder_noTax.jsp?fId="+ selRows[0]["fId"];
							}else{
								editUrl = "${contextPath}/omp/print/printOrder_hasTax.jsp?fId="+ selRows[0]["fId"];
							}
							//top.location.href = editUrl;
							window.open(editUrl);
				  	 }
				 }
			];
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
		showDetail : function(fId, fTax){
			var url;
			if(fTax == 0 || fTax == 3){//工业票
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
		}
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
