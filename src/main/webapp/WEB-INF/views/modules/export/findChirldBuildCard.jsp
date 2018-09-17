<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title></title>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<style>
		#exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
		#exportTable .text-left{text-align: left !important;}
		#exportTable .text-right{text-align: left !important;}
		#exportTable .tips{line-height: 16px; font-size: 14px;}
	</style>
<script type="text/javascript">
 function goback(){
 	window.location.href="${ctx}/export/export/exportlist";
 }
function print1(){
	$("#searchForm").css("display","none");
	window.print();
}

$(function(){
	
	$("#batch").change(function manufacturer() {
			//通过疫苗名称和批次查询所有的厂家
			var name = $("#vaccineName").val();
			var batchno = $("#batch").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/manage_stock_in/manageStockIn/manufacturer",
				data : {
				name :  name,
				batchno : batchno
				},
				dataType : "json",
				success : function(data) {
					var html = "";
					var html2 = "";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.manufacturer + "'>"+ item.manufacturer + "</option>";
						html2 += "<option value='" + item.specificationname + "'>"+ item.specificationname + "</option>";
					});
					$("#manufacturer").html(html);
					$("#manufacturer").change();
					$("#standard").html(html2);
					$("#standard").change();
				}
			});	
		});
	//添加社区code
	$("input:checkbox[name='areaType']").click(function(){
		 text2 = $("input:checkbox[name='areaType']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#areaCodes").val(text2);
       	 $("#areaCodes").change();
	});
	
	//添加居住类型code
	$("input:checkbox[name='resideType']").click(function(){
		 text3 = $("input:checkbox[name='resideType']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#resideCodes").val(text3);
       	 $("#resideCodes").change();
	});	
	
	$("input:checkbox[name='hukouType']").click(function(){
		 text3 = $("input:checkbox[name='hukouType']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#hukouCodes").val(text3);
       	 $("#hukouCodes").change();
	});	
});

function ff(){
var are=$("input:radio[name='arType'][checked]").val();//  var val= $("input[@name='z'][checked]").val();  $('#divID #inputId').attr('readonly','readonly');
	if(are=="0"){
		$("#areaType_div").attr("readonly",false);//removeProp("readonly")
	}else{
		$("#areaType_div").attr("readonly",true);
	}
 }
 
