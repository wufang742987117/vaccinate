<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>幼儿园配置</title>
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
		 function alertForm(url,title)
			{
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
			        	/* window.location.href="${ctx}/yiyuan/sysBirthHospital"; */
			        }
			    });
			}
		 function closeForm(){
				layer.closeAll();
				window.location.href="${ctx}/kindergarten/bsKindergarten";
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
		<li class="active"><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="bsKindergarten" action="${ctx}/kindergarten/bsKindergarten/" method="post" class="form-inline">
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">幼儿园编码：</span>
									<form:input path="kindergartenCode" htmlEscape="false" maxlength="3" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">单位全称：</span>
									<form:input path="nameAll" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">单位简称：</span>
									<form:input path="name" htmlEscape="false" maxlength="32" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">地址：</span>
									<form:input path="address" htmlEscape="false" maxlength="255" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">联系人：</span>
									<form:input path="contacts" htmlEscape="false" maxlength="5" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">联系电话：</span>
									<form:input path="teletephone" htmlEscape="false" maxlength="11" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">手机：</span>
									<form:input path="moblie" htmlEscape="false" maxlength="11" class="form-control"/>
							</div>
						</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>&emsp;&emsp;&emsp;
					<a class="btn btn-primary w100" href="javascript:void(0);"  onclick="alertForm('${ctx}/kindergarten/bsKindergarten/form','添加');"><span class="glyphicon glyphicon-plus"/>添加</a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>幼儿园编码</th>
						<th>单位全称</th>
						<th>单位简称</th>
						<th>地址</th>
						<th>联系人</th>
						<th>联系电话</th>
						<th>手机</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsKindergarten">
					<tr>
						<td><a href="${ctx}/kindergarten/bsKindergarten/form?id=${bsKindergarten.id}">
							${bsKindergarten.kindergartenCode}
						</a></td>
						<td>
							${bsKindergarten.nameAll}
						</td>
						<td>
							${bsKindergarten.name}
						</td>
						<td>
							${bsKindergarten.address}
						</td>
						<td>
							${bsKindergarten.contacts}
						</td>
						<td>
							${bsKindergarten.teletephone}
						</td>
						<td>
							${bsKindergarten.moblie}
						</td>
						<td>
							${bsKindergarten.remarks}
						</td>
						<td>
							<a href="javascript:void(0);" onclick="alertForm('${ctx}/kindergarten/bsKindergarten/form?id=${bsKindergarten.id}', '修改幼儿园信息');">修改</a>&emsp;&emsp;
							<a href="${ctx}/kindergarten/bsKindergarten/delete?id=${bsKindergarten.id}" onclick="return confirmx('确认要删除该幼儿园信息吗？', this.href)">删除</a>
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