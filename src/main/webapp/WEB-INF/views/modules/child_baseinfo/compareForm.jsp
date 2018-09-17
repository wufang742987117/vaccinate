<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>个案对比</title>
<meta name="decorator" content="default" />
<style type="text/css">
	.w130{
	    width: 130px !important;
	}
	.noborder{
		border: none !important;
	}
	#ctable,#ctable tr th,#ctable tr td { 
		border:1px solid #DDD; 
		text-align:center; 
		/* background-color:#ccc; */
	}
	.pull-right{
		float:right !important;
		margin-right:100px;
	}
	.text-circle{
		display: block;
		margin: 0 auto;
		width: 15px;
		height: 15px;
		border: 1px #000 solid;
		border-radius: 15px;
		font-size: .8em;
		text-align: center;
	}
</style>
<c:if test="${ 1.eq(arg) }">
	<script type="text/javascript">
		$(function() {
			parent.closeForm1('${code1}');
		});
	</script>
</c:if>
<c:if test="${not empty arg }">
	<script type="text/javascript">
		$(function() {
			parent.closeForm0();
		});
	</script>
</c:if> 
<script type="text/javascript">
		var islocal="${islocal}";
		var larea="${larea}";
		var lsituation="${childBaseinfo.situation}";
		var lreside="${childBaseinfo.reside}";		
		
		var coms = ${not empty coms ? coms : ""};
		var comss = "";
		for(var i = 0; i < coms.length ; i ++){
			if(larea!=coms[i].code){
				comss += "<option value='" + coms[i].code + "'>" + coms[i].name + "</option>";
			}else{
				comss += "<option selected ='selected' value='" + coms[i].code + "'>" + coms[i].name + "</option>";
			}
		}

		/* 获取接种单位 */
		var depts = ${not empty depts ? depts : ""};
		function getDept(code){
			for(var i = 0; i < depts.length ; i ++){
				if(code == depts[i].code){
					return depts[i].name;
				}
			}
			return code;
		}
		
	$(document).ready(function() {
		$("#import").attr("disabled", true);
		btnSubmit();
	});
	
	function cmpdetail(){
		var stbody =document.getElementById('stbody'); 
	    var ltbody =document.getElementById('ltbody');
	    var sRows = stbody.rows.length;
	    var lRows = ltbody.rows.length;
	    var lastdays="1000-01-01";
	    var lastdayl="1000-01-01";
	    for(var i=0; i<sRows; i++){ 
	    	var tt=false;
	    	if(stbody.rows[i].cells[2].innerHTML>lastdays){
	    		lastdays=stbody.rows[i].cells[2].innerHTML;
	    	}
	        for(var p=0; p<lRows; p++){
	            if((stbody.rows[i].cells[0].innerHTML==ltbody.rows[p].cells[0].innerHTML )&&(stbody.rows[i].cells[2].innerHTML == ltbody.rows[p].cells[2].innerHTML) &&
	            		(stbody.rows[i].cells[4].innerHTML==ltbody.rows[p].cells[4].innerHTML )){ 
	            	tt=true;
	            	break;
	           } 
	        }
	        if(!tt){
		        stbody.rows[i].style.backgroundColor = '#ffd5b1'; 	          	  	        	
	        }
	    }
	    for(var i=0; i<lRows; i++){
	    	if(ltbody.rows[i].cells[2].innerHTML>lastdayl){
	    		lastdayl=ltbody.rows[i].cells[2].innerHTML;
	    	}
	    	var tt=false;
	        for(var p=0; p<sRows; p++){
	            if((ltbody.rows[i].cells[0].innerHTML==stbody.rows[p].cells[0].innerHTML )&&(ltbody.rows[i].cells[2].innerHTML == stbody.rows[p].cells[2].innerHTML) &&
	            		(ltbody.rows[i].cells[4].innerHTML==stbody.rows[p].cells[4].innerHTML )){ 
	            	tt=true;
	            	break;
	           } 
	        }
	        if(!tt){
		        ltbody.rows[i].style.backgroundColor = '#ffd5b1'; 	          	  	        	
	        }
	    }
    	$("#lds").html(lastdays);
	    $("#ldl").html(lastdayl);
	    if(lastdays>lastdayl){
	    	$("#lds").attr("style", "color:#ff0000");
	    }else  if(lastdays<lastdayl){
		    $("#ldl").attr("style", "color:#ff0000");
	    }
	    if($("#counts").html()>$("#countl").html()){
	    	$("#counts").attr("style", "color:#ff0000");
	    }else if($("#counts").html()<$("#countl").html()){
	    	$("#countl").attr("style", "color:#ff0000");
	    }
	    
	}
	
	function btnSubmit(){
		var url = "${ftx}/childBaseinfo/wsdlProxy/download";
			var data = new Array();
			data.push("${childBaseinfo.childcode}");
			data.push("0");
			console.info("更新数据：",data);
			var load_idx = layer.load();
			$.ajax({
				url:url,
				data:{"data":data},
				type:"POST",
				traditional: true,  
				timeout:60000,
				success:function(data){
					if(data){
						if(data.indexOf("{") == 0){
							data = "[" + data + "]";
						}
						if(data.indexOf("[{") > -1){
							var infos = JSON.parse(data);
							layer.close(load_idx);
							console.info(infos);
							var html = "";
							var chtml =  "<thead><tr>"+
											"<th>疫苗名称</th>"+
											"<th><span class=\"ib\">针次</span></th>"+
											"<th width=\"20%\">接种日期</th>"+
											"<th>批号</th>"+
											"<th><span class=\"ib\">接种类型</span></th>"+
										"</tr></thead><tbody id='stbody'>";
							for(var i = 0; i < infos.length; i ++ ){
								var icount=0;
								var tt = infos[i];
								for(var j = 0; j < tt.BCV.length; j ++ ){
									var tc = tt.BCV[j];
									if(tc.INOC_UNION_CODE==null || tc.INOC_UNION_CODE==""){
										chtml += "<tr style=\"color: black;\">"+
												"<td>" + tc.VACC_NAME + "</td>"+
												"<td><span class='text-circle'>" + tc.DOSAGE + "<span></td>"+
												"<td>" + tc.VACCINATEDATE.substr(0,10) + "</td>"+
												"<td>" + tc.BATCH + "</td>"+
												"<td>" + getDictLabel(${fns:getDictListJson('vacctype')}, tc.VACCTYPE, '', true) + "</td>"+
											"</tr>";
										icount++;
									}
								}
								html = "<div> <span>儿童编码："+tt.CHILDCODE+"</span></div>"+
								"<div><span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名："+tt.CHILDNAME+"</span>&nbsp;&nbsp;<span>儿童性别："+(tt.GENDER == "1"?"男":"女")+"</span></div>"+
								"<div><span>出生日期："+ tt.BIRTHDAY.substr(0,10)+"</span></div>"+
								"<div><span>父母姓名："+tt.GUARDIANNAME+"/"+ tt.FATHER+"</span></div>"+
								"<div><span>通讯地址："+tt.ADDRESS+"</span></div>"+
								"<div>最后接种日期：<span id='lds'></span>&nbsp;&nbsp;接种剂次数：<span id='counts'>"+icount+"</span></div>";	
							}
							$("#sinfo").html(html);
							$("#stable").html(chtml+"</tbody>");
							
							if(islocal!="1"){
								var appendHtml = "<tr style='background:#ccc;'  data-role='form'>"+ 
								"<td colspan=3 class='noborder'>" +
									"<div class='input-group' style='margin:0 auto;'>"+
									"<span class='input-group-addon gray-bg text-right'>区域划分: </span>" +
										"<select class='form-control w130' id='area'>" + comss+
										"</select>" +
									"</div>"+
								"</td>" +
								"<td colspan=3 class='noborder'>" +
									"<div class='input-group' style='margin:0 auto;'>"+
									"<span class='input-group-addon gray-bg text-right'>在册情况: </span>" +
										"<select class='form-control w130' id='situation'>" +
											"<option value='1'>在册</option> <option value='2'>离册</option> <option value='4'>死亡</option><option value='9'>空挂户</option>"+
										"</select>" +
									"</div>"+
								"</td>" +
								"<td colspan=3 class='noborder'>" +
									"<div class='input-group' style='margin:0 auto;'>"+
									"<span class='input-group-addon gray-bg text-right'>居住属性:  </span>" +
										"<select class='form-control w130' id='reside'>" +
											"<option value='1'>本地</option><option value='2'>流动</option><option value='3'>临时</option>"+
										"</select>" +
									"</div>"+
								"</td>";
								$("#childareainfo").html(appendHtml);
							}
							//$("#import").attr("data-code",infos[0].CHILDCODE);
							$("#import").removeAttr("disabled");
							cmpdetail();
						}else{
							layer.close(load_idx);
							layer.msg(data);
						}
					}else{
						layer.close(load_idx);
						layer.msg("未查询到记录");
					}
				},
				error:function(a,b,c){
					layer.close(load_idx);
					layer.msg("查询失败",{"icon":2});
				}
			});
	}	
	
	function imp(thi){
		var load_idx = layer.load();
		if(islocal!="1"){
			larea=$("#area").val();
			lsituation=$("#situation").val();
			lreside=$("#reside").val();		
		}
		$.ajax({
			url:'${ctx}/child_vaccinaterecord/childVaccinaterecord/testwdsl',
			method:"POST",
			data:{"localCode":localCode,"officeinfo":islocal,"area":larea,"situation":lsituation,"reside":lreside,"childcode":thi},
			timeout:60000,
			success:function(data){
				console.info(data);
				if(data == "200"){
					layer.close(load_idx);
					layer.msg("个案更新成功",{"icon":1});
					setTimeout(function() {
						success(thi);
					}, 1000)
				}else{
					layer.msg(data);
				}
				
			},
			error:function(a,b){
				layer.close(load_idx);
				layer.msg("个案更新失败",{"icon":7});
				console.error(b);
			}
		});
	}
	
	function success(code){
		parent.closeForm1(code);
	}
	
