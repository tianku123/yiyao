<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>医药管理系统</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${contextPath}/resource/css/common.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/scripts/plugin/easyui/themes/${skin}/easyui.css" rel="stylesheet" type="text/css" />
<link href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${contextPath}/resource/scripts/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/validate.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		queryByCode : {
			// 验证编号
			validator : function(value) {
				var result = true;
				var ajaxTools = new QM.ajax();
				ajaxTools.singleReq({
					async : false,
					data : {
						"reqUrl" : "cardType",
						"reqMethod" : "queryBeanById",
						"code" : value
					},
					success : function(ret) {
						if (ret.d != null) {
							result = false;
						} else {
							result = true;
						}
					}.bind(this)
				});
				return result;
			},
			message : '该编号已被使用'
		}
	});
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region : 'center',border:false" style="height:120px;background-color: #FFF;padding:0px 10px 0px 10px;">
				<div id="AA" style="width:100%;height:100%;" >
					<table id="selectedCustomer"></table>  
				</div>
				
				<table id="BB" cellpadding="0" cellspacing="0" class="formTable" width="100%" style="display:none;">
					
					<tr>
						<th width="15%"><label>乡镇：</label>
						</th>
						<td width="20%"><input id="fTownship" name="fTownship" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true"
							maxlength="50" /></td>
						<th width="15%"><label>药房：</label>
						</th>
						<td width="50%"><input id="fYaofang" name="fYaofang" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" style="width:300px;"
							maxlength="100" />
						</td>
					</tr>
					
				</table>
			</div>
			<div id="div1" data-options="region : 'north',border:false" style="background-color: #FFF;padding: 0px 10px 0px 10px;">
				<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
					
					<tr>
						<th width="20%"><label>类型：</label>
						</th>
						<td width="20%">
							<select id="tax">
								<option value="0">普通发票</option>
								<option value="3">专用发票</option>
								<option value="1">高开(增值税)</option>
								<option value="2">高开(普通)</option>
							</select>
						</td>
						
						<th width="15%"><label>收货人：</label>
						</th>
						<td width="45%"><input id="fName" name="fName" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true"
							maxlength="50" /></td>
					</tr>
				
					<tr>
						<th width="20%"><label>收货电话：</label>
						</th>
						<td width="20%"><input id="fPhone" name="fPhone" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'number'"
							maxlength="50" /></td>
						<th width="15%"><label>购货地址：</label>
						</th>
						<td width="45%"><input id="fAddress" name="fAddress" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" style="width:300px;"
							maxlength="100" />
						</td>
					</tr>
				
					<tr>
						<th width="20%"><label>政策内容：</label>
						</th>
						<td width="80%" colspan="3">
							<textarea id="fPolicyIntro" name="fPolicyIntro" rows="5" cols="80"><c:out value="${param.fPolicyIntro}"></c:out></textarea>
						</td>
					</tr>
					
				</table>
				
			</div>
			<div data-options="region : 'south',border:false" style="height:180px;background-color: #FFF;padding:0px 10px 0px 10px;">
				<div style="width:100%;height:100%;">
					<table id="selectedDrug"></table>  
				</div>
			</div>
		</div>
	</div>

	<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
		<button class="orangeBtn" onclick="merchantUserInfoComponent.comit();return false;">提交</button><button class="grayBtn" onclick="merchantUserInfoComponent.close()">关闭</button>
	</div>

