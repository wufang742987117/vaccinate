<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>生成方案管理</title>
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
		<li class="active"><a href="${ctx}/gen/genScheme/">生成方案列表</a></li>
		<shiro:hasPermission name="gen:genScheme:edit"><li><a href="${ctx}/gen/genScheme/form">生成方案添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="genScheme" action="${ctx}/gen/genScheme/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">方案名称：</span>
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<button id="btnSubmit" class="btn btn-primary" type="submit">查询</button>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th>方案名称</th><th>生成模块</th><th>模块名</th><th>功能名</th><th>功能作者</th><shiro:hasPermission name="gen:genScheme:edit"><th>操作</th></shiro:hasPermission></tr></thead>
				<tbody>
				<c:forEach items="${page.list}" var="genScheme">
					<tr>
						<td><a href="${ctx}/gen/genScheme/form?id=${genScheme.id}">${genScheme.name}</a></td>
						<td>${genScheme.packageName}</td>
						<td>${genScheme.moduleName}${not empty genScheme.subModuleName?'.':''}${genScheme.subModuleName}</td>
						<td>${genScheme.functionName}</td>
						<td>${genScheme.functionAuthor}</td>
						<shiro:hasPermission name="gen:genScheme:edit"><td>
							<a href="${ctx}/gen/genScheme/form?id=${genScheme.id}">修改</a>
							<a href="${ctx}/gen/genScheme/delete?id=${genScheme.id}" onclick="return confirmx('确认要删除该生成方案吗？', this.href)">删除</a>
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
