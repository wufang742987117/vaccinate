<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta name="author" content="http://jeesite.com/"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Cache-Control" content="no-cache">

	<%@include file="/WEB-INF/views/include/head1.jsp" %>

	<!--[if lt IE 9]>
	<meta http-equiv="refresh" content="0;ie.html" />
	<![endif]-->
	<title>${fns:getConfig('productName')}</title>
	<c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>
	
	<style type="text/css">
		.grid-container{
			/* width:900px; */
			margin:60px auto;
			text-align: center;
		}
		.grid{
			display: inline-block;
			width:300px;
			height:300px;
			margin-left: 20px;
			margin-top: 50px;
			position: relative;
			box-shadow:6px 6px 4px rgba(0,0,0,0.3);
		}
		.grid>span{
			position: absolute;
			bottom: 20px;
			left:0;
			display: inline-block;
			width:100%;
			font-size: 40px;
			color: #000;
		}
		.grid.jdqr{
			background:#F7EEDD url(${ctxStatic}/images/vac01.png) no-repeat center 50px;
		}
		.grid.jzt{
			background:#F7EEDD url(${ctxStatic}/images/vac02.png) no-repeat center 50px;
		}
		.grid.system{
			background:#F7EEDD url(${ctxStatic}/images/vac03.png) no-repeat center 50px;
		}
		.grid.userinfo{
			background:#F7EEDD url(${ctxStatic}/images/vac04.png) no-repeat center 50px;
		}
		.grid.dog{
			background:#F7EEDD url(${ctxStatic}/images/vac05.png) no-repeat center 50px;
		}
		.grid.hebp{
			background:#F7EEDD url(${ctxStatic}/images/vac06.png) no-repeat center 50px;
		}
		.user-info i{
			float: left;
			background-image: url(${ctxStatic}/images/user-info.png);
			background-size: 100%;
			background-repeat: no-repeat;
			height: 21px;
			width: 21px;
		}
		.user-info{
	        margin-top: 18px;
		    display: block;
		    margin-right: 25px;
		    /*width: 80px;*/
		    height: 25px;
		    float: left;
		    cursor: pointer;
		}
		.user-info em{
		    margin-left: 5px;
		    line-height: 23px;
		    font-style: normal;
		    float: left;
		    font-size: 16px;
		    color: #1c84c6;
		}
		.user-info em:hover{
			text-decoration: underline;
		}
		.pos{
			position: relative;
		}
		.none{
			display: none;
		}
		.pop-info{
			position: absolute;
			top: 40px;
			left: -90px;
			padding: 20px;
			background-color: #49494a;
			/*opacity: 0.7;*/
			border-radius: 5px;
			color: #fff;
			text-align: left;
		}
		.arrow-up{
			position: absolute;
			top: 30px;
			left: 0px;
		    width:0; 
		    height:0; 
		    border-left:10px solid transparent;
		    border-right:10px solid transparent;
		    border-bottom:10px solid #49494a;

		}
		.hospital i{
			float: left;
			background-image: url(${ctxStatic}/images/hospital.png);
			background-size: 100%;
			background-repeat: no-repeat;
			height: 21px;
			width: 21px;
		}
		.hospital{
	        margin-top: 18px;
		    display: block;
		    margin-right: 25px;
		    /*width: 80px;*/
		    height: 25px;
		    float: left;
		    cursor: pointer;
		}
		.hospital em{
		    margin-left: 5px;
		    line-height: 23px;
		    font-style: normal;
		    float: left;
		    font-size: 16px;
		    color: #1c84c6;
		}
		/*.hospital em:hover{
			text-decoration: underline;
		}*/
		.pop-info p{
			white-space:nowrap;
		}
	</style>

</head>
<body class="fixed-sidebar full-height-layout gray-bg text-right">
<div id="wrapper1">
<div  class=" row pull-left">
	<span>预约人数：</span><span class="remindNum"></span>
	<span>排号人数：</span><span class="queneNum"></span>
	<span>完成人数：</span><span class="recordNum"></span>
	<span>犬伤门诊：</span><span class="rabiesNum"></span>
