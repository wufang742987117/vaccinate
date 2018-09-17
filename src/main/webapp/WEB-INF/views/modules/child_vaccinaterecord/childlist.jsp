<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>儿童列表</title>
<meta name="decorator" content="default" />
<style>
		table tr:hover .egg{
		  font-size:20px;
		}
		table tr:hover td{
		  color: red; 
		  background: rgb(255, 213, 177); 
		  font-weight: bolder;
		}
</style>
<c:if test="${ 1.eq(arg) }">
	<script type="text/javascript">
		$(function() {
			parent.closeForm1('${code1}');
		});
	</script>
</c:if>
<c:if test="${not empty arg }">
	<script type="text/javascript">
		$(function() {
			parent.closeForm0();
		});
	</script>
</c:if>
<script type="text/javascript">
		function submitYouFrom() {
			var childcode = $("#childcode").val();
			var birthday = $("#birthday").val();
			parent.closeForm2(childcode,birthday);
		}
		function db(code){
// 		parent.closeForm3(id);
			parent.closeForm1(code);
		};
		
	
	
</script>
</head>
<body>
	<div class="ibox">
		<%-- <div class="ibox-content">
			 <form:form id="searchForm" modelAttribute="childBaseinfo"
				action="${ctx}/child_vaccinaterecord/childVaccinaterecord1/list1"
				method="post" class="form-inline">
				<div class="form-group" style="display: none">
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">儿童编码：</span>
						 	<form:input path="childcode" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">出生日期：</span>
						<form:input path="birthday" readonly=""
							onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
							class="laydate-icon form-control layer-date" value="" />
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary"
						onclick="submitYouFrom()" value="查询" />
				</div>
			</form:form> 
			

		</div> --%>
		<sys:message content="${message}" />
		<table id="contentTable"
			class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>儿童编码</th>
					<th>儿童姓名</th>
					<th>性别</th>
					<th>出生日期</th>
					<th>母亲姓名</th>
					<th>儿童身份证</th>
					<th>出生证号</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${childlist}" var="bsChildBaseinfo">
					<tr ondblclick="db('${bsChildBaseinfo.childcode}')">
						<td>${bsChildBaseinfo.childcode}</td>
						<td class="egg">${bsChildBaseinfo.childname}</td>
						<td class="egg">${fns:getDictLabel(bsChildBaseinfo.gender, 'sex', '')}</td>
						<td class="egg"><fmt:formatDate value="${bsChildBaseinfo.birthday}"
								pattern="yyyy-MM-dd" /></td>
						<td>${bsChildBaseinfo.guardianname}</td>
						<td>${bsChildBaseinfo.cardcode}</td>
						<td>${bsChildBaseinfo.birthcode}</td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>