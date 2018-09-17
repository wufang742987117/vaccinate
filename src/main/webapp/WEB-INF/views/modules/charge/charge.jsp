<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>划价收费系统</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxBase}/static/common/jeesite.js"></script>
<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<style type="text/css">
.all-button{
	margin-top: 50px;
}
.none{
	display: none;
}
#table3 table tr{
	height: 40px;
}
#table-detail table tr{
	height: 40px;
}
.no-num{
	text-align: center;
	height: 60px;
}
.invalid{
	padding: 3px 4px;
	background-color: #ff0000;
	cursor: pointer;
	color: #fff;
	border-radius: 3px;
	display: inline-block;
}
.print{
	padding: 3px 4px;
	background-color: #1bb394;
	cursor: pointer;
	color: #fff;
	border-radius: 3px;
	display: inline-block;
	margin-left: 8px;
}
.green{
	color: green;
}
.red{
	color: red;
}
.page{
	float: right;
}
td{
	text-align: center;
}
.table>thead>tr>.table-title{
	font-size: 16px;
}
.fr{
	float: right;
}

.tk-list{
    color: #474b52;
    font-size: 15px;
    font-weight: 600;
    /*padding: 10px 20px 10px 25px;*/
    background-color: #afd9df;
    border: none;
    /*display: block;*/
}
.choosed{
	background: 0 0;
    background-color: #FCFCFC;
    border-color: #ddd #ddd rgba(0, 0, 0, 0);
    border-bottom: #f3f3f4;
    -webkit-border-image: none;
    -o-border-image: none;
    border-image: none;
    border-style: solid;
    border-width: 1px;
    /* font-size: 16px; */
    color: #1c84c6;
    cursor: default;
}
.all-button{
	border-bottom: 1px solid #fff;
}
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{
	font-size: 16px;
}

