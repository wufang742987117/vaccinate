<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){

				layer.confirm("确认要导出用户数据吗？", {
					btn: ['确认','取消'], //按钮
					shade: true, //不显示遮罩
					icon : 0
				}, function(index){
					$("#searchForm").attr("action","${ctx}/sys/user/export");
					$("#searchForm").submit();
					layer.close(index);
				}, function()
				{
					layer.close();
				});
				<%--top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){--%>
					<%--if(v=="ok"){--%>
						<%--$("#searchForm").attr("action","${ctx}/sys/user/export");--%>
						<%--$("#searchForm").submit();--%>
					<%--}--%>
				<%--},{buttonsFocus:1});--%>
				<%--top.$('.jbox-body .jbox-icon').css('top','55px');--%>
			});
			$("#btnImport").click(function(){
				layer.open({
					type: 1,
					title: '导入数据',
					content: $("#importBox").html()
					,btn: ['关闭']
					,btn3: function(index,layero)
					{
						layer.close(index);
					}
				});
//				$.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
//					bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
			});
		});
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div id="importBox" class="hide">
		<form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
			class="form-inline" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
			<div class="form-group">
				<div class="input-group">
					<input id="uploadFile" name="file" type="file" />
					<input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
				</div>
			</div>
			<div class="form-group">
				<div class="input-group">
					<a href="${ctx}/sys/user/import/template">下载模板</a>
					<span>导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！</span>
				</div>
			</div>
		</form>
	</div>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/user/list">用户列表</a></li>
		<shiro:hasPermission name="sys:user:edit"><li><a href="${ctx}/sys/user/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="form-inline ">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">归属公司：</span>
						<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
										title="公司" url="/sys/office/treeData?type=1" cssClass="form-control" allowClear="true"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">登录名：</span>
						<form:input path="loginName" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">归属部门：</span>
						<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}"
										title="部门" url="/sys/office/treeData?type=2" cssClass="form-control" allowClear="true" notAllowSelectParent="true"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">姓名：</span>
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
					<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
					<input id="btnImport" class="btn btn-primary" type="button" value="导入"/></li>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th>归属公司</th><th>归属部门</th><th class="sort-column login_name">登录名</th><th class="sort-column name">姓名</th><th>电话</th><th>手机</th><%--<th>角色</th> --%><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
				<tbody>
				<c:forEach items="${page.list}" var="user">
					<tr>
						<td>${user.company.name}</td>
						<td>${user.office.name}</td>
						<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
						<td>${user.name}</td>
						<td>${user.phone}</td>
						<td>${user.mobile}</td><%--
						<td>${user.roleNames}</td> --%>
						<shiro:hasPermission name="sys:user:edit"><td>
							<a href="${ctx}/sys/user/form?id=${user.id}">修改</a>
							<a href="${ctx}/sys/user/delete?id=${user.id}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>