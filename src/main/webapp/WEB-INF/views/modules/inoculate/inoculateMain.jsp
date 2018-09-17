<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接种台</title>
<style type="text/css">
	.numbertitle span{font-size: 50px !important; margin: 10px !important; } .ml2{margin-left:2px; } .mr100{margin-right: 100px; } /* .finished{color:#999; } */ .chooseed{color:#ef0000; font-weight: bold; background: rgba(29, 132, 198, 0.2) !important; } .chooseed>td{font-size: 1.4em !important; } .normal{color:black; } .scroll{height:500px; overflow: auto; } .rechoosed{background: rgba(131, 102, 241, 0.32) !important; } .tips{font-size: 20px; } .btn-top{margin-top: 14px; } .btn-top .btn{margin-right: 15px; float: right; } .text-circle{display: block; margin: 0 auto; width: 18px; height: 18px; border: 1px #000 solid; border-radius: 9px; font-size: .8em; text-align: center; } /* contextMenu */ .dropdown-menu{font-size: 16px; } .dropdown-menu>li>a{line-height: 20px; } .dropdown-menu>li>a{padding: 2px 5px; } .nav-header{padding: 2px 5px; background: #ccc; } /* end contextMenu */ table th,#contentTableD td{word-break: keep-all;white-space:nowrap;padding:1px 1px;}
	.layui-layer-dialog .layui-layer-padding {padding: 40px 20px 20px 80px!important;text-align: left;font-size: 20px!important;}
	.layui-layer-dialog .layui-layer-content .layui-layer-ico {top: 35px!important;left: 35px!important;}
	.layui-layer-btn a {height: 35px!important;line-height: 35px!important;margin: 0 6px;padding: 0 15px;border: 1px solid #dedede;background-color: #f1f1f1;color: #333;border-radius: 2px;font-weight: 400;cursor: pointer;text-decoration: none;}
	.layui-layer-dialog .layui-layer-padding {padding: 38px 20px 34px 81px!important;text-align: left;font-size: 20px!important;
}
</style>
<!-- <script src="${ctxStatic}/js/queneContextMenu.js" type="text/javascript"></script>   -->
<meta name="decorator" content="default" />
<c:if test="${not empty quene.queueno}">
	<script type="text/javascript">
		parent.flash_title(${quene.queueno});
	</script>
</c:if>
<script type="text/javascript">
	var childid = "${childBaseinfo.id}";
	var childcode = "${childBaseinfo.childcode}";
	var queueno = "${quene.queueno}";
	if(queueno){
		window.sessionStorage.queueno = queueno;
	}
	
	/******** app 语音触发叫号功能  开始*******/
	function callNumbers(){
		disSendApp();
		if($("#selected").val()){
			$.ajax({
				url:"${ctx}/inoculate/quene/callNumber?queueno=${quene.queueno}",
				methed:"POST",
				success:function(data){
					debugger;
					layer.msg(data,{icon:1,"time":800000});
				}
			});
		}else if(childid){
			window.location.href="${ctx}/inoculate/quene/inoculateMain";
		}else{
			layer.msg("当前队列已完成", {icon: 1});  		
		}
	}
	
	//按钮倒计时
	var countdownApp;
	var countApp = 300000;
	function disSendApp(){
		countApp = 3000000;
		$("#callNumber").attr("disabled", true);
		$("#callNumber").html(countApp + "秒后重新叫号");
		countdownApp = setInterval(countDownApp, 10000000);
	}
	
	function countDownApp(){
		countApp--;
		$("#callNumber").html(countApp + "秒后重新叫号");
		if (countApp <= 0) {
			$("#callNumber").removeAttr("disabled");
			$("#callNumber").html("叫号");
			clearInterval(countdownApp);
		}
	}
	
	var finishCount = 0;
	function btnFinishApp(){
		if($("#selected").val()){
			/*if($(".option .recSign").attr("data-sign") == '0' && preference.signReq=='1'){
				warning("当前排号还未签字，请先签字");
				$(".option .recSign").find("input").click();
				return ;
			}*/
			finishCount ++;
			if(finishCount <= 1){
				alertFormApp('${ctx}/inoculate/quene/confirm?queueno=${quene.queueno}', '选择接种部位');
			}
		}else{
			layer.msg("当前队列已完成", {icon: 1});    
		}
	}
	
	function alertFormApp(url,title){
	 	layer.open({
	        type: 2,
	        area: [ww,hh],
	        title: isNull(title) ? "信息" : title,
	        fix: false, //不固定
	        maxmin: false,
	        shade: 0.3,
	        shift: 0, //0-6的动画形式，-1不开启
	        content: url,
			end: function(){
				finishCount = 0;
			} 
	    });
	}
	
	function btnFinishConfirmApp(){
		var iframe = $("iframe");
		if(iframe && iframe.length > 0){
			if(iframe[0].contentWindow.btnFinishConfirmTrueApp){
				iframe[0].contentWindow.btnFinishConfirmTrueApp();
			}
		}
	}
	
	function btnDefaultApp(){
		var url = "${ctx}/inoculate/quene/doCancel?queueno=${quene.queueno}";
		return confirmxApp('确认要取消该排号吗', url);
	}
	
	function confirmxApp(mess, href) {
		if (document.getElementById('selected').value) {
			layer.confirm(mess, {
				btn : [ '确认', '取消' ], //按钮
				shade : true, //不显示遮罩
				icon : 3,
				area : [ '500px', '200px' ]
			}, function(index) {
				layer.close(index);
				loading("正在执行...");
				if (typeof href == 'function') {
					href();
				} else {
					window.sessionStorage.queueno="";
					location = href;
				}
			}, function() {
				layer.close();
			});
		} else {
			layer.msg("当前队列已完成", {icon : 1 });
		}
		return false;
	}
	
	//点击选择框
	function openSelectApp(i,title){
		var iframe = $("iframe");
		if(iframe && iframe.length > 0){
			if(iframe[0].contentWindow.openSelect){
				iframe[0].contentWindow.openSelect(i,title);
			}
		}
	}
	
	//选择选择框
	function selSelectApp(i,j){
		var iframe = $("iframe");
		if(iframe && iframe.length > 0){
			if(iframe[0].contentWindow.selSelect){
				iframe[0].contentWindow.selSelect(i,j);
			}
		}
	}
	
	//过号
	function doPassApp(){
		if (document.getElementById('selected').value) {
			window.location.href = "${ctx}/inoculate/quene/doPass?queueno=${quene.queueno}";
		} else {
			layer.msg("当前队列已完成", {icon : 1});
		}
		return false;
	}
	
	//点击取消
	function doCancelApp(){
		comconfirmx('确认要取消该排号吗？', "");
	}
	
	//确认取消
	function confirmCancelApp(){
		if (document.getElementById('selected').value) {
			window.location.href = "${ctx}/inoculate/quene/doCancel?queueno=${quene.queueno}";
		}else{
			layer.msg("当前队列已完成", {icon : 1 });
		}
	}
	
	//关闭弹出层
	function closeAlertApp(){
		layer.closeAll();
	}
	
	/******** app 语音触发叫号功能  结束*******/
	
	$(document).ready(function() {
	
		//非正常叫号，显示下一号
		if(!queueno && childcode){
			$(".option-normal").hide();
			$(".option-ajax").show();
		}
	
		$("#callNumber").click(function(){
			disSend();
			if($("#selected").val()){
				$.ajax({
				url:"${ctx}/inoculate/quene/callNumber?queueno=${quene.queueno}",
				methed:"POST",
				success:function(data){
					//debugger;
					layer.msg(data,{icon:1});
				}
			});
			}else if(childid){
				window.location.href="${ctx}/inoculate/quene/inoculateMain";
			}else{
				layer.msg("当前队列已完成", {icon: 1});    
			}
		});
		
		$(".option").dblclick(function(){
			if($(".option .recSign").attr("data-sign") == '0' && preference.signReq=='1'){
				warning("当前排号还未签字，请先签字");
				$(".option .recSign").find("input").click();
				return ;
			}
 			alertForm('${ctx}/inoculate/quene/confirm?queueno=${quene.queueno}','选择接种部位');
 		});

		/* 点击回车 直接选择接种部位 */
		$(document).keydown(function(event){ 		
			if(event.keyCode == 13){ 
				$(".option").dblclick();
			}
		}); 

		/* 完成 */
		$("#btnFinish").click(function(){
			if($("#selected").val()){
				if($(".option .recSign").attr("data-sign") == '0' && preference.signReq=='1'){
					warning("当前排号还未签字，请先签字");
					$(".option .recSign").find("input").click();
					return ;
				}
				alertForm('${ctx}/inoculate/quene/confirm?queueno=${quene.queueno}', '选择接种部位');
			}else{
				layer.msg("当前队列已完成", {icon: 1});    
			}
		});
		
		/* 完成 */
		$(".btnAddVacc").click(function(){
			if(childcode){
				alertForm2('${ctx}/child_vaccinaterecord/childVaccinaterecord/addVacc?childcode='+childcode, '单剂录入');
			}else{
				layer.msg("当前队列已完成", {icon: 1});    
			}
		});
		
		var opt = "${not empty bsNum.group ? '' : 'empty' }";
		//若显示信息，也不刷新
		if(childid){
			opt = "";
		}
		if(opt){
			var i = 0;
			time = setInterval(function(){
				$.ajax({
					url:"${ctx}/inoculate/quene/checkQuene",
					success:function(data){
						console.info("当前系统空闲" + (i+=5) + "秒");
						if(data){
							window.location.href="${ctx}/inoculate/quene/inoculateMain"
						}
					},
				});
			},5000);
		}
		
		//按钮倒计时
		var countdown;
		var count = 3;
		function disSend() {
			count = 3;
			$("#callNumber").attr("disabled", true);
			$("#callNumber").html(count + "秒后重新叫号");
			countdown = setInterval(CountDown, 1000);
		}

		function CountDown() {
// 			$("#callNumber").attr("disabled", true);
			count--;
			$("#callNumber").html(count + "秒后重新叫号");
			if (count <= 0) {
				$("#callNumber").removeAttr("disabled");
				$("#callNumber").html("叫号");
				clearInterval(countdown);
				
			}
		}
	});

	/* 完成 */
	function doFinish(){
		var v = document.getElementById('selected').value;
    	var p = document.getElementById('position').value;
    	var pid = document.getElementById('pid').value;
    	var isnew = document.getElementById('isnew').value;
    	if(!v){	    		
    		layer.msg("数据错误，请重新排号", {icon: 2});
    		return;
    	}
    	if(!p){
    		layer.msg("请选择接种部位", {icon: 2});    		
    		return;
    	}
    	var href = "${ctx}/inoculate/quene/doFinish?queueno=${quene.queueno}&selected=#selected#&pid=#pid#&position=#position#&isnew=#isnew#";
    	href = href.replace("#selected#",v).replace("#pid#",pid).replace("#position#",p).replace("#isnew#",isnew);    	
    	if(isnew == 1){
			/* 计数板减一 */
			parent.minus(document.getElementById('pid').value); 
		}		
   		window.sessionStorage.setItem(queueno, '');   	
    	loading("正在执行...");        
    	   
    	if(preference && preference.queueRefresh == 1){
			location = href;
    	}else{
        	var api = "${ctx}/inoculate/quene/doFinishApi";
	     	$.ajax({
	     		url:api,
	     		data:{"queueno":"${quene.queueno}","selected":v,"pid":pid,"position":p,"isnew":isnew},
	     		success:function(data){
	     			if(data[0] == 200){
	     				layer.msg("保存成功",{"icon":1});
						$(".chooseed .recDate").html(data[1].vaccinatedate.substr(0,10));
						$(".chooseed .recPosition").html(data[1].bodypart);
						$(".chooseed .recVaccName").html(data[1].vaccName);
						$(".chooseed .recVacctype").html(data[1].vacctype);
						$(".chooseed .recExpDate").html(data[1].vaccExpDate);
						$(".chooseed .recManufacturer").html(data[1].manufacturer);
						$(".chooseed .recBatch").html(data[1].batch);
						$(".chooseed").css("background", "rgb(118, 218, 118);");
						$(".option-normal").hide();
						$(".option-ajax").show();
	     			}else if(data[0] == 500){
	     				layer.msg(data[1],{"icon":2});
	     			}
	     		}
	     	});
    	}

	}
	
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function submitYouFrom() {
		/* $("#searchForm").action = path; */
		$("#searchForm").submit();
	}
	
	var newFrame;
	function openWindows(url,title,isReflash){
		newFrame = layer.open({
	        type: 2,
	        area: [$(document).width()-200+'px',$(document).height()-100+'px'],
	        title: isNull(title) ? "信息" : title,
	        fix: false, //不固定
	        maxmin: false,
	        shade: 0.3,
	        shift: 0, //0-6的动画形式，-1不开启
	        content: url,
	        closeBtn:1,
	    });
	    //layer.full(i);
	    
	}
	
	 var ww;
	 var hh;
	 
	 $(function(){
	 	ww = ($(document).width() < 400 ? $(document).width()-30 : 400) + "px";
		hh = ($(document).height() < 480 ? $(document).height()-30 : 480) + "px";
	 })
	 
	 function alertForm(url,title){
	 	layer.open({
	        type: 2,
	        area: [ww,hh],
	        title: isNull(title) ? "信息" : title,
	        fix: false, //不固定
	        maxmin: false,
	        shade: 0.3,
	        shift: 0, //0-6的动画形式，-1不开启
	        content: url,		       
	    });
	}
		
	function alertForm2(url,title){
		ww = ($(document).width() < 500 ? $(document).width()-30 : 500) + "px";
		hh = ($(document).height() < 700 ? $(document).height()-30 : 700) + "px";
		alertForm(url,title);
		ww = ($(document).width() < 400 ? $(document).width()-30 : 400) + "px";
		hh = ($(document).height() < 480 ? $(document).height()-30 : 480) + "px";
	}
	
	function alertSetting(){
		ww = ($(document).width() < 700 ? $(document).width()-30 : 700) + "px";
		hh = ($(document).height() < 600 ? $(document).height()-30 : 600) + "px";
		alertForm('${ctx}/sys/user/userSetting', '设置用户首选项',800,800);
		ww = ($(document).width() < 400 ? $(document).width()-30 : 400) + "px";
		hh = ($(document).height() < 480 ? $(document).height()-30 : 480) + "px";
	}
	
	//重新叫号
	function reCallNum(url){
		//layer.close(newFrame);
		layer.closeAll();
		window.location.href=url;
	}

	//关闭所以弹出层
	function closeAll(){
		layer.closeAll();
		document.getElementById("clickme").onclick();
	}
	
	function closeForm(){
		layer.closeAll();
		parent.reflush();
	}

	//确认接种部位
	function confirmp(id,pid,isnew){			
		document.getElementById("position").value = id;	
		document.getElementById("isnew").value = isnew;	
		if(pid){
			document.getElementById("pid").value = pid;		
		}	
		layer.closeAll();
		doFinish();
	}
	
	function showBack(){
		parent.showBack();
	}
	
	//过号,取消二次确认
	function comconfirmx(mess, href, closed) {
		if (document.getElementById('selected').value) {
			layer.confirm(mess, {
				btn : [ '确认取消', '关闭' ], //按钮 
				shade : true, //不显示遮罩
				icon : 3,
				area : [ '500px', '200px' ]
			}, function(index) {
				layer.close(index);
				loading("正在执行...");
				if (typeof href == 'function') {
					href();
				} else {
					window.sessionStorage.queueno="";
					location = href;
				}
			}, function() {
				layer.close();
			});

		} else {
			layer.msg("当前队列已完成", {icon : 1 });
		}
		return false;
	}
	
	function checkx(href) {
		if (document.getElementById('selected').value) {
			window.sessionStorage.queueno="";
			window.location.href = href;
		} else {
			layer.msg("当前队列已完成", {icon : 1});
		}

		return false;
	}
	
	$(function(){
		var lastTime;
		var lastTr = new Array();
		$(".recDate").each(function(i,t){
			if($(this).html()){
				if(!lastTime){
					lastTime = Date.parse($(this).html());
				}
				var d = Date.parse($(this).html());
				if(d > lastTime){
					lastTr = new Array();
					lastTime = d;
					lastTr.push($(this).parent());
				}
				if(d == lastTime){
					lastTr.push($(this).parent());
				}
				if(SimpleDateFormat(new Date(),"yyyy-MM-dd") == $(this).html()){
					$(this).parent().css("color","red");
				}
			}
		});
		if(lastTr){
			$.each(lastTr, function(i,t){
				t.css("background", "rgb(255, 213, 177)");
				t.css("font-weight", "bold");
			});
		}
		
		$("#queneno-serach").click(function(){
			if($("#queneno-no").val()){
				if($("#queneno-no").val().length < 6){
					$.ajax({
						url:"${ctx}/inoculate/quene/checkQueneNo",
						data:{"queueno":$("#queneno-no").val()},
						success:function(data){
							if(data){
								location.href = "${ctx}/inoculate/quene/inoculateMain?queueno=" + data;
							}else{
								layer.msg("该排号不存在",{"icon":7});
							}
						},
					});
				}else{
					 openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord1/childlist?childcode="+$("#queneno-no").val()+"&searchBirth="+$("#queneno-no").val(),"儿童列表", "", "");
					 $("#queneno-serach").attr("disable","disable");
				}
			}
		});
		
		$("#queneno-no").keydown(function(e){
			if(e.keyCode == 13){
				$("#queneno-serach").click();
				return false; 
			}
		});
		
		$("#queneno-no").focus();
	})

	//查询成功
	function closeForm1(childcode) {
		reCallNum("${ctx}/inoculate/quene/inoculateMain?childid=" + childcode);
	}
	//查询失败
	function closeForm0(){
		layer.closeAll();
		layer.msg("未查询到个案记录！",{"icon":7});
	}
	
	//改变子类元素
	function signatureForm(msg){
		$("td[data-signature="+msg+"]").html('<input  type="button" value="已签字" class="btn btn-xs btn-primary" onclick="parent.signatures(\'' + msg + '\')" >');
		$("td[data-signature="+msg+"]").attr("data-sign",'1');
		
	}
	
	function queneWaitNum(msg){
		$("#waitNum").html("人数:" + msg);
	}
	
		/* 补录记录 */
	function addOfflineRecord() {
		if(childcode){
				ww = ($(document).width() < 800 ? $(document).width()-30 : 800) + "px";
				hh = ($(document).height() < 800 ? $(document).height()-30 : 800) + "px";
				alertForm(ctx + "/child_vaccinaterecord/childVaccinaterecord/addRecord?childid=${childBaseinfo.id}&childcode=${childBaseinfo.childcode}","补录接种记录");
				ww = ($(document).width() < 400 ? $(document).width()-30 : 400) + "px";
				hh = ($(document).height() < 480 ? $(document).height()-30 : 480) + "px";
			}else{
				layer.msg("当前队列已完成", {icon: 1});    
			}
	}
	
	$(function(){
		if(window.sessionStorage.contentTableD){
			
		}else{
			window.sessionStorage.contentTableD=preference.contentTableD;		
		}
		var onSampleResized = function(e){
			var tableSize = "";
			var tabletotal=0;
			var columns = $(e.currentTarget).find("th");
			columns.each(function(){ tableSize += $(this).width() + ";";tabletotal+=$(this).width()*1 })
			$("#collen").val(tableSize+tabletotal); 
			preference.contentTableD=tableSize+tabletotal;
		};	
		$("#contentTableD").colResizable({
			liveDrag:true, 
			gripInnerHtml:"<div class='grip'></div>", 
			draggingClass:"dragging", 
			onResize: onSampleResized,
			partialRefresh:true,
			postbackSafe: true
		});
		$("#contentTableD th").on('contextmenu', function(e) {
			var items = [
				{ title: '保存设置', icon: '', fn: function(){
					$.ajax({
						url:"${ctx}/sys/user/saveUserSetting",
						data:{"preference":JSON.stringify(preference)},
						success:function(data){
							if(data.code==200){
								layer.msg("保存成功",{"icon":1});
								setTimeout(function() {
									parent.layer.closeAll();
								}, 1000)
							}else{
								layer.msg("保存失败", {"icon":7});
								console.error(data.msg);
							}
						},
					});
					}
				}
			]
			basicContext.show(items, e.originalEvent)
		});
		
		
		$("#contentTableD td").on('contextmenu', function(e) {
			var _thi=$(this).parent("tr");
			var tt=_thi.find(".recVaccName").html() + " 第" + _thi.find(".text-circle").html() + "剂";
			var items = [
				{title: tt, icon: '',fn:function(){}},
				{title:'修改',icon: '', fn: function(){
		        	if(!_thi.find(".recDate").html()){
		        		layer.msg(_thi.find(".recVaccName").html() + " 第" + _thi.find(".text-circle").html() + "剂 尚未完成，不能修改！",{"icon":7});
		        		return ;
		        	}
		        	openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord?id="+_thi.attr("data-id"), "修改接种记录", "", "");
		        	
				}},
				{title:'删除',icon: '', fn: function(){
		        	if(!_thi.find(".recDate").html()){
		        		layer.msg(_thi.find(".recVaccName").html() + " 第" + _thi.find(".text-circle").html() + "剂 尚未完成，不能修改！",{"icon":7});
		        		return ;
		        	}
		        	var txt = "删除异地的接种记录将不回退库存<br>"
		        	if(_thi.attr("data-source") != 3){
		        		txt = "删除今日以前的记录库存将不回退库存<br>";
		        		if(SimpleDateFormat(new Date(),"yyyy-MM-dd") == _thi.find(".recDate").html()){
			        		txt = "删除今日的接种记录将回退库存<br>"
			        	}
		        	}   	
		        	layer.confirm(txt + "确认删除  <strong>" + _thi.find(".recVaccName").html() + " 第" + _thi.find(".text-circle").html() + "剂？</strong>",
		        			{ 
		        				btn: ['确认','取消'], //按钮
				    	        shade: true, //不显示遮罩
				    	        icon : 3,
				    	        area : ['500px' , '200px']
	        				},
		        			function(){
	        					$.ajax({
	        						url:ctx + "/child_vaccinaterecord/childVaccinaterecord/deleteFromQuene?id=" + _thi.attr("data-id"),
	        						type:"DELETE",
	        						success:function(data){
	        							if(data == 200){
	        								layer.msg("删除成功",{"icon":1, "time":800});
	            			        		setTimeout(function() {
	            			        			location.href = ctx + "/inoculate/quene/inoculateMain?childid=" + childcode;
	            			        		}, 800)
	        							}else{
	        								layer.msg("删除失败",{"icon":2, "time":800});
	        							}
	        						},
	        						error:function(){
	        							layer.msg("删除失败",{"icon":2, "time":800});
	        						}
	        					});
				        	});
				}}
			]
			basicContext.show(items, e.originalEvent)
		});
		
	});	
		

</script>
</head>
<body>
	<div class="ibox">
		<div class="row col-sm-12">
			<div class="col-xs-6">
				<form:form id="searchForm" modelAttribute="childVaccinaterecord"
					action="${ctx}/child_vaccinaterecord/childVaccinaterecord1/list1"
					method="post" class="form-inline">
					<div class="form-group numbertitle">
						<span>当前排号:</span><span style="color:red">${not empty quene ? quene.queueno : '' }</span>
						<c:if test="${not empty quene.queueno}">
							<span style="font-size: 15px !important;" id="waitNum">人数:${(queneN==0)?0:queneN-1}</span>
						</c:if>
					</div>
				</form:form>
			</div>
			<div class="col-xs-6 btn-top option-normal"> 
				<a class="btn btn-lg btn-default" href="${ctx}/inoculate/quene/doCancel?queueno=${quene.queueno}" onclick="return comconfirmx('确认要取消该排号吗？', this.href)">取消</a>
				<a class="btn btn-lg btn-warning" href="${ctx}/inoculate/quene/doPass?queueno=${quene.queueno}" onclick="return checkx(this.href)">过号</a>
				<button class="btn btn-lg btn-success btnFinish" id="btnFinish" >完成</button>
				<button class="btn btn-lg btn-primary" id="callNumber">叫号</button>	
			</div>
			<div class="col-xs-6 btn-top option-ajax" style="display: none;"> 
				<a class="btn btn-lg btn-primary" href="${ctx}/inoculate/quene/inoculateMain?callNumber=true" >下一号</a>
				<button type="button" class="btn btn-lg btn-success btnAddVacc">单剂录入</button>&nbsp;&nbsp;
			</div>
		</div>
		<div class="row ibox-content" >
			<div class=" col-sm-3">
				<div class="form-group">
					<div class="input-group" style="width: 100%;">
						<span class="input-group-addon gray-bg text-right" style="width: 100px">编码&nbsp;/&nbsp;生日</span> 
						<input id="queneno-no" maxlength="6" class="form-control" style="" placeholder="4位排号 / 6位儿童编码 / 6位生日" />
						<input type="hidden" id="queneno-serach" style="height: 24px; padding: 2px 3px;margin-left: 3px;" class="btn btn-primary" value="查询"/>
					</div>
				</div>
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<td style="width: 100px" class="text-right">儿童编码</td>
							<td>${childBaseinfo.childcode}</td>
						</tr>
						<%-- <tr>
							<td class="text-right">身份证号</td>
							<td>${childBaseinfo.cardcode}</td>
						</tr> --%>
						<tr>
							<td class="text-right">出生证号</td>
							<td>${childBaseinfo.birthcode}</td>
						</tr>
						<tr>
							<td class="text-right">儿童姓名</td>
							<td>${childBaseinfo.childname}<c:if test="${not empty childBaseinfo.mouage }"><span style="color: red; font-weight: bold;">（${childBaseinfo.mouage 	}）</span></c:if></td>
						</tr>
						<tr>
							<td class="text-right">性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别</td>
							<td>${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}</td>
						</tr>
						<tr>
							<td class="text-right">出生日期</td>
							<td><fmt:formatDate value="${childBaseinfo.birthday}" pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td class="text-right">出生医院</td>
							<td>${childBaseinfo.birthhostipal}</td>
						</tr>
						<tr>
							<td class="text-right">出生体重</td>
							<td>${childBaseinfo.birthweight}&nbsp;克</td>
						</tr>
						<tr>
							<td class="text-right">母亲姓名</td>
							<td>${childBaseinfo.guardianname}</td>
						</tr>
						<tr>
							<td class="text-right">手机号码</td>
							<td>${childBaseinfo.guardianmobile}</td>
						</tr>
						<tr>
							<td class="text-right">身份证号</td>
							<td>${childBaseinfo.guardianidentificationnumber}</td>
						</tr>
						<%-- <tr>
							<td class="text-right">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系</td>
							<td>${fns:getDictLabel(childBaseinfo.guardianrelation, 'relation', '')}</td>
						</tr> --%>
						<tr>
							<td class="text-right">家庭住址</td>
							<td>${childBaseinfo.homeaddress}</td>
						</tr>
						<tr>
							<td class="text-right">户籍地址</td>
							<td>${childBaseinfo.registryaddress}</td>
						</tr>
						<%-- <tr>
							<td class="text-right">异常反应</td>
							<td>${fns:getDictLabel(childBaseinfo.paradoxicalreaction, 'FN', '')}
							</td>
						</tr> --%>
						<tr>
							<td class="text-right">接种单位</td>
							<td>${childBaseinfo.officeinfo}</td>
						</tr>
						<tr>
							<td class="text-right">备注</td>
							<td>${childBaseinfo.remarks}</td>
						</tr>
						
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<!-- 接种记录 -->
			<div style="display:none" id="clickme"></div>
			<div class="col-sm-9">
				<input id = "selected" type="hidden" value="${quene.vaccineid}"/>
				<input id = "vaccID" type="hidden" value="${bsNum.group}"/>
				<input id = "pid" type="hidden" value="${quene.pid}"/>
				<input id = "position" type="hidden" value=""/>
				<input id = "isnew" type="hidden" value=""/>
				<input id="collen" type="hidden" value=""/>
				<table id="contentTableD" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th class="text-center">种类</th>
							<th class="text-center">剂次</th>
							<th class="text-center">名称</th>
							<th class="text-center">接种类型</th>
							<th class="text-center">接种日期</th>
							<th class="text-center">接种部位</th>
							<th class="text-center">批次</th>
							<th class="text-center">有效期</th>
							<th class="text-center">生产企业</th>
							<th class="text-center">接种单位</th>
							<th class="text-center">付款状态</th>
							<th class="text-center">签字状态</th>
							<!-- <th>接种单位</th> -->
							<!-- <th>医生姓名</th> -->
							<%-- <shiro:hasPermission name="child:cord:edit">
								<th>操作</th>
							</shiro:hasPermission> --%>
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${records}" var="childVaccinaterecord" varStatus="st" >
								<tr id="rec${st.index}" data-id="${childVaccinaterecord.id}" data-source="${childVaccinaterecord.source }"
									<c:if test="${childVaccinaterecord.status == 0 and quene.vaccineid == childVaccinaterecord.nid}">
										class="chooseed option"
									</c:if>									
								>
								<c:if test="${not empty childVaccinaterecord.size &&  childVaccinaterecord.size != 0}">
									<td rowspan="${childVaccinaterecord.size}"
										<c:if test="${childVaccinaterecord.status=='8' }"> style="background:url('${ctxStatic}/images/dismantle.png') no-repeat right bottom" </c:if> 
									>${childVaccinaterecord.vaccCode}</td>
								</c:if>
								<td><span class="text-circle">${childVaccinaterecord.dosage}</span></td>
								<td class="recVaccName">${childVaccinaterecord.vaccName}</td>
								<td class="recVacctype text-center">${fns:getDictLabel(childVaccinaterecord.vacctype, 'vacctype', '')}</td>
								<td class="recDate"><fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd" /></td>
								<td class="recPosition">${fns:getDictLabel(childVaccinaterecord.bodypart, 'position', '')}</td>
								<td class="recBatch">${childVaccinaterecord.batch}</td>
								<td class="recExpDate"><fmt:formatDate value="${childVaccinaterecord.vaccExpDate}" pattern="yyyy-MM-dd" /></td>
								<td class="recManufacturer">${childVaccinaterecord.manufacturer}</td>
								<td class="recSource"><c:if test="${childVaccinaterecord.office != zdbm }">${childVaccinaterecord.office}</c:if></td>
								<c:if test="${childVaccinaterecord.price == 0 }">
									<td class="pay">免费</td>
								</c:if>
								<c:if test="${childVaccinaterecord.price != 0 }">
									<td class="pay">${fns:getDictLabel(childVaccinaterecord.payStatus, 'paystatus', '')}</td>
								</c:if>
								 <td class="recSign" data-signature="${childVaccinaterecord.id}" data-sign=${childVaccinaterecord.signature}>
									<c:if test="${childVaccinaterecord.signature == 0}">
										<input data-role="signature" type="button" value="签字" class="btn btn-xs btn-danger" onclick="parent.SignatureId('${childVaccinaterecord.id}','${childVaccinaterecord.vaccineid}','${childVaccinaterecord.dosage}')">
									</c:if>
									<c:if test="${childVaccinaterecord.signature == 1}">
										<input  type="button" value="查看签字" class="btn btn-xs btn-primary" onclick="parent.signatures('${childVaccinaterecord.id}')" >
									</c:if>
								</td>
								
								<%-- <td>${childVaccinaterecord.office}</td> --%>
								<%-- <td>${childVaccinaterecord.doctor}</td> --%>
								<%-- <td><c:if test="${childVaccinaterecord.status == 0 }"><a href="javascript:void(0);" data-id="${childVaccinaterecord.dosage}" data-vaccid="${childVaccinaterecord.vaccineid }" class="confirmVacc">确认</a></c:if></td> --%>
								<%-- <td>
									<c:if test="${childVaccinaterecord.status == 0 }">
										<a href="javascript:void(0);" data-id="${num.id}" data-vaccid="${num.vaccineid }"  data-pid="${num.product.id }" class="confirmVacc">确认</a>
									</c:if>
								</td>	 --%>							
								</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">${page}</div>
			</div>
			
		</div>

	</div>
	
	<div class="tips ibox-content h65" >
	<!--  <div class="sampleText">
			<label id="sample2Txt">Drag the columns to start</label>				
		</div>  -->
		<div class="row text-right " style="margin-right: 50px;">
			完成接种<input type="button" class="btn btn-success ml2" onclick="openWindows('${ctx}/inoculate/quene/listF', '今日完成接种列表')" value="${finished}">人&nbsp;&nbsp;
			当前排队<input type="button"  class="btn btn-primary ml2" onclick="openWindows('${ctx}/inoculate/quene/list', '当前排队列表')" value="${queneN}">人&nbsp;&nbsp;
			过号<input type="button" class="btn btn-warning ml2" onclick="openWindows('${ctx}/inoculate/quene/listY','过号列表')" value="${queneY}">人&nbsp;&nbsp;
			<div class="mt10 text-right">
				<button type="button" class="btn btn-primary ml2 btnAddVacc" >单剂录入</button>&nbsp;&nbsp;
				<button type="button" class="btn btn-primary ml2 " onclick="addOfflineRecord()" >批量补录</button>&nbsp;&nbsp;
<%-- 				<a type="button" class="btn btn-primary ml2" href="${ctx}/product/bsManageProduct/check?from=inoculate" onclick="showBack()">疫苗盘点</a>&nbsp;&nbsp; --%>
				<a type="button" class="btn btn-primary ml2" href="${ctx}/product/bsManageCheck/tables?checkDate=${checkDate}&from=inoculate" onclick="showBack()">疫苗盘点</a>&nbsp;&nbsp;
<%-- 				<a type="button" class="btn btn-primary ml2" href="${ctx}/manage_stock_in/manageStockIn/addStockOut?from=inoculate" onclick="showBack()">疫苗出库</a>&nbsp;&nbsp; --%>
<%-- 				<a type="button" class="btn btn-primary ml2" href="${ctx}/manage_stock_in/manageStockIn/addStockIn?from=inoculate" onclick="showBack()">疫苗入库</a>&nbsp;&nbsp; --%>
				<a type="button" class="btn btn-primary ml2" href="${ctx}/inoculate/quene/quenelog" target="_blank" >接种日志</a>&nbsp;&nbsp;
				<button type="button" class="btn btn-primary ml2" onclick="alertSetting()">设置</button>&nbsp;&nbsp;
			</div>
			<div style="height: 100px;"></div>
		</div>
	</div>
	


</body>
</html>