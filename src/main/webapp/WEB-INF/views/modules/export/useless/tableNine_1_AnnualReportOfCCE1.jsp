<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
		#exportTable .text-left{text-align: left !important;}
		#exportTable .text-right{text-align: left !important;}
		#exportTable .tips{line-height: 16px; font-size: 14px;}
	</style>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
</head>
<body>

	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表九-1（冷链设备情况年报表（1））')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th class="title" colspan="20">冷链设备情况年报表（1）</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">单位名称</td>
				<td rowspan="2">乡镇(行政村)数</td>
				<td rowspan="2">辖区人口总数</td>
				<td colspan="4">低温冰箱(台)</td>
				<td colspan="4">普通冰箱(台)</td>
				<td colspan="3">冷藏箱(个)</td>
				<td colspan="3">冷藏包(个)</td>
				<td colspan="3">冰排(个)</td>
			</tr>
			<tr>
				<td>总数</td>
				<td>待修</td>
				<td>保费</td>
				<td>现有</td>
				<td>总数</td>
				<td>待修</td>
				<td>保费</td>
				<td>现有</td>
				<td>总数</td>
				<td>保费</td>
				<td>现有</td>
				<td>总数</td>
				<td>保费</td>
				<td>现有</td>
				<td>总数</td>
				<td>保费</td>
				<td>现有</td>
			</tr>
			<tr>
				<td>单位1</td>
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
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>单位2</td>
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