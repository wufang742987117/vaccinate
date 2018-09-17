<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>儿童接种提醒管理</title>
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
		<li class="active"><a href="${ctx}/remind/vacChildRemind/">儿童接种提醒列表</a></li>
		<shiro:hasPermission name="remind:vacChildRemind:edit"><li><a href="${ctx}/remind/vacChildRemind/form">儿童接种提醒添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="vacChildRemind" action="${ctx}/remind/vacChildRemind/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">儿童编号：</span>
									<form:input path="childcode" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">提醒日期：</span>
									<form:input path="beginRemindDate" readonly="" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
									class="laydate-icon form-control layer-date" value=""/>
									<span class="input-group-addon gray-bg">-</span>
									<form:input path="endRemindDate" readonly="" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
									class="laydate-icon form-control layer-date" value=""/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">疫苗编号：</span>
									<form:select path="vaccId" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">批号：</span>
									<form:input path="batch" htmlEscape="false" maxlength="20" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">自选时段-日期：</span>
									<form:input path="selectDate" readonly="" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
									class="laydate-icon form-control layer-date" value=""/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">状态 0未完成  1已完成：</span>
									<form:select path="status" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('remind_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
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
						<th>id</th>
						<th>儿童编号</th>
						<th>提醒日期</th>
						<th>提醒的内容</th>
						<th>疫苗编号</th>
						<th>厂商编号</th>
						<th>批号</th>
						<th>剂量</th>
						<th>自选时段-日期</th>
						<th>自选时段-时间段</th>
						<th>状态 0未完成  1已完成</th>
						<th>保险id，没有保险null</th>
						<th>创建者</th>
						<th>更新时间</th>
						<shiro:hasPermission name="remind:vacChildRemind:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="vacChildRemind">
					<tr>
						<td><a href="${ctx}/remind/vacChildRemind/form?id=${vacChildRemind.id}">
							${vacChildRemind.id}
						</a></td>
						<td>
							${vacChildRemind.childcode}
						</td>
						<td>
							<fmt:formatDate value="${vacChildRemind.remindDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${vacChildRemind.remindVacc}
						</td>
						<td>
							${fns:getDictLabel(vacChildRemind.vaccId, '', '')}
						</td>
						<td>
							${vacChildRemind.com}
						</td>
						<td>
							${vacChildRemind.batch}
						</td>
						<td>
							${vacChildRemind.spec}
						</td>
						<td>
							<fmt:formatDate value="${vacChildRemind.selectDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${vacChildRemind.selectTime}
						</td>
						<td>
							${fns:getDictLabel(vacChildRemind.status, 'remind_status', '')}
						</td>
						<td>
							${vacChildRemind.insuranceId}
						</td>
						<td>
							${vacChildRemind.createBy.id}
						</td>
						<td>
							<fmt:formatDate value="${vacChildRemind.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<shiro:hasPermission name="remind:vacChildRemind:edit"><td>
							<a href="${ctx}/remind/vacChildRemind/form?id=${vacChildRemind.id}">修改</a>
							<a href="${ctx}/remind/vacChildRemind/delete?id=${vacChildRemind.id}" onclick="return confirmx('确认要删除该儿童接种提醒吗？', this.href)">删除</a>
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