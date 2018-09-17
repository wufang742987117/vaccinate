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
<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表一（儿童计划免疫基础资料统计年报表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th class="title" colspan="13"> 表一（儿童计划免疫基础资料统计年报表）</th>
			</tr>
		</thead>
		<tr>
			<td rowspan="2">报告单位</td>
			<td rowspan="2">国标编码</td>
			<td colspan="4">行政区划分</td>
			<td rowspan="2">人口总数</td>
			<td rowspan="2"><15岁儿童数</td>
			<td rowspan="2"><=7岁儿童数</td>
			<td colspan="2">本年度出生</td>
			<td rowspan="2">建卡人数</td>
			<td rowspan="2">建证人数</td>
		</tr>
		<tr>
			<td>市级</td>
			<td>县级</td>
			<td>镇级</td>
			<td>村级</td>
			<td>人数</td>
			<td>率（%）</td>
			
		</tr>


		<tr>
			<td>本地</td>
			<td>3406031001</td>
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
			<td>3406031001</td>
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
			<td>3406031001</td>
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
		</tr>
		<tr>
			<td colspan="13" style="text-align: left;">
			备注：①“参加保偿”指应种儿童是否参加计划免疫保偿或已缴纳保证金。
			②“建卡（证）儿童数”指当年累计建卡（证）儿童数。<br>
			&emsp;&emsp;&emsp;③“<=7岁流动儿童数”指非本县户口的<=7岁儿童数。&emsp;&emsp;&emsp;&emsp;&emsp;
			④本表乡镇级填至村级。<br><br>
			填报人：&emsp;&emsp;&emsp;&emsp;&emsp;审核人：&emsp;&emsp;&emsp;&emsp;&emsp;填报单位（印章）：&emsp;&emsp;&emsp;&emsp;&emsp;填报日期&emsp;&emsp;年&emsp;&emsp;月&emsp;&emsp;日
			</td>
		</tr>
	</table>
	
</html>
