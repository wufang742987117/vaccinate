<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>修改</title>
<meta name="decorator" content="default" />
<c:if test="${not empty arg }">
	<script type="text/javascript">
		$(function() {
			layer.msg('操作成功');
			setTimeout(funB, 1200);
		});
		function funB(){
			parent.closeForm1("${childcode}");
			} 
	</script>
</c:if>
<style type="text/css">
ul,li{list-style:none;padding: 0;margin: 0;}
/* #batch{width:100%;padding-left:10px;}
#manufacturer{width:100%;padding-left:10px;} */

#list{display:none;width: 280px;padding-left:10px;position: absolute;background: #fff;z-index: 999;height:40px;overflow-y: auto;cursor: pointer;border:1px solid #ddd;}
#list1{display:none;width: 280px;padding-left:10px;position: absolute;background: #fff;z-index: 999;height:40px;overflow-y: auto;cursor: pointer;border:1px solid #ddd;}

.box{width: 200px;height: 30px;position: relative;}
.mt10{margin-top:10px;}
.pt0{padding-top:0!important;}
.lv{
	background-color: #FFF;
    background-image: none;
    border: 1px solid #e5e6e7;
    border-radius: 1px;
    color: inherit;
    display: inline-block;
    padding: 6px 12px;
    width: 26%;
    font-size: 14px;}
</style>
<c:if test="${childVaccinaterecord.source!=3 }">
	<script type="text/javascript">
		$(function() {
			$("#vaccName").change(
				//通过疫苗名称询所有的批次
				function batchno() {
					var id = $("#vaccName").val();
					$.ajax({
						type : "POST",
						url : "${ctx}/product/bsManageProduct/findViewListApi",
						data : {"vaccineid" : id, "storenumIsNull":false,"showAll":'1'},
						dataType : "json",
						success : function(data) {
						var	html="";
						$.each(data, function(idx, item) { //循环对象取值
							html += "<option value='" + item.batchno + "'>"+ item.batchno + "[" + item.manufacturer + "]" + "</option>";
						});
						
						$("#batch").html(html);
						$("#batch").change();  
						}
					});
				}
					
		);
				
		$(function(){
			$("#batch").change(
				
				//通过疫苗名称和批次查询所有的厂家
				function manufacturer() {
					$("#pid").val($(this).find("option:selected").attr("data-pid"));
				
					var name = $("#vaccName").val();
					var batchno = $("#batch").val();
					$.ajax({
						type : "POST",
						url : "${ctx}/manage_stock_in/manageStockIn/manufacturer",
						data : {
						name :  name,
						batchno : batchno
						},
						dataType : "json",
						success : function(data) {
							var html = "";
							$.each(data, function(idx, item) { //循环对象取值
								html += "<option value='" + item.manufacturer + "'>"+ item.manufacturer + "</option>";
							});
							$("#manufacturer").html(html);
							
						}
					});
				
				}
			);

		});
	});

	</script>
