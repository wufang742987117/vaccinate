<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>划价收费系统</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<style type="text/css">
.wrap {
	/*background: #fafafa;*/
	min-width: 430px;
	padding: 10px 20px;
	font-family: Microsoft Yahei;
}
label{
	font-weight: 100;
}
.patient-info label {
	text-align: left !important;
	line-height: 34px;
}
.fl{
	float: left;
}
.wid50{
	width: 50%;
}
.clear{
	clear: both;
}
.top{
	background-color: #fff;
	border-radius: 5px;
	font-weight: bold;
}
.title{
	background-color: #dcdddd;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
	padding: 10px;
	position: relative;
}
.content{
	padding: 20px 0px 15px 25px;
	font-weight: 100;
}
.choose-vac,.gathering{
	background-color: #fff;
	border-radius: 5px;
	margin-top: 20px;
}
.mar10{
	margin-top: 7px;
	margin-left: 10px;
}
.wid70{
	width: 70%;
}
.form-control, .single-line{
	padding: 6px 5px;
}
.wid48{
	width: 48%
}
.wid60{
	width: 60%;
}
.ml7{
	margin-left:10px;
}
.mt20{
	margin-top: 20px;
}
.form-horizontal .form-group {
    margin-right: 0px; 
    margin-left: 0px; 
}
.vacName,.batchno,.specification{
	height: 30px;
	border-radius: 3px;
}
.vas-box{
	position: relative;
}
.vas1{
	margin-top: 10px;
	position: relative;
}
.add{
	position: absolute;
	top: 10px;
	right: 30px;
	background-image: url("${ctxStatic}/images/add.png");
	height: 21px;
	width: 20px;
	background-size: 100%;
	cursor: pointer;
}
.del{
	position: absolute;
	top: 10px;
	right: 30px;
	background-image: url("${ctxStatic}/images/delete.png"); 
	height: 21px;
	width: 20px;
	background-size: 100%;
	cursor: pointer;
}
.none{
	display: none;
}
.vacName{
	width: 137px;
}
.vacCount{
	width: 50px;
	text-align: center;
}
.jiage{
	width: 120px;
}
.pihao{
	width: 255px;
}
.ml15{
	margin-left: 15px;
}
.ml5{
	margin-left: 5px;
}
.price,.vacCount{
	height: 30px;
	/*width: 70px;*/
}
.specification{
	width: 70px;
}
.guige{
	width: 110px;
}
.yimiao{
	width: 178px;
}
.price{
	width: 75px;
}
.wid55{
	width:55%;
}
</style>
<script type="text/javascript">
	var TYPE='';
	var patientId = '';
	var billingStatus;
	var VACC = [];
	var pageSize = 10;
	var session = '';
	var init = {
		inputInfo: function(){
			$.ajax({
				url: '${ctx}/charge/insertCharge',
				data: '',
				type: "post",
				dataType: "json",
				success: function(data){
					$("#Outpatient").val(data.hospNum);
					$("#prescription").val(data.presNum);
					$("#doctors").val(data.doctor);
					$("#department").val(data.department);
					if(data.vacclist.length>0){
						var html = '';
						for(i=0;i<data.vacclist.length;i++){
							html = html+"<option data-id='"+data.vacclist[i].VACCINEID+"'>"+data.vacclist[i].VACC_NAME+"</option>";
						}
						$(".vacName").html(html);
						// $("#vacName").change();
						var that = $(".vacName");
						init.batchno(that);
					}
				}
			});
		},
		batchno: function(that){
			//var that = this
			var data = {
				vaccineid: that.find("option:selected").attr("data-id"),//$(".vacName option:selected").attr("data-id"),
				showAll: 1
			};
			$.ajax({
				url: '${ctx}/charge/findViewListApi',
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					if(data.productList.length>0){
						var html = '';
						for(i=0;i<data.productList.length;i++){
							html= html+ "<option data-bat='"+data.productList[i].batchno+"' data-off='"+data.productList[i].officeCodeDb+"'>"+data.productList[i].manufacturer+"_"+data.productList[i].batchno+"_"+data.productList[i].officeCodeDb+"</option>";
						}
						that.parents(".yimiao").siblings(".pihao").find(".batchno").html(html);
						that = that.parents(".yimiao").siblings(".pihao").find(".batchno");
						// $("#batchno").change();
						init.specification(that);

					}
				}
			});
		},
		specification: function(that){
			//var that = this;
			var data = {
				vaccineid: that.parents(".pihao").siblings(".yimiao").find(".vacName option:selected").attr("data-id"),//$(".vacName option:selected").attr("data-id"),
				showAll: 1,
				batchno: that.find("option:selected").attr("data-bat"),//$(".batchno option:selected").text()
				officeCodeDb: that.find("option:selected").attr("data-off")
			};
			$.ajax({
				url: '${ctx}/charge/findViewListApi',
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					if(data.productList.length>0){
						var html = '';
						for(i = 0;i<data.productList.length;i++){
							html = html + "<option data-MANUFACTURER='"+data.productList[i].manufacturer+"'  data-id='"+data.productList[i].id+"'>"+data.productList[i].specification+"</option>";
						}
						that.parents(".pihao").siblings(".guige").find(".specification").html(html);
						//$("#specification").html(html);
						init.searchPrice(that);
					}
				}
			})
		},
		searchPrice: function(that){
			var data= {
				VACCINEID: that.parents(".pihao").siblings(".yimiao").find(".vacName option:selected").attr("data-id"),//$(".vacName option:selected").attr("data-id"),
				VACCINENAME: that.parents(".pihao").siblings(".yimiao").find(".vacName option:selected").text(),//$(".vacName option:selected").text(),
				BATCHNUM: that.find("option:selected").attr("data-bat"),//$(".batchno option:selected").text(),
				MANUFACTURER: that.parents(".pihao").siblings(".guige").find(".specification option:selected").attr("data-MANUFACTURER"),//$(".specification option:selected").attr("data-MANUFACTURER"),
				DOSE: that.parents(".pihao").siblings(".guige").find(".specification option:selected").text(),//$(".specification option:selected").text(),
				PID: that.parents(".pihao").siblings(".guige").find(".specification option:selected").attr("data-id")//$(".specification option:selected").attr("data-id")
			};
			$.ajax({
				url: "${ctx}/charge/getVaccById",
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					perPrice = data.SELLPRICE;
					that.parents(".pihao").siblings(".jiage").find(".price").val(parseFloat(data.SELLPRICE).toFixed(2));
					var vacCount = that.parents(".pihao").siblings(".shuliang").find(".vacCount");
					vacCount.attr("data-price",JSON.stringify(data));
					var price = 0;
					if($(".vas1").length == 0 ){
						var count = vacCount.val();
						var perPrice = JSON.parse(vacCount.attr("data-price")).SELLPRICE;
						price = count*perPrice;
						$("#allPrice").val(parseFloat(price).toFixed(2));
						$("#payment").val(parseFloat(price).toFixed(2));
						$("#changes").val(parseFloat(0).toFixed(2));
					}else{
						var vas = $(".vas-box").find(".vacCount");
						var vasCount = vas.val();
						var vasPrice = JSON.parse(vas.attr("data-price")).SELLPRICE;
						price = vasCount*vasPrice;
						$(".vas1").each(function(){
							var vas1 = $(this).find(".vacCount");
							var vasCount1 = vas1.val();
							var varPrice1 = JSON.parse(vas1.attr("data-price")).SELLPRICE;
							//$(this).find(".price").val(varPrice1);
							price = price+vasCount1*varPrice1;
						});
						$("#allPrice").val(parseFloat(price).toFixed(2));
						$("#payment").val(parseFloat(price).toFixed(2));
						$("#changes").val(parseFloat(0).toFixed(2));
					}
				}
			});
		},
		scanInfo: function(sessionId){
			var data={
				sessionId : sessionId
			};
			$.ajax({
				url: "${ftx}/api/saveToPage.do",
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					if(data.result){
						$("#patientName").val(data.PATIENT_NAME);
						$("#patientName").attr("readonly",true);
						var html = '';
						for(i=0;i<data.vacc.length;i++){
							html = html + `<div class="vas-box">
							<div class="all-price fl yimiao">
								<label class="fl">疫苗:</label>
								<div class="wid70 fl ml7">
									<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
									<select class="vacName" disabled="disabled">
										<option >`+data.vacc[i].VACC_NAME+`</option>
									</select>
								</div>
								<div class="clear"></div>
							</div>
							<div class="all-price fl pihao ml15">
								<label class="fl">厂家_批号_科室:</label>
								<div class="wid55 fl ml7">
									<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
									<select class="batchno" disabled="disabled" style="width: 100%;">
										<option >`+data.vacc[i].MANUFACTURER+"_"+data.vacc[i].BATCHNUM+"_"+data.vacc[i].officeCodeDb+`</option>
									</select>
								</div>
								<div class="clear"></div>
							</div>
							<div class="all-price fl guige ml5">
								<label class="fl">规格:</label>
								<div class="wid48 fl ">
									<!-- <input class="form-control" name="costId" type="text" id="costId" value="0.25ml" readonly/> -->
									<select class="specification" disabled="disabled">
										<option >`+data.vacc[i].SPECIFICATIONVALUE+`</option>
									</select>
								</div>
								<div class="clear"></div>
							</div>
							<div class="all-price jiage fl ml15">
								<label class="fl">单价:</label>
								<div class="wid50 fl ml7">
									<input class="form-control price" name="costId" type="number" class="" value="`+parseFloat(data.vacc[i].SELLPRICE).toFixed(2)+`" readonly/>
								</div>
								<div class="clear"></div>
							</div>
							<div class="all-price fl shuliang ml15">
								<label class="fl">数量:</label>
								<div class="wid50 fl ml7">
									<input class="form-control vacCount" name="costId" type="number" class=""  data-price="`+data.vacc[i].SELLPRICE+`" value="`+data.vacc[i].COUNT+`" readonly/>
								</div>
								<div class="clear"></div>
							</div>
							
							<div class="clear"></div>
							</div>`;
						}
						$(".choose-vac .title span").addClass("none");
						$(".addContent").html(html);
						$("#allPrice").val(parseFloat(init.countPrice()).toFixed(2));
						$("#payment").val(parseFloat(init.countPrice()).toFixed(2));
						$("#changes").val(parseFloat(0).toFixed(2));
						patientId = data.PATIENTID;
						var vacc = [];
						var newVacc = [];
						
						vacc = data.vacc;
						seesion = data.PAY_SESSION_PARAM;
						for(j=0;j<vacc.length;j++){
							var perVacc = {};
							perVacc.vaccName = vacc[j].VACC_NAME;
							perVacc.vaccCode = vacc[j].VACCINEID;
							perVacc.vaccPrice = parseFloat(vacc[j].SELLPRICE).toFixed(2);
							perVacc.batchnum = vacc[j].BATCHNUM;
							perVacc.manufacturer = vacc[j].MANUFACTURER;
							perVacc.dose = vacc[j].SPECIFICATIONVALUE;
							perVacc.vaccCount = vacc[j].COUNT;
							perVacc.pid = vacc[j].PID;
							newVacc.push(perVacc);
						}
						VACC = newVacc;
					}else{
						parent.type();
						parent.error(data.message);
						var index_ss = parent.layer.getFrameIndex(window.name);
	 					parent.layer.close(index_ss);
					}
				}

			})
		},
		countPrice: function(){
			var price = 0;
			$(".vacCount").each(function(){
				var perCount = parseInt($(this).val()) * parseInt($(this).attr("data-price"));
				price += perCount;
			})
			return price;
		},
		serialNum: function(){
			$.ajax({
				url: "${ctx}/charge/getBillNumCurral",
				type: "post",
				data: '',
				dataType: "json",
				success: function(data){
					$("#serialNum").val(data.billNum);
				}
			})
		},
		searchBillingStatus: function(){
			$.ajax({
				url:"${ctx}/charge/getBillingStatus",
				type: "post",
				data: '',
				dataType: "json",
				success: function(data){
					billingStatus = data.billingStatus;
					if(data.billingStatus == 1){
						$("#btnSave").text("打印发票");
						//$("#serialNum").val("0000000000");
					}
				}
			})
		},
		submite: function(){
			var thisCount = true;
			var nameReg = /^[\u4e00-\u9fa5]{2,30}$|(^[a-zA-Z]+[/s.]?([a-zA-Z]+[/s.]?){0,4}[a-zA-Z]{2,30}$)/;
			var name = $("#patientName").val();
			if($("#patientName").val() == '' || nameReg.test(name) == false){
				parent.error("请输入正确的姓名");
				return false;
			}
		 	var billNum = $.trim($("#serialNum").val());
		 	if(!/^\d{1,10}$/.test(billNum)){      
        		parent.error("请输入10位以内0-9的数字！");    
		    	return true;
        	}

			$(".vacCount").each(function(){
				if($(this).val() == '' || $(this).val()<1){
					parent.error("请输入正确的数量");
					thisCount =false;
					return false;
				}
			})
			if($("#payment").val()-$("#allPrice").val()<0){
				parent.error("请输入正确的金额");
				return false;
			}
			if(thisCount == false){
				return false;
			}
			var paySession;
			var jsonData = {};
			var vacc = [];
			if(TYPE == 0){
				paySession = '';
				jsonData.patientId = '';
				vacc = init.submiteDate();
				
			}else{
				paySession = seesion;
				jsonData.patientId = patientId;
				vacc = VACC; 
				
			}
			jsonData.patientName = $("#patientName").val();
			jsonData.billNum = $.trim($("#serialNum").val());
			jsonData.sumPrice = $("#allPrice").val();
			jsonData.pay = $("#payment").val();
			jsonData.refund = $("#changes").val();
			jsonData.billing = billingStatus;
			jsonData.vacc = vacc;
			
			var data = {
				paySession: paySession,
				jsonData: JSON.stringify(jsonData)
			};
			console.log(data);
			$.ajax({
				url: "${ctx}/charge/saveChargeData",
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					if(data.result == true && billingStatus == 1){
						parent.type();
						parent.changePageT();
						parent.success(name+"用户支付了"+parseFloat(jsonData.pay).toFixed(2)+"元，找零"+parseFloat(jsonData.refund).toFixed(2)+"元，发票正在打印中...");
						parent.truePrint(jsonData.billNum,jsonData.patientName,1,jsonData.billNum)
						//init.printTk(jsonData);
					}else if(data.result == true && billingStatus == 0){
						parent.type();
						parent.changePageT();
						parent.success(name+"用户支付了"+parseFloat(jsonData.pay).toFixed(2)+"元，找零"+parseFloat(jsonData.refund).toFixed(2)+ "元");
						// parent.init.allShow(1,pageSize);
						// parent.init.detailReceipt(1,pageSize);
						
						//window.parent.location.reload();
						var index_ss = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index_ss);
						
					}else if(data.result == false){
						parent.error(data.message);
						// var index_ss = parent.layer.getFrameIndex(window.name);
						// parent.layer.close(index_ss);
					}
				}
			})
		},
		submiteDate: function(){
			var manualVacc = [];
			if($(".vas1").length == 0 ){
				var vacCount = $(".vas-box").find(".vacCount");
				var count = vacCount.val();
				var data = JSON.parse(vacCount.attr("data-price"));
				var vacData = {};
				vacData.pid = data.PID;
				vacData.vaccName = data.VACC_NAME;
				vacData.vaccCode = data.VACCINEID;
				vacData.vaccPrice = parseFloat(data.SELLPRICE).toFixed(2);
				vacData.batchnum = (!data.BATCHNUM?"无":data.BATCHNUM);
				vacData.manufacturer = (!data.MANUFACTURER?"无":data.MANUFACTURER);
				vacData.dose = (!data.SPECIFICATIONVALUE?"无":data.SPECIFICATIONVALUE);
				vacData.vaccCount = count;
				manualVacc.push(vacData);
			}else{
				var vacCount = $(".vas-box").find(".vacCount");
				var count = vacCount.val();
				var data = JSON.parse(vacCount.attr("data-price"));
				var vacData = {};
				vacData.pid = data.PID;
				vacData.vaccName = data.VACC_NAME;
				vacData.vaccCode = data.VACCINEID;
				vacData.vaccPrice = parseFloat(data.SELLPRICE).toFixed(2);
				vacData.batchnum = (!data.BATCHNUM?"无":data.BATCHNUM);
				vacData.manufacturer = (!data.MANUFACTURER?"无":data.MANUFACTURER);
				vacData.dose = (!data.SPECIFICATIONVALUE?"无":data.SPECIFICATIONVALUE);
				vacData.vaccCount = count;
				manualVacc.push(vacData);
				$(".vas1").each(function(){
					var vacCount1 = $(this).find(".vacCount");
					var count1 = vacCount1.val();
					var preData = JSON.parse(vacCount1.attr("data-price"));
					var vacDatas = {};
					vacDatas.pid = preData.PID;
					vacDatas.vaccName = preData.VACC_NAME;
					vacDatas.vaccCode = preData.VACCINEID;
					vacDatas.vaccPrice = parseFloat(preData.SELLPRICE).toFixed(2);
					vacDatas.batchnum = (!preData.BATCHNUM?"无":preData.BATCHNUM);
					vacDatas.manufacturer = (!preData.MANUFACTURER?"无":preData.MANUFACTURER);
					vacDatas.dose = (!preData.SPECIFICATIONVALUE?"无":preData.SPECIFICATIONVALUE);
					vacDatas.vaccCount = count1;
					manualVacc.push(vacDatas);
				});
			}
			return manualVacc;
		},
		changeCount: function(){
			var price = 0;
			if($(".vas1").length == 0 ){
				var vacCount = $(".vas-box").find(".vacCount");
				var count = vacCount.val();
				var perPrice = JSON.parse(vacCount.attr("data-price")).SELLPRICE;
				price = count*perPrice;
				$("#allPrice").val(parseFloat(price).toFixed(2));
				$("#payment").val(parseFloat(price).toFixed(2));
				$("#changes").val(parseFloat(0).toFixed(2));
			}else{
				var vas = $(".vas-box").find(".vacCount");
				var vasCount = vas.val();
				var vasPrice = JSON.parse(vas.attr("data-price")).SELLPRICE;
				price = vasCount*vasPrice;
				$(".vas1").each(function(){
					var vas1 = $(this).find(".vacCount");
					var vasCount1 = vas1.val();
					var varPrice1 = JSON.parse(vas1.attr("data-price")).SELLPRICE;
					price = price+vasCount1*varPrice1;

				});
				$("#allPrice").val(parseFloat(price).toFixed(2));
				$("#payment").val(parseFloat(price).toFixed(2));
				$("#changes").val(parseFloat(0).toFixed(2));
			}
		},
		printTk: function(data){
			if(billingStatus == "1"){
		  		//var vaccine = JSON.stringify(obj);
			    var jsondata = data;
			    var b = [];
			    var h = 0;
			    var a = 0;
			    //将多针次合并一条
			    for(var i = 0; i < jsondata.vacc.length; i++) {
			        var status = 0;
			        for(var j = 0; j < b.length; j++) {
			            if(jsondata.vacc[i].vaccName == b[j].vaccName && jsondata.vacc[i].vaccPrice == b[j].vaccPrice) {
			            	b[j].vaccCount++;
			                status = 1;
			                break;
			            }
			        }
			        if(status == 0) {
			            b.push(jsondata.vacc[i]);
			        }
			    }
			    var sum = 0; 
			    var myDate = new Date();
			    LODOP = getLodop();
			    LODOP.SET_PRINT_MODE("PRINT_DEFAULTSOURCE",7);//1-纸盒 4-手动 7-自动 0-不控制
			    LODOP.PRINT_INIT("打印发票");
			    LODOP.SET_PRINT_PAGESIZE(1,2000,1258,"");
			    LODOP.ADD_PRINT_TEXT(70,195,300,20,jsondata.billNum);
			    LODOP.ADD_PRINT_TEXT(95,150,200,20,jsondata.patientName);
			    LODOP.ADD_PRINT_TEXT(95,400,100,20,"自费");
	      		while(a < b.length) { 
	        		LODOP.ADD_PRINT_TEXT(160+h*20,130,100,20,"【疫苗】"); 
	         		LODOP.ADD_PRINT_TEXT(160+h*20,180,200,30,b[a].vaccName);
	          		LODOP.ADD_PRINT_TEXT(160+h*20,300,400,20,b[a].vaccCount);  
		          	LODOP.ADD_PRINT_TEXT(160+h*20,330,300,20,parseFloat(b[a].vaccPrice).toFixed(2));
		          	LODOP.ADD_PRINT_TEXT(160+h*20,390,400,20,(parseFloat(b[a].vaccPrice) * b[a].vaccCount).toFixed(2)); 
		          	a++;h++;
	    		}  
			    LODOP.ADD_PRINT_TEXT(335,250,200,20,"现金支付：" + jsondata.sumPrice);
			    LODOP.ADD_PRINT_TEXT(335,370,100,20,"移动支付:0.00");
			    LODOP.ADD_PRINT_TEXT(365,560,100,20,"" + jsondata.sumPrice);
			    LODOP.ADD_PRINT_TEXT(365,180,300,20,convertCurrency(jsondata.sumPrice));
			    LODOP.ADD_PRINT_TEXT(425,480,100,20,'${fns:getUser().name }'); 
			    LODOP.ADD_PRINT_TEXT(425,570,100,20,myDate.getFullYear()+"   "+(myDate.getMonth()+1)+"   "+myDate.getDate());
			    LODOP.SET_PREVIEW_WINDOW(0,0,0,760,540,"");
			    //LODOP.PREVIEW();
			    LODOP.PRINT();
			 //    parent.init.allShow(1,pageSize);
				// parent.init.detailReceipt(1,pageSize);
				parent.changePageT();
			    //window.parent.location.reload();
			    var index_ss = parent.layer.getFrameIndex(window.name);
				parent.layer.close(index_ss);
		  	}
		}
	};

	function convertCurrency(money) {
		// 汉字的数字
		var cnNums = new Array('零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖');
		// 基本单位
		var cnIntRadice = new Array('', '拾', '佰', '仟');
		// 对应整数部分扩展单位
		var cnIntUnits = new Array('', '万', '亿', '兆');
		// 对应小数部分单位
		var cnDecUnits = new Array('角', '分', '毫', '厘');
		// 整数金额时后面跟的字符
		var cnInteger = '整';
		// 整型完以后的单位
		var cnIntLast = '元';
		// 最大处理的数字
		var maxNum = 999999999999999.9999;
		// 金额整数部分
		var integerNum;
		// 金额小数部分
		var decimalNum;
		// 输出的中文金额字符串
		var chineseStr = '';
		// 分离金额后用的数组，预定义
		var parts;
		if (money == '') {
			chineseStr = cnNums[0] + cnIntLast + cnInteger;
			return chineseStr;
		}
		money = parseFloat(money);
		if (money >= maxNum) {
			// 超出最大处理数字
			return '';
		}
		if (money == '0') {
			chineseStr = cnNums[0] + cnIntLast + cnInteger;
			return chineseStr;
		}
		// 转换为字符串
		money = money.toString();
		if (money.indexOf('.') == -1) {
			integerNum = money;
			decimalNum = '';
		} else {
			parts = money.split('.');
			integerNum = parts[0];
			decimalNum = parts[1].substr(0, 4);
		}
		// 获取整型部分转换
		if (parseInt(integerNum, 10) > 0) {
			var zeroCount = 0;
			var IntLen = integerNum.length;
			for (var i = 0; i < IntLen; i++) {
				var n = integerNum.substr(i, 1);
				var p = IntLen - i - 1;
				var q = p / 4;
				var m = p % 4;
				if (n == '0') {
					zeroCount++;
				} else {
					if (zeroCount > 0) {
						chineseStr += cnNums[0];
					}
					// 归零
					zeroCount = 0;
					chineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
				}
				if (m == 0 && zeroCount < 4) {
					chineseStr += cnIntUnits[q];
				}
			}
			chineseStr += cnIntLast;
		}
		// 小数部分
		if (decimalNum != '') {
			var decLen = decimalNum.length;
			for (var i = 0; i < decLen; i++) {
				var n = decimalNum.substr(i, 1);
				if (n != '0') {
					chineseStr += cnNums[Number(n)] + cnDecUnits[i];
				}
			}
		}
		if (chineseStr == '') {
			chineseStr += cnNums[0] + cnIntLast + cnInteger;
		} else if (decimalNum == '') {
			chineseStr += cnInteger;
		}
		return chineseStr;
	}

	var addEvent = function(){
		$("body").on("change",".vacName",function(){
			var that = $(this);
			init.batchno(that);
		});

		$("body").on("change",".batchno",function(){
			var that = $(this);
			init.specification(that);
		});

		var i = 0
		$(".add").click(function(){
			var $a = $(".vas-box");
			var c = "<div class='vas1'></div>"
			//c.addClass("vas1").removeClass("vas-box");
			//var b= $(".vas1");
			$(".addContent").append(c);
			var b = $(".vas1");
			$(b[b.length-1]).html($a.html());
			$(".vas1").find(".price").val(parseFloat($a.find(".price").val()).toFixed(2));
			$(".vas1").find(".del").removeClass("none");
			init.changeCount();
		});

		$("body").on("click",".del",function(){
			var div = $(".vas1");
			if(div.length>0){
				$(this).parents(".vas1").remove();
			}
			init.changeCount();
		});

		$("body").on("change",".vacCount",function(){
			//init.searchPrice();
			init.changeCount();
		});

		$("body").on("change","#payment",function(){
			var a= $(this).val();
			var b =$("#allPrice").val();
			$("#changes").val(parseFloat(a-b).toFixed(2));
		});

		$("#btnSave").click(function(){
			layer.confirm('确认提交？', {title: "确认提交",
				  btn: ['确定', '取消',] //可以无限个按钮
				}, function(index, layero){
					layer.close(index);
					init.submite();
				  //按钮【按钮一】的回调
				}, function(index){
				  //按钮【按钮二】的回调
				  layer.close(index);
				  return false;
				});
		});
		// $(".layui-layer-content").keydown(function(e){
		// 	if(e.keyCode == 13){

		// 	}
		// });
	};

	function isScan(){
		var url = window.location.href;
		var id = url.split("=")[2];
		TYPE = id;
		var sessionId = url.split("=")[1].split("&")[0];
		if(id == 0){
			init.inputInfo();
		}else{
			init.scanInfo(sessionId);
		}
	}
	function changeType(){
			var close =$(".layui-layer-close1")[0];
			close.addEventListener("click",function(){
				type();
			});
		}

	$(document).ready(function() {
		$(".vas-box").find(".del").addClass("none");
		init.serialNum();
		init.searchBillingStatus();
		isScan();
		//键盘事件
		addEvent();
		//changeType();
		$(document).keydown(function(event){
			var index_s;
			if(event.keyCode == 13){
				if($(".layui-layer-dialog").length == 0){
					//$("#btnSave").focus(); 
					layer.confirm('确认提交？', {
						  btn: ['确定', '取消',] //可以无限个按钮
						}, function(index, layero){
							index_s = index;
							layer.close(index);
							init.submite();
						  //按钮【按钮一】的回调
						}, function(index){
						  //按钮【按钮二】的回调
						  layer.close(index);
						  return false;
					});
				}else{
					//layer.close(index_s);
					$(".layui-layer-btn0").click();
					//init.submite();
				}
			}else if(event.keyCode == 27){
				parent.layer.closeAll(); 
			}
	  	});
		// $(document).on("keydown",".layui-layer-dialog",function(){
		// 	alert(11);
		// })
		//退出
		$("#btnExit").click(function() {
			parent.type();
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭   
		});
	});
