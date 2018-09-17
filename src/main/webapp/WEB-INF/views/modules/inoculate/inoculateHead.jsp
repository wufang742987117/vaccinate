<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>智慧接种</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta name="author" content="http://jeesite.com/"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Cache-Control" content="no-cache">

	<style type="text/css">
		.numpadWrap{
			margin-top: 10px;
			margin-bottom: 5px;
			padding-left: 50px;
			padding-right: 50px;
			overflow: hidden;
		}
		
		.numPad{
			width: 70px;
			margin-left: 5px;
			margin-right: 20px;			
		}
		.fr{
			float: right;
		}
		.ml10{
			margin-left: 10px;
		}
		.mt4{
			margin-top: 4px;
		}
		
		.slide-tips{
			margin-left: 45px;
			background: url("${ctxStatic}/images/tips.png");
			background-repeat: no-repeat;
			background-position: left;
			text-align: center;
			width: 80px;
			line-height: 20px;
			padding-top: 3px;
			position: relative;
    		top: -2px;
    		cursor: hand;
		}
		
		.slide-tips img{
			margin-top: -3px;
			margin-right: 3px;
		}
		.layui-layer-dialog .layui-layer-padding {padding: 40px 20px 20px 80px!important;text-align: left;font-size: 25px!important;height:auto!important;}
		.layui-layer-dialog .layui-layer-content .layui-layer-ico {top: 38px!important;left: 35px!important;}
		.layui-layer-btn a {height: 35px;line-height: 35px;padding: 0 20px;font-size:16px;}
	</style>
	<%@include file="/WEB-INF/views/include/head1.jsp" %>
	<script src="${ctxStatic}/js/shineMsg.js"></script>
	<script type='text/javascript' src='${ctxBase}/dwr/engine.js'></script>
	<script type='text/javascript' src='${ctxBase}/dwr/util.js'></script>
	<script type='text/javascript' src='${ctxBase}/dwr/interface/signature.js'></script>
	<script type='text/javascript' src='${ctxStatic}/js/signSockect.js?v=10'></script>
	<script>
		window.onload = function() {  
        	dwr.engine.setActiveReverseAjax(true);  
        	var officeCode = '${officeCode}';
        	if(officeCode){
	        	signature.getwebindex("vacc_" + officeCode + "_" + sessionId);
        	}
    	}
    	
    	function waitPay(recs){
	    	signature.getwebindex(sessionId + "_" + recs);  
	    }
	    
	    function recid(dwrmsg){
	    	console.info("签字sessionid-->" + dwrmsg);
	    }
	    
	    /* 签字回调 */
	    function ifSignatureSuccess(msg){  
	    	console.info("签字记录id-->" + msg);
	    	/* do someting */
	    	if(msg != ""){
	    		document.getElementById("inoculateMain").contentWindow.signatureForm(msg);
	    		layer.msg("签字成功",{"icon":1});
	    	}
	    } 
	    
	    function queneWaitNum(msg){
	    	document.getElementById("inoculateMain").contentWindow.queneWaitNum(msg);
	    } 
	    
	    function SignatureId(id,vaccineid,dosage) {
			Send(id, '${host}${ftx}/api/disclosure?vaccid=' + vaccineid);
	    }
	    
	    function signatures(id) {
	    	openWindowsHH("${ctx}/child_vaccinaterecord/childVaccinaterecord/signature?id="+id+"&type=1", "查看签字", "", "");
	    }
	    
	    function openWindowsHH(url, title, w, h) {
			w = isNull(w) ? 1000 : w;
			h = isNull(h) ? 830 : h;
			var ww = ($(document).width() < w ? $(document).width() - 30 : w)+ "px";
			var hh = ($(document).height() < h ? $(document).height() - 30 : h)+ "px";
			layer.open({
				type : 2,
				area : [ ww, hh ],
				title : isNull(title) ? "信息" : [title,'text-align:left;'],
				fix : false, //不固定
				maxmin : false,
				shadeClose : true,
				shade : 0.3,
				shift : 0, //0-6的动画形式，-1不开启
				content : url,
			});
		}
	</script>	
	<!--[if lt IE 9]>
	<meta http-equiv="refresh" content="0;ie.html" />
	<![endif]-->
	<title>${fns:getConfig('productName')}</title>
	<c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
	
	<script type="text/javascript">
	/******** app 语音触发叫号功能  开始*******/
	//叫号
	function callNumbers(){
		document.getElementById("inoculateMain").contentWindow.callNumbers();
	}
	//完成
	function btnFinishApp(){
		document.getElementById("inoculateMain").contentWindow.btnFinishApp();
	}
	//确认完成
	function btnFinishConfirmApp(){
		document.getElementById("inoculateMain").contentWindow.btnFinishConfirmApp();
	}
	
	//
	function btnDefaultApp(){
		document.getElementById("inoculateMain").contentWindow.btnDefaultApp();
	}
 	
 	//打开下拉选择
	function openSelectApp(i,title){
		document.getElementById("inoculateMain").contentWindow.openSelectApp(i,title);
	}
	
	//选择下拉选项
	function selSelectApp(i,j){
		document.getElementById("inoculateMain").contentWindow.selSelectApp(i,j);
	}
	
	//过号
	function doPassApp(){
		document.getElementById("inoculateMain").contentWindow.doPassApp();
	}
	
	//点击取消
	function doCancelApp(){
		document.getElementById("inoculateMain").contentWindow.doCancelApp();
	}
	
	//确认取消
	function confirmCancelApp(){
		document.getElementById("inoculateMain").contentWindow.confirmCancelApp();
	}
	
	//关闭弹框
	function closeAlertApp(){
		document.getElementById("inoculateMain").contentWindow.closeAlertApp();
	}
	
	
	/******** app 语音触发叫号功能  结束*******/
	
	$(function(){
		$(".numPad").mouseover(function(){
			var v = $(this).val();
			$(this).focus().val(v);
		});
		
		$(".numPad").keyup(function(e){
			var v = $(this).val();
			/* 文本框内不是数字则不执行 */
			if(isNaN(v)){
				return;
			}
			if(e.keyCode == 40 || e.keyCode == 39){
				v--;
			}else if(e.keyCode == 38 || e.keyCode == 37){
				v++;
			}
			$(this).val(v<0?0:v);
		});
		
			
			$("#back").click(function(){
// 				$("#").location.href = '${ctx}/inoculate/quene/inoculateMain';
// 				document.getElementById("inoculateMain").src="${ctx}/inoculate/quene/inoculateMain";
				window.location.reload();
				$(this).hide();
				$("#back2Index").show();
			});
	});
		
		function minus(id){
			$(".numPad").each(function(){
				if($(this).attr("data-id") == id){
				var n = $(this).val()-1;
					$(this).val(n < 0 ? 0 : n);
				}
				/* $(this).val($(this).val()-1); */
			});
		}
		
		function say(str){
// 			alert("弹出层 " + str);
			console.info("弹出层 " + str);
		}
		
		function showBack(){
			$("#back").show();
			$("#back2Index").hide();
		}
		
		function reflush(){
			window.location.href= "${ctx}/inoculate/quene/inoculateIndex";
		}
			
		function closeFormId(id){
			layer.closeAll();
		}
		
		/* data-stat=0 开启状态 */
		/* data-stat=1 关闭状态 */
		var padheight;
		$(function(){
			$(".slide-tips").click(function(){
				if(!padheight){
					padheight = $(".numpadWrap").height();
				}
				if($(this).attr("data-stat") == '0'){
// 					开启状态
					$(".numpadWrap").stop(true,true).animate({ "height":"40"},500);
					setTimeout(function() {
						$(".slide-tips").find("span").html("展开");/* 收起变成展开 */
						$(".slide-tips").find("img").css("transform","rotate(180deg)");
					}, 500);
					$(this).attr("data-stat",'1');
				}else{
// 					关闭状态
					$(".numpadWrap").stop(true,true).animate({ "height":padheight},500);
					setTimeout(function() {
						$(".slide-tips").find("span").html("收起");/* 收起变成展开 */
						$(".slide-tips").find("img").css("transform","rotate(0deg)");
					}, 500);
					$(this).attr("data-stat",'0');
				}
			});	
			setTimeout(function() {
				$(".slide-tips").click();
			}, 100);
			
		});
		
		function clearSessionStorage(){window.sessionStorage.queueno=""}
	</script>
	
