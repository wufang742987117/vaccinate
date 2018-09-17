<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>出入库配置</title>
<meta name="decorator" content="default" />
<style type="text/css">
.ml10 {
	margin-left: 10px;
}
.mr10 {
	margin-right: 10px;
}
</style>
<link rel="stylesheet" href="${ctxStatic}/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
<script src="${ctxStatic}/js/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	function openWindows(url, title,w,h) {
		w = isNull(w) ? 900 : w;
	    h = isNull(h) ? 800 : h;
		var ww = ($(document).width() < w ? $(document).width()-30 : w) + "px";
		var hh = ($(document).height() < h ? $(document).height()-30 : h) + "px";
		layer.open({
	        type: 2,
	        area: [ww,hh],
	        title: isNull(title) ? "信息" : title,
	        fix: false, //不固定
	        maxmin: false,
	        shade: 0.3,
	        shift: 0, //0-6的动画形式，-1不开启
	        content: url,
		});
	} 
	
	function closeForm(){
		layer.closeAll();
		window.location.href = '${ctx}/manage_stock_in/manageStockIn/form';
	}
		
	$(document).ready( function() {
		//根据疫苗名字查询疫苗的所有批次
/* 		$("#name").change(function() {
			var vaccid = $("#name").val();
			$.ajax({ 
				type : "POST",
				url : "${ctx}/product/bsManageProduct/findViewListApi",
				data : {"vaccineid" : vaccid, "storenumIsNull":true},
				dataType : "json",
				success : function(data) {
					var html = "<option value=''>--请选择--</option>";
					$.each(data,function(idx,item) { //循环对象取值
						html += "<option value='" + item.batchno + "'>"+ item.batchno+ "</option>";
					});
					$("#batchno").html(html);
					 if(data==null||name!="${manageStockIn.product.name}"){
						$("#batchno").val();
					}else{
						$("#batchno").val('${manageStockIn.product.batchno}');
					} 
				}
			});
		});
		$("#name").change(); 	 */
		var nameValue = '${fns:toJson(manageStockIn.product.vaccineidIn)}';
		if(nameValue && nameValue != "\"\""){
			$("#name").selectpicker('val',JSON.parse(nameValue));
		}
	});

	$(document).ready(function() {
		$("#btnExport").click(function(){
			layer.confirm("确认要导出数据吗？", {
				btn: ['确认','取消'], //按钮
				shade: true, //不显示遮罩
				icon : 0
			}, function(index){
				var temp = $("#searchForm").attr("action");
				$("#searchForm").attr("action","${ctx}/manage_stock_in/manageStockIn/export");
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
        <li ><a href="${ctx}/product/bsManageProduct/">疫苗库存管理</a></li>
		<li class="active"><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
		<div class=" col-sm-12">
			<form:form id="searchForm" modelAttribute="manageStockIn" action="${ctx}/manage_stock_in/manageStockIn" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
				<div class="form-group" >
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">日期(起)：</span>
						<input name="beginTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${manageStockIn.beginTime}" pattern="yyyy-MM-dd"/>" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">日期(止)：</span>
						<input name="endTime" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${manageStockIn.endTime}" pattern="yyyy-MM-dd"/>"   />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">类型：</span>
							<form:select path="type" class="form-control">
								<form:option value="">--请选择-- </form:option>
								<form:options items="${fns:getDictList('in_out')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称：</span>
						 <select title="请选择疫苗名称" id="name" name="product.vaccineidIn" class="selectpicker show-tick form-control w164"  multiple data-live-search="false">
							<option value="">--请选择--</option>
							<c:forEach items="${products}" var="pro">
                               <option value="${pro.id}" class="properties" >${pro.name}</option>
                            </c:forEach>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗批次：</span>
						<form:select path="product.batchno" id="batchno" class="form-control">
						</form:select>
					</div>
				</div>
				<div style="display: inline-block;">
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary mr10 w100" type="submit" value="查询" />
				</div>
				<div class="form-group">
<%-- 					<a class="btn btn-primary mr10 w100" type="button" href="${ctx}/manage_stock_in/manageStockIn/addStockIn">入库</a> --%>
<%-- 					<a class="btn btn-primary mr10 w100" type="button" href="${ctx}/manage_stock_in/manageStockIn/addStockOut">出库</a> --%>
					<a class="btn btn-primary mr10" href="javascript:void(0);"  onclick="alertForm('${ctx}/stock/bsStockInOut','疫苗销存月报表');">疫苗销存月报表</a>
					<!-- <input id="btnExport" class="btn btn-primary mr10" type="button" value="导出报表"/> -->
				</div>
				</div>
				<%-- <div class=" row pull-right">
					<span>库存&nbsp;</span><span id="amount" style="color: red" >${amount}</span>
				</div> --%>
			</form:form>
		</div>
		<div class=" col-sm-12">
			<sys:message content="${message}" />
			<table id="contentTable"
				class="table  table-bordered table-condensed">
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
			</table>
			<div class="page">${page}</div> 
		</div>
	</div>
	
</body>
</html>