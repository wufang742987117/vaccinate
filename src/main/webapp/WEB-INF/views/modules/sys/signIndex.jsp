<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title></title>
    <style>
    	*{ margin: 0; padding: 0;} 
    	
    	html{ height: 100%;} 
    	
			#body{background: url(${pageContext.request.contextPath}/static/images/welcom_bg.jpg) no-repeat; background-color: #244fa9; min-width: 600px; min-height: 100%; position: relative; overflow: hidden;}
			 
			.title{ text-align: center; padding-bottom: 500px; padding-top: 30px;} 
			
			h1{font-family: simhei; font-size: 64px; color: #f9f5f5; text-shadow: #0b318f 4px 4px 0.1em; margin: 0 0 15px 0; } 
			
			.footer{width: 100%; height: 500px; position: absolute; text-align: center; bottom: 0; } 
			
			img{ height: 496px; }
    </style>
  </head>
  <body id="body" onresize="resize()">
  		<div class="title">
	  		<h1>欢迎使用</h1>
	  		<h1>智慧接种管理系统</h1>
	  	</div>
	  	<div class="footer">
	  		<img src="${pageContext.request.contextPath}/static/images/welcome-logo.png"/>
	  	</div>
  </body>
  <script>
			// 获取浏览器窗口的可视区域的宽度
			function getViewPortWidth() {
			  return document.body.clientWidth;
			}
			  
			// 获取浏览器窗口的可视区域的高度
			function getViewPortHeight() {
			  return document.body.clientHeight;
			}
		
			var body = document.getElementById("body");
			
			window.onload=function(){ 
				//初始化
				body.style.backgroundSize = ""+getViewPortWidth()+"px "+ getViewPortHeight() + "px";
			} 
		
			function resize(){		
        // console.info(getViewPortWidth() + "_" + getViewPortHeight());
				body.style.backgroundSize = ""+getViewPortWidth()+"px "+ getViewPortHeight() + "px";
			}
		</script>
</html>
