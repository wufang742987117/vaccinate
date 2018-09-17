<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<title>儿童列表</title>
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

		function submitYouFrom() {
			var childcode = $("#childcode").val();
			var birthday = $("#birthday").val();
			parent.closeForm2(childcode,birthday);
		}
		function db(id){
			parent.closeForm3(id);
		};
		
		var coms = ${not empty coms ? coms : ""};
		var comss = "";
		for(var i = 0; i < coms.length ; i ++){
			comss += "<option value='" + coms[i].code + "'>" + coms[i].name + "</option>";
		}
		
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
							"</td>" +
							"<td colspan=1 class='noborder'>" +
								"<div class='input-group'>"+
									"<button class='btn btn-primary' onclick='imp(this)' data-code='#code#'>导入</button>"+
								"</div>"+
							"</td>"+
						"</tr>";
		
		function select(code,area,bcv,thi){
			$this=$(thi);
			$("tr[data-role=form]").remove();
			$this.after(appendHtml.replace("#code#",code));
			if(area != ""){
				$("#area").val(area);
			}
			$("#ctbody").html(bcv);
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
		
		$(function(){
		
			/* 时间校验 */
			$("#birthday").blur(function(){
				if($(this).val()){
					var birthstr = $(this).val().replace("-", "");
					if(birthstr.length == 6){
						birthstr = (new Date().getFullYear()+"").substr(0,2) + birthstr;
					}
					if(birthstr.length == 8){
						birthstr = birthstr.substr(0, 4) + "-" + birthstr.substr(4, 2) + "-" + birthstr.substr(6, 2);
						var birthday = Date.parse(birthstr)
						if(birthday){
							$(this).val(birthstr);
							return ;
						}
					}
					layer.msg("日期格式错误",{"icon":7});
					$(this).val("");
				}
			});
			
			$("#clear").click(function(){
				$("input").val("");
				$("#childcode").keyup();
			});
			
			$("#childcode").keyup(function(){
				if($(this).val()){
					$("#searchForm .form-group").each(function(i,t){
						if(i != 0 && i != 7 && i != 3){
							$(this).hide();
						}
					})
				}else{
					$("#searchForm .form-group").show();
				}
			});
			
			$("#childcode").change(function(){
				if($(this).val()){
					$("#searchForm .form-group").each(function(i,t){
						if(i != 0 && i != 7 && i != 3){
							$(this).hide();
						}
					})
				}else{
					$("#searchForm .form-group").show();
				}
			});
		
			$("#btnSubmit").click(function(){
				var url = "${ftx}/childBaseinfo/wsdlProxy/selectdept"
				var data = new Array();
				if($("#childcode").val()){
					if(!isNaN($("#childcode").val()) && $("#childcode").val().length == 18){
						url = "${ftx}/childBaseinfo/wsdlProxy/downloaddept";
						data.push($("#childcode").val());
						data.push("0");
					}else{
						layer.msg("儿童编码格式错误，请重新输入",{"icon":7});
						$("#childcode").val("");
						return;
					}
				}else{
					var hashname = false;
					$(".name").each(function(i,t){
						if($(this).val()){
							hashname = true;
						}
					});
					if(!hashname){
						layer.msg("儿童姓名和父母亲姓名至少填写一个",{"icon":7});
						return ;
					}
					if(!$("#birthday").val()){
						layer.msg("请填写出生日期",{"icon":7});
						return ;
					}
					data.push($("#childname").val());
					data.push($("#gender").val());
					data.push($("#birthday").val());
					if($("#father").val()==null || $("#father").val()==""){
						data.push($("#guardianname").val());
						data.push(true);
					}else{
						data.push($("#father").val());
						data.push(false);
					}
				}
				data.push(localCode);
				console.info("查询异地数据：",data);
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
								var chtml =  "<tr>"+
												"<th>接种疫苗</th>"+
												"<th>剂次</th>"+
												"<th>接种时间</th>"+
											"</tr>";
								for(var i = 0; i < infos.length; i ++ ){
									var tt = infos[i];
									for(var j = 0; j < tt.BCV.length; j ++ ){
										var tc = tt.BCV[j];
										chtml += "<tr>"+
												"<td>" + tc.VACC_NAME + "</td>"+
												"<td>" + tc.DOSAGE + "</td>"+
												"<td>" + tc.VACCINATEDATE + "</td>"+
											"</tr>";
									}
									
									html += "<tr data-role='list' ondblclick='select(\"" + tt.CHILDCODE + "\",\"" + tt.AREA + "\",\"" + chtml + "\",this)'>"+
											"<td>" + tt.CHILDCODE + "</td>"+
											"<td>" + tt.CHILDNAME + "</td>"+
											"<td>" + (tt.GENDER == "1"?"男":"女") + "</td>"+
											"<td>" + tt.BIRTHDAY.substr(0,10) + "</td>"+
											"<td>" + tt.GUARDIANNAME + "</td>"+
											"<td>" + tt.FATHER + "</td>"+
											"<td>" + tt.ADDRESS + "</td>"+
											"<td>" + getDept(tt.OFFICECODE) + "</td>"+
											"<td>" + tt.CARDCODE + "</td>"+
											"<td>" + tt.BIRTHCODE + "</td>"+
										"</tr>"
										
								}
								$("#tbody").html(html);
							}else if(data.length>0){
								layer.closeAll();
								if(data.length==1){
									parent.window.location.href=ctx + "/child_vaccinaterecord/childVaccinaterecord1/list1?childid="+data[0].childcode;
								}else{
									//parent.openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord1/childlist?childcode=&birthday="+data[0].childbirthday,"儿童列表", "", "");
								}
							}else{
								layer.close(load_idx);
								layer.msg(data);
							}
						}else{
							layer.close(load_idx);
							layer.msg("未查询的记录");
						}
					},
					error:function(a,b,c){
						layer.close(load_idx);
						layer.msg("查询失败",{"icon":2});
					}
				});

				
			});
		})
	
	function imp(thi){
		var _this = $(thi);
		var load_idx = layer.load();
		$.ajax({
			url:'${ctx}/child_vaccinaterecord/childVaccinaterecord/testwdsl',
			method:"POST",
			data:{"localCode":localCode,"area":$("#area").val(),"situation":$("#situation").val(),"reside":$("#reside").val(),"childcode":_this.attr("data-code")},
			timeout:60000,
			success:function(data){
				console.info(data);
				if(data == "200"){
					layer.close(load_idx);
					layer.msg("个案导入成功",{"icon":1});
					setTimeout(function() {
						success(_this.attr("data-code"));
					}, 1000)
				}else{
					layer.msg(data);
				}
				
			},
			error:function(a,b){
				layer.close(load_idx);
				layer.msg("个案导入失败",{"icon":7});
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
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="childBaseinfo" class="form-inline">
				<div class="form-group">
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">儿童编码：</span>
						 	<form:input path="childcode" data-attr="childcode" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">出生日期：<span style="color: red">*</span></span>
							<form:input path="birthday" data-attr="birthday" readonly="" class="form-control" value="" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">性别：<span style="color: red">*</span></span>
						<form:select path="gender" items="${fns:getDictList('sex')}" data-attr="sex" itemLabel="label" itemValue="value" htmlEscape="false" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<button id="clear" type="button" class="btn btn-primary">清空</button>
				</div>
				<div class="form-group">
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">儿童姓名：</span>
						 	<form:input path="childname" htmlEscape="false" data-attr="childname" maxlength="50" class="form-control name"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">母亲姓名：</span>
						 	<form:input path="guardianname" htmlEscape="false" data-attr="guardianname" maxlength="50" class="form-control name"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">父亲姓名：</span>
						 	<form:input path="father" htmlEscape="false" data-attr="father" maxlength="50" class="form-control name"/>
					</div>
				</div>
				
				<div class="form-group">
					<button id="btnSubmit" type="button" class="btn btn-primary">查询</button>
				</div>
			</form:form>
			<sys:message content="${message}" />

		</div>
		<table id="contentTable"
			class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>儿童编码</th>
					<th>儿童姓名</th>
					<th>性别</th>
					<th>出生日期</th>
					<th>母亲姓名</th>
					<th>父亲姓名</th>
					<th>通讯地址</th>
					<th>现管单位</th>
					<th>儿童身份证</th>
					<th>出生证号</th>
				</tr>
			</thead>
			<tbody id="tbody">
			</tbody>
		</table>
		<div style="overflow-x: auto; overflow-y: auto; height: 300px; width:450px;">
			<table id="ctable" style="height: 300px;width: 440px">
				<tbody id="ctbody">
					
				</tbody>
			</table>
		</div>
		
	</div>
</body>
</html>