</head>
<body class="fixed-sidebar full-height-layout gray-bg text-right" style="overflow:hidden">
	<div class="numpadWrap" >
		<div class="row">
			<c:if test="${not empty indatelist }">
				<div class="col-xs-12" style="padding-right: 500px;">
					<div style="float: left;margin-top: 10px;color: f75c2f; font-weight: bold;">以下疫苗即将过期：</div>
					<c:forEach items="${indatelist}"  var="v">
						<div style="float: left;margin-top: 10px;color: f75c2f">${ v.vaccName} [ ${v.batchno}-${v.manufacturer} ]&emsp;&emsp;</div>
					</c:forEach>
				</div>
			</c:if>
			<div class="pull-right" style="width: 480px; position: absolute; top: 12px; right: 40px; z-index:998;">
			<!-- onclick="return confirmx('返回将清空计数板数据,确认退出？', this.href)" -->
<%-- 				<a href="${ctx}" onclick="clearSessionStorage()" id="back2Index" class="btn btn-success fr ml10 mt4" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a> --%>
				<div class="btn-group" id="back2Index">
					<a type="button" class="btn btn-default w100" onclick="javascript:loading('正在跳转...');" href="${ctx}/child_vaccinaterecord/childVaccinaterecord1">登记台</a>
				    <a type="button" class="btn btn-success" onclick="javascript:void(0);" >儿童接种台</a>
				    <a type="button" class="btn btn-default" onclick="javascript:loading('正在跳转...');" href="${ctx}/rabiesvaccine/bsRabiesCheckin">狂犬病免疫</a>
				    <a type="button" class="btn btn-default" onclick="javascript:loading('正在跳转...');" href="${ctx}/hepatitis/bsHepatitisBcheckin">成人免疫</a>
				    <a type="button" class="btn btn-default w100" onclick="javascript:loading('正在跳转...');" href="${ctx}">首页</a>
				</div>
				<button class="btn btn-primary fr ml10 mt4" style="display: none;" id="back"><span class="glyphicon glyphicon-arrow-left"></span>返回接种台</button>
			</div>
			<div class="col-xs-12" style="padding-right: 500px;">
				<c:forEach items="${productList}"  var="v">
					<div style="float: left;margin-top: 10px;min-width: 300px;">${ v.name}[${ v.batchno}]<input data-id="${v.id }" class="numPad" type="text" value="${v.storenum}" readonly></div>
				</c:forEach>
			</div>
			
			
		</div>
		
	</div>
	<div style="height: 0px;">
		<div class="slide-tips" data-stat=0>
			<img width="10" src="${ctxStatic}/images/icon_slidedown.png"><span>收起</span>
		</div>
	</div>
	<div class="row J_mainContent" id="content-main">
		<iframe id="inoculateMain" class="J_iframe" name="iframe1" width="100%" height="100%" src="${ctx}/inoculate/quene/inoculateMain" frameborder="0" seamless></iframe>
	</div>

</body>
</html>

