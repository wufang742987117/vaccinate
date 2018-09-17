<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>出入库统计</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        $(document).ready(function() {
        $("#year").val("${yeay}");
        b();
        $("#month").val("${month}");
       	a();
        $("#day").val("${day}");
        
				$("#btnExport").click(function(){
					layer.confirm("确认要导出数据吗？", {
						btn: ['确认','取消'], //按钮
						shade: true, //不显示遮罩
						icon : 0
					}, function(index){
					var temp = $("#searchForm").attr("action");
						$("#searchForm").attr("action","${ctx}/rabiesvaccine/bsRabiesCheckin/exportList");
						$("#searchForm").submit();
						layer.close(index);
						$("#searchForm").attr("action",temp);
					}, function()
					{
						layer.close();
					});
				});

		});
        
       function a(){
        var year=$("#year").val();
        var month=$("#month").val();
        if(month==0){
        html += "<option value='" + 0 + "'";
				html += "> " + "全&nbsp;年" + " </option>";
        $("#day").html(html);
        return;
        }
        if(month!=2){
        var c=["1","3","5","7","8","10","12"]
        var bl=( $.inArray(month, c)!=-1)
        if(bl){
        var html = "";
			for (var int = 0; int < 32; int++) {
			if(int==0){
				html += "<option value='" + 0 + "'";
				html += "> " + "全&nbsp;月" + " </option>";
			
			}else{
			html += "<option value='" + int + "'";
				html += "> " + int + "&nbsp;号 </option>";
			}
			}
		$("#day").html(html);
        }else{
         var html = "";
			for (var int = 0; int < 31; int++) {
			if(int==0){
				html += "<option value='" + 0 + "'";
				html += "> " + "全&nbsp;月" + " </option>";
			
			}else{
			html += "<option value='" + int + "'";
				html += "> " + int + "&nbsp;号 </option>";
			}
			}
		$("#day").html(html);
        }
        }else{
      var boolean=(   0==year%4&&((year%100!=0)||(year%400==0)))
        if(boolean){
         var html = "";
			for (var int = 0; int < 30; int++) {
			if(int==0){
				html += "<option value='" + 0 + "'";
				html += "> " + "全&nbsp;月" + " </option>";
			
			}else{
			html += "<option value='" + int + "'";
				html += "> " + int + "&nbsp;号 </option>";
			}
			}
		$("#day").html(html);
        
        }else{
        
         var html = "";
			for (var int = 0; int < 29; int++) {
			if(int==0){
				html += "<option value='" + 0 + "'";
				html += "> " + "全&nbsp;月" + " </option>";
			
			}else{
			html += "<option value='" + int + "'";
				html += "> " + int + "&nbsp;号 </option>";
			}
			}
		$("#day").html(html);
        }
        }
        } 
        
   function b(){
   $("#month").val("0");
   a();
   }     
   
        
        
        
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		 <li ><a
			href="${ctx}/child_baseinfo/documentStatistics">个案统计</a></li>
		<li><a
			href="${ctx}/child_vaccinaterecord/childVaccinaterecord1/childDailyManagement">应种统计</a></li>
		<li><a
			href="${ctx}/child_vaccinaterecord/inoculationOfStatistics">接种统计</a></li>
<%-- 		<li><a href="${ctx}/product/bsManageProduct/repertorySum">库存统计</a></li> --%>
		<li><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsBatch">批次接种统计</a></li>
		<li><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsSmallCategory">小类接种统计</a></li>
		<li><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsBigCategory">大类接种统计</a></li>
		<li class="active"><a href="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsList">出入库统计</a></li>
		<div class=" row pull-right">
			<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" onclick="this.href"><span class="glyphicon glyphicon-arrow-left"></span>返回登记台</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="bsRabiesStatistics3" action="${ctx}/rabiesvaccine/bsRabiesCheckin/bsCheckinStatisticsList" method="post" class="form-inline mt20">
					<div class = "col-xs-12">
						<%-- <div class="form-group" >
							<div class="input-group" >
								<span class="input-group-addon gray-bg text-right">日期(起)：</span>
								<input name="beginTime" value="<fmt:formatDate value="${bsRabiesStatistics.beginTime}" pattern="yyyy-MM-dd"/>"
									onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
									class="laydate-icon form-control layer-date" />
							</div>
						</div>
						<div class="form-group" style="padding-left: 70px">
							<div class="input-group">
								<span class="input-group-addon gray-bg text-right">日期(止)：</span>
								<input name="endTime" 
									onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
									class="laydate-icon form-control layer-date" value="<fmt:formatDate value="${bsRabiesStatistics.endTime}" pattern="yyyy-MM-dd"/>"   />
							</div>
						</div> --%>
					<%-- 	<div class="form-group" style="padding-left: 70px">
							<div class="input-group">
								<input type="text" id="txtName" name="time" class="date" value="${data }" /><br><label style="color: red">注：0月表示全年，0日表示全月</label> 
							</div>
						</div> --%>
						<div class="form-group">
							<select id="year" name="yeay" class="form-control" onchange="b()">
								<c:forEach items="${ yearList}" var="year">
									<option value="${year}">${year}&nbsp;年</option>
								</c:forEach>
							</select >
							<select id="month" name="month" class="form-control" onchange="a()">
								<option value="0">全&nbsp;年</option>
								<option value="1">1&nbsp;月</option>
								<option value="2">2&nbsp;月</option>
								<option value="3">3&nbsp;月</option>
								<option value="4">4&nbsp;月</option>
								<option value="5">5&nbsp;月</option>
								<option value="6">6&nbsp;月</option>
								<option value="7">7&nbsp;月</option>
								<option value="8">8&nbsp;月</option>
								<option value="9">9&nbsp;月</option>
								<option value="10">10&nbsp;月</option>
								<option value="11">11&nbsp;月</option>
								<option value="12">12&nbsp;月</option>
							</select>
							<select id="day" name="day" class="form-control">
								<option value="0">全&nbsp;月</option>
							</select>
						</div>
						<div class="form-group">
							<input id="btnSubmit" class="btn btn-primary mr15" type="submit" value="统计" />
							<input id="btnExport" class="btn btn-primary mr15" type="button" value="导出报表"/> 
						</div>
					</div>
			</form:form>
			
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗大类名称</th>
						<th>入库数量</th>
						<th>接种数量</th>
						<th>报废数量</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${bsStatisticsList}" var="bsRabiesStatistics">
					<tr>
						<td>
							${bsRabiesStatistics.name}
						</td>
						<td>
							${bsRabiesStatistics.total}
						</td>
						<td>
							${bsRabiesStatistics.completion}
						</td>
						<td>
							${bsRabiesStatistics.scraptotal}
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>