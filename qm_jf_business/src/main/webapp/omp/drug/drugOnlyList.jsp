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
		<div data-options="region:'north',border:false" style="height:77px;">
		
		  <div class="searchColumn">
			<div class="keySearch">
				药品名称： <input type="search" placeholder="药品名称" id="fUserCode" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				药品介绍分类： <input type="search" placeholder="药品介绍分类" id="fDrugIntroName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				药品经营分类： <input type="search" placeholder="药品经营分类" id="fDrugPrinterName" onkeyup="enterEvent(event, 'merchantUserInfoComponent.userDataGrid.formQry()');"/>
				有无提成：<select id="isHasTc">
					<option value="please">请选择</option>
					<option value="no">无提成</option>
					<option value="yes">有提成</option>
				</select>
				<button class="grayBtn"
					onclick="merchantUserInfoComponent.userDataGrid.formQry()">查询</button>
				<button class="grayBtn" onclick="merchantUserInfoComponent.exportA()">导出</button>
			</div>
			<p class="shrink" onClick="resizeNorth();">收起搜索栏</p>
		  </div>
		</div>
		<div data-options="region:'center',border:false">
			<table id="userDataGrid">
			</table>
		</div>
</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script>
	var ajaxTools = new QM.ajax();
	var merchantUserInfoComponent = {
		userDataGrid : null,
		getQueryParams : function() {
			var fName = $("#fUserCode").val();
			var fDrugIntroName = $("#fDrugIntroName").val();
			var fDrugPrinterName = $("#fDrugPrinterName").val();
			var isHasTc = $("#isHasTc").val();
			return {
				"reqUrl" : "drugOnly",
				"reqMethod" : "getList",
				"fName" : fName,
				"fDrugIntroName" : fDrugIntroName,
				"isHasTc" : isHasTc,
				"fDrugPrinterName" : fDrugPrinterName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : true},
				{field : 'fName',title : '药品名称',width : 100,align:'center'}, 
				{field : 'fSpecification',title : '药品规格',width : 100,align:'center'}, 
				{field : 'fAddress',title : '产地',width :100,align:'center'},
				{field : 'fSupplyPrice',title : '供货价',width :100,align:'center'},
				{field : 'fRetailPrice',title : '零售价',width :100,align:'center'},
				{field : 'fXqTc',title : '主管提成',width :100,align:'center'},
				{field : 'fDqTc',title : '大区提成',width :100,align:'center'},
				{field : 'fDepartmentName',title : '部门',width :100,align:'center'},
				{field : 'fDrugIntroName',title : '产品介绍分类',width :100,align:'center'},
				{field : 'fDrugPrinterName',title : '产品经营分类',width :100,align:'center'}, 
				{field : 'fImg',title : '图片',width : 120,align:'center',
					formatter : function(value,row,index){
						if(value!=null && value!=""){
							return "<a onclick='javascript:merchantUserInfoComponent.showImage(\""+value+"\")'>"+
								"<img src='${ctx}" + value+"'  width='30' height='30' />"+"</a>";
						}else{
							return "无"
						}
					}
				}
			];
			var toolbars = {};
			toolbars.btns = [ "ADD", "EDIT" ];
			toolbars.urls = {
				"ADD" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/drug/addDrugOnly.jsp",
				"EDIT" : GLOBAL_INFO.CONTEXTPATH
						+ "/omp/drug/editDrugOnly.jsp"
			};
			toolbars.newBtns = [
				
 				{iconCls: 'icon-no',text:'删除',handler: function(){
						var selRows = $("#userDataGrid").datagrid("getSelections");
						if(!selRows || selRows.length != 1)
						{
							QM.dialog.showFailedDialog("请选择要删除的数据，只能选取单个数据删除！");
							return false;
						}else{	
							QM.dialog.showConfirmDialog("是否确定删除?", function(flg) {
								if(flg) {
									merchantUserInfoComponent.deleteMerUser(selRows[0].fId);
								}else {
									return false;
								}
							});
						}
				  	 }
				 }
			];
			toolbars.getPKConds = this.getPKConds;
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
		getPKConds : function(selRow) {	
			return {
				"queryStr" : "fId=" + selRow.fId
							+"&fName=" + encodeURI(selRow.fName)
							+"&fAddress=" + encodeURI(selRow.fAddress)
							+"&fSpecification=" + selRow.fSpecification
							+"&fSupplyPrice=" + selRow.fSupplyPrice
							+"&fRetailPrice=" + selRow.fRetailPrice
							+"&fImg=" + selRow.fImg
							+"&fIntro=" + selRow.fIntro
							+"&fDrugPrinterId=" + selRow.fDrugPrinterId
							+"&fDrugIntroId=" + selRow.fDrugIntroId
							+"&fDepartmentId=" + selRow.fDepartmentId
							+"&fDepartmentName=" + selRow.fDepartmentName
							+"&fDqTc=" + selRow.fDqTc
							+"&fXqTc=" + selRow.fXqTc
							,
				"queryJson" : {
					"fCode" : selRow.fId
				}
			};
		},
		deleteMerUser : function (fCode) {
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "drugOnly",
					"reqMethod" : "delete",
					"fId" : fCode
				},
				success : function(ret)
				{	
					if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
						
						QM.dialog.showSuccessDialog("删除成功");
						merchantUserInfoComponent.userDataGrid.formQry();
					}else {
						QM.dialog.showFailedDialog("删除失败");
					}
				}
			});
		},
		showImage : function(path) {	
			QM.dialog.openWin({"title" : "图片预览"}, 
				GLOBAL_INFO.CONTEXTPATH + "/omp/common/previewImg.jsp?img=" + path,
			function(ret) {

			});
		},
		exportA : function(){
			
			location.href = "${ctx}system/exportDrugOnly.do";
		} 
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
