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
		
		<c:if test="${not empty arg}">
		<script type="text/javascript">
			$(function() {
				layer.msg('${arg}');
				
			});
			
		</script>
	</c:if>
	
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/yiyuan/sysBirthHospital/">信息列表</a></li>
		<li class="active"><a href="${ctx}/yiyuan/sysBirthHospital/form?id=${sysBirthHospital.id}">信息<shiro:hasPermission name="yiyuan:sysBirthHospital:edit">${not empty sysBirthHospital.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="yiyuan:sysBirthHospital:edit">查看</shiro:lacksPermission></a></li>
	</ul> --%>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysBirthHospital" action="${ctx}/yiyuan/sysBirthHospital/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">医院编码：<span
						class="help-inline"><font color="red">*</font> </span></label>
                        <div class="col-sm-6">
						<form:input path="code" htmlEscape="false" maxlength="50" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">医院名称：<span
						class="help-inline"><font color="red">*</font> </span></label>
                        <div class="col-sm-6">
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
                        <div class="col-sm-6">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">排序：</label>
                        <div class="col-sm-6">
						<form:input path="indexNum" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
						<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>