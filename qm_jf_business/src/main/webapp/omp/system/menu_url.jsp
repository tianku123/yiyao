<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>商户角色权限管理</title>
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
	<script type="text/javascript" src="${contextPath}/resource/scripts/zTree_v3/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
function resize() {
	var bodyHeight = $(document.body).height();
	$('.treeColumn').eq(0).css('height', bodyHeight-74);
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
		<!-- 左侧模块树开始 -->
	<div data-options="region:'west',border:false" style="width:210px; padding:10px;">
		<div class="treeColumn">
		   <ul id="role_tree" class="ztree"></ul>
		</div>
	</div>
		<!-- 左侧模块树结束 -->
	<div data-options="region:'center',border:false" style="padding:10px 10px 10px 0;overflow:hidden;height:auto;"  >
      	<!-- 右侧权限树开始 -->
			<div class="treeColumn" style="width:98%;">
			<div id="warnning" style="display: none;">您没有可以分配的权限</div>
			     <ul id="url_tree" class="ztree"></ul>
			</div>
	</div>
	<div data-options="region:'south',border:false" style="text-align:center; padding:5px; background:#FFFFFF; border-top:1px #ccc solid;">
			<p class="alcenter mt10">
		        <button class="orangeBtn" onclick="funcQxComponent.submitClick()">提交</button>
		        <span class="ml15">
		          <button class="grayBtn" onclick="funcQxComponent.funTreeRest()">重置</button>
		        </span>
		        <span class="ml15">
		          <button class="grayBtn" onclick="funcQxComponent.expandAll()">全部展开</button>
		        </span>
		        <span class="ml15">
		          <button class="grayBtn" onclick="funcQxComponent.collapseAll()">全部收缩</button>
		        </span>
		        <span class="ml15">
		          <button class="grayBtn" onclick="funcQxComponent.selectAll()">全部选中</button>
		        </span>
	        </p>
	</div>
</body>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript" src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script language=javascript>
	var ajaxTools = new QM.ajax();
	var funcQxComponent =
	{
		//初始化
		init : function()
		{	
			//角色树  start
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
							var urlTree = $.fn.zTree.getZTreeObj("url_tree");
							var roleTree = $.fn.zTree.getZTreeObj(treeId);
							var nodes = roleTree.getSelectedNodes();
							/*if('999999'.indexOf(nodes[0]['roleCode'])!=-1 && (''+nodes[0]['roleCode']).indexOf('999999')!=-1){
								urlTree.destroy();
								return;
							}*/
							urlTree.setting.async.otherParam = ['roleCode',nodes[0]['roleCode']];
							urlTree.reAsyncChildNodes(null,"refresh");
						},
						onAsyncSuccess:function(event, treeId, treeNode, msg){
							var roleTree = $.fn.zTree.getZTreeObj(treeId);
							roleTree.expandAll(true);
							var treeObj = $.fn.zTree.getZTreeObj(treeId);
							var nodes = treeObj.getNodesByParam("roleCode", "admin", null);	
							treeObj.selectNode(nodes[0]);
						}
					}
				});
				//角色树  end
				
				
			//URL树  start
			$.fn.zTree.init($("#url_tree"), {
				view:{
						showIcon:true,
						showLine:false
					},
					async: {
						enable:true,
						dataType:'json',
						url: '${contextPath}/system/getMenuUrlList.do',
						otherParam : ['roleCode','admin']
						
					},
					data: {
						key :{name:'funcName'},
						simpleData: {
							enable: true,
							idKey: 'funcId',
							pIdKey: 'funcParentId'
						}
					},
					check: {
						enable:true
					},
					callback:{
						onClick:function(event,treeId,treeNode){
						},
						onAsyncSuccess:function(event, treeId, treeNode, msg){
							var treeObj = $.fn.zTree.getZTreeObj(treeId);
							treeObj.expandAll(true);
						}
					}
				});
				//URL树  end
		},
		
		//确认修改用户权限
		submitClick : function(){
			//角色id
			var roleTree = $.fn.zTree.getZTreeObj("role_tree");
			nodes = roleTree.getSelectedNodes();
			var roleCode = nodes[0]['roleCode'];
			//tree中所勾选的URL
			var urlTree = $.fn.zTree.getZTreeObj("url_tree");
			var nodes = urlTree.getCheckedNodes(true);
			
			var _urlId = '';
			for(var i=0;i<nodes.length;i++){
				if(i<nodes.length-1){
					_urlId += nodes[i]['funcId'] + ',';
				}else{
					_urlId += nodes[i]['funcId'];
				}
			}
			
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl"      : "menu",
					"reqMethod"   : "modifyFuncQX",
					"roleCode"    :  roleCode,
					"_urlId"    :  _urlId
				},
				success : function(ret)
				{
					if(ret.a == GLOBAL_INFO.SUCCESS_CODE)
					{
						QM.dialog.showSuccessDialog("修改成功");
					}
					else
					{
						QM.dialog.showFailedDialog("修改失败");
					}
				}.bind(this)
			});
		},
		
		//全部收缩
		collapseAll : function (){
			var urlTree = $.fn.zTree.getZTreeObj("url_tree");
			urlTree.expandAll(false);
		},
		
		//全部展开
		expandAll :	function (){
			var urlTree = $.fn.zTree.getZTreeObj("url_tree");
			urlTree.expandAll(true);
		},
		selectAll : function () {
			var treeObj = $.fn.zTree.getZTreeObj("url_tree");
			treeObj.checkAllNodes(true);
		},
		//重置权限树
		funTreeRest : function(){
			var treeObj = $.fn.zTree.getZTreeObj("url_tree");
			treeObj.reAsyncChildNodes(null, "refresh");
		}
	};
	
	//初始化方法
	$(document).ready(function(){
		 
		funcQxComponent.init();
	});
</script>
</html>
