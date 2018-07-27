<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fn"   prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.xwtech.com/jsp/jstl/pub" prefix="pub"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="request"/>
<%
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";
	pageContext.setAttribute("ctx",basePath);
	pageContext.setAttribute("updateDate",new Date().getTime());
%>
<c:set var="skin" value="gray" scope="request"/>
