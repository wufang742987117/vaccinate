<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存明细</title>
	<meta name="decorator" content="hbdefault"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function printStock() {
        	var vaccType = $("#vaccType").val();
			window.open("${ctx}/hepatitis/bsHepatitisBcheckin/printStock?vaccType=" + vaccType, '_blank');
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckin">个案统计</a></li>
		<li><a href="${ctx}/hepatitisnum/bsHepatitisBNum/list">接种明细</a></li>
		<li><a href="${ctx}/hepatitisnum/bsHepatitisBNum/bsHepatitisBStockList">统计记录</a></li>
		<li class="active"><a href="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckinStock">库存明细</a></li>
		<div class=" row pull-right">
			<a href="${ctx}/hepatitis/bsHepatitisBcheckin" class="btn btn-success mr15" ><span class="glyphicon glyphicon-arrow-left"></span>返回上一页</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsHepatitisBcheckinStock" action="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckinStock" method="post" class="form-inline mt20">
				 <div class = "col-xs-11" style="padding-left:0;">
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">疫苗类型：</span>
							<form:select path="vaccType" class="form-control" >
								<form:option value="0">全部</form:option>
								<form:options items="${vaccInfoList}" itemLabel="vaccName" itemValue="id" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<input id="btnSubmit" class="btn btn-bshepb btn-sm" type="submit" value="查询" />
						<a href="javascript:printStock()" class="btn btn-bshepb btn-sm" >打印</a>
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
					<c:forEach items="${i}" var="bsHepatitisBchekinStock">
						<tr>
							<c:if test="${bsHepatitisBchekinStock.leng != 0}">
								<td rowspan="${bsHepatitisBchekinStock.leng}">
									${bsHepatitisBchekinStock.vaccineName}
								</td>
							</c:if>
							<td>
								${bsHepatitisBchekinStock.batch}
							</td>
							<td>
								${bsHepatitisBchekinStock.manufacturer}
							</td>
							<td>
								${bsHepatitisBchekinStock.standard}
							</td>
							<td>
								${bsHepatitisBchekinStock.storeNum2}
							</td>
							<td>
								${bsHepatitisBchekinStock.storeNum - bsHepatitisBchekinStock.storeNum2}
							</td>
							<td>
								${bsHepatitisBchekinStock.storeNum}
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