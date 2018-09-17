<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>二类疫苗统计表</title>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<c:if test="${not empty arg }">
		<script type="text/javascript">			
			$(function(){
				layer.msg('你输入的年或者季度有误',{icon: 2});
			})
		</script>		
	</c:if>
	<style>
		#exportTable{text-align: center;}
		#exportTable td,th{padding: 5px 10px 5px 10px !important; }
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
		#exportTable .text-left{text-align: left !important;}
		#exportTable .text-right{text-align: right !important;}
		#exportTable .pl50{padding-left: 50px !important;}
		#exportTable .tips{line-height: 16px; font-size: 14px;}
	</style>
</head>
<body>
	<div class=" col-sm-12" style="margin-top: 50px;">
		<form:form id="searchForm" modelAttribute="export" action="${ctx}/export/export/twoCategoriesOfVaccStatistics" method="post" class="form-inline">
			<form:hidden path="id"/>
			<div class="form-group" >
				<div class="input-group" >
					<form:input path="timeYear" placeholder="如2017" class="form-control number"/>
					<span class="input-group-addon gray-bg text-right">年</span>
				</div>
				<div class="input-group" >
					<%-- <form:input path="" placeholder="如1" class="form-control number"/> --%>
					<form:select path="timeQuarterly" class="form-control">
						<form:option value="1">1</form:option>
						<form:option value="2">2</form:option>
						<form:option value="3">3</form:option>
						<form:option value="4">4</form:option>
					</form:select>
					<span class="input-group-addon gray-bg text-right">季度</span>
				</div>
			</div>
			<div class="form-group" >
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
				<button class="btn btn-primary" onclick="SaveAsFile('二类疫苗统计表')">导出</button>	
				<a class="btn btn-default" href="${ctx}/export/export/exportlist">返回</a>
			</div>
		</form:form>
	</div>
	<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
			<tr class="title">
				<th colspan="5">
					<font size="3">${export.timeYear==null?"2017":export.timeYear }年${export.timeQuarterly==null?"1":export.timeQuarterly }季度第二类其它疫苗接种情况统计表</font>
				</th>
			</tr>
			<tr class="subtitle">
				<td colspan="5">
					<font size="2">(接种单位汇总、上报使用)</font>
				</td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="4" class="text-left">
					<font size="2">安徽省 淮北市 相山区 相南办 相阳路门诊</font>
				</td>
				<td colspan="1">
					<font size="2">第1页/共1页</font>
				</td>
			</tr>
			<tr class="b">
				<td>疫苗名称</td>
				<td>规格</td>
				<td>购苗数量</td>
				<td>受种人数</td>
				<td>库存数量</td>
			</tr>
			<c:forEach items="${exportList }" var="export">
			<tr>
				<td>${export.name }</td>
				<td>
				${fns:getDictLabel(export.specification , 'specification', '')}
				</td>
				<td>${export.num }</td>
				<td>${export.num1 }</td>
				<td>${export.num2 }</td>
			</tr>
			</c:forEach>
			<tr>
				<td>合计</td>
				<td></td>
				<td>${count1 }</td>
				<td>${count2 }</td>
				<td>${count3 }</td>
			</tr>
			<tr>  
				<td colspan="5" class="text-left">
					<font size="2">备注：①本表供接种单位使用；</font>
				</td>
			</tr> 
			<tr>  
				<td colspan="5" class="text-left">
					<font size="2"> ②本表于每年4月、7月、10月、次年1月的5日前汇总上报至镇级单位。</font>
				</td>
			</tr>
			<tr>  
				<td colspan="5" class="text-left">
					<font size="2">填报人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;审核人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填报单位(印章)：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</font>
				</td>
			</tr>
			<tr>  
				<td colspan="5" class="text-left">
					<font size="2">填报日期：${date }</font>
				</td>
			</tr>	
		</tbody>
	</table>
</body>
</html>