</style>
<script type="text/javascript">
	var socket;  
    var MSG = '';
    var DATAS = '';
    var TYPE =0;
    var billNum = '';
    var pageT = 0;
    /*  socket连接  */
    function Connect(){  
        try{  
        	if(socket && socket.readyState == 3){
        		socket.close();
        		socket = null;
        	}
            socket = new WebSocket('ws://127.0.0.1:8077');              
        }catch(e){  
            console.error("WebSocket 连接失败" ,e) 
            return;
        }  
        socket.onopen = sOpen;  
        socket.onerror = sError;
        socket.onmessage= sMessage;
        socket.onclose= sClose;
    }
    
    function sOpen(){
        console.info("WebSocket sOpen" ,"打开连接成功")
    }
        
    function sError(e){
     	console.error("WebSocket sError" ,e);
     	socket.close();
    }
    
    function sMessage(msg){  
    	console.log("WebSocket sMessage" ,msg.data);
    	MSG = msg.data;
    	TYPE = 1;
    	layer.closeAll();
    	$("#btnAddPer").click();
    	//initData(MSG)
    }
    
    function sClose(e){
  		console.info("WebSocket sClose" ,e);
    }
        
    function Send(recordId, url){
        if(socket && socket.readyState == 1){
            socket.send("open," + url + "," + recordId);
        }else{
        	Connect();
        	if(socket){
	        	setTimeout(function(){socket.send("open," + url + "," + recordId);},500);
        	}
        }
    }
     Send("aaa","bbb");
    //Connect();
	/*  socket连接 end  */
   

  	var PAGE = 1;
  	var pageSize = 10;
  	var INFO = '';
  	var init={
  		changeColor: function(){
  			$(".tkStatus").each(function(){
  				if($(this).text() == "正常"){
  					$(this).addClass("green");
  				}else{
  					$(this).addClass("red");
  				}
  			});
  		},
  		kaiPcolor: function(){
  			$(".iskaiP").each(function(){
  				if($(this).text() == "已开票"){
  					$(this).addClass("green");
  				}else{
  					$(this).addClass("red");
  				}
  			});

  		},
  		allShow: function(page,pageSize){
  			var pageSizes = pageSize;
  			var billNum = $.trim($("#billNum").val());
  			var patientName = $("#patientName").val();
  			var beginChargedate = $("#beg").val();
  			var endChargedate = $("#end").val();
  			var pageSize = 10;
  			var data = {
  				billNum: billNum,
  				patientName: patientName,
  				beginChargedate: beginChargedate,
  				endChargedate: endChargedate,
  				pageNo: page,
  				pageSize: pageSizes
  			};
  			$.ajax({
  				url: "${ctx}/charge/findListOverview",
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					console.log(data);
					pageT = 0;
					if(data.OverviewPageList.list.length >0){
						var html = '';
						var list = data.OverviewPageList.list;

						for(i=0;i<list.length;i++){
							var operate = '';//'<span class="invalid" data-num="'+list[i].billNum+'">作废</span>';
							var status = '';
							var iskaiP = '';
							if(list[i].status == 1 &&list[i].rstatus == 1 ){
								operate = operate + '<span class="invalid" data-num="'+list[i].billNum+'">作废</span>';//"<span class='print'>打印</span>";
							}
							if(list[i].status == 1 && list[i].billing == 0){
								operate = operate +"<span class='print' data-name='"+list[i].patientName+"' data-num='"+list[i].billNum+"' data-status='"+list[i].status+"'>打印</span>";
							}
							if(list[i].status == 0){
								status = "作废";
							}else if(list[i].status == 1){
								status = "正常";
							}
							if(list[i].billing == 0){
								iskaiP = "未开票";
							}else{
								iskaiP = "已开票";
							}
							html=html+"<tr><td>"+(i+1)+"</td><td>"+list[i].patientName+"</td><td>"+list[i].billNum+"</td><td>"
							+list[i].vaccCount+"</td><td>"+list[i].sumPrice+"</td><td>"+list[i].createDate+"</td><td>"+list[i].createByName+"</td><td>"+operate+"</td><td class='tkStatus'>"+status+"</td><td class='iskaiP'>"+iskaiP+"</td><td>"+list[i].remarks+"</td></tr>";
						}
						$("#allShow").html(html);
						init.changeColor();
						init.kaiPcolor();
						//init.page(data.OverviewPageList.count);
						$("#table3 .page").html(data.OverviewPageList.html);
					}else if(data.OverviewPageList.list.length == 0 || data.OverviewPageList.list == undefined){
						var html = '';
						var html = "<tr><th colspan='13' class='no-num'>暂无数据</th></tr>";
						$("#allShow").html(html);
						$("#table3 .page").html('');
					}
				} 
  			});
  		},
  		//分页
  		
  		detailReceipt: function(page,pageSize){
  			var pageSizes = pageSize;
  			var billNum = $.trim($("#billNum").val());
  			var patientName = $("#patientName").val();
  			var beginChargedate = $("#beg").val();
  			var endChargedate = $("#end").val();
  			var pageSize = 10;
  			var data = {
  				billNum: billNum,
  				patientName: patientName,
  				beginChargedate: beginChargedate,
  				endChargedate: endChargedate,
  				pageNo: page,
  				pageSize: pageSizes
  			};
  			$.ajax({
  				url: "${ctx}/charge/findListDetails",
  				data: data,
  				type: 'post',
  				dataType: "json",
  				success: function(data){
  					pageT = 1;
  					if(data.DetailsPageList.list.length >0){
  						var html = '';
						var list = data.DetailsPageList.list;
						for(i=0;i<list.length;i++){
							var operate = '<span class="invalid">作废</span>';
							var status = '';
							if(list[i].status == 0){
								operate = operate + "<span class='print' >打印</span>";
								status = "已作废";
							}else if(list[i].status == 1){
								status = "正常";
							}
							html=html+"<tr><td>"+(i+1)+"</td><td>"+list[i].patientName+"</td><td>"+list[i].billNum+"</td><td>"+list[i].vaccName+"</td><td>"+list[i].vaccCount+"</td><td>"+list[i].vaccBatchnum+"</td><td>"+list[i].vaccManufacturer+"</td><td>"+list[i].vaccPrice+"</td><td>"+
							list[i].vaccDose+"</td><td>"+list[i].sumPrice+"</td><td>"+list[i].createDate+"</td><td class='tkStatus'>"+status+"</td></tr>";
						}
						$("#tableDatil").html(html);
						init.changeColor();
						$("#table-detail .page").html(data.DetailsPageList.html);
  					}else if(data.DetailsPageList.list.length == 0 || data.DetailsPageList.list == undefined){
  						var html = '';
						var html = "<tr><th colspan='14' class='no-num'>暂无数据</th></tr>";
						$("#tableDatil").html(html);
						$("#table-detail .page").html('');
  					}
  				}
  			})
  		},
  		//默认时间
  		defaultTime: function(){
  			var date = new Date();
  			var year = date.getFullYear();
  			var month = date.getMonth()+1;
  			var day = date.getDate();
  			month.toString().length>1 ?month =  month : month = "0" +month;
  			day.toString().length>1?day = day: day = "0"+day;
  			$("#beg").val(year+"-"+month+"-"+day+" 00:00:00");
  			$("#end").val(year+"-"+month+"-"+day+" 23:59:59");
  		},
  		//验证流水号
  		vaildBillNum: function(){
  			$.ajax({
  				url: "${ctx}/charge/getBillNumCurral",
  				data: "",
  				type: "post",
  				dataType: "json",
  				success: function(data){
  					if(data.result == true){
  						billNum = data.billNum;
  					}else{
  						primary(data.message);
  					}
  				},
  				error: function(error){
  					error(error);
  				}
  			})
  		}
  	};
  	function type (){
  		TYPE = 0;
  		MSG = '';
  	}
  	function page(n,s){
		var pages=n;
		pageSize=s;
		if(pageT == 0){
			init.allShow(n,s);
		}else{
			init.detailReceipt(n,s);
		}
	}

	$(document).ready(function() {
		init.vaildBillNum();
		init.defaultTime();
		init.allShow(1,pageSize);
        $("body").on("click",".invalid",function(){
        	if(billNum == ""){
				$("#setting").click();
			}else{
	        	var data = $(this).attr("data-num");
	        	var url = "${ctx}/charge/deleteTicket?billNum="+data;
	        	alertForm(url, "发票作废", "750", "400");
	        }
        })

		//录入信息
		$("#btnAddPer:not(.openPop)").click(function() {
			$(this).addClass("openPop");

			if(billNum == ""){
				$("#setting").click();
			}else{
				var url = "${ctx}/charge/insertPer?sessionId="+MSG+"&type="+TYPE;
				alertForm(url, "录入信息", "1000", "600");
				$(this).removeClass("openPop");
				changeType();
			}
			
		});

		//打印事件
		$("body").on("click",".print",function(){
			if(billNum == ""){
				$("#setting").click();
			}else{

				var billing = $(this).attr("data-num");
				var name = $(this).attr("data-name");
				var status = $(this).attr("data-status");
				var url = "${ctx}/charge/findPrintDatil?billing="+billing+"&name="+escape(name)+"&status="+status;
				alertForm(url, "发票打印", "500", "300");
				//updateBilling(billing,name,status);
			}
		})
		
		//发票退款
		$("#returnsCharge").click(function() {
			if(billNum == ""){
				$("#setting").click();
			}else{
				alertForm("${ctx}/charge/financeDatil", "发票退款", "500", "300");
			}
		});
		
		//设置
		$("#setting").click(function(){
			var url = "${ctx}/charge/setCharge?billNum="+billNum;
			alertForm(url,"设置","500","300");
		})
		
		//键盘事件
		$(document).keydown(function(event){
			console.info(event.keyCode);
			if(event.keyCode == 113 ){
				 $("#btnAddPer").click(); 
			}else if(event.keyCode == 115 ){
				 $("#btnAddVacc").click(); 
			}
		});
		
		//发票重打
		$("#printAgain").click(function(){
			if(billNum == ""){
				$("#setting").click();
			} else{
				alertForm("${ctx}/charge/findPrintAgain", "发票重打查询", "500", "250");
			}
		});
		
	});
	
	//金额中文转化方法
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

	//消息提示
	function alertMsg(msg) {
		layer.msg(msg);
	}

	//打印发票
	function updateBilling(bid,patientName,status,newBid){
		// if(!confirm("确认打印该发票？")){
		// 	return false;
		// }
		layer.confirm('确认打印该发票？', {title: "确认打印",
					  btn: ['确定', '取消',] //可以无限个按钮
					}, function(index, layero){
						//按钮【按钮一】的回调
						layer.close(index);
						truePrint(bid,patientName,status,newBid);		 
					}, function(index){
					  //按钮【按钮二】的回调
					  layer.close(index);
					  return false;
				});
	}
	function truePrint(bid,patientName,status,newBid){
		if(status == '0'){
			error("已作废发票，不可重新开票！");
		}else{
			$.ajax({
			 url : "${ctx}/charge/updateBilling?billNum="+bid+"&newBillNum="+newBid,
			 type : "post",
			 dataType : "json",
			 success:function(data){
			 		success(data.mas)
			 	if(data.result == true){
			 		success("发票打印中....");
			 		LODOP = getLodop();
				  	var h = 0;
				  	var j = 0;
				  	var sumPrice = 0 ;
				  	var myDate = new Date();
				    LODOP.SET_PRINT_MODE("PRINT_DEFAULTSOURCE",7);//1-纸盒 4-手动 7-自动 0-不控制
				    LODOP.PRINT_INIT("chargeCase");
				    LODOP.SET_PRINT_PAGESIZE(1,2000,1258,"");
				    LODOP.ADD_PRINT_TEXT(70,195,300,20,bid);
				    LODOP.ADD_PRINT_TEXT(95,150,200,20,patientName);
				    LODOP.ADD_PRINT_TEXT(95,400,100,20,"自费");
				    while(j<data.list.length) {
					     var charge = data.list[j];
				         LODOP.ADD_PRINT_TEXT(160+h*20,130,100,20,"【疫苗】");
				         LODOP.ADD_PRINT_TEXT(160+h*20,180,200,30,charge.vaccName);
				         LODOP.ADD_PRINT_TEXT(160+h*20,300,400,30,charge.vaccCount);
				         LODOP.ADD_PRINT_TEXT(160+h*20,330,300,20,(charge.vaccPrice).toFixed(2));
				         LODOP.ADD_PRINT_TEXT(160+h*20,390,400,20,(charge.vaccPrice * charge.vaccCount).toFixed(2));
				         j++;h++;
				     }
				    LODOP.ADD_PRINT_TEXT(335,250,200,20,"现金支付：" + charge.sumPrice.toFixed(2));
				    LODOP.ADD_PRINT_TEXT(335,370,100,20,"移动支付:0.00");
				    LODOP.ADD_PRINT_TEXT(365,560,100,20,""+charge.sumPrice.toFixed(2));
				    LODOP.ADD_PRINT_TEXT(365,180,300,20,convertCurrency(charge.sumPrice.toFixed(2)));  //200->15个字
				    LODOP.ADD_PRINT_TEXT(425,480,100,20,'${fns:getUser().name }');
				    LODOP.ADD_PRINT_TEXT(425,570,100,20,myDate.getFullYear()+"   "+(myDate.getMonth()+1)+"   "+myDate.getDate());
				    LODOP.SET_PREVIEW_WINDOW(0,0,0,760,540,"");	
				    //LODOP.PREVIEW();
				    LODOP.PRINT();
				    // location.href='${ctx}/charge/findCharge';
				    changePageT();
			 	}else{
			 		error(data.message);
			 	}
			 },
			 error:function(error){
			 	error(error);
			 }
		  })
		}
	}
	function changePageT(){
		if($("#chargeAll").hasClass("choosed")){
			init.allShow(1,pageSize);
		}else{
			init.detailReceipt(1,pageSize);
		}
	}
	function changeType(){
			var close =$(".layui-layer-close1")[0];
			close.addEventListener("click",function(){
				type();
			});
		}
	$(document).ready(function(){
		 //发票报表统计
		 $(document).ready(function(){
		  	$("#poi").click(function(){
               var beg = $("#beg").val();
               var end = $("#end").val();
               var createById = $("#createById").val();
			   //window.open("${ctx}/charge/findReport","_slef");
			   window.location.href="${ctx}/charge/findReport";
   		  	})
		 })
		 
		//
		$(document).ready(function(){
			$("#chargeDatil").click(function(){
				$(this).addClass("choosed").siblings().removeClass("choosed");
				$("#table3").addClass("none");
				$("#table-detail").removeClass("none");
				init.detailReceipt(1,pageSize);
				pageT = 1;
			});
			$("#chargeAll").click(function(){
				$(this).addClass("choosed").siblings().removeClass("choosed");
				$("#table3").removeClass("none");
				$("#table-detail").addClass("none");
				init.allShow(1,pageSize);
				pageT = 0;
			})
			
		});
		
		//发票退费实现
		$(document).ready(function(){

		 	$(".returnsCharge").click(function(){
			  var _this = $(this);
			  var bid = _this.attr("data-billnum");
			  alertForm("${ctx}/charge/financeDatil?billNum="+bid, "退费", "500", "300");
			});
		 	$("#btnSubmit").click(function(){
		 		var billsNum = $.trim($("#billNum").val());
		 		if(!/^\d{0,10}$/.test(billsNum)){      
            		parent.error("请输入10位以内0-9的数字！");    
		    	return true;
            	}
		 		if($("#chargeAll").hasClass("choosed")){
		 			init.allShow(1,pageSize);
		 		}else{
		 			init.detailReceipt(1,pageSize);
		 		}
		 	});
		});
	});
