<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>免疫规划使用情况</title>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
	</style>
</head>
<body>
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('免疫规划使用情况')">导出</button>
	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="52">
					<font size="3">2017年04月国家免疫规划疫苗使用数量月报表</font>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="47" class="text-left">
					<font size="2">报告单位：安徽省 淮北市 相山区 相南办 相阳路门诊</font>
				</td>
				<td colspan="5">
					<font size="2">第1页/共1页</font>
				</td>
			</tr>
			<tr class="b">
				<td rowspan=2>居住属性</td>
				<td colspan=3>卡介苗</td>
				<td colspan=3>脊灰（减毒二倍体）</td>
				<td colspan=3>百白破</td>
				<td colspan=3>麻疹</td>
				<td colspan=3>乙肝（酿酒酵母）</td>
				<td colspan=3>流脑A</td>
				<td colspan=3>麻腮风</td>
				<td colspan=3>白破</td>
				<td colspan=3>甲肝（减毒）</td>
				<td colspan=3>乙脑（减毒）</td>
				<td colspan=3>二价脊灰疫苗</td>
				<td colspan=3>乙肝（汉逊酵母）</td>
				<td colspan=3>流脑A+C</td>
				<td colspan=3>脊灰（减毒猴肾）</td>
				<td colspan=3>流脑A+C结合</td>
				<td colspan=3>麻风</td>
				<td colspan=3>百白破（无细胞）</td>
			</tr>
			<tr>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（粒）</td>
				<td>损耗数（粒）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
				<td>小计</td>
				<td>使用数（支）</td>
				<td>损耗数（支）</td>
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
				<td colspan="52" class="text-left">
					<font size="2">填报人：&nbsp;&nbsp;&nbsp;&nbsp;审核人：&nbsp;&nbsp;&nbsp;&nbsp;填报单位(印章)： &nbsp;&nbsp;&nbsp;&nbsp;填报日期：&nbsp;&nbsp;&nbsp;&nbsp;年 &nbsp;&nbsp;&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;日</font>
				</td>
			</tr>	
		</tbody>
	</table>
</body>
</html>