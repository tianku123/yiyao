<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fn"   prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.xwtech.com/jsp/jstl/pub" prefix="pub"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request"/>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	pageContext.setAttribute("ctx",basePath);
%>
<c:set var="skin" value="gray" scope="request"/>
