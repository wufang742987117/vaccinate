<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接种台</title>
<style type="text/css">
	.numbertitle span{
		font-size: 50px !important;
		margin: 20px !important;
	}
	
	.ml2{
		margin-left:2px;
	}
	.mr100{
		margin-right: 100px;
	}
	
	/* .finished{
		color:#999;
	} */
	.chooseed{
		color:red;
	}
	.normal{
		color:black;
	}
	
	.scroll{
		height:500px; 
		overflow: auto;
	}
	
	.rechoosed{
		background: rgba(131, 102, 241, 0.32) !important;
	}
	
	.tips{
		font-size: 20px;
	}
</style>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(function() {
		$("#callNumber").click(function(){
			$.ajax({
				url:"${ctx}/inoculate/quene/callNumber?queueno=${quene.queueno}",
				methed:"POST",
				success:function(data){
					layer.msg(data,{icon:1});
				}
			});
		});
		
		//计算现在有几个已登记的记录
/* 		var dd = "${recStatusNo}".split("_");
		if(dd.length > 3){
			$("#selected").val("");
			$("#vaccID").val("");
		} */
	});
	
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}

	function submitYouFrom() {
		/* $("#searchForm").action = path; */
		$("#searchForm").submit();
	}
	
	//避免输入错误页面刷新
	var oldcode = ${not empty childBaseinfo.childcodeEnd ? childBaseinfo.childcodeEnd : -1}
	
	$(function(){
		$("#codeInput input").mouseover(function(){
			var v = $(this).val();
			$(this).focus().val(v);
		});
		
		$("#childcodePre").keyup(function(){
			if(isNaN($("#childcodePre").val())){
				layer.msg("儿童编码应为数字", {icon: 2});
			}else  if($(this).val().length == 12){
				var v = $("#childcodeEnd").val();
				$("#childcodeEnd").focus().val(v);
			}
		});
		
		$("#childcodeEnd").keyup(function(){
			if(isNaN($("#childcodeEnd").val())){
				layer.msg("儿童编码应为数字", {icon: 2});
			}else if($(this).val().length == 6 && oldcode != $(this).val()){
				window.location.href="${ctx}/inoculate/quene?childid=" + $("#childcodePre").val() + $("#childcodeEnd").val()
			}
		});
		
		
 		$(".confirmVacc").click(function(){
 			if(!$(this).parent().parent().hasClass("rechoosed")){
 				$("#selected").val($(this).attr("data-id"));
 				$("#vaccID").val($(this).attr("data-vaccid"));
 				$("#pid").val($(this).attr("data-pid"));
 				$(".confirmVacc").each(function(){
 					$(this).parent().parent().removeClass("rechoosed");
 				});
 				$(this).parent().parent().addClass("rechoosed");
 			}
 		});
 		
 		$(".option").click(function(){
 			if(!$(this).hasClass("rechoosed")){
 				$("#selected").val($(this).find("a").attr("data-id"));
 				$("#vaccID").val($(this).find("a").attr("data-vaccid"));
 				$("#pid").val($(this).find("a").attr("data-pid"));
 				$(".option").each(function(){
 					$(this).removeClass("rechoosed");
 				});
 				$(this).addClass("rechoosed");
 			}
 		});
 		
// 自动选择需要接种的疫苗
/* 		$(".option a").each(function(){
 			if($(this).attr("data-id") == ${not empty quene.vaccineid ? quene.vaccineid : -1} 
			&& $(this).attr("data-pid") == ${not empty quene.pid ? quene.pid : -1}){
 					$(this).click();
 			}
 		});*/
 		
 
 	});
	
	
	var newFrame;
	function openWindows(url,title,isReflash){
		newFrame = layer.open({
	        type: 2,
	        area: [$(document).width()-200+'px',$(document).height()-100+'px'],
	        title: isNull(title) ? "信息" : title,
	        fix: false, //不固定
	        maxmin: false,
	        shade: 0.3,
	        shift: 0, //0-6的动画形式，-1不开启
	        content: url,
	        closeBtn:1,
	    });
	    //layer.full(i);
	    
	}
	
	//重新叫号
	function reCallNum(url){
		//layer.close(newFrame);
		layer.closeAll();
		window.location.href=url;
		
	}
	
	function myConfirmx(mess, href){
	    layer.confirm(mess, {
	        btn: ['确认','取消'], //按钮
	        shade: true, //不显示遮罩
	        icon : 3,
	        offset : ['300px' , '35%']
	    }, function(index){
	    	var v = document.getElementById('selected').value;
	    	if(!v){
	    		layer.msg("请选择要接种的疫苗", {icon: 2}); 
	    		return;
	    	}
	    	if(href.indexOf("selected=") > 0){
	    		href += v; 
	    	}
	    	href += "&pid=" + document.getElementById('pid').value;
	    	/* 计数板减一 */
	   		parent.minus(document.getElementById('vaccID').value);
	    	layer.close(index);
	    	loading("正在执行...");
	        if (typeof href == 'function') {
	            href();
	        }else{
	            location = href;
	        }
	       
	    }, function()
	    {
	        layer.close();
	    });
		return false;
	}
