<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>成人疫苗针次信息管理</title>
	<meta name="decorator" content="hbdefault"/>
<c:if test="${not empty arg }">
	<script type="text/javascript">
		$(function() {
			parent.closeFormArg("${arg}");
		});
	</script>
</c:if>
<c:if test="${not empty id }">
	<script type="text/javascript">
		$(function() {
			parent.closeFormId("${id}","0","${msg}");
		});
	</script>
</c:if>
<script type="text/javascript">
	window.onload = function() {  
		 document.getElementById('btnSubmit').focus();  
         (function($){
          funObj = {
              timeUserFun:'timeUserFun',
          }
          $[funObj.timeUserFun] = function(time){
              var time = time || 2;
              var userTime = time * 60;
              var objTime = {
                  init:0,
                  time:function(){
                      objTime.init += 1;
                      if(objTime.init == userTime){
						 document.getElementById('btnSubmit').focus();  
						 <!--设置id为name的元素得到焦点--> 
                      }
                  },
                  eventFun:function(){
                      clearInterval(testUser);
                      objTime.init = 0;
                      testUser = setInterval(objTime.time,1000);
                  }
              }

              var testUser = setInterval(objTime.time,1000);

              var body = document.querySelector('html');
              body.addEventListener("click",objTime.eventFun);
              body.addEventListener("keydown",objTime.eventFun);
              body.addEventListener("mousemove",objTime.eventFun);
              body.addEventListener("mousewheel",objTime.eventFun);
          }
      })(window)

//     直接调用 参数代表分钟数,可以有一位小数;
       timeUserFun(0.1);
    }
    
	document.onkeydown = function (e) { 
		var theEvent = window.event || e; 
		var code = theEvent.keyCode || theEvent.which; 
		if (code == 13) { 
			$("#inputForm").submit();
		} 
	}

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
				if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
	});
	
	$(function(){
		var vaccType = '${not empty bsHepatitisBNum.vaccType ? bsHepatitisBNum.vaccType : '-1'}';
		var batch = '${not empty bsHepatitisBNum.batch ? bsHepatitisBNum.batch : '-1'}';
		var vaccineId = '${not empty bsHepatitisBNum.vaccineId ? bsHepatitisBNum.vaccineId : '-1'}';
		
		var url;
		if("${type}" == 1){
			url = "${ctx}/product/bsManageProduct/findViewListApi";
		}else{
			$("#vaccType").attr("disabled","disabled");
			url = "${ctx}/product/bsManageProduct/findViewListApiNo";
		}
		
		$("#vaccType").change(function vaccineNames() {
			//通过疫苗类型所有的批次
			var vtype = $("#vaccType").val();
			$.ajax({
				type : "POST",
				url : "${ctx}/hepatitis/bsHepatitisBcheckin/findAllVaccNameApi",
				data : {vaccType : vtype},
				dataType : "json",
				success : function(data) {
					var	html="";
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.id + "'>"+ item.name + "</option>";
					});
					$("#vaccineId").html(html);
					if( vaccType == vtype && vaccType != '-1' && vaccineId != '-1'){
						$("#vaccineId").val(vaccineId);
					}
					$("#vaccineId").change(); 
				}
			});		
		});
		$("#vaccType").change();
		
		$("#vaccineId").change(function batchnos() {
			//通过疫苗名称询所有的批次
			var vid = $("#vaccineId").val();
			if(vid == null){
				$("#batch").html(""); 
				$("#batch").change(); 
				return;
			}
			var flags = true;
			$.ajax({
				type : "POST",
				url : url,
				data : {vaccineid : vid},
				dataType : "json",
				success : function(data) {
					var	html="";
					flags = false;
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.batchno + "'>"+ item.batchno + "</option>";
						if(item.batchno == batch){
							flags = true;
						}
					});
					$("#batch").html(html);
					if( vaccineId == vid && batch != '-1'){
						$("#batch").val(batch);
					}
					$("#batch").change(); 
				}
			});	
			
			if(!flags){
				$.ajax({
					type : "POST",
					url : "${ctx}/product/bsManageProduct/findViewListApiMan",
					data : {vaccineid : vid,batchno : batch },
					dataType : "json",
					success : function(mdata) {
						if(mdata.batchno != "" || mdata.batchno != null){
							$("#batch").val(mdata.batchno);
							$("#batch").change(); 
						}
					}
				});	
			}	
		});
		
		$("#batch").change(function manufacturer() {
			//通过疫苗名称和批次查询所有的厂家
			var vid = $("#vaccineId").val();
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
						
						if(item.storenum <= 0){
							layer.msg("暂无库存");
						}
					});
					$("#manufacturer").html(html);
					$("#standard").html(html2);
					$("#pid").html(html3);
				}
			});	
		});
	})
</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsHepatitisBNum" action="${ctx}/hepatitisnum/bsHepatitisBNum/save?type=${type }" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<form:hidden path="checkId"/>
				<form:hidden path="payStatus"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">针次:<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="vaccineNum" class="form-control ">
							<form:options items="${fns:getDictList('pin')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<label class="col-sm-2 control-label">异地完成:<span class="help-inline"> <font color="red">*</font> </span></label>	
                    <div class="col-sm-4">
						<form:radiobuttons path="wStatus" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks required'"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">程序接种时间：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<input  name="vaccineDate" value="<fmt:formatDate value="${bsHepatitisBNum.vaccineDate}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"  class="laydate-icon form-control layer-date required"/>
					</div>
					<label class="col-sm-2 control-label">实际接种时间：</label>
                    <div class="col-sm-4">
						<input name="realDate" value="<fmt:formatDate value="${bsHepatitisBNum.realDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date "/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗类型：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="vaccType" class="form-control required">
							<form:options items="${vaccInfoList}" itemLabel="vaccName" itemValue="id" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">疫苗名称：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="vaccineId" class="form-control">
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗批号：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
						<form:select path="batch" class="form-control required">
						</form:select>
					</div>
					<label class="col-sm-2 control-label">生产厂家：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="manufacturer" class="form-control required">
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗规格：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="standard" class="form-control required">
						</form:select>
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">pid：</label>
					<div class="col-sm-4">
						<form:select path="pid" class="form-control">
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">付款状态：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4 mt5">
                    	<span style="color:red;font-weight:bold;">${fns:getDictLabel(bsHepatitisBNum.payStatus, 'paystatus', '')}</span>
                    	<%-- <form:radiobuttons path="payStatus" items="${fns:getDictList('paystatus')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks required'"/> --%>
					</div>
					<label class="col-sm-2 control-label">完成状态：<span class="help-inline"><font color="red">*</font> </span></label>
                    <div class="col-sm-4">
                    	<form:radiobuttons path="status" items="${fns:getDictList('wstatus')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks required'"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-8 col-sm-offset-2 text-right">
						<input id="btnSubmit" class="btn btn-bshepb btn-sm" type="submit" value="保 存" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>