//获取打印机数量
function getPrinterCount(LODOP) {	
	return LODOP.GET_PRINTER_COUNT();	
};
//获取打印机
function getPrinterName(LODOP,iPrinterNO) {	
	return LODOP.GET_PRINTER_NAME(iPrinterNO);	
};

/** 小票打印 */
//
function printRemind(data){
	console.info("打印预约单",data);
	if(!data || !data.baseinfo || !data.nextTime || !data.nextVacc){
		console.error("预约单打印失败",data);
		return false;
	}
	
	/* 打印关注二维码 */
	html = '<div style="height: 5px;width: 250px"></div>'+
	'<div style="font-family: 微软雅黑;width: 250px">'+
	'<div style="width: 250px;height: auto;border-top:1px solid #000;border-bottom:0px dashed #000;position: relative;margin:15px 10px 10px 10px;">'+
		'<span style="text-align: center;position: absolute; top: -13px;background: #fff;left: 38%;padding: 0 10px; z-index: 999">智慧接种</span>'+
		'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; padding-top: 18px;"><span>宝宝姓名：</span>' + data.baseinfo.childname + '</p>'+
		'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>下次接种日期：</span>' +  data.nextTime + "</p>"+
		'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>预&ensp;约&ensp;时&ensp;间&nbsp;：</span>' +  data.selectTime + '</p>';
	html+='<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>下次接种疫苗：</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;' + data.nextVacc + '</p></div></div>';
	
	html2 ='<div style="font-family: 微软雅黑;width: 250px">'+
			'<div style="width: 250px;height: auto;border-top:0px;border-bottom:1px dashed #000;position: relative;margin:0 10px 10px 10px;">'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>扫码关注公众号，即可随时接受宝宝接种疫苗提醒、了解最新疫苗咨询</span></p></div></div>';			
	
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
	LODOP.PRINT_INIT("打印预约单");
	LODOP.ADD_PRINT_HTM("-10",0,"100%","100%",html);
	LODOP.ADD_PRINT_BARCODE("40mm","26mm","30mm","30mm","QRCode","http://www.chinavacc.cn/wpwx/child/attenT.do?id=" + data.baseinfo.id);
	LODOP.ADD_PRINT_HTM("60mm",0,"100%","100%",html2);
	LODOP.SET_PRINT_PAGESIZE(3,800,"","");
	LODOP.SET_PRINTER_INDEX("XP-80C");
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
	return true;
}
