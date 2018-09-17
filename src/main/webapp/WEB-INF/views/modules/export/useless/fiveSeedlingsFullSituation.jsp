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
<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('五苗全程情况')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>	

<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr><th colspan="30" class="title">五苗全程情况</th></tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan=2>年度</td>
				<td rowspan=2>儿童数</td>
				<td colspan=4>乙肝</td>
				<td colspan=4>卡介苗</td>
				<td colspan=4>脊灰</td>
				<td colspan=4>百白破</td>
				<td colspan=4>麻疹</td>
				<td colspan=4>四苗</td>
				<td colspan=4>五苗</td>
			</tr>
			<tr>
				<td>实种数</td>
				<td>实种率</td>
				<td>合格数</td>
				<td>合格率</td>
				<td>实种数</td>
				<td>实种率</td>
				<td>合格数</td>
				<td>合格率</td>
				<td>实种数</td>
				<td>实种率</td>
				<td>合格数</td>
				<td>合格率</td>
				<td>实种数</td>
				<td>实种率</td>
				<td>合格数</td>
				<td>合格率</td>
				<td>实种数</td>
				<td>实种率</td>
				<td>合格数</td>
				<td>合格率</td>
				<td>实种数</td>
				<td>实种率</td>
				<td>合格数</td>
				<td>合格率</td>
				<td>实种数</td>
				<td>实种率</td>
				<td>合格数</td>
				<td>合格率</td>
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
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
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
		</tbody>
				
	</table>
</body>
</html>