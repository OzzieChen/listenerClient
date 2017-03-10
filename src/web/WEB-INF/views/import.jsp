<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div id="uploadImg" style="width: 100%;text-align: center;vertical-align: middle;">
	<div class="Huialert Huialert-success" style="margin-bottom: 0px;">
		<i class="icon-remove"></i>${msg}</div>
	<form method="post" style="height: 50px;margin-top: 100px;" class="my${systime}" action="${ctx}/import" enctype="multipart/form-data">
		<input name="op" type="hidden" value="${op}">
		<input id="upfile${systime}" class="btn btn-warning size-S radius" name="file" type="file" />
		<input class="btn btn-primary size-S radius" type="submit" value="上传" />
	</form>
</div>
<script type="text/javascript">
	$('.my${systime}').submit(function(event) {
		event.preventDefault();
		var fileDir = $("#upfile${systime}").val();
		var suffix = fileDir.substr(fileDir.lastIndexOf("."));
		if ("" == fileDir) {
			alert("选择需要导入的CSV文件！");
		} else if (".csv" != suffix) {
			alert("选择csv格式的文件导入！");
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
				processData : false
			}).success(function() {
				if (layer != null)
					layer.msg('上传成功', {
						icon : 1,
						time : 3000
					});
				else
					alert('上传成功')
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