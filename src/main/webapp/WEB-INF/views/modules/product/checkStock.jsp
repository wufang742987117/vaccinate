<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta name="decorator" content="default" />
	<meta charset="UTF-8">
	<title>盘点</title>
	<style>
		.text-center{
			text-align: center;
		}
		.table-total{
			width: 700px;
			margin: 10px auto 20px auto;
		}
		.table-title{
			margin: 5px auto;
			text-align: center;
		}

		.text-circle{
			display: block;
			margin: 0 auto;
			width: 15px;
			height: 15px;
			border: 1px #000 solid;
			border-radius: 15px;
			font-size: .7em;
		}
		.search-panel{
			
		}
		
		.vaccpanel{
			display: block;
			width: 100%;
		}
		
		.vacitem{
			float: left;
			width: 223px;
			font-size: 14px;
			margin-left: 15px;
			margin-top: 5px;
		}
		
		.data-table,.data-table th,.data-list{
			font-size: 16px;
			text-align: center;
		}
		
		.data-list th{
			font-size: 15px;
			text-align: center;
		}

		.data-list td{
			font-size: 14px;
		}
		
		.data-list table{
			margin: 10px auto 20px auto;
			
		}
		.data-list table th, .data-list table td{
			padding: 3px 5px;
		}
		
		.vacclist{
			height: 190px;
			overflow: auto;
		    border: #333 1px solid;
		    padding: 5px;
		    margin-top: 10px;
		}
	</style>
		<script src="${ctxStatic}/js/LodopFuncs.js"></script>
		<script>
		$(function(){
			$("select[name=doctor]").val("${record.doctor}");
			/* 疫苗种类复选框赋初值 */
			var d = "${vacc}";
			
			//全选
			$("#selectAll").click(function(){
				$(".vaccpanel input[type=checkbox]").each(function(){
					$(this).attr("checked", true);
					$(this).parent().addClass("checked");
				});
			});
			//取消全选
			$("#cancelAll").click(function(){
				$(".vaccpanel input[type=checkbox]").each(function(){
					$(this).attr("checked", false);
					$(this).parent().removeClass("checked");
				});
			});
			//选择本科室
			$("#selectThis").click(function(){
				$.each(d.split(","),function(i,d){
					if(d){
						$(".vaccpanel input[data-gnum=" + d + "]").attr("checked", true);
						$(".vaccpanel input[data-gnum=" + d + "]").parent().addClass("checked");
						$(".vaccpanel input[value=" + d + "]").attr("checked", true);
						$(".vaccpanel input[value=" + d + "]").parent().addClass("checked");	
					}
				});
			});
			$("#selectThis").click();
			
			$("#btnPrint").click(function(){
				var LODOP; //声明为全局变量
				try{	
					LODOP = getLodop();
				} catch(err) {
					layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
				};
				if (LODOP == null || LODOP ==''){
					return;
				}  
				LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
				LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
				LODOP.PRINT_INIT("打印接种日志");
				LODOP.ADD_PRINT_HTM(0,0,"100%","100%",$(".data-print").html());
				LODOP.PRINT();
// 				LODOP.PREVIEW();
			});
			
			$(".countUse").keyup(function(){
				if(isNaN($(this).val())){
					$(this).css("color","red");
					$(this).parent().next().html("NaN");
				}else{
					$(this).css("color","inherit");
					var ht = $(this).attr("data-use") - $(this).val();
					if(ht > 0){
						ht = "+" + ht;
					}
					$(this).parent().next().html(ht);
				}
			})
			
			$("#btnSave").click(function(){
				var idx = layer.load();
				var data = new Array();
				$(".countUse").each(function(){
					if(!$(this).val() || isNaN($(this).val())){
						$(this).css("color","red");
						layer.msg("参数错误",{"icon":2});
						return ;
					}
					var d = new Object();
					d["productId"] = $(this).attr("data-id");
					d["num"] = $(this).attr("data-use") - $(this).val();
					data.push(d);
				});
				if(data.length > 0){
					$.ajax({
						url:"${ctx}/product/bsManageProduct/saveCheckStock",
						method: "POST",
						timeout: 60000,
						data:{"data":JSON.stringify(data)},
						traditional: true, 
						success:function(data){
							layer.close(idx);
							if(data.code == "200"){
								layer.msg("保存成功",{"icon":1});
								var p = data.products;
								$.each(p,function(i,t){
									$("td[data-storenumid=" + t.pid + "]").html(t.storenum);
								});
							}else{
								layer.msg(data.msg,{"icon":2});
							}
						},
						error:function(){
						
							layer.close(idx);
						}
						
					});
				}else{
					layer.close(idx);
					layer.msg("未提交任何数据",{"icon":7});
				}
			});
		})
	</script>
