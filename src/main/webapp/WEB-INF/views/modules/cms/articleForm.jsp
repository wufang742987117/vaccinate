<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
            if($("#link").val()){
                $('#linkBody').show();
                $('#url').attr("checked", true);
            }
			$("#title").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
                    if ($("#categoryId").val()==""){
                        $("#categoryName").focus();
						toastrMsg('请选择归属栏目','warning');
                    }else if (CKEDITOR.instances.content.getData()=="" && $("#link").val().trim()==""){
						toastrMsg('请填写正文','warning');
                    }else{
                        loading('正在提交，请稍等...');
                        form.submit();
                    }
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
		<li><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
		<li class="active"><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章<shiro:hasPermission name="cms:article:edit">${not empty article.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:article:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="article" action="${ctx}/cms/article/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">归属栏目:</label>
					<div class="col-sm-6">
						<sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
							title="栏目" url="/cms/category/treeData" module="article" selectScopeModule="true" notAllowSelectRoot="false" notAllowSelectParent="true" cssClass="form-control required"/>&nbsp;
					</div>
					<div class="col-sm-3">
						<label for="url">
							<input id="url" type="checkbox" class="i-checks" onclick="if(this.checked){$('#linkBody').show()}else{$('#linkBody').hide()}$('#link').val()">
							外部链接
						</label>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">标题:</label>
					<div class="col-sm-3">
						<form:input path="title" htmlEscape="false" maxlength="200" class="form-control measure-input required"/>
					</div>
					<label class="col-sm-2 control-label">颜色:</label>
					<div class="col-sm-3">
					<form:select path="color" class="form-control">
						<form:option value="" label="默认"/>
						<form:options items="${fns:getDictList('color')}" itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select>
						</div>
				</div>
				<div id="linkBody" class="form-group" style="display:none">
					<label class="col-sm-2 control-label">外部链接:</label>
					<div class="col-sm-10">
						<form:input path="link" htmlEscape="false" maxlength="200" class="form-control"/>
						<span class="help-inline">绝对或相对地址。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">关键字:</label>
					<div class="col-sm-10">
						<form:input path="keywords" htmlEscape="false" maxlength="200" class="form-control"/>
						<span class="help-inline">多个关键字，用空格分隔。</span>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">权重:</label>
					<div class="col-sm-2">
						<form:input path="weight" htmlEscape="false" maxlength="200" class="form-control required digits"/>
					</div>
					<div class="col-sm-1">
						<label for="weightTop"><input id="weightTop" type="checkbox" class="i-checks" onclick="$('#weight').val(this.checked?'999':'0')">置顶</label>
					</div>
					<label class="col-sm-2 control-label">过期时间：</label>
					<div class="col-sm-3">
						<input name="weightDate" readonly="" id="weightDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
							   value="<fmt:formatDate value="${article.weightDate}"/>" class="laydate-icon form-control layer-date required">
					</div>
					<span class="help-inline">数值越大排序越靠前，过期时间可为空，过期后取消置顶。</span>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">发布时间:</label>
					<div class="col-sm-10">
						<input name="createDate"  id="createDate" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
						value="<fmt:formatDate value="${article.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="laydate-icon form-control layer-date required">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">摘要:</label>
					<div class="col-sm-10">
						<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">缩略图:</label>
					<div class="col-sm-10">
						<input type="hidden" id="image" name="image" value="${article.imageSrc}" />
						<sys:ckfinder input="image" type="thumb" uploadPath="/cms/article" selectMultiple="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">正文:</label>
					<div class="col-sm-10">
						<form:textarea id="content" htmlEscape="true" path="articleData.content" rows="4" maxlength="200"/>
						<sys:ckeditor replace="content" uploadPath="/cms/article" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">来源:</label>
					<div class="col-sm-10">
						<form:input path="articleData.copyfrom" htmlEscape="false" maxlength="200" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">相关文章:</label>
					<div class="col-sm-10">
						<form:hidden id="articleDataRelation" path="articleData.relation" htmlEscape="false" maxlength="200" class="input-xlarge"/>
						<ol id="articleSelectList"></ol>
						<input id="relationButton" class="btn btn-primary" value="添加相关">
						<script type="text/javascript">
							var articleSelect = [];
							function articleSelectAddOrDel(id,title){
								var isExtents = false, index = 0;
								for (var i=0; i<articleSelect.length; i++){
									if (articleSelect[i][0]==id){
										isExtents = true;
										index = i;
									}
								}
								if(isExtents){
									articleSelect.splice(index,1);
								}else{
									articleSelect.push([id,title]);
								}
								articleSelectRefresh();
							}

							function articleSelectRefresh(){
								$("#articleDataRelation").val("");
								$("#articleSelectList").children().remove();
								for (var i=0; i<articleSelect.length; i++){
									$("#articleSelectList").append("<li>"+articleSelect[i][1]+"&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"articleSelectAddOrDel('"+articleSelect[i][0]+"','"+articleSelect[i][1]+"');\">×</a></li>");
									$("#articleDataRelation").val($("#articleDataRelation").val()+articleSelect[i][0]+",");
								}
							}

							$.getJSON("${ctx}/cms/article/findByIds",{ids:$("#articleDataRelation").val()},function(data){
								for (var i=0; i<data.length; i++){
									articleSelect.push([data[i][1],data[i][2]]);
								}
								articleSelectRefresh();
							});

							$("#relationButton").click(function(){
								layer.open({
									type: 2 //此处以iframe举例
									,title: '添加相关'
									,area: [$(window).width()-220+'px',$(window).height()-180+'px']
									,shade: 0.3
									,content: "${ctx}/cms/article/selectList?pageSize=8"
									,btn: ['确定','关闭']
									,maxmin: true //开启最大化最小化按钮
									,yes: function(index,layero)
									{
										layer.close(index);
									},
									btn3 : function(index)
									{
										layer.close(index);
									}
									,zIndex: layer.zIndex //重点1
									,success: function(layero,index){
										layer.setTop(layero); //重点2
									}
								});
							});

						</script>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">是否允许评论:</label>
					<div class="col-sm-10">
						<form:radiobuttons path="articleData.allowComment" element="label class='checkbox-inline i-checks'" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">推荐位:</label>
					<div class="col-sm-10">
						<form:checkboxes path="posidList" cssClass="i-checks" items="${fns:getDictList('cms_posid')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</div>
				</div>

				<shiro:hasPermission name="cms:article:audit">
					<div class="form-group">
						<label class="col-sm-2 control-label">发布状态:</label>
						<div class="col-sm-10">
							<form:radiobuttons path="delFlag" element="label class='checkbox-inline i-checks'" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
							<span class="help-inline"></span>
						</div>
					</div>
				</shiro:hasPermission>
				<shiro:hasPermission name="cms:category:edit">
					<div class="form-group">
						<label class="col-sm-2 control-label">自定义内容视图:</label>
						<div class="col-sm-10">
							  <form:select path="customContentView" class="form-control">
								  <form:option value="" label="默认视图"/>
								  <form:options items="${contentViewList}" htmlEscape="false"/>
							  </form:select>
							  <span class="help-inline">自定义内容视图名称必须以"${article_DEFAULT_TEMPLATE}"开始</span>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">自定义视图参数:</label>
						<div class="col-sm-10">
							  <form:input path="viewConfig" cssClass="form-control" htmlEscape="true"/>
							  <span class="help-inline">视图参数例如: {count:2, title_show:"yes"}</span>
						</div>
					</div>
				</shiro:hasPermission>
				<c:if test="${not empty article.id}">
					<div class="form-group">
						<label class="col-sm-2 control-label">查看评论:</label>
						<div class="col-sm-10">
							<input id="btnComment" class="btn form-control" type="button" value="查看评论" onclick="viewComment('${ctx}/cms/comment/?module=article&contentId=${article.id}&status=0')"/>
							<script type="text/javascript">
								function viewComment(href){
									layer.open({
										type: 2 //此处以iframe举例
										,title: '查看评论'
										,area: [$(window).width()-220+'px',$(window).height()-180+'px']
										,shade: 0.3
										,content: href
										,btn: ['关闭']
										,maxmin: true //开启最大化最小化按钮
										,btn3 : function(index)
										{
											layer.close(index);
										}
										,zIndex: layer.zIndex //重点1
										,success: function(layero){
											layer.setTop(layero); //重点2
										}
									});
//									top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-180,{
//										buttons:{"关闭":true},
//										loaded:function(h){
//											$(".jbox-content", top.document).css("overflow-y","hidden");
//											$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
//											$("body", h.find("iframe").contents()).css("margin","10px");
//										}
//									});
									return false;
								}
							</script>
						</div>
					</div>
				</c:if>
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="cms:article:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>