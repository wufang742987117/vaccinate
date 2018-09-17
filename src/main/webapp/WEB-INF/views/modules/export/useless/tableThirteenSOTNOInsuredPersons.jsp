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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表十三（计免保偿制入保人数统计表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th class="title" colspan="15">计免保偿制入保人数统计表</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">单位</td>
				<td colspan="6">本年入保人数</td>
				<td colspan="8">历史累计入保人数</td>
			</tr>
			<tr>
				<td>12年出生</td>
				<td>13年出生</td>
				<td>14年出生</td>
				<td>15年出生</td>
				<td>16年出生</td>
				<td>小计</td>
				<td>9年出生</td>
				<td>10年出生</td>
				<td>11年出生</td>
				<td>12年出生</td>
				<td>13年出生</td>
				<td>14年出生</td>
				<td>15年出生</td>
				<td>16年出生</td>
			</tr>			
			<tr>
				<td>本地</td>
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
				<td>流动</td>
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
				<td>临时</td>
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
				<td>合计</td>
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
		<tbody>
			<tr class="text-left">
				<td colspan="15" class="tips">备注：1、本年入保人数指在2016年内入保的人数，历年累计入保人数指到2016年12月31日止各年出生儿童的入保人数<br>&emsp;&emsp;&emsp;2、外地入保儿童作为一个单位进行统计</td>
			</tr>			
			<tr class="text-left">
				<td colspan="3"></td>
				<td colspan="3">填报人</td>
				<td colspan="3">审核人</td>
				<td colspan="3">填报单位(印章)</td>
				<td colspan="3">填报日期 2017年1月1号</td>
				
			</tr>
		</tbody>
	</table>
	
</body>
</html>