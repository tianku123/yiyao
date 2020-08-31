<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
	<link rel="stylesheet" href="${contextPath}/resource/scripts/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
</head>
<body class="easyui-layout">
	
		<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
			<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
				<tr>
					<th width="15%"><label>用户编码：</label></th>
					<td width="35%">
						<input id="fUserCode" name="fUserCode" type="text" autofocus="autofocus" disabled="disabled" />
					</td>
					<th width="15%"><label>姓名：</label></th>
					<td width="35%">
						<input id="fUserName" name="fUserName" type="text" autofocus="autofocus" class="easyui-validatebox"  data-options="required:true" />
					</td>
				</tr>
				<tr>
					<th><label>所属角色：</label></th>
					<td>
						 <select id="role_combo" name="PROVENCE" style="width: 150px;"></select>
						<div id="role_panel">
							<ul id="role_tree" class="ztree"></ul>
						</div>
					</td>
					<th><label>联系电话：</label></th>
					<td>
						<input  id="fUserTel" name="fUserTel" type="text" autofocus="autofocus" class="easyui-validatebox" />
					</td>
				</tr>
				<tr>
					<th><label>所属区域：</label>
					</th>
					<td>
					
						 <select id="customer_combo" name="customer" style="width: 150px;"></select>
						<div id="customer_panel">
							<ul id="customer_tree" class="ztree"></ul>
						</div>
					</td>
				
					<th width="15%"><label>部门</label></th>
					<td width="35%">
						<select id="department_combo" name="department" style="width: 150px;"></select>
						<div id="department_panel">
							<ul id="department_tree" class="ztree"></ul>
						</div>
					</td>
				</tr>
			</table>
			</div>
		<div data-options="region:'south',border:false" style="text-align:right; padding:5px; background:#f4f4f4; border-top:1px #ccc solid;">
				<button class="orangeBtn" onclick="merchantUserInfoComponent.comit();return false;">提交</button><button class="grayBtn" onclick="merchantUserInfoComponent.close()">关闭</button>
		</div>

</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script>
	var ajaxTools = new QM.ajax();
	var merchantUserInfoComponent = {	
		init : function () 	{
			$("#fUserCode").val("${param.fUserCode}");
			$("#fUserName").val("${param.fUserName}");
			$("#fUserTel").val("${param.fUserTel}");
			
			//角色参数start
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
					url: '${contextPath}/system/getRoleList.do',
					otherParam : {'roleType' : '0'}
				},
				data: {
					key :{name:'roleName'},
					simpleData: {
						enable: true,
						idKey: 'roleCode',
						pIdKey: 'parentId'
					}
				},callback: {
					onClick: function(){
						var treeObj = $.fn.zTree.getZTreeObj("role_tree");
						var nodes = treeObj.getSelectedNodes();
						$('#role_combo').combo('clear').combo('setText',nodes[0]['roleName']).combo('setValue',nodes[0]['roleCode']);
						$('#role_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj("role_tree");
						var nodes = treeObj.getNodesByParam("roleCode", "${param.roleCode}", null);
						treeObj.selectNode(nodes[0]);
						if (nodes.length > 0) {
							$('#role_combo').combo('clear').combo('setText',nodes[0]['roleName']).combo('setValue',nodes[0]['roleCode']);
						}
					}
				}
			});
			//角色参数end	
			
			
			//客户区域start
			$('#customer_combo').combo({
				editable:false
			});//end combo
		
			$('#customer_panel').appendTo($('#customer_combo').combo('panel'));
			
			$.fn.zTree.init($("#customer_tree"), {
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
						var treeObj = $.fn.zTree.getZTreeObj("customer_tree");
						var nodes = treeObj.getSelectedNodes();
						$('#customer_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						$('#customer_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj("customer_tree");
						var nodes = treeObj.getNodesByParam("fId", "${param.fCityId}", null);
						treeObj.selectNode(nodes[0]);
						if (nodes.length > 0) {
							$('#customer_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						}
					}
				}
			});
			//客户区域end	
			
			//部门 start
			$('#department_combo').combo({
				editable:false
			});//end combo
		
			$('#department_panel').appendTo($('#department_combo').combo('panel'));
			
			$.fn.zTree.init($("#department_tree"), {
				view:{
					showIcon:true
				},
				check: {
					enable: true
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllDepartment_checked.do",
					otherParam : ['userId',"${param.fId}"]
				},
				data: {
					key :{name:'fName'},
					simpleData: {
						enable: true,
						idKey: 'fId',
						pIdKey: 'parentId'
					}
				},callback: {
					onCheck: function(){
						var urlTree = $.fn.zTree.getZTreeObj("department_tree");
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
						$('#department_combo').combo('clear').combo('setText',_urlName).combo('setValue',_urlId);
						$('#department_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var urlTree = $.fn.zTree.getZTreeObj("department_tree");
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
						$('#department_combo').combo('clear').combo('setText',_urlName).combo('setValue',_urlId);
					}
				}
			});
			//部门 end
		},
		comit : function() {
			//获取参数
			var fUserCode = $("#fUserCode").val();
			var fUserName = $("#fUserName").val();
			var fUserRoleRel = $("#role_combo").combo("getValue");
			var fCityId = $("#customer_combo").combo("getValue");
			var fDepartmentIds = $("#department_combo").combo("getValue");
			var fUserTel = $("#fUserTel").val();
			var result = $("#fUserName").validatebox("isValid");
			if(result==false || $.trim(fUserName)=='') {
				QM.dialog.showFailedDialog("用户名不能为空！");
	            return false;
			}
			if("999999".indexOf(fUserRoleRel)!=-1 && fUserRoleRel.indexOf("999999")!=-1){
				QM.dialog.showFailedDialog("请选择角色！");
				return;
			}
			
			var fUserType = 0;//终端异常通知角色2，终端登陆角色1、后台角色0，决定用户类型,2：终端异常通知用户，1：终端用户，0：后台用户
			if("terminalUser".indexOf(fUserRoleRel)!=-1 && fUserRoleRel.indexOf("terminalUser")!=-1){
				fUserType = 1;
			}else if("terminalPhoneUser".indexOf(fUserRoleRel)!=-1 && fUserRoleRel.indexOf("terminalPhoneUser")!=-1){
				fUserType = 2;
			}
			ajaxTools.singleReq({
					data : 
					{
						"reqUrl" : "user",
						"reqMethod" : "editUser",
						"fUserCode" : fUserCode,
						"fUserName" : fUserName,
						"fUserRoleRel" : fUserRoleRel,
						"fCityId" : fCityId,
						"fDepartmentIds" : fDepartmentIds,
						"fUserTel" : fUserTel,
						"fUserType" : fUserType,
						"fId" : "${param.fId}"
					},
					success : function(ret)
					{
						if(ret && ret.a == GLOBAL_INFO.SUCCESS_CODE)
						{
							QM.dialog.doWinCallback(true, ret);
						}
						else
						{
							QM.dialog.doWinCallback(false, ret);
						}
					}
			});	
		},
		close : function () {
			QM.dialog.closeWin();
		}
	}
	$(function () {
		merchantUserInfoComponent.init();
	});
</script>
</html>
