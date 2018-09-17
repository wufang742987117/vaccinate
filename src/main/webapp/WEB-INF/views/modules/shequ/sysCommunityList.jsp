<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>社区配置</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	.w130{
	    width: 150px !important;
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
		        	/* window.location.href="${ctx}/shequ/sysCommunity"; */
		        }
		    });
		}
        
		function closeForm(){
			layer.closeAll();
			window.location.href="${ctx}/shequ/sysCommunity";
		}
		
		function transfer(thi){
			showa();
			var _this = $(thi);
			var coms = ${not empty coms ? coms : ""};
			var comss = "";
			for(var i = 0; i < coms.length ; i ++){
				if(_this.attr("data-code")!=coms[i].code){
					comss += "<option value='" + coms[i].code + "'>" + coms[i].name + "</option>";
				}
			}
			aphtml="转入区域:<select id='newarea' class='form-control w150' style='display:inline'>" + comss+
						"</select>&nbsp;&nbsp;" +
						"<button class='btn btn-primary' type='button' onclick='savetrs(\""+_this.attr("data-code")+"\")' >确认</button>&nbsp;&nbsp;"+
						"<button class='btn btn-primary' type='button' onclick='showa()' >取消</button>";
			$(_this).parent().parent().children().hide();
			$(_this).parent().parent().children('#ts').html(aphtml).show();
			
		}
		function showa(){
			$("#contentTable a").each(function(){ 
			    $(this).show(); 
			});
			$("#contentTable span").each(function(){ 
			    $(this).show(); 
			});
			$("#contentTable #ts").each(function(){ 
			    $(this).html(""); 
			});
		}
		function savetrs(code){
			comconfirmx('确定转移(此操作不可逆)？',function(){
				var load_idx = layer.load();
				$.ajax({
					url:"${ctx}/shequ/sysCommunity/savetrs",
					data:{"code":code,"newarea":$("#newarea").val()},
					type:"POST",
					success:function(data){
						if(data == "200"){
							layer.msg("转移社区儿童信息成功",{"icon":1});
						}else{
							layer.msg(data);
						}
						setTimeout(funB, 1400);
					},
					error:function(){
						layer.msg("转移社区儿童信息失败",{"icon":7});
					}
				});
        	});

		}
		
		function funB() {
			window.location.reload();//刷新当前页面.
		}
		
		function comconfirmx(mess, href) {
				layer.confirm(mess, {
					btn : [ '确认', '取消' ], //按钮
					shade : true, //不显示遮罩
					icon : 3,
					area : [ '500px', '200px' ]
				}, function(index) {
					layer.close(index);
					if (typeof href == 'function') {
						href();
					} else {
						location = href;
					}

				}, function() {
					layer.close();
					showa();
				});
			return false;
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
		<li class="active"><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="sysCommunity" action="${ctx}/shequ/sysCommunity/" method="post" class="form-inline mt20">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="col-xs-1 pull-right">
					<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
				</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">社区编码：</span>
									<form:input path="code" htmlEscape="false" maxlength="50" class="form-control"/>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">名称：</span>
									<form:input path="name" htmlEscape="false" maxlength="100" class="form-control"/>
							</div>
						</div>
						<%-- <div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">备注：</span>
									<form:input path="remarks" htmlEscape="false" maxlength="200" class="form-control"/>
							</div>
						</div> --%>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>&emsp;&emsp;&emsp;
					<a class="btn btn-primary w100" href="javascript:void(0);"  onclick="alertForm('${ctx}/shequ/sysCommunity/form','添加');"><span class="glyphicon glyphicon-plus"></span>添加</a>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>社区编码</th>
						<th>名称</th>
						<th>人数</th>
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="sysCommunity">
					<tr>
						<td>
							${sysCommunity.code}
						</td>
						<td>
							${sysCommunity.name}
						</td>
						<td>
							${sysCommunity.count}
						</td>
						<td>
							${sysCommunity.remarks}
						</td>
						<td>
						<%-- 	<a href="${ctx}/shequ/sysCommunity/form?id=${sysCommunity.id}">修改</a> --%>
							<a href="javascript:void(0);" onclick="alertForm('${ctx}/shequ/sysCommunity/edit?id=${sysCommunity.id}', '修改社区信息');">修改</a>&emsp;&emsp;
							<c:if test="${sysCommunity.count==0}">
								<a href="${ctx}/shequ/sysCommunity/delete?id=${sysCommunity.id}" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除&emsp;&emsp;</a>
							</c:if>
							<c:if test="${sysCommunity.count>0}">
								<span id="trs"><button class="btn btn-primary" onclick="transfer(this)" type="button" data-code="${sysCommunity.code}">儿童转移</button></span>
							</c:if>
							<span id="ts"></span>
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