<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接种明细</title>
	<meta name="decorator" content="hbdefault"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function printProduct() {
			var begin = $(".beginTime").val();
			var end = $(".endTime").val();
			var createById = $("#createById").val();
			var vaccType = $("#vaccType").val();
			var manufacturer = $("#manufacturer").val();
			window.open(encodeURI("${ctx}/hepatitisnum/bsHepatitisBNum/printProduct?beginTime="+ begin + "&&endTime="+ end + "&&createById=" + createById + "&&vaccType=" + vaccType + "&&manufacturer=" + encodeURIComponent(manufacturer)), '_blank');
		}
        
        $(document).ready(function() {
			$("#btnExport").click(function(){
				layer.confirm("确认要导出数据吗？", {
					btn: ['确认','取消'], //按钮
					shade: true, //不显示遮罩
					icon : 0
				}, function(index){
					var temp = $("#searchForm").attr("action");
					$("#searchForm").attr("action","${ctx}/hepatitisnum/bsHepatitisBNum/export");
					$("#searchForm").submit();
					layer.close(index);
					$("#searchForm").attr("action",temp);
				}, function(){
					layer.close();
				});
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a
			href="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckin">个案统计</a></li>
		<li class="active"><a
			href="${ctx}/hepatitisnum/bsHepatitisBNum/list">接种明细</a></li>
		<li><a
			href="${ctx}/hepatitisnum/bsHepatitisBNum/bsHepatitisBStockList">统计记录</a></li>
		<li><a
			href="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckinStock">库存明细</a></li>
		<div class=" row pull-right">
			<a href="${ctx}/hepatitis/bsHepatitisBcheckin" class="btn btn-success mr15" ><span class="glyphicon glyphicon-arrow-left"></span>返回上一页</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsHepatitisBNum" action="${ctx}/hepatitisnum/bsHepatitisBNum/list" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
					<div class = "col-xs-11" style="padding-left:0;">
						<div class="form-group" >
							<div class="input-group" >
								<span class="input-group-addon gray-bg text-right">起始日期：</span>
								<input name="beginTime" value="<fmt:formatDate value="${bsHepatitisBNum.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
									onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
									class="laydate-icon form-control layer-date beginTime" />
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">终止日期：</span>
								<input name="endTime" value="<fmt:formatDate value="${bsHepatitisBNum.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>" 
									onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
									class="laydate-icon form-control layer-date endTime" />
							</div>
						</div>
					 	<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">疫苗厂家：</span>
								<form:input path="manufacturer" class="form-control" />
							</div>
						</div> 
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
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">操作状态：</span>
								<form:select path="payStatus" class="form-control ">
									<form:option value="0">应种未付款</form:option>
									<form:option value="1">应种已付款</form:option>
									<form:option value="2">已种已签字</form:option>
									<form:option value="3">未种已付款</form:option>
									<form:option value="4">未种已签字</form:option>
									<form:option value="5">已种未签字</form:option>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">接种医生：</span>
								<form:select path="createById" class="form-control ">
									<form:option value="0">全部</form:option>
									<form:options items="${createByNameList}" itemValue="id" itemLabel="loginName" htmlEscape="false"/>
								</form:select>
							</div>
						</div>
						<div class="form-group">
							<input id="btnSubmit" class="btn btn-bshepb btn-sm" type="submit" value="查询" />
							<input id="btnExport" class="btn btn-bshepb btn-sm ml15" type="button" value="导出报表"/>
							<a href="javascript:printProduct()" class="btn btn-bshepb btn-sm ml15" >打印</a>
						</div>
					</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>序     号</th>
						<th>编     码</th>
						<th>姓     名</th>
						<th>性     别</th>
						<th>针     次</th>
						<th>针     数</th>
						<th>疫苗类型</th>
						<th>疫苗厂家</th>
						<th>疫苗批次</th>
						<th>疫苗规格</th>
						<th>接种医生</th>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsHepatitisBProduct" varStatus="status">
					<c:if test="${bsHepatitisBProduct.signature == 0 || bsHepatitisBProduct.signature == 2}">
						<tr ondblclick="window.location.href='${ctx}/hepatitis/bsHepatitisBcheckin/form?id=${bsHepatitisBProduct.kin.id}'" style="background-color:#FFA07A !important ">
					</c:if>
					<c:if test="${bsHepatitisBProduct.signature == 1}">
						<tr ondblclick="window.location.href='${ctx}/hepatitis/bsHepatitisBcheckin/form?id=${bsHepatitisBProduct.kin.id}'">
					</c:if>
						<td>
							${status.index + 1}
						</td>
						<td>
							${bsHepatitisBProduct.kin.hepaBcode}
						</td>
						<td>
							${bsHepatitisBProduct.kin.username} 
						</td>
						<td>
						 	${fns:getDictLabel(bsHepatitisBProduct.kin.sex, 'sex', '')} 
						</td>
						<td>
							${fns:getDictLabel(bsHepatitisBProduct.vaccineNum, 'pin', '')}    
						</td>
						<td>
							${bsHepatitisBProduct.vacciCount} 
						</td>
						<td>
							${bsHepatitisBProduct.vaccType}
						</td>
						<td>
							${bsHepatitisBProduct.manufacturer}
						</td>
						<td>
							${bsHepatitisBProduct.batch}
						</td>
						<td>
							${bsHepatitisBProduct.standard}
						</td>
						<td>
							${bsHepatitisBProduct.createName}
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