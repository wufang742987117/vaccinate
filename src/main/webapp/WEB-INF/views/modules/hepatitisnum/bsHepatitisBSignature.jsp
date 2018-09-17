<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" >
<head>
<title>信息管理</title>
<meta name="decorator" content="hbdefault"/>
<style type="text/css">
	html,body{font-size: 18px;}
	body{padding:20px;margin:0;}
	h2{text-align: center;margin: 0 0 20px 0;font-size:24px;font-weight:bold;}
	p{text-indent: 2em;margin:5px 0;font-size: 1em;}
</style>
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
			<h2>乙肝疫苗接种知情告知书</h2>
		</div>
		<div id="content" style="padding: 10px 60px;">
			<p><b>【疾病知识】</b>乙型肝炎是由乙型肝炎病毒引起的一种传染病，通过血液、体液和母婴传播。母婴传播是我国目前乙肝病毒传播的主要因素。急性肝炎临床症状有疲倦、厌食、恶心、呕吐、皮肤巩膜黄染等。大多数的急性乙肝感染可完全康复，但部分人特别是婴幼儿可成为慢性乙肝携带者，随后可能发展成为慢性肝病、肝硬化或肝癌。乙肝病毒在体内不断复制使得肝脏发生炎性病变，肝细胞受损，而且还可能恶变成肝硬化和肝癌，威胁生命。在我国，71%的肝癌是由乙型肝炎发展而来的。接种乙肝疫苗是预防乙肝经济而有效的手段。</p>
			<p><b>【疫苗知识】</b>目前使用的乙肝疫苗是基因工程疫苗，其主要成分是乙肝病毒的表面抗原，不含有病毒遗传物质，不具备感染性和致病性，但保留了免疫原性，即刺激机体产生保护性抗体的能力。根据所含的抗原含量等疫苗分为多种：	</p>
			<p><b>一、乙肝疫苗（5ug）</b></p>
			<p>（一）该疫苗是国家免疫规划疫苗，政府提供免费接种。儿童于出生后24小时内接种第一剂次；第二剂次在第一剂接种后1个月接种；第三剂次在第一剂次接种后6个月接种。</p>
			<p>（二）按规定程序接种3剂后，90%以上能产生保护作用，但有部分人群接种3剂后仍不产生抗体（无应答）。对于该部分人群，最常用办法是增加接种疫苗的剂量、更换接种疫苗的种类等</p>
			<p>（三）接种后很少有不良反应，个别可能有中、低度发热或注射局部微痛，24小时内即自行消失。</p>
			<p><b>二、乙肝疫苗（10ug、20ug）</b></p>
			<p>（一）该疫苗现属二类疫苗,遵循知情、自愿、自费原则进行接种。出生24小时内、1月、6月各注射一剂。</p>
			<p>（二）该乙肝疫苗抗原含量相对较高（10ug/0.5ml），接种后能更快地产生保护作用。</p>
			<p>（三）接种后很少有不良反应，个别可能有中、低度发热或注射局部微痛，24小时内即自行消失。</p>
			<p><b>【注意事项】</b>1、正在发病的乙肝患者或隐形感染者、慢性乙肝病毒携带者和乙肝病毒既往感染者，都没有必要注射乙肝疫苗。有发热、患有急性或慢性严重疾病者（如心脏、肾脏病等）或严重脏器畸形、严重的皮肤湿疹患者、对酵母成分过敏者不能接种。</p>
			<p>2、接种时请带上儿童预防接种证和本告知书。</p>
			<p>3、为及时诊治可能发生的过敏反应，接种后请在接种门诊留观30分钟。</p>
		</div>
	</div>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsHepatitisBNum" action="${ftx}/hepatitisnum/bsHepatitisBNum/saveSignature" method="post" class="form-horizontal">
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
					    <!-- <input class="btn btn-bshepb" type="button" onclick="hWClearPenSign_onclick()" value="重置"/>  -->
						<!-- <input id="btnSubmit" class="btn btn-bshepb" type="submit" onclick="hWFinalize_onclick()" value="保 存" /> -->
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>