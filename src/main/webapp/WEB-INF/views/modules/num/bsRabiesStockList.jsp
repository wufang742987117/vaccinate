<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>统计记录</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function printStock() {
			var begin = $(".beginTime").val();
			var end = $(".endTime").val();
			var createById = $("#createById").val();
			window.open("${ctx}/num/bsRabiesNum/printStock?beginTime="+ begin + "&&endTime="+ end + "&&createById=" + createById, '_blank');
		}
		
		function stockVal(val) {
			var begin = $(".beginTime").val();
			var end = $(".endTime").val();
			var createById = $("#createById").val();
			window.location.href=encodeURI("${ctx}/num/bsRabiesNum/listUp?beginTime="+ begin + "&&endTime="+ end + "&&createById=" + createById + "&&paystatus=2" + "&&manufacturer=" + encodeURIComponent(val));
		}
		
		function stockValNo(val) {
			var begin = $(".beginTime").val();
			var end = $(".endTime").val();
			var createById = $("#createById").val();
			window.location.href=encodeURI("${ctx}/num/bsRabiesNum/listUp?beginTime="+ begin + "&&endTime="+ end + "&&createById=" + createById + "&&paystatus=3" + "&&manufacturer=" + encodeURIComponent(val));
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a
			href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckin">个案统计</a></li>
		<li><a
			href="${ctx}/num/bsRabiesNum/list">接种明细</a></li>
		<li class="active"><a
			href="${ctx}/num/bsRabiesNum/bsRabiesStockList">统计记录</a></li>
		<li><a
			href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStock">库存明细</a></li>
		<div class=" row pull-right">
			<a href="${ctx}/rabiesvaccine/bsRabiesCheckin" class="btn btn-success mr15" ><span class="glyphicon glyphicon-arrow-left"></span>返回上一页</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsRabiesNum" action="${ctx}/num/bsRabiesNum/bsRabiesStockList" method="post" class="form-inline mt20">
				<div class = "col-xs-11" style="padding-left:0;">
					<div class="form-group" >
						<div class="input-group" >
							<span class="input-group-addon gray-bg text-right">完成日期(起)：</span>
							<input name="beginTime" value="<fmt:formatDate value="${bsRabiesNum.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								class="laydate-icon form-control layer-date beginTime" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">完成日期(止)：</span>
							<input name="endTime" 
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								class="laydate-icon form-control layer-date endTime" value="<fmt:formatDate value="${bsRabiesNum.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"   />
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
						<input id="btnSubmit" class="btn btn-primary btn-sm" type="submit" value="查询" />
						<a href="javascript:printStock()" class="btn btn-primary btn-sm ml15" >打印</a>
					</div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<div class = "col-xs-2" style="padding-left:0;">
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right" style="font-weight: bold;">新建卡：</span>
						<span class="form-control gray-bg text-right" style="color: red ;font-weight: bold; font-size: 13px; line-height: 22px;" >
							${countNumOne}
						</span>
					</div>
				</div>
				<div class="form-group"  >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right" style="font-weight: bold;">接种数：</span>
						<span class="form-control gray-bg text-right" style="color: red ;font-weight: bold; font-size: 13px; line-height: 22px;" >
							${countNumTwo}
						</span>
					</div>
				</div>
			</div>
			<table id="contentTable0" class="table table-striped table-bordered table-condensed">
				<caption class="text-center" style="font-weight: bold; font-size: 28px; ">疫苗当前库存表</caption>
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>疫苗厂家</th>
						<th>疫苗批号</th>
						<th>疫苗规格</th>
						<th>当前库存</th>
						<th>付款未种支数</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${returnlist0}" var="i">
					<c:forEach items="${i}" var="j">
						<tr>
							<c:if test="${j.leng != 0}">
								<td rowspan="${j.leng}">
									${j.vaccname}
								</td>
							</c:if>
							<c:if test="${j.manufacturer != '其他'}">
								<td>
									${j.manufacturer}
								</td>
								<td>
									${j.batchnum}
								</td>
								<td>
									${j.standard}
								</td>
								<td>
									${j.storenum}
								</td>
								<td ondblclick="stockValNo('${j.manufacturer}')">
									<a href="javascript:void(0);" onclick="stockValNo('${j.manufacturer}')">${j.storenum2}</a>
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:forEach>
				</tbody>
			</table>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<caption class="text-center" style="font-weight: bold; font-size: 28px; ">疫苗消耗表</caption>
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>疫苗厂家</th>
						<th>疫苗规格</th>
						<th>小计</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${returnlistOne}" var="i">
					<c:forEach items="${i}" var="j">
						<tr>
							<c:if test="${j.leng != 0}">
								<td rowspan="${j.leng}">
									${j.vaccname}
								</td>
							</c:if>
							<td ondblclick="stockVal('${j.manufacturer}')">
								<a href="javascript:void(0);" onclick="stockVal('${j.manufacturer}')">${j.manufacturer}</a>
							</td>
							<td ondblclick="stockVal('${j.manufacturer}')">
								<a href="javascript:void(0);" onclick="stockVal('${j.manufacturer}')">${j.standard}</a>
							</td>
							<td ondblclick="stockVal('${j.manufacturer}')">
								<a href="javascript:void(0);" onclick="stockVal('${j.manufacturer}')">${j.vcount}</a>
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
					<tr>
						<td>
							合计
						</td>
						<td>
							
						</td>
						<td>
							
						</td>
						<td>
							${socketlistCount }
						</td>
					</tr>
				</tbody>
			</table>
			<table id="contentTable1" class="table table-striped table-bordered table-condensed">
				<caption class="text-center" style="font-weight: bold; font-size: 28px; ">接种统计表</caption>
				<tbody>
					<c:forEach items="${requestScope.str }" var="i" >
						<tr>
							<c:forEach items="${i }" var="j">
								<td>${j}</td>
							</c:forEach>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>