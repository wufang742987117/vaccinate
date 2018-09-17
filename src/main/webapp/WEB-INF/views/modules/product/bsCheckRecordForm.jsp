<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存盘点记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/bsCheckRecord/">库存盘点记录列表</a></li>
		<li class="active"><a href="${ctx}/product/bsCheckRecord/form?id=${bsCheckRecord.id}">库存盘点记录<shiro:hasPermission name="product:bsCheckRecord:edit">${not empty bsCheckRecord.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:bsCheckRecord:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsCheckRecord" action="${ctx}/product/bsCheckRecord/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品id：</label>
                        <div class="col-sm-2">
						<form:input path="productId" htmlEscape="false" maxlength="32" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">库存盘点前：</label>
                        <div class="col-sm-2">
						<form:input path="checkBefore" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">库存盘点后：</label>
                        <div class="col-sm-2">
						<form:input path="checkAfter" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">状态：</label>
                        <div class="col-sm-2">
						<form:input path="status" htmlEscape="false" maxlength="2" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
                        <div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">操作单位编码：</label>
                        <div class="col-sm-2">
						<form:input path="localcode" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">盘点库存一次盘点哪些：</label>
                        <div class="col-sm-2">
						<form:input path="code" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">盘点数量：</label>
                        <div class="col-sm-2">
						<form:input path="checknum" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="product:bsCheckRecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>