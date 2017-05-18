<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:if test="${header['OpenWay'] ne 'div'}">
	<!DOCTYPE HTML>
	<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link href="${ctx}/static/lib/Hui-iconfont/1.0.7/iconfont.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/h-ui/css/H-ui.min.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/admin/css/admin.css" rel="stylesheet" type="text/css" />
<title>${code}页面</title>
</head>
<body class="bg404">
</c:if>
<img class="bg404" src='${ctx}/static/admin/css/<c:if test="${not empty param.building}">pgingbg.jpg</c:if><c:if test="${empty param.building}">404bg.jpg</c:if>' alt="404">
<p style="width: 100%;text-align: center;position: fixed;bottom: 65px;;font-size: 200%">${error}</p>
<p style="width: 100%;text-align: center;position: fixed;bottom: 20px;font-size: 200%">
	您可以：<a href="javascript:;" onclick="history.go(-1)" class="c-primary"style="color: #77216e;">&lt; 返回上一页</a><span class="ml-20">|</span><a href="${pageContext.request.contextPath}" class="ml-20" style="color: #77216e;">去首页 &gt;</a>
</p>
<c:if test="${header['OpenWay'] ne 'div'}">
	</body>
	</html>
</c:if>
