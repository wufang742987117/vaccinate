<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<meta name="decorator" content="blank" />
<link rel="stylesheet" href="${ctxStatic}/css/cat-head-animate.css" />
<style type="text/css">
body{ margin: 0; padding: 0; background: #f2f2f2!important; }

.wrap{ width: 100%; min-width: 680px; height: 100%; min-height: 580px; position: relative; }

.top-titlt{ width: 100%; height: 100px; padding: 2% 0 0 0; }

.logo-name{
	width: 654px;
    height: 80px;
    font-family: simhei;
    font-weight: 700;
    font-size: 46px;
    color: #007bc7;
    line-height: 80px;
    padding-left: 90px;
    background: url(${ctxStatic}/images/loginlogo.png) 0 no-repeat;
    background-size: 74px;
    margin: 0 auto;
    letter-spacing: 0;
}
.form-box{
	position: absolute;
	top: 50%;
	margin-top: -220px; 
	width: 100%;
	height: 500px; 
	padding-top: 75px;
	background: url(${ctxStatic}/images/loginbg.png) center top no-repeat; 
	background-size: cover; 
}
.glass-bg{
	width: 670px;
	height: 360px;
	margin: 0 auto;
	background: rgba(255, 255, 255, 0.1);
	border: 2px solid #adadad;
	box-sizing:border-box;
	-moz-box-sizing:border-box; /* Firefox */
	-webkit-box-sizing:border-box; /* Safari */
	border-radius: 10px;
	padding: 18px;
}
.form-ctn{
	width: 630px;
	height: 320px; 
	background: #fff;
	margin: 0;
	padding: 20px 50px; 
	display: table;
    overflow: hidden;
}
.input-sty , .validate-sty{
    background: #eeefef!important;
    height: 40px!important;
    border: 0!important;
    font-size: 18px!important;
    padding: 6px 20px!important;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
}
.input-sty{
	width:390px;
	color: #555;
    border-radius: 20px!important;
    margin-bottom: 20px!important;
}
.validate-sty{
	width:250px;
	color: #555;
    border-top-left-radius: 20px!important;
	border-bottom-left-radius: 20px!important;
}
.input-sty:focus , .validate-sty:focus{
	border-color: rgba(82, 168, 236, 0.8);
    outline: 0;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
    -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
}

#messageBox{
	width: 330px;
    position: absolute;
    left: 50%;
    margin-top: 1%;
    margin-left: -165px;
    z-index: 99;
    text-align: center;
}
.footer{
	width: 100%;
	text-align: center;
	font-size: 14px;
	color: #888;
	position: fixed;
	bottom: 10px;
	z-index: -1;
	border: none;
	margin: 0;
    padding: 0;
    height: 20px;
    background: transparent;
}
.hide-custom{
		display: none;
	}
#rememberMe{
	display: none;
}
.s + i{
	display: block;
    float: left;
    width: 24px;
    height: 24px;
    margin: 0 6px 0 -4px;
    background: url(${ctxStatic}/images/login_check0.png) 0 0 no-repeat;
    background-size: cover;
}	
.s:checked + i{
	background: url(${ctxStatic}/images/login_check1.png) 0 0 no-repeat;
  		background-size: cover;
}	
</style>
<script type="text/javascript">
	//获取用户登陆名，根据登陆找到用户可选部门
	function findOffice(){
		$.ajax({
			url:"../findOffice",
			data:{"username":$("#username").val()},
			type:"get",
			dataType:"json",
			success:function(result){
				$("#office").empty();
				$(result).each(function(i,t){
					if(localStorage.getItem("loginOffice") == t.id){
						$("#office").append("<option value='"+this.id+"' selected='selected'>"+this.name+"</option>");
					}else{
						$("#office").append("<option value='"+this.id+"'>"+this.name+"</option>");
					}
				})
				
			}
		})
	}
	
	$(document).ready(function() {

		$("#loginForm").validate({
			rules: {
				validateCode: {remote: "${pageContext.request.contextPath}/servlet/validateCodeServlet"}
			},
			messages: {
				username: {required: "请填写用户名."},password: {required: "请填写密码."},
				validateCode: {remote: "验证码不正确.", required: "请填写验证码."}
			},
			errorLabelContainer: "#messageBox",
			errorPlacement: function(error, element) {
				error.appendTo($("#loginError").parent());
			} 
		});
		
		
		if($("#username").val()!=''){
			findOffice();
		}
		$("#username").blur(function(){
				findOffice();
		})
		
		//本地存储用户所选部门，下次登陆自动被选
		$("#office").change(function(){
			localStorage["loginOffice"] = $("#office").val();
		})
		
	});
	
	// 如果在框架或在对话框中，则弹出提示并跳转到首页
	if(self.frameElement && self.frameElement.tagName == "IFRAME" || $('#left').length > 0 || $('.jbox').length > 0){
		alert('未登录或登录超时。请重新登录，谢谢！');
		top.location = "${ctx}";
	}
	
	
</script>
</head>
<body>
	<div class="wrap">
		<div class="top-titlt">
			<p class="logo-name">智慧预防接种信息管理系统</p>
		</div>
		<div id="messageBox" class="alert alert-danger alert-dismissable ${empty message ? 'hide-custom' : ''}">
			<button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error">${message}</label>
		</div>
		<div class="form-box">
			<div class="glass-bg">
				<form id="loginForm" class="m-t form-ctn" role="form" action="${ctx}/login" method="post">
					<div style="vertical-align: middle;display: table-cell;">
						<div class="text-center">
							<input id="username" name="username" type="text" class="required input-sty" placeholder="用户名" value="${username}">
						</div>
						<div class="text-center">
							<input id="password" name="password" type="password" class="required input-sty" placeholder="密码">
						</div>
						
						<div class="text-center">
							<select class="input-sty" id="office" name="office">
								<option value="" class="input-sty">请选择部门</option>
							</select>
						</div>
						<c:if test="${isValidateCodeLogin}">
							<div class="text-center">
								<sys:validateCode name="validateCode" inputCssStyle="" />
							</div>
						</c:if>
						<%-- <div class="form-group text-left">
							<label for="rememberMe" title="下次不需要再登录">
							<input id="rememberMe" name="rememberMe" type="checkbox" class="s" ${rememberMe ? 'checked' : ''} /><i></i>
							<span style="color: #999;font-weight:normal;font-size:18px;">记住我（公共场所慎用）</span>
							</label>
						</div> --%>
						
						
						<div class="text-center">
							<input class="btn btn-primary" type="submit" value="登 录" style="width: 390px;height: 40px;border-radius: 20px;font-size: 22px;background-color: #3daae9;border: 0;"/>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="footer">
				Copyright &copy; 2017-${fns:getConfig('copyrightYear')}
				- Powered By
				<a href="javascript:void(0);" target="_blank">智慧接种</a>
				${fns:getConfig('version')}
		</div>
	</div>
</body>
</html>