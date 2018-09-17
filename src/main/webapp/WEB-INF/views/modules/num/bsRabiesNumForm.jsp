<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>信息管理</title>
<meta name="decorator" content="default" />
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
			parent.closeFormIdPrint("${id}","${notitems}","${items}","${msg}");
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
		var batchnum = '${not empty bsRabiesNum.batchnum ? bsRabiesNum.batchnum : '-1'}';
		var vaccineid = '${not empty bsRabiesNum.vaccineid ? bsRabiesNum.vaccineid : '-1'}';
		var url;
		if("${type}" == 1){
			url = "${ctx}/product/bsManageProduct/findViewListApi";
		}else{
			url = "${ctx}/product/bsManageProduct/findViewListApiNo";
		}
		$("#vaccineid").change(function batchno() {
			//通过疫苗名称询所有的批次
			var vid = $("#vaccineid").val();
			if(vid == null){
				$("#batchnum").html(""); 
				$("#batchnum").change(); 
				return;
			}
			var flags = true;
			$.ajax({
				type : "POST",
				async: false,
				url : url,
				data : {vaccineid : vid},
				dataType : "json",
				success : function(data) {
					var	html="";
					flags = false;
					$.each(data, function(idx, item) { //循环对象取值
						html += "<option value='" + item.batchno + "'>"+ item.batchno + "</option>";
						if(item.batchno == batchnum){
							flags = true;
						}
					});
					$("#batchnum").html(html);
					if( vaccineid == vid && batchnum != '-1'){
						$("#batchnum").val(batchnum);
					}
					$("#batchnum").change(); 
				}
			});	
			
			if(!flags){
				$.ajax({
					type : "POST",
					url : "${ctx}/product/bsManageProduct/findViewListApiMan",
					data : {vaccineid : vid,batchno : batchnum },
					dataType : "json",
					success : function(mdata) {
						if(mdata.batchno != "" || mdata.batchno != null){
							$("#batchnum").val(mdata.batchno);
							$("#batchnum").change(); 
						}
					}
				});	
			}
		});
		$("#batchnum").change(function manufacturer() {
			//通过疫苗名称和批次查询所有的厂家
			var vid = $("#vaccineid").val();
			var batchno = $("#batchnum").val();
			if(batchno == null){
				$("#manufacturer").html("");
				$("#dose").html("");
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
					$("#dose").html(html2);
					$("#pid").html(html3);
					$("#manufacturer").change();
					$("#dose").change();
					$("#pid").change();
				}
			});	
		});
		$("#vaccineid").change();
	})
	$(function(){
		var vaccineid ='${not empty bsRabiesNum.vaccineid ? bsRabiesNum.vaccineid : '-1'}';
		if(vaccineid=='2802'){
			layer.msg('此针为冻干疫苗，请注意选择', {icon: 7}); 
		}else if(vaccineid=='2801'){
			layer.msg('此针为水剂，请注意选择', {icon: 7}); 
		}
	});
</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="bsRabiesNum" action="${ctx}/num/bsRabiesNum/save?type=${type }" method="post" class="form-horizontal">
				<form:hidden path="id" />
				<form:hidden path="checkid" />
				<form:hidden path="paystatus" />
				<sys:message content="${message}" />
				<div class="form-group">
					<label class="col-sm-2 control-label">针次：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="vaccinum" class="form-control">
							<form:options items="${fns:getDictList('pin')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
					<label class="col-sm-2 control-label">异地完成:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="wstatus" items="${fns:getDictList('01')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks required'"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">程序接种时间:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<input name="vaccidate" value="<fmt:formatDate value="${bsRabiesNum.vaccidate}" pattern="yyyy-MM-dd "/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date required ">
					</div>
					<label class="col-sm-2 control-label">实际接种时间：</label>
					<div class="col-sm-4">
						<input name="realdate" value="<fmt:formatDate value="${bsRabiesNum.realdate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date ">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">疫苗名称：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="vaccineid" class="form-control">
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
					<label class="col-sm-2 control-label">生产厂家：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="manufacturer" class="form-control required">
						</form:select>
					</div>
					<label class="col-sm-2 control-label">疫苗规格：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:select path="dose" class="form-control required">
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
					<label class="col-sm-2 control-label">付款状态：</label>
					<div class="col-sm-4 mt5">
						<span style="color:red;font-weight:bold;">${fns:getDictLabel(bsRabiesNum.paystatus, 'paystatus', '')}</span>
						<%-- <form:radiobuttons path="paystatus" items="${fns:getDictList('paystatus')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks required'"/> --%>
					</div>
					<label class="col-sm-2 control-label">接种状态：<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-4">
						<form:radiobuttons path="status" items="${fns:getDictList('wstatus')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks required'"/>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-8 col-sm-offset-2 text-right">
						<input id="btnSubmit" class="btn btn-primary btn-sm" type="submit" value="保 存" />
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>