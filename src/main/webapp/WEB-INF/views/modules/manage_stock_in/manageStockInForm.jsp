<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>入库</title>
<meta name="decorator" content="default" />
<c:if test="${not empty arg }">
	<script type="text/javascript">
		$(function() {
		layer.msg('操作成功');
		setTimeout(funB, 1200);
		});
		function funB(){
		parent.closeForm();
		}
	</script>
</c:if>
<script type="text/javascript">
$(document).ready(
	function() {
		$("#inputForm").validate(
			{
				submitHandler : function(form) {
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")|| element.is(":radio")|| element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
	});
	
	$(function() {
		$("#num").keyup(function() {
			if (eval($("#num").val()) > eval($("#amount").html())) {
				$("input[name='num']").val($("#amount").html());
			}
		});
	});
	
	$(function(){
		$("#name").change(
				//通过疫苗名称询所有的批次
				function batchno() {
					var name = $("#name").val();
					$.ajax({
						type : "POST",
						url : "${ctx}/manage_stock_in/manageStockIn/batchno",
						data : {name : name},
						dataType : "json",
						success : function(data) {
						var	html="";
						$.each(data, function(idx, item) { //循环对象取值
							html += "<option value='" + item.batchno + "'>"+ item.batchno + "</option>";
						});
						$("#batchno").html(html);
						$("#batchno").change(); 
						}
					});
				}
			
			);
			
			$(function(){
			$("#batchno").change(
				//通过疫苗名称和批次查询所有的厂家
				function manufacturer() {
					var name = $("#name").val();
					var batchno = $("#batchno").val();
					$.ajax({
						type : "POST",
						url : "${ctx}/manage_stock_in/manageStockIn/manufacturer",
						data : {
						name :  name,
						batchno : batchno
						},
						dataType : "json",
						success : function(data) {
							var html = "";
							$.each(data, function(idx, item) { //循环对象取值
								html += "<option value='" + item.manufacturer + "'>"+ item.manufacturer + "</option>";
							});
							$("#manufacturer").html(html);
							 var html = "";
							$.each(data, function(idx, item) { //循环对象取值
								 html += "<option value='" + item.specification + "'>"+ item.specificationname + "</option>"; 
								/*  html += "<option value='" + item.specification + "'>"+ item.specification + "</option>";  */
							});
							 $("#specification").html(html); 
							$("#manufacturer").change();
						}
					});
				
				}
			);
	
		});
	$("#manufacturer").change(
		//通过疫苗名称厂家和批次查询疫苗库存
		function amount() {
			var name = $("#name").val();
			var manufacturer = $("#manufacturer").val();
			var batchno = $("#batchno").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/manage_stock_in/manageStockIn/amount",
				data : {
					name : name,
					batchno : batchno,
					manufacturer:manufacturer
				},
				dataType : "json",
				success : function(data) {
					$("#amount").html(data);
					
				}
			});
		}
	);
})
</script>

</head>
<body>

	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="manageStockIn"
				action="${ctx}/manage_stock_in/manageStockIn/save" method="post"
				class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<div class="form-group">
						<label class="col-sm-2 control-label">名称：</label>
					<div class="col-sm-2">
						<form:select path="name" class="form-control required" >
							<c:forEach items="${VaccineList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.id }"
									label="${bsManageVaccine.name }" />
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
						<label class="col-sm-2 control-label">批次：</label>
					<div class="col-sm-2">
						<form:select path="batchno" class="form-control required"  >
						</form:select>
					</div>
				</div>
			
					<div class="form-group">
						<label class="col-sm-2 control-label">厂家：</label>
						<div class="col-sm-5">
							<form:select path="manufacturer" class="form-control required" >
								<%-- <c:forEach items=" " var="product">
									<form:option value="${product }" label="${product }" />
								</c:forEach> --%>
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">疫苗规格：</label>
						<div class="col-sm-5">
							<form:select path="specification" class="form-control required" >
								<form:options items="${fns:getDictList('specification')}" itemLabel="label"
									itemValue="value" htmlEscape="false" />
								</form:select>
						</div>
					</div>
			
				
				<div class="form-group">
					<c:if test="${manageStockIn.type=='0' }">
						<label class="col-sm-2 control-label ">盒数：<span
						class="help-inline"><font color="red">*</font> </span></label>
					</c:if>
					<c:if test="${manageStockIn.type=='1' }">
						<label class="col-sm-2 control-label">剂量：<span
						class="help-inline"><font color="red">*</font> </span></label>
						<span>当前库存&nbsp;</span>
						<span id="amount" style="color: red" ></span>
						
					</c:if>
					<div class="col-sm-2">
					
						<c:if test="${manageStockIn.type=='0' }">
								<form:input path="num" htmlEscape="false" maxlength="18"
								class="form-control required digits number required"  placeholder="请输入领用数量" />					
						</c:if>
						<c:if test="${manageStockIn.type=='1' }">
								<form:input path="num" htmlEscape="false" maxlength="18"
								class="form-control required digits number required"  placeholder="请输入报废数量" />					
						</c:if>
						
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">时间：</label>
					<div class="col-sm-2">
						<input name="indate" 
							value="<fmt:formatDate value="${manageStockIn.indate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
							class="laydate-icon form-control layer-date required">
					</div>
				</div>
				<c:if test="${manageStockIn.type=='1' }">
					<div class="form-group">
						<label class="col-sm-2 control-label">说明：</label>
						<div class="col-sm-2">
							<form:select path="state" class="form-control ">
								<form:options items="${fns:getDictList('state')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-2">
						<form:input path="mark" htmlEscape="false" maxlength="200"
							class="form-control " />
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">type：</label>
					<div class="col-sm-2">
						<form:input path="type" htmlEscape="false" maxlength="200"
							class="form-control " />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="managestockin:IN:edit">
							<input id="btnSubmit" class="btn btn-primary" type="submit"
								value="保 存" />&nbsp;</shiro:hasPermission>
						<!-- <input id="btnCancel" class="btn" type="button" value="返 回"
							onclick="history.go(-1)" /> -->
					</div>
				</div>
			</form:form>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#name").change();
		}
		);

</script>
</body>

</html>