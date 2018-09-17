<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head1.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Document</title>

<style type="text/css">
	.i {
		height: 35px;
		border: 2px #c8dbea solid;
		background: #e2eff1;
		margin-top: 5px;
		font-size: 16px;
		line-height: 35px;
		text-align:center;
		display: block;
	}
	
	.title{
		
	}
	
	.i:hover{
		background: #fff;
		color: #000;
	}
	
	.row {
		margin-top: 20px;
		margin-bottom: 60px;
	}
</style>
</head>
<body>

	<div class="container">
		<div class="row">
		<div class=" col-md-12">
			<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success pull-right" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
		</div>	
		<div class="col-md-12 text-center title">
			<h1 style="margin-top: 0px; margin-bottom: 35px;">报表导出</h1>
		</div>	
		<%-- <input type="hidden" name="yearstr" value="${yearstr}"/>
		<input type="hidden" name="monthstr" value="${monthstr}"/> --%>
		<%-- <a class="col-md-6 i" href="${ctx}/export/export/routineVacc3_1_1">
			<span >常规接种（3-1-1）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc3_1_2?yearstr=${yearstr}&monthstr=${monthstr}">
			<span >常规接种（3-1-2）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc3_1_2_GrandTotal">
			<span >常规接种（3-1-2）累计</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc3_1_3">
			<span >常规接种（3-1-3）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc3_1_3_GrandTotal">
			<span >常规接种（3-1-3）累计</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc3_1_4">
			<span >常规接种（3-1-4）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc3_1_5">
			<span >常规接种（3-1-5）</span>
		</a> --%>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc6_1">
			<span >常规免疫接种情况6-1汇总表</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/routineVacc6_2">
			<span >常规免疫接种情况6-2汇总表</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/nationalIPVRoutineVaccReport6_1">
			<span >国家免疫规划疫苗常规接种情况报表6-1</span>
		</a> 
		<a class="col-md-6 i" href="${ctx}/export/export/typeTwoVaccReport6_2">
			<span >第二类疫苗接种情况报表6-2</span>
		</a>
		
		<a class="col-md-6 i" href="${ctx}/export/export/Exp_vacc_7_2">
			<span >第二类疫苗进销存报表7-2</span>
		</a>
		
		<%-- <a class="col-md-6 i" href="${ctx}/export/export/withinPlanVaccUse">
			<span >计划免疫疫苗使用情况报表</span>
		</a> --%>
		<%-- <a class="col-md-6 i" href="${ctx}/export/export/tableOneAROnCPImmunizationBasicData">
			<span >表一（儿童计划免疫基础资料统计年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableTwoAnnualReportOnPIS">
			<span >表二（计划免疫工作人员统计年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableThreeAnnualReportOnPIS">
			<span >表三（计划免疫服务形式统计年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableFourSAROnBasicImmunization">
			<span >表四（基础免疫接种情况统计年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableFiveAROnEnhancedImmunization">
			<span >表五（加强免疫接种情况统计年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableSixDiseaseMorbidityAndMST">
			<span >表六-1（疾病发病死亡统计表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableSix_2_SummaryOfDMS">
			<span >表六-2（疾病发病死亡统计汇总表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableSixBISSYearReport">
			<span >表七（基础免疫接种调查统计年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableEightAnnualReportOTUOfBP">
			<span >表八（生物制品使用情况统计年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableNine_1_AnnualReportOfCCE1">
			<span >表九-1（冷链设备情况年报表（1））</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableNine_2_AnnualReportOfCCE2">
			<span >表九-2（冷链设备情况年报表（2））</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableNine_3_AnnualReportOfCCE3">
			<span >表九-3（冷链设备情况年报表（3））</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableTen_1AnnualReportOnISR1">
			<span >表十-1（免疫成功率监测情况年报表（1））</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableTen_2AnnualReportOnISR2">
			<span >表十-2（免疫成功率监测情况年报表（2））</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableElevenCPAgeGroupDemographics">
			<span >表十一（儿童预防年龄组人口统计表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableTwelveEPIVStatusOfTheReport">
			<span >表十二（扩大免疫疫苗接种情况年报表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/tableThirteenSOTNOInsuredPersons">
			<span >表十三（计免保偿制入保人数统计表）</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/bOPV_IPPVaccWeeklyReport">
			<span >bOPV免疫规划脊灰疫苗接种情况周报表</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/iPV_PAPVaccDayOrWeekReport">
			<span >IPV试点地区脊灰疫苗接种日（周）报表</span>
		</a> --%>
		
		<%-- <a class="col-md-6 i" href="${ctx}/export/export/shortTermResidentCIPVSSummaryReport">
			<span >短期居住儿童免疫规划疫苗接种情况统计汇总报表</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/caseAnalysis">
			<span >个案分析</span>
		</a>--%>
		<%-- <a class="col-md-6 i" href="${ctx}/export/export/gDistributionOfTheCardsChildren">
			<span >建卡儿童地区分布情况</span>
		</a> --%>
		<%-- <a class="col-md-6 i" href="${ctx}/export/export/buildCardToBuildTheStatisticsTable">
			<span >建卡建证统计表</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/inoculationUnitOperation">
			<span >接种单位运转情况</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/vaccOQCCVVardCardStatistics">
			<span >接种门诊季度核查儿童接种卡、证统计</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/fiveSeedlingsFullSituation">
			<span >五苗全程情况</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/numberOfVaccForHBV">
			<span >乙肝疫苗接种人次数统计表</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/monthlyReportOnVaccConsumption">
			<span >疫苗消耗月报表</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/twoCategoriesOfVaccStatistics">
			<span >二类疫苗统计表</span>
		</a>		
		<a class="col-md-6 i" href="${ctx}/export/export/hepatitisBSchedule">
			<span >接种乙肝明细表</span>
		</a>		
		<a class="col-md-6 i" href="${ctx}/export/export/mobileChildrenMonthly">
			<span >流动儿童月报</span>
		</a>		
		<a class="col-md-6 i" href="${ctx}/export/export/immunizationPlanningUsage">
			<span >免疫规划使用情况</span>
		</a>		
		<a class="col-md-6 i" href="${ctx}/export/export/vaccConsumptionDaily">
			<span >疫苗消耗日报</span>
		</a> --%>
		<%-- <a class="col-md-6 i" href="${ctx}/export/export/findChirldBuildCard">
			<span >儿童建卡查询</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/card_in_time">
			<span >建卡及时率</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/record_integrity">
			<span >个案信息完整性统计</span>
		</a>
		<a class="col-md-6 i" href="${ctx}/export/export/inoculationUnitOperation">
			<span >接种考核</span>
		</a> --%>
		</div>
	</div>
</body>
</html>