<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>划价收费系统</title>
<meta name="decorator" content="default" />
<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<style type="text/css">
.wrap {
	/*background: #fafafa;*/
	min-width: 430px;
	padding: 10px 20px;
	font-family: Microsoft Yahei;
}
label{
	font-weight: 100;
}
.patient-info label {
	text-align: left !important;
	line-height: 34px;
}
.fl{
	float: left;
}
.wid50{
	width: 50%;
}
.clear{
	clear: both;
}
.top{
	background-color: #fff;
	border-radius: 5px;
	font-weight: bold;
}
.title{
	background-color: #dcdddd;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
	padding: 10px;
	position: relative;
}
.content{
	padding: 20px 0px 15px 25px;
	font-weight: 100;
}
.choose-vac,.gathering{
	background-color: #fff;
	border-radius: 5px;
	margin-top: 20px;
}
.mar10{
	margin-top: 7px;
	margin-left: 10px;
}
.wid70{
	width: 70%;
}
.form-control, .single-line{
	padding: 6px 5px;
}
.wid48{
	width: 48%
}
.wid60{
	width: 60%;
}
.ml7{
	margin-left:10px;
}
.mt20{
	margin-top: 20px;
}
.form-horizontal .form-group {
    margin-right: 0px; 
    margin-left: 0px; 
}
.vacName,.batchno,.specification{
	height: 30px;
	border-radius: 3px;
}
.vas-box{
	position: relative;
}
.vas1{
	margin-top: 10px;
	position: relative;
}
.add{
	position: absolute;
	top: 10px;
	right: 30px;
	background-image: url("${ctxStatic}/images/add.png");
	height: 21px;
	width: 20px;
	background-size: 100%;
	cursor: pointer;
}
.del{
	position: absolute;
	top: 10px;
	right: 30px;
	background-image: url("${ctxStatic}/images/delete.png"); 
	height: 21px;
	width: 20px;
	background-size: 100%;
	cursor: pointer;
}
.none{
	display: none;
}
.vacName{
	width: 137px;
}
.vacCount{
	width: 50px;
	text-align: center;
}
.jiage{
	width: 120px;
}
.pihao{
	width: 255px;
}
.ml15{
	margin-left: 15px;
}
.ml5{
	margin-left: 5px;
}
.price,.vacCount{
	height: 30px;
	/*width: 70px;*/
}
.specification{
	width: 70px;
}
.guige{
	width: 110px;
}
.yimiao{
	width: 178px;
}
.price{
	width: 75px;
}
.wid55{
	width:55%;
}
</style>
<script type="text/javascript">
	var init = {
		showCharge: function(){
			var url = window.location.href;
			var billNum = url.split("=")[1];
			var data = {
				billNum: billNum
			};
			//alert(billNum);
			$.ajax({
				url: "${ctx}/charge/findChargePrintAgain",
				data: data,
				type: "post",
				dataType: "json",
				success: function(data){
					init.scanInfo(data);
				}
			});
		},
		scanInfo: function(data){
			$("#patientName").val(data.patientName);
			$("#patientName").attr("readonly",true);
			$("#serialNum").val(data.billNum);
			var html = '';
			for(i=0;i<data.vacc.length;i++){
				html = html + `<div class="vas-box">
				<div class="all-price fl yimiao">
					<label class="fl">疫苗:</label>
					<div class="wid70 fl ml7">
						<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
						<select class="vacName" disabled="disabled">
							<option >`+data.vacc[i].vaccName+`</option>
						</select>
					</div>
					<div class="clear"></div>
				</div>
				<div class="all-price fl pihao ml15">
					<label class="fl">厂家_批号室:</label>
					<div class="wid60 fl ml7">
						<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
						<select class="batchno" disabled="disabled" style="width: 100%;">
							<option >`+data.vacc[i].manufacturer+"_"+data.vacc[i].batchnum+`</option>
						</select>
					</div>
					<div class="clear"></div>
				</div>
				<div class="all-price fl guige ml5">
					<label class="fl">规格:</label>
					<div class="wid48 fl ">
						<!-- <input class="form-control" name="costId" type="text" id="costId" value="0.25ml" readonly/> -->
						<select class="specification" disabled="disabled">
							<option >`+data.vacc[i].dose+`</option>
						</select>
					</div>
					<div class="clear"></div>
				</div>
				<div class="all-price jiage fl ml15">
					<label class="fl">单价:</label>
					<div class="wid50 fl ml7">
						<input class="form-control price" name="costId" type="number" class="" value="`+parseFloat(data.vacc[i].vaccPrice).toFixed(2)+`" readonly/>
					</div>
					<div class="clear"></div>
				</div>
				<div class="all-price fl shuliang ml15">
					<label class="fl">数量:</label>
					<div class="wid50 fl ml7">
						<input class="form-control vacCount" name="costId" type="number" class=""  data-price="`+data.vacc[i].vaccPrice+`" value="`+data.vacc[i].vaccCount+`" readonly/>
					</div>
					<div class="clear"></div>
				</div>
				
				<div class="clear"></div>
				</div>`;
			}
			$(".choose-vac .title span").addClass("none");
			$(".addContent").html(html);
			$("#allPrice").val(data.sumPrice);
			$("#payment").val(data.pay);
			$("#changes").val(data.refund);
		}
	};
	var addEvent = function(){
		$("#btnSave").click(function(){
			layer.confirm('确认提交？', {title: "确认提交",
				  btn: ['确定', '取消',] //可以无限个按钮
				}, function(index, layero){
					layer.close(index);
					//init.submite();
					parent.truePrint($("#serialNum").val(),$("#patientName").val(),1,$("#serialNum").val());
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index); //再执行关闭 
				  //按钮【按钮一】的回调
				}, function(index){
				  //按钮【按钮二】的回调
				  layer.close(index);
				  return false;
			});
		});
		$("#btnExit").click(function() {
			parent.type();
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index); //再执行关闭   
		});
	};
	$(function(){
		init.showCharge();
		addEvent();
	})
