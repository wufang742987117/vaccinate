<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>告知书管理</title>
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
		<li class="active"><a href="${ctx}/cms/cmsDisclosure/">告知书列表</a></li>
		<%-- <li><a href="${ctx}/cms/cmsDisclosure/form">告知书添加</a></li> --%>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="cmsDisclosure" action="${ctx}/cms/cmsDisclosure/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				
				<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">告知书编号：</span>
								<form:input path="id" class="form-control"/>
						</div>
				</div>
				
				<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">告知书内容：</span>
								<form:input path="context" class="form-control"/>
						</div>
				</div>
				
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
					<a class="btn btn-primary w100" href="javascript:void(0);"  onclick="alertForm('${ctx}/cms/cmsDisclosure/form','添加');"><span class="glyphicon glyphicon-plus"></span>添加</a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th style="width: 1%; display: none;">id</th>
						<th style="width: 5%;">疫苗外键id</th>
						<th style="width: 89%;">context</th>
						<th style="width: 5%;">操作</th>
						<shiro:hasPermission name="cms:cmsDisclosure:edit"></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="cmsDisclosure">
					<tr>
						<td style="display: none;"><a href="${ctx}/cms/cmsDisclosure/form?id=${cmsDisclosure.id}">
							${cmsDisclosure.id}
						</a></td>
						<td>
							<%-- ${cmsDisclosure.vid} --%>
							${cmsDisclosure.vaccine.gName}
						</td>
						<td>
							${cmsDisclosure.context}
						</td>
						<td style="text-align: center;">
							<a class="btn btn-xs btn-danger" href="javascript:void(0);" onclick="alertForm('${ctx}/cms/cmsDisclosure/form?id=${cmsDisclosure.id}','修改')">修改</a>
							<a class="btn btn-xs btn-danger" href="${ctx}/cms/cmsDisclosure/delete?id=${cmsDisclosure.id}" onclick="return confirmx('确认要删除该告知书吗？', this.href)">删除</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>