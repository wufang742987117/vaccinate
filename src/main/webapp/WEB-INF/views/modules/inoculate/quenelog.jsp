<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="default" />
	<meta charset="UTF-8">
	<title>接种日志</title>
	<style>
		.layui-layer-dialog .layui-layer-padding {padding: 40px 20px 20px 80px!important;text-align: left;font-size: 25px!important;height:auto!important;}
		.layui-layer-dialog .layui-layer-content .layui-layer-ico {top: 38px!important;left: 35px!important;}
		.layui-layer-btn a {height: 35px;line-height: 35px;padding: 0 20px;font-size:16px;}
		tr.focus{background-color:#ffd5b1; } .text-center{text-align: center; } .table-total{width: 600px; margin: 10px auto 20px auto; } .table-title{margin: 5px auto; text-align: center; } .text-circle{display: block; margin: 0 auto; width: 15px; height: 15px; border: 1px #000 solid; border-radius: 15px; font-size: .7em; } .search-panel{} .vaccpanel{display: block; width: 100%; } .vacitem{float: left; width: 230px; font-size: 14px; margin-left: 15px; margin-top: 5px; } .data-table,.data-table th,.data-list{font-size: 16px; text-align: center; } .data-list th{font-size: 15px; text-align: center; } .data-list td{font-size: 14px; } .data-list table{margin: 10px auto 20px auto; } .data-list table th, .data-list table td{padding: 3px 5px; } .vacclist{height: 190px; overflow: auto; border: #333 1px solid; padding: 5px; margin-top: 10px; }
	</style>
		<script src="${ctxStatic}/js/LodopFuncs.js"></script>
		<script>
		$(function(){
			/* 默认选中医生 */
			$("select[name=doctor]").val("${record.doctor}");
			/* 疫苗种类复选框赋初值 */
			var d = "${vacc}";
			
			//选择本科室
			$("#selectThis").click(function(){
				$.each(d.split(","),function(i,d){
					if(d){
						$(".vaccpanel input[data-gnum=" + d + "]").prop("checked", true);
						$(".vaccpanel input[data-gnum=" + d + "]").parent().addClass("checked");
						$(".vaccpanel input[value=" + d + "]").prop("checked", true);
						$(".vaccpanel input[value=" + d + "]").parent().addClass("checked");
					}
				});
			});
			$("#selectThis").click(); 
			//全选
			$("#selectAll").click(function(){
				$(".vaccpanel input[type=checkbox]").each(function(){
					$(this).prop("checked", true);
					$(this).parent().addClass("checked");
				});
			});
			//取消全选
			$("#cancelAll").click(function(){
				$(".vaccpanel input[type=checkbox]").each(function(){
					$(this).prop("checked", false);
					$(this).parent().removeClass("checked");
				});
			});
			
			$("#btnPrint").click(function(){
				layer.confirm('确认打印接种统计?',{icon: 3, title:'打印接种统计',area : ['500px','200px']}, function(index){
				  	var LODOP; //声明为全局变量
					try{	
						LODOP = getLodop();
					} catch(err) {
						layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
					};
					if (LODOP == null || LODOP ==''){
						return;
					}  
					debugger;
					LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
					LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
					LODOP.PRINT_INIT("打印接种日志");
					LODOP.ADD_PRINT_HTM(0,0,"100%","100%",$(".data-print").html());
					LODOP.PRINT();
	// 				LODOP.PREVIEW();
				  
				  layer.close(index);
				});  
			});
			
			//旧签字展示
			var strm;
			var im;
			$(".image").each(function(i,t){
				strm = t.innerText;
				im = new Image();
		        im.src = "data:image/png;base64" + "," + strm;
		        im.height = 33;
		        $(this).html(im);
			});
		})
		
		//选中某行
		$(document).ready(function () {
			$("#tUsers>tbody>tr").on("click", function () {
				$(this).parent().find("tr.focus").toggleClass("focus");//取消原先选中行
				$(this).toggleClass("focus");//设定当前行为选中行
			});
		});
		
/* 		function onSearch(obj){//js函数开始  
			setTimeout(function(){//因为是即时查询，需要用setTimeout进行延迟，让值写入到input内，再读取  
				var storeId = document.getElementById('tUsers');//获取table的id标识  
				var rowsLength = storeId.rows.length;//表格总共有多少行  
				var key = obj.value;//获取输入框的值  
		  
				var searchCol = 5;//要搜索的哪一列，这里是第一列，从0开始数起  
		  
				for(var i=1;i<rowsLength;i++){//按表的行数进行循环，本例第一行是标题，所以i=1，从第二行开始筛选（从0数起）  
					var searchText = storeId.rows[i].cells[searchCol].innerHTML;//取得table行，列的值  
		  
					if(searchText.match(key)){//用match函数进行筛选，如果input的值，即变量 key的值为空，返回的是ture，  
						storeId.rows[i].style.display='';//显示行操作，  
					}else{  
						storeId.rows[i].style.display='none';//隐藏行操作  
					}  
				}  
			},200);//200为延时时间  
		} */  
		
		function printLog(){   
			var beginTime = $(".beginTime").val();
			var endTime = $(".endTime").val();
			var doctor = $("#doctor").val();
			var includeBlue = $("#includeBlue").val();
			var chk_value =[]; 
			$('input[name="vacselected"]:checked').each(function(){ 
				chk_value.push($(this).val()); 
			}); 
			var vacselected = chk_value.join(",");
			window.open("${ctx}/inoculate/quene/quenelog?beginTime="+ beginTime + 
				"&&endTime="+ endTime + 
				"&&doctor=" + doctor + 
				"&&includeBlue=" + includeBlue + 
				"&&vacselected=" + vacselected +
				"&&type=1",
			 '_blank');
		} 

	/* 筛选接种记录 */
	function selRow(vaccId, pin, type, price){
		var idx = layer.load(); 
		$(".reclist").each(function(i,t){
			var _t = $(t);
			if(vaccId && _t.attr("data-vaccid") != vaccId){
				_t.hide();
			}else if(pin && pin != 'all' && pin != _t.attr("data-pin")){
				_t.hide();
			}else if(price && ((price == '0' && _t.attr("data-price") > 0) || (price == '1' && _t.attr("data-price") == 0))){
				_t.hide();
			}else if(type && type != _t.attr("data-type")){
				_t.hide();
			}else{
				_t.show();
			}
		});
		layer.close(idx);
	}
	</script>
</head>
<body style="background: #fff !important;">
	<div class="Wrap">
		<div class="searchWrap">
			<div class="search-panel">	
				<form:form id="searchForm" modelAttribute="record" action="${ctx}/inoculate/quene/quenelog" method="post" class="form-inline">
					<div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">开始时间:</span> 
							<input name="beginTime" value="<fmt:formatDate value="${record.beginTime}" pattern="yyyy-MM-dd"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date beginTime" />
						</div>
					</div>
					<div class="form-group" >
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">截至时间:</span> <input
								name="endTime" value="<fmt:formatDate value="${record.endTime}" pattern="yyyy-MM-dd"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date endTime"  />
						</div>
					</div>
					<div class="form-group" >
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">接种医生:</span> 
							<form:select path="doctor" class="form-control">
								<form:option value="" >全部</form:option>
								<c:forEach items="${doctorlist }" var="vac">
									<form:option value="${vac.name }">${vac.name }</form:option>
								</c:forEach>
								
							</form:select>
						</div>
					</div>
					<div class="form-group" >
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">数据范围:</span> 
							<select name="includeBlue" class="form-control">
								<option value="false">不包含补录信息</option>
								<option value="true">包含补录记录</option>
							</select>
						</div>
					</div>
					<script type="text/javascript">
						$(function(){
							$("select[name=includeBlue]").val('${includeBlue}');
						});
					</script>
					
					<div class="form-group">
						<input id="btnSubmit" onclick="layer.load()" class="btn btn-primary w100" type="submit" value="查询"/>
						<input id="btnPrint" class="btn btn-primary" type="button" value="打印接种统计"/>
						<input class="btn btn-success" type="button" onclick="printLog()" value="打印接种日志"/>
						<input class="btn btn-default w100" type="button" onclick="window.close()" value="返回"/>
						
						<label for="toCheck" class="red ft16">前往每日盘点--></label>
						<input class="btn btn-danger w100" id="toCheck" type="button" 
						onclick="window.open('${ctx}/product/bsManageCheck/tables?checkDate=${fns:getDate("yyyyMMdd")}&from=quenelog','_blank')" value="每日盘点"/>
						
					</div>
<!-- 					<div class="form-group"> -->
<%-- 						<a href="${ctx}/product/bsManageProduct/checkStock"  class="btn btn-success">库存盘点</a> --%>
<!-- 					</div> -->
					<div class="form-group vaccpanel">
						<label class="col-sm-12 control-label" style="font-size: 16px; padding-left: 0;">
							接种疫苗:
							<a href="javascript:void(0)" class="btn btn-xs btn-primary" type="button" id="selectThis">选择本科室</a> 
							<a href="javascript:void(0)" class="btn btn-xs btn-primary" type="button" id="selectAll">全选</a>
							<a href="javascript:void(0)" class="btn btn-xs btn-primary" type="button" id="cancelAll">取消全选</a> 
						</label>
						<div class="col-sm-12 vacclist">
							<c:forEach items="${vacclist }" var="vac">
								<div class="vacitem i-checks ">
									<form:checkbox path="vacselected" label="${vac.name }" value="${vac.id }" cssClass="fl" data-gnum="${vac.mNum }"/>
								</div>
							</c:forEach>
						</div>
						
					</div>
				</form:form>
				<div>
			</div>
			</div>
		</div>
		<div class="dataWrap">
			<div class="data-print" style="width: 800px; margin: 0 auto;">
			<div class="data-title text-center">
				<h2 style="text-align: center; margin-top: 50px;">接种日志(
				<c:choose>
					<c:when test="${record.beginShort == record.endShort }">
						${record.beginShort}
					</c:when>
					<c:otherwise>
						${record.beginShort} ~ ${record.endShort}
					</c:otherwise>
				</c:choose>
				)</h2>
			</div>
			<div class="data-table">
				<div class="table-title text-center" style="text-align: center;margin-top: 30px;position: relative;"><strong>疫苗消耗</strong><span style="position: absolute; right:100px;">接种人数：${childCount}</span></div>
				<table class="table-total text-center" style="margin: 10px 100px; width: 600px;text-align: center;"  border="1" cellspacing="0" cellpadding="0">

					<thead>
						<tr>
							<th>疫苗名称</th>
							<th>基础</th>
							<th>加强</th>
							<th>临时</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${simplelist }" var="vo">
							<tr>
								<td>${vo.vaccName}</td>
								<td class="link" onclick="selRow('${vo.vaccId}','','1')">${vo.pinBase}</td>
								<td class="link" onclick="selRow('${vo.vaccId}','','5')">${vo.pinPro}</td>
								<td class="link" onclick="selRow('${vo.vaccId}','','4')">${vo.pinTmp}</td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			<div class="table-title text-center" style="text-align: center; margin-top: 30px;"><strong>各疫苗具体剂次</strong></div>
			<table class="table-total text-center" style="margin: 10px 100px; width: 600px;text-align: center;" border="1" cellspacing="0" cellpadding="0">
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>剂次</th>
						<th>自费</th>
						<th>免费</th>
						
						<th>价格总计</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${detaillist }" var="de">
						<tr>
							<c:if test="${de.itemsize > 0}"><td rowspan="${de.itemsize }">${de.vaccName}</td></c:if>
							
							<!-- 合计 -->
							<c:if test="${empty de.pinnum }"><td><strong>合计</strong></td></c:if>
							<!-- 剂次 -->
							<c:if test="${not empty de.pinnum and de.pinnum != 'all'}"><td><span class="text-circle">${de.pinnum}</span></td></c:if>
							<!-- 总计 -->
							<c:if test="${not empty de.pinnum and de.pinnum == 'all'}"><td></td></c:if>
							
							<c:if test="${empty de.pinnum or de.pinnum == 'all'}">
								<td class="link" onclick="selRow('${de.vaccId}','${de.pinnum}','','1')"><strong>${de.forPay }</strong></td>
								<td class="link" onclick="selRow('${de.vaccId}','${de.pinnum}','','0')"><strong>${de.forFree }</strong></td>
								<td><strong>${de.priceCountDouble}</strong></td>
							</c:if>
							<c:if test="${not empty de.pinnum and de.pinnum != 'all'}">
								<td class="link" onclick="selRow('${de.vaccId}','${de.pinnum}','','1')">${de.forPay }</td>
								<td class="link" onclick="selRow('${de.vaccId}','${de.pinnum}','','0')">${de.forFree }</td>
								<td>${de.priceCountDouble}</td>
							</c:if>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>

			</div>
			</div>
			<div class="data-list">
				<div style="margin-top: 50px;"><strong>接种明细</strong></div>
				<table id="tUsers" class="text-center" style="margin-top: 10px;"  border="1" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th>接种日期<br />(年月日)</th>
							<th>儿童姓名</th>
							<th>性别</th>
							<th>出生日期<br />(年月日)</th>
							<th>过敏<br />史</th>
							<th>发热</th>
							<th>咳嗽</th>
							<th>腹泻</th>
							<th>其他<br/>症状</th>
							<th>近期是<br />否患病</th>
							<th>疫苗</th>
							<th>剂次</th>
							<th>疫苗批号</th>
							<th>生产企业</th>
							<th>有效期</th>
							<th>家长签字及<br />更新电话号码</th>
							<th>备注</th>
							<th>接种医生</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${returnList }" var="rec">
							<tr class="reclist" data-vaccid='${rec.vaccineid}' data-pin='${rec.dosage}' data-type='${rec.vacctype }' data-price='${rec.price }'>
								<td><fmt:formatDate value="${rec.vaccinatedate}" pattern="yyyy-MM-dd" /></td>
								<td>${rec.childname}</td>
								<td>${fns:getDictLabel(rec.gender, 'sex', '')}</td>
								<td><fmt:formatDate value="${rec.birthday}" pattern="yyyy-MM-dd" /></td>
								<td>
									<c:if test="${rec.childAbnormalReaction eq '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_circle.png">
									</c:if>
									<c:if test="${rec.childAbnormalReaction ne '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_hook.png">
									</c:if>
								</td>
								<td>
									<c:if test="${rec.fever eq '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_circle.png">
									</c:if>
									<c:if test="${rec.fever ne '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_hook.png">
									</c:if>
								</td>
								<td>
									<c:if test="${rec.cough eq '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_circle.png">
									</c:if>
									<c:if test="${rec.cough ne '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_hook.png">
									</c:if>
								</td>
								<td>
									<c:if test="${rec.diarrhea eq '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_circle.png">
									</c:if>
									<c:if test="${rec.diarrhea ne '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_hook.png">
									</c:if>
								</td>
								<td>
									<c:if test="${rec.symptom eq '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_circle.png">
									</c:if>
									<c:if test="${rec.symptom ne '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_hook.png">
									</c:if>
								</td>
								<td>
									<c:if test="${rec.disease eq '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_circle.png">
									</c:if>
									<c:if test="${rec.disease ne '无'}">
										<img alt="" src="${ctxStatic}/images/loglist_hook.png">
									</c:if>
								</td>
								<td class="text-left">${rec.vaccName}</td>
								<td ><span class="text-circle">${rec.dosage}</span></td>
								<td>${rec.batch}</td>
								<td>${rec.manufacturer}</td>
								<td class="recExpDate"><fmt:formatDate value="${rec.vaccExpDate}" pattern="yyyy-MM-dd" /></td>
								<td>
									<c:if test="${empty rec.signatureList || rec.signature == 0}">
										${rec.parentsMoblie}
									</c:if>
									<c:if test="${not empty rec.signatureList && rec.signature == 1 && rec.stype == 1}">
										<div class="image" style="margin-right: 0;">${rec.signList}</div>
										${rec.parentsMoblie}
									</c:if>
									<c:if test="${not empty rec.signatureList && rec.signature == 1 && rec.stype != 1}">
										<img src="${ctx}/child_vaccinaterecord/childVaccinaterecord/signatureimg?id=${rec.id}" height="33px" style="margin-right: 0;"/>
										${rec.parentsMoblie}
									</c:if>
								</td>
								<td>${fns:getDictLabel(rec.bodypart, 'position', '')}</td>
								<td>${rec.doctor}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>