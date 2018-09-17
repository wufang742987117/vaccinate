<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节假日配置</title>
	<meta name="decorator" content="default"/>
	<style>
		.layui-layer-dialog .layui-layer-padding {padding: 40px 20px 20px 80px!important;text-align: left;font-size: 25px!important;height:auto!important;}
		.layui-layer-dialog .layui-layer-content .layui-layer-ico {top: 38px!important;left: 35px!important;}
		.layui-layer-btn a {height: 35px;line-height: 35px;padding: 0 20px;font-size:16px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnAdd").click(function(){
				alertForm('${ctx}/holiday/sysHoliday/form?datetype=1','添加法定节假日');
			});
			$(document).on("click",".btn-modify",function(){
				alertForm('${ctx}/holiday/sysHoliday/form?id='+$(this).attr("data-id"),'添加法定节假日');
			});
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
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li class="active"><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
			<form:form id="searchForm" modelAttribute="sysHoliday" action="${ctx}/holiday/sysHoliday/" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">节假日类型：</span>
									<form:select path="dateType" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('holiday_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">节假日：</span>
									<form:input path="remarks" htmlEscape="false" maxlength="255" class="form-control"/>
							</div>
						</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>
					<input id="btnAdd" class="btn btn-primary w100" type="button" value="新增"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>节假日</th>
						<th>类型</th>
						<th>日期</th>
						<th>星期</th>
						<th>更新时间</th>
						<th>操作</th>
<%-- 						<shiro:hasPermission name="holiday:sysHoliday:edit"><th>操作</th></shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sysHoliday">
					<tr>
						<td class="text-center">
							${sysHoliday.remarks}
						</td>
						<td class="text-center">
							${fns:getDictLabel(sysHoliday.dateType, 'holiday_type', '')}
						</td>
						<td class="text-center">
							<fmt:formatDate value="${sysHoliday.dateTime}" pattern="yyyy-MM-dd"/>
						</td>
						<td class="text-center">
							${fns:getDictLabel(sysHoliday.dateDay, 'holiday_date_day', '')}
						</td>
						<td class="text-center">
							<fmt:formatDate value="${sysHoliday.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							<button class="btn btn-xs btn-primary btn-modify" data-id=${sysHoliday.id}>修改</button>
							<a class="btn btn-xs btn-danger" href="${ctx}/holiday/sysHoliday/delete?id=${sysHoliday.id}" onclick="return confirmx('确认要删除该节假日吗？', this.href)">删除</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
	</div>
</body>
</html>