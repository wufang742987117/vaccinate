<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp"%>
<head>
<meta charset="UTF-8">
<title>常规免疫接种情况6-1汇总表</title>
<style>
body {
	margin: 0;
	padding: 0;
	background: rgba(111, 186, 197, 0.55);
}

table {
	font-size: 14px;
}

th, td {
	text-align: center;
}

thead th {
	width: 58px;
	min-width: 58px;
}

.title {
	font-size: 18px;
	font-weight: bold;
	text-align: center;
	color: #000;
}

.template-table {
	width: 100%;
	border: 1px solid #ccc;
}

.template-table th {
	height: 40px;
}

.template-table td {
	height: 35px;
}

.table-striped>tbody>tr:nth-of-type(odd) {
	background-color: rgba(212, 215, 216, 0.55) !important;
}

.headTab {
	position: fixed;
	top: 42px;
	z-index: 999;
}

.headTab thead {
	background: #ccc;
}

.selectColor {
	color: #337ab7;
}

.tab-title {
	border-bottom: 1px solid #aedce7;
	padding: 0;
	margin: 20px;
}

.tab-title button {
	width: 100px;
	height: 45px;
	font-size: 16px;
	/* background: #aedce7; */
	border: 0;
	font-weight: bold;
	background: #eeefef;
}

.colorSelect {
	background: #aedce7 !important;
}
/* .tabtitle button:nth-child(2) {
	    	background: #eeefef;
	    } */
.tableContent {
	width: 96%;
	margin: 0 auto;
	height: 568px;
	max-height: 568px;
	overflow: hidden;
	border: 1px solid #ccc;
	border-radius: 10px;
}

.page {
	position: absolute;
	bottom: 25px;
	left: 15px;
}

#exportBt {
	background: #0098D9;
	border-color: #0098D9;
}

