/*!
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 * 
 * 通用公共方法
 * @author ThinkGem
 * @version 2014-4-29
 */

//var localStorage = window.localStorage;
//var sessionStorage = window.sessionStorage;
$(document).ready(function() {
	try{
		// 链接去掉虚框
		$("a").bind("focus",function() {
			if(this.blur) {this.blur()};
		});
		//所有下拉框使用select2
		$("select").select2();
	}catch(e){
		// blank
	}
	
	// 设置checkbox样式
	$(".i-checks").iCheck({checkboxClass:"icheckbox_square-green",radioClass:"iradio_square-green"});
	
	//双击打开接种台儿童页面
	$(document).on("dbclick",".gotoChildDetail",function(){
		window.open(ctx + "/child_vaccinaterecord/childVaccinaterecord1/list1?childid=" + $(this).attr("data-childcode"), "_blank");
	});
	
	//刷新显示默认排号科室
	refreshRoomcode();
	
	$(".roomcodeCancel").click(function(){
		localStorage["roomcode"] = "";
		$(".roomcodeWrap").hide();
	});
	
});

// 引入js和css文件
function include(id, path, file){
	if (document.getElementById(id)==null){
        var files = typeof file == "string" ? [file] : file;
        for (var i = 0; i < files.length; i++){
            var name = files[i].replace(/^\s|\s$/g, "");
            var att = name.split('.');
            var ext = att[att.length - 1].toLowerCase();
            var isCSS = ext == "css";
            var tag = isCSS ? "link" : "script";
            var attr = isCSS ? " type='text/css' rel='stylesheet' " : " type='text/javascript' ";
            var link = (isCSS ? "href" : "src") + "='" + path + name + "'";
            document.write("<" + tag + (i==0?" id="+id:"") + attr + link + "></" + tag + ">");
        }
	}
}

// 获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}

// 打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}

// 恢复提示框显示
function resetTip(){
	top.$.jBox.tip.mess = null;
}

// 关闭提示框
function closeTip(){
	top.$.jBox.closeTip();
}

//显示提示框
function showTip(mess, type, timeout, lazytime){
	resetTip();
	setTimeout(function(){
		top.$.jBox.tip(mess, (type == undefined || type == '' ? 'info' : type), {opacity:0, 
			timeout:  timeout == undefined ? 2000 : timeout});
	}, lazytime == undefined ? 500 : lazytime);
}

// 显示加载框
function loading(mess){
	if (mess == undefined || mess == ""){
		mess = "正在提交，请稍等...";
	}
	//resetTip();
    //layer.msg(mess, {icon: 16});
    //top.$.jBox.tip(mess,'loading',{opacity:0});
    layer.msg(mess, {icon: 16,shade:0.3});
}

// 关闭提示框
function closeLoading(){
	// 恢复提示框显示
	resetTip();
	// 关闭提示框
	closeTip();
}

