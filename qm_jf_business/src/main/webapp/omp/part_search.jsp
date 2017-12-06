<%@ page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ include file="../taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>医药管理系统</title>
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link href="${contextPath}/resource/css/common.css" rel="stylesheet"
	type="text/css" />
</head>
<body class="easyui-layout" id="filter">
	<div data-options="region:'north',border:false" style="height:77px; padding:0 10px; margin-top:10px;">
		<div class="searchColumn">
			<div class="keySearch">
				关键词： <input type="search" />
				<button class="grayBtn">查询</button>
			</div>
			<p class="shrink" onClick="resizeNorth();">收起搜索栏</p>
		</div>
	</div>
		<!--end searchColumn-->
		<div data-options="region:'center',border:false" style="padding:10px;">
			<ul class="operColumn">
				<li><a href="javascript:void(0);" class="add">新增</a>
				</li>
				<li><a href="javascript:void(0);" class="revise">修改</a>
				</li>
				<li><a href="javascript:void(0);" class="delete">删除</a>
				</li>
			</ul>
			<table cellpadding="0" cellspacing="0" class="table" width="100%">
				<tr>
					<th width="20">&nbsp;</th>
					<th>&nbsp;</th>
					<th>&nbsp;</th>
				</tr>
				<tr>
					<td>1</td>
					<td>2</td>
					<td>3</td>
				</tr>
				<tr>
					<td>1</td>
					<td>2</td>
					<td>3</td>
				</tr>
				<tr>
					<td>1</td>
					<td>2</td>
					<td>3</td>
				</tr>
				<tr>
					<td>1</td>
					<td>2</td>
					<td>3</td>
				</tr>
			</table>
			<br class="cb" />
		</div>
</body>
</html>
