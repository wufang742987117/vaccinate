<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>每日盘库管理</title>
	<meta name="decorator" content="default"/>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/product/bsManageCheck/">每日盘库列表</a></li>
		<li class="active"><a href="${ctx}/product/bsManageCheck/form?id=${bsManageCheck.id}">每日盘库<shiro:hasPermission name="product:bsManageCheck:edit">${not empty bsManageCheck.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="product:bsManageCheck:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsManageCheck" action="${ctx}/product/bsManageCheck/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗产品id：</label>
					<div class="col-sm-2">
						<form:input path="product.id" htmlEscape="false" maxlength="32" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上期盘点时间：</label>
					<div class="col-sm-2">
						<input  name="lastDate" readonly="" value="<fmt:formatDate value="${bsManageCheck.lastDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date required">
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">上期盘点库存：</label>
					<div class="col-sm-2">
						<form:input path="lastNum" htmlEscape="false" maxlength="18" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">本期入库：</label>
					<div class="col-sm-2">
						<form:input path="inNum" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">本期出库：</label>
					<div class="col-sm-2">
						<form:input path="outNum" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">本期使用：</label>
					<div class="col-sm-2">
						<form:input path="useNum" htmlEscape="false" maxlength="18" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">本期剩余：</label>
					<div class="col-sm-2">
						<form:input path="restNum" htmlEscape="false" maxlength="18" class="form-control required digits"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">盘点日期-盘点所属日期：</label>
					<div class="col-sm-2">
						<input  name="checkDate" readonly="" value="<fmt:formatDate value="${bsManageCheck.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date required">
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">盘点类型-1当日 2-次日：</label>
					<div class="col-sm-2">
						<form:input path="checkType" htmlEscape="false" maxlength="1" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="product:bsManageCheck:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>