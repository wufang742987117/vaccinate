<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息量统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function autoRowSpan(tb,row,col){
	        var lastValue="",value="",pos=1;  
	        for(var i=row;i<tb.rows.length;i++){
	            value = tb.rows[i].cells[col].innerText;  
	            if(lastValue == value){
	                tb.rows[i].deleteCell(col); 
	                tb.rows[i-pos].cells[col].rowSpan = tb.rows[i-pos].cells[col].rowSpan+1;
	                pos++;
	            }else{
	                lastValue = value;
	                pos=1;
	            }
	        }
	    }
		$(document).ready(function(){
			autoRowSpan(contentTable,0,0);
	        $("td,th").css({"text-align":"center","vertical-align":"middle"});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/cms/stats/article">信息量统计</a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/stats/article" method="post" class="form-inline">
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">归属栏目：</span>
						<sys:treeselect id="category" name="categoryId" value="${paramMap.id}" labelName="categoryName" labelValue="${paramMap.name}"
							title="栏目" url="/cms/category/treeData" module="article" cssClass="form-control" allowClear="true"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">归属机构：</span>
						<sys:treeselect id="office" name="officeId" value="${paramMap.office.id}" labelName="officeName" labelValue="${paramMap.office.name}"
							title="机构" url="/sys/office/treeData" cssClass="form-control" allowClear="true"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">开始日期：</span>
						<input  name="beginDate" id="beginDate" readonly="" value="${paramMap.beginDate}" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" placeholder="开始日期" class="laydate-icon form-control layer-date">
						<span class="input-group-addon gray-bg text-right">-</span>
						<input id="endDate" name="endDate" readonly="" value="${paramMap.endDate}" onclick="laydate({istime: true, format: 'YYYY-MM-DD'})" class="laydate-icon form-control layer-date" placeholder="结束日期">
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th>父级栏目</th><th>栏目名称</th><th>信息量</th><th>点击量</th><th>最后更新时间</th><th>归属机构</th>
				<tbody>
				<c:forEach items="${list}" var="stats">
					<tr>
						<td><a href="javascript:" onclick="$('#categoryId').val('${stats.parent.id}');$('#categoryName').val('${stats.parent.name}');$('#searchForm').submit();return false;">${stats.parent.name}</a></td>
						<td><a href="javascript:" onclick="$('#categoryId').val('${stats.id}');$('#categoryName').val('${stats.name}');$('#searchForm').submit();return false;">${stats.name}</a></td>
						<td>${stats.cnt}</td>
						<td>${stats.hits}</td>
						<td><fmt:formatDate value="${stats.updateDate}" type="both"/></td>
						<td><a href="javascript:" onclick="$('#officeId').val('${stats.office.id}');$('#officeName').val('${stats.office.name}');$('#searchForm').submit();return false;">${stats.office.name}</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>