<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp"%>
<html>
<head>
<title>计划免疫疫苗使用情况报表</title>
<!-- <meta name="decorator" content="default" /> -->
<script type="text/javascript" src="${ctxBase}/static/common/jeesite.js"></script>
<script src="${ctxStatic}/js/fangwu/template.js"></script>
<style type="text/css">
	body {
		margin: 0;
		padding: 0;
		background: rgba(111, 186, 197, 0.55);
	}
	#yearStr{margin-left: 50px;}
	.ml20 {
		margin-left:20px;
	}
	#contentVaccine input,#contentcharges input {
		width:50px;
	}
	input:-webkit-autofill,
	input:-webkit-autofill:hover,
	input:-webkit-autofill:focus {
	box-shadow:0 0 0 60px #eee inset;
	-webkit-text-fill-color: #878787;
	}
	.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
	    /* font-size: 16px!important; */
	}
	#contentBox p input {
		background:none;
		border:0;
		border-bottom:1px solid rgb(43, 38, 38);
		text-align:center;
	}
	.table{
		width: auto;
		margin-left: 50px;
	}
</style>
</head>
<body>
	<div>
        <div class="searchForm form-inline mt20">
				<div class="input-group">
					<table>
						<tr>
							<td><select id="yearstr" name="yearstr" class="form-control"/></select>
							</td>
							<td>&nbsp;&nbsp;年&nbsp;&nbsp;</td>
							<td><select id="monthstr" name="monthstr"
								class="form-control"></select></td>
							<td>&nbsp;&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td>
								<button id="btnSubmit"
									style="margin-left: 29px; margin-top: 0px;"
									class="btn btn-primary" type="submit">统计</button>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>

							<td>
								<button id="exportBt" style="margin-top: 0px;"
									class="btn btn-primary" onclick="SaveAsExcel()">导出</button>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							
							<td>
								<button id="exportBt" style="margin-top: 0px;"
									class="btn btn-primary" onclick="printExp()">打印</button>&nbsp;&nbsp;&nbsp;&nbsp;
							</td>
							<td><a class="btn btn-default" href="javascript:history.go(-1)">返回</a>
							</td>
						</tr>
					</table>
				</div>
			
				<div id="contentBox"></div>
				<script id="modInformCharge" type="text/html">
				
				<div class="content-detail" id="excelBox" style="padding-top: 30px;">
					
					<table id="contentTable" class="table table-striped table-bordered table-condensed" style="text-align:center">
						<thead>
							<tr>
								<td colspan="8">
									<span style="text-align:center;font-size: 18px;font-weight: bold;">{{year}}年{{month}}月计划免疫疫苗使用情况报表</span>
								</td>
							</tr>
							<tr>
								<td rowspan="2">疫苗</td>
								<td rowspan="2">规格</td>
								<td rowspan="2">上月库存数</td>
								<td colspan="3">{{month}}</td>
								<td rowspan="2">本月底</br>库存数</td>
								<td rowspan="2">下月需求</td>
							</tr>
							<tr>
								<td>领苗数</td>
								<td>使用数</td>
								<td>报废数</td>
							</tr>
						</thead>
					     <tbody>
					     	{{each data as value i}}
							<tr>
								<td>{{value.NAME}}</td>
								<td>{{value.SPECIFICATION}}</td>
								<td>{{value.LAST_NUM}}</td>
								<td>{{value.IN_NUM}}</td>
								<td>{{value.USE_NUM}}</td>
								<td>{{value.OUT_NUM}}</td>
								<td>{{value.REST_NUM}}</td>
								<td>{{value.NEXT_NEED}}</td>
							</tr>
							{{/each}}
							<tr style="text-align:left">
								<td colspan="8">
									<p>注：本月底库存数=上月库存数+本月领苗数-本月下发数-本月使用数-本月报废数</p>
									<p>填表单位（盖公章）:&nbsp;&nbsp;{{officeName}}卫生院预防接种门诊 &nbsp;&nbsp;填表人签名:{{name}}&nbsp;&nbsp;  单位法人代表签名：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 填表日期 ： {{nowyear}}年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;月 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日</p>
								</td>
							</tr>
					 	</tbody>
					</table>
					
				</div>
				</script>
			</div>
		</div>
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script>
	var date=new Date; 
	var nowyear=date.getFullYear();
	
	$(document).ready(function(){
		
		//为年份赋值
		for(var i=2008;i<2051 ;i++){
			$("#yearstr").append("<option value="+i+">"+i+"</option>");//添加option
		}
		$("#yearstr").val(${yearstr});
		//为月份赋值   起始
		for(var i=1;i<=12 ;i++){
			$("#monthstr").append("<option value="+i+">"+i+"</option>");//添加option
		}
		$("#monthstr").val(${startmonthstr});
		//为月份赋值   截止
		/* var html = template('modInformCharge', '');
		
        document.getElementById('contentcharges').innerHTML = html; */
       
        $("#yearstr  option[value='"+nowyear+"'] ").attr("selected",true);
       
	})
	var defaultYear=2017,
	defaultMonth=1;
	
	$("#btnSubmit").click(function(){
		   var year=$("#yearstr").val(),
		   month=$("#monthstr").val();
		   defaultYear=year;
		   defaultMonth=month;
		   var index = null;
    	   $.ajax({
			   url: ctx+"/export/export/withinPlanVaccUseData",
               type: 'POST',
               dataType: 'json',
               data:{
               	'year':year,
               	'month':month
               },
               beforeSend: function (request) {
					index = layer.load(1);
				},
               success:function(res) {
               	   layer.close(index);
            	   var datalist =  JSON.parse(res.data);
            	   var username=res.userName;
            	   var officeName=res.officeName;
            	   var data={
            			"data":datalist,
            			"year":defaultYear,
            			"month":defaultMonth,
            			"name":username,
            			"officeName":officeName,
            			"nowyear":nowyear
            	   }
            	   var html = template('modInformCharge', data);
           		
                   document.getElementById('contentBox').innerHTML = html;
            	   
               }
    	   })
       })
	
       	var LODOP; //声明为全局变量
		function SaveAsExcel(){  
			var year=$("#yearstr").val();
		    var month=$("#monthstr").val();
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
	        LODOP.ADD_PRINT_TABLE("10mm","10mm","98%","100%",$("#excelBox").html()); 
	        //LODOP.SET_SAVE_MODE("QUICK_SAVE",true);//快速生成（无表格样式,数据量较大时或许用到）
	        LODOP.SAVE_TO_FILE(year + "年" + month + "月计划免疫疫苗使用情况报表.xlsx");  
	        /* LODOP.PREVIEW();
	        LODOP.PRINT(); */
	     };
	     
	     function printExp(){
	     	var bodyData = $(document.body).html();
	     	var printData = document.getElementById("excelBox").innerHTML;
	     	window.document.body.innerHTML = printData;
	     	window.print();
	     	window.document.body.innerHTML = bodyData;
		};
				
	</script>
</body>
</html>
