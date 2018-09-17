<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>进销存统计管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
	<script src="${ctxStatic}/js/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			/* var nameValue = '${fns:toJson(bsInvoicing.vaccineidIn)}';
			if(nameValue && nameValue != "\"\""){
				$("#name").selectpicker('val',JSON.parse(nameValue));
			} */
			
			$("#btnExport").click(function(){
				layer.confirm("确认要导出数据吗？", {
					btn: ['确认','取消'], //按钮
					shade: true, //不显示遮罩
					icon : 0
				}, function(index){
					var temp = $("#searchForm").attr("action");
					$("#searchForm").attr("action","${ctx}/invoicing/bsInvoicing/export");
					$("#searchForm").submit();
					layer.close(index);
					$("#searchForm").attr("action",temp);
				}, function(){
					layer.close();
				});
			});
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
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
		<li><a href="${ctx}/product/bsManageProduct/productUseList">疫苗使用详情</a></li>
		<li class="active"><a href="${ctx}/invoicing/bsInvoicing/">进销存统计</a></li>
		<%-- <shiro:hasPermission name="invoicing:bsInvoicing:edit"><li><a href="${ctx}/invoicing/bsInvoicing/form">进销存统计添加</a></li></shiro:hasPermission> --%>
		<div class=" row pull-right">
				<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
		</div>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsInvoicing" action="${ctx}/invoicing/bsInvoicing/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group" >
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">起始日期：</span>
						<input name="beginTime" value="<fmt:formatDate value="${bsInvoicing.beginTime}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date beginTime" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">终止日期：</span>
						<input name="endTime" value="<fmt:formatDate value="${bsInvoicing.endTime}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date endTime" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg">疫苗名称：</span>
						<form:select path="vaccineid" class="form-control">
							<form:option value="" label="--请选择--"/>
							<form:options items="${products }" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
						<%-- <select title="请选择疫苗名称" id="name" name="vaccineidIn" class="selectpicker show-tick form-control w164"  multiple data-live-search="false">
							<option value="">--请选择--</option>
							<c:forEach items="${products}" var="pro">
                               <option value="${pro.id}" class="properties" >${pro.name}</option>
                            </c:forEach>
						</select> --%>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg">批次编号：</span>
						<form:input path="batchno" htmlEscape="false" maxlength="20" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary btn-sm" type="submit" value="查询"/>
					<input id="btnExport" class="btn btn-primary btn-sm ml15" type="button" value="导出报表"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>编号</th>
						<!-- <th>疫苗编号</th> -->
						<th>疫苗名称</th>
						<th>批次编号</th>
						<th>疫苗规格</th>
						<th>生产厂商</th>
						<th>有效期至</th>
						<th>成本价</th>
						<th>出售价</th>
						<th>期初库存</th>
						<th>领取</th>
						<th>损耗</th>
						<th>售出</th>
						<th>期末库存</th>
						<!-- <th>历史时间</th> -->
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsInvoicing" varStatus="status">
					<tr>
						<td>
							${status.index + 1}
						</td>
						<%-- <td>
							<a href="${ctx}/invoicing/bsInvoicing/form?id=${bsInvoicing.id}">
								${bsInvoicing.vaccineid}
							</a>
						</td> --%>
						<td>
							${bsInvoicing.vaccName}
						</td>
						<td>
							${bsInvoicing.batchno}
						</td>
						<td>
							${bsInvoicing.specification}
						</td>
						<td>
							${bsInvoicing.manufacturer}
						</td>
						<td>
							<fmt:formatDate value="${bsInvoicing.vaccExpDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
							${bsInvoicing.costpriceNew}
						</td>
						<td>
							${bsInvoicing.sellpriceNew}
						</td>
						<td>
							${bsInvoicing.oldStorenum}
						</td>
						<td>
							${bsInvoicing.receive}
						</td>
						<td>
							${bsInvoicing.consume}
						</td>
						<td>
							${bsInvoicing.apply}
						</td>
						<td>
							${bsInvoicing.newStorenum}
						</td>
						<%-- <td>
							<fmt:formatDate value="${bsInvoicing.histroydate}" pattern="yyyy-MM-dd"/>
						</td> --%>
						<%-- <shiro:hasPermission name="invoicing:bsInvoicing:edit"><td>
							<a href="${ctx}/invoicing/bsInvoicing/form?id=${bsInvoicing.id}">修改</a>
							<a href="${ctx}/invoicing/bsInvoicing/delete?id=${bsInvoicing.id}" onclick="return confirmx('确认要删除该进销存统计吗？', this.href)">删除</a>
						</td></shiro:hasPermission> --%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>