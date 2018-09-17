<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" />
<html>
<head>
<title>档案新增</title>
<style type="text/css">
	.mr5 {margin-right: 5px; } ul,li {list-style: none; padding: 0; margin: 0; } #list {display: none; width: 218px; padding-left: 10px; position: absolute; background: #fff; z-index: 999; height: 150px; overflow-y: auto; cursor: pointer; border: 1px solid #ddd; } #list1 {display: none; width: 218px; padding-left: 10px; position: absolute; background: #fff; z-index: 999; height: 150px; overflow-y: auto; cursor: pointer; border: 1px solid #ddd; } #list2 {display: none; width: 218px; padding-left: 10px; position: absolute; background: #fff; z-index: 999; height: 150px; overflow-y: auto; cursor: pointer; border: 1px solid #ddd; } .box {width: 200px; height: 30px; position: relative; } .mt10 {margin-top: 10px; } .pt0 {padding-top: 0 !important; } .lv {background-color: #FFF; background-image: none; border: 1px solid #e5e6e7; border-radius: 1px; color: inherit; display: inline-block; padding: 0px 12px; width: 26%; font-size: 14px; } .form-control {height: 28px !important; } .form-control,.single-line {padding: 0px 12px; } .form-group {margin-bottom: 10px !important; } .form-group select {height: 28px !important; } .check-wrap{width: 80px; display: inline-block; height: 22px; } .check-box{width:22px; height:22px; background: url("${ctxStatic}/img/fx1.png") no-repeat; background-size:cover; position: relative; } .check-box i{width:22px; height:22px; background: url("${ctxStatic}/img/fx2.png") no-repeat; background-size:cover; position: absolute; top:0; left:0; opacity:0; } #removeBox{display: none;} #removeBox:checked + i{opacity:1; }
</style>
<meta name="decorator" content="default" />

<c:if test="${not empty code }">
	<script type="text/javascript">
		$(function() {
			parent.closeForm1('${code}');
		});
	</script>
</c:if>
<c:if test="${childBaseinfo.childcode == null}">
	<script type="text/javascript">
		$(function() {
			$("#gender1").attr("checked", true);
			$("#gender1").parent().addClass("checked");
			$("#paradoxicalreaction2").attr("checked", true);
			$("#paradoxicalreaction2").parent().addClass("checked");

		});
	</script>
</c:if>
<c:if test="${childBaseinfo.childcodePre != null}">
	<script type="text/javascript">
		$(function() {
			var code = "${childBaseinfo.childcode}";
			$("input[name='code']").val(code);
			
		});
	</script>
