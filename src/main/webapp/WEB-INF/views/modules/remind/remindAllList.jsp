<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>儿童接种提醒管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic}/js/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
	<script src="${ctxStatic}/js/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
	<script type="text/javascript">
		var rcvs = JSON.parse('${rcvs}');
		$(document).ready(function() {
			$(".lastVacc").each(function(){
				var childid = $(this).attr("data-childid");
				var html = "";
				for(var i = 0; i < rcvs.length; i ++){
					if(rcvs[i].childid == childid){
						html += rcvs[i].vaccName + "&nbsp;&nbsp;" + rcvs[i].manufacturer + "&nbsp;&nbsp;" + rcvs[i].batch + "<br>";
					}
				}
				$(this).html(html);
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="childBaseinfo" action="${ctx}/remind/vacChildRemind/remindAllList" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
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
				
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">生日(起):</span> <input
							name="birthbeginTime"
							value="<fmt:formatDate value="${childBaseinfo.birthbeginTime}" pattern="yyyyMMdd"/>"
							class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">生日(止):</span> <input
							name="birthendTime" value="<fmt:formatDate value="${childBaseinfo.birthendTime}" pattern="yyyyMMdd"/>"
							class="form-control" />
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
					<form:checkbox path="hasRemindOption" label="包含已预约" value="1" cssClass="checkbox-inline i-checks"/>
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>
				</div>
				<div class="form-group">
					<a class="btn btn-success w100" href="${ctx}/remind/vacChildRemind/remindAllForm">预约</a>
				</div>
				<div class="form-group">
					<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>儿童编码</th>
						<th>儿童姓名</th>
						<th>性别</th>
						<th>户籍类别</th>
						<th>出生日期</th>
						<th>母亲姓名</th>
						<th>母亲身份证号</th>
						<th>母亲手机</th>
						<th>最近接种</th>
						<th>预约状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="info">
					<tr>
						<td>${info.childcode}</td>
						<td>${info.childname}</td>
						<td>${fns:getDictLabel(info.gender, 'sex', '')}</td>
						<td>${fns:getDictLabel(info.reside, 'reside', '')}</td>
						<td><fmt:formatDate value="${info.birthday}" pattern="yyyy-MM-dd" />&emsp;<strong>${info.mouage}</strong></td>
						<td>${info.guardianname}</td>
						<td>${info.guardianidentificationnumber}</td>
						<td>${info.guardianmobile}</td>
						<td class="lastVacc text-left" data-childid=${info.id}></td>
						<td>${empty info.hasRemind ?"未预约":"已预约" }</td>
						<td>
							<a class="btn btn-xs btn-success" href="${ctx}/remind/vacChildRemind/remindAllForm?code=${info.childcode}">预约</a>
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