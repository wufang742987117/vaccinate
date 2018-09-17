<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
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
		<li><a href="${ctx}/test/testData/">单表列表</a></li>
		<li class="active"><a href="${ctx}/test/testData/form?id=${testData.id}">单表<shiro:hasPermission name="test:testData:edit">${not empty testData.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="test:testData:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="testData" action="${ctx}/test/testData/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">归属用户：</label>
					<div class="col-sm-2">
						<sys:treeselect id="user" name="user.id" value="${testData.user.id}" labelName="user.name" labelValue="${testData.user.name}"
							title="用户" url="/sys/office/treeData?type=3" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">归属部门：</label>
					<div class="col-sm-2">
						<sys:treeselect id="office" name="office.id" value="${testData.office.id}" labelName="office.name" labelValue="${testData.office.name}"
							title="部门" url="/sys/office/treeData?type=2" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">归属区域：</label>
					<div class="col-sm-2">
						<sys:treeselect id="area" name="area.id" value="${testData.area.id}" labelName="area.name" labelValue="${testData.area.name}"
							title="区域" url="/sys/area/treeData" cssClass="form-control required" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">名称：</label>
					<div class="col-sm-2">
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性别：</label>
					<div class="col-sm-2">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" cssClass="required" element="label class='checkbox-inline i-checks'" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">加入日期：</label>
					<div class="col-sm-2">
						<input name="inDate"  id="inDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" value="<fmt:formatDate value="${testData.inDate}"/>" class="laydate-icon form-control layer-date required">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注信息：</label>
					<div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="test:testData:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>