</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js"></script>
<script>
	var ajaxTools = new QM.ajax();
	//管理门店编码，以.分割

	var merchantUserInfoComponent = {
		init : function() {
		var fAddress = $("#fAddress").val();
			var fName = $("#fName").val();
			var fPhone = $("#fPhone").val();
			var tax = $("#tax").val();
			var fTownship = $("#fTownship").val();
			var fYaofang = $("#fYaofang").val();
			
			$("#fAddress").val("${param.fAddress}");
			$("#fName").val("${param.fName}");
			$("#fPhone").val("${param.fPhone}");
			$("#fTownship").val("${param.fTownship}");
			$("#fYaofang").val("${param.fYaofang}");
			$("#tax").val("${param.fTax}");
			//$("#tax").triggerHandler("change");
			$("#tax").attr("disabled","disabled");
			$("#selectedCustomer").datagrid({
				title : '已选客户',
				fit : true,
				fitColumns : true,
				rownumbers : true,
				idField : 'fId',
				columns:[[ 
					{field : 'fId',align:'center',checkbox : true},
					{field : 'fName',title : '经营者',width : 100,align:'center'}, 
					{field : 'fCityName',title : '所属区域',width : 100,align:'center'}, 
					{field : 'fUnit',title : '购货单位',width : 100,align:'center'}
				]],
				/* toolbar : [
					{
						iconCls : "icon-add",
						text : '选择客户',
						handler : function(){
							merchantUserInfoComponent.selectCustomer();
						}
					},
					{
						iconCls : "icon-no",
						text : '删除',
						handler : function(){
							var rows = $("#selectedCustomer").datagrid('getChecked');
							var len = rows.length;
							for(var i=0;i<len;i++){
								var ind = $("#selectedCustomer").datagrid('getRowIndex',rows[0]);
								$("#selectedCustomer").datagrid('deleteRow',ind);
							}
						}
					}
				] */
			});
			
			//if("0".indexOf("${param.fTax}") == -1){//含税则加载客户
				//客户
				ajaxTools.singleReq({
					data : 
					{
						"reqUrl" : "customer",
						"reqMethod" : "getListById",
						"fCustomerId" : "${param.fCustomerId}"
					},
					success : function(ret)
					{	
						$("#selectedCustomer").datagrid('loadData',ret.d);
					}
				});
			//}
			
			$("#selectedDrug").datagrid({
				title : '已选药品',
				fit : true,
				fitColumns : true,
				rownumbers : true,
				idField : 'fId',
				columns:[[    
					{field : 'fId',align:'center',checkbox : true},
					{field : 'fName',title : '药品名称',width : 100,align:'center'}, 
					{field : 'fSpecification',title : '药品规格',width : 100,align:'center'}, 
					{field : 'fExpiryDate',title : '效期',width :100,align:'center'},
					{field : 'fBuyingPrice',title : '批号',width :100,align:'center',hidden:true},
					{field : 'fPrice',title : '价格',width :100,align:'center',
						formatter: function(value,row,index){
							return value+"元";
						}
					},
					{field : 'fKaiPiaoPrice',title : '开票价',width :100,align:'center',
						formatter: function(value,row,index){
							if(value == 0 || typeof(value)=='undefined'){
								return "";
							}else{
								return value+"元";
							}
						}
					},
					{field : 'fSalesNumber',title : '出售数量',width :100,align:'center'}
				]],
				toolbar : [
					{
						iconCls : "icon-add",
						text : '选择药品',
						handler : function(){
							merchantUserInfoComponent.selectDrug();
						}
					},
					{
						iconCls : "icon-no",
						text : '删除',
						handler : function(){
							var rows = $("#selectedDrug").datagrid('getChecked');
							var len = rows.length;
							for(var i=0;i<len;i++){
								var ind = $("#selectedDrug").datagrid('getRowIndex',rows[0]);
								$("#selectedDrug").datagrid('deleteRow',ind);
							}
						}
					}
				]
			});
			
			//药品
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "orderDetail",
					"reqMethod" : "getList_EditOrder",
					"fOrderId" : "${param.fId}"
				},
				success : function(ret)
				{	
					$("#selectedDrug").datagrid('loadData',ret.d);
				}
			});
		},
		comit : function() {
			var fAddress = $("#fAddress").val();
			var fName = $("#fName").val();
			var fPhone = $("#fPhone").val();
			var tax = $("#tax").val();
			var fTownship = $("#fTownship").val();
			var fYaofang = $("#fYaofang").val();
			var fPolicyIntro = $("#fPolicyIntro").val();
			
			if(!$("#fName").validatebox('isValid')){
				return;
			}
			if(!$("#fAddress").validatebox('isValid')){
				return;
			}
			if(!$("#fPhone").validatebox('isValid')){
				return;
			}
			var customer = '';
			/* if(tax == 0){//工业票
				if(!$("#fTownship").validatebox('isValid')){
					return;
				}
				if(!$("#fYaofang").validatebox('isValid')){
					return;
				}
			}else{ *///含税
				//选择的药品  start
				var customerData = $("#selectedCustomer").datagrid('getData');
				if(customerData['total']==0){
					QM.dialog.showFailedDialog("请选择客户！");
					return;
				}else{
					customer = JSON.encode(customerData['rows']);
				}
			//}
			
			var drugData = $("#selectedDrug").datagrid('getData');
			var drug = null;
			if(drugData['total']==0){
				QM.dialog.showFailedDialog("请选择药品！");
				return;
			}else{
				drug = drugData['rows'];
			}
			/* var _d = $("#selectedDrug").datagrid('getData');
			var _data;
			if(_d['total']==0){
				QM.dialog.showFailedDialog("请选择药品！");
				return;
			}else{
				_data = _d['rows'];
				var idStr = '';
				for(var i=0;i<_data.length;i++){
					if(i==_data.length-1){
						idStr += _data[i]['fId'];
					}else{
						idStr += _data[i]['fId'] + ',';
					}
				}
				drugIds = idStr;
			} */
			//选择的药品  end
			
			var fName = $("#fName").val();
			ajaxTools.singleReq({
				data : {
					"reqUrl" : "order",
					"reqMethod" : "editOrder_policy",
					"drug" : JSON.encode(drug),
					"customer" : customer,
					"fYaofang" : fYaofang,
					"fTownship" : fTownship,
					"fAddress" : fAddress,
					"fPhone" : fPhone,
					"fTax" : tax,
					"fPolicyIntro" : fPolicyIntro,
					"fId" : "${param.fId}",
					"fName" : fName
				},
				success : function(ret) {
					
					if (ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
						QM.dialog.doWinCallback(true, ret);
					} else {
						QM.dialog.showFailedDialog(ret.d);
						QM.dialog.doWinCallback(false, ret);
					}
					
				}
			});
		},
		close : function() {
			QM.dialog.closeWin();
		},
		selectCustomer : function() {//选择客户
			var _d = $("#selectedCustomer").datagrid('getData');
			var _data;
			if(_d['total']==0){
				_data = "[]";
			}else{
				_data = JSON.encode(_d['rows']);
			}
			QM.dialog
					.openWinOther(
							{
								"title" : "选择客户",
								"width" : "800",
								"height" : "520"
							},
							GLOBAL_INFO.CONTEXTPATH
									+ "/omp/drug/selectCustomerList.jsp?data="+_data,
							function(data) {
								var newD = eval(data);
								$("#fAddress").val(newD[0]['fAddress']);
								$("#fName").val(newD[0]['fName']);
								$("#fPhone").val(newD[0]['fPhone']);
								$("#selectedCustomer").datagrid('loadData',newD);
							});
		},
		selectDrug : function() {//选择药品
			//含税的情况下先选择客户，因为需要通过客户的经营权限过滤药品   start
			//是否含税
			var tax = $("#tax").val();
			var fDrugPrinterIds;
			var fCompanyIds;
			/* if(tax == 0){//工业票
				
			}else{ *///含税
				//选择的药品  start
				var customerData = $("#selectedCustomer").datagrid('getData');
				if(customerData['total']==0){
					QM.dialog.showFailedDialog("请选择客户！");
					return;
				}else{
					fDrugPrinterIds = customerData['rows'][0]["fDrugPrinterIds"];
					fCompanyIds = customerData['rows'][0]["fCompanyIds"];
				}
			//}
			
			//已选药品id
			var ids = "";
			//已选药品对应的库存
			var nums = "";
			var _d = $("#selectedDrug").datagrid('getData');
			var _data;
			if(_d['total']==0){
				_data = "[]";
			}else{
				var arr = _d['rows'];
				_data = JSON.encode(_d['rows']);
				ids = "";
				nums = "";
				for(var i=0; i<arr.length; i++){
					if(i == arr.length-1){
						ids += arr[i]['fId'];
						nums += arr[i]['fSalesNumber'];
					}else{
						ids += arr[i]['fId'] + ",";
						nums += arr[i]['fSalesNumber'] + ",";
					}
				}
				
			}
			QM.dialog
					.openWinOther(
							{
								"title" : "选择药品",
								"width" : "800",
								"height" : "520"
							},
							GLOBAL_INFO.CONTEXTPATH
									+ "/omp/order/selectDrugList.jsp?ids="+ids + "&tax="+tax 
									+ "&nums="+nums
									+ "&fCompanyIds="+fCompanyIds
									+ "&fDrugPrinterIds="+fDrugPrinterIds
									,
							function(data) {
								var newD = _d['rows'].concat(eval(data));
								
								$("#selectedDrug").datagrid('loadData',newD);
								$("#tax").attr("disabled","disabled");
							});
		}
	}
	$(function() {
		//一旦确定含税或者工业票则不可切换，如果切换则
		/* $("#tax").change(function(){
			var num = $(this).val();
			if(num==0){
				$("#BB").show();//工业票，不需要选择客户，人工输入
				$("#AA").hide();
			}else{
				$("#BB").hide();
				$("#AA").show();//选择客户
			}
		}); */
		
		merchantUserInfoComponent.init();
		
	});
</script>
</html>