</c:if>
<script type="text/javascript">
	
	$(document).ready(
		function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler : function(form) {
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer : "#messageBox",
				errorPlacement : function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")|| element.is(":radio")|| element.parent().is(".input-append")) {
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			});
			

			 function signature(id) {
				window.location.href= "${ctx}/child_vaccinaterecord/childVaccinaterecord/signature?id="+id+"&type=0";
			}
			 function disassemble(id) {
				window.location.href= "${ctx}/child_vaccinaterecord/childVaccinaterecord/disassemble?id="+id;
			}
			
			       var baseinfo = JSON.parse('${baseinfo}');       
       function checkBirthDay1(value){
      	 var currDay = new Date(value);
      	 checkBirthDay(currDay, ".checkBirthDay1");
       }       
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
</head>
<body>

	<div class="ibox" >
		<div class="ibox-content">
			<form:form id="inputForm" modelAttribute="childVaccinaterecord"
				action="${ctx}/child_vaccinaterecord/childVaccinaterecord/save"
				method="post" class="form-horizontal">
				<form:hidden path="id" />
				<sys:message content="${message}" />
				 <c:choose>
					 <c:when test="${childVaccinaterecord.source==3 }">
					
					<div class="form-group">
						<label class="col-sm-2 control-label">疫苗名称：</label>
						<div class="col-sm-5">
						<%-- <form:input path="vaccName" htmlEscape="false" maxlength="50" readonly="true"
								class="form-control " /> --%>
								<form:select path="vaccineid" class="form-control ">
									<form:options items="${vacclist}" itemLabel="name" itemValue="id" htmlEscape="false" />
								</form:select> 
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">疫苗批号：</label>
						<div class="col-sm-5">
							<input type="text" id="batch" class="form-control "  placeholder="请输入批号" name="batch" value="${childVaccinaterecord.batch }">
						</div>
					</div> 
					
					 <div class="form-group">
						<label class="col-sm-2 control-label">疫苗厂家：</label>
						<div class="col-sm-5">
							<form:select path="manufacturer" class="form-control " >
								<form:option value="" label="" />
								<c:forEach items="${EnterpriseInfoList }" var="bsManageVaccine">
									<form:option value="${bsManageVaccine.name }"
										label="${bsManageVaccine.name }" />
								</c:forEach>
							</form:select>
						</div>
					</div> 
					
					<div class="form-group">
						<label class="col-sm-2 control-label">疫苗价格：</label><c:choose>
						<c:when test="${childVaccinaterecord.price!=0 }">
						<div class="col-sm-5" >
						<label class="col-sm-2 control-label" ><span class="label label-danger">自费</span>	</label>
						</div>
						</c:when>
						<c:otherwise>
						<div class="col-sm-5" >
						<label class="col-sm-2 control-label"><span class="label label-deafult">免费</span>	</label>
						</div>
						</c:otherwise>
						
						</c:choose>
						<div class="col-sm-5" hidden="hidden">
							<form:input path="price" htmlEscape="false" maxlength="50" 
								class="form-control required number" />
						</div>
						</div>
					
						<div class="form-group" hidden="hidden" >
						<label class="col-sm-2 control-label">儿童ID：</label>
						<div class="col-sm-5">
							<form:input path="childid" htmlEscape="false" maxlength="50"
								class="form-control " />
						</div>
					</div>
						<div class="form-group" hidden="hidden" >
						<label class="col-sm-2 control-label">NID：</label>
						<div class="col-sm-5">
							<form:input path="nid" htmlEscape="false" maxlength="50"
								class="form-control " />
						</div>
					</div>
						<div class="form-group" hidden="hidden" >
						<label class="col-sm-2 control-label">数据来源：</label>
						<div class="col-sm-5">
							<form:input path="source" htmlEscape="false" maxlength="50"
								class="form-control " />
						</div>
					</div>
						<div class="form-group" hidden="hidden" >
						<label class="col-sm-2 control-label">儿童编码：</label>
						<div class="col-sm-5">
							<form:input path="childcode" htmlEscape="false" maxlength="50"
								class="form-control " />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">针&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</label>
						<div class="col-sm-5">
							<form:input path="dosage" htmlEscape="false" maxlength="50" readonly="true"
								class="form-control " />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种日期：</label>
						<div class="col-sm-5">
							<input name="vaccinatedate" 
								value="<fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss',choose: checkBirthDay1})"
								class="laydate-icon form-control layer-date required checkBirthDay1">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种部位：</label>
						<div class="col-sm-5">
							<form:select path="bodypart" class="form-control ">
								<form:options items="${fns:getDictList('position')}" itemLabel="label"
									itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种类型：</label>
						<div class="col-sm-5">
							<form:select path="vacctype" class="form-control ">
								<form:options items="${fns:getDictList('vacctype')}" itemLabel="label"
									itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种地点：</label>
						<div class="col-sm-5">
						<c:choose>
						<c:when test="${childVaccinaterecord.sign=='1' }">
						<form:select path="office" class="form-control ">
								<form:options items="${BirthHospitallist}" itemLabel="name"
									itemValue="code" htmlEscape="false" />
						</form:select> 
						</c:when>
						<c:otherwise>
						 <form:select path="office" class="form-control ">
								<form:options items="${departmentlist}" itemLabel="name"
									itemValue="code" htmlEscape="false" />
						</form:select> 
						</c:otherwise>
						</c:choose>
						<%-- 	
						<form:input path="office" htmlEscape="false" maxlength="50"
								class="form-control " />  --%>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">医生姓名：</label>
						<div class="col-sm-5">
							<form:input path="doctor" htmlEscape="false" maxlength="20"
								class="form-control " />
						</div>
					</div>
					
					</c:when>
					
					
					<c:otherwise>
					<div class="form-group">
						<label class="col-sm-2 control-label">疫苗名称：</label>
						<div class="col-sm-5">
								<form:select path="vaccName" class="form-control " disabled="true">
