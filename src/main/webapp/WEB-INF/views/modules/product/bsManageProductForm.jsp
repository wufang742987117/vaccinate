<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					var type="${bsManageProduct.isforeign}";
					var result=$(".checked").find("input").val();
					var id="${bsManageProduct.id}";
					/* if(id&&(type!=result)){
						layer.confirm("确定改变疫苗类型？", {
					        btn: ['确认','取消'], //按钮
					        shade: true, //不显示遮罩
					        icon : 3,
					        offset : ['300px' , '35%']
					    }, function(){
					    	
					    });
					}else{
						loading('正在提交，请稍等...');
						form.submit();
					} */
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
 			//$(".div_costprice").hide();
			$(".iCheck-helper").click(function(){
				var check=$(this).parent().find("input").val();
				if(check=="2"){
					$(".div_costprice").show();
				}else{
					$(".div_costprice").hide();
				}
				
			})
			showOrHide();
		});
		
		function showOrHide(){
			var type="${bsManageProduct.isforeign}";
			if(type=="2"){
				$(".div_costprice").show();
			}
		}
		
	</script>
	<c:if test="${not empty isok}">
		<script type="text/javascript">
			$(function() {
				layer.msg('保存成功');
				setTimeout(funB, 1500);
			});
			function funB(){
				parent.closeForm();
			} 
		</script>
	</c:if>
</head>
<body>
	
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsManageProduct" action="${ctx}/product/bsManageProduct/save" method="post" class="form-horizontal ">
				<form:hidden path="id"/>
				<form:hidden path="dosage"/>
				<input type="hidden" name="dosage" value="1">
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：</label>
                    <div class="col-sm-4" >
						<form:select path="vaccineid" class="form-control ">
							<form:options items="${vacclist}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
					</div>
					<label class="col-sm-2 control-label">批次编号：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
                        <form:input path="batchno" maxlength="50" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">生产企业：</label>
                    <div class="col-sm-4" >
						<form:select path="manufacturer" class="form-control ">
							<form:options items="${EnterpriseInfoList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
					</div>
					<label class="col-sm-2 control-label">疫苗规格：</label>
                    <div class="col-sm-4" >
						<form:select path="specification" class="form-control ">
							<form:options items="${fns:getDictList('specification')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
<!-- 					<label class="col-sm-2 control-label">每盒剂次：<span class="help-inline"><font color="red">*</font> </span></label> -->
<!--                     <div class="col-sm-4" > -->
<%--                         <form:input path="dosage" maxlength="10" class="form-control plusDigits required number"/> --%>
<!-- 					</div> -->
					<label class="col-sm-2 control-label">有效期：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<input  name="vaccExpDate" value="<fmt:formatDate value="${bsManageProduct.vaccExpDate}" pattern="yyyy-MM-dd"/>"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date required ">
					</div>
					<label class="col-sm-2 control-label">剂量(剂/支)：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
                        <form:input path="spec" maxlength="2" class="form-control required number"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗类型：</label>
                    <div class="col-sm-4" >
						<form:radiobuttons path="isforeign" items="${fns:getDictList('isImport')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'" class=""/>
					</div>
					<label class="col-sm-2 control-label">出售价格：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4" >
						<form:input path="sellprice" htmlEscape="false" maxlength="15" class="form-control plusNum required "/>
					</div>
				</div>
				<div class="form-group div_costprice">
					<label class="col-sm-2 control-label">成本价格：<span class="help-inline"></span></label>
                    <div class="col-sm-4" >
						<form:input path="costprice" htmlEscape="false" maxlength="15" class="form-control plusNum required costprice"/>
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">库存：</label>
                        <div class="col-sm-4" >
                        <c:if test="${bsManageProduct.id==null||bsManageProduct.id=='' }">
                        	<form:input path="storenum" value="0"   htmlEscape="false" maxlength="18" class="form-control  digits" />
                        </c:if>
					</div>
				</div> 
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">是否显示：</label>
                        <div class="col-sm-4" >
						<form:radiobuttons path="isshow" items="${fns:getDictList('isShow')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'" class=""/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">最小适用月龄：</label>
                    <div class="col-sm-4">
                        <form:input path="applicableMin" maxlength="2" class="form-control number"/>
					</div>
					<label class="col-sm-2 control-label">最大适用月龄：</label>
					<div class="col-sm-4">
                    	<form:input path="applicableMax" maxlength="2" class="form-control number"/>
                   	</div>
				</div>
				<div class="form-group">
					<div class="col-sm-2"></div>
					注：适用月龄默认值为0，不做提示。若该疫苗注明适用月龄范围，请配置适用月龄。
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="product:bsManageProduct:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp; </shiro:hasPermission>
						<%--<input id="btnCancel" class="btn" type="button" value="关闭" onclick="parent.closeForm()"/> --%>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>