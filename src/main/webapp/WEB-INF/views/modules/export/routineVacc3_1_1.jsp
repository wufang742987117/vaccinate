<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>常规接种（3-1-1）</title>
	<style>
		body{margin:0;padding:0;background: rgba(111, 186, 197, 0.55);}
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
	//为居住属性赋值  本地0  流动1  合计2
	$("#liveProp").append('<option value="1">本地</option>');
	$("#liveProp").append('<option value="2">流动</option>');
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
 	$("#notPrint").css("display","none");
 	window.print();
 }
</script>
</head>
<body>	
	<form:form id="notPrint" action="${ctx}/export/export/routineVacc3_1_1" method="post">
		<div class="input-group"  >
			<table style="margin-top:20px;margin-left:50px;">
				<tr>
					<td>
						<select id="timeyear" name="yearstr" class="form-control"/></select>
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
				</tr>
			</table>
		</div>
		<div class="input-group" style="margin-left:50px; margin-top: 10px;">
			<button id="btnSubmit" style="margin-left:50px; margin-top:10px;" class="btn btn-primary" type="submit" >统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="exportBt"  style="margin-top:10px;margin-left:10px;"class="btn btn-primary" onclick="SaveAsFile('常规接种（3-1-1）')"  >导出</button>
			<button id="saveAsBtn"  style="margin-top:10px; margin-left:10px;"class="btn btn-primary" onclick="javascript:#"  >另存</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button style="margin-left:10px;margin-top:10px;" class="btn btn-primary" onclick="print1()">打印</button>
			<a  style="margin-left:10px;margin-top:10px; " class="btn btn-default" href="javascript:goback();">返回</a>
		</div>
	</form:form>
	<table id="exportTable"  border="1" style="border:solid 1px black;border-collapse:collapse;">
		<thead>
		<tr>
			<th class="title" colspan="8" style="text-align:center"> ${yearstr}&nbsp;年&nbsp;${monthstr}&nbsp;月&nbsp;国家免疫规划疫苗常规预防接种情况报表（表3-1-1）
				<c:choose>
					<c:when test="${liveProp==0}">合计</c:when>
					<c:when test="${liveProp==2}">流动</c:when>
					<c:otherwise>本地</c:otherwise>
				</c:choose>
				<br>
				（接种单位使用）
			</th>
			</tr>
			<tr>
				<td class="subtitle" style="text-align:center;" colspan="8">${sheng}&emsp;${shi}&emsp;${qu}&emsp;相南办&emsp;${off}</td>
			</tr>
			<tr>
				<td class="subtitle" colspan="8"> 去年人口总数&emsp;<u>&emsp;${newAndAll.numAll}&emsp;</u>去年出生人数&emsp;<u>&emsp;${newAndAll.numNew}&emsp;</u>出生率（%）<u>&emsp;${avgAll}&emsp;</u></td>
			</tr>
		</thead>
		<tr>
			<td rowspan="3" colspan="2">疫苗及剂次</td>
			<td colspan="4">本月（次）基础免疫</td>
			<td colspan="2">本月（次）加强免疫</td>
		</tr>
		<tr>
			<td colspan="2">应种人数</td>
			<td colspan="2">受种人数</td>
			<td rowspan="2">应种人数</td>
			<td rowspan="2">受种人数</td>
		</tr>
		<tr>
			<td>小计</td>
			<td>其中＜12月龄</td>
			<td>小计</td>
			<td>其中＜12月龄</td>
		</tr>
		<c:forEach items="${maps}" var ="m">
		 	<c:if test="${m.NAME_NUM=='001'}">
		 		<tr>
					<td>卡介苗</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='002'}">
		 		<tr>
					<td rowspan="3">脊灰（减毒二倍体）</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='003'}">
		 		<tr>
					<td>2</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='004'}">
		 		<tr>
					<td >3</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='005'}">
		 		<tr>
					<td rowspan="3">百白破</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='006'}">
		 		<tr>
					<td>2</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='007'}">
		 		<tr>
					<td >3</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='008'}">
		 		<tr>
					<td >白破</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='009'}">
		 		<tr>
		 			<td rowspan="4">乙肝（酵母）</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='010'}">
		 		<tr>
					<td>及时数</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='011'}">
		 		<tr>
					<td>2</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='012'}">
		 		<tr>
					<td >3</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='013'}">
		 		<tr>
					<td >麻疹</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='014'}">
		 		<tr>
					<td rowspan="2">乙脑</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='015'}">
		 		<tr>
					<td>2</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='016'}">
		 		<tr>
					<td >风疹（二倍体）</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='017'}">
		 		<tr>
					<td >腮腺炎</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		 	<c:if test="${m.NAME_NUM=='018'}">
		 		<tr>
					<td >流脑A</td>
					<td>1</td>
					<td>${m.BS_SH_A }</td>
					<td>${m.BS_SH_B }</td>
					<td>${m.BS_REAL_B }</td>
					<td>${m.BS_REAL_B}</td>
					<td>${m.EN_SH}</td>
					<td>${m.EN_REAL}</td>
				</tr>
		 	</c:if>
		</c:forEach>
		<tr>
			<td colspan="8" style="text-align: left;">
				说明：<br>
				&emsp;&emsp;①接种单位在完成当月（次）常规接种后，在5天内根据接种记录汇总于本表（表3-1-1）并上报乡级单位；<br>
				&emsp;&emsp;②基础免疫的“小计”包括所有儿童，对＜12月龄儿童完成情况另行统计，加强免疫则按规定的免疫程序统计；<br>
				&emsp;&emsp;③风疹疫苗.腮腺炎疫苗.流脑疫苗和乙肝疫苗未纳入国家免疫规划的可不统计；<br>
				&emsp;&emsp;④接种含有统计表中单价疫苗成份的联合疫苗，则统计入该单价疫苗中；<br>
				&emsp;&emsp;⑤本月应种人数包括：按免疫程序要求当月应受种的所有儿童数；<br>
				&emsp;&emsp;⑥全年人口总数.全年出生人数及出生率（%）：仅在每年第1次报表时填写。<br>
			</td>
		</tr>
		<tr>
			<td colspan="8" style="text-align: left;">
				&emsp;&emsp;填报人：&nbsp;${username}&nbsp;&nbsp;&nbsp;审核人：&nbsp;${username}&nbsp;&nbsp;&nbsp;填报单位(印章)：&nbsp;${off}&nbsp;&nbsp;&nbsp;填报日期：&nbsp;${date}&nbsp;
			</td>
		</tr>
	</table>
</html>
