<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个案记录</title>
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
        
        $(document).ready(function() {
				$("#btnExport").click(function(){
					layer.confirm("确认要导出数据吗？", {
						btn: ['确认','取消'], //按钮
						shade: true, //不显示遮罩
						icon : 0
					}, function(index){
					var temp = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/rabiesvaccine/bsRabiesCheckin/exportBigCategory");
						$("#searchForm").submit();
						layer.close(index);
						$("#searchForm").attr("action",temp);
					}, function()
					{
						layer.close();
					});
				});

		});
        
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		 <li ><a
			href="${ctx}/child_baseinfo/documentStatistics">个案统计</a></li>
		<li><a
			href="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement">应种统计</a></li>
		<li><a
			href="${ctx}/child_vaccinaterecord/inoculationOfStatistics">接种统计</a></li>
<%-- 		<li><a href="${ctx}/product/bsManageProduct/repertorySum">库存统计</a></li> --%>
		<li><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsBatch">批次接种统计</a></li>
		<li><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsSmallCategory">小类接种统计</a></li>
		<li class="active"><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsBigCategory">大类接种统计</a></li>
		<li><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsList">出入库统计</a></li>
		<div class=" row pull-right">
			<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsRabiesStatistics" action="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsBigCategory" method="post" class="form-inline mt20">
					<div class = "col-xs-12">
						<div class="form-group" >
							<div class="input-group" >
								<span class="input-group-addon gray-bg text-right">日期(起)：</span>
								<input name="beginTime" value="<fmt:formatDate value="${bsRabiesStatistics.beginTime}" pattern="yyyy-MM-dd"/>"
									onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
									class="laydate-icon form-control layer-date" />
							</div>
						</div>
						<div class="form-group" style="padding-left: 70px">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">日期(止)：</span>
								<input name="endTime" 
									onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
									class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${bsRabiesStatistics.endTime}" pattern="yyyy-MM-dd"/>"   />
							</div>
						</div>
						<div class="form-group">
							<input id="btnSubmit" class="btn btn-primary mr15" type="submit" value="查询" />
							<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/>
						</div>
					</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗大类名称</th>
						<th>接种总数</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${bsStatisticsList}" var="bsRabiesStatistics">
					<tr>
						<td>
							${bsRabiesStatistics.gname}
						</td>
						<td>
							${bsRabiesStatistics.sums}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>