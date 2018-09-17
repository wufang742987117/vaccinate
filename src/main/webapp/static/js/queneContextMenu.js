/**
 * 
 */

$(document).ready(function(){
	
	context.init({preventDoubleContext: false});
	
	function attachContext(selector) {
		var _thi = $(selector);
	    context.attach(selector, [
	    {header: _thi.find(".recVaccName").html() + " 第" + _thi.find(".text-circle").html() + "剂"},
	    {
	        text: "修改",
	        action: function(e) {
	        	var _tr = context.getClickEle();
	        	if(!_tr.find(".recDate").html()){
	        		layer.msg(_thi.find(".recVaccName").html() + " 第" + _thi.find(".text-circle").html() + "剂 尚未完成，不能修改！",{"icon":7});
	        		return ;
	        	}
//	        	if(_tr.hasClass("option")){
//	        		_tr.dblclick();
//	        		return ;
//	        	}
	        	openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord?id="+_tr.attr("data-id"), "修改接种记录", "", "");
	        	
	        }
	    },{
	        text: "删除",
	        action: function(e) {
	        	var _tr = context.getClickEle();
	        	if(!_tr.find(".recDate").html()){
	        		layer.msg(_thi.find(".recVaccName").html() + " 第" + _thi.find(".text-circle").html() + "剂 尚未完成，不能修改！",{"icon":7});
	        		return ;
	        	}
//	        	if(_tr.hasClass("option")){
//	        		_tr.dblclick();
//	        		return ;
//	        	}
	        	var txt = "删除异地的接种记录将不回退库存<br>"
	        	if(_thi.attr("data-source") != 3){
	        		txt = "删除今日以前的记录库存将不回退库存<br>";
	        		if(SimpleDateFormat(new Date(),"yyyy-MM-dd") == _thi.find(".recDate").html()){
		        		txt = "删除今日的接种记录将回退库存<br>"
		        	}
	        	}   	
	        	layer.confirm(txt + "确认删除  <strong>" + _tr.find(".recVaccName").html() + " 第" + _tr.find(".text-circle").html() + "剂？</strong>",
	        			{ 
	        				btn: ['确认','取消'], //按钮
			    	        shade: true, //不显示遮罩
			    	        icon : 3,
			    	        offset : ['300px' , '35%']
        				},
	        			function(){
        					$.ajax({
        						url:ctx + "/child_vaccinaterecord/childVaccinaterecord/deleteFromQuene?id=" + _tr.attr("data-id"),
        						type:"DELETE",
        						success:function(data){
        							if(data == 200){
        								layer.msg("删除成功",{"icon":1, "time":800});
            			        		setTimeout(function() {
            			        			location.href = ctx + "/inoculate/quene/inoculateMain?childid=" + childcode;
            			        		}, 800)
        							}else{
        								layer.msg("删除失败",{"icon":2, "time":800});
        							}
        						},
        						error:function(){
        							layer.msg("删除失败",{"icon":2, "time":800});
        						}
        					});
			        	});
	        }
	    }
	    ])
	}
	
	$("#recordTbody > tr").each(function(){
		var id = $(this).attr("id");
	    attachContext("#" + id);
	});
	
	context.settings({compress: true});
	
	$(document).on('mouseover', '.me-codesta', function(){
		$('.finale h1:first').css({opacity:0});
		$('.finale h1:last').css({opacity:1});
	});
	
	$(document).on('mouseout', '.me-codesta', function(){
		$('.finale h1:last').css({opacity:0});
		$('.finale h1:first').css({opacity:1});
	});
	
});