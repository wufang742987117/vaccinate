<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信息管理</title>
<meta name="decorator" content="default" />
<style type="text/css">
.vacitem{
	float: left;
	font-size: 14px;
	margin-left: 15px;
	margin-top: 5px;
}
.lv {
	background-color: #FFF;
	background-image: none;
	border: 1px solid #e5e6e7;
	border-radius: 1px;
	color: inherit;
	display: inline-block;
	padding: 0px 12px;
	width: 26%;
	font-size: 14px;
}
.mt10 {
	margin-top: 10px;
}
.form-control {
	height: 28px !important;
}
.form-group select {
	height: 28px !important;
}
.form-control,.single-line {
	padding: 0px 12px;
}
</style>
<c:if test="${not empty arg }">
	<script type="text/javascript">
		$(function() {
			parent.closeFormArg("${arg}");
		});
	</script>
</c:if>
<c:if test="${not empty msg }">
	<script type="text/javascript">
		$(function() {
			parent.closeFormMsg("${msg}");
		});
	</script>
</c:if>
<c:if test="${not empty id }">
	<script type="text/javascript">
		$(function() {
			parent.closeFormId("${id}");
		});
	</script>
</c:if>
<c:if test="${bsRabiesCheckin.username == null}">
	<script type="text/javascript">
		$(function() {
			$("#sex1").attr("checked", true);
			$("#sex1").parent().addClass("checked");
			$("#isinoculate2").attr("checked", true);
			$("#isinoculate2").parent().addClass("checked");
			$("#judgmentTimes2").attr("checked", true);
			$("#judgmentTimes2").parent().addClass("checked");
			$("#payment2").attr("checked", true);
			$("#payment2").parent().addClass("checked");
			$("#bitetype1").attr("checked", true);
			$("#bitetype1").parent().addClass("checked");
		});
	</script>
