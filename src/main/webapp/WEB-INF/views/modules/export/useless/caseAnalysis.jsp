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
	<script src="${ctxStatic}/js/ExportExcel.js"></script><link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">

</head>
<body>	
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('个案分析')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr><th colspan="13" class="title">个案分析</th></tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">出生年度</td>
				<td colspan="3">儿童数</td>
				<td colspan="5">建卡情况</td>
				<td rowspan="2">迁出数</td>
				<td rowspan="2">迁入数</td>
				<td colspan="2">重卡情况</td>
			</tr>
			<tr>
				<td>小计</td>
				<td>男</td>
				<td>女</td>
				<td>新建卡数</td>
				<td>0月</td>
				<td>1月</td>
				<td>2月</td>
				<td>及时建卡率(%)</td>
				<td>重卡数</td>
				<td>重卡率(%)</td>
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