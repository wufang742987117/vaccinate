<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>疫苗出库</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
// 					form.submit();
					//验证疫苗出库数量是否合法
					var num = $("#num").val();
					var storenum = $("#storenum").val();
					if(num>storenum){
						layer.msg("输入出库数量大于库存，请重新输入！");
						return;
					}
					$.ajax({
						url:ctx + "/manage_stock_in/manageStockIn/saveStockOut",
						data:{
							"type":$("#type").val(),
							"product.id":$("#productid").val(),
							"state":$("#state").val(),
							"num":$("#num").val(),
							"remarks":$("#remarks").val(),
							"roomCode":$("#roomCode").val()
							},
						success:function(data){
							if(data.code == '200'){
// 								success(data.msg);
								parent.success(data.msg);
								parent.afterStockOut();
								var index = parent.layer.getFrameIndex(window.name);
								parent.layer.close(index);
							}else{
								error(data.msg);
							}
						},
						error:function(a,b,c){
							error("请求失败");
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
		
		$(function(){
			//数据错误，自动关闭窗口
			if('${empty error? "" : error}'){
				parent.layer.error('${empty error? "" : error}');
				var index = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index);
			}
			
			$("#state").change(function(){
				if($(this).val() == '2'){
					$("#out2").show();
					$("#remarks").val("库存调剂");
				}else{
					$("#out2").hide();
				}
				if($(this).val() == '1'){
					$("#remarks").val("报损");
				}
				if($(this).val() == '3'){
					$("#remarks").val("");
				}
			});
		})
	</script>
</head>
<body>
	<div class="ibox">
		<div class="" >
			<form:form id="inputForm" modelAttribute="manageStockIn" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="type"/>
				<form:hidden path="product.id" id="productid"/>
				<sys:message content="${message}"/>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">疫苗名称:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" disabled="disabled" value="${manageStockIn.product.name }">
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">剂量:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" disabled="disabled" value="${manageStockIn.product.specification }">
					</div>
				</div>
				
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">厂商:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" disabled="disabled" value="${manageStockIn.product.manufacturer }">
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">价格:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" disabled="disabled" value="${manageStockIn.product.sellprice == 0? '免费':manageStockIn.product.sellprice}">
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">批号:</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" disabled="disabled" value="${manageStockIn.product.batchno }">
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">库存:</label>
					<div class="col-sm-8">
						<input id="storenum" type="text" class="form-control" disabled="disabled" value="${manageStockIn.product.storenum }">
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">出库类型:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-8">
						<form:select path="state" items="${fns:getDictList('stock_out_state')}" itemLabel="label" itemValue="value"  cssClass="form-control" />
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">出库数量:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-8">
						<form:input path="num"  cssClass="form-control number required" maxlength="10" />
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">说明:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-8">
						<form:textarea path="remarks" htmlEscape="false"  cssClass="form-control required" maxlength="50" />
					</div>
				</div>
				<div class="form-group col-sm-6" style="display: none;" id="out2">
					<label class="col-sm-4 control-label">调剂科室:<span class="help-inline"></span></label>
					<div class="col-sm-8">
						<select name="roomCode" id="roomCode" class="form-control">
							<c:forEach items="${officelist }" var="off">
								<option value='${off.code }'>${off.name }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-8 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>
						<input id="btnCancel" class="btn" type="button" value="取消" onclick="parent.layer.closeAll()"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>