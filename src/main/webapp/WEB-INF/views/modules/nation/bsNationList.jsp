<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
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
		<li class="active"><a href="${ctx}/nation/bsNation/">信息列表</a></li>
		<shiro:hasPermission name="nation:bsNation:edit"><li><a href="${ctx}/nation/bsNation/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsNation" action="${ctx}/nation/bsNation/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">code：</span>
									<form:input path="code" htmlEscape="false" maxlength="50" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">民族名称：</span>
									<form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">排序：</span>
									<form:input path="sort" htmlEscape="false" maxlength="50" class="form-control"/>
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
						<th>code</th>
						<th>民族名称</th>
						<th>排序</th>
						<shiro:hasPermission name="nation:bsNation:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsNation">
					<tr>
						<td><a href="${ctx}/nation/bsNation/form?id=${bsNation.id}">
							${bsNation.code}
						</a></td>
						<td>
							${bsNation.name}
						</td>
						<td>
							${bsNation.sort}
						</td>
						<shiro:hasPermission name="nation:bsNation:edit"><td>
							<a href="${ctx}/nation/bsNation/form?id=${bsNation.id}">修改</a>
							<a href="${ctx}/nation/bsNation/delete?id=${bsNation.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
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