<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>疫苗销存月报</title>
<meta name="decorator" content="default" />
<style type="text/css">
	#recordTbody tr td{
		padding: 2px;
	}
	
	.inputInTable{
	    padding: 0px 2px;
	    border: none;
        width: 100%;
    }
</style>
<script type="text/javascript">
	$(function(){
		$("#inputForm").validate({
			submitHandler : function(form) {
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")|| element.is(":radio")|| element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
				
		});
			
		/* 初始化表格 */
// 		var recs = JSON.parse('${vacclist}');
		var recs = new Array();
		for(var i in recs){
			var rec = recs[i];
			var targetTds = $("#recordTbody>tr[data-id=" + rec.vaccineid + "]>td");
			for(var j in targetTds){
				var _td = $(targetTds[j]);
				if(!_td.html()){
					_td.html(SimpleDateFormat(new Date(rec.vaccinatedate),"yyyy-MM-dd"));
					_td.addClass("history");
// 					_td.attr("data-id",rec.id);
					break;
				}
			}
		}
		
	});
	
</script>
<style type="text/css">
.date-wrap{position: relative;}
.date{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
.inputclass{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
table{-moz-user-select: none; -khtml-user-select: none; user-select: none; }
table input,select{background: rgba(255,255,255,0.65) !important; }
/* .tr_color{background-color: rgba(245, 245, 246, 0.5);} */
.tr_color{background-color: rgba(245, 245, 211, 0.23);} 
.history{background-color: #AEEEEE}
</style>
</head>
<body>
	<div class="ibox">
			<div class=" col-sm-12 " >
				<label style="font-size: 1.8em;" class="col-sm-12 text-center">销存月报表</label>
				<sys:message content="${message}" />
				
				<div class="searchForm">
					
				</div>
				<table class="table  table-bordered table-condensed"  id="table">
					<thead>
						<tr>
							<th style="color: black;" rowspan="2">疫苗名称</th>
							<th style="color: black;" rowspan="2">生产厂商</th>
							<th style="color: black;" rowspan="2">上月库存</th>
							<th style="color: black;" colspan="4">本月发生</th>
							<th style="color: black;"rowspan="2">本月库存</th>
						</tr>
						<tr>
							<th style="color: black;">入库</th>
							<th style="color: black;">出库</th>
							<th style="color: black;">退回</th>
							<th style="color: black;">报废</th>
						</tr>
					</thead>
					<tbody id="recordTbody">
<%-- 						<c:forEach items="${list}" var="bsManageVaccine" varStatus="vs">
								<tr data-mNum='${bsManageVaccine.mNum}' data-id='${bsManageVaccine.id}' data-idx='${vs.index}'>
									<td class="bsManage" >${bsManageVaccine.name }</td>
									<td class="Manage" data-pin='1'></td>
									<td class="Manage" data-pin='2'></td>
									<td class="Manage" data-pin='3'></td>
									<td class="Manage" data-pin='4'></td>
									<td class="Manage" data-pin='5'></td>
								</tr>	
						</c:forEach> --%>
						
					</tbody>
				</table>
			</div>
				<div class="col-sm-8" >
					<div  style=" float: left;width: 100%;">
						<label><input type="radio" class="record_addr" name="record_choose" value="1" style="text-align: left;">补录本地接种信息</label>
					</div>
					<div  style=" float: left;width: 100%;">
						<label>
							<input type="radio" class="record_addr" name="record_choose" checked value="2">补录外地接种信息
							<span class="record_addr_info">&nbsp;&nbsp;&nbsp;接种地点：<input type="text" id="record_addr" name="record_addr" value="0000000000"></span>
						</label>
					</div>
				</div>
				<div class="col-sm-4" style="text-align: center;" >
					<button type="button" class="btn btn-lg btn-primary" id="btnsave"  >保存</button>
					<button type="button" class="btn btn-lg btn-primary" id="btncancel" >取消</button>
				</div>
		</div>
</body>
</html>