</script>
</head>
<body>
	<div class="ibox">
		<div style="float:left;overflow-x: auto; overflow-y: auto;  width:450px;">
		<h2 style="font-weight:bold;margin-top:0px;margin-bottom:3px;">本地个案</h2>
			<div class="form-group">
				<div>
					<span>儿童编码：${childBaseinfo.childcode}</span>
				</div>
				<div>
					<span>姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名：${childBaseinfo.childname}</span>&nbsp;&nbsp;<span>儿童性别：${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}</span>
				</div>
				<div>
					<span>出生日期：<fmt:formatDate value="${childBaseinfo.birthday}" pattern="yyyy-MM-dd" /></span>
				</div>
				<div>
					<span>父母姓名：${childBaseinfo.guardianname}/${childBaseinfo.father}</span>
				</div>
				<div>
					<span>通讯地址：${childBaseinfo.address}</span>
				</div>
				<div>
					最后接种日期：<span id="ldl"></span>&nbsp;&nbsp;接种剂次数：<span id="countl">${returnlist.size()}</span>
				</div>
			</div>
			<div>
				<table class="table table-bordered table-condensed" id="ltable">
					<thead>
						<tr>
							<th>疫苗名称</th>
							<th><span class="ib">针次</span></th>
							<th width="20%">接种日期</th>
							<th>批号</th>
							<th><span class="ib">接种类型</span></th>
						</tr>
					</thead>
					<tbody id="ltbody">
						<c:forEach items="${returnlist}" var="Nursery1">
							<tr style="color: black;">								
								<td>${Nursery1.vaccName}</td>
								<td><span class="text-circle">${Nursery1.dosage}</span></td>
								<td class="recDate"><fmt:formatDate value="${Nursery1.vaccinatedate}" pattern="yyyy-MM-dd" /></td>
								<td>${Nursery1.batch}</td>		
								<td>${fns:getDictLabel(Nursery1.vacctype, 'vacctype', '')}</td>
								</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<div style="float:right;overflow-x: auto; overflow-y: auto; width:450px;">
		<h2 style="font-weight:bold;margin-top:0px;margin-bottom:3px;">服务器个案</h2>
			<div id="sinfo" class="form-group">
			</div>
			<div>
				<table class="table  table-bordered table-condensed" id="stable">
					
				</table>
			</div>
		</div>
		<div class="ibox-content">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<tbody id="childareainfo">
				</tbody>
			</table>
			<div class="pull-right fix">
				<button type="button" class="btn btn-primary"  style="margin-right: 20px;" onclick="btnSubmit()">查询</button>
				<a id="import" type="button" href="javascript:imp('${childBaseinfo.childcode}')" class="btn btn-primary"  onclick="return confirm('确认要取消该信息吗？', this.href)">导入</a>
			</div>
		</div>
		
	</div>
</body>
</html>