<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<html>
<head>
<title>排号</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/plugins/datepicker/jquery-ui.min.css" rel="stylesheet" />
<style type="text/css">
.nextVaccTable {width: 100%; } .ui-state-highlight, .ui-widget-content .ui-state-highlight, .ui-widget-header .ui-state-highlight {border: 1px solid #03bbdc; font-weight: bold; } .ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active, a.ui-button:active, .ui-button:active, .ui-button.ui-state-active:hover {border: 1px solid #1cb394; background: #1cb394; font-weight: bold; color: #fff; } td.highlight {border: none !important; padding: 1px 0 1px 1px !important; background: none !important; overflow: hidden; } td.highlight a {background: rgb(91, 195, 167) !important; border: 1px #ffffff solid !important; border-radius: 8px !important; color: #000 !important; } .ui-datepicker {width: 30em !important; padding: .2em .2em 0; font-size: 14px; margin: 0 auto; } .nextVaccDiv {width: 90%; margin: 0 5% 10px 5%; background: rgba(255, 255, 255, 0.3); } .nextVaccDiv .title {text-align: center; font-weight: bold; line-height: 30px; margin: 0; background: #ccc; border-radius: 5px; border: #999 solid 1px; cursor: pointer; } .nextVaccDiv ul {list-style: none; margin: 2px; padding: 5px; } .nextVaccDiv ul li {margin: 0; padding: 2px 0 2px 24px; border-bottom: #fff 1px solid; position: relative; font-size: 16px; cursor: pointer; } .nextVaccDiv .selected i {display: block; height: 18px; width: 18px; position: absolute; left: 4px; top: 4px; background: url("${ctxStatic}/img/fx2.png") 0 0 no-repeat; background-size: cover; } .nextVaccDiv .selected {background: rgba(175, 217, 223, 0.7); font-weight: bold; } #nextTimeInput {width: 100px; } #nextVaccInput {width: 220px; } .inputNull {background: rgba(255, 255, 255, 0); border: none; margin: 10px 0 10px 0; font-size: 18px; width: 100px; } .text-circle {display: inline-block; margin: 0 auto; width: 15px; height: 18px; border: 1px #000 solid; border-radius: 15px; font-size: .8em; text-align: center; } .nextEl {display: none; } .remindList table {position: relative; top: -20px; left: 96px; } .remindList table td {text-align: center; } .remindList ul li {font-size: 16px; line-height: 26px; } .short-age {list-style: none; padding: 0; margin: 4px 10px 0 10px; } .short-age li {display: inline-block; margin: 4px 4px 0 4px; } .national span {background: #fac1c7 !important; } .weekday a {background: #bcc7ff !important; } .datapicker-tips {position: absolute; top: 24px; left: 12px; display: block; } .ui-datepicker {width: 15em !important; padding: .2em .2em 0; font-size: 14px; /*margin: 0 auto;*/ margin-left: 0px; margin-top: 0px; } #nextDatePicker{position: relative; } .choose-time{position: absolute; left: 15em; top: 0; } .choose-time ul {padding: 0 0 0 10px; } .choose-time ul li {list-style:none; } .choose-time ul li label{position: relative; } .choose-time ul li label input{position:absolute; top: 1px; } .choose-time ul li label span{padding-left: 22px; font-size:16px; } .pepNum{font-size: 13px; margin-left: 10px; } .process{width: 107px; height:2px; background-color: gray; margin-left: 20px; } .process span{display:block; width: 0; height: 2px; background-color: #1bb394; content: ''; } .choose,.choosetime{display: inline-block; background:url("${ctxStatic}/img/choose.png"); background-repeat: no-repeat; height: 17px; width: 18px; vertical-align: middle; position: absolute; top: 1px; } .choosed{background: url("${ctxStatic}/img/choosed.png"); background-repeat: no-repeat; height: 20px; } .choose-time ul li label .choose{padding-left: 0px; } .day{color: red; position: absolute; top: -23px; left: 18em; font-weight: 600; font-size: 15px; }
.short-age{
	width: 50%;
}
.none{
	display: none;
}
#signature{
	margin-left: -16px;
}
</style>

