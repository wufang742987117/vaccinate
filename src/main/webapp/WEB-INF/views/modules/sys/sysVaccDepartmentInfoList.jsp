<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>门诊信息管理</title>
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
		<%-- <li><a href="${ctx}/sys/sysVaccDepartmentInfo/">科室信息</a></li> --%>
		<%-- <li><a href="${ctx}/sys/sysVaccDepartmentInfo/form">科室信息添加</a></li> --%>
		<li ><a href="${ctx}/sys/user/info">科室设置</a></li>
		<li class="active"><a href="${ctx}/sys/sysVaccDepartmentInfo/">门诊信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">密码设置</a></li>
		<li ><a href="${ctx}/inoculate/quene/msgPostTo">信息推送</a></li>
		<li ><a href="${ctx}/sys/user/list">用户管理</a></li>
		<li ><a href="${ctx}/sys/user/about">门诊功能配置</a></li>
		<li ><a href="${ctx}/charge/findCharge">收银台</a></li>
		<div class=" row pull-right">
			<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
		</div>
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