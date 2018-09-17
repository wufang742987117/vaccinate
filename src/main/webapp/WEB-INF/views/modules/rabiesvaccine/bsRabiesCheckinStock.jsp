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
        
        function printStock() {
			window.open("${ctx}/rabiesvaccine/bsRabiesCheckin/printStock", '_blank');
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckin">个案统计</a></li>
		<li><a href="${ctx}/num/bsRabiesNum/list">接种明细</a></li>
		<li><a href="${ctx}/num/bsRabiesNum/bsRabiesStockList">统计记录</a></li>
		<li class="active"><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStock">库存明细</a></li>
		<div class=" row pull-right">
			<a href="${ctx}/rabiesvaccine/bsRabiesCheckin" class="btn btn-success mr15" ><span class="glyphicon glyphicon-arrow-left"></span>返回上一页</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsRabiesCheckinStock" action="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStock" method="post" class="form-inline mt20">
				<div class = "col-xs-11" style="padding-left:0;">
					<div class="form-group">
						<a href="javascript:printStock()" class="btn btn-primary btn-sm" >打印</a>
					</div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>疫苗批次</th>
						<th>疫苗厂家</th>
						<th>疫苗规格</th>
						<th>已付款库存</th>
						<th>可用库存</th>
						<th>实际库存</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${bsList}" var="i">
					<c:forEach items="${i}" var="bsRabiesCheckinStock">
						<tr>
							<c:if test="${bsRabiesCheckinStock.leng != 0}">
								<td rowspan="${bsRabiesCheckinStock.leng}">
									${bsRabiesCheckinStock.vaccinatename}
								</td>
							</c:if>
							<td>
								${bsRabiesCheckinStock.batchnum}
							</td>
							<td>
								${bsRabiesCheckinStock.manufacturer}
							</td>
							<td>
								${bsRabiesCheckinStock.standard}
							</td>
							<td>
								${bsRabiesCheckinStock.storenum2}
							</td>
							<td>
								${bsRabiesCheckinStock.storenum - bsRabiesCheckinStock.storenum2}
							</td>
							<td>
								${bsRabiesCheckinStock.storenum}
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>