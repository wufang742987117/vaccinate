<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预约登记管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		<li class="active"><a href="${ctx}/reserve/bsReserve/">预约登记列表</a></li>
		<shiro:hasPermission name="reserve:bsReserve:edit"><li><a href="${ctx}/reserve/bsReserve/form">预约登记添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsReserve" action="${ctx}/reserve/bsReserve/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">儿童id：</span>
									<form:input path="childId" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>update_date</th>
						<th>remarks</th>
						<shiro:hasPermission name="reserve:bsReserve:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsReserve">
					<tr>
						<td><a href="${ctx}/reserve/bsReserve/form?id=${bsReserve.id}">
							<fmt:formatDate value="${bsReserve.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</a></td>
						<td>
							${bsReserve.remarks}
						</td>
						<shiro:hasPermission name="reserve:bsReserve:edit"><td>
							<a href="${ctx}/reserve/bsReserve/form?id=${bsReserve.id}">修改</a>
							<a href="${ctx}/reserve/bsReserve/delete?id=${bsReserve.id}" onclick="return confirmx('确认要删除该预约登记吗？', this.href)">删除</a>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>