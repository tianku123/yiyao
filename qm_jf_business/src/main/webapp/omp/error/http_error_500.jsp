<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	response.setStatus(HttpServletResponse.SC_OK); //这句也一定要写,不然IE不会跳转到该页面
	String path=request.getContextPath();
%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>服务器异常</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>
	<body style="font-family: 微软雅黑; font-size: 14px">
		<div>
			<p>
				系统执行发生错误，信息描述如下
				<span style="color: green;">（发布时请注释调web.xml配置中的error-page标签）</span>：
			</p>
		</div>
		<div>
			<p>
				错误状态代码是：${pageContext.errorData.statusCode}
			</p>
		</div>
		<div>
			<p>
				错误发生页面是：${pageContext.errorData.requestURI}
			</p>
		</div>
		<div>
			<p>
				错误信息：${pageContext.exception}
			</p>
		</div>
		<div>
			错误堆栈信息：
			<div style="color: #f00;">
				<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
					<span style="margin-left: 80px;">${trace}</span>
					<br />
				</c:forEach>
			</div>
		</div>
	</body>
</html>