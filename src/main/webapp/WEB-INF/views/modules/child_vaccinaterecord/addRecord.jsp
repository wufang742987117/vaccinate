<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>补录</title>
<meta name="decorator" content="default" />
<style type="text/css">
	#recordTbody tr td{
		padding: 2px;
	}
	
	.inputInTable{
	    padding: 0px 2px;
	    border: none;
        width: 100%;
    }
</style>
<script type="text/javascript">
	$(function(){
		$("#inputForm").validate({
			submitHandler : function(form) {
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")|| element.is(":radio")|| element.parent().is(".input-append")) {
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
				
		});
			
		/* 初始化表格 */
		var recs = JSON.parse('${vacclist}');
		for(var i in recs){
			var rec = recs[i];
			var targetTds = $("#recordTbody>tr[data-id=" + rec.vaccineid + "]>td");
			for(var j in targetTds){
				var _td = $(targetTds[j]);
				if(!_td.html()){
					_td.html(SimpleDateFormat(new Date(rec.vaccinatedate),"yyyy-MM-dd"));
					_td.addClass("history");
// 					_td.attr("data-id",rec.id);
					break;
				}
			}
		}
			
		$(".Manage").click(function(){
			if($(this).hasClass("history")){
				return ;
			}
			if($(this).html()){
				transForm(this);
			}else{
				var vaccineId = $(this).parent().attr("data-id");
				var tds = $("#recordTbody>tr[data-id=" + vaccineId + "]>td");
				for(var i in tds){
					if(!$(tds[i]).html()){
						transForm(tds[i]);
						return;
					}
				}		
			}
		});
			
			
		$("#btnsave").click(function(){
			var record_addr=$("#record_addr").val();
			var radio=$(".record_addr:checked").val();
			if(radio==2){
				if(!record_addr){
					toastrMsg("接种地点不能为空","error");
					return ;
				}
			}else {
				record_addr = radio;
			}
			layer.confirm("确定要补录上述疫苗吗？", {
				btn: ['确认','取消'], //按钮
				shade: true, //不显示遮罩
				icon : 0
			}, 
			function(index){
				var data = new Array();
				$(".new").each(function(){
					var recode=new Object();
					recode.vaccineId = $(this).parent().attr("data-id");
					recode.date = $(this).html();
					recode.pin = $(this).attr("data-pin");
					data.push(recode);
				});
				if(data.length == 0){
					toastrMsg("未添加记录","error");
					return ;
				}
				console.info(data);
				var index = layer.load();
				$.ajax({
					url:"${ctx}/child_vaccinaterecord/childVaccinaterecord/saveRecord?childid=${childid}",
					data : {"valArr" : JSON.stringify(data), "office":record_addr},
					type:"post",
					success:function(data){
						layer.close(index); 
						if(data && data.code == 200){
							toastrMsg("补录成功","success");
							setTimeout(function(){parent.closeForm1('${childcode}');}, 1000);
						}else{
							toastrMsg(data.msg,"error");
							setTimeout(function(){parent.closeForm1('${childcode}');}, 3000);
						}
					},
					error:function(){
						layer.close(index);
						toastrMsg("保存失败，请重新补录","error");
					}
				});
				
			});
		} );
			
		$("#btncancel").click(function(){
			parent.layer.closeAll();
		});
		
	});
	
		/* 将表格内的元素转换为文本框 */
		function transForm(thi){
			var _this = $(thi);
			var txt = _this.html();
			if(txt.indexOf('<input') > -1){
				return ;
			}
			_this.html("");
			_this.html("<input type='text' value='"+txt+"' onblur='getValue(this)' onkeyup='autoWrite(this)' maxlength='10' class='inputInTable'>");
			_this.find("input").focus();
		}
			
			
		/* 获取文本框内的值 */
		function getValue(thi){
			var _this = $(thi);
			var _parent = _this.parent();
			var txt = _this.val();
			var id = _this.parent().parent().attr("data-id");
			if(!txt.length){
				_parent.html("");
				return ;
			}
			/* 校验时间 */
			var curDate=Date.parse(txt);
			if(txt.length != 10 || !curDate){
				_parent.html("");
				toastrMsg("时间格式错误","primary");
				return ;
			}
			/* 停用三价脊灰 */
			if((id=='0301'||id=='0302'||id=='0304'||id=='0305') && curDate>=Date.parse('2016-05-01')){
				_parent.html("");
				toastrMsg("2016-05-01后停用三价OPV（tOPV）,开始使用二价OPV（bOPV）","primary");
				return ;
			}
			if(_this.parent().attr("data-pin") != "1"){
				/* 判断与上一针的时间差 */
				var preDate = Date.parse(_this.parent().prev().html());
				if(preDate > curDate){
					_parent.html("");
					toastrMsg("时间不能早于上一针时间","primary");
					return ;
				}
			}				
			_parent.html(txt);
			if(_parent.html()){
				_parent.addClass("new");
			}else{
				_parent.removeClass("new");
			}
		}
			
		/* 日期自动补全 */
		function autoWrite(thi){
			 var _this = $(thi); 
			 var txt = _this.val();
			 /* 监控键盘事件 */
			 var key=window.event.keyCode; 
// 			 if(key == 37 && _this.parent().attr("data-pin") != "1"){
// 			 	_this.parent().prev().click();
// 			 }
			 if(key == 38){
			 	var idx = _this.parent().parent().attr("data-idx");
			 	if(idx != '0'){
			 		idx = parseInt(idx);
			 		var target = $("#recordTbody>tr[data-idx=" + (idx - 1) + "]>td[data-pin=" + _this.parent().attr("data-pin") + "]");
			 		if(target.hasClass("history")){
			 			$("#recordTbody>tr[data-idx=" + (idx - 1) + "]>td[data-pin=5]").click();
			 		}else{
			 			target.click();
			 		}
			 		
			 	}
			 }
// 			 if(key == 39 && _this.parent().attr("data-pin") != "1"){
// 			 	_this.parent().next().click();
// 			 }
 			 if(key == 40){
			 	var idx = _this.parent().parent().attr("data-idx");
		 		idx = parseInt(idx);
		 		var target = $("#recordTbody>tr[data-idx=" + (idx + 1) + "]>td[data-pin=" + _this.parent().attr("data-pin") + "]");
		 		if(target.hasClass("history")){
		 			$("#recordTbody>tr[data-idx=" + (idx + 1) + "]>td[data-pin=5]").click();
		 		}else{
		 			target.click();
		 		}
			 }			 			 
			 if(txt.length==4 &&key!=8){
				_this.val(txt+'-');
			 }
			 if(txt.length==7&&key!=8){
				_this.val(txt+'-');
			 }
			 if(txt.length == 10){
			 	_this.parent().next().click();
			 }
		}
		
</script>
<style type="text/css">
.date-wrap{position: relative;}
.date{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
.inputclass{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
table{-moz-user-select: none; -khtml-user-select: none; user-select: none; }
table input,select{background: rgba(255,255,255,0.65) !important; }
/* .tr_color{background-color: rgba(245, 245, 246, 0.5);} */
.tr_color{background-color: rgba(245, 245, 211, 0.23);} 
.history{background-color: #AEEEEE}
</style>
</head>
<body>
	<div class="ibox">
			<div class=" col-sm-12 " >
				<label style="font-size: 1.8em;" class="col-sm-12 text-center">可补录疫苗</label>
				<sys:message content="${message}" />
				<table class="table  table-bordered table-condensed"  id="table">
					<thead>
						<tr class="finished">
							<th style="color: black;width: 250px;">疫苗名称</th>
							<th style="color: black;width: 120px;">第一针</th>
							<th style="color: black;width: 120px;">第二针</th>
							<th style="color: black;width: 120px;">第三针</th>
							<th style="color: black;width: 120px;">第四针</th>
							<th style="color: black;width: 120px;">第五针</th>
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${list}" var="bsManageVaccine" varStatus="vs">
								<tr data-mNum='${bsManageVaccine.mNum}' data-id='${bsManageVaccine.id}' data-idx='${vs.index}'>
									<td class="bsManage" >${bsManageVaccine.name }</td>
									<td class="Manage" data-pin='1'></td>
									<td class="Manage" data-pin='2'></td>
									<td class="Manage" data-pin='3'></td>
									<td class="Manage" data-pin='4'></td>
									<td class="Manage" data-pin='5'></td>
								</tr>	
						</c:forEach>
						
					</tbody>
				</table>
			</div>
				<div class="col-sm-8" >
					<div  style=" float: left;width: 100%;">
						<label><input type="radio" class="record_addr" name="record_choose" value="${office.code}" style="text-align: left;">补录本地接种信息(${office.name})</label>
					</div>
					<div  style=" float: left;width: 100%;">
						<label>
							<input type="radio" class="record_addr" name="record_choose" checked value="2">补录外地接种信息
							<span class="record_addr_info">&nbsp;&nbsp;&nbsp;接种地点：<input type="text" id="record_addr" name="record_addr" value="0000000000"></span>
						</label>
					</div>
				</div>
				<div class="col-sm-4" style="text-align: center;" >
					<button type="button" class="btn btn-lg btn-primary" id="btnsave"  >保存</button>
					<button type="button" class="btn btn-lg btn-primary" id="btncancel" >取消</button>
				</div>
		</div>
</body>
</html>