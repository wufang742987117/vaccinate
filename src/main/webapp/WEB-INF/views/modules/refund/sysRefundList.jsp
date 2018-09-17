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
		<li class="active"><a href="${ctx}/refund/sysRefund/">信息列表</a></li>
		<shiro:hasPermission name="refund:sysRefund:edit"><li><a href="${ctx}/refund/sysRefund/form">信息添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="sysRefund" action="${ctx}/refund/sysRefund/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">儿童编号：</span>
									<form:input path="childcode" htmlEscape="false" maxlength="20" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">儿童姓名：</span>
									<form:input path="childname" htmlEscape="false" maxlength="20" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">计划表ID：</span>
									<form:input path="nid" htmlEscape="false" maxlength="10" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">用户ID：</span>
									<form:input path="uid" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">pid：</span>
									<form:input path="pid" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">创建时间：</span>
									<form:input path="createdate" readonly="" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
									class="laydate-icon form-control layer-date" value=""/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">退款来源0登记台1微信2一体机：</span>
									<form:input path="refundsource" htmlEscape="false" maxlength="2" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">退款金额：</span>
									<form:input path="refundmoney" htmlEscape="false" maxlength="20" class="form-control"/>
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
						<th>儿童编号</th>
						<th>儿童姓名</th>
						<th>计划表ID</th>
						<th>用户ID</th>
						<th>pid</th>
						<th>创建时间</th>
						<th>退款来源0登记台1微信2一体机</th>
						<th>退款金额</th>
						<shiro:hasPermission name="refund:sysRefund:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sysRefund">
					<tr>
						<td><a href="${ctx}/refund/sysRefund/form?id=${sysRefund.id}">
							${sysRefund.childcode}
						</a></td>
						<td>
							${sysRefund.childname}
						</td>
						<td>
							${sysRefund.nid}
						</td>
						<td>
							${sysRefund.uid}
						</td>
						<td>
							${sysRefund.pid}
						</td>
						<td>
							<fmt:formatDate value="${sysRefund.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${sysRefund.refundsource}
						</td>
						<td>
							${sysRefund.refundmoney}
						</td>
						<shiro:hasPermission name="refund:sysRefund:edit"><td>
							<a href="${ctx}/refund/sysRefund/form?id=${sysRefund.id}">修改</a>
							<a href="${ctx}/refund/sysRefund/delete?id=${sysRefund.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
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