<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>疫苗名称配置</title>
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
		<li ><a href="${ctx}/product/bsManageProduct/">疫苗库存管理</a></li>
		<li ><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li class="active"><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsManageVaccine" action="${ctx}/vaccine/bsManageVaccine/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗大类：</span>
							<form:select path="gNum" class="form-control">
								<form:option value="" label="--请选择--"/>
								<form:options items="${groups}" itemLabel="gName" itemValue="gNum" htmlEscape="false"/>
							</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>
					<a class="btn btn-primary w100" href="javascript:void(0);"  onclick="alertForm('${ctx}/vaccine/bsManageVaccine/form','添加');"><span class="glyphicon glyphicon-plus"></span>添加</a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗大类</th>
						<th>疫苗属性</th>
						<th>疫苗名称</th>
						<th>英文简称</th>
						<th>国家编码</th>
						<th>是否活苗</th>
						<shiro:hasPermission name="vaccine:bsManageVaccine:edit"><th>操作</th></shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsManageVaccine">
					<tr>
						<td>
							${bsManageVaccine.gName}
						</td>
						<td>
							${fns:getDictLabel(bsManageVaccine.type, 'isImport', '')}
						</td>
						<td>
							${bsManageVaccine.name}
						</td>
						<td>
							${bsManageVaccine.codeAll}
						</td>
						<td>
							${bsManageVaccine.id}
						</td>
						<td>
							${fns:getDictLabel(bsManageVaccine.live, 'yes_no', '')}
						</td>
						<shiro:hasPermission name="vaccine:bsManageVaccine:edit"><td>
							<a href="${ctx}/vaccine/bsManageVaccine/form?id=${bsManageVaccine.id}">修改</a>
							<a href="${ctx}/vaccine/bsManageVaccine/delete?id=${bsManageVaccine.id}" onclick="return confirmx('确认要删除该疫苗字典数据吗？', this.href)">删除</a>
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