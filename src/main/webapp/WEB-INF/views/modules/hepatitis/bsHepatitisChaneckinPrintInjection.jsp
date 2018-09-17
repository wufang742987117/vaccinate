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
		<div style="position: absolute; left: 2%; top:53px;"><img alt="" src="${ctx }/hepatitis/bsHepatitisBcheckin/qrcode/${bsHepatitisBcheckin.hepaBcode}A?w=140&h=140" /></div>
		<div style="font-size: 20px; color: red">编号：${bsHepatitisBcheckin.hepaBcode}</div>
		<div style="font-size: 30px; text-align: center; font-weight: bold; letter-spacing: 10px; margin-top:12px;">${vaccinatename }</div>
		<br>
		<div style="font-size: 20px; font-weight:bold; text-align: center;/* color: green; */margin-top: 10px;">姓名：${bsHepatitisBcheckin.username}&#12288;性别：${fns:getDictLabel(bsHepatitisBcheckin.sex, 'sex', '')}&#12288;年龄：${bsHepatitisBcheckin.age}岁</div>
		<div style="font-size: 26px; text-align: center;color: red;margin-top: 10px; font-weight:bold;  letter-spacing: 16px">注射日期</div>
		<table style="width: 90%; margin: 15px 5% 15px 5%; border: 1px #008000 solid; border-spacing:0">
			<tbody>
				<c:forEach items="${bsList }" var="bs">
					<tr style="font-size: 24px; font-weight: bold;">
						<td style="border: 1px #008000 solid; margin: 0px;width: 25%; text-align: center;">${fns:getDictLabel(bs.vaccineNum, 'pin', '')}</td>
						<td style="border: 1px #008000 solid; margin: 0px;width: 25%; text-align: center;">${bs.manufacturer}</td>
						<td style="border: 1px #008000 solid; margin: 0px; padding-left: 15px;"><fmt:formatDate value="${bs.vaccineDate}"
													pattern="yyyy-MM-dd" /></td>
					</tr>
				</c:forEach>		
			</tbody>
		</table>
		
		<div style="font-size: 26px; text-align: center;color: red;margin-top: 10px; font-weight:bold;  letter-spacing: 16px">接种时间 </div>
		<div style="font-size: 25px; /* color: red; */ margin:10px 5% 20px 5%;text-align: center;">上午8:00-11:00 &emsp;&emsp;下午2:30-5:00</div>
		<br/>
		<div style="font-size: 25px; text-align: center;color: red; font-weight: bold; letter-spacing: 16px; margin:10px 5% 10px 5%; ">注意事项</div>
		<div>
			<ol style=" padding: 0 5% 0 5%; margin-left: 16px; font-size: 20px;margin-top: 0px;line-height: 30px">
				<c:if test="${bsHepatitisBcheckin.vaccType == 2}">
					<li>注射疫苗期间可照常工作。正在发病的乙肝患者或隐形感染者、慢性乙肝病毒携带者和乙肝病毒既往感染者，都没有必要注射乙肝疫苗。</li>
					<li>发热者、患有急性或慢性严重疾病者（如心脏、肾脏病等）或严重脏器畸形、严重的皮肤湿疹患者、对酵母成分过敏者不能接种。</li>
				</c:if>
				<li>为及时诊治可能发生的过敏反应，接种后留观30分钟。</li>
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