<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存盘点</title>
	<meta name="decorator" content="default"/>	
	<style type="text/css">
		td,th{
			text-align: center !important;
		}
	</style>
	
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
			
			
			/* reset */
			$("#reset").click(function(){
				$("#vaccineid").val("");
				$("#manufacturer").val("");
				$("#batchno").val("");
				$("#check0").attr("checked",false);
			});
			
			/* 选择疫苗名称加载生产厂家 */
			$(document).on("change","#vaccineid",function(){
				$.ajax({
					url : "${ctx}/product/bsManageProduct/findViewListApi",
					data : {"vaccineid" : $(this).val()},
					
					success: function(data){
						var html ="<option value=''>--请选择--</option>";
						$.each(data,function(i,t){
							html += "<option value=" + t.code + ">" + t.manufacturer + "</option>"
						});
						$("#manufacturer").html(html);
// 						$("#manufacturer").select2();
						
					}
					
				})
			});
			
			
			/* 实际库存移除，计算 */
			$(document).on("blur",".stockInput",function(){
				var sto = $(this).parent().parent().find("td[data-role=storenum]").html();
				if(sto){
					var stoInt = parseInt(sto);
					var ii = parseInt($(this).val());
					var text = "";
					if(ii > sto){
						text = (ii - sto ) + "<span style='color:#21C429'>(盘盈)</span>"
						$(this).addClass("alt");
					}else if(ii < sto){
						text = (sto - ii) + "<span style='color:#ff0000'>(盘亏)</span>"
						$(this).addClass("alt");
					}else{
						$(this).removeClass("alt");
					}
					$(this).parent().parent().find("td[data-role=result]").html(text);
				}
			});
			
			
			/* 保存 */
			$(document).on("click","#saveBtn",function(){
				$(".stockInput").blur();
				var data = new Array();
				//循环取已修改的值
				$(".alt").each(function(){
					var obj = new Object();
					obj.id=$(this).attr("data-id");
					obj.remarks=$(this).parent().parent().find("td[data-role=remarks] input").val()
					obj.num=$(this).val();
					data.push(obj);
				});
				
				//ajax提交后台
				$.ajax({
					url:"${ctx}/product/bsManageProduct/saveCheck",
					type: "POST",
					data:{"list":JSON.stringify(data)},
					traditional: true, 
					success:function(data){
						if(data.code == 500){
							layer.msg(data.msg,{"icon":7});
						}else if(data.code == 200){
							layer.msg("保存成功", {"icon":1,"time":1500});
							$.each(JSON.parse(data.list), function(i,t){
								var thi = $(".stockInput[data-id=" + t.id + "]");
								thi.val(t.num);
								thi.parent().parent().find("td[data-role=storenum]").html(t.num);
								thi.parent().parent().find("td[data-role=result]").html("");
								thi.parent().parent().find("td[data-role=remarks] input").val("");
								
							})	
							$("td[data-role=result]").html("");
							$(".stockInput").removeClass("alt");
							$(".opset").removeClass("opset");					
						}	
					},
					error:function(a,b){
						console.error(a);
						layer.msg("保存失败",{"icon":2});
					}
				})
				
			});
			
			$("#vaccineid").change();
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>

	
</head>
<body>
	<div class="ibox">
		<div>
			<form:form id="searchForm" modelAttribute="bsManageProduct" action="${ctx}/product/bsManageProduct/check" method="post" class="form-inline mt20">
			<form:hidden path="from"/>
				<div class="form-group" >
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">疫苗种类：</span>
						<form:select path="vaccineid" id="vaccineid" class="form-control">
							<form:option value="" label="--请选择--"/>
							<form:options items="${products}" itemLabel="vaccName" itemValue="vaccineid" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">生产企业：</span>
						<form:select path="code" id="manufacturer" class="form-control">
							<form:option value="" label="--请选择--"/> 
						</form:select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">批号：</span>
						<form:input path="batchnoLike" id="batchno" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<label class="mr15"><input id="check0" name="check0" type="checkbox" value="1" class="i-checks"><i></i><span>维护0库存</span></label>
					<form:checkbox path="showAll" label="全部显示" value="1" cssClass="checkbox-inline i-checks"/>
					<input id="btnSubmit" class="btn btn-primary w100" type="submit" value="查询"/>
					<!-- <input type="button" class="btn btn-primary"  id="reset" value="重置"/> -->
					<button class="btn btn-primary w100" type="button" id="saveBtn">保存</button>
				    <a id="back" class="btn btn-primary w200" href="${ctx}/product/bsCheckRecord/" ><span style="width: 500">盘点记录管理</span></a>
					<c:if test="${empty bsManageProduct.from}">
						<a id="back" class="btn btn-success mr10 w100" href="${ctx}/product/bsManageProduct" ><span class="glyphicon glyphicon-arrow-left">返回</span></a>
					</c:if>
				</div>
			</form:form> 
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>编码</th>
						<th>疫苗名称</th>
						<th>生产企业</th>
						<th>批号</th>
						<th>剂量</th>
						<th>规格<br>(剂/支或粒)</th>
						<th>有效期</th>
						<th>入库时间</th>
						<th>单价</th>
						<th>当前库存</th>
						<th>实际库存</th>
						<th>盘点数量</th>
						<th>备注</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${bsProductList}" var="bsProduct"  begin="0"  varStatus="status">
					<tr>
						<!-- 编码 -->
						<td>
							${status.index+1}
						</td>
						<!-- 疫苗名称/注射器类型 -->
						<td>
							${bsProduct.vaccName}
						</td>
						<!-- 生产厂商 -->
						<td>
							${bsProduct.manufacturer}
						</td>
						<!-- 批次号 -->
						<td>
							${bsProduct.batchno}
						</td>
						<!-- 规格 -->
						<td class="text-center">
							${bsProduct.specification}
						</td>
						<!-- 计量 -->
						<td>
							${bsProduct.spec}
						</td>
						<!-- 有效期 -->
						<td> <fmt:formatDate value="${bsProduct.vaccExpDate}" pattern="yyyy-MM-dd"/>   </td>
						<td class="text-center"> <fmt:formatDate value="${bsProduct.createDate}" pattern="yyyy-MM-dd"/> </td>
						<!-- 成本价 -->
						<td>
							<c:choose>
								<c:when test="${empty bsProduct.sellprice or bsProduct.sellprice == '0'}">
									免费
								</c:when>
								<c:otherwise>
									￥${bsProduct.sellprice}
								</c:otherwise>
							</c:choose>
						</td>
						<!-- 上次盘点库存量 -->
						<td data-role=storenum>
							${bsProduct.storenum}
						</td>
						<td data-role="checkNum" >
							<input type="text" class="input-medium input-intable stockInput w100" data-id="${bsProduct.id}" value="${bsProduct.storenum}"  onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')">
						</td>
						<td data-role="result">
							
						</td>
						<td data-role="remarks">
							<input type="text" class="input-medium input-intable remarks w200" data-remarks="${bsProduct.id}" value=""/>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="pagination pull-right">
					
			</div>
		</div>
	</div>
</body>
</html>