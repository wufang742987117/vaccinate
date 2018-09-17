<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>疫苗消耗日报</title>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
	</style>
</head>
<body>
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('疫苗消耗日报')">导出</button>
	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="9">
					<font size="3">疫苗消耗日报</font>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr class="b">
				<td rowspan=2>接种日期</td>
				<td rowspan=2>疫苗名称</td>
				<td rowspan=2>生产企业</td>
				<td rowspan=2>批号</td>
				<td rowspan=2>剂次数</td>
				<td colspan=2>收费情况</td>
				<td colspan=2>免疫情况</td>
			</tr>
			<tr>
				<td>免费数</td>
				<td>自费数</td>
				<td>常规免疫</td>
				<td>强化应急</td>
			</tr>
			<c:forEach items="${List }" var="monthlyReportOnVaccConsumption">
			<tr>
				<td>${date }</td>
				<td>${ monthlyReportOnVaccConsumption.name}</td>
				<td>${ monthlyReportOnVaccConsumption.manufacturer}</td>
				<td>${ monthlyReportOnVaccConsumption.batchno}</td>
				<td>${ monthlyReportOnVaccConsumption.count}</td>
				<c:choose>
				<c:when test="${monthlyReportOnVaccConsumption.isforeign=='Y' }">
				
				<td>${ monthlyReportOnVaccConsumption.count}</td>
				<td></td>
				</c:when>
				<c:otherwise>
				<td></td>
				<td>${ monthlyReportOnVaccConsumption.count}</td>
				</c:otherwise>
				</c:choose>
				<td></td>
				<td></td>
			</tr>
			</c:forEach>
		</tbody>
				
	</table>
</body>
</html>