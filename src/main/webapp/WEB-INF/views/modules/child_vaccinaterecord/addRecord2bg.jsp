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
			getAllRows();			 

			$(".Manage").click(function(){
				if($(this).hasClass("history")){
					return ;
				}
				var str=$(this).text();
				stBuf=str;
				var index = $(this).index();
				if(index>1){
					var p_id=$(this).parent().find("input").eq(0).val();//获取父节点id
					var data=getParentID(p_id);//获取 相同父ID的所有行
					var result=false;
					for(var i=0;i<data.length;i++){
						 if($(data[i]).find("td").eq(index-1).text()){//如果其中一个有值  可以继续；
							 result=true ;
						 } 
					}
					if(!result){
						return ;
					}
					
				}
				if(!str){//如果还未曾输入
					var p_id=$(this).parent().find("input").eq(0).val();//获取父节点id
					var data=getParentID(p_id);//获取 相同父ID的所有行
					for(var i=0;i<data.length;i++){
						 if($(data[i]).find("td").eq(index).text()){
							 return ;
						 } 
					}
				}
				$(this).empty();
				$(this).append("<input type='text' class='add_str' onkeyup='keyUp(this)' onblur='getValue(this)' value='"+str+"' maxlength='10' style='width:100px;'>");
				$(this).children("input").focus();
				
			});
			
			$("#btnsave").click(function(){
				var record_addr=$("#record_addr").val();
				var radio=$(".record_addr:checked").val();
				if(radio==2){
					if(!record_addr){
						alert("接种地点不能为空！");
						return ;
					}
				}
				layer.confirm("确定要补录上述疫苗吗？", {
					btn: ['确认','取消'], //按钮
					shade: true, //不显示遮罩
					icon : 0
				}, 
				function(index){
					var data=[];
					var tab =document.getElementById("table");
				    var rowIndex = tab.rows.length;
				    for(var i=1;i<rowIndex;i++){
			    		for(var k=1;k<6;k++){
				    		if(!$(tab.rows[i]).find("td").eq(k).hasClass("history")){
				    			var recode=new Object();
						    		recode.gNum=$(tab.rows[i]).find("td").eq(0).find("input").eq(0).val();
						    		recode.id=$(tab.rows[i]).find("td").eq(0).find("input").eq(1).val();
						    		recode.name=$(tab.rows[i]).find("td").eq(0).text();
				    			if($.trim($(tab.rows[i]).find("td").eq(k).text())){
					    			recode.dosage=k;
					    			recode.date=$.trim($(tab.rows[i]).find("td").eq(k).text());
					    			data.push(recode);
				    			}
				    		}
			    		}
				    }
 					if(data.length > 0){
					console.info(data);
					var index = layer.load();
					$.ajax({
						url:"${ctx}/child_vaccinaterecord/childVaccinaterecord/saveRecord?childid=${childid}&childcode=${childcode}",
						data : {"valArr" : JSON.stringify(data),
							"readdr":record_addr,
							"office":"${n}"},
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
					layer.close(index);
				}else{
					layer.msg("未添加记录",{"icon":1, "time":1000});
					setTimeout(function() {
						parent.layer.closeAll();
					}, 1000);
				}}, function()
				{
					layer.close();
				});
				
			});
			
			$("#btncancel").click(function(){
				parent.layer.closeAll();
			});
			$(".record_addr").click(function(){
				var check=$(".record_addr:checked").val();
				if(check==1){
					$(".record_addr_info").css("display","none");
					$("#record_addr").val("");
				}
				if(check==2){
					$(".record_addr_info").css("display","inline");
				}
				
			});
			
			
			
			initRecode();
		} );
		
		function initRecode(){//初始化加载后台数据
			var chlid_recode=${vacclist};
			var tab =document.getElementById("table");
		    var rowIndex = tab.rows.length;
			for(var i=0;i<chlid_recode.length;i++){
				for(var k=1;k<rowIndex;k++){
					if($.trim(chlid_recode[i].vaccName)==$.trim($(tab.rows[k]).find("td").eq(0).text())){
						if(chlid_recode[i].dosage==1){
							$(tab.rows[k]).find("td").eq(1).text(formatDate(chlid_recode[i].vaccinatedate));
							$(tab.rows[k]).find("td").eq(1).addClass("history");
						}
						if(chlid_recode[i].dosage==2){
							$(tab.rows[k]).find("td").eq(2).text(formatDate(chlid_recode[i].vaccinatedate));
							$(tab.rows[k]).find("td").eq(2).addClass("history");
						}
						if(chlid_recode[i].dosage==3){
							$(tab.rows[k]).find("td").eq(3).text(formatDate(chlid_recode[i].vaccinatedate));
							$(tab.rows[k]).find("td").eq(3).addClass("history");
						}
						if(chlid_recode[i].dosage==4){
							$(tab.rows[k]).find("td").eq(4).text(formatDate(chlid_recode[i].vaccinatedate));
							$(tab.rows[k]).find("td").eq(4).addClass("history");
						}
						if(chlid_recode[i].dosage==5){
							$(tab.rows[k]).find("td").eq(5).text(formatDate(chlid_recode[i].vaccinatedate));
							$(tab.rows[k]).find("td").eq(5).addClass("history");
						}
					}
				}
				
			}
			
		}
		function formatDate(date) {  
			var d = new Date(date),
			month = '' + (d.getMonth() + 1),
			day = '' + d.getDate(),
			year = d.getFullYear();
			if (month.length < 2) month = '0' + month;
			if (day.length < 2) day = '0' + day;
			return [year, month, day].join('-');
		}
		
        function getAllRows(){
        	 var tab =document.getElementById("table");
		     var rowIndex = tab.rows.length;
		     var data=[];//大类
		     for(var i=1;i<rowIndex-1;i++){
				 //获取所有大类不同的唯一对象
				 if($(tab.rows[i]).find("input").eq(0).val()!=$(tab.rows[i-1]).find("input").eq(0).val()){
				 data.push(tab.rows[i]);
				 }
		     }
		     for(var i=0;i<data.length;i++){//遍历所有唯一大类
		    	 if((i+1)%2==0){
		    		 var trs=getParentID($(data[i]).find("input").eq(0).val());
		    		 for(var k=0;k<trs.length;k++){
		    			 $(trs[k]).addClass("tr_color");
		    		 }
		    		 
		    	 }
		    	 
		     }
			
		}
         
        
        
		var stBuf="";
		function getParentID(id){
			 var tab =document.getElementById("table");
		     var rowIndex = tab.rows.length;
		     var data=[];
		     for(var i=1;i<rowIndex;i++){
		    	 if($(tab.rows[i]).find("input").eq(0).val()==id){
		    		 data.push(tab.rows[i]);
		    	 }
		     }
		     return data;
		}
		
		function keyUp(obj){//键盘抬起事件
			 var focus=document.activeElement; 
			 var tab =document.getElementById("table");
			 if(!tab.contains(focus)) return; 
			 var col=parseInt($(obj).parents('td').index())+parseInt(1);  //列
			 var row =parseInt($(obj).parents('tr').index()+1);  //行
			 var str=$(obj).text();
			 var event=window.event;
			 var key=event.keyCode; 
			 //alert(key);
			 switch(key) 
			 { 
			  case 37: {  //左
			    if((col-2)>0){
					str=$(obj).text();
					if($(tab.rows[row].cells[col-2]).text()){
						str=$(tab.rows[row].cells[col-2]).text();
						$(tab.rows[row].cells[col-2]).html("");
					}
					$(tab.rows[row].cells[col-2]).append("<input type='text' class='add_str' onkeyup='keyUp(this)' onblur='getValue(this)' value='"+str+"' maxlength='10' style='width:100px;'>");
					$(tab.rows[row].cells[col-2]).children("input").focus();
					var t=$(tab.rows[row].cells[col-2]).children("input").val(); 
					$(tab.rows[row].cells[col-2]).children("input").val("").focus().val(t); 
				}
			    break; 
			   }
			  case 38: {  //上
				if((row-1)>0){
					 str=$(obj).text();
					if($(tab.rows[row-1].cells[col-1]).text()){
						str=$(tab.rows[row-1].cells[col-1]).text();
						$(tab.rows[row-1].cells[col-1]).html("");
					}
					$(tab.rows[row-1].cells[col-1]).append("<input type='text' class='add_str' onkeyup='keyUp(this)' onblur='getValue(this)' value='"+str+"' maxlength='10' style='width:100px;'>");
					$(tab.rows[row-1].cells[col-1]).children("input").focus();
					var t=$(tab.rows[row-1].cells[col-1]).children("input").val(); 
					$(tab.rows[row-1].cells[col-1]).children("input").val("").focus().val(t); 
				}
			    break; 
			   }
			  case 13:
			  case 39: {   //右
					str=$(obj).text();
					if($(tab.rows[row].cells[col]).text()){
						str=$(tab.rows[row].cells[col]).text();
						$(tab.rows[row].cells[col]).html("");
					}
					$(tab.rows[row].cells[col]).append("<input type='text' class='add_str' onkeyup='keyUp(this)' onblur='getValue(this)' value='"+str+"' maxlength='10' style='width:100px;'>");
					$(tab.rows[row].cells[col]).children("input").focus();
					var t=$(tab.rows[row].cells[col]).children("input").val(); 
					$(tab.rows[row].cells[col]).children("input").val("").focus().val(t); 
			    break; 
			   } 
			  case 40: {  //下
				str=$(obj).text();
				if($(tab.rows[row+1].cells[col-1]).text()){
					str=$(tab.rows[row+1].cells[col-1]).text();
					$(tab.rows[row+1].cells[col-1]).html("");
				}
				$(tab.rows[row+1].cells[col-1]).append("<input type='text' class='add_str' onkeyup='keyUp(this)' onblur='getValue(this)' value='"+str+"' maxlength='10' style='width:100px;'>");
				$(tab.rows[row+1].cells[col-1]).children("input").focus();
				var t=$(tab.rows[row+1].cells[col-1]).children("input").val(); 
				$(tab.rows[row+1].cells[col-1]).children("input").val("").focus().val(t); 
			    break; 
			   }
			  }

			 if($(obj).val().length==4 &&key!=8){
				$(obj).val($(obj).val()+'-');
			 }
			 if($(obj).val().length==7&&key!=8){
				$(obj).val($(obj).val()+'-');
			 }
			if($(obj).val().length==10 && key!=37 && key!=38 && key!=39 && key!=40 &&key!=13 ){
				$(tab.rows[row].cells[col]).append("<input type='text' class='add_str' onkeyup='keyUp(this)' onblur='getValue(this)' value='"+str+"' maxlength='10' style='width:100px;'>");
				$(tab.rows[row].cells[col]).children("input").focus();
		     }
		}
		
		function getValue(obj){
			var str=$(obj).val();
			var result=Date.parse(str);
			if(str==""||str==null){
				$(obj).parent().html("");
			}else{
				if(result){
					var tr=$(obj).parent().parent();
					var index=$(obj).parent().index();//获取当前列
					if(index>1){
						var p_id=$(obj).parent().parent().find("input").eq(0).val();
						var data=getParentID(p_id);//获取 相同父ID的所有行
						var result="";
						var nextResult="";
						for(var i=0;i<data.length;i++){
							 if($(data[i]).find("td").eq(index-1).text()){//如果其中一个有值  可以继续；
								 result=$(data[i]).find("td").eq(index-1).text();
							 } 
							 if($(data[i]).find("td").eq(index+1).text()){//如果其中一个有值  可以继续；
								 nextResult=$(data[i]).find("td").eq(index+1).text();
							 } 
							 
						}
						if(result!="" && nextResult!=""){
							if(Date.parse(nextResult)){//如果有下一针的时间
								if(compareDate(nextResult,str)){
									 alert("日期不能大于下一针111");
									 $(obj).parent().html(stBuf);
									 return ;
								}else{
									var days=DateDiff(str, nextResult);
									if(days<28){
										 alert("距下一针日期不能小于30天1111");
										 $(obj).parent().html(stBuf);
										 return ;
									 }
								}
							}
							if(compareDate(str,result)){
								 alert("日期不能小于上一针2222");
								 $(obj).parent().html(stBuf);
								 return ;
							}else{
								var days=DateDiff(str, result);
								if(days>28){
									 $(obj).parent().html(str);
									 return ;
								 }else{
									 alert("距上一针日期不能小于30天2222");
									 $(obj).parent().html(stBuf);
									 return ;
								 }
								
							}
						} 
					}
					if(index=1){
						var p_id=$(obj).parent().parent().find("input").eq(0).val();
						var data=getParentID(p_id);//获取 相同父ID的所有行
						var nextResult="";
						for(var i=0;i<data.length;i++){
							 if($(data[i]).find("td").eq(index+1).text()){//如果其中一个有值  可以继续；
								 nextResult=$(data[i]).find("td").eq(index+1).text();
							 } 
						}
						if(nextResult!="" &&str!=""){
							if(Date.parse(nextResult)){//如果有下一针的时间
								if(compareDate(nextResult,str)){
								//	 alert("日期不能大于下一针33333");
								//	 $(obj).parent().html(stBuf);
								}else{
									var days=DateDiff(str, nextResult);
									if(days>28){
										 $(obj).parent().html(str);
									 }else{
										// alert("距下一针日期不能小于30天33333");
										// $(obj).parent().html(stBuf);
									 }
									
								}
							} 
						}
					}
					 $(obj).parent().html(str);
					
				}else{
					alert("日期出错啦！ 该日期无效！");
					$(obj).parent().html(stBuf);
				}
				
			}
			
			
		}
		
		
		//比较两时间相隔多少天（绝对值）
		function DateDiff(sDate1, sDate2) {  //sDate1和sDate2是yyyy-MM-dd格式
		    var aDate, oDate1, oDate2, iDays;
		    aDate = sDate1.split("-");
		    oDate1 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);  //转换为yyyy-MM-dd格式
		    aDate = sDate2.split("-");
		    oDate2 = new Date(aDate[1] + '-' + aDate[2] + '-' + aDate[0]);
		    iDays = parseInt(Math.abs(oDate1 - oDate2) / 1000 / 60 / 60 / 24); //把相差的毫秒数转换为天数
		    return iDays;  //返回相差天数
		}
		//比较两时间的大小（前后）
		 function compareDate(start, end) {
		    var arr = start.split("-");
		    var starttime = new Date(arr[0], arr[1], arr[2]);
		    var starttimes = starttime.getTime();
		    var arrs = end.split("-");
		    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
		    var lktimes = lktime.getTime();
		    if (starttimes >= lktimes) {
		        return false;
		    }
		    else
		        return true;

			}
		
