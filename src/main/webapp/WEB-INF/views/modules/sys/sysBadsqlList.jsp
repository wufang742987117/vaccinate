<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>sql上报失败管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
		$(document).ready(function(){
			$("#checkboxAll").click(function(){
				if($(this).is(":checked")){
					$("[name='ckb']:checkbox").prop("checked",true);
				}else{
					$("[name='ckb']:checkbox").prop("checked",false);
				}
			});
		});
		function deletesysBadsql(){
			if('${sysBadsql.delFlag}'!='1'){
				layer.confirm("确认要删除吗？", {
			        btn: ['确认','取消'], //按钮
			        shade: true, //不显示遮罩
			        icon : 3,
			        offset : ['300px' , '35%']
			    }, function(index){
				    	layer.close(index);
				    	loading("正在执行...");
				    	var checkboxlength=$("input[name='ckb']:checked").length;
				    	if(checkboxlength==0){
				    		layer.msg('请选择删除项！');
				    		return;
				    	}
				    	var id="";
				    	$("[name='ckb']:checkbox").each(function(){
							if($(this).is(":checked")){
								id=id+$(this).val()+",";
							}
						})
						$.ajax({
							type : "POST",
							url  : "${ctx}/sys/sysBadsql/delete?id="+id,
							success : function(data) {
									 layer.msg(data,{time:1500},function(){
									 	location.reload();
									 });	  	   
							}
						});
				        
				    }
			    );
			}else{
				layer.msg('不可重复删除');
			} 
			
			
		 }
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		//弹出窗 函数 
        function openWindowsBM(url, title, w, h) {
			w = isNull(w) ? 800 : w;
			h = isNull(h) ? 530 : h;
			var ww = ($(document).width() < w ? $(document).width() - 30 : w)+ "px";
			var hh = ($(document).height() < h ? $(document).height() - 30 : h)+ "px";
			layer.open({
				type : 2,
				area : [ww,hh],
				title : isNull(title) ? "信息" : title,
				fix : false, //不固定
				maxmin : false,
				shade : 0.3,
				shift : 0, //0-6的动画形式，-1不开启
				content : url,
				shadeClose : true,
			});
		}
		function addAllsysBadsql(){
			if('${sysBadsql.delFlag}'!='1'){
				layer.confirm("确认要补传吗？", {
			        btn: ['确认','取消'], //按钮
			        shade: true, //不显示遮罩
			        icon : 3,
			        offset : ['300px' , '35%']
			    }, function(index){
				    	layer.close(index);
				    	loading("正在执行...");
				    	var checkboxlength=$("input[name='ckb']:checked").length;
				    	if(checkboxlength==0){
				    		layer.msg('请选择补录项！');
				    		return;
				    	}
				    	var id="";
				    	var ids = new Array();
				    	$("[name='ckb']:checkbox").each(function(){
							if($(this).is(":checked")){
								id="";
								id=$(this).val();
								ids.push(id);
							}
						})
				    	//alert(ids);
				    	$.ajax({
							type : "POST",
							url  : "${ctx}/sys/sysBadsql/sqlqueue",
							traditional:true,
							data :{"ids":ids},  
							success : function(data) {
								layer.msg(data,{time:1500},function(){
								 	location.reload();
								 }); 	  	   
							},
							
						});
				    }
			    );
			}else{
				layer.msg('不可重复补录');
			} 
			
		}
		
		function addsysBadsql(){
			
       	 	openWindowsBM(ctx +  "/sys/sysBadsql/form", "sql上报失败添加", "", "");
        }
		
		 function changeA(id){
	        openWindowsBM(ctx +  "/sys/sysBadsql/form?id=" + id, "sql上报失败修改", "", "");
	     }
		 
	        /* 删除单个sql上报失败  */
			function deleteInfo(id){
				if('${sysBadsql.delFlag}'!='1'){
					layer.confirm("确认要删除吗？", {
				        btn: ['确认','取消'], //按钮
				        shade: true, //不显示遮罩
				        icon : 3,
				        offset : ['300px' , '35%']
				    }, function(index){
					    	layer.close(index);
					    	loading("正在执行...");
					    	
					        $.ajax({
								type : "POST",
								url  : "${ctx}/sys/sysBadsql/delete?id="+id,
								success : function(data) {
										 layer.msg(data,{time:1500},function(){
										 	location.reload();
										 });	  	   
								}
							});
					    }
				    );
				}else{
					layer.msg('不可重复删除');
				} 
			}
		
		
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/sysBadsql/">sql上报失败列表</a></li>
		<shiro:hasPermission name="sys:sysBadsql:edit"><li><a href="${ctx}/sys/sysBadsql/form">sql上报失败添加</a></li></shiro:hasPermission>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="sysBadsql" action="${ctx}/sys/sysBadsql/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/> -->
					<!-- <input class="btn btn-primary" type="button" value="添加" onclick="addsysBadsql()"/> -->
					<input class="btn btn-primary" type="button" value="批量删除" onclick="deletesysBadsql()"/>
					<input class="btn btn-primary" type="button" value="批量补传" onclick="addAllsysBadsql()"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td width="100px"><input id="checkboxAll" class="checkboxAll" type="checkbox" /><label for="checkboxAll">全选/全不选 </label></td>
						<td>序号</td>
						<td>时间</td>
						<td>内容</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
				<c:forEach begin="0" items="${page.list}" var="sysBadsql" varStatus="status">
					<%-- <tr>
						<shiro:hasPermission name="sys:sysBadsql:edit"><td>
							<a href="${ctx}/sys/sysBadsql/form?id=${sysBadsql.id}">修改</a>
							<a href="${ctx}/sys/sysBadsql/delete?id=${sysBadsql.id}" onclick="return confirmx('确认要删除该sql上报失败吗？', this.href)">删除</a>
						</td></shiro:hasPermission>
					</tr> --%>
					
					<tr>
						<td align="center"><input type="checkbox" name="ckb" value="${sysBadsql.id }"/></td>
						<td class="text-center">${status.index+1}</td>
						<td>
							<fmt:formatDate value="${sysBadsql.sqlTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
						</td>
						<td width="1300px">
							${sysBadsql.sqlContext}
						</td>
						<td class="text-center">
							${sysBadsql.sqlStatus} 
						</td>
						<td>
							<a href="javascript:void(0)" class="btn btn-success" onclick="changeA(this.rel)" rel="${sysBadsql.id}">修改</a>
							<a href="javascript:void(0)" class="btn btn-danger" onclick="deleteInfo(this.rel)" rel="${sysBadsql.id}">删除</a>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>