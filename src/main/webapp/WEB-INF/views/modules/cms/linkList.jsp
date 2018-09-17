<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>链接管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
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
		<li class="active"><a href="${ctx}/cms/link/?category.id=${link.category.id}">链接列表</a></li>
		<shiro:hasPermission name="cms:link:edit"><li><a href="<c:url value='${fns:getAdminPath()}/cms/link/form?id=${link.id}&category.id=${link.category.id}'><c:param name='category.name' value='${link.category.name}'/></c:url>">链接添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="link" action="${ctx}/cms/link/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">栏目：</span>
						<sys:treeselect id="category" name="category.id" value="${link.category.id}" labelName="category.name" labelValue="${link.category.name}"
							title="栏目" url="/cms/category/treeData" module="link" notAllowSelectRoot="false" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">名称：</span>
						<form:input path="title" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<label class="control-label">状态：</label>
						<form:radiobuttons element="label class='checkbox-inline i-checks'" onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th>栏目</th><th>名称</th><th>权重</th><th>发布者</th><th>更新时间</th><shiro:hasPermission name="cms:link:edit"><th>操作</th></shiro:hasPermission></tr></thead>
				<tbody>
				<c:forEach items="${page.list}" var="link">
					<tr>
						<td><a href="javascript:" onclick="$('#categoryId').val('${link.category.id}');$('#categoryName').val('${link.category.name}');$('#searchForm').submit();return false;">${link.category.name}</a></td>
						<td><a href="${ctx}/cms/link/form?id=${link.id}" title="${link.title}">${fns:abbr(link.title,40)}</a></td>
						<td>${link.weight}</td>
						<td>${link.user.name}</td>
						<td><fmt:formatDate value="${link.updateDate}" type="both"/></td>
						<shiro:hasPermission name="cms:link:edit"><td>
							<a href="${ctx}/cms/link/form?id=${link.id}">修改</a>
							<a href="${ctx}/cms/link/delete?id=${link.id}${link.delFlag ne 0?'&isRe=true':''}&categoryId=${link.category.id}" onclick="return confirmx('确认要${link.delFlag ne 0?'发布':'删除'}该链接吗？', this.href)" >${link.delFlag ne 0?'发布':'删除'}</a>
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