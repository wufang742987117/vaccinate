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
<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表五（加强免疫接种情况统计年报表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
		<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th class="title" colspan="13"> 表五（加强免疫接种情况统计年报表）</th>
			</tr>
		</thead>
		<tr>
			<td rowspan="2">报告单位</td>
			<td colspan="6">当年加强免疫应种人数</td>
			<td colspan="6">当年加强免疫实种人数</td>
		</tr>
		<tr>
			<td>OPV-hdc</td>
			<td>DPT</td>
			<td>MV</td>
			<td>DT</td>
			<td>JE-i1</td>
			<td>JE-i2</td>
			<td>OPV-hdc</td>
			<td>DPT</td>
			<td>MV</td>
			<td>DT</td>
			<td>JE-i1</td>
			<td>JE-i2</td>
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
		</tr>
		<tr>
			<td colspan="13" style="text-align: left;">
			备注：&emsp;1.OPV1指1.5~2岁加强；&emsp;2.OPV2指四岁加强。 <br> <br> 填报人：&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;审核人：&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;填报单位（印章）：&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;填报日期：&emsp;&emsp;&emsp;年
			</td>
		</tr>
	</table>
	<div>
		


	</div>
</html>
