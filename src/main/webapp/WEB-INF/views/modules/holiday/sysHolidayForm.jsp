<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节假日管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					if($("#dateType").val() == 1 && !$("#dateDay").val()){
						toastrMsg("请选择日期");
						return false;
					}
					if($("#dateType").val() == 0 && !$("#dateTime").val()){
						toastrMsg("请选择日期");
						return false;
					}
					$.ajax({
						url:'${ctx}/holiday/sysHoliday/saveApi',
						data:{
							"id":$("#id").val(),
							"dateType":$("#dateType").val(),
							"dateTime":$("#dateTime").val(),
							"dateDay":$("#dateDay").val(),
							"remarks":$("#remarks").val()
						},
						success:function(data){
							closeLoading();
							if(data.code == '200'){
								toastrMsg(data.msg,'success');
								setTimeout(function() {
									parent.location.reload();
									parent.layer.closeAll();
								}, 800);
							}else if(data.code == '500'){
								toastrMsg(data.msg,'error');
							}else{
								toastrMsg('保存失败，请重新添加','error');
							}
						},
						error:function(a,b){
							closeLoading();
							toastrMsg('保存失败，请重新添加','error');
							console.error(a,b);
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
			
			$("#dateType").change(function(){
				if($(this).val() == '1'){
					$(".dateTimeGroup").hide();
					$(".dateDayGroup").show();
				}else if($(this).val() == '0'){
					$(".dateDayGroup").hide();
					$(".dateTimeGroup").show();
				}
			});
			
			$("#dateDay").change(function(){
				if($(this).val()){
					var html = $("#dateDay option[value=" + $(this).val() + "]").html();
					$("#remarks").val(html);
				}else{
					$("#remarks").val("");
				}
			});
			
			$("#dateType").change();
		});
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysHoliday" action="" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">节假日类型：<span class="help-inline"><font color="red">*</font> </span> </label>
                        <div class="col-sm-4">
						<form:select path="dateType" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('holiday_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						
					</div>
				</div>
				<div class="form-group dateTimeGroup">
					<label class="col-sm-2 control-label">具体日期：</label>
                        <div class="col-sm-4">
						<input id="dateTime"  name="dateTime" readonly="" value="<fmt:formatDate value="${sysHoliday.dateTime}" pattern="yyyy-MM-dd"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date ">
					</div>
				</div>
				<div class="form-group dateDayGroup">
					<label class="col-sm-2 control-label">星期几：</label>
                        <div class="col-sm-4">
<%-- 						<form:input path="dateDay" htmlEscape="false" maxlength="1" class="form-control "/> --%>
						<form:select path="dateDay" class="form-control">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('holiday_date_day')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">说明：</label>
                        <div class="col-sm-4">
						<form:input path="remarks" htmlEscape="false" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
<%-- 						<shiro:hasPermission name="holiday:sysHoliday:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission> --%>
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="parent.layer.closeAll()"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>