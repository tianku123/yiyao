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
		<input type="hidden" id="fId" name="fId" value=${ param.fId } />
		<input type="hidden" name="reqUrl" id="reqUrl" value="customer" /> 
		<input type="hidden" name="reqMethod" id="reqMethod" value="edit" />
		<input type="hidden" name="fCityId" id="fCityId" />
		<input type="hidden" name="fDrugPrinterIds" id="fDrugPrinterIds" />
		<input type="hidden" name="fCompanyIds" id="fCompanyIds" />
		<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
			<tr>
				<th width="15%">
					<label>经营者：</label>
				</th>
				<td width="35%">
					<input id="fName" name="fName" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
				</td>
				
				<th width="15%">
					<label>购货单位：</label>
				</th>
				<td width="35%">
					<input id="fUnit" name="fUnit" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
				</td>
				
			</tr>
			<tr>
				<th width="15%">
					<label>购货地址：</label>
				</th>
				<td width="35%">
					<input id="fAddress" name="fAddress" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
				</td>
				
				<th width="15%">
					<label>联系方式：</label>
				</th>
				<td width="35%">
					<input id="fPhone" name="fPhone" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" />
				</td>
				
			</tr>
			<tr>
				<th width="15%"><label>委托书到期日期：</label></th>
				<td colspan="3"><input id="fBeginTime" name="fBeginTime"
					type="text" class="Wdate"
					onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})"
					style="width:150px;" /> 许可证到期日期： <input id="fEndTime" name="fEndTime"
					type="text" class="Wdate"
					onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd',isShowClear:false,readOnly:true})"
					style="width:150px;" />
				</td>
			</tr>
			<tr>
				<th width="15%">
					<label>生日：</label>
				</th>
				<td width="35%">
					<input id="fBirthday" name="fBirthday" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'yyyyMMdd'" />
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
				<th width="15%"><label>客户所属区域：</label></th>
				<td width="35%">
					<select id="role_combo" name="PROVENCE" style="width: 150px;"></select>
					<div id="role_panel">
						<ul id="role_tree" class="ztree"></ul>
					</div>
				</td>
				
				<th width="15%"><label>药品经营分类：</label></th>
				<td width="35%">
					<select id="printer_combo" name="printer" style="width: 150px;"></select>
					<div id="printer_panel">
						<ul id="printer_tree" class="ztree"></ul>
					</div>
				</td>
			</tr>
			<tr>
				<th width="15%"><label>备注：</label></th>
				<td colspan="3">
					<input id="fRemark" name="fRemark" type="text" autofocus="autofocus" class="easyui-validatebox" style="width:400px;"/>
				</td>
			</tr>
		</table>
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
			$("#fUnit").val("${param.fUnit}");
			$("#fAddress").val("${param.fAddress}");
			$("#fPhone").val("${param.fPhone}");
			$("#fBeginTime").val("${param.fBeginTime}");
			$("#fEndTime").val("${param.fEndTime}");
			$("#fRemark").val("${param.fRemark}");
			$("#fBirthday").val("${param.fBirthday}");
		
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
					url: "${contextPath}/system/getAllCity.do",
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
						var nodes = treeObj.getNodesByParam("fId", "${param.fCityId}", null);
						treeObj.selectNode(nodes[0]);
						$('#role_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
					}
				}
			});
			//客户区域end
			
			//药品经营分类 start
			$('#printer_combo').combo({
				editable:false
			});//end combo
		
			$('#printer_panel').appendTo($('#printer_combo').combo('panel'));
			
			$.fn.zTree.init($("#printer_tree"), {
				view:{
					showIcon:true,
					showLine:false
				},
				check: {
					enable: true
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllDrugPrinter2_checked.do",
					otherParam : ['customerId',"${param.fId}"]
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
						var treeObj = $.fn.zTree.getZTreeObj("printer_tree");
						var nodes = treeObj.getSelectedNodes();
						$('#printer_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						$('#printer_combo').combo('hidePanel');
					},
					onCheck: function(){
						var urlTree = $.fn.zTree.getZTreeObj("printer_tree");
						var nodes = urlTree.getCheckedNodes(true);
						
						var _urlId = '';
						var _urlName = '';
						for(var i=0;i<nodes.length;i++){
							if(i<nodes.length-1){
								_urlId += nodes[i]['fId'] + ',';
								_urlName += nodes[i]['fName'] + ',';
							}else{
								_urlId += nodes[i]['fId'];
								_urlName += nodes[i]['fName'];
							}
						}
						$('#printer_combo').combo('clear').combo('setText',_urlName).combo('setValue',_urlId);
						$('#printer_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var urlTree = $.fn.zTree.getZTreeObj("printer_tree");
						var nodes = urlTree.getCheckedNodes(true);
						
						var _urlId = '';
						var _urlName = '';
						for(var i=0;i<nodes.length;i++){
							if(i<nodes.length-1){
								_urlId += nodes[i]['fId'] + ',';
								_urlName += nodes[i]['fName'] + ',';
							}else{
								_urlId += nodes[i]['fId'];
								_urlName += nodes[i]['fName'];
							}
						}
						$('#printer_combo').combo('clear').combo('setText',_urlName).combo('setValue',_urlId);
						
					}
				}
			});
			//药品经营分类 end
			
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
				check: {
					enable: true
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllCompany_checked.do",
					otherParam : ['customerId',"${param.fId}"]
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
					onCheck: function(){
						var urlTree = $.fn.zTree.getZTreeObj("company_tree");
						var nodes = urlTree.getCheckedNodes(true);
						
						var _urlId = '';
						var _urlName = '';
						for(var i=0;i<nodes.length;i++){
							if(i<nodes.length-1){
								_urlId += nodes[i]['fId'] + ',';
								_urlName += nodes[i]['fName'] + ',';
							}else{
								_urlId += nodes[i]['fId'];
								_urlName += nodes[i]['fName'];
							}
						}
						$('#company_combo').combo('clear').combo('setText',_urlName).combo('setValue',_urlId);
						$('#company_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var urlTree = $.fn.zTree.getZTreeObj("company_tree");
						var nodes = urlTree.getCheckedNodes(true);
						
						var _urlId = '';
						var _urlName = '';
						for(var i=0;i<nodes.length;i++){
							if(i<nodes.length-1){
								_urlId += nodes[i]['fId'] + ',';
								_urlName += nodes[i]['fName'] + ',';
							}else{
								_urlId += nodes[i]['fId'];
								_urlName += nodes[i]['fName'];
							}
						}
						$('#company_combo').combo('clear').combo('setText',_urlName).combo('setValue',_urlId);
					}
				}
			});
			//药品经营分类 end
			
		},
		formTools : new QM.Form("ticketInfoForm"),
		comit : function() {
			var fCityId = $("#role_combo").combo("getValue");
			var fDrugPrinterIds = $("#printer_combo").combo("getValue");//商户经营药品分类权限
			var fCompanyIds = $("#company_combo").combo("getValue");//商户经营公司权限
			var fBeginTime = $("#fBeginTime").val();
			var fEndTime = $("#fEndTime").val();
			if(fEndTime == '' || fBeginTime == ''){
				alert("请选择到期日期!");
				return false;
			}
			if(fCityId == ''){
				alert("请选择客户所属区域!");
				return false;
			}
			if(fDrugPrinterIds == ''){
				alert("请选择药品经营分类!");
				return false;
			}
			if(fCompanyIds == ''){
				alert("请选择药品公司!");
				return false;
			}
			
			$("#fCityId").val(fCityId);
			$("#fDrugPrinterIds").val(fDrugPrinterIds);
			$("#fCompanyIds").val(fCompanyIds);
			
			this.formTools.submitForm(function() {
						return true;
					});
		},
		close : function() {
			QM.dialog.closeWin();
		}
	}
	$(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