</head>
<body style="background: #fff !important;">
	<div class="Wrap">
		<div class="searchWrap">
			<div class="search-panel">	
				<form:form id="searchForm" modelAttribute="record" action="${ctx}/product/bsManageProduct/checkStock" method="post" class="form-inline">
					<%-- <div class="form-group">
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">开始时间:</span> 
							<input name="beginTime" value="<fmt:formatDate value="${record.beginTime}" pattern="yyyy-MM-dd"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date" />
						</div>
					</div>
					<div class="form-group" >
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">截至时间:</span> <input
								name="endTime" value="<fmt:formatDate value="${record.endTime}" pattern="yyyy-MM-dd"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD'})"
								class="laydate-icon form-control layer-date"  />
						</div>
					</div> --%>
					<%-- <div class="form-group" >
						<div class="input-group">
							<span class="input-group-addon gray-bg text-right">接种医生:</span> 
							<form:select path="doctor" class="form-control">
								<form:option value="" >全部</form:option>
								<c:forEach items="${doctorlist }" var="vac">
									<form:option value="${vac.name }">${vac.name }</form:option>
								</c:forEach>
								
							</form:select>
						</div>
					</div> --%>
					<!-- <div class="form-group">
						<input id="btnSubmit" onclick="layer.load()" class="btn btn-primary" type="submit" value="查询"/>
					</div> -->
					<!-- <div class="form-group">
						<input id="btnPrint" class="btn btn-primary" type="button" value="打印"/>
					</div> -->
					<div class="form-group vaccpanel">
						<label class="col-sm-12 control-label" style="font-size: 16px; padding-left: 0;">
							接种疫苗:
							<button class="btn btn-xs btn-primary" type="button" id="selectThis">选择本科室</button> 
							<button class="btn btn-xs btn-primary" type="button" id="selectAll">全选</button> 
							<button class="btn btn-xs btn-primary" type="button" id="cancelAll">取消全选</button>
							<input id="btnSubmit" onclick="layer.load()" class="btn btn-primary" type="submit" value="查询"/>
							<a href="${ctx}/inoculate/quene/quenelog"  class="btn btn-success">接种日志</a>
						</label>
						<div class="col-sm-12 vacclist">
							<c:forEach items="${vacclist }" var="vac">
								<div class="vacitem i-checks ">
									<form:checkbox path="vacselected" label="${vac.name }" value="${vac.id }" cssClass="fl" data-gnum="${vac.mNum }" />
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
			<div class="data-print" style="width: 900px; margin: 0 auto;">
			<div class="data-title text-center">
				<h2 style="text-align: center; margin-top: 50px;">库存盘点(${sysdata})</h2>
			</div>
			<div class="data-table">
				<table class="table-total text-center" style="margin: 30px 100px; width: 700px;text-align: center;"  border="1" cellspacing="0" cellpadding="0">

					<thead>
						<tr>
							<th>疫苗大类</th>
							<th>疫苗名称</th>
							<th>厂商</th>
							<th>批号</th>
							<th>当前库存</th>
							<th>接种人数</th>
							<th>消耗数量</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${products }" var="p">
							<tr>
								<td>${p.gname }</td>
								<td>${p.vaccName }</td>
								<td>${p.manufacturer }</td>
								<td>${p.batchno }</td>
								<td data-storenumid="${p.id}">${p.storenum }</td>
								<td>${p.countAll }</td>
								<td><input type="text" data-id="${p.id}" data-use="${p.countUse }" value="${p.countUse }" class="form-control countUse" style="width: 65px; margin:3px auto; height: 32px;"></input></td>
								<td>0</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>

			</div>
				<div class="form-group text-right">
					<input id="btnSave" class="btn btn-primary btn-lg" style="margin-right: 100px;width: 100px;" type="button" value="保存"/>
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>