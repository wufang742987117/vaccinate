<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html id="all">

<head>
<meta charset="UTF-8">
<title></title>
<style>
body{ padding:0; margin:0; }
@font-face
{
    font-family:'weiruanyahei';
    src:url('${ctxStatic}/fonts/yahei.eot');
    src:local('☺'),
        url('${ctxStatic}/fonts/yahei.eot?#iefix') format('embedded-opentype'),
        font-weight:normal;
        font-style:normal;
}

</style>
<script type="text/javascript" src='${ctxStatic}/js/jquery.min.js'></script>
<script type="text/javascript" src='${ctxStatic}/js/jquery-qrcode-1.0/jquery.qrcode.js'></script>
<script type="text/javascript" src='${ctxStatic}/js/jquery-qrcode-1.0/qrcode.js'></script>
<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<script >
	$(function(){
		$('#qrcodeCanvas').qrcode({
			text : "${childBaseinfo.childcode}",//二维码代表的字符串  
			width : 100,//二维码宽度  
			height : 100,//二维码高度  
			level:'H'//纠错率
		
		});
	});
	
	
			$(function() {
		    var date = new Date();
		    var year=date.getFullYear();
		    var month = date.getMonth() + 1;
		    var strDate = date.getDate();
		    if (month >= 1 && month <= 9) {
		        month = "0" + month;
		    }
		    if (strDate >= 0 && strDate <= 9) {
		        strDate = "0" + strDate;
		    }
		   document.getElementById("year").innerHTML=year;
		   document.getElementById("month").innerHTML=month;
		   document.getElementById("strDate").innerHTML=strDate;
		});
</script>
</head>

<body> 
	<div style="text-align: left; font-size: 12px;  font-family: "weiruanyahei" id="baseinfo" >
	
		<div style="width: 320px; height: 470px; font-size: 12px; padding: 20px 30px;">
			<table style="border: none; font-size: 12px; line-height: 20px;width: 100%;">
				<tbody>
					<tr>
						<td colspan="2">儿童编码：${childBaseinfo.childcode}</td>
					</tr>
					<tr>
						<td colspan="2">身份证号：${childBaseinfo.cardcode}</td>
					</tr>
					<tr>
						<td colspan="2">出生证号：${childBaseinfo.birthcode}</td>
					</tr>
					<tr>
						<td style="width: 45%">儿童姓名：${childBaseinfo.childname}</td>
						<td style="width: 49%">性别：${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}</td>
					</tr>
					<tr>
						<td colspan="2">出生医院：${childBaseinfo.birthhostipal}</td>
					</tr>
					<tr>
						<td style="width: 45%">出生日期：<fmt:formatDate value="${childBaseinfo.birthday}" pattern="yyyy-MM-dd" /></td>
						<td style="width: 49%">出生体重：${childBaseinfo.birthweight}&nbsp;克</td>
					</tr>
					<tr>
						<td style="width: 45%">母亲姓名：${childBaseinfo.guardianname}</td>
						<td style="width: 49%">母亲电话：${childBaseinfo.guardianmobile}</td>
					</tr>
					<tr>
						<td style="width: 45%">父亲姓名：${childBaseinfo.father}</td>
						<td style="width: 49%">父亲电话：${childBaseinfo.fatherphone}</td>
					</tr>
					<tr>
						<td colspan="2">区域划分：${childBaseinfo.area}</td>
					</tr>
					<tr>
						<td colspan="2" style="padding-left: 60px; text-indent: -60px;">家庭住址：${province }&emsp;${city }&emsp;${county}&emsp;${address }</td>
					</tr>
					<tr>
						<td colspan="2" style="padding-left: 60px; text-indent: -60px;">户籍住址：${pr }&emsp;${ci}&emsp;${co}&emsp;${add }</td>
					</tr>
					<tr>
						<td colspan="2">接种单位名称：${childBaseinfo.officeinfo}</td>
					</tr>
				</tbody>
			</table>
			
		
		
		<%-- 
			<div style="font-size: 12px;line-height: 25px;">儿童编码：${childBaseinfo.childcode}</div>
			<div style="font-size: 12px;line-height: 25px;">身份证号：${childBaseinfo.cardcode}</div>
			<div style="font-size: 12px;line-height: 25px;">出生证号：${childBaseinfo.birthcode}</div>
			<div style="font-size: 12px;line-height: 25px;">
				<div style="font-size: 12px;width: 45%;display: inline-block;">儿童姓名：${childBaseinfo.childname}</div>
				<div style="font-size: 12px;width: 49%;display: inline-block;">性别：${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}</div>
				<div style="font-size: 12px;float: both"></div>
			</div>				
			<div style="font-size: 12px;line-height: 25px;">出生医院：${childBaseinfo.birthhostipal}
			</div>
			<div style="font-size: 12px;line-height: 25px;">
				<div style="font-size: 12px;width: 45%;display: inline-block;">出生日期：<fmt:formatDate value="${childBaseinfo.birthday}" pattern="yyyy-MM-dd" /></div>
				<div style="font-size: 12px;width: 49%;display: inline-block;">出生体重：${childBaseinfo.birthweight}&emsp;克</div>
				<div style="font-size: 12px;float: both"></div>
			</div>
			<div style="font-size: 12px;line-height: 25px;">
				<div style="font-size: 12px;width: 45%;display: inline-block;">母亲姓名：${childBaseinfo.guardianname}</div>
				<div style="font-size: 12px;width: 49%;display: inline-block;">母亲电话：${childBaseinfo.guardianmobile}</div>
				<div style="font-size: 12px;float: both"></div>
			</div>
				
			<div style="font-size: 12px;line-height: 25px;">
				<div style="font-size: 12px;width: 45%;display: inline-block;">父亲姓名：${childBaseinfo.father}</div>
				<div style="font-size: 12px;width: 50%;display: inline-block;">父亲电话：${childBaseinfo.fatherphone}</div>
				<div style="font-size: 12px;float: both"></div>
			</div> 	
			
			--%>
			

			<div style="font-size: 12px;display: inline;width: 100px;height: 126px; position: absolute; top: 370px;"class="code-rect" id="qrcodeCanvas"	 >
				<span style="font-size: 10px; position: absolute; top:110px; left:16px;">(接种证二维码)<span>
			</div>
			
			<div style="font-size: 12px; line-height: 20px; width: 200px; height: 100px; POSITION: ABSOLUTE; top: 360px; left: 170px;">
				<div style="position: relative;top: 26px; ">发证单位（签章）：_________</div>
				<div style="position: relative;top: 50px; ">发证日期: <label id="year"></label> 年 <label id="month"></label> 月 <label id="strDate"></label>日</div>
			</div>
		</div>
	</div>
	
	
	<div style="margin-top: 20px; margin-left: 220px;">
		<button class="btn btn_primary fl" onclick="printinfo()">打印</button>
		<button class="btn btn_default fl" style="margin-left: 20px;" onclick="window.close()">取消</button>
	</div>
