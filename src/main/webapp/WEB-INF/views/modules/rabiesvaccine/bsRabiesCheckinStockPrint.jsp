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
		<h1 style="text-align:center;"><strong>每日库存明细（${date}）</strong></h1>
		<div >
			<table id="contentTable" border="1" style="border:solid 1px black;border-collapse:collapse;text-align:center;font-size:22px;margin: 0 auto 30px auto; width:860px;">
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>疫苗批次</th>
						<th>疫苗厂家</th>
						<th>已付款库存</th>
						<th>可用库存</th>
						<th>实际库存</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${bsList}" var="i">
					<c:forEach items="${i}" var="bsRabiesCheckinStock">
						<tr>
							<c:if test="${bsRabiesCheckinStock.leng != 0}">
								<td rowspan="${bsRabiesCheckinStock.leng}">
									${bsRabiesCheckinStock.vaccinatename}
								</td>
							</c:if>
							<td>
								${bsRabiesCheckinStock.batchnum}
							</td>
							<td>
								${bsRabiesCheckinStock.manufacturer}
							</td>
							<td>
								${bsRabiesCheckinStock.storenum2}
							</td>
							<td>
								${bsRabiesCheckinStock.storenum - bsRabiesCheckinStock.storenum2}
							</td>
							<td>
								${bsRabiesCheckinStock.storenum}
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
<script>
	var idx = layer.load();
	setTimeout(function() {
		layer.close(idx);
	 	window.print();  
	 	setTimeout("top.opener=null;window.close()",100);
	}, 500);
</script>
</html>