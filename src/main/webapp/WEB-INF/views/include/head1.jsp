<%@ page contentType="text/html;charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="author" content="http://jeesite.com/"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<link href="${ctxStatic}/css/bootstrap.min14ed.css" rel="stylesheet">
<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />
<link href="${ctxStatic}/css/font-awesome.min93e3.css" rel="stylesheet">
<link href="${ctxStatic}/css/animate.min.css" rel="stylesheet">
<link href="${ctxStatic}/css/plugins/iCheck/custom.css" rel="stylesheet">
<link href="${ctxStatic}/css/style.min862f.css?v=20180228" rel="stylesheet">
<link href="${ctxStatic}/css/custom.css" rel="stylesheet">
<link href="${ctxStatic}/css/edge.css" rel="stylesheet" />
<link href="${ctxStatic}/css/basicContext.min.css" rel="stylesheet" />

<script src="${ctxStatic}/js/jquery.min.js"></script>
<script type="text/javascript">
    jQuery.browser={};(function(){jQuery.browser.msie=false; jQuery.browser.version=0;if(navigator.userAgent.match(/MSIE ([0-9]+)./)){ jQuery.browser.msie=true;jQuery.browser.version=RegExp.$1;}})();
</script>
<script type="text/javascript">
/* 重写ajax,每次ajax都带上localcode属性 */
(function($){  
    //备份jquery的ajax方法
    var _ajax=$.ajax;
      
    //重写jquery的ajax方法
    $.ajax=function(opt){
        //备份opt中error和success方法
        var fn = {
            error:function(XMLHttpRequest, textStatus, errorThrown){},
            success:function(data, textStatus){},
            data:{"localCode":'${fns:getUser().localCode}'}
        }  
        if(opt.error){
            fn.error=opt.error;
        }  
        if(opt.success){
            fn.success=opt.success;
        } 

       fn.data = $.extend(fn.data, opt.data);

        //扩展增强处理
        var _opt = $.extend(opt,{
            error:function(XMLHttpRequest, textStatus, errorThrown){
                //错误方法增强处理
                console.error("request error status[" + textStatus + "] errorThrown=" + errorThrown );
                fn.error(XMLHttpRequest, textStatus, errorThrown);
            },  
            success:function(data, textStatus){  
                //成功回调方法增强处理
                fn.success(data, textStatus);  
            },
            data:fn.data
        });  
        _ajax(_opt);
    };  
})(jQuery);  
</script>

<script src="${ctxStatic}/js/bootstrap.min.js"></script>
<script src="${ctxStatic}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${ctxStatic}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<script src="${ctxStatic}/js/plugins/layer/layer.min.js"></script>
<script src="${ctxStatic}/js/plugins/layer/laydate/laydate.js"></script>
<script type="text/javascript" src="${ctxStatic}/js/contabs.min.js"></script>
<script src="${ctxStatic}/js/plugins/pace/pace.min.js"></script>
<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/plugins/iCheck/icheck.min.js"></script>
<script src="${ctxStatic}/js/content.min.js"></script>
<script src="${ctxStatic}/js/plugins/validate/jquery.validate.min.js"></script>
<script src="${ctxStatic}/js/plugins/validate/messages_zh.min.js"></script>
<script src="${ctxStatic}/jquery-validation/1.11.1/jquery.validate.method.js"></script>
<script src="${ctxStatic}/js/hplus.min.js"></script>
<%-- <link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
<script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script> --%>

<script src="${ctxStatic}/common/jeesite.js?v=20180205" type="text/javascript"></script>
<script src="${ctxStatic}/js/context.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/basicContext.min.js" type="text/javascript"></script>
<script src="${ctxStatic}/js/colResizable-1.5.min.js" type="text/javascript"></script>
<script type="text/javascript">var sessionId = "${sessionId}";</script>

<script type="text/javascript">
//    $(function(){
//        $("#side-menu li").each("click",function(){
//
//        });
//    })

    var ctx = '${ctx}', ctxStatic='${ctxStatic}';
    var preference = JSON.parse('${preference}');
    var localCode = '${fns:getUser().localCode}';

    var toastrMsg = function(msg,type)
    {
        var icon = 0;
        switch(type)
        {
            case "success":
                icon = 1;
                break;
            case "warning":
                icon = 3;
                break;
            case "error":
                icon = 2;
                break;
            case "primary":
                icon = 7;
                break;
            default:
                icon = 0;
        }

        if(msg == "" || msg == null)
        {
            return;
        }
        layer.msg(msg, {icon: icon});
    };
    
    var success = function(msg){
    	toastrMsg(msg, 'success');
    }
    var error = function(msg){
    	toastrMsg(msg, 'error');
    }
    var warning = function(msg){
    	toastrMsg(msg, 'warning');
    }
    jQuery.validator.addMethod("mobile", function(value, element)
    {
		var length = value.trim().length;
		var mobile =  /^1[1-9]\d{9}$/;
		if(this.optional(element) || (length == 11 && mobile.test(value.trim()))){
			element.value=element.value.trim();
		}
		return this.optional(element) || (length == 11 && mobile.test(value.trim()));
    }, "手机号码格式错误");

    jQuery.validator.addMethod("gt",function(value, element, param) {
        return value*1 > $(param).val()*1;
    },"请输入更大的值");

    function rest(id){
    	$("#"+id+" :input:not(:submit)").val("");
    }
    
    function showExaminer(ctx){
		var url = ctx+"/examiner/examiner/examinerList";
		layer.open({
		    type: 2,
		    title: "实习审查员信息",
		    scrollbar: true,
		    maxmin: true,
		    shade: [0],
		    area: ['800px', '500px'],
		    content: [url]
		});
	}
	function setPracticeExaminer(id,name){
		$("#practiceExaminerId").val(id);
		$("#practiceExaminerName").val(name);
	}
	jQuery.validator.addMethod("notSame",function(value, element) {
		var val = new Array();
		$(".notSame").each(function()
		{
			if($(this).val() != '' && $(this).val() !=null && $(element)[0].getAttribute("id") != $(this).attr("id"))
			{
				val.push($(this).val());
			}
		});
		return val.indexOf(value) == -1;
	},"不能存在相同");


	function getOs()  
	{  
	    var OsObject = "";  
	   if(navigator.userAgent.indexOf("MSIE")>0) {  
	        return "MSIE";  
	   }  
	   if(isFirefox=navigator.userAgent.indexOf("Firefox")>0){  
	        return "Firefox";  
	   }  
	   if(isSafari=navigator.userAgent.indexOf("Safari")>0) {  
	        return "Safari";  
	   }   
	   if(isCamino=navigator.userAgent.indexOf("Camino")>0){  
	        return "Camino";  
	   }  
	   if(isMozilla=navigator.userAgent.indexOf("Gecko/")>0){  
	        return "Gecko";  
	   }  
	     
	}
	
   /*
	* 解决Firefox时间控件相对位置bug
	*/
	$(function(){
		if(getOs() == "Firefox"){
			$('body').css("position","relative");
		}
	}); 
	
</script>
