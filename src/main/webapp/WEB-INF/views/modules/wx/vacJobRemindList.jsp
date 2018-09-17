<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>微信定时提醒管理</title>
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
		<li class="active"><a href="${ctx}/wx/vacJobRemind/">微信定时提醒列表</a></li>
		<shiro:hasPermission name="wx:vacJobRemind:edit"><li><a href="${ctx}/wx/vacJobRemind/form">微信定时提醒添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="vacJobRemind" action="${ctx}/wx/vacJobRemind/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<shiro:hasPermission name="wx:vacJobRemind:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="vacJobRemind">
					<tr>
						<shiro:hasPermission name="wx:vacJobRemind:edit"><td>
							<a href="${ctx}/wx/vacJobRemind/form?id=${vacJobRemind.id}">修改</a>
							<a href="${ctx}/wx/vacJobRemind/delete?id=${vacJobRemind.id}" onclick="return confirmx('确认要删除该微信定时提醒吗？', this.href)">删除</a>
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