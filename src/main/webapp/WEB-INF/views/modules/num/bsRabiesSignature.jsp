<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title>信息管理</title>
<meta name="decorator" content="default" />

<c:if test="${not empty id }">
	<script type="text/javascript">
		$(function() {
			window.close();
		});
		
	</script>
</c:if>
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
			submitHandler : function(form) {
				layer.load();
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
		
	});
	
	var obj;
	window.onload = function(){   
          obj = document.getElementById("HWPenSign"); 
		  obj.HWSetCtlFrame(2, 0x000000);
		  //初始化设备
          hWInitialize_onclick();
          //黑色
	      hWSetPenColor_onclick();
	      //钢笔
	      hWSetPenMode_onclick();
    }
	
	//初始化设备
	function hWInitialize_onclick() {
	  res = obj.HWInitialize();
	  error_code(res);
	}
	
	//关闭设备
	function hWFinalize_onclick() {
	   var stream;
	   stream = obj.HWFinalize();
	}
	
	//重新签名
	function hWClearPenSign_onclick() {
	   obj.HWClearPenSign();
	}
	
	//黑色
	function hWSetPenColor_onclick() {
	   obj.HWSetPenColor(0x000000);   
	}
	
	//压力笔
	function hWSetParMode_onclick() {
	   obj.HWSetPenMode(1);
	}
	
	//钢笔
	function hWSetPenMode_onclick() {
	   obj.HWSetPenMode(0);
	}
	
	//Base64
	function hWGetBase64Stream_onclick() {
	   var stream;
	   stream = obj.HWGetBase64Stream(2);
       if(stream != null){
       		$("#signatureData").val(stream);
       }else{
       		alert("未获取到签字流！！！");
       }
	}
	
	//是否可保存
	function hWIsConfirm_onclick() {
	   var ret;
	   ret = obj.HWIsConfirm();
	   switch(res){
			case 0:
			  hWGetBase64Stream_onclick();
			  break;
			case -5:
			  alert("用户未签字");
			  break;
			case -10:
			  alert("用户未确认");
			  break;
			case -6:
			  alert("无效输入");
			  break;
		}
	}
	
	//确认
	function signComplete() {
    	hWIsConfirm_onclick();
    	$("#inputForm").submit();
	}
	
	//重签
	function signRestart() {
	     ;         
	}
	
	//加载code
	function error_code(res) {
		switch(res){
			case -1:
			  alert("未找到对应的汉王手写设备");
			  break;
			case -2:
			  alert("手写模块加载失败");
			  break;
			case -3:
			  alert("手写模块初始化失败");
			  break;
			case -4:
			  alert("不支持的图片格式");
			  break;
			case -5:
			  alert("没有签名数据");
			  break;
			case -6:
			  alert("无效输入参数");
			  break;
		}
	}
