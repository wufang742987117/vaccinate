<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
	<head>
		<meta charset="UTF-8">
		<title>国家免疫规划疫苗常规接种情况报表6-1</title>
		<style>
			body{margin:0;padding:0;background: rgba(111, 186, 197, 0.55);}
			table{font-size:14px!important;}
			table th , table td{text-align:center;}
			.table-ctn{border:solid 1px black;border-collapse:collapse;width:100%;}
			.table-ctn th{padding:4px 0;}
			.table-ctn p{margin:6px 0;}
			.v-t{vertical-align: top;}
			.w-min{width:48px;min-width:48px;}
			.pl70{padding-left:70px!important;}
			.title{font-size:18px;font-weight:bold;}
			.third-table{ border-collapse: collapse; border: 1px solid #000;}
			.third-table td{ text-align: center;}
			.t-l{ text-align: left!important;}
			.none{
				display:none;
			}
		</style>
		<script src="${ctxStatic}/js/LodopFuncs.js"></script>
		<script type="text/javascript">
			var LODOP; //声明为全局变量
			function SaveAsExcel(){  
		       try{	
					LODOP = getLodop();
				} catch(err) {
					layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
				};
				if (LODOP == null || LODOP ==''){
					return;
				}
		       LODOP.PRINT_INIT(""); 
		       LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
                  LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
			   LODOP.ADD_PRINT_TABLE("10mm","10mm","98%","100%",$("#tablediv").html()); 
		       //LODOP.SET_SAVE_MODE("QUICK_SAVE",true);//快速生成（无表格样式,数据量较大时或许用到）
		       LODOP.SAVE_TO_FILE(${yearstr}+"年"+ ${monthstr} + "月" + "国家免疫规划疫苗常规接种情况表6-1.xlsx");  
			};
			function saveMonitorInfo(){
				$("#tablediv").addClass("none");
				$("#monitordiv").removeClass("none");
				try{	
					LODOP = getLodop();
				} catch(err) {
					layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
				};
				if (LODOP == null || LODOP ==''){
					return;
				}   
			    LODOP.PRINT_INIT(""); 
			    LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
				LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
				LODOP.ADD_PRINT_TABLE("10mm","10mm","98%","100%",$("#monitordiv").html()); 
			    LODOP.SAVE_TO_FILE(${yearstr}+"年"+ ${monthstr} + "月" + "国家免疫规划疫苗常规接种情况表6-1.xlsx");  
			};
			$(document).ready(function(){
				//为年份赋值
				for(var i=2008;i<2051 ;i++){
					$("#yearstr").append("<option value="+i+">"+i+"</option>");//添加option
				}
				$("#yearstr").val(${yearstr});
				//为月份赋值   
				for(var i=1;i<=12 ;i++){
					$("#monthstr").append("<option value="+i+">"+i+"</option>");//添加option
				}
				$("#monthstr").val(${monthstr});
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
			function tongji(){
				$("#tablediv").removeClass("none");
				$("#monitordiv").addClass("none");
			}
			function print1(){
			//$("#tablediv").addClass("none");
				$("#tablediv").removeClass("none");
				$("#monitordiv").addClass("none");
				//$("#searchForm").css("display","none");
				window.print();
			}
			/* function showMonitorInfo(){
				$("#tablediv").addClass("none");
				$("#monitordiv").removeClass("none");
			} */
			function upload(){
				var load_idx = layer.load();
				var data = new Array();
				data.push('${officeCode}');
				data.push('${yearstr}'+pad('${monthstr}',2));
				console.info("上报6-1：",data);
				$.ajax({
					url:"${ctx}/export/export/upload/uploadRoutineImmuReport",
					data:{"data":data}, 
					type:"POST",
					traditional: true,
					success:function(data){
						layer.close(load_idx);
						layer.msg("上报数据:" + data);
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						layer.close(load_idx);
						layer.msg("上报失败："+XMLHttpRequest.status+textStatus,{"icon":2});	
					}
				});
			}
		</script>
	</head>
	<body>
		<div class="ibox-content">
			<form id="searchForm" action="${ctx}/export/export/nationalIPVRoutineVaccReport6_1" method="post">
			<div class="input-group"  >
				<table style="margin-top:20px;">
					<tr>
						<td>
							<select id="yearstr"  name="yearstr" class="form-control"/></select>
						</td>
						<td>&nbsp;&nbsp;年&nbsp;&nbsp;</td>
						<td>
							<select id="monthstr" name="monthstr" class="form-control"></select>
						</td>
				 		<td>&nbsp;&nbsp;月&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;显示类型&nbsp;&nbsp;</td>
						<td>
							<select id="showType" name="showType" class="form-control"/></select>
						</td>
						<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
						<td>&nbsp;&nbsp;居住类型&nbsp;&nbsp;</td>
						<td>
							<select id="liveProp" name="liveProp" class="form-control"/></select>
						</td>
			            <td>
			            	<button id="btnSubmit" style="margin-left:50px; margin-top:0px;" class="btn btn-primary" type="submit" onclick="tongji()">统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
			            </td>
			          <!-- <td>
			            	<button id="printBtn" style="margin-left:10px;margin-top:0px;" class="btn btn-primary" onclick="print1()">预览</button> &nbsp;&nbsp;&nbsp;&nbsp;
			            </td> -->
			            <td>
			            	<a class="btn btn-primary" style="margin-top:0px;" href="javascript:upload()">上报</a>&nbsp;&nbsp;&nbsp;&nbsp;
			            </td>
			            <td>
			            	<button id="printBtn" style="margin-top:0px;" class="btn btn-primary" onclick="print1()">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
			            </td>
			            <!-- <td>
			            	<span  style="margin-top:0px;" class="btn btn-primary" onclick="showMonitorInfo()">预览监测</span> &nbsp;&nbsp;&nbsp;&nbsp;
			            </td> -->
			            <td>
			            	<span id="exportBt"  style="margin-top:0px;"class="btn btn-primary" onclick="saveMonitorInfo()">导出监测</span>&nbsp;&nbsp;&nbsp;&nbsp;
			            </td>
			            <td>
			            	<button id="exportBt"  style="margin-top:0px;"class="btn btn-primary" onclick="SaveAsExcel()"  >导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
			            </td>
			            <td>
			            	<a class="btn btn-default" style="margin-top:0px;" href="javascript:goback()">返回</a>
			            </td>
					</tr>
				</table>
			</div>
			</form>
		</div>
		<div id="tablediv" class="tablediv" style="overflow-x:auto;overflow-y:auto;margin:20px 20px 50px 20px;text-align:center;">
			<c:forEach items="${returnlist}" var="maps">
				<p class="title">${yearstr}&nbsp;年&nbsp;${monthstr}&nbsp;月&nbsp;国家免疫规划疫苗常规接种情况报表(表6-1)
					<c:choose>
						<c:when test="${liveProp==0}">合计</c:when>
						<c:when test="${liveProp==2}">流动</c:when>
						<c:when test="${liveProp==3}">临时</c:when>
						<c:otherwise>本地</c:otherwise>
					</c:choose>
				</p>
				<p class="title-tips">(省、地区、县及乡级单位汇总、上报使用)</p>
				<table border="1" class="table-ctn">
					<thead>
						<tr>
							<th colspan="2">表${returnlist.indexOf(maps)+1}&nbsp;/&nbsp;共${returnlist.size()}张</th>
							<th colspan="69" class="text-left pl70">${sheng}&emsp;${shi}&emsp;${qu}&emsp;${off}</th>
						</tr>
						<tr>
							<th rowspan="3" style="width:114px;min-width:114px;">报告单位</th>
							<th rowspan="3" style="width:90px;min-width:90px;">国标编码(县)</th>
							<th colspan="34">本月（次）应种剂次数</th>
							<th colspan="35">本月（次）实种剂次数</th>
						</tr>
						<tr>
							<th colspan="3">HepB</th>
							<th rowspan="2" class="v-t">BCG</th>
							<th colspan="4">PV</th>
							<th colspan="4">DTP</th>
							<th rowspan="2" class="v-t">DT</th>
							<th colspan="2">MR</th>
							<th colspan="2">MMR</th>
							<th colspan="2">MM</th>
							<th colspan="2">MV</th>
							<th colspan="2">MenA</th>
							<th colspan="2">MenAC</th>
							<th colspan="2">JE-l</th>
							<th colspan="4">JE-i</th>
							<th rowspan="2" class="v-t">HepA-l</th>
							<th colspan="2">HepA-i</th>
							<th colspan="4">HepB</th>
							<th rowspan="2" class="v-t">BCG</th>
							<th colspan="4">PV</th>
							<th colspan="4">DTP</th>
							<th rowspan="2" class="v-t">DT</th>
							<th colspan="2">MR</th>
							<th colspan="2">MMR</th>
							<th colspan="2">MM</th>
							<th colspan="2">MV</th>
							<th colspan="2">MenA</th>
							<th colspan="2">MenAC</th>
							<th colspan="2">JE-l</th>
							<th colspan="4">JE-i</th>
							<th rowspan="2" class="v-t">HepA-l</th>
							<th colspan="2">HepA-i</th>
						</tr>
						<tr>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>及时</th>
							<th>2</th>
							<th>3</th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>1</th>
							<th>2</th>
							<th>3</th>
							<th>4</th>
							<th>1</th>
							<th>2</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${maps}" var="map">
							<tr>
								<td style="line-height:28px;">${map.UNIT_NAME}</td>
								<td>${map.UNIT_CODE}</td>
								<td class="w-min">${map.HEPB_A_RE}</td>
								<td class="w-min">${map.HEPB_B_RE}</td>
								<td class="w-min">${map.HEPB_C_RE}</td>
								<td class="w-min">${map.BCG_RE}</td>
								<td class="w-min">${map.PV_A_RE}</td>
								<td class="w-min">${map.PV_B_RE}</td>
								<td class="w-min">${map.PV_C_RE}</td>
								<td class="w-min">${map.PV_D_RE}</td>
								<td class="w-min">${map.DTP_A_RE}</td>
								<td class="w-min">${map.DTP_B_RE}</td>
								<td class="w-min">${map.DTP_C_RE}</td>
								<td class="w-min">${map.DTP_D_RE}</td>
								<td class="w-min">${map.DT_RE}</td>
								<td class="w-min">${map.MR_A_RE}</td>
								<td class="w-min">${map.MR_B_RE}</td>
								<td class="w-min">${map.MMR_A_RE}</td>
								<td class="w-min">${map.MMR_B_RE}</td>
								<td class="w-min">${map.MM_A_RE}</td>
								<td class="w-min">${map.MM_B_RE}</td>
								<td class="w-min">${map.MV_A_RE}</td>
								<td class="w-min">${map.MV_B_RE}</td>
								<td class="w-min">${map.MENA_A_RE}</td>
								<td class="w-min">${map.MENA_B_RE}</td>
								<td class="w-min">${map.MENAC_A_RE}</td>
								<td class="w-min">${map.MENAC_B_RE}</td>
								<td class="w-min">${map.JEL_A_RE}</td>
								<td class="w-min">${map.JEL_B_RE}</td>
								<td class="w-min">${map.JEI_A_RE}</td>
								<td class="w-min">${map.JEI_B_RE}</td>
								<td class="w-min">${map.JEI_C_RE}</td>
								<td class="w-min">${map.JEI_D_RE}</td>
								<td class="w-min">${map.HEPAL_RE}</td>
								<td class="w-min">${map.HEPAI_A_RE}</td>
								<td class="w-min">${map.HEPAI_B_RE}</td>
								<td class="w-min">${map.HEPB_A_RE}</td>
								<td class="w-min">${map.HEPB_AA_RE}</td>
								<td class="w-min">${map.HEPB_B_RE}</td>
								<td class="w-min">${map.HEPB_C_RE}</td>
								<td class="w-min">${map.BCG_RE}</td>
								<td class="w-min">${map.PV_A_RE}</td>
								<td class="w-min">${map.PV_B_RE}</td>
								<td class="w-min">${map.PV_C_RE}</td>
								<td class="w-min">${map.PV_D_RE}</td>
								<td class="w-min">${map.DTP_A_RE}</td>
								<td class="w-min">${map.DTP_B_RE}</td>
								<td class="w-min">${map.DTP_C_RE}</td>
								<td class="w-min">${map.DTP_D_RE}</td>
								<td class="w-min">${map.DT_RE}</td>
								<td class="w-min">${map.MR_A_RE}</td>
								<td class="w-min">${map.MR_B_RE}</td>
								<td class="w-min">${map.MMR_A_RE}</td>
								<td class="w-min">${map.MMR_B_RE}</td>
								<td class="w-min">${map.MM_A_RE}</td>
								<td class="w-min">${map.MM_B_RE}</td>
								<td class="w-min">${map.MV_A_RE}</td>
								<td class="w-min">${map.MV_B_RE}</td>
								<td class="w-min">${map.MENA_A_RE}</td>
								<td class="w-min">${map.MENA_B_RE}</td>
								<td class="w-min">${map.MENAC_A_RE}</td>
								<td class="w-min">${map.MENAC_B_RE}</td>
								<td class="w-min">${map.JEL_A_RE}</td>
								<td class="w-min">${map.JEL_B_RE}</td>
								<td class="w-min">${map.JEI_A_RE}</td>
								<td class="w-min">${map.JEI_B_RE}</td>
								<td class="w-min">${map.JEI_C_RE}</td>
								<td class="w-min">${map.JEI_D_RE}</td>
								<td class="w-min">${map.HEPAL_RE}</td>
								<td class="w-min">${map.HEPAI_A_RE}</td>
								<td class="w-min">${map.HEPAI_B_RE}</td>
							</tr>
						</c:forEach>
						<tr>  
							<td colspan="71" class="text-left">
								<p>填写说明：乡级防保组织每月5日前汇总上报县级疾病预防控制机构，县级疾病预防控制机构每月10日前录入上报中国免疫规划监测信息管理系统。</p>
								<p>填报日期：${date}&emsp;&emsp;填报单位(印章)：${off}&emsp;&emsp;填报人：${username}</p>
							</td>
						</tr>
					</tbody>
				</table>
			</c:forEach>
		</div>
		<div id="monitordiv" class="monitordiv none" style="margin-left: 10px; margin-bottom: 10px;">
			<table class="third-table" border="1">
				<tr>
					<th colspan="2">报告年:${yearstr}</th>
					<th colspan="2">报告月:${monthstr}</th>
					<th colspan="4">单位名称:${officeName}</th>
					<th colspan="4">单位编码:${officeCode}</th>
				</tr>
				<tr>
					<td colspan="4" rowspan="2">疫苗</td>
					<td colspan="4">本地</td>
					<td colspan="4">流动</td>
				</tr>
				<tr>
					<td colspan="2">应种剂次数</td>
					<td colspan="2">实种剂次数</td>
					<td colspan="2">应种剂次数</td>
					<td colspan="2">实种剂次数</td>
				</tr>
				
				<c:forEach items="${existDataList}" var="existDataList">
				<tr>
					<td colspan="2" rowspan="4" class="t-l">乙肝疫苗</td>
					<td colspan="2">1</td>
					<td colspan="2">${existDataList.HEPB_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.HEPB_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">1(及时)</td>
					<td colspan="2">${existDataList.HEPB_AA_SH_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_AA_RE_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_AA_SH_FLOW}</td>
					<td colspan="2">${existDataList.HEPB_AA_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">2</td>
					<td colspan="2">${existDataList.HEPB_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.HEPB_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">3</td>
					<td colspan="2">${existDataList.HEPB_C_SH_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_C_RE_LOCAL}</td>
					<td colspan="2">${existDataList.HEPB_C_SH_FLOW}</td>
					<td colspan="2">${existDataList.HEPB_C_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="4" class="t-l">卡介苗</td>
					<td colspan="2">${existDataList.BCG_SH_LOCAL}</td>
					<td colspan="2">${existDataList.BCG_RE_LOCAL}</td>
					<td colspan="2">${existDataList.BCG_SH_FLOW}</td>
					<td colspan="2">${existDataList.BCG_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2" rowspan="4" class="t-l">脊灰疫苗</td>
					<td colspan="2">1</td>
					<td colspan="2">${existDataList.PV_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.PV_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.PV_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.PV_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">2</td>
					<td colspan="2">${existDataList.PV_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.PV_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.PV_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.PV_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">3</td>
					<td colspan="2">${existDataList.PV_C_SH_LOCAL}</td>
					<td colspan="2">${existDataList.PV_C_RE_LOCAL}</td>
					<td colspan="2">${existDataList.PV_C_SH_FLOW}</td>
					<td colspan="2">${existDataList.PV_C_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">4</td>
					<td colspan="2">${existDataList.PV_D_SH_LOCAL}</td>
					<td colspan="2">${existDataList.PV_D_RE_LOCAL}</td>
					<td colspan="2">${existDataList.PV_D_SH_FLOW}</td>
					<td colspan="2">${existDataList.PV_D_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2" rowspan="4" class="t-l">百白破疫苗</td>
					<td colspan="2">1</td>
					<td colspan="2">${existDataList.DTP_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.DTP_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">2</td>
					<td colspan="2">${existDataList.DTP_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.DTP_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">3</td>
					<td colspan="2">${existDataList.DTP_C_SH_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_C_RE_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_C_SH_FLOW}</td>
					<td colspan="2">${existDataList.DTP_C_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="2">4</td>
					<td colspan="2">${existDataList.DTP_D_SH_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_D_RE_LOCAL}</td>
					<td colspan="2">${existDataList.DTP_D_SH_FLOW}</td>
					<td colspan="2">${existDataList.DTP_D_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="4" class="t-l">白破疫苗</td>
					<td colspan="2">${existDataList.DT_SH_LOCAL}</td>
					<td colspan="2">${existDataList.DT_RE_LOCAL}</td>
					<td colspan="2">${existDataList.DT_SH_FLOW}</td>
					<td colspan="2">${existDataList.DT_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="2" class="t-l">麻风疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.MR_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MR_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MR_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.MR_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.MR_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MR_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MR_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.MR_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="2" class="t-l">麻腮风疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.MMR_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MMR_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MMR_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.MMR_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.MMR_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MMR_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MMR_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.MMR_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="2" class="t-l">麻疹疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.MV_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MV_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MV_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.MV_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.MV_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MV_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MV_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.MV_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="2" class="t-l">A+C群流脑结合疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.MENA_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MENA_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MENA_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.MENA_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.MENA_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MENA_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MENA_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.MENA_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="2" class="t-l">A+C群流脑多糖疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.MENAC_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MENAC_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MENAC_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.MENAC_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.MENAC_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.MENAC_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.MENAC_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.MENAC_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="2" class="t-l">乙脑减毒活疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.JEL_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.JEL_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.JEL_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.JEL_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.JEL_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.JEL_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.JEL_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.JEL_B_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="4" class="t-l">乙脑灭活疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.JEI_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_A_SH_FLOW}</td> 
					<td colspan="2">${existDataList.JEI_A_RE_FLOW}</td> 
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.JEI_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_B_SH_FLOW}</td> 
					<td colspan="2">${existDataList.JEI_B_RE_FLOW}</td> 
				</tr>
				<tr>
					<td>3</td>
					<td colspan="2">${existDataList.JEI_C_SH_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_C_RE_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_C_SH_FLOW}</td> 
					<td colspan="2">${existDataList.JEI_C_RE_FLOW}</td> 
				</tr>
				<tr>
					<td>4</td>
					<td colspan="2">${existDataList.JEI_D_SH_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_D_RE_LOCAL}</td>
					<td colspan="2">${existDataList.JEI_D_SH_FLOW}</td> 
					<td colspan="2">${existDataList.JEI_D_RE_FLOW}</td> 
				</tr>
				<tr>
					<td colspan="4" class="t-l">甲肝减毒活疫苗</td>
					<td colspan="2">${existDataList.HEPAL_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.HEPAL_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.HEPAL_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.HEPAL_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td colspan="3" rowspan="2" class="t-l">甲肝灭活疫苗</td>
					<td>1</td>
					<td colspan="2">${existDataList.HEPAI_A_SH_LOCAL}</td>
					<td colspan="2">${existDataList.HEPAI_A_RE_LOCAL}</td>
					<td colspan="2">${existDataList.HEPAI_A_SH_FLOW}</td>
					<td colspan="2">${existDataList.HEPAI_A_RE_FLOW}</td>
				</tr>
				<tr>
					<td>2</td>
					<td colspan="2">${existDataList.HEPAI_B_SH_LOCAL}</td>
					<td colspan="2">${existDataList.HEPAI_B_RE_LOCAL}</td>
					<td colspan="2">${existDataList.HEPAI_B_SH_FLOW}</td>
					<td colspan="2">${existDataList.HEPAI_B_RE_FLOW}</td>
				</tr>
				</c:forEach>
				
			</table>
		</div>
	</body>
</html>