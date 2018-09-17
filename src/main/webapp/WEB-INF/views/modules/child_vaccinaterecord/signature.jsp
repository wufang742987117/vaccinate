<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>签字</title>
<meta name="decorator" content="default" />
<style type="text/css">
	.title{text-align: center; padding: 10px 0; font-size: 1.2em; font-weight: 700;}
	p{text-indent: 2em;margin:5px 0;font-size: 1em;line-height: 20px;}
	.unit{text-align: right;font-size: 0.90em;padding: 15px 0;}
</style>
<script type="text/javascript">
	function signature(id) {
		window.location.href = "${ctx}/child_vaccinaterecord/childVaccinaterecord?id=" + id;
	}
	
	function signatures(id) {
		parent.closeFormId(id);
	}
	
	$(function(){
		var strm;
		var im;
		$(".image").each(function(i,t){
			strm = t.innerText;
			im = new Image();
	        im.src = "data:" + "image/png;base64" + "," + strm;
	        im.width = 200;
	        $(this).html(im);
		});
	})
	
	function PrintPage(){   
		var newstr = document.getElementById("bool").innerHTML; 
		var oldstr = document.body.innerHTML; 
		document.body.innerHTML = newstr; 
		window.print(); 
		document.body.innerHTML = oldstr; 
		return false;
	} 
	
	function prints(){
		window.print();
	}
</script>
</head>
<body>
	<div style="background-color: #fff; padding: 20px 30px;">
		<div id="bool">
			<div style="font-size: 14px;">${signature}</div>
			<div style="margin-top: 10px;text-align: right;" >
				<p style="font-size: 16px;margin-right: 200px;">签字:</p>
				<c:if test="${not empty rec.signatureList && rec.signature == 1 && rec.stype == 1}">
					<div class="image" style="margin-right: 0;">${signatureList}</div>
				</c:if>
				<c:if test="${not empty rec.signatureList && rec.signature == 1 && rec.stype != 1}">
					<img src="${ctx}/child_vaccinaterecord/childVaccinaterecord/signatureimg?id=${rec.id}" width="200px" style="margin-right: 0;"/>
				</c:if>
			</div>
		</div>
		<div id="fool" style="text-align: center;margin-top: 15px;">
			<button class="btn btn-lg btn-primary" value="" onclick="PrintPage()">打印</button>
			<c:if test="${type == 0}">
				<button class="btn btn-lg btn-primary" value="" onclick="signature('${rec.id}')">返回</button>
			</c:if>
			<c:if test="${type == 1}">
				<button class="btn btn-lg btn-primary" value="" onclick="signatures('${rec.id}')">返回</button>
			</c:if>
		</div>
	</div>
</body>
</html>