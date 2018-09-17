<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>市平台入库配置</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.parentTable{margin: 5px 0 30px 0; border: 3px solid rgb(127, 154, 143); } .parentTable>thead{/* 			background: #fff; */ font-size: 16px; border: 3px solid rgb(127, 154, 143); } .childTable{width: 100%; margin: 0px; } .childTable>tbody{background:rgb(175, 217, 223); } .table>thead>tr>td, .table-bordered>thead>tr>th{border-color: rgb(127, 154, 143); color: #000; } .table>tbody>tr>td, .table-bordered>tbody>tr>th{border-color: #404040; color: #000; padding: 2px; font-size: 14px; } .childOrderBtn{text-align: center; } .tc{text-align: center; } /* radio 选择样式 */ .operation{display: inline; } .operation input{display: none; } .s + i {display: block; float: left; width: 20px; height: 20px; margin: 0 5px; background: url(${ctxStatic}/img/check0.png) 0 0 no-repeat; background-size: cover; } .s:checked + i {background: url(${ctxStatic}/img/check1.png) 0 0 no-repeat; background-size: cover; } .operation label{margin: 1px 0; } .red{border-color: red; border-width: 2px; }
		.layui-layer-dialog .layui-layer-padding {padding: 40px 20px 20px 80px!important;text-align: left;font-size: 25px!important;height:auto!important;}
		.layui-layer-dialog .layui-layer-content .layui-layer-ico {top: 38px!important;left: 35px!important;}
		.layui-layer-btn a {height: 35px;line-height: 35px;padding: 0 20px;font-size:16px;}
	</style>
	<script type="text/javascript">
		var officecode = '${childcodePre}';
		/* 当前门诊所有科室信息  */
		var offices = JSON.parse('${offices}');
		var officeOps = "<option value=''> </option>";
		var username = '${fns:getUser().name}';
		
       	var storeup = "${storeup}";
       	var token = '${token}';
		
		$.each(offices, function(i,t){
			officeOps+="<option value='" + t.code + "'>" + t.name + "</option>"
		});
		var vaccModel = JSON.parse('${vaccModel}');
		
        /* 根据小类id查询，模型大类 */
        function getMnum(vid){
        	for(var i = 0; i < vaccModel.length; i ++){
        		if(vaccModel[i].id == vid){
        			return vaccModel[i].mNum;
        		}
        	}
        }
	
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        $(function(){
        	//输入校验
			$(document).on("change",".in",function(){
				if($(this)){
					$(this).removeClass("red");
				}
			});
			$(document).on("click",".orderUp",function(){
				$(this).addClass("active").siblings().removeClass("active");
			});
			$(document).on("change",".inNum",function(){
				var vaccNum = $(this).attr("data-max");
				var inNum = $(this).val();
				if(parseInt(vaccNum) < parseInt(inNum)){
					$(this).val('');
					error("入库库存不能大于下发数量");
				}
				if(parseInt(vaccNum) < 1){
					$(this).val('');
					error("入库库存不能小于0");
				}
			});
			
			/* 单个入库 */
			$(document).on("click",".storage",function(){
				var delist = genDelist($(this).parent().parent());
				var orderid = $(this).parent().parent().parent().parent().attr("data-orderid");
				FromEpiToStore(delist, orderid, officecode);
			});
			
			
			/* 批量入库 */
			$(document).on("click",".storageAll",function(){
				var _this = $(this);
				layer.confirm('确认按以上数量全部入库?', {icon:7}, function(index){
					var orderid = _this.attr("data-orderid"); 
					var trs = $("table[data-orderid=" + orderid + "] .orderUp");
					var delist = genDelist(trs);
					var orderid = _this.attr("data-orderid");
					FromEpiToStore(delist, orderid, officecode);
				    layer.close(index);
				});   
			});
			
			/* 退回 */
			$(document).on("click",".refuseAll",function(){
				var _this = $(this);
				layer.confirm('确认按以上数量全部入库?', {icon:7}, function(index){
					var load = layer.load();
					var orderid = _this.attr("data-orderid"); 
					$.ajax({
						type: "post",
					    dataType:"json",
					    url : storeup + "refuseOrder?token=${token}&appid=${appid}",
						data:{"orderId":orderid, "username":username, "officecode":officecode},
						success:function(data){
							layer.close(load); 
							if(data.result){
								success("退回操作成功");
								$(".parentTable[data-orderid=" + orderid + "]").remove();
							}else{
								error("操作失败,请重试");
							}
						},
						error:function(a,b,c){
							layer.close(load); 
							error("操作失败,请重新加载后重试");
						}
					});
				    layer.close(index);
				});  
			});
		})
        
        $(function(){
        	if(!token){
        		toastrMsg("授权失败","error")
        		return;
        	}
        	store(officecode,storeup);
        })
        
        //请求市平台，出库单
        function store(officecode,storeup){
        	var load = layer.load();
        	var list = new Array();
        	$.ajax({
				type: "post",
			    dataType:"json",
			    url : storeup + "receiveList?code=" + officecode + "&token=${token}&appid=${appid}",
			    timeout:60000,
				success : function(data) {
					layer.close(load); 
					console.info(data);
					if(data.success && data.data && data.data.length > 0){
						layer.msg("出库单请求成功",{"icon":1});
						for(var i = 0 ; i< data.data.length; i ++){
							if(!data.data[i].list){
								continue;
							}
							for(var j = 0; j < data.data[i].list.length; j ++){
								if(data.data[i].list[j].sellprice > 0){
// 									data.data[i].list[j].orignprice = data.data[i].list[j].sellprice;
									data.data[i].list[j].sellprice = data.data[i].list[j].sellprice + 20;
								}
								if(data.data[i].list[j].bsProduct && data.data[i].list[j].bsProduct.cType == '2'){
									data.data[i].list[j].bsProduct.cType = "二类";
								}else if(data.data[i].list[j].bsProduct && data.data[i].list[j].bsProduct.cType == '1'){
									data.data[i].list[j].bsProduct.cType = "一类";
								}
								
							}
						}
						var html = "";
						var tpl = $("#tpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						html = Mustache.render(tpl, data);
						$(".ibox").html(html);
						
						//加载科室信息
						$(".rooms").each(function(){
							var _rooms = $(this);
							_rooms.html(officeOps);
							for(var i = 0; i < offices.length; i ++){
								if(offices[i].vaccines && offices[i].vaccines.indexOf(getMnum(_rooms.attr("data-vid"))) > -1){
									_rooms.val(offices[i].code);
									break;
								}
							}
						});
					}else{
						layer.msg(data.msg,{"icon":7});
					}
				},
				error:function(){
					layer.close(load); 
					error("市平台获取订单失败，请重新加载~");
				}
			});
        }
        	
        /* 获取tr中的数据，组织成请求参数 */
        function genDelist(trs){
        	var delist = new Array();
        	var returnFlag = true;
			for(var i = 0; i < trs.length; i ++){
				var de = new Object();
				de["id"] = $(trs[i]).attr("data-id");
				de["inNum"] = $(trs[i]).find(".inNum").val();
				de["rooms"] = $(trs[i]).find(".rooms").val();
				de["sellprice"] = $(trs[i]).find(".sellprice").val();
				if(!de["id"]){
					error("入库信息错误");
					returnFlag = false;
				}
				if(!de["inNum"] || isNaN(de["inNum"]) || de["inNum"] == 0){
					error("入库数量错误");
					$(trs[i]).find(".inNum").addClass("red");
					returnFlag = false;
				}
				if(!de["rooms"]){
					error("入库科室不能为空");
					$(trs[i]).find(".rooms").addClass("red");
					returnFlag = false;
				}
				if(!de["sellprice"] || isNaN(de["sellprice"])){
					error("入库价格不能为空");
					$(trs[i]).find(".sellprice").addClass("red");
					returnFlag = false;
				}
				delist.push(de);
			}
			//若有一个参数错误则返回空
			if(!returnFlag){
				return ;
			}
			console.info(delist);
			return delist;
        }
        
  		/* 请求市平台入库 */
        function FromEpiToStore(delist, orderid, officecode){
        	//参数错误，则终止请求
        	if(!delist || delist.length == 0){
        		return ;
        	}
			$.ajax({
				url : storeup + "receiveOrder?token=${token}&appid=${appid}",
				data : {"orderId":orderid, "officecode":officecode, "delist":JSON.stringify(delist)},
				async: false,
				success : function(data){
					console.info("市平台入库操作成功",data);
					if(data && data.success){
						//订单是否完成 true-完成 false-未完成
						var result = data.data.hasFinished;
						var detaillist = data.data.detaillist;
						/* 本地入库 */
						success("平台请求成功，正在保存");
						$.ajax({
							type : "POST",
							url : "${ctx}/product/bsManageProduct/stockInFromEpi",
							data: {"list":JSON.stringify(data.data.bpList)},
							async: false,
	// 						timeout:60000,
							success : function(data) {
								console.info(data);
								if(data.code == "200"){
									success("入库成功");
									var _table =  $(".parentTable[data-orderid=" + orderid + "]");
									//刷新页面
									if(result){
										_table.remove();
									}else{
										$(".refuseAll[data-orderid=" + orderid + "]").remove();
										$.each(detaillist, function(i,t){
											var _tr = _table.find(".orderUp[data-id=" + t.id + "]");
											if(t.vaccNumRest == 0){
												_tr.remove();
											}else{
												_tr.find(".vaccNum").html(t.vaccNumRest);
												_tr.find(".inNum").val(t.vaccNumRest);
												_tr.find(".inNum").attr("data-max",t.vaccNumRest);
											}
										});	
									}
								}else{
									layer.msg(data.msg,{"icon":7});
								}
							},
							error:function(){
								layer.msg("本地入库连接超时，清稍后再试~");
							}
						});
					}
				}
			});
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li ><a href="${ctx}/product/bsManageProduct/">疫苗库存管理</a></li>
		<li ><a href="${ctx}/manage_stock_in/manageStockIn">出入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccine">疫苗名称配置</a></li>
		<li class="active"><a href="${ctx}/product/bsManageProduct/store">市平台入库配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageVaccinenum">儿童免疫程序配置</a></li>
		<li ><a href="${ctx}/yiyuan/sysBirthHospital">出生医院配置</a></li>
		<li ><a href="${ctx}/shequ/sysCommunity">社区配置</a></li>
		<li ><a href="${ctx}/kindergarten/bsKindergarten">幼儿园配置</a></li>
		<li ><a href="${ctx}/department/sysVaccDepartment/">接种门诊配置</a></li>
		<li ><a href="${ctx}/holiday/sysHoliday/">节假日配置</a></li>
		<li ><a href="${ctx}/vaccine/bsManageBinded/">联合疫苗替代原则列表</a></li>
		<li><a href="${ctx}/sys/office/workTime/">工作日时段配置</a></li>
	</ul> 
	<div class="ibox">
		<sys:message content="${message}"/>
		<script type="text/template" id="tpl">
		{{#data}}<table class="table table-bordered parentTable" data-orderid={{id}}>
			<thead>
				<tr>
					<td><strong>单号:</strong><span>{{orderNo}}</span></td>
					<td><strong>供货单位:</strong><span>{{supplyorgName}}</span></td>
					<td><strong>供货经手人:</strong><span>{{consignorName}}</span></td>
				</tr>
				<tr>
					<td><strong>类型:</strong><span>{{orderType1}}</span></td>
					<td><strong>收货单位:</strong><span>{{receiveorgName}}</span></td>
					<td><strong>收货经手人:</strong><span>{{consigneeName}}</span></td>
				</tr>
				<tr>
					<td><strong>日期:</strong><span>{{outboundDate}}</span></td>
					<td><strong>所属单位:</strong><span>{{createorgName}}</span></td>
					<td class="childOrderBtn">
						<span class="glyphicon glyphicon-chevron-up" aria-hidden="true">收起</span>				
					</td>
				</tr>
			</thead> 
			<tbody>
				<tr>	
					<td colspan="3">
						<table class="table table-bordered childTable" data-orderid={{id}}>
							<tbody >
								<tr>
									<th>疫苗名称</th>
									<!-- <th>英文名称</th> -->
									<th>批次编号</th>
									<th>生产企业</th>
									<!-- <th>厂商编码</th> -->
									<th>规格</th>
									<th>类型</th>
                                    <th>成本价</th>
									<th>售价</th>
									<th>有效期</th>
									<th>疫苗数量</th>
									<th>入库数量</th>
									<th>入库科室</th>
									<th>操作</th>
								</tr>
								{{#list}}
								<tr class="orderUp" data-id="{{id}}">
									<td>{{bsVaccineBatchno.vaccineName}}</td>
									<!--  <td>{{codeAll}}</td> -->
									<td>{{bsVaccineBatchno.batchno}}</td>
									<td>{{bsVaccineBatchno.companyName}}</td>
									<!-- <td>{{bsVaccineBatchno.companyCode}}</td> -->
									<td>{{bsVaccineBatchno.spec}}</td>
									<td>{{bsProduct.cType}}</td>
									<td>￥{{orignprice}}</td>
									<td>￥<input type="text" class="sellprice w50 in" value="{{sellprice}}" /></td> 
									<td>{{bsVaccineBatchno.outBoundDate}}</td>	
									<td class="vaccNum">{{vaccNumRest}}</td>
									<td><input type="text" class="inNum w50 in" value="{{vaccNumRest}}" data-max="{{vaccNumRest}}" /></td>
									<td><select type="text" class="rooms w100 in" data-vid="{{bsProduct.vid}}"></select></td>
									<td>
										<button type="button" class="btn btn-xs btn-primary storage" data-id="{{id}}">入库</button>
										<!-- <div class="operation">
											<label><input type="radio" value="0" name="{{id}}" class="s" checked="checked"><span>入库</span></label>
										</div>
											<div class="operation">
											<label><input type="radio" value="1" name="{{id}}" class="s"><i></i><span>退回</span></label>
										</div> -->
									</td>								
								</tr>
								{{/list}}
							</tbody>
						</table>
						<div class="text-right padding10">
							{{^vaccNumHasDone}}
								<button type="button" data-orderid={{id}} class="btn btn-default refuseAll" >全部退回</button>
							{{/vaccNumHasDone}}
							<button type="button" data-orderid={{id}} class="btn btn-success storageAll" >全部入库</button>
						</div>
					</td>	
				</tr>	
			</tbody>		
		</table>{{/data}}
		<!-- <tfoot>
			<tr>
				<td colspan="3" class="tc">
					<a href="javascript:void(0)" onclick='storeUp("{{id}}",this)' class='btn btn-info w100'>提交</a>
					<a href="javascript:void(0)" onclick='storeDown("{{id}}",this)' class='btn btn-info w100' style='margin-left:20px;'>重置</a>
				</td>
			</tr>
		</tfoot> -->
		</script>
	</div>
	<div style="padding: 20px; text-align: center;">
		<a class="btn btn-success btn-lg " href="${ctx}/product/bsManageProduct/store?option=reload" onclick="layer.load()">重新加载</a>
	</div>
</body>
</html>