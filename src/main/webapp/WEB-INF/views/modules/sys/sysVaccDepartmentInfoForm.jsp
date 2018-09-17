<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科室信息管理</title>
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
		<li><a href="${ctx}/sys/sysVaccDepartmentInfo/">科室信息列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysVaccDepartmentInfo/form?id=${sysVaccDepartmentInfo.id}">科室信息<shiro:hasPermission name="sys:sysVaccDepartmentInfo:edit">${not empty sysVaccDepartmentInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysVaccDepartmentInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysVaccDepartmentInfo" action="${ctx}/sys/sysVaccDepartmentInfo/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种单位编码：</label>
                        <div class="col-sm-2">
						<form:input path="localCode" htmlEscape="false" maxlength="50" class="form-control " readonly="true" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种单位名称：</label>
                        <div class="col-sm-2">
						<form:input path="localname" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工作时间：</label>
                        <div class="col-sm-2">
						<form:input path="jobstime" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系电话：</label>
                        <div class="col-sm-2">
						<form:input path="phonenumber" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
                        <div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
                        <div class="col-sm-2">
						<form:input path="address" htmlEscape="false" maxlength="500" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>