</c:if>
<script type="text/javascript" src="${ctxStatic}/js/child/infoForm.js"></script>
<script type="text/javascript">

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
		html="";
		$.each(shi, function(i, t) {
			html += "<option value='" + t.id + "'";
			if (t.id == ${defaultShi}) {
				html += " selected =true ";
			}
			html += "> " + t.name + " </option>";
		});
		$("#shi").html(html);
		
		var qu = ${qu};
		var v=${defaultQu};
		if(v==-1){
			html="<option value='' ></option>"	
		}else{
			html="";
		}
		$.each(qu, function(i, t) {
			if(v!=-1){
				html += "<option value='" + t.id + "'";
			if (t.id == ${defaultQu}) {
					html += " selected =true ";
				}
				html += "> " + t.name + " </option>";
			}else{
				
				html += "<option value='" + t.id + "'";
				html += "> " + t.name + " </option>";	
			}
			
		});
		$("#qu").html(html);
		
		/* 加载户籍地址*/
		html = "";
		$.each(sheng, function(i, t) {
			html += "<option value='" + t.id + "'";
			if (t.id == ${defaultShen1}) {
				html += " selected =true ";
			}
			html += "> " + t.name + " </option>";
		});
		$("#sheng1").html(html);
		
		var shi1 = ${shi1};
		html="";
		$.each(shi1, function(i, t) {
			html += "<option value='" + t.id + "'";
			if (t.id == ${defaultShi1}) {
				html += " selected =true ";
			}
			html += "> " + t.name + " </option>";
		});
		$("#shi1").html(html);
		
		var qu1 = ${qu1};
		var v=${defaultQu1};
		if(v==-1){
			html="<option value='' ></option>"	
		}else{
			html="";
		}
		$.each(qu1, function(i, t) {
			if(v!=-1){
				html += "<option value='" + t.id + "'";
			if (t.id == ${defaultQu1}) {
					html += " selected =true ";
				}
				html += "> " + t.name + " </option>";
			}else{
				
				html += "<option value='" + t.id + "'";
				html += "> " + t.name + " </option>";	
			}
			
		});
		$("#qu1").html(html);
		
		
		
		//地址三级联动
		$("#sheng").change(
				function() {
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
			$("#shi").change(
				function() {
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
			$("#sheng1").change(
				function() {
					$.ajax({
						url : ctx + "/sys/sysArea/getbypid/" + $("#sheng1").val(),
						type : "GET",
						success : function(data) {
							var html = "";
							$.each(data, function(i, t) {
								html += "<option value='" + t.id + "'> " + t.name + " </option>";
							});
							$("#shi1").html(html);
							$.ajax({
								url : ctx + "/sys/sysArea/getbypid/" + $("#shi1").val(),
								type : "GET",
								success : function(data) {
									var html = "";
									$.each(data, function(i, t) {
										html += "<option value='" + t.id + "'> " + t.name + " </option>";
									});
									html += "<option value=''></option>";
									$("#qu1").html(html);
								}
							});
						}
					});
			});
	
			$("#shi1").change(
				function() {
					$.ajax({
						url : ctx + "/sys/sysArea/getbypid/" + $("#shi1").val(),
						type : "GET",
						success : function(data) {
							var html = "";							
								$.each(data, function(i, t) {
									html += "<option value='" + t.id + "'> " + t.name + " </option>";
								});
							if(!html){
								html += "<option value=''></option>";
							}
							$("#qu1").html(html);
						}
					});
			});
});
		
				$(function(){
				$("#childcode").blur(function(){
				var code=$("#childcode").val();
						if(code.length>0&&code.length!=18){
						layer.msg('请输入正确的编码');
						}
				});
				})

	function ingoing(){
		parent.ingoing();
	}
</script>
</head>
<body>
	<!-- <div class="ibox"> -->
		<div class="ibox-content"  >
			<form:form id="inputForm" modelAttribute="childBaseinfo" action="${ctx}/child_baseinfo/childBaseinfo/save" method="post" class="form-horizontal">
			<form:input path="id" type="hidden" />
			<form:input path="userId" type="hidden" />
			<form:input path="tempid" type="hidden" />
				<sys:message content="${message}" />
				<c:choose>
					<c:when test="${ empty childBaseinfo.id }">
						<div class="form-group"  id="divid" name="name" style="height: 33px;">
							<label class="col-sm-2 control-label">儿童编码：</label>
							<div class="col-sm-4">
								<input id="childcode" name="code" maxlength="18"  readonly="readonly"
									class="form-control number " oninput="showmer()" 
									onpropertychange="showmer()" 
									 placeholder="新增档案时不需填写" />
							</div>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-group">
							<label class="col-sm-2 control-label">儿童编码：</label>
							<div class="col-sm-10">
								<input id="childcode" name="code" maxlength="18"
									class="form-control number" readonly="readonly" />
							</div>
						</div>
					</c:otherwise>
				</c:choose>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">儿童姓名：<span
						class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:input path="childname" htmlEscape="false" maxlength="20" placeholder="请输入儿童姓名"
							class="form-control required realName" />

					</div>
					<label class="col-sm-2 control-label">儿童性别：<span
						class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="gender" items="${fns:getDictList('sex')}"
							itemLabel="label" itemValue="value" htmlEscape="false"
							element="label class='checkbox-inline i-checks'" class="required" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">出生证号：</label>
					<div class="col-sm-4">
						<form:input path="birthcode" htmlEscape="false" maxlength="50" placeholder="请输入儿童出生证号"
							class="form-control " />
				</div>
				<label class="col-sm-2 control-label">儿童身份证：</label>
					<div class="col-sm-4 " id="divid">
						<form:input path="cardcode" htmlEscape="false" maxlength="18" placeholder="请输入儿童身份证号"
							class="form-control card " />
					</div>
				</div>
				
				<hr style="margin: 15px 0px;" />
				<div class="form-group">
				<label class="col-sm-2 control-label">母亲姓名：<span
						class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:input path="guardianname" htmlEscape="false" maxlength="20" placeholder="请输入母亲姓名"
							class="form-control required realName" />

					</div>
					<label class="col-sm-2 control-label">父亲姓名：</label>
					<div class="col-sm-4">
						<form:input path="father" htmlEscape="false" placeholder="请输入父亲姓名"
							class="form-control realName" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">母亲身份证：<span
						class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4 ">
						<form:input path="guardianidentificationnumber" htmlEscape="false" placeholder="请输入母亲身份证号"
							maxlength="20" class="form-control card  required" />
							</div>
					<label class="col-sm-2 control-label">父亲身份证：</label>
					<div class="col-sm-4">
						<form:input path="fathercard" htmlEscape="false" maxlength="20"	class="form-control card" placeholder="请输入父亲身份证号"/>
					</div>
					</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">母亲电话：<span
						class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4 ">
						<form:input path="guardianmobile" htmlEscape="false" placeholder="请输入母亲电话"
							maxlength="20" class="form-control mobile required" />
					</div>
					<label class="col-sm-2 control-label">父亲电话：</label>
					<div class="col-sm-4">
						<form:input path="fatherphone" htmlEscape="false" placeholder="请输入父亲电话"
							class="form-control mobile" />
					</div>
					</div>
					<hr style="margin: 15px 0px;" />
					
					<div class="form-group">
					<label class="col-sm-2 control-label">胎&#12288;&#12288;次：<span
						class="help-inline"><font color="red">*</font> </span></label>
                        <div class="col-sm-4">
						<form:input path="childorder" htmlEscape="false" maxlength="50" cssClass="form-control required number" placeholder="请输入胎次"/>
					</div>
					
					<label class="col-sm-2 control-label">出生医院：</label> 
					<div class="col-sm-4">
						<form:select path="birthhostipal" class="form-control">
							<form:options items="${birthlist}"
								itemLabel="name" itemValue="code" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				
				<div class="form-group">
						<label class="col-sm-2 control-label">出生日期：<span class="help-inline" style="color: red">*</span></label>
						<div class="col-sm-4">
								<input name="birthday" value="<fmt:formatDate value="${childBaseinfo.birthday}" pattern="yyyy-MM-dd"/>" placeholder="请选择出生日期"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD',max:laydate.now()})"
								class="laydate-icon required form-control layer-date ">
						</div>
						
						<label class="col-sm-2 control-label">民&#12288;&#12288;族：</label>
                        <div class="col-sm-4">
							<form:select path="nation" class="form-control">
							<form:options items="${nationlist}"
								itemLabel="name" itemValue="code" htmlEscape="false" />
						</form:select>
						</div> 
				</div>
			
				<div class="form-group">
					<label class="col-sm-2 control-label">区域划分：<span class="help-inline"><font color="red">*</font> </span></label>
                        <div class="col-sm-4">
						<form:select path="area" class="form-control required">
							<form:option value="" label="--请选择--"/>
							<form:options items="${list}" itemLabel="name" itemValue="code"/>
						</form:select>
					</div>
					<label class="col-sm-2 control-label">体重(g)：<span
						class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:input path="birthweight" htmlEscape="false" placeholder="请输入儿童出生体重"
							class="form-control required number" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">户口类别：</label>
                        <div class="col-sm-4">
                          <form:select path="properties" class="form-control">
							<form:options items="${fns:getDictList('properties')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">居住类别：</label>
                        <div class="col-sm-4">
                        <form:select path="reside" class="form-control" items="${fns:getDictList('reside')}" itemLabel="label" itemValue="value" >
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">接种单位：</label>
                    <div class="col-sm-4">
                        <form:select path="officeinfo" class="form-control">
							<form:option value="" label="--请选择--"/>
							<form:options items="${departmentlist}" itemLabel="name" itemValue="code" htmlEscape="false" />
							
						</form:select>
					</div>
					<label class="col-sm-2 control-label">学校：</label>
                    <div class="col-sm-4">
                        <form:select path="kindergartencode" class="form-control">
							<form:option value=""></form:option>
							<form:options items="${kindergartenlist}"
								itemLabel="name" itemValue="kindergartenCode" htmlEscape="false" />
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">家庭地址：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-10">
						<select id="sheng" name="province" class="lv"></select>&nbsp;&nbsp;<span >省</span> &nbsp;
					    <select id="shi" name="city" class="lv"></select>&nbsp;&nbsp;<span >市</span> &nbsp; 
					    <select id="qu" name="county" class="lv"></select>&nbsp;&nbsp;<span >县/区</span>
						<form:input path="address" htmlEscape="false" maxlength="100"	class="form-control required mt10" placeholder="请填写家庭详细地址" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">户籍地址：</label>
					<div class="col-sm-10">
						<select id="sheng1" name="pr" class="lv"></select>&nbsp;&nbsp;<span >省</span> &nbsp;
						<select id="shi1" name="ci" class="lv"></select>&nbsp;&nbsp;<span >市</span> &nbsp;
						<select id="qu1" name="co" class="lv"></select>&nbsp;&nbsp;<span >县/区</span> 
						<form:input path="add" htmlEscape="false" maxlength="100"	class="form-control mt10" placeholder="请填写户籍详细地址" />
					</div>
				</div>
				 <div class="form-group"  >	
				 	<label class="col-sm-2 control-label">异常反应：<font color="red">*</font></label>
					 <div class="col-sm-5">
						<form:input path="paradoxicalreaction"  htmlEscape="false" placeholder="请输入接种异常反应史、接种禁忌和疫苗针对传染病" maxlength="200" 
							class="form-control required" />
					
					</div>
				 	 <label class="col-sm-1 control-label">备注：</label>
					 <div class="col-sm-4">
						<form:input path="remarks"  htmlEscape="false" placeholder="请输入备注" maxlength="400"
							class="form-control " />
					</div> 
				</div> 
				 <div class="form-group"  <c:if test="${ empty  childBaseinfo.id  }">  style="display: none" </c:if>>	
				 	<label class="col-sm-2 control-label">在册类别：</label>
					 <div class="col-sm-4">
                       <form:select path="situation" class="form-control">
							<form:options items="${fns:getDictList('situation')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>

				<div class="col-sm-4" style="display: none">
					<form:input path="fileorigin" name="fileorigin" value="${childBaseinfo.fileorigin}" htmlEscape="false" maxlength="20" placeholder="档案来源"
								class="form-control required realName" />
				</div>
				
				<c:if test="${empty childBaseinfo.id  }">
				<div class="form-group" id="vaccineid">
					<label class="col-sm-2 control-label pt0">补录疫苗：</label>
					<div class="col-sm-10">
						<input type="checkbox" id="bcg" name="bcg" value="卡介苗 " checked="checked" class="i-checks" ><label for="bcg">卡介苗 </label>
						<input type="checkbox" id="hepbig" name="hepbig" value="乙肝球蛋白" class="i-checks " ><label for="hepbig">乙肝球蛋白 </label>
						<input type="radio" id="cho1" name="cho" value="乙肝疫苗(CHO) "  class="i-checks " ><label for="cho1">乙肝疫苗(CHO)</label>
						<input type="radio" id="cho2" name="cho" value="乙肝疫苗(酿酒酵母)" checked="checked" class="i-checks " ><label for="cho2">乙肝疫苗(酿酒酵母)</label>
						<input type="radio" id="cho3" name="cho" value="乙肝疫苗(汉逊酵母)" class="i-checks " ><label for="cho3">乙肝疫苗(汉逊酵母)</label>
						<input type="radio" id="cho4" name="cho" value="未接种乙肝" class="i-checks " ><label for="cho4">未接种乙肝</label>
						
					</div>
				</div>
				</c:if>
				<div class="form-group">
					<div class="col-sm-2 col-sm-offset-2">
						<shiro:hasPermission name="child:baseinfo:edit">
							<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp; 
						</shiro:hasPermission>
					</div>
				</div> 
			</form:form>
		</div >
	<!-- </div>  -->
	<script>
	$(function(){
		var oInput = $('#nation');
		var oUl = $('#list');
		var oLi = oUl.find('li');
		oInput.on('focus',function(){
			oUl.show();
			oLi.on('click',function(){
				oInput.val($(this).html());
				oUl.hide();	
			});	
			 	
			$(this).on('input',function(){
			
				var inputVal = $(this).val();
				$('#list').find('li').hide();
				 $('#list').find('li').each(function(){
				
				 	if ($(this).text().indexOf(inputVal)>=0) {
				 		$(this).show();
				 	}
				    
				  });
			});
		});
		$(document).on('click',function(e){
			if(e.target.id == 'nation'){
				oUl.show();
			}else{
				oUl.hide();
			}		
		})


	});
	$(function(){
		var oInput = $('#birthhostipal');
		var oUl = $('#list1');
		var oLi = oUl.find('li');
		oInput.on('focus',function(){
			oUl.show();
			oLi.on('click',function(){
				oInput.val($(this).html());
				oUl.hide();	
			});	
		
			$(this).on('input',function(){
				var inputVal = $(this).val();
				$('#list1').find('li').hide();
				 $('#list1').find('li').each(function(){
				
				 	if ($(this).text().indexOf(inputVal)>=0) {
				 		$(this).show();
				 	}
				    
				  });
			});
		});
		$(document).on('click',function(e){
			if(e.target.id == 'birthhostipal'){
				oUl.show();
			}else{
				oUl.hide();
			}		
		})
	});
	$(function(){
		var oInput = $('#area');
		var oUl = $('#list2');
		var oLi = oUl.find('li');
		oInput.on('focus',function(){
			oUl.show();
			oLi.on('click',function(){
				oInput.val($(this).html());
				oUl.hide();	
			});	
			
			$(this).on('input',function(){
				var inputVal = $(this).val();
				$('#list2').find('li').hide();
				 $('#list2').find('li').each(function(){
				 	if ($(this).text().indexOf(inputVal)>=0) {
				 		$(this).show();
				 	}
				    
				  });
			});
		});
		$(document).on('click',function(e){
			if(e.target.id == 'area'){
				oUl.show();
			}else{
				oUl.hide();
			}		
		})
	});


</script>
</body>
</html>
<!-- <script src="jquery.min.js"></script> -->
				
					
					
					
					
