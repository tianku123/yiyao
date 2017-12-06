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
		<input type="hidden" name="reqUrl" id="reqUrl" value="drugOnly" /> 
		<input type="hidden" name="reqMethod" id="reqMethod" value="edit" />
		<input type="hidden" name="fId" id="fId" value="${param.fId }" />
		
		<input type="hidden" name="fDrugIntroId" id="fDrugIntroId" value="${param.fDrugIntroId }"/>
		<input type="hidden" name="fDrugPrinterId" id="fDrugPrinterId" value="${param.fDrugPrinterId }"/>
		<input type="hidden" name="fDepartmentId" id="fDepartmentId" value="${param.fDepartmentId }"/>
		<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
			<tr>
				<th width="15%">
					<label>药品名称：</label>
				</th>
				<td width="35%">
					<input id="fName" name="fName" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" value="${param.fName }"/>
				</td>
				
				<th width="15%">
					<label>药品规格：</label>
				</th>
				<td width="35%">
					<input id="fSpecification" name="fSpecification" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" value="${param.fSpecification }"/>
				</td>
				
			</tr>
			<tr>
				<th width="15%">
					<label>产地：</label>
				</th>
				<td width="35%">
					<input id="fAddress" name="fAddress" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true" value="${param.fAddress }"/>
				</td>
				
				<th width="15%"><label>部门</label></th>
				<td width="35%">
					<select id="department_combo" name="department" style="width: 150px;"></select>
					<div id="department_panel">
						<ul id="department_tree" class="ztree"></ul>
					</div>
				</td>
				
			</tr>
			<tr>
				<th width="15%"><label>药品介绍分类：</label></th>
				<td width="35%">
					<select id="intro_combo" name="intro" style="width: 150px;"></select>
					<div id="intro_panel">
						<ul id="intro_tree" class="ztree"></ul>
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
				<th width="15%">
					<label>供货价：</label>
				</th>
				<td width="35%">
					<input id="fSupplyPrice" name="fSupplyPrice" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"  value="${param.fSupplyPrice }"/>
				</td>
				
				<th width="15%">
					<label>零售价：</label>
				</th>
				<td width="35%">
					<input id="fRetailPrice" name="fRetailPrice" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'"  value="${param.fRetailPrice }"/>
				</td>
				
			</tr>
			<tr>
					<th width="15%">
						<label>主管提成：</label>
					</th>
					<td width="35%">
						<input id="fXqTc" name="fXqTc" value="${param.fXqTc }" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" />
					</td>
					<th width="15%">
						<label>大区提成：</label>
					</th>
					<td width="35%">
						<input id="fDqTc" name="fDqTc" value="${param.fDqTc }" type="text" autofocus="autofocus" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'" />
					</td>
			</tr>
			<tr>
				<input type="hidden" id="fImage" name="fImage">
				<th><label>封面图片：</label></th>
				<td colspan="3">
					<div id="frontfile"></div> <label class="act-img" onclick="merchantUserInfoComponent.showImgDialog('frontfile');"> <a><br />点击添加<br />封面图片</a> </label>
                       
                       </td>
			</tr>
			<tr>
				<th width="15%"><label>药品介绍：</label></th>
				<td colspan="3">
					<textarea id="fIntro" name="fIntro" rows="3" cols="100"><c:out value="${param.fIntro}"></c:out></textarea>
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
			var fImage = "${param.fImg}";
			if(fImage!=null && fImage!=''){
				$("#fImage").val(fImage);
				var ihtml = "<label class='labelImgDel'>";
					ihtml = ihtml + "  <span><img src='${ctx}"
							+ fImage
							+ "' class='act-img' ></span>";
					ihtml = ihtml + "  <span class='imgDel' onclick='merchantUserInfoComponent.onDelImg(this);'><a>X</a></span>";
					ihtml = ihtml + "</label>";
				//隐藏添加图片区域
				$(".act-img").css("display", "none");
				//界面添加图片
				$("#frontfile").empty().append(ihtml);
			}
			
			
			//药品介绍分类 start
			$('#intro_combo').combo({
				editable:false
			});//end combo
		
			$('#intro_panel').appendTo($('#intro_combo').combo('panel'));
			
			$.fn.zTree.init($("#intro_tree"), {
				view:{
					showIcon:true
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllDrugIntro.do"
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
						var treeObj = $.fn.zTree.getZTreeObj("intro_tree");
						var nodes = treeObj.getSelectedNodes();
						$('#intro_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						$('#intro_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj("intro_tree");
						var nodes = treeObj.getNodesByParam("fId", "${param.fDrugIntroId}", null);
						treeObj.selectNode(nodes[0]);
						if (nodes.length > 0) {
							$('#intro_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']); 
						}
					}
				}
			});
			//药品介绍分类 end
			
			
			//药品经营分类 start
			$('#printer_combo').combo({
				editable:false
			});//end combo
		
			$('#printer_panel').appendTo($('#printer_combo').combo('panel'));
			
			$.fn.zTree.init($("#printer_tree"), {
				view:{
					showIcon:true
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllDrugPrinter.do"
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
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj("printer_tree");
						var nodes = treeObj.getNodesByParam("fId", "${param.fDrugPrinterId}", null);
						treeObj.selectNode(nodes[0]);
						if (nodes.length > 0) {
							$('#printer_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						} 
					}
				}
			});
			//药品经营分类 end
			
			//部门 start
			$('#department_combo').combo({
				editable:false
			});//end combo
		
			$('#department_panel').appendTo($('#department_combo').combo('panel'));
			
			$.fn.zTree.init($("#department_tree"), {
				view:{
					showIcon:true
				},
				async: {
					enable:true,
					dataType:'json',
					url: "${contextPath}/system/getAllDepartment.do"
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
						var treeObj = $.fn.zTree.getZTreeObj("department_tree");
						var nodes = treeObj.getSelectedNodes();
						$('#department_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']);
						$('#department_combo').combo('hidePanel');
					},
					onAsyncSuccess:function(event, treeId, treeNode, msg){
						var treeObj = $.fn.zTree.getZTreeObj("department_tree");
						var nodes = treeObj.getNodesByParam("fId", "${param.fDepartmentId}", null);
						treeObj.selectNode(nodes[0]);
						if (nodes.length > 0) {
							$('#department_combo').combo('clear').combo('setText',nodes[0]['fName']).combo('setValue',nodes[0]['fId']); 
						}
					}
				}
			});
			//部门 end
			
		},
		formTools : new QM.Form("ticketInfoForm"),
		comit : function() {
			var fDrugIntroId = $("#intro_combo").combo("getValue");
			$("#fDrugIntroId").val(fDrugIntroId);
			var fDrugPrinterId = $("#printer_combo").combo("getValue");
			$("#fDrugPrinterId").val(fDrugPrinterId);
			var fDepartmentId = $("#department_combo").combo("getValue");
			$("#fDepartmentId").val(fDepartmentId);
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
								$("#" + showDiv).append(ihtml);
							});
		},
		//删除图片
		onDelImg : function(o) {
			var obj = $(o).parent();
			obj.remove();
			$("#fImage").val("");//清空隐藏的图片地址
			//显示添加图片区域
			$(".act-img").css("display", "block");
		}
	}
	$(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
