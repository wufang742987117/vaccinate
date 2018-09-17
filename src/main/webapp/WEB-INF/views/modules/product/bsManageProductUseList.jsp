<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>疫苗使用详情</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
<script src="${ctxStatic}/js/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
<script type="text/javascript">
$(document).ready(
	function() {
		$("#searchForm").validate(
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

	$(document).ready( function() {
/* 		$("#vaccName").change(function() {
			var name = $("#vaccName").val();
			$.ajax({ 
			type : "POST",
					url : "${ctx}/manage_stock_in/manageStockIn/batchno2",
					data : {name : name},
					dataType : "json",
					success : function(data) {
						var html = "<option value=''></option>";
						$.each(data,function(idx,item) { //循环对象取值
							html += "<option value='" + item.batchno + "'>"+ item.batchno+ "</option>";
						});
						$("#batch").html(html);
						if(data==null||name!="${childVaccinaterecord.vaccName}"){
							$("#batch").val();
						}else{
							$("#batch").val('${childVaccinaterecord.batch}');
						}
					}
			});
	}); */
		 	
			var nameValue = '${fns:toJson(productUseList.nameIn)}';
			if(nameValue && nameValue != "\"\""){
				$("#name").selectpicker('val',JSON.parse(nameValue));
			}
	});

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	$(document).ready(function() {
		$("#btnExport").click(function(){
			layer.confirm("确认要导出数据吗？", {
				btn: ['确认','取消'], //按钮
				shade: true, //不显示遮罩
				icon : 0
			}, function(index){
			var temp = $("#searchForm").attr("action");
				$("#searchForm").attr("action","${ctx}/child_vaccinaterecord/inoculationOfStatistics/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action",temp);
				layer.close(index);
			}, function()
			{
				layer.close();
			});
		});
});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/child_baseinfo/documentStatistics">个案统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement">应种统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/inoculationOfStatistics">接种统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/inoculationOfStatistics/logList">接种日志</a></li>
		<li><a href="${ctx}/child_baseinfo/overduevacc">逾期未种</a></li>
<%-- 		<li><a href="${ctx}/product/bsManageProduct/repertorySum">库存统计</a></li> --%>
		<li class="active"><a href="${ctx}/product/bsManageProduct/productUseList">疫苗使用详情</a></li>
		<li><a href="${ctx}/invoicing/bsInvoicing/">进销存统计</a></li>
		<div class=" row pull-right">
				<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
		</div>
		
	</ul>
	<div>
         <form:form id="searchForm" modelAttribute="productUseList" action="${ctx}/product/bsManageProduct/productUseList"  method="post" class="form-inline mt20">
					 <div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称:</span>
						 <select title="请选择疫苗名称" id="name" name="nameIn" class="selectpicker show-tick form-control w164"  multiple data-live-search="false">
							<option value="">--请选择--</option>
							<c:forEach items="${bsManageVaccineList}" var="pro">
                               <option value="${pro.name}" class="properties" >${pro.name}</option>
                            </c:forEach>
						</select>
					</div>
						<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗批次:</span>
						<form:input path="batchno" class="form-control"/>
							<%-- <form:option value="" label="" />
							<c:forEach items="${batchList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.batchno }"
									label="${bsManageVaccine.batchno }" />
							</c:forEach> --%>
					</div>
						<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗厂商:</span>
						<form:select path="manufacturer" class="form-control">
							<form:option value="" label="" />
							<c:forEach items="${EnterpriseInfoList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.name }"
									label="${bsManageVaccine.name }" />
							</c:forEach>
						</form:select>
					</div>
					<div class="input-group" >
						<div class="input-group" >
							<span class="input-group-addon gray-bg text-right">出库时间(起)：</span>
							<input name="beginCreateDate"  value="<fmt:formatDate value="${productUseList.beginCreateDate}" pattern="yyyy-MM-dd"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date" />
						</div>
					</div>
					<div class="input-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">出库时间(止)：</span>
							<input name="endCreateDate" 
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${productUseList.endCreateDate}" pattern="yyyy-MM-dd"/>"   />
						</div>
					</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>&emsp;&emsp;&emsp;
					<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
		</div>
	<div class="ibox">
			<sys:message content="${message}" />
			<table id="contentTable"
				class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗批次</th>
						<th>疫苗编号</th>
						<th>疫苗名称</th>
						<th>疫苗厂家</th>
						<th>厂商编号</th>
						<th>库存剩余</th>
						<th>已使用</th>
						<th>入库时间</th>
						<%-- <shiro:hasPermission
							name="child:cord:edit">
							<th>操作</th>
						</shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="productUseList">
						<tr>
							<td>${productUseList.batchno}</td>
							<td>${productUseList.id}</td>
							<td>${productUseList.name}</td>
							<td>${productUseList.manufacturer}</td>
							<td>${productUseList.manufacturerCode}</td>
							<td>${productUseList.storesum}</td>
							<td><a href="${ctx}/product/bsManageProduct/childVaccinatereCord?batchno=${productUseList.batchno}&id=${productUseList.id}
							&manufacturerCode=${productUseList.manufacturerCode}&beginCreateDate=<fmt:formatDate value="${beginCreateDate}" pattern="yyyy-MM-dd"/>
							&endCreateDate=<fmt:formatDate value="${endCreateDate}" pattern="yyyy-MM-dd"/>" 
							target="_Blank">${productUseList.usesum}</a></td>
							<td><fmt:formatDate value="${productUseList.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<script type="text/javascript">
		$(function(){
		$("#vaccineid").change(); 
		})
		$(function(){
		$("#batchno").change(); 
		})
		</script>
	</div>
</body>
</html>