<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>库存统计</title>
<meta name="decorator" content="default" />
</style>
<script type="text/javascript">
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
						$("#searchForm").attr("action","${ctx}/product/bsManageProduct/export");
						$("#searchForm").submit();
						layer.close(index);
						$("#searchForm").attr("action",temp);
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
<%-- 		<li class="active"> <a href="${ctx}/product/bsManageProduct/repertorySum">库存统计</a></li> --%>
		<li><a href="${ctx}/product/bsManageProduct/productUseList">疫苗使用详情</a></li>
		<div class="col-xs-1 pull-right">
				<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
		</div>
	</ul> 
	
	<div class="ibox">
		<div class=" col-sm-12" >
			<form:form id="searchForm" modelAttribute="bsManageProduct"
				action="${ctx}/product/bsManageProduct/repertorySum" method="post"
				class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden"
					value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden"
					value="${page.pageSize}" />
					
			<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗大类：</span>
						<form:select path="bigcode" class="form-control " >
							<form:option value="" label="" />
							<c:forEach items="${bsmanagevaccineList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.gNum }"
									label="${bsManageVaccine.gName }" />
							</c:forEach>
						</form:select>
					</div>
				</div>
		
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗小类名称：</span>
						<form:select path="vaccineid" class="form-control " >
							<form:option value="" label="" />
							<c:forEach items="${bsManageVaccineList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.id }"
									label="${bsManageVaccine.name }" />
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗批次：</span>
						<form:select path="batchno" class="form-control " >
							<form:option value="" label="" />
							<c:forEach items="${batchList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.batchno }"
									label="${bsManageVaccine.batchno }" />
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary mr15" type="submit" value="查询" />
					<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/>
				</div>
				
			</form:form>
		</div>
		<div class=" col-sm-6">
			<sys:message content="${message}" />
			<table id="contentTable"
				class="table  table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>疫苗批次</th>
						<th>库存</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${bsmanageproductlist }" var="bsmanageproduct">
					<tr>
					<td>${bsmanageproduct.name }</td>		
					<td>${bsmanageproduct.batchno }</td>		
					<td>${bsmanageproduct.storenum }</td>		
					</tr>
					</c:forEach>
					
						<tr>
							
						</tr>
					
				</tbody>
			</table>
			<div class="page">${page}</div> 
		</div>
	</div>
	
</body>
</html>