<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科室配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
			
			$("#edit-office").click(function(){
				var d=$("#officeid").val();
				if(d!="2"){
	// 				window.location.href = "${ctx}/sys/office/form?id=" + $("#officeid").val();
					alertForm("${ctx}/sys/office/form?id=" + $("#officeid").val(), "科室配置", 800, 700);
				}else{
					layer.msg('登记台不可配置',{icon: 2});
				}
			});
		});
		
		function closeForm(){
			layer.closeAll();
		}
	</script>
	 <style>
	p{
		margin: 10px 0 0 0;
	}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/info">科室设置</a></li>
		<li><a href="${ctx}/sys/sysVaccDepartmentInfo/">门诊信息</a></li>
		<li ><a href="${ctx}/sys/user/modifyPwd">密码设置</a></li>
		<li ><a href="${ctx}/inoculate/quene/msgPostTo">信息推送</a></li>
		<li ><a href="${ctx}/sys/user/list">用户管理</a></li>
		<li ><a href="${ctx}/sys/user/about">门诊功能配置</a></li>
		<li ><a href="${ctx}/charge/findCharge">收银台</a></li>
		<div class=" row pull-right">
			<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
		</div>
	</ul> 
	<div class="ibox">
		<div class="mt20">
			<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/info" method="post" class="form-horizontal">
				<sys:message content="${message}"/>
				<form:hidden path="loginName"/>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">头像:</label>
					<div class="col-sm-2">
						<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="form-control"/>
						<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
					</div>
				</div> --%>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">归属公司:</label>
					<div class="col-sm-2">
						<label class="form-control">${user.company.name}</label>
					</div>
				</div> 
				<div class="form-group">
					<label class="col-sm-2 control-label">登录名:</label>
					<div class="col-sm-2">
						<span class="form-control" style="background-color: #A0A0A0 !important">${user.loginName}</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">所属科室:</label>
					<div class="col-sm-2">
						<form:select path="office.id"  class="form-control" id="officeid">
							<form:options items="${officelist}" itemLabel="name" itemValue="id"/>
						</form:select>
						<%-- <label class="form-control">${user.office.name}</label> --%>
					</div>
					<input type="button" id="edit-office" class="btn btn-primary" value="配置" >
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名:<font color="red">*</font></label>
					<div class="col-sm-2">
						<form:input path="name" htmlEscape="false" maxlength="50" cssClass="form-control required " class="required"/>
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">邮箱:</label>
					<div class="col-sm-2">
						<form:input path="email" type="email" htmlEscape="false" maxlength="50" cssClass="form-control"/>
					</div>
				</div> --%>
			 	<div class="form-group">
					<label class="col-sm-2 control-label">手机号码:</label>
					<div class="col-sm-2">
						<form:input path="phone" htmlEscape="false" cssClass="form-control mobile" maxlength="50"/>
					</div>
				</div> 
				<div class="form-group mt10">
					<label class="col-sm-1 control-label"></label>
					<div class="col-sm-4">
						<p style="color: red">-------------狂犬病和成人模块权限功能 （默认否不开启自动流程）-------------</p>
					</div>
				</div> 
				<div class="form-group">
					<label class="col-sm-2 control-label">自动流程:</label>
					<div class="col-sm-2">
						<form:radiobuttons path="openStatus" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false"
							element="label class='checkbox-inline i-checks required'"/>
					</div>
				</div> 
				<div class="form-group">
					<label class="col-sm-2 control-label">自动出票:</label>
					<div class="col-sm-2">
						<form:radiobuttons path="printStatus" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false"
							element="label class='checkbox-inline i-checks required'"/>
					</div>
				</div> 
				<div class="form-group">
					<label class="col-sm-2 control-label">备注:</label>
					<div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" cssClass="form-control" class="input-xlarge"/>
					</div>
				</div>
			<%--	<div class="form-group">
					<label class="col-sm-2 control-label">用户类型:</label>
					<div class="col-sm-2">
						<label class="form-control">${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</label>
					</div>
				</div>
				 <div class="form-group">
					<label class="col-sm-2 control-label">用户角色:</label>
					<div class="col-sm-2">
						<label class="form-control">${user.roleNames}</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上次登录:</label>
					<div class="controls">
						<label class="lbl">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>
					</div>
				</div> --%>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
						<%-- <a class="btn btn-primary" href="javascript:void(0);" onclick="alertForm('${ctx}/sys/user/modifyPwd','修改密码', 500, 400)">修改密码</a> --%>
						<%-- <a class="btn btn-default" href="${ctx}">返回</a> --%>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>