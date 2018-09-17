<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单表管理</title>
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
		<li class="active"><a href="${ctx}/test/testData/">单表列表</a></li>
		<shiro:hasPermission name="test:testData:edit"><li><a href="${ctx}/test/testData/form">单表添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="testData" action="${ctx}/test/testData/" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon gray-bg text-right">归属用户：</span>
					<sys:treeselect id="user" name="user.id" value="${testData.user.id}" labelName="user.name" labelValue="${testData.user.name}"
									title="用户" url="/sys/office/treeData?type=3" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon gray-bg text-right">归属部门：</span>
					<sys:treeselect id="office" name="office.id" value="${testData.office.id}" labelName="office.name" labelValue="${testData.office.name}"
									title="部门" url="/sys/office/treeData?type=2" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon gray-bg text-right">归属区域：</span>
					<sys:treeselect id="area" name="area.id" value="${testData.area.id}" labelName="area.name" labelValue="${testData.area.name}"
									title="区域" url="/sys/area/treeData" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
				</div>
			</div>

			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon gray-bg text-right">名称：</span>
					<div class="input-group ">
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control"/>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<label class="control-label">性别：</label>
					<form:radiobuttons path="sex" items="${fns:getDictList('sex')}"  element="label class='checkbox-inline i-checks'" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon gray-bg text-right">加入日期：</span>
					<input  name="beginInDate" id="beginInDate"  onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" placeholder="开始日期" class="laydate-icon form-control layer-date">
					<span class="input-group-addon gray-bg text-right">-</span>
					<input id="endInDate" name="endInDate"  onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" placeholder="结束日期">
				</div>
			</div>
			<div class="form-group">
				<button id="btnSubmit" class="btn btn-primary" type="submit">查询</button>
			</div>
		</form:form>
		<sys:message content="${message}"/>
		<div class="hr-line-dashed"></div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
			<tr>
				<th>归属用户</th>
				<th>归属部门</th>
				<th>归属区域</th>
				<th>名称</th>
				<th>性别</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="test:testData:edit"><th>操作</th></shiro:hasPermission>
			</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="testData">
				<tr>
					<td><a href="${ctx}/test/testData/form?id=${testData.id}">
							${testData.user.name}
					</a></td>
					<td>
							${testData.office.name}
					</td>
					<td>
							${testData.area.name}
					</td>
					<td>
							${testData.name}
					</td>
					<td>
							${fns:getDictLabel(testData.sex, 'sex', '')}
					</td>
					<td>
						<fmt:formatDate value="${testData.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td>
							${testData.remarks}
					</td>
					<shiro:hasPermission name="test:testData:edit"><td>
						<a href="${ctx}/test/testData/form?id=${testData.id}">修改</a>
						<a href="${ctx}/test/testData/delete?id=${testData.id}" onclick="return confirmx('确认要删除该单表吗？', this.href)">删除</a>
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