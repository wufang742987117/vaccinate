<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>常规接种（3-1-3）</title>
	<style>
		html {overflow-x: hidden;}
		body{ margin:0; padding: 10px 20px;}
		.vaccsel-label{ margin: 6px 6px 6px 0; height: 20px;}
		#selectbox{ border-bottom: 2px solid #777777; padding-bottom: 10px; margin-bottom: 10px;}
		.vaccname-label{ margin: 6px 0; width: 140px; height: 20px; }
		
	</style>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<script type="text/javascript">
    	$(function() {
           $("#all-lab , #all-lab .iCheck-helper").click(function() {
               if($("#all").is(":checked")){
		    		$('input[name="vaccname"]').prop("checked",true); 
		            $('input[name="vaccname"]').parent(".icheckbox_square-green").addClass("hover checked");
		    	}
           });
            
           $("#not-lab , #not-lab .iCheck-helper").click(function() {
            	if($("#not").is(":checked")){
            		$('input[name="vaccname"]').prop("checked",false); 
                	$('input[name="vaccname"]').parent(".icheckbox_square-green").removeClass("hover checked");
            	}
            });
            
            $(".vaccname-label , .vaccname-label .iCheck-helper").click(function(){
            	var vacc_num = $("input[name='vaccname']").size();
            	var vacc_ck_num = $("input[name='vaccname']:checked").size();
            	
            	//判断是否全选
            	if( vacc_ck_num == vacc_num){
            		$("input[name='vaccSelect']").prop("checked",false);
            		$("input[name='vaccSelect']").parent(".iradio_square-green").removeClass("hover checked");
            		$("#all").prop("checked",true); 
		            $("#all").parent(".iradio_square-green").addClass("hover checked");
            	}else{
            		$("#all").prop("checked",false); 
		            $("#all").parent(".iradio_square-green").removeClass("hover checked");
            	}
            	
            	//判断是否全不选
            	if(vacc_ck_num == 0){
            		$("input[name='vaccSelect']").prop("checked",false);
            		$("input[name='vaccSelect']").parent(".iradio_square-green").removeClass("hover checked");
            		$("#not").prop("checked",true); 
		            $("#not").parent(".iradio_square-green").addClass("hover checked");
            	}else{
            		$("#not").prop("checked",false); 
		            $("#not").parent(".iradio_square-green").removeClass("hover checked");
            	}
            });
        });
      
		$(function(){
			$("#confirmBtn").click(function(){
				text1 = $("input:checkbox[name='vaccname']:checked").parent().siblings('span').map(function(index,elem) {
		            return $(elem).text();
		        }).get().join('/');
		        text2 = $("input:checkbox[name='vaccname']:checked").map(function(index,elem) {
		           	return $(elem).val();
		      	}).get().join(',');
		      	text3 = $("input:radio[name='vaccSelect']:checked ").map(function(index,elem) {
		           	return $(elem).val();
		      	}).get().join('/');
	      		parent.openbox6_2back(text1,text2,text3);
	      		parent.closeForm();
			});
		});
		
		function cancle(){
			parent.closeForm();
		}