</script>
</head>
<body>
	<div style="margin:0 auto; width: 100%;">
		<div id="header" style="text-align: center;">
			<h2>狂犬病疫苗和抗狂犬病血清/狂犬病人免疫球蛋白</h2>
			<h2>使用知情同意书</h2>
		</div>
		<div id="content" style="padding: 10px 60px;">
			<p style="text-indent: 2em; font-size: 14px;">狂犬病是由狂犬病病毒引起的急性传染病，主要由携带狂犬病病毒的犬、猫等动物咬伤所致。当人被感染狂犬病病毒的动物咬伤、抓伤及舔舐伤口或粘膜后，其唾液所含病毒经伤口或粘膜进入人体，一旦引起发病，病死率达100%。</p>
			<p style="text-indent: 2em; font-size: 14px;">人暴露狂犬病病毒后最理想的处理原则是：1、规范的处理伤口。2、正确使用狂犬病人免疫球蛋白。3、按时全程接种合格狂犬疫苗能大大减少发病的风险。</p>
			<p style="text-indent: 2em; font-size: 14px;">狂犬病人免疫球蛋白特异地中和狂犬病病毒，可立即生效。狂犬病疫苗接种10-14天后可刺激机体产生抗狂犬病病毒的保护性抗体。为安全有效地使用狂犬病疫苗和狂犬病人免疫球蛋白，在您使用之前我们将有关信息告知于您，您可以根据自己的具体情况决定是否使用。</p>
			<h5>一、伤口处理</h5>
			<p style="text-indent: 2em; font-size: 14px;">用20%的肥皂水（或者其他弱碱性清洁剂）和一定压力的流动清水交替彻底清洗、冲洗所有咬伤和抓伤处至少15分钟。彻底清洗后用2-3碘酒（碘伏）75%酒精涂擦伤口或用皮肤粘膜消毒液喷涂伤口。伤口较深、污染严重者酌情进行抗破伤风处理和使用维生素等，以控制狂犬病病毒以外的其他感染。</p>
			<h5>二、疫苗接种程序</h5>
			<p style="text-indent: 2em; font-size: 14px;color: red;">判定为Ⅰ级暴露无需进行处理。</p>
			<p style="text-indent: 2em; font-size: 14px;color: red;">1、II级暴露：无出血的轻微抓伤或擦伤、裸露的皮肤被轻咬：于0（注射当天）3、7、14和28天各注射狂犬病疫苗1个计量。</p>   
			<p style="text-indent: 2em; font-size: 14px;color: red;">2、III级暴露：单处或多处贯穿性皮肤咬伤或抓伤；破损皮肤被舔；开放性伤口或粘膜被污染；确认为II级暴露者且免疫功能低下的，或者II级暴露位于头面部且致伤动物不能确定健康时，按照III级暴露处置：注射狂犬病人免疫球蛋白，随后接种狂犬疫苗。</p>
			<p style="text-indent: 2em; font-size: 14px;color: red;">3、健康者预防：对未咬伤健康者预防接种，可按0、7、21天接种注射3针次。根据WHO的暴露后预防建议，II级或III级暴露者都应在接种疫苗的同时，使用抗狂犬病毒免疫球蛋白。</p>
			<h5>三、不良反应</h5>
			<p style="text-indent: 2em; font-size: 14px;color: red;">狂犬病疫苗：个别人接种后可产生不同程度的不良反应。如：注射部位局部反应（疼痛、红肿、硬结等）；皮疹和荨麻疹等过敏反应；发热或全身不适等全身反应。</p>
			<p style="text-indent: 2em; font-size: 14px;color: red;">
				狂犬病人免疫球蛋白：一般无不良反应，少数人有红肿、疼痛感，无需特殊处理可自行恢复。
			</p>
			<h5>四、注意事项</h5>
			<p style="text-indent: 2em; font-size: 14px;">发生在头、面、颈部、手部和外生殖器的咬伤属于III 级暴露。（WHO 推荐：由于头、面、颈、手和外生殖器部位神经丰富，建议这些部位的暴露属于III 级暴露）</p>
			<p style="text-indent: 2em; font-size: 14px;">狂犬病疫苗和抗狂犬病血清/狂犬病人免疫球蛋白属于公民自费、自愿接种疫苗。接种后留观30分钟，如出现轻微反应，一般不需特殊处理。特殊情况可电话咨询接种单位，必要时可赴医院诊治。</p>
		</div>
	</div>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsRabiesNum" action="${ftx}/num/bsRabiesNum/saveSignature" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="sessionId" />
				<form:hidden path="signatureData" />
				<sys:message content="${message}" />
				<div class="form-group">
					<h2 align="center">签字效果</h2>
					<table border="0" align="center" cellpadding="3" cellspacing="0">
				       <tr class="style7">
					       <td style="padding:10px;">
					           <object id="HWPenSign" name="HWPenSign" classid="clsid:E8F5278C-0C72-4561-8F7E-CCBC3E48C2E3" width="400" height="150"></object>
						   </td>
				       </tr>                       
    				</table>
				</div>
				<div class="form-group">
					<div align="center">
					    <!-- <input class="btn btn-primary" type="button" onclick="hWClearPenSign_onclick()" value="重置"/>  -->
						<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" onclick="hWFinalize_onclick()" value="保 存" /> -->
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>