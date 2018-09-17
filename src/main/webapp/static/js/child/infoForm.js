/* 默认地址 */
$(function() {
		$("#inputForm").validate(
			{
				submitHandler : function(form) {
					if(new Date($("input[name='birthday']").val()) > new Date()){
						error("出生日期不能在今天以后");
						return;
					}
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio")
							|| element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}

			});

		/* 回调父容器关闭自己 */
		$("#btnSubmit").click(
				function() {
					parent.setRefreshArg($("#childcode").val(),
							$("#childcode1").val());
				});
		
		
		$("#removeBox").change(function(){
			if($(this).is(':checked')){
				document.getElementById("childcode").readOnly=false;
				$('#testwdsl').show();
				
			}else{
				document.getElementById("childcode").readOnly=true;
				$('#testwdsl').hide();
			}
		});
		
		$('#testwdsl').click(
				function(){
					var index = layer.load();
					var childcode = $("#childcode").val();
					$.ajax({
						type : "POST",
						url : ctx + "/child_vaccinaterecord/childVaccinaterecord/testwdsl",
						data : {childcode : childcode},
						
						success : function(data) {
							layer.close(index); 
							if(data=='0'){
								showmer();
							}else if(data=='1'){
								layer.msg('没找到该儿童信息',{icon: 2});
							}else if(data=='2'){
								layer.msg('你输入的儿童编码有误',{icon: 2});
							}else{
								layer.msg('网络异常，请稍后重试',{icon: 2});
							};
						},
						error:function(){
							layer.close(index); 
						}
						});
				}	
		);
		
	});
	
			
