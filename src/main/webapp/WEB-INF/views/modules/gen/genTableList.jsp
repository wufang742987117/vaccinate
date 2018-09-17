<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/gen/genTable/">业务表列表</a></li>
		<shiro:hasPermission name="gen:genTable:edit"><li><a href="${ctx}/gen/genTable/form">业务表添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="genTable" action="${ctx}/gen/genTable/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

				<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>

				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">表名：</span>
						<form:input path="nameLike" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">说明：</span>
						<form:input path="comments" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">父表表名：</span>
						<form:input path="parentTable" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<button id="btnSubmit" class="btn btn-primary" type="submit">查询</button>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th class="sort-column name">表名</th><th>说明</th><th class="sort-column class_name">类名</th><th class="sort-column parent_table">父表</th><shiro:hasPermission name="gen:genTable:edit"><th>操作</th></shiro:hasPermission></tr></thead>
				<tbody>
				<c:forEach items="${page.list}" var="genTable">
					<tr>
						<td><a href="${ctx}/gen/genTable/form?id=${genTable.id}">${genTable.name}</a></td>
						<td>${genTable.comments}</td>
						<td>${genTable.className}</td>
						<td title="点击查询子表"><a href="javascript:" onclick="$('#parentTable').val('${genTable.parentTable}');$('#searchForm').submit();">${genTable.parentTable}</a></td>
						<shiro:hasPermission name="gen:genTable:edit"><td>
							<a href="${ctx}/gen/genTable/form?id=${genTable.id}">修改</a>
							<a href="${ctx}/gen/genTable/delete?id=${genTable.id}" onclick="return confirmx('确认要删除该业务表吗？', this.href)">删除</a>
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
