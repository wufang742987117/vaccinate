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
</style>
</head>
<body>
	<div class="wrap" style=" padding: 20px; " id="baseinfo">
		<div style="position: absolute; left: 2%; top:53px;"><img alt="" src="${ctx }/rabiesvaccine/bsRabiesCheckin/qrcode/${bsRabiesCheckin.rabiescode}A?w=130&h=130" /></div>
		<div style="font-size: 20px; color: red">编号：${bsRabiesCheckin.rabiescode}</div>
		<div style="font-size: 30px; font-weight: bold; text-align: center; letter-spacing: 10px; margin-top:12px;">狂&nbsp;犬&nbsp;疫&nbsp;苗</div>
		<br>
		<div style="font-size: 20px; font-weight:bold; text-align: center; margin-top: 10px;">姓名：${bsRabiesCheckin.username}&#12288;性别：${fns:getDictLabel(bsRabiesCheckin.sex, 'sex', '')}&#12288;年龄：${bsRabiesCheckin.age}岁</div>
		<div style="font-size: 26px; text-align: center;color: red;margin-top: 10px; font-weight:bold;  letter-spacing: 16px">注射日期</div>
		<table style="width: 90%; margin: 15px 5% 15px 5%; border: 1px #008000 solid; border-spacing:0">
			<tbody>
				<c:if test="${not empty bsRabiesNumNotList }">
					<tr style="font-size: 24px; font-weight: bold;">
						<td style="border: 1px #008000 solid; margin: 0px;width: 25%; text-align: center;">${fns:getDictLabel(bsRabiesNumNotList.vaccinum, 'pin', '')}</td>
						<td style="border: 1px #008000 solid; margin: 0px;width: 25%; text-align: center;">${bsRabiesNumNotList.manufacturer}</td>
						<td style="border: 1px #008000 solid; margin: 0px; padding-left: 15px;"><fmt:formatDate value="${bsRabiesNumNotList.vaccidate}" pattern="yyyy-MM-dd" />&nbsp;&nbsp;(共${bsNum}针)</td>
					</tr>
				</c:if>
				<c:forEach items="${bsRabiesNumList }" var="bsRabiesNum">
					<tr style="font-size: 24px; font-weight: bold;">
						<td style="border: 1px #008000 solid; margin: 0px;width: 25%; text-align: center;">${fns:getDictLabel(bsRabiesNum.vaccinum, 'pin', '')}</td>						
						<c:choose>
							<c:when test="${bsRabiesNum.vaccineid=='2802'}">
								<td style="border: 1px #008000 solid; margin: 0px;width: 40%; text-align: center;">${bsRabiesNum.manufacturer}(Vero冻干)</td>
							</c:when>
							<c:when test="${bsRabiesNum.vaccineid=='2801'}">
								<td style="border: 1px #008000 solid; margin: 0px;width: 40%; text-align: center;">${bsRabiesNum.manufacturer}(水剂)</td>
							</c:when>
							<c:otherwise>
								<td style="border: 1px #008000 solid; margin: 0px;width: 40%; text-align: center;">${bsRabiesNum.manufacturer}</td>
							</c:otherwise>
						</c:choose>
						<td style="border: 1px #008000 solid; margin: 0px; padding-left: 15px;"><fmt:formatDate value="${bsRabiesNum.vaccidate}" pattern="yyyy-MM-dd" /></td>
					</tr>
				</c:forEach>		
			</tbody>
		</table>
		
		<div style="font-size: 26px; text-align: center;color: red;margin-top: 10px; font-weight:bold;  letter-spacing: 16px">接种时间 </div>
		<div style="font-size: 25px; margin:10px 5% 20px 5%;text-align: center;">上午8:00-11:00 &emsp;&emsp;下午2:30-5:00</div>
		<br/>
		<div style="font-size: 25px; text-align: center;color: red; font-weight: bold; letter-spacing: 16px; margin:10px 5% 10px 5%; ">注意事项</div>
		<div>
			<ol style=" padding: 0 5% 0 5%; margin-left: 16px; font-size: 20px;margin-top: 0px;line-height: 30px">
				<li >注射疫苗期间可照常工作，切忌饮酒、浓茶等刺激性食物及剧烈劳动和运动，以避免引起反应。</li>
				<li>发热者暂缓接种。</li>
				<li>个别人可能出现红、肿、痒、痛等过敏现象。</li>
				<li>接种当日不宜沐浴。</li>
				<li>接种后留观30分钟。</li>
				<li>请您按照以上规定的时间按时接种疫苗。</li>
			</ol>
		</div>
		<div style="font-size: 20px; /* color: red; */ text-align: right; margin-right: 25px;">医师：<span style="text-decoration:underline;">${docaterName}</span></div>
		<div style="font-size: 20px;  text-align: right; margin-right: 25px; font-weight: bold;">${officeName}</div>
		<div  style="font-size: 20px;  text-align: right; margin-right: 25px; font-weight: bold;">${data }</div>
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