<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>狂犬病信息管理</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<script src="${ctxStatic}/js/printrips.js"></script>
<script type='text/javascript' src='${ctxBase}/dwr/engine.js'></script>
<script type='text/javascript' src='${ctxBase}/dwr/util.js'></script>
<script type='text/javascript' src='${ctxBase}/dwr/interface/pay.js'></script>
<style type="text/css">
	.btn-sm{
		font-size: 15px;
		padding: 4px 6px;
	}
	.fr{
		float: right;
	}
	.form-queue{
		position: absolute;
		bottom: 30px;
		right: 30px;
		width: 220px;
	}
	.form-queue table{
		width: 100%;
		border-top: 1px solid #fff;
		border-left: 1px solid #fff;
	}
	.form-queue table tr{
		border-bottom: 1px solid #fff;
		border-right: 1px solid #fff;
		height: 35px;
	}
	.form-queue table thead tr th{
		font-size: 18px;
		font-weight: bold;
	}
	.form-queue table tbody tr td{
		width: 50%;
		text-align: center;
		font-size: 16px;
	}
	.delete{
		padding: 4px 9px;
		border-radius: 3px;
		cursor: pointer;
		background-color: red;
		color: #fff;
	}
	#formQueue{
		max-height: 211px;
		overflow-y: auto;
		display: block;
	}
	#formQueue tr{
		display:table;
		width:100%;
		table-layout:fixed;
		
	}
	body{
		position: relative;
	}
	#formQueue tr:hover{
		 background: rgb(255, 213, 177);
	}
	body{
		position: relative;
	}
</style>