// 警告对话框
function alertx(mess, closed){
	top.$.jBox.info(mess, '提示', {closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	top.$('.jbox-body .jbox-icon').css('top','55px');
}

function alertIframe(url,title)
{
    layer.open({
        type: 2,
        area: [$(document).width()-200+'px',$(document).height()-180+'px'],
        title: isNull(title) ? "信息" : title,
        fix: false, //不固定
        maxmin: true,
        shade: 0.3,
        shift: 1, //0-6的动画形式，-1不开启
        content: url
    });
}

function alertForm(url,title, w, h)
{
	w = isNull(w) ? 800 : w;
	h = isNull(h) ? 550 : h;
	
	var ww = ($(document).width() < w ? $(document).width()-30 : w) + "px";
	var hh = ($(document).height() < h ? $(document).height()-30 : h) + "px";
    layer.open({
        type: 2,
        area: [ww,hh],
        title: isNull(title) ? "信息" : title,
        fix: false, //不固定
        maxmin: false,
        shade: 0.3,
        shift: 0, //0-6的动画形式，-1不开启
        content: url
    });
}

function LayerThickness(url,title)
{
	var index = layer.open({
		type: 2,
		area: [$(document).width()-200+'px',$(document).height()-180+'px'],
		title: isNull(title) ? "信息" : title,
				fix: false, //不固定
				 maxmin: true,
				shade: 0.3,
				shift: 1, //0-6的动画形式，-1不开启
				content: url
	});
	
	layer.full(index);
}

// 确认对话框
function confirmx(mess, href, closed){
    layer.confirm(mess, {
        btn: ['确认','取消'], //按钮
        shade: true, //不显示遮罩
        icon : 3,
        font:25,
        //offset : ['300px' , '35%'],
        area : ['500px','200px']
    }, function(index){
    	layer.close(index);
    	loading("正在执行...");
        if (typeof href == 'function') {
            href();
        }else{
            location = href;
        }
       
    }, function()
    {
        layer.close();
    });
	return false;
}
/**
 *输入原因弹框
 * @param href
 * @param tip
 * @param title
 * @returns {boolean}
 */
/*function prompt(href,tip,title)
{
    layer.prompt({
        formType: 2,
        value: isNull(tip) ? "请输入原因" : tip,
        title: isNull(title) ? "提示" : title ,
        maxlength : 200
    }, function(value, index, elem)
    {
        layer.close(index);
        loading("正在执行...");
        location = href + '&value=' + value;
    });
    return false;
}*/

// 提示输入对话框
function promptx(title, lable, href, closed){
	top.$.jBox("<div class='form-search' style='padding:20px;text-align:center;'>" + lable + "：<input type='text' id='txt' name='txt'/></div>", {
			title: title, submit: function (v, h, f){
	    if (f.txt == '') {
	        top.$.jBox.tip("请输入" + lable + "。", 'error');
	        return false;
	    }
		if (typeof href == 'function') {
			href();
		}else{
			resetTip(); //loading();
			location = href + encodeURIComponent(f.txt);
		}
	},closed:function(){
		if (typeof closed == 'function') {
			closed();
		}
	}});
	return false;
}

// 添加TAB页面
function addTabPage(title, url, closeable, $this, refresh){
	top.$.fn.jerichoTab.addTab({
        tabFirer: $this,
        title: title,
        closeable: closeable == undefined,
        data: {
            dataType: 'iframe',
            dataLink: url
        }
    }).loadData(refresh != undefined);
}

// cookie操作
function cookie(name, value, options) {
    if (typeof value != 'undefined') { // name and value given, set cookie
        options = options || {};
        if (value === null) {
            value = '';
            options.expires = -1;
        }
        var expires = '';
        if (options.expires && (typeof options.expires == 'number' || options.expires.toUTCString)) {
            var date;
            if (typeof options.expires == 'number') {
                date = new Date();
                date.setTime(date.getTime() + (options.expires * 24 * 60 * 60 * 1000));
            } else {
                date = options.expires;
            }
            expires = '; expires=' + date.toUTCString(); // use expires attribute, max-age is not supported by IE
        }
        var path = options.path ? '; path=' + options.path : '';
        var domain = options.domain ? '; domain=' + options.domain : '';
        var secure = options.secure ? '; secure' : '';
        document.cookie = [name, '=', encodeURIComponent(value), expires, path, domain, secure].join('');
    } else { // only name given, get cookie
        var cookieValue = null;
        if (document.cookie && document.cookie != '') {
            var cookies = document.cookie.split(';');
            for (var i = 0; i < cookies.length; i++) {
                var cookie = jQuery.trim(cookies[i]);
                // Does this cookie string begin with the name we want?
                if (cookie.substring(0, name.length + 1) == (name + '=')) {
                    cookieValue = decodeURIComponent(cookie.substring(name.length + 1));
                    break;
                }
            }
        }
        return cookieValue;
    }
}

// 数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;//字符串长度，一个汉字长度为2   
 var s = 0;//汉字个数   
 var p = false;//判断字符串当前循环的前一个字符是否为汉字   
 var b = false;//判断字符串当前循环的字符是否为汉字   
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    //单字节加1   
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}

function isNull(obj)
{
    return (obj =='' || obj == null || obj == undefined);
}

function SimpleDateFormat(now,mask)
{
    var d = now;
    var zeroize = function (value, length)
    {
        if (!length) length = 2;
        value = String(value);
        for (var i = 0, zeros = ''; i < (length - value.length); i++)
        {
            zeros += '0';
        }
        return zeros + value;
    };
 
    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0)
    {
        switch ($0)
        {
            case 'd': return d.getDate();
            case 'dd': return zeroize(d.getDate());
            case 'ddd': return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd': return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M': return d.getMonth() + 1;
            case 'MM': return zeroize(d.getMonth() + 1);
            case 'MMM': return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM': return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy': return String(d.getFullYear()).substr(2);
            case 'yyyy': return d.getFullYear();
            case 'h': return d.getHours() % 12 || 12;
            case 'hh': return zeroize(d.getHours() % 12 || 12);
            case 'H': return d.getHours();
            case 'HH': return zeroize(d.getHours());
            case 'm': return d.getMinutes();
            case 'mm': return zeroize(d.getMinutes());
            case 's': return d.getSeconds();
            case 'ss': return zeroize(d.getSeconds());
            case 'l': return zeroize(d.getMilliseconds(), 3);
            case 'L': var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt': return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT': return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z': return d.toUTCString().match(/[A-Z]+$/);
            // Return quoted strings with the surrounding quotes removed
            default: return $0.substr(1, $0.length - 2);
        }
    });
};

