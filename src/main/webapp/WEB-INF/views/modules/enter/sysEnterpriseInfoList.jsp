<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
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
		function addEnterpriseInfo(){
       	 	openWindowsBM(ctx +  "/enter/sysEnterpriseInfo/form", "企业产品信息添加", "", "");
        }
		
		 function changeA(id){
	        openWindowsBM(ctx +  "/enter/sysEnterpriseInfo/form?id=" + id, "企业产品信息修改", "", "");
	     }
		 
		
		
		/* 删除单个企业产品信息  */
		function deleteInfo(id){
			if('${sysEnterpriseInfo.delFlag}'!='1'){
				layer.confirm("确认要删除吗？", {
			        btn: ['确认','取消'], //按钮
			        shade: true, //不显示遮罩
			        icon : 3,
			        offset : ['300px' , '35%']
			    }, function(index){
				    	layer.close(index);
				    	loading("正在执行...");
				        $.ajax({
							type : "POST",
							url  : "${ctx}/enter/sysEnterpriseInfo/delete?id="+id,
							success : function(data) {
									 layer.msg(data,{time:1500},function(){
									 	location.reload();
									 });	  	   
							}
						});
				    }
			    );
			}else{
				layer.msg('不可重复删除');
			} 
		}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/enter/sysEnterpriseInfo/">信息列表</a></li>
		<%-- <shiro:hasPermission name="enter:sysEnterpriseInfo:edit"><li><a href="${ctx}/enter/sysEnterpriseInfo/form">信息添加</a></li></shiro:hasPermission> --%>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="sysEnterpriseInfo" action="${ctx}/enter/sysEnterpriseInfo/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">名称：</span>
									<form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/>
							</div>
						</div>
						<%-- <div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">全名称：</span>
									<form:input path="nameAll" htmlEscape="false" maxlength="100" class="form-control"/>
							</div>
						</div> --%>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">code：</span>
									<form:input path="code" htmlEscape="false" maxlength="10" class="form-control"/>
							</div>
						</div>
						<%-- <div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">排序字段：</span>
									<form:input path="indexNum" htmlEscape="false" maxlength="18" class="form-control"/>
							</div>
						</div> --%>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">是否可用：</span>
									<%-- <form:input path="doIf" htmlEscape="false" maxlength="18" class="form-control"/> --%>
								
								<form:select path="doIf" class="form-control" >
									<form:option value="">-请选择-</form:option>
									<form:option value="1">是</form:option>
									<form:option value="0">否</form:option>
								</form:select> 
							</div>
						</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
					<input  class="btn btn-primary" type="button" value="添加" onclick="addEnterpriseInfo()"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>code</th>
						<th>名称</th>
						<th>全名称</th>
						
						<th>排序字段</th>
						<th>是否可用</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sysEnterpriseInfo">
					<tr>
						<td>
							${sysEnterpriseInfo.code}
						</td>
						<td><a href="javascript:void(0)" onclick="changeA(this.rel)" title="点击查看详情并可修改" rel="${sysEnterpriseInfo.id}">
							${sysEnterpriseInfo.name}
						</a></td>
						<td>
							${sysEnterpriseInfo.nameAll}
						</td>
						
						<td>
							${sysEnterpriseInfo.indexNum}
						</td>
						<td>${fns:getDictLabel(sysEnterpriseInfo.doIf, 'yes_no', '')}
							<%-- ${sysEnterpriseInfo.doIf} --%>
						</td>
						<td width="160px">
							<a href="javascript:void(0)" class="btn btn-success" onclick="changeA(this.rel)" rel="${sysEnterpriseInfo.id}">修改</a>
							<a href="javascript:void(0)" class="btn btn-danger" onclick="deleteInfo(this.rel)" rel="${sysEnterpriseInfo.id}">删除</a>
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