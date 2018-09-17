<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存盘点记录管理</title>
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
		<li class="active"><a href="${ctx}/product/bsCheckRecord/">库存盘点记录列表</a></li>
		<shiro:hasPermission name="product:bsCheckRecord:edit"><li><a href="${ctx}/product/bsCheckRecord/form">库存盘点记录添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsCheckRecord" action="${ctx}/product/bsCheckRecord/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				    <div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称:</span>
						<form:select path="name" class="form-control">
						           <option value="">--请选择--</option>
							<c:forEach items="${bsManageVaccineList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.name }"
									label="${bsManageVaccine.name }" />
							</c:forEach>
						</form:select>
					</div>
				</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">盘点编号：</span>
									<form:input path="code" id="code" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">操作人：</span>
									<form:input path="createBy.id" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
						<div class="form-group" >
						<div class="input-group" >
							<span class="input-group-addon gray-bg text-right">盘点起始时间：</span>
							<input name="beginCreateDate"  value="<fmt:formatDate value="${bsCheckRecord.beginCreateDate}" pattern="yyyy-MM-dd"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">盘点结束时间：</span>
							<input name="endCreateDate" 
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${bsCheckRecord.endCreateDate}" pattern="yyyy-MM-dd"/>"   />
						</div>
					</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
					<input id="reset" class="btn btn-primary" type="reset" value="重置"/>
					<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/>
					<a id="back" class="btn btn-success mr10 w100" href="${ctx}/product/bsManageProduct/check" ><span class="glyphicon glyphicon-arrow-left">返回</span></a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
					 <th style="width: 50px;">编号</th>
					<th>盘点编码</th>
					<th>名称</th>
					<th>生产厂商</th>
					<th>批次编号</th>
					<th>盘点前库存</th>
					<th>盘点后库存</th>
					<th>盘点数量</th>
					<th>操作人</th>
					<th>备注</th>
					<th>操作时间</th>
						<shiro:hasPermission name="product:bsCheckRecord:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsCheckRecord" varStatus="status">
					<tr>
					     <th style="width: 50px;">${status.index + 1}</th>
						 <td>
							${bsCheckRecord.code}
						</td>
						 <td>
							${bsCheckRecord.name}
						</td>
						 <td>
							${bsCheckRecord.manufacturer}
						</td>
						<td>
							${bsCheckRecord.batchno}
						</td>
					    <td>
							${bsCheckRecord.checkBefore}
						</td>
						 <td>
							${bsCheckRecord.checkAfter}
						</td>
						 <td>
							${bsCheckRecord.checknum}
						</td>
						 <td>
							${userName}
						</td>
						<td>
							${bsCheckRecord.remarks}
						</td>
						<td><a href="">
							<fmt:formatDate value="${bsCheckRecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</a></td>
						
						<shiro:hasPermission name="product:bsCheckRecord:edit"><td>
							<a href="${ctx}/product/bsCheckRecord/form?id=${bsCheckRecord.id}">修改</a>
							<a href="${ctx}/product/bsCheckRecord/delete?id=${bsCheckRecord.id}" onclick="return confirmx('确认要删除该库存盘点记录吗？', this.href)">删除</a>
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