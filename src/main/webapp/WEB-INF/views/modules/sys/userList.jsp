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
				}, function(){
					layer.close();
				});
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
			});
		});
		
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	    
		function openWindows(url, title, w, h) {
			w = isNull(w) ? 1000 : w;
			h = isNull(h) ? 830 : h;
			var ww = ($(document).width() < w ? $(document).width() - 30 : w)+ "px";
			var hh = ($(document).height() < h ? $(document).height() - 30 : h)+ "px";
			layer.open({
				type : 2,
				area : [ ww, hh ],
				title : isNull(title) ? "信息" : title,
				fix : false, //不固定
				maxmin : false,
				shade : 0.3,
				shift : 0, //0-6的动画形式，-1不开启
				content : url,
			});
		}
	 	
		function closeForm() {
			layer.closeAll();
			window.location.href = '${ctx}/sys/user/list';
		}
		
		function insertUser() {
			openWindows("${ctx}/sys/user/form", "新增用户", "", "");
		}

		function updateUser(id) {
			openWindows("${ctx}/sys/user/form?id=" + id, "修改用户", "", "");
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/sys/user/info">科室设置</a></li>
		<li><a href="${ctx}/sys/sysVaccDepartmentInfo/">门诊信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">密码设置</a></li>
		<li><a href="${ctx}/inoculate/quene/msgPostTo">信息推送</a></li>
		<li class="active"><a href="${ctx}/sys/user/list">用户管理</a></li>
		<li ><a href="${ctx}/sys/user/about">门诊功能配置</a></li>
		<li ><a href="${ctx}/charge/findCharge">收银台</a></li>
		<div class="row pull-right">
			<a href="javascript:insertUser()" class="btn btn-primary mr15" onclick="this.href">用户新增</a>
			<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>&emsp;&emsp;
		</div>
	</ul>
	<div class="ibox">
		<div class="mt20">
			<form:form id="searchForm" modelAttribute="user" action="${ctx}/sys/user/list" method="post" class="form-inline ">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>归属科室</th>
						<th class="sort-column login_name">登录名</th>
						<th class="sort-column name">姓名</th>
						<th>手机号码</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="user">
					<tr>
						<td>${user.office.name}</td>
						<td>${user.loginName}</td>
						<td>${user.name}</td>
						<td>${user.phone}
						<td>${user.remarks}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>