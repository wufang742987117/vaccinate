<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>儿童基本信息完整性统计</title>
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
	//添加社区code 将复选框的内容传向后台
	$("input:checkbox[name='areaType']").click(function(){
		 text2 = $("input:checkbox[name='areaType']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#areaCodes").val(text2);
       	 $("#areaCodes").change();
	});
	
	//添加居住类型code 将复选框的内容传向后台
	$("input:checkbox[name='resideType']").click(function(){
		 text3 = $("input:checkbox[name='resideType']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#resideCodes").val(text3);
       	 $("#resideCodes").change();
	});	
	//完整性
	$("input:checkbox[name='wanzhen']").click(function(){
		 text3 = $("input:checkbox[name='wanzhen']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#wanzhenCodes").val(text3);
       	 $("#wanzhenCodes").change();
	});	
	//添加在册情况    将复选框的内容传向后台
	$("input:checkbox[name='recordType']").click(function(){
		 text3 = $("input:checkbox[name='wanzhen']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#recordCodes").val(text3);
       	 $("#recordCodes").change();
	});	
	//添加疫苗种类    将复选框的内容传向后台
	$("input:checkbox[name='vaccname']").click(function(){
		 text3 = $("input:checkbox[name='vaccname']:checked").map(function(index,elem) {
            	return $(elem).val();
       	 }).get().join('/');
       	 $("#vaccCodes").val(text3);
       	 $("#vaccCodes").change();
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
	//区域划分全选
	$("#all1").click(function(){   
	    if(this.checked){   
	        $("input:checkbox[name='areaType']").prop("checked", true); 
	        text2 = $("input:checkbox[name='areaType']").map(function(index,elem) {
            	return $(elem).val();
	       	 }).get().join('/');
	       	 $("#areaCodes").val(text2);
	       	 $("#areaCodes").change(); 
	    }else{   
			$("input:checkbox[name='areaType']").prop("checked", false);
			 $("#areaCodes").val("");
	       	 $("#areaCodes").change(); 
	    }
	});
	//在册情况全选
	$("#all2").click(function(){   
	    if(this.checked){   
	        $("input:checkbox[name='recordType']").prop("checked", true); 
	         text3 = $("input:checkbox[name='wanzhen']").map(function(index,elem) {
            	return $(elem).val();
	       	 }).get().join('/');
	       	 $("#recordCodes").val(text3);
	       	 $("#recordCodes").change();
	         
	    }else{   
			$("input:checkbox[name='recordType']").prop("checked", false);
			 $("#recordCodes").val("");
	       	 $("#recordCodes").change();
	    }
    }); 
    //居住属性全选
    $("#all3").click(function(){   
	    if(this.checked){   
	        $("input:checkbox[name='resideType']").prop("checked", true);  
	         text3 = $("input:checkbox[name='resideType']").map(function(index,elem) {
            	return $(elem).val();
	       	 }).get().join('/');
	       	 $("#resideCodes").val(text3);
	       	 $("#resideCodes").change();
	    }else{   
			$("input:checkbox[name='resideType']").prop("checked", false);
			$("#resideCodes").val("");
	       	$("#resideCodes").change();
	    }
    }); 
    
    //疫苗种类全选
    $("#all5").click(function(){  
	    if(this.checked){   
	        $("input:checkbox[name='vaccname']").prop("checked", true);  
	         text3 = $("input:checkbox[name='vaccname']").map(function(index,elem) {
            	return $(elem).val();
	       	 }).get().join('/');
	       	 $("#vaccCodes").val(text3);
	       	 $("#vaccCodes").change();
	    }else{   
			$("input:checkbox[name='vaccname']").prop("checked", false);
			$("#vaccCodes").val("");
	       	$("#vaccCodes").change();
	    }
    }); 
    //完整性字段全选区
    $("#all4").click(function(){   
	    if(this.checked){   
	        $("input:checkbox[name='wanzhen']").prop("checked", true); 
	         text3 = $("input:checkbox[name='wanzhen']").map(function(index,elem) {
            	return $(elem).val();
	       	 }).get().join('/');
	       	 $("#wanzhenCodes").val(text3);
	       	 $("#wanzhenCodes").change(); 
	    }else{   
			$("input:checkbox[name='wanzhen']").prop("checked", false);
			$("#wanzhenCodes").val("");
			$("#wanzhenCodes").change(); 
	    }
    });
    
    var html='<table>'+
				'<thead>'+
					'<tr>'+
						'<th rowspan="2">疫苗类别</th><th rowspan="2">剂次数</th><th rowspan="2">完整 数</th><th rowspan="2">完整率</th>#bioati1#'+
					'</tr>'+
					'<tr>'+
						'<th>#vaccname#</th><th>#jici#</th><th>#wanzhen#</th><th>#wanzhenlv#</th>#biaoti2#'+
					'</tr>'+
				'</thead>'+
				'<tbody>'+
					'<td>#nianfen#</td><td>#chirld_num#</td><td>#wanzheng_num#</td><td>#wanzheng_lv#</td>#shujv#'+
				'</tbody>'+
			'</table>';
 	var biaoti1="";
 	var biaoti2="";
 	var shujv="";
 	if(null!='${namelist}' && '${namelist}' !='' && '${namelist}'!=undefined ){
	 	var namelist =eval(${namelist});
	 	for(var i=0; i<namelist.length;i++){
	 		if(namelist[i]=="childname"){// 儿童姓名
	 			biaoti1+='<th colspan="2">儿童姓名</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#childname#</td><td>#childname_lv#</td>';
	 		}
	 		if(namelist[i]=="gender"){// 性别
	 			biaoti1+='<th colspan="2">性别</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#gender#</td><td>#gender_lv#</td>';
	 		}
	 		if(namelist[i]=="birthday"){// 出生日期
	 			biaoti1+='<th colspan="2">出生日期</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#birthday#</td><td>#birthday_lv#</td>';
	 		}
	 		if(namelist[i]=="cardcode"){//儿童身份证
	 			biaoti1+='<th colspan="2">儿童身份证</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#cardcode#</td><td>#cardcode_lv#</td>';
	 		}
	 		if(namelist[i]=="creater"){// 创建者  建证单位
	 			biaoti1+='<th colspan="2">建证单位</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#creater#</td><td>#creater_lv#</td>';
	 		}
	 		if(namelist[i]=="createdate"){//建卡日期
	 			biaoti1+='<th colspan="2">建卡日期</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#createdate#</td><td>#createdate_lv#</td>';
	 		}
	 		if(namelist[i]=="fatherphone"){//父亲电话
	 			biaoti1+='<th colspan="2">父亲电话</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#fatherphone#</td><td>#fatherphone_lv#</td>';
	 		}
	 		if(namelist[i]=="guardianmobile"){//母亲电话
	 			biaoti1+='<th colspan="2">母亲电话</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#guardianmobile#</td><td>#guardianmobile_lv#</td>';
	 		}
	 		if(namelist[i]=="father"){//父亲姓名
	 			biaoti1+='<th colspan="2">父亲姓名</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#father#</td><td>#father_lv#</td>';
	 		}
	 		if(namelist[i]=="guardianname"){//母亲姓名
	 			biaoti1+='<th colspan="2">母亲姓名</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#guardianname#</td><td>#guardianname_lv#</td>';
	 		}
	 		if(namelist[i]=="birthcode"){//出生证
	 			biaoti1+='<th colspan="2">出生证</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#birthcode#</td><td>#birthcode_lv#</td>';
	 		}
	 		if(namelist[i]=="area"){//户口县国标
	 			biaoti1+='<th colspan="2">户口县国标</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#area#</td><td>#area_lv#</td>';
	 		}
	 		if(namelist[i]=="co"){//户口地址 co 县
	 			biaoti1+='<th colspan="2">户口地址</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#co#</td><td>#co_lv#</td>';
	 		}
	 		if(namelist[i]=="mailingaddress"){//通讯地址
	 			biaoti1+='<th colspan="2">通讯地址</th>';
	 			biaoti2+='<td>录入数</td><td>录入率</td>';
	 			shujv+='<td>#mailingaddress#</td><td>#mailingaddress_lv#</td>';
	 		}
	 		
	 	}
 	}
 	
 	if(${maps} !=null && ${maps} !='' && ${maps} !=undefined){
 		var arr=eval(${maps});
	 	shujv='<tr>'+shujv+'</tr>';
	 	var shujv_bubian=shujv;
	 	var temp="";
	 	for(var i=0;i<arr.length;i++){
	 		shujv=shujv_bubian;
	 		temp+=shujv.replace("#childname#", arr[i].ch_name_num).replace("#childname_lv#", arr[i].ch_name_lv).replace("#gender#", arr[i].ch_gender_num).replace("#gender_lv#", arr[i].ch_gender_lv)
	 			.replace("#birthday#", arr[i].ch_birth_num).replace("#birthday_lv#", arr[i].ch_birth_lv).replace("#cardcode#", arr[i].ch_card_num).replace("#cardcode_lv#", arr[i].ch_card_lv)
	 			.replace("#fatherphone#", arr[i].far_phone_num).replace("#fatherphone_lv#", arr[i].far_phone_lv).replace("#guardianmobile#", arr[i].mar_phone_num).replace("#guardianmobile_lv#", arr[i].mar_phone_lv)
	 			.replace("#creater#", arr[i].createBy_num).replace("#creater_lv#", arr[i].createBy_lv).replace("#createdate#", arr[i].createDate_num).replace("#createdate_lv#", arr[i].createDate_lv)
	 			.replace("#father#", arr[i].far_phone_num).replace("#father_lv#", arr[i].far_phone_lv).replace("#guardianname#", arr[i].mar_phone_num).replace("#guardianname_lv#", arr[i].mar_phone_lv)
	 			.replace("#birthcode#", arr[i].birthcode_num).replace("#birthcode_lv#", arr[i].birthcode_lv).replace("#area#", arr[i].area_num).replace("#area_lv#", arr[i].area_lv)
	 			.replace("#co#", arr[i].co_num).replace("#co_lv#", arr[i].co_lv).replace("#mailingaddress#", arr[i].mailinGaddress_num).replace("#mailingaddress_lv#", arr[i].mailinGaddress_lv)
	 			.replace("#nianfen#", arr[i].nianfen).replace("#chirld_num#", arr[i].ch_num).replace("#wanzheng_num#", "").replace("#wanzheng_lv#", "")
	 			;
	 	}
	 	
	 	html=html.replace("#shujv#",temp).replace("#biaoti1#",biaoti1).replace("#biaoti2#",biaoti2);
	 	$("#tablediv").html(html);
 	
 	}
 	
});