<script type='text/javascript' src='${ctxStatic}/js/signSockect.js?v=20180101'></script>
<script type="text/javascript">
	/* 接种记录中最后一个记录 */
	var lastVaccd;
	var lastVaccdList = new Array();
	var baseinfo = JSON.parse('${baseinfo}');
	var defaultBatchs = JSON.parse('${defaultBatchs}');
	var holidays = JSON.parse('${holidays}');
	var weekdays = JSON.parse('${weekdays}');
	var nid = '${nid}';
	var receiptType = '${receiptType}';
	var nextTime = '${nextTime}';
	$(document).ready( function() {
	
		if(preference.quickOption == 1){
			$("#btnSubmit").val("登记保存");
			$("#btnSubmit").addClass("w100");
			$(".quick-option").show();
		}
	
				$("#inputForm").validate(
					{submitHandler : function(form) {
						
/* 						if(!$(".remindList tbody").html()){
							if(!$("#nextTimeInput").val()){
								layer.msg("请选择下次预约接种的时间",{"icon":7});
								return false;
							}
							if(!$("#nextVaccInput").val() || !$("#nextVaccGroup").val()){
								layer.msg("请选择下次预约接种的疫苗",{"icon":7});
								return false;
							}
						} */
					
						/* if(lastVaccd && lastVaccd.nid.substr(0,2)=='54' && lastVaccd.manufacturercode != $("#batch option[value=" + $("#batch").val() + "]").attr("data-manufacturercode")){
							toastrMsg("所选厂商与上次接种不一致","error");
							return false;
						} */
						loading('正在提交，请稍等...');
						$.ajax({
							url:ctx + "/child_vaccinaterecord/childVaccinaterecord1/stamp",
							data:{
								"id":$("#id").val(),
								"productid":$("#productid").val(),
								"vaccName":$("#vaccName").val(),
								"dosage":$("#dosage").val(),
								"childid":$("#childid").val(),
								"nid":$("#nid").val(),
								"rid":"",
								"batch":$("#batch").val(),
								"vacctype":$("#vacctype").val(),
								"bodypart":$("#bodypart").val(),
								"price":$("#price").val(),
								"insurance":$("#insurance").val(),
								"remindDate" : $("#nextTimeInput").val(),
								"remindVacc" : JSON.stringify(remindList),
								"signList" : $("#signatureInp").val(),
								"roomcode" : localStorage["roomcode"]
							},
							success:function(data){
								if(data.code == '200'){
									//刷新预约记录
									parent.loadChildRecord();
									if(receiptType){
										//打印小票
										if(parent.printtt(data.tips)){
											toastrMsg(data.msg,"success");
											setTimeout(function(){
												parent.closeForm1(data.name.childcode);
											}, 800); 			
										}else{
											parent.warning("排号成功，小票打印失败,请检查本地打印控件是否安装或关闭打印配置");
											parent.layer.close(parent.layer.getFrameIndex(window.name));
										}
									}else{
										toastrMsg(data.msg,"success");
										setTimeout(function(){
											parent.closeForm1(data.name.childcode);
										}, 800); 
									}
								}else{
									toastrMsg(data.msg,"error");
									setTimeout(function(){
										parent.closeForm1(data.name.childcode);
									}, 2000); 
								}
							},
							error:function(){
								toastrMsg("操作失败","error");
							}
						});
// 						form.submit();
						},
					errorContainer : "#messageBox",
					errorPlacement : function(error, element) {
						$("#messageBox").text("输入有误，请先更正。");
						if (element.is(":checkbox")|| element.is(":radio")|| element.parent().is(".input-append")) {
							error.appendTo(element.parent().parent());
						} else {
							error.insertAfter(element);
						}
					}
				});
			$("#vaccName").change(
				//通过疫苗名称询所有的批次
				function batchno() {
					var name = $("#vaccName").val();
					if(!name){
						$("#batch").empty();
						return;
					}
					$.ajax({
						type : "POST",
						url : "${ctx}/product/bsManageProduct/findQueueViewListApi",
						data : {"vaccineid" : name, "storenumIsNull":true, "showAll":'1', "exceptQuene":true},
						dataType : "json",
						success : function(data) {
						var	html="";
						$.each(data, function(idx, item) { //循环对象取值
							html += "<option value='" + item.batchno
							 + "' data-pid='" + item.id 
							 + "' data-price="+ item.sellprice 
							 + " data-appmax = "+ item.applicableMax
							 + " data-appmin = "+ item.applicableMin 
							 + " data-manufacturercode = "+ item.code 
							 + " data-live = "+ item.vaccinate.live
							 + " data-store = "+ item.storenum 
							+">"+ item.batchno+"[" + item.manufacturer + "]" + "</option>";
						});
						$("#batch").html(html);
						for(var i in defaultBatchs){
							if(defaultBatchs[i].vaccId == name){
								$("#batch").val(defaultBatchs[i].batch);
								dfBatch = defaultBatchs[i].batch;
							}
						}
						$("#batch").change(); 
						}
					});
				});
			
			$("#batch").change(function(){
					var mouthAge = parseInt("${mouthAge}");
					$("#productid").val($("#batch > option:selected").attr("data-pid"));
					var price = $("#batch > option:selected").attr("data-price");
					var store = $("#batch > option:selected").attr("data-store");
					store = store?store:0;
					var appmax = parseInt($("#batch > option:selected").attr("data-appmax"));
					var appmin = parseInt($("#batch > option:selected").attr("data-appmin"));
					if(appmax && mouthAge > appmax){
						layer.msg("儿童月龄超过该疫苗适用年龄<br>(" + appmin + " - " + appmax + "个月)",{"icon":2, "time":2500});
					}
					if(appmin && mouthAge < appmin){
						layer.msg("儿童月龄小于该疫苗适用年龄<br>(" + appmin + " - " + appmax + "个月)",{"icon":2,"time":2500});
					}
					if(!price){
						price = 0;
					}
					$("#price").val(price);
					if(!price){
						$("#labelid").html("<span class='label label-deafult'>免费</span>");
					}else{
						$("#labelid").html(price+"元");
					}
					if(store<5){
						$("#labelstore").html("<span style='color:red'>"+store+"支</span>");
					}else{
						$("#labelstore").html(store+"支");
					}
				});
				
				
				/* 加载预约记录 */
				var remindDate = JSON.parse('${remindlist}');
				if(!remindDate.length){
					$(".nextEl").show();
					$("#btnRemindToggle").hide();
				}else{
					var ul = "<label>预约记录：</label><table class='table table-bordered'><thead><tr><th>疫苗</th><th>预约日期</th><th>预约时段</th><th>操作</th></tr></thead><tbody>";
					for(i in remindDate){
						ul += '<tr>'+
								'<td>' + remindDate[i].remindVacc + '</td>'+
								'<td>' + remindDate[i].remindDate + '</td>'+
								'<td>' + (remindDate[i].selectTime?remindDate[i].selectTime:'') + '</td>'+
								'<td><button class="btn btn-xs btn-success mr10" type="button" data-id="' + remindDate[i].id + '" onclick="cancelRemind(this)">取消</button></td>'+
								'</tr>';
					}
					ul += "</tbody></table>";
					$(".remindList").html(ul);
					$("#btnRemindToggle").show();
				}
				
				$("#btnRemindToggle").click(function(){
					if($(this).attr("data-stat") == "list"){
						showDatePicker();
						$(this).html("预约记录");
						$(this).attr("data-stat","picker");
					}else{
						hideDatePicker();
						$(this).html("预约");
						$(this).attr("data-stat","list");
					}				
			});	
		});
		
	
	/* 隐藏时间选择器 */
	function showDatePicker(){
		$(".nextEl").show();
		$(".remindList").hide();
	}
	
	function hideDatePicker(){
		$(".nextEl").hide();
		$(".remindList").show();
	}
	
	function reloadRemind(){
		if(!$(".remindList tbody").html()){
			showDatePicker();
			$("#btnRemindToggle").hide();
		}
	}
	
	function findLastPinByGroup(){
	/* 获取最大针次 */
		$.ajax({
			url:'${ctx}/child_vaccinaterecord/childVaccinaterecord/findLastPinByGroup',
			data:{"group":$("#bigname").val(),"childid":baseinfo.id, "vacctype":$("#vacctype").val()},
			async:false,
			success:function(data){
				if(data){
					$("#dosage").val(data);
					$("#nid").val($("#bigname").val() + data + ($("#vacctype").val()==1?"":$("#vacctype").val()));
				}
			}
		});
	}
	
	$(function(){
		var html = '';
		var count=0;
		for(ag in agelist){
			if(agelist[ag].age <= baseinfo.mouthAgeInt){
				continue;
			}
			count++;
			html += '<li> <button class="btn btn-xs btn-success" type="button" data-offset=' + agelist[ag].age + ' >' + agelist[ag].text + '</button> </li>';
			if(count>5){
				break;
			}
		}
		$(".short-age").html(html);
		
		$(document).on("click", ".short-age button",function(){
			var offset = $(this).attr("data-offset");
			var nextd=new Date(addMouth(new Date(baseinfo.birthday),$(this).attr("data-offset")));
			nextd=getNextworkDay(nextd);
			$('#nextDatePicker').datepicker('setDate',nextd);
			$('#nextDatePicker .ui-state-active').click();
		});
		$(".sign-finish").click(function(){
			var obj = document.getElementById("HWPenSign"); 
	    	var stream = obj.HWGetBase64Stream(2);
		   	if(stream == ''){
		   		layer.msg("请签字后再确定",{'icon':7,'time':1000});
		   		return false;
		   	}
			$("#signatureInp").val(stream);
			success("签字成功");
			res = obj.HWInitialize();
			$("#signature").addClass("none");
			// $("#nextVacc").removeClass("none");
			$("#btnSubmit").show();
			$("#waitSign").hide();
			$(".sign-finish").hide();
		})
        $("#bigname").change(function(){
        	$.ajax({
        		url:'${ctx}/vaccine/bsManageVaccine/findVaccListAbleModel',
        		data:{"group":$(this).val()},
        		async:false,
        		success:function(data){
        			var	html1="";
        			$.each(data, function(idx, item) { //循环对象取值
						html1 += "<option value='" + item.id + "'>"+ item.vaccName + "</option>";
					});
					$("#vaccName").html(html1)	
					$("#vaccName").change();				
        		}
        	});
        	findLastPinByGroup();        	
        });
        $("#bigname").change();
        
        $("#vacctype").change(function(){
        	findLastPinByGroup();
        });
        
        $("#sendSign").click(function(){
			if($("#vaccName").val()){
				SignatureId('D_' + baseinfo.id + '_' + $("#vaccName").val(), $("#vaccName").val());
				signatureStart();
			}else{
				error("未选择疫苗");
			}
        });
	});
	
	/* 签字开始 */
	function signatureStart(){
		console.info("签字开始");
		$("#signature").removeClass("none");
		$("#sendSign").hide();
		$("#btnSubmit").hide();
		$("#waitSign").show();
		$(".sign-finish").show();
		inisign();
	}
	
	/*  签字异常 */
	function signatureError(){
		$("#sendSign").show();
		$("#signature").addClass("none");
		$("#btnSubmit").show();
		$("#waitSign").hide();
		$(".sign-finish").hide();
	}
	
    function SignatureId(id,vaccineid) {
   		signatureStart();
		//发送websocket通知签字程序
		Send(id, '${host}${ftx}/api/disclosure?vaccid=' + vaccineid);
		
    }
    
    /* 安卓签字版 */
   	function SignatureIdApp(id,vaccineid) {
   		signatureStart();
		//通知安卓签字程序
		document.location = "js://signview?id=" + id + "&dis=${host}${ftx}/api/disclosure?vaccid=" + vaccineid;
    }
    
     /* 签字回调 */
    function ifSignatureSuccess(msg){
    	layer.closeAll();
    	console.info("签字记录id-->" + msg);
    	/* do someting */
    	if(msg != ""){
			success("签字成功");
			$("#btnSubmit").show();
			$("#waitSign").hide();
			$(".sign-finish").hide();
			$("#signature").addClass("none");
    	}
    }
    function inisign(){
    	var penwidth = 3;
 		var obj = document.getElementById("HWPenSign"); 
        obj.HWSetBkColor(0xEEEEEE);  
	    obj.HWSetCtlFrame(2, 0xFFFFFF);
	    res = obj.HWInitialize();
    }

    function signComplete(){
    	var obj = document.getElementById("HWPenSign"); 
    	var stream = obj.HWGetBase64Stream(2);
	   	signatureDatas = stream;
	   	if(signatureDatas == ''){
	   		layer.msg("请签字后再确定",{'icon':7,'time':1000});
	   		return false;
	   	}
	   	$("#signatureInp").val(signatureDatas);
	   	success("签字成功");
	   	res = obj.HWInitialize();
		$("#signature").addClass("none");
		// $("#nextVacc").removeClass("none");
		$("#btnSubmit").show();
		$("#waitSign").hide();
		$(".sign-finish").hide();
		//关闭签字版
		obj.HWFinalize();
    }