</script>
</head>
<body>
<%-- 	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/inoculate/quene">顺序叫号</a></li>
		<li><a href="${ctx}/inoculate/quene/list">排队列表<span class="label label-primary ml2">${queneN}</span></a></li>
		<li><a href="${ctx}/inoculate/quene/listY">过号列表<span class="label label-warning ml2">${queneY}</span></a></li>
	</ul> --%>
	<div class="ibox">
		<div class=" col-sm-12">
			<form:form id="searchForm" modelAttribute="childVaccinaterecord"
				action="${ctx}/child_vaccinaterecord/childVaccinaterecord1/list1"
				method="post" class="form-inline">
				<div class="form-group numbertitle">
					<span>当前排号:</span><span style="color:red">${not empty quene ? quene.queueno : '' }</span>
				</div>

			</form:form>
		</div>
		<div class="row" style="background-color: #ffffff;">
			<div class=" col-sm-3">
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>

						<tr>
							<td class="text-right">儿童编码</td>
							<td id="codeInput"><input type="text"  style="width: 90px;" class="number" id="childcodePre" value="${empty childBaseinfo.childcodePre ? childcodePre : childBaseinfo.childcodePre}">&nbsp;<input type="text" class="number" style="width: 65px;" id="childcodeEnd" value="${childBaseinfo.childcodeEnd}"></td>
						</tr>
						<tr>
							<td class="text-right">身份证号</td>
							<td>${childBaseinfo.cardcode}</td>
						</tr>
						<tr>
							<td class="text-right">出生证号</td>
							<td>${childBaseinfo.birthcode}</td>
						</tr>
						<tr>
							<td class="text-right">儿童姓名</td>
							<td>${childBaseinfo.childname}</td>
						</tr>
						<tr>
							<td class="text-right">性别</td>
							<td>${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}</td>
						</tr>
						<tr>
							<td class="text-right">出生日期</td>
							<td><fmt:formatDate value="${childBaseinfo.birthday}"
									pattern="yyyy-MM-dd" /></td>
						</tr>
						<tr>
							<td class="text-right">出生医院</td>
							<td>${childBaseinfo.birthhostipal}</td>
						</tr>
						<tr>
							<td class="text-right">出生体重(克)</td>
							<td>${childBaseinfo.birthweight}</td>
						</tr>
						<tr>
							<td class="text-right">母亲</td>
							<td>${childBaseinfo.guardianname}</td>
						</tr>
						<tr>
							<td class="text-right">手机号码</td>
							<td>${childBaseinfo.guardianmobile}</td>
						</tr>
						<tr>
							<td class="text-right">关系</td>
							<td>${fns:getDictLabel(childBaseinfo.guardianrelation, 'relation', '')}</td>
							
							</td>
						</tr>
						<tr>
							<td class="text-right">家庭住址</td>
							<td>${childBaseinfo.homeaddress}</td>
						</tr>
						<tr>
							<td class="text-right">户籍地址</td>
							<td>${childBaseinfo.registryaddress}</td>
						</tr>
						<tr>
							<td class="text-right">异常反应</td>
							<td>${fns:getDictLabel(childBaseinfo.paradoxicalreaction, 'FN', '')}
							</td>
						</tr>
						<tr>
							<td class="text-right">接种单位</td>
							<td>${childBaseinfo.officeinfo}</td>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<!-- 已接种记录 -->
			
			<div class=" col-sm-5 scroll" >
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							
							<th>名称</th>
							<th>剂次</th>
							<th>接种日期</th>
							<th>接种部位</th>
							<!-- <th>接种单位</th> -->
							<!-- <th>医生姓名</th> -->
							<%-- <shiro:hasPermission name="child:cord:edit">
								<th>操作</th>
							</shiro:hasPermission> --%>
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${records}" var="childVaccinaterecord" >
							<c:if test="${childVaccinaterecord.status == 1 }">
							<tr >
								<td>${childVaccinaterecord.name}</td>
								<td>${childVaccinaterecord.dose}</td>
								<td><fmt:formatDate
										value="${childVaccinaterecord.vaccinatedate}"
										pattern="yyyy-MM-dd" /></td>
								<td>${fns:getDictLabel(childVaccinaterecord.bodypart, 'position', '')}</td>
								<%-- <td>${childVaccinaterecord.batch}</td> --%>
								<%-- <td>${childVaccinaterecord.office}</td> --%>
								<%-- <td>${childVaccinaterecord.doctor}</td> --%>
								<%-- <td><c:if test="${childVaccinaterecord.status == 0 }"><a href="javascript:void(0);" data-id="${childVaccinaterecord.dosage}" data-vaccid="${childVaccinaterecord.vaccineid }" class="confirmVacc">确认</a></c:if></td> --%>
								
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">${page}</div>
			</div>
			
			<!-- 未接种记录 -->
			<div class=" col-sm-4 scroll" >
				<sys:message content="${message}" />
				<input id = "selected" type="hidden" value="${quene.vaccineid}">
				<input id = "vaccID" type="hidden" value="${quene.vaccine.id}"/>
				<input id = "pid" type="hidden" value="${quene.pid}"/>
				<table id="contentTable"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							
							<th>名称</th>
							<th>剂次</th>
							<!-- <th>接种日期</th> -->
							<th>接种部位</th>
							<th>价格</th>
							<shiro:hasPermission name="child:cord:edit">
								<th>操作 </th>
							</shiro:hasPermission>
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${nums}" var="num" >
						<c:set var="nid" value="_${num.id }_"/>
							<tr
							<c:choose>
								<c:when test="${fn:contains(recStatusNo,nid) == true and quene.pid == num.product.id}">
									class="chooseed option"
								</c:when>
								<c:otherwise>
									class="option"
								</c:otherwise>
							</c:choose>
							
							>
								<td>${num.name}</td>
								<td>${num.pin}</td>
								<%-- <td><fmt:formatDate
										value="${childVaccinaterecord.vaccinatedate}"
										pattern="yyyy-MM-dd" /></td> --%>
								<td>${fns:getDictLabel(quene.bodypart, 'position', '')}</td>
								<%-- <td>${childVaccinaterecord.batch}</td> --%>
								<%-- <td>${childVaccinaterecord.office}</td> --%>
								<%-- <td>${childVaccinaterecord.doctor}</td> --%>
								<td>${num.product.sellprice}</td>
									<td>
											<a href="javascript:void(0);" data-id="${num.id}" data-vaccid="${num.vaccineid }"  data-pid="${num.product.id }" class="confirmVacc">确认</a>
										</td>

								</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="page">${page}</div>
			</div>
			
			
		</div>
		<div class="container" >
			<div class="row"> 
			<c:choose>
				<c:when test="${not empty isTemp }">
				
					<div class="col-xs-1 col-xs-offset-3">
						<a class="btn btn-lg btn-success btnFinish" data-id="" href="${ctx}/inoculate/quene/doFinish?childid=${quene.childid}&selected=" onclick="return myConfirmx('确认要完成本次操作吗？', this.href)">完成</a>
					</div>
					<div class="col-xs-1">
						<a class="btn btn-lg btn-default" href="${ctx}/inoculate/quene">取消</a>
					</div>
				</c:when>
				<%-- <c:when test="${not empty isPass }">
				
					<div class="col-xs-1 col-xs-offset-3">
						<a class="btn btn-lg btn-success btnFinish" data-id=""  href="${ctx}/inoculate/quene/doFinish?queueno=${quene.queueno}" onclick="return confirmx('确认要完成本次操作吗？', this.href)">完成</a>
					</div>
					<div class="col-xs-1">
						<a class="btn btn-lg btn-default" href="${ctx}/inoculate/quene/doCancel?queueno=${quene.queueno}" onclick="return confirmx('确认要取消该排号吗？', this.href)">取消</a>
					</div>
				</c:when> --%>
				
				<c:otherwise>
					<div class="col-xs-1 col-xs-offset-3">
						<a href="javascript:void(0);" class="btn btn-lg btn-primary" id="callNumber">叫号</a>
					</div>
					<div class="col-xs-1">
						<a class="btn btn-lg btn-warning" href="${ctx}/inoculate/quene/doPass?queueno=${quene.queueno}" onclick="return confirmx('确认跳过？', this.href)">过号</a>
					</div>
					<div class="col-xs-1">
						<a class="btn btn-lg btn-success btnFinish" data-id=""  id="btnFinish" href="${ctx}/inoculate/quene/doFinish?queueno=${quene.queueno}&selected=" onclick="return myConfirmx('确认要完成本次操作吗？', this.href)">完成</a>
					</div>
					<div class="col-xs-1">
						<a class="btn btn-lg btn-default" href="${ctx}/inoculate/quene/doCancel?queueno=${quene.queueno}" onclick="return confirmx('确认要取消该排号吗？', this.href)">取消</a>
					</div>
				</c:otherwise>
			</c:choose>
			</div>
		</div>
	</div>
	
		<div class="tips" >
			<div class="row pull-right mr100 ">
				今日完成接种人数<input type="button" class="btn btn-success ml2" onclick="openWindows('${ctx}/inoculate/quene/listF', '今日完成接种列表')" value="${finished}">&nbsp;&nbsp;
				当前排队人数<input type="button"  class="btn btn-primary ml2" onclick="openWindows('${ctx}/inoculate/quene/list', '当前排队列表')" value="${queneN}">&nbsp;&nbsp;
				过号人数<input type="button" class="btn btn-warning ml2" onclick="openWindows('${ctx}/inoculate/quene/listY','过号列表')" value="${queneY}">
			</div>
		</div>
	


</body>
</html>