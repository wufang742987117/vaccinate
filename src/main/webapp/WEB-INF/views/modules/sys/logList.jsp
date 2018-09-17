<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
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
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/log/">日志列表</a></li> --%>
<!-- 	</ul> -->
		<div class="ibox">
			<div class="ibox-content">
			<form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">操作菜单：</span>
						<input id="title" name="title" type="text" maxlength="50" class="form-control" value="${log.title}"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">用户ID：</span>
						<input id="createBy.id" name="createBy.id" type="text" maxlength="50" class="form-control" value="${log.createBy.id}"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">URI：</span>
						<input id="requestUri" name="requestUri" type="text" maxlength="50" class="form-control" value="${log.requestUri}"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">日期范围：</span>
						<input  name="beginDate" id="beginDate" readonly="" value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" placeholder="开始日期" class="laydate-icon form-control layer-date">
						<span class="input-group-addon gray-bg text-right">-</span>
						<input id="endDate" name="endDate" readonly="" value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" placeholder="结束日期">
					</div>
				</div>
				<div class="form-group">
					         <span class="input-group-addon gray-bg text-right">
					<input id="exception" name="exception" class="i-checks" type="checkbox"${log.exception eq '1'?' checked':''} value="1"/>
					只查询异常信息：</span>
				</div>
				<div class="form-group">
					<button id="btnSubmit" class="btn btn-primary" type="submit">查询</button>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th>操作菜单</th><th>操作用户</th><th>所在公司</th><th>所在部门</th><th>URI</th><th>提交方式</th><th>操作者IP</th><th>操作时间</th></thead>
				<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
				<c:forEach items="${page.list}" var="log">
					<tr>
						<td>${log.title}</td>
						<td>${log.createBy.name}</td>
						<td>${log.createBy.company.name}</td>
						<td>${log.createBy.office.name}</td>
						<td><strong>${log.requestUri}</strong></td>
						<td>${log.method}</td>
						<td>${log.remoteAddr}</td>
						<td><fmt:formatDate value="${log.createDate}" type="both"/></td>
					</tr>
					<c:if test="${not empty log.exception}"><tr>
						<td colspan="8" style="word-wrap:break-word;word-break:break-all;">
		<%-- 					用户代理: ${log.userAgent}<br/> --%>
		<%-- 					提交参数: ${fns:escapeHtml(log.params)} <br/> --%>
							异常信息: <br/>
							${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}</td>
					</tr></c:if>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>