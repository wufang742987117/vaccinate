<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>表六（疾病发病死亡统计表）</title>
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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表六（疾病发病死亡统计表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="11">
					表六（疾病发病死亡统计表）
				</th>
			</tr>
		</thead>
		<tbody>
			<tr class="b">
				<td rowspan="3">年龄组</td>
				<td colspan="5">发病</td>
				<td colspan="5">死亡</td>
			</tr>
			<tr>
				<td rowspan="2">发病数</td>
				<td colspan="4">接种情况</td>
				<td rowspan="2">死亡数</td>
				<td colspan="4">接种情况</td>
			</tr>
			<tr>
				<td>全程</td>
				<td>未全程</td>
				<td>未接种</td>
				<td>不详</td>
				<td>全程</td>
				<td>未全程</td>
				<td>未接种</td>
				<td>不详</td>
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
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</body>
</html>