<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<style type="text/css">
.borderLength {
	width: 300px;
}

.mr15{
	margin-right: 15px;
}
</style>
<title>建档统计</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
<script src="${ctxStatic}/js/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
<script type="text/javascript">

$(document).ready(function() {
	$("#btnExport").click(function(){
		layer.confirm("确认要导出数据吗？", {
			btn: ['确认','取消'], //按钮
			shade: true, //不显示遮罩
			icon : 0
		}, function(index){
		var temp = $("#searchForm").attr("action");
			$("#searchForm").attr("action","${ctx}/child_baseinfo/childBaseinfo/export");
			$("#searchForm").submit();
			layer.close(index);
			$("#searchForm").attr("action",temp);
		}, function()
		{
			layer.close();
		});
	});
	

});
			$(function(){
			//判断是否选中户籍类别
			var reside="${childBaseinfo.resides}";
			if(reside.indexOf("'1'")>-1){
				$th = $(".reside[value='1']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(reside.indexOf("'2'")>-1){
				$th = $(".reside[value='2']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(reside.indexOf("'3'")>-1){
				$th = $(".reside[value='3']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			
			//判断是否选中在册类别
			var situation="${childBaseinfo.situations}";
			if(situation.indexOf("'1'")>-1){
				$th = $(".situation[value='1']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(situation.indexOf("'2'")>-1){
				$th = $(".situation[value='2']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(situation.indexOf("'3'")>-1){
				$th = $(".situation[value='3']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(situation.indexOf("'4'")>-1){
				$th = $(".situation[value='4']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			
			//判断是否选中户口类别
			var properties="${childBaseinfo.propertiess}";
			if(properties.indexOf("'1'")>-1){
				$th = $(".properties[value='1']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(properties.indexOf("'2'")>-1){
				$th = $(".properties[value='2']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(properties.indexOf("'3'")>-1){
				$th = $(".properties[value='3']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(properties.indexOf("'4'")>-1){
				$th = $(".properties[value='4']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			if(properties.indexOf("'5'")>-1){
				$th = $(".properties[value='5']");
				$th.attr("selected",true);
				$th.parent().addClass("selected");
			}
			});
	
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
</script>
  
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/child_baseinfo/documentStatistics">个案统计</a></li>
		<li><a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement">应种统计</a></li>
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
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="childBaseinfo" action="${ctx}/child_baseinfo/documentStatistics/sele/" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
				<%-- <div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">儿童出生日期：</span> 
						<input name="birthday" class="form-control"
							value="<fmt:formatDate value="${childBaseinfo.birthday}" pattern="yyyyMMdd"/>"
							
							 />
							
						<!-- <input onchange="changeInputBySelect(this);"
						onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								class="laydate-icon required form-control layer-date ">	 -->
					</div>
				</div> --%>
				<div class="form-group"  >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">儿童编码:</span>
						<form:input path="childcode" htmlEscape="false" maxlength="50" class="form-control" />
					</div>
				</div> 
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right ">儿童姓名:</span>
						<form:input path="childname" htmlEscape="false" maxlength="20" class="form-control" />
					</div>
				</div>
				<%-- <div class="form-group" >
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">儿童身份证:</span>
						<form:input path="cardcode" htmlEscape="false" maxlength="50" class="form-control" />
					</div>
				</div>  --%>
				
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">生日(起):</span> 
						<input name="birthbeginTime" value="<fmt:formatDate value="${childBaseinfo.birthbeginTime}" pattern="yyyyMMdd"/>" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">生日(止):</span> 
						<input name="birthendTime" value="<fmt:formatDate value="${childBaseinfo.birthendTime}" pattern="yyyyMMdd"/>" class="form-control" />
					</div>
				</div>	
					<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">儿童性别:</span>
							<form:select path="gender" class="form-control">
							<form:option value=" "> </form:option>
							<form:options items="${fns:getDictList('sex')}" itemLabel="label"
								itemValue="value" htmlEscape="false" />
							</form:select>
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right ">母亲姓名:</span>
						<form:input path="guardianname" htmlEscape="false" maxlength="20" class="form-control" />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right ">母亲电话:</span>
						<form:input path="guardianmobile" htmlEscape="false" maxlength="20" class="form-control" />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right ">父亲姓名:</span>
						<form:input path="father" htmlEscape="false" maxlength="20" class="form-control" />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right ">父亲电话:</span>
						<form:input path="fatherphone" htmlEscape="false" maxlength="20" class="form-control" />
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">建档日(起):</span> <input
							name="beginTime"
							value="<fmt:formatDate value="${childBaseinfo.beginTime}" pattern="yyyyMMdd"/>"
							class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">建档日(止):</span> <input
							name="endTime" value="<fmt:formatDate value="${childBaseinfo.endTime}" pattern="yyyyMMdd"/>"
							class="form-control" />
					</div>
					</div>
			<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">居住类别:</span>
						<select title="请选择居住类别" id="resides" name="resides" class="selectpicker show-tick form-control w164" multiple data-live-search="false">
                               <c:forEach items="${fns:getDictList('reside')}" var="reside">
                               <option value="${ reside.value}" class="reside" >${reside.label}</option>
                               </c:forEach>
                          </select> 
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">区域划分:</span>
						<select title="请选择区域划分" id="areas" name="areas" class="selectpicker show-tick form-control w164" multiple data-live-search="false">
                               <c:forEach items="${areas}" var="area">
                               		<c:choose>
									   	<c:when test="${childBaseinfo.areas.indexOf(area.code)>-1}">
									   		<option  selected="selected"value="${ area.code}" class="area" >${area.name}</option>
									   	</c:when>
									   	<c:otherwise>
									   		<option value="${ area.code}" class="area" >${area.name}</option>
									   	</c:otherwise>  
									</c:choose> 	 
                               </c:forEach>
                          </select> 
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">户口类别:</span>
							 <select title="请选择户口类别" id="propertiess" name="propertiess" class="selectpicker show-tick form-control w164" multiple data-live-search="false">
                               <c:forEach items="${fns:getDictList('properties')}" var="properties">
                               <option value="${ properties.value}" class="properties" >${properties.label}</option>
                               </c:forEach>
                          </select> 
					</div>
				</div>
				
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">在册类别:</span>
						 <select title="请选择在册类别" id="situations" name="situations" class="selectpicker show-tick form-control w164" multiple data-live-search="false">
                                   <c:forEach items="${fns:getDictList('situation')}" var="situation">
                                   <option value="${ situation.value}" class="situation" >${situation.label}</option>
                                   </c:forEach>
                          </select> 
					</div>
				</div>
				
					<div class="form-group">
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
						<th>儿童身份证号</th>
						<th>出生证号</th>
						<th>儿童姓名</th>
						<th>性别</th>
						<th>户籍类别</th>
						<th>出生日期</th>
						<th>出生医院</th>
						<th>出生体重(克)</th>
						<th>母亲姓名</th>
						<th>母亲身份证号</th>
						<th>母亲手机</th>
						<!-- <th>家庭住址</th> -->
						<th>户籍地址</th>
						<!-- <th>是否异常反应</th> -->
						<th>接种单位</th>
						<th>建档日期</th>
						<th>建档人</th>
						<%-- 	<shiro:hasPermission name="child:baseinfo:edit">
							<th>操作</th>
						</shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="childBaseinfo">
						<tr ondblclick="window.location.href='${ctx}/child_vaccinaterecord/childVaccinaterecord1/list1?childid=${childBaseinfo.childcode}'">
							<td>${childBaseinfo.childcode}</td>
							<td>${childBaseinfo.cardcode}</td>
							<td>${childBaseinfo.birthcode}</td>
							<td>${childBaseinfo.childname}</td>
							<td>${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}</td>
							<td>${fns:getDictLabel(childBaseinfo.reside, 'reside', '')}</td>
							<td><fmt:formatDate value="${childBaseinfo.birthday}"
									pattern="yyyy-MM-dd" /></td>
							<td>${childBaseinfo.birthhostipal}</td>
							<td>${childBaseinfo.birthweight}</td>
							<td>${childBaseinfo.guardianname}</td>
							<td>${childBaseinfo.guardianidentificationnumber}</td>
							<td>${childBaseinfo.guardianmobile}</td>
							<%-- <td>${childBaseinfo.guardianrelation}</td> --%>
							<%-- <td> ${fns:getDictLabel(childBaseinfo.guardianrelation, 'relation', '')}</td> --%>
							<%-- <td>${childBaseinfo.homeaddress}</td> --%>
							<td>${childBaseinfo.registryaddress}</td>
							<%-- <td>${fns:getDictLabel(childBaseinfo.paradoxicalreaction, 'FN', '')}
							</td> --%>
							<td>${childBaseinfo.officeinfo}</td>
							<td><fmt:formatDate value="${childBaseinfo.createDate}"
									pattern="yyyy-MM-dd" /></td>
							<td>${childBaseinfo.creater}</td>
							<%-- <shiro:hasPermission name="child:baseinfo:edit">
								<td><a
									href="${ctx}/child_baseinfo/documentStatistics/delete?id=${childBaseinfo.id}"
									onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a></td>
							</shiro:hasPermission> --%>
						</tr>
					</c:forEach>
				</tbody>
			</table>
 				<div class="page">${page}</div> 
		</div>
	</div>
</body>
</html>