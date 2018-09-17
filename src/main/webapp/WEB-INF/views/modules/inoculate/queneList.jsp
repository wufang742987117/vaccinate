<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>排队叫号管理管理</title>
	<style type="text/css">
	
		.numPad{
			width: 70px;
			margin-left: 5px;
			margin-right: 20px;			
		}
		
	</style>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        function reCallNum(url){
        	parent.reCallNum(url);
        }
        
	</script>
	
	<c:if test='${"N" eq ispass}'>
		<script type="text/javascript">
			$(function(){
				 $(document).on("dblclick",".item",function(){
				 	reCallNum('${ctx}/inoculate/quene/inoculateMain?queueno='+$(this).attr("data-no"))
				 });
			});
		</script>
	</c:if>
	<c:if test='${"F" eq ispass}'>
		<script type="text/javascript">
			$(function(){
				 $(document).on("dblclick",".item",function(){
// 					parent.showBack();
// 				 	reCallNum("${ctx}/child_vaccinaterecord/childVaccinaterecord1/list1?childid=" + $(this).attr("data-childcode") + "&from=inoculate");
				 	reCallNum("${ctx}/inoculate/quene/inoculateMain?childid=" + $(this).attr("data-childcode"));
				 });
			});
		</script>
	</c:if>
	
	<c:if test="${quene.status == 0 and quene.ispass == 'Y'}">
			<script type="text/javascript">
			$(function(){
				 $(document).on("dblclick",".item",function(){
				 	reCallNum('${ctx}/inoculate/quene/inoculateMain?queueno=' + $(this).attr("data-no"));
				 });
			});
		</script>					
	</c:if>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="quene" action="${ctx}/inoculate/quene/" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/> -->
				</div>
			</form:form>
			<sys:message content="${message}"/>
					<c:forEach items="${quenelist }" var="quene">
					<div style="float: left;margin-bottom: 20px">${quene.name }<input  readonly="readonly" class="numPad" type="text" value="${quene.total }" ></div>
					</c:forEach>
					<c:if test="${total!=null}">
					<div class=" row pull-right">
					<div style="float: left;margin-bottom: 20px">今日已完成人数<input  readonly="readonly" class="numPad" type="text" value="${total}" ></div>
				</div>
					</c:if>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				
				<thead>
					<tr>
						<th>排号</th>
						<th>儿童编号</th>
						<th>儿童姓名</th>
						<!-- <th>疫苗编号</th> -->
						<th>疫苗名称</th>
						<th>针次</th>
						<!-- <th>科室编号</th> -->
						<!-- <th>接种医生</th> -->
						<!-- <th>是否过号</th> -->
						<shiro:hasPermission name="inoculate:quene:edit">
							<c:if test="${quene.status == 0 and quene.ispass == 'Y'}">
								<th>操作</th>
							</c:if>
						</shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${page.list}" var="quene">
					<tr class="item" data-no="${quene.queueno}" data-childcode="${quene.childid}">
						<th>${quene.queueno}</th>
						<th>${quene.childid}</th>
						<th>${quene.child.childname}</th>
						<%-- <th>${quene.vaccineid}</th> --%>
						<th>${quene.vaccine.name}</th>
						<th>${quene.pin}</th>
						<%-- <th>${quene.roomcode}</th> --%>
						<%-- <th>${quene.doctor}</th> --%>
						<%-- <th>${fns:getDictLabel(quene.ispass, "quene_ispass", "")}</th> --%> <!-- 字典quene_ispass -->
						
						
						<shiro:hasPermission name="inoculate:quene:edit">
							<c:if test="${quene.status == 0 and quene.ispass == 'Y'}">
								<th>
									<input type="button" class="btn btn-primary" onclick="reCallNum('${ctx}/inoculate/quene/inoculateMain?queueno=${quene.queueno}')" value="详情">
								</th>
							</c:if>
						</shiro:hasPermission>
						
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
		</div>
	</div>
</body>
</html>