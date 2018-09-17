<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp"%>
<html>
<head>
<meta charset="UTF-8">
<title>建卡建证统计表</title>
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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('建卡建证统计表')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable" border="1"
		style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="13"><font size="3">建卡建证统计表</font></th>
			</tr>
		</thead>
		<tbody>
			<tr class="b">
				<td rowspan=3>报告单位</td>
				<td colspan=6>常住儿童</td>
				<td colspan=5>流动儿童</td>
				<td rowspan=3>备注</td>
			</tr>
			<tr>
				<td rowspan=2>出生数</td>
				<td rowspan=2>应建卡证数</td>
				<td colspan=4>建卡（证）情况</td>
				<td rowspan=2>当年出生儿童数</td>
				<td colspan=4>建卡（证）情况</td>
			</tr>
			<tr>
				<td>建卡数</td>
				<td>建卡率%</td>
				<td>建证数</td>
				<td>建证率%</td>
				<td>建卡数</td>
				<td>建卡率%</td>
				<td>建证数</td>
				<td>建证率%</td>
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
				<td></td>
				<td></td>
			</tr>
		</tbody>

	</table>
</body>
</html>