</c:if>
<script type="text/javascript">
	$(document).ready(function() {
		$("#inputForm").validate({
			submitHandler : function(form) {
				layer.load();
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")
						|| element.is(":radio")
						|| element.parent().is(
								".input-append")) {
					error.appendTo(element.parent()
							.parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});
	
	$(function(){
		/*加载家庭地址信息 */
		var sheng = ${sheng};
		var html = "";
		$.each(sheng, function(i, t) {
			html += "<option value='" + t.id + "'";
			if (t.id == ${defaultShen}) {
				html += " selected =true ";
			}
			html += "> " + t.name + " </option>";
		});
		$("#sheng").html(html);
		
		var shi = ${shi};
		html = "";
		$.each(shi, function(i, t) {
			html += "<option value='" + t.id + "'";
			if (t.id == ${defaultShi}) {
				html += " selected =true ";
			}
			html += "> " + t.name + " </option>";
		});
		$("#shi").html(html);
		
		var qu = ${qu};
		html = "";
		$.each(qu, function(i, t) {
			html += "<option value='" + t.id + "'";
			var v = ${defaultQu};
			if(v != -1){
				if (t.id == ${defaultQu}) {
					html += " selected =true ";
				}
				html += "> " + t.name + " </option>";
			}
		});
		$("#qu").html(html);
		
		//地址三级联动
		$("#sheng").change(function() {
			$.ajax({
				url :ctx + "/sys/sysArea/getbypid/" + $("#sheng").val(),
				type : "GET",
				success : function(data) {
					var html = "";
					$.each(data, function(i, t) {
						html += "<option value='" + t.id + "'> " + t.name + " </option>";
					});
					$("#shi").html(html);
					$.ajax({
						url : ctx + "/sys/sysArea/getbypid/" + $("#shi").val(),
						type : "GET",
						success : function(data) {
							var html = "";
							$.each(data, function(i, t) {
								html += "<option value='" + t.id + "'> " + t.name + " </option>";
							});
							html += "<option value=''></option>";
							$("#qu").html(html);
						}
					});
				}
			});
		});
		$("#shi").change(function() {
			$.ajax({
				url : ctx + "/sys/sysArea/getbypid/" + $("#shi").val(),
				type : "GET",
				success : function(data) {
					var html = "";
					$.each(data, function(i, t) {
						html += "<option value='" + t.id + "'> " + t.name + " </option>";
					});
					if(!html){
						html += "<option value=''></option>";
					}
					$("#qu").html(html);
				}
			});
		});
	})
		
	//免疫蛋白按钮选择事件
	$(function(){
		var val = $('input:radio[name="isinoculate"]:checked').val();
		if(val == 0){
			$('.w').hide();
		}else if(val == 1){
			$('.w').show();
		}
		
		$('input:radio[name="isinoculate"]').on("click", function(){
			var val1 = $('input:radio[name="isinoculate"]:checked').val();
			$(this).attr("checked", true);
			$(this).parent().addClass("checked");
			$(this).parents('.checkbox-inline').siblings().find('input:radio[name="isinoculate"]').attr("checked", false);
			$(this).parents('.checkbox-inline').siblings().find('input:radio[name="isinoculate"]').parent().removeClass("checked");
			if(val1 == 0){
				$('.w').hide();
			}else if(val1 == 1){
				$('.w').show();
			}
		});
		
		$('input:radio[name="isinoculate"]').parent().next().on("click", function(){
			var val1 = $('input:radio[name="isinoculate"]:checked').val();
			$(this).attr("checked", true);
			$(this).parent().addClass("checked");
			$(this).parents('.checkbox-inline').siblings().find('input:radio[name="isinoculate"]').attr("checked", false);
			$(this).parents('.checkbox-inline').siblings().find('input:radio[name="isinoculate"]').parent().removeClass("checked");
			if(val1 == 0){
				$('.w').hide();
			}else if(val1 == 1){
				$('.w').show();
			}
		});
		
		$('input:radio[name="isinoculate"]').siblings().on("click", function(){
			var val1 = $('input:radio[name="isinoculate"]:checked').val();
			$(this).attr("checked", true);
			$(this).parent().addClass("checked");
			$(this).parents('.checkbox-inline').siblings().find('input:radio[name="isinoculate"]').attr("checked", false);
			$(this).parents('.checkbox-inline').siblings().find('input:radio[name="isinoculate"]').parent().removeClass("checked");
			if(val1 == 0){
				$('.w').hide();
			}else if(val1 == 1){
				$('.w').show();
			}
		});
	})
		
	$(function(){
		$("input[name=card]").change( function() {
			//获取输入身份证号码 
			var ic = $("#card").val();
			if(ic.length == 18){
				//获取出生日期  
			    var birth = ic.substring(6, 10) + "-" + ic.substring(10, 12) + "-" + ic.substring(12, 14);
			    $("input[name=birthday]").val(birth); 
			  	//获取年龄  
			    var myDate = new Date();  
			    var month = myDate.getMonth() + 1;  
			    var day = myDate.getDate();  
			    var age = myDate.getFullYear() - ic.substring(6, 10) - 1;  
			    if (ic.substring(10, 12) < month || ic.substring(10, 12) == month && ic.substring(12, 14) <= day) {  
			        age++;  
			    }  
			    $("#age").val(age);  
			}
		});
		$("input[name=card]").change();
		
		/* 疫苗种类复选框赋初值 */
		var d = "${not empty bsRabiesCheckin.bitepart?bsRabiesCheckin.bitepart:'-1'}";
		if(d != '-1'){
			$.each(d.split(","),function(i,d){
				$("input[value=" + d + "]").attr("checked", true);
				$("input[value=" + d + "]").parent().addClass("checked");
			})
		}
		
		$("input[name=weight]").change( function(){
			//获取输入体重
			var wt = $("#weight").val();
			if(wt != 0){
				var wtt = Math.ceil(wt / 10); 
				$("#dosageNo").val(wtt);
			}
		})
		$("input[name=weight]").change();
	})
	
	$(function(){
		// $(document).on("click","#laydate_box",function(){
		// 	var date = new Date();
	 // 		var dateYear = date.getFullYear();
	 // 		var birthYear = $(this).val().split("-")[0];
	 // 		var year = dateYear - birthYear;
	 // 		$("#age").val(year);
		// });
		// $("#birthday").change(function(){
		// 	if($(this).val() != ''){
		// 		var date = new Date();
		// 		var dateYear = date.getFullYear();
		// 		var birthYear = $(this).val().split("-")[0];
		// 		var year = dateYear - birthYear;
		// 		$("#age").val(year);
		// 	}else{
		// 		$("#age").val('');
		// 	}
		// })
		// $("#birthday").change(function(){
		// 	console.log($("#birthday").val());
		// })
		var batchnum = '${not empty bsRabiesCheckin.batchnum ? bsRabiesCheckin.batchnum : '-1'}';
		var vaccinatename = '${not empty bsRabiesCheckin.vaccinatename ? bsRabiesCheckin.vaccinatename : '-1'}';
		var url;
		if("${type}" == 0){
			url = "${ctx}/product/bsManageProduct/findViewListApi";
		}else{
			url = "${ctx}/product/bsManageProduct/findViewListApiNo";
		}
		$("#vaccinatename").change(function batchno() {
			//通过疫苗名称询所有的批次
			var vid = $("#vaccinatename").val();
			if(vid == null){
				$("#batchnum").html(""); 
				$("#batchnum").change(); 
				return;
			}
			$.ajax({
				type : "POST",
				url : url,
				data : {vaccineid : vid},
				dataType : "json",
				success : function(data) {
					var	html="";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.batchno + "'>"+ item.batchno + "</option>";
					});
					$("#batchnum").html(html);
					if( vaccinatename == vid && batchnum != '-1'){
						$("#batchnum").val(batchnum);
					}
					$("#batchnum").change(); 
				}
			});		
		});
			
		var batchnumNo = '${not empty bsRabiesCheckin.batchnumNo ? bsRabiesCheckin.batchnumNo : '-1'}';
		var vaccinatenameNo = '${not empty bsRabiesCheckin.vaccinatenameNo ? bsRabiesCheckin.vaccinatenameNo : '-1'}';
		$("#vaccinatenameNo").change(function batchno() {
			//通过疫苗名称询所有的批次
			var vid = $("#vaccinatenameNo").val();
			if(vid == null){
				$("#batchnumNo").html(""); 
				$("#batchnumNo").change(); 
				return;
			}
			$.ajax({
				type : "POST",
				url : url,
				data : {vaccineid : vid},
				dataType : "json",
				success : function(data) {
					var	html="";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.batchno + "'>"+ item.batchno + "</option>";
					});
					$("#batchnumNo").html(html);
					if( vaccinatenameNo == vid && batchnumNo != '-1'){
						$("#batchnumNo").val(batchnumNo);
					}
					$("#batchnumNo").change(); 
				}
			});		
		});
	})
	function checkDate() {
			var date = new Date();
	 		var dateYear = date.getFullYear();
	 		var month = date.getMonth() + 1;  
			    var day = date.getDate();  
	 		var birthYear = $("#birthday").val().split("-")[0];
	 		var year = dateYear - birthYear - 1;
	 		if ($("#birthday").val().split("-")[1] < month || $("#birthday").val().split("-")[1] == month && $("#birthday").val().split("-")[2] <= day) {  
			        year++;  
			    }
	 		$("#age").val(year);
		}
	$(function(){
		// $("#card").keyup(function(e){
		// 	if($(this).val().toString().length==10){
		// 		var year = $(this).val().toString().substr($(this).val().toString().length-4);
		// 		var date = new Date();
		// 		var dateYear = date.getFullYear();
		// 		$("#age").val(dateYear-parseInt(year));
		// 	}
		// })
		var url;
		if("${type}" == 0){
			url = "${ctx}/product/bsManageProduct/findViewListApi";
		}else{
			url = "${ctx}/product/bsManageProduct/findViewListApiNo";
		}
		$("#batchnum").change(function manufacturer() {
			//通过疫苗名称和批次查询所有的厂家
			var vid = $("#vaccinatename").val();
			var batchno = $("#batchnum").val();
			if(batchno == null){
				$("#manufacturer").html("");
				$("#standard").html("");
				$("#pid").html("");
				return;
			}
			$.ajax({
				type : "POST",
				url : url,
				data : {vaccineid : vid,batchno : batchno},
				dataType : "json",
				success : function(data) {
					var html = "";
					var html2 = "";
					var html3 = "";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.manufacturer + "'>"+ item.manufacturer + "</option>";
						html2 += "<option value='" + item.specificationname + "'>"+ item.specificationname + "</option>";
						html3 += "<option value='" + item.id + "'>"+ item.id + "</option>";
					});
					$("#manufacturer").html(html);
					$("#standard").html(html2);
					$("#pid").html(html3);
					$("#manufacturer").change();
					$("#standard").change();
					$("#pid").change();
				}
			});	
		});
		$("#vaccinatename").change();
		$("input[name=card]").change();
		$("#batchnumNo").change(function manufacturer() {
			//通过疫苗名称和批次查询所有的厂家
			var vid = $("#vaccinatenameNo").val();
			var batchno = $("#batchnumNo").val();
			if(batchno == null){
				$("#manufacturerNo").html("");
				$("#standardNo").html("");
				$("#pidNo").html("");
				return;
			}
			$.ajax({
				type : "POST",
				url : url,
				data : {vaccineid : vid,batchno : batchno},
				dataType : "json",
				success : function(data) {
					var html = "";
					var html2 = "";
					var html3 = "";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.manufacturer + "'>"+ item.manufacturer + "</option>";
						html2 += "<option value='" + item.specificationname + "'>"+ item.specificationname + "</option>";
						html3 += "<option value='" + item.id + "'>"+ item.id + "</option>";
					});
					$("#manufacturerNo").html(html);
					$("#standardNo").html(html2);
					$("#pidNo").html(html3);
					$("#manufacturerNo").change();
					$("#standardNo").change();
					$("#pidNo").change();
				}
			});		
		});
		$("#vaccinatenameNo").change();
	})
	
	//判断处理地点
	$(function(){
		if($("#dealaddress").val() == "local"){
			$("#wound").val("10")
		}else{
			$("#wound").val("");
			$("#wound").attr({ readonly: 'true' });
		}
		$("#dealaddress").change(function(){
			if($("#dealaddress").val() == "local"){
				$("#wound").val("10")
			}else{
				$("#wound").val("");
				$("#wound").attr({ readonly: 'true' });
			}
		});
	});
