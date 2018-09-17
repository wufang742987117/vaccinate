<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接种统计</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(
	function() {
		$("#searchForm").validate(
			{
				submitHandler : function(form) {
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")|| element.is(":radio")|| element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
});

	$(document).ready( function() {
/* 		$("#vaccName").change(function() {
			var name = $("#vaccName").val();
			$.ajax({ 
			type : "POST",
					url : "${ctx}/manage_stock_in/manageStockIn/batchno2",
					data : {name : name},
					dataType : "json",
					success : function(data) {
						var html = "<option value=''></option>";
						$.each(data,function(idx,item) { //循环对象取值
							html += "<option value='" + item.batchno + "'>"+ item.batchno+ "</option>";
						});
						$("#batch").html(html);
						if(data==null||name!="${childVaccinaterecord.vaccName}"){
							$("#batch").val();
						}else{
							$("#batch").val('${childVaccinaterecord.batch}');
						}
					}
			});
	}); */
		 	
	});

	function page(n, s) {
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
				$("#searchForm").attr("action","${ctx}/child_vaccinaterecord/inoculationOfStatistics/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action",temp);
				layer.close(index);
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
		<li><a href="${ctx}/child_baseinfo/documentStatistics">个案统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement">应种统计</a></li>
		<li class="active"><a href="${ctx}/child_vaccinaterecord/inoculationOfStatistics">接种统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/inoculationOfStatistics/logList">接种日志</a></li>
		<li><a href="${ctx}/child_baseinfo/overduevacc">逾期未种</a></li>
<%-- 		<li><a href="${ctx}/product/bsManageProduct/repertorySum">库存统计</a></li> --%>
		<li><a href="${ctx}/product/bsManageProduct/productUseList">疫苗使用详情</a></li>
		<li><a href="${ctx}/invoicing/bsInvoicing/">进销存统计</a></li>
		<div class=" row pull-right">
				<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
		</div>
		
	</ul>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="childVaccinaterecord"
				action="${ctx}/child_vaccinaterecord/inoculationOfStatistics"
				method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden"
					value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden"
					value="${page.pageSize}" />
						<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">开始时间:</span> <input
							name="beginTime" value="<fmt:formatDate value="${childVaccinaterecord.beginTime}" pattern="yyyy-MM-dd"/>"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
							class="laydate-icon form-control layer-date" />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">截至时间:</span> <input
							name="endTime" value="<fmt:formatDate value="${childVaccinaterecord.endTime}" pattern="yyyy-MM-dd"/>"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
							class="laydate-icon form-control layer-date"  />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">儿童姓名:</span>
						<form:input path="childname" htmlEscape="false" maxlength="18"
							class="form-control" />
					</div>
				</div> 
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">儿童编号:</span>
						<form:input path="childcode" htmlEscape="false" maxlength="18"
							class="form-control " />
					</div>
				</div> 
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称:</span>
						<form:select path="vaccName" class="form-control">
							<form:option value="" label="" />
							<c:forEach items="${bsManageVaccineList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.id }"
									label="${bsManageVaccine.name }" />
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗批号:</span>
<%-- 						<form:select path="batch" class="form-control">
							<form:option value="" label="" />
						</form:select> --%>
						<form:input path="batch" class="form-control"/>
					</div>
				</div>
				<div class="form-group" >
					<input id="btnSubmit" class="btn btn-primary mr15" type="submit" value="查询" />
					<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/>
				</div>
			</form:form>
			<sys:message content="${message}" />
			<table id="contentTable"
				class="table table-striped table-bordered table-condensed">
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
						<th>创建时间</th>
						<th>疫苗价格</th>
						<%-- <shiro:hasPermission
							name="child:cord:edit">
							<th>操作</th>
						</shiro:hasPermission> --%>
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
							<td><fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd" /></td>
							<td>${fns:getDictLabel(childVaccinaterecord.bodypart, 'position', '')}
							<td>${childVaccinaterecord.batch}</td>
							<td>${childVaccinaterecord.office}</td>
							<td>${childVaccinaterecord.doctor}</td>
							<td><fmt:formatDate value="${childVaccinaterecord.createDate}"	pattern="yyyy-MM-dd" /></td>
							<td>${childVaccinaterecord.price}</td>
							<%-- <shiro:hasPermission
								name="child:cord:edit">
								<td><a
									href="${ctx}/child_vaccinaterecord/InoculationOfStatistics/delete?id=${childVaccinaterecord.id}"
									onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a></td>
							</shiro:hasPermission> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<div class="page">${page}</div>  
		</div>
		
		<script type="text/javascript">
		$(function(){
		$("#vaccName").change(); 
		})
		</script>
	</div>
</body>
</html>