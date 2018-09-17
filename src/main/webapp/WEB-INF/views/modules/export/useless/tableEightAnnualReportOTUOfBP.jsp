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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表八（生物制品使用情况统计年报表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th colspan="21" class="title">生物制品使用情况统计年报表</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="3">报告单位</td>
				<td colspan="5">卡介苗</td>
				<td colspan="5">脊灰(减毒二倍体)</td>
				<td colspan="5">百白破</td>
				<td colspan="5">乙脑(灭活)</td>
			</tr>
			<tr>
				<td rowspan="2">用苗数(毫升)</td>
				<td colspan="2">接种人次数</td>
				<td rowspan="2">有效使用数(毫升)</td>
				<td rowspan="2">损耗系数</td>
				<td rowspan="2">用苗数(毫升)</td>
				<td colspan="2">接种人次数</td>
				<td rowspan="2">有效使用数(毫升)</td>
				<td rowspan="2">损耗系数</td>
				<td rowspan="2">用苗数(毫升)</td>
				<td colspan="2">接种人次数</td>
				<td rowspan="2">有效使用数(毫升)</td>
				<td rowspan="2">损耗系数</td>
				<td rowspan="2">用苗数(毫升)</td>
				<td colspan="2">接种人次数</td>
				<td rowspan="2">有效使用数(毫升)</td>
				<td rowspan="2">损耗系数</td>
			</tr>
			<tr>
				<td>本点</td>
				<td>临时</td>
				<td>本点</td>
				<td>临时</td>
				<td>本点</td>
				<td>临时</td>
				<td>本点</td>
				<td>临时</td>
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
				<td></td>
			</tr>
			<tr> <td>单位2</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> </tr> 
			<tr> <td>单位3</td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> <td></td> </tr> 

			</tbody>

		<tbody>
			<tr>
				<td colspan="21" class="text-left">说明：1.领苗数=上年底库存数+本年领苗数-本年底库存数; 2.有效使用数=接种剂次×接种人数; 3.损耗系数=用苗数÷有效使用数</td>
			</tr>
			<tr class="text-left">
				<td colspan="3"></td>
				<td colspan="3">填报人</td>
				<td colspan="3">审核人</td>
				<td colspan="5">填报单位(印章)</td>
				<td colspan="5">填报日期 2017年1月1号</td>
				<td colspan="2"></td>
			</tr>
		</tbody>
	</table>
	
</body>
</html>