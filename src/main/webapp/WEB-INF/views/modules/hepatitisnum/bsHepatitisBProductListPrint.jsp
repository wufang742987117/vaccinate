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
							<th style="width:13%">编     号</th>
							<th style="width:12%">姓     名</th>
							<th style="width:7%">性     别</th>
							<th>年    龄</th>
							<th style="width:7%">针次</th>
							<th style="width:10%">发票号</th>
							<th style="width:11%">疫苗类型</th>
							<th style="width:13%">疫苗厂家</th>
							<th style="width:12%">接种医生</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${i}" var="bsHepatitisBProduct" varStatus="status">
						<tr>
							<td>
								${status.index + 1}
							</td>
							<td>
								${bsHepatitisBProduct.kin.hepaBcode}
							</td>
							<td>
								${bsHepatitisBProduct.kin.username}
							</td>
							<td>
								 ${fns:getDictLabel(bsHepatitisBProduct.kin.sex, 'sex', '')} 
							</td>
							<td>
								${bsHepatitisBProduct.kin.age}
							</td>
							<td>
								${bsHepatitisBProduct.vaccineNum}
							</td>
							<td></td>
							<td>
								${bsHepatitisBProduct.vaccType}
							</td>
							<td>
								${bsHepatitisBProduct.manufacturer}
							</td>
							<td>
								${bsHepatitisBProduct.createName}
							</td>
						</tr>
						</c:forEach>
						<tr>
							<td colspan="4">总数合计：</td>
							<td colspan="6">${i.size()}</td>
						</tr>
					</tbody>
				</table>
			</c:forEach>
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