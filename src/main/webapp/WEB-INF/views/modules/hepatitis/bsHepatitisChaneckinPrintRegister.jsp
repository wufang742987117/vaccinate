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
@page { size: landscape; }

*{margin: 0; padding: 0; font-size: 14px; text-align: center; }

.wrap{width:1100px;margin:50px 20px;}

.title{font-size: 2em; font-weight: bold; }

.sub_title{font-size: 1.5em; font-weight: bold; }

.table_0{width:100%;margin-top:20px;}

.table_1{width:10%;float: left;border: 1px solid #000 ;border-collapse:collapse;}

.table_0 th{text-align: left;}

.table_0 td{text-align: left;text-decoration:underline;}

th,td{padding: 4px 8px;}

.bottom_num{width:100%;font-size: 1.4em;margin: 30px auto 0 auto;text-align: right;}

</style>
<script type="text/javascript" src='${ctxStatic}/jquery/jquery-1.9.1.min.js'></script>
<!--[if lt IE 9]>
<script src="${ctxStatic}/js/flashcanvas.js"></script>
<![endif]-->
<script src="${ctxStatic}/js/jSignature.min.js"></script>
<script type="text/javascript">
	$(function(){
		var datapair;
		var str;
		var ig;
		$(".img").each(function(i,t){
			str = t.innerText;
			ig = new Image();
	        ig.src = "data:" + "image/png;base64" + "," + str;
	        ig.width = 104;
	        ig.height = 38;
	        $(this).html(ig);
		});
	});
</script>
</head>
<body>
	<div class="wrap" id="baseinfo">
		<p class="title">${officeName}</p>
		<p class="sub_title">${vaccinatename }接种登记表</p>
		<table class="table_0">
			<tr>
				<th>姓名:</th><td>${bsHepatitisBcheckin.username}</td>
				<th>性别:</th><td>${fns:getDictLabel(bsHepatitisBcheckin.sex, 'sex', '')}</td>
				<th>年龄:</th><td>${bsHepatitisBcheckin.age}岁</td>
				<th>联系电话:</th><td>${bsHepatitisBcheckin.linkPhone}</td>
			</tr>
			<tr>
				<th>详细地址:</th><td colspan="7">${bsHepatitisBcheckin.homeAddress}</td>
			</tr>
			<tr>
				<th>疫苗名称:</th><td>${bsHepatitisBcheckin.vaccineName}</td>
				<th>规格:</th><td>${bsHepatitisBcheckin.standard}</td>
				<th>批号:</th><td>${bsHepatitisBcheckin.batch}</td>
				<th>生产厂家:</th><td>${bsHepatitisBcheckin.manufacturer}</td>
			</tr>
			<tr>
				<c:if test="${not empty bsHepatitisBcheckin.weight }">
					<th>受种者体重:</th><td>${bsHepatitisBcheckin.weight}kg</td>
				</c:if>
				<c:if test="${empty bsHepatitisBcheckin.weight }">
					<th>受种者体重:</th><td style="text-decoration:none;">__________kg</td>
				</c:if>
				<th>接种剂量:</th><td>${bsNum}支&nbsp;</td>
				<th>医生签名:</th><td>${docaterName}</td>	
			</tr>
		</table>
	
		<div style="height:200px;margin-top:30px;">
			<table class="table_1" border="1">
				<tbody>
					<tr>
						<th>疫苗接种针次</th>
					</tr>
					<tr>
						<th>程序接种时间</th>
					</tr>
					<tr>
						<th>实际接种时间</th>
					</tr>
					<tr>
						<th>接种医生签字</th>
					</tr>
					<tr style="height: 52px;">
						<th style="padding: 0 !important;">受种者或<br>监护人签字</th>
					</tr>
					<tr>
						<th>发票编号</th>
					</tr>
				</tbody>
			</table>
			<c:forEach items="${bsList }" var="bs">
				<table class="table_1" border="1">
					<tr>
						<th>${fns:getDictLabel(bs.vaccineNum, 'pin', '')}</th>
					</tr>
					<tr>
						<td><fmt:formatDate value="${bs.vaccineDate}" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr>
						<c:if test="${not empty bs.realDate && bs.status == 1}">
							<td><fmt:formatDate value="${bs.realDate}" pattern="yyyy-MM-dd" />&nbsp;</td>
						</c:if>
						<c:if test="${empty bs.realDate || bs.status != 1}">
							<td>&nbsp;</td>
						</c:if>
					</tr>
					<tr>
						<c:if test="${not empty bs.realDate && bs.status == 1}">
							<td>${bs.createName}&nbsp;</td>
						</c:if>
						<c:if test="${empty bs.realDate || bs.status != 1}">
							<td>&nbsp;</td>
						</c:if>
					</tr>
					<tr style="height: 52px;">
						<c:if test="${not empty bs.signatureList && bs.signature == 1 && bs.stype == 0}">
							<td class="img" style="padding:0;">${bs.signatureList}</td>
						</c:if>
						<c:if test="${not empty bs.signatureList && bs.signature == 1 && bs.stype != 0}">
							<td style="padding:0;"><img src="${ctx}/hepatitis/bsHepatitisBcheckin/signatureimg?id=${bs.id}" width="104px" height="38px"/></td>
						</c:if>
						<c:if test="${bs.signature == 0 || (bs.signature == 1 && empty bs.signatureList)  }">
							<td style="padding:0;">&nbsp;</td>
						</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</c:forEach>	
		</div>
		<div class="bottom_num">编号：${bsHepatitisBcheckin.hepaBcode}</div>
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