<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>每日盘库管理</title>
	<meta name="decorator" content="default"/>
<link rel="stylesheet" href="${ctxStatic}/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
<script src="${ctxStatic}/js/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
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
	</style>
	<script type="text/javascript">
		$(function(){
			var nameValue = '${fns:toJson(bsManageCheck.product.vaccineidIn)}';
			if(nameValue && nameValue != "\"\""){
				$("#vaccName").selectpicker('val',JSON.parse(nameValue));
			}
		})
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		
		/* 打开详细列表页面 */
		function detailList(thi){
			var _this = $(thi); 
			if(_this.html() && _this.html().trim() == '0'){
				return;
			}
			var pid = _this.parent().attr("data-pid");
			var type = _this.attr("data-type");
			var startTime = _this.parent().attr("data-beginTime");
			var endTime = _this.parent().attr("data-endTime");
			window.open('${ctx}/product/bsManageCheck/detailList?pid='+ pid + '&type=' + type + '&startTime=' + startTime+ '&endTime=' + endTime ,"_blank");
		}
	</script>
</head>
<body>
	<div class="form-group text-left ml20" style="position: absolute;">
		<span class="sub-title">每日盘点-记录</span>
	</div>
	<div class="ibox" style="margin-top: 27px;">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsManageCheck" action="${ctx}/product/bsManageCheck" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg">日期：</span>
							<input type="text" name="checkDate" value="${bsManageCheck.checkDate}" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称：</span>
						 <select title="请选择疫苗名称" id="vaccName" name="product.vaccineidIn" class="selectpicker show-tick form-control w164"  multiple data-live-search="false">
							<option value="">--请选择--</option>
							<c:forEach items="${vaccinates}" var="vacc">
                               <option value="${vacc.id}" class="properties" >${vacc.name}</option>
                            </c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg">批号：</span>
							<input type="text" name="product.batchno" value="${bsManageCheck.product.batchno}" class="form-control"/>
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">操作人:</span> 
						<form:select path="createBy.id" class="form-control">
							<form:option value="" >全部</form:option>
							<c:forEach items="${doctorlist }" var="vac">
								<form:option value="${vac.id}">${vac.name }</form:option>
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<input id="btnSearch" class="btn btn-primary w100" type="submit"  value="查询"/>
				</div>

			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>盘点日期</th>
						<th>盘点时间</th>
						<th>疫苗名称</th>
						<th>疫苗批号</th>
						<th>疫苗厂商</th>
						<th>剂量</th>
						<th>入库时间</th>
<!-- 						<th>上期时间</th> -->
						<th>上期库存</th>
						<th>本期入库</th>
					    <th>其他出库</th>
					    <th>调剂出库</th>
						<th>本期报损</th>
						<th>本期使用</th>
						<th>实际库存</th>
						<th>操作人</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="check">
					<tr data-pid='${check.product.id}' data-beginTime='<fmt:formatDate value="${check.lastDate}" pattern="yyyy-MM-dd HH:mm:ss"/>'
						data-endTime='<fmt:formatDate value="${check.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
						<td>
							<a href="${ctx}/product/bsManageCheck?checkDate=${check.checkDate}">${check.checkDate}</a>
						</td>
						<td>
							<fmt:formatDate value="${check.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td class="text-left">
							${check.product.name}
						</td>
						<td>
							${check.product.batchno}
						</td>
						<td>
							${check.product.manufacturer}
						</td>
						<td>
							${check.product.specification}
						</td>
						<td>
							<fmt:formatDate value="${check.product.createDate}" pattern="yyyy-MM-dd"/>
						</td>
						<%-- <td>
 							<fmt:formatDate value="${check.lastDate}" pattern="yyyy-MM-dd"/>
						</td> --%>
						<td>
							${check.lastNum}
						</td>
						<td class="link" data-type='IN' onclick="detailList(this)">
							${check.inNum}
						</td>
						<td class="link" data-type='OUT' onclick="detailList(this)">
							${check.outNum}
						</td>
						<td class="link" data-type='EXCHANGE' onclick="detailList(this)">
							${check.exchangeNum}
						</td>
						<td class="link" data-type='SCRAP' onclick="detailList(this)">
							${check.scrapNum}
						</td>
						<td class="link" data-type='USE' onclick="detailList(this)">
							${check.useNum}
						</td>
						<td>
							${check.restNum}
						</td>
						<td>
							${check.createBy.name}
						</td>
						<td>
							${check.remarks}
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