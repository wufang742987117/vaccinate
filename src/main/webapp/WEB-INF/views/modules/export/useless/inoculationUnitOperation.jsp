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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('接种单位运转情况')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th colspan="10" class="title">儿童预防接种个案录入情况</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">年度</td>
				<td colspan="3">儿童数</td>
				<td rowspan="2">出生儿童数</td>
				<td rowspan="2">覆盖率(%)</td>
				<td rowspan="2">及时建卡数</td>
				<td rowspan="2">及时建卡率(%)</td>
				<td rowspan="2">重卡数</td>
				<td rowspan="2">重卡率</td>				
			</tr>
			<tr>
				<td>小计</td>
				<td>男</td>
				<td>女</td>
			</tr>
			<tr>
				<td>2011</td>
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
				<td>2012</td>
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
				<td colspan="10" style="height: 40px; border: none !important;"></td>
			</tr>
		</tbody>

		<tbody>
			<tr>
				<th colspan="12" class="title">儿童预防接种记录录入及时性与完整性</th>
			</tr>
			<tr>
				<td >疫苗名称</td>
				<td >接种剂次数</td>
				<td >及时录入剂次数</td>
				<td >及时录入率</td>
				<td >录入批号剂次数</td>
				<td >批号录入率</td>
				<td >录入生产企业剂次数</td>				
				<td >生产企业录入率</td>	
				<td >录入接种部位剂次数</td>				
				<td >接种部位录入率</td>
				<td >信息完整剂次数</td>				
				<td >信息完整率</td>				
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
			</tr>
		</tbody>
	</table>
	
</body>
</html>