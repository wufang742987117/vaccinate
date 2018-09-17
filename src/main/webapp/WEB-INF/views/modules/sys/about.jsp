<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>关于</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
			function clearQueneCache(){
				$.ajax({
					url:"${ctx}/inoculate/quene/clearQueneCache",
					success:function(data){
						layer.msg("清除缓存" + data + "条");
					}
				});
			}
			function togglePay(){
				$.ajax({
					url:"${ctx}/inoculate/quene/togglePay",
					success:function(data){
					//返回当前状态
						if(data){
							$(".payOption").html("关闭功能");
							$(".payStatus").html('状态（<span style="color: green">开</span>）');
							$(".payOption").removeClass("btn-primary");
							$(".payOption").addClass("btn-success");
						}else{
							$(".payOption").html("打开功能");
							$(".payStatus").html('状态（<span style="color: red">关</span>）');
							$(".payOption").addClass("btn-primary");
							$(".payOption").removeClass("btn-success");
						}
					}
				});
			}
			
			function toggleReserveOption(){
				$.ajax({
					url:"${ctx}/inoculate/quene/toggleReserve",
					success:function(data){
					//返回当前状态
						if(data){
							$(".reserveOption").html("关闭功能");
							$(".reserveStatus").html('状态（<span style="color: green">开</span>）');
							$(".reserveOption").removeClass("btn-primary");
							$(".reserveOption").addClass("btn-success");
						}else{
							$(".reserveOption").html("打开功能");
							$(".reserveStatus").html('状态（<span style="color: red">关</span>）');
							$(".reserveOption").addClass("btn-primary");
							$(".reserveOption").removeClass("btn-success");
						}
					}
				});
			}
			
			function toggleQuickOption(){
				$.ajax({
					url:"${ctx}/inoculate/quene/toggleQuick",
					success:function(data){
					//返回当前状态
						if(data){
							$(".quickOption").html("关闭功能");
							$(".quickStatus").html('状态（<span style="color: green">开</span>）');
							$(".quickOption").removeClass("btn-primary");
							$(".quickOption").addClass("btn-success");
						}else{
							$(".quickOption").html("打开功能");
							$(".quickStatus").html('状态（<span style="color: red">关</span>）');
							$(".quickOption").addClass("btn-primary");
							$(".quickOption").removeClass("btn-success");
						}
					}
				});
			}
			
			function toggleReceiptOption(){
				$.ajax({
					url:"${ctx}/inoculate/quene/toggleReceipt",
					success:function(data){
					//返回当前状态
						if(data){
							$(".receiptOption").html("关闭功能");
							$(".receiptStatus").html('状态（<span style="color: green">开</span>）');
							$(".receiptOption").removeClass("btn-primary");
							$(".receiptOption").addClass("btn-success");
						}else{
							$(".receiptOption").html("打开功能");
							$(".receiptStatus").html('状态（<span style="color: red">关</span>）');
							$(".receiptOption").addClass("btn-primary");
							$(".receiptOption").removeClass("btn-success");
						}
					}
				});
			}
			
			function toggleCallReadyOption(){
				$.ajax({
					url:"${ctx}/sys/user/toggleCallReady",
					success:function(data){
					//返回当前状态
						if(data.code == '200' && data.msg == true){
							$(".callReadyOption").html("关闭功能");
							$(".callReadyStatus").html('状态（<span style="color: green">开</span>）');
							$(".callReadyOption").removeClass("btn-primary");
							$(".callReadyOption").addClass("btn-success");
						}else if(data.code == '200' && data.msg == false){ 
							$(".callReadyOption").html("打开功能");
							$(".callReadyStatus").html('状态（<span style="color: red">关</span>）');
							$(".callReadyOption").addClass("btn-primary");
							$(".callReadyOption").removeClass("btn-success");
						}
					}
				});
			}
			
			function upLoaddata(){
				var load_idx = layer.load();
				$.ajax({
					url:"${ctx}/child_baseinfo/childBaseinfo/upLoaddata",
					success:function(data){
						layer.close(load_idx);
						layer.msg("上报数据:" + data);
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						layer.close(load_idx);
						layer.msg("上报失败："+XMLHttpRequest.status+textStatus,{"icon":2});	
					}
				});
			}
			
			function saveVacDelay(){
				var vacDelay = $(".vacDelay").val();
				var load_idx = layer.load();
 				$.ajax({
					url:"${ctx}/sys/user/saveVacDelay",
					data:{"vacDelay":vacDelay},
					success:function(data){
						if(data.code == '200'){
							layer.close(load_idx);
							success("保存成功");
						}else{
							layer.close(load_idx);
							error(data.msg);
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						layer.close(load_idx);
						error("保存失败："+XMLHttpRequest.status+textStatus);	
					}
				});
			}
			
			function saveObligate(){
				var obligate = $(".obligate").val();
				var load_idx = layer.load();
 				$.ajax({
					url:"${ctx}/sys/user/saveObligate",
					data:{"obligate":obligate},
					success:function(data){
						if(data.code == '200'){
							layer.close(load_idx);
							success("保存成功");
						}else{
							layer.close(load_idx);
							error(data.msg);
						}
					},
					error:function(XMLHttpRequest, textStatus, errorThrown){
						layer.close(load_idx);
						error("保存失败："+XMLHttpRequest.status+textStatus);	
					}
				});
			}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/sys/user/info">科室设置</a></li>
		<li><a href="${ctx}/sys/sysVaccDepartmentInfo/">门诊信息</a></li>
		<li><a href="${ctx}/sys/user/modifyPwd">密码设置</a></li>
		<li><a href="${ctx}/inoculate/quene/msgPostTo">信息推送</a></li>
		<li><a href="${ctx}/sys/user/list">用户管理</a></li>
		<li class="active"><a href="${ctx}/sys/user/about">门诊功能配置</a></li>
		<li ><a href="${ctx}/charge/findCharge">收银台</a></li>
		<div class=" row pull-right">
			<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
		</div>
	</ul>
	<div class="ibox">
		<div class="mt20">
			<sys:message content="${message}"/>
			<table id="contentTable" style="width: 600px; margin: 0 auto;" class="table table-striped table-bordered table-condensed">
				<tbody>
					<tr>
						<td class="text-right" style="line-height: 42px">项&ensp;目&ensp;名：</td>
						<td>${fns:getConfig('productName')}</td>
					</tr>
					<tr>
						<td class="text-right" style="line-height: 42px">版&ensp;本&ensp;号：</td>
						<td>${fns:getConfig('version')}</td>
					</tr>
					<tr>
						<td class="text-right" style="line-height: 42px">发布时间：</td>
						<td>${fns:getConfig('releaseTime')}</td> 
					</tr>
<!-- 					<tr>
						<td class="text-right">清除缓存：</td>
						<td><button class="btn btn-primary" onclick="javascript:clearQueneCache()">清空排号队列缓存</button></td>
					</tr> -->
					<tr>
						<td class="text-right">缴费排号：</td>
						<c:if test="${payOption}">
							<td><button class="btn btn-success payOption" onclick="javascript:togglePay()">关闭功能</button> <span class="payStatus">状态（<span style="color: green">开</span>）</span>
						</c:if>
						<c:if test="${!payOption}">
							<td><button class="btn btn-primary payOption" onclick="javascript:togglePay()">打开功能</button> <span class="payStatus">状态（<span style="color: red">关</span>）</span>
						</c:if>
						<br>
						<span style="color: red">打开此功能时，自费疫苗需通过财务扫码才能进入接种台</span></td>
						
					</tr>
<%-- 					<tr>
						<td class="text-right">使用排号：</td>
						<c:if test="${quickOption}">
							<td><button class="btn btn-success quickOption" onclick="javascript:toggleQuickOption()">关闭功能</button> <span class="quickStatus">状态（<span style="color: green">开</span>）</span>
						</c:if>
						<c:if test="${!quickOption}">
							<td><button class="btn btn-primary quickOption" onclick="javascript:toggleQuickOption()">打开功能</button> <span class="quickStatus">状态（<span style="color: red">关</span>）</span>
						</c:if>
						<br>
						<span style="color: red">打开此功能时，接种台直接完成接种，不进行排号</span></td>
					</tr> --%>
					<tr>
						<td class="text-right">小票打印：</td>
						<c:if test="${receiptOption}">
							<td><button class="btn btn-success receiptOption" onclick="javascript:toggleReceiptOption()">关闭功能</button> <span class="receiptStatus">状态（<span style="color: green">开</span>）</span>
						</c:if>
						<c:if test="${!receiptOption}">
							<td><button class="btn btn-primary receiptOption" onclick="javascript:toggleReceiptOption()">打开功能</button> <span class="receiptStatus">状态（<span style="color: red">关</span>）</span>
						</c:if>
						<br>
						<span style="color: red">关闭此功能时，登记台可不出小票直接完成</span></td>
					</tr>
					<tr>
						<td class="text-right">叫号准备：</td>
						<c:if test="${callReadyOption}">
							<td><button class="btn btn-success callReadyOption" onclick="javascript:toggleCallReadyOption()">关闭功能</button> <span class="callReadyStatus">状态（<span style="color: green">开</span>）</span>
						</c:if>
						<c:if test="${!callReadyOption}">
							<td><button class="btn btn-primary callReadyOption" onclick="javascript:toggleCallReadyOption()">打开功能</button> <span class="callReadyStatus">状态（<span style="color: red">关</span>）</span>
						</c:if>
						<br>
						<span style="color: red">打开此功能时，叫号会叫下一号准备</span></td>
					</tr>
					<tr>
						<td class="text-right">疫苗间隔：</td>
							<td>
								<input type="number" value="${vacDelay}" class="w100 vacDelay" style="height: 32px;">
								<button class="btn btn-primary" onclick="saveVacDelay()">保存</button>
								<br>
								<span style="color: red">不同疫苗之间最少间隔天数（最小7天）</span>
							</td>
					</tr>
					<tr>
						<td class="text-right">疫苗预留库存：</td>
							<td>
								<input type="number" value="${obligate}" class="w100 obligate" style="height: 32px;">
								<button class="btn btn-primary" onclick="saveObligate()">保存</button>
								<br>
								<span style="color: red">当库存小于数值是自动排号不显示</span>
							</td>
					</tr>
					<tr>
						<td class="text-right">数据：</td>
						<td><button class="btn btn-success" onclick="javascript:upLoaddata()">上报接种数据</button></td>
					</tr>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>