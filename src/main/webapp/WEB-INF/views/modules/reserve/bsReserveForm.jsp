<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预约登记管理</title>
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
		<li><a href="${ctx}/reserve/bsReserve/">预约登记列表</a></li>
		<li class="active"><a href="${ctx}/reserve/bsReserve/form?id=${bsReserve.id}">预约登记<shiro:hasPermission name="reserve:bsReserve:edit">${not empty bsReserve.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="reserve:bsReserve:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsReserve" action="${ctx}/reserve/bsReserve/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">儿童id：</label>
                        <div class="col-sm-2">
						<form:input path="childId" htmlEscape="false" maxlength="32" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗id：</label>
                        <div class="col-sm-2">
						<form:input path="vaccId" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：</label>
                        <div class="col-sm-2">
						<form:input path="vaccName" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品id：</label>
                        <div class="col-sm-2">
						<form:input path="pid" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">计划id：</label>
                        <div class="col-sm-2">
						<form:input path="nid" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">风险告知书id：</label>
                        <div class="col-sm-2">
						<form:input path="context" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗种类：</label>
                        <div class="col-sm-2">
						<form:input path="vaccType" htmlEscape="false" maxlength="10" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">remarks：</label>
                        <div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="reserve:bsReserve:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>