.btn-group button:nth-child(1), .btn-group button:last-child {
	height: 30px;
}
</style>
<script src="${ctxStatic}/js/template.js"></script>
<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<script src="${ctxStatic}/js/ExportExcel.js"></script>
<script type="text/javascript">
		var LODOP; //声明为全局变量
	    var iRadioValue=1;
	    
	    function SaveAsExcel(){  
	        try{	
				LODOP = getLodop();
			} catch(err) {
				layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
			};
			if (LODOP == null || LODOP ==''){
				return;
			}
	        LODOP.PRINT_INIT(""); 
	        LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
	        LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
	        LODOP.ADD_PRINT_TABLE("10mm","10mm","98%","100%",$("#tablediv").html()); 
	        //LODOP.SET_SAVE_MODE("QUICK_SAVE",true);//快速生成（无表格样式,数据量较大时或许用到）
	        LODOP.SAVE_TO_FILE("常规免疫接种情况6-1汇总表.xlsx");  
	     };
	     
		function isZeroDisplay(thi){
			var _this = $(thi);
			if(_this.is(":checked")){
				//选中状态(显示0值) 
				$("#tablediv > table > tbody > tr").each(function(i,t){
					$(t).children().each(function(ii,tt){
							var ttv =  $(tt).html();
							if(!isNaN(ttv) && ttv == ""){
								$(tt).text("0");
							}
					});
				});
			}else{
				//取消状态(不显示0值)
				$("#tablediv > table > tbody > tr,#tablediv > table > tbody > tr>td>a").each(function(i,t){
					$(t).children().each(function(ii,tt){
						var ttv =  $(tt).html();
						if(!isNaN(ttv) && ttv == '0'){
							$(tt).text("");
						}
					});
				});
			}
		}
	
		$(document).ready(function(){
			
			//为年份赋值
			for(var i=2008;i<2051 ;i++){
				$("#yearstr").append("<option value="+i+">"+i+"</option>");//添加option
			}
			$("#yearstr").val(${yearstr});
			//为月份赋值   起始
			for(var i=1;i<=12 ;i++){
				$("#startmonthstr").append("<option value="+i+">"+i+"</option>");//添加option
			}
			$("#startmonthstr").val(${startmonthstr});
			//为月份赋值   截止
			for(var i=1;i<=12 ;i++){
				$("#endmonthstr").append("<option value="+i+">"+i+"</option>");//添加option
			}
			$("#endmonthstr").val(${endmonthstr});
			//为显示类型赋值  按居委名称0  按常规修订1   按特殊修订2 
			$("#showType").append('<option value="0">按居委名称</option>');
			$("#showType").append('<option value="1">按常规修订</option>');
			$("#showType").append('<option value="2">按特殊修订</option>');
			$("#showType").append('<option value="3">按居住属性</option>');
			$("#showType").val(${showType});
			if("${showType}"=="3"){
				$("#liveProp_label").hide();
			}
			if("${live}"=="0"){
				$("#liveProp").attr("checked",false)
			}else{
				$("#liveProp").attr("checked",true);
			}
			$("#live").val("${live}");
		});
		
		function shuaxin(){
			var value = $("#live").val();
			if(value=="0"){
				$("#live").val("1");
			}else{
				$("#live").val("0");
			}
			value = $("#live").val();
		}
		
		function goback(){
			window.location.href="${ctx}/export/export/exportlist";
		}
		
		function print1(){
			$("#searchForm").css("display","none");
			window.print();
			
		}
		
		function vaccbox(){
			openWindowsH400("${ctx}/export/export/openbox6_1","",700,400);
		}
		 
		function openWindowsH400(url, title, w, h) {
			w = isNull(w) ? 700 : w;
			h = isNull(h) ? 400 : h;
			var ww = ($(document).width() < w ? $(document).width() - 30 : w)
					+ "px";
			var hh = ($(document).height() < h ? $(document).height() - 30 : h)
					+ "px";
			layer.open({
				type : 2,
				area : [ ww, hh ],
				title : isNull(title) ? "信息" : title,
				fix : false, //不固定
				maxmin : false,
				shade : 0.3,
				shift : 0, //0-6的动画形式，-1不开启
				content : url,
			});
		}
		
		function openbox6_1back(text1,text2,text3){
			$("#vaccname").val(text1);
			$("#vaccCode").val(text2);
			$("#typename").val(text3);
		}
		
		function closeForm() {
			layer.closeAll();
		}
		
		
		$(function(){
			//表格1
			var html='<table border="1" style="border:solid 1px black;border-collapse:collapse;overflow:auto;">'+
				         	'<thead>'+
				         		'<tr><th colspan="#titleclo#">#yearstr#年#startmonthstr#月~#endmonthstr#月常规免疫接种情况汇总表（#vaccCode#）</th></tr>'+
								'<tr><th rowspan="3" style="width:114px;min-width:114px;">户籍类型</th><th colspan="#typenameclo1#">#typename1#</th><th colspan="#typenameclo2#">#typename2#</th></tr>'+ 
								'<tr>'+'<th rowspan="2">应种数</th>#c1#<th rowspan="2">接种率</th>'+'<th rowspan="2">应种数</th>#c2#<th rowspan="2">接种率</th>'+'</tr>'+
								'<tr>'+'#shizhong#'+'</tr>'+
							'</thead>'+
							'<tbody>'+
								'#shujv#'+
							'</tbody>'+
				     '</table>';
		    //表格2
			var html2='<table border="1" style="border:solid 1px black;border-collapse:collapse;overflow:auto;">'+
				         	'<thead>'+
				         		'<tr><th colspan="#titleclo#">#yearstr#年#startmonthstr#月~#endmonthstr#月常规免疫接种情况汇总表（#vaccCode#）</th></tr>'+
								'<tr><th rowspan="2" style="width:114px;min-width:114px;">户籍名称</th>#vaccname#</tr>'+ 
								'<tr>'+	'#biaoti#'+'</tr>'+
							'</thead>'+
							'<tbody>'+
								'#shujv#'+
							'</tbody>'+
				     '</table>';
		   	//表格3
			var html3='<table border="1" style="border:solid 1px black;border-collapse:collapse;overflow:auto;">'+
				         	'<thead>'+
				         		'<tr><th colspan="#titleclo#">#yearstr#年#startmonthstr#月~#endmonthstr#月常规免疫接种情况汇总表（#vaccCode#）</th></tr>'+
								'<tr><th rowspan="2" style="width:114px;min-width:114px;">户籍名称</th><th rowspan="2">居住类型</th>#vaccname#</tr>'+ 
								'<tr>'+'#biaoti#'+'</tr>'+
							'</thead>'+
							'<tbody>'+
								'#shujv#'+
							'</tbody>'+
				     '</table>';
		   	//表格4
			var html4='<table border="1" style="border:solid 1px black;border-collapse:collapse;overflow:auto;">'+
				         	'<thead>'+
				         		'<tr><th colspan="#titleclo#">#yearstr#年#startmonthstr#月~#endmonthstr#月常规免疫接种情况汇总表（#vaccCode#）</th></tr>'+
								'<tr><th rowspan="2">居住类型</th>#vaccname#</tr>'+ 
								'<tr>'+'#biaoti#'+'</tr>'+
							'</thead>'+
							'<tbody>'+
								'#shujv#'+
							'</tbody>'+
				     '</table>';	     
		   	//表格5  总合计
			var html5='<table border="1" style="border:solid 1px black;border-collapse:collapse;overflow:auto;">'+
				         	'<thead>'+
				         		'<tr><th colspan="#titleclo#">#yearstr#年#startmonthstr#月~#endmonthstr#月常规免疫接种情况汇总表（#vaccCode#）</th></tr>'+
								'<tr><th rowspan="2">居住类型</th>#vaccname#</tr>'+ 
								'<tr>'+'#biaoti#'+'</tr>'+
							'</thead>'+
							'<tbody>'+
								'#shujv#'+
							'</tbody>'+
				     '</table>';
				     
			 if("${showType}" != null && "${showType}" != "" && "${showType}" != undefined  && "${live}" != null && "${live}" != "" && "${live}" != undefined){
				if("${showType}"==3){
					//按居住类型  表格4数据拼接
					var arr = eval(${live_maps});
					var biaoti_arr = "${vaccCode}".split("/");
					var shujv_temp = '<td>#label#</td>';
					var titleclo = 1;
					html4 = yihang(biaoti_arr,shujv_temp,titleclo,html4,arr,4);
					if("${vaccCode}" != ""){
						$("#tablediv").html(html4);
					}
				}else if("${showType}" == "0" && "${live}" == "1"){
					//按社区查询    分居住类型  表格3数据拼接
					var arr = eval(${shequ_live_maps});
					var biaoti_arr = "${vaccCode}".split("/");
					var shujv_temp = '<td rowspan="2" style="">#shequ_name#</td><td>#label#</td>';
					var titleclo = 2;
					html3 = yihang(biaoti_arr,shujv_temp,titleclo,html3,arr,3);
					if("${vaccCode}" != ""){
						$("#tablediv").html(html3);
					}
				 }else if("${showType}" == "0" && "${live}" == "0"){
				 	//按社区查询  不分居住类型  表格2数据拼接
					var arr = eval(${maps});
					var biaoti_arr = "${vaccCode}".split("/");
					var shujv_temp = '<td style="">#shequ_name#</td>';
					var titleclo = 1;
					html2 = yihang(biaoti_arr,shujv_temp,titleclo,html2,arr,2);
					if("${vaccCode}" != ""){
						$("#tablediv").html(html2);
					}
				}else{//总合计  表格5数据拼接
					var arr = eval(${all_maps});
					var biaoti_arr = "${vaccCode}".split("/");
					var shujv_temp = '<td>#label#</td>';
					var titleclo = 1;
					html5 = yihang(biaoti_arr,shujv_temp,titleclo,html5,arr,5);
					if("${vaccCode}" != ""){
						$("#tablediv").html(html5);
					}
				} 
			 } 
			 
	        //表格1数据拼接     
			if("${typename}" != null && "${typename}" != "" && "${typename}" != undefined){
			var shujv = "";
				html = html.replace("#yearstr#","${yearstr}").replace("#startmonthstr#","${startmonthstr}").replace("#endmonthstr#", "${endmonthstr}").replace("#vaccCode#", "${vaccCode}");
				html = html.replace("#typename1#","${typename}"+'1').replace("#typename2#","${typename}"+'2');
				//处理表头显示
				var arr = eval(${maps});
				if("${typename}" == "MCV"){
					html = html.replace("#c1#","<th>mr</th><th>mmr</th><th>mm</th><th>mv</th>").replace("#c2#","<th>mr</th><th>mmr</th><th>mm</th><th>mv</th>");
					html = html.replace("#titleclo#",13).replace("#typenameclo1#",6).replace("#typenameclo2#",6);
					html = html.replace("#shizhong#","<th>实种数</th><th>实种数</th><th>实种数</th><th>实种数</th><th>实种数</th><th>实种数</th><th>实种数</th><th>实种数</th>");
					for(var i=0;i<arr.length;i++){
						shujv += "<tr><td>"+arr[i].UNIT_NAME+"</td><td>"+arr[i].MCV_SUM_A_SH+"</td><td>"+arr[i].MR_A_RE+"</td><td>"+arr[i].MMR_A_RE+"</td><td>"+arr[i].MM_A_RE+"</td><td>"+arr[i].MV_A_RE+"</td><td>"+arr[i].MCV_SUM_A_SR+"</td><td>"+arr[i].MCV_SUM_B_SH+"</td><td>"+arr[i].MR_B_RE+"</td><td>"+arr[i].MMR_B_RE+"</td><td>"+arr[i].MM_B_RE+"</td><td>"+arr[i].MV_B_RE+"</td><td>"+arr[i].MCV_SUM_B_SR+"</td></tr>";	
					}
					html = html.replace("#shujv#",shujv);
				}else if("${typename}" == "RCV"){
					html = html.replace("#c1#","<th>mr</th><th>mmr</th>").replace("#c2#","<th>mr</th><th>mmr</th>");
					html = html.replace("#titleclo#",9).replace("#typenameclo1#",4).replace("#typenameclo2#",4);
					html = html.replace("#shizhong#","<th>实种数</th><th>实种数</th><th>实种数</th><th>实种数</th>");
					for(var i=0;i<arr.length;i++){
						shujv += "<tr><td>"+arr[i].UNIT_NAME+"</td><td>"+arr[i].RCV_SUM_A_SH+"</td><td>"+arr[i].MR_A_RE+"</td><td>"+arr[i].MMR_A_RE+"</td><td>"+arr[i].RCV_SUM_A_SR+"</td><td>"+arr[i].RCV_SUM_B_SH+"</td><td></td<td>"+arr[i].MR_B_RE+"</td><td>"+arr[i].MMR_B_RE+"</td><td>"+arr[i].RCV_SUM_B_SR+"</td></tr>";	
					}
					html = html.replace("#shujv#",shujv);
				}else if("${typename}" == "MumCV"){
					html = html.replace("#c1#","<th>mm</th><th>mmr</th>").replace("#c2#","<th>mm</th><th>mmr</th>");
					html = html.replace("#titleclo#",9).replace("#typenameclo1#",4).replace("#typenameclo2#",4);
					html = html.replace("#shizhong#","<th>实种数</th><th>实种数</th><th>实种数</th><th>实种数</th>");
					for(var i=0;i<arr.length;i++){
						shujv += "<tr><td>"+arr[i].UNIT_NAME+"</td><td>"+arr[i].MUMCV_SUM_A_SH+"</td><td>"+arr[i].MM_A_RE+"</td><td>"+arr[i].MMR_A_RE+"</td><td>"+arr[i].MUMCV_SUM_A_SR+"</td><td>"+arr[i].MUMCV_SUM_B_SH+"</td><td></td<td>"+arr[i].MM_B_RE+"</td><td>"+arr[i].MMR_A_RE+"</td><td>"+arr[i].MUMCV_SUM_B_SR+"</td></tr>";	
					}
					html=html.replace("#shujv#",shujv);
				}else if("${typename}" == "HepA"){
					html = html.replace("#c1#","<th>Hepa-l</th><th>Hepa-i</th>").replace("#c2#","<th>Hepa-i</th>");
					html = html.replace("#titleclo#",8).replace("#typenameclo1#",4).replace("#typenameclo2#",3);
					html = html.replace("#shizhong#","<th>实种数</th><th>实种数</th><th>实种数</th>");
					for(var i=0;i<arr.length;i++){
						shujv += "<tr><td>"+arr[i].UNIT_NAME+"</td><td>"+arr[i].HEPA_SUM_A_SH+"</td><td>"+arr[i].HEPAL_RE+"</td><td>"+arr[i].HEPAI_SUM_A_RE+"</td><td>"+arr[i].HEPA_SUM_A_SR+"</td><td>"+arr[i].HEPAI_SUM_B_SH+"</td><td>"+arr[i].HEPAI_B_RE+"</td><td>"+arr[i].HEPAI_SUM_B_SR+"</td></tr>";	
					}
					html = html.replace("#shujv#",shujv);
				}else{
				}
				$("#tablediv").html(html); 
			} 
		 }); 
		
		function yihang(biaoti_arr,shujv_temp,titleclo,html,arr,htnum){
			var vaccname = "";
			var biaoti = "";
			for(var i=0;i<biaoti_arr.length;i++){
				if(biaoti_arr[i] == "hepb"){
					vaccname += '<th colspan="3">HepB1</th><th colspan="3">及时数</th><th colspan="3">HepB2</th><th colspan="3">HepB3</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="HepB1" data-tdType="2" class="tdContent selectColor"><a>#HEPB_A_SH#</a></td><td data-Title="HepB1" data-tdType="3" class="tdContent"><a>#HEPB_A_RE#</a></td><td data-Title="HepB1" data-tdType="2" class="tdContent">#HEPB_A_SR#</td><td data-Title="及时数" data-tdType="2" class="tdContent">#HEPB_AA_SH#</td><td data-Title="及时数" data-tdType="3" class="tdContent">#HEPB_AA_RE#</td><td data-Title="及时数" data-tdType="2" class="tdContent">#HEPB_AA_SR#</td><td data-Title="HepB2" data-tdType="2" class="tdContent"><a>#HEPB_B_SH#</a></td><td data-Title="HepB2" data-tdType="3" class="tdContent"><a>#HEPB_B_RE#</a></td><td data-Title="HepB2" data-tdType="2" class="tdContent">#HEPB_B_SR#</td><td data-Title="HepB3" data-tdType="2" class="tdContent"><a>#HEPB_C_SH#</a></td><td data-Title="HepB3" data-tdType="3" class="tdContent"><a>#HEPB_C_RE#</a></td><td data-Title="HepB3" data-tdType="2" class="tdContent">#HEPB_C_SR#</td>';
					titleclo += 12;
				}
				if(biaoti_arr[i] == "bcg"){
					vaccname += '<th colspan="3">BCG</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="BCG" data-tdType="2" class="tdContent"><a>#BCG_SH#</a></td><td data-Title="BCG" data-tdType="3" class="tdContent"><a>#BCG_RE#</a></td><td data-Title="BCG" data-tdType="2" class="tdContent">#BCG_SR#</td>';
					titleclo += 3;
				}
				if(biaoti_arr[i] == "pv"){
					vaccname+='<th colspan="3">PV1</th><th colspan="3">PV2</th><th colspan="3">PV3</th><th colspan="3">PV4</th>'
					biaoti +='<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp+='<td data-Title="PV1" data-tdType="2" class="tdContent"><a>#PV_A_SH#</a></td><td data-Title="PV1" data-tdType="3" class="tdContent"><a>#PV_A_RE#</a></td><td data-Title="PV1" data-tdType="2" class="tdContent">#PV_A_SR#</td><td data-Title="PV2" data-tdType="2" class="tdContent"><a>#PV_B_SH#</a></td><td data-Title="PV2" data-tdType="3" class="tdContent"><a>#PV_B_RE#</a></td><td data-Title="PV2" data-tdType="2" class="tdContent">#PV_B_SR#</td><td data-Title="PV3" data-tdType="2" class="tdContent"><a>#PV_C_SH#</a></td><td data-Title="PV3" data-tdType="3" class="tdContent"><a>#PV_C_RE#</a></td><td data-Title="PV3" data-tdType="2" class="tdContent">#PV_C_SR#</td><td data-Title="PV4" data-tdType="2" class="tdContent"><a>#PV_D_SH#</a></td><td data-Title="PV4" data-tdType="3" class="tdContent"><a>#PV_D_RE#</a></td><td data-Title="PV4" data-tdType="2" class="tdContent">#PV_D_SR#</td>';
					titleclo += 12;
				}
				if(biaoti_arr[i] == "dtp"){
					vaccname += '<th colspan="3">DTP1</th><th colspan="3">DTP2</th><th colspan="3">DTP3</th><th colspan="3">DTP4</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="DTP1" data-tdType="2" class="tdContent"><a>#DTP_A_SH#</a></td><td data-Title="DTP1" data-tdType="3" class="tdContent"><a>#DTP_A_RE#</a></td><td data-Title="DTP1" data-tdType="2" class="tdContent">#DTP_A_SR#</td><td data-Title="DTP2" data-tdType="2" class="tdContent"><a>#DTP_B_SH#</a></td><td data-Title="DTP2" data-tdType="3" class="tdContent"><a>#DTP_B_RE#</a></td><td data-Title="DTP2" data-tdType="2" class="tdContent">#DTP_B_SR#</td><td data-Title="DTP3" data-tdType="2" class="tdContent"><a>#DTP_C_SH#</a></td><td data-Title="DTP3" data-tdType="3" class="tdContent"><a>#DTP_C_RE#</a></td><td>#DTP_C_SR#</td><td data-Title="DTP4" data-tdType="2" class="tdContent"><a>#DTP_D_SH#</a></td><td data-Title="DTP4" data-tdType="3" class="tdContent"><a>#DTP_D_RE#</a></td><td>#DTP_D_SR#</td>';
					titleclo += 12;
				}
				if(biaoti_arr[i] == "dt"){
					vaccname += '<th colspan="3">DT</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="DT" data-tdType="2" class="tdContent"><a>#DT_SH#</a></td><td data-Title="DT" data-tdType="3" class="tdContent"><a>#DT_RE#</a></td><td data-Title="DT" data-tdType="2" class="tdContent">#DT_SR#</td>';
					titleclo += 3;
				}
				if(biaoti_arr[i] == "mr"){
					vaccname += '<th colspan="3">MR1</th><th colspan="3">MR2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="MR1" data-tdType="2" class="tdContent"><a>#MR_A_SH#</a></td><td data-Title="MR1" data-tdType="3" class="tdContent"><a>#MR_A_RE#</a></td><td data-Title="MR1" data-tdType="2" class="tdContent">#MR_A_SR#</td><td data-Title="MR2" data-tdType="2" class="tdContent"><a>#MR_B_SH#</a></td><td data-Title="MR2" data-tdType="3" class="tdContent"><a>#MR_B_RE#</a></td><td data-Title="MR2" data-tdType="2" class="tdContent">#MR_B_SR#</td>';
					titleclo += 6;
				}
				if(biaoti_arr[i] == "mmr"){
					vaccname += '<th colspan="3">MMR1</th><th colspan="3">MMR2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="MMR1" data-tdType="2" class="tdContent"><a>#MMR_A_SH#</a></td><td data-Title="MMR1" data-tdType="3" class="tdContent"><a>#MMR_A_RE#</a></td><td data-Title="MMR1" data-tdType="2" class="tdContent">#MMR_A_SR#</td><td data-Title="MMR2" data-tdType="2" class="tdContent"><a>#MMR_B_SH#</a></td><td data-Title="MMR2" data-tdType="3" class="tdContent"><a>#MMR_B_RE#</a></td><td data-Title="MMR2" data-tdType="2" class="tdContent">#MMR_B_SR#</td>';
					titleclo += 6;
				}
				if(biaoti_arr[i] == "mm"){
					vaccname += '<th colspan="3">MM1</th><th colspan="3">MM2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="MM1" data-tdType="2" class="tdContent"><a>#MM_A_SH#</a></td><td data-Title="MM1" data-tdType="3" class="tdContent"><a>#MM_A_RE#</a></td><td data-Title="MM1" data-tdType="2" class="tdContent">#MM_A_SR#</td><td data-Title="MM2" data-tdType="2" class="tdContent"><a>#MM_B_SH#</a></td><td data-Title="MM2" data-tdType="3" class="tdContent"><a>#MM_B_RE#</a></td><td data-Title="MM2" data-tdType="2" class="tdContent">#MM_B_SR#</td>';
					titleclo += 6;
				}
				if(biaoti_arr[i] == "mv"){
					vaccname += '<th colspan="3">MV1</th><th colspan="3">MV2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="MV1" data-tdType="2" class="tdContent"><a>#MV_A_SH#</a></td><td data-Title="MV1" data-tdType="3" class="tdContent"><a>#MV_A_RE#</a></td><td data-Title="MV1" data-tdType="2" class="tdContent">#MV_A_SR#</td><td data-Title="MV2" data-tdType="2" class="tdContent"><a>#MV_B_SH#</a></td><td data-Title="MV2" data-tdType="3" class="tdContent"><a>#MV_B_RE#</a></td><td data-Title="MV2" data-tdType="2" class="tdContent">#MV_B_SR#</td>';
					titleclo += 6;
				}
				if(biaoti_arr[i] == "mpv"){
					vaccname += '<th colspan="3">MenA1</th><th colspan="3">MenA2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="MenA1" data-tdType="2" class="tdContent"><a>#MENA_A_SH#</a></td><td data-Title="MenA1" data-tdType="3" class="tdContent"><a>#MENA_A_RE#</a></td><td data-Title="MenA1" data-tdType="2" class="tdContent">#MENA_A_SR#</td><td data-Title="MenA2" data-tdType="2" class="tdContent"><a>#MENA_B_SH#</a></td><td data-Title="MenA2" data-tdType="3" class="tdContent"><a>#MENA_B_RE#</a></td><td data-Title="MenA2" data-tdType="2" class="tdContent">#MENA_B_SR#</td>';
					titleclo += 6;
				}
				if(biaoti_arr[i] == "mpvc"){
					vaccname += '<th colspan="3">MenAC1</th><th colspan="3">MenAC2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="MenAC1" data-tdType="2" class="tdContent"><a>#MENAC_A_SH#</a></td><td  data-Title="MenAC1" data-tdType="3" class="tdContent"><a>#MENAC_A_RE#</a></td><td  data-Title="MenAC1" data-tdType="2" class="tdContent">#MENAC_A_SR#</td><td  data-Title="MenAC2" data-tdType="2" class="tdContent"><a>#MENAC_B_SH#</a></td><td  data-Title="MenAC2" data-tdType="3" class="tdContent"><a>#MENAC_B_RE#</a></td><td  data-Title="MenAC2" data-tdType="2" class="tdContent">#MENAC_B_SR#</td>';
					titleclo += 6;
				}
				if(biaoti_arr[i] == "jevl"){
					vaccname += '<th colspan="3">JE-l1</th><th colspan="3">JE-l2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="JE-l1" data-tdType="2" class="tdContent"><a>#JEL_A_SH#</a></td><td data-Title="JE-l1" data-tdType="3" class="tdContent"><a>#JEL_A_RE#</a></td><td data-Title="JE-l1" data-tdType="2" class="tdContent">#JEL_A_SR#</td><td data-Title="JE-l2" data-tdType="2" class="tdContent"><a>#JEL_B_SH#</a></td><td data-Title="JE-l2" data-tdType="3" class="tdContent"><a>#JEL_B_RE#</a></td><td data-Title="JE-l2" data-tdType="2" class="tdContent">#JEL_B_SR#</td>';
					titleclo += 6;
				}
				if(biaoti_arr[i] == "jevi_vero"){
					vaccname += '<th colspan="3">JE-i1</th><th colspan="3">JE-i2</th><th colspan="3">JE-i3</th><th colspan="3">JE-i4</th>'
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="JE-i1" data-tdType="2" class="tdContent"><a>#JEI_A_SH#</a></td><td data-Title="JE-i1" data-tdType="3" class="tdContent"><a>#JEI_A_RE#</a></td><td data-Title="JE-i1" data-tdType="2" class="tdContent">#JEI_A_SR#</td><td data-Title="JE-i2" data-tdType="2" class="tdContent"><a>#JEI_B_SH#</a></td><td data-Title="JE-i2" data-tdType="3" class="tdContent"><a>#JEI_B_RE#</a></td><td data-Title="JE-i2" data-tdType="2" class="tdContent">#JEI_B_SR#</td><td data-Title="JE-i3" data-tdType="2" class="tdContent"><a>#JEI_C_SH#</a></td><td data-Title="JE-i3" data-tdType="3" class="tdContent"><a>#JEI_C_RE#</a></td><td data-Title="JE-i3" data-tdType="2" class="tdContent">#JEI_C_SR#</td><td data-Title="JE-i4" data-tdType="2" class="tdContent"><a>#JEI_D_SH#</a></td><td data-Title="JE-i4" data-tdType="3" class="tdContent"><a>#JEI_D_RE#</a></td><td data-Title="JE-i4" data-tdType="2" class="tdContent">#JEI_D_SR#</td>';
					titleclo += 12;
				}
				if(biaoti_arr[i] == "hepal"){
					vaccname += '<th colspan="3">HepA-l1</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="HepA-l1" data-tdType="2" class="tdContent"><a>#HEPAL_SH#</a></td><td data-Title="HepA-l1" data-tdType="3" class="tdContent"><a>#HEPAL_RE#</a></td><td data-Title="HepA-l1" data-tdType="2" class="tdContent">#HEPAL_SR#</td>';
					titleclo += 3;
				}
				if(biaoti_arr[i] == "hepai"){
					vaccname += '<th colspan="3">HepA-i1</th><th colspan="3">HepA-i2</th>';
					biaoti += '<th>应种数</th><th>实种数</th><th>接种率%</th><th>应种数</th><th>实种数</th><th>接种率%</th>';
					shujv_temp += '<td data-Title="HepA-i1" data-tdType="2" class="tdContent"><a>#HEPAI_A_SH#</a></td><td data-Title="HepA-i1" data-tdType="3" class="tdContent"><a>#HEPAI_A_RE#</a></td><td data-Title="HepA-i1" data-tdType="2" class="tdContent">#HEPAI_A_SR#</td><td data-Title="HepA-i2" data-tdType="2" class="tdContent"><a>#HEPAI_B_SH#</a></td><td data-Title="HepA-i2" data-tdType="3" class="tdContent"><a>#HEPAI_B_RE#</a></td><td data-Title="HepA-i2" data-tdType="2" class="tdContent">#HEPAI_B_SR#</td>';
					titleclo += 6;
				}
			}//添加一行内容结束 
			
			//循环出所有的信息
			var temp = "";
			shujv_temp = "<tr>"+shujv_temp+"</tr>";//加上行标签
			var temp_bubian = shujv_temp;
			for(var i=0;i<arr.length;i++){
				shujv_temp = temp_bubian;
				if(htnum == "5"){
					if(i%2 == 1){//去除第一行无用的数据
						shujv_temp = shujv_temp.replace('<td rowspan="2" style="">#shequ_name#</td>',"");	
					}
					shujv_temp = shujv_temp.replace("#label#", arr[i].LABEL);//h5 
				}
				if(htnum == "2"){
					shujv_temp = shujv_temp.replace("#shequ_name#",arr[i].UNIT_NAME);//h2
				}
				if(htnum == "3"){
					if(i%2 == 1){
						shujv_temp = shujv_temp.replace('<td rowspan="2" style="">#shequ_name#</td>',"");
					}
					shujv_temp = shujv_temp.replace("#shequ_name#",arr[i].UNIT_NAME).replace("#label#", arr[i].LABEL);
				}
				if(htnum == "4"){
					if(i%2 == 1){
						shujv_temp = shujv_temp.replace('<td rowspan="2" style="">#shequ_name#</td>',"");	
					}
					shujv_temp = shujv_temp.replace("#label#", arr[i].LABEL);
				}
				
				/* 应种显示 */
				shujv_temp = shujv_temp.replace("#HEPB_A_SH#",arr[i].HEPB_A_SH).replace("#HEPB_AA_SH#",arr[i].HEPB_A_SH).replace("#HEPB_B_SH#",arr[i].HEPB_B_SH).replace("#HEPB_C_SH#",arr[i].HEPB_C_SH);
				shujv_temp = shujv_temp.replace("#BCG_SH#",arr[i].BCG_SH);
				shujv_temp = shujv_temp.replace("#PV_A_SH#",arr[i].PV_A_SH).replace("#PV_B_SH#",arr[i].PV_B_SH).replace("#PV_C_SH#",arr[i].PV_C_SH).replace("#PV_D_SH#",arr[i].PV_D_SH);
				shujv_temp = shujv_temp.replace("#DTP_A_SH#",arr[i].DTP_A_SH).replace("#DTP_B_SH#",arr[i].DTP_B_SH).replace("#DTP_C_SH#",arr[i].DTP_C_SH).replace("#DTP_D_SH#",arr[i].DTP_D_SH);
				shujv_temp = shujv_temp.replace("#DT_SH#",arr[i].DT_SH);
				shujv_temp = shujv_temp.replace("#MR_A_SH#",arr[i].MR_A_SH).replace("#MR_B_SH#",arr[i].MR_B_SH);
				shujv_temp = shujv_temp.replace("#MMR_A_SH#",arr[i].MMR_A_SH).replace("#MMR_B_SH#",arr[i].MMR_B_SH);
				shujv_temp = shujv_temp.replace("#MM_A_SH#",arr[i].MM_A_SH).replace("#MM_B_SH#",arr[i].MM_B_SH);
				shujv_temp = shujv_temp.replace("#MV_A_SH#",arr[i].MV_A_SH).replace("#MV_B_SH#",arr[i].MV_B_SH);
				shujv_temp = shujv_temp.replace("#MENA_A_SH#",arr[i].MENA_A_SH).replace("#MENA_B_SH#",arr[i].MENA_B_SH);
				shujv_temp = shujv_temp.replace("#MENAC_A_SH#",arr[i].MENAC_A_SH).replace("#MENAC_B_SH#",arr[i].MENAC_B_SH);
				shujv_temp = shujv_temp.replace("#JEL_A_SH#",arr[i].JEL_A_SH).replace("#JEL_B_SH#",arr[i].JEL_B_SH);
				shujv_temp = shujv_temp.replace("#JEI_A_SH#",arr[i].JEI_A_SH).replace("#JEI_B_SH#",arr[i].JEI_B_SH).replace("#JEI_C_SH#",arr[i].JEI_C_SH).replace("#JEI_D_SH#",arr[i].JEI_D_SH);
				shujv_temp = shujv_temp.replace("#HEPAL_SH#",arr[i].HEPAL_SH);
				shujv_temp = shujv_temp.replace("#HEPAI_A_SH#",arr[i].HEPAI_A_SH).replace("#HEPAI_B_SH#",arr[i].HEPAI_B_SH);
				
				/* 实种显示 */
				shujv_temp = shujv_temp.replace("#HEPB_A_RE#",arr[i].HEPB_A_RE).replace("#HEPB_AA_RE#",arr[i].HEPB_AA_RE).replace("#HEPB_B_RE#",arr[i].HEPB_B_RE).replace("#HEPB_C_RE#",arr[i].HEPB_C_RE);
				shujv_temp = shujv_temp.replace("#BCG_RE#",arr[i].BCG_RE);
				shujv_temp = shujv_temp.replace("#PV_A_RE#",arr[i].PV_A_RE).replace("#PV_B_RE#",arr[i].PV_B_RE).replace("#PV_C_RE#",arr[i].PV_C_RE).replace("#PV_D_RE#",arr[i].PV_D_RE);
				shujv_temp = shujv_temp.replace("#DTP_A_RE#",arr[i].DTP_A_RE).replace("#DTP_B_RE#",arr[i].DTP_B_RE).replace("#DTP_C_RE#",arr[i].DTP_C_RE).replace("#DTP_D_RE#",arr[i].DTP_D_RE);
				shujv_temp = shujv_temp.replace("#DT_RE#",arr[i].DT_RE);
				shujv_temp = shujv_temp.replace("#MR_A_RE#",arr[i].MR_A_RE).replace("#MR_B_RE#",arr[i].MR_B_RE);
				shujv_temp = shujv_temp.replace("#MMR_A_RE#",arr[i].MMR_A_RE).replace("#MMR_B_RE#",arr[i].MMR_B_RE);
				shujv_temp = shujv_temp.replace("#MM_A_RE#",arr[i].MM_A_RE).replace("#MM_B_RE#",arr[i].MM_B_RE);
				shujv_temp = shujv_temp.replace("#MV_A_RE#",arr[i].MV_A_RE).replace("#MV_B_RE#",arr[i].MV_B_RE);
				shujv_temp = shujv_temp.replace("#MENA_A_RE#",arr[i].MENA_A_RE).replace("#MENA_B_RE#",arr[i].MENA_B_RE);
				shujv_temp = shujv_temp.replace("#MENAC_A_RE#",arr[i].MENAC_A_RE).replace("#MENAC_B_RE#",arr[i].MENAC_B_RE);
				shujv_temp = shujv_temp.replace("#JEL_A_RE#",arr[i].JEL_A_RE).replace("#JEL_B_RE#",arr[i].JEL_B_RE);
				shujv_temp = shujv_temp.replace("#JEI_A_RE#",arr[i].JEI_A_RE).replace("#JEI_B_RE#",arr[i].JEI_B_RE).replace("#JEI_C_RE#",arr[i].JEI_C_RE).replace("#JEI_D_RE#",arr[i].JEI_D_RE);
				shujv_temp = shujv_temp.replace("#HEPAL_RE#",arr[i].HEPAL_RE);
				shujv_temp = shujv_temp.replace("#HEPAI_A_RE#",arr[i].HEPAI_A_RE).replace("#HEPAI_B_RE#",arr[i].HEPAI_B_RE);
				
				/* 接种率显示 */
				shujv_temp = shujv_temp.replace("#HEPB_A_SR#",arr[i].HEPB_A_SR).replace("#HEPB_AA_SR#",arr[i].HEPB_AA_SR).replace("#HEPB_B_SR#",arr[i].HEPB_B_SR).replace("#HEPB_C_SR#",arr[i].HEPB_C_SR);
				shujv_temp = shujv_temp.replace("#BCG_SR#",arr[i].BCG_SR);
				shujv_temp = shujv_temp.replace("#PV_A_SR#",arr[i].PV_A_SR).replace("#PV_B_SR#",arr[i].PV_B_SR).replace("#PV_C_SR#",arr[i].PV_C_SR).replace("#PV_D_SR#",arr[i].PV_D_SR);
				shujv_temp = shujv_temp.replace("#DTP_A_SR#",arr[i].DTP_A_SR).replace("#DTP_B_SR#",arr[i].DTP_B_SR).replace("#DTP_C_SR#",arr[i].DTP_C_SR).replace("#DTP_D_SR#",arr[i].DTP_D_SR);
				shujv_temp = shujv_temp.replace("#DT_SR#",arr[i].DT_SR);
				shujv_temp = shujv_temp.replace("#MR_A_SR#",arr[i].MR_A_SR).replace("#MR_B_SR#",arr[i].MR_B_SR);
				shujv_temp = shujv_temp.replace("#MMR_A_SR#",arr[i].MMR_A_SR).replace("#MMR_B_SR#",arr[i].MMR_B_SR);
				shujv_temp = shujv_temp.replace("#MM_A_SR#",arr[i].MM_A_SR).replace("#MM_B_SR#",arr[i].MM_B_SR);
				shujv_temp = shujv_temp.replace("#MV_A_SR#",arr[i].MV_A_SR).replace("#MV_B_SR#",arr[i].MV_B_SR);
				shujv_temp = shujv_temp.replace("#MENA_A_SR#",arr[i].MENA_A_SR).replace("#MENA_B_SR#",arr[i].MENA_B_SR);
				shujv_temp = shujv_temp.replace("#MENAC_A_SR#",arr[i].MENAC_A_SR).replace("#MENAC_B_SR#",arr[i].MENAC_B_SR);
				shujv_temp = shujv_temp.replace("#JEL_A_SR#",arr[i].JEL_A_SR).replace("#JEL_B_SR#",arr[i].JEL_B_SR);
				shujv_temp = shujv_temp.replace("#JEI_A_SR#",arr[i].JEI_A_SR).replace("#JEI_B_SR#",arr[i].JEI_B_SR).replace("#JEI_C_SR#",arr[i].JEI_C_SR).replace("#JEI_D_SR#",arr[i].JEI_D_SR);
				shujv_temp = shujv_temp.replace("#HEPAL_SR#",arr[i].HEPAL_SR);
				shujv_temp = shujv_temp.replace("#HEPAI_A_SR#",arr[i].HEPAI_A_SR).replace("#HEPAI_B_SR#",arr[i].HEPAI_B_SR);
				
				/*大类应种和显示*/
				shujv_temp = shujv_temp.replace("#MCV_SUM_SH#",arr[i].MCV_SUM_SH).replace("#MCV_SUM_RA#",arr[i].MCV_SUM_SH);
				shujv_temp = shujv_temp.replace("#RCV_SUM_SH#",arr[i].RCV_SUM_SH).replace("#RCV_SUM_RA#",arr[i].RCV_SUM_RA);
				shujv_temp = shujv_temp.replace("#MumCV_SUM_SH#",arr[i].MumCV_SUM_SH).replace("#MumCV_SUM_RA#",arr[i].MumCV_SUM_RA);
				shujv_temp = shujv_temp.replace("#HepAL_SUM_SH#",arr[i].HEPB_A_SR).replace("#HepAL_SUM_RA#",arr[i].HEPB_AA_SR);
				shujv_temp = shujv_temp.replace("#HepAI_SUM_SH#",arr[i].HEPB_A_SR).replace("#HepAI_SUM_RA#",arr[i].HEPB_AA_SR);
				
				temp += shujv_temp;
				
			}//循环出所有的信息结束
			html = html.replace("#vaccname#",vaccname);	     
			html = html.replace("#biaoti#",biaoti).replace("#titleclo#",titleclo);
			html = html.replace("#yearstr#","${yearstr}").replace("#startmonthstr#","${startmonthstr}").replace("#endmonthstr#", "${endmonthstr}").replace("#vaccCode#", "${vaccCode}");
			html = html.replace("#shujv#",temp); //加入数据
	 		return html;
		}
		
		
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form id="searchForm" action="${ctx}/export/export/routineVacc6_1"
				method="post">
				<div class="input-group">
					<table style="margin-top: 20px;">
						<tr>
							<td><select id="yearstr" name="yearstr" class="form-control" /></select>
							</td>
							<td>&nbsp;&nbsp;年&nbsp;&nbsp;</td>
							<td><select id="startmonthstr" name="startmonthstr"
								class="form-control"></select></td>
							<td>&nbsp;&nbsp;月&nbsp;&nbsp;~&nbsp;&nbsp;</td>
							<td><select id="endmonthstr" name="endmonthstr"
								class="form-control"></select></td>
							<td>&nbsp;&nbsp;月&nbsp;&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td>&nbsp;&nbsp;显示类型&nbsp;&nbsp;</td>
							<td><select id="showType" name="showType"
								class="form-control" /></select></td>
							<td>
								<button id="btnSubmit"
									style="margin-left: 29px; margin-top: 0px;"
									class="btn btn-primary" type="submit">统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<!-- <td>
								<button id="printBtn" style="margin-left: px; margin-top: 0px;"
									class="btn btn-primary" onclick="print1()">预览</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td> -->
							<td>
								<button id="printBtn" style="margin-top: 0px;"
									class="btn btn-primary" onclick="print1()">打印</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								<button id="exportBt" style="margin-top: 0px;"
									class="btn btn-primary" onclick="SaveAsExcel()">导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td>
								<span id="reCountBt" style="margin-top: 0px;"
									class="btn btn-primary" onclick="reCount()">重新统计</span>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td><a class="btn btn-default" style="margin-top: 0px;"
								href="javascript:goback()">返回</a>
							</td>
						</tr>
					</table>
				</div>
				<div class="input-group" style="margin-top: 20px;">
					<span>疫苗名称</span>&nbsp;&nbsp; <input type="text" id="vaccname"
						name="vaccname" value="${vaccname}" readonly="readonly"
						style="width: 418px;" />
					<input id="morebtn" type="button" style="margin-left: 5px;" onclick="vaccbox()" value="..."> 
					<input type="text" id="vaccCode" name="vaccCode" hidden="hidden" value="${vaccCode}" style="width: 400px;" /> 
					<input type="text" id="typename" name="typename" value="${typename}" hidden="hidden" style="width: 400px;" /> 
					<input type="text" id="live" name="live" hidden="hidden" /> 
					<label id="liveProp_label">
						<input id="liveProp" type="checkbox" value="0" name="liveProp" style="margin-left: 29px;" onclick="shuaxin()" />&nbsp;&nbsp;
						<span style="font-size: 16px;">是否按居住属性</span>
					</label> 
					<label id="zero_label">
						<input id="zerobox" type="checkbox" style="margin-left: 40px;" checked="checked" onchange="isZeroDisplay(this)" />&nbsp;&nbsp;
						<span style="font-size: 16px;">显示零值</span>
					</label>
				</div>
			</form>
		</div>
		<div id="tablediv"
			style="margin: 0 20px; overflow-x: auto; overflow-y: auto; text-align: center;">
		</div>
	</div>
	<div id="contentsShould"></div>
	<div id="contentsActual"></div>



	<div id="threeMonthData" class="hide"></div>
	<script id="modInformShould" type="text/html">
	    
		{{if type==2}}
		<div class="tab-title">
			<button class="buttonList buttonListOne colorSelect" data-index="2">应种</button>
			<button class="buttonList buttonListTwo" data-index="1">应种未种</button>
		</div>
		{{else}}
		<div class="tab-title">
			<button class="buttonList buttonListOne " data-index="2">应种</button>
			<button class="buttonList buttonListTwo colorSelect"  data-index="1">应种未种</button>
		</div>
		{{/if}}
		
		<div class="tableContent">
		<table class="template-table table table-striped table-hover contentTab">
			<thead>
				<tr style="background-color: #aedce7;">
					<th>姓名</th>
					<th style="width:150px">生日</th>
					<th style="width:80px;">母亲姓名</th>
					<th style="width:150px">母亲电话</th>
					<th style="width:80px;">父亲姓名</th>
					<th style="width:150px">父亲电话</th>
					{{if type==1}}
					<th style="width: 100px;">下针可预约时间</th>
					{{else}}
					
					{{/if}}
					
				</tr>
			</thead>
			<tbody>
				{{if data!='undefined'}}
				{{if type==1}}
				{{each data as vaule i}}
				<tr>
					<td>{{vaule.CHILDNAME}}</td>
					<td>{{vaule.BIRTHDAY}}</td>
					<td>{{vaule.GUARDIANNAME}}</td>
					<td>{{vaule.GUARDIANMOBILE}}</td>
					<td>{{vaule.FATHER}}</td>
					<td style="padding-right: 9px;">{{vaule.FATHERPHONE}}</td>
					<td>{{vaule.remindDate}}</td>
				</tr>
				{{/each}}	
				{{else}}
				{{each data as vaule i}}
				<tr>
					<td>{{vaule.CHILDNAME}}</td>
					<td>{{vaule.BIRTHDAY}}</td>
					<td>{{vaule.GUARDIANNAME}}</td>
					<td>{{vaule.GUARDIANMOBILE}}</td>
					<td>{{vaule.FATHER}}</td>
					<td style="padding-right: 9px;">{{vaule.FATHERPHONE}}</td>
				</tr>
				{{/each}}	
				{{/if}}
				{{/if}}
			</tbody>
		</table>
		
		</div>
		<div class="page"></div>
		<button id="exportBt"  style="margin: 20px;float: right;"class="btn btn-primary" onclick="SaveYZWZAsExcel()" >导出3个月应种未种</button>&nbsp;&nbsp;&nbsp;&nbsp;
	</script>

	<script id="modInformActual" type="text/html">
		<div class="tableContent"  style="margin-top:20px;">
		<table class="template-table table table-striped table-hover contentTab">
			<thead>
				<tr style="background-color: #aedce7;">
					<th>姓名</th>
					<th>生日</th>
					<th style="width:80px;">母亲姓名</th>
					<th>母亲电话</th>
					<th style="width:80px;">父亲姓名</th>
					<th>父亲电话</th>
					
				</tr>
			</thead>
			<tbody>
				{{each data as vaule i}}
				<tr>
					<td>{{vaule.CHILDNAME}}</td>
					<td>{{vaule.BIRTHDAY}}</td>
					<td>{{vaule.GUARDIANNAME}}</td>
					<td>{{vaule.GUARDIANMOBILE}}</td>
					<td>{{vaule.FATHER}}</td>
					<td style="padding-right: 9px;">{{vaule.FATHERPHONE}}</td>
					
				</tr>
				{{/each}}
			</tbody>
		</table>
		</div>
		<div class="page">${page}</div>
	</script>

	<script id="modInformMonthThree" type="text/html">
		<table class="template-table table table-striped table-hover contentTab">
			<thead>
				<tr style="background-color: #a6b0b5;">
					<th>姓名</th>
					<th>生日</th>
					<th style="width:80px;">母亲姓名</th>
					<th>母亲电话</th>
					<th style="width:80px;">父亲姓名</th>
					<th>父亲电话</th>
					<th>下一针可预约接种时间</th>
				</tr>
			</thead>
			<tbody>
				{{each data as vaule i}}
				<tr>
					<td>{{vaule.CHILDNAME}}</td>
					<td>{{vaule.BIRTHDAY}}</td>
					<td>{{vaule.GUARDIANNAME}}</td>
					<td>{{vaule.GUARDIANMOBILE}}</td>
					<td>{{vaule.FATHER}}</td>
					<td style="padding-right: 9px;">{{vaule.FATHERPHONE}}</td>
					<td>{{vaule.remindDate}}</td>
				</tr>
				{{/each}}
			</tbody>
		</table>
	</script>
	<script>
		var community='';
		var vaccName='';
		var startMonth='';
		var endMonth='';
		var years='';
		var tabIndex=1;
		var $dialogTab='';
		var pageNum=1;
		var types='';
		var pageSize=15;
		var tabType=2;
		
		function reCount(){  
			var year=$('#yearstr').val();
		    var starMonth=$('#startmonthstr').val();
		    var endMonth=$('#endmonthstr').val();
		    var index = null;
	        $.ajax({
                url: ctx+"/export/export/routineVacc6_1/reCountData",
                type: 'POST',
                dataType: 'json',
                data:{
               		'year':year,
                	'startMonth':starMonth,
                	'endMonth':endMonth
               	},
               	beforeSend: function (request) {
					index = layer.load(1);
				},
               	success:function(res) {
               	 	layer.msg("数据重新生成已完成");
               		layer.close(index);
               	}
            });
	     };
		
		$(function(){
			$("#showType").change(function(){
				var temp=$("#showType").val(); 
				if(temp==3){
					$("#liveProp_label").css("visibility","hidden");
				}else{
					$("#liveProp_label").css("visibility","visible");
				}//visibility:hidden
			});
			
			$(".tdContent a").click(function(){
			    var type=$(this).parent().parent().find("td").eq(0).text();
			    var titleIndex=$(this).parent().attr('data-Title');
			    var index=$(this).parent().attr('data-tdType').toString();
			    var year=$('#yearstr').val();
			    var monthStart=$('#startmonthstr').val();
			    var monthEnd=$('#endmonthstr').val();
			    community=type;
			    vaccName=titleIndex;
			    startMonth=monthStart;
			    endMonth=monthEnd;
			    types=index;
			    years=year;
		    	$.ajax({
	                url: ctx+"/export/export/routineVacc6_1/getShouldInfo",
	                type: 'POST',
	                dataType: 'json',
	                data:{
	                	'community':type,
	                	'vaccName':titleIndex,
	                	'type':index,
	                	'year':year,
	                	'startMonth':monthStart,
	                	'endMonth':monthEnd,
	                	'page':1,
		               	'pageSize':15,
	                },
	                success:function(res) {
	                	
	                	var data=res.page.list;
	                	/* console.log(res.data); */
		                	for(var i=0;i<data.length;i++) {
		                		data[i].BIRTHDAY=showDate(data[i].BIRTHDAY);
		                	}
	                	tabIndex=res.type;
                		var data={"data":data,type:tabIndex,html:res.page.html}
                		if(res.type==2) {
                			
                			//var data={"data":data}
                			var html = template('modInformShould', data);
                			
                            document.getElementById('contentsShould').innerHTML = html;
                            $('.page').html(data.html);
                            $dialogTab=$('#contentsShould');
                            layer.open({
    	          		    	  type: 1,
    	          		    	  title: '详细儿童个案', 
    	          		    	  area: ['950px', '780px'], 
    	          		    	  scrollbar: false,
    	          		    	  content: $dialogTab
    	          		    });
                            document.getElementById('threeMonthData').innerHTML = html;
                		}
                		else if(res.type==3) {
                			var html = template('modInformActual', data);
                            document.getElementById('contentsActual').innerHTML = html;
                            $('.page').html(data.html);
                            $dialogTab=$('#contentsActual');
                            layer.open({
    	          		    	  type: 1,
    	          		    	  title: '实种儿童个案', 
    	          		    	  area: ['950px', '720px'], 
    	          		    	  scrollbar: false,
    	          		    	  content:$dialogTab
    	          		    }); 
                           /*  document.getElementById('threeMonthData').innerHTML = html;	 */
                		}
	                	
	                }
	            }) 
			});
			$(document).on("click",".buttonList",function(){
				$this=$(this);
				var type=$(this).attr("data-index");
				types=type;
				$.ajax({
					 url: ctx+"/export/export/routineVacc6_1/getShouldInfo",
	                 type: 'POST',
	                 dataType: 'json',
	                 data:{
	                	'community':community,
	                	'vaccName':vaccName,
	                	'type':type,
	                	'year':years,
	                	'startMonth':startMonth,
	                	'endMonth':endMonth,
	                	'page':1,
		               	'pageSize':15,
	                },
	                success:function(res) {
	                	$dialogTab.html('');
	                	var data=res.page.list;
	                	if(!data){
	                		data = new Array();
	                	}
	                	for(var i=0;i<data.length;i++) {
	                		data[i].BIRTHDAY=showDate(data[i].BIRTHDAY);
	                	}
	            		var data={"data":data,type:res.type,html:res.page.html} 
	            		var html = template('modInformShould', data);
                        document.getElementById('contentsShould').innerHTML = html;
                        $('.page').html(data.html);
                        $dialogTab=$('#contentsShould');
                        var index = $this.attr("data-index");
                        $(".buttonList").each(function(){
                        	if($(this).attr("data-index") == index){
                        		$(this).addClass("colorSelect").siblings().removeClass('colorSelect');
                        	}
                        })
	                    document.getElementById('threeMonthData').innerHTML = html; 
	                }
				})
			})
		});
		
		function showDate(index) {
			var date = new Date(index);
			Y = date.getFullYear() + '-';
			M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
			D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate());
			return Y+M+D;
		}
		
		function page(n,s){
			var page=n;
			var pageSize=s;
			$.ajax({
				url: ctx+"/export/export/routineVacc6_1/getShouldInfo",
                type: 'POST',
                dataType: 'json',
                data:{
	               	'page':page,
	               	'pageSize':pageSize,
	               	'community':community,
                	'vaccName':vaccName,
                	'type':types,
                	'year':years,
                	'startMonth':startMonth,
                	'endMonth':endMonth
                },
               
               success:function(res) {
            	pageNum=res.page.pageNo;
            	$dialogTab.html('');
            	var data=res.page.list;
               	for(var i=0;i<data.length;i++) {
               		data[i].BIRTHDAY=showDate(data[i].BIRTHDAY);
               	}
           		var data={"data":data,type:res.type,html:res.page.html} 
           		if(types==1||types==2) {
           			var html = template('modInformShould', data);
                    document.getElementById('contentsShould').innerHTML = html;
                    $('.page').html(data.html);
                    $dialogTab=$('#contentsShould');
           		}else if(types==3) {
           			var html = template('modInformActual', data);
                    document.getElementById('contentsActual').innerHTML = html;
                    $('.page').html(data.html);
                    $dialogTab=$('#contentsActual');
           		}
               }
			})
        	return false;
        }
		
		//导出三个月应种未种儿童详细个案信息,格式为Excel
	    function SaveYZWZAsExcel(){
	        try{	
				LODOP = getLodop();
			} catch(err) {
				layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
			};
			if (LODOP == null || LODOP ==''){
				return;
			}
			$.ajax({
				url: ctx+"/export/export/routineVacc6_1/getShouldInfo",
                type: 'POST',
                dataType: 'json',
                data:{
                	'community':community,
                	'vaccName':vaccName,
                	'type':4
                },
                success:function(res) {
                	var data=eval("("+res.page.list+")");
                	/* console.log(res.data); */
                	for(var i=0;i<data.length;i++) {
                		data[i].BIRTHDAY=showDate(data[i].BIRTHDAY);
                	}
                	var data={"data":data}
                	var html = template('modInformMonthThree', data);
                    
                }
			})
			 try{	
				LODOP = getLodop();
			} catch(err) {
				layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
			};
			if (LODOP == null || LODOP ==''){
				return;
			}  
	        LODOP.PRINT_INIT(""); 
	        LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
	       /*  LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F"); */
	        LODOP.ADD_PRINT_TABLE("10mm","10mm","98%","100%",$("#threeMonthData").html()); 
	        LODOP.SAVE_TO_FILE(community+"应种未种详细儿童个案.xlsx");  
	    };
	</script>
</body>
</html>