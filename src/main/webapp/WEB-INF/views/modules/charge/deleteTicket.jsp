<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>划价收费系统</title>
<meta name="decorator" content="default" />
<style type="text/css">
.wrap {
	background: #fafafa;
	min-width: 430px;
	padding: 10px 20px;
	padding-top: 25px;
	font-size: 16px;
}
.patient-info label {
	text-align: left !important;
	line-height: 34px;
}
.clear{
	clear: both;
}
.ticketInfo{
	margin-top: 10px;
	font-size: 14px;
}
.vacInfo{
	margin-top: 40px;
}
.vacInfo table{
	width: 100%;
}
.vacInfo table {
	border-top: 1px solid #999;
	border-left: 1px solid #999;
}
.vacInfo table thead tr{
	background-color: ;
	height: 40px;
}
.vacInfo table td,.vacInfo table th{
	border-bottom: 1px solid #999;
	border-right: 1px solid #999;
}
.vacInfo table tbody tr td{
	text-align: center;
	height: 40px;
}
.bottom{
	margin-top: 20px;
}
#allPrice{
	color: red;
	font-weight: bold;
}
</style>
<script type="text/javascript">
	var  billNum = '';
	var pageSize = 10;
	var init = {
		showData: function(){
			var url = window.location.href;
			var id = url.split("=")[1];
			billNum = id;
			var data = {
				billNum: id
			};
			$.ajax({
				url: "${ctx}/charge/findChargeCase",
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					if(data.list.length>0){
						var data = data.list;
						init.showPage(data);
					}
					
				}
			})
		},
		showPage: function(data){
			$("#name").text(data[0].patientName);
			$("#billNum").text(data[0].billNum);
			$("#time").text(data[0].createDate);
			$("#allPrice").text(data[0].sumPrice);
			var html = '';
			for(i=0;i<data.length;i++){
				html += `<tr>
							<td>`+data[i].vaccName+`</td>
							<td>`+data[i].vaccBatchnum+`</dt>
							<td>`+data[i].vaccManufacturer+`</dt>
							<td>`+data[i].vaccCount+`</dt>
							<td>`+data[i].vaccPrice+`</dt>
						</tr>`;
			}
			$("#showList").html(html);
		}
	};
	var addEvent =function(){
		var data={
			billNum: billNum
		};
		$("#btnDeleteTicketSave").click(function(){
			layer.confirm('确认提交？', {title: "确认提交",
				  btn: ['确定', '取消',] //可以无限个按钮
				}, function(index, layero){
					layer.close(index);
					//init.submite();
					$.ajax({
						url: "${ctx}/charge/deleteTicketSave",
						data: data,
						type: "post",
						dataType: "json",
						success: function(data){
							if(data.result ==  true){
								parent.success(billNum + "发票作废！");
								// parent.init.allShow(1,pageSize);
								// parent.init.detailReceipt(1,pageSize);
								parent.changePageT();
								//window.parent.location.reload();
								var index_ss = parent.layer.getFrameIndex(window.name);
			 					parent.layer.close(index_ss);
							}else{
								parent.error(data.message);
								var index_ss = parent.layer.getFrameIndex(window.name);
			 					parent.layer.close(index_ss);
							}
						},
						error: function(error){
							parent.error(error);
						}
					})
				  //按钮【按钮一】的回调
				}, function(index){
				  //按钮【按钮二】的回调
				  layer.close(index);
				  return false;
				});
			
		});
	}
	$(document).ready(function() {
		init.showData();
		addEvent();
	});
</script>
</head>
<body>
	<div class="wrap">
		<form class="form-horizontal patient-info">
			<div class="content">
				<div class="ticketInfo">
					<div class="col-xs-3">
						姓名：<span id="name"></span>
					</div>
					<div class="col-xs-4">
						业务流水号：<span id="billNum"></span>
					</div>
					<div class="col-xs-5" style="text-align: right;">
						创建时间：<span id="time"></span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="vacInfo">
					<table>
						<thead>
							<tr>
								<th>疫苗名称</th>
								<th>疫苗批号</th>
								<th>疫苗厂家</th>
								<th>数量</th>
								<th>单价</th>
							</tr>
						</thead>
						<tbody id="showList">
						</tbody>
					</table>
				</div>
				
			</div>
			<div class="bottom">
				<div class="col-xs-3" style="margin-top: 8px;">
					总金额：<span id="allPrice"></span> 元
				</div>
				<div class="col-xs-9">
					<button id="btnDeleteTicketSave" class="btn btn-danger mr15 pull-right" type="button" >
						<span class="glyphicon glyphicon-floppy-remove" aria-hidden="true"></span> 确认作废
					</button> 
				</div>
				<div class="clear"></div>
			</div>
		</form>
	</div>
</body>
</html>