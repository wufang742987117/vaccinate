<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>建卡及时率查询</title>
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
		
	$("#all1").click(function() {
        $('input[name="areaType"]').prop("checked",true); 
    });
            
    $("#not1").click(function() {
        $('input[name="areaType"]').prop("checked",false); 
    });
    $("#all2").click(function() {
        $('input[name="recordType"]').prop("checked",true); 
    });
            
    $("#not2").click(function() {
        $('input[name="recordType"]').prop("checked",false); 
    });
    //添加社区
	var arr_shequ= eval(${shequMaplist});
	for(var i=0; i<arr_shequ.length;i++){
		$("#areaType_div").append('<label><input type="checkbox" value='+arr_shequ[i].shequCode+' name="areaType">'+arr_shequ[i].shequName+'</label>');
	}
	//添加社区code
	$("input:checkbox[name='areaType']").click(function(){
		 text2 = $("input:checkbox[name='areaType']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#areaCodes").val(text2);
       	 $("#areaCodes").change();
	});
	
	//添加居住类型code
	$("input:checkbox[name='recordType']").click(function(){
		 text3 = $("input:checkbox[name='recordType']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#recordCodes").val(text3);
       	 $("#recordCodes").change();
	});
	
	 var html ='<table border="1" style="border:solid 1px black; width:1180px;border-collapse:collapse;" >'+
				 	'<thead>'+
				 		'<tr>'+
				 			'<th>区域划分名称</th><th>儿童数</th><th>建卡数</th><th>建卡率</th><th>及时数</th><th>及时率</th><th>合格数</th><th>合格率</th>'+
				 		'</tr>'+
				 	'</thead>'+
				 	'<tbody>'+
				 		'#shujv#'+
				 	'</tbody>'+
			 	'</table>';
	    var shujv_temp='<tr><td>#name#</td><td>#chirld_num#</td><td>#card_num#</td><td>#card_lv#</td><td>#jishi_num#</td>'+
	    	'<td>#jishi_lv# </td><td>#hege_num#</td><td>#hege_lv# </td><tr>'
		var shujv_bubian=shujv_temp;
		var temp="";
		var arrStr=${cardMaps};
		var arr=eval(${cardMaps});
		if(arrStr !=null && arrStr !='' && arrStr !=undefined){
			for(var i=0;i<arr.length;i++){
				shujv_temp=shujv_bubian;
				shujv_temp=shujv_temp.replace("#name#",arr[i].name).replace("#chirld_num#",arr[i].chirld_num).replace("#card_num#",arr[i].card_num).replace("#card_lv#",arr[i].card_lv)
				.replace("#jishi_num#",arr[i].jishi_num).replace("#jishi_lv#",arr[i].jishi_lv).replace("#hege_num#",arr[i].hege_num).replace("#hege_lv#",arr[i].hege_lv );
				temp+=shujv_temp;
			}
			html=html.replace("#shujv#",temp);
			$("#tablediv").html(html);
		}
		
});

function ff(){
var are=$("input:radio[name='arType'][checked]").val();//  var val= $("input[@name='z'][checked]").val();  $('#divID #inputId').attr('readonly','readonly');
	if(are=="0"){
		$("#areaType_div").attr("readonly",false);//removeProp("readonly")
	}else{
		$("#areaType_div").attr("readonly",true);
	}
 }
