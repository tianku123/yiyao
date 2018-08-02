<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>管理系统</title>
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
					<button class="grayBtn"
							onclick="merchantUserInfoComponent.userDataGrid.formQry()">查询</button>
					</div>
					
				</div>
				<div data-options="region:'center',border:false" >
						<!-- 数据部分 -->
						<table id="userDataGrid">
						</table>
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
			return {
				"reqUrl" : "drugOnly",
				"reqMethod" : "getList",
				"fName" : fName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : true},
				{field : 'fName',title : '药品名称',width : 100,align:'center'}, 
				{field : 'fSpecification',title : '药品规格',width : 100,align:'center'}, 
				{field : 'fAddress',title : '产地',width :100,align:'center'}
			];
			var toolbars = {};
			
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "药品列表",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				sortOrder : 'asc',
				columns : [ columns ]
			}, toolbars, this.getQueryParams);
			this.userDataGrid.init();
			$(".datagrid-header-check input").hide();
		},
		ok :function(){
			
			var _arr = $('#userDataGrid').datagrid('getChecked');
			if(_arr == 'undefined' || _arr.length<=0){
				QM.dialog.showFailedDialog("请选择要添加的记录！");
			}else{
				
				var res = "[";
				
					for(var i=0;i<_arr.length;i++){
						var node = _arr[i];
						var fId = ''+node['fId'];//药品编码
						if(!merchantUserInfoComponent.removeDuplicate(fId)){//去重
							if(i==_arr.length-1){
								res += "{fId:'"+node['fId']
								+"',fName:'"+node['fName']
								+"',fSpecification:'"+node['fSpecification']
								+"',fAddress:'"+node['fAddress']+"'}";
							}else{
								res += "{fId:'"+node['fId']
								+"',fName:'"+node['fName']
								+"',fSpecification:'"+node['fSpecification']
								+"',fAddress:'"+node['fAddress']+"'},";
							}
						}
					}
				
				res += "]";
			    QM.dialog.doWinOtherCallback(true, res);
			}
		},
		cancel : function(){
	       QM.dialog.closeWinOther();
		},
		removeDuplicate : function(str){//去除重复数据
			var flag = false;
			var _d = '${param.data}';
			var _arrSel;
			if(_d!=null && _d!=''){
				_arrSel = JSON.decode(_d);
				for(var j=0;j<_arrSel.length;j++){
						var node2 = _arrSel[j];
						var fId2 = ''+node2['fId'];//药品编码
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