$(function(){
 	 var html1= '<table border="1">'+
			 		'<tr>'+
			 			'<td>类别</td><td>本地</td><td>流动</td><td>临时</td><td>小计</td>'+
			 		'</tr>'+
			 		'<tr>'+
			 			'<td>统计数</td><td>#bendi#</td><td>#liudong#</td><td>#linshi#</td><td>#xiaoji#</td>'+
			 		'</tr>'+
 				'</table>';
 				
 				
 	 var html2='<table border="1">'+
			 		'<tr>'+
			 			'<td>类别</td><td>异地接种</td><td>临时接种</td><td>本地</td><td>外地转来</td><td>空挂户</td><td>临时外转</td><td>入托</td><td>死亡</td><td>迁出</td>'+
			 		'</tr>'+
			 		'<tr>'+
			 			'<td>统计数</td><td>#yidi#</td><td>#linshi#</td><td>#bendi#</td><td>#waidi#</td><td>#konggua#</td><td>#lin#</td><td></td><td></td><td></td>'+
			 		'</tr>'+
 				'</table>';
 				
 	if("${showType}"!=null && "${showType}" !="" && "${showType}" !=undefined){
 		if("${showType}"=="1"){
 			var arr=eval(${card1_numMaps});
 			for(var i=0;i<arr.length;i++){
 				if(arr[i].reside=="1"){
 					html1=html1.replace("#bendi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="2"){
 					html1=html1.replace("#liudong#",arr[i].card_num);
 				}
 				if(arr[i].reside=="3"){
 					html1=html1.replace("#linshi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="4"){
 					html1=html1.replace("#xiaoji#",arr[i].card_num);
 				}
 			}
 			//替换所有的未被替换的##
 			html1 = html1.replace(/^#.*#$/g,"")
 			$("#tablediv").html(html1);
 			
 		}else if("${showType}"==2){
 			var arr=eval(${card2_numMaps});
 			for(var i=0;i<arr.length;i++){
 				if(arr[i].reside=="1"){
 					html2=html2.replace("#yidi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="2"){
 					html2=html2.replace("#linshi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="3"){
 					html2=html2.replace("#waidi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="4"){
 					html2=html2.replace("#bendi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="5"){
 					html2=html2.replace("#liudong#",arr[i].card_num);
 				}
 				if(arr[i].reside=="6"){
 					html2=html2.replace("#linshi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="2"){
 					html2=html2.replace("#heji#",arr[i].card_num);
 				}
 				if(arr[i].reside=="7"){
 					html2=html2.replace("#waidi#",arr[i].card_num);
 				}
 				if(arr[i].reside=="9"){
 					html2=html2.replace("#konggua#",arr[i].card_num);
 				}
 				if(arr[i].reside=="99"){
 					html2=html2.replace("#lin#",arr[i].card_num);
 				}
 			}
 			html2 = html2.replace(/^#.*#$/g,"")
 			$("#tablediv").html(html2);
 		}
 	
 	}			
		
 
 });
</script>
</head>
 <body>
 <div id="buAll" style="width:1200px;margin-left:300px;border:1px;">
 	<form action="${ctx}/export/export/findChirldBuildCard">
 	<div id="rowOne" style="margin-left:350px;">
 		<div class="input-group" >
			<button id="btnSubmit" style="margin-left:50px; margin-top:20px;" class="btn btn-primary" type="submit" >统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="printBtn" style="margin-top:20px;" class="btn btn-primary" onclick=" print1();">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-default" style="margin-top:20px;" href="javascript:goback()">返回</a> 
		</div>
 	</div>
 	<div id="rowTwo" style="margin-top: 10px;">
 		<table>
 			<tr>
	 			<td>出生日期:&nbsp;&nbsp;</td>
	 			<td><input name="startBirthday" value="<fmt:formatDate value="${startBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
	 			<td><input name="endBirthday" value="<fmt:formatDate value="${endBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd HH:mm:ss" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 			<td>建卡日期:&nbsp;&nbsp;</td>
	 			<td><input name="startCarday" value="<fmt:formatDate value="${startCarday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
	 			<td><input name="endCarday" value="<fmt:formatDate value="${endCarday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
 			</tr>
 			<tr>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 			<td>性别:&nbsp;&nbsp;</td>
	 			<td><input name="sex" value="1"type="radio">男&nbsp;&nbsp;&nbsp;&nbsp;<input name="sex" value="2"type="radio">女&nbsp;&nbsp;&nbsp;&nbsp;<input name="sex" value="0"type="radio">全部</td>
	 			<td><input type="checkbox">条码</td>
 			</tr>
 		</table>
 		<table style="margin-top: 10px;">
 			<tr>
 				<td>
	 				<div style="border:1px dashed #000;width: 150px;height: 150px; margin-left: 5px;">
	 					<div style="width: 300px;"><label>区域类别</label></div>
	 					<div style="margin:0px; width:100px;float:left;">
	 						<label><input type="checkbox" name="areaType">异地接种</label>
		 					<label><input type="checkbox" name="areaType">临时接种</label>
		 					<label><input type="checkbox" name="areaType">本地</label>
		 					<label><input type="checkbox" name="areaType">外地转来</label>
		 					<label><input type="checkbox" name="areaType">空挂户</label>
		 					<input type="hidden" id="areaCodes">
	 					</div>
	 				</div>
				</td>
				<td>
	 				<div style="border:1px dashed #000;width: 150px;height: 150px;margin-left: 5px;">
	 					<div style="width: 300px;"><label>户籍类别</label><label>全选<input type="checkbox" value="10"/></label></div>
	 					<div style="margin:0px; width:100px;float:left;">
		 					<label><input type="checkbox" name="resideType">本地</label>
		 					<label><input type="checkbox" name="resideType">流动</label>
		 					<label><input type="checkbox" name="resideType">临时</label>
		 					<input type="hidden" id="resideCodes">
	 					</div>
	 				</div>
				</td>
				<td>
					<div style="border:1px dashed #000;width: 150px;height:150px;margin-left: 5px;">
	 					<div style="width: 300px;"><label>户口类别</label></div>
	 					<div style="margin:0px; width:100px;float:left;">
	 						<label><input type="checkbox" name="hukouType">本省</label>
	 						<label><input type="checkbox" name="hukouType">本市</label>
		 					<label><input type="checkbox" name="hukouType">外省</label>
		 					<label><input type="checkbox" name="hukouType">境外</label>
		 					<label><input type="checkbox" name="hukouType">本县</label>
		 					<label><input type="checkbox" name="hukouType">其他</label>
	 						<input type="hidden" id="hukouCodes">
	 					</div>
	 				</div>
				</td>
				<td>
					<div style="border:1px dashed #000;width: 150px;height:150px;margin-left: 5px;">
	 					<div style="width: 300px;"><label>输出方式 </label></div>
	 					<div style="margin:0px; width:100px;float:left;">
	 						<label><input type="radio" value="1" checked="checked" name="showType">户籍类别</label><br>
	 						<label><input type="radio" value="2" name="showType">在册情况</label>
	 					</div>
	 				</div>
				</td>
				<td>
	 				<div style="margin-left: 5px;">
	 					<div style="margin-top: 40px;">
	 						<div style="float:left;">
	 							<label>建卡单位：</label>
	 						</div>
	 						<div style="float:left;border:1px solid #11D;" >
	 							<label><input type="radio" name="jianka">所有个案</label>&emsp;&emsp;&emsp;
	 							<label><input type="radio" name="jianka">本点建卡</label>&emsp;&emsp;&emsp;
	 						</div>
	 					</div>
	 				</div>
				</td>
 			</tr>
 		</table>
 	</div>
 	</form>
 	<div id="tablediv">
 	
 	</div>
	
</div>
  
 </body>
</html>
