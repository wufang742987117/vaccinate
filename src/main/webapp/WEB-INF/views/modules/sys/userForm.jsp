<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<c:if test="${not empty arg }">
	
	<script type="text/javascript">
	$(function(){
	if(${arg}==1){
	layer.msg('添加失败，该用户名已存在！',{icon: 2});
	
	}else{
	layer.msg('添加成功',{icon: 1});
	setTimeout(funB, 1200);
	}
	});

	function funB(){
		parent.closeForm();
	}
	</script>
	</c:if>
	
	
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
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
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/user/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/sys/user/form?id=${user.id}">用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:user:edit">查看</shiro:lacksPermission></a></li>
	</ul> --%>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">头像:</label>
					<div class="col-sm-5">
						<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
						<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
					</div>
				</div> --%>
				 <div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">归属公司:</label>
					<div class="col-sm-5">
						<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
							title="公司" url="/sys/office/treeData?type=1" cssClass="form-control required"/>
					</div>
				</div> 
				<div class="form-group">
					<label class="col-sm-2 control-label">归属科室:</label>
					<div class="col-sm-5">
						<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
							title="科室" url="/sys/office/treeData?type=2" cssClass="form-control required" notAllowSelectParent="true"/>
					</div>
				</div>
				<%-- <div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">工号:</label>
					<div class="col-sm-5">
						<form:input path="no" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
					<span class="help-inline"><font color="red">*</font> </span>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名:<font color="red">*</font></label>
					<div class="col-sm-5">
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required "/>
					</div>
					<span class="help-inline"> </span>
				</div>
			<%-- 	<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">登录名:</label>
					<div class="col-sm-5">
						<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
						<form:input path="loginName" htmlEscape="false" maxlength="50" class="form-control  userName"/>
					</div>
					<span class="help-inline"> </span>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">登录名:<font color="red">*</font></label>
					<div class="col-sm-5">
						<form:input path="loginName" htmlEscape="false" class="form-control required" maxlength="100"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">密码:<font color="red">*</font></label>
					<div class="col-sm-5">
						<input id="newPassword" name="newPassword" type="password" value="" maxlength="50" minlength="3" class="form-control ${empty user.id?'required':''}"/>
					</div>
					<c:if test="${empty user.id}"><span class="help-inline"> </span></c:if>
					<c:if test="${not empty user.id}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">确认密码:<font color="red">*</font></label>
					<div class="col-sm-5">
						<input id="confirmNewPassword" name="confirmNewPassword" class="form-control required" type="password" value="" maxlength="50" minlength="3" equalTo="#newPassword"/>
					</div>
					<c:if test="${empty user.id}"><span class="help-inline"> </span></c:if>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">邮箱:</label>
					<div class="col-sm-5">
						<form:input path="email" htmlEscape="false" maxlength="100" class="form-control email"/>
					</div>
				</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机号码:</label>
					<div class="col-sm-5">
						<form:input path="phone" htmlEscape="false" class="form-control mobile" maxlength="11"/>
					</div>
				</div>
				<%--<div class="form-group">
					<label class="col-sm-2 control-label">手机:</label>
					<div class="col-sm-5">
						<form:input path="mobile" htmlEscape="false" class="form-control" maxlength="100"/>
					</div>
				</div> --%>
				
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">是否允许登录:</label>
					<div class="col-sm-5">
						<form:select path="loginFlag" cssClass="form-control">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">用户类型:</label>
					<div class="col-sm-5">
						<form:select path="userType" class="form-control">
							<form:option value="" label="请选择"/>
							<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div> --%>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">用户角色:</label>
					<div class="col-sm-10">
						<form:checkboxes path="roleIdList" items="${allRoles}" itemLabel="name" itemValue="id" htmlEscape="false" class="i-checks required " />
					</div>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注:</label>
					<div class="col-sm-5">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/>
					</div>
				</div>
				<c:if test="${not empty user.id}">
					<div class="form-group">
						<label class="col-sm-2 control-label">创建时间:</label>
						<div class="col-sm-5">
							<label class="lbl"><fmt:formatDate value="${user.createDate}" type="both" dateStyle="full"/></label>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">最后登陆:</label>
						<div class="col-sm-5">
							<label class="lbl">IP: ${user.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.loginDate}" type="both" dateStyle="full"/></label>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="sys:user:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>