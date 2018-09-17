<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>单剂录入</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		.layer-date{
			max-width:100%;
		}
		.nav-tabs>li>a{
			padding: 3px;
		}
	</style>
	<script type="text/javascript">
	
		var op = 1;
        $(function(){
        	$("#childid2").focus();

	        /* 点击ESC 关闭 */
			$(document).keydown(function(event){ 
				if(event.keyCode == 27){ 
					parent.closeAll();
				}
			}); 

			$(".cancel").click(function(){				
				parent.closeAll();
			});
        })
        
        $(function(){
 			$("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				if(!$("#vaccinatedate").val()){
					layer.msg("接种日期不能为空",{"icon":2, "time":1000});
					return false;
				}
				if(op == 1){
					console.info("完成" + op++);					
					form.submit();
				}
				
			},
			errorContainer: "#messageBox",
			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});
        
        $("#bigname").change(function(){
        	$.ajax({
        		url:'${ctx}/vaccine/bsManageVaccine/findVaccListAbleModel',
        		data:{"group":$(this).val()},
        		success:function(data){
        			var	html1="";
        			$.each(data, function(idx, item) { //循环对象取值
// 						html1 += "<option value='" + item.id + "'>"+ item.name + "</option>";
						html1 += "<option value='" + item.id + "'>"+ item.vaccName + "</option>";
					});
					$("#name").html(html1)	
					$("#name").change();				
        		}
        	});
        });
        $("#bigname").change();
        
        //通过疫苗小类id查询所有产品信息
		$("#name").change(function() {	
		var vaccid = $(this).val(); 	
		$.ajax({
			type : "POST",
			url : "${ctx}/product/bsManageProduct/findQueueViewListApi",
			data : {"vaccineid" : vaccid, "storenumIsNull":true, "showAll":"1"},
			dataType : "json",
			success : function(data) {
			var	html1="";
			var initbatch;
			$.each(data, function(idx, item) { //循环对象取值
				html1 += "<option value='" + item.batchno + "' data-vaccineid='" + item.vaccineid +"'>"+ item.batchno + "(" + item.manufacturer + ")" + "</option>";
				if(item.id == "${not empty pid?pid:''}"){
					initbatch = item.batchno;
				}
			});
			$("#batch").html(html1);
			if(initbatch){
				$("#batch").val(initbatch);	
			}
			$("#batch").change(); 
			}
		}); 
			});	
		$("#vaccinatedate").val(SimpleDateFormat(new Date(),"yyyy-MM-dd"));
		
	})
        
	</script>
