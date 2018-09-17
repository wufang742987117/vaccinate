<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>
<html>
<head>
	<title>接种门诊日志</title>
	<meta charset="UTF-8">
	<style>
	@font-face
	{
	    font-family:'微软雅黑';
/* 	    src:url('${ctxStatic}/fonts/MSYH.eot'); */
/* 	    src:local('☺'), */
/* 	        url('${ctxStatic}/fonts/MSYH.eot?#iefix') format('eot'), */
/* 	        font-weight:normal; */
/*         src:url('${ctxStatic}/fonts/MSYH.woff2') format('woff2'),  */
/*         	url('MSYH.ttf') format('truetype');/*non-IE*/ */
/* 	    font-style:normal; */
	}
	@page { 
		size: landscape;
		size: 29.7cm 21cm;   /*A4*/
 		margin:14px 16px; /*webkit says no*/ 
 	}
	#contentTable td , #contentTable th{ padding: 1px; height: 38px; max-height: 38px;}
	*{
		font-family:'msyh';
	}
	</style>
	<script type="text/javascript">
		$(function(){
			var strm;
			var im;
			$(".image").each(function(i,t){
				strm = t.innerText;
				im = new Image();
		        im.src = "data:image/png;base64" + "," + strm;
		        im.height = 33;
		        $(this).html(im);
			});
			
			var map = eval(${map});
			var html = '小计：';
			for(var key in map){
			    html += '<span>'+ key + '</span>&nbsp;<span style="text-decoration: underline;">' + map[key] + '</span>&emsp;&nbsp;';
			}
			$("#foot").html(html);
		})
		
	</script>
</head>
<body>
<c:forEach items="${returnList}" var="list">
	<div class="ibox" style=" margin-bottom: 0; margin-top: 20px;">
		<p style="font-size: 20px;" class="text-center">预防接种门诊日志</p>
		<div style="padding: 0 5px;">
			<table id="contentTable" border="1" class="text-center ft12" style="border:solid 1px black;border-collapse:collapse;margin: 0 auto 10px auto; width: 100%;">
				<thead>
					<tr>
						<th class="w70">接种日期<br />(年月日)</th>
						<th>儿童姓名</th>
						<th class="w30">性<br>别</th>
						<th class="w70">出生日期<br />(年月日)</th>
						<th class="w30">过敏<br />史</th>
						<th class="w30">发热</th>
						<th class="w30">咳嗽</th>
						<th class="w30">腹泻</th>
						<th class="w30">其他<br />症状</th>
						<th class="w50">近期是<br />否患病</th>
						<th>接种的疫苗<br />及第几剂次</th>
						<th class="w60">接种<br>部位</th>
						<th>疫苗批号</th>
						<th>生产企业</th>
						<th class="w150">家长签字及更新电话号码</th>
						<th>接种<br>医生</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list}" var="childVaccinaterecord" varStatus="status">
						<tr>
							<td><fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd" /></td>
							<td>${childVaccinaterecord.childname}</td>
							<td>${fns:getDictLabel(childVaccinaterecord.gender, 'sex', '')}</td>
							<td><fmt:formatDate value="${childVaccinaterecord.birthday}" pattern="yyyy-MM-dd" /></td>
							<td>
								<c:if test="${childVaccinaterecord.childAbnormalReaction eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.childAbnormalReaction ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.fever eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.fever ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.cough eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.cough ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.diarrhea eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.diarrhea ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.symptom eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.symptom ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td>
								<c:if test="${childVaccinaterecord.disease eq '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_circle.png">
								</c:if>
								<c:if test="${childVaccinaterecord.disease ne '无'}">
									<img alt="" src="${ctxStatic}/images/loglist_hook.png">
								</c:if>
							</td>
							<td class="text-left pl8">${childVaccinaterecord.vaccName}<br />第${childVaccinaterecord.dosage}剂次</td>
							<td>${fns:getDictLabel(childVaccinaterecord.bodypart, 'position', '')}</td>
							<td>${childVaccinaterecord.batch}</td>
							<td>${childVaccinaterecord.manufacturer}</td>
							<td>
								<c:if test="${empty childVaccinaterecord.signatureList || childVaccinaterecord.signature == 0}">
									${childVaccinaterecord.parentsMoblie}
								</c:if>
								<c:if test="${not empty childVaccinaterecord.signatureList && childVaccinaterecord.signature == 1 && childVaccinaterecord.stype == 1}">
									<div class="image" style="margin-right: 0;">${childVaccinaterecord.signList}</div>
									${childVaccinaterecord.parentsMoblie}
								</c:if>
								<c:if test="${not empty childVaccinaterecord.signatureList && childVaccinaterecord.signature == 1 && childVaccinaterecord.stype != 1}">
									<img src="${ctx}/child_vaccinaterecord/childVaccinaterecord/signatureimg?id=${childVaccinaterecord.id}" height="33px" style="margin-right: 0;"/>
									${childVaccinaterecord.parentsMoblie}
								</c:if>
							</td>
							<td>${childVaccinaterecord.doctor}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p class="ft12">
				<span>注：有症状打"<img src="${ctxStatic}/images/loglist_hook.png"/>"，无症状打"<img src="${ctxStatic}/images/loglist_circle.png"/>"</span>
				<span class="fr">接种单位：${officeName }<%-- ，接种医生：${docaterName } --%></span>
			</p>
		</div>
	</div>
</c:forEach>
<div id="foot" class="text-left ft12" style="padding: 0 20px;">
					
</div>
</body>
<script>
	var idx = layer.load();
	setTimeout(function() {
		layer.close(idx);
	 	window.print();  
	 	setTimeout("top.opener=null;window.close()",100);
	}, 3000);
</script>
</html>