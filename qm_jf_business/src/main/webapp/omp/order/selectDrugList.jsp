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
<script type="text/javascript" src="${contextPath}/resource/scripts/validate.js"></script>
</head>
<body class="easyui-layout" id="filter">
	
		<div data-options="region:'center',border:false">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false" style="height:40px;">
					<div style="margin-left:20px;margin-top:3px;"> 
						药品名称： <input type="search" placeholder="药品名称" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
						仓库： <input type="search" placeholder="仓库" id="fCityName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
						<button class="grayBtn"
							onclick="merchantUserInfoComponent.userDataGrid.formQry()">查询</button>
					</div>
					
				</div>
				<div data-options="region:'center',border:false" >
					<div class="easyui-layout" data-options="fit:true">
							<div data-options="region:'center',border:false" >
									<!-- 数据部分 -->
									<table id="userDataGrid">
									</table>
							</div>
							<div data-options="region:'south',border:false" style="height:70px;background-color: #FFF;padding-left: 10px;padding-right: 10px; padding-top:10px;">
									<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
										<tr>
											<th width="15%"><label>出售数量： </label></th>
											<td width="15%">
												<input style="width:100px;" id="fSalesNumber" type="text" placeholder="请输入整数"  class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
											</td>
											<th width="15%"><label>单价： </label></th>
											<td width="20%">
												<input style="width:100px;" id="fPrice_user" type="text" placeholder="请输入"  class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/>
											</td>
											<th width="15%"><label>开票价： </label></th>
											<td width="20%">
												<c:if test="${param.tax==0 }">
													<input style="width:100px;" id="fKaiPiaoPrice" type="text" disabled="disabled" placeholder="请输入"  class="easyui-validatebox" value="0"/>  
												</c:if>
												<c:if test="${param.tax!=0 }">
													<input style="width:100px;" id="fKaiPiaoPrice" type="text" placeholder="请输入"  class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"/>  
												</c:if>
											</td>
										</tr>
									</table>
							</div>
					</div>
				</div>
			</div>
		</div>
		<div data-options="region:'south',border:false" style="text-align: right; padding: 5px; background: #f4f4f4; border-top: 1px #ccc solid;">
				<button class="orangeBtn" onclick="merchantUserInfoComponent.ok();return false;">提交</button>
				<button class="grayBtn" onclick="merchantUserInfoComponent.cancel();">关闭</button>
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
			return {
				"reqUrl" : "drug",
				"reqMethod" : "getListByUser_ExceptSelected",
				"ids" : "${param.ids}",
				"nums" : "${param.nums}",
				"tax" : "${param.tax}",
				"fDrugPrinterIds" : "${param.fDrugPrinterIds}",
				"fCompanyIds" : "${param.fCompanyIds}",
				"fName" : fName,
				"fCityName" : fCityName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : true},
				{field : 'fName',title : '药品名称',width : 100,align:'center'}, 
				{field : 'fSpecification',title : '药品规格',width : 100,align:'center'}, 
				//{field : 'fAddress',title : '产地',width :100,align:'center'},
				{field : 'fBatchNumber',title : '批号',width :100,align:'center'},
				{field : 'fExpiryDate',title : '效期',width :100,align:'center'},
				{field : 'fPrice',title : '价格',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fGongyePrice',title : '工业票价',width :100,align:'center',
					formatter: function(value,row,index){
						return value+"元";
					}
				},
				{field : 'fWareHouseName',title : '仓库名称',width :100,align:'center'},
				{field : 'fNumber',title : '库存',width :100,align:'center'}
			];
			var toolbars = {};
			
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "药品列表",
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
		ok :function(){
			var fKaiPiaoPrice = $("#fKaiPiaoPrice").val();
			var fSalesNumber = $("#fSalesNumber").val();
			var fPrice_user = $("#fPrice_user").val();//政策报单药品自定义单价
			
			var _arr = $('#userDataGrid').datagrid('getChecked');
			//开票价大于单价
			if(_arr == 'undefined' || _arr.length<=0){
				QM.dialog.showFailedDialog("请选择要添加的记录！");
			}else{
				var fPrice;//药品单价
				var fNumber;//库存
				var fDrugOnlyId;//药品id
				var res = "[";
				
					for(var i=0;i<_arr.length;i++){
						var node = _arr[i];
						var fId = ''+node['fId'];//药品编码
						fPrice = node['fPrice'];
						fNumber = node['fNumber'];
						fDrugOnlyId = node['fDrugOnlyId'];
						
						if(!merchantUserInfoComponent.removeDuplicate(fId)){//去重
							if(i==_arr.length-1){
								res += "{fId:'"+node['fId']
								+"',fName:'"+node['fName']
								+"',fSpecification:'"+node['fSpecification']
								+"',fExpiryDate:'"+node['fExpiryDate']
								+"',fPrice:'"+fPrice_user
								+"',fBuyingPrice:'"+node['fBuyingPrice']
								+"',fGongyePrice:'"+node['fGongyePrice']
								+"',fKaiPiaoPrice:'"+fKaiPiaoPrice
								+"',fSalesNumber:'"+fSalesNumber
								+"',fState:'"+node['fState']+"'}";
							}else{
								res += "{fId:'"+node['fId']
								+"',fName:'"+node['fName']
								+"',fSpecification:'"+node['fSpecification']
								+"',fExpiryDate:'"+node['fExpiryDate']
								+"',fPrice:'"+fPrice_user
								+"',fBuyingPrice:'"+node['fBuyingPrice']
								+"',fGongyePrice:'"+node['fGongyePrice']
								+"',fKaiPiaoPrice:'"+fKaiPiaoPrice
								+"',fSalesNumber:'"+fSalesNumber
								+"',fState:'"+node['fState']+"'},";
							}
						}
					}
				
				res += "]";
				
				//同种药品选择效期最近的卖，强制性限制  start
				var noSelected = false;
				ajaxTools.singleReq({
					async : false,
					data : 
					{
						"reqUrl" : "drug",
						"reqMethod" : "checkExpireDate",
						"fId" : fId,
						"fDrugOnlyId" : fDrugOnlyId,
						"ids" : '${param.ids}',
						"nums" : '${param.nums}',
						"tax" : "${param.tax}",
						"fDrugPrinterIds" : "${param.fDrugPrinterIds}",
						"fCompanyIds" : "${param.fCompanyIds}",
					},
					success : function(ret)
					{	
						if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
							
						}else {
							if("fail".indexOf(ret.d) != -1){//不能选择这个药品
								noSelected = true;
							}
						}
					}
				});
				if(noSelected){
					QM.dialog.showFailedDialog("请选择效期近的药品！");
					return;
				}
				//同种药品选择效期最近的卖，强制性限制  end
				
				if(!$("#fSalesNumber").validatebox('isValid')){
					QM.dialog.showFailedDialog("请输入出售数量！");
					return;
				}
				if(!$("#fPrice_user").validatebox('isValid')){
					QM.dialog.showFailedDialog("请输入单价！");
					return;
				}
				if(fSalesNumber > fNumber){//
					QM.dialog.showFailedDialog("库存不足！");
					return;
				}
				if(!$("#fKaiPiaoPrice").validatebox('isValid')){
					return;
				}
				if(fKaiPiaoPrice == 0){
				}else{
					if(parseInt(fKaiPiaoPrice,10) < parseInt(fPrice_user,10)){
						QM.dialog.showFailedDialog("开票价必须要大于等于单价！");
						return;
					}
				} 
			    QM.dialog.doWinOtherCallback(true, res);
			}
		},
		cancel : function(){
	       QM.dialog.closeWinOther();
		},
		removeDuplicate : function(str){//去除重复数据
			var flag = false;
			var _d = '${param.ids}';
			var _arrSel;
			if(_d!=null && _d!=''){
				_arrSel = _d.split(",");
				for(var j=0;j<_arrSel.length;j++){
						var fId2 = _arrSel[j];//药品编码
						if(fId2.indexOf(str)!=-1 && str.indexOf(fId2)!=-1){
							flag = true;
							return true;
						}
				}
			}
			return flag;
		}
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