/* 数组操作，根据val移除元素 */
function removeByValue(arr, val) {
	  for(var i=0; i<arr.length; i++) {
	    if(arr[i] == val) {
	      arr.splice(i, 1);
	      break;
	    }
	  }
	}

/* 数组操作，根据序列号移除元素 */
function removeByIdx(arr, idx) {
	  for(var i=0; i<arr.length; i++) {
	    if(i == idx) {
	      arr.splice(i, 1);
	      break;
	    }
	  }
	}

function dateAdd(date,strInterval, Number) {  //参数分别为日期对象，增加的类型，增加的数量 
    var dtTmp = date;  
    switch (strInterval) {   
        case 'second':
        case 's' :
            return new Date(Date.parse(dtTmp) + (1000 * Number));  
        case 'minute':
        case 'n' :
            return new Date(Date.parse(dtTmp) + (60000 * Number));  
        case 'hour':
        case 'h' :
            return new Date(Date.parse(dtTmp) + (3600000 * Number)); 
        case 'day':                           
        case 'd' :
            return new Date(Date.parse(dtTmp) + (86400000 * Number)); 
        case 'week':                           
        case 'w' :
            return new Date(Date.parse(dtTmp) + ((86400000 * 7) * Number));
        case 'month':
        case 'm' :
            return new Date(dtTmp.getFullYear(), (dtTmp.getMonth()) + Number, dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
        case 'year':
        case 'y' :
            return new Date((dtTmp.getFullYear() + Number), dtTmp.getMonth(), dtTmp.getDate(), dtTmp.getHours(), dtTmp.getMinutes(), dtTmp.getSeconds());  
    }  
    
}

/**
 * @author fuxin
 * @param id
 * @param new-js-src
 * 重新加载js
 */
function reloadAbleJSFn(id, newJS) {
	var oldjs = null;
	var t = null;
	var oldjs = document.getElementById(id);
	if (oldjs) oldjs.parentNode.removeChild(oldjs);
	if (newJS){
		var scriptObj = document.createElement("script");
		scriptObj.src = newJS;
		scriptObj.type = "text/javascript";
		scriptObj.id = id;
		document.getElementsByTagName("head")[0].appendChild(scriptObj);
	}
	
}

function addMouth(date, Number){
	var yy = date.getFullYear();
	var mm = date.getMonth()+1;
	var dd = date.getDate();
	mm += parseInt(Number);
	while(mm > 12){
		yy = yy+1;
		mm = mm-12;
	}
	var d_max = new Date(yy,mm,0).getDate();  //获取计算后的月的最大天数
    if (dd > d_max) {
		var ovdd=addDate(new Date(yy,mm-1,d_max),1);
		yy = ovdd.getFullYear();
		mm = ovdd.getMonth()+1;
		dd = ovdd.getDate();
    }
	return yy + '-' + mm + '-' + dd;
}

var agelist = [{"age": 1, "text": "1月龄"}, {"age": 2, "text": "2月龄"}, {"age": 3, "text": "3月龄"}, {"age": 4, "text": "4月龄"}, {"age": 5, "text": "5月龄"}, {"age": 6, "text": "6月龄"}, {"age": 7, "text": "7月龄"}, {"age": 8, "text": "8月龄"}, {"age": 18, "text": "一周半"}, {"age": 24, "text": "2周岁"}, {"age": 36, "text": "3周岁"}, {"age": 48, "text": "4周岁"}, {"age": 72, "text": "6周岁"}];
//var agelist = [{"age": 18, "text": "一周半"}, {"age": 24, "text": "2周岁"}, {"age": 36, "text": "3周岁"}, {"age": 48, "text": "4周岁"}, {"age": 72, "text": "6周岁"}];

//刷新默认科室状态
function refreshRoomcode(){
	if(localStorage["roomcode"]){
		$(".roomcode").html(localStorage["roomcode"]);
		$(".roomcodeWrap").show();
	}else{
		$(".roomcodeWrap").hide();
	}
}

//检查方法是否存在
function isExitsFunction(funcName) {
	  try {
	    if (typeof(eval(funcName)) == "function") {
	      return true;
	    }
	  } catch(e) {}
	  return false;
	}