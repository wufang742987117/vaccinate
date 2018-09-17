<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>儿童免疫程序配置</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.text-circle{
			display: block;
			margin: 0 auto;
			width: 15px;
			height: 15px;
			border: 1px #000 solid;
			border-radius: 15px;
			font-size: .8em;
			text-align: center;
		}
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
			var hh = ($(document).height() < 730 ? $(document).height()-30 : 730) + "px";
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
		        /* 	window.location.href="${ctx}/vaccine/bsManageVaccinenum"; */
		        }
		    });
		}
        
		function closeForm(){
			layer.closeAll();
			window.location.href="${ctx}/vaccine/bsManageVaccinenum";
		}
		
		
	var list=${list};
	function a(){
		var name=$("#name").val();
		if(name==""){
			$("#group").val("");
		}
		$.each(list, function(idx, item) { //循环对象取值
			if(item.gName==name){
				$("#group").val(item.gNum);
				return;
			}
		});
	};
		
	function b(){
	var group=$("#group").val();
	if(group==""){
		$("#name").val("");
	}
	$.each(list, function(idx, item) { //循环对象取值
		if(item.gNum==group){
			$("#name").val(item.gName);
			return;
		}
		});
	};
	
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/product/bsManageProduct/">疫苗库存管理</a></li>
		<li ><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li class="active"><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
		<div>
			<form:form id="searchForm" modelAttribute="bsManageVaccinenum" action="${ctx}/vaccine/bsManageVaccinenum" method="post" class="form-inline mt20">
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">疫苗权重：</span>
									<form:input path="weight" htmlEscape="false" maxlength="50" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">儿童月龄：</span>
									<form:select path="mouage" class="form-control" >
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('mouage')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select> 
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right" >疫苗状态：</span>
								<form:select path="status" class="form-control">
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('status')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select> 
							</div>
						</div>
						<div class="form-group" >
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">疫苗大类：</span>
									<form:select path="name" class="form-control" onchange="a()">
									<form:option value="" label="" />
									<form:options items="${VaccineList}" itemLabel="gName" itemValue="gName" htmlEscape="false" />
							</form:select>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">大类编码：</span>
									<form:select path="group" class="form-control" onchange="b()">
									<form:option value="" label="" />
								<form:options items="${VaccineList}" itemLabel="gNum" itemValue="gNum" htmlEscape="false" />
							</form:select>
							</div>
						</div>
						
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">疫苗类型：</span>
									<form:select path="type" class="form-control" >
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select> 
							</div>
						</div>
						
<%-- 						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">五联关系：</span>
								<form:select path="pentrep" class="form-control" >
									<form:option value="" label="" />
									<form:options items="${fns:getDictList('pentrep')}" itemLabel="label" itemValue="value" htmlEscape="false" />
									</form:select> 
							</div>
						</div> --%>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>&emsp;&emsp;&emsp;
					<a href="javascript:void(0);" class="btn btn-primary w100" onclick="alertForm('${ctx}/vaccine/bsManageVaccinenum/form','添加');">添加</a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
					<th>月龄</th>
					<th>模型大类</th>
					<th>针次</th>
					<th>接种类型</th>
					<th>疫苗类型</th>
					<th>权重</th>
<!-- 					<th>与五联关系</th> -->
					
					<th>状态</th>
					<th>接种方式</th>
					<th>时间间隔 (月)</th>
					<th>最晚接种月龄</th>
					<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsManageVaccinenum">
					<tr>
						<td>
							 ${fns:getDictLabel(bsManageVaccinenum.mouage, 'mouage', '')} 
						</td>
						<td>
							${bsManageVaccinenum.group}_${bsManageVaccinenum.name}_${bsManageVaccinenum.code}
						</td>
						<td>
							<span class="text-circle">${bsManageVaccinenum.pin }</span>
						</td>
						<td>
							${fns:getDictLabel(bsManageVaccinenum.vaccTypeFromNid, 'vacctype', '')}
						</td>
						<td>
							${fns:getDictLabel(bsManageVaccinenum.type, 'type', '')}
						</td>
						<td>
							${bsManageVaccinenum.weight}
						</td>
						<%-- <td>
							${fns:getDictLabel(bsManageVaccinenum.pentrep, 'pentrep', '')}
						</td> --%>
						

						<td>
						${fns:getDictLabel(bsManageVaccinenum.status, 'status', '')}
						</td>
						<td>
						${fns:getDictLabel(bsManageVaccinenum.intype, 'intype', '')}
						</td>
						
						<td>
						${fns:getDictLabel(bsManageVaccinenum.pintime, 'pintime', '')}
						</td>
						<td>
						${fns:getDictLabel(bsManageVaccinenum.lasttime, 'lasttime', '')}
						</td>
						<td>
							<a href="javascript:void(0);" onclick="alertForm('${ctx}/vaccine/bsManageVaccinenum/form?id=${bsManageVaccinenum.id}', '修改');">修改</a>&emsp;&emsp;
							<a href="${ctx}/vaccine/bsManageVaccinenum/delete?id=${bsManageVaccinenum.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
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