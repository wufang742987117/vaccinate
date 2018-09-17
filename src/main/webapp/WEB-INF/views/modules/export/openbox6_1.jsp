<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head1.jsp" %>   
<html>
<head>
	<meta charset="UTF-8">
	<title>常规接种（3-1-3）</title>
	<style>
		body{ margin:0; padding: 20px 25px;}
		#selectbox{ border-bottom: 2px solid #777777; padding-bottom: 10px; margin-bottom: 10px;}
		.vaccsel-label{ margin: 8px 5px;}
		.vaccname-label{ margin: 8px 5px; width: 110px;}
		#confirmBtn{ position: absolute; right: 25px; bottom: 20px;}
		
	</style>
	<link rel="stylesheet" href="${ctxStatic}/css/ExportExcel.css">
	<script src="${ctxStatic}/js/LodopFuncs.js"></script>
	<script src="${ctxStatic}/js/ExportExcel.js"></script>
	<script type="text/javascript">
    	$(function(){
    		$("#all-lab , #all-lab .iCheck-helper").click(function(){
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
            
            $("#mcv-lab , #mcv-lab .iCheck-helper").click(function(){
            	if($("#mcv").is(":checked")){
            		$('input[name="vaccname"]').prop("checked",false); 
	             	$('input[name="vaccname"]').parent(".icheckbox_square-green").removeClass("hover checked");
	            	$(".mcv-group").prop("checked",true);
	            	$(".mcv-group").parent(".icheckbox_square-green").addClass("hover checked");
            	}
            });
            
            $("#rcv-lab , #rcv-lab .iCheck-helper").click(function(){
            	if($("#rcv").is(":checked")){
            		$('input[name="vaccname"]').prop("checked",false); 
	             	$('input[name="vaccname"]').parent(".icheckbox_square-green").removeClass("hover checked");
	             	$(".rcv-group").prop("checked",true);
	            	$(".rcv-group").parent(".icheckbox_square-green").addClass("hover checked");
            	}
            });
            
            $("#mumcv-lab , #mumcv-lab .iCheck-helper").click(function(){
            	if($("#mumcv").is(":checked")){
            		$('input[name="vaccname"]').prop("checked",false); 
	             	$('input[name="vaccname"]').parent(".icheckbox_square-green").removeClass("hover checked");
	            	$(".mumcv-group").prop("checked",true);
	            	$(".mumcv-group").parent(".icheckbox_square-green").addClass("hover checked");
            	}
            });
            
            $("#hepa-lab , #hepa-lab .iCheck-helper").click(function(){
            	if($("#hepa").is(":checked")){
            		$('input[name="vaccname"]').prop("checked",false); 
	             	$('input[name="vaccname"]').parent(".icheckbox_square-green").removeClass("hover checked");
	            	$(".hepa-group").prop("checked",true);
	            	$(".hepa-group").parent(".icheckbox_square-green").addClass("hover checked");
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
            	
            	//判断是否全选MCV
            	if($(".mcv-group:checked").size() == $(".mcv-group").size() && vacc_ck_num == 4){
            		$("input[name='vaccSelect']").prop("checked",false);
            		$("input[name='vaccSelect']").parent(".iradio_square-green").removeClass("hover checked");
            		$("#mcv").prop("checked",true); 
		            $("#mcv").parent(".iradio_square-green").addClass("hover checked");
            	}else{
            		$("#mcv").prop("checked",false); 
		            $("#mcv").parent(".iradio_square-green").removeClass("hover checked");
            	}
            	
            	//判断是否全选RCV
            	if($(".rcv-group:checked").size() == $(".rcv-group").size() && vacc_ck_num == 2){
            		$("input[name='vaccSelect']").prop("checked",false);
            		$("input[name='vaccSelect']").parent(".iradio_square-green").removeClass("hover checked");
            		$("#rcv").prop("checked",true); 
		            $("#rcv").parent(".iradio_square-green").addClass("hover checked");
            	}else{
            		$("#rcv").prop("checked",false); 
		            $("#rcv").parent(".iradio_square-green").removeClass("hover checked");
            	}
            	
            	//判断是否全选MumCV
            	if($(".mumcv-group:checked").size() == $(".mumcv-group").size() && vacc_ck_num == 2){
            		$("input[name='vaccSelect']").prop("checked",false);
            		$("input[name='vaccSelect']").parent(".iradio_square-green").removeClass("hover checked");
            		$("#mumcv").prop("checked",true); 
		            $("#mumcv").parent(".iradio_square-green").addClass("hover checked");
            	}else{
            		$("#mumcv").prop("checked",false); 
		            $("#mumcv").parent(".iradio_square-green").removeClass("hover checked");
            	}
            	
            	//判断是否全选HepA
            	if($(".hepa-group:checked").size() == $(".hepa-group").size() && vacc_ck_num == 2){
            		$("input[name='vaccSelect']").prop("checked",false);
            		$("input[name='vaccSelect']").parent(".iradio_square-green").removeClass("hover checked");
            		$("#hepa").prop("checked",true); 
		            $("#hepa").parent(".iradio_square-green").addClass("hover checked");
            	}else{
            		$("#hepa").prop("checked",false); 
		            $("#hepa").parent(".iradio_square-green").removeClass("hover checked");
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
	       		 }).get().join('/');
	       		 text3 = $("input:radio[name='vaccSelect']:checked ").map(function(index,elem) {
	            	return $(elem).val();
	       		 }).get().join('/');
	       		 parent.openbox6_1back(text1,text2,text3);
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
		<label id="all-lab" class="vaccsel-label i-checks"><input id="all" name="vaccSelect" type="radio" value="" /><span>全选</span></label>
		<label id="not-lab" class="vaccsel-label i-checks"><input id="not" name="vaccSelect" type="radio" value="" /><span>全不选</span></label><br />
		<label id="mcv-lab" class="vaccsel-label i-checks"><input id="mcv" name="vaccSelect" type="radio" value="MCV" /><span>MCV(MV、MR、MM、MMR)</span></label>
		<label id="rcv-lab" class="vaccsel-label i-checks"><input id="rcv" name="vaccSelect" type="radio" value="RCV" /><span>RCV(MR、MMR)</span></label>
		<label id="mumcv-lab" class="vaccsel-label i-checks"><input id="mumcv" name="vaccSelect" type="radio" value="MumCV" /><span>MumCV(MM、MMR)</span></label>
		<label id="hepa-lab" class="vaccsel-label i-checks"><input id="hepa" name="vaccSelect" type="radio" value="HepA" /><span>HepA</span></label>
	</div> 
	<div id="selectctn">
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="hepb" /><span>乙肝</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="bcg" /><span>卡介苗</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="pv" /><span>脊灰</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="dtp" /><span>百白破</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="dt" /><span>白破</span></label><br>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="mr" class="mcv-group rcv-group" /><span>麻风</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="mmr" class="mcv-group rcv-group mumcv-group" /><span>麻腮风</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="mm" class="mcv-group mumcv-group" /><span>麻腮</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="mv" class="mcv-group" /><span>麻疹</span></label><br>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="mpv" /><span>流脑A</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="mpvc" /><span>流脑A+C</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="jevl" /><span>乙脑（减毒）</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="jevi_Vero" /><span>乙脑（灭活）</span></label><br>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="hepal" class="hepa-group" /><span>甲肝（减毒）</span></label>
		<label class="vaccname-label i-checks"><input name="vaccname" type="checkbox" value="hepai" class="hepa-group" /><span>甲肝（灭活）</span></label>
	</div>
	<button id="confirmBtn" class="btn btn-primary">确定</button> 
</body>
</html>