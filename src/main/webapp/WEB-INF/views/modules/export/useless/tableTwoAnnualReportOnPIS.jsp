<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>表二（计划免疫工作人员统计年报表）</title>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
		#exportTable .text-left{text-align: left !important;}
		#exportTable .text-right{text-align: left !important;}
		#exportTable .tips{line-height: 16px; font-size: 14px;}
	</style>
</head>
<body>
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表二（计划免疫工作人员统计年报表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="21">
					表二（计划免疫工作人员统计年报表）
				</th>
			</tr>
		</thead>
		<tbody>
			<tr class="b">
				<td rowspan="2">单位名称</td>
				<td rowspan="2">国标编码</td>
				<td rowspan="2">总计</td>
				<td colspan="7">卫生防疫机构</td>
				<td colspan="5">城镇防保人员</td>
				<td colspan="5">乡镇防保人员</td>
				<td rowspan="2">乡村医生</td>
			</tr>
			<tr>
				<td>小计</td>
				<td>主任医师</td>
				<td>副主任医师</td>
				<td>主管医师</td>
				<td>医师</td>
				<td>医士</td>
				<td>其它</td>
				<td>小计</td>
				<td>主管医师以上</td>
				<td>医师</td>
				<td>医士</td>
				<td>其它</td>
				<td>小计</td>
				<td>主管医师以上</td>
				<td>医师</td>
				<td>医士</td>
				<td>其它</td>
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
			</tr>
		</tbody>
	</table>
</body>
</html>