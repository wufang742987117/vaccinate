<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>站点管理</title>
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
		<li><a href="${ctx}/cms/site/">站点列表</a></li>
		<li class="active"><a href="${ctx}/cms/site/form?id=${site.id}">站点<shiro:hasPermission name="cms:site:edit">${not empty site.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:site:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="site" action="${ctx}/cms/site/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">站点名称:</label>
					<div class="col-sm-2">
						<form:input path="name" htmlEscape="false" maxlength="200" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">站点标题:</label>
					<div class="col-sm-2">
						<form:input path="title" htmlEscape="false" maxlength="200" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">站点Logo:</label>
					<div class="col-sm-2">
						<form:hidden path="logo" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="logo" type="images" uploadPath="/cms/site"/>
						<span class="help-inline">建议Logo大小：1000 × 145（像素）</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">描述:</label>
					<div class="col-sm-2">
						<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">关键字:</label>
					<div class="col-sm-2">
						<form:input path="keywords" htmlEscape="false" cssClass="form-control" maxlength="200"/>
						<span class="help-inline">填写描述及关键字，有助于搜索引擎优化</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">默认主题:</label>
					<div class="col-sm-2">
						<form:select path="theme" class="form-control">
							<form:options items="${fns:getDictList('cms_theme')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">版权信息:</label>
					<div class="col-sm-10">
						<form:textarea id="copyright" htmlEscape="true" path="copyright" rows="4" maxlength="200" />
						<sys:ckeditor replace="copyright" uploadPath="/cms/site" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">自定义首页视图:</label>
					<div class="col-sm-2">
						<form:input path="customIndexView" htmlEscape="false" cssClass="form-control" maxlength="200"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="cms:site:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>