</script>
<style type="text/css">
.date-wrap{position: relative;}
.date{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
.inputclass{background: none;border: none;position: absolute;width: 100%;height: 100%;top:0;left:0;text-align: center;}
table{-moz-user-select: none; -khtml-user-select: none; user-select: none; }
table input,select{background: rgba(255,255,255,0.65) !important; }
/* .tr_color{background-color: rgba(245, 245, 246, 0.5);} */
.tr_color{background-color: rgba(245, 245, 211, 0.23);} 
.history{background-color: #AEEEEE}
</style>
</head>
<body>
	<div class="ibox">
			<div class=" col-sm-12 " >
				<label style="font-size: 1.8em;" class="col-sm-12 text-center">可补录疫苗</label>
				<sys:message content="${message}" />
				<table class="table  table-bordered table-condensed"  id="table">
					<thead>
						<tr class="finished">
							<th style="color: black;width: 250px;">疫苗名称</th>
							<th style="color: black;width: 120px;">第一针</th>
							<th style="color: black;width: 120px;">第二针</th>
							<th style="color: black;width: 120px;">第三针</th>
							<th style="color: black;width: 120px;">第四针</th>
							<th style="color: black;width: 120px;">第五针</th>
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${list}" var="bsManageVaccine" >
								<tr>
									<td class="bsManage" data-list=''>${bsManageVaccine.name }
										<input class="bigcode" type="hidden"  value='${bsManageVaccine.gNum }'/>
										<input class="vaccineid" type="hidden"  value='${bsManageVaccine.id }'/>
									</td>
									<td class="Manage" data-name data-id></td>
									<td class="Manage" data-name data-id></td>
									<td class="Manage" data-name data-id></td>
									<td class="Manage" data-name data-id></td>
									<td class="Manage" data-name data-id></td>
								</tr>	
						</c:forEach>
						
					</tbody>
				</table>
			</div>
				<div class="col-sm-8" >
					<div  style=" float: left;width: 100%;">
						<label><input type="radio" class="record_addr" name="record_choose" value="1" style="text-align: left;">补录本地接种信息</label>
					</div>
					<div  style=" float: left;width: 100%;">
						<label>
							<input type="radio" class="record_addr" name="record_choose" checked value="2">补录外地接种信息
							<span class="record_addr_info">&nbsp;&nbsp;&nbsp;接种地点：<input type="text" id="record_addr" name="record_addr" value=""></span>
						</label>
					</div>
				</div>
				<div class="col-sm-4" style="text-align: center;" >
					<button type="button" class="btn btn-lg btn-primary" id="btnsave"  >保存</button>
					<button type="button" class="btn btn-lg btn-primary" id="btncancel" >取消</button>
				</div>
		</div>
</body>
</html>