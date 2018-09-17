<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>部署流程 - 流程管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function(){
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
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/act/process/">流程管理</a></li>
		<li class="active"><a href="${ctx}/act/process/deploy/">部署流程</a></li>
		<li><a href="${ctx}/act/process/running/">运行中的流程</a></li>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div class="ibox-content">
			<form id="inputForm" action="${ctx}/act/process/deploy" method="post" enctype="multipart/form-data" class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label">流程分类：</label>
					<div class="col-sm-2">
						<select id="category" name="category" class="required form-control">
							<c:forEach items="${fns:getDictList('act_category')}" var="dict">
								<option value="${dict.value}">${dict.label}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">流程文件：</label>
					<div class="col-sm-2">
						<input type="file" id="file" name="file" class="form-control required"/>
						<span class="help-inline">支持文件格式：zip、bar、bpmn、bpmn20.xml</span>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="提 交"/>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>
