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
	<c:if test="${not empty isok}">
		<script type="text/javascript">
			$(function() {
				layer.msg('保存成功');
				setTimeout(funB, 1500);
			});
			function funB(){
				parent.closeForm();
			} 
		</script>
	</c:if>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysVaccDepartment" action="${ctx}/department/sysVaccDepartment/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">门诊编码：<span
						class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-6">
						<form:input path="code" htmlEscape="false" maxlength="20" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">门诊简称：<span
						class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-6">
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">门诊全称：<span
						class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-6">
						<form:input path="allName" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">code级别：</label>
                        <div class="col-sm-2">
						<form:input path="codeLevel" htmlEscape="false" maxlength="10" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">父节点code：</label>
                        <div class="col-sm-2">
						<form:input path="fCode" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">新code：</label>
                        <div class="col-sm-2">
						<form:input path="nCode" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
                    <div class="col-sm-6">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="100" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>