</script>
</head>
 <body>
 <ul class="nav nav-tabs">
		<li><a
			href="${ctx}/export/export/record_integrity/">基本资料完整性</a></li>
		<li class="active"><a
			href="${ctx}/export/export/record_integrity_num/">接种资料完整性</a></li>
</ul>
 <div id="buAll" style="width:1500px;margin-left:300px;border:1px;">
 	<form action="${ctx}/export/export/record_integrity_num">
 	<div id="rowTwo" style="margin-top: 10px;">
		<button id="btnSubmit" style="margin-left:300px; margin-top:10px;" class="btn btn-primary" type="submit" >统计</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button id="printBtn" style="margin-top:10px;" class="btn btn-primary" onclick=" print1();">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a class="btn btn-default" style="margin-top:8px;" href="javascript:goback()">返回</a>
 	</div>
 	<div id="rowTwo" style="margin-top: 10px;">
 		<table>
 			<tr>
	 			<td>出生日期:&nbsp;&nbsp;</td>
	 			<td><input name="startBirthday" style="width:120px;"value="<fmt:formatDate value="${startBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
	 			<td><input name="endBirthday"style="width:120px;" value="<fmt:formatDate value="${endBirthday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd HH:mm:ss" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	 			<td>接种日期:&nbsp;&nbsp;</td>
	 			<td><input name="startVaccday" style="width:120px;"value="<fmt:formatDate value="${startVaccday}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" ></td>
	 			<td>&nbsp;&nbsp;&nbsp;&nbsp;到：&nbsp;&nbsp;</td>
	 			<td><input name="endVaccday" style="width:120px;" value="<fmt:formatDate value="${endVaccay}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" 
						class="laydate-icon form-control layer-date "  placeholder="yyyy-MM-dd" >&nbsp;&nbsp;</td>
 				<td>现管理单位：&nbsp;&nbsp;</td>
 				<td>
 					<select>
						<option value="1">本点管理</option>
						<option value="2">非本点管理</option>
	 				</select>
 				</td>
 				<!-- <td>条件选择</td>
 				<td><input type="checkbox">本点接种</td>
 				<td><input type="checkbox">空白卡 </td>
 				<td>及时录入天数<input type="text" style="width:50px;"> </td> -->
 			</tr>
 		</table>
 		<table style="margin-top: 10px;">
 			<tr>
 				<td>
	 				<div style="border:1px dashed #000;width: 150px;height: 150px; margin-left: 5px;">
	 					<div style="width: 300px;"><label>区域划分</label>&emsp;&emsp;<label>全选<input name="areaType" id="all1" type="checkbox" /></label></div>
	 					<div id="areaType_div" style="margin:0px;margin-left:10px; height:120px;width:120px;float:left;border:1px solid #00F;overflow-y:scroll;">
	 					
		 					<input type="hidden"  id="areaCodes" name="areaCodes">	
	 					</div>
	 				</div>
				</td>
				<td>
	 				<div style="border:1px dashed #000;width: 150px;height: 150px;margin-left: 5px;">
	 					<div style="width: 300px;"><label>在册情况</label>&emsp;&emsp;<label>全选<input id="all2" name="recordType" type="checkbox" value="10"/></label></div>
	 					<div style="margin:0px;margin-left:10px;height:120px;width:120px;float:left;border:1px solid #00F;overflow-y:scroll;">
		 					<label><input type="checkbox" value="11" name="recordType">异地接种</label>
		 					<label><input type="checkbox" value="3" name="recordType">临时接种</label>
		 					<label><input type="checkbox"  value="1" name="recordType">本地</label>
		 					<label><input type="checkbox" value="10"name="recordType">外地转来</label>
		 					<label><input type="checkbox" value="12" name="recordType">空挂户</label>
		 					<label><input type="checkbox" value="13"name="recordType">临时外转</label>
		 					<label><input type="checkbox" value="14"name="recordType">入托</label>
		 					<label><input type="checkbox" value="15"name="recordType">死亡</label>
		 					<label><input type="checkbox" value="16"name="recordType">迁出</label>
		 					<input type="hidden"  id="recordCodes" name="recordCodes">
	 					</div>
	 				</div>
				</td>
				<td>
					<div style="border:1px dashed #000;width: 100px;height:150px;margin-left: 5px;">
	 					<div style="width: 150px;"><label>居住属性</label></div>
	 					<div style="margin:0px; width:100px;float:left;">
	 						&emsp;&emsp;&emsp;<label>全选<input id="all3" name="resideType" type="checkbox" /></label><br>
	 						<label><input type="checkbox" name="resideType">本地</label><br>
	 						<label><input type="checkbox" name="resideType">流动</label><br>
		 					<label><input type="checkbox" name="resideType">临时</label>
		 					<input type="hidden" name="resideCodes" id="resideCodes">
	 					</div>
	 				</div>
				</td>
				<td>
					<div style="border:1px dashed #000;width: 180px;height:150px;margin-left: 5px;">
	 					<div style="width: 180px;"><label>疫苗类别</label>&nbsp;&nbsp;&nbsp;&nbsp;<label>全选<input id="all5"  type="checkbox" /></label></div>
	 					<div style="margin:0px; width:200px;border:1px solid #00F;float:left;height:120px;width:180px;overflow-y:scroll;">
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="bcg"/><span>卡介苗</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="hepb"/><span>乙肝(CHO)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="hepb"/><span>乙肝(酿酒酵母)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="hepb"/><span>乙肝(汉逊酵母)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="pv"/><span>脊灰(减毒二倍体)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="pv"/><span>脊灰(减毒猴肾)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="pv"/><span>脊灰灭活(salk株)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="pv"/><span>脊灰灭活(sabin株)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="dtp"/><span>二价脊灰疫苗</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="dt"/><span>百白破</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="dt"/><span>百白破(无细胞)</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mr"/><span>白破</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mmr"/><span>百白破(青少年)</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mm"/><span>破伤风</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mv"/><span>麻疹</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="1001"/><span>腮腺炎</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="1001"/><span>风疹（二倍体）</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="1201"/><span>麻腮风</span></label><br>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="1301"/><span>麻腮</span></label><br>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="1401"/><span>麻风</span></label><br>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mpv"/><span>流脑A</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mpv"/><span>流脑A+C</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mpv"/><span>流脑A+C结合</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="mpv"/><span>流脑A+C+Y+W135</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="jevl"/><span>乙脑（减毒）</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="jevi_Vero"/><span>乙脑（灭活）</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="hepal"/><span>甲肝（减毒）</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="hepal"/><span>甲肝（减毒冻干）</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="hepai"/><span>甲肝（灭活）</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2001"/><span>甲乙肝</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2101/2102/2103/2104/2105/2106"/><span>流感（全病毒）</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2101/2102/2103/2104/2105/2106"/><span>流感（裂解）</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2101/2102/2103/2104/2105/2106"/><span>流感（亚单位）</span></label>  
							<label style="margin:5px,0px,5px,5px;"><input name="vaccname" type="checkbox" value="2101/2102/2103/2104/2105/2106"/><span>甲型流感(裂解无佐剂)</span></label>  
							<label style="margin:5px,0px,5px,5px;"><input name="vaccname" type="checkbox" value="2101/2102/2103/2104/2105/2106"/><span>甲型流感(裂解有佐剂)</span></label>  
							<label style="margin:5px,0px,5px,5px;"><input name="vaccname" type="checkbox" value="2101/2102/2103/2104/2105/2106"/><span>甲型流感(全病毒)</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2201"/><span>水痘</span></label><br>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2301"/><span>Hib</span></label><br>   
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2401"/><span>轮状病毒</span></label>    
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2501"/><span>23价肺炎</span></label> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2502"/><span>7价肺炎</span></label><br>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2502"/><span>气管炎</span></label><br>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2502"/><span>兰菌净</span></label><br>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2502"/><span>狂犬病</span></label><br> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="3001/3002"/><span>出血热</span></label><br> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="3001/3002"/><span>伤寒</span></label><br> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="3301"/><span>痢疾</span></label><br>   
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="3801"/><span>霍乱</span></label><br>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="3801"/><span>乙肝球蛋白</span></label><br>
	 						<label style="margin:5px;"><input name="vaccname" type="checkbox" value="0201/0202/0203"/><span>破伤风球蛋白</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="1701"/><span>狂犬病球蛋白</span></label>
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="0601/0602"/><span>白破</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="1602"/><span>结合菌素衍生物</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="2502"/><span>百白破Hib四联</span></label>  
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="4701"/><span>百白破IPV和Hib五联</span></label><br> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="4701"/><span>黄热减毒活疫苗</span></label><br> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="4701"/><span>重组戊型肝炎 疫苗</span></label><br> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="4701"/><span>AC-Hib联合疫苗</span></label><br> 
							<label style="margin:5px;"><input name="vaccname" type="checkbox" value="4701"/><span>71型灭活疫苗</span></label><br> 
							<input id="vaccCodes" type="hidden" name="vaccCodes">
	 						
	 					</div>
	 				</div>
				</td>
				<td>
					<div style="border:1px dashed #000;width:500px;height:150px;margin-left: 5px;">
	 					<div style="width: 700px;">完整性字段&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<label><input id="all4" name="wanzhen" type="checkbox"/>全选</label> &emsp;&emsp;&emsp;&emsp; </div>
	 					<div style="margin:0px;float:left;">
	 						<input type="checkbox" value="vacc_name" name="wanzhen">接种疫苗&nbsp;&nbsp;&nbsp;
	 						<input type="checkbox" value="vaccinate_date" name="wanzhen">接种日期&nbsp;&nbsp;&nbsp;
	 						<input type="checkbox" value="batch" name="wanzhen">疫苗批号&nbsp;&nbsp;&nbsp;
	 						<input type="checkbox" value="manufacturer" name="wanzhen">生产企业&nbsp;&nbsp;&nbsp;
	 						<!-- <input type="checkbox" value="creater" name="wanzhen">接种途径&nbsp;&nbsp;&nbsp;<br><br> -->
	 						<input type="checkbox" value="vacctype" name="wanzhen">接种类型&nbsp;&nbsp;&nbsp;<br><br>
	 						<input type="checkbox" value="office" name="wanzhen">接种地点&nbsp;&nbsp;&nbsp;
	 						<input type="checkbox" value="paystatus" name="wanzhen">收费状态&nbsp;&nbsp;&nbsp;
	 						<input type="checkbox" value="doctor" name="wanzhen">接种医生&nbsp;&nbsp;&nbsp;
	 						<input type="checkbox" value="fatherphone" name="wanzhen">及时录入&nbsp;&nbsp;&nbsp;
	 						<input type="checkbox" value="bodypart" name="wanzhen">接种部位&nbsp;&nbsp;&nbsp;
	 						<input type="hidden" name="wanzhenCodes"/>
	 					</div>
	 				</div>
				</td>
 			</tr>
 		</table>
 	</div>
 	</form>
 </div>
 	
 	

<div id="tablediv1">

</div>
<div id="tablediv2" style="margin-top: 300px;overflow: auto;">
<table border="1" style="width: 1200px;">
	<thead>
		<tr>
			<th>儿童编码</th><th>儿童 姓名</th><th>性别</th><th>出生日期</th><th>疫苗名称</th><th>剂次</th><th>接种日期</th><th>描述</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${map_numinfo}" var="m"> </c:forEach>
		<tr>
			<td>${m.childcode}</td><td>${m.childname}</td><td>${m.gender}</td><td>${m.birthday}</td><td>${m.vacc_name}</td><td>${m.dosage}</td><td>${m.vacc_date}</td><td>${m.vacc_date}</td>
		</tr>
	</tbody>
</table>
</div>
</body>
</html>
