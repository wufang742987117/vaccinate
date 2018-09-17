<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接种统计</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#searchForm").validate({
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
				$("#searchForm").attr("action","${ctx}/child_vaccinaterecord/inoculationOfStatistics/logExport");
				$("#searchForm").submit();
				$("#searchForm").attr("action",temp);
				layer.close(index);
			}, function(){
				layer.close();
			});
		});
	});
	
	$(function(){
		//旧签字展示
		var strm;
		var im;
		$(".image").each(function(i,t){
			strm = t.innerText;
			im = new Image();
	        im.src = "data:image/png;base64" + "," + strm;
	        im.height = 33;
	        $(this).html(im);
		});
		
		$("#vaccName").change(); 
		
	})
	
	function printLog(){   
		var beginTime = $(".beginTime").val();
		var endTime = $(".endTime").val();
		var childname = $("#childname").val();
		var vaccName = $("#vaccName").val();
		var batch = $("#batch").val();
		var pageNo = $("#pageNo").val();
		var pageSize = $("#pageSize").val();
		var createById = $("#createById").val();
		window.open("${ctx}/child_vaccinaterecord/inoculationOfStatistics/logListPrint?beginTime="+ beginTime + 
			"&&endTime="+ endTime + 
			"&&createById=" + createById + 
			"&&childname=" + childname + 
			"&&vaccName=" + vaccName + 
			"&&batch=" + batch + 
			"&&pageNo="+ pageNo + 
			"&&pageSize=" + pageSize,
		 '_blank');
	} 
	
	
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/child_baseinfo/documentStatistics">个案统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement">应种统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/inoculationOfStatistics">接种统计</a></li>
		<li class="active"><a href="${ctx}/child_vaccinaterecord/inoculationOfStatistics/logList">接种日志</a></li>
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
			<form:form id="searchForm" modelAttribute="childVaccinaterecord" action="${ctx}/child_vaccinaterecord/inoculationOfStatistics/logList" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">开始时间:</span> 
						<input name="beginTime" value="<fmt:formatDate value="${childVaccinaterecord.beginTime}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date beginTime" />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">截至时间:</span> 
						<input name="endTime" value="<fmt:formatDate value="${childVaccinaterecord.endTime}" pattern="yyyy-MM-dd"/>" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date endTime"  />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">儿童姓名:</span>
						<form:input path="childname" htmlEscape="false" maxlength="18" class="form-control" />
					</div>
				</div> 
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称:</span>
						<form:select path="vaccName" class="form-control">
							<form:option value="" label="" />
							<c:forEach items="${bsManageVaccineList }" var="bsManageVaccine">
								<form:option value="${bsManageVaccine.id }" label="${bsManageVaccine.name }" />
							</c:forEach>
						</form:select>
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗批号:</span>
						<form:input path="batch" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">接种医生：</span>
							<form:select path="createById" class="form-control ">
								<form:option value="0">全部</form:option>
								<form:options items="${createByNameList}" itemValue="name" itemLabel="name" htmlEscape="false"/>
							</form:select>
					</div>
				</div>
				<div class="form-group" >
					<input id="btnSubmit" class="btn btn-primary mr15" type="submit" value="查询" />
					<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/>
					<a href="javascript:printLog()" class="btn btn-primary btn-sm ml15" >打印</a>
				</div>
			</form:form>
			<sys:message content="${message}" />
			<table id="contentTable" class="table table-striped table-bordered table-condensed text-center" style="height: 38px; max-height: 38px;">
				<thead>
					<tr>
						<th>接种日期<br />(年月日)</th>
						<th>儿童姓名</th>
						<th>性别</th>
						<th>出生日期<br />(年月日)</th>
						<th>过敏<br />史</th>
						<th>发热℃</th>
						<th>咳嗽</th>
						<th>腹泻</th>
						<th>其他症状</th>
						<th>近期是<br />否患病</th>
						<th>接种疫苗<br />及第几剂次</th>
						<th>疫苗批号</th>
						<th>生产企业</th>
						<th>家长签字及<br />更新电话号码</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="childVaccinaterecord">
						<tr>
							<td><fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd" /></td>
							<td>${childVaccinaterecord.childname}</td>
							<td>${fns:getDictLabel(childVaccinaterecord.gender, 'sex', '')}</td>
							<td><fmt:formatDate value="${childVaccinaterecord.birthday}" pattern="yyyy-MM-dd" /></td>
							<td>
								<c:if test="${childVaccinaterecord.childAbnormalReaction eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.childAbnormalReaction ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.fever eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.fever ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.cough eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.cough ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.diarrhea eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.diarrhea ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.symptom eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.symptom ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.disease eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.disease ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>${childVaccinaterecord.vaccName}<br />第${childVaccinaterecord.dosage}剂次</td>
							<td>${childVaccinaterecord.batch}</td>
							<td>${childVaccinaterecord.manufacturer}</td>
							<td>
								<c:if test="${empty childVaccinaterecord.signatureList || childVaccinaterecord.signature == 0}">
									${childVaccinaterecord.parentsMoblie}
								</c:if>
								<c:if test="${not empty childVaccinaterecord.signatureList && childVaccinaterecord.signature == 1 && childVaccinaterecord.stype == 1}">
									<div class="image" style="margin-right: 0;">${childVaccinaterecord.signList}</div>
									${childVaccinaterecord.parentsMoblie}
								</c:if>
								<c:if test="${not empty childVaccinaterecord.signatureList && childVaccinaterecord.signature == 1 && childVaccinaterecord.stype != 1}">
									<img src="${ctx}/child_vaccinaterecord/childVaccinaterecord/signatureimg?id=${childVaccinaterecord.id}" height="33px" style="margin-right: 0;"/>
									${childVaccinaterecord.parentsMoblie}
								</c:if>
							</td>
							<td>${childVaccinaterecord.childRemarks}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>  
		</div>
	</div>
</body>
</html>