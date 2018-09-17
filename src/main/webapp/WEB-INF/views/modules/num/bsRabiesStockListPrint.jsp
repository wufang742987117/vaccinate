<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html id="all">

<head>
<meta charset="UTF-8">
<title></title>
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
*{margin: 0; padding: 0; font-size: 14px; text-align: center; }

.wrap{width: 850px; margin: 50px 0; }

.title{font-size: 1.5em; font-weight: bold; }

.table-title{
	font-size: 1.2em;
	margin: 20px 0 5px 0;
}

.sub-title{
	margin-top: 10px;
}

.sub-title span{
	margin:0 10px;
}

.data-table{
	width: 600px;	
	margin: 0 auto;
	border: 1px solid #000 ;
	border-collapse:collapse;
}

.data-table td,.data-table th{
	padding:4px;
}

.data-table th{
	background-color: #eee;
	font-weight: 600;
}

.strong{
	font-weight: bold;
}
</style>
</head>
<body style="height:840px">
	<div class="wrap">
		<p class="title">
			统计记录（
			<span>
				<fmt:formatDate value="${bsRabiesNum.beginTime}" pattern="yyyy-MM-dd" />
			</span>
			~ 
			<span>
				<fmt:formatDate value="${bsRabiesNum.endTime}" pattern="yyyy-MM-dd" />
			</span>
			）
		</p>
		<div class="sub-title">
			<span><strong>接种医生：</strong> ${createByName}</span>
			<span><strong>新建卡：</strong> ${countNumOne}</span>
			<span><strong>接种数：</strong> ${countNumTwo}</span>
		</div>
		<p class="table-title">疫苗当前库存</p>
		<table class="data-table" border='1' >
			<thead>
				<tr>
					<th>疫苗名称</th>
					<th>疫苗厂家</th>
					<th>疫苗批号</th>
					<th>当前库存</th>
					<th>付款未种支数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${returnlist0}" var="i">
					<c:forEach items="${i}" var="j">
						<tr>
							<c:if test="${j.leng != 0}">
								<td rowspan="${j.leng}">
									${j.vaccname}
								</td>
							</c:if>
							<c:if test="${j.manufacturer != '其他'}">
								<td>
									${j.manufacturer}
								</td>
								<td>
									${j.batchnum}
								</td>
								<td>
									${j.storenum}
								</td>
								<td>
									${j.storenum2}
								</td>
							</c:if>
						</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
		
		<p class="table-title">疫苗消耗表</p>
		<table class="data-table" border='1'>
			<thead>
				<tr>
					<th width="40%">疫苗名称</th>
					<th>疫苗厂家</th>
					<th>小计</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${returnlistOne}" var="i">
					<c:forEach items="${i}" var="j">
						<tr>
							<c:if test="${j.leng != 0}">
								<td rowspan="${j.leng}">
									${j.vaccname}
								</td>
							</c:if>
							<td>
								${j.manufacturer}
							</td>
							<td>
								${j.vcount}
							</td>
						</tr>	
					</c:forEach>
				</c:forEach>
				<tr class="strong">
					<td>
						合计
					</td>
					<td>
						
					</td>
					<td>
						${socketlistCount }
					</td>
				</tr>
			</tbody>
		</table>
		
		<p class="table-title">接种统计表</p>
		<table class="data-table" border='1'>
			<tbody>
				<c:forEach items="${requestScope.str }" var="i" >
					<tr>
						<c:forEach items="${i }" var="j">
							<td>${j}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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