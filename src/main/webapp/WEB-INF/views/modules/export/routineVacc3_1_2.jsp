<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>常规接种（3-1-2）</title>
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
	//为居住属性赋值  本地1 流动2  合计0
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
function page(n,s){
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
    return false;
}
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
	<div class="ibox">
	<div class="ibox-content">
		<form:form id="searchForm" action="${ctx}/export/export/routineVacc3_1_2" method="post">
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
						<td>&nbsp;&nbsp;显示类型&nbsp;&nbsp;</td>
						<td>
							<select id="showType" name="showType" class="form-control"/></select>
						</td>
					</tr>
				</table>
			</div>
			<div class="input-group" >
				<button id="btnSubmit" style="margin-left:50px; margin-top:20px;" class="btn btn-primary" type="submit" >统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="exportBt"  style="margin-top:20px;"class="btn btn-primary" onclick="SaveAsFile('常规接种（3-1-2）')"  >导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="saveAsBtn"  style="margin-top:20px;"class="btn btn-primary" onclick="javascript:#"  >另存</button>&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="printBtn" style="margin-top:20px;" class="btn btn-primary" onclick="print1();">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
				<a class="btn btn-default" style="margin-top:20px;" href="javascript:goback()">返回</a> 
			</div>
		</form:form>
		<c:forEach items="${returnlist}" var="explist">
		<table id="exportTable" border="1" style="border:solid 1px black;border-collapse:collapse;width: 1000px;">
			<thead>
				<tr class="title">
					<th colspan="30">
						<p size="3" style="text-align:center;">${yearstr}&nbsp;年&nbsp;${monthstr}&nbsp;月&nbsp;所有儿童国家免疫规划疫苗基础预防接种情况统计汇总报表（表3-1-2）
							<c:choose>
								<c:when test="${liveProp==0}">合计</c:when>
								<c:when test="${liveProp==2}">流动</c:when>
								<c:otherwise>本地</c:otherwise>
							</c:choose>
						</p>
					</th>
				</tr>
				<tr class="subtitle">
					<td colspan="30">
						<font size="2">(省、市、县及乡级单位汇总、上报使用)</font>
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="25" class="text-left">
						<font size="2" style="text-align:center;">${sheng}&emsp;${shi}&emsp;${qu}&emsp;相南办&emsp;${off}</font><br>
						<font size="2">本月（次）应报单位数${totalshequ}&nbsp;，本月（次）实报单位数${count}，报告率%:${avgAll}</font>
					</td>
					<td colspan="5">
						<font size="2">第${returnlist.indexOf(explist)+1}页/共${returnlist.size()}页</font>
					</td>
				</tr>
				<tr class="b">
					<td rowspan=4>报告单位</td>
					<td rowspan=4>国标<br>编码<br>(县)</td>
					<td rowspan=4>接种<br>报告<br>情况</td>
					<td rowspan=4>去年<br>人口<br>总数</td>
					<td rowspan=4>去年<br>出生<br>人数</td>
					<td colspan=12>基础免疫应种人数</td>
					<td colspan=13>基础免疫受种人数</td>
				</tr>
				<tr>
					<td rowspan=3>BCG</td>
					<td colspan=3>OPV-hdc</td>
					<td colspan=3>DPT</td>
					<td rowspan=3>MV</td>
					<td colspan=3>HepB-yst</td>
					<td rowspan=3>JE-i</td>
					<td rowspan=3>BCG</td>
					<td colspan=3>OPV-hdc</td>
					<td colspan=3>DPT</td>
					<td rowspan=3>MV</td>
					<td colspan=4>HepB-yst</td>
					<td rowspan=3>JE-i</td>
				</tr>
				<tr>
					<td rowspan=2>1</td>
					<td rowspan=2>2</td>
					<td rowspan=2>3</td>
					<td rowspan=2>1</td>
					<td rowspan=2>2</td>
					<td rowspan=2>3</td>
					<td rowspan=2>1</td>
					<td rowspan=2>2</td>
					<td rowspan=2>3</td>
					<td rowspan=2>1</td>
					<td rowspan=2>2</td>
					<td rowspan=2>3</td>
					<td rowspan=2>1</td>
					<td rowspan=2>2</td>
					<td rowspan=2>3</td>
					<td colspan=2>1</td>
					<td rowspan=2>2</td>
					<td rowspan=2>3</td>
				</tr>	
				<tr>
					<td>小计</td>
					<td>及时数</td>
				</tr>
				<c:forEach items="${explist}" var="ex">
				<tr>
					<td>${ex.UNIT_NAME}</td>
					<td>${ex.UNIT_CODE}</td>
					<td>${ex.REPORT_STATUS}</td>
					<td>${ex.LYP_NUM}</td>
					<td>${ex.LYP_BT_NUM}</td>
					<td>${ex.SH_BCG}</td>
					<td>${ex.SH_OPV_A}</td>
					<td>${ex.SH_OPV_B}</td>
					<td>${ex.SH_OPV_C}</td>
					<td>${ex.SH_DPT_A}</td>
					<td>${ex.SH_DPT_B}</td>
					<td>${ex.SH_DPT_C}</td>
					<td>${ex.SH_MV}</td>
					<td>${ex.SH_HEPB_A}</td>
					<td>${ex.SH_HEPB_B}</td>
					<td>${ex.SH_HEPB_C}</td>
					<td>${ex.SH_JEI}</td>
					<td>${ex.RE_BCG}</td>
					<td>${ex.RE_OPV_A}</td>
					<td>${ex.RE_OPV_B}</td>
					<td>${ex.RE_OPV_C}</td>
					<td>${ex.RE_DPT_A}</td>
					<td>${ex.RE_DPT_B}</td>
					<td>${ex.RE_DPT_C}</td>
					<td>${ex.RE_MV}</td>
					<td>${ex.RE_HEPB_A}</td>
					<td>${ex.RE_HEPB_B}</td>
					<td>${ex.RE_HEPB_C}</td>
					<td>${ex.RE_JEI}</td>
					<td>${ex.RE_OPV_AA}</td>
				</tr>
				</c:forEach>	
				<tr>  
					<td colspan="30" class="text-left">
						<font size="2">备注：①本表供镇级及乡级以上单位填报；</font>
					</td>
				</tr> 
				<tr>  
					<td colspan="30" class="text-left">
						<font size="2">②应种和受种人数均包括12月龄内和12月龄以上的儿童；</font>
					</td>
				</tr>
				<tr>  
					<td colspan="30" class="text-left">
						<font size="2"> ③国际编码、去年人口总数、去年出生人口数仅在每年第1次报表填写。</font>
					</td>
				</tr>
				<tr>  
					<td colspan="30" class="text-left">
						<font size="2">填报人：&nbsp;${username}&nbsp;&nbsp;&nbsp;审核人：&nbsp;${username}&nbsp;&nbsp;&nbsp;填报单位(印章)：&nbsp;${off}&nbsp;&nbsp;&nbsp;填报日期：&nbsp;${date}&nbsp;</font>
					</td>
				</tr>
			</tbody>
		</table>
		</c:forEach>
	</div>
	</div>
</body>
</html>