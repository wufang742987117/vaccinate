<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>补录</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(
	 function() {
			$("#inputForm").validate(
				{
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
			function getNowFormatDate() {
			    var date = new Date();
			    var seperator1 = "-";
			    var year = date.getFullYear();
			    var month = date.getMonth() + 1;
			    var strDate = date.getDate();
			    if (month >= 1 && month <= 9) {
			        month = "0" + month;
			    }
			    if (strDate >= 0 && strDate <= 9) {
			        strDate = "0" + strDate;
			    }
			    var currentdate = year + seperator1 + month + seperator1 + strDate;
			    return currentdate;
			}
			$(".Manage").dblclick(function(){
				if(!$(this).hasClass("active")){
					return 0;
				}
				var dict = ${fns:getDictListJson("position")};
				var html="";
				$.each(dict, function(idx, item) {
						html +="<option value='" + item.value +" '>"+ item.label + "</option>";
					});
				var name=$(this).attr("data-name");
				if(name){
				$("#recordTbody1").append(
						"<tr class='op'>"+
						"<td ><span class='vaccname'>"+name+"</span><input type='hidden' class='ii' value=" + $(this).attr("data-id") + "></td>"+
						"<td class='date-wrap'><input class='vaccdate date vaccinatedate  required' value='"+getNowFormatDate()+"'></td>"+
						"<td class='date-wrap'><input class='batch inputclass'></td>"+
						"<td class='date-wrap'><input class='office inputclass' value='${n}'></td>"+
						"<td class='date-wrap'><input class='price inputclass'></td>"+
						"<td class='date-wrap'><input class='manufacturer inputclass'></td>"+
						"<td class='date-wrap'><input class='doctor inputclass'></td>"+
						"<td class='date-wrap'><select class='bodypart inputclass'>" + html + "</select></td>"+
						"<td><a class='delete-el'>取消</a></td>"+
					"</tr>"
				);
				}; });
		
			//取消
			$("#recordTbody1").delegate('.delete-el','click',function(){
				$(this).parents('tr').remove();
			});
			//修改日期
			$("#recordTbody1").delegate('.date','click',function(){
				laydate({istime: true, format: 'YYYY-MM-DD'});
			});
			$("#btnsave").click(function(){
				layer.confirm("确定要补录上述疫苗吗？", {
					btn: ['确认','取消'], //按钮
					shade: true, //不显示遮罩
					icon : 0
				}, function(index){
				var ischecked = true;
				var valArr = [];
				$(".op").each(
						function(index) {
							var cell = new Object();
							cell.vaccineid = $(this).find(".ii").val();
							cell.vaccinatedate = $(this).find(".vaccinatedate").val();
							if(!$(this).find(".vaccinatedate").val()){
								layer.msg('请填写接种日期');
								ischecked = false;
							}
							cell.vaccName = $(this).find(".vaccname").html();
							cell.batch = $(this).find(".batch").val();
							cell.office = $(this).find(".office").val();
							cell.price = $(this).find(".price").val();
							cell.manufacturer = $(this).find(".manufacturer").val();
							cell.doctor = $(this).find(".doctor").val();
							cell.bodypart = $(this).find(".bodypart").val();
							valArr.push(cell);						
						});
					
					if(valArr.length > 0 && ischecked){
						console.info(valArr);
						var index = layer.load();
						$.ajax({
							url:"${ctx}/child_vaccinaterecord/childVaccinaterecord/blsave?childid=${childid}&childcode=${childcode}",
							data : {"valArr" : JSON.stringify(valArr)},
							type:"post",
							success:function(data){
							layer.close(index); 
								layer.msg('补录成功');
								setTimeout(funB, 1000);
								function funB(){
									parent.closeForm1(data);
								}
							},
							error:function(){
								
							}
							
						});
					}
				
				}, function()
				{
					layer.close();
				});
				
			});
		} );
		
		$(function(){
						
			$(".bsManage").each(function(idx){
				$(this).click(function(){
					$(".Manage").css("background","rgba(255,255,255,0)");
					$(".Manage").removeClass("active");
					$(".bsManage").css("background","rgba(255,255,255,0)");
					$(this).css("background","#fff");
					var name = $.parseJSON($(this).attr("data-list")); 
					var size=${size};
					$("#recordTbody tr td:nth-child(2)").html("");
					for(var i = 0; i < name.length; i ++){
						if(idx<size){
							$t = $("#recordTbody tr:eq(" + (idx+i) + ") td:nth-child(2)");
						}else{
							$t = $("#recordTbody tr:eq(" + (idx-i) + ") td:nth-child(2)");	
						}
						$t.html(name[i].name);
						$t.attr("data-name",name[i].name );
						$t.attr("data-id",name[i].id );
						$t.css("background", "#fff")
						$t.addClass("active");
					}
				});
			});
		});
		
		
</script>
<style type="text/css">
.date-wrap{position: relative;}
.date{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
.inputclass{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
table{-moz-user-select: none; -khtml-user-select: none; user-select: none; }
table input,select{background: rgba(255,255,255,0.65) !important; }
</style>
</head>
<body>
	<div class="ibox">
			<div class=" col-sm-4 " >
				<label style="font-size: 1.8em;" class="col-sm-12 text-center">可补录疫苗</label>
				<sys:message content="${message}" />
				<table class="table  table-bordered table-condensed">
					<thead>
						<tr class="finished">
							<th style="color: black;width: 145px;">疫苗大类名称<br><span style="font-weight: normal; font-size: 12px;">(点击选择小类)</span></th>
							<th style="color: black;">疫苗小类名称<br><span style="font-weight: normal; font-size: 12px;">(双击添加疫苗)</span></th>
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${vacclist}" var="bsManageVaccine">
								<tr style="background: rgba(245, 245, 246, 0.23);" >
								<td class="bsManage" data-list='${bsManageVaccine.json }'>${bsManageVaccine.str }</td>
								<td class="Manage" data-name data-id></td>
								</tr>	
						</c:forEach>
						
					</tbody>
				</table>
			</div>
			<div class=" col-sm-8 " >
				<label style="font-size: 1.8em;" class="col-sm-12 text-center">已选择疫苗</label>
				<sys:message content="${message}" />
				<table class="table  table-bordered table-condensed">
					<thead>
						<tr class="finished">
							<th style="color: black;" class="name">疫苗名称</th>
							<th style="color: black;">接种日期</th>
							<th style="color: black;">疫苗批号</th>
							<th style="color: black;">接种地点</th>
							<th style="color: black;">接种价格</th>
							<th style="color: black;">生产企业</th>
							<th style="color: black;">接种医生</th>
							<th style="color: black;">接种部位</th>
							<th style="color: black;">操作</th>
						</tr>
					</thead>
					<tbody id="recordTbody1">
					</tbody>
				</table>
				<button type="button" class="btn btn-lg btn-primary" id="btnsave" >保存</button>
			</div>
		</div>
</body>
</html>