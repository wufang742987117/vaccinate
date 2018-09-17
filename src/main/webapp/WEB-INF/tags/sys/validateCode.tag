<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="验证码输入框名称"%>
<%@ attribute name="inputCssStyle" type="java.lang.String" required="false" description="验证框样式"%>
<%@ attribute name="imageCssStyle" type="java.lang.String" required="false" description="验证码图片样式"%>
<%@ attribute name="buttonCssStyle" type="java.lang.String" required="false" description="看不清按钮样式"%>
<div class="input-group" style="width: 390px;margin: 0 auto 20px auto;">
	<input type="text" id="${name}" name="${name}" maxlength="5" class="required form-control validate-sty" placeholder="验证码" style="${inputCssStyle}"/>
	<span class="input-group-addon add-on-box">
		<img src="${pageContext.request.contextPath}/servlet/validateCodeServlet" onclick="$('.${name}Refresh').click();" class="mid ${name}" style="${imageCssStyle}"/>
	</span>
	<span class="input-group-btn">
		<a href="javascript:" onclick="$('.${name}').attr('src','${pageContext.request.contextPath}/servlet/validateCodeServlet?'+new Date().getTime());" class="mid ${name}Refresh btn add-on-btn" style="${buttonCssStyle}">看不清</a>
	</span>
</div>