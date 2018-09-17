<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>接种明细</title>
	<style>
	@font-face
	{
	    font-family:'weiruanyahei';
	    src:url('${ctxStatic}/fonts/yahei.eot');
	    src:local('☺'),
	        url('${ctxStatic}/fonts/yahei.eot?#iefix') format('embedded-opentype'),
	        font-weight:normal;
	    font-style:normal;
	}
	</style>
</head>
<body>
	<div class="ibox">
		<h1 style="text-align:center;"><strong>每日接种明细（${date}）</strong></h1>
		<div>
			<c:forEach items="${page}" var="i">
				<table id="contentTable" border="1" style="border:solid 1px black;border-collapse:collapse;text-align:center;font-size:22px;margin: 0 auto 30px auto; width:860px;">
					<thead>
						<tr style="font-weight: bold;">
							<th style="width:8%">序     号</th>
							<th style="width:14%">编     号</th>
							<th style="width:16%">姓     名</th>
							<th style="width:8%">性     别</th>
							<th style="width:8%">针次</th>
							<th style="width:10%">发票号</th>
							<th style="width:16%">疫苗厂家</th>
							<th style="width:16%">接种医生</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${i}" var="bsRabiesProduct" varStatus="status">
						<tr>
							<td>
								${status.index + 1}
							</td>
							<td>
								${bsRabiesProduct.kin.rabiescode}
							</td>
							<td>
								${bsRabiesProduct.kin.username}
							</td>
							<td>
								${fns:getDictLabel(bsRabiesProduct.kin.sex, 'sex', '')}
							</td>
							<td>
								${bsRabiesProduct.vaccinum}
							</td>
							<td></td>
							<td>
								${bsRabiesProduct.manufacturer}
							</td>
							<td>
								${bsRabiesProduct.createByName}
							</td>
						</tr>
					</c:forEach>
						<tr>
							<td colspan="4">总数合计：</td>
							<td colspan="4">${i.size()}</td>
						</tr>
					</tbody>
				</table>
			</c:forEach>
			<c:if test="${not empty socketlistUp}">
				<table id="contentTable"  border="1" style="border:solid 1px black;border-collapse:collapse;text-align:center;font-size:22px;margin: 50px auto 0 auto; width:860px;">
					<caption class="text-center" style="font-weight: bold; font-size: 28px; margin-bottom:20px;">狂犬免疫球蛋白</caption>
					<thead>
						<tr style="font-weight: bold;">
							<th style="width:8%">序     号</th>
							<th style="width:14%">编     号</th>
							<th style="width:16%">姓     名</th>
							<th style="width:8%">性     别</th>
							<th style="width:8%">针数</th>
							<th style="width:10%">发票号</th>
							<th style="width:16%">疫苗厂家</th>
							<th style="width:16%">接种医生</th>
						</tr>
					</thead>
					<tbody>
					<c:forEach items="${socketlistUp}" var="bsRabiesProduct" varStatus="status">
						<tr>
							<td>
								${status.index + 1}
							</td>
							<td>
								${bsRabiesProduct.kin.rabiescode}
							</td>
							<td>
								${bsRabiesProduct.kin.username}
							</td>
							<td>
								${fns:getDictLabel(bsRabiesProduct.kin.sex, 'sex', '')}
							</td>
							<td>
								${bsRabiesProduct.vaccinum}
							</td>
							<td></td>
							<td>
								${bsRabiesProduct.manufacturer}
							</td>
							<td>
								${bsRabiesProduct.createByName}
							</td>
						</tr>
					</c:forEach>
					<c:set var="sum" value="0" />  
					<c:forEach items="${socketlistUp}" var="bsRabiesProduct">
						<c:set var="sum" value="${sum + bsRabiesProduct.vaccinum }"></c:set>
					</c:forEach>
						<tr>
							<td colspan="4">总数合计：</td>
							<td colspan="4">
								${sum}
							</td>
						</tr>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</body>
<script>
	var idx = layer.load();
	setTimeout(function() {
		layer.close(idx);
	 	window.print();  
	 	setTimeout("top.opener=null;window.close()",100);
	}, 1000);
</script>
</html>