<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>在册变更</title>
<meta name="decorator" content="default" />
<style type="text/css">
	.w130{
	    width: 130px !important;
	}
	.noborder{
		border: none !important;
	}
	#ctable,#ctable tr th,#ctable tr td { 
		border:1px solid #DDD; 
		text-align:center; 
		/* background-color:#ccc; */
	}
	.pull-right{
		float:right !important;
		margin-right:100px;
	}
</style>
<script type="text/javascript">
		
	function btnClose(){
		parent.layer.closeAll();
	}	
	
	function btnChange(){
		var load_idx = layer.load();
		var	larea=$("#area").val();
		var	lsituation=$("#situation").val();
		var	lreside=$("#reside").val();		
		var	remarks=$("#remarks").val();
		var guardianmobile=$("#guardianmobile").val();
		var address=$("#address").val();
		$.ajax({
			url:'${ctx}/child_baseinfo/childBaseinfo/savechangeSitu',
			data:{"guardianmobile":guardianmobile,"address":address,"area":larea,"situation":lsituation,"reside":lreside,"id":'${childBaseinfo.id}',"remarks":remarks},
			success:function(data){
				console.info(data);
					layer.close(load_idx);
					layer.msg("个案更新成功",{"icon":1});
					parent.funB();
				},
			error:function(a,b){
				layer.close(load_idx);
				layer.msg("个案更新失败",{"icon":7});
				console.error(b);
			}
		});
	}
	
	
</script>
</head>
<body>
	<div class="form-group">
		<div class='input-group' style='margin:0 auto;'>
			<span class='input-group-addon gray-bg text-right'>儿童姓名:</span>
			<input maxlength="400" class="form-control " value="${childBaseinfo.childname}" readonly="readonly"> 
		</div>
		<div class='input-group' style='margin:10px auto;'>
			<span class='input-group-addon gray-bg text-right'>母亲电话: </span> 
			<input id="guardianmobile"  maxlength="400" class="form-control" value="${childBaseinfo.guardianmobile}"/>
		</div>
		<div class='input-group' style='margin:10px auto;'>
			<span class='input-group-addon gray-bg text-right'>区域划分: </span>
			<select class='form-control' id='area'>
				<c:forEach items="${coms}" var="area">
					<c:choose>
						<c:when test="${childBaseinfo.area==area.code}">
							<option selected="selected" value="${ area.code}" class="area">${area.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${area.code}" class="area">${area.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>

		<div class='input-group' style='margin:10px auto;'>
			<span class='input-group-addon gray-bg text-right'>在册情况: </span> <select
				class='form-control' id='situation'>
				<c:forEach items="${fns:getDictList('situation')}" var="situation">
          			<c:choose>
						<c:when test="${childBaseinfo.situation==situation.value}">
							<option selected="selected" value="${ situation.value}">${situation.label}</option>
						</c:when>
						<c:otherwise>
							<option value="${situation.value}">${situation.label}</option>
						</c:otherwise>
					</c:choose>
                </c:forEach>
			</select>
		</div>

		<div class='input-group' style='margin:10px auto;'>
			<span class='input-group-addon gray-bg text-right'>居住属性: </span> <select
				class='form-control' id='reside'>
				  <c:forEach items="${fns:getDictList('reside')}" var="reside">
                  		<c:choose>
						<c:when test="${childBaseinfo.reside==reside.value}">
							<option selected="selected" value="${reside.value}">${reside.label}</option>
						</c:when>
						<c:otherwise>
							<option value="${reside.value}">${reside.label}</option>
						</c:otherwise>
					</c:choose>
                  </c:forEach>
			</select>
		</div>
		<div class='input-group' style='margin:10px auto;'>
			<span class='input-group-addon gray-bg text-right'>家庭地址:</span> 
			<input id="address" placeholder="请输入家庭地址" maxlength="400" class="form-control" value="${childBaseinfo.address}"/>
		</div>
		<div class='input-group' style='margin:10px auto;'>
			<span class='input-group-addon gray-bg text-right'>变更备注: </span> 
			<input id="remarks" placeholder="请输入备注" maxlength="400" class="form-control "/>
		</div>
		<div class="pull-right fix">
				<button type="button" class="btn btn-primary"  style="margin-right: 20px;" onclick="btnChange()">保存</button>
				<button type="button" class="btn btn-primary"  style="margin-right: 20px;" onclick="btnClose()">取消</button>
			</div>
	</div>
</body>
</html>