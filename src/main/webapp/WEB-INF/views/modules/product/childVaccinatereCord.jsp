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
	<div class="ibox">
		<div >
			<table id="contentTable"
				class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>儿童姓名</th>
						<th>儿童编号</th>
						<th>疫苗名称</th>
						<th>疫苗厂家</th>
						<th>针次</th>
						<th>接种部位</th>
						<th>疫苗批号</th>
						<th>医生姓名</th>
						<th>疫苗价格</th>
						<th>使用时间</th>
						<%-- <shiro:hasPermission
							name="child:cord:edit">
							<th>操作</th>
						</shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="childVaccinaterecord">
						<tr>
							<td>${childVaccinaterecord.childName}</td>
							<td>${childVaccinaterecord.childCode}</td>
							<td>${childVaccinaterecord.name}</td>
							<td>${childVaccinaterecord.manufacturer}</td>
							<td>${childVaccinaterecord.dosage}</td>
							<td>${fns:getDictLabel(childVaccinaterecord.bodyPart, 'position', '')}
							<td>${childVaccinaterecord.batchno}</td>
							<td>${childVaccinaterecord.doctor}</td>
							<td>${childVaccinaterecord.price}</td>
							<td><fmt:formatDate value="${childVaccinaterecord.vaccinateDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<%-- <shiro:hasPermission
								name="child:cord:edit">
								<td><a
									href="${ctx}/child_vaccinaterecord/InoculationOfStatistics/delete?id=${childVaccinaterecord.id}"
									onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a></td>
							</shiro:hasPermission> --%>
						</tr>
		 <form:form id="searchForm" modelAttribute="productUseList" action="${ctx}/product/bsManageProduct/childVaccinatereCord">
						   <input id="batchno" name="batchno" type="hidden" value="${childVaccinaterecord.batchno}">
						   <input id="id" name="id" type="hidden" value="${childVaccinaterecord.id}">
						   <input id="manufacturerCode" name="manufacturerCode" type="hidden" value="${childVaccinaterecord.manufacturerCode}">
					       <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
					       <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>		       
		</form:form>
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