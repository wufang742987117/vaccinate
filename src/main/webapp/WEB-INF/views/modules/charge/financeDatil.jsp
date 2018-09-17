<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title></title>
<meta name="decorator" content="default" />
<style type="text/css">
/*patientInfo.html*/
.wrap {
	background: #fafafa;
	min-width: 430px;
	padding: 10px 20px;
}
.patient-info label {
	text-align: left !important;
	line-height: 34px;
}
.wrap{
	padding-top: 25px;
}
</style>
<script type="text/javascript">
	var pageSize = 10;
	function submitCk(num){
		var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
		if (!reg.test(num)) {
			return false;
		}
		return true;
	}
	
	function submit(){
		//通过业务流水号查询缴费记录初始值
		var billNum = $.trim($("#billNum").val()); 
		if(!/^\d{1,10}$/.test(billNum)){      
        		parent.error("请输入10位以内0-9的数字！");    
		    	return true;
        	}
		if(billNum == ""){
			parent.error("业务流水号为空,退费失败");
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭    
		}
		//获取发票退款总额
		var updateSumPrice = document.getElementById('returnSumPrice').value;
		if(updateSumPrice == ""){
			parent.error("退费总额为空,退费失败");
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭    
		}
		if(!submitCk(updateSumPrice)){
			parent.error("退费总额输入错误，请重新输入");
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭    
		}
		//获取发票剩余退款余额
		 $.ajax({
			type : "POST",
			url : "${ctx}/charge/findChargeCase",
			data : {billNum : billNum},
			dataType : "json",
			success : function(data) {
				if(data.list.length == 0){
					parent.error("不存在发票或为退款发票以及不是本人所开发票都不可退款："+billNum);
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭  
				}
				if((data.list[0].sumPrice - data.list[0].updateSumPrice) < updateSumPrice){
					parent.error("剩余金额不足,退费失败,剩余金额为"+parseFloat(data.list[0].sumPrice - data.list[0].updateSumPrice).toFixed(2));//剩余金额为"+parseFloat(data.list[0].sumPrice - data.list[0].updateSumPrice).toFixed(2)
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭   
				}else{
					$.ajax({
						type : "POST",
						url : "${ctx}/charge/refundCharge",
						dataType: "json",
			            data:{updateSumPrice : updateSumPrice, billNum : billNum},
						success : function(data) {
							if (data.result == true) {
								parent.success("退费成功");
								// parent.init.allShow(1,pageSize);
								// parent.init.detailReceipt(1,pageSize);
								parent.changePageT();
								//window.parent.location.reload();
							    var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
								parent.layer.close(index); //再执行关闭    
							} else {
								parent.error(data.message);
							}
						},
						error : function(error) {
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); //再执行关闭   
							parent.error(error);
						}
					});
				}
			}
		});
	}

	$(document).ready(function() {
		//点击确认数据保存
		$("#btnInsertReturnVaccSave").click(function() {
			layer.confirm('确认退款该发票？', {title: "确认退款",
					  btn: ['确定', '取消',] //可以无限个按钮
					}, function(index, layero){
						//按钮【按钮一】的回调
						layer.close(index);
						submit();				 
					}, function(index){
					  //按钮【按钮二】的回调
					  layer.close(index);
					  return false;
				});
		});
		
		//退出
		$("#btnInsertVaccExit").click(function() {
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
	});
</script>
</head>
<body>
	<div class="wrap">
		<form class="form-horizontal patient-info">
			<div class="form-group">
				<label class="col-xs-4 control-label">业务流水号：<span class="help-inline"><font color="red">*</font> </span></label>
				<div class="col-xs-8">
					<input class="form-control" id="billNum" name="billNum"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label" for="count">退款总额：</label>
				<div class="col-xs-8">
					<input class="form-control" id="returnSumPrice" name="returnSumPrice"/>
				</div>
			</div>
			<div class="form-group">
				<button id="btnInsertVaccExit" class="btn btn-default mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 退出
				</button> 
				<button id="btnInsertReturnVaccSave" class="btn btn-success mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 确认
				</button> 
				
			</div>
		</form>
	</div>
</body>
</html>