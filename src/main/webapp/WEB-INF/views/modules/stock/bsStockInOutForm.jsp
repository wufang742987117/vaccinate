<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>进销存月报表管理</title>
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
		<li><a href="${ctx}/stock/bsStockInOut/">进销存月报表列表</a></li>
		<li class="active"><a href="${ctx}/stock/bsStockInOut/form?id=${bsStockInOut.id}">进销存月报表<shiro:hasPermission name="stock:bsStockInOut:edit">${not empty bsStockInOut.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="stock:bsStockInOut:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsStockInOut" action="${ctx}/stock/bsStockInOut/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">年：</label>
                        <div class="col-sm-2">
						<form:select path="year" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">月：</label>
                        <div class="col-sm-2">
						<form:select path="mouth" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗id：</label>
                        <div class="col-sm-2">
						<form:select path="vaccineId" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">厂商id：</label>
                        <div class="col-sm-2">
						<form:select path="comId" class="form-control ">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">vaccine_name：</label>
                        <div class="col-sm-2">
						<form:input path="vaccineName" htmlEscape="false" maxlength="100" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">com_name：</label>
                        <div class="col-sm-2">
						<form:input path="comName" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上月库存：</label>
                        <div class="col-sm-2">
						<form:input path="stockOld" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">入库：</label>
                        <div class="col-sm-2">
						<form:input path="numIn" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出库：</label>
                        <div class="col-sm-2">
						<form:input path="numOut" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">退货：</label>
                        <div class="col-sm-2">
						<form:input path="numReturn" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">报废：</label>
                        <div class="col-sm-2">
						<form:input path="numScrap" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">本月库存：</label>
                        <div class="col-sm-2">
						<form:input path="stockNow" htmlEscape="false" maxlength="18" class="form-control  digits"/>
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
						<shiro:hasPermission name="stock:bsStockInOut:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>