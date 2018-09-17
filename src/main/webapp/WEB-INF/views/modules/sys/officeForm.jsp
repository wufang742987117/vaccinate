<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>科室配置</title>
	<meta name="decorator" content="default"/>
	
	<style type="text/css">
		.vacitem{
			float: left;
			font-size: 14px;
			margin-left: 15px;
			margin-top: 5px;
			min-width: 150px;
		}
		.hide{
			display: none;
		}
		li{
		list-style: none;
		}
		ul{
		margin-left: -20px;
		}
		.trangle{
			display: inline-block;
			width: 0;
		    height: 0;
		    border-top: 5px solid transparent;
		    border-bottom: 5px solid transparent;
		    border-left: 7px solid gray;
		}
		.trangles{
			width: 0;
		    height: 0;
		    border-top: 7px solid gray;
		    border-right: 5px solid transparent;
		    border-left: 5px solid transparent;
		    transform: translateY(25%);
		    
		}
		.label-child{
			font-weight: 500;
		}
		
 		.child-box{
		    display: block;
		    position: absolute;
		    background: #fff;
		    padding: 10px 10px 0 0;
		    border: 1px #339381 solid;
		    opacity: 0.95;
		}
	</style>
	
	<c:if test="${not empty saveSuccess }">
		<script type="text/javascript">
			setTimeout(function(){
				parent.closeForm();
			}, 1000);
		</script>
	</c:if>
	
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
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
			
			/* 疫苗种类复选框赋初值  */
			var d = "${office.vaccines}";
			if(""!=d){
				$.each(d.split(","),function(i,d){
					$("input[value=" + d + "]").attr("checked", true);
					$("input[value=" + d + "]").parent().addClass("checked");
				});
			}
			
		});
		function showChild(e){
			var ev = e || window.event;  
            var target = ev.target || ev.srcElement; 
            $(target).children(".child-box").hasClass("hide")?$(target).children(".child-box").removeClass("hide"):$(target).children(".child-box").addClass("hide");
		}
		$(document).ready(function() {
			//全选
			$("#selectAll").click(function(){
				$("input[type=checkbox]").each(function(){
					$(this).prop("checked", true);
					$(this).parent().addClass("checked");
				}); 
			});
			//取消全选
			$("#cancelAll").click(function(){
				$("input[type=checkbox]").each(function(){
					$(this).prop("checked", false);
					$(this).parent().removeClass("checked");
				});
			});
			
			//默认
			var d0= "${office.code}";
			var dd =""; 
			$("#defaultoc").click(function(){
				$(".vaccpanel input[type=checkbox]").each(function(){
					$(this).prop("checked", false);
					$(this).parent().removeClass("checked");
				});
				if(d0=="1"){
					dd="01,02,17,87";
				}else if(d0=="2"){
					dd = "85,83,81,21,22,23,24,25,50,54";
				}else if(d0=="3"){
					dd = "03,04";
				}else if(d0=="4"){
					dd = "06,12,14,84,82";
				}
				$.each(dd.split(","),function(i,dd){
					$(".vaccpanel input[value=" + dd + "]").prop("checked", true);
					$(".vaccpanel input[value=" + dd + "]").parent().addClass("checked");
				});
				
			});
			$(".icheckbox_square-green").click(function(){
				$(this).hasClass("checked")?$(this).removeClass("checked"):$(this).addClass("checked");
				if(!$(this).hasClass("checked")){
					if($(this).hasClass("parent")){
						$(this).siblings(".child-box").find(".child").removeClass("checked");
						$(this).siblings(".child-box").find(".child").find("input").attr("checked", false);
					}else{
						if($(this).parents("ul").find(".child").length == $(this).parents("ul").find("label .checked").length + 1){
							$(this).parents(".child-box").siblings(".icheckbox_square-green").removeClass("checked");
							$(this).parents(".child-box").siblings(".icheckbox_square-green").find("input").attr("checked", false);
						}
					}
					
				}else{
					if($(this).hasClass("parent")){
						$(this).siblings(".child-box").find(".child").addClass("checked");
						$(this).siblings(".child-box").find(".child").find("input").attr("checked", true);
					}else{
						if($(this).parents("ul").find(".child").length == $(this).parents("ul").find("label .checked").length){
							$(this).parents(".child-box").siblings(".icheckbox_square-green").addClass("checked");
							$(this).parents(".child-box").siblings(".icheckbox_square-green").find("input").attr("checked", true);
						}
					}
					
				}
				
			});
			$(".vacitem span").click(function(){
				$(this).siblings(".child-box").hasClass("hide")?$(this).siblings(".child-box").removeClass("hide"):$(this).siblings(".child-box").addClass("hide");
				if($(this).siblings(".child-box").hasClass("hide")){
					$(this).siblings(".trangle").removeClass("trangles");
				}else{
					$(this).siblings(".trangle").addClass("trangles");
				}
			});
			$(".vacitem .trangle").click(function(){
				$(this).hasClass("trangles")?$(this).removeClass("trangles"):$(this).addClass("trangles");
			});
			
			/*$("body").on("click",".vacitem",function(){
				$(this).child(".child-box").hasClass("none")?$(this).child(".child-box").removeClass("none"):$(this).child(".child-box").addClass("none");
			});*/
			/*$(".i-checks").click(function(){
				$(this).children("child-box").removeClass("hide");
			})*/
		});
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content" >
			<form:form id="inputForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<input type="hidden" name="vaccines" value="0"/>
				
				<sys:message content="${message}"/>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">上级机构:</label>
					<div class="col-sm-5">
						<sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
							title="机构" url="/sys/office/treeData" extId="${office.id}" cssClass="form-control" allowClear="${office.currentUser.admin}"/>
					</div>
				</div> 
				
				<div class="form-group">
					<label class="col-sm-2 control-label">科室名称:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-5">
						<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required"/>
					</div>
					
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">科室编号:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-5">
						<form:input path="code" htmlEscape="false"  cssClass="form-control" maxlength="50" />
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">是否排号:<span class="help-inline"><font color="red">*</font> </span></label>
					<div class="col-sm-5">
						<form:select path="queueAble" cssClass="form-control">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline">“是”代表该科室疫苗参与儿童排号，“否”则表不参与儿童排号</span>
					</div>
				</div>
				
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">科室级别:</label>
					<div class="col-sm-5">
					 <form:select path="grade" class="form-control" readonly="true">
							<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select> 
					
					</div>
				</div>
				<div class="form-group" hidden="hidden">
					<label class="col-sm-2 control-label">是否可用:</label>
					<div class="col-sm-5">
						<form:select path="useable" cssClass="form-control">
							<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
						<span class="help-inline">“是”代表此账号允许登陆，“否”则表示此账号不允许登陆</span>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-2 control-label">备注:</label>
					<div class="col-sm-5">
						<form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200" class="form-control"/>
					</div>
				</div>
				
				<div class="form-group vaccpanel">
					<label class="col-sm-2 control-label">可接种疫苗:</label>
					<a style="margin-left:28px;" href="javascript:void(0)" class="btn btn-xs btn-primary" type="button" id="selectAll">全选</a>
					<a href="javascript:void(0)" class="btn btn-xs btn-primary" type="button" id="cancelAll">取消全选</a> 
					<a href="javascript:void(0)" class="btn btn-xs btn-primary" type="button" id="defaultoc">默认设置</a> 
					<div class="col-sm-9">
