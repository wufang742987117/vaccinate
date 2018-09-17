<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作时间段管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
	$(document).ready(function() {
		//$("#name").focus();
		
		$("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				$.ajax({
					url:'${ctx}/sys/sysWorkingHours/save',
					data : {
						"id":$("#id").val(),
						"localCode":$("#localCode").val(),
						"week" : $("#week").val(),
						"timeSlice" : $("#timeSlice").val(),
						"maximum":$("#maximum").val(),
						"dateDay":$("#dateDay").val(),
						"remarks":$("#remarks").val()
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
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/sysWorkingHours/">工作时间段列表</a></li>
		<li class="active"><a href="${ctx}/sys/sysWorkingHours/form?id=${sysWorkingHours.id}">工作时间段<shiro:hasPermission name="sys:sysWorkingHours:edit">${not empty sysWorkingHours.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:sysWorkingHours:edit">查看</shiro:lacksPermission></a></li>
	</ul> --%>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysWorkingHours" action="${ctx}/sys/sysWorkingHours/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				
				
				<div class="form-group">
					<label class="col-sm-3 control-label">接种单位编码：</label>
                        <div class="col-sm-5">
						<form:input path="localCode" htmlEscape="false" maxlength="50" class="form-control"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-3 control-label">星期：</label>
                        <div class="col-sm-5">
						<form:select path="week" htmlEscape="false" maxlength="50" class="form-control ">
							<form:option value="星期一">星期一</form:option>
							<form:option value="星期二">星期二</form:option>
							<form:option value="星期三">星期三</form:option>
							<form:option value="星期四">星期四</form:option>
							<form:option value="星期五">星期五</form:option>
							<form:option value="星期六">星期六</form:option>
							<form:option value="星期日">星期日</form:option>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">时间段：</label>
                        <div class="col-sm-5">
						<form:input path="timeSlice" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">该时段最大接种人数：</label>
                        <div class="col-sm-5">
						<form:input path="maximum" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">星期(从1到7)：</label>
                        <div class="col-sm-5">
						<form:input path="dateDay" htmlEscape="false" maxlength="1" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">备注：</label>
                        <div class="col-sm-5">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
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