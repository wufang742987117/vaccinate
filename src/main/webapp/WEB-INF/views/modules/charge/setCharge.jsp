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
}

.patient-info label {
	text-align: left !important;
	line-height: 34px;
}
.content{
	margin-top: 20px;
}
.choose-box{
	position: relative;
	display: inline-block;
	width: 90px;
    padding-left: 50px;

}
.checked-box{
	/*position: absolute; */
	display: inline-block;
	background-image: url("${ctxStatic}/images/login_check0.png");
	width:30px;
	height: 30px;
	/*top: 10px;*/
	/*left: 10px;*/
    position: absolute;
    display: inline-block;
    background-image: url(/vaccinate/static/images/login_check0.png);
    width: 20px;
    height: 20px;
    top: 7px;
    left: 23px;
    background-size: 100%;
}
.checked{
	background-image: url("${ctxStatic}/images/login_check1.png");
}
</style>
<script type="text/javascript">
var pageSize = 10;
var num;
	var init = {
		showData: function(){
			var url = window.location.href;
			var id = url.split("=")[1];
			num = id;
			$("#billNum").val(id);
		},
		bindData: function(){
			$.ajax({
				url: "${ctx}/charge/getBillingStatus",
				type: "post",
				dataType: "json",
				data: "",
				success: function(data){
					if(data.billingStatus == 1){
						$(".box1").addClass("checked");
					}else{
						$(".box2").addClass("checked");
					}
				}
			})
		}
	}

	var addEvent = function(){
		$("#btnDeleteTicketSave").click(function(){
			var billNum = $.trim($("#billNum").val());
			var quanxian = $(".checked").attr("data-id");
            if(!/^\d{1,10}$/.test(billNum)){      
            	parent.error("请输入10位以内0-9的数字！");    
		    	return true;
            }else if(quanxian == ""){
            	parent.error("请选择开票权限！");
            }
            var data = {
            	billNum: billNum,
            	billing: quanxian
            }
            $.ajax({
            	url: "${ctx}/charge/saveSetCharge",
            	data: data,
            	type: "post",
            	dataType: "json",
            	success: function(data){
            		if(data.result == true){
            			parent.success(data.message);
            			 //window.parent.location.reload();
      //       			parent.init.allShow(1,pageSize);
						// parent.init.detailReceipt(1,pageSize);
						parent.init.vaildBillNum();
						parent.changePageT();
            			var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
						
            		}else{
            			parent.error(data.message);
            		}
            	},
            	error: function(error){
            		parent.error(error);
            	}
            })
		});
		$(".checked-box").click(function(){
			$(this).addClass("checked").parent().siblings().find(".checked-box").removeClass("checked");
		})
		$("#btnDeleteTicketExit").click(function() {
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
	}

	$(document).ready(function() {
		init.showData();
		init.bindData();
		addEvent();
	});
</script>
</head>
<body>
	<div class="wrap">
		<form class="form-horizontal patient-info">
			
			<div class="content">
				<div class="form-group">
					<label class="col-xs-3 control-label" for="count">业务流水号</label>
					<div class="col-xs-9">
						<input class="form-control" type="text" id="billNum" name="billNum" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label" for="count">是否开票</label>
					<div class="col-xs-9">
						<label class="choose-box">
							<!-- <input class="quanxian" type="radio" id="billYes" name="billNum" value="1" checked="true" /> -->
							<span class="checked-box box1" data-id="1"></span>
							是
						</label>
						<label class="choose-box" >
							<!-- <input class="quanxian" type="radio" id="billNo" name="billNum" value="0"/> -->
							<span class="checked-box box2" data-id="0" ></span>
							否
						</label>
					</div>
			</div>
			<div class="form-group">
			<button id="btnDeleteTicketExit" class="btn btn-default mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 退出
				</button> 
				<button id="btnDeleteTicketSave" class="btn btn-info mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-floppy-remove" aria-hidden="true"></span> 确定
				</button> 
				
			</div>
		</form>
	</div>
</body>
</html>