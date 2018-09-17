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
	<p><span></span></p>
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('接种门诊季度核查0~7岁儿童接种卡、证统计')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th colspan="7" class="title">接种门诊季度核查0~7岁儿童接种卡、证统计</th>
			</tr>
			<tr>
				<td colspan="5">淮北市 相山区(市、区) 相南办(镇、街道) 相阳路门诊(社区居委)</td>
				<td colspan="2">2017年0季度</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">年龄组(岁)</td>
				<td rowspan="2">实有儿童总数</td>
				<td rowspan="2">已建卡数</td>
				<td rowspan="2">已建证数</td>
				<td colspan="3">本季度核查结果</td>
			</tr>
			<tr>
				<td>补卡数</td>
				<td>剔卡数</td>
				<td>消卡数</td>
			</tr>
			<tr>
				<td>0</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>1</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>3</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>5</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>6</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>7</td>
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
			</tr>
		</tbody>
		<tbody>
			<tr class="text-left">
				<td colspan="2"></td>
				<td colspan="2">填表人：</td>
				<td colspan="3">填表日期：</td>
			</tr>
		</tbody>
	</table>
	<span></span>
	
</body>
</html>