</script>
</head>
<body>
	<div id="selectbox">
		<label id="all-lab" class="vaccsel-label i-checks"><input id="all" name="vaccSelect" value="" type="radio"/>&nbsp;<span>全选</span></label>&nbsp;&nbsp;
		<label id="not-lab" class="vaccsel-label i-checks"><input id="not" name="vaccSelect" value="" type="radio"/>&nbsp;<span>全不选</span></label>&emsp;&emsp;
	</div> 
	<div>
		<table style="font-size:12px;">
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="0201,0202,0203"/><span>乙肝</span></label></td> <!-- 乙肝(HepB) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1701"/><span>流脑A+C群</span></label></td> <!-- 流脑A+C群(MPSV-AC) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2501"/><span>23价肺炎</span></label></td> <!-- 23价肺炎(PPSV-23) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="3801"/><span>霍乱</span></label><br></td> <!-- 霍乱(Chol) -->
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="0601"/><span>白破</span></label></td> <!-- 白破(DT) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1702"/><span>流脑A+C（结合）</span></label></td> <!-- 流脑A+C(结合)(MCV-AC) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2502"/><span>7价肺炎</span></label></td> <!-- 7价肺炎(PCV-7) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="4701"/><span>森林脑炎</span></label><br></td> <!-- 森林脑炎(TBE) -->
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="0401"/><span>百白破</span></label></td> <!-- 百白破(DTP) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1703"/><span>流脑A+C+Y+W135</span></label></td> <!-- 流脑A+C+Y+W135(MPSV-ACYW135) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2901,2902,2903,2904,2905,2906"/><span>出血热</span></label></td> <!-- 出血热(HF) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="0303"/><span>脊灰疫苗(灭活Salk株)</span></label><br> </td> <!-- 脊灰疫苗(灭活Salk株)(IPV) -->
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1401"/><span>麻风</span></label></td> <!-- 麻风(MR) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1901,1902"/><span>甲肝（减毒）</span></label></td> <!-- 甲肝(减毒)(HepA-l) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="3401"/><span>钩体</span></label></td> <!-- 钩体(Lep) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1301"/><span>麻腮</span></label></td>
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="5201"/><span>戊肝</span></label></td> <!-- 戊肝疫苗(HepE) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1903"/><span>甲肝（灭活）</span></label></td>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="3601"/><span>炭疽</span></label></td>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="5001"/><span>百白破IPV和Hib五联</span></label></td>
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1201"/><span>麻腮风</span></label></td> <!-- 麻腮风(MMR) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2001"/><span>甲乙肝</span></label><br></td> <!-- 甲乙肝(HepAB) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="4301,4401,4402,2801,2802,2803,2804,2805"/><span>狂犬病</span></label></td> <!-- 狂犬病(Rab) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="4901"/><span>百白破Hib四联</span></label><br></td> <!-- 百白破Hib四联(DTaP-Hib) -->
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1101,1102"/><span>风疹(Rub)</span></label></td>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2301"/><span>Hib</span></label></td> <!-- Hib(Hib) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="3001,3002,3101,3201"/><span>伤寒</span></label><br></td> <!-- 伤寒(Typh) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="5301"/><span>A+C流脑Hib联合疫苗</span></label><br></td> <!-- A+C流脑Hib联合疫苗(MPCV-AC/Hib) -->
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1001"/><span>腮腺炎</span></label></td> <!-- 腮腺炎(Mum) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2201"/><span>水痘</span></label></td> <!-- 水痘(Var) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="3301"/><span>痢疾</span></label></td> <!-- 痢疾(Dys) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="5401,5402"/><span>71型肠道病毒(灭活)</span></label><br></td> <!-- 肠道病毒71型疫苗(灭活)(EV71V-i) -->
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1801"/><span>乙脑（减毒）</span></label></td> <!-- 乙脑(减毒)(JE-l) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2401"/><span>轮状病毒</span></label></td> <!-- 轮状病毒(RV) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="3701"/><span>布病</span></label></td> <!-- 布病(Bruc) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="1802,1803"/><span>乙脑（灭活）</span></label></td> <!-- 乙脑(灭活)(JE-i) -->
			</tr>
			<tr>
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="2101,2102,2103,2104,2105,2106"/><span>流感</span></label></td> <!-- 流感(Flu) -->
				<td><label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="3501"/><span>鼠疫</span></label><br></td> <!-- 鼠疫(Plag) -->
			</tr>
		</table>
	</div>
	<div style=" text-align: right; padding: 10px 0; ">
		<button id="confirmBtn" class="btn btn-primary" style=" margin-right: 14px; ">确定</button> 
		<button class="btn btn-default" onclick="javascript:cancle();">取消</button>
	</div>
</body>
</html>
