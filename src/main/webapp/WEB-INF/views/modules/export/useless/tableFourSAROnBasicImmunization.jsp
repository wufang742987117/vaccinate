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
	<h1></h1>
	<p><span></span><span ></span></p>
	<p><span></span><span></span></p>
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表四（基础免疫接种情况统计年报表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse; width: 800px;">
		<thead>
			<tr>
				<th class="title" colspan="13">2016年基础免疫接种情况统计年报表</th>
			</tr>
			<tr>
				<td class="text-left" colspan="3">防计表四</td>
				<td class="text-left" colspan="10">淮北市相山区相南办相阳路门诊</td>
			</tr>
			<tr>
				<td class="text-left" colspan="13">本年(次)应报单位数1,本年(次)实报单位数1,报告率100%</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="3">报告单位</td>
				<td colspan="12">当年基础免疫应种人数</td>
			</tr>
			<tr>
				<td rowspan="2">BCG</td>
				<td colspan="3">OPV-hdc</td>
				<td colspan="3">DPT</td>
				<td rowspan="2">MV</td>
				<td colspan="3">HepB-yst</td>
				<td rowspan="2">JE-i</td>
			</tr>
			<tr>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>1</td>
				<td>2</td>
				<td>3</td>
				<td>1</td>
				<td>2</td>
				<td>3</td>
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
			</tr>
		</tbody>
		<tbody>
			<tr class="text-left">
				<td colspan="1"></td>
				<td colspan="3">填报人</td>
				<td colspan="3">审核人</td>
				<td colspan="3">填报单位(印章)</td>
				<td colspan="3">填报日期 2017年1月1号</td>
				
			</tr>
		</tbody>
	</table>
	
</body>
</html>