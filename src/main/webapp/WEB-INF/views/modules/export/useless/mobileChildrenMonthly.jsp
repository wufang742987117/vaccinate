<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>  
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
	</style>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
</head>
<body>	
<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('流动儿童月报')">导出</button>	
<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th class="title" colspan="22">淮北市流动儿童免疫接种情况月报表</th>
			</tr>
			<tr>
				<td class="subtitle" colspan="22">淮北市&emsp;相山区（市.区）&emsp;相南办（镇.街道）&emsp;相阳路门诊（社区.居委）</td>
			</tr>
		</thead>
		<tr>
			<td rowspan="4">年龄（岁）</td>
			<td  colspan="14">基础免疫（实种人数）</td>
			<td  colspan="3">加强免疫（实种人数）</td>
			<td  colspan="4">其他免疫</td>
		
		</tr>
		<tr>
			<td colspan="2">EV71V-i</td>
			<td rowspan="3">BCG</td>
			<td colspan="3">bOPV</td>
			<td rowspan="3">MV</td>
			<td colspan="4">HepB-yst</td>
			<td colspan="3">HepB-hpy</td>
			<td rowspan="3">bOPV</td>
			<td rowspan="3">MV</td>
			<td rowspan="3">DT</td>
			<td rowspan="3"></td>
			<td rowspan="3"></td>
			<td rowspan="3"></td>
			<td rowspan="3"></td>
			</tr>
		<tr>
			<td rowspan="2">1</td>
			<td rowspan="2">2</td>		
			<td rowspan="2">1</td>
			<td rowspan="2">2</td>
			<td rowspan="2">3</td>
			<td colspan="2">1</td>
			<td rowspan="2">2</td>
			<td rowspan="2">3</td>
			<td rowspan="2">1</td>
			<td rowspan="2">2</td>
			<td rowspan="2">3</td>
		</tr>
		<tr>
			<td>小计</td>
			<td>及时</td>
		</tr>
		<tr>
			<td>10</td>
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
			<td>10</td>
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
			<td>10</td>
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
			<td>10</td>
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
			<td colspan="22" style="text-align: left;">
			说明：本表为市（地）级.县级.乡级（基层免疫接种单位）使用。<br>
			实际出报日期：&emsp;&emsp;年&emsp;&emsp;月&emsp;&emsp;日&emsp;&emsp;&emsp;&emsp;制表人：&emsp;&emsp;&emsp;审核人：&emsp;&emsp;&emsp;
			</td>
		</tr>
	</table>
</html>
