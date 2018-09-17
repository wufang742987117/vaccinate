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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('bOPV免疫规划脊灰疫苗接种情况周报表')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>	
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;"> 
		<thead>
			<tr>
				<th colspan="7" class="title">bOPV免疫规划脊灰疫苗接种情况周报表</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan=2>剂次</td>
				<td colspan=5>接种剂次数</td>
				<td rowspan=2>合计</td>
			</tr>
			<tr>
				
				<td>昆明所IPV</td>
				<td>巴斯德IPV</td>
				<td>五联疫苗</td>
				<td>二价OPV</td>
				<td>三价OPV</td>
			</tr>	

			<tr>
				<td>一</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>二</td>
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