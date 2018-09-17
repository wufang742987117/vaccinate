<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
	</style>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
</head>
<body>	
	<div class=" col-sm-12" style="padding-left: 50px; margin-top: 50px;">
		<form:form id="searchForm" modelAttribute="export"
			action="${ctx}/export/export/hepatitisBSchedule" method="post"
			class="form-inline">
			<form:hidden path="id" />
			<div class="form-group">
				<div class="input-group">
					<span class="input-group-addon gray-bg text-right">日期(起)：</span> <input
						name="begintime"
						value="${export.begintime}"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
						class="laydate-icon form-control layer-date" />
				</div>
			</div>
			<div class="form-group" style="padding-left: 70px">
				<div class="input-group">
					<span class="input-group-addon gray-bg text-right">日期(止)：</span> <input
						name="endtime"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
						class="laydate-icon form-control layer-date"
						value="${export.endtime}" />
				</div>
			</div>

			<div class="form-group" style="padding-left: 50px;">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="查询" />
					<button class="btn btn-primary" onclick="SaveAsFile('接种乙肝明细表')">导出</button>
					<a class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
			</div>
		</form:form>
	</div>
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				
				<th class="title" colspan="11">接种乙肝明细表</th>
			</tr>
		</thead>
		<tr>
			<td rowspan="2">儿童编号</td>
			<td rowspan="2">儿童姓名</td>
			<td rowspan="2">性别</td>
			<td rowspan="2">出生日期</td>
			<td rowspan="2">建卡日期</td>
			<td colspan="2">第一剂</td>
			<td colspan="2">第二计</td>
			<td colspan="2">第三计</td>
			
		</tr>
		<tr>
			<td>接种日期</td>
			<td>评价</td>
			<td>接种日期</td>
			<td>评价</td>
			<td>接种日期</td>
			<td>评价</td>
			
		</tr>
		<c:forEach items="${HepatitisBSchedulelist }" var="hepatitisBSchedule">
		<tr>
			<td>${hepatitisBSchedule.childcode }</td>
			<td>${hepatitisBSchedule.childname }</td>
			<td>${fns:getDictLabel(hepatitisBSchedule.gender, 'sex', '')}</td>
			<td><fmt:formatDate value="${hepatitisBSchedule.birthday}" pattern="yyyy-MM-dd " /></td>  
			<td><fmt:formatDate value="${hepatitisBSchedule.createDate}" pattern="yyyy-MM-dd " /></td>
			<td><fmt:formatDate value="${hepatitisBSchedule.vaccinatedateone}" pattern="yyyy-MM-dd " /></td>
			<td>${hepatitisBSchedule.evaluateone }</td>
			<td><fmt:formatDate value="${hepatitisBSchedule.vaccinatedatetwo}" pattern="yyyy-MM-dd " /></td>
			<td>${hepatitisBSchedule.evaluatetwo }</td>
			<td><fmt:formatDate value="${hepatitisBSchedule.vaccinatedatethree}" pattern="yyyy-MM-dd " /></td>
			<td>${hepatitisBSchedule.evaluatethree }</td>
		</tr>
		</c:forEach>
	</table>
</html>
