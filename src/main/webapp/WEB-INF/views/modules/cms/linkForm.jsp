<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>链接管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					if ($("#categoryId").val()==""){
						$("#categoryName").focus();
						top.$.jBox.tip('请选择归属栏目','warning');
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					}
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
		<li><a href="${ctx}/cms/link/?category.id=${link.category.id}">链接列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/link/form?id=${link.id}&category.id=${link.category.id}'><c:param name='category.name' value='${link.category.name}'/></c:url>">链接<shiro:hasPermission name="cms:link:edit">${not empty link.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:link:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="link" action="${ctx}/cms/link/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">归属栏目:</label>
					<div class="col-sm-2">
						<sys:treeselect id="category" name="category.id" value="${link.category.id}" labelName="category.name" labelValue="${link.category.name}"
							title="栏目" url="/cms/category/treeData" module="link" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">名称:</label>
					<div class="col-sm-3">
						<form:input path="title" htmlEscape="false" maxlength="200" class="form-control required measure-input"/>
					</div>
					<label class="col-sm-2 control-label">颜色:</label>
					<div class="col-sm-3">
						<form:select path="color" class="form-control">
							<form:option value="" label="默认"/>
							<form:options items="${fns:getDictList('color')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">链接图片:</label>
					<div class="col-sm-2">
						<form:hidden path="image" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="image" type="images" uploadPath="/cms/link" selectMultiple="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">链接地址:</label>
					<div class="col-sm-2">
						<form:input path="href" htmlEscape="false" maxlength="255" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">权重:</label>
					<div class="col-sm-2">
						<form:input path="weight" htmlEscape="false" maxlength="200" class="form-control required digits"/>
					</div>
					<div class="col-sm-1">
						<label for="weightTop"><input id="weightTop" type="checkbox" class="i-checks" onclick="$('#weight').val(this.checked?'999':'0')">置顶</label>
					</div>
					<label class="col-sm-2 control-label">过期时间：</label>
					<div class="col-sm-3">
						<input name="weightDate" readonly="" id="weightDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
							   value="<fmt:formatDate value="${article.weightDate}"/>" class="laydate-icon form-control layer-date required">
					</div>
					<span class="help-inline">数值越大排序越靠前，过期时间可为空，过期后取消置顶。</span>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">备注:</label>
					<div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="200" class="form-control"/>
					</div>
				</div>
				<shiro:hasPermission name="cms:article:audit">
					<div class="form-group">
						<label class="col-sm-2 control-label">发布状态:</label>
						<div class="col-sm-2">
							<form:radiobuttons path="delFlag" element="label class='checkbox-inline i-checks'" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
							<span class="help-inline"></span>
						</div>
					</div>
				</shiro:hasPermission>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="cms:link:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>