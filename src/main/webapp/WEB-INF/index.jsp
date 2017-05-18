<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="${ctx}/static/lib/html5.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/respond.min.js"></script>
<script type="text/javascript" src="${ctx}/static/lib/PIE_IE678.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="${ctx}/static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/admin.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/layer/skin/layer.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/lib/layui/css/layui.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>IP 测量</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
.page-container{
padding-left: 300px;
}
</style>
</head>
<body>
	<article class="page-container">
		<div id="uploadImg" style="width: 100%;text-align: left;vertical-align: middle;padding-left: 50px;">
			<c:if test="${not empty msg}">
				<div class="Huialert Huialert-success" style="margin-bottom: 0px;">
					<i class="icon-remove"></i>${msg}</div>
			</c:if>
			<form method="post" style="height: 50px;margin-top: 100px;" class="myfile" action="${ctx}/import" enctype="multipart/form-data">
				<input id="upfile" class="btn btn-warning size-S radius" name="file" type="file" />
				<input class="btn btn-primary size-S radius" type="submit" value="上传" />
			</form>
			
			<div style="color: red;font-size: 25px;" >或者</div>
		</div>
		<form class="layui-form mt-40" method="post" action="${ctx}/import2">
			<div class="layui-form-item">
				<label class="layui-form-label">文件名：</label>
				<div class="layui-input-inline">
					<input type="text" name="fname" autocomplete="off" class="layui-input" />
				</div>
				<span id="description-error"></span>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label"><span class="c-red">*</span>文本：</label>
				<div class="layui-input-inline">
					<textarea name="txt" required="required" cols="70" rows="30"></textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter="mrt-go">提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</article>
	<script type="text/javascript" src="${ctx}/static/lib/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/lib/layui/layui.js"></script>
	<script type="text/javascript" src="${ctx}/static/lib/layer/layer.js"></script>
	<script>
		layui.use('form', function() {
			var form = layui.form();
			if (form != undefined)
				form.render();
			form.on('submit(mrt-go)', function(data) {
				var form = data.form;
				$.ajax({
					type : $(form).attr('method'),
					url : $(form).attr('action'),
					data : $(form).serialize()
				}).success(function(idata) {
					var errorCode = idata.error_code;
					if (errorCode == 0) {
						layer.msg(idata.reason, {
							icon : 1,
							time : 3500
						});
						$(':input', form).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected')
					} else {
						if (idata.reason != null)
							layer.msg(idata.reason, {
								icon : 5,
								time : 6000
							});
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					layer.msg('数据加载失败', {
						icon : 5
					});
				});
				return false;
			});
		});
		$('.myfile').submit(function(event) {
			event.preventDefault();
			var fileDir = $("#upfile").val();
			var suffix = fileDir.substr(fileDir.lastIndexOf("."));
			if ("" == fileDir) {
				layer.msg("选择需要导入的txt文件！", {
					icon : 5,
					time : 5000
				});
			} else if (".txt" != suffix) {
				layer.msg("选择txt格式的文件导入！", {
					icon : 5,
					time : 5000
				});
			} else {
				var form = $(this);
				var formData = new FormData(this);
				$.ajax({
					type : form.attr('method'),
					url : form.attr('action'),
					data : formData,
					mimeType : "multipart/form-data",
					contentType : false,
					cache : false,
					dataType : "json",
					processData : false
				}).success(function(idata) {
					var errorCode = idata.error_code;
					if (errorCode == 0) {
						layer.msg(idata.reason, {
							icon : 1,
							time : 3500
						});
						$(':input', form).not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected')
					} else {
						if (idata.reason != null)
							layer.msg(idata.reason, {
								icon : 5,
								time : 6000
							});
					}
				}).fail(function(jqXHR, textStatus, errorThrown) {
					if (layer != null)
						layer.msg('上传失败，请检查文件格式或网络', {
							icon : 5,
							time : 10000
						});
					else
						alert('上传失败，请检查文件格式或网络')
				});
			}
		});
	</script>
</body>
</html>