/* 修改地址 */
function showmer() {
	var a = $("#childcode").val().length;
	if (a == 18) {
		var childcodeval = $("#childcode").val();
		$.ajax({
					type : "POST",
					url : ctx + "/child_baseinfo/childBaseinfo/searchData",
					data : {childcodeval : childcodeval},
					dataType : "json",
					success : function(data) {
						if(!data[0]){
							layer.msg('此编码可用',{icon: 1});
							$("input[name='id']").val("");
							$("input[name='childname']").val("");//儿童姓名
							$("#gender1").parent().parent().click();//儿童性别
							$("input[name='birthcode']").val("");//出生证号
							$("input[name='cardcode']").val("");//儿童身份证号
							$("input[name='guardianname']").val("");//母亲姓名
							$("input[name='father']").val("");//监护人姓名
							$("input[name='guardianidentificationnumber']").val("");//母亲身份证号
							$("input[name='fathercard']").val("");//监护人身份证号
							$("input[name='guardianmobile']").val("");//母亲电话
							$("input[name='fatherphone']").val("");//监护人电话
							$("input[name='childorder']").val("1");//胎次
							$("input[name='birthhostipal']").val("3406020102");//出生医院
							$("input[name='birthday']").val(data[1].birthday);//出生日期
							$("input[name='nation']").val("01");//民族
							$("input[name='area']").val("001");//社区
							$("input[name='birthweight']").val("");//体重
							$("#properties").val("5");//户口类别
							$("#reside").val("1");//居住类别
							$("input[name='mailingaddress']").val("");//通讯地址
							//家庭地址
							$("#sheng").val(data[1].province);
							 var shi =JSON.parse(data[2]);
								html="";
								$.each(shi, function(i, t) {
									html += "<option value='" + t.id + "'";
									if (t.id == data[1].city) {
										html += " selected =true ";
									}
									html += "> " + t.name + " </option>";
								});
								$("#shi").html(html);
								var qu =JSON.parse(data[3]);
								html="";
								$.each(qu, function(i, t) {
									html += "<option value='" + t.id + "'";
									if (t.id == data[1].county) {
										html += " selected =true ";
									}
									html += "> " + t.name + " </option>";
								});
								$("#qu").html(html);
								
							$("input[name='address']").val("");
							//户籍地址
							$("#sheng1").val(data[1].pr);
							 var shi1 =JSON.parse(data[4]);
								html="";
								$.each(shi1, function(i, t) {
									html += "<option value='" + t.id + "'";
									if (t.id == data[1].ci) {
										html += " selected =true ";
									}
									html += "> " + t.name + " </option>";
								});
								$("#shi1").html(html);
								var qu1 =JSON.parse(data[5]);
								html="";
								$.each(qu1, function(i, t) {
									html += "<option value='" + t.id + "'";
									if (t.id == data[1].co) {
										html += " selected =true ";
									}
									html += "> " + t.name + " </option>";
								});
								$("#qu1").html(html);
							$("#add").val("");
							$("input[name='paradoxicalreaction']").val("无");//异常反应
							$("input[name='remarks']").val("");//备注
						}else{
							layer.msg('此编码已存在',{icon: 2});
						document.getElementById('vaccineid').style.display="None";
						/* if(data[1]!=""){ */
						$("input[name='id']").val(data[1].id);
						$("input[name='childname']").val(data[1].childname);//儿童姓名
						if ($("#gender2").val() == data[1].gender) {//儿童性别
							$("#gender2").parent().parent().click();
						} else {
							$("#gender1").parent().parent().click();
						}
						$("input[name='birthcode']").val(data[1].birthcode);//出生证号
						$("input[name='cardcode']").val(data[1].cardcode);//儿童身份证
						
						$("input[name='guardianname']").val(data[1].guardianname);//母亲姓名
						$("#guardianname").attr("readOnly","true");
						$("input[name='father']").val(data[1].father);//监护人姓名
						$("#father").attr("readOnly","true");
						
						$("input[name='guardianidentificationnumber']").val(data[1].guardianidentificationnumber);//母亲身份证
						$("#guardianidentificationnumber").attr("readOnly","true");
						$("input[name='fathercard']").val(data[1].fathercard);//监护人身份证
						$("#fathercard").attr("readOnly","true");
						
						$("input[name='guardianmobile']").val(data[1].guardianmobile);//母亲电话
						$("input[name='fatherphone']").val(data[1].fatherphone);//监护人电话
						$("input[name='childorder']").val(data[1].childorder);//胎次：
						$("#birthhostipal").val(data[1].birthhostipal);//出生医院
						$("input[name='birthday']").val(data[1].birthday);//出生日期
						$("#nation").val(data[1].nation);//民族
						$("#area").val(data[1].area);//社区
						$("input[name='birthweight']").val(data[1].birthweight);//体重
						$("#properties").val(data[1].properties);//户口类别
						$("#reside").val(data[1].reside);//居住类别
						$("input[name='mailingaddress']").val(data[1].mailingaddress);//通讯地址
						//家庭地址
						$("#sheng").val(data[1].province);
						 var shi =JSON.parse(data[2]);
						html="";
						$.each(shi, function(i, t) {
							html += "<option value='" + t.id + "'";
							if (t.id == data[1].city) {
								html += " selected =true ";
							}
							html += "> " + t.name + " </option>";
						});
						$("#shi").html(html);
						/*var qu =JSON.parse(data[3]);
						html="";
						$.each(qu, function(i, t) {
							html += "<option value='" + t.id + "'";
							if (t.id == data[1].county) {
								html += " selected =true ";
							}
							html += "> " + t.name + " </option>";
						});
						$("#qu").html(html);*/
						
						var qu = JSON.parse(data[3]);
						var v=data[1].county;
						if(!v){
							html="<option value='' ></option>"	
						}else{
							html="";
						}
						$.each(qu, function(i, t) {
							if(v){
								html += "<option value='" + t.id + "'";
							if (t.id == data[1].county) {
									html += " selected =true ";
								}
								html += "> " + t.name + " </option>";
							}else{
								html += "<option value='" + t.id + "'";
								html += "> " + t.name + " </option>";	
							}
							
						});
						$("#qu").html(html);
						
						$("input[name='address']").val(data[1].address);
						
						//户籍地址
						$("#sheng1").val(data[1].pr);
						
						 var shi1 =JSON.parse(data[4]);
							html="";
							$.each(shi1, function(i, t) {
								html += "<option value='" + t.id + "'";
								if (t.id == data[1].ci) {
									html += " selected =true ";
								}
								html += "> " + t.name + " </option>";
							});
							$("#shi1").html(html);
							
							var qu1 = JSON.parse(data[5]);
							var v=data[1].co;
							if(!v){
								html="<option value='' ></option>"	
							}else{
								html="";
							}
							$.each(qu1, function(i, t) {
								if(v){
									html += "<option value='" + t.id + "'";
								if (t.id == data[1].co) {
										html += " selected =true ";
									}
									html += "> " + t.name + " </option>";
								}else{
									html += "<option value='" + t.id + "'";
									html += "> " + t.name + " </option>";	
								}
								
							});
							$("#qu1").html(html);
							
						$("#add").val(data[1].add);
						$("input[name='paradoxicalreaction']").val(data[1].paradoxicalreaction);//异常反应
						$("#remarks").val(data[1].remarks);//备注
					}
						}/* } */
				});

	}
}





	
