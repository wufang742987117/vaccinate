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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表十一（儿童预防年龄组人口统计表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th colspan="20" class="title">儿童预防年龄组人口统计表</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">单位名称</td>
				<td rowspan="2">总人口数</td>
				<td rowspan="2">出生率(%)</td>
				<td colspan="15">各年龄组儿童数(岁)</td>
				<td rowspan="2">≤7小计</td>
				<td rowspan="2">≤15小计</td>
			</tr>
			<tr>
				<td>0~</td>
				<td>1~</td>
				<td>2~</td>
				<td>3~</td>
				<td>4~</td>
				<td>5~</td>
				<td>6~</td>
				<td>7~</td>
				<td>8~</td>
				<td>9~</td>
				<td>10~</td>
				<td>11~</td>
				<td>12~</td>
				<td>13~</td>
				<td>14~</td>
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
		</tbody>
	</table>
	
</body>
</html>