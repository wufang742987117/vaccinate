<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<html>
<head>
<title>再次排号 </title>
<meta name="decorator" content="default" />

<c:if test="${not empty ts }">
	<script type="text/javascript">
		var receiptType = '${receiptType}';
		$(function() {
			if('${code}' && receiptType){
				parent.printtt(JSON.parse('${code}'));
			}
			layer.msg('${ts}');
			setTimeout(funB, 1500); 
		});
		function funB(){
			parent.closeForm1("${childcode}");
		}
		
	</script>
</c:if>
<script type="text/javascript">
	$(document).ready(
			function() {
			
					if(${childVaccinaterecord.price }==0){
					$("#labelid").html("免费");
					}else{
					$("#labelid").html(${childVaccinaterecord.price }+"元");
					}
			
			
			
				$("#inputForm").validate(
					{submitHandler : function(form) {
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
			/* $("#vaccName").change(
				//通过疫苗名称询所有的批次
				function batchno() {
					var name = $("#vaccName").val();
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
						$("#batch").html(html);
						$("#batch").change(); 
						}
					});
				}
			
			);
			
			$(function(){
			$("#batch").change(
				//通过疫苗名称和批次查询所有的厂家
				function manufacturer() {
					var name = $("#vaccName").val();
					var batchno = $("#batch").val();
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
								html += "<option value='" + item + "'>"+ item + "</option>";
							});
							$("#manufacturer").html(html);
							$("#manufacturer").change();
						}
					});
				
				}
			);
	
		});
			$(function(){
			$("#manufacturer").change(
				//通过疫苗名称和批次和厂家查询疫苗的价格
				function manufacturer() {
					var name = $("#vaccName").val();
					var batchno = $("#batch").val();
					var manufacturer = $("#manufacturer").val();
					$.ajax({
						type : "POST",
						url : "${ctx}/manage_stock_in/manageStockIn/price",
						data : {
						name :  name,
						batchno : batchno,
						manufacturer:manufacturer
						},
						dataType : "json",
						success : function(data) {
							$("#price").val(data);
							if(data==0){
							$("#labelid").html("免费");
							}else{
							$("#labelid").html(data+"元");
							}
						}
					});
				
				}
			);
	
		});*/
		
	}); 
</script>
</head>
<body>

	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="childVaccinaterecord"
				action="${ctx}/child_vaccinaterecord/childVaccinaterecord1/stampAgain"
				method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：</label>
					<div class="col-sm-5">
							<%-- <form:select path="vaccName" class="form-control " >
							<form:options items="${productlist}"
								itemLabel="name" itemValue="id" htmlEscape="false" />
						</form:select> --%>
						<form:input path="vaccName" htmlEscape="false" readonly="true"
							maxlength="50" class="form-control " />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">针次：</label>
					<div class="col-sm-5">
						<form:input path="dosage" htmlEscape="false" readonly="true"
							maxlength="50" class="form-control " />
					</div>
				</div>
				
			 <div class="form-group">
					<label class="col-sm-2 control-label">疫苗批号：</label>
					<div class="col-sm-5">
						<form:input path="batch" htmlEscape="false" readonly="true"
							maxlength="50" class="form-control " />
					</div>
				</div>  
				 <div class="form-group" hidden="hidden">
					<form:input path="nid" htmlEscape="false" readonly="true"
						maxlength="50" class="form-control " />
					<form:input path="childid" htmlEscape="false" readonly="true"
						maxlength="50" class="form-control " />
					<form:input path="productid" htmlEscape="false" readonly="true"
						maxlength="50" class="form-control " />
						
					</div>  
			 
				 <div class="form-group">
					<label class="col-sm-2 control-label">疫苗厂家：</label>
					<div class="col-sm-5">
						<form:input path="manufacturer" htmlEscape="false" readonly="true"
							maxlength="50" class="form-control " />
					</div>
				</div>  
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗价格：</label>
					<div class="col-sm-5">
						<label id="labelid"  style="font-size: 120%"> </label>
						<%-- <input value="${childVaccinaterecord.price }">  --%>
					</div>
				</div>
				<%-- <div class="form-group">
					<label class="col-sm-2 control-label">保险：</label>
					<div class="col-sm-5">
						<form:radiobuttons path="insurance" items="${fns:getDictList('yes_no')}"
							itemLabel="label" itemValue="value" htmlEscape="false" disabled="true"
							element="label class='checkbox-inline i-checks'" class="required" />

					</div>
				</div> --%>
				<div class="form-group" hidden="hidden"   >
				<input name="source" id="source" value="0"> 
				</div>
				<div class="form-group">
					<div class="col-sm-5 col-sm-offset-2">
					<shiro:hasPermission name="child:cord:edit">
					<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="再次排号" />
					</shiro:hasPermission>
											
					</div>
				</div>
			</form:form>
			<div class="form-group" style="height: 40px;">
			<table  style="color: red;border: none;" class="col-sm-5 col-sm-offset-2">
			<tr><td style="width: 190px;;font-size: 20px">下次接种疫苗时间：</td><td style="font-size: 20px; text-align: left">${preMonth} </td></tr>
			<!-- <tr><td style="width: 32%;font-size: 20px"	>下次打的疫苗名称为：</td><td></td></tr> -->
			</table>
 			</div>
	</div>
	<!-- <script type="text/javascript">
	
	$(function(){
	
	$("#vaccName").change();
	
	});
	
	</script> -->
	
</body>
</html>