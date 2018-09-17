<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>划价收费系统</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxBase}/static/common/jeesite.js"></script>
<script src="${ctxStatic}/js/fangwu/template.js"></script>
</head>
	<style>
		*{
			box-sizing: border-box;
		}
		 table{
			width:100%!important;
			margin: 0;
			font-size: 14px;
			word-wrap:break-word; 
			word-break:break-all;
		}
		td{
			height: 22px;
			text-align: center;
			padding: 0 4px;
			word-break: break-all;
		}
		.clr{
			clear: both;
		}
		.nameInfoContainer{
			position: relative;
			margin-top: 10px;
		}
		.nameInfo{
			width: 200px;
			position: absolute;
			right: 100px;
			top: 0;
		}
		.title-1{
			line-height: 40px;
		}
		.ell{overflow:hidden;;white-space:nowrap;}
		
	</style>
<body> 
	<div id="chargeTab"></div>
	<script id="modInform" type="text/html">
	<h3 style="text-align:center;">
		划价收费系统发票统计报表
	</h3>
	<table border="1" cellspacing="0" cellpadding="0" style="width:100%;text-align:center">
		<tbody>
			<tr>
				<td style="width:50%" colspan="2">收银员:{{$data.createByName }}</td>
				<td style="width:50%" colspan="2">收款日期:{{$data.date}}</td>
			</tr>
			<tr>
				<td colspan="4" style="height: 60px;text-align:left;padding:0 20px;"><div>{{$data.billFromTo}}</div>
					
				</td>
			</tr>
			<tr>
				<td colspan="4"" style="text-align:right;padding-right:20px;">共:&emsp;<strong>{{$data.sumNum + $data.expChargeSum+$data.sumNumNo}}</strong>&emsp;张,其中实际打印发票：共&emsp;<strong>{{$data.sumNum}}</strong>&emsp;张</td>
			</tr>
			<tr>
				<td>项目名称</td>
				<td>金额</td>
				<td>项目名称</td>
				<td>金额</td>
			</tr>
			<tr>
				<td>疫苗费</td>
				<td>￥{{$data.sumVaccPrice}}</td>
				<td>伤口处理费</td>
				<td>￥{{$data.sumWounPrice}}</td>
			</tr>
			<tr>
				<td>收入合计</td>
				<td colspan="3"><strong>￥{{$data.sumPrice}}</strong></td> 
			</tr>
			<tr>
				<td>大写金额</td>
				<td colspan="3">{{$data.sumVaccPriceBig}}</td>
			</tr>
		</tbody>
	</table>
	<table border="1" cellspacing="0" cellpadding="0" style="border-top: 0;width:100%;table-layout: fixed;text-align:center">
		<tr>
			<td rowspan="3">其中</td>
			<td>现金金额</td>
			<td>￥{{$data.sumPrice}}</td>
			<td>支票</td>
			<td>￥0.00</td>
		</tr>
		<tr>
			<td>个人账户</td>
			<td>￥0.00</td>
			<td>其他应收</td>
			<td>￥0.00</td>
		</tr>
		<tr>
			<td>货币误差</td>
			<td>￥0.00</td>
			<td>透支金额</td>
			<td>￥0.00</td>
		</tr>
		
		<tr>
			<td rowspan="2">作废发票</td>
			<td colspan="4" style="height: 40px;word-wrap: break-word;text-align:left;padding: 5px;" >
				{{each $data.expBillNum as value i }}
					<div style="display:inline">{{value}}</div>
				{{/each}}
			</td>
		</tr>
		<tr>					
			<td colspan="4">张数：&emsp;<strong>{{$data.expChargeSum}}</strong>&emsp;作废发票金额：￥{{$data.sumVaccPriceNull}}</td>
		</tr>
		<tr>
			<td rowspan="2">退费发票</td>
			<td colspan="4" style="height: 40px;word-wrap: break-word;text-align:left;padding: 5px;">
				{{each $data.returnBillNum as value i }}
					<div style="display:inline">{{value}}</div>
				{{/each}}
			</td>
		</tr>
		<tr>					
			<td colspan="4">张数：&emsp;<strong>{{$data.returnCharge}}</strong>&emsp;退费发票金额：￥{{$data.returnSumPrice}}</td>
		</tr>
	</table>
	<table border="0" cellspacing="0" cellpadding="0" style="float: right;">
		<tfoot >
			<tr style="text-align:right">
				<td>制表: ${fns:getUser().name }&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td>复核:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
				<td colspan="3">制表日期 : &nbsp;&nbsp;&nbsp;{{$data.newdate}}</td>
			</tr>
		</tfoot>
	</table>
	</script>
	<script>
	var beg = sessionStorage.getItem("beg");
	var end = sessionStorage.getItem("end");
	var type = sessionStorage.getItem("type");
	var createById = sessionStorage.getItem("createById");
	function detailTab () {
		$.ajax({
	        url:'${ctx}/charge/printChargeReport',
	        type: 'POST',
	        dataType: 'json',
	        data:{
	        	"beg":beg,
	        	"end":end,
	        	"type":0,
	        	"createById":createById
	        },
	        success:function(data) {
	        	var html = template('modInform', data);
	            document.getElementById('chargeTab').innerHTML = html; 
	        }
	    })
	}
	
	detailTab();
	
	setTimeout(function(){
		window.print();
		setTimeout("top.opener=null;var index_ss = parent.layer.getFrameIndex(window.name);parent.layer.close(index_ss);",100);
	}, 100);
	
	document.onkeydown=function(event){
	    var e = event || window.event || arguments.callee.caller.arguments[0];   
	    if(e && e.keyCode==13){ // enter 键
	      //要做的事情
		  window.print(); 
		  setTimeout("top.opener=null;window.close()",1);  
	    }
	    if(e && e.keyCode==null){
		  window.print(); 
		  setTimeout("top.opener=null;window.close()",1);  
	    }
	}; 
</script>
</body>
</html>