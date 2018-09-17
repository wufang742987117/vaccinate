<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>常规免疫接种情况6-2汇总表</title>
	<style>
		body{margin:0;padding:0;background: rgba(111, 186, 197, 0.55);}
		/* #exportTable{text-align: center;}
		#exportTable .title{font-size: 20px;}
		#exportTable .b{font-weight: bold;}
		#exportTable .subtitle{font-size: 16px; font-weight: normal;}
		#exportTable .text-left{text-align: left !important;}
		#exportTable .text-right{text-align: left !important;}
		#exportTable .tips{line-height: 16px; font-size: 14px;} */
		.table-ctn{
			border:solid 1px black;
			border-collapse:collapse;
		}
		.table-ctn th , .table-ctn td {
			text-align:center;
			padding:6px 8px;
			font-size:14px;
		}
		.w240{ width:240px; }
		.w110{ width:110px; }
	</style>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script type="text/javascript">
		var LODOP; //声明为全局变量
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
	        LODOP.SAVE_TO_FILE("常规免疫接种情况6-2汇总表.xlsx");  
	     };
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
		});
	
		function goback(){
		 	window.location.href="${ctx}/export/export/exportlist";
		}
		 
		function print1(){
		 	$("#searchForm").css("display","none");
		 	window.print();
		}
		 
		function vaccbox(){
			openWindowsH400("${ctx}/export/export/openbox6_2","",690,500);
		}
		 
		function openWindowsH400(url, title, w, h) {
			w = isNull(w) ? 800 : w;
			h = isNull(h) ? 550 : h;
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
		
		function openbox6_2back(text1,text2,text3){
			$("#vaccname").val(text1);
			$("#vaccCode").val(text2);
			$("#typename").val(text3);
		}
		
		function closeForm() {
			layer.closeAll();
		}
		
		$(function(){
			$("#showType").change(function(){
				var temp = $("#showType").val(); 
				if(temp == 3){
					$("#liveProp").css("visibility","hidden");
				}else{
					$("#liveProp").css("visibility","visible");
				}//visibility:hidden
			});
		});
		
		$(function(){
			//表格1
			var html1 = '<table class="table-ctn" border="1">'+
				         	'<thead>'+
							'<tr><th colspan="4" style="font-size:20px;">#yearstr#年#startmonthstr#月~#endmonthstr#月常规免疫接种情况汇总表</th></tr>'+
							'<tr><th colspan="4" style="font-size:16px;">#sheng#&emsp;#shi#&emsp;#qu#&emsp;#off#</th></tr>'+
							'<tr><th class="w240">疫苗名称</th><th class="w110">接种针次数</th><th class="w240">疫苗名称</th><th class="w110">接种针次数</th></tr>'+ 
							'</thead>'+
							'<tbody>'+
								'#shujv#'+
							'</tbody>'+
				     '</table>';
			//表格4数据展示
			var arr = eval(${maps});
			html1 = html1.replace("#yearstr#","${yearstr}").replace("#startmonthstr#","${startmonthstr}").replace("#endmonthstr#", "${endmonthstr}")
				.replace("#sheng#","${sheng}").replace("#shi#","${shi}").replace("#qu#","${qu}").replace("#off#","${off}");
			var shujv_temp = '<tr><td>#name1#</td><td>#num1#</td><td>#name2#</td><td>#num2#</td></tr>';
			var bubian_temp = shujv_temp;
			var temp_html = "";
			if(arr.length%2 == 0){
				for(var i=0;i<arr.length;i+=2){
					shujv_temp = bubian_temp;
					shujv_temp = shujv_temp.replace("#name1#",arr[i].name).replace("#num1#",arr[i].num).replace("#name2#",arr[i+1].name).replace("#num2#",arr[i+1].num);
					temp_html += shujv_temp;
				}
			}else{
				for(var i=0;i<arr.length-1;i+=2){
					shujv_temp = bubian_temp;
					shujv_temp = shujv_temp.replace("#name1#",arr[i].name).replace("#num1#",arr[i].num).replace("#name2#",arr[i+1].name).replace("#num2#",arr[i+1].num);
					temp_html += shujv_temp;
				}
				shujv_temp = bubian_temp;
				shujv_temp = shujv_temp.replace("#name1#",arr[arr.length-1].name).replace("#num1#",arr[arr.length-1].num).replace("#name2#","").replace("#num2#","");
				temp_html += shujv_temp;
			}
			html1 = html1.replace("#shujv#", temp_html);
			$("#tablediv").html(html1);
		}); 
	
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
				$("#tablediv > table > tbody > tr").each(function(i,t){
					$(t).children().each(function(ii,tt){
							var ttv =  $(tt).html();
							if(!isNaN(ttv) && ttv == '0'){
								$(tt).text("");
							}
					});
				});
			}
		}
		
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" action="${ctx}/export/export/routineVacc6_2" method="post">
				<div class="input-group"  >
					<table style="margin-top:20px;font-size:14px;">
						<tr>
							<td>
								<select id="yearstr"  name="yearstr" class="form-control"/></select>
							</td>
							<td>&nbsp;&nbsp;年&nbsp;&nbsp;</td>
							<td>
								<select id="startmonthstr" name="startmonthstr" class="form-control"></select>
							</td>
					 		<td>&nbsp;&nbsp;月&nbsp;&nbsp;~&nbsp;&nbsp;</td>
							<td>
								<select id="endmonthstr" name="endmonthstr" class="form-control"></select>
							</td>
							<td>&nbsp;&nbsp;月&nbsp;&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
				            <td>
				            	<button id="btnSubmit" style="margin-left:60px;" class="btn btn-primary" type="submit" >统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
				            </td>
				            <!-- <td>
				           		<button id="printBtn" class="btn btn-primary" onclick="print1()">预览</button> &nbsp;&nbsp;&nbsp;&nbsp;
				            </td> -->
				            <td>
				            	<button id="printBtn" class="btn btn-primary" onclick="print1()">打印</button> &nbsp;&nbsp;&nbsp;&nbsp;
				            </td>
				            <td>
				            	<button id="exportBt" class="btn btn-primary" onclick="SaveAsExcel()"  >导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
				            </td>
				            <td>
				            	<a class="btn btn-default" href="javascript:goback()">返回</a>
				            </td>
						</tr>
					</table>
				</div>
				<div class="input-group" style="margin-top: 20px;" >
					<span>疫苗名称</span>&nbsp;&nbsp;
					<input type="text" id="vaccname" name ="vaccname"  value="${vaccname}" readonly="readonly" style="width:418px;"/> 
					<input id="morebtn" type="button" style="margin-left:5px;" onclick="vaccbox()" value="...">
					<input type="text" id="vaccCode" name ="vaccCode" hidden="hidden" value="${vaccCode}"style="width: 400px;"/> 
					<input type="text" id="typename" name="typename" value="${typename}" hidden="hidden" style="width: 400px;"/> 
					<label id="zero_label" style="margin-left:50px;" ><input  id="showNull" name="showNull" type="checkbox"checked="checked" onchange="isZeroDisplay(this)"/>&nbsp;&nbsp;<span style="font-size: 16px;">显示零值</span></label>
				</div>
			</form:form>
		</div>
	</div>
	<div id="tablediv" style="margin: 0 20px;overflow-x:auto; overflow-y:auto;">
	</div>
</body>
</html>