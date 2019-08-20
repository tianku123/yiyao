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
			return {
				"reqUrl" : "drugOnly",
				"reqMethod" : "getList_DrugOnlyIntro",
				"fName" : fName,
				"fDrugIntroName" : fDrugIntroName
			}
		},	
		init : function() {
			
			var columns = [ 
				{field : 'fId',align:'center',checkbox : false},
				{field : 'fDrugIntroName',title : '产品分类',width :50,align:'center'},
				{field : 'fName',title : '药品名称',width : 50,align:'center'}, 
				{field : 'fSpecification',title : '药品规格',width : 50,align:'center'}, 
				{field : 'fExpiryDate',title : '药品效期',width : 50,align:'center'},
				{field : 'fSupplyPrice',title : '供货价',width :50,align:'center'},
				{field : 'fRetailPrice',title : '零售价',width :50,align:'center'},
				{field : 'fNumber',title : '库存',width :50,align:'center'},
				{field : 'fIntro',title : '产品介绍',width :200,align:'center'
				},
				{field : 'fImg',title : '图片',width : 50,align:'center',
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
			
			toolbars.getPKConds = this.getPKConds;
			this.userDataGrid = new QM.dataGrid("userDataGrid", {
				title : "药品介绍列表",
				singleSelect : true,
				checkOnSelect : true,
				selectOnCheck : true,
				remoteSort : false,
				pagination : false,
				sortOrder : 'asc',
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
		showIntro : function(path) {	
			QM.dialog.openWin({"title" : "介绍"}, 
				GLOBAL_INFO.CONTEXTPATH + "/omp/sale/showDrugOnlyIntro.jsp?intro=" + path,
			function(ret) {

			});
		},
		exportA : function(){
			var fName = $("#fUserCode").val();
			var fDrugIntroName = $("#fDrugIntroName").val();
			location.href = "${ctx}system/exportDrugOnlyIntro.do?fName=" + fName +"&fDrugIntroName=" + fDrugIntroName;
		}
	}
	$(document).ready(function() {
	
		merchantUserInfoComponent.init();
	});
</script>
</html>
