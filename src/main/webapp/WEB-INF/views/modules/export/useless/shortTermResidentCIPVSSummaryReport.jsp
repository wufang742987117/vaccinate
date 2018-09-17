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
<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('流动儿童月报')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
		<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
		<tr><th colspan="26" class="title">2017年04月短期居住儿童免疫规划疫苗接种情况统计汇总报表（本地户籍）</th></tr>	
		<tr><td colspan="26" class="subtitle">(省市县及乡级单位汇总、上报使用)</td></tr>	
		<tr><td colspan="26" class="subtitle">安徽省淮北市相山区相南办相阳路门诊</td></tr>	
		</thead>
		<tbody>
			<tr>
				<td rowspan="3">报告单位</td>
				<td rowspan="3">行政区划代码</td>
				<td rowspan="3">接种报告情况</td>
				<td colspan="23">实种人数</td>
			</tr>
			<tr>
				<td rowspan="2">BCG</td>
				<td colspan="4">OPV-hdc</td>
				<td colspan="5">DPT</td>
				<td colspan="2">MV</td>
				<td colspan="4">HepB-yst</td>
				<td colspan="3">JE-i</td>
				<td colspan="4">MenA</td>
			</tr>
			<tr>
				<td>基础1</td>
				<td>基础2</td>
				<td>基础3</td>
				<td>加强</td>
				<td>基础1</td>
				<td>基础2</td>
				<td>基础3</td>
				<td>加强</td>
				<td>DT加强</td>
				<td>基础</td>
				<td>加强</td>
				<td>基础1</td>
				<td>及时数</td>
				<td>基础2</td>
				<td>基础3</td>
				<td>基础</td>
				<td>基础1</td>
				<td>基础2</td>
				<td>基础1</td>
				<td>基础2</td>
				<td>基础1</td>
				<td>基础2</td>
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
				<td style="color: blue">合计</td>
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
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
			<td colspan="26" style="text-align: left;">
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;审核人：&emsp;&emsp;填表人：&emsp;&emsp;报出日期：&emsp;&emsp;<br><br>
			备注：该表只适用于在辖区内居住时间少于三个月.建立临时接种卡证的本地户籍儿童（如返乡儿童）。
			</td>
			</tr>	
		</tbody>
	</table>
	
</body>
</html>