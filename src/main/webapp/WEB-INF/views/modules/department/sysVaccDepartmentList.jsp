<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接种门诊配置</title>
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
        
        function alertForm(url,title){
			var ww = ($(document).width() < 800 ? $(document).width()-30 : 800) + "px";
			var hh = ($(document).height() < 500 ? $(document).height()-30 : 550) + "px";
		    layer.open({
		        type: 2,
		        area: [ww,hh],
		        title: isNull(title) ? "信息" : title,
		        fix: false, //不固定
		        maxmin: false,
		        shade: 0.3,
		        shift: 0, //0-6的动画形式，-1不开启
		        content: url,
		        cancel:function(){
		        	/* window.location.href="${ctx}/department/sysVaccDepartment"; */
		        }
		    });
		}
        
		function closeForm(){
			layer.closeAll();
			window.location.href="${ctx}/department/sysVaccDepartment";
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/product/bsManageProduct/">疫苗库存管理</a></li>
		<li ><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li class="active"><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
		<div>
			<form:form id="searchForm" modelAttribute="sysVaccDepartment" action="${ctx}/department/sysVaccDepartment/" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="col-xs-1 pull-right">
							<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">门诊编码：</span>
									<form:input path="code" htmlEscape="false" maxlength="20" class="form-control"/>
							</div>
						</div>
						<div class="form-group" style="margin-left: 2%">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">门诊简称：</span>
									<form:input path="name" htmlEscape="false" maxlength="50" class="form-control"/>
							</div>
						</div>
						<%-- <div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">门诊全称：</span>
									<form:input path="allName" htmlEscape="false" maxlength="100" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">code级别：</span>
									<form:input path="codeLevel" htmlEscape="false" maxlength="10" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">父节点code：</span>
									<form:input path="fCode" htmlEscape="false" maxlength="20" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">新code：</span>
									<form:input path="nCode" htmlEscape="false" maxlength="20" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">备注：</span>
									<form:input path="remarks" htmlEscape="false" maxlength="100" class="form-control"/>
							</div>
						</div> --%>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>&emsp;&emsp;&emsp;
					<a class="btn btn-primary w100" href="javascript:void(0);"  onclick="alertForm('${ctx}/department/sysVaccDepartment/form','新增');"><span class="glyphicon glyphicon-plus"/>新增</a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>门诊编码</th>
						<th>门诊简称</th>
						<th>门诊全称</th>
						<!-- <th>code级别</th>
						<th>父节点code</th>
						<th>新code</th> -->
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sysVaccDepartment">
					<tr>
						<td>
							${sysVaccDepartment.code}
						</td>
						<td>
							${sysVaccDepartment.name}
						</td>
						<td>
							${sysVaccDepartment.allName}
						</td>
						<%-- <td>
							${sysVaccDepartment.codeLevel}
						</td>
						<td>
							${sysVaccDepartment.fCode}
						</td>
						<td>
							${sysVaccDepartment.nCode}
						</td> --%>
						<td>
							${sysVaccDepartment.remarks}
						</td>
						<td>	
							<%-- <a href="${ctx}/department/sysVaccDepartment/form?id=${sysVaccDepartment.id}">修改</a> --%>
							<a href="javascript:void(0);"  onclick="alertForm('${ctx}/department/sysVaccDepartment/form?id=${sysVaccDepartment.id}','修改');">修改</a>
							<a href="${ctx}/department/sysVaccDepartment/delete?id=${sysVaccDepartment.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
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