<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<c:if test="${not empty arg }">
		<script type="text/javascript">
			$(function() {
				parent.closeForm();
			});
		</script>
	</c:if>
	<c:if test="${not empty hint }">
		<script type="text/javascript">
			$(function() {
				layer.msg("${hint}");
			});
		</script>
	</c:if>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
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
		function a(){
		var list=${list};
		var name=$("#name").val();
		$.each(list, function(idx, item) { //循环对象取值
							if(item.gName==name){
							$("#group").val(item.gNum);
							$("#code").val(item.gCode);
							return;
							}
						});
		};
		function b(){
		var list=${list};
		var group=$("#group").val();
		$.each(list, function(idx, item) { //循环对象取值
							if(item.gNum==group){
							$("#name").val(item.gName);
							$("#code").val(item.gCode);
							return;
							}
						});
		};
		function c(){
		var list=${list};
		var code=$("#code").val();
		$.each(list, function(idx, item) { //循环对象取值
							if(item.gCode==code){
							$("#group").val(item.gNum);
							$("#name").val(item.gName);
							return;
							}
						});
		};
		
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsManageVaccinenum" action="${ctx}/vaccine/bsManageVaccinenum/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<c:choose>
                    <c:when test="${bsManageVaccinenum.id==null ||bsManageVaccinenum.id==''}">
		                <div class="form-group">
							<label class="col-sm-2 control-label">模型大类：<span class="help-inline"><font color="red">*</font> </span></label>
		                    <div class="col-sm-5">
								<form:select path="name" class="form-control required" onchange="a()">
									<form:options items="${VaccineList}" itemLabel="gName" itemValue="gName" htmlEscape="false" />
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">大类编码：<span class="help-inline"><font color="red">*</font> </span></label>
		                    <div class="col-sm-5">
								<form:select path="group" class="form-control required" onchange="b()">
									<form:options items="${VaccineList}" itemLabel="gNum" itemValue="gNum" htmlEscape="false" />
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">英文名称：<span class="help-inline"><font color="red">*</font> </span></label>
		                    <div class="col-sm-5">
								<form:select path="code" class="form-control required" onchange="c()">
									<form:options items="${VaccineList}" itemLabel="gCode" itemValue="gCode" htmlEscape="false" />
								</form:select>
							</div>
						</div>
                    </c:when>
                	<c:otherwise>
		                <div class="form-group">
							<label class="col-sm-2 control-label">疫苗大类：<span class="help-inline"><font color="red">*</font> </span></label>
	                        <div class="col-sm-5">
								<form:input path="name" htmlEscape="false" maxlength="30" class="form-control required" readonly="true"/> 
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">大类编码：<span class="help-inline"><font color="red">*</font> </span></label>
		                    <div class="col-sm-5">
		                        <form:input path="group" htmlEscape="false" maxlength="30" class="form-control required" readonly="true"/> 
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">英文名称：<span class="help-inline"><font color="red">*</font> </span></label>
		                    <div class="col-sm-5">
								<form:input path="code" htmlEscape="false" maxlength="30" class="form-control required" readonly="true"/> 
							</div>
						</div>
                    </c:otherwise>
                </c:choose>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗类型：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-5">
						<form:radiobuttons path="type" items="${fns:getDictList('type')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'" class="required" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗权重：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-5">
						<form:input path="weight" htmlEscape="false" maxlength="50" class="form-control required" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">儿童月龄：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-5">
						<form:select path="mouage" class="form-control required">
							<form:options items="${fns:getDictList('mouage')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">针次：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-5">
	                      <c:choose>
		                      <c:when test="${bsManageVaccinenum.id==null||bsManageVaccinenum.id=='' }">
			                      <form:select path="pin" class="form-control required">
								  		<form:options items="${fns:getDictList('pin')}" itemLabel="label" itemValue="value" htmlEscape="false" />
								  </form:select> 
		                      </c:when>
		                      <c:otherwise>
		                      		<form:input path="pin" htmlEscape="false" maxlength="50" class="form-control required" readonly="true" />
		                      </c:otherwise>
	                      </c:choose>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗状态：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-5">
						<form:radiobuttons path="status" items="${fns:getDictList('status')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'" class="required" />
					</div>	
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种方式：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-5">
						<form:select path="intype" items="${fns:getDictList('intype')}" itemLabel="label" itemValue="value" htmlEscape="false" class="form-control" />
					</div>	
				</div>
				<c:if test="${not empty bsManageVaccinenum.id }">
					<div class="form-group">
						<label class="col-sm-2 control-label">接种类型：</label>
                        <div class="col-sm-5">
							<form:select path="vaccType" class="form-control" disabled="true">
								<form:options items="${fns:getDictList('vacctype')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select> 
						</div>
					</div>
				</c:if>				
				<c:if test="${empty bsManageVaccinenum.id }">
					<div class="form-group">
						<label class="col-sm-2 control-label">接种类型：</label>
                        <div class="col-sm-5">
							<form:select path="vaccType" class="form-control">
									<form:options items="${fns:getDictList('vacctype')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select> 
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label class="col-sm-2 control-label">间隔(月)：</label>
                    <div class="col-sm-5">
						<form:select path="pintime" class="form-control">
								<form:option value=""></form:option>
								<form:options items="${fns:getDictList('pintime')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最晚月龄：</label>
                    <div class="col-sm-5">
						<form:select path="lasttime" class="form-control">
							<form:option value=""></form:option>
							<form:options items="${fns:getDictList('lasttime')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select> 
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">替代关系：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-5">
						<form:select path="pentrep" items="${fns:getDictList('pentrep')}" itemLabel="label" itemValue="value" htmlEscape="false" class="form-control required" />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-5 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>