</script>
</head>
<body>
	<sys:message content="${message}"/>
	<div>
        <form:form id="searchForm" modelAttribute="bsChargeLog" action=""  method="post" class="form-inline mt20">
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">业务流水号：</span>
						<input name="billNum" id="billNum"  class="form-control" value="${billNum}"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">姓名：</span>
						<input name="patientName" id="patientName" class="form-control" value="${patientName}"/>
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">创建时间(起)：</span>
						<input name="beginChargedate" id="beg" value="<fmt:formatDate value="${bsChargeLog.beginChargedate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">创建时间(止)：</span>
						<input name="endChargedate" id="end" value="<fmt:formatDate value="${bsChargeLog.endChargedate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" />
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="text" value="查询" readonly />&emsp;&emsp;&emsp;
				</div>
				<a href="${ctx}" class="btn btn-success pull-right"><span class="glyphicon glyphicon-arrow-left" ></span> 退出</a>
				<a href="${ctx}/charge/findCharge" class="pull-right"><span class="btn btn-danger mr15">刷新</span></a>
		</form:form>
		<sys:message content="${message}"/>
	</div>
	<div class="form-group all-button">
		<button id="btnAddPer" class="btn btn-success mr15">录入信息</button>
		<button id="returnsCharge" class="btn btn-danger mr15">发票退款</button>
		<c:if test="${printAgain == 1}">
			<button id="printAgain" class="btn btn-warning mr15">发票重打</button>
		</c:if>
		
		<button id="poi" class="btn btn-primary mr15">报表统计</button>
		<button id="setting" class="btn btn-primary mr15" style="width:82px;">设置</button>

		
			<button id="chargeDatil" class="btn  fr tk-list">发票明细</button>
			<button id="chargeAll" class="btn  choosed  fr choosed tk-list">发票总览</button>
		
	</div>
	<sys:message content="${message}" />
	<div id="table3">
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th colspan="14" class="table-title">发票总览</th>
				</tr>
				<tr>
					<th style="width: 50px;">序号</th>
					<th>姓名</th>
					<th>业务流水号</th>
					
					<th>疫苗数量</th>
					<th style="width: 120px;">发票总额（元）</th>
					<th>缴费时间</th>
					<th>开票人</th>
					<th>操作</th>
					<th>状态</th>
					<th>开票状态</th>
					<th>备注</th>
				</tr>
			</thead>
			<tbody id="allShow">

			</tbody>
		</table>
		<div class="page"></div>
	</div>
	<div id="table-detail" class="none">
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th colspan="16" class="table-title">发票明细</th>
					</tr>
					<tr>
						<th style="width: 50px;">序号</th>
						<th>姓名</th>
						<th>业务流水号</th>
						<th>名称</th>
						<th>疫苗数量</th>
						<th style="width: 120px;">批次</th>
						<th>疫苗厂商</th>
						<th>单价（元）</th>
						<th>疫苗规格</th>
						<th>总额（元）</th>
						<th>缴费时间</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody id="tableDatil">
					
				</tbody>
			</table>
			<div class="page"></div>
	</div>
</body>
</html>