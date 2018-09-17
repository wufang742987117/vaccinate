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
</style>
<script type="text/javascript">
	var billing;
	var named;
	var status;
	var init = {
		showData: function(){
			//var url = window.location.href;
			//var id = url.split("=")[1];
			var billings = GetRequest().billing;
			billing = billings;
			var names = unescape(GetRequest().name);
			named = names;
			var statuses = GetRequest().status;
			status = statuses;
			$("#billNum").val(billings);
		},
	};
	function GetRequest() {

	   var url = location.search; //获取url中含"?"符后的字串

	   var theRequest = new Object();

	   if (url.indexOf("?") != -1) {

	      var str = url.substr(1);

	      strs = str.split("&");

	      for(var i = 0; i < strs.length; i ++) {

	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);

	      }

	   }

	   return theRequest;

	}

	var addEvent = function(){
		$("#btnDeleteTicketSave").click(function(){
			var newBid = $.trim($("#billNum").val());
			if(!/^\d{1,10}$/.test(newBid)){      
        		parent.error("请输入10位以内0-9的数字！");    
		    	return true;
        	}
			layer.confirm('确认打印该发票？', {title:"确认打印",
					  btn: ['确定', '取消',] //可以无限个按钮
					}, function(index, layero){
						//按钮【按钮一】的回调
						layer.close(index);
						parent.truePrint(billing,named,status,newBid);
						var index = parent.layer.getFrameIndex(window.name);
						parent.layer.close(index);
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
		$("#btnDeleteTicketExit").click(function() {
			var index = parent.layer.getFrameIndex(window.name);
			parent.layer.close(index);
		});
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
				<!-- <div class="form-group">
					<label class="col-xs-3 control-label" for="count">开票权限</label>
					<div class="col-xs-9">
						<label>
							<input class="quanxian" type="radio" id="billYes" name="billNum" value="1" checked />
							是
						</label>
						<label>
							<input class="quanxian" type="radio" id="billNo" name="billNum" value="0"/>
							否
						</label>
					</div>
				</div> -->
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