<!-- 						<div class="vacitem " style="position:relative;">
						<form:checkbox path="vaccines" label="二价脊灰疫苗" value="0311" />
							<p class="icheckbox_square-green parent"><input type="checkbox" name="vaccines" value="0311" style="opacity:0;"/></p>
							<span class="trangle"></span>
							<span style="font-weight:600;">二价脊灰疫苗</span>
							<input type="hidden" value="0311"/>
							<div class="child-box hide">
									<ul>
										<li><label class="label-child"><p class="icheckbox_square-green child"><input type="checkbox" name="vaccines" style="opacity:0;" value="0311"/></p>子选项</label></li>
										<li><label class="label-child"><p class="icheckbox_square-green child"><input type="checkbox" name="vaccines" style="opacity:0;" value="0311"/></p>子选项</label></li>
									</ul>
								</div>
						</div> -->
						<c:forEach items="${office.vaccinesList}" var="vac">
							<div class="vacitem" >
							<!--<form:checkbox path="vaccines" label="${vac.gName }" value="${vac.gNum }" />-->
								
								<p class="icheckbox_square-green parent"><input type="checkbox" name="vaccines" value="${vac.gNum }" style="opacity:0;"/></p>
								<span class="trangle"></span>
								<span style="font-weight:600;">${vac.gName }</span>
								<input type="hidden" value="${vac.gNum }"/>
								<div class="child-box hide">
									<ul>
										<c:forEach items="${vac.subList}" var="subVac">
											<li>
												<label class="label-child">
													<p class="icheckbox_square-green child">
														<input type="checkbox" name="vaccines" value="${subVac.id }" style="opacity:0;"/>
													</p>${subVac.name }<!-- <input type="hidden" value="0311"/> -->
												</label>
											</li>
										</c:forEach>
									</ul>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-sm-4 col-sm-offset-2">
						<shiro:hasPermission name="sys:office:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
						<input id="btnCancel" class="btn" type="button" value="取消" onclick="parent.closeForm()"/>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</body>
</html>