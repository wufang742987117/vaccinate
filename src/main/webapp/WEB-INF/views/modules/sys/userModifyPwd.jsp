<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#oldPassword").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
		<li ><a href="${ctx}/sys/user/info">科室设置</a></li>
		<li><a href="${ctx}/sys/sysVaccDepartmentInfo/">门诊信息</a></li>
		<li class="active"><a href="${ctx}/sys/user/modifyPwd">密码设置</a></li>
		<li ><a href="${ctx}/inoculate/quene/msgPostTo">信息推送</a></li>
		<li ><a href="${ctx}/sys/user/list">用户管理</a></li>
		<li ><a href="${ctx}/sys/user/about">门诊功能配置</a></li>
		<li ><a href="${ctx}/charge/findCharge">收银台</a></li>
		<div class=" row pull-right">
			<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
		</div>
	</ul> 
	<div class="ibox">
		<div class="mt20">
			<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/modifyPwd" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 col-xs-3 text-right control-label">旧密码:</label>
					<div class="col-sm-2 col-xs-5">
						<input id="oldPassword" name="oldPassword" type="password" value="" required="" aria-required="true" maxlength="50" minlength="3" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-xs-3 text-right control-label">新密码:</label>
					<div class="col-sm-2 col-xs-5">
						<input id="newPassword" name="newPassword" type="password" value="" required="" aria-required="true" maxlength="50" minlength="3" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-xs-3 text-right control-label">确认新密码:</label>
					<div class="col-sm-2 col-xs-5">
						<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" required="" aria-required="true" maxlength="50" minlength="3" class="form-control " equalTo="#newPassword"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2 col-xs-offset-3">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>