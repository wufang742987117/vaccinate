<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>疫苗名称</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$.ajax({
						url:'${ctx}/vaccine/bsManageVaccine/saveApi',
						data : {
							"gName" : $("#gName").val(),
							"name" : $("#name").val(),
							"nameAll" : $("#nameAll").val(),
							"id" : $("#id").val(),
							"codeAll" : $("#codeAll").val(),
							"pathema" : $("#pathema").val(),
							"inType" : $("#inType").val(),
							"mNum" : $("#mNum").val(),
							"live" : $("input[name=live]").val(),
							"type" : $("input[name=type]").val()
						},
						success:function(data){
							if(data.status == '200'){
								toastrMsg(data.msg, 'success');
								setTimeout(function() {
									parent.layer.closeAll();
								}, 1500)	
							}else{
							toastrMsg(data.msg, 'error');
							}
						},
						error:function(a,b,c){
						}
					});
// 					form.submit();
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
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsManageVaccine" action="${ctx}/vaccine/bsManageVaccine/save" method="post" class="form-horizontal">
<%-- 				<form:hidden path="id"/> --%>
				<sys:message content="${message}"/>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">大类名称：<span class="red">*</span></label>
                        <div class="col-sm-8">
						<form:input path="gName" htmlEscape="false" maxlength="10" class="form-control required"/>
						
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">疫苗简称：<span class="red">*</span></label>
                        <div class="col-sm-8">
						<form:input path="name" htmlEscape="false" maxlength="18" class="form-control required "/>
						
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">疫苗全称：<span class="red">*</span></label>
                       <div class="col-sm-8">
						<form:input path="nameAll" htmlEscape="false" maxlength="18" class="form-control required "/>
						
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">国标编码：<span class="red">*</span></label>
                       <div class="col-sm-8">
						<form:input path="id" htmlEscape="false" maxlength="18" class="form-control required "/>
						
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">英文简称：<span class="red">*</span></label>
                       <div class="col-sm-8">
						<form:input path="codeAll" htmlEscape="false" maxlength="10" class="form-control required"/>
						
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">预防疾病：</label>
                       <div class="col-sm-8">
						<form:input path="pathema" htmlEscape="false" rows="4" maxlength="100" class="form-control "/>
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">接种类型：<span class="red">*</span></label> 
					<div class="col-sm-8">
						<form:select path="inType" cssClass="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('intype')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">模型大类：</label>
                       <div class="col-sm-8">
						<form:input path="mNum" htmlEscape="false" maxlength="10" class="form-control"/>
						
					</div>
				</div> 
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">是否活苗：<span class="red">*</span></label>
					<div class="col-sm-8">
						<form:radiobuttons path="live" items="${fns:getDictList('yes_no')}" cssClass="required" element="label class='checkbox-inline i-checks'" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>

				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">种类：<span class="red">*</span></label>
					<div class="col-sm-8">
						<form:radiobuttons path="type" items="${fns:getDictList('isImport')}" cssClass="required" element="label class='checkbox-inline i-checks'" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>

				<div class="form-group col-sm-12">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
						<input id="btnCancel" class="btn ml10" type="button" value="返 回" onclick="parent.layer.closeAll()"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>