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
				$.ajax({
					url:'${ctx}/enter/sysEnterpriseInfo/save',
					data : {
						"isNewRecord":$("#isNewRecord").val(),
						"id":$("#id").val(),
						"name":$("#name").val(),
						"nameAll":$("#nameAll").val(),
						"code" : $("#code").val(),
						"indexNum" : $("#indexNum").val(),
						"doIf" : $("#doIf").val()
					},
					success:function(data){
						if(data.status == '200'){
							toastrMsg(data.msg, 'success');
							setTimeout(function() {
								parent.location.reload();
							}, 1500)
						}else{
						toastrMsg(data.msg, 'error');
						
						}
					},
					error:function(a,b,c){
					
					}
				});
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
	<!--  
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/enter/sysEnterpriseInfo/">信息列表</a></li>
		<li class="active"><a href="${ctx}/enter/sysEnterpriseInfo/form?id=${sysEnterpriseInfo.id}">信息<shiro:hasPermission name="enter:sysEnterpriseInfo:edit">${not empty sysEnterpriseInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="enter:sysEnterpriseInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	-->
	<br/>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysEnterpriseInfo" action="${ctx}/enter/sysEnterpriseInfo/save" method="post" class="form-horizontal">
				<%-- <form:hidden path="id"/> --%>
				<form:hidden path="isNewRecord"/> 
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">ID：</label>
                        <div class="col-sm-6">
						<form:input path="id" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-3 control-label">名称：</label>
                        <div class="col-sm-6">
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">全名称：</label>
                        <div class="col-sm-6">
						<form:input path="nameAll" htmlEscape="false" maxlength="100" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">code：</label>
                        <div class="col-sm-6">
						<form:input path="code" htmlEscape="false" maxlength="10" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">排序字段：</label>
                        <div class="col-sm-6">
						<form:input path="indexNum" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">1：可用 0：不可用：</label>
                        <div class="col-sm-6">
						<form:input path="doIf" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
						<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
						
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>