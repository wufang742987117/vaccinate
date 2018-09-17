<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<!DOCTYPE html>
<html>
<head>
	<title>7-2</title>
	<style type="text/css">
		@page { size: landscape; }
		.table-ctn{
			border:solid 1px black;
			border-collapse:collapse;
			margin-top:40px;
		}
		.table-ctn th , .table-ctn td {
			text-align:center;
			padding:6px 8px!important;
			font-size:14px;
		}
	</style>
	<meta name="decorator" content="default"/>
	<script type="text/javascript" src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script type="text/javascript">

		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        $(document).ready(function(){
			for(var i=2017;i<2051 ;i++){
				$("#timeyear").append("<option value="+i+">"+i+"</option>");//添加option
				$("#timeyear").val(${yearstr});
			}
			for(var i=1;i<=12 ;i++){
				$("#timemonth").append("<option value="+i+">"+i+"</option>");//添加option
				$("#timemonth").val(${monthstr});
			}
			
		});
		
		function print1(){
			$("#notPrint").css("display","none");
			window.print();
		}
		
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
	        LODOP.ADD_PRINT_TABLE("10mm","10mm","98%","100%",$("#tableprint").html()); 
	        LODOP.SAVE_TO_FILE("第二类疫苗进销存报表7-2.xlsx");
	     };
		
		function goback(){
			window.location.href="${ctx}/export/export/exportlist";
		}
		
		/* 打开详细列表页面 */
		function detailList(thi){
			var _this = $(thi); 
			if(_this.html() && _this.html().trim() == '0'){
				return;
			}
			var vaccineid = _this.parent().attr("data-vaccineid");
			var batch= _this.parent().attr("data-batchno");
			var manufacturer= _this.parent().attr("data-manufacturer");
			var type = _this.attr("data-type");
			var yearmonth = '${yearstr}'+pad('${monthstr}',2);
			window.open('${ctx}/product/bsManageCheck/detailListTwo?vaccineid='+ vaccineid + '&batch='+ batch + '&manufacturer=' + manufacturer + '&yearmonth=' + yearmonth+ '&type=' + type ,"_blank");
		}
		
		function isZeroDisplay(thi){
			var _this = $(thi);
			if(_this.is(":checked")){
				//选中状态(显示0值) 
				$("#tableprint > table > tbody > tr").each(function(i,t){
					$(t).children().each(function(ii,tt){
							var ttv =  $(tt).html();
							if(!isNaN(ttv) && ttv == ""){
								$(tt).text("0");
							}
					});
				});
			}else{
				//取消状态(不显示0值)
				$("#tableprint > table > tbody > tr>td").each(function(i,t){
					$(t).children().each(function(ii,tt){
						var ttv =  $(tt).html();
						if(!isNaN(ttv) && (ttv == '0' || ttv == '0.0')){
							$(tt).text("");
						}
					});
				});
			}
		}
		
		$(document).ready(function(){
			var html="<tr><td colspan='3'>合计</td>";
			var sum=0;
			$(".lastwcount").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".lastjcount").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".lastmoney").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".income").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".incomemoney").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".sellcount").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".sellcost").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".sellincome").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".vacccount").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".vacccost").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".vaccincome").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".discount").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".baofei").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".nwcount").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".njcount").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td>";
			sum=0;
			$(".nmoney").each(function(i,val){
				sum+=$(this).text()*1;
			});
			html+="<td>"+sum+"</td><td/><td/><td/></tr>";
			html+="<tr><td colspan='11'>填报人：${username}</td><td colspan='11'>填报日期：${date}</td></tr>";
			$("#tableprint  tbody").append(html);
		});
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<div id="notPrint" style="margin-top:10px;">
			<form:form id="searchForm" modelAttribute="export" action="${ctx}/export/export/Exp_vacc_7_2" method="post" class="form-inline">
			<div class="form-group" style="margin: 0 20px ;">
			    <div class="input-group">
			      <select id="timeyear" name="yearstr" class="form-control" style="width:90px; margin-right: 0;"/></select>
			      <div class="input-group-addon gray-bg" style="width:50px;">年</div>
			    </div>
			</div>
			<div class="form-group" style="margin: 0 20px 0 0;">
			    <div class="input-group">
			      <select id="timemonth" name="monthstr" class="form-control" style="width:90px; margin-right: 0;"></select>
			      <div class="input-group-addon gray-bg" style="width:50px;">月</div>
			    </div>
			</div>
			<label id="zero_label"><input
						id="zerobox" type="checkbox" style="margin-left: 50px;"
						checked="checked" onchange="isZeroDisplay(this)" />&nbsp;&nbsp;<span
						style="font-size: 16px;">显示零值</span>
			</label>
			<input id="btnSubmit" style="margin-left:144px;" class="btn btn-primary" type="submit" value="统计" />&nbsp;&nbsp;&nbsp;&nbsp;
			<button class="btn btn-primary" onclick="print1()">打印</button>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-primary" onclick="SaveAsExcel()">导出</a>&nbsp;&nbsp;&nbsp;&nbsp;
			<a class="btn btn-default" href="javascript:goback()">返回</a>&nbsp;&nbsp;&nbsp;&nbsp;
		</form:form>
		</div>
			<sys:message content="${message}"/>
			<div id="tableprint" style="margin:-10px 20px;overflow-x:auto; overflow-y:auto;text-align:center;">
			<table class="table-ctn" border="1">
				<thead>
					<tr><th colspan="22" style="font-size:28px;">${yearstr}年${monthstr}月${off}二类疫苗汇总表</th></tr>
					<tr>
					<th>名称</th>
					<th>批号</th>
					<th>厂家</th>
					<th>上月预售</th>
					<th>上月库存结余</th>
					<th>上月库存金额</th>
					<th>本月领苗</th>
					<th>本月领苗金额</th>
					<th>本月售苗数</th>
					<th>本月售苗成本</th>
					<th>本月售苗收入</th>
					<th>本月接种数量</th>
					<th>本月接种成本</th>
					<th>本月接种收入</th>
					<th>优惠数量</th>
					<th>出库数量</th>
					<th>本月预售数</th>
					<th>本月库存结余</th>
					<th>库存金额</th>
					<th>成本价</th>
					<th>销售单价</th>
					<th>备注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${tables}" var="Exp_vacc_7_2" varStatus="status">
					<tr data-vaccineid='${Exp_vacc_7_2.vaccineid}' data-batchno='${Exp_vacc_7_2.batchno}' data-manufacturer='${Exp_vacc_7_2.product.code}'>
					     <%-- <th style="width: 50px;">${status.index + 1}</th> --%>
						 <td>${Exp_vacc_7_2.vaccname}</td>
						 <td>${Exp_vacc_7_2.batchno}</td>
						 <td>${Exp_vacc_7_2.manufacturer}</td>
						<td class="lastwcount">${Exp_vacc_7_2.lastwcount}</td>
					    <td class="lastjcount">${Exp_vacc_7_2.lastjcount}</td>
						 <td class="lastmoney">${Exp_vacc_7_2.lastmoney}</td>
				<!-- 		 <td class="link" data-type='IN' onclick="detailList(this)">  -->
						<td class="income">${Exp_vacc_7_2.income}</td>
						 <td class="incomemoney">${Exp_vacc_7_2.incomemoney}</td>
				<!-- 		<td class="link" data-type='IN' onclick="detailList(this)">  -->
						<td class="sellcount">${Exp_vacc_7_2.sellcount}</td>
						<td class="sellcost">${Exp_vacc_7_2.sellcost}</td>
						 <td class="sellincome">${Exp_vacc_7_2.sellincome}</td>
				<!--  		 <td class="link" data-type='IN' onclick="detailList(this)"> -->
						<td class="vacccount">${Exp_vacc_7_2.vacccount}</td>
						<td class="vacccost">${Exp_vacc_7_2.vacccost}</td>
					    <td class="vaccincome">${Exp_vacc_7_2.vaccincome}</td>
						 <td class="discount">${Exp_vacc_7_2.discount}</td>
						 <td class="baofei">${Exp_vacc_7_2.baofei}</td>
						 <td class="nwcount">${Exp_vacc_7_2.nwcount}</td>
						<td class="njcount">${Exp_vacc_7_2.njcount}</td>
						<td class="nmoney">${Exp_vacc_7_2.nmoney}</td>
						 <td>${Exp_vacc_7_2.costprice}</td>
						 <td>${Exp_vacc_7_2.sellprice}</td>
						<td>${Exp_vacc_7_2.remarks}</td>
						<%-- <td><a href="">
							<fmt:formatDate value="${Exp_vacc_7_2.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</a></td> --%>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			</div>
		</div>
	</div>
</body>
</html>