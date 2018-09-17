//获取打印机数量
function getPrinterCount(LODOP) {	
	return LODOP.GET_PRINTER_COUNT();	
};
//获取打印机
function getPrinterName(LODOP,iPrinterNO) {	
	return LODOP.GET_PRINTER_NAME(iPrinterNO);	
};

function printInvoices(data,url){
	var html = "";
	html = '<div style="margin:0 auto;width: 280px;">'+
	'<div style="padding: 0px 15px;">'+
		'<div id="header" style="text-align: center;">'+
		'<h3>#officeName#</h3>'+
		'<h2>接种通知单</h2>'+
		'<span style="font-size: 14px;">姓名:</span>'+
		'<input type="text" style="width:75px;border:none;border-bottom:1px solid #000; text-align: center; " value="#username#"></input>'+
		'<span style="font-size: 14px;">性别:</span>'+
		'<input type="text"  style="width:28px;border:none;border-bottom:1px solid #000; text-align: center;" value="#sex#"></input>'+
		'<span style="font-size: 14px;">年龄:</span>'+
		'<input type="text" style="width:28px;border:none;border-bottom:1px solid #000; text-align: center;" value="#age#"></input>'+
		'</div>'+
		'<hr/>'+
		'<div id="content" style="position: relative; height: 250px; padding: 0 10px;">'+
		'<h4 style=" line-height:30px; margin: 0;">请到第<span style="text-decoration:underline;">#roomCode#</span>接种室接种</h4>'+
		'<h4 style=" line-height:30px; margin: 0;">接种疫苗名称：</h4>'+
		'<table style="margin:0 auto;">';
		if(data.count != 0){
			html += '<tr><td style="font-size:14px;padding:0px;">&diams;#vaccinatename#/#manufacturer#/#standard#/#count#支,共#total#元</td></tr>';
		}
		html += '</table>'+
		'<div id="erweiCode" style="width: 120px;height:120px;margin: 4px 0;">'+
		'</div>'+
		'<h4 style="position: absolute;top:228px;right: 10px;line-height:30px; margin: 0;">接种医生签名：#docaterName#</h4>'+
		'</div>'+
		'<hr/>'+
		'<div id="footer">'
		if(data.sendOrder != ""){
			html += '<span>金额:#str#元</span>';
		}else{
			html += '<span>金额:#total#元</span>';
		}
		html += '<span style="padding-left:30px;">#data#</span>'+
		'</div>'+
		'</div>'+
	'</div>';
	html = html.replace("#username#",data.username).replace("#sex#",data.sex).replace("#age#",data.age).replace("#data#",data.data).replace("#docaterName#",data.docaterName).replace("#officeName#",data.officeName);
	if(data.count != 0){
		html = html.replace("#vaccinatename#",data.vaccinatename).replace("#manufacturer#",data.manufacturer).replace("#standard#", data.standard).replace("#count#",data.count).replace("#total#",data.total);
	}
	if(data.sendOrder != ""){
		html = html.replace("#str#",data.str);
	}else{
		html = html.replace("#total#",data.total);
	}
	html = html.replace("#roomCode#",data.roomCode.substring(0,1));
	
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
	LODOP.PRINT_INIT("打印小票");
	LODOP.ADD_PRINT_HTM(0,0,"280","500",html);
	LODOP.ADD_PRINT_BARCODE(260,20,100,100,"QRCode",url);
	LODOP.SET_PRINT_PAGESIZE(1,800,1200,"");
	var hasPrint = false;
	for(var i = 0; i < getPrinterCount(LODOP); i ++){
		if("XP-80C" == getPrinterName(LODOP,i)){
			console.info("找到打印机XP-80C-->"+i);
			hasPrint = true;
			LODOP.SET_PRINTER_INDEX("XP-80C");
			LODOP.PRINT();
//			LODOP.PREVIEW();
		}
	}
	if(!hasPrint){
		console.error("未找到小票打印机XP-80C,打印小票失败");
		layer.msg("未找到小票打印机XP-80C,打印小票失败",{icon:1,offsetTop: 500});
	}
}


function printInvoiceId(data){	
	if(!data){
		return false;
	}
     if(data.error){
		error(data.error);
		return true;
	}
	var html = "";
	html = '<div style="margin:0 auto;width: 280px;">'+
	'<div style="padding: 0px 15px;">'+
		'<div id="header" style="text-align: center;">'+
		'<h3>#officeName#</h3>'+
		'<h2>接种记录单</h2>'+
		'<span style="font-size: 14px;">姓名:</span>'+
		'<input type="text" style="width:75px;border:none;border-bottom:1px solid #000; text-align: center; " value="#username#"></input>'+
		'<span style="font-size: 14px;">性别:</span>'+
		'<input type="text"  style="width:28px;border:none;border-bottom:1px solid #000; text-align: center;" value="#sex#"></input>'+
		'<span style="font-size: 14px;">年龄:</span>'+
		'<input type="text" style="width:28px;border:none;border-bottom:1px solid #000; text-align: center;" value="#age#"></input>'+
		'</div>'+
		'<hr/>'+
		'<div id="content" style="position: relative; height: 250px; padding: 0 10px;">';
	if(data.count != 0){
		html += '<table>'+
		'<tr><td style="font-size:14px;padding:3px;font-weight:bold;">用户编码：#rabiescode#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗类型：#vaccinatename#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗厂家：#manufacturer#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗规格：#standard#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗针次：#dose#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">付款状态：#paystatus#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">接种状态：#status#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">总计：#count#支</td></tr>'+
		'</table>';
	}
	html += '<h4 style="position: absolute;top:228px;right: 10px;line-height:30px; margin: 0;">接种医生签名：#docaterName#</h4>'+
		'</div>'+
		'<hr/>'+
		'<div id="footer">'+
		'<span style="float:right;">#data#</span>'+
		'</div>'+
		'</div>'+
	'</div>';
	html = html.replace("#username#",data.username).replace("#sex#",data.sex).replace("#age#",data.age).replace("#data#",data.data).replace("#docaterName#",data.docaterName).replace("#officeName#",data.officeName).replace("#rabiescode#",data.rabiescode);
	if(data.count != 0){
		html = html.replace("#vaccinatename#",data.vaccinatename).replace("#manufacturer#",data.manufacturer).replace("#standard#", data.standard).replace("#count#",data.count).replace("#dose#",data.dose).replace("#paystatus#",data.paystatus).replace("#status#",data.status);
	}
	
	var LODOP; //声明为全局变量
	try{	
		LODOP = getLodop();
	} catch(err) {
		console.error("打印控件出错");
	};
	if (LODOP == null || LODOP ==''){
		return false;
	}
	LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
	LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
	LODOP.PRINT_INIT("打印小票");
	LODOP.ADD_PRINT_HTM(0,0,"280","500",html);
	LODOP.SET_PRINT_PAGESIZE(1,800,1200,"");
	var hasPrint = false;
	for(var i = 0; i < getPrinterCount(LODOP); i ++){
		if("XP-80C" == getPrinterName(LODOP,i)){
			console.info("找到打印机XP-80C-->"+i);
			hasPrint = true;
			LODOP.SET_PRINTER_INDEX("XP-80C");
			LODOP.PRINT();
//			LODOP.PREVIEW();
		}
	}
	if(!hasPrint){
		console.error("未找到小票打印机XP-80C,打印小票失败");
		layer.msg("未找到小票打印机XP-80C,打印小票失败",{icon:1,offsetTop: 500});
	}
	return true;
}
