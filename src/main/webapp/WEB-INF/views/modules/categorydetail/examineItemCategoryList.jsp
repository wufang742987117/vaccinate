<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>核查详情管理</title>
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
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="categoryDetailsEntity" action="${ctx}/categorydetail/examineItemCategory/list" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="categoryId" name="categoryId" type="hidden" value="${categoryid}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">检查项目名：</span>
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
						<th>编号</th>
						<th>核查项目</th>
						<th>核查内容</th>
						<th>核查要点</th>
						<th>核查方法及关注点</th>
						<th>符合判定</th>
						<th>轻微缺陷判定</th>
						<th>不符合判定</th>
						<th>记录要定及索证要求</th>
						<th>核查记录(参考示例)</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="examineItemCategory">
					<tr>
						<td>
							${examineItemCategory.id}
						</td>
						<td>
							${examineItemCategory.name}
						</td>
						<td>
							${examineItemCategory.content}
						</td>
						<td>
							${examineItemCategory.mainPoints}
						</td>
						<td>
							${examineItemCategory.methodNote}
						</td>
						<td>
							${examineItemCategory.good}
						</td>
						<td>
							${examineItemCategory.general}
						</td>
						<td>
							${examineItemCategory.poor}
						</td>
						<td>
							${examineItemCategory.recordMainPoints}
						</td>
						<td>
							${examineItemCategory.reference}
						</td>
						<td>
							${examineItemCategory.remark}
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