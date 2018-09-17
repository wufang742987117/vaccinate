<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>疫苗出入库</title>
<meta name="decorator" content="default" />

<style type="text/css">
.ml10 {
	margin-left: 10px;
}
.mr10 {
	margin-right: 10px;
}

th , td{
	text-align: center;
}

.batchShow{
	display: inline-block !important;
}
.data-batch{
	display: none;
}

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

.laydate-icon, .laydate-icon-default, .laydate-icon-danlan, .laydate-icon-dahong, .laydate-icon-molv{
	height: 26px;
}
</style>
<script type="text/javascript">
	
	var offices = JSON.parse('${offices}');
	var officeOps = "<option value=''>--请选择-- </option>";
	$.each(offices, function(i,t){
		officeOps+="<option value='" + t.code + "'>" + t.name + "</option>"
	});
	
	var typeOptions="<option value=''>--请选择--</option><option value='1'>一类</option><option value='2'>二类</option>";
	
	
	var allData = new Array();
	var index = 0;
	var type = ${type};
	var from = '${manageStockIn.from}'

	var vaccinates = JSON.parse('${vaccinates}');
	var vaccOptions = '<option value="">--请选择--</option>';
	for(i in vaccinates){
		vaccOptions += '<option value="' + vaccinates[i].id + '">' + vaccinates[i].name + '</option>';
	}
	
	var specifications = JSON.parse('${fns:getDictListJson("specification")}');
	var specOptions = '<option value="">--请选择--</option>';
	for(i in specifications){
		specOptions += '<option value="' + specifications[i].value + '">' + specifications[i].label + '</option>';
	}
	
	var companys = JSON.parse('${companys}');
	var comOptions = '<option value="">--请选择--</option>';
	for(i in companys){
		comOptions += '<option value="' + companys[i].code + '">' + companys[i].name + '</option>';
	}
	
	function valiTr(thi){
		var _this = $(thi);
		var idx = $(thi).attr("data-idx");
		var ManageStockIn = new Object();
		ManageStockIn.product = new Object();
		
		ManageStockIn.product.isforeign=$(".data-type[data-idx="+idx+"]").val()
		ManageStockIn.product.officeCode=$(".data-officecode[data-idx="+idx+"]").val();
		ManageStockIn.product.vaccineid = $(".data-vacc[data-idx=" + idx + "]").val();
		ManageStockIn.product.batchno = $(".batchShow[data-idx=" + idx + "]").val();
		ManageStockIn.product.specification = $(".data-specification[data-idx=" + idx + "]").val();
		ManageStockIn.product.code = $(".data-company[data-idx=" + idx + "]").val();
		ManageStockIn.product.vaccExpDate = $(".data-exp[data-idx=" + idx + "]").val();
		ManageStockIn.product.storenum = $(".data-num[data-idx=" + idx + "]").val();
		ManageStockIn.product.costprice = $(".data-costprice[data-idx=" + idx + "]").val();
		ManageStockIn.product.sellprice = $(".data-sellprice[data-idx=" + idx + "]").val();
		ManageStockIn.product.spec = $(".data-spec[data-idx=" + idx + "]").val();
		ManageStockIn.remarks = $(".data-remark[data-idx=" + idx + "]").val();
		ManageStockIn.remarks = ManageStockIn.remarks?ManageStockIn.remarks:"";
		ManageStockIn.num = ManageStockIn.product.storenum;
		ManageStockIn.type = type;
		ManageStockIn.orderNo = $(".data-orderno").val();
		
		if(!(ManageStockIn.product.officeCode)) return false;
		if(!(ManageStockIn.product.isforeign)) return false;
		if(!(ManageStockIn.product.vaccineid)) return false;
		if(!(ManageStockIn.product.batchno)) return false;
		if(!(ManageStockIn.product.specification)) return false;
		if(!(ManageStockIn.product.code)) return false;
		if(!(ManageStockIn.product.vaccExpDate)) return false;
		if(!(ManageStockIn.product.storenum)) return false;
		if(!(ManageStockIn.product.costprice)) return false;
		if(!(ManageStockIn.product.sellprice)) return false;
		if(!(ManageStockIn.product.spec)) return false;
		return ManageStockIn;
	}
	
	
	$(function(){
		$("#btnAddLine").click(function(){
			var idx = index++;
			var html = '<tr data-idx="' + idx + '">'+
							'<td class="text-center data-index"></td>'+
							'<td><select class="btnInTable data-vacc" style="max-width:200px" data-idx=' + idx + '>' + vaccOptions + '</select></td>'+
							'<td><select name="" class="btnInTable data-batch data-batch-select batchShow" style="min-width:100px" data-idx=' + idx + '></select><input name="" type="text" class="btnInTable data-batch data-batch-input w100"  data-idx=' + idx + '><button data-type="add" class="btn btn-xs btn-success btn-batch-change" data-idx=' + idx + '>新增</button></td>'+
							'<td><select name="" class="btnInTable data-specification " data-idx=' + idx + '>' + specOptions + '</select></td>'+
							'<td><select name="" class="btnInTable data-company w100" data-idx=' + idx + '>' + comOptions + '</select></td>'+
							'<td><input name=""  onclick="laydate({istime: true, format: \'YYYY-MM-DD\'})" class="laydate-icon btnInTable layer-date data-exp w100" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-num w50" data-idx=' + idx + '></td>'+
							
							'<td><select name="" class="btnInTable data-officecode w100" data-idx=' + idx + '>' + officeOps + '</select></td>'+
							
							'<td><input name="" type="text" class="btnInTable data-costprice w50" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-sellprice w50" data-idx=' + idx + '></td>'+
							
							'<td><select class="btnInTable data-type w100" data-idx=' + idx + '>' + typeOptions + '</select></td>'+
							
// 							'<td><input name="" type="text" class="btnInTable w140" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-spec w50" value="1"  data-idx=' + idx + '></td>'+
// 							'<td><input name="" onclick="laydate({istime: true, format: \'YYYY-MM-DD\'})" class="laydate-icon btnInTable layer-date w100" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-remark" data-idx=' + idx + '></td>'+
							'<td class="text-center"><button class="btn btn-xs btn-primary btn-remove" data-idx=' + idx + '>删除</button></td>'+
						'</tr>';
			$("#tbody").append(html);
			allData.push(new Array());
			reIndex();
		});
		
		/* 序号重新排序 */
		function reIndex(){
			var idx = 1;
			$(".data-index").each(function(){
				$(this).html(idx++);
			});
		}
		
		function btnAdd(selecter){
			var idx = $(selecter).attr("data-idx");
			$(".data-batch-select[data-idx=" + idx + "]").val("");
			$(".data-batch-select[data-idx=" + idx + "]").removeClass("batchShow");
			$(".data-batch-input[data-idx=" + idx + "]").addClass("batchShow");			
			$(selecter).attr("data-type","select");
			$(selecter).html("选择");
			$(selecter).removeClass("btn-success");
			$(selecter).addClass("btn-warning");
		}
		function btnSelect(selecter){
			$(".data-batch-select[data-idx=" + idx + "]").val("");
			var idx = $(selecter).attr("data-idx");

			$(".data-batch-select[data-idx=" + idx + "]").addClass("batchShow");
			$(".data-batch-input[data-idx=" + idx + "]").removeClass("batchShow");
			$(selecter).attr("data-type","add");
			$(selecter).html("新增");
			$(selecter).removeClass("btn-warning");
			$(selecter).addClass("btn-success");
		}
		
		$(document).on("click", ".btn-batch-change", function(){
			if($(this).attr("data-type") == "add"){
				btnAdd(this);
			}else{
				btnSelect(this);
			}
		});
		
		/* 移除一行 */
		$(document).on("click", ".btn-remove", function(){
			$("tr[data-idx=" + $(this).attr("data-idx") + "]").remove();
			reIndex();
		});
		
		$(document).on("change", ".data-vacc", function(){
			var idx = $(this).attr("data-idx");
			if($(this).val()){
				$.ajax({
					url:"${ctx}/product/bsManageProduct/findViewListApi",
					data:{"vaccineid":$(this).val(), "showAll" : '1'},
					success:function(data){
						/* 保存每次下载数据 */
						allData[idx] = data;
						console.info(data);
						/* 没有记录 */
						if(data.length == 0){
							btnAdd(".btn-batch-change[data-idx=" + idx + "]");
							//批号为空时，清空其他所选信息
							$(".data-specification[data-idx=" + idx + "]").val('');
							$(".data-company[data-idx=" + idx + "]").val('');
							$(".data-exp[data-idx=" + idx + "]").val('');
							$(".data-costprice[data-idx=" + idx + "]").val('');
							$(".data-sellprice[data-idx=" + idx + "]").val('');
							$(".data-spec[data-idx=" + idx + "]").val('');
							$(".data-type[data-idx=" + idx + "]").val(''); 
						}else{
							btnSelect(".btn-batch-change[data-idx=" + idx + "]");
							var batchOptions = '';
							for(i in data){
								batchOptions += '<option >' + data[i].batchno + '</option>';
							}
							$(".data-batch[data-idx=" + idx + "]").html(batchOptions);
							$(".data-batch[data-idx=" + idx + "]").change(); 
						}
					}
				});
			}
		});
		
		$(document).on("change", ".data-batch", function(){
			var idx = $(this).attr("data-idx");
			if(this.type != "text"){
			/* 选择框 */
				var list = allData[idx];
				for(i in list){
					if(list[i].batchno == $(this).val()){
						$(".data-specification[data-idx=" + idx + "]").val(list[i].specification);
						$(".data-company[data-idx=" + idx + "]").val(list[i].code);
						$(".data-exp[data-idx=" + idx + "]").val(SimpleDateFormat(new Date(list[i].vaccExpDate),"yyyy-MM-dd"));
						$(".data-costprice[data-idx=" + idx + "]").val(list[i].costprice);
						$(".data-sellprice[data-idx=" + idx + "]").val(list[i].sellprice);
						$(".data-spec[data-idx=" + idx + "]").val(list[i].spec);
						
						//根据选择的疫苗的售价判断疫苗的类型
						if($(".data-sellprice[data-idx=" + idx + "]").val()==0){
							$(".data-type[data-idx=" + idx + "]").val(1);
						}else{
							$(".data-type[data-idx=" + idx + "]").val(2);
						}
					}
				}
			}
		});
		
		//通过输入方式确定疫苗的类型
		$(document).on("blur", ".data-sellprice", function(){
			var idx = $(this).attr("data-idx");
			if($(".data-sellprice[data-idx=" + idx + "]").val()==0){
				$(".data-type[data-idx=" + idx + "]").val(1);
			}else{
				$(".data-type[data-idx=" + idx + "]").val(2);
			}
		})
		
 		$(document).on("click", "#btnSubmit", function(){
 			var isok = true;
 			var list = new Array();
			$("#tbody>tr").each(function(){
				//校验数据
				var data = valiTr(this);
				if(!data){
					layer.msg("请将数据填写完整<br>[疫苗名称]<br>[批号]<br>[规格]<br>[有效期]<br>[入库数量]<br>[归属科室]<br>[成本价]<br>[售价]<br>[类型]<br>[剂量(剂/支)]", {"icon":7});
					isok = false;
					return ;
				}
				list.push(data);
			});
			
			if(isok && list.length > 0){
				console.info(list);
				$.ajax({
					url:"${ctx}/manage_stock_in/manageStockIn/saveStockIn",
					data:{"list":JSON.stringify(list)},
					type:"POST",
					success:function(data){
						console.info(data);
						if(data.code == "200"){
							layer.msg("保存成功",{"icon":1, "time":800});
							if(!from){
								setTimeout(function() {
									window.location.href= "${ctx}/manage_stock_in/manageStockIn";
								}, 800);
							}
						}
					}
				});
			}
		});
		
		$("#btnAddLine").click();
	})
