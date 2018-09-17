<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联合疫苗替代原则管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$.ajax({
						url:'${ctx}/vaccine/bsManageBinded/save',
						data : {
							"id":$("#bsId").val(),
							"bindVaccId" : $("#bindVaccId").val(),
							"bindVaccPin" : $("#bindVaccPin").val(),
							"vaccId" : $("#vaccId").val(),
							"vaccPin" : $("#vaccPin").val(),
							"defaultVaccId" : $("#defaultVaccId").val(),
							"remarks" : $("#remarks").val()
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
	<div class="ibox" style="">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsManageBinded" action="${ctx}/vaccine/bsManageBinded/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-3 control-label">被联合疫苗：</label>
                    <div class="col-sm-3">
						<c:if test="${not empty bsManageBinded.id }">
							<input type="hidden" id="bsId" value="${bsManageBinded.id }">
							<form:select path="vaccId" class="form-control">
								<form:options items="${vaccineList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</c:if>
						<c:if test="${empty bsManageBinded.id }">
							<form:select path="vaccId" class="form-control">
								<form:option value="" label="--请选择--"/>
								<form:options items="${vaccineList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</c:if>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
					
					<label class="col-sm-3 control-label">被联合疫苗剂次：</label>
                    <div class="col-sm-2">
						<form:input path="vaccPin" htmlEscape="false" maxlength="18" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				
				<hr style="margin: 15px 0px;" />
				
				<div class="form-group">
					<label class="col-sm-3 control-label">联合疫苗：</label>
                        <div class="col-sm-3">
						<c:if test="${not empty bsManageBinded.id }">
							<form:select path="bindVaccId" class="form-control">
								<form:options items="${vaccineList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</c:if>
						<c:if test="${empty bsManageBinded.id }">
							<form:select path="bindVaccId" class="form-control">
								<form:option value="" label="--请选择--"/>
								<form:options items="${vaccineList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</c:if>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
					
					<label class="col-sm-3 control-label">联合疫苗剂次：</label>
                        <div class="col-sm-2">
						<form:input path="bindVaccPin" htmlEscape="false" maxlength="18" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				
				<hr style="margin: 15px 0px;" />
				
				<div class="form-group">
					<label class="col-sm-3 control-label">默认使用疫苗：</label>
                        <div class="col-sm-3">
						<c:if test="${not empty bsManageBinded.id }">
							<form:select path="defaultVaccId" class="form-control">
								<form:options items="${vaccineList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</c:if>
						<c:if test="${empty bsManageBinded.id }">
							<form:select path="defaultVaccId" class="form-control">
								<form:option value="" label="--可选择--"/>
								<form:options items="${vaccineList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
						</c:if>
					</div>
				</div>
				
				<hr style="margin: 15px 0px;" />
				
				<div class="form-group">
					<label class="col-sm-3 control-label">评论：</label>
                        <div class="col-sm-3">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<%-- <shiro:hasPermission name="vaccine:bsManageBinded:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%> 
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
						<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>