</script>
</head>
<body>

	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="childVaccinaterecord" action="${ctx}/child_vaccinaterecord/childVaccinaterecord1/stamp"
				method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="productid" />
				<sys:message content="${message}" />
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label text-right">疫苗种类：</label>
		            <div class="col-sm-8">
	                	<select class="form-control valid required" id="bigname" >
							<c:forEach items="${vacclist}" var="vac">
								<option value="${vac.mNum}" >${vac.mName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
				 <div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">接种类型：</label>
					<div class="col-sm-8">
						<form:select path="vacctype" class="form-control ">
							<form:options items="${fns:getDictList('vacctype')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>  
				
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">疫苗名称：</label>
					<div class="col-sm-8">
							<%-- <form:select path="vaccName" class="form-control " >
							<c:forEach items="${productlist}" var="pro">
								<form:option value="${pro.id }" label="${pro.vaccName }" data-live="${pro.vaccinate.live}" htmlEscape="false" />
							</c:forEach>
							<form:options items="${productlist}" itemLabel="vaccName" itemValue="id" data-live="${vaccinate.live }" htmlEscape="false" />
						</form:select> --%>
						<select class="form-control valid required" id="vaccName" name="vaccName">
                	
						</select>
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">儿童ID：</label>
					<div class="col-sm-5">
						<form:input path="childid" htmlEscape="false" maxlength="50"
							class="form-control " />
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">有无异常反应：</label>
					<div class="col-sm-5">
						<form:input path="iseffect" htmlEscape="false" maxlength="50" class="form-control " />
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">nID：</label>
					<div class="col-sm-5">
						<input value="" name="nid" id="nid">
					</div>
				</div>

				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">针&#12288;&#12288;次：</label>
					<div class="col-sm-8">
						<form:input path="dosage" htmlEscape="false" readonly="true" maxlength="50" class="form-control " />
					</div>
				</div>

				<div class="form-group col-sm-6 quick-option" style="display: none;">
					<label class="col-sm-4 control-label">接种部位：</label>
					<div class="col-sm-8">
	                	<form:select path="bodypart" items="${fns:getDictList('position')}" itemLabel="label" itemValue="value" cssClass="form-control">
						</form:select>
					</div>
				</div>

			 	<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">疫苗批号：</label>
					<div class="col-sm-8">
						<form:select path="batch" class="form-control ">
							
						</form:select>
					</div>
				</div>  
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">疫苗价格：</label>
					<div class="col-sm-8">
						<!-- <label id="labelid" class="col-sm-4 control-label text-left" style="font-size: 120%"> </label> -->
						<label id="labelid"  style="font-size: 120%"> </label>
						<input name="price" id="price" hidden="hidden"> 
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label class="col-sm-4 control-label">批次库存：</label>
					<div class="col-sm-8">
						<!-- <label id="labelid" class="col-sm-4 control-label text-left" style="font-size: 120%"> </label> -->
						<label id="labelstore"  style="font-size: 120%"> </label>
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">保险：</label>
					<div class="col-sm-5">
						<form:radiobuttons path="insurance" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"
							element="label class='checkbox-inline i-checks'" class="required" />

					</div>
				</div>
				<div class="form-group" hidden="hidden"   >
					<input name="source" id="source" value="0"> 
				</div>				
				<!-- 下次接种时间 -->
				<label class="col-sm-10 col-sm-offset-1 control-label text-left nextEl" style="font-size: 16px;">预约下一针时间：<span id="mouthAge" style="color: red; font-weight: bold;">（）</span></label>
				<div class="col-sm-12"></div>
				<div class="next form-group">
					<div class="col-sm-1"></div>
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
		 					<div class="remindList">
		 						
		 					</div>
								<div class="form-group btn-queue mt20">
									<div class="col-sm-12">
										<p class="roomcodeWrap" style="display: none; font-size: 20px">已选指定排号至&nbsp;
											<strong class="red  roomcode"></strong>&nbsp;科室 &nbsp;
											<button type="button" class="btn btn-xs btn-danger roomcodeCancel">取消</button>
										</p>
										<button id="sendSign" class="btn btn-danger" type="button" >电子签字</button>
										<input id="waitSign" class="btn btn-danger w100" type="button" value="正在签字" disabled="disabled" style="display: none;" />
										<a href="javascript:dofinish('','');" class="btn btn-danger w100 sign-finish" style="display: none;">确认签字</a> 
										<input id="btnSubmit" class="btn btn-primary" type="submit" value="登记打印排号" />
										<button id="btnRemindToggle" class="btn btn-primary w100" type="button" data-stat="list" >预约</button>
										<input class="btn btn-default w100" type="button" value="取消" onclick="parent.layer.closeAll()" />
									</div>
								</div>
		 				</div>
		 				<input type="hidden" name="signature" id="signatureInp">
						<div id="signature" class="col-sm-5 none">
							<object id="HWPenSign"
				                    name="HWPenSign"
				                    classid="clsid:E8F5278C-0C72-4561-8F7E-CCBC3E48C2E3"
				                    width="310"
				                    height="206">
				            </object>
						</div>
		 			</div>
					<div id="nextVacc" class="form-group col-sm-5 nextEl">
		 				<div class="nextVaccDiv fl clearfix">
		 					<p class="title">一类苗</p>
		 					<ul id="sa1" data-role="1">
		 						<c:forEach items="${vaccs1}" var="v">
			 						<c:if test="${v.id!=nid}">
			 							<li data-intype="${v.intype}" data-mNum=${v.group}${v.pin}><i></i><span>${v.name}</span></li>
			 						</c:if>
		 						</c:forEach>
			 				</ul>
		 				</div>
		 				<div class="nextVaccDiv fl clearfix">
		 					<p class="title">二类苗</p>
		 					<ul id="sa2" data-role="2">
			 					<c:forEach items="${vaccs2}" var="v">
				 					<c:if test="${v.id!=nid}">
			 							<li data-intype="${v.intype}" data-mNum=${v.group}${v.pin}><i></i><span>${v.name}</span></li>
		 							</c:if>
		 						</c:forEach>
		 					</ul>
		 				</div>
		 			</div>
				</div>
				<!-- <input type="hidden" name="signature" id="signatureInp">
						<div id="signature" class="col-sm-6 none">
							<object id="HWPenSign"
				                    name="HWPenSign"
				                    classid="clsid:E8F5278C-0C72-4561-8F7E-CCBC3E48C2E3"
				                    width="412"
				                    height="206">
				            </object>
						</div> -->
			</form:form>
	</div>
	<script type="text/javascript" src="${ctxStatic}/plugins/datepicker/jquery-ui.min.js"></script>
	<script type="text/javascript" src="${ctxStatic}/plugins/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="${ctxStatic}/js/childRemind.js" ></script>
	
</body>
</html>