</script>
</head>
<body>
	<div class="ibox">
		<div class=" col-sm-12">
			<form:form id="searchForm" modelAttribute="manageStockIn" action="" method="post" class="form-inline mt20">
			<form:hidden path="from"/>
				<div class="text-right">
					<c:if test="${type == '0'}">
						<div class="form-group text-left" style="position: absolute; left: 15px;">
							<span class="sub-title">疫苗入库</span>
						</div>
						<div class="form-group">
							<label>单号：</label>
							<input class="form-control mr10 data-orderno" type="text" />
						</div>
					</c:if>
					<c:if test="${type == '1'}">
						<div class="form-group text-left" style="position: absolute; left: 15px">
							<span class="sub-title">疫苗出库</span>
						</div>
					</c:if>
					<div class="form-group">
						<button id="btnAddLine" class="btn btn-primary mr10 w100" type="button"><span class="glyphicon glyphicon-plus">添加一行</button>
					</div>
					<div class="form-group">
						<button id="btnSubmit" class="btn btn-primary mr10 w100" type="button"><span class="glyphicon glyphicon-ok">保存</button>
					</div>
					<c:if test="${empty manageStockIn.from}">
						<div class="form-group">
							<a id="back" class="btn btn-success mr10 w100" href="${ctx}/manage_stock_in/manageStockIn" ><span class="glyphicon glyphicon-arrow-left">返回</span></a>
						</div>
					</c:if>
				</div>
			</form:form>
		</div>
		<div class=" col-sm-12">
			<sys:message content="${message}" />
			<table id="contentTable"
				class="table  table-bordered table-condensed">
				<thead>
					<tr>
						<!-- <th>类型</th> -->
						<th>序号</th>
						<th>疫苗名称</th>
						<th>批号</th>
						<th>规格</th>
						<th>生产企业</th>
						<th>有效期</th>
						<th>入库数量</th>
						<th>入库科室</th> 
						<th>成本价</th>
						<th>售价</th>
						<th>类型</th>
						<!-- <th>签批发号</th> -->
						<th>剂量(剂/支)</th>
						<!-- <th>生产时间</th> -->
						<th>备注</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="tbody">
					
				</tbody>
			</table>
		</div>
	</div>
	
</body>
</html>