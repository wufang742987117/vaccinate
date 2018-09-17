<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>排队叫号管理管理</title>
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
		<li><a href="${ctx}/inoculate/quene/">排队叫号管理列表</a></li>
		<li class="active"><a href="${ctx}/inoculate/quene/form?id=${quene.id}">排队叫号管理<shiro:hasPermission name="inoculate:quene:edit">${not empty quene.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="inoculate:quene:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="quene" action="${ctx}/inoculate/quene/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">排队号码：</label>
                        <div class="col-sm-2">
						<form:input path="queueno" htmlEscape="false" maxlength="20" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">儿童编号：</label>
                        <div class="col-sm-2">
						<form:input path="childid" htmlEscape="false" maxlength="18" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗编号：</label>
                        <div class="col-sm-2">
						<form:input path="vaccineid" htmlEscape="false" maxlength="18" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">科室编号：</label>
                        <div class="col-sm-2">
						<form:input path="roomcode" htmlEscape="false" maxlength="5" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种医生姓名：</label>
                        <div class="col-sm-2">
						<form:input path="doctor" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否过号 Y已过号 N未过号，排队中：</label>
                        <div class="col-sm-2">
						<form:input path="ispass" htmlEscape="false" maxlength="1" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="inoculate:quene:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>