<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>工作日时段配置</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/css/worktime.css">
	<link rel="stylesheet" href="${ctxStatic}/css/timePicker.css">
	<script src="${ctxStatic}/js/fangwu/common.js"></script>
	<script src="${ctxStatic}/js/worktime/worktime.js"></script>
	<script src="${ctxStatic}/js/fangwu/template.js"></script>
	<script src="${ctxStatic}/js/fangwu/jquery-timepicker.js"></script>
</head>
	<style>
		
	</style>
<body>

	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/product/bsManageProduct/">疫苗产品配置</a></li>
		<li ><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li class="active"><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	
	<div class="col-xs-1 pull-right mt13">
		<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
	</div>
	
	<div id="content"></div>
	
	<script id="modInform" type="text/html">
		{{each data as value i}}
			<div class="mod-form">
				<div class="mod-dayName">
					<span>{{value.dayName}}</span>
				</div>
				<button data-type={{i}} data-dayName={{value.dayName}} class="addbtn btn btn-primary">添加</button>
				{{if value.timeEvent.length!=0}}
				<button data-type={{i}} class="editbtn btn btn-default" >删除</button>
				{{/if}}
				<div class="mod-eventList">
					<ul>
						{{each value.timeEvent as values j}}
							<li><div class="time" data-timeIndex="{{j}}">{{values.timeSlice}}</div><div class="count">{{values.maximum}}人</div><img src="${ctxStatic}/images/remove.png"  class="remove hide" data-index="{{j}}" data-id="{{values.id}}"></li>
						{{/each}}
					</ul>
				</div>
			</div>
		{{/each}}
	</script>
</body>
</html>
