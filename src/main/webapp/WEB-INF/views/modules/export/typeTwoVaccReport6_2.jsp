<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>第二类疫苗接种情况报表6-2</title>
	<style>
		body{margin:0;padding:0;background: rgba(111, 186, 197, 0.55);}
		.table-ctn{
			border:solid 1px black;
			border-collapse:collapse;
			margin-top:30px;
		}
		.table-ctn th , .table-ctn td {
			text-align:center;
			padding:6px 8px!important;
			font-size:14px;
		}
		.w200{ width:200px; }
		.w100{ width:100px; }
		#searchForm{padding-top: 30px;}
	</style>
	<script type="text/javascript" src="${ctxStatic}/js/LodopFuncs.js"></script>
	<c:if test="${not empty arg }">
		<script type="text/javascript">			
			$(function(){
				layer.msg('你输入的时间格格式有误',{icon: 2});
			})
		</script>		
	</c:if>
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
	        LODOP.SAVE_TO_FILE("第二类疫苗接种情况报表6-2.xlsx");
	     };
		$(document).ready(function(){
			for(var i=2008;i<2051 ;i++){
				$("#timeyear").append("<option value="+i+">"+i+"</option>");//添加option
				$("#timeyear").val(${yearstr});
			}
			for(var i=1;i<=12 ;i++){
				$("#timemonth").append("<option value="+i+">"+i+"</option>");//添加option
				$("#timemonth").val(${monthstr});
			}
		});
		
		function goback(){
			window.location.href="${ctx}/export/export/exportlist";
		}
		
		function print1(){
			$("#notPrint").css("display","none");
			window.print();
		}
		 
		$(function(){
				//表格1
			var html1 = '<table class="table-ctn" border="1">'+
				         	'<thead>'+
							'<tr><th colspan="4" style="font-size:20px;">#yearstr#年#monthstr#月常规免疫接种情况汇总表</th></tr>'+
							'<tr><th colspan="4" style="font-size:16px;">#sheng#&emsp;#shi#&emsp;#qu#&emsp;#off#</th></tr>'+
							'<tr><th class="">疫苗名称</th><th class="">接种针次数</th><th class="">疫苗名称</th><th class="">接种针次数</th></tr>'+ 
							'</thead>'+
							'<tbody>'+
								'#shujv#'+
								'<tr><td colspan="4"  class="tips text-left">填写说明：本表用于全人群第二类疫苗接种情况报告；乡级防保组织每月5日前汇总上报县级疾病预防控制机构，<br>&emsp;&emsp;&emsp;&emsp;&emsp;县级疾病预防控制机构每月10日前录入上报国家信息管理平台。</td></tr>'+
								'<tr><td colspan="4" class="text-left">填报日期：#date# &emsp;&emsp;&emsp;填报单位：#off1# &emsp;&emsp;&emsp;&emsp;填报人：#username#</td></tr>'+
							'</tbody>'+
				     '</table>';
			//表格4数据展示
			var arr = eval(${maps});
			html1 = html1.replace("#yearstr#","${yearstr}").replace("#monthstr#", "${monthstr}").replace("#date#","${date}")
				.replace("#sheng#","${sheng}").replace("#shi#","${shi}").replace("#qu#","${qu}").replace("#off1#", "${off}").replace("#username#", "${username}").replace("#off#", "${off}");
			var shujv_temp = '<tr><td>#name1#</td><td>#num1#</td><td>#name2#</td><td>#num2#</td></tr>';
			var bubian_temp = shujv_temp;
			var temp_html = "";
			if(arr.length%2==0){
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
		
		function upload(){
				var load_idx = layer.load();
				var data = new Array();
				data.push('${officeCode}');
				data.push('${yearstr}'+pad('${monthstr}',2));
				console.info("上报6-2：",data);
				$.ajax({
					url:"${ctx}/export/export/upload/uploadRoutineImmuReport",
					data:{"data":data}, 
					type:"POST",
					traditional: true,
					success:function(data){
						layer.close(load_idx);
						layer.msg("上报数据:" + data);
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						layer.close(load_idx);
						layer.msg("上报失败："+XMLHttpRequest.status+textStatus,{"icon":2});	
					}
				});
			}
	</script>
</head>
<body>	
	<div id="notPrint">
		<form:form id="searchForm" modelAttribute="export" action="${ctx}/export/export/typeTwoVaccReport6_2" method="post" class="form-inline">
			<form:hidden path="id"/>
			<div class="form-group" style="margin: 0 20px ;">
			    <div class="input-group">
			      <select id="timeyear" name="yearstr" class="form-control" style="width:80px; margin-right: 0;"/></select>
			      <div class="input-group-addon gray-bg" style="width:42px;">年</div>
			    </div>
			</div>
			<div class="form-group" style="margin: 0 20px 0 0;">
			    <div class="input-group">
			      <select id="timemonth" name="monthstr" class="form-control" style="width:80px; margin-right: 0;"></select>
			      <div class="input-group-addon gray-bg" style="width:42px;">月</div>
			    </div>
			</div>
			<input id="btnSubmit" style="margin-left:80px;" class="btn btn-primary" type="submit" value="统计" />&nbsp;&nbsp;&nbsp;&nbsp;
			<button class="btn btn-primary" onclick="print1()">打印</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-primary" style="margin-top:0px;" href="javascript:upload()">上报</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-primary" onclick="SaveAsExcel()">导出</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-default" href="javascript:goback()">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</form:form>
	</div>
	<div id="tablediv" style="margin:0 20px;overflow-x:auto; overflow-y:auto;text-align:center;">
	
	</div>
</html>