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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表九-2（冷链设备情况年报表（2））')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th colspan="14" class="title">冷链设备情况年报表（2）</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">单位名称</td>
				<td colspan="7">普通冷库</td>
				<td colspan="6">低温冷库</td>				
			</tr>
			<tr>
				<td>数量(座)</td>
				<td>容积(M3)</td>
				<td>库体结构</td>
				<td>机组产地</td>
				<td>安装日期</td>
				<td>运转情况</td>
				<td>备注</td>
				<td>数量(座)</td>
				<td>容积(M3)</td>
				<td>库体结构</td>
				<td>机组产地</td>
				<td>运转情况</td>
				<td>备注</td>
			</tr>
			<tr>
				<td>哈哈</td>
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