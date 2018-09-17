<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品类别管理</title>
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
		<li class="active"><a href="${ctx}/procategory/productType/">产品类别列表</a></li>
		<shiro:hasPermission name="procategory:productType:edit"><li><a href="${ctx}/procategory/productType/form">产品类别添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="productType" action="${ctx}/procategory/productType/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">类别名称：</span>
									<form:input path="name" htmlEscape="false" maxlength="30" class="form-control"/>
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
						<th>类别名称</th>
						<th>更新时间</th>
						<shiro:hasPermission name="procategory:productType:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="productType">
					<tr>
						<td><a href="${ctx}/procategory/productType/form?id=${productType.id}">
							${productType.name}
						</a></td>
						<td>
							<fmt:formatDate value="${productType.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<shiro:hasPermission name="procategory:productType:edit"><td>
							<a href="${ctx}/procategory/productType/form?id=${productType.id}">修改</a>
							<a href="${ctx}/procategory/productType/delete?id=${productType.id}" onclick="return confirmx('确认要删除该产品类别吗？', this.href)">删除</a>
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