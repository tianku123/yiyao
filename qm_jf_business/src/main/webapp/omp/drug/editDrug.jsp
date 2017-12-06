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
<script type="text/javascript" src="${contextPath}/resource/scripts/My97DatePicker/WdatePicker.js"></script>
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
<form name="ticketInfoForm" id="ticketInfoForm" method="post" enctype="multipart/form-data" style="width:100%;heigth:100%;">
	<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
		<input type="hidden" name="reqUrl" id="reqUrl" value="drug" /> 
		<input type="hidden" name="reqMethod" id="reqMethod" value="edit" />
		<input type="hidden" id="fId" name="fId" value="${ param.fId }" />
		<input type="hidden" name="fWareHouseId" id="fWareHouseId" />
		<input type="hidden" name="fCompanyId" id="fCompanyId" />
		<input type="hidden" name="fDrugOnlyId" id="fDrugOnlyId" value="${param.fDrugOnlyId }"/>
		<div data-options="region : 'north',border:false" style="height:120px;background-color: #FFF;padding:0px 10px 0px 10px;">
			<div style="width:100%;height:100%;">
				<table id="selectedDrugOnly"></table>  
			</div>
		</div>
		<div id="div1" data-options="region : 'center',border:false" style="background-color: #FFF;padding: 0px 10px 0px 10px;">
				<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
					<tr>
						<th width="15%">
							<label>药品名称：</label>
						</th>
						<td width="35%">
							<input id="fName" name="fName" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
						</td>
						
						<th width="15%">
							<label>药品规格：</label>
						</th>
						<td width="35%">
							<input id="fSpecification" name="fSpecification" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
						</td>
						
					</tr>
					<tr>
						<th width="15%">
							<label>产地：</label>
						</th>
						<td width="35%">
							<input id="fAddress" name="fAddress" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
						</td>
						
						<th width="15%">
							<label>批号：</label>
						</th>
						<td width="35%">
							<input id="fBatchNumber" name="fBatchNumber" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
						</td>
						
					</tr>
					<tr>
						<th width="15%">
							<label>效期：</label>
						</th>
						<td width="35%">
							<input id="fExpiryDate" name="fExpiryDate" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'yyyyMMdd'" />
						</td>
						
						<th width="15%">
							<label>价格：</label>
						</th>
						<td width="35%">
							<input id="fPrice" name="fPrice" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" />
						</td>
						
					</tr>
					<tr>
						<th width="15%">
							<label>进货价：</label>
						</th>
						<td width="35%">
							<input id="fBuyingPrice" name="fBuyingPrice" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" />
						</td>
						
						<th width="15%">
							<label>库存：</label>
						</th>
						<td width="35%">
							<input id="fNumber" name="fNumber" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'positiveAndZero'" />
						</td>
					</tr>
					<tr>
						<th width="15%"><label>仓库：</label></th>
						<td width="35%">
							<select id="role_combo" name="PROVENCE" style="width: 150px;"></select>
							<div id="role_panel">
								<ul id="role_tree" class="ztree"></ul>
							</div>
						</td>
						
						<th width="15%"><label>医药公司：</label></th>
						<td width="35%">
							<select id="company_combo" name="company" style="width: 150px;"></select>
							<div id="company_panel">
								<ul id="company_tree" class="ztree"></ul>
							</div>
						</td>
					</tr>
					<tr>
						<th width="15%">
							<label>工业票价：</label>
						</th>
						<td width="35%">
							<input id="fGongyePrice" name="fGongyePrice" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" />
						</td>
					</tr>
				</table>
		</div>
	</div>

	<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
		<button class="orangeBtn" onclick="merchantUserInfoComponent.comit();return false;">提交</button><button class="grayBtn" onclick="merchantUserInfoComponent.close()">关闭</button>
	</div>
