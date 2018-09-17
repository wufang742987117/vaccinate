<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
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
		<li><a href="${ctx}/sys/office/list?id=${office.parent.id}&parentIds=${office.parentIds}">机构列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/form?id=${office.id}&parent.id=${office.parent.id}">机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:office:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/saveAdmin" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">上级机构:</label>
					<div class="col-sm-3">
						<sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
							title="机构" url="/sys/office/treeData" extId="${office.id}" cssClass="form-control" allowClear="${office.currentUser.admin}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">归属区域:</label>
					<div class="col-sm-3">
						<form:input path="area.id" htmlEscape="false" maxlength="50" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">机构名称:</label>
					<div class="col-sm-3">
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/>
					</div>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">机构编码:</label>
					<div class="col-sm-3">
						<form:input path="code" htmlEscape="false"  cssClass="form-control" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">机构类型:</label>
					<div class="col-sm-3">
						<form:select path="type" class="form-control">
							<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">机构级别:</label>
					<div class="col-sm-3">
						<form:input path="grade" htmlEscape="false" maxlength="50" class="form-control required"/>	
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否可用:</label>
					<div class="col-sm-3">
						<form:select path="useable" cssClass="form-control">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">主负责人:</label>
					<div class="col-sm-3">
						 <sys:treeselect id="primaryPerson" name="primaryPerson.id" value="${office.primaryPerson.id}" labelName="office.primaryPerson.name" labelValue="${office.primaryPerson.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">副负责人:</label>
					<div class="col-sm-3">
						 <sys:treeselect id="deputyPerson" name="deputyPerson.id" value="${office.deputyPerson.id}" labelName="office.deputyPerson.name" labelValue="${office.deputyPerson.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系地址:</label>
					<div class="col-sm-3">
						<form:input path="address" cssClass="form-control" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮政编码:</label>
					<div class="col-sm-3">
						<form:input path="zipCode" cssClass="form-control" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">负责人:</label>
					<div class="col-sm-3">
						<form:input path="master" cssClass="form-control" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电话:</label>
					<div class="col-sm-3">
						<form:input path="phone" cssClass="form-control" htmlEscape="false" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">传真:</label>
					<div class="col-sm-3">
						<form:input path="fax" htmlEscape="false" cssClass="form-control" maxlength="50"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱:</label>
					<div class="col-sm-3">
						<form:input path="email" htmlEscape="false" cssClass="form-control" maxlength="50"/>
					</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注:</label>
					<div class="col-sm-3">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/>
					</div>
				</div>
				<c:if test="${empty office.id}">
					<div class="form-group">
						<label class="col-sm-2 control-label">快速添加下级部门:</label>
						<div class="col-sm-3">
							<form:checkboxes path="childDeptList" cssClass="i-checks" items="${fns:getDictList('sys_office_common')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>