<script type="text/javascript">
	/* websocket */
    var socket;  
    
    function Connect(){  
        try{  
        	if(socket && socket.readyState == 3){
        		socket.close();
        		socket = null;
        	}
            socket = new WebSocket('ws://127.0.0.1:40000');              
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
     	console.error("WebSocket sError" ,e) 
     	if(isExitsFunction('CancelS') && e.data != undefined){
			CancelS(e.data.substring(1));
		}
     	socket.close();
    }
    
    function sMessage(msg){  
    	console.info("WebSocket sMessage" ,msg) 
    	if(isExitsFunction('ifSigntureSuccess') && msg.data != undefined){
			ifSigntureSuccess(msg.data);
		}
    }
    
    function sClose(e){
  		console.info("WebSocket sClose" ,e) 
  		if(isExitsFunction('CancelS') && e.data != undefined){
			CancelS(e.data.substring(1));
		}
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
	
	function CancelS(id){
		if(socket && socket.readyState == 1){
            socket.send("close");
			setTimeout(function(){socket.close();},500);
        }else{
        	Connect();
        	if(socket){
				setTimeout(function(){socket.send("close");setTimeout(function(){socket.close();},500);},500);
        	}
        }
		$("." + id + "1").html("<input data-role=\"signature\" type=\"button\" value=\"签字\" class=\"btn btn-danger btn-xs\" onclick=\"SignatureId('"+id+"',this)\">");
	}
	
	function dofinish(id){
		if(socket && socket.readyState == 1){
            socket.send("finish");
			setTimeout(function(){socket.close();},500);
        }else{
        	Connect();
        	if(socket){
				setTimeout(function(){socket.send("finish");setTimeout(function(){socket.close();},500);},500);
        	}
        }
		$("." + id + "1").html("<input data-role=\"signature\" type=\"button\" value=\"签字\" class=\"btn btn-danger btn-xs\" onclick=\"SignatureId('"+id+"',this)\">");
	}
        
    function Close(){
        socket.close();
    }
    
    //	启动打开socket
	Connect();
    /* websocket end */
	
	var fromcode = false;
	var isopen = false;
	
    window.onload = function() {  
        dwr.engine.setActiveReverseAjax(true); 
        //默认注册sessionId 
        pay.getwebindex(sessionId);
    }
    
    function waitPay(recs){
    	pay.getwebindex(sessionId);
    }
    
    function waitSignture(recs){
    	pay.getwebindex(sessionId);
    }
    
    function recid(dwrmsg){
    	console.info("回调sessionid-->" + dwrmsg);
    }
    
    function ifPaySuccess(msg){  
    	console.info("已付款记录id-->" + msg);
    	/* do someting */
    	if(msg != ""){
    		var list = JSON.parse(msg);
	    	for(var i=0;i<list.length;i++){
				var pay = trim($("." + list[i]).html());
				if(pay == undefined){
		    		continue;
		    	}
		    	$("." + list[i]).html("已付款");
			}
	    	layer.msg("支付成功",{"icon":1});
    	}
    }  
    
    function ifSigntureSuccess(id){  
	   	console.info("签字记录id-->" + id);
    	/* do someting */
    	if(id != ""){
	    	var pay = trim($("." + id).html());
	    	if(pay == undefined){
	    		return;
	    	}
	       	if(pay == "未付款"){
	       		$("." + id + "1").html("<span style='color:red;font-weight:bold;'>已签字</span>");
	       	}else{
	       		var vaccidate = $("." + id + "1").parent().find("td[data-role=vaccidate]").html();
	       		var newdata = StringToDate(vaccidate);
	       		$("[name=notitems]:checkbox").each(function(i,t){
					var map = $(this).parent().parent().find("td[data-role=vaccidate]").html();
					var oldata = StringToDate(map);
					var flag = new Date(formatterDate(newdata)) - new Date(formatterDate(oldata));
					var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
					if(flag == 0 && html != "未付款"){
						$(this).parent().parent().find("td[data-role=signatureNo]").html("<span style='color:red;font-weight:bold;'>已签字</span>");
					}
				});
		        $("[name=items]:checkbox").each(function(i,t){
					var map = $(this).parent().parent().find("td[data-role=vaccidate]").html();
					var oldata = StringToDate(map);
					var flag = new Date(formatterDate(newdata)) - new Date(formatterDate(oldata));
					var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
					if(flag == 0 && html != "未付款"){
						$(this).parent().parent().find("td[data-role=signatureNo]").html("<span style='color:red;font-weight:bold;'>已签字</span>");
					}
				});
				if(pay == "已付款"){
					bsNumFormId(id,$("." + id + "1").find("span"));
				}
	        }
	        layer.msg("签字成功",{"icon":1});
    	}
    }  
</script>

<script type="text/javascript">
	var openStatus = "${openStatus}";
	var printStatus = "${printStatus}";
	var specialStatus = "${specialStatus}";

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	function openWindowsH880(url, title, w, h) {
		closefous();
		w = isNull(w) ? 800 : w;
		h = isNull(h) ? 880 : h;
		var ww = ($(document).width() < w ? $(document).width() - 30 : w) + "px";
		var hh = ($(document).height() < h ? $(document).height() - 30 : h) + "px";	
		layer.open({
			type : 2,
			area : [ ww, hh ],
			title : isNull(title) ? "信息" : title,
			fix : false, //不固定
			maxmin : false,
			shade : 0.3,
			shift : 0, //0-6的动画形式，-1不开启
			content : url,
			shadeClose : true,
			cancel: function(index, layero){ 
				if(fromcode){
					document.getElementById('code').focus(); 
					isopen = false;
				}
				openfous();
			    layer.close(index);
			    return false; 
			},
			end: function(){
				openfous();
			}
		});
	}
	
	function openWindowsH600(url, title, w, h) {
		closefous();
		w = isNull(w) ? 800 : w;
		h = isNull(h) ? 600 : h;
		var ww = ($(document).width() < w ? $(document).width() - 30 : w) + "px";
		var hh = ($(document).height() < h ? $(document).height() - 30 : h) + "px";
		layer.open({
			type : 2,
			area : [ ww, hh ],
			title : isNull(title) ? "信息" : title,
			fix : false, //不固定
			maxmin : false,
			shade : 0.3,
			shift : 0, //0-6的动画形式，-1不开启
			content : url,
			shadeClose : true,
			cancel: function(index, layero){ 
				openfous();
			    layer.close(index);
			    return false; 
			},
			end: function(){
				openfous();
			}
		});
	}
	
	function closefous(){
		document.onkeydown = function(e){  
		   e = window.event || e;  
		   var keycode = e.keyCode || e.which;  
	       if(window.event){
	           // ie  
	           try{
	           	   e.keyCode = 0;
	           }catch(e){
	           	   
	           }  
	           e.returnValue = false;  
	       }
	    } 
	}
	
	function openfous(){
		document.onkeydown = function(e){  
		   e = window.event || e;  
		   var keycode = e.keyCode || e.which;  
	       if(window.event){
	           e.returnValue = true;  
	       }
	    } 
	}
	
	function closeForm() {
		$("#searchForm").submit();
		layer.closeAll();
	}
	
	function closeFormId(id,search) {
		if(search == undefined){
			search = "0";
		}
		window.location.href="${ctx}/rabiesvaccine/bsRabiesCheckin/form?id="+id+"&search="+search;
		layer.closeAll(); 
	}
	
	function closeFormIdPrint(id,notitem,item,msg) {
		var search = "0";
		if((notitem != "" || item != "") && printStatus == "1"){
			var items = new Array();
			var notitems = new Array();
			if(notitem != ""){
				notitems.push(notitem);
			}
			if(item != ""){
				items.push(item);
			}
			if(notitems.length != 0 || items.length != 0){
				$.ajax({
			        type: "post",
			        dataType:"json",
			        url: "${ctx}/rabiesvaccine/bsRabiesCheckin/printInvoice?id="+id+"&items="+items+"&notitems="+notitems+"&type=0",
			        success: function (data) {
			        	if(data.error != undefined){
			           		layer.msg(data.error,{"icon":7});
			           		return;
			           	}
			           	if(!printInvoiceId(data)){
			           		warning("小票打印失败,请检查本地打印控件是否安装或关闭打印配置");
			           	}		           	
			        }
		    	});
			}
		}
		window.location.href="${ctx}/rabiesvaccine/bsRabiesCheckin/form?id="+id+"&search="+search; 
		if(msg != undefined && msg != ""){
			layer.msg(msg);
		}
		layer.closeAll();
	}
	
	function closeFormArg(arg) {
		window.location.href = window.location.href; 
		layer.closeAll();
	}
	
	function closeFormMsg(msg) {
		layer.msg(msg);
		layer.closeAll();
	}
	
	function submitYouForm() {
		var searchName = $("#searchName").val();
		if(searchName != ""){
			openWindowsH600(encodeURI("${ctx}/rabiesvaccine/bsRabiesCheckin/namelist?searchName="+ encodeURIComponent(searchName)), "个案列表", "1000", "");
		}
	}
	
	//刷新当前页面
	function funB() {
		window.location.reload();
	}
	
	function funC() {
		window.location.href = "${ctx}/rabiesvaccine/bsRabiesCheckin";
	}
	
	function callBack(){
		window.location.href = window.location.href; 
		window.opener.location.reload(); 
	}
	
	//新增犬伤档案
	function bsInsert() {
		fromcode=false;
		openWindowsH880("${ctx}/rabiesvaccine/bsRabiesCheckin/update?type=0", "档案新增", "1000", "");
	}
	
	//新增成人档案
	function bsInsertHepatitisBcheckin() {
		fromcode=false;
		openWindowsH880("${ctx}/hepatitis/bsHepatitisBcheckin/update?type=0", "档案新增", "1000", "");
	}
	
	function bsUpdate() {
		fromcode=false;
		openWindowsH880("${ctx}/rabiesvaccine/bsRabiesCheckin/update?id=${bsRabiesCheckin.id}&type=1", "档案修改", "1000", "");
	}
	
	//删除档案
	function checkIdDelete(a) {
		$.ajax({
			type : "POST",
			url : "${ctx}/rabiesvaccine/bsRabiesCheckin/delete?id=" + a,
			success : function(data) {
	            setTimeout(funC, 100);
			}
		});
	}
	
	//注射单打印
	function printInjection(id) {
		window.open("${ctx}/rabiesvaccine/bsRabiesCheckin/printInjection?id="+id,'_blank');
	}
	
	//登记单打印
	function printRegister(id) {
		window.open("${ctx}/rabiesvaccine/bsRabiesCheckin/printRegister?id="+id,'_blank');
	}
	
	//告知书打印
	function printInform(id) {
		window.open("${ctx}/rabiesvaccine/bsRabiesCheckin/printInform?id="+id,'_blank');
	}
	
	//调价
	function adjustment(id) {
		var flag;
		var nflag;
		var items = new Array();
		var notitems = new Array();
		var err = 0;
		$("[name=items]:checkbox").each(function(i,t){
			flag = $(this).is(':checked');
			if(flag){
				var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
				if(html == "已付款" || html == "已完成"){
					err = 1;
					$("[name=items]:checkbox").prop("checked", false);
					layer.msg("勾选错误，存在已付款或已完成疫苗，请重新选择！！",{"icon":7});
					return;
				}
				items.push($(this).val());
			}
		});
		$("[name=notitems]:checkbox").each(function(i,t){
			nflag = $(this).is(':checked');
			if(nflag){
				var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
				if(html == "已付款" || html == "已完成"){
					err = 1;
					$("[name=notitems]:checkbox").prop("checked", false);
					layer.msg("勾选错误，存在已付款或已完成疫苗，请重新选择！！",{"icon":7});
					return;
				}
				notitems.push($(this).val());
			}
		});
		if(items.length != 0 || notitems.length != 0){
			openWindowsH600("${ctx}/num/bsRabiesNum/adjustment?checkid=${bsRabiesCheckin.id}&items="+items+"&notitems="+notitems, "调价",  "800", "480");
		}else{
			if(err == 0){
				layer.msg("未勾选疫苗！！！",{"icon":7});
			}
		}
	}
	
	//发票打印
	function printInvoice(id,val) {
		var flag;
		var nflag;
		var wflag;
		var url;
		var items = new Array();
		var witems = new Array();
		var notitems = new Array();
		var err = 0;
		$("[name=items]:checkbox").each(function(i,t){
			flag = $(this).is(':checked');
			if(flag){
				var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
				if(html == "已付款" || html == "已完成"){
					err = 1;
					$("[name=items]:checkbox").prop("checked", false);
					layer.msg("勾选错误，存在已付款或已完成疫苗，请重新选择！！",{"icon":7});
					return;
				}
				items.push($(this).val());
			}
		});
		
		$("[name=notitems]:checkbox").each(function(i,t){
			nflag = $(this).is(':checked');
			if(nflag){
				var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
				if(html == "已付款" || html == "已完成"){
					err = 1;
					$("[name=notitems]:checkbox").prop("checked", false);
					layer.msg("勾选错误，存在已付款或已完成疫苗，请重新选择！！",{"icon":7});
					return;
				}
				notitems.push($(this).val());
			}
		});
		
		$("[name=witems]:checkbox").each(function(i, t){
			wflag = $(this).is(':checked');
			if(wflag){
				var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
				if(html == "已付款" || html == "已完成"){
					err = 1;
					$("[name=witems]:checkbox").prop("checked", false);
					layer.msg("勾选错误，存在已付款或已完成疫苗，请重新选择！！",{"icon":7});
					return;
				}
				witems.push($(this).val());
			}
		});
		
		if(items.length != 0 || notitems.length != 0 || witems.length != 0){
			$.ajax({
		        type: "post",
		        dataType:"json",
		        url: "${ctx}/rabiesvaccine/bsRabiesCheckin/printInvoice?id="+id+"&items="+items+"&notitems="+notitems+"&witems="+witems+"&type=1",
		        success: function (data) {
		        	if(data.error != undefined){
		           		layer.msg(data.error,{"icon":7});
		           		return;
		           	}
		           	if(data.count == 0 && data.ncount == 0){
		           		layer.msg("未勾选疫苗！！！",{"icon":7});
		           	}else{
		           		//注册dwr sessionId等待回调
		           		waitPay(data.payId);
		           		//弹框
		           		if(val == 1){
		           			show_prompt(data,sessionId,"1");
		           		}else{
		           			url = "A_" + sessionId + "_" + data.payId + "_1";
			           		//调用打印js
			           		printInvoices(data,url);
		           		}
		           		var datas = {
							username: $("#userName").text(),
							rabiescode: $("#archiveCode").text(),
							type: 1
						};
						$.ajax({
							url: "${ctx}/rabiesvaccine/bsRabiesCheckin/insertBsRabiesBubble",
							data: datas,
							type: "post",
							dataType: "json",
							success: function(data){
								quene();
							}
						});
		           	}
		        }
	    	});
		}else{
			if(err == 0){
				layer.msg("未勾选疫苗！！！",{"icon":7});
			}
		}
	}
	
	function show_prompt(data1,sessionId,flg){
		var str = prompt("请输入总金额！");  
        if(str == ''){  
	        alert('输入为空，请重新输入！');  
	        show_prompt(data1,sessionId,flg);  
	    }else{
    		$.ajax({
		        type: "post",
		        dataType:"json",
		        url: "${ctx}/rabiesvaccine/bsRabiesCheckin/printPay",
		        data:{"json":JSON.stringify(data1),"sessionId":sessionId,"str":str,"type":flg},
		        success: function (data) {
					if(data.sendOrder != ""){
		           		printInvoices(data,data.sendOrder);
		           	}
		           			
				}
	    	});
	    }
	}
	
	//添加针次
	function formUpdateCheckid() {
		openWindowsH600("${ctx}/num/bsRabiesNum/form?checkid=${bsRabiesCheckin.id}&type=1", "添加针次",  "800", "480");
	}
	
	function formUpdateId(a) {
		openWindowsH600("${ctx}/num/bsRabiesNum/form?id=" + a + "&type=0", "修改针次",  "800", "480");
	}
	
	//删除针次
	function formDelete(a) {
		$.ajax({
			type : "POST",
			url : "${ctx}/num/bsRabiesNum/delete?id=" + a,
			success : function(data) {
	            if(data.success){
	            	$("tr[data-id="+ a +"]").remove();
	            }
			}
		});
	}
	
	//登记针次
	function bsNumFormId(a,thi) {
		var html = trim($("." + a).html());
		if(html == "未付款"){
			if(confirm("该针还未付款！若继续请点击确认，否则取消！")){
				bsNumFormPayId(a,thi);
			}
		}else{
			bsNumFormPayId(a,thi);
		}
	}
	
	function bsNumFormPayId(a,thi) {
		if(thi == undefined){
			openWindowsH600("${ctx}/num/bsRabiesNum/form?id=" + a + "&type=1", "登记针次", "800", "480");
		}else{
			var _this;
			if(thi instanceof jQuery){
				_this = thi;
			}else{
				_this = $(thi);
			}
			var vaccidate = _this.parent().parent().find("td[data-role=vaccidate]").html();
			var vdate = StringToDate(vaccidate);
			var mydata = new Date();
			var ndate;
			var prevVaccidate = _this.parent().parent().prev().find("td[data-role=vaccidate]").html();
			var prevRealdate = _this.parent().parent().prev().find("td[data-role=realdate]").html();
			if(prevVaccidate == "" || prevVaccidate == null){
				openWindowsH600("${ctx}/num/bsRabiesNum/form?id=" + a + "&type=1", "登记针次", "800", "480");
			}else{
				if(prevRealdate == "" || prevRealdate == null){
					if(confirm("上一针次还未完成！若继续请点击确认，否则取消！")){
						openWindowsH600("${ctx}/num/bsRabiesNum/form?id=" + a + "&type=1", "登记针次", "800", "480");
					}
				}else{
					var preVdate = StringToDate(prevVaccidate);
					var preRdate = StringToDate(prevRealdate);
					var flag = DateDiff(formatterDate(preRdate), formatterDate(preVdate));
					var nflag;
					ndate = addDay(flag, vdate);
					nflag = DateDiff(formatterDate(mydata), formatterDate(ndate));
					if(nflag < 0){
						if(confirm("当前时间小于应种时间！若继续请点击确认，否则取消！")){
							openWindowsH600("${ctx}/num/bsRabiesNum/form?id=" + a + "&type=1", "登记针次", "800", "480");
						}
					}else{
						openWindowsH600("${ctx}/num/bsRabiesNum/form?id=" + a + "&type=1", "登记针次", "800", "480");
					}
				}
			}
		}
	}
	
	//签字
	function SignatureId(id,thi) {
		if(thi == undefined){
			setSigntureId(id);
		}else{
			var _this;
			if(thi instanceof jQuery){
				_this = thi;
			}else{
				_this = $(thi);
			}
			var vaccidate = _this.parent().parent().find("td[data-role=vaccidate]").html();
			var vdate = StringToDate(vaccidate);
			var mydata = new Date();
			var ndate;
			var prevVaccidate = _this.parent().parent().prev().find("td[data-role=vaccidate]").html();
			var prevRealdate = _this.parent().parent().prev().find("td[data-role=realdate]").html();
			if(prevVaccidate == "" || prevVaccidate == null){
				setSigntureId(id,thi);
			}else{
				if(prevRealdate == "" || prevRealdate == null){
					if(confirm("上一针次还未完成！若继续请点击确认，否则取消！")){
						setSigntureId(id,thi);
					}
				}else{
					var preVdate = StringToDate(prevVaccidate);
					var preRdate = StringToDate(prevRealdate);
					var flag = DateDiff(formatterDate(preRdate), formatterDate(preVdate));
					var nflag;
					ndate = addDay(flag, vdate);
					nflag = DateDiff(formatterDate(mydata), formatterDate(ndate));
					if(nflag < 0){
						if(confirm("当前时间小于应种时间！若继续请点击确认，否则取消！")){
							setSigntureId(id,thi);
						}
					}else{
						setSigntureId(id,thi);
					}
				}					
			}
		}
	}
	
	//签字方法
	function setSigntureId(id,thi){
		//通用c#
		Send("A"+id, '${host}${ftx}/api/disclosure?vaccid=' + "A"+id);
		$.ajax({
	        type: "post",
	        dataType:"json",
	        url: "${ctx}/num/bsRabiesNum/updateSignStatus?id="+id,
	        success: function (data) {
	        	if(data.success){
	        		$("." + id + "1").html("<a href=\"javascript:CancelS('"+id+"')\" style='color:red;font-weight:bold;'>正在签字</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:dofinish('"+id+"')\" style='color:red;font-weight:bold;'>确认签字</a>");     	
	        	}else{
	        		layer.msg("签字存在问题！",{"icon":7});
	        	}
	        }
    	});
	}
	
	//记录查询
	function invoiceByListId(a,thi){
		var bid = "${bsRabiesCheckin.id}";
		var items = new Array();
		var notitems = new Array();
		if(thi == undefined){
			notitems.push(a);
		}else{
			items.push(a);
		}
		if(notitems.length != 0 || items.length != 0){
			$.ajax({
		        type: "post",
		        dataType:"json",
		        url: "${ctx}/rabiesvaccine/bsRabiesCheckin/printInvoice?id="+bid+"&items="+items+"&notitems="+notitems+"&type=0",
		        success: function (data) {
		        	if(data.error != undefined){
		           		layer.msg(data.error,{"icon":7});
		           		return;
		           	}
		           	if(!printInvoiceId(data)){
		           		warning("小票打印失败,请检查本地打印控件是否安装或关闭打印配置");
		           	}		           	
		        }
	    	});
		}
	}
	
	//退款
	function refundById(a){
		$.ajax({
			type : "POST",
			url : "${ctx}/num/bsRabiesNum/refundById?id=" + a,
			success : function(data) {
	            if(data.success){
	            	$("td[data-refund="+ a +"]").html("<span style='color:red;font-weight:bold;'>退款成功</span>");
			    	$("." + a).html("未付款");
	            }
			}
		});
	}
	
	//伤口处理费退款
	function refundDealById(a){
		$.ajax({
			type : "POST",
			url : "${ctx}/num/bsRabiesNum/refundDealById?id=" + a,
			success : function(data) {
	            if(data.success){
	            	$("td[data-refund="+ a +"]").html("<span style='color:red;font-weight:bold;'>退款成功</span>");
	            	$("." + a).html("未付款");
	            }
			}
		});
	}
	
	 //请求签字信息
    function signUp(id) {
    	$.ajax({
	        type: "post",
	        dataType:"json",
	        timeout: 3000,
	        url: "${ctx}/rabiesvaccine/bsRabiesCheckin/signUp",
	        data:{"id":id},
	        beforeSend: function (xhr) {
               layer.load(0, {time: 3000});  	// 数据加载成功之前，使用loading组件
            },
	        success: function (data) {
	        	if(data.success && data.list != ""){
		        	for(var i=0; i<data.list.length; i++){
		        		$("." + data.list[i] + "1").html("<span style='color:red;font-weight:bold;'>已签字</span>");
		        	}
		        	//layer.msg("获取签字数据成功！",{"icon":1});
	        	}else{
	        		//layer.msg("未找到签字数据！",{"icon":7});
	        	}
			},
            error: function (textStatus) {
                console.error(textStatus);
            },
            complete: function (XMLHttpRequest,status) {
            	layer.closeAll('loading'); 		//关闭加载层
                if(status == 'timeout') {
                    xhr.abort();    // 超时后中断请求
                    layer.msg("微信请求超时，请即时联系系统工作人员！！",{"icon":7});
                }
            }
    	});
    }
	
	$(function() {
		QueneDoge();
		//获取微信签字
		var bid = "${bsRabiesCheckin.id}";
		var signType = "${signType}";
		if(bid != "" && signType != "" && signType == "0"){
			signUp("${bsRabiesCheckin.id}");
		}
		
		//键盘事件
		$(document).keydown(function(event){
			// 先获取输入框元素
			var input = document.getElementById('code');
			// 如果hasFocus为true表示input元素获得焦点，否则没有获得焦点
			var hasFocus = document.hasFocus() && document.activeElement === input;
			if(hasFocus == false ){
				 document.getElementById('searchName').focus(); 
			}
	　　});

		var ls;
		$('#searchName').keyup(function(e) {
			ls = e.timeStamp;//利用event的timeStamp来标记时间，这样每次的keyup事件都会修改last的值，注意last必需为全局变量
			setTimeout(function(){    //设时延迟0.3s执行
				if(ls - e.timeStamp == 0) { //如果时间差为0（也就是你停止输入0.3s之内都没有其它的keyup事件发生）则做你想要做的事
		            var searchName = $("#searchName").val();
					if (searchName.length == 20 && e.keyCode!=13) {
						var b = searchName.substring(0,1);
						var c = searchName.substring(19,20);
						if(b == "D" && c == "A"){
							openWindowsH600(encodeURI("${ctx}/rabiesvaccine/bsRabiesCheckin/namelist?searchName="+ encodeURIComponent(searchName)), "个案列表", "1000", "");
						}
					}
                 }
			},300);
		});
		
		$('#searchName').keydown(function(e){
			if(e.keyCode==13){
				var searchName = $("#searchName").val();
				if(searchName != ""){
					openWindowsH600(encodeURI("${ctx}/rabiesvaccine/bsRabiesCheckin/namelist?searchName="+ encodeURIComponent(searchName)), "个案列表", "1000", "");			
				}
			}
		})
		
		$('#code').keyup(function(event) {
		    var a = $("#code").val();
			if (a.length == 8 &&((event.keyCode <= 57 && event.keyCode >= 48) || (event.keyCode <= 105 && event.keyCode >= 96))) {
				fromcode=true;
				if(!isopen){
					isopen=true;
					openWindowsH880("${ctx}/rabiesvaccine/bsRabiesCheckin/code?code=" + a, "自助建档", "1000", "");
				}
			}										        
		});
		
		
		//全选。全不选
		$("#CheckedAll").click(function () {
            if ($(this).is(":checked")) {
                $("[name=items]:checkbox").prop("checked", true);
            } else {
                $("[name=items]:checkbox").prop("checked", false);
            }
        });
        $("#CheckedAllNot").click(function () {
            if ($(this).is(":checked")) {
                $("[name=notitems]:checkbox").prop("checked", true);
            } else {
                $("[name=notitems]:checkbox").prop("checked", false);
            }
        });
        $("#CheckedAllWound").click(function () {
            if ($(this).is(":checked")) {
                $("[name=witems]:checkbox").prop("checked", true);
            } else {
                $("[name=witems]:checkbox").prop("checked", false);
            }
        });
        $("[name=items]:checkbox").each(function(i,t){
			var map = $(this).parent().parent().find("td[data-role=vaccidate]").html();
			var mydata = new Date();
			var oldata = StringToDate(map);
			var flag = new Date(formatterDate(mydata)) - new Date(formatterDate(oldata));
			if(flag == 0){
				$(this).prop("checked", true);
			}
		});
		$("[name=notitems]:checkbox").each(function(i,t){
			var map = $(this).parent().parent().find("td[data-role=vaccidate]").html();
			var mydata = new Date();
			var oldata = StringToDate(map);
			var flag = new Date(formatterDate(mydata)) - new Date(formatterDate(oldata));
			if(flag == 0){
				$(this).prop("checked", true);
			}
		});
		
		var search = "${bsRabiesCheckin.search}";
		if(search == "1" && openStatus == "1"){
			var n = 0;
			var m = 0;
			$("[name=notitems]:checkbox").each(function(i,t){
				var map = $(this).parent().parent().find("td[data-role=vaccidate]").html();
				var mydata = new Date();
				var oldata = StringToDate(map);
				var flag = new Date(formatterDate(mydata)) - new Date(formatterDate(oldata));
				var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
				var id = $(this).parent().parent().find("td[data-role=paystatus]")[0].className;
				var signature = $(this).parent().parent().find("." + id + "1>span").html();
				if(flag == 0 && html == "已付款" && signature != "已签字"){
					n++;
					SignatureId(id);
					return false;
				}else if(flag == 0 && html == "已付款" && signature == "已签字"){
					n++;
					bsNumFormId(id);
					return false;
				}
			});
			if(n == 0){
				$("[name=items]:checkbox").each(function(i,t){
					var map = $(this).parent().parent().find("td[data-role=vaccidate]").html();
					var mydata = new Date();
					var oldata = StringToDate(map);
					var flag = new Date(formatterDate(mydata)) - new Date(formatterDate(oldata));
					var html = trim($(this).parent().parent().find("td[data-role=paystatus]").html());
					var id = $(this).parent().parent().find("td[data-role=paystatus]")[0].className;
					var signature = $(this).parent().parent().find("." + id + "1>span").html();
					if(flag == 0 && html == "已付款" && signature != "已签字"){
						m++;
						SignatureId(id,$(this));
						return false;
					}else if(flag == 0 && html == "已付款" && signature == "已签字"){
						m++;
						bsNumFormId(id,$(this));
						return false;
					}
				});
			}
		}
	});
	
	/* 排号 */
	function insertQueneDoge(){
		$.ajax({
			url:"${ftx}/childBaseinfo/insertQueneDoge",
			success:function(data){
				console.info(data);
				var arr = new Array();
				if(data != null){
					arr = data.split("_");
				}
				//打印小票
				printtips(arr[0],"","","","","",arr[1]);
				QueneDoge();
				layer.msg(data,{time:3000})
			}
		});
	}
	
	//按钮倒计时
	var countdown;
	var count = 3;
	function disSend() {
		count = 3;
		$("#btnRow").attr("disabled", true);
		$("#btnRow").val(count + "秒后重新叫号");
		countdown = setInterval(CountDown, 1000);
	}

	function CountDown() {
		count--;
		$("#btnRow").val(count + "秒后重新叫号");
		if (count <= 0) {
			$("#btnRow").removeAttr("disabled");
			$("#btnRow").val("叫号");
			clearInterval(countdown);
		}
	}
	
	/* 叫号 */
	function callQueneDoge(){
		disSend();
		$.ajax({
			url:"${ftx}/childBaseinfo/callQueneDoge",
			success:function(data){
				console.info(data);
				layer.msg(data,{time:3000})
			}
		});
	}
	
	/* 完成 */
	function finishQueneDoge(){
		$.ajax({
			url:"${ftx}/childBaseinfo/finishQueneDoge",
			success:function(data){
				console.info(data);
				$("#doge").text(data);
				layer.msg(data,{time:3000})
			}
		});
	}
	
	/* 获取排号 */
	function QueneDoge(){
		$.ajax({
			url:"${ftx}/childBaseinfo/QueneDoge",
			success:function(data){
				console.info(data);
				if(data != ""){
					$("#doge").text(data);
				}else{
					$("#doge").text("无");
				}
			}
		});
	}
	
	//字符串转成日期类型
	function StringToDate(DateStr){
		var converted = Date.parse(DateStr);
		var myDate = new Date(converted);
		if (isNaN(myDate)){
			var arys= DateStr.split('-');
			myDate = new Date(arys[0],--arys[1],arys[2]);
		}
		return myDate;
	}
	
	//日期格式化
	function formatterDate(date) {
        var datetime = date.getFullYear()
                + "-"// "年"
                + ((date.getMonth() + 1) >= 10 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1))
                + "-"// "月"
                + (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
        return datetime;
    }
	
	//计算相差天数
	function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是yyyy-MM-dd格式
	    var aDate, oDate1, oDate2, iDays;
	    aDate = sDate1.split("-");
	    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
	    aDate = sDate2.split("-");
	    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
	    iDays = parseInt((oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
	    return iDays;  //返回相差天数
	}
	
	//增加时间天数
	function addDay(dayNumber, date) {
        date = date ? date : new Date();
        var ms = dayNumber * (1000 * 60 * 60 * 24)
        var newDate = new Date(date.getTime() + ms);
        return newDate;
    }
    
    function trim(str){ //删除左右两端的空格
　　    	 	return str.replace(/(^\s*)|(\s*$)/g, "");
　　    }
	function quene(){
		$.ajax({
			url: "${ctx}/rabiesvaccine/bsRabiesCheckin/findBsRabiesBubble",
			data: {type: "1"},
			type: "post",
			dataType: "json",
			success: function(data){
				if(data.bubbles.length >0){
					var html = '';
					for(i=0;i<data.bubbles.length;i++){
						html += `<tr>
						<td class="queneName" data-rabiescode="`+data.bubbles[i].rabiescode+`">`+data.bubbles[i].username+`</td>
						<td><span class="delete" data-rabiescode="`+data.bubbles[i].rabiescode+`">删除</span></td>
						</tr>`;
					}
					$("#formQueue").html(html);
				}else{
					$("#formQueue").html("<tr><td colspan='2'>暂无数据</td></tr>")
				}
			}
		});
	}
	$(function(){
		$("body").append(`<div class="form-queue fr">
			<table class="table-striped">
				<thead>
					<tr>
						<th colspan="2">接种队列</th>
					</tr>
				</thead>
				<tbody id="formQueue">

				</tbody>
			</table>
		</div>`);
		quene();
		$(document).on("click",".delete",function(){
			var datas={
				
				rabiescode: $(this).attr("data-rabiescode")
			};
			$.ajax({
				url: "${ctx}/rabiesvaccine/bsRabiesCheckin/deleteBsRabiesBubble",
				data: datas,
				type: "post",
				dataType: "json",
				success: function(data){
					quene();
				}
			})
		});
		$(document).on("dblclick","#formQueue tr",function(){

			$("#searchName").val($(this).find(".queneName").attr("data-rabiescode"));
			$(this).find(".delete").click();
			submitYouForm();
		})
	});
</script>

</head>
<body>
	<div class="ibox" >
		<div class=" col-sm-12">
			<div class="row" >
				<div>
					<div class="pl15 pr15" >
						<form:form id="searchForm" modelAttribute="bsRabiesCheckin"
							action="${ctx}/rabiesvaccine/bsRabiesCheckin/form" method="post" class="form-inline">
							<div class="form-group">
								<div class="input-group" >
									<form:input path="searchName" htmlEscape="false" maxlength="200" class="form-control w250" placeholder="姓名/电话/编号/证件/出生日期(如：20170101)"/>
									<script language="JavaScript"> 
										document.getElementById('searchName').value="";
										document.getElementById('searchName').focus();  
										<!--设置id为name的元素得到焦点--> 
									</script>
								</div>
							</div>
							<div class="form-group mr6">
								<input id="btnSubmit" type="button" class="btn btn-primary btn-sm" onclick="submitYouForm()" value="查询" />
							</div>
							<c:if test="${not empty bsRabiesCheckin.id }">
								<div class="form-group" >
									<a href="javascript:signUp('${bsRabiesCheckin.id}')" class="btn  btn-primary btn-sm mr6">签字下载</a>
								</div>
							</c:if>
							<!-- <div class="form-group mr6 " >
								<input id="btnCall" type="button" class="btn btn-primary btn-sm" onclick="insertQueneDoge()" value="排号" />
							</div>
							<div class="form-group mr6 ">
								<input id="btnRow" type="button" class="btn btn-primary btn-sm" onclick="callQueneDoge()" value="叫号" />
							</div>
							<div class="form-group mr6 ">
								<input id="btnToo" type="button" class="btn btn-primary btn-sm" onclick="finishQueneDoge()" value="下一号" />
							</div>
							<div class="form-group mr15" style="height:30px;">
								<p style="font-size:20px;font-weight:bold;"><span >当前排号：</span><span id="doge" style="color:#F00;"></span></p>
							</div> -->
							<div class="pull-right">
<%-- 								<a href="${ctx}/hepatitis/bsHepatitisBcheckin" class="btn btn-success" ><span class="glyphicon glyphicon-arrow-left"></span>返回成人免疫</a> --%>
								<div class="btn-group">
								    <a type="button" class="btn btn-default w100" onclick="javascript:loading('正在跳转...');" href="${ctx}/child_vaccinaterecord/childVaccinaterecord1">登记台</a>
								    <a type="button" class="btn btn-default" onclick="javascript:loading('正在跳转...');" href="${ctx}/inoculate/quene/inoculateIndex">儿童接种台</a>
								    <a type="button" class="btn btn-success" onclick="javascript:void(0);" >狂犬病免疫</a>
								    <a type="button" class="btn btn-default" onclick="javascript:loading('正在跳转...');" href="${ctx}/hepatitis/bsHepatitisBcheckin">成人免疫</a>
								    <a type="button" class="btn btn-default w100" onclick="javascript:loading('正在跳转...');" href="${ctx}">首页</a>
								</div>
							</div>
							<br>
							<div class="form-group">
								<div class="input-group w250">
									<span class="input-group-addon gray-bg text-right mr15">自助建档：</span>
									<input id="code" name="code"  maxlength="20" class="form-control w160"/>
								</div>
							</div>
							<div class="form-group" >
								<a href="javascript:bsInsert()" class="btn btn-primary btn-sm mr6">犬伤档案新增</a>
							</div>
							<div class="form-group" >
								<a href="javascript:bsInsertHepatitisBcheckin()" class="btn btn-primary btn-sm mr6">成人档案新增</a>
							</div>
							<c:if test="${not empty bsRabiesCheckin.id }">
								<div class="form-group" >
									<a href="javascript:bsUpdate()" class="btn btn-danger btn-sm mr6">档案修改</a>
								</div>
								<div class="form-group" >
									<a href="javascript:checkIdDelete('${bsRabiesCheckin.id}')" class="btn  btn-danger btn-sm mr6" onclick="return confirmx('确认要删除该信息吗？', this.href)">档案删除</a>
								</div>
								<c:if test="${specialStatus == 1 }">
									<div class="form-group" >
										<a href="javascript:adjustment('${bsRabiesCheckin.id}')" class="btn  btn-primary btn-sm mr6">调价</a>
									</div>
								</c:if>
								<div class="form-group" >
									<a href="javascript:printInvoice('${bsRabiesCheckin.id}','0')" class="btn  btn-primary btn-sm mr6">现金支付</a>
								</div>
								<%-- <div class="form-group" >
									<a href="javascript:printInvoice('${bsRabiesCheckin.id}','1')" class="btn  btn-primary btn-sm mr6">微信支付</a>
								</div> --%>
								<div class="form-group" >
									<a href="javascript:printInjection('${bsRabiesCheckin.id}')" class="btn  btn-primary btn-sm mr6">注射单</a>
								</div>
								<div class="form-group" >
									<a href="javascript:printRegister('${bsRabiesCheckin.id}')" class="btn  btn-primary btn-sm mr6">登记单</a>
								</div>
								<div class="form-group" >
									<a href="javascript:printInform('${bsRabiesCheckin.id}')" class="btn  btn-primary btn-sm mr6">告知书</a>
								</div>
							</c:if>
							<div class="form-group" >
								<a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckin" class="btn  btn-primary btn-sm mr6">统计管理</a>
							</div>
							<c:if test="${not empty bsRabiesCheckin.id }">
								<div class="form-group" >
									<span style="color:red;font-weight:bold;">${payMessage }</span>
								</div>
							</c:if>
						</form:form>
					</div>
					<div class="col-sm-4 col-lg-3 col-xs-5">
						<sys:message content="${message}" />
						<table id="contentTable"
							class="table table-striped table-bordered table-condensed">
							<thead>
								<c:if test="${bsRabiesCheckin.rabiescode != '0'}">
									<tr>
										<td style="width: 120px" class="text-right">犬伤编码</td>
										<td id="archiveCode">${bsRabiesCheckin.rabiescode}</td>
									</tr>
								</c:if>
								<tr>
									<td style="width: 120px" class="text-right">姓&nbsp;&nbsp;&nbsp;&nbsp;名</td>
									<td id="userName">${bsRabiesCheckin.username}</td>
								</tr>
								<tr>
									<td class="text-right">性&nbsp;&nbsp;&nbsp;&nbsp;别</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.sex, 'sex', '')}</td>
								</tr>
								<c:if test="${not empty bsRabiesCheckin.birthday }">
									<tr>
										<td class="text-right">出生日期</td>
										<td><fmt:formatDate value="${bsRabiesCheckin.birthday}"
												pattern="yyyy-MM-dd" /></td>
									</tr>
								</c:if>
								<tr>
									<td class="text-right">年&nbsp;&nbsp;&nbsp;&nbsp;龄</td>
									<td>${bsRabiesCheckin.age}</td>
								</tr>
								<tr>
									<td class="text-right">详细地址</td>
									<td>${bsRabiesCheckin.homeaddress}</td>
								</tr>
								<tr>
									<td class="text-right">联系电话</td>
									<td>${bsRabiesCheckin.linkphone}</td>
								</tr>
								<tr>
									<td class="text-right">咬伤时间</td>
									<td><fmt:formatDate value="${bsRabiesCheckin.bitedate}"
											pattern="yyyy-MM-dd" /></td>
								</tr>
								<tr>
									<td class="text-right">受伤方式</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.bitetype, 'biteType', '')}</td>
								</tr>
								<tr>
									<td class="text-right">受伤部位</td>
									<td>${bsRabiesCheckin.bitepart}</td>
								</tr>
								<tr>
									<td class="text-right">动物名称</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.animal, 'animal', '')}</td>
								</tr>
								<tr>
									<td class="text-right">处理时间</td>
									<td><fmt:formatDate value="${bsRabiesCheckin.dealdate}"
											pattern="yyyy-MM-dd" /></td>
								</tr>
								<tr>
									<td class="text-right">处理地点</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.dealaddress, 'disposal_sites', '')}</td>
								</tr>
								<tr>
									<td class="text-right">免疫前后</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.expose, 'expose', '')}</td>
								</tr>
								<tr>
									<td class="text-right">暴露级别</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.exposelevel, 'rank', '')}</td>
								</tr>
								<tr>
									<td class="text-right">疫苗名称</td>
									<td>${bsRabiesCheckin.vaccinatename}</td>
								</tr>
								<tr>
									<td class="text-right">规&nbsp;&nbsp;&nbsp;&nbsp;格</td>
									<td>${bsRabiesCheckin.standard}</td>
								</tr>
								<tr>
									<td class="text-right">生产厂家</td>
									<td>${bsRabiesCheckin.manufacturer}</td>
								</tr>
								<tr>
									<td class="text-right">疫苗批号</td>
									<td>${bsRabiesCheckin.batchnum}</td>
								</tr>
								<tr>
									<td class="text-right">是否48小时</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.judgmentTimes, '01', '')}</td>
								</tr>
								<tr>
									<td class="text-right">免疫球蛋白</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.isinoculate, '01', '')}</td>
								</tr>
								<c:if test="${not empty bsRabiesCheckin.weight }">
									<tr>
										<td class="text-right">受种者体重</td>
										<td>${bsRabiesCheckin.weight}</td>
									</tr>
								</c:if>
								<tr>
									<td class="text-right">接种剂量</td>
									<td>${fns:getDictLabel(bsRabiesCheckin.dosage, 'needle_times', '')}</td>
								</tr>
								<c:if test="${not empty bsRabiesCheckin.vaccinatenameNo }">
									<tr>
										<td class="text-right">疫苗名称</td>
										<td>${bsRabiesCheckin.vaccinatenameNo}</td >
									</tr>
								</c:if>
								<c:if test="${not empty bsRabiesCheckin.batchnumNo }">
									<tr>
										<td class="text-right">疫苗批号</td>
										<td>${bsRabiesCheckin.batchnumNo}</td>
									</tr>
								</c:if>
								<c:if test="${not empty bsRabiesCheckin.standardNo }">
									<tr>
										<td class="text-right">疫苗规格</td>
										<td>${bsRabiesCheckin.standardNo}</td>
									</tr>
								</c:if>
								<c:if test="${not empty bsRabiesCheckin.manufacturerNo }">
									<tr>
										<td class="text-right">生产厂家</td>
										<td>${bsRabiesCheckin.manufacturerNo}</td>
									</tr>
								</c:if>
								<c:if test="${bsRabiesCheckin.isinoculate == 1 }">
									<tr>
										<td class="text-right">接种剂量</td>
										<td>${bsRabiesCheckin.dosageNo}支</td>
									</tr>
								</c:if>
								<tr>
									<td class="text-right">备&nbsp;&nbsp;&nbsp;&nbsp;注</td>
									<td>${bsRabiesCheckin.remarks}</td>
								</tr>
								<tr>
									<td class="text-right">既往病史</td>
									<td>${bsRabiesCheckin.history}</td>
								</tr>
							</thead>
						</table>
					</div>
					<div class="col-sm-8 col-lg-9 scroll">
					<c:if test="${not empty NotNumlist }">
						<table id="contentTable" class="table table-bordered table-condensed">
							<thead>
								<tr>
									<th><input type="checkbox" id="CheckedAllNot" /></th>
									<th>针次</th>
									<th>程序接种时间</th>
									<th>实际接种时间</th>
									<th>厂家</th>
									<th>批号</th>
									<th>规格</th>
									<th>状态</th>
									<th>签字</th>
									<th>操作</th>
									<th>打印</th>
									<th>退款</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${NotNumlist}" var="bsRabiesNum" varStatus="vs">
									<tr data-id='${bsRabiesNum.id}'>
										<td><input type="checkbox" name="notitems" value="${bsRabiesNum.id}" /></td>
										<td data-role="vaccinum">${fns:getDictLabel(bsRabiesNum.vaccinum, 'pin', '')}</td>
										<td data-role="vaccidate"><fmt:formatDate value="${bsRabiesNum.vaccidate}" pattern="yyyy-MM-dd" /></td>
										<td data-role="realdate"><fmt:formatDate value="${bsRabiesNum.realdate}" pattern="yyyy-MM-dd " /></td>
										<td data-role="manufacturer">${bsRabiesNum.manufacturer}</td>
										<td data-role="batchnum">${bsRabiesNum.batchnum}</td>
										<td data-role="dose">${bsRabiesNum.dose}</td>
										<td data-role="paystatus" class="${bsRabiesNum.id}" >
											<c:if test="${bsRabiesNum.status != 0}">
												${fns:getDictLabel(bsRabiesNum.status, 'wstatus', '')}
											</c:if>
											<c:if test="${bsRabiesNum.status == 0}">
												${fns:getDictLabel(bsRabiesNum.paystatus, 'paystatus', '')}
											</c:if>
										</td>
										<td data-role="signatureNo" class="${bsRabiesNum.id}1" >
											<c:if test="${bsRabiesNum.signature == 0 || bsRabiesNum.signature == 2}">
												<input data-role="signature" type="button" value="签字" class="btn btn-danger btn-xs" onclick="SignatureId('${bsRabiesNum.id}')">
											</c:if>
											<c:if test="${bsRabiesNum.signature == 1}">
												<span style="color:red;font-weight:bold;">已签字</span>
											</c:if>
										</td>
										<td data-role="btn">
											<c:if test="${empty bsRabiesNum.realdate && bsRabiesNum.wstatus != 1}">
												<input type="button" value="登记" class="btn btn-primary btn-xs" onclick="bsNumFormId('${bsRabiesNum.id}')">
												<a href="javascript:formDelete('${bsRabiesNum.id}')" class="btn btn-danger btn-xs" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
											</c:if>
											<c:if test="${not empty bsRabiesNum.realdate && bsRabiesNum.status != 1 && bsRabiesNum.wstatus != 1}">
												<input type="button" value="登记" class="btn btn-primary btn-xs" onclick="bsNumFormId('${bsRabiesNum.id}')">
												<a href="javascript:formDelete('${bsRabiesNum.id}')" class="btn btn-danger btn-xs" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
											</c:if>
											<c:if test="${(not empty bsRabiesNum.realdate) && bsRabiesNum.status == 1 && bsRabiesNum.nstatus == 0 && bsRabiesNum.wstatus != 1}">
												<input type="button" value="修改" class="btn btn-danger btn-xs" onclick="formUpdateId('${bsRabiesNum.id}')">
											</c:if>
											<c:if test="${(not empty bsRabiesNum.realdate) && bsRabiesNum.status == 1 && bsRabiesNum.nstatus == 1 && bsRabiesNum.wstatus != 1}">
												<input type="button" value="修改" class="btn btn-danger btn-xs" onclick="formUpdateId('${bsRabiesNum.id}')">    
												<%-- <c:choose>  
											          <c:when test="${bsRabiesNum.dataStatus == 0}">  
											               <input type="button" value="修改" class="btn btn-danger" onclick="formUpdateId('${bsRabiesNum.id}')">      
											          </c:when>  
											          <c:otherwise>  
											                        
										         	  </c:otherwise>  
										        </c:choose>  --%> 
											</c:if>
											<c:if test="${bsRabiesNum.wstatus == 1}">
												<input type="button" value="修改" class="btn btn-danger btn-xs" onclick="formUpdateId('${bsRabiesNum.id}')"> 
												<span style="color:red;font-weight:bold;">异地</span>
											</c:if>
										</td>
										<td>
											<input type="button" value="记录单" class="btn btn-primary btn-xs" onclick="invoiceByListId('${bsRabiesNum.id}')">
										</td>
										<td data-refund='${bsRabiesNum.id}'>
											<c:if test="${(empty bsRabiesNum.realdate || bsRabiesNum.status == 0 ) && bsRabiesNum.paystatus == 1}">
												<input type="button" value="退款" class="btn btn-danger btn-xs" onclick="refundById('${bsRabiesNum.id}')">
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:if>
						<table id="contentTable" class="table table-bordered table-condensed">
							<thead>
								<tr>
									<th><input type="checkbox" id="CheckedAll" /></th>
									<th>针次</th>
									<th>程序接种时间</th>
									<th>实际接种时间</th>
									<th>疫苗名称</th>
									<th>厂家</th>
									<th>批号</th>
									<th>规格</th>
									<th>状态</th>
									<th>签字</th>
									<th>操作</th>
									<th>打印</th>
									<th>退款</th>
								</tr>
							</thead>
							<tbody>
							<c:if test="${not empty Numlist }">
								<c:forEach items="${Numlist}" var="bsRabiesNum" varStatus="vs">
									<tr data-id='${bsRabiesNum.id}'>
										<td><input type="checkbox" name="items" value="${bsRabiesNum.id}" /></td>
										<td data-role="vaccinum">${fns:getDictLabel(bsRabiesNum.vaccinum, 'pin', '')}</td>
										<td data-role="vaccidate"><fmt:formatDate value="${bsRabiesNum.vaccidate}" pattern="yyyy-MM-dd" /></td>
										<td data-role="realdate"><fmt:formatDate value="${bsRabiesNum.realdate}" pattern="yyyy-MM-dd " /></td>
										<td data-role="vaccinatename">
											<c:if test="${bsRabiesCheckin.vaccinatename=='狂犬病疫苗(Vero冻干)'}">
													<span style="color:red;font-weight:bold;">${bsRabiesCheckin.vaccinatename}</span>
											</c:if>
											<c:if test="${bsRabiesCheckin.vaccinatename=='狂犬病疫苗(地鼠肾)'}">
													${bsRabiesCheckin.vaccinatename}
											</c:if>
											<c:if test="${bsRabiesCheckin.vaccinatename=='狂犬病疫苗(二倍体)'}">
													${bsRabiesCheckin.vaccinatename}
											</c:if>
											<c:if test="${bsRabiesCheckin.vaccinatename=='狂犬病疫苗(鸡胚)'}">
													${bsRabiesCheckin.vaccinatename}
											</c:if>
											<c:if test="${bsRabiesCheckin.vaccinatename=='狂犬病疫苗(Vero)'}">
													<span style="color:#3507F1;font-weight:bold;">${bsRabiesCheckin.vaccinatename}</span>
											</c:if>
										</td>
										<td data-role="manufacturer">${bsRabiesNum.manufacturer}</td>
										<td data-role="batchnum">${bsRabiesNum.batchnum}</td>
										<td data-role="dose">${bsRabiesNum.dose}</td>
										<td data-role="paystatus" class="${bsRabiesNum.id}">
											<c:if test="${bsRabiesNum.status != 0}">
												${fns:getDictLabel(bsRabiesNum.status, 'wstatus', '')}
											</c:if>
											<c:if test="${bsRabiesNum.status == 0}">
												${fns:getDictLabel(bsRabiesNum.paystatus, 'paystatus', '')}
											</c:if>
										</td>
										<td data-role="signatureNo" class="${bsRabiesNum.id}1">
											<c:if test="${bsRabiesNum.signature == 0 || bsRabiesNum.signature == 2}">
												<input data-role="signature" type="button" value="签字" class="btn btn-danger btn-xs" onclick="SignatureId('${bsRabiesNum.id}',this)">
											</c:if>
											<c:if test="${bsRabiesNum.signature == 1}">
												<span style="color:red;font-weight:bold;">已签字</span>
											</c:if>
										</td>
										<td data-role="btn">
											<c:if test="${empty bsRabiesNum.realdate && bsRabiesNum.wstatus != 1}">
												<input type="button" value="登记" class="btn btn-primary btn-xs" onclick="bsNumFormId('${bsRabiesNum.id}',this)">
												<a href="javascript:formDelete('${bsRabiesNum.id}')" class="btn btn-danger btn-xs" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
											</c:if>
											<c:if test="${not empty bsRabiesNum.realdate && bsRabiesNum.status != 1 && bsRabiesNum.wstatus != 1}">
												<input type="button" value="登记" class="btn btn-primary btn-xs" onclick="bsNumFormId('${bsRabiesNum.id}',this)">
												<a href="javascript:formDelete('${bsRabiesNum.id}')" class="btn btn-danger btn-xs" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
											</c:if>
											<c:if test="${(not empty bsRabiesNum.realdate) && bsRabiesNum.status == 1 && bsRabiesNum.nstatus == 0  && bsRabiesNum.wstatus != 1}">
												<input type="button" value="修改" class="btn btn-danger btn-xs" onclick="formUpdateId('${bsRabiesNum.id}')">
											</c:if>
											<c:if test="${(not empty bsRabiesNum.realdate) && bsRabiesNum.status == 1 && bsRabiesNum.nstatus == 1  && bsRabiesNum.wstatus != 1}">
												<input type="button" value="修改" class="btn btn-danger btn-xs" onclick="formUpdateId('${bsRabiesNum.id}')">    
												<%-- <c:choose>  
											          <c:when test="${bsRabiesNum.dataStatus == 0}">  
											               <input type="button" value="修改" class="btn btn-danger" onclick="formUpdateId('${bsRabiesNum.id}')">      
											          </c:when>  
											          <c:otherwise>  
											                        
										         	  </c:otherwise>  
										        </c:choose>   --%>
											</c:if>
											<c:if test="${bsRabiesNum.wstatus == 1}">
												<input type="button" value="修改" class="btn btn-danger btn-xs" onclick="formUpdateId('${bsRabiesNum.id}')"> 
												<span style="color:red;font-weight:bold;">异地</span>
											</c:if>
										</td>
										<td>
											<input type="button" value="记录单" class="btn btn-primary btn-xs" onclick="invoiceByListId('${bsRabiesNum.id}',this)">
										</td>
										<td data-refund='${bsRabiesNum.id}'>
											<c:if test="${(empty bsRabiesNum.realdate || bsRabiesNum.status == 0 ) && bsRabiesNum.paystatus == 1}">
												<input type="button" value="退款" class="btn btn-danger btn-xs" onclick="refundById('${bsRabiesNum.id}')">
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</c:if>
							</tbody>
						</table>
						<div class="row pull-right fix" style="margin-right: 0">
							<c:if test="${not empty bsRabiesCheckin.id }">
								<div class="form-group" >
									<a href="javascript:formUpdateCheckid()" class="btn btn-sm btn-info">添加针次</a>
								</div>
							</c:if>
						</div>
						<c:if test="${not empty dealList}">
						<table id="contentTable" class="table table-bordered table-condensed">
							<thead>
								<tr>
									<th><input type="checkbox" id="CheckedAllWound" /></th>
									<th>名称</th>
									<th>处理时间</th>
									<th>处理人</th>
									<th>状态</th>
									<!-- <th>操作</th> -->
									<!-- <th>打印</th> -->
									<th>退款</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${dealList}" var="bsRabiesCheckinDeal" varStatus="vs">
								<tr data-id='${bsRabiesCheckinDeal.id}'>
									<td><input type="checkbox" name="witems" value="${bsRabiesCheckinDeal.id}" /></td>
									<td data-role="">伤口处理</td>
									<td data-role="createDate"><fmt:formatDate value="${bsRabiesCheckinDeal.createDate}" pattern="yyyy-MM-dd" /></td>
									<td data-role="createName">${bsRabiesCheckinDeal.createName}</td>
									<td data-role="paystatus" class="${bsRabiesCheckinDeal.id}" >
										<c:if test="${bsRabiesCheckinDeal.paystatus == 1}">
												已付款
										</c:if>
										<c:if test="${bsRabiesCheckinDeal.paystatus == 0}">
										未付款
										</c:if>
									</td>
								<!-- 	<td>
									</td> -->
									<%-- <td data-role="btn"><input type="button" value="记录单" class="btn btn-primary btn-xs" onclick="invoiceByListId('${bsRabiesCheckinDeal.id}')"></td> --%>
									<td data-refund='${bsRabiesCheckinDeal.id}'>
										<c:if test="${(bsRabiesCheckinDeal.status == 0 ) && bsRabiesCheckinDeal.paystatus == 1}">
											<input type="button" value="退款" class="btn btn-danger btn-xs" onclick="refundDealById('${bsRabiesCheckinDeal.id}')">
										</c:if>
										
										
									</td>
								</tr>
								</c:forEach>
							</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>