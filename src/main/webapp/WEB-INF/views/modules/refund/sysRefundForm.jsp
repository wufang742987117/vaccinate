<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
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
		<li><a href="${ctx}/refund/sysRefund/">信息列表</a></li>
		<li class="active"><a href="${ctx}/refund/sysRefund/form?id=${sysRefund.id}">信息<shiro:hasPermission name="refund:sysRefund:edit">${not empty sysRefund.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="refund:sysRefund:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysRefund" action="${ctx}/refund/sysRefund/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">儿童编号：</label>
                        <div class="col-sm-2">
						<form:input path="childcode" htmlEscape="false" maxlength="20" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">儿童姓名：</label>
                        <div class="col-sm-2">
						<form:input path="childname" htmlEscape="false" maxlength="20" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">计划表ID：</label>
                        <div class="col-sm-2">
						<form:input path="nid" htmlEscape="false" maxlength="10" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户ID：</label>
                        <div class="col-sm-2">
						<form:input path="uid" htmlEscape="false" maxlength="32" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">pid：</label>
                        <div class="col-sm-2">
						<form:input path="pid" htmlEscape="false" maxlength="32" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">创建时间：</label>
                        <div class="col-sm-2">
						<input  name="createdate" readonly="" value="<fmt:formatDate value="${sysRefund.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date required">
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退款来源0登记台1微信2一体机：</label>
                        <div class="col-sm-2">
						<form:input path="refundsource" htmlEscape="false" maxlength="2" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退款金额：</label>
                        <div class="col-sm-2">
						<form:input path="refundmoney" htmlEscape="false" maxlength="20" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="refund:sysRefund:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>