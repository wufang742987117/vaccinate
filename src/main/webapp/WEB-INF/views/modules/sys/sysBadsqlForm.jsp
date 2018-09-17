<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>sql上报失败管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
		function MaxMe(o) {
		    o.style.height = o.scrollTop + o.scrollHeight + "px";
		}
		$(document).ready(function() {
			//$("#name").focus();
			
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					$.ajax({
						url:'${ctx}/sys/sysBadsql/save',
						data : {
							"id":$("#sqlId").val(),
							"sqlTime":$("#sqlTime").val(),
							"sqlContext" : $("#sqlContext").val(),
							"sqlStatus" : $("#sqlStatus").val(),
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
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="sysBadsql" action="${ctx}/sys/sysBadsql/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					
					<c:if test="${not empty sysBadsql.id }">
						<input type="hidden" id="sqlId" value="${sysBadsql.id }">
						<label class="col-sm-2 control-label">sql时间：</label>
                        <div class="col-sm-4">
						<input id="sqlTime" name="sqlTime" readonly="" value="<fmt:formatDate value="${sysBadsql.sqlTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date required">
						<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</c:if>
					<c:if test="${empty sysBadsql.id }">
						<label class="col-sm-2 control-label">sql时间：</label>
                        <div class="col-sm-4">
						<input id="sqlTime" name="sqlTime" readonly="" 
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date required">
						<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</c:if>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">sql内容：</label>
                        <div class="col-sm-4">
						<form:textarea path="sqlContext" htmlEscape="false" class="form-control " onpropertychange="MaxMe(this)" oninput="MaxMe(this)" style="overflow:hidden"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">sql状态：</label>
                        <div class="col-sm-4">
						<form:input path="sqlStatus" htmlEscape="false" maxlength="10" class="form-control required" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>