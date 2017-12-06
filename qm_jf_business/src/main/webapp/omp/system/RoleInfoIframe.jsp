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
		<link rel="stylesheet" href="${contextPath}/resource/scripts/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript"
	src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/plugin/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
function resize() {
	var bodyHeight = $(document.body).height();
	$('.treeColumn').eq(0).css('height', bodyHeight-20);
}
$(document).ready(function() {
	resize();
});
window.onresize = function(){
	resize();
}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'west',border:false" style="width:210px; padding:10px;">
		<div class="treeColumn">
			<p class="ptl10">右键新建和编辑角色信息</p>
			<ul id="role_tree" class="ztree"></ul>
			<div id="mm" class="easyui-menu" style="width:120px;">
				<div onclick="merchantRoleInfoIframe.append()" data-options="iconCls:'icon-add'">新增</div>
				<div onclick="merchantRoleInfoIframe.edit()" data-options="iconCls:'icon-edit'">修改</div>
				<div onclick="merchantRoleInfoIframe.remove()" data-options="iconCls:'icon-remove'">删除</div>
			</div>
		</div>
	</div>
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 0;">
		<iframe id="RoleInfoFrame"
			src=""
			style="width:100%;height:100%" marginwidth="0" marginheight="0"
			scrolling="auto" frameborder="no"></iframe>
	</div>
</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js"></script>
<script>
var ajaxTools = new QM.ajax();
var merchantRoleInfoIframe = 
{
	init : function(id) {
		$.fn.zTree.init($("#role_tree"), {
			view:{
					showIcon:true,
					showLine:false
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
				},
				callback:{
					onClick:function(event,treeId,treeNode){
						$("#RoleInfoFrame").attr("src","${contextPath}/omp/system/RoleInfoDetail.jsp"+"?roleCode="+treeNode['roleCode']);
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						var nodes = treeObj.getNodesByParam("roleCode", "admin", null);	
						treeObj.selectNode(nodes[0]);
						$("#RoleInfoFrame").attr("src","${contextPath}/omp/system/RoleInfoDetail.jsp"+"?roleCode=admin");
					},
					onRightClick : function(e, treeId, treeNode){
						
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						treeObj.selectNode(treeNode);
						e.preventDefault();
						$('#mm').menu('show',{
							left: e.pageX,
							top: e.pageY
						});
					}
				}
			});
		
	},
	append : function() {
		 
		 top.QM.dialog.openWin(
			{"title" : "新增角色"},
			GLOBAL_INFO.CONTEXTPATH + "/omp/system/addRole.jsp?",
			function(ret) {
				if(ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
					QM.dialog.showSuccessDialog("新增成功");
					//$("#menuTree").tree("loadData",ret);
				}else {
					QM.dialog.showFailedDialog("新增出错");
					
				}
				merchantRoleInfoIframe.init(ret.d);
			}
		 );		 
	},
	edit : function() {
		var treeObj = $.fn.zTree.getZTreeObj("role_tree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes!=null && nodes.length>1){
			QM.dialog.showFailedDialog("只能选择一个角色");
			return;
		}
		 top.QM.dialog.openWin(
			{"title" : "修改角色"},
			GLOBAL_INFO.CONTEXTPATH + "/omp/system/editRole.jsp?roleCode="+nodes[0]['roleCode'],
			function(ret) {
				if(ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
					QM.dialog.showSuccessDialog("修改成功");
				}else {
					QM.dialog.showFailedDialog("修改出错");
				}
				merchantRoleInfoIframe.init(ret.d);
			}
		 );	
		
	},
	remove : function() {
	 	var treeObj = $.fn.zTree.getZTreeObj("role_tree");
		var nodes = treeObj.getSelectedNodes();
		if(nodes!=null && nodes.length>1){
			QM.dialog.showFailedDialog("只能选择一个角色");
			return;
		}
		if(nodes[0]['roleCode'] =='admin'
			|| nodes[0]['roleCode'] =='caiwu'
			|| nodes[0]['roleCode'] =='daquzhuguan'
			|| nodes[0]['roleCode'] =='fahuo'
			|| nodes[0]['roleCode'] =='xiaoquzhuguan'
			|| nodes[0]['roleCode'] =='yewuyuan'
		){
			QM.dialog.showFailedDialog("不能删除系统管理员角色");
			return;
		}
 	 	 top.QM.dialog.showConfirmDialog("是否确定删除?",function(flg) {
		 if(flg) {
			 //删除指定角色
			 ajaxTools.singleReq({
					data : 
					{
						"reqUrl" : "role",
						"reqMethod" : "deleteRole",
						"roleCode" : nodes[0]['roleCode']
					},
					success : function(ret)
					{
						if(ret && ret.a == GLOBAL_INFO.SUCCESS_CODE)
							{
								QM.dialog.showSuccessDialog("删除成功");
								merchantRoleInfoIframe.init(0);
							}
							else
							{
								QM.dialog.showFailedDialog(ret.c);
							}
						
					}
			 });
			}
 		});
	 	 
	}
}
$(function(){
	merchantRoleInfoIframe.init(0);
});
</script>
</html>