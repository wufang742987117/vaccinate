<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信定时提醒管理</title>
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
		<li><a href="${ctx}/wx/vacJobRemind/">微信定时提醒列表</a></li>
		<li class="active"><a href="${ctx}/wx/vacJobRemind/form?id=${vacJobRemind.id}">微信定时提醒<shiro:hasPermission name="wx:vacJobRemind:edit">${not empty vacJobRemind.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wx:vacJobRemind:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="vacJobRemind" action="${ctx}/wx/vacJobRemind/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户openid：</label>
                        <div class="col-sm-2">
						<form:input path="openid" htmlEscape="false" maxlength="32" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-2">
						<form:input path="ctxusername" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">1:犬伤：</label>
                        <div class="col-sm-2">
						<form:input path="rtype" htmlEscape="false" maxlength="10" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">提醒内容时间：</label>
                        <div class="col-sm-2">
						<input  name="ctxdate" readonly="" value="<fmt:formatDate value="${vacJobRemind.ctxdate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date required">
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建时间：</label>
                        <div class="col-sm-2">
						<input  name="createdate" readonly="" value="<fmt:formatDate value="${vacJobRemind.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date ">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：</label>
                        <div class="col-sm-2">
						<form:input path="ctxvaccname" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">0:正常状态：</label>
                        <div class="col-sm-2">
						<form:input path="rstatus" htmlEscape="false" maxlength="10" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="wx:vacJobRemind:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>