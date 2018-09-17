<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个案记录</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        $(document).ready(function() {
			$("#btnExport").click(function(){
				layer.confirm("确认要导出数据吗？", {
					btn: ['确认','取消'], //按钮
					shade: true, //不显示遮罩
					icon : 0
				}, function(index){
					var temp = $("#searchForm").attr("action");
					$("#searchForm").attr("action","${ctx}/rabiesvaccine/bsRabiesCheckin/export");
					$("#searchForm").submit();
					layer.close(index);
					$("#searchForm").attr("action",temp);
				}, function(){
					layer.close();
				});
			});
		});
		
		//删除档案
		function formDelete(a) {
			$.ajax({
				type : "POST",
				url : "${ctx}/rabiesvaccine/bsRabiesCheckin/delete?id=" + a,
				success : function(data) {
		            setTimeout(funB, 100);
				}
			});
		}
		
		//刷新当前页面
		function funB() {
			window.location.reload();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a
			href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckin">个案统计</a></li>
		<li><a
			href="${ctx}/num/bsRabiesNum/list">接种明细</a></li>
		<li><a
			href="${ctx}/num/bsRabiesNum/bsRabiesStockList">统计记录</a></li>
		<li><a
			href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStock">库存明细</a></li>
		<div class="row pull-right">
			<a href="${ctx}/rabiesvaccine/bsRabiesCheckin" class="btn btn-success mr15" ><span class="glyphicon glyphicon-arrow-left"></span>返回上一页</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsRabiesCheckin" action="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckin" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class = "col-xs-11" style="padding-left:0;">
					<div class="form-group" >
						<div class="input-group" >
							<span class="input-group-addon gray-bg text-right">建档日期(起)：</span>
							<input name="beginTime" value="<fmt:formatDate value="${bsRabiesCheckin.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								class="laydate-icon form-control layer-date" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">建档日期(止)：</span>
							<input name="endTime" 
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${bsRabiesCheckin.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"   />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">姓      名：</span>
								<form:input path="username" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<input id="btnSubmit" class="btn btn-primary btn-sm" type="submit" value="查询" />
						<input id="btnExport" class="btn btn-primary btn-sm ml15" type="button" value="导出报表"/>
					</div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>序     号</th>
						<th>编     号</th>
						<th>姓     名</th>
						<th>身份证号</th>
						<th>性     别</th>
						<th>出生日期</th>
						<th>年    龄</th>
						<th>联系电话</th>
						<th>咬伤时间</th>
						<th>受伤方式</th>
						<th>受伤部位</th>
						<th>动物名称</th>
						<th>建档日期</th>
						<th>操     作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsRabiesCheckin" varStatus="status">
					<tr ondblclick="window.location.href='${ctx}/rabiesvaccine/bsRabiesCheckin/form?id=${bsRabiesCheckin.id}'">
						<td>
							${status.index + 1}
						</td>
						<td>
							${bsRabiesCheckin.rabiescode}
						</td>
						<td>
							${bsRabiesCheckin.username}
						</td>
						<td>
							${bsRabiesCheckin.card}
						</td>
						<td>
							${fns:getDictLabel(bsRabiesCheckin.sex, 'sex', '')}
						</td>
						<td>
							<fmt:formatDate value="${bsRabiesCheckin.birthday}" pattern="yyyy-MM-dd" />
						</td>
						<td>
							${bsRabiesCheckin.age}
						</td>
						<td>
							${bsRabiesCheckin.linkphone}
						</td>
						<td>
							<fmt:formatDate value="${bsRabiesCheckin.bitedate}" pattern="yyyy-MM-dd" />
						</td>
						<td>
							${fns:getDictLabel(bsRabiesCheckin.bitetype, 'biteType', '')}
						</td>
						<td>
							${bsRabiesCheckin.bitepart}
						</td>
						<td>
							${fns:getDictLabel(bsRabiesCheckin.animal, 'animal', '')}
						</td>
						<td>
							<fmt:formatDate value="${bsRabiesCheckin.createDate}" pattern="yyyy-MM-dd" />
						</td>
						<td>
							<a href="javascript:formDelete('${bsRabiesCheckin.id}')" class="btn btn-danger btn-xs" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
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