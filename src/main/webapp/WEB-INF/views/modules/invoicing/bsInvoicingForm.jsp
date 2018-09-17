<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>进销存统计管理</title>
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
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsInvoicing" action="${ctx}/invoicing/bsInvoicing/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">产品id：</label>
                    <div class="col-sm-2">
						<form:input path="productId" htmlEscape="false" maxlength="32" class="form-control required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗编号：</label>
                    <div class="col-sm-2">
						<form:input path="vaccineid" htmlEscape="false" maxlength="10" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗小类名称：</label>
                    <div class="col-sm-2">
						<form:input path="vaccName" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">批次编号：</label>
                    <div class="col-sm-2">
						<form:input path="batchno" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗规格：</label>
                    <div class="col-sm-2">
						<form:input path="specification" htmlEscape="false" maxlength="20" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗的制造厂商：</label>
                    <div class="col-sm-2">
						<form:input path="manufacturer" htmlEscape="false" maxlength="50" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">有效期：</label>
                    <div class="col-sm-2">
						<input  name="vaccExpDate" readonly="" value="<fmt:formatDate value="${bsInvoicing.vaccExpDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date ">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出售价格：</label>
                    <div class="col-sm-2">
						<form:input path="sellprice" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">成本价：</label>
                    <div class="col-sm-2">
						<form:input path="costprice" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">期初库存：</label>
                    <div class="col-sm-2">
						<form:input path="oldStorenum" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">期末库存：</label>
                    <div class="col-sm-2">
						<form:input path="newStorenum" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">领取/购进（入库）：</label>
                    <div class="col-sm-2">
						<form:input path="receive" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">损耗（出库）：</label>
                    <div class="col-sm-2">
						<form:input path="consume" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">使用/接种记录（存储）：</label>
                    <div class="col-sm-2">
						<form:input path="apply" htmlEscape="false" maxlength="18" class="form-control  digits"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
                    <div class="col-sm-2">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">状态：</label>
                    <div class="col-sm-2">
						<form:input path="status" htmlEscape="false" maxlength="2" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">操作单位编码：</label>
                    <div class="col-sm-2">
						<form:input path="localcode" htmlEscape="false" maxlength="32" class="form-control "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">历史时间：</label>
                    <div class="col-sm-2">
						<input  name="histroydate" readonly="" value="<fmt:formatDate value="${bsInvoicing.histroydate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date ">
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
						<%-- <shiro:hasPermission name="invoicing:bsInvoicing:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> --%>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>