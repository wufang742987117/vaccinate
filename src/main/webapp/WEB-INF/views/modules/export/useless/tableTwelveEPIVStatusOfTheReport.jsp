<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>表十二（扩大免疫疫苗接种情况年报表）</title>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
		#exportTable .text-left{text-align: left !important;}
		#exportTable .text-right{text-align: left !important;}
		#exportTable .tips{line-height: 16px; font-size: 14px;}
	</style>
</head>
<body>
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表十二（扩大免疫疫苗接种情况年报表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="9">
					表十二（扩大免疫疫苗接种情况年报表）
				</th>
			</tr>
		</thead>
		<tbody>	
			<tr class="b">
				<td rowspan="2">疫苗名称</td>
				<td rowspan="2">针次</td>
				<td colspan="3">基础免疫</td>
				<td colspan="3">加强免疫</td>
				<td rowspan="2">备注</td>
			</tr>
			<tr>
				<td>应种人数</td>
				<td>实种人数</td>
				<td>接种率</td>
				<td>应种人数</td>
				<td>实种人数</td>
				<td>接种率</td>
			</tr>
			<tr>
				<td>1</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		<tbody>
		</table>
</body>
</html>