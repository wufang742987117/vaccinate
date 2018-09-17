<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>常规接种（3-1-5）</title>
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
		#exportTable .text-left{text-align: left !important;}
		#exportTable .text-right{text-align: left !important;}
		#exportTable .tips{line-height: 16px; font-size: 14px;}
	</style>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//为年份赋值
	for(var i=2008;i<2051 ;i++){
		$("#timeyear").append("<option value="+i+">"+i+"</option>");//添加option
	}
	$("#timeyear").val(${yearstr});
	//为月份赋值
	for(var i=1;i<=12 ;i++){
		$("#timemonth").append("<option value="+i+">"+i+"</option>");//添加option
	}
	$("#timemonth").val(${monthstr});
	//为居住属性赋值  本地1  流动2  合计0  临时3
	$("#liveProp").append('<option value="1">本地</option>');
	$("#liveProp").append('<option value="2">流动</option>');
	$("#liveProp").append('<option value="3">临时</option>');
	$("#liveProp").append('<option value="0">合计</option>');
	$("#liveProp").val(${liveProp});
	//为显示类型赋值  按居委名称0  按常规修订1   按特殊修订2 
	$("#showType").append('<option value="0">按居委名称</option>');
	$("#showType").append('<option value="1">按常规修订</option>');
	$("#showType").append('<option value="2">按特殊修订</option>');
	$("#showType").val(${showType});
});
function goback(){
	window.location.href="${ctx}/export/export/exportlist";
}
function print1(){
	$("#searchForm").css("display","none");
	window.print();
}
</script>
</head>
<body>
	<form:form id="searchForm" action="${ctx}/export/export/routineVacc3_1_5" method="post">
		<div class="input-group"  >
			<table style="margin-top:20px;margin-left:50px;">
				<tr>
					<td>
						<select id="timeyear"  name="yearstr" class="form-control"/></select>
					</td>
					<td>&nbsp;&nbsp;年&nbsp;&nbsp;</td>
					<td>
						<select id="timemonth" name="monthstr" class="form-control"></select>
					</td>
					<td>&nbsp;&nbsp;月&nbsp;&nbsp;</td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>居住属性&nbsp;&nbsp;</td>
					<td>
						<select id="liveProp" name="liveProp" class="form-control"/></select>
					</td>
					<td>&nbsp;&nbsp;显示类型&nbsp;&nbsp;</td>
					<td>
						<select id="showType" name="showType" class="form-control"/></select>
					</td>
				</tr>
			</table>
		</div>
		<div class="input-group" >
			<button id="btnSubmit" style="margin-left:50px; margin-top:20px;" class="btn btn-primary" type="submit" >统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="exportBt"  style="margin-top:20px;"class="btn btn-primary" onclick="SaveAsFile('常规接种（3-1-5）')"  >导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="saveAsBtn"  style="margin-top:20px;"class="btn btn-primary" onclick="javascript:#"  >另存</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="printBtn" style="margin-top:20px;" class="btn btn-primary" onclick="print1()">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-default" style="margin-top:20px;" href="javascript:goback()">返回</a> 
		</div>
		</form:form>	
	<c:forEach    items="${returnlist}" var="explist">
	<table id="exportTable"  border="1" style="border:solid 1px black;width:1000px;border-collapse:collapse;">
		<thead>
		<tr>
			<th style="text-align:center"class="title" colspan="23">${yearstr}&nbsp;年&nbsp;${monthstr}&nbsp;月&nbsp;省级增加的国家免疫规划疫苗接种情况统计汇总报表（表3-1-5）
				<c:choose>
					<c:when test="${liveProp==0}">合计</c:when>
					<c:when test="${liveProp==2}">流动</c:when>
					<c:when test="${liveProp==3}">临时</c:when>
					<c:otherwise>本地</c:otherwise>
				</c:choose>
				<br>
				（省.市.县及乡级报告单位汇总.上报使用）
			</th>
		</tr>
		<tr>
			<td class="subtitle" colspan="23">${sheng}&emsp;${shi}&emsp;${qu}&emsp;相南办&emsp;${off}</td>
		</tr>
		</thead>
		<tr>
			<td rowspan="4" >报告单位</td>
			<td rowspan="4" >国标编码（县）</td>
			<td rowspan="4" >接种报告情况</td>
			<td colspan="10">当月应种人数</td>
			<td colspan="10">当月受种人数</td>
		</tr>
		<tr>
			<td rowspan="3">Rub-hdc</td>
			<td colspan="4">MenA</td>
			<td rowspan="3">Mun</td>
			<td colspan="4">JE-i</td>
			<td rowspan="3">Rub-hdc</td>
			<td colspan="4">MenA</td>
			<td rowspan="3">Mun</td>
			<td colspan="4">JE-i</td>
		</tr>
		<tr>
			<td colspan="2">基础</td>
			<td colspan="2">加强</td>
			<td colspan="2">基础</td>
			<td colspan="2">加强</td>
			<td colspan="2">基础</td>
			<td colspan="2">加强</td>
			<td colspan="2">基础</td>
			<td colspan="2">加强</td>
		</tr>
		<tr>
			<td>1</td>
			<td>2</td>
			<td>1</td>
			<td>2</td>
			<td>1</td>
			<td>2</td>
			<td>1</td>
			<td>2</td>
			<td>1</td>
			<td>2</td>
			<td>1</td>
			<td>2</td>
			<td>1</td>
			<td>2</td>
			<td>1</td>
			<td>2</td>
		</tr>
		<c:forEach items="${explist}" var="ex">
			<tr>
				<td>${ex.UNIT_NAME}</td>
				<td>${ex.UNIT_CODE}</td>
				<td>${ex.REPORT_STATUS}</td>
				<td>${ex.SH_RUB}</td>
				<td>${ex.SH_MENA_AA}</td>
				<td>${ex.SH_MENA_AB}</td>
				<td>${ex.SH_MENA_BA}</td>
				<td>${ex.SH_MENA_BB}</td>
				<td>${ex.SH_MUN}</td>
				<td>${ex.SH_JEI_AA}</td>
				<td>${ex.SH_JEI_AB}</td>
				<td>${ex.SH_JEI_BA}</td>
				<td>${ex.SH_JEI_BB}</td>
				<td>${ex.RE_RUB}</td>
				<td>${ex.RE_MENA_AA}</td>
				<td>${ex.RE_MENA_AB}</td>
				<td>${ex.RE_MENA_BA}</td>
				<td>${ex.RE_MENA_BB}</td>
				<td>${ex.RE_MUN}</td>
				<td>${ex.RE_JEI_AA}</td>
				<td>${ex.RE_JEI_AB}</td>
				<td>${ex.RE_JEI_BA}</td>
				<td>${ex.RE_JEI_BB}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="23" style="text-align: left;">
			注：根据各地情况，可进行月报或年报。
			<br>
			实际报出日期：&nbsp;${date}&nbsp;&emsp;&emsp;&emsp;单位负责人（签章）：&nbsp;${username}&nbsp;&nbsp;&nbsp;制表人（签章）：&nbsp;${off}&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>
	</c:forEach>
</html>
