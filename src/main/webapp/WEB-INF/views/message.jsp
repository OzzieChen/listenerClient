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
<title>民生通-提示</title>
</head>
<body>
</c:if>
<section class="container-fluid page-404 minWP text-c">
	<p class="error-title">
		<i class="Hui-iconfont va-m" style="font-size:80px">&#xe68e;</i><span class="va-m">${code}</span>
	</p>
	<p class="error-description">${msg}</p>
	<p class="error-info">
		您可以：<a href="javascript:;" onclick="history.go(-1)" class="c-primary">&lt; 返回上一页</a><span class="ml-20">|</span><a href="${pageContext.request.contextPath}" class="c-primary ml-20">去首页 &gt;</a>
	</p>
</section>
<c:if test="${header['OpenWay'] ne 'div'}">
	</body>
	</html>
</c:if>
