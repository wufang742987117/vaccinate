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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表三（计划免疫服务形式统计年报表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th class="title" colspan="17"> 表三（计划免疫服务形式统计年报表）</th>
			</tr>
		</thead>
		<tr>
			<td rowspan="3">单位名称</td>
			<td rowspan="3">人口总数</td>
			<td rowspan="2" colspan="4">接种形式（覆盖人口数）</td>
			<td rowspan="2" colspan="7">接种时间（覆盖人口数）</td>
			<td rowspan="3">接种点总数</td>
			<td colspan="3">不同注射器的接种点数</td>
			
		</tr>
		<tr>
			<td rowspan="2">一次性注射器</td>
			<td colspan="2">玻璃注射器</td>
			</tr>
		<tr>
			<td>城镇门诊</td>
			<td>乡镇门诊</td>
			<td>村卫生室接种</td>
			<td>上门接种</td>
			<td>按日接种</td>
			<td>按周接种</td>
			<td>按旬接种</td>
			<td>按月接种</td>
			<td>双月接种</td>
			<td>按季接种</td>
			<td>其他</td>
			<td>一人一针一管</td>
			<td>一人一针多人管</td>
		</tr>

		<tr>
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
		<tr>
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
		<tr>
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
		<tr>
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
		<tr>
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

	</table>
</html>
