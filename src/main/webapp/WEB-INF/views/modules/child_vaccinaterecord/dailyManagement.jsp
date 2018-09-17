<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css">
.borderLength {
	width: 300px;
}
.w155{
	width: 155px !important;
}

.mr15{
	margin-right: 15px;
}
</style>
<title>应种统计</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
<script src="${ctxStatic}/js/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
<script type="text/javascript">
	$(function(){
	
		//数据回显
		var residesValue = '${fns:toJson(expChildBaseInfoVo.resides)}';
		if(residesValue && residesValue != "\"\""){
			$("#resides").selectpicker('val',JSON.parse(residesValue));
		}
		var situationsValue = '${fns:toJson(expChildBaseInfoVo.situations)}';
		if(situationsValue && situationsValue != "\"\""){
			$("#situations").selectpicker('val',JSON.parse(situationsValue));
		}
		var areasValue = '${fns:toJson(expChildBaseInfoVo.areas)}';
		if(areasValue && areasValue != "\"\""){
			$("#areas").selectpicker('val',JSON.parse(areasValue));
		}
		var vaccinatesValue = '${fns:toJson(expChildBaseInfoVo.vaccinates)}';
		if(vaccinatesValue && vaccinatesValue != "\"\""){
			$("#vaccinates").selectpicker('val',JSON.parse(vaccinatesValue));
		}
		
		//全选和全不选操作
		$("#areas").change(function () {  
	        var text = $(this).children('option:selected').val();  
	        if ($(".total").parent("li").hasClass("selected")) {  
	       		$(".none").parents('li').removeClass('selected'); 
				$(".selection").parents('li').addClass('selected');
	        } else {  
	       		$(".total").parents('li').removeClass('selected'); 
				$(".selection").parents('li').removeClass('selected');
	        }  
	    });
	});
	
	
	$(document).ready(
		function() {
			$("#searchForm").validate({
			submitHandler : function(form) {
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

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	};
	
	$(document).ready(function() {
		$("#btnExport").click(function(){
			layer.confirm("确认要导出数据吗？", {
				btn: ['确认','取消'], //按钮
				shade: false, //不显示遮罩
				icon : 0
			}, 
			function(index){
			var temp = $("#searchForm").attr("action");
				$("#searchForm").attr("action","${ctx}/child_vaccinaterecord/childVaccinaterecord1/export");
				$("#searchForm").submit();
				$("#searchForm").attr("action",temp);
				layer.close(index);
			}, function(){
				layer.close();
			});
		});
	});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/child_baseinfo/documentStatistics">个案统计</a></li>
		<li class="active"><a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement">应种统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/inoculationOfStatistics">接种统计</a></li>
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
			<form:form id="searchForm" modelAttribute="expChildBaseInfoVo"
				action="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement"
				method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden"
					value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden"
					value="${page.pageSize}" />
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">区域划分:</span>
						<select title="请选择区域划分" id="areas" name="areas" class="selectpicker show-tick form-control w155" multiple data-live-search="false">
							<option value="total" class="area total" id="total" >全选</option>
							<option value="none" class="area none" id="none" >全不选</option>
                               <c:forEach items="${areas}" var="area">
							   		<option value="${area.code}" class="area selection" >${area.name}</option>
                               </c:forEach>
                          </select> 
					</div>
				</div>
				
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称:</span>
						<select title="接种疫苗" id="vaccinates" name="vaccinates" class="selectpicker show-tick form-control w155" multiple data-live-search="false">
                               <c:forEach items="${bsManageVaccineList}" var="bsManageVaccine">
									   		<option value="${bsManageVaccine.gNum}" class="vaccinate" >${bsManageVaccine.gName}</option>
                               </c:forEach>
                          </select> 
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">出生日期(起):</span> <input
							name="birthbeginTime"
							value="<fmt:formatDate value="${expChildBaseInfoVo.birthbeginTime}" pattern="yyyy-MM-dd"/>"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
							class="laydate-icon form-control layer-date"  />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">出生日期(止):</span>
						 <input name="birthendTime" value="<fmt:formatDate value="${expChildBaseInfoVo.birthendTime}" pattern="yyyy-MM-dd"/>"
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
							class="laydate-icon form-control layer-date"  />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">居住类别:</span>
						<select title="请选择居住类别" id="resides" name="resides" class="selectpicker show-tick form-control w155" multiple data-live-search="false">
                               <c:forEach items="${fns:getDictList('reside')}" var="reside">
                               		<option value="${reside.value}" class="reside" >${reside.label}</option>
                               </c:forEach>
                          </select> 
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">在册类别:</span>
						 <select title="请选择在册类别" id="situations" name="situations" class="selectpicker show-tick form-control w155" multiple data-live-search="false">
                                   <c:forEach items="${fns:getDictList('situation')}" var="situation">
                                   <option value="${situation.value}" class="situation" >${situation.label}</option>
                                   </c:forEach>
                          </select> 
					</div>
				</div>
				
				<div class="form-group" >
					<label class="mr15">
<!-- 						<input id="checkEx" name="checkEx" type="checkbox" value="1" class="i-checks"><span>排除近期已接种</span> -->
						<%-- <form:checkbox path="checkEx" cssClass="i-checks" value="1"  label="排除近期已接种"/> --%>
					</label>
					<input id="btnSubmit" class="btn btn-primary mr15" type="submit" value="查询" />
					<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/>
				</div>
			</form:form>
			<sys:message content="${message}" />
			<table id="contentTable"
				class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>儿童编码</th>
						<th>儿童姓名</th>
						<th>性别</th>
						<th>居住属性</th>
						<th>出生日期</th>
						<th>母亲姓名</th>
						<th>母亲联系方式</th>
						<th>父亲姓名</th>
						<th>父亲联系方式</th>
						<th>地址</th>
						<th>区域划分</th>
						<th>应种疫苗</th>
						<th>应种剂次</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="info">
						<tr ondblclick="window.open('${ctx}/child_vaccinaterecord/childVaccinaterecord1/list1?childid=${info.CHILDCODE}')">
							<td>${info.CHILDCODE}</td>
							<td>${info.CHILDNAME}</td>
							<td>${fns:getDictLabel(info.GENDER, 'sex', '')}</td>
							<td>${fns:getDictLabel(info.RESIDE, 'reside', '')}</td>
							<td><fmt:formatDate value="${info.BIRTHDAY}" pattern="yyyy-MM-dd" /></td>
							<td>${info.GUARDIANNAME}</td>
							<td>${info.GUARDIANMOBILE}</td>
							<td>${info.FATHER}</td>
							<td>${info.FATHERPHONE}</td>
							<td>${info.ADDRESS}</td>
							<td>${info.COMMNAME}</td>
							<td>${info.NAME}</td>
							<%-- <td><fmt:formatDate value="${info.CHILDORDER}" pattern="yyyy-MM-dd" /></td> --%>
							<td>${info.DOSAGE}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<div class="page">${page}</div>  
		</div>
	</div>
</body>
</html>