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

p {margin:0 auto}
h5 {margin:0;padding:0;}
h3 {margin:2px 0;padding:2px 0;}
</style>
<script type="text/javascript" src='${ctxStatic}/jquery/jquery-1.9.1.min.js'></script>
<script type="text/javascript">
	$(function(){
		var strm = "${signatureList}";
		if(strm != null || strm != ""){
			$("#img").attr("src","data:" + "image/png;base64" + "," + strm)
		}
		
		var flag = "${isinoculate}";
		if(flag != null || flag != ""){
			if(flag == 0){
				 $("#nitems_one").prop("checked", true);
			}else{
				 $("#nitems_two").prop("checked", true);
			}
		}
		
		var nflag = "${exposelevelnum}";
		if(nflag != null || nflag != ""){
			if(nflag == 3){
				$("#items_two").prop("checked", true);
			}else{
				$("#items_one").prop("checked", true);
			}
		}
		
	})
</script>
</head>
<body>
	<div>
		<div style="margin:0 auto; width: 100%;">
			<div id="header" style="text-align: center;">
				<h3>狂犬病疫苗和抗狂犬病血清/狂犬病人免疫球蛋白</h3>
				<h3>使用知情同意书</h3>
			</div>
			<div id="content" style="padding: 10px 60px;">
				<p style="text-indent: 2em; font-size: 14px;">狂犬病是由狂犬病病毒引起的急性传染病，主要由携带狂犬病病毒的犬、猫等动物咬伤所致。当人被感染狂犬病病毒的动物咬伤、抓伤及舔舐伤口或粘膜后，其唾液所含病毒经伤口或粘膜进入人体，一旦引起发病，病死率达100%。</p>
				<p style="text-indent: 2em; font-size: 14px;">人暴露狂犬病病毒后最理想的处理原则是：1、规范的处理伤口。2、正确使用狂犬病人免疫球蛋白。3、按时全程接种合格狂犬疫苗能大大减少发病的风险。</p>
				<p style="text-indent: 2em; font-size: 14px;">狂犬病人免疫球蛋白特异地中和狂犬病病毒，可立即生效。狂犬病疫苗接种10-14天后可刺激机体产生抗狂犬病病毒的保护性抗体。为安全有效地使用狂犬病疫苗和狂犬病人免疫球蛋白，在您使用之前我们将有关信息告知于您，您可以根据自己的具体情况决定是否使用。</p>
				<h5>一、伤口处理</h5>
				<p style="text-indent: 2em; font-size: 14px;">用20%的肥皂水（或者其他弱碱性清洁剂）和一定压力的流动清水交替彻底清洗、冲洗所有咬伤和抓伤处至少15分钟。彻底清洗后用2-3碘酒（碘伏）75%酒精涂擦伤口或用皮肤粘膜消毒液喷涂伤口。伤口较深、污染严重者酌情进行抗破伤风处理和使用维生素等，以控制狂犬病病毒以外的其他感染。</p>
				<h5>二、疫苗接种程序</h5>
				<p style="text-indent: 2em; font-size: 14px;color: red;">判定为Ⅰ级暴露无需进行处理。</p>
				<p style="text-indent: 2em; font-size: 14px;color: red;">1、II级暴露：无出血的轻微抓伤或擦伤、裸露的皮肤被轻咬：于0（注射当天）3、7、14和28天各注射狂犬病疫苗1个计量。</p>   
				<p style="text-indent: 2em; font-size: 14px;color: red;">2、III级暴露：单处或多处贯穿性皮肤咬伤或抓伤；破损皮肤被舔；开放性伤口或粘膜被污染；确认为II级暴露者且免疫功能低下的，或者II级暴露位于头面部且致伤动物不能确定健康时，按照III级暴露处置：注射狂犬病人免疫球蛋白，随后接种狂犬疫苗。</p>
				<p style="text-indent: 2em; font-size: 14px;color: red;">3、健康者预防：对未咬伤健康者预防接种，可按0、7、21天接种注射3针次。根据WHO的暴露后预防建议，II级或III级暴露者都应在接种疫苗的同时，使用抗狂犬病毒免疫球蛋白。</p>
				<h5>三、不良反应</h5>
				<p style="text-indent: 2em; font-size: 14px;color: red;">狂犬病疫苗：个别人接种后可产生不同程度的不良反应。如：注射部位局部反应（疼痛、红肿、硬结等）；皮疹和荨麻疹等过敏反应；发热或全身不适等全身反应。</p>
				<p style="text-indent: 2em; font-size: 14px;color: red;">
					狂犬病人免疫球蛋白：一般无不良反应，少数人有红肿、疼痛感，无需特殊处理可自行恢复。
				</p>
				<h5>四、注意事项</h5>
				<p style="text-indent: 2em; font-size: 14px;">发生在头、面、颈部、手部和外生殖器的咬伤属于III 级暴露。（WHO 推荐：由于头、面、颈、手和外生殖器部位神经丰富，建议这些部位的暴露属于III 级暴露）</p>
				<p style="text-indent: 2em; font-size: 14px;">狂犬病疫苗和抗狂犬病血清/狂犬病人免疫球蛋白属于公民自费、自愿接种疫苗。接种后留观30分钟，如出现轻微反应，一般不需特殊处理。特殊情况可电话咨询接种单位，必要时可赴医院诊治。</p>
				<p style="text-indent: 2em; font-size: 14px;">暴露级别<span style="text-decoration: underline;padding: 0 5px;">${exposelevel}</span>医生建议接种：<input type="checkbox" id="items_one">1狂犬疫苗&nbsp;&nbsp;<input type="checkbox" id="items_two">2狂犬疫苗+狂犬免疫球蛋白、接种医生签字<span style="text-decoration: underline;padding: 0 5px;">
					<c:if test="${not empty createByName }">
						${createByName}
					</c:if>
					<c:if test="${empty createByName }">
						&nbsp;&nbsp;&nbsp;
					</c:if>
				</span></p>
				<p style="text-indent: 2em; font-size: 14px;">以上告知内容本人已经详细阅读、愿意采取上述接种程序。&nbsp;&nbsp;&nbsp;&nbsp;
				1<input id="nitems_one" type="checkbox">&nbsp;&nbsp;2<input id="nitems_two" type="checkbox" >&nbsp;&nbsp;3<input id="nitems_three" type="checkbox">
				</p><br/><br/>
				<p style="text-indent: 2em; font-size: 14px;">接种单位（盖章）：</p><br/>
				<div style="font-size: 14px;text-align: right;">
					<span>受种人（或监护人）签字:</span>
					<c:if test="${not empty signatureList && stype == 0 }">
						<img id="img" style="width: 150px; height: 50px; border-bottom: 1px solid #000;vertical-align: bottom;" src="">
					</c:if>
					<c:if test="${empty signatureList && not empty sid && stype != 0 }">
						<img style="width: 150px; height: 50px; border-bottom: 1px solid #000;vertical-align: bottom;" src="${ctx}/rabiesvaccine/bsRabiesCheckin/signatureimg?id=${sid}">
					</c:if>
					<c:if test="${empty signatureList && empty sid }">
						<span style="width: 150px; height: 50px; border-bottom: 1px solid #000;vertical-align: bottom;">&emsp;&emsp;&emsp;&emsp;&emsp;</span>
					</c:if>
					<p>日期：${data}</p>
					<p>${officeName}</p>
				</div><br/>
				<span style="font-size: 10px;border: 1px solid #000; ">本知情同意书一式两份（受种者和接种单位各持一份），请妥善保管两年</span> 
			</div>		
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