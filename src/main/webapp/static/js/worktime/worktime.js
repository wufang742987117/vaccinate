/**
 * @author: 方武
 * @time: 2018年1月23日19:54:13
 */

$(function() {

    //定义vm
    var vm = {};

    //定义全局变量
    var GLOBAL = {
        $parent: '',
        index:0
    };
    
    //事件绑定
    vm.addEvent = function() {
        $('body')
            .on('click', '.addbtn', vm.eventController.add)
            .on('click', '.editbtn', vm.eventController.edit)
            .on('click', '.remove', vm.eventController.remove)
    };

    //事件处理
    vm.eventController = {
    	//默认数据渲染
        showDefault: function() {
            $.ajax({
                url: ctx+"/sys/sysWorkingHours/findTreeListApi",
                type: 'GET',
                dataType: 'json',
                data:{},
                success:function(data) {
                	if(data.status=="200"){
                        var html = template('modInform', data);
                        document.getElementById('content').innerHTML = html;
                	}
                }
            })
        },
        // 添加新的时间段
        add: function(){
        	GLOBAL.$parent = $(this).parent();
            GLOBAL.index = $(this).attr('data-dayName');
        	var parentHtml=GLOBAL.$parent ;
        	layer.open({   
		        type: 1,  
		        title:'添加',  
		        skin:'layui-layer-rim', 
		        area:['300px', 'auto'],
		        content: ' <div class="row" style="width: 280px;  margin-left:7px; margin-top:10px;">'  
		            +'<div class="col-sm-12">'  
		            +'<div class="input-group">'  
		            +'<span class="input-group-addon"> 时间段   ：</span>'  
		            +'<input name="timestart" id="timePicker" value="09:00" name="1" readonly  type="text" class="form-control">' 
		            +'<span class="timeicon">-</span>' 
		            +'<input name="timeend"  type="text" name="2" value="09:30" readonly class="time-picker form-control" style="margin-left: 15px;">'  
		            +'</div>'  
		            +'</div>'  
		              +'<div class="col-sm-12" style="margin-top: 10px">'  
		              +'<div class="input-group">'  
		              +'<span class="input-group-addon">人数    ：</span>'  
		              +'<input name="count" type="number" class="form-control" placeholder=""  style="margin-left: 15px;">'  
		              +'</div>'  
		              +'</div>'  
		              +'</div>'  
		        ,  
		        btn:['保存','取消'],  
		        btn1: function (index,layero) {  
		        	var startTime=$(layero).find("input[name=timestart]").val(),
		        	endTime=$(layero).find("input[name=timeend]").val(),
		        	count=$(layero).find("input[name=count]").val();
		        	var time=startTime+'-'+endTime;
		        	$.ajax({
		                url: ctx+"/sys/sysWorkingHours/saveApi?week="+GLOBAL.index+"&maximum="+count+"&timeSlice="+time,
		                type: 'GET',
		                dataType: 'json',
		                data:{},
		                success:function(data) {
		                	if(data.status=="200"){
			                	parentHtml.find('ul').append("<li><div class='time'>"+startTime+"-"+endTime+"</div><div class='count'>"+count+"人</div><img src='img/remove.png' class='remove hide'></li>");
			                	layer.close(index);  
			                	vm.eventController.showDefault();
		                	}
		                	layer.msg('添加成功');
		                }
		            })
		        },  
		        btn2:function (index,layero) {  
		             layer.close(index);  
		        }  
		   });  
        	$().ready(function(e) {
    	        $("#timePicker").hunterTimePicker();
    	        $(".time-picker").hunterTimePicker();
    	    });
        },

        // 编辑
        edit:function() {
            if($(this).parent().find('img').hasClass('hide')){
                $(this).parent().find('img').removeClass('hide');
            }else {
                $(this).parent().find('img').addClass('hide');
            }
            GLOBAL.index = $(this).attr('data-type'); 
        },

        // 删除
        remove:function() {
        	var $this=$(this);
//        	var index=$(this).attr("data-index");
        	var id=$(this).attr("data-id");
        	layer.confirm("确认要删除吗？", {
                btn: ['确认','取消'], //按钮
                shade: true, //不显示遮罩
                icon : 3,
                area : ['500px' , '200px']
            }, function(index){
            	$.ajax({
                    url: ctx+"/sys/sysWorkingHours/deleteApi?id="+id,
                    type: 'GET',
                    dataType: 'json',
                    data:{},
                    success:function(data) {
                    	if(data.status=="200"){
    	                	$this.parent('li').remove();
    	                	vm.eventController.showDefault();
                    	}
                    }
                })
                layer.close(index);  
              }
            );
        	
        }
    }

    //初始化函数
    vm.init = function() {
        vm.eventController.showDefault();
        vm.addEvent();
    };

    //执行函数
    vm.init();
})
