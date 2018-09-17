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
	
	var allData = new Array();
	var index = 0;
	var type = ${type};
	var from = '${manageStockIn.from}';

	var products = JSON.parse('${products}');
	var proOptions = '<option value="">--请选择--</option>';
	for(i in products){
		proOptions += '<option value="' + products[i].vaccineid + '">' + products[i].vaccName + '</option>';
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
		var storenum = parseInt($(".data-storenum[data-idx=" + idx + "]").val());
		ManageStockIn.product = new Object();
		
		ManageStockIn.product.id = $(".data-pid[data-idx=" + idx + "]").val();
		ManageStockIn.remarks = $(".data-remark[data-idx=" + idx + "]").val();
		ManageStockIn.remarks = ManageStockIn.remarks?ManageStockIn.remarks:"";
		ManageStockIn.num = $(".data-num[data-idx=" + idx + "]").val();
		ManageStockIn.type = type;
		ManageStockIn.orderNo = $(".data-orderno").val();
		
		if(!(ManageStockIn.product.id)) return false;
		if(!(ManageStockIn.num)) return false;
		if(!(ManageStockIn.remarks)) return false;
		
		if(storenum < parseInt(ManageStockIn.num)){
			return false;
		}
		return ManageStockIn;
	}
	
	
	$(function(){
		$("#btnAddLine").click(function(){
			var idx = index++;
			var html = '<tr data-idx="' + idx + '">'+
							'<td class="text-center data-index"></td>'+
							'<td><select class="btnInTable data-vacc" style="max-width:200px" data-idx=' + idx + '>' + proOptions + '</select></td>'+
							'<td><input class="data-pid" type="hidden" data-idx=' + idx + '></input><select name="" class="btnInTable data-batch data-batch-select batchShow" style="min-width:100px" data-idx=' + idx + '></select></td>'+
							'<td><input name="" type="text" class="btnInTable data-num w50" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-storenum w50" readonly="true" data-storenum data-idx=' + idx + '></td>'+
							'<td><select name="" class="btnInTable data-specification " readonly="true"  data-idx=' + idx + '>' + specOptions + '</select></td>'+
							'<td><select name="" class="btnInTable data-company w100" data-idx=' + idx + '>' + comOptions + '</select></td>'+
							'<td><input name=""  class="laydate-icon btnInTable data-exp w100" readonly="true" data-idx=' + idx + '></td>'+
// 							'<td><input name="" type="text" class="btnInTable data-costprice w50" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-sellprice w50" readonly="true" data-idx=' + idx + '></td>'+
// 							'<td><input name="" type="text" class="btnInTable w140" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-spec w50" value="1"  readonly="true" data-idx=' + idx + '></td>'+
// 							'<td><input name="" onclick="laydate({istime: true, format: \'YYYY-MM-DD\'})" class="laydate-icon btnInTable layer-date w100" data-idx=' + idx + '></td>'+
							'<td><input name="" type="text" class="btnInTable data-remark"  readonly="true" data-idx=' + idx + ' value="报损"></td>'+
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
					data:{"vaccineid":$(this).val()},
					success:function(data){
						/* 保存每次下载数据 */
						allData[idx] = data;
						console.info(data);
						/* 没有记录 */
						if(data.length == 0){
							btnAdd(".btn-batch-change[data-idx=" + idx + "]");
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
						$(".data-pid[data-idx=" + idx + "]").val(list[i].id);
						$(".data-storenum[data-idx=" + idx + "]").val(list[i].storenum);
						$(".data-storenum[data-idx=" + idx + "]")[0].attr("attr-storenum",list[i].storenum);
					}
				}
			}
		});
		
 		$(document).on("click", "#btnSubmit", function(){
 			var isok = true;
 			var list = new Array();
			$("#tbody>tr").each(function(){
				//校验数据
				var data = valiTr(this);
				if(!data){
					layer.msg("请将数据填写完整<br>[疫苗名称]<br>[批号]<br>[数量]<br>[备注]<br>[出库数量]不可大于[库存]", {"icon":7});
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
/* 		$(document).on("click", ".btn-remove", function(){
			
		}); */
	})
</script>
</head>
<body>
	<div class="ibox">
		<div class="col-sm-12">
			<form:form id="searchForm" modelAttribute="manageStockIn" action="" method="post" class="form-inline mt20">
			<form:hidden path="from"/>
				<c:if test="${empty manageStockIn.from}">
					<div class="col-xs-1 pull-right">
						<a href="${ctx}" class="btn btn-success w100" ><span class="glyphicon glyphicon-arrow-left"></span>返回主页</a>
					</div>
				</c:if>
				<div class="text-right">
					<c:if test="${type == '0'}">
						<div class="form-group text-left" style="position: absolute; left: 15px;">
							<span class="sub-title">疫苗入库</span>
						</div>
						<div class="form-group">
							<label>出库单号：</label>
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
						<th>数量</th>
						<th>库存</th>
						<th>规格</th>
						<th>生产企业</th>
						<th>有效期</th>
<!-- 						<th>成本价</th> -->
						<th>售价</th>
						<!--  <th>签批发号</th> -->
						<th>剂量(剂/支)</th>
						<!--  <th>生产时间</th> -->
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