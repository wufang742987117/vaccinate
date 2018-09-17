<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>乙肝疫苗接种人次数统计表</title>
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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('乙肝疫苗接种人次数统计表')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="10">
					<font size="3">2017年04月乙肝接种人次数月报表</font>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="7" class="text-left">
					<font size="2">报告单位：淮北市 相山区 孟山中路门诊</font>
				</td>
				<td colspan="3">
					<font size="2">第1页/共1页</font>
				</td>
			</tr>
			<tr class="b">
				<td rowspan=2>单位<br>报告单位</td>
				<td colspan=4>第一剂</td>
				<td rowspan=2>第二剂</td>
				<td rowspan=2>第三剂</td>
				<td rowspan=2>加强</td>
				<td rowspan=2>报废</td>
				<td rowspan=2>合计</td>
			</tr>
			<tr>
				<td>本院产科</td>
				<td>外院产科</td>
				<td>计免门诊</td>
				<td>小计</td>
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
			</tr>
			<tr>
				<td>合计</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>  
				<td colspan="10" class="text-left">
					<font size="2">填报人：&nbsp;&nbsp;&nbsp;&nbsp;审核人：&nbsp;&nbsp;&nbsp;&nbsp;填报单位(印章)： &nbsp;&nbsp;&nbsp;&nbsp;填报日期：&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</font>
				</td>
			</tr>	
		</tbody>
	</table>
</body>
</html>