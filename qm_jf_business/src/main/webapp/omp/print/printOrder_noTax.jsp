<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../../taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>发货单</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${contextPath}/resource/scripts/jquery-1.7.1.min.js"></script>
<script type="text/javascript">
	function printer(){
		$("#dayin").hide();
		window.print();
		$("#dayin").show();
	}
</script>

<style>@media print{　　.noprint{  　　display:none　　}}</style>
</head>
<body>
	<div id="dayin" class="noprint" style="width:100%;height:20px;margin:10px auto 0 auto;font-size:12px;text-align:left;">    
	<input value="打印" type="button" onclick="javascript:printer()" />    
	<OBJECT classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" height="0" id="wb" name="wb" width="0">    </OBJECT>       
	<input type=button name=button_fh value="关闭" onclick="javascript:window.close();">
	</div>
	<div style="width:100%;height:auto;margin:10px auto;">
		<table id="data" border="1" cellpadding="10" cellspacing="0">
			<caption><h3>销售出库申请表</h3></caption>
			<tr>
				<th colspan="2">收货单位</th>
				<td id="fUnit" colspan="7"></td>
			</tr>
			<tr id="drugData">
				<th colspan="2">药品名称</th>
				<th>药品规格</th>
				<th>生产厂家</th>
				<th>批号</th>
				<th>数量</th>
				<th>单位</th>
				<th>单价</th>
				<th>金额</th>
				<!-- <th>仓库</th> -->
			</tr>
			
			<tr>
				<th>购货单位</th>
				<td id="fAddress" colspan="8"></td>
			</tr>
			<tr>
				<th>收货人</th>
				<td id="fName"></td>
				<th>联系电话</th>
				<td id="fPhone"></td>
				<th>物流信息</th>
				<td colspan="4"></td>
			</tr>
			<tr>
				<th>申请人</th>
				<td></td>
				<th>税票(增值/普通)</th>
				<td colspan="2"></td>
				<th>申请日期</th>
				<td colspan="3"></td>
			</tr>
			<tr>
				<th>备注</th>
				<td colspan="8"></td>
			</tr>
		</table>
	</div>
</body>
</html>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js"></script>
<script type="text/javascript">
	var ajaxTools = new QM.ajax();
	ajaxTools.singleReq({
		async : false,
		data : {
			"reqUrl" : "orderDetail",
			"reqMethod" : "printer",
			"fOrderId" : "${param.fId}"
		},
		success : function(ret) {
			if(ret&&ret.a==GLOBAL_INFO.SUCCESS_CODE) {
				$("#fName").html(ret.d['fName']);
				$("#fPhone").html(ret.d['fPhone']);
				$("#fAddress").html(ret.d['fAddress']);
				$("#fUnit").html(ret.d['fUnit']);
				$("#fOrderId").html(ret.d['fOrderId']);
			
				var arr = ret.d['list'];
				var trHtml = "";
				for(var i=0; i<arr.length; i++){
					trHtml += "<tr>";
					trHtml += "<td colspan='2'> " + arr[i]['F_DRUG_NAME'] + " </td>";
					trHtml += "<td> " + arr[i]['F_SPECIFICATION'] + " </td>";
					trHtml += "<td> " + arr[i]['F_ADDRESS'] + " </td>";
					trHtml += "<td> " + arr[i]['F_BATCH_NUMBER'] + " </td>";
					trHtml += "<td> " + arr[i]['F_NUMBER'] + " </td>";
					trHtml += "<td></td>";
					trHtml += "<td> " + arr[i]['F_GONGYE_PRICE'] + " </td>";
					trHtml += "<td> </td>";
					
					trHtml += "</tr>";
				}
				$("#drugData").after(trHtml);
				
				
			}else {
				alert("获取数据失败");
			}
		}.bind(this)
	});
</script>