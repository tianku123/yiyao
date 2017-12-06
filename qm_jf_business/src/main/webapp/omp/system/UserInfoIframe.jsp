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
	<link rel="stylesheet" href="${contextPath}/resource/scripts/zTree_v3/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link
	href="${contextPath}/resource/scripts/plugin/easyui/themes/icon.css"
	rel="stylesheet" type="text/css" />
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
			<ul id="role_tree" class="ztree"></ul>
		</div>
	</div>
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 0;">
			<iframe id="UserInfoFrame"
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
$(function(){
	
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
						$("#UserInfoFrame").attr("src","${contextPath}/omp/system/UserInfoList.jsp"+"?roleCode="+treeNode['roleCode']);
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj(treeId);
						var nodes = treeObj.getNodesByParam("roleCode", "admin", null);	
						treeObj.selectNode(nodes[0]);
						$("#UserInfoFrame").attr("src","${contextPath}/omp/system/UserInfoList.jsp"+"?roleCode=admin");
					}
				}
			});
		
		
});
</script>
</html>