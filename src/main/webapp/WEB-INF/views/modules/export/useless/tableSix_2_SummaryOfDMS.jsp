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
	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('表六-2（疾病发病死亡统计汇总表）')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th colspan="17" class="title">疾病发病死亡统计汇总表</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td rowspan="2">单位名称</td>
				<td colspan="2">脊髓灰质炎</td>
				<td colspan="2">麻疹</td>
				<td colspan="2">新生儿破伤风</td>
				<td colspan="2">白喉</td>
				<td colspan="2">百日咳</td>
				<td colspan="2">乙脑</td>
				<td colspan="2">流脑</td>
				<td colspan="2">乙肝</td>
			</tr>
			<tr>
				<td>发病数</td>
				<td>死亡数</td>				
				<td>发病数</td>
				<td>死亡数</td>
				<td>发病数</td>
				<td>死亡数</td>
				<td>发病数</td>
				<td>死亡数</td>
				<td>发病数</td>
				<td>死亡数</td>
				<td>发病数</td>
				<td>死亡数</td>
				<td>发病数</td>
				<td>死亡数</td>
				<td>发病数</td>
				<td>死亡数</td>				
			</tr>
			<tr>
				<td>单位1</td>
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
				<td>单位2</td>
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
		<tbody>
			<tr class="text-left">
				<td colspan="1"></td>
				<td colspan="3">填报人</td>
				<td colspan="3">审核人</td>
				<td colspan="5">填报单位(印章)</td>
				<td colspan="5">填报日期 2017年1月1号</td>
				
			</tr>
		</tbody>
	</table>
	
</body>
</html>