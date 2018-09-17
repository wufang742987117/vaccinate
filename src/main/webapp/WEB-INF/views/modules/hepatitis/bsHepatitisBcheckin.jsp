<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个案记录</title>
	<meta name="decorator" content="hbdefault"/>
	<script type="text/javascript">
		function page(n,s){
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
					$("#searchForm").attr("action","${ctx}/hepatitis/bsHepatitisBcheckin/export");
					$("#searchForm").submit();
					layer.close(index);
					$("#searchForm").attr("action",temp);
				}, function(){
					layer.close();
				});
			});
		});
	
	function formDelete(id) {
		$.ajax({
			type : "POST",
			url : "${ctx}/hepatitis/bsHepatitisBcheckin/delete?id=" + id,
			success : function(data) {
	            setTimeout(funB, 100);
			}
		});
	}
	//刷新当前页面
	function funB() {
		window.location.reload();
	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a
			href="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckin">个案统计</a></li>
		<li><a
			href="${ctx}/hepatitisnum/bsHepatitisBNum/list">接种明细</a></li>
		<li><a
			href="${ctx}/hepatitisnum/bsHepatitisBNum/bsHepatitisBStockList">统计记录</a></li>
		<li><a
			href="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckinStock">库存明细</a></li>
		<div class="row pull-right">
			<a href="${ctx}/hepatitis/bsHepatitisBcheckin" class="btn btn-success mr15" ><span class="glyphicon glyphicon-arrow-left"></span>返回上一页</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsHepatitisBcheckin" action="${ctx}/hepatitis/bsHepatitisBcheckin/bsCheckin" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class = "col-xs-11" style="padding-left:0;">
					<div class="form-group" >
						<div class="input-group" >
							<span class="input-group-addon gray-bg text-right">建档日期(起)：</span>
							<input name="beginTime" value="<fmt:formatDate value="${bsHepatitisBcheckin.beginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								class="laydate-icon form-control layer-date" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">建档日期(止)：</span>
							<input name="endTime" 
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})"
								class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${bsHepatitisBcheckin.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"   />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">疫苗类型：</span>
							<form:select path="vaccType" class="form-control" >
								<form:option value="0">全部</form:option>
								<form:options items="${vaccInfoList}" itemLabel="vaccName" itemValue="id" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">姓      名：</span>
								<input name="username" class="form-control"/>
						</div>
					</div>
					<div class="form-group">
						<input id="btnSubmit" class="btn btn-bshepb btn-sm" type="submit" value="查询" />
						<input id="btnExport" class="btn btn-bshepb btn-sm ml15" type="button" value="导出报表"/>
					</div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>序     号</th>
						<th>档案编码</th>
						<th>疫苗类型</th>
						<th>姓     名</th>
						<th>监护人姓名</th>
						<th>身份证号</th>
						<th>性     别</th>
						<th>出生日期</th>
						<th>年    龄</th>
						<th>联系电话</th>
						<th>建档日期</th>
						<th>操  纵</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsHepatitisBcheckin" varStatus="status">
					<tr ondblclick="window.location.href='${ctx}/hepatitis/bsHepatitisBcheckin/form?id=${bsHepatitisBcheckin.id}'">
						<td>
							${status.index + 1}
						</td>
						<td>
							${bsHepatitisBcheckin.hepaBcode}
						</td>
						<td>
							${bsHepatitisBcheckin.vaccType}
						</td>
						<td>
							${bsHepatitisBcheckin.username}
						</td>
						<td>
							${bsHepatitisBcheckin.realname}
						</td>
						<td>
							${bsHepatitisBcheckin.idcardNo}
						</td>
						<td>
							${fns:getDictLabel(bsHepatitisBcheckin.sex, 'sex', '')}
						</td>
						<td>
							<fmt:formatDate value="${bsHepatitisBcheckin.birthday}" pattern="yyyy-MM-dd" />
						</td>
						<td>
							${bsHepatitisBcheckin.age}
						</td>
						<td>
							${bsHepatitisBcheckin.linkPhone}
						</td>
						<td>
							<fmt:formatDate value="${bsHepatitisBcheckin.createDate}" pattern="yyyy-MM-dd" />
						</td>
						<td>
						<a href="javascript:formDelete('${bsHepatitisBcheckin.id}')" class="btn btn-danger btn-xs" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
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