</body>
<script>
	 var LODOP;
	 
	 /* 直接打印个案 */
	 function printinfo(){
	 	try{	
			LODOP = getLodop();
		} catch(err) {
			layer.msg('打印控件出错，请检查打印控件是否安装正确，或联系管理员', { icon: 2});
		};
		if (LODOP == null || LODOP ==''){
			return;
		}
		
		//LODOP API 参考网址： https://www.cnblogs.com/banlideli/p/7070435.html
		
	 	LODOP.SET_LICENSES("安徽奇兵医学科技有限公司","56E2EB898EE17DEBD030D1E8A683CAFE","安徽奇兵醫學科技有限公司","423D486AF17E2120FEB7B2BDDF66F396");
		LODOP.SET_LICENSES("THIRD LICENSE","","AnHui Ace-power Medical and Technology Co., Ltd","709251107F8D9D680D1A81F88BED121F");
		
		//打印初始化， PRINT_INIT(strTaskName)，初始化运行环境，清理异常打印遗留的系统资源，设定打印任务名
		LODOP.PRINT_INIT("打印个案");
		
		//增加超文本打印项(普通模式)，ADD_PRINT_HTM(Top,Left,Width,Height,strHtmlContent)
		LODOP.ADD_PRINT_HTM(0,0,"100%","100%",document.getElementById("baseinfo").innerHTML);
		
		//设定纸张大小，SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
		LODOP.SET_PRINT_PAGESIZE(5,998,1400,"");
		
		//增加条形码：ADD_PRINT_BARCODE(Top, Left,Width, Height, CodeType, CodeValue)
		LODOP.ADD_PRINT_BARCODE(370,30,120,120,"QRCode","${childBaseinfo.childcode}");
		
		//设置打印项风格，SET_PRINT_STYLE(strStyleName,varStyleValue)
		LODOP.SET_PRINT_STYLE("FontSize", 9);
		
		//指定打印设备，SET_PRINTER_INDEX(oIndexOrName)
		LODOP.SET_PRINTER_INDEX("Gprinter  GP-1324D");  
		LODOP.PRINT();	
// 		LODOP.PREVIEW(); //打印预览
 		window.close();
	 }
</script>
</html>