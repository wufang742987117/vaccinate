<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>儿童接种提醒管理</title>
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
		<li><a href="${ctx}/remind/vacChildRemind/">儿童接种提醒列表</a></li>
		<li class="active"><a href="${ctx}/remind/vacChildRemind/form?id=${vacChildRemind.id}">儿童接种提醒<shiro:hasPermission name="remind:vacChildRemind:edit">${not empty vacChildRemind.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="remind:vacChildRemind:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="vacChildRemind" action="${ctx}/remind/vacChildRemind/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">儿童编号：</label>
                        <div class="col-sm-2">
						<form:input path="childcode" htmlEscape="false" maxlength="32" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提醒日期：</label>
                        <div class="col-sm-2">
						<input  name="remindDate" readonly="" value="<fmt:formatDate value="${vacChildRemind.remindDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date required">
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提醒的内容：</label>
                        <div class="col-sm-2">
						<form:input path="remindVacc" htmlEscape="false" maxlength="255" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗编号：</label>
                        <div class="col-sm-2">
						<form:select path="vaccId" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">厂商编号：</label>
                        <div class="col-sm-2">
						<form:input path="com" htmlEscape="false" maxlength="10" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">批号：</label>
                        <div class="col-sm-2">
						<form:input path="batch" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">剂量：</label>
                        <div class="col-sm-2">
						<form:input path="spec" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">自选时段-日期：</label>
                        <div class="col-sm-2">
						<input  name="selectDate" readonly="" value="<fmt:formatDate value="${vacChildRemind.selectDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date ">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">自选时段-时间段：</label>
                        <div class="col-sm-2">
						<form:input path="selectTime" htmlEscape="false" maxlength="4" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">状态 0未完成  1已完成：</label>
                        <div class="col-sm-2">
						<form:select path="status" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('remind_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">保险id，没有保险null：</label>
                        <div class="col-sm-2">
						<form:input path="insuranceId" htmlEscape="false" maxlength="32" class="form-control "/>
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
						<shiro:hasPermission name="remind:vacChildRemind:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>