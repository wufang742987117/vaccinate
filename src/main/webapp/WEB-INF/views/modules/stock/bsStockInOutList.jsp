<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>进销存月报表管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#year").change(function(){
				loadTable();
			});
			$("#mouth").change(function(){
				loadTable();
			});
		});
		
		function loadTable(){
			if(!$("#year").val() || !$("#mouth").val()){
				return;
			}
			
			$.ajax({
				url:ctx + "/stock/bsStockInOut/findListApi",
				data:{year:$("#year").val(), mouth:$("#mouth").val()},
				async: false,
				success:function(data){
					var html = "";
					for(var i = 0; i < data.length; i ++){
						html+="<tr><td>" + data[i].vaccineName + "</td>"
							+ "<td>" + data[i].comName + "</td>"
							+ "<td>" + data[i].stockOld + "</td>"
							+ "<td>" + data[i].numIn + "</td>"
							+ "<td>" + data[i].numOut + "</td>"
							+ "<td>" + data[i].numReturn + "</td>"
							+ "<td>" + data[i].numScrap + "</td>"
							+ "<td>" + data[i].stockNow + "</td></tr>";
					}
					$("#dateTable").html(html);
				}
			})
					
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/stock/bsStockInOut/">进销存月报表列表</a></li>
	</ul> --%>
	<h2 class="text-center">疫苗销存月报表</h2>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsStockInOut" action="${ctx}/stock/bsStockInOut/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">年：</span>
									<form:select path="year" class="form-control">
										<form:option value="2017" label="2017"/>
										<form:option value="2016" label="2016"/>
									</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">月：</span>
									<form:select path="mouth" class="form-control">
										<form:option value="1" label="1"/>
										<form:option value="2" label="2"/>
										<form:option value="3" label="3"/>
										<form:option value="4" label="4"/>
										<form:option value="5" label="5"/>
										<form:option value="6" label="6"/>
										<form:option value="7" label="7"/>
										<form:option value="8" label="8"/>
										<form:option value="9" label="9"/>
										<form:option value="10" label="10"/>
										<form:option value="11" label="11"/>
										<form:option value="12" label="12"/>
									</form:select>
							</div>
						</div>
						<%-- <div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">疫苗id：</span>
									<form:select path="vaccineId" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">厂商id：</span>
									<form:select path="comId" class="form-control">
										<form:option value="" label=""/>
										<form:options items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
									</form:select>
							</div>
						</div> --%>
				<!-- <div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</div> -->
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th rowspan="2">疫苗名称</th>
						<th rowspan="2">生产厂商</th>
						<th rowspan="2">上月库存</th>
						<th colspan="4">本月发生</th>
						<th rowspan="2">本月库存</th>
					</tr>
					<tr>
						<th>入库</th>
						<th>出库</th>
						<th>退货</th>
						<th>报废</th>
					</tr>
					
				</thead>
				<tbody id="dateTable">
				<c:forEach items="${page.list}" var="bsStockInOut">
					<tr>
						<td>
							${bsStockInOut.vaccineName}
						</td>
						<td>
							${bsStockInOut.comName}
						</td>
						<td>
							${bsStockInOut.stockOld}
						</td>
						<td>
							${bsStockInOut.numIn}
						</td>
						<td>
							${bsStockInOut.numOut}
						</td>
						<td>
							${bsStockInOut.numReturn}
						</td>
						<td>
							${bsStockInOut.numScrap}
						</td>
						<td>
							${bsStockInOut.stockNow}
						</td>
						<shiro:hasPermission name="stock:bsStockInOut:edit"><td>
							<a href="${ctx}/stock/bsStockInOut/form?id=${bsStockInOut.id}">修改</a>
							<a href="${ctx}/stock/bsStockInOut/delete?id=${bsStockInOut.id}" onclick="return confirmx('确认要删除该进销存月报表吗？', this.href)">删除</a>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>