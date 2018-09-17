<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>工作时间段管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		//弹出窗 函数 
        function openWindowsBM(url, title, w, h) {
			w = isNull(w) ? 800 : w;
			h = isNull(h) ? 530 : h;
			var ww = ($(document).width() < w ? $(document).width() - 30 : w)+ "px";
			var hh = ($(document).height() < h ? $(document).height() - 30 : h)+ "px";
			layer.open({
				type : 2,
				area : [ww,hh],
				title : isNull(title) ? "信息" : title,
				fix : false, //不固定
				maxmin : false,
				shade : 0.3,
				shift : 0, //0-6的动画形式，-1不开启
				content : url,
				shadeClose : true,
			});
		}
		function addWorkingHours(){
       	 	openWindowsBM(ctx +  "/sys/sysWorkingHours/form", "工作时间段添加", "", "");
        }
		function changeA(id){
	        openWindowsBM(ctx +  "/sys/sysWorkingHours/form?id=" + id, "工作时间段修改", "", "");
	     }
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/sysWorkingHours/">工作时间段列表</a></li>
		<shiro:hasPermission name="sys:sysWorkingHours:edit"><li><a href="${ctx}/sys/sysWorkingHours/form">工作时间段添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<%-- <form:form id="searchForm" modelAttribute="sysWorkingHours" action="${ctx}/sys/sysWorkingHours/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</div>
			</form:form> --%>
			<div class="form-group">
					<input  class="btn btn-primary" type="button" value="添加" onclick="addWorkingHours()"/>
				</div>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>接种单位编码</th>
						<th>星期</th>
						<th>时间段</th>
						<th>该时间段最大接种人数</th>
						<th>星期（从1到7）</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sysWorkingHours">
					<tr>
						<td>${sysWorkingHours.localCode }</td>
						<td>${sysWorkingHours.week }</td>
						<td>${sysWorkingHours.timeSlice }</td>
						<td>${sysWorkingHours.maximum }</td>
						<td>
							${sysWorkingHours.dateDay}
						</td>
						<td>
							${sysWorkingHours.remarks}
						</td>
						<td>
							<a href="javascript:void(0)" class="btn btn-success" onclick="changeA(this.rel)" rel="${sysWorkingHours.id}">修改</a>
							<a href="${ctx}/sys/sysWorkingHours/delete?id=${sysWorkingHours.id}" class="btn btn-danger" onclick="return confirmx('确认要删除该工作时间段吗？', this.href)">删除</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>