<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>每日盘库管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.sub-title{
			font-size: 18px;
		}
		.sub-title:after{
		    content: "";
		    display: block;
		    height: 3px;
		    border-bottom: 2px #000 solid;
		    width: 160px;
		}
		
		#contentTable tr>td{
			text-align: center;
		}
	</style>
	<script type="text/javascript">
		var tables = ${fns:toJson(tables)};
//  		var tables;
		$(document).ready(function() {
			 $(document).on("change",".in-text",function(){
				var pid = $(this).attr("data-id"); 
				var value = $(this).val();
				for(var j = 0; j < tables.length; j ++){
					if(pid == tables[j].product.id){
						if($(this).hasClass("restNum")){
							tables[j]["restNum"] = value;
							break;
						}else if($(this).hasClass("remarks")){
							tables[j]["remarks"] = value;
							break;
						}
					}
				}
			}); 
		});
		
		function saveCheck(){
			var idx = layer.load();
			//保存盘点数据
			$.ajax({
				url:"${ctx}/product/bsManageCheck/saveTables",
				type: "POST",
				data:{'tables':JSON.stringify(tables)},
				success:function(data){
					layer.close(idx);
					if(data.code == 200){
						success("保存成功");
						reloadTable(data.list);
						parent.showBack();
						$(".tips").remove();
					}else{
						error("保存失败");
					}
					
				},
				error:function(){
					layer.close(idx);
					
				}			
			});
		}
		
		//保存完成后刷新页面
		function reloadTable(data){
			var html = "";
			var dataP = JSON.parse(data);
			for(var i = 0; i < dataP.length; i++){
				dataP[i].product.createDate = dataP[i].product.createDate.substr(0,10);
			}
			var da = new Object();
			da["tables"] = dataP;
			var tpl = $("#tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			html = Mustache.render(tpl,da);
			$("#contentTable").html(html);
		}
		
		/* 打开详细列表页面 */
		function detailList(thi){
			var _this = $(thi); 
			if(_this.html() && _this.html().trim() == '0'){
				return;
			}
			var pid = _this.parent().attr("data-pid");
			var type = _this.attr("data-type");
			var startTime = _this.parent().attr("data-beginTime");
			window.open('${ctx}/product/bsManageCheck/detailList?pid='+ pid + '&type=' + type + '&startTime=' + startTime ,"_blank");
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
	<div class="form-group text-left ml20" style="position: absolute;">
		<span class="sub-title">每日盘点</span>
	</div>
	<div class="ibox" style="margin-top: 27px;">
		<div class="ibox-content margin0" >
			<form:form id="searchForm" modelAttribute="bsManageCheck" action="${ctx}/product/bsManageCheck/table" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
						<div class="form-group">
							<div class="input-group">
								<span class="input-group-addon gray-bg">日期：</span>
									<input type="text" disabled="disabled" name="checkDate" value="${bsManageCheck.checkDate}" class="form-control" style="background: #fff" />
							</div>
						</div>
				<div class="form-group">
					<input id="btnSave" class="btn btn-primary w100" type="button" onclick="saveCheck()" value="保存"/>
					<a href="${ctx}/product/bsManageCheck" class="btn btn-success w100" >盘点记录</a>
					<c:if test="${empty bsManageCheck.from}">
						<label class="red ml20 ft16 tips" >>>>>>上期库存未盘点，请先盘点库存<<<<<</label>
					</c:if>
				</div>
				<c:if test="${bsManageCheck.from == 'quenelog'}">
					<div class="form-group pull-right">
						<input class="btn btn-success w100" type="button" onclick="window.close()" value="返回"/>
					</div>
				</c:if>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>疫苗批号</th>
						<th>疫苗厂商</th>
						<th>剂量</th>
						<th>入库时间</th>
						<th>上期时间</th>
						<th>上期库存</th>
						<th>本期入库</th>
						<th>其他出库</th>
						<th>调剂出库</th>
						<th>本期报损</th>
						<th>本期使用</th>
						<th>当前库存</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${tables}" var="check">
					<tr data-pid='${check.product.id}' data-beginTime='<fmt:formatDate value="${check.lastDate}" pattern="yyyy-MM-dd HH:mm:ss"/>'>
						<td class="text-left">
							${check.product.name}
						</td>
						<td>
							${check.product.batchno}
						</td>
						<td>
							${check.product.manufacturer}
						</td>
						<td>
							${check.product.specification}
						</td>
						<td>
							<fmt:formatDate value="${check.product.createDate}" pattern="yyyy-MM-dd"/>
						</td>
						<td>
 							<fmt:formatDate value="${check.lastDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
						<td>
							${check.lastNum}
						</td>
						<td class="link" data-type='IN' onclick="detailList(this)">
							${check.inNum}
						</td>
						<td class="link" data-type='OUT' onclick="detailList(this)">
							${check.outNum}
						</td>
						<td class="link" data-type='EXCHANGE' onclick="detailList(this)">
							${check.exchangeNum}
						</td>
						<td class="link" data-type='SCRAP' onclick="detailList(this)">
							${check.scrapNum}
						</td>
						<td class="link" data-type='USE' onclick="detailList(this)">
							${check.useNum}
						</td>
						<td>
							${check.product.storenum}
						</td>
						<%-- <td>
							<input type="text" class="input-medium input-intable w100 restNum in-text" value="${check.product.storenum}" data-id="${check.product.id}">
						</td>
						<td>
							<input type="text" class="input-medium input-intable w100 remarks in-text" data-id="${check.product.id}">
						</td>		 --%>	
						<td>
							<input type="button" class="btn btn-sm btn-success" value="出库" onclick="outPut('${check.product.id}')" />
						</td>			
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	<script type="text/template" id="tpl">
	<thead>
		<tr>
		    <th>疫苗名称</th>
		    <th>疫苗批号</th>
		    <th>疫苗厂商</th>
		    <th>剂量</th>
		    <th>入库时间</th>
		    <th>上期时间</th>
		    <th>上期库存</th>
		    <th>本期入库</th>
		    <th>其他出库</th>
		    <th>调剂出库</th>
		    <th>本期报损</th>
		    <th>本期使用</th>
		    <th>当前库存</th>
		    <th>操作</th>
		</tr>
	</thead>
	<tbody>
	
	{{#tables}}
		<tr  data-pid={{product.id}}  data-beginTime={{lastDate}} >
			<td class="text-left">
				{{product.name}}
			</td>
			<td>
				{{product.batchno}}
			</td>
			<td>
				{{product.manufacturer}}
			</td>
			<td>
				{{product.specification}}
			</td>
			<td>
				{{product.createDate}}
			</td>
			<td>
				{{lastDate}}
			</td>
			<td>
				{{lastNum}}
			</td>

			<td class="link" data-type='IN' onclick="detailList(this)">
				{{inNum}}
			</td>
			<td class="link" data-type='OUT' onclick="detailList(this)">
				{{outNum}}
			</td>
			<td class="link" data-type='EXCHANGE' onclick="detailList(this)">
				{{exchangeNum}}
			</td>
			<td class="link" data-type='SCRAP' onclick="detailList(this)">
				{{scrapNum}}
			</td>
			<td class="link" data-type='USE' onclick="detailList(this)">
				{{useNum}}
			</td>
			<td>
				{{product.storenum}}
			</td>
			<td>
				<input type="button" class="btn btn-sm btn-success" value="出库" onclick="outPut('{{product.id}}')" />
			</td>						
		</tr>

	{{/tables}}
	</tbody>
</script>
</body>
</html>