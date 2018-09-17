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

	<button id="exportBtn" class="btn btn-primary" onclick="SaveAsFile('疫苗消耗月报表')">导出</button>	<a id="exportCancel" class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>						
	<table id="exportTable"   border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr>
				<th colspan="18" class="title">疫苗消耗月报表</th>
			</tr>
		</thead>
		<tbody>
			<tr class="b">
				<td rowspan="2" colspan="2">疫苗名称</td>
				<td rowspan="2">单位</td>
				<td colspan="9">当月</td>
				<td colspan="6">当年累计</td>
			</tr>
			<tr class="b">
				<td>上月底库存数</td>
				<td>本月入库数</td>
				<td>开启使用数</td>
				<td>接种人次数</td>
				<td>消耗系数</td>
				<td>损坏数</td>
				<td>合计核销数</td>
				<td>本月底库存数</td>
				<td>下月计划书</td>
				<td>入库数</td>
				<td>开启使用数</td>
				<td>接种人次数</td>
				<td>消耗系数</td>
				<td>损坏数</td>
				<td>合计核销数</td>
			</tr>
			<tr>
				<td rowspan="5">一类疫苗</td>
				<td>卡介苗</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>糖丸</td>
				<td>粒</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>百白破</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>麻疹</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>乙肝</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td rowspan="5">二类疫苗</td>
				<td>卡介苗</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>糖丸</td>
				<td>粒</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>百白破</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>麻疹</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
				<td>乙肝</td>
				<td>毫升</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
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
			<tr>
				<td colspan="18" class="tips text-left">注：1.损耗系数=开启使用数÷(接种人次数×每针次接种剂量) 2.损坏数指运输途中,存取疫苗等意外造成破损及过期报废的数量 3.核销数=开启使用数+损坏数</td>
			</tr>
			<tr class="text-left">
				<td colspan="2"></td>
				<td colspan="3">填报人</td>
				<td colspan="3">审核人</td>
				<td colspan="5">填报单位(印章)</td>
				<td colspan="5">填报日期 2017年1月1号</td>

			</tr>
		</tbody>
	</table>
	
</body>
</html>