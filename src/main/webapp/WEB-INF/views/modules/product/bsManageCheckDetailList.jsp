<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>每日盘库管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.sub-title{
			font-size: 18px;
		}
		.sub-title:after{
		    content: "";
		    display: block;
		    height: 3px;
		    border-bottom: 2px #000 solid;
		    width: 160px;
		}
		
		#contentTable tr>td{
			text-align: center;
		}
		.top-close{
		    position: absolute;
		    top: -8px;
		    right: 36px;
		}
		
	</style>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
	</script>
</head>
<body>
	<div class="form-group text-left ml20" style="position: absolute;">
		<span class="sub-title">每日盘点-明细</span>
	</div>
	<button class="btn btn-success w100 top-close" onclick="window.close()"" >关闭</button>
	<div class="ibox" style="margin-top: 27px;">
		<div class="ibox-content">
			<form:form id="searchForm" action="${ctx}/product/bsManageCheck/detailList" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<input id="type" name="type" type="hidden" value="${type}"/>
				<input id="startTime" name="startTime" type="hidden" value="${startTime}"/>
				<input id="endTime" name="endTime" type="hidden" value="${endTime}"/>
				<input id="pid" name="pid" type="hidden" value="${pid}"/>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
<!-- ===入库列表 start=========================================== -->
					<c:if test="${type eq 'IN' or type eq'OUT' or type eq'SCRAP' or type eq'EXCHANGE' }">
						<thead>
							<tr>
								<th>类型</th>
								<th>疫苗名称</th>
								<th>生产企业</th>
								<th>疫苗批次</th>
								<th>疫苗规格</th>
								<th>数量</th>
								<th>出入库时间</th>
								<th>出库单号</th>
								<th>操作人</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="manageStockIn">
								<c:if test="${ manageStockIn.type=='0'}">
									<tr>
								</c:if>
								<c:if test="${ manageStockIn.type=='1'}">
									<tr style="background: rgba(255, 0, 27, 0.08);">
								</c:if>
									<td class="text-center">${fns:getDictLabel(manageStockIn.type, 'in_out', '')}</td>
									<td>${manageStockIn.product.vaccName}</td>
									<td class="text-center">${manageStockIn.product.manufacturer}</td>
									<td class="text-center">${manageStockIn.product.batchno}</td>
									<td class="text-center">${fns:getDictLabel(manageStockIn.product.specification, 'specification', '')}</td>
									<td class="text-center">${manageStockIn.num}</td>
									<td class="text-center"><fmt:formatDate value="${manageStockIn.indate}" pattern="yyyy-MM-dd HH:mm" /></td>
									<td class="text-center">${manageStockIn.orderNo}</td>
									<td class="text-center">${manageStockIn.createBy.name}</td> 
									<td >${manageStockIn.remarks}</td> 
								</tr>
							</c:forEach>
						</tbody>
					</c:if>
<!-- ===入库列表 end============================================= -->

<!-- ===消耗列表 start=========================================== -->
					<c:if test="${type eq'USE' }">
						<thead>
							<tr>
								<th>儿童姓名</th>
								<th>儿童编号</th>
								<th>疫苗名称</th>
								<th>疫苗厂家</th>
								<th>针次</th>
								<th>接种日期</th>
								<th>接种部位</th>
								<th>疫苗批号</th>
								<th>接种单位</th>
								<th>医生姓名</th>
<!-- 								<th>创建时间</th> -->
								<th>疫苗价格</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${page.list}" var="childVaccinaterecord">
								<tr>
									<td>${childVaccinaterecord.childname}</td>
									<td>${childVaccinaterecord.childcode}</td>
									<td>${childVaccinaterecord.vaccName}</td>
									<td>${childVaccinaterecord.manufacturer}</td>
									<td>${childVaccinaterecord.dosage}</td>
									<td><fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>${fns:getDictLabel(childVaccinaterecord.bodypart, 'position', '')}
									<td>${childVaccinaterecord.batch}</td>
									<td>${childVaccinaterecord.office}</td>
									<td>${childVaccinaterecord.doctor}</td>
<%-- 									<td><fmt:formatDate value="${childVaccinaterecord.createDate}"	pattern="yyyy-MM-dd" /></td> --%>
									<td>${childVaccinaterecord.price}</td>
								</tr>
							</c:forEach>
						</tbody>
					</c:if>
<!-- ===消耗列表 end============================================ -->
			</table>
			<div class="page">${page}</div>
		</div>
	</div>

</body>
</html>