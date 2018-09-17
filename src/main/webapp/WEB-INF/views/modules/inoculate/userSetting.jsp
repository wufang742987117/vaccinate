<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户首选项</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
		.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th{text-align: center;}
		.date-wrap{position: relative; height: 34px;}
		.inputclass{border: none;position: absolute;width: 80%;height: 28px;top: 0;left: 0;text-align: center;margin: 3px 10%;background: #fff;}
		.hid{display: none;}
	</style>
	
	<script type="text/javascript">
		var from = '${from}';
		var batchTpl = '<tr class="batchs"> <td class="date-wrap"> <select class="inputclass vaccId"> </select> </td> <td class="date-wrap"> <select class="inputclass batch"> </select> </td> <td><button class="btn btn-xs btn-del">删除</button></td> </tr>';
		var vaccs = JSON.parse('${vaccs}');
		var vaccsOpt = "<option value=''>--请选择--</option>";
		$.each(vaccs,function(i,t){
			vaccsOpt += "<option value=" + t.id + ">" + t.name + "</option>";
		});
		$(function(){
			//初始化
			$("input[name=contentTableD]").val(preference.contentTableD);
			$(".queueRefreshWrap input[value='" + preference.queueRefresh + "']").prop("checked",true);
			$(".queueRefreshWrap input[value='" + preference.queueRefresh + "']").parent().addClass("checked")
			$(".signReqWrap input[value='" + preference.signReq + "']").prop("checked",true);
			$(".signReqWrap input[value='" + preference.signReq + "']").parent().addClass("checked")
			$(".checkReqWrap input[value='" + preference.checkReq + "']").prop("checked",true);
			$(".checkReqWrap input[value='" + preference.checkReq + "']").parent().addClass("checked")
			$(".quickOptionWrap input[value='" + preference.quickOption + "']").prop("checked",true);
			$(".quickOptionWrap input[value='" + preference.quickOption + "']").parent().addClass("checked")
			$("input[name=queueDelay]").val(preference.queueDelay);
			$("input[name=fontSize]").val(preference.fontSize);
			$("select[name=inPosition]").val(preference.inPosition);
			if(preference.vaccBatch){
				$.each(preference.vaccBatch, function(i,t){
					addBatchRow(t);
				});
			}
			
			
			//选择疫苗加载批号信息
			$(document).on("change", ".vaccId", function(){
				var vaccId = $(this).val();
				//验证是否已选
				var isExist = false;
				$(this).parent().parent().siblings().find(".vaccId").each(function(){
					if($(this).val() && $(this).val() == vaccId){
						isExist = true;
					}
				});
				if(isExist){
					layer.msg("该疫苗默认批次已添加",{"icon":7});
					$(this).val("");
					return false;
				}
				//加载批号
				var _bat = $(this).parent().parent().find(".batch");
					$.ajax({
						type : "POST",
						url : "${ctx}/product/bsManageProduct/findQueueViewListApi",
						data : {"vaccineid" : vaccId, "storenumIsNull":true},
						dataType : "json",
						async: false,
						success : function(data) {
						var	html1="";
						$.each(data, function(idx, item) { //循环对象取值
							html1 += "<option value='" + item.batchno + "' data-vaccineid='" + item.vaccineid +"'>"+ item.batchno + "(" + item.manufacturer + ")" + "</option>";
						});
							_bat.html(html1);
						}
					}); 
					
			});
			
			//删除一行
			$(document).on("click", ".btn-del", function(){
				$(this).parent().parent().remove();
			});
			
			//添加一行
			$(document).on("click", ".btn-add", function(){
				var vaccId = $(this).parent().parent().find(".vaccId");
				addBatchRow();
			});
			
			$(document).on("click", ".btn-save", function(){
				var pp = new  Object()
				if($(".queueRefreshWrap input:checked").val()){
					pp.queueRefresh = parseInt($(".queueRefreshWrap input:checked").val());
				}
				if($(".signReqWrap input:checked").val()){
					pp.signReq = parseInt($(".signReqWrap input:checked").val().trim());
				}
				if($(".checkReqWrap input:checked").val()){
					pp.checkReq = parseInt($(".checkReqWrap input:checked").val().trim());
				}
				if($(".quickOptionWrap input:checked").val()){
					pp.quickOption = parseInt($(".quickOptionWrap input:checked").val().trim());
				}
				if($("input[name=queueDelay]").val()){
					pp.queueDelay = parseInt($("input[name=queueDelay]").val().trim());
				}
				if($("input[name=contentTableD]").val()){
					pp.contentTableD = $("input[name=contentTableD]").val().trim();
				}
// 				pp.fontSize = parseInt($("input[name=fontSize]").val().trim());
				if($("select[name=inPosition]").val()){
					pp.inPosition = parseInt($("select[name=inPosition]").val().trim());
				}
				pp.vaccBatch = new Array();
				$(".batchs").each(function(){
					var vaccBatch = new Object();
					vaccBatch.vaccId = $(this).find(".vaccId").val();
					vaccBatch.batch = $(this).find(".batch").val();
					pp.vaccBatch.push(vaccBatch);
				});
				console.info("保存首选项",pp);
				$.ajax({
					url:"${ctx}/sys/user/saveUserSetting",
					data:{"preference":JSON.stringify(pp)},
					success:function(data){
						if(data.code==200){
							preference = pp;
							parent.preference = pp;
							if(isExitsFunction("parent.finishUserSetting")){
								parent.finishUserSetting();
							}
							layer.msg("保存成功",{"icon":1});
							setTimeout(function() {
								parent.layer.closeAll();
							}, 1000)
						}else{
							layer.msg("保存失败", {"icon":7});
							console.error(data.msg);
						}
					},
					error:function(){
					
					}
				});
				
			});
			
			if(from && from == 'djt'){
				$(".djt").show();
			}else{
				$(".queue").show();
			}
			
			//登记台现在默认排号科室
			var rooms = localStorage["roomcode"];
			if(rooms){
				for(var i in rooms.split(",")){
					if(rooms.split(",")[i]){
						$(".roomcode[value=" + rooms.split(",")[i] + "]").prop("checked", true);
						$(".roomcode[value=" + rooms.split(",")[i] + "]").parent().addClass("checked");
					}
				}
			}
			$(".roomcodeLab").click(function(){
				var _input = $(this).find("input");
				choiceRoom(_input);
				parent.refreshRoomcode();//刷新默认排号科室状态显示
			});
			$(".roomcodeLab .iCheck-helper").click(function(){
				var _input = $(this).siblings("input");
				choiceRoom(_input);
				parent.refreshRoomcode();//刷新默认排号科室状态显示
			});
			
		})
		//选择默认科室
		function choiceRoom(i){
			var local = localStorage["roomcode"];
			local = local?local:"";
			if(i.is(":checked")){
				localStorage["roomcode"] = local + i.val() + ",";
			}else{
				localStorage["roomcode"] = local.replace((i.val() + ","),"");
			}
		}
		
		//添加一行
		function addBatchRow(t){
			$(".batchTbody").append(batchTpl);
			var _last = $(".batchTbody :last-child");
			var _lastV = _last.find(".vaccId");
			var _lastB = _last.find(".batch");
			
			_lastV.html(vaccsOpt);			
 			if(t){
				_lastV.val(t.vaccId);
// 				_lastV.change();
				$.ajax({
					type : "POST",
					url : "${ctx}/product/bsManageProduct/findViewListApi",
					data : {"vaccineid" : t.vaccId},
					dataType : "json",
					async: false,
					success : function(data) {
					var	html1="";
					$.each(data, function(idx, item) { //循环对象取值
						html1 += "<option value='" + item.batchno + "' data-vaccineid='" + item.vaccineid +"'>"+ item.batchno + "(" + item.manufacturer + ")" + "</option>";
					});
						_lastB.html(html1);
					}
				}); 
				_lastB.val(t.batch);
			}
		}
		
	</script>

</head>
<body>
	<div class="ibox">
		<div class="">
			<input type="text" name="contentTableD" hidden="hidden">
			<div class="form-group col-xs-12 hid queue">
				<label class="col-xs-5 control-label text-right">接种完成是否刷新：</label>
	                <div class="col-xs-6 queueRefreshWrap">
	                	<label><input type="radio" name="queueRefresh" value="0"  class="i-checks">不刷新</label>
	                	<label><input type="radio" name="queueRefresh" value="1"  class="i-checks">刷新</label>
					</div>	                
			</div>
			<div class="form-group col-xs-12 hid queue">
				<label class="col-xs-5 control-label text-right">科室叫号延迟（秒）：</label>
	                <div class="col-xs-6">
	                	<input type="text" name="queueDelay" class="form-control">
					</div>	                
			</div>
			
			<!-- <div class="form-group col-xs-12"> 
				<label class="col-xs-5 control-label text-right">页面字体：</label>
	                <div class="col-xs-6">
	                	<input type="text" name="fontSize" class="form-control">
					</div>	                
			</div> -->
			
			<div class="form-group col-xs-12 hid queue">
				<label class="col-xs-5 control-label text-right">默认接种部位：</label>
	                <div class="col-xs-6">
	                	<select class="form-control valid" name="inPosition">
						<c:forEach items="${fns:getDictList('position')}" var="lab">
							<option value="${lab.value}">${lab.label}</option>
						</c:forEach>
						</select>
					</div>
			</div>
			<div class="form-group col-xs-12 hid queue djt">
				<label class="col-xs-5 control-label text-right">是否强制签字：</label>
	                <div class="col-xs-6 signReqWrap">
	                	<label><input type="radio" name="signReq" value="0"  class="i-checks">不强制签字</label>
	                	<label><input type="radio" name="signReq" value="1"  class="i-checks">强制签字</label>
					</div>	                
			</div>
			<div class="form-group col-xs-12 hid queue">
				<label class="col-xs-5 control-label text-right">是否提示盘点：</label>
	                <div class="col-xs-6 checkReqWrap">
	                	<label><input type="radio" name="checkReq" value="0"  class="i-checks">不提示盘点</label>
	                	<label><input type="radio" name="checkReq" value="1"  class="i-checks">提示盘点</label>
					</div>	                
			</div>
			
			<div class="form-group col-xs-12 hid djt">
				<label class="col-xs-5 control-label text-right">是否快速登记：</label>
	                <div class="col-xs-6 quickOptionWrap">
	                	<label><input type="radio" name="quickOption" value="0"  class="i-checks">正常排号</label>
	                	<label><input type="radio" name="quickOption" value="1"  class="i-checks">快速登记</label>
					</div>	                
			</div>
			<div class="form-group col-xs-12 hid djt">
				<label class="col-xs-5 control-label text-right">指定排号科室：</label>
	                <div class="col-xs-5">
	                	<c:forEach items="${officelist}" var="off">
	                			<label class="i-checks roomcodeLab"><input type="checkbox" name="roomcode" class="roomcode" value="${off.code }"/>${off.name }</label>
						</c:forEach>
					</div>
			</div>
			
			<div class="form-group col-xs-12 hid queue">
				<label class="col-xs-5 control-label text-left">疫苗默认批号：</label>
				<table class="table">
					<thead>
						<tr>
							<th>疫苗</th>
							<th>批号</th>
							<th style="width: 50px;">操作</th>
						</tr>
					</thead>
					<tbody class="batchTbody">
					</tbody>
					<tfoot>
						<td colspan="2"></td>
						<td><button class="btn btn-primary btn-xs btn-add">添加</button></td>
					</tfoot>
				</table>
			</div>
			
			<div class="form-group col-xs-12 text-center mt20" style="height: 27px;">
				<div class="row">
                	<input type="button" id="btn" value="保存" class="btn btn-success btn-save w100" >
                	<input type="button" id="cancel" onclick="javascript:parent.layer.closeAll()" value="取消" class="btn btn-default w100" >
				</div>
			</div>
			
		</div>
	</div>
</body>
</html>