</form>
</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js"></script>
<script>
	var ajaxTools = new QM.ajax();
	//管理门店编码，以.分割

	var merchantUserInfoComponent = {
		
		init : function() {
			$("#fName").val("${param.fName}");
			$("#fSpecification").val("${param.fSpecification}");
			$("#fAddress").val("${param.fAddress}");
			$("#fBatchNumber").val("${param.fBatchNumber}");
			$("#fExpiryDate").val("${param.fExpiryDate}");
			$("#fNumber").val("${param.fNumber}");
			$("#fPrice").val("${param.fPrice}");
			$("#fGongyePrice").val("${param.fGongyePrice}");
			$("#fBuyingPrice").val("${param.fBuyingPrice}");
			
			//客户区域start
			$('#role_combo').combo({
				editable:false
			});//end combo
		
			$('#role_panel').appendTo($('#role_combo').combo('panel'));
			
			$.fn.zTree.init($("#role_tree"), {
				view:{
					showIcon:true
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllWarehouse.do",
					otherParam : {'roleType' : '0'}
				},
				data: {
					key :{name:'fName'},
					simpleData: {
						enable: true,
						idKey: 'fId',
						pIdKey: 'parentId'
					}
				},callback: {
					onClick: function(){
						var treeObj = $.fn.zTree.getZTreeObj("role_tree");
						var nodes = treeObj.getSelectedNodes();
						$('#role_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						$('#role_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj("role_tree");
						var nodes = treeObj.getNodesByParam("fId", "${param.fWareHouseId}", null);
						treeObj.selectNode(nodes[0]);
						$('#role_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']); 
					}
				}
			});
			//客户区域end
			
			//医药公司分类 start
			$('#company_combo').combo({
				editable:false
			});//end combo
		
			$('#company_panel').appendTo($('#company_combo').combo('panel'));
			
			$.fn.zTree.init($("#company_tree"), {
				view:{
					showIcon:true,
					showLine:false
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllCompany2.do"
				},
				data: {
					key :{name:'fName'},
					simpleData: {
						enable: true,
						idKey: 'fId',
						pIdKey: 'parentId'
					}
				},callback: {
					onClick: function(){
						var treeObj = $.fn.zTree.getZTreeObj("company_tree");
						var nodes = treeObj.getSelectedNodes();
						$('#company_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						$('#company_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj("company_tree");
						var nodes = treeObj.getNodesByParam("fId", "${param.fCompanyId}", null);
						treeObj.selectNode(nodes[0]);
						$('#company_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']); 
					}
				}
			});
			//药品经营分类 end
			
			$("#selectedDrugOnly").datagrid({
				title : '已选药品',
				fit : true,
				fitColumns : true,
				rownumbers : true,
				idField : 'fId',
				columns:[[ 
					{field : 'fId',align:'center',checkbox : true},
					{field : 'fName',title : '药品名称',width : 100,align:'center'}, 
					{field : 'fSpecification',title : '药品规格',width : 100,align:'center'}, 
					{field : 'fAddress',title : '产地',width :100,align:'center'}
				]],
				toolbar : [
					{
						iconCls : "icon-add",
						text : '选择药品',
						handler : function(){
							merchantUserInfoComponent.selectedDrugOnly();
						}
					},
					{
						iconCls : "icon-no",
						text : '删除',
						handler : function(){
							$("#fDrugOnlyId").val('');
							$("#fName").val('');
							$("#fSpecification").val('');
							$("#fAddress").val('');
							var rows = $("#selectedDrugOnly").datagrid('getChecked');
							var len = rows.length;
							for(var i=0;i<len;i++){
								var ind = $("#selectedDrugOnly").datagrid('getRowIndex',rows[0]);
								$("#selectedDrugOnly").datagrid('deleteRow',ind);
							}
						}
					}
				]
			});
			
			//已选药品
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "drugOnly",
					"reqMethod" : "getSelectedList",
					"fId" : "${param.fDrugOnlyId}"
				},
				success : function(ret)
				{	
					$("#selectedDrugOnly").datagrid('loadData',ret.d);
				}
			});
		},
		formTools : new QM.Form("ticketInfoForm"),
		comit : function() {
			var fWareHouseId = $("#role_combo").combo("getValue");
			$("#fWareHouseId").val(fWareHouseId);
			var fCompanyId = $("#company_combo").combo("getValue");
			$("#fCompanyId").val(fCompanyId);
			/* var fId = $("#fId").val();
			var fName = $("#fName").val();
			var fSpecification = $("#fSpecification").val();
			var fAddress = $("#fAddress").val();
			var fBatchNumber = $("#fBatchNumber").val();
			var fWareHouseId = $("#role_combo").combo("getValue");
			var fExpiryDate = $("#fExpiryDate").val();
			var fNumber = $("#fNumber").val();
			var fPrice = $("#fPrice").val();
			var fSupplyPrice = $("#fSupplyPrice").val();
			var fRetailPrice = $("#fRetailPrice").val();
			var fIntro = $("#fIntro").val();
			
			ajaxTools.singleReq({
				data : {
					"reqUrl" : "drug",
					"reqMethod" : "edit",
					"fSpecification" : fSpecification,
					"fAddress" : fAddress,
					"fBatchNumber" : fBatchNumber,
					"fExpiryDate" : fExpiryDate,
					"fNumber" : fNumber,
					"fPrice" : fPrice,
					"fSupplyPrice" : fSupplyPrice,
					"fRetailPrice" : fRetailPrice,
					"fWareHouseId" : fWareHouseId,
					"fIntro" : fIntro,
					"fId" : fId,
					"fName" : fName
				},
				success : function(ret) {
					if (ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
						QM.dialog.doWinCallback(true, ret);
					} else {
						QM.dialog.doWinCallback(false, ret);
					}
					
				}
			}); */
			//选择的药品  start
			var customerData = $("#selectedDrugOnly").datagrid('getData');
			if(customerData['total']==0){
				QM.dialog.showFailedDialog("请选择药品！");
				return;
			}
			this.formTools.submitForm(function() {
						return true;
					});
		},
		close : function() {
			QM.dialog.closeWin();
		},
		showImgDialog : function(showDiv) {
			QM.dialog
					.openWinOther(
							{
								"title" : "新增",
								"width" : "800",
								"height" : "520"
							},
							GLOBAL_INFO.CONTEXTPATH
									+ "/omp/common/selectImgDialog.jsp?flg=1&showTabs=lib,upload,url",
							function(data) {
								var oResult;
								var fImage;
								var ihtml = "";
								if (data == null) {
									return;
								}
								try {
									oResult = eval("(" + data + ")");
								} catch (e) {
								}
								for ( var i = 0; i < oResult.length; i++) {
									fImage = oResult[i].src;
									fImage = fImage
											.substring("${ctx}".length);
									$("#fImage").val(fImage);
									ihtml = ihtml
											+ "<label class=\"labelImgDel\">";
									ihtml = ihtml
											+ "  <span><img src=\""
											+ oResult[i].src
											+ "\" class=\"act-img\" ></span>";
									ihtml = ihtml
											+ "  <span class=\"imgDel\" onclick=\"merchantUserInfoComponent.onDelImg(this);\"><a>X</a></span>";
									ihtml = ihtml + "</label>";
								}
								//隐藏添加图片区域
								$(".act-img").css("display", "none");
								//界面添加图片
								$("#" + showDiv).empty().append(ihtml);
							});
		},
		//删除图片
		onDelImg : function(o) {
			var obj = $(o).parent();
			obj.remove();
			$("#fImage").val("");//清空隐藏的图片地址
			//显示添加图片区域
			$(".act-img").css("display", "block");
		},
		selectedDrugOnly : function() {//选择客户
			var _d = $("#selectedDrugOnly").datagrid('getData');
			var _data;
			if(_d['total']==0){
				_data = "[]";
			}else{
				_data = JSON.encode(_d['rows']);
			}
			QM.dialog
					.openWinOther(
							{
								"title" : "选择药品",
								"width" : "800",
								"height" : "520"
							},
							GLOBAL_INFO.CONTEXTPATH
									+ "/omp/drug/selectDrugOnlyList.jsp?data="+_data,
							function(data) {
								var newD = eval(data);
								
								$("#selectedDrugOnly").datagrid('loadData',newD);
								
								var drugData = $("#selectedDrugOnly").datagrid('getData');
								var customer = drugData['rows'][0];
								$("#fDrugOnlyId").val(customer['fId']);
								$("#fName").val(customer['fName']);
								$("#fSpecification").val(customer['fSpecification']);
								$("#fAddress").val(customer['fAddress']);
							});
		}
	}
	$(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
