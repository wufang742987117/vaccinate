<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>列表</title>
<meta name="decorator" content="hbdefault"/>
<c:if test="${not empty arg }">
	<script type="text/javascript">
		$(function() {
			parent.closeFormArg("${arg}");
		});
	</script>
</c:if>
<c:if test="${not empty id }">
	<script type="text/javascript">
		$(function() {
			parent.closeFormId("${id}","${search}");
		});
	</script>
</c:if>
<script type="text/javascript">
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
			<form:form id="searchForm" modelAttribute="bsHepatitisBcheckin" action="${ctx}/hepatitis/bsHepatitisBcheckin/namelist" method="post" class="form-inline">
				<form:hidden path="searchName" />
			</form:form>
			<sys:message content="${message}" />
		</div>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>档案编号</th>
					<th>疫苗类型</th>
					<th>姓名</th>
					<th>性别</th>
					<th>出生日期</th>
					<th>联系电话</th>
					<th>创建时间</th>
					<th>已完成/所有针次</th>
				</tr>
			</thead>
			<tbody>
				 <c:forEach items="${namelist}" var="bsHepatitisBcheckin">
					<tr ondblclick="window.location.href='${ctx}/hepatitis/bsHepatitisBcheckin/namelist?id=${bsHepatitisBcheckin.id}'">
						<td>${bsHepatitisBcheckin.hepaBcode}</td>
						<td>${bsHepatitisBcheckin.vaccType}</td>
						<td>${bsHepatitisBcheckin.username}</td>
						<td>${fns:getDictLabel(bsHepatitisBcheckin.sex, 'sex', '')}</td>
						<td><fmt:formatDate value="${bsHepatitisBcheckin.birthday}" pattern="yyyy-MM-dd" /></td>	
						<td>${bsHepatitisBcheckin.linkPhone}</td>
						<td><fmt:formatDate value="${bsHepatitisBcheckin.createDate}" pattern="yyyy-MM-dd" /></td>
						<td>${bsHepatitisBcheckin.finishTimes}/${bsHepatitisBcheckin.totalTimes}</td>
					</tr>    
				</c:forEach> 
				
				<c:forEach items="${bsRabiesCheckins}" var="bsRabiesCheckin">
					<tr ondblclick="window.location.href='${ctx}/hepatitis/bsHepatitisBcheckin/namelist?id=${bsRabiesCheckin.id}'">
						<td>${bsRabiesCheckin.rabiescode}</td>
						<td>${bsRabiesCheckin.vaccinatename}</td>
						<td>${bsRabiesCheckin.username}</td>
						<td>${fns:getDictLabel(bsRabiesCheckin.sex, 'sex', '')}</td>
						<td><fmt:formatDate value="${bsRabiesCheckin.birthday}" pattern="yyyy-MM-dd" /></td>	
						<td>${bsRabiesCheckin.linkphone}</td>
						<td><fmt:formatDate value="${bsRabiesCheckin.createDate}" pattern="yyyy-MM-dd" /></td>
						<td>${bsRabiesCheckin.finishpin }/${bsRabiesCheckin.undonepin }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>