//获取打印机数量
function getPrinterCount(LODOP) {	
	return LODOP.GET_PRINTER_COUNT();	
};
//获取打印机
function getPrinterName(LODOP,iPrinterNO) {	
	return LODOP.GET_PRINTER_NAME(iPrinterNO);	
};

/** 小票打印 */
//no,name,vac,price,pay,time,insurance
function printtips(data){
	if(!data){
		layer.msg("参数异常，打印失败",{icon:1});
		return false;
	}

	var pinstr;
	if(data.pin=="1"){
		pinstr = "第一剂次"
	}
	if(data.pin=="2"){
		pinstr = "第二剂次"
	}
	if(data.pin=="3"){
		pinstr = "第三剂次"
	}
	if(data.pin=="4"){
		pinstr = "第四剂次"
	}
	if(data.pin=="5"){
		pinstr = "第五剂次"
	}
	if(data.impart.group == '17' && data.pin=="1"){
		pinstr = "第三剂次"
	}
	if(data.impart.group == '17' && data.pin=="2"){
		pinstr = "第四剂次"
	}
	
	var birthday = new Date(data.name.birthday.replace(/-/g,"/"));
	var now = new Date();
	var printArgs = new Object();
	printArgs["roomCode"] = data.no.substring(0,1);
	printArgs["no"] = data.no;
	printArgs["childCode"] = data.name.childcode;
	printArgs["childName"] = data.name.childname;
	printArgs["birth"] = data.name.birthday;
	printArgs["vaccName"] = data.vac;
	printArgs["companyName"] = data.product.manufacturer;
	printArgs["batch"] = data.product.batchno;
	printArgs["price"] = data.price;
	printArgs["pay"] = data.pay==0?"未付款":data.pay;
	printArgs["createTime"] = data.time;
	printArgs["wait"] = data.wait<0?0:data.wait;
	printArgs["disTitle"] = data.impart.title;
	printArgs["disPin"] = pinstr;
	printArgs["disBirth"] = birthday.getFullYear()+ '年' + (birthday.getMonth()+1) + '月' + birthday.getDate() + '日';
	printArgs["disInTime"] = now.getFullYear()+ '年' + (now.getMonth()+1) + '月' + now.getDate() + '日';
	
	console.info("当前os版本" + gs.ClientOs());
	if(gs.ClientOs().indexOf("Win") < 0){
		document.location = "js://printtipview?sign=" + (data.sign?1:0) +"&data="+escape(JSON.stringify(printArgs));
		console.info("调用安卓打印,任务结束");
		return true;
	}
	
	var html = "";
	var hh = 10;  
	if(data.type=='reserve'){
		html = '<div style="height: 5px;width: 250px"></div>'+
		'<div style="font-family: 微软雅黑;width: 250px">'+
			'<div style="width: 250px;height: auto;border-top:1px solid #000;border-bottom:1px dashed #000;position: relative;margin:10px;">'+
			'<span style="text-align: center;position: absolute; top: -13px;background: #fff;left: 38%;padding: 0 10px; z-index: 999">智慧接种</span>'+
			'<p style="margin: 0;  line-height: 22px;font-size: 12px;padding-left: 5px; margin-top:8px;"><span>编号：</span>' + data.no.substring(0,1) + '</p>'+
			'<p style="margin: 0;  line-height: 22px;font-size: 12px;padding-left: 5px;"><span>姓名：</span>' + data.name.childname + '(' + data.name.birthday + ')</p>'+
			'<p style="margin: 0;  line-height: 22px;font-size: 12px;padding-left: 5px;"><span>疫苗：</span>' + data.vaccName + '</p><p>&nbsp;</p>'+
			'</div>'+
		'</div>';
		hh = 420;
	}else{
		var paradoxicalreaction = data.name.paradoxicalreaction;
		if(!paradoxicalreaction){
			paradoxicalreaction = "无";
		}
	
		html = '<div style="height: 5px;width: 250px"></div>'+
		'<div style="font-family: 微软雅黑;width: 250px">';
		if(data.no){
			hh += 700;
			html +='<div style="width: 250px;height: auto;border-top:1px solid #000;border-bottom:1px dashed #000;position: relative;margin:10px 10px 10px 10px;">'+
				'<span style="text-align: center;position: absolute; top: -13px;background: #fff;left: 38%;padding: 0 10px; z-index: 999">智慧接种</span>'+
				'<h2 style="text-align: center;margin-top:8px; margin-bottom: 8px;">第#noo#接种室 &nbsp;&nbsp;#no#</h2>'+
				'<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>儿童编号：</span>#childcode#</p>'+
				'<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>儿童姓名：</span>#name#</p>';
			if(data.pay==0 && data.price){
				html += '<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>疫苗名称：</span>#vac#</p>';
			}else{
				html += '<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>疫苗名称：</span>#vac#</p>';
			}
			html += '<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px; width:160px;"><span>疫苗厂家：</span>#manu#</p>';
			html += '<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>疫苗批号：</span>#batch#</p>';
			html +='<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>疫苗价格：</span>￥#price#</p>'+
				'<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>付费状态：</span>#pay#</p>'+
				'<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>排号时间：</span>#time#</p>'+
				'<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>等待人数：</span>#insurance#</p><hr>'
		}

//			'<p style="margin: 0; line-height: 20px;font-size: 12px;padding-left: 5px;"><span>保险：</span>#insurance#</p>'+
		if(data.impart.title){
			hh = hh+ 800;
			html += 
			'<p style="margin: 0; margin-top:10px; line-height: 22px;font-size: 14x;text-align: center;"><span><strong>' + data.impart.title + '</strong></span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 14px;text-align: center;"><span><strong>接种知情告知书回执（' + pinstr + '）</strong></span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; padding-top: 15px;"><span>受种者姓名：<span style="text-decoration: underline;">&nbsp;&nbsp;' + data.name.childname + '&nbsp;&nbsp;</span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;"><span>出生日期：'+birthday.getFullYear()+ '年' + (birthday.getMonth()+1) + '月' + birthday.getDate() + '日</span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;"><span><strong>健康状况</strong></span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;"><span>1.近期是否发热>37.5C、急性传染病。（无）</span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;"><span>2.以往有无过敏史（' + paradoxicalreaction + '）</span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;"><span>3.有无癫痫病、神经系统疾病史及惊厥史。（无）</span></p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;"><span>4.是否患有严重慢性疾病。（无）</span></p>';
			
		if(!data.sign){
			html += '<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;padding-top: 5px;"><span>家长或监护人签字：<span style="text-decoration: underline;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></p>'
			html += '<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>接种时间：'+now.getFullYear()+ '年' + (now.getMonth()+1) + '月' + now.getDate() + '日</span></p>'
		}
		if(data.sign){
			hh = hh + 200;
			html += '<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; padding-top: 5px;"><span>家长或监护人签字：<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span></p>'
			html += '<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; margin-top:80px;"><span>接种时间：'+now.getFullYear()+ '年' + (now.getMonth()+1) + '月' + now.getDate() + '日</span></p>'
		}
//		if(data.impart.choose && data.impart.choose != 'normal'){
//			hh = hh + 200;
//			var cho = data.impart.choose;
//			html += cho.replace(new RegExp("#","gm"),"\"");
//		}
		hh = hh + 100;
		html += '<p style="margin: 0; line-height: 20px;font-size: 14px;padding-left: 5px;font-weight: bold;">' + data.vac + '</p>';
		html += '<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px;">厂家：' + data.product.manufacturer + '&nbsp;&nbsp;批号：' + data.product.batchno + '</p>';
				
	}
		html += '</div>'+
		'</div>';
		html = html.replace("#no#",data.no)
		.replace("#childcode#",data.name.childcode)
		.replace("#name#",data.name.childname+'(' + data.name.birthday + ')')
		.replace("#vac#",data.vac)
		.replace("#price#",data.price)
		.replace("#pay#",data.pay==0?"未付款":data.pay)
		.replace("#time#",data.time)
		.replace("#insurance#",data.wait<0?0:data.wait);
		
		html = html.replace("#noo#",data.no.substring(0,1)).replace("#manu#",data.manu);
		html = html.replace("#batch#",(data.product.batchno));
			
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
	LODOP.PRINT_INIT("登记台排号小票");
	LODOP.ADD_PRINT_HTM(0,0,"100%","120%",html);
	LODOP.SET_PRINT_PAGESIZE(1,900,2100,"");
	if(preference.quickOption != 1 && data.pay && data.pay==0 && data.price){
		LODOP.ADD_PRINT_BARCODE("30mm","50mm","28mm","28mm","QRCode","C_"+data.no + "_" + data.name.localCode);
		console.info("C_"+data.no + "_" + data.name.localCode);
	}

	if(data.sign && preference.quickOption != 1){
		LODOP.ADD_PRINT_IMAGE("142mm","15mm",180,67.5,"data:image/png;base64,"+data.sign);
		LODOP.SET_PRINT_STYLEA(0,"Stretch",2);
	}else if(data.sign && preference.quickOption == 1){
		LODOP.ADD_PRINT_IMAGE((hh-800)+"px","15mm",180,67.5,"data:image/png;base64,"+data.sign);
		LODOP.SET_PRINT_STYLEA(0,"Stretch",2);
	}
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
	
	/* 打印关注二维码 */
	if(hasPrint && data.nextTime && data.nextVacc){
		html = '<div style="height: 5px;width: 250px"></div>'+
		'<div style="font-family: 微软雅黑;width: 250px">'+
		'<div style="width: 250px;height: auto;border-top:1px solid #000;border-bottom:0px dashed #000;position: relative;margin:15px 10px 10px 10px;">'+
			'<span style="text-align: center;position: absolute; top: -13px;background: #fff;left: 38%;padding: 0 10px; z-index: 999">智慧接种</span>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; padding-top: 18px;"><span>宝宝姓名：</span>' + data.name.childname + '</p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>下次接种日期：</span>' +  data.nextTime + '</p>'+
			'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>预&ensp;约&ensp;时&ensp;间&nbsp;：</span>' +  data.selectTime + '</p>';
		
		html+='<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>下次接种疫苗：</span><br/>&nbsp;&nbsp;&nbsp;&nbsp;' + data.nextVacc + '</p></div></div>';
		
		html2 ='<div style="font-family: 微软雅黑;width: 250px">'+
				'<div style="width: 250px;height: auto;border-top:0px;border-bottom:1px dashed #000;position: relative;margin:0 10px 10px 10px;">'+
				'<p style="margin: 0; line-height: 20px;font-size: 13px;padding-left: 5px; "><span>扫码关注公众号，即可随时接受宝宝接种疫苗提醒、了解最新疫苗咨询</span></p></div></div>';			
		
		LODOP=getLodop();  
		LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
		LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
		LODOP.PRINT_INIT("微信关注二维码");
		LODOP.ADD_PRINT_HTM("-10",0,"100%","100%",html);
		LODOP.ADD_PRINT_BARCODE("40mm","26mm","30mm","30mm","QRCode","http://www.chinavacc.cn/wpwx/child/attenT.do?id=" + data.name.id);
		LODOP.ADD_PRINT_HTM("60mm",0,"100%","100%",html2);
		LODOP.SET_PRINT_PAGESIZE(3,800,"","");
		LODOP.SET_PRINTER_INDEX("XP-80C");
//		LODOP.PREVIEW();
		LODOP.PRINT();
	}
	
	if(!hasPrint){
		console.error("未找到小票打印机XP-80C,打印小票失败");
		layer.msg("未找到小票打印机XP-80C,打印小票失败",{icon:1,offsetTop: 500});
	}
	return true;
}
