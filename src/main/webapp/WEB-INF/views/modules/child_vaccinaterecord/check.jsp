<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>查看</title>
<meta name="decorator" content="default" />
</head>
<body>

	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="childVaccinaterecord"
				action="${ctx}/child_vaccinaterecord/childVaccinaterecord/save"
				method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				
				
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：</label>
					<div class="col-sm-5">
					 <form:input path="vaccName" htmlEscape="false" maxlength="50" readonly="true"
							class="form-control " /> 
							
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗批号：</label>
					<div class="col-sm-5">
					
							 <form:input path="batch" htmlEscape="false" maxlength="50" readonly="true"
							class="form-control " /> 
					</div>
				</div> 
				
				 <div class="form-group">
					<label class="col-sm-2 control-label">疫苗厂家：</label>
					<div class="col-sm-5">
							
						<form:input path="manufacturer" htmlEscape="false" maxlength="50" readonly="true"
							class="form-control " /> 
					</div>
				</div> 
				
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">疫苗价格：</label>
					<div class="col-sm-5">
						<form:input path="price" htmlEscape="false" maxlength="50" readonly="true"
							class="form-control required number" />
					</div>
				</div> --%>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗价格：</label><c:choose>
					<c:when test="${childVaccinaterecord.price!=0 }">
					<div class="col-sm-5" >
					<label class="col-sm-2 control-label"><span class="label label-danger">自费</span>	</label>
					</div>
					</c:when>
					<c:otherwise>
					<div class="col-sm-5" >
					<label class="col-sm-2 control-label"><span class="label label-deafult">免费</span>	</label>
					</div>
					</c:otherwise>
					
					</c:choose>
					<div class="col-sm-5" hidden="hidden">
						<form:input path="price" htmlEscape="false" maxlength="50" 
							class="form-control required number" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">针&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</label>
					<div class="col-sm-5">
						<form:input path="dosage" htmlEscape="false" maxlength="50" readonly="true"
							class="form-control " />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种日期：</label>
					<div class="col-sm-5">
						<input name="vaccinatedate" readonly="true"
							value="<fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							class="form-control " 
							>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种部位：</label>
					<div class="col-sm-5">
						<form:select path="bodypart" class="form-control " disabled="true">
							<form:options items="${fns:getDictList('position')}" itemLabel="label"
								itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种类型：</label>
					<div class="col-sm-5">
						<form:select path="vacctype" class="form-control " disabled="true">
							<form:options items="${fns:getDictList('vacctype')}" itemLabel="label"
								itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">医生姓名：</label>
					<div class="col-sm-5">
						<form:input path="doctor" htmlEscape="false" maxlength="20" readonly="true"
							class="form-control " />
					</div>
				</div>
				
				
				 <div class="form-group">
					<label class="col-sm-2 control-label">异常反应：</label>
					<div class="col-sm-5">
						<form:radiobuttons path="iseffect" items="${fns:getDictList('iseffect')}"
							itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"
							element="label class='checkbox-inline i-checks'" class="required" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">保险：</label>
					<div class="col-sm-5">
						<%-- <form:input path="insurance" htmlEscape="false" maxlength="200"
							class="form-control " /> --%>
							<form:radiobuttons path="insurance" items="${fns:getDictList('insurance')}"
							itemLabel="label" itemValue="value" htmlEscape="false"
							element="label class='checkbox-inline i-checks'" class="required" disabled="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-5">
						<form:input path="remarks" htmlEscape="false" maxlength="200" readonly="true"
							class="form-control " />
					</div>
				</div>
			
			</form:form>
				
		</div>

	</div>
</body>
</html>