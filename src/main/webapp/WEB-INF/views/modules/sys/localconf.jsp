<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接种单位配置</title>
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
			
			$("#name").change(function(){
				var list=${list};
				var name=$("#name").val();
				$.each(list, function(idx, item) { //循环对象取值
					if(item.name==name){
					$("#code").val(item.code);
						return;
					}
				});
			});
			$("#code").change(function(){
				var list=${list};
				var code=$("#code").val();
				$.each(list, function(idx, item) { //循环对象取值
					if(item.code==code){
					$("#name").val(item.name);
						return;
					}
				});
			});
		});
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/sys/user/info">科室设置</a></li>
		<li ><a href="${ctx}/sys/user/modifyPwd">密码设置</a></li>
		<li class="active"><a href="${ctx}/sys/office/localconf">接种单位设置</a></li>
		<li ><a href="${ctx}/inoculate/quene/msgPostTo">信息推送</a></li>
		<li ><a href="${ctx}/sys/user/list">用户管理</a></li>
		<li ><a href="${ctx}/sys/user/about">门诊功能配置</a></li>
		<div class=" row pull-right">
			<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
		</div>
	</ul> 
	<div class="ibox">
		<div class="mt20">
			<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/localconfsave" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 col-xs-3 text-right control-label">接种单位名称:</label>
					<div class="col-sm-2 col-xs-5">
						<%-- <form:input path="name" htmlEscape="false" maxlength="50" cssClass="form-control" class="required"/> --%>
						<form:select path="name" class="form-control" >
							<form:options items="${departmentlist}"
								itemLabel="name" itemValue="name" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 col-xs-3 text-right control-label">接种单位编码:</label>
					<div class="col-sm-2 col-xs-5">
						<%-- <form:input path="code" htmlEscape="false" maxlength="50" cssClass="form-control" class="required"/> --%>
						<form:select path="code" class="form-control" >
							<form:options items="${departmentlist}"
								itemLabel="code" itemValue="code" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2 col-xs-offset-3">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="修改"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>