</div>
<div  class=" row pull-right">
	<span class="hospital"><i></i><em></em></span>
	<span class="user-info pos"><i></i><em></em>
		<div class="arrow-up none"></div>
		<div class="pop-info none">
			<p>单位编码：<span class="unit-code"></span></p>
			<p>单位全称：<span class="unit-name"></span></p>
			<!-- <p>单位级别：<span class="unit-type"></span></p> -->
			<p>联系方式：<span class="phone"></span></p>
			<p>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：<span class="name"></span></p>
			<!-- <p>学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;历：<span class="record"></span></p>
			<p>职称等级：<span class="professional"></span></p> -->
		</div>
	</span>
	<c:if test="${fns:getConfig('hbepi.login.password') eq 'true' }">
		
		<a style="margin-right: 30px ;margin-top: 10px" href="${ctx}/logout" class="btn btn-success w100"><span class="glyphicon glyphicon-arrow-left" ></span> 退出</a>
	</c:if>
	<c:if test="${fns:getConfig('hbepi.login.password') eq 'false' }">
		<!-- <span class="user-info"><i></i><em>XXX</em></span> -->
		<a style="margin-right: 30px ;margin-top: 10px" href="${ctx}/logoutHb" class="btn btn-success w100"><span class="glyphicon glyphicon-arrow-left" ></span> 退出</a>
	</c:if>
	
</div>
<div class="container">
	<div class="grid-container">
		<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1">
			<div class="grid jdqr">
				<span>登记台</span>
			</div>
		</a>
		<a href="${ctx}/inoculate/quene/inoculateIndex">
			<div class="grid system">
				<span>接种台</span>
			</div>
		</a>
		<a href="${ctx}/rabiesvaccine/bsRabiesCheckin">
			<div class="grid dog">
				<span>狂犬病免疫</span>
			</div>
		</a>
		<a href="${ctx}/hepatitis/bsHepatitisBcheckin">
			<div class="grid hebp">
				<span>成人免疫</span>
			</div>
		</a> 
		<a href="${ctx}/product/bsManageProduct/">
			<div class="grid jzt">
				<span>系统配置</span>
			</div>
		</a>
		<a href="${ctx}/sys/user/info">
			<div class="grid userinfo">
				<span>用户设置</span>
			</div>
		</a>
	</div>
</div>
</div>
</body>
</html>
<script type="text/javascript">
	var init = {
		getUserInfo: function(){
			$.ajax({
				url: "${ctx}/sys/sysVaccDepartmentInfo/getCurrentInfo",
				type: "post",
				data: "",
				dataType: "json",
				success: function(data){
					$(".hospital em").text(data.departmentInfo.localname);
					$(".user-info em").text(data.user.loginName);
					$(".unit-code").text(data.departmentInfo.localCode);
					$(".unit-name").text(data.departmentInfo.localname);
					// $(".unit-type").text();
					$(".phone").text(data.departmentInfo.phonenumber);
					$(".name").text(data.user.name);
					// $(".record").text();
					// $(".professional").text();

				}
			});
		}
	}
	var initCount = {
		getRemindCount: function(){
			$.ajax({
				url: "${ctx}/sys/user/getRemindCount",
				type: "post",
				data: "",
				dataType: "json",
				success: function(data){
					$(".remindNum").text(data.childRemind);
					$(".queneNum").text(data.queneNum);
					$(".recordNum").text(data.recordNum);
					$(".rabiesNum").text(data.rabiesNum);
				}
			});
		}
	}
	var addEvent = function(){
		$(".user-info").mouseover(function(){
			$(".arrow-up").stop();
			$(".arrow-up").fadeIn(500);
			$(".pop-info").stop();
			$(".pop-info").fadeIn(500);
			//  $(".arrow-up").removeClass("none");
			// $(".pop-info").removeClass("none");
			return false;
		});
		$(".user-info").mouseout(function(){
			$(".arrow-up").stop();
			$(".arrow-up").fadeOut(500);
			$(".pop-info").stop();
			$(".pop-info").fadeOut(500);
			// $(".arrow-up").addClass("none");
			// $(".pop-info").addClass("none");
			return false;
		});
		$(".user-info").click(function(){
			window.location.href = "${ctx}/sys/user/info";
		});
		$(".hospital").click(function(){
			window.location.href = "${ctx}/sys/sysVaccDepartmentInfo";
		})
	};
	$(function(){
		init.getUserInfo();
		initCount.getRemindCount();
		addEvent();
		
	});
</script>