</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsRabiesCheckin" action="${ctx}/rabiesvaccine/bsRabiesCheckin/save" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="openid" />
				<form:hidden path="tempid" />
				<form:hidden path="rabiescode" />
				<sys:message content="${message}" />
				<div class="form-group">
					<label class="col-sm-2 control-label">姓&nbsp;&nbsp;&nbsp;&nbsp;名：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:input path="username" htmlEscape="false" maxlength="50" class="form-control required realName" />
					</div>
					<label class="col-sm-2 control-label">身份证号：</label>
					<div class="col-sm-4">
						<form:input path="card" htmlEscape="false" maxlength="50" class="form-control card" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性&nbsp;&nbsp;&nbsp;&nbsp;别：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline required i-checks'" class="" />
					</div>
					<label class="col-sm-2 control-label">联系电话：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:input path="linkphone" htmlEscape="false" maxlength="50" class="form-control phone required " />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出生日期：</label>
					<div class="col-sm-4">
						<input name="birthday" id="birthday" type="text"  readonly  onclick="laydate({istime: true, format: 'YYYY-MM-DD',choose:checkDate})"  value="<fmt:formatDate value="${bsHepatitisBcheckin.birthday}" pattern="yyyy-MM-dd"/>" class="laydate-icon form-control layer-date required"  placeholder="yyyy-MM-dd" >
					</div>
					<label class="col-sm-2 control-label">年&nbsp;&nbsp;&nbsp;&nbsp;龄：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:input path="age" htmlEscape="false" readonly="true" maxlength="10" class="form-control  digits required" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
					<div class="col-sm-10">
						<select id="sheng" name="province" class="lv"></select>&nbsp;&nbsp;<span >省</span> &nbsp;
					    <select id="shi" name="city" class="lv"></select>&nbsp;&nbsp;<span >市</span> &nbsp; 
					    <select id="qu" name="county" class="lv"></select>&nbsp;&nbsp;<span >县/区</span>
						<form:input path="address" htmlEscape="false" maxlength="100"	class="form-control mt10 " placeholder="请填写详细地址" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种时间：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-2">
						<input name="newdate" value="<fmt:formatDate value="${bsRabiesCheckin.newdate}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date required ">
					</div>
					<label class="col-sm-2 control-label">咬伤时间：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-2">
						<input name="bitedate" value="<fmt:formatDate value="${bsRabiesCheckin.bitedate}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date required ">
					</div>
					<label class="col-sm-2 control-label">伤口处理费：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-2">
						<input id="wound" name="wound" value="" class="form-control">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">动物名称：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="animal" class="form-control">
							<form:options items="${fns:getDictList('animal')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">受伤方式：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="bitetype" items="${fns:getDictList('biteType')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">受伤部位：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-10">
					    <c:forEach items="${fns:getDictList('bite')}" var="vac">
							<div class="vacitem i-checks "><form:checkbox path="bitepart" label="${vac.label }" value="${vac.value }" /></div>
						</c:forEach>  
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">处理时间：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<input name="dealdate" value="<fmt:formatDate value="${bsRabiesCheckin.dealdate}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date required">
					</div>
					<label class="col-sm-2 control-label">处理地点：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="dealaddress" class="form-control">
							<form:options items="${fns:getDictList('disposal_sites')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">免疫前后：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="expose" items="${fns:getDictList('expose')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'" class="" />
					</div>
					<label class="col-sm-2 control-label">暴露级别：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="exposelevel" class="form-control">
							<form:options items="${fns:getDictList('rank')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>	
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="vaccinatename" class="form-control required">
							<form:options items="${productlist}" itemLabel="name" itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">疫苗批号：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="batchnum" class="form-control required">
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗规格：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="standard" class="form-control required">
						</form:select>
					</div>
					<label class="col-sm-2 control-label">接种剂量：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="dosage" class="form-control">
							<form:options items="${fns:getDictList('needle_times')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否48小时：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="judgmentTimes" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'"/>
					</div>
					<label class="col-sm-2 control-label">生产厂家：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="manufacturer" class="form-control required">
						</form:select>
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">pid：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="pid" class="form-control required">
						</form:select>
					</div>
					<label class="col-sm-2 control-label">是否已付款：</label>
					<div class="col-sm-4">
						<form:radiobuttons path="payment" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'"/>
					</div> 
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">受种者体重(kg)：</label>
					<div class="col-sm-4">
						<form:input path="weight" htmlEscape="false" maxlength="10" class="form-control number" />
					</div>
					<label class="col-sm-2 control-label">免疫球蛋白：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="isinoculate" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'"/>
					</div>
				</div>
				<div class="form-group w">
					<label class="col-sm-2 control-label">疫苗名称：</label>
					<div class="col-sm-4">
						<form:select path="vaccinatenameNo" class="form-control">
							<form:options items="${productlist1}" itemLabel="name" itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">疫苗批号：</label>
					<div class="col-sm-4">
						<form:select path="batchnumNo" class="form-control ">
						</form:select>
					</div>
				</div>
				<div class="form-group w">
					<label class="col-sm-2 control-label">生产厂家：</label>
					<div class="col-sm-4">
						<form:select path="manufacturerNo" class="form-control ">
						</form:select>
					</div>
					<label class="col-sm-2 control-label">疫苗规格：</label>
					<div class="col-sm-4">
						<form:select path="standardNo" class="form-control ">
						</form:select>
					</div>
				</div>
				<div class="form-group w">
					<label class="col-sm-2 control-label">接种剂量(支)：</label>
					<div class="col-sm-4">
						<form:input path="dosageNo" htmlEscape="false" maxlength="3" class="form-control number" />
					</div>
				</div>
				<div class="form-group w hide">
					<label class="col-sm-2 control-label">pid：</label>
					<div class="col-sm-4">
						<form:select path="pidNo" class="form-control ">
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-4">
						<form:input path="remarks" htmlEscape="false" maxlength="100" class="form-control " />
					</div>
					<label class="col-sm-2 control-label">既往病史：</label>
					<div class="col-sm-4">
						<form:input path="history" htmlEscape="false" maxlength="200" class="form-control " />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="rabiesvaccine:bsRabiesCheckin:edit">
							<input id="btnSubmit" class="btn btn-primary btn-sm" type="submit" value="保 存" />&nbsp;
						</shiro:hasPermission>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>