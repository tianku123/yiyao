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
<body class="easyui-layout">
		 <div data-options="region:'center',border:false" style="background-color:#FFF; padding:10px; border:1px solid #D3D3D3;">
			<table cellpadding="0" cellspacing="0" class="formTable" width="100%">
				<tr>
					<th width="15%"><label>角色名称</label>
					</th>
					<td width="85%" id="fRoleName"></td>
				</tr>
				<tr>
					<th><label>角色编码</label>
					</th>
					<td id="fRoleCode"></td>
				</tr>
				<tr>
					<th><label>创建日期</label>
					</th>
					<td id="fRoleDate"></td>
				</tr>
			</table>
		</div>
</body>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_main.js?v=${js_version}"></script>
<script type="text/javascript"
	src="${contextPath}/resource/scripts/qm_util.js?v=${js_version}"></script>
<script>
	var ajaxTools = new QM.ajax();
	var merchantRoleInfoComponent = {	
		init : function() {
				//获取角色数据
				ajaxTools.singleReq({
				data : 
				{
					"reqUrl" : "role",
					"reqMethod" : "queryRoleDetail",
					"roleCode" : "${param.roleCode}"
				},
				success : function(ret)
				{	
					if(ret.d!=null) {
						
						//填充表格数据
						$("#fRoleCode").text(ret.d.roleCode);
						//fRoleCode
						$("#fRoleName").text(ret.d.roleName);
						$("#fRoleDate").text(formatDate14(ret.d.roleDate));
					}
				}
			});
		}
	}
	$(document).ready(function() {
		merchantRoleInfoComponent.init();
	});
</script>
</html>