</head>
<body>
	<div>
 		<script type="text/javascript">
			/* 判断是否在接种计划中 */
			var notFinishNum = JSON.parse('${notFinishNum}');
			function chickFinishNum(gg){
				var isfinish = false;
				for(var i = 0; i < notFinishNum.length; i ++){
					if(notFinishNum[i].group == gg){
						isfinish = true;
					}		
				}
				return isfinish;
			}
 			/*查询疫苗的种类*/
			$(function(){
		        $("#bigname2").change(function(){
		        	$.ajax({
		        		url:'${ctx}/vaccine/bsManageVaccine/findListApi',
		        		data:{"gNum":$(this).val()},
		        		success:function(data){
		        			var	html1="<option value=''></option>";
		        			$.each(data, function(idx, item) { //循环对象取值
								html1 += "<option value='" + item.id + "'>"+ item.name + "</option>";
							});
							$("#vaccineid2").html(html1)	
							$("#vaccineid2").change();				
		        		}
		        	});
		        });
		        $("#bigname2").change();
			
				$("#batch2").change(function(){
					
				});
				
				$("#btn2").click(function(){
					if(!$("#vaccinatedate2").val()){
						layer.msg("请选择接种时间",{"icon":2});
						return false;
					}
					if(!$("#vaccineid2").val()){
						layer.msg("请选择疫苗",{"icon":2});
						return false;
					}
					
					/* 停用三价脊灰 */
					var id2_t=$("#vaccineid2").val();
					if((id2_t=="0301"||id2_t=="0302"||id2_t=="0304"||id2_t=="0305") && $("#vaccinatedate2").val()>="2016-05-01"){
						layer.msg("2016-05-01后停用三价OPV（tOPV）,开始使用二价OPV（bOPV）",{"icon":7});
						return false;
					}
					
					if(!chickFinishNum($("#bigname2").val())){
						layer.confirm('疫苗方案未定义该针次<br>是否继续接种?', {icon: 7,title:"提示"}, function(index){
						  ajaxSave()
						  layer.close(index);
						}); 
					}else{
						ajaxSave()
					}
				});
				
				$("select[name=office2]").val('0000000000');
				$("select[name=vacctype2]").val('1');
			})
			
			//其他接种保存
			function ajaxSave(){
				if(op == 1){
					loading('正在提交，请稍等...');
					console.info("完成" + op++);		
					$.ajax({
						url:"${ctx}/child_vaccinaterecord/childVaccinaterecord/saveAddVacc3",
						data: {
							"vaccinatedate":$("#vaccinatedate2").val(),
							"childid":$("#childid2").val(),
							"vaccineid":$("#vaccineid2").val(),
							"dosage":"1",
							"manufacturer":$("#manufacturer2").val(),
							"manufacturercode":$("#manufacturercode2").val(),
							"batch":$("#batch2").val(),
							"office":$("#office2").val(),
							"bodypart":$("#bodypart2").val(),
							"vacctype":$("#vacctype2").val(),
							"price":$("input[name='price']:checked").val()
							},
						success:function(data){
							if(data.code=="200"){
								layer.msg("保存成功",{"icon":1})
								$("#btn2").attr("disabled", true);
								setTimeout(function() {
									parent.closeForm1(data.childcode);
								}, 1000)
							}else{
								layer.msg(data.msg,{"icon":2})
								op == 1;
							}
							closeLoading();
							},
						error:function(){
							layer.msg("保存失败",{"icon":7});
							op = 1;
							closeLoading();
						}									
					});
				}			
			}
       var baseinfo = JSON.parse('${baseinfo}');       
       function checkBirthDay2(value){
      	 var currDay = new Date(value);
      	 checkBirthDay(currDay, ".checkBirthDay2");
       }
       function checkBirthDay(currDay, el){
       		var _this = $(el);
       		var birthDay = new Date(baseinfo.birthday);
       		if(!currDay){
       			toastrMsg("接种日期错误")
       			_this.val("");
       		}
       		if(currDay > new Date()){
    		    toastrMsg("接种日期不能晚于今天")
       			_this.val("");
       		}
       		if(currDay < birthDay){
       			 toastrMsg("接种日期不能早于宝宝生日")
       			_this.val("");
       		}
       } 
		</script>
		<!-- 异地补录 - 不减库存 -->
			<form id="inputForm2" method="post" action="">
			<input type="hidden" id="childid2" name="childid" value="${childVaccinaterecord.childid }">
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">接种日期：<span style="color: red">*</span></label>
				<div class="col-sm-8">
					<input name="vaccinatedate" id="vaccinatedate2"  value="" onclick="laydate({istime: true, format: 'YYYY-MM-DD',choose: checkBirthDay2})"
						class="laydate-icon form-control layer-date checkBirthDay2">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">疫苗种类：</label>
	            <div class="col-sm-8">
                	<select class="form-control valid required" id="bigname2" >
                		<option value=""></option>
						<c:forEach items="${vacclist2}" var="vac">
							<option value="${vac.gNum}" >${vac.gName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">疫苗小类：</label>
				<div class="col-sm-8">
                	<select class="form-control valid required" id="vaccineid2" name="vaccineid">
                	
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">疫苗价格：</label>
	           	<input type="radio" name="price"  id="free" value="0"  class="i-checks " checked="checked" ><label for="free">一类&nbsp;&nbsp;</label>
	           	<input type="radio" name="price"  id="nofress" value="-1" class="i-checks " ><label for="nofress">二类&nbsp;&nbsp;</label>
			</div>
			<%-- <div class="form-group">
				<label class="col-sm-4 control-label text-right">针次：</label>
				<div class="col-sm-8">
                	<select class="form-control valid required" id="dosage2" name="dosage">
                		<c:forEach items="${fns:getDictList('pin') }" var="dict">
                			<option value="${dict.value }">${dict.label }</option>
                		</c:forEach>
					</select>
				</div>
			</div> --%>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">厂家：</label>
	             <div class="col-sm-8">
                	<select class="form-control valid" id="manufacturer2" name="manufacturer2">
           				<option value=""></option>
                		<c:forEach items="${enterprise}" var="en">
                			<option data-code="${en.id}">${en.name}</option>
                		</c:forEach>
                	</select>
                	<input type="hidden" id="manufacturercode2" name="manufacturercode2">
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">批号：</label>
	             <div class="col-sm-8">
                	<input class="form-control valid" id="batch2" name="batch2"/>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">接种部位：</label>
	                <div class="col-sm-8">
	                	<select class="form-control valid position required" name="bodypart2" id="bodypart2">
						<c:forEach items="${fns:getDictList('position')}" var="lab">
							<option value="${lab.value}">${lab.label}</option>
						</c:forEach>
						</select>
					</div>
	                
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">接种类型：</label>
				<div class="col-sm-8">
                	<select class="form-control valid required" id="vacctype2" name="vacctype2">
                		<c:forEach items="${fns:getDictList('vacctype') }" var="dict">
                			<option value="${dict.value }">${dict.label }</option>
                		</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">接种单位：</label>
	             <div class="col-sm-8">
<%--                 	<input class="form-control valid" id="office2" name="office" value="${localcode}"/> --%>
					<select class="form-control valid position required" name="office2" id="office2" >
						<c:forEach items="${departmentlist}" var="dept">
							<option value="${dept.code}">${dept.name}</option>
						</c:forEach>
<%-- 						<form:options items="${departmentlist}" itemLabel="name" itemValue="code" htmlEscape="false"/> --%>
					</select>
                	
				</div>
			</div>
			
			<div class="form-group " style="height: 27px;">
				<div class="col-xs-6 col-xs-offset-6">
					<div class="row">
						<div class="col-xs-6">
		                	<input type="button" id="btn2" value="保存" class="btn btn-success" >
						</div>
						<div class="col-xs-6">
		                	<input type="button" value="取消" class="btn btn-default cancel" >
						</div>
					</div>					
				</div>
			</div>
				
				</form>
		</div>
		<!-- 异地补录结束 -->
</body>
</html>