<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>


<html>
<head>
<title>推送</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
		$("#btnSubmit").click(function(){
		 	var context = $("#queneMsg").val();
		 	var video = $("#video").val()==""?"":window.location.protocol + '//' + window.location.host + $("#video").val();
		 	//video = window.location.protocol + '//' + window.location.host + video;
		 	
			if(context.replace(/(^\s*)|(\s*$)/g, "") || video){
				 $.ajax({
		             type: "POST",
		             url: "${ctx}/inoculate/quene/msgPost",
		             data: {
		             	context:context,
		             	video:video
		             },		            
		             success: function(data){
		             	 var res;
			             if(data){
			            	 res = "推送成功。";
			             }else{
			            	 res = "推送失败。";
			             }
			             layer.msg(res, {icon: 1});   
			           /*   parent.closeFormMsg(res); */
		             } 
		         });
			}else{
				layer.msg("请输入内容或选择视频。", {icon: 0});    
			}
			
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/sys/user/info">科室设置</a></li>
		<li><a href="${ctx}/sys/sysVaccDepartmentInfo/">门诊信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">密码设置</a></li>
		<li class="active"><a href="${ctx}/inoculate/quene/msgPostTo">信息推送</a></li>
		<li><a href="${ctx}/sys/user/list">用户管理</a></li>
		<li><a href="${ctx}/sys/user/about">门诊功能配置</a></li>
		<li ><a href="${ctx}/charge/findCharge">收银台</a></li>
		<div class=" row pull-right">
			<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
		</div>
	</ul>
	<div class="ibox" >
		<div class="mt20 form-horizontal" >
			<sys:message content="${message}" />
			<div class="form-group">
				<label class="col-sm-2 col-xs-3 text-right control-label">推送消息</label>
				<div class="col-sm-2 col-xs-5" >
					<textarea id="queneMsg" rows="20" cols="50" style="height: 80px;resize: none;margin:auto; padding: 10px; font-size: 15px;"></textarea>
				</div> 
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-xs-3 text-right control-label">推送视频</label>
				<div class="col-sm-2 col-xs-5" style="border: 1px solid #999;margin-left:15px;">
					<input id="video" name="video" maxlength="100" class="input-xlarge" type="hidden" value="">
					<sys:ckfinder input="video" type="files" uploadPath="/flash" selectMultiple="false"/>
				</div> 
			</div>
			<div class="form-group">
				<div class="col-sm-4 col-sm-offset-2 col-xs-offset-3" style="margin-top:20px;">
					<input id="btnSubmit" class="btn btn-lg btn-primary" type="submit"  value="推送" />
				</div>
			</div>
 		</div>
	</div>
</body>
</html>