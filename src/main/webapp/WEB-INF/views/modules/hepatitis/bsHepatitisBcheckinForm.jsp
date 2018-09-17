<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成人信息管理管理</title>
	<meta name="decorator" content="hbdefault"/>
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
<c:if test="${bsHepatitisBcheckin.username == null}">
	<script type="text/javascript">
		$(function() {
			$("#sex1").attr("checked", true);
			$("#sex1").parent().addClass("checked");
			$("#payStatus2").attr("checked", true);
			$("#payStatus2").parent().addClass("checked");
			$("#antibodies1").attr("checked", true);
			$("#antibodies1").parent().addClass("checked");
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
			if (t.id == ${defaultSheng}) {
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
	});
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
		$("input[name=idcardNo]").change( function() {
			//获取输入身份证号码 
			var ic = $("#idcardNo").val();
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
		$("input[name=idcardNo]").change();
		
		$('input:radio[name="antibodies"]').on("click", function(){
			var val = $('input:radio[name="antibodies"]:checked').val();
			if(val == 0){
				$('#dosage').val("3");
			}else if(val == 1){
				$('#dosage').val("1");
			}
		}); 
		
		$('input:radio[name="antibodies"]').parent().next().on("click", function(){
			var val = $('input:radio[name="antibodies"]:checked').val();
			if(val == 0){
				$('#dosage').val("3");
			}else if(val == 1){
				$('#dosage').val("1");
			}
		}); 
		
		$('input:radio[name="antibodies"]').siblings().on("click", function(){
			var val = $('input:radio[name="antibodies"]:checked').val();
				if(val == 0){
				$('#dosage').val("3");
			}else if(val == 1){
				$('#dosage').val("1");
			}
		}); 
		
		var vaccType = '${not empty bsHepatitisBcheckin.vaccType ? bsHepatitisBcheckin.vaccType : '-1'}';
		var vaccineName = '${not empty bsHepatitisBcheckin.vaccineName ? bsHepatitisBcheckin.vaccineName : '-1'}';
		var batch = '${not empty bsHepatitisBcheckin.batch ? bsHepatitisBcheckin.batch : '-1'}';
		
		$("#vaccType").change(function vaccineNames() {
			//通过疫苗类型所有的批次
			var vtype = $("#vaccType").val();
			if(vtype == "2"){
				$('.vtype2').show();
			}else{
				$('.vtype2').hide();
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/hepatitis/bsHepatitisBcheckin/findVaccInterVer",
				data : {vaccType : vtype},
				dataType : "json",
				success : function(data) {
					//默认针次
					$('#dosage').val(data);
				}
			});	
			$.ajax({
				type : "POST",
				url : "${ctx}/hepatitis/bsHepatitisBcheckin/findAllVaccNameApi",
				data : {vaccType : vtype},
				dataType : "json",
				success : function(data) {
					var	html="";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.id + "'>"+ item.name + "</option>";
						if(item.name  == null || item.name == ""){
							layer.msg("此疫苗暂无库存");
						}
					});
					$("#vaccineName").html(html);
					if( vaccType == vtype && vaccType != '-1' && vaccineName != '-1'){
						$("#vaccineName").val(vaccineName);
					}
					$("#vaccineName").change(); 
				}
			});		
		});
		$("#vaccType").change();
		
		var url;
		//判断新增或者修改
		if("${type}" == 0){
			url = "${ctx}/product/bsManageProduct/findViewListApi";
		}else{
			url = "${ctx}/product/bsManageProduct/findViewListApiNo";
			//疫苗类型只读
			$("#vaccType").attr("disabled","disabled");
		}
		
		$("#vaccineName").change(function batchnos() {
			//通过疫苗名称询所有的批次
			var vid = $("#vaccineName").val();
			if(vid == null){
				$("#batch").html(""); 
				$("#batch").change(); 
				return;
			}
			$.ajax({
				type : "POST",
				url : url,
				data : {vaccineid : vid},
				dataType : "json",
				success : function(data) {
					var	html = "";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.batchno + "'>"+ item.batchno + "</option>";
					});
					$("#batch").html(html);
					if( vaccineName == vid && batch != '-1'){
						$("#batch").val(batch);
					}
					$("#batch").change(); 
				}
			});		
		});
		
		$("#batch").change(function manufacturer() {
			//通过疫苗名称和批次查询所有的厂家
			var vid = $("#vaccineName").val();
			var batchno = $("#batch").val();
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
				}
			});	
		});
		
		//根据年龄查询规格和厂家信息	
		$("#age").change( function() {
			//疫苗类型
			var vtype = $("#vaccType").val();
			//通过疫苗名称询所有的批次
			var vid = $("#vaccineName").val();
			//获取当前年龄
			var age = $("#age").val();
			if(age >= 16 || age == "" || age == undefined){
				$('.age').hide();
			}else{
				$('.age').show();
			}
			if(age != 0 && age != "" && age != undefined){
				$.ajax({
					type : "POST",
					url : "${ctx}/hepatitis/bsHepatitisBcheckin/queryAge",
					data : {age : age ,vacctype : vtype},
					dataType : "json",
					success : function(data) {
						if(data.flag == "true"){
							if(vid != data.vaccineId){
								$('#vaccineName').val(data.vaccineId);
								$("#vaccineName").change(); 
							}
							$('#pid').val(data.pid);
							$('#standard').val(data.specification);
							$('#manufacturer').val(data.manufacturer);
							$('#batch').val(data.batchno);
							$("#batch").change();
						}
					}
				});	
			}
		});
		$("#age").change();
		
	});
