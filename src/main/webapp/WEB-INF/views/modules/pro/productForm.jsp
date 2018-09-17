<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
		<li><a href="${ctx}/pro/product/">产品列表</a></li>
		<li class="active"><a href="${ctx}/pro/product/form?id=${product.id}">产品<shiro:hasPermission name="pro:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="pro:product:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div >
			<form:form id="inputForm" modelAttribute="product" action="${ctx}/pro/product/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品类别：</label>
                        <div class="col-sm-2">
						<sys:treeselect id="product" name="productTypeId.id" value="${product.productTypeId.id}" labelName="productTypeId.name" labelValue="${product.productTypeId.name}"
							title="产品分类" url="/procategory/productType/protypedata" extId="${ProductType.id}"  cssClass="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品编号：</label>
                        <div class="col-sm-2">
						<form:input path="code" htmlEscape="false" maxlength="30" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品名称：</label>
                        <div class="col-sm-2">
						<form:input path="name" htmlEscape="false" maxlength="30" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否隐藏：</label>
                        <div class="col-sm-2">
						<form:radiobuttons element="label class='checkbox-inline i-checks'" path="isEnable" items="${fns:getDictList('show_hide')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="pro:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>