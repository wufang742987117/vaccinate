<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>联合疫苗替代原则管理</title>
	<meta name="decorator" content="default"/>
	<style>
		.layui-layer-dialog .layui-layer-padding {padding: 40px 20px 20px 80px!important;text-align: left;font-size: 25px!important;height:auto!important;}
		.layui-layer-dialog .layui-layer-content .layui-layer-ico {top: 38px!important;left: 35px!important;}
		.layui-layer-btn a {height: 35px;line-height: 35px;padding: 0 20px;font-size:16px;}
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
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
        
        /* 添加联合疫苗替代原则 */
        function addBsManageBinded(){
       	 	openWindowsBM(ctx +  "/vaccine/bsManageBinded/form", "疫苗替代原则添加", "", "");
        }
        
        /* 修改联合疫苗替代原则 */
        function changeA(id){
        	openWindowsBM(ctx +  "/vaccine/bsManageBinded/form?id=" + id, "疫苗替代原则修改", "", "");
        }
        
        /* 删除单个联合疫苗替代原则  */
		function deleteInfo(id){
			if('${bsManageBinded.delFlag}'!='1'){
				layer.confirm("确认要删除吗？", {
			        btn: ['确认','取消'], //按钮
			        shade: true, //不显示遮罩
			        icon : 3,
			        area : ['500px' , '200px']
			    }, function(index){
				    	layer.close(index);
				    	loading("正在执行...");
				    	
				        $.ajax({
						type : "POST",
						url  : "${ctx}/vaccine/bsManageBinded/delete?id="+id,
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
		<li ><a href="${ctx}/product/bsManageProduct/">疫苗产品配置</a></li>
		<li ><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li class="active"><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsManageBinded" action="${ctx}/vaccine/bsManageBinded/" method="post" class="form-inline">
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
				
				<input id="pageNo" name="" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="" type="hidden" value="${page.pageSize}"/>
				
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗名称：</span>
							<form:select path="vaccId" class="form-control">
								<form:option value="" label="--请选择--"/>
								<form:options items="${vaccineList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
					</div>
				</div>
				
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
					<input class="btn btn-primary" type="button" value="添加" onclick="addBsManageBinded()"/>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>被联合疫苗 / 编号</th>
						<th>被联合疫苗剂次</th>
						<th>联合疫苗 / 编号</th>
						<th>联合疫苗剂次</th>
						<th>默认使用疫苗</th>
						<th>操作</th>
						<%-- <shiro:hasPermission name="vaccine:bsManageBinded:edit"><th>操作</th></shiro:hasPermission> --%>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsManageBinded">
					<tr>
						<td><a href="javascript:void(0)"  data-toggle="toltip" title="点击查看详情并可修改" onclick="changeA(this.rel)" rel="${bsManageBinded.id}">
							${bsManageBinded.vaccine.name} / ${bsManageBinded.vaccId}
						</a></td>
						<td>
							${bsManageBinded.vaccPin}
						</td>
						<td>
							${bsManageBinded.bindName} / ${bsManageBinded.bindVaccId}
						</td>
						<td>
							${bsManageBinded.bindVaccPin}
						</td>
						<td>
							${bsManageBinded.defaultName}
						</td>
						<td>
							<a href="javascript:void(0)" onclick="changeA(this.rel)" rel="${bsManageBinded.id}">修改</a>
							<a href="javascript:void(0)" onclick="deleteInfo(this.rel)" rel="${bsManageBinded.id}">删除</a>
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