</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsHepatitisBcheckin" action="${ctx}/hepatitis/bsHepatitisBcheckin/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="openId" />
				<form:hidden path="tempId" />
				<form:hidden path="hepaBcode" />
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓&nbsp;&nbsp;&nbsp;&nbsp;名：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:input path="username" htmlEscape="false" maxlength="50" class="form-control required realName" />
					</div>
					<label class="col-sm-2 control-label">身份证号：</label>
					<div class="col-sm-4">
						<form:input path="idcardNo" htmlEscape="false" maxlength="50" class="form-control card" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">性&nbsp;&nbsp;&nbsp;&nbsp;别：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline required i-checks'" class="" />
					</div>
					<label class="col-sm-2 control-label">联系电话：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:input path="linkPhone" htmlEscape="false" maxlength="50" class="form-control phone required  "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出生日期：</label>
                    <div class="col-sm-4">
						<input name="birthday" id="birthday" value="<fmt:formatDate value="${bsHepatitisBcheckin.birthday}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD',choose: checkDate})"  class="laydate-icon form-control layer-date required"  placeholder="yyyy-MM-dd" >
					</div>
					<label class="col-sm-2 control-label">年&nbsp;&nbsp;&nbsp;&nbsp;龄：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:input path="age" htmlEscape="false" maxlength="10" readonly="true" class="form-control digits required "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">详细地址：</label>
                    <div class="col-sm-10">
                    	<select id="sheng" name="province" class="lv"></select>&nbsp;&nbsp;<span >省</span> &nbsp;
					    <select id="shi" name="city" class="lv"></select>&nbsp;&nbsp;<span >市</span> &nbsp; 
					    <select id="qu" name="county" class="lv"></select>&nbsp;&nbsp;<span >县/区</span>
						<form:input path="address" htmlEscape="false" maxlength="100" class="form-control mt10 "  placeholder="请填写详细地址"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗类型：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="vaccType" class="form-control required">
							<form:options items="${vaccInfoList}" itemLabel="vaccName" itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">接种时间：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<input name="newdate" value="<fmt:formatDate value="${bsHepatitisBcheckin.newdate}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date required ">
					</div>
				</div>
				<div class="form-group">
					<div class="age">
						<label class="col-sm-2 control-label">监护人姓名：</label>
						<div class="col-sm-4">
							<form:input path="realname" htmlEscape="false" maxlength="50" class="form-control realName" />
						</div>
					</div>
					<div class="vtype2">
						<label class="col-sm-2 control-label">抗体水平：<span class="help-inline"><font color="red">*</font> </span></label>
						<div class="col-sm-4">
							<form:radiobuttons path="antibodies" items="${fns:getDictList('antibodies')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks required'" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="vaccineName" class="form-control required">
						</form:select>
					</div>
					<label class="col-sm-2 control-label">疫苗批号：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="batch" class="form-control required">
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗规格：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="standard" class="form-control required">
						</form:select>
					</div>
					<label class="col-sm-2 control-label">生产厂家：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="manufacturer" class="form-control required ">
						</form:select>
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">pid：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="pid" class="form-control required">
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种剂量：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="dosage" class="form-control required">
							<form:options items="${fns:getDictList('needle_times')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">体&nbsp;&nbsp;重(kg)：</label>
                    <div class="col-sm-4">
						<form:input path="weight" htmlEscape="false" maxlength="10" class="form-control number "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否付款：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="payStatus" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'"/>	
					</div>
					<label class="col-sm-2 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
                     <div class="col-sm-4">
						<form:input path="remarks" htmlEscape="false" maxlength="10" class="form-control " />	
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">既往病史：</label>
                    <div class="col-sm-10">
						<form:input path="history" htmlEscape="false" maxlength="200" class="form-control " />	
					</div>
				</div>
			   <div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-bshepb btn-sm" type="submit" value="保存"/>	
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>