</script>
</head>
<body>
	<div class="wrap">
		<form:form id="inputForm" modelAttribute="ChargeEntity" action="" method="post" class="form-horizontal patient-info">
			<input type="hidden" id="billNum" name="billNum" value = "${billNum}"/>
			<input type="hidden" id="presNum" name="presNum" value = "${billNum}"/>
			<input type="hidden" id="doctor" name="doctor" value = "${fns:getUser().name }"/>
			<input type="hidden" id="balance" name="balance" value = ""/>
			<div class="top">
				<div class="title">用户信息</div>
				<div class="content">
					<div class="form-group fl col-xs-6">
						<label class="fl control-label" for="patientName">姓名:</label>
						<div class="wid50 fl mar10">
							<input class="form-control" name="patientName" type="text" id="patientName" />
							<script language="JavaScript"> 
								document.getElementById('patientName').value="";
								document.getElementById('patientName').focus();  
								<!--设置id为name的元素得到焦点--> 
							</script>
						</div>
						<div class="clear"></div>
					</div>
					<div class="form-group fl col-xs-6">
						<label class="fl control-label" for="hospId">业务流水号:</label>
						<div class="wid50 fl mar10">
							<input class="form-control" name="hospId" type="number" id="serialNum" value="" />
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>
					
			</div>
			<div class="choose-vac">
				<div class="title">选择疫苗 <span class="add"></span></div>
				<div class="content addContent">
					<div class="vas-box">
						<div class="all-price yimiao fl">
							<label class="fl">疫苗:</label>
							<div class="wid70 fl ml7">
								<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
								<select  class="vacName"></select>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price pihao fl ml15">
							<label class="fl">厂家_批号_科室:</label>
							<div class="wid55 fl ml7">
								<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
								<select  class="batchno" style="width: 100%;"></select>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price guige fl ml5">
							<label class="fl">规格:</label>
							<div class="wid48 fl ml7">
								<!-- <input class="form-control" name="costId" type="text" id="costId" value="0.25ml" readonly/> -->
								<select id="specification" class="specification"></select>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price jiage fl ml15">
							<label class="fl">单价:</label>
							<div class="wid50 fl ml7">
								<input class="form-control price" name="costId" type="number" class="" value="" readonly/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price shuliang fl ml15">
							<label class="fl">数量:</label>
							<div class="wid50 fl ml7">
								<input class="form-control vacCount" name="costId" type="number" class="" value="1" />
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price fl">
							<span class="del"></span>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<div class="gathering">
				<div class="title">收款结算</div>
				<div class="content">
					<div class="all-price fl col-xs-4">
						<label class="fl">总计金额:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="text" id="allPrice" value="（无）" readonly/>
						</div>
					</div>
					<div class="all-price fl col-xs-4">
						<label class="fl">交款:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="text" id="payment" value="" />
						</div>
					</div>
					<!-- <div class="all-price fl col-xs-4">
						<label class="fl">业务流水号:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="number" id="costId" value="（无）"/>
						</div>
					</div>
					<div class="all-price fl col-xs-4">
						<label class="fl">交&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;款:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="number" id="costId" value="（无）"/>
						</div>
					</div>
					<div class="all-price fl col-xs-4">
						<label class="col-xs-6"><input type="radio" name="" value="">现金支付</label>
						<label class="col-xs-6"><input type="radio" name="" value="">移动支付</label>
						
					</div> -->
					<div class="all-price fl col-xs-4">
						<label class="fl">找&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;零:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="number" id="changes" value="（无）" readonly />
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
				
			<div class="form-group mt20">
				
				<button id="btnExit" class="btn btn-default mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 退出
				</button> 
				<button id="btnSave" class="btn btn-success mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 确认
				</button> 
			</div>
		</form:form>
	</div>
</body>
</html>