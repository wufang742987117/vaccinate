//获取打印机数量
function getPrinterCount(LODOP) {	
	return LODOP.GET_PRINTER_COUNT();	
};
//获取打印机
function getPrinterName(LODOP,iPrinterNO) {	
	return LODOP.GET_PRINTER_NAME(iPrinterNO);	
};

/** 小票打印 */
function printtips(no,name,vac,price,pay,time,insurance){	
	if(!no){
		layer.msg("参数异常，打印失败",{icon:1});
		return;
	}
	var html = "";
	var hh = 700;
	if(!name){
		html = '<div style="height: 5px;width: 250px"></div>'+
		'<div style="font-family: 微软雅黑;width: 250px">'+
			'<div style="width: 250px;height: auto;border-top:1px solid #000;border-bottom:1px dashed #000;position: relative;margin:10px;">'+
			'<span style="text-align: center;position: absolute; top: -13px;background: #fff;left: 38%;padding: 0 10px; z-index: 999">智慧接种</span>'+
			'<h1 style="text-align: center;">#no#</h1>'+
			'<p style="line-height: 6px;font-size: 12px;padding-left: 5px;"><span>等待人数：</span>#insurance#</p>'+
			'<p style="line-height: 6px;font-size: 12px;padding-left: 5px;text-align: center;font-size: 20px"><span>犬伤及成人疫苗</span></p>'+
			'</div>'+
		'</div>';
		html = html.replace("#no#",no).replace("#insurance#",insurance);
		hh = 420;
	}

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
	LODOP.ADD_PRINT_HTM(0,0,"100%","100%",html);
	LODOP.SET_PRINT_PAGESIZE(1,800,hh,"");
	var hasPrint = false;
	for(var i = 0; i < getPrinterCount(LODOP); i ++){
		if("XP-80C" == getPrinterName(LODOP,i)){
			console.info("找到打印机XP-80C-->"+i);
			hasPrint = true;
			LODOP.SET_PRINTER_INDEX("XP-80C");
//			LODOP.PREVIEW();
			LODOP.PRINT();
		}
	}
	if(!hasPrint){
		console.error("未找到小票打印机XP-80C,打印小票失败");
		layer.msg("未找到小票打印机XP-80C,打印小票失败",{icon:1,offsetTop: 500});
	}
}


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
		if(data.ncount != 0){
			html += '<tr><td style="font-size:14px;padding:2px;">&diams;#vaccinatenameNo#/#manufacturerNo#/#ncount#支1,共#totalNCount#元</td></tr>';
		}
		if(data.count != 0){
			html += '<tr><td style="font-size:14px;padding:2px;">&diams;#vaccinatename#/#manufacturer#/#count#支,共#totalCount#元</td></tr>';
		}
		if(data.wcount != 0){
			html += '<tr><td style="font-size:14px;padding:2px;">&diams;#dealName#/#wcount#次,共#totalWCount#元</td></tr>';
		}
		html += '</table>'+
		'<div id="erweiCode" style="width: 120px;height:120px;margin: 4px 0;">'+
		'</div>'+
		'<h4 style="position: absolute;top:228px;right: 10px;line-height:30px; margin: 0;">接种医生签名：#docaterName#</h4>'+
		'</div>'+
		'<hr/>'+
		'<div id="footer">';
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
		html = html.replace("#vaccinatename#",data.vaccinatename).replace("#manufacturer#",data.manufacturer).replace("#count#",data.count).replace("#totalCount#",data.totalCount);
	}
	if(data.ncount != 0){
		html = html.replace("#vaccinatenameNo#",data.vaccinatenameNo).replace("#manufacturerNo#",data.manufacturerNo).replace("#ncount#",data.ncount).replace("#totalNCount#",data.totalNCount);
	}
	if(data.wcount != 0){
		html = html.replace("#dealName#",data.dealName).replace("#wcount#",data.wcount).replace("#totalWCount#",data.totalWCount);
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
	LODOP.ADD_PRINT_BARCODE(280,20,100,100,"QRCode",url);
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
	if(data.count != 0 && data.ncount == 0){
		html += '<table>'+
		'<tr><td style="font-size:14px;padding:3px;font-weight:bold;">用户编码：#rabiescode#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗类型：#vaccinatename#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗厂家：#manufacturer#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗针次：#dose#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">付款状态：#paystatus#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">接种状态：#status#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">总计：#num#支</td></tr>'+
		'</table>';
	}else if(data.count == 0 && data.ncount != 0){
		html += '<table>'+
		'<tr><td style="font-size:14px;padding:3px;font-weight:bold;">用户编码：#rabiescode#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗类型：#vaccinatenameNo#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗厂家：#manufacturerNo#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">疫苗针次：#doseNo#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">付款状态：#paystatusNo#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">接种状态：#statusNo#</td></tr>'+
		'<tr><td style="font-size:14px;padding:3px;">总计：#numNo#支</td></tr>'+
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
	if(data.count != 0 && data.ncount == 0){
		html = html.replace("#vaccinatename#",data.vaccinatename).replace("#manufacturer#",data.manufacturer).replace("#num#",data.num).replace("#dose#",data.dose).replace("#paystatus#",data.paystatus).replace("#status#",data.status);
	}else if(data.count == 0 && data.ncount != 0){
		html = html.replace("#vaccinatenameNo#",data.vaccinatenameNo).replace("#manufacturerNo#",data.manufacturerNo).replace("#numNo#",data.numNo).replace("#doseNo#",data.doseNo).replace("#paystatusNo#",data.paystatusNo).replace("#statusNo#",data.statusNo);
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
