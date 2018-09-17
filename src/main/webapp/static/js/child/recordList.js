function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	

	function submitYouFrom() {
		var a = $("#childid").val().trim().length;
		var b = $("#childid").val().trim();
		if (a== 6) {
			layer.closeAll();
			 openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord1/childlist?childcode="+b+"&searchBirth="+b,"儿童列表", "", "");
		}
		if (a== 18) {
			layer.closeAll();
			$("#searchForm").submit();
		}
	}
	function openWindows(url, title, w, h) {
		w = isNull(w) ? 1000 : w;
		h = isNull(h) ? 830 : h;
		var ww = ($(document).width() < w ? $(document).width() - 30 : w)+ "px";
		var hh = ($(document).height() < h ? $(document).height() - 200 : h)+ "px";
		layer.open({
			type : 2,
			area : [ ww, hh ],
			title : isNull(title) ? "信息" : title,
			fix : false, //不固定
			maxmin : false,
			shade : 0.3,
			shift : 0, //0-6的动画形式，-1不开启
			content : url,
			shadeClose : true,
		});
	}
	
	
	function closeForm() {
		layer.closeAll();
		$("#searchForm").submit();
	}
	function closeForm0() {
		layer.closeAll();
		layer.msg('你输入的儿童编码不存在', {icon: 2});    
		
	}

	function closeForm1(childcode) {
		layer.closeAll();
		/*$(childid).val(a);*/
		var navigatorName = "Microsoft Internet Explorer"; 
		if( navigator.appName == navigatorName ){ 
			window.open(ctx + "/child_vaccinaterecord/childVaccinaterecord1/list1?childid="+childcode,"_self"); 
		}else{
			window.location.href = ctx+"/child_vaccinaterecord/childVaccinaterecord1/list1?childid="+childcode;
		}
		
		/*$("#searchForm").submit();*/
	}
	
	function closeForm2(childcode,birthday) {
		layer.closeAll();
		 openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord1/childlist?childcode="+childcode+"&birthday="+birthday,"儿童列表", "", "");
		
	}
	function closeForm3(id) {
		layer.closeAll();
		openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord1/childlist?id="+id,"儿童列表", "", "");
	}
	
	function setRefreshArg(chid1, chid2) {
		$("input[name=chid2]").val(chid2);
	}
	
	
	$(function() {
		var value = $("#childid").val();
		if (value == "") {
			document.getElementById('childid').focus();
		}
	});