</script>
</head>
<body>
	<div class="wrap">
		<form:form id="inputForm" modelAttribute="ChargeEntity" action="" method="post" class="form-horizontal patient-info">
			<div class="top">
				<div class="title">用户信息</div>
				<div class="content">
					<div class="form-group fl col-xs-6">
						<label class="fl control-label" for="patientName">姓名:</label>
						<div class="wid50 fl mar10">
							<input class="form-control" name="patientName" type="text" id="patientName" />
							<script language="JavaScript"> 
								document.getElementById('patientName').value="";
								document.getElementById('patientName').focus();  
								<!--设置id为name的元素得到焦点--> 
							</script>
						</div>
						<div class="clear"></div>
					</div>
					<div class="form-group fl col-xs-6">
						<label class="fl control-label" for="hospId">业务流水号:</label>
						<div class="wid50 fl mar10">
							<input class="form-control" name="hospId" type="number" id="serialNum" value="" readonly="readonly" />
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
				</div>
					
			</div>
			<div class="choose-vac">
				<div class="title">选择疫苗</div>
				<div class="content addContent">
					<div class="vas-box">
						<div class="all-price yimiao fl">
							<label class="fl">疫苗:</label>
							<div class="wid70 fl ml7">
								<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
								<select  class="vacName"></select>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price pihao fl ml15">
							<label class="fl">厂家_批号_科室:</label>
							<div class="wid55 fl ml7">
								<!-- <input class="form-control" name="costId" type="text" id="costId" value="（无）" readonly/> -->
								<select  class="batchno" style="width: 100%;"></select>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price guige fl ml5">
							<label class="fl">规格:</label>
							<div class="wid48 fl ml7">
								<!-- <input class="form-control" name="costId" type="text" id="costId" value="0.25ml" readonly/> -->
								<select id="specification" class="specification"></select>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price jiage fl ml15">
							<label class="fl">单价:</label>
							<div class="wid50 fl ml7">
								<input class="form-control price" name="costId" type="number" class="" value="" readonly/>
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price shuliang fl ml15">
							<label class="fl">数量:</label>
							<div class="wid50 fl ml7">
								<input class="form-control vacCount" name="costId" type="number" class="" value="1" />
							</div>
							<div class="clear"></div>
						</div>
						<div class="all-price fl">
							<span class="del"></span>
						</div>
						<div class="clear"></div>
					</div>
				</div>
			</div>
			<div class="gathering">
				<div class="title">收款结算</div>
				<div class="content">
					<div class="all-price fl col-xs-4">
						<label class="fl">总计金额:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="text" id="allPrice" value="（无）" readonly/>
						</div>
					</div>
					<div class="all-price fl col-xs-4">
						<label class="fl">交款:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="text" id="payment" value="" readonly />
						</div>
					</div>
					<div class="all-price fl col-xs-4">
						<label class="fl">找&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;零:</label>
						<div class="fl wid60 ml7">
							<input class="form-control" name="costId" type="number" id="changes" value="（无）" readonly />
						</div>
					</div>
					<div class="clear"></div>
				</div>
			</div>
				
			<div class="form-group mt20">
				
				<button id="btnExit" class="btn btn-default mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 退出
				</button> 
				<button id="btnSave" class="btn btn-success mr15 pull-right" type="button" >
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 打印
				</button> 
			</div>
		</form:form>
	</div>
</body>
</html>