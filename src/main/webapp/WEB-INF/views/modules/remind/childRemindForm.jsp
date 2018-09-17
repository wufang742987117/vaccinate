<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<html>
<head>
<title>排号</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/plugins/datepicker/jquery-ui.min.css" rel="stylesheet" />
<style type="text/css">
#nextDatePicker {
	
}

.nextVaccTable {
	width: 100%;
}

.ui-state-highlight, .ui-widget-content .ui-state-highlight,
	.ui-widget-header .ui-state-highlight {
	border: 1px solid #03bbdc;
	font-weight: bold;
}

.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active,
	a.ui-button:active, .ui-button:active, .ui-button.ui-state-active:hover
	{
	border: 1px solid #1cb394;
	background: #1cb394;
	font-weight: bold;
	color: #fff;
}

td.highlight {
	border: none !important;
	padding: 1px 0 1px 1px !important;
	background: none !important;
	overflow: hidden;
}

td.highlight a {
	background: rgb(91, 195, 167) !important;
	border: 1px #ffffff solid !important;
	border-radius: 8px !important;
	color: #000 !important;
}

.ui-datepicker {
	width: 30em !important;
	padding: .2em .2em 0;
	font-size: 14px;
	margin: 0 auto;
}

.nextVaccDiv {
	width: 90%;
/* 	margin: 0 1%; */
	margin: 0 5% 10px 5%;
	background: rgba(255, 255, 255, 0.3);
}

.nextVaccDiv .title {
	text-align: center;
	font-weight: bold;
	line-height: 30px;
	margin: 0;
	background: #ccc;
	border-radius: 5px;
	border: #999 solid 1px;
	cursor: pointer;
}

.nextVaccDiv ul {
	list-style: none;
	margin: 2px;
	padding: 5px;
}

.nextVaccDiv ul li {
	margin: 0;
	padding: 2px 0 2px 24px;
	border-bottom: #fff 1px solid;
	position: relative;
	font-size: 16px;
	cursor: pointer;
}

.nextVaccDiv .selected i {
	display: block;
	height: 18px;
	width: 18px;
	position: absolute;
	left: 4px;
	top: 4px;
	background: url("${ctxStatic}/img/fx2.png") 0 0 no-repeat;
	background-size: cover;
}

.nextVaccDiv .selected {
	background: rgba(175, 217, 223, 0.7);
	font-weight: bold;
}

#nextTimeInput {
	width: 100px;
}

#nextVaccInput {
	width: 220px;
}

.inputNull {
	background: rgba(255, 255, 255, 0);
	border: none;
	margin: 10px 0 10px 0;
	font-size: 18px;
	width: 100px;
}

.text-circle {
	display: inline-block;
	margin: 0 auto;
	width: 15px;
	height: 18px;
	border: 1px #000 solid;
	border-radius: 15px;
	font-size: .8em;
	text-align: center;
}

.remindList table {
	position: relative;
	top: -20px;
	left: 96px;
}

.remindList table td {
	text-align: center;
}

.remindList ul li {
	font-size: 16px;
	line-height: 26px;
}

.short-age {
	list-style: none;
	padding: 0;
	margin: 4px 10px 0 10px;
}

.short-age li {
	display: inline-block;
	margin: 4px 4px 0 4px;
}

.national span {
	background: #fac1c7 !important;
}

.weekday a {
	background: #bcc7ff !important;
}

.datapicker-tips {
	position: absolute;
	top: 24px;
	left: 12px;
	display: block;
}

.sub-li {
	background: #fff;
}
.ui-datepicker {
    width: 15em !important;
    padding: .2em .2em 0;
    font-size: 14px;
    /*margin: 0 auto;*/
	margin-left: 0px;
	margin-top: 0px;
}
#nextDatePicker{
	position: relative;
}
.choose-time{
	position: absolute;
    left: 15em;
    top: 0;
}
.choose-time ul {
	padding: 0 0 0 10px;
}
.choose-time ul li {
	list-style:none;
}
.choose-time ul li label{
	position: relative;
}
.choose-time ul li label input{
	position:absolute;
	top: 1px;
}
.choose-time ul li label span{
	padding-left: 22px;
	font-size:16px;
}
.pepNum{
	font-size: 13px;
	margin-left: 10px;
}
.process{
	width: 107px;
	height:2px;
	background-color: gray;
	margin-left: 20px;
}
.process span{
	display:block;
	width: 0;
	height: 2px;
	background-color: #1bb394;
	content: '';
}
.choose,.choosetime{
			display: inline-block;
			background:url("${ctxStatic}/img/choose.png");
			background-repeat: no-repeat;
			height: 17px;
			width: 18px;
			vertical-align: middle;
			position: absolute;
			top: 1px;
		}
.choosed{
			background: url("${ctxStatic}/img/choosed.png");
			background-repeat: no-repeat;
			height: 20px;
		}
.choose-time ul li label .choose{
	padding-left: 0px;
}
.day{
	    color: red;
    position: absolute;
    top: -23px;
    left: 18em;
    font-weight: 600;
    font-size: 15px;
}
.short-age{
	width: 50%;
}
</style>

<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<script src="${ctxStatic}/js/printRemindTips.js?v=20180224"></script>
<script >
	var baseinfo = JSON.parse('${baseinfo}');
	var holidays = JSON.parse('${holidays}');
	var weekdays = JSON.parse('${weekdays}');
	var nid = '${nid}';
	var receiptType = '${receiptType}';
	var nextTime = '${nextTime}';
</script>
<script src="${ctxStatic}/js/childRemind.js?v=10" ></script>
</head>
<body>

	<div class="ibox">
		
		<div class="ibox-content">
			<form:form id="inputForm" action="" class="form-horizontal">
				<sys:message content="${message}" />
			
				<!-- 下次接种时间 -->
				<label class="col-sm-10 control-label text-left nextEl" style="font-size: 16px;">预约下一针时间：<span id="mouthAge" style="color: red; font-weight: bold;">（）</span></label>
				<div class="col-sm-12"></div>
				<div class="next form-group">
		 			<div id="nextTime" class="form-group col-sm-6">
		 				<div id="nextDatePicker" class="nextEl"></div>
		 				<div class="nextEl">
		 					<ul class="short-age">
		 						
		 					</ul>
		 				</div>
		 				<div class="">
		 					<label class="ft16" for="nextTimeInput" style="display: none" >下一针时间：</label>
		 					<input id="nextTimeInput" class="inputNull" name="nextTime" type="hidden"/>
		 					<input id="nextVaccInput" class="inputNull" name="nextVacc" type="hidden"/>
		 					<input id="nextVaccGroup" class="inputNull" name="nextVaccGroup" type="hidden"/>
		 					<!-- <div class="remindList">
		 						
		 					</div> -->
								<div class="form-group btn-queue mt20">
									<div class="col-sm-10 ">
										<button id="btnRemind" class="btn btn-primary w100" type="button" style="margin-left: 14px;"  data-stat="list" >预约</button>
															
									</div>
								</div>
		 				</div>
		 			</div>
					<div id="nextVacc" class="form-group col-sm-6 nextEl">
		 				<div class="nextVaccDiv fl clearfix">
		 					<p class="title">一类苗</p>
		 					<ul id="sa1" data-role="1">
			 				</ul>
		 				</div>
		 				<div class="nextVaccDiv fl clearfix">
		 					<p class="title">二类苗</p>
		 					<ul id="sa2" data-role="2">
		 					</ul>
		 				</div>
		 			</div>
				</div>
			</form:form>
 			
	</div>
	<script type="text/javascript" src="${ctxStatic}/plugins/datepicker/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/plugins/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
</body>
</html>