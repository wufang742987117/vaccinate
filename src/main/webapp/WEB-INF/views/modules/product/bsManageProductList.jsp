<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>疫苗产品配置</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			/* 初始化数据 */
			if('${bsManageProduct.check0}' == '1'){
				$("#check0").prop("checked",true);
				$("#check0").parent().addClass("checked");
			}else{
				$("#check0").attr("checked",false);
				$("#check0").parent().removeClass("checked");
			}
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
		
		
		
        function alertForm(url,title){
			var w = 810;
			var h = 530;
			var ww = ($(document).width() < w ? $(document).width()-30 : w) + "px";
			var hh = ($(document).height() < h ? $(document).height()-30 : h) + "px";
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
		        	/* window.location.href="${ctx}/product/bsManageProduct/"; */
		        }
		    });
		}
        
		function closeForm(){
			layer.closeAll();
			window.location.href="${ctx}/product/bsManageProduct/";
		}
		
		function outPut(pid){
			alertForm(ctx + "/manage_stock_in/manageStockIn/stockOutForm?product.id="+pid,"出库", '800', '390');
		}
		
		//出库保存后回调
		function afterStockOut(){
			window.location.reload();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/product/bsManageProduct/">疫苗库存管理</a></li>
		<li ><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li ><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul> 
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsManageProduct" action="${ctx}/product/bsManageProduct/" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
				<div class = "col-xs-11" style="padding-left: 0;">
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">疫苗名称：</span>
								<form:select path="vaccineid" class="form-control">
									<form:option value="" label="--请选择--"/>
									<form:options items="${vacclist }" itemLabel="vaccName" itemValue="vaccineid" htmlEscape="false"/>
								</form:select>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">批号：</span>
								<form:input path="batchno" class="form-control"/>
						</div>
					</div>
					<div class="form-group" style="margin-left: 2%">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">生产企业：</span>
								<%-- <form:input path="manufacturer" htmlEscape="false" maxlength="50" class="form-control"/> --%>
								<form:select path="code" class="form-control">
									<form:option value="" label="--请选择--"/>
									<form:options items="${EnterpriseInfoList}" itemLabel="name" itemValue="code" htmlEscape="false"/>
								</form:select>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">疫苗类型：</span>
								<%-- <form:radiobuttons path="isforeign" items="${fns:getDictList('isImport')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'"/> --%>
								<form:select path="isforeign" class="form-control">
									<form:option value="" label="--请选择--"/>
									<form:options items="${fns:getDictList('isImport')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
						</div>
					</div>
					<%-- <div class="form-group" style="margin-left: 13%">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">是否显示：</span>
								<form:radiobuttons path="isshow" items="${fns:getDictList('isShow')}" itemLabel="label" itemValue="value" htmlEscape="false" element="label class='checkbox-inline i-checks'"/>
								<form:select path="isshow" class="form-control">
									<form:option value="" label="--请选择--"/>
									<form:options items="${fns:getDictList('isShow')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
						</div>
					</div> --%>
					<div class="form-group">
						<label class="mr15"><input id="check0" name="check0" type="checkbox" value="1" class="i-checks"><i></i><span>维护0库存</span></label>
						<form:checkbox path="showAll" label="全部显示" value="1" cssClass="checkbox-inline i-checks"/>
						<input id="btnSubmit" class="btn btn-primary mr10 w100" type="submit" value="查询" />
<%-- 						<a class="btn btn-primary mr10 w100" type="button" href="${ctx}/product/bsManageProduct/check">盘点</a> --%>
						<%-- <a class="btn btn-primary mr10" href="javascript:void(0);"  onclick="alertForm('${ctx}/product/bsManageProduct/form','添加');"><span class="glyphicon glyphicon-plus"></span>添加</a> --%>
					</div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<!-- <th>id</th> -->
						<th>疫苗名称</th>
						<!-- <th>英文名称</th> -->
						<th>批号</th>
						<th>生产企业</th>
						<!-- <th>生产企业编码</th> -->
						<th>规格</th>
<!-- 						<th>每盒剂次</th> -->
						<th>剂量</th>
						<th>类型</th>
						<th>库存</th>
						<th>价格</th>
						<th>归属科室</th>
						<!-- <th>是否显示</th> -->
						<th>有效期</th>
						<th>入库时间</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="bsManageProduct">
					<tr>
						<%-- <td><a href="${ctx}/product/bsManageProduct/form?id=${bsManageProduct.id}"> ${bsManageProduct.name} </a></td> --%>
						<%-- <td> ${bsManageProduct.id} </td> --%>
						<td class="text-center"> ${bsManageProduct.name} </td>
						<%-- <td> ${bsManageProduct.codeall} </td> --%>
						<td class="text-center"> ${bsManageProduct.batchno} </td>
						<td class="text-center"> ${bsManageProduct.code}_${bsManageProduct.manufacturer} </td>
						<%-- <td> ${bsManageProduct.code} </td> --%>
						<td class="text-center"> ${bsManageProduct.spec} </td>
<%-- 						<td class="text-center"> ${bsManageProduct.dosage} </td> --%>
						<td class="text-center"> ${fns:getDictLabel(bsManageProduct.specification, 'specification', '')} </td>
						<td class="text-center"> ${fns:getDictLabel(bsManageProduct.isforeign, 'isImport', '')} </td>
						<td class="text-center"> ${bsManageProduct.storenum} </td>
						<td class="text-center"> <fmt:formatNumber>${bsManageProduct.sellprice}</fmt:formatNumber>  </td>
						<td class="text-center"> ${bsManageProduct.officeCodeDb}</td>
						<%-- <td> ${fns:getDictLabel(bsManageProduct.isshow, 'isShow', '')} </td> --%>
						<td class="text-center"> <fmt:formatDate value="${bsManageProduct.vaccExpDate}" pattern="yyyy-MM-dd"/> </td>
						<td class="text-center"> <fmt:formatDate value="${bsManageProduct.createDate}" pattern="yyyy-MM-dd"/> </td>
							<td class="text-center" >
								 <a href="javascript:void(0);" class="btn btn-xs btn-danger" onclick="alertForm('${ctx}/product/bsManageProduct/form?id=${bsManageProduct.id}', '修改疫苗信息');">修改</a>
<%-- 								 <a href="${ctx}/product/bsManageProduct/delete?id=${bsManageProduct.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>  --%>
								<input type="button" class="btn btn-xs btn-success" value="出库" onclick="outPut('${bsManageProduct.id}')" />
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