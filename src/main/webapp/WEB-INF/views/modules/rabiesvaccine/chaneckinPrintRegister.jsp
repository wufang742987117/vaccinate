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
	        $(t).html(ig);
		});
		
		var datapairm;
		var strm;
		var im;
		$(".image").each(function(i,t){
			strm = t.innerText;
			im = new Image();
	        im.src = "data:" + "image/png;base64" + "," + strm;
	        im.width = 104;
	        im.height = 38;
	        $(t).html(im);
		});
	})
	
</script>
</head>
<body>
	<div class="wrap" id="baseinfo">
		<p class="title">${officeName}</p>
		<p class="sub_title">人用狂犬病疫苗、狂犬病人免疫球蛋白接种登记表</p>
		<table class="table_0">
			<tr>
				<th>姓名:</th><td>${bsRabiesCheckin.username}</td>
				<th>性别:</th><td>${fns:getDictLabel(bsRabiesCheckin.sex, 'sex', '')}</td>
				<th>年龄:</th><td>${bsRabiesCheckin.age}岁</td>
				<th>联系电话:</th><td>${bsRabiesCheckin.linkphone}</td>
			</tr>
			<tr>
				<th>详细地址:</th><td colspan="7">${bsRabiesCheckin.homeaddress}</td>
			</tr>
			<tr>
				<th>受伤部位:</th><td colspan="7">${bsRabiesCheckin.bitepart}</td>
			</tr>
			<tr>
				<th>动物名称:</th><td>${fns:getDictLabel(bsRabiesCheckin.animal, 'animal', '')}</td>
				<th>受伤时间:</th><td><fmt:formatDate value="${bsRabiesCheckin.bitedate}" pattern="yyyy-MM-dd" /></td>
				<th>受伤方式:</th><td>${fns:getDictLabel(bsRabiesCheckin.bitetype, 'biteType', '')}</td>
			</tr>
			
			<tr>
				<th>伤口处理地点:</th><td>${fns:getDictLabel(bsRabiesCheckin.dealaddress, 'disposal_sites', '')}</td>
				<th>伤口处理时间:</th><td><fmt:formatDate value="${bsRabiesCheckin.dealdate}" pattern="yyyy-MM-dd" /></td>
				<th>暴露级别:</th><td>${fns:getDictLabel(bsRabiesCheckin.exposelevel, 'rank', '')}</td>
				<th>免疫前后:</th><td colspan="5">${fns:getDictLabel(bsRabiesCheckin.expose, 'expose', '')}</td>
			</tr>
			<tr>
				<th>疫苗名称:</th><td>${bsRabiesCheckin.vaccinatename}</td>
				<th>规格:</th><td>${bsRabiesCheckin.standard}</td>
				<th>批号:</th><td>${bsRabiesCheckin.batchnum}</td>
				<th>生产厂家:</th><td>${bsRabiesCheckin.manufacturer}</td>
			</tr>
			<tr>
				<th>是否接种狂犬<br>病人免疫球蛋白:</th><td>${fns:getDictLabel(bsRabiesCheckin.isinoculate, '01', '')}</td>
				<c:if test="${not empty bsRabiesCheckin.weight }">
					<th>受种者体重:</th><td>${bsRabiesCheckin.weight}kg</td>
				</c:if>
				<c:if test="${empty bsRabiesCheckin.weight }">
					<th>受种者体重:</th><td style="text-decoration:none;">__________kg</td>
				</c:if>
				<c:if test="${bsRabiesCheckin.isinoculate == 1 }">
					<th>接种剂量:</th><td>${bsNotNum}支&nbsp;(免疫蛋白:${bsNum}支)</td>
				</c:if>
				<c:if test="${bsRabiesCheckin.isinoculate == 0 }">
					<th>接种剂量:</th><td>${bsNotNum}支&nbsp;(免疫蛋白:0支)</td>
				</c:if>
				
			</tr>
			<tr>
				<c:if test="${not empty bsRabiesCheckin.vaccinatenameNo }">
					<th>名称:</th><td>${bsRabiesCheckin.vaccinatenameNo}</td>
					<th>生产厂家:</th><td>${bsRabiesCheckin.manufacturerNo}</td>
					<th>规格:</th><td>${bsRabiesCheckin.standardNo}</td>
					<th>批号:</th><td>${bsRabiesCheckin.batchnumNo}</td>
				</c:if>
			    <c:if test="${empty bsRabiesCheckin.vaccinatenameNo }">
					<th>名称:</th><td style="text-decoration:none;">____________</td>
					<th>生产厂家:</th><td style="text-decoration:none;">____________</td>
					<th>规格:</th><td style="text-decoration:none;">____________</td>
					<th>批号:</th><td style="text-decoration:none;">____________</td>
				</c:if>
			</tr>
			<tr>
				<th>抗体检测时间:</th><td style="text-decoration:none;">____________</td>
				<th>抗体检测结果:</th><td colspan="3" style="text-decoration:none;">_______________________________________________</td>
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
			<c:if test="${not empty bsRabiesNumNotList }">
				<table class="table_1" border="1">
					<tr>
						<th>${fns:getDictLabel(bsRabiesNumNotList.vaccinum, 'pin', '')}</th>
					</tr>
					<tr>
						<td><fmt:formatDate value="${bsRabiesNumNotList.vaccidate}" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr>
						<c:if test="${not empty bsRabiesNumNotList.realdate && bsRabiesNumNotList.status == 1}">
							<td><fmt:formatDate value="${bsRabiesNumNotList.realdate}" pattern="yyyy-MM-dd" /></td>
						</c:if>
						<c:if test="${empty bsRabiesNumNotList.realdate || bsRabiesNumNotList.status != 1}">
							<td>&nbsp;</td>
						</c:if>
					</tr>
					<tr>
						<c:if test="${not empty bsRabiesNumNotList.realdate && bsRabiesNumNotList.status == 1}">
							<td>${bsRabiesNumNotList.createByName}</td>
						</c:if>
						<c:if test="${empty bsRabiesNumNotList.realdate || bsRabiesNumNotList.status != 1}">
							<td>&nbsp;</td>
						</c:if>
					</tr>
					<tr style="height: 52px;">
						<c:if test="${not empty bsRabiesNumNotList.signatureList && bsRabiesNumNotList.signature == 1 && bsRabiesNumNotList.stype == 0}">
							<td class="image" style="padding:0;">${bsRabiesNumNotList.signatureList}</td>
						</c:if>
						<c:if test="${not empty bsRabiesNumNotList.signatureList && bsRabiesNumNotList.signature == 1 && bsRabiesNumNotList.stype != 0}">
							<td style="padding:0;"><img src="${ctx}/rabiesvaccine/bsRabiesCheckin/signatureimg?id=${bsRabiesNumNotList.id}" width="104px" height="38px"/></td>
						</c:if>
						<c:if test="${bsRabiesNumNotList.signature == 0 || (bsRabiesNumNotList.signature == 1 && empty bsRabiesNumNotList.signatureList)}">
							<td style="padding:0;">&nbsp;</td>
						</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</c:if>
			<c:forEach items="${bsRabiesNumList }" var="bsRabiesNum">
				<table class="table_1" border="1">
					<tr>
						<th>${fns:getDictLabel(bsRabiesNum.vaccinum, 'pin', '')}</th>
					</tr>
					<tr>
						<td><fmt:formatDate value="${bsRabiesNum.vaccidate}" pattern="yyyy-MM-dd" /></td>
					</tr>
					<tr>
						<c:if test="${not empty bsRabiesNum.realdate && bsRabiesNum.status == 1}">
							<td><fmt:formatDate value="${bsRabiesNum.realdate}" pattern="yyyy-MM-dd" /></td>
						</c:if>
						<c:if test="${empty bsRabiesNum.realdate || bsRabiesNum.status != 1}">
							<td>&nbsp;</td>
						</c:if>
					</tr>
					<tr>
						<c:if test="${not empty bsRabiesNum.realdate && bsRabiesNum.status == 1}">
							<td>${bsRabiesNum.createByName}</td>
						</c:if>
						<c:if test="${empty bsRabiesNum.realdate || bsRabiesNum.status != 1}">
							<td>&nbsp;</td>
						</c:if>
					</tr>
					<tr style="height: 52px;">
						<c:if test="${not empty bsRabiesNum.signatureList && bsRabiesNum.signature == 1 && bsRabiesNum.stype == 0}">
							<td class="img" style="padding:0;">${bsRabiesNum.signatureList}</td>
						</c:if>
						<c:if test="${not empty bsRabiesNum.signatureList && bsRabiesNum.signature == 1 && bsRabiesNum.stype != 0}">
							<td style="padding:0;"><img src="${ctx}/rabiesvaccine/bsRabiesCheckin/signatureimg?id=${bsRabiesNum.id}" width="104px" height="38px"/></td>
						</c:if>
						<c:if test="${bsRabiesNum.signature == 0 || (bsRabiesNum.signature == 1 && empty bsRabiesNum.signatureList)  }">
							<td style="padding:0;">&nbsp;</td>
						</c:if>
					</tr>
					<tr>
						<td>&nbsp;</td>
					</tr>
				</table>
			</c:forEach>	
		</div>
		<div class="bottom_num">编号：${bsRabiesCheckin.rabiescode}</div>
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