</script>
</head>
 <body>
 <div id="buAll" style="width:1200px;margin-left:300px;border:1px;">
 	<form action="${ctx}/export/export/card_in_time" method="post">
 	<div id="rowOne" style="margin-left:350px;">
 		<div class="input-group" >
			<button id="btnSubmit" style="margin-left:50px; margin-top:20px;" class="btn btn-primary" type="submit" >统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<button id="printBtn" style="margin-top:20px;" class="btn btn-primary" onclick=" print1();">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
			<button id="exportBt"  style="margin-top:20px;"class="btn btn-primary" onclick="SaveAsFile('常规接种（3-1-2）')"  >导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-default" style="margin-top:20px;" href="javascript:goback()">返回</a> 
		</div>
 	</div>
 	<div id="rowTwo" style="margin-top: 10px;">
 		<table>
 			<tr>
	 			<td>出生日期:&nbsp;&nbsp;</td>
	 			<td><input name="startBirthday" value="<fmt:formatDate value="${startBirthday}" pattern="yyyy-MM-dd"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
	 			<td><input name="endBirthday" value="<fmt:formatDate value="${endBirthday}" pattern="yyyy-MM-dd"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 			<td>建卡日期:&nbsp;&nbsp;</td>
	 			<td><input name="startCarday" value="<fmt:formatDate value="${startCarday}" pattern="yyyy-MM-dd"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
	 			<td><input name="endCarday" value="<fmt:formatDate value="${endCarday}" pattern="yyyy-MM-dd"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
 			</tr>
 		</table>
 		<table style="margin-top: 10px;">
 			<tr>
 				<td>
	 				<div style="border:1px dashed #000;width: 300px;height: 200px; margin-left: 5px;">
	 					<div style="width: 300px;"><label>区域类别</label>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<label>全选<input id="all1"  name="all1" type="radio" value="10"/></label>
	 							<label>全不选<input id="not1"  name="all1" type="radio" value="10"/></label></div>
	 					<div style="margin:0px; width:100px;float:left;">
	 						<hr>
	 						<label>按划分：&emsp;<input type="radio" name="arType" onclick="ff();"   value="4" checked=<c:if test="${arType==0}">checked</c:if> ></label><br>
	 						<label>按常规：&emsp;<input type="radio" name="arType" onclick="ff();" value="1" checked=<c:if test="${arType==0}">checked</c:if> ></label><br>
	 						<label>按特殊：&emsp;<input type="radio" name="arType" onclick="ff();" value="2"checked=<c:if test="${arType==0}">checked</c:if> ></label><br>
	 					</div>
	 					<div id="areaType_div" style="margin:0px;margin-right:10px; height:150px;width:100px;float:right;border:1px solid #00F;overflow-y:scroll; ">
	 						<input type="hidden"  id="areaCodes" name="areaCodes">
	 					</div>
	 				</div>
				</td>
				<td>
	 				<div style="border:1px dashed #000;width: 300px;height: 200px;margin-left: 5px;">
	 					<div style="width: 300px;"><label>在册类别</label>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<label>全选<input id="all2"  name="all2" type="radio" value="10"/></label>
	 						<label>全不选<input id="not2"  name="all2" type="radio" value="10"/></label></div>
	 					
	 					<div style="margin:0px; width:100px;float:left;">
	 						<hr>
	 						<label>按在册：&emsp;<input type="radio" name="reType" value="0"></label><br>
	 						<label>按常规：&emsp;<input type="radio" name="reType" value="1"></label><br>
	 						<label>按特殊：&emsp;<input type="radio" name="reType" value="2"></label><br>
	 					</div>
	 					<div style="margin:0px;margin-right:10px; height:150px;width:100px;float:right;border:1px solid #00F;overflow-y:scroll;">
		 					<input type="hidden"  id="recordCodes" name="recordCodes">
		 					<label><input type="checkbox" value="11" name="recordType">异地接种</label>
		 					<label><input type="checkbox" value="3" name="recordType">临时接种</label>
		 					<label><input type="checkbox"  value="1" name="recordType">本地</label>
		 					<label><input type="checkbox" value="10"name="recordType">外地转来</label>
		 					<label><input type="checkbox" value="12" name="recordType">空挂户</label>
		 					<label><input type="checkbox" value="13"name="recordType">临时外转</label>
		 					<label><input type="checkbox" value="14"name="recordType">入托</label>
		 					<label><input type="checkbox" value="15"name="recordType">死亡</label>
		 					<label><input type="checkbox" value="16"name="recordType">迁出</label>
	 					</div>
	 				</div>
				</td>
				<td>
	 				<div style="margin-left: 5px;">
	 					<div style="margin-top: 30px;">
	 						<label>及时天数&nbsp;<input type="text" value="${in_time_days}" name="in_time_days" style="width: 100px">&nbsp;天</label>&emsp;&emsp;
	 						<label>合格天数&nbsp;<input type="text" value="${hege_days}" name="hege_days" style="width: 100px">&nbsp;天</label>
	 					</div>
	 					<div style="margin-top: 40px;">
	 						<div style="float:left;">
	 							<label>建卡单位：</label>
	 						</div>
	 						<div style="float:left;border:1px solid #11D;" >
	 							<label><input type="radio" value="" name="jianka">所有个案</label>&emsp;&emsp;&emsp;
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
 	
 	<!-- <table>
	 	<thead>
	 		<tr>
	 			<th>区域划分名称</th><th>儿童数</th><th>建卡数</th><th>建卡率</th><th>及时数</th><th>及时率</th><th>合格数</th><th>合格率</th>
	 		</tr>
	 	</thead>
	 	<tbody>
	 		<tr>
	 			<td>#name#</td><td>#chirld_num#</td><td>#card_num#</td><td>#card_lv#</td><td>#jishi_num#</td><td>#jishi_lv#</td><td>#hege_num#</td><td>#hege_lv#</td><td></td>
	 		<tr>
	 	</tbody>
 	</table> -->
 	</div>
	
 </div>
 </body>
</html>
