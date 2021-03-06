<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>信息管理</title>
	<meta name="decorator" content="default"/>
	<c:if test="${not empty message }">
		<script type="text/javascript">
			$(function() {
				parent.layer.msg("${message}", {"icon":7});
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); //再执行关闭  
			});
		</script>
	</c:if>
	<script type="text/javascript">
		function submitCk(num){
			var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			if (!reg.test(num)) {
				return false;
			}
			return true;
		}
	
		function valiTr(thi){
			var _this = $(thi);
			var id = $(thi).attr("data-id");
			var originalPrice = $(thi).attr("data-originalPrice");
			var currentPrice = $(thi).find(".currentPrice").val();
			var BsRabiesNum = new Object();
			if(currentPrice == ""){
				BsRabiesNum.msg = "实收价格为空,提交失败";
				return BsRabiesNum;
			}
			if(!submitCk(currentPrice)){
				BsRabiesNum.msg = "实收价格输入错误，请重新输入";
				return BsRabiesNum;
			}
			BsRabiesNum.id = id;
			BsRabiesNum.originalPrice = originalPrice;
			BsRabiesNum.currentPrice = currentPrice;
			
			if(!(BsRabiesNum.id)) return false;
			if(!(BsRabiesNum.originalPrice)) return false;
			if(!(BsRabiesNum.currentPrice)) return false;
			return BsRabiesNum;
		}
		
		$(document).ready(function() {
			//提交		
			$("#btnSubmit").click(function() {
				if(!confirm("确认提交本次调价？")){
					return false;
				}
				var isok = true;
	 			var list = new Array();
				$("#tbody>tr").each(function(){
					//校验数据
					var data = valiTr(this);
					if(!data.id){
						layer.msg("提交数据为空", {"icon":7});
						isok = false;
						return ;
					}else if(data.msg){
						layer.msg(data.msg, {"icon":7});
						isok = false;
						return ;
					}
					list.push(data);
				});
				
				if(isok && list.length > 0){
					console.info(list);
					$.ajax({
						url:"${ctx}/num/bsRabiesNum/saveAdjustment",
						data:{"list":JSON.stringify(list)},
						type:"POST",
						success:function(data){
							console.info(data);
							if(data.code == "200"){
								parent.success("保存成功", {"time":800});
							}else{
								parent.error(data.message, {"time":800});
							}
							var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
							parent.layer.close(index); //再执行关闭  
						}
					});
				}
			});
			
			//退出
			$("#btnExit").click(function() {
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index); //再执行关闭   
			});
		});
	</script> 
</head>
<body>
	<div class="wrap">
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>针次</th>
					<th>程序接种时间</th>
					<th>厂家</th>
					<th>批号</th>
					<th>规格</th>
					<th>状态</th>
					<th>应收价格</th>
					<th>实收价格</th>
				</tr>
			</thead>
			<tbody id="tbody">
			<c:forEach items="${list}" var="bsRabiesNum">
				<tr data-id="${bsRabiesNum.id}" data-originalPrice="${bsRabiesNum.originalPrice}">
					<td data-role="vaccinum">${fns:getDictLabel(bsRabiesNum.vaccinum, 'pin', '')}</td>
					<td data-role="vaccidate"><fmt:formatDate value="${bsRabiesNum.vaccidate}" pattern="yyyy-MM-dd" /></td>
					<td data-role="manufacturer">${bsRabiesNum.manufacturer}</td>
					<td data-role="batchnum">${bsRabiesNum.batchnum}</td>
					<td data-role="dose">${bsRabiesNum.dose}</td>
					<td data-role="paystatus">${fns:getDictLabel(bsRabiesNum.paystatus, 'paystatus', '')}</td>
					<td data-role="originalPrice">${bsRabiesNum.originalPrice}</td>
					<td data-role="currentPrice"><input type="text" class="number currentPrice" value="${bsRabiesNum.currentPrice}" ></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div class="form-group">
			<button id="btnSubmit" class="btn btn-success mr15 pull-right" type="button" >
				<span class="glyphicon glyphicon-ok" aria-hidden="true"></span> 提交
			</button> 
			<button id="btnExit" class="btn btn-default mr15 pull-right" type="button" >
				<span class="glyphicon glyphicon-remove" aria-hidden="true"></span> 关闭
			</button> 
		</div>
	</div>
</body>
</html>