<%-- 									<form:options items="${productlist}" itemLabel="vaccName" itemValue="id" htmlEscape="false" /> --%>
									<form:options items="${vacclist}" itemLabel="name" itemValue="id" htmlEscape="false" />
								</form:select> 
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">疫苗批号：</label>
						<div class="col-sm-5">
							
						<form:select path="batch" class="form-control " disabled="true">
								<c:forEach items="${bsManageProductList }" var="bsManageProduct">
									<form:option value="${bsManageProduct.batchno }" label="${bsManageProduct.batchno }  [${bsManageProduct.manufacturer }]" data-pid="${bsManageProduct.id }" />
								</c:forEach>
							</form:select>
						</div>
					</div> 
					<input id="pid" type="hidden" name="productid" value="${childVaccinaterecord.productid}">
					
					<%--  <div class="form-group">
						<label class="col-sm-2 control-label">疫苗厂家：</label>
						<div class="col-sm-5">
								
							<form:select path="manufacturer" class="form-control " >
								<c:forEach items="${productList }" var="product">
									<form:option value="${product.manufacturer }"
										label="${product.manufacturer }" />
								</c:forEach>
							</form:select>
						</div>
					</div>  --%>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">疫苗价格：</label>
						<c:choose>
							<c:when test="${childVaccinaterecord.price!=0 }">
								<div class="col-sm-5" >
									<label class="col-sm-2 control-label" ><span class="label label-danger">自费</span>	</label>
								</div>
							</c:when>
							<c:otherwise>
								<div class="col-sm-5" >
									<label class="col-sm-2 control-label"><span class="label label-deafult">免费</span>	</label>
								</div>
							</c:otherwise>
						</c:choose>
						<div class="col-sm-5" hidden="hidden">
							<form:input path="price" htmlEscape="false" maxlength="50" class="form-control required number" />
						</div>
					</div>
					
						<div class="form-group" hidden="hidden" >
						<label class="col-sm-2 control-label">儿童ID：</label>
						<div class="col-sm-5">
							<form:input path="childid" htmlEscape="false" maxlength="50" class="form-control " />
						</div>
					</div>
						<div class="form-group" hidden="hidden" >
						<label class="col-sm-2 control-label">NID：</label>
						<div class="col-sm-5">
							<form:input path="nid" htmlEscape="false" maxlength="50" class="form-control " />
						</div>
					</div>
						<div class="form-group" hidden="hidden" >
						<label class="col-sm-2 control-label">数据来源：</label>
						<div class="col-sm-5">
							<form:input path="source" htmlEscape="false" maxlength="50" class="form-control " />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">针&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;次：</label>
						<div class="col-sm-5">
							<form:input path="dosage" htmlEscape="false" maxlength="50" disabled="true" class="form-control " />
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种日期：</label>
						<div class="col-sm-5">
							<input name="vaccinatedate" 
								value="<fmt:formatDate value="${childVaccinaterecord.vaccinatedate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss' ,choose: checkBirthDay2})"
								class="laydate-icon form-control layer-date required checkBirthDay2">
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种部位：</label>
						<div class="col-sm-5">
							<form:select path="bodypart" class="form-control ">
								<form:options items="${fns:getDictList('position')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种类型：</label>
						<div class="col-sm-5">
							<form:select path="vacctype" class="form-control ">
								<form:options items="${fns:getDictList('vacctype')}" itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">接种地点：</label>
						<div class="col-sm-5">
						<c:choose>
							<c:when test="${childVaccinaterecord.sign=='1' }">
								<form:select path="office" class="form-control ">
										<form:options items="${BirthHospitallist}" itemLabel="name" itemValue="code" htmlEscape="false" />
								</form:select> 
							</c:when>
							<c:otherwise>
								 <form:select path="office" class="form-control ">
										<form:options items="${departmentlist}" itemLabel="name" itemValue="code" htmlEscape="false" />
								</form:select> 
							</c:otherwise>
						</c:choose>
						<%-- 	
						<form:input path="office" htmlEscape="false" maxlength="50"
								class="form-control " />  --%>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">医生姓名：</label>
						<div class="col-sm-5">
							<form:input path="doctor" htmlEscape="false" maxlength="20"
								class="form-control " />
						</div>
					</div>
					</c:otherwise>				 
				 </c:choose>
				 
				 <div class="form-group">
					<label class="col-sm-2 control-label">异常反应：</label>
					<div class="col-sm-5">
						<form:radiobuttons path="iseffect" items="${fns:getDictList('iseffect')}"
							itemLabel="label" itemValue="value" htmlEscape="false"
							element="label class='checkbox-inline i-checks'" class="required" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">保险：</label>
					<div class="col-sm-5">
						<%-- <form:input path="insurance" htmlEscape="false" maxlength="200"
							class="form-control " /> --%>
							<form:radiobuttons path="insurance" items="${fns:getDictList('insurance')}"
							itemLabel="label" itemValue="value" htmlEscape="false"
							element="label class='checkbox-inline i-checks'" class="required" disabled="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注：</label>
					<div class="col-sm-5">
						<form:input path="remarks" htmlEscape="false" maxlength="200"
							class="form-control " />
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-5 col-sm-offset-2">
						<shiro:hasPermission name="child:cord:edit">
							<input id="btnSubmit" class="btn btn-primary" type="submit"
								value="修改" />&nbsp;&nbsp;&nbsp;</shiro:hasPermission>
					
						 <c:if test="${childVaccinaterecord.signature!='0'}">
						<input class="btn  btn-primary"   value="查看签字" onclick="signature('${childVaccinaterecord.id}')" >&nbsp;&nbsp;&nbsp;
						 </c:if>  
						<c:if test="${childVaccinaterecord.bigcode== 50}">
						<input class="btn  btn-primary"   value="五联拆解" onclick="disassemble('${childVaccinaterecord.id}')" >
						</c:if>	 
					
					</div>
					
				</div>
				
			</form:form>
				
		</div>
<script type="text/javascript">
	/* $(function(){
		var oInput = $('#batch');
		var oUl = $('#list');
		var oLi = oUl.find('li');
		oInput.on('focus',function(){
			oUl.show();
			$('#list').delegate("li","click",function(){
				oInput.val($(this).html());
				oUl.hide();	
				$("#batch").change();
			});
			$(this).on('input',function(){
				var inputVal = $(this).val();
				$('#list').find('li').hide();
				 $('#list').find('li').each(function(){
				 	if ($(this).text().indexOf(inputVal)>=0) {
				 		$(this).show();
				 	}
				    
				  });
			});
		});
		 $(document).on('click',function(e){
			if(e.target.id == 'batch'){
				oUl.show();
			}else{
				oUl.hide();
			}		
		}); 


	});
	$(function(){
		var oInput = $('#manufacturer');
		var oUl = $('#list1');
		var oLi = oUl.find('li');
		oInput.on('focus',function(){
			oUl.show();
			
			$('#list1').delegate("li","click",function(){
				oInput.val($(this).html());
				oUl.hide();	
			});
			$(this).on('input',function(){
				var inputVal = $(this).val();
				$('#list1').find('li').hide();
				 $('#list1').find('li').each(function(){
				 	if ($(this).text().indexOf(inputVal)>=0) {
				 		$(this).show();
				 	}
				    
				  });
			});
		});
		 $(document).on('click',function(e){
			if(e.target.id == 'manufacturer'){
				oUl.show();
			}else{
				oUl.hide();
			}		
		}); 


	});
	 */
 	$(function(){
		$("#vaccName").change();
	});
	 
	</script>
	</div>
</body>
</html>