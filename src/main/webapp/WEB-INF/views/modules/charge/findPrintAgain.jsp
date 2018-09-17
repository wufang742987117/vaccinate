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
	$(document).ready(function(){
		//退出
		$("#btnInsertVaccExit").click(function() {
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
		
		$("#btnInsertReturnVaccSave").click(function(){
			submit();
		});
		
	});
		
	function submit(){
		//通过业务流水号查询缴费记录初始值
		var billNum = $.trim($("#billNum").val()); 
		if(billNum == ""){
			parent.error("业务流水号为空,查询失败");
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭    
		}
		if(!/^\d{1,10}$/.test(billNum)){      
    		parent.error("请输入10位以内0-9的数字！");    
	    	return true;
    	}
		var url = "${ctx}/charge/printChargeAgain?billNum="+billNum;
		parent.alertForm(url, "发票信息", "1000", "600");
		$("#btnInsertVaccExit").click();
	}
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