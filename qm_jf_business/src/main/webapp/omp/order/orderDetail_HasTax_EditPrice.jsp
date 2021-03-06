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
	<div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px;">
		<div style="width:100%;height:100%;">
			<table id="selectedDrug"></table>  
		</div>
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
			
		init : function() {
			$("#selectedDrug").datagrid({
				title : '已选药品',
				fit : true,
				fitColumns : true,
				rownumbers : true,
				idField : 'fId',
				columns:[[    
					{field : 'fId',align:'center',checkbox : false},
					{field : 'fDrugName',title : '药品名称',width : 100,align:'center'},  
					{field : 'fSpecification',title : '药品规格',width : 100,align:'center'}, 
					{field : 'fAddress',title : '产地',width :100,align:'center'},
					{field : 'fBatchNumber',title : '批号',width :100,align:'center'},
					{field : 'fExpiryDate',title : '效期',width :100,align:'center'},
					{field : 'fNumber',title : '销售数量',width : 100,align:'center',
						formatter: function(value,row,index){
							var fId = row['fId'];
							return "<input class='nnnnn' disabled='true' value='"+value+"' data='" + fId + "'/>";
						}
					}, 
					{field : 'fPrice',title : '单价',width :100,align:'center',
						formatter: function(value,row,index){
							var fId = row['fId'];
							return "<input class='aaaaa' value='"+value+"' data='" + fId + "'/>";
						}
					},
					{field : 'fKaiPiaoPrice',title : '开票价',width :100,align:'center',
						formatter: function(value,row,index){
							var fId = row['fId'];
							return "<input class='bbbbb' value='"+value+"' data='" + fId + "'/>";
						}
					},
					{field : 'fGuoJiFei',title : '过票费',width :100,align:'center',
						formatter: function(value,row,index){
							var fId = row['fId'];
							return "<input class='ccccc' value='"+value+"' data='" + fId + "'/>";
						}
					},
					{field : 'fGaoKaiFei',title : '高开费',width :100,align:'center',
						formatter: function(value,row,index){
							var fId = row['fId'];
							return "<input class='ddddd' value='"+value+"' data='" + fId + "'/>";
						}
					},
					{field : 'fMoney',title : '合计',width :100,align:'center',
						formatter: function(value,row,index){
							var fId = row['fId'];
							return "<input class='eeeee' value='"+value+"' data='" + fId + "'/>";
						}
					}
				]]
			});
			
			//药品
			ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "orderDetail",
					"reqMethod" : "getList",
					"fOrderId" : "${param.fId}"
				},
				success : function(ret)
				{	
					$("#selectedDrug").datagrid('loadData',ret.d);
				}
			});
		},
		comit : function() {
			
			var drugIds='';
			//数量  start
			var nums='';
			var flag = false;
			$("input.nnnnn").each(function(){
				nums += $.trim($(this).val()) + ",";
			});
			//数量   end
			//单价  start
			var prices='';
			var price;
			var flag = false;
			$("input.aaaaa").each(function(){
				drugIds += $(this).attr("data") + ",";
				price = $.trim($(this).val());
				if('' != price && /^\d+(\.\d{1,2})?$/i.test(price)){//验证价格是否为正整数或小数
					
				}else{
					flag = true;
				}
				prices += price + ",";
			});
			//单价   end
			//开票价  start
			var kaipiaoPrices='';
			var kaipiaoPrice;
			$("input.bbbbb").each(function(){
				kaipiaoPrice = $.trim($(this).val());
				if('' != kaipiaoPrice && /^\d+(\.\d{1,2})?$/i.test(kaipiaoPrice)){//验证价格是否为正整数或小数
					
				}else{
					flag = true;
				}
				kaipiaoPrices += kaipiaoPrice + ",";
			});
			//开票价  end
			//过票费  start
			var guopiaofeis='';
			var guopiaofei;
			$("input.ccccc").each(function(){
				guopiaofei = $.trim($(this).val());
				if('' != guopiaofei && /^\d+(\.\d{1,2})?$/i.test(guopiaofei)){//验证价格是否为正整数或小数
					
				}else{
					flag = true;
				}
				guopiaofeis += guopiaofei + ",";
			});
			//过票费  end
			//高开费  start
			var gaokaifeis='';
			var gaokaifei;
			$("input.ddddd").each(function(){
				gaokaifei = $.trim($(this).val());
				if('' != gaokaifei && /^\d+(\.\d{1,2})?$/i.test(gaokaifei)){//验证价格是否为正整数或小数
					
				}else{
					flag = true;
				}
				gaokaifeis += gaokaifei + ",";
			});
			//高开费  end
			//金额  start
			var moneys='';
			var money;
			$("input.eeeee").each(function(){
				money = $.trim($(this).val());
				if('' != money && /^\d+(\.\d{1,2})?$/i.test(money)){//验证价格是否为正整数或小数
					
				}else{
					flag = true;
				}
				moneys += money + ",";
			});
			//金额  end
			if(flag){
				alert("请检查修改的价格是否符合格式！");
				return false;
			}
			ajaxTools.singleReq({
				data : {
					"reqUrl" : "order",
					"reqMethod" : "editPrice_policy",
					"fOrderId" : "${param.fId}",//订单id
					"fTax" : "${param.fTax}",//是否含税
					"nums" : nums,//数量集合
					"drugIds" : drugIds,//药品id集合
					"prices" : prices,//药品对应修改的价格集合
					"kaipiaoPrices" : kaipiaoPrices,//药品对应修改的开票价格集合
					"guopiaofeis" : guopiaofeis,//药品对应修改的过票费集合
					"gaokaifeis" : gaokaifeis,//药品对应修改的高开费集合
					"moneys" : moneys//药品对应修改的金额集合
				},
				success : function(ret) {
					if (ret && ret.a == GLOBAL_INFO.SUCCESS_CODE) {
						QM.dialog.doWinCallback(true, ret);
					} else {
						QM.dialog.doWinCallback(false, ret);
					}
					
				}
			});
		},
		close : function() {
			QM.dialog.closeWin();
		}
	}
	$(document).ready(function() {
		merchantUserInfoComponent.init();
	});
</script>
</html>
