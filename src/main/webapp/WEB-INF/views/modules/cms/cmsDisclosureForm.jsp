<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>告知书管理</title>
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
		});
		
		function changeValue(){
			 $("#refundReason option[value='"+key+"']").attr("selected","selected");
		}
		
	</script>
	
	<c:if test="${not empty isok}">
		<script type="text/javascript">
			$(function() {
				setTimeout(funB, 1500);
			});
			function funB(){
				parent.layer.closeAll();
			} 
		</script>
	</c:if>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/cmsDisclosure/">告知书列表</a></li>
		<li class="active"><a href="${ctx}/cms/cmsDisclosure/form?id=${cmsDisclosure.id}">告知书<shiro:hasPermission name="cms:cmsDisclosure:edit">${not empty cmsDisclosure.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:cmsDisclosure:edit">查看</shiro:lacksPermission></a></li>
	</ul> --%>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="cmsDisclosure" action="${ctx}/cms/cmsDisclosure/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					
					<label class="col-sm-2 control-label">疫苗外键id：</label>
                    <div class="col-sm-8">
	                    <form:select path="vaccine.gNum" class="form-control" onchange="">
	                    	<form:option value="" label="--请选择--"/>
							<form:options items="${groups}" itemLabel="gName" itemValue="gNum" htmlEscape="false"/>
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">context：</label>
                    <div class="col-sm-8">
						<form:textarea path="context" htmlEscape="false" rows="12" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.layer.closeAll()"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>