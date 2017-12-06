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
		
		<div data-options="region:'center',border:false">
			<table id="userDataGrid">
			</table>
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
			return {
				"reqUrl" : "repayment",
				"reqMethod" : "getList",
				"fOrderId" : "${param.fId}"
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : true},
				{field : 'fMoney',title : '还款金额',width :100,align:'center'}, 
				{field : 'fSource',title : '还款方式',width :100,align:'center',
					formatter: function(value,row,index){
						if(value=='0'){
							return "对公账号";
						}else if(value=='1'){
							return "对私";
						}else if(value=='2'){
							return "其他";
						}
					}
				}, 
				{field : 'fIntro',title : '备注',width :100,align:'center'}, 
				{field : 'fTime',title : '还款时间',width :100,align:'center',
					formatter: function(value,row,index){
						return formatDate14(value);
					}
				}
			];
			var toolbars = {};
			toolbars.newBtns = [
				
			];
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "还款记录",
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
							+"&fMoney=" + selRow.fMoney
							+"&fSource=" + selRow.fSource
							+"&fIntro=" + selRow.fIntro
							,
				"queryJson" : {
					"fCode" : selRow.fId
				}
			};
		}
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
