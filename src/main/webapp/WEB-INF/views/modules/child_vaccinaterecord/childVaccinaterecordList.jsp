<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接种登记</title>
<style type="text/css">
.scroll {height: 500px; overflow: auto; } .finished {color: #999; } .fix{position: relative; right:10px; bottom:15px; } .red{color:red; } .black{color:black; } .btn{padding: 5px;} .btn-xs{padding: 1px 5px;} .text-circle{display: block; margin: 0 auto; width: 15px; height: 15px; border: 1px #000 solid; border-radius: 15px; font-size: .8em; text-align: center; } .ib{display: block; float: left; } .reserve{background: #DEBBD1; } .positionAbs{position: absolute; right: 0; }
.bsnone td{
	background: rgba(237, 85, 101, 0.2);
	color: red; 
}

.layui-layer-btn{
	*margin-top: -35px;
	margin-top: -35px\0;
}

.layui-layer-btn a {
	font-size: 20px;
	padding: 2px 5px 2px 5px;
}
		
.layui-layer-content{
	font-size: 24px !important;
}
</style>

	
		
		
	
	
<meta name="decorator" content="default" />

<script src="${ctxStatic}/js/LodopFuncs.js"></script>
<script src='${ctxStatic}/js/os.js?v=20180202'></script>
<script src="${ctxStatic}/js/printtips.js?v=20180224"></script>
<script type="text/javascript" src="${ctxStatic}/js/child/recordList.js?v=5"> </script>

<script type="text/javascript">
	var lastLiveDays = '${lastLiveDays}';
	var childcode = '${childBaseinfo.childcode}';
	var receiptType = '${receiptType}';
	var localcode = '${childBaseinfo.localCode}';

	//计算两个日期间隔
	function getDays(strDateStart,strDateEnd){
	   var strSeparator = "-"; //日期分隔符
	   var oDate1;
	   var oDate2;
	   var iDays;
	   oDate1= strDateStart.split(strSeparator);
	   oDate2= strDateEnd.split(strSeparator);
	   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
	   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
	   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24);//把相差的毫秒数转换为天数 
	   return iDays ;
	}
	
	function getMouthAge(date1,date2){
	   // 拆分年月日
		var mouth =  date2.getMonth() - date1.getMonth();
		var year = date2.getYear() - date1.getYear();
		if(date2.getDate() > date1.getDate()){
			mouth --;
		}
		mouth = mouth < 0? 0:mouth;
		return (mouth + year*12) + "月龄&nbsp;";
	}

	/* 档案添加 */
	function addBaseInfo() {
		openWindows(ctx + "/child_baseinfo/childBaseinfo", "档案添加", "", "");
	}
	
	/* 修改档案信息 */
	function altBaseInfo() {
		 openWindows(ctx + "/child_baseinfo/childBaseinfo?id=${childBaseinfo.id}","档案修改", "", "");
	}

	/* 修改用户首选项后 */
	function finishUserSetting(){
		if(preference.quickOption == 1){
			$(".btn-quene").html("登记");
			$(".btn-quene-anyway").html("自定义登记");
		}else{
			$(".btn-quene").html("排号");
			$(".btn-quene-anyway").html("自定义排号");
		}
	}

	$(document).ready(function() {
		//根据设置更新排号按钮
		finishUserSetting();
		 if('${childBaseinfo.situation}'!=''){
			 if('${childBaseinfo.situation}'!='5'){
				 if('${ocode}'=='${zdbm}'){
					 $("#addOfflineRecord").on('click', function(e) {
							var items = [
								{ title: '在册变更', icon: '', fn: function(){openWindows(ctx + "/child_baseinfo/childBaseinfo/changeSitu?id=${childBaseinfo.id}","在册变更", "400", "400") }},
								{ title: '单剂补录', icon: '', fn: function(){openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/addRecordS?childid=${childBaseinfo.id}","补录接种记录", "400", "500") }},
								{ title: '批量补录', icon: '', fn: function(){openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/addRecord?childid=${childBaseinfo.id}&childcode=${childBaseinfo.childcode}","补录接种记录", "800", "") }},
								{ title: '个案更新', icon: '', fn: function(){openWindows(ctx + "/child_baseinfo/childBaseinfo/compareForm?id=${childBaseinfo.id}","个案对比", "", "700");}},
							]
							basicContext.show(items, e)
						});
				 }else{
					 if(confirm("该儿童非本站点，请先更新数据！")){
						 openWindows(ctx + "/child_baseinfo/childBaseinfo/compareForm?id=${childBaseinfo.id}","个案对比", "", "700");
					 }
				 }
			 }else{
				layer.msg('该儿童个案是删除状态');
			 }
		 }
	})
	 
	 
	/* 修改接种记录 */
	function altRecord(a,b) {
		if('${childBaseinfo.situation}'!='5'){
			openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord?id="+a+"&pin="+b+"&childcode=${childBaseinfo.childcode}", "修改接种记录", "", "");	
		}else{
			layer.msg('儿童个案已被删除,无法进行修改');
		}
	}
	
	/* 排号  模型大类，针次，nid，预约id */
	function preQueue(a,b,c,rid) {
		if('${childBaseinfo.situation}'!='5'){
		
			$.ajax({
				type : "POST",
				url : "${ctx}/child_vaccinaterecord/childVaccinaterecord/date?childid=${childBaseinfo.id}",
				success : function(data) {
					if(data=="4"){
						openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/add?group="+ a+"&dosage="+b +"&nid="+c+ "&childid=${childBaseinfo.id}&rid="+rid, "登记打印排号", "", "");
						return;
					}
					if(data){
						if(getDays(data,SimpleDateFormat(new Date(),"yyyy-MM-dd"))>0){
							openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/add?group="+ a+"&dosage="+b +"&nid="+c+ "&childid=${childBaseinfo.id}&rid="+rid, "登记打印排号", "", "");
						}else{
							layer.msg('上一次接种时间距今'+getDays(data,SimpleDateFormat(new Date(),"yyyy-MM-dd")) +'天,无法进行排号');
						}
					}else{
						layer.confirm("你今天已经接种三针了，确定还要排号吗？", {
							btn: ['确认','取消'], //按钮
							shade: true, //不显示遮罩
							icon : 0
						}, function(index){
							layer.close(index);
							openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/add?group="+ a+"&dosage="+b +"&nid="+c+ "&childid=${childBaseinfo.id}&rid="+rid, "登记打印排号", "", "");
						}, function()
						{
							layer.close();
						});
					}
				}
			});
		}else{
			layer.msg('儿童个案已被删除,无法进行排号');
		}
	}
	
	/* 打印不干胶 */
	function printBaseInfo(id) {
		window.open(ctx + "/child_baseinfo/childBaseinfo/print?id="+id,'_blank');
	}
	
	/* 打印入托证明 */
	function printProve(id) {
		window.open(ctx + "/child_baseinfo/childBaseinfo/prove?id="+id,'_blank');
	}
	
	/* 自助建档 */
	function selfAddBaseInfo() {
		var code= $("#inputid").val();
		if($("#inputid").val() == ''){
			return false;
		}
		var re = /^[0-9]+.?[0-9]*$/;
		if(!re.test(code)){
			layer.msg('输入格式错误，请输入数字');
		}
		if(code.length==4){
			 openWindows(ctx + "/child_baseinfo/childBaseinfo/code?code="+code,"自助建档", "", "");
		}
	}
	
	//打印排号小票方法
	function printtt(data){
		return printtips(data);
	}
	
	/* 再排 */
	function stmpAgen(id) {
		if('${childBaseinfo.situation}'!='5'){
			 openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/again?id="+id+"&childcode=${childBaseinfo.childcode}","再次排号 ", "", "");
		}else{
			layer.msg('儿童个案已被删除,无法进行再排');
		}
	}
	function j(id) {
		openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/check?id="+id,"查看 ", "", "");
	}
	
	/* 删除接种记录 */
	function delRecord(id,code) {
		if('${childBaseinfo.situation}'!='5'){
			$.ajax({
				type : "POST",
				url : "${ctx}/child_vaccinaterecord/childVaccinaterecord/del?id="+id+"&code="+code,
				success : function(data) {
		               success(data);
		               setTimeout(funB, 1000);
		              
				}
			});
		}else{
			layer.msg('儿童个案已被删除,无法进行操作');
		}
	}
	
	/* 删除个案 */
	function delinfo(id){
		if('${childBaseinfo.situation}'!='5'){
			if(confirm("确认删除该儿童档案?")){
				$.ajax({
					type : "POST",
					url : "${ctx}/child_baseinfo/childBaseinfo/del?id="+id,
					success : function(data) {
							 layer.msg(data,{time:3000});	  	   
							 setTimeout(funB, 1800);      
					}
				});
			}
		}else{
			layer.msg('不可重复删除');
		}
	}
	
	/* 恢复个案 */
	function restoreinfo(id){
		if('${childBaseinfo.situation}'=='5'){
			if(confirm("确认恢复该儿童档案?")){
				$.ajax({
					type : "POST",
					url : "${ctx}/child_baseinfo/childBaseinfo/restore?id="+id,
					success : function(data) {
							if(data=='200'){
								layer.msg("恢复个案成功，请仔细核对接种信息", {time : 3000});
								setTimeout(funB, 1800);
							}else {
								layer.msg("恢复失败", {time : 3000});
							}
							     
					}
				});
			}
		}
	}
	
	function funB() {
		window.location.reload();//刷新当前页面.
	}
	
	//根据时间显示最后一次接种时间和今日接种记录
	$(function(){
		var lastTime;
		var lastTr = new Array();
		$(".recDate").each(function(i,t){
			if($(this).html()){
				if(!lastTime){
					lastTime = Date.parse($(this).html());
				}
				var d = Date.parse($(this).html());
				if(d > lastTime){
					lastTr = new Array();
					lastTime = d;
					lastTr.push($(this).parent());
				}
				if(d == lastTime){
					lastTr.push($(this).parent());
				}
				if(SimpleDateFormat(new Date(),"yyyy-MM-dd") == $(this).html()){
					$(this).parent().css("color","red");
				}
			}
		});
		if(lastTr){
			$.each(lastTr, function(i,t){
				t.css("background", "rgb(255, 213, 177)");
				t.css("font-weight", "bold");
			});
		}
	})
	
	//键盘事件
	$(document).keydown(function(event){
		// 先获取输入框元素
	    var input = document.getElementById('inputid');
	    // 如果hasFocus为true表示input元素获得焦点，否则没有获得焦点
	    var hasFocus = document.hasFocus() && document.activeElement === input;
		if(hasFocus == false &&((event.keyCode <= 57 && event.keyCode >= 48) || (event.keyCode <= 105 && event.keyCode >= 96))){
			 document.getElementById('childid').focus(); 
		}
　　});
	
	/* 异地迁入 */
	function ingoing(){
		//layer.closeAll();
		openWindows(ctx + "/child_baseinfo/childBaseinfo/ingoingForm","异地迁入", "", "");
	}	
		
	/* 取消预约 */
	function cancelRemind(thi){
		$.ajax({
			url:ctx + "/remind/vacChildRemind/cancelRemind",
			data:{"id":$(thi).attr("data-id")},
			success:function(data){
				if(data.code == 200){
					layer.msg("取消成功",{"icon":1});
					$(thi).parent().parent().remove();
				}else{
					layer.msg("取消失败",{"icon":2});	
				}
			},
			error:function(){
				layer.msg("取消失败",{"icon":2});	
			}
		});
	}
	
		$(function(){
			loadChildRecord();
		})
		
	function loadChildRecord(){
		/* 加载预约记录 */
		if(childcode){
			$.ajax({
				url:ctx + "/remind/vacChildRemind/findListApi",
				data:{"childcode":childcode, "status":'0'}, 
				success:function(remindDate){
					if(remindDate.length){
						var ul = "<label>预约记录：</label><table class='table table-bordered'><thead><tr><th>疫苗</th><th>预约日期</th><th>预约时段</th><th>操作</th></tr></thead><tbody>";
						for(i in remindDate){
							ul += '<tr>'+
									'<td>' + remindDate[i].remindVacc + '</td>'+
									'<td class="text-center">' + remindDate[i].remindDate + '</td>'+
									'<td class="text-center">' + (remindDate[i].selectTime?remindDate[i].selectTime:'') + '</td>'+
									'<td class="text-center"><button class="btn btn-xs btn-success mr10" type="button" data-id="' + remindDate[i].id + '" onclick="cancelRemind(this)">取消</button></td>'+
									'</tr>';
						}
						ul += "</tbody></table>";
						$(".remindList").html(ul);
					}
				}
			});
		}
	}
	
	function openRemind(){
		if(!childcode){
			toastrMsg("未选择儿童信息","error");
			return false;
		}
		if('${childBaseinfo.situation}'!='5'){
			openWindows(ctx + "/remind/vacChildRemind/remindForm?childcode=" + childcode, "预约下次接种时间", "", "");
		}else{
			layer.msg('儿童个案已被删除,无法进行预约');
		}
	}
	
	function delReserve(id){
		$.ajax({
			url:ctx + "/reserve/bsReserve/del?id="+id,
			type:"DELETE",
			success:function(data){
				if(data.code == 200){
					toastrMsg(data.msg,"success");
					$(".reserve[data-id=" + id + "]").remove();
				}else{
					toastrMsg(data.msg,"error");
				}
			},
			error:function(){
				toastrMsg("删除失败","error");
			}
		});
	}
	
	function openAddAnyWay(){
		if('${childBaseinfo.situation}'!='5'){
			openWindows(ctx + "/child_vaccinaterecord/childVaccinaterecord/addAnyWay?childid=${childBaseinfo.id}", "自定义排号", "", "");
		}else{
			layer.msg('儿童个案已被删除,无法进行排号');
		}
	}
	
	function alertSetting(){
		openWindows('${ctx}/sys/user/userSetting?from=djt', '设置用户首选项',600,400);
	}
	
	function getMouthAge(dd1,dd2){
		var y1 = dd1.getFullYear();
		var m1 = dd1.getMonth()+1;
		var d1 = dd1.getDate();
		var y2 = dd2.getFullYear();
		var m2 = dd2.getMonth()+1;
		var d2 = dd2.getDate();
		var age = m2 - m1;
		var yy = y2-y1;
		if (d2 < d1) {
			age--;
		}
		if(age < 0){
			age+=12;
			yy --;
		}
		var agestr = "";
		if(yy > 0){
			agestr += (yy) + "岁";
		}
		agestr += age + "月龄";
		
		return agestr;
	}
	
	//显示接种月龄
	$(function(){
		
		$(".recDate").mouseover(function(){
			var dd1 = new Date('${childBaseinfo.birthday}');
			if($(this).html()){
				var dd2 =strToDate($(this).html());
				layer.tips("<span style='color:red;font-size:14px'>"+getMouthAge(dd1,dd2)+"接种</span>", this,{ tips: [2, '#FFFFFF'],time: 0});
			}
		}).mouseout(function()    
		 {
			layer.closeAll('tips'); 
		 });
		$("#childid").keydown(function(e){
			if(e.keyCode == 13){
				submitYouFrom();
			}
		});
	})
</script>
</head>
<body>  
	<div class="ibox" style="background: rgba(0,0,0,0);">
		<div class=" col-sm-12">
			<input type="hidden" name="chid1"> <input type="hidden"
				name="chid2">
			<form:form id="searchForm" modelAttribute="childVaccinaterecord"
				action="${ctx}/child_vaccinaterecord/childVaccinaterecord1/list1" method="post" class="form-inline">
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">编码&nbsp;/&nbsp;生日：</span> 
							 <input id="childid" name="childid" maxlength="20" class="form-control" style="width: 200px"
							 	oninput="submitYouFrom()" onpropertychange="submitYouFrom()" value="${childid}" placeholder="请输入儿童编码或生日" />
							<script language="JavaScript"> 
									document.getElementById('childid').value="";
									document.getElementById('childid').focus();  
									<!--设置id为name的元素得到焦点--> 
								</script>
					</div>
				</div>
				<a href="javascript:addBaseInfo()" class="btn btn-lg btn-primary">档案新增</a>
				<a href="javascript:ingoing()" class="btn btn-lg btn-primary" style="margin-left: 10px">异地迁入</a>
				<c:if test="${childBaseinfo.id !=null}">
					<a href="javascript:altBaseInfo()" class="btn btn-lg btn-danger" style="margin-left: 10px">档案修改</a>
					<button class="btn btn-lg btn-primary" type="button" onclick="openRemind()" style="margin-left: 10px">预约登记</button>
				</c:if>
				<c:if test="${empty from}">
					<div class="row pull-right">
						<div class="btn-group">
							<a type="button" class="btn btn-success w100" onclick="javascript:void(0);" >登记台</a>
						    <a type="button" class="btn btn-default" onclick="javascript:loading('正在跳转...');" href="${ctx}/inoculate/quene/inoculateIndex">儿童接种台</a>
						    <a type="button" class="btn btn-default" onclick="javascript:loading('正在跳转...');" href="${ctx}/rabiesvaccine/bsRabiesCheckin">狂犬病免疫</a>
						    <a type="button" class="btn btn-default" onclick="javascript:loading('正在跳转...');" href="${ctx}/hepatitis/bsHepatitisBcheckin">成人免疫</a>
						    <a type="button" class="btn btn-default w100" onclick="javascript:loading('正在跳转...');" href="${ctx}">首页</a>
						</div>
					</div>
				</c:if>
				
				
				<br>
				<div class="form-group" >
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">&nbsp;&nbsp;&nbsp;自助建档：</span> 
						<input id="inputid" type="text" value=''  name="input" maxlength="20" class="form-control" oninput="selfAddBaseInfo()" style="width: 200px" placeholder="请输入4位建档编码"  /> 
						
					</div>
				</div>
			</form:form>
			<div style="margin: 0 auto; width: 200px;"></div>
		</div>
		<div class="row" style="background: rgba(0,0,0,0);">
			<div class=" col-sm-3" style="padding: 5px">
			<label style="font-size: 1.8em;" class="col-sm-12 text-center">儿童基本信息</label>
			<sys:message content="${message}" />
				<table 
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<td style="width: 100px" class="text-right">儿童编码</td>
							<td>${childBaseinfo.childcode}</td>
						</tr>
						<tr>
							<td class="text-right">儿童身份证</td>
							<td>${childBaseinfo.cardcode}</td>
						</tr>
						<tr>
							<td class="text-right">出生证号</td>
							<td>${childBaseinfo.birthcode}</td>
						</tr>
						<tr>
							<td class="text-right">儿童姓名</td>
							<td>${childBaseinfo.childname}<c:if test="${not empty childBaseinfo.mouage }"><span style="color: red; font-weight: bold;">（${childBaseinfo.mouage 	}）</span></c:if></td>
						</tr>
						<tr>
							<td class="text-right">性&emsp;&emsp;别</td>
							<td>${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}</td>
						</tr>
						<tr>
							<td class="text-right">出生日期</td>
							<td><fmt:formatDate value="${childBaseinfo.birthday}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
						<tr>
							<td class="text-right">出生医院</td>
							<td>${childBaseinfo.birthhostipal}</td>
						</tr>
						<tr>
							<td class="text-right">出生体重(g)</td>
							<td>${childBaseinfo.birthweight}</td>
						</tr>
						<tr>
							<td class="text-right">区域划分</td>
							<td>${childBaseinfo.area}</td>
						</tr>
						<tr>
							<td class="text-right">母亲姓名</td>
							<td>${childBaseinfo.guardianname}</td>
						</tr>
						<tr>
							<td class="text-right">手机号码</td>
							<td>${childBaseinfo.guardianmobile}</td>
						</tr>
						<tr>
							<td class="text-right">母亲身份证</td>
							<td>${childBaseinfo.guardianidentificationnumber}</td>
						</tr>
						<%-- <tr>
							<td class="text-right">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系</td>
							<td>${fns:getDictLabel(childBaseinfo.guardianrelation, 'relation', '')}</td>
						</tr> --%>
						<tr>
							<td class="text-right">家庭住址</td>
							<td>${childBaseinfo.homeaddress}</td>
						</tr>
						<tr>
							<td class="text-right">户籍地址</td>
							<td>${childBaseinfo.registryaddress}</td>
						</tr>
						<%-- <tr>
							<td class="text-right">异常反应</td>
							<td>${fns:getDictLabel(childBaseinfo.paradoxicalreaction, 'FN', '')}
							</td>
						</tr> --%>
						<tr>
							<td class="text-right">接种单位</td>
							<td>
								<c:if test="${childBaseinfo.officeinfo=='淮海路接种门诊'}">${childBaseinfo.officeinfo}</c:if>
								<c:if test="${childBaseinfo.officeinfo!='淮海路接种门诊'}">
									<span style="color: red; font-weight: bold;">${childBaseinfo.officeinfo}</span>
								</c:if>
							</td>
						</tr>
						<tr>
							<td class="text-right">在册情况</td>
							<td>
								<c:if test="${childBaseinfo.situation=='1'}">${fns:getDictLabel(childBaseinfo.situation, 'situation', '')}</c:if>
								<c:if test="${childBaseinfo.situation!='1'}"><span style="color: red; font-weight: bold;">${fns:getDictLabel(childBaseinfo.situation, 'situation', '')}</span></c:if>
							</td>
						</tr>
						<tr>
							<td class="text-right">备注</td>
							<td><span style="color: red; font-weight: bold;">${childBaseinfo.remarks}</span></td>
						</tr>
						<c:if test="${not empty childBaseinfo.id }">
						<tr>
							<td colspan="2" style="text-align: center;">
								<input type="button" value="档案" class="btn btn-success"  onclick="printBaseInfo('${childBaseinfo.id}')">
								<input type="button" value="入托证明" class="btn btn-success "  onclick="printProve('${childBaseinfo.id}')">
								<a href="#" id="addOfflineRecord"  class="btn btn-success">操作</a>
								<c:if test="${childBaseinfo.situation!='5'}">
									<input type="button"   value="删除个案" class="btn btn-danger"  onclick="delinfo('${childBaseinfo.id}')">
								</c:if>
								<c:if test="${childBaseinfo.situation=='5'}">
									<input type="button"   value="恢复个案" class="btn btn-success"  onclick="restoreinfo('${childBaseinfo.id}')">
								</c:if>
							</td>
						</tr>
						</c:if>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			
			<div class=" col-sm-5 " style="padding: 5px">
			<label style="font-size: 1.8em;" class="col-sm-12 text-center">已接种疫苗</label>
				<sys:message content="${message}" />
				<table 
					class="table  table-bordered table-condensed">
					<thead>
						<tr>
						<!-- <th>疫苗种类</th> -->
							<th>疫苗名称</th>
							<th><span class="ib">针次</span></th>
							<th>批号</th>
							<th>厂家</th>
					   <!-- <th>疫苗价格</th> -->
							<th width="20%">接种日期</th>
					   <!-- <th><span class="ib">接种</span><span class="ib">部位</span></th> -->
							<th><span class="ib">接种</span><span class="ib">类型</span></th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${reserves}" var="reserve">
							<tr class="reserve" data-id="${reserve.id}">
								<td>${reserve.vaccinate.name }</td>
								<td>${reserve.product.batchno }</td>
								<td><span class="text-circle">${reserve.num.pin }</span></td>
								<td></td>
								<td>${reserve.vaccType }</td>
								<td>
<!-- 								<button class="btn btn-xs btn-primary" type="button">排号</button> -->
									<button class="btn btn-xs btn-danger" type="button" onclick="delReserve('${reserve.id}')">删除</button>
								</td>
							</tr>
						</c:forEach>
						<c:forEach items="${returnlist}" var="Nursery">
								<c:forEach items="${Nursery}" var="Nursery1">
									<tr style="color: black;" 
										<c:if test="${ Nursery1.status=='0' }"> class="bsnone" ondblclick="javascript:stmpAgen('${Nursery1.id}');"</c:if> 
										<c:if test="${ Nursery1.status=='1'&&Nursery1.source!='4' }">ondblclick="javascript:altRecord('${Nursery1.id}',${Nursery1.dosage});" </c:if>  
										<c:if test="${Nursery1.source=='4' }">ondblclick="javascript:j('${Nursery1.id}');" </c:if>
									>
										<c:if test="${Nursery1.size!=0}">
									<!--	<td rowspan="${Nursery1.size}">${Nursery1.vaccCode}</td>  -->
										</c:if>
										<td>${Nursery1.vaccName}</td>
										<td><span class="text-circle">${Nursery1.dosage}</span></td>
										<td>${Nursery1.batch}</td>
										<td>${Nursery1.manufacturer}</td>
										  
							<%-- 		<td>										
											<c:choose>
												<c:when test="${Nursery1.price==0 }" > <span class="label label-deafult">免费</span> </c:when>
												<c:otherwise> <span class="label label-danger">自费</span> </c:otherwise>
											</c:choose>
										</td > --%>
									<td class="recDate"><fmt:formatDate value="${Nursery1.vaccinatedate}" pattern="yyyy-MM-dd" /></td>
<%-- 								<td>${fns:getDictLabel(Nursery1.bodypart, 'position', '')}</td> --%>
									<td>${fns:getDictLabel(Nursery1.vacctype, 'vacctype', '')}</td>
										 <td>
										<c:if test="${ Nursery1.status=='0' }"> 
											<a style="display: inline-block;" href="javascript:stmpAgen('${Nursery1.id}');" class="confirmVacc btn btn-xs btn-primary">再排</a> 
										</c:if> 
										 <c:if test="${ Nursery1.status=='0' }">
											<a style="display: inline-block;" class="confirmVacc btn btn-xs btn-danger" href="javascript:delRecord('${Nursery1.id}','${childBaseinfo.childcode}')" onclick="return confirmx('确认要取消该信息吗？', this.href)">取消</a>
										</c:if>  
										<c:if test="${ Nursery1.status=='1'&&Nursery1.source!='4' }"> 
											<a  style="display: inline-block;" href="javascript:altRecord('${Nursery1.id}',${Nursery1.dosage});" class="confirmVacc btn btn-xs btn-success">修改</a> 
										</c:if> 
										<c:if test="${ Nursery1.status=='8' }"> 
											<a style="display: inline-block;" href="javascript:j('${Nursery1.id}');" class="confirmVacc btn btn-xs btn-success" >查看</a> 
										</c:if> 
										<c:if test="${ Nursery1.source=='3'&&Nursery1.source!='4' }"> <%-- <a href="${ctx}/child_vaccinaterecord/childVaccinaterecord/del?id=${Nursery1.id}&code=${childBaseinfo.childcode}"
											class="confirmVacc">删除</a>  --%>
											<a style="display: inline-block;" class="confirmVacc btn btn-xs btn-danger" href="javascript:delRecord('${Nursery1.id}','${childBaseinfo.childcode}')" onclick="return confirmx('确认要删除该信息吗？', this.href)">删除</a>
										</c:if>  
										</td> 
									</tr>
								</c:forEach>
							</c:forEach>
					</tbody>
				</table>
			</div>
			<div class=" col-sm-4 " style="padding: 5px">
				<em style="font-size: 1.3em;position: absolute; top:-20px; right: 10px;">${lastLiveDaysTips}</em>
				<label style="font-size: 1.8em;" class="col-sm-12 text-center">可接种疫苗
					<c:if test="${childBaseinfo.id !=null}">
					<button class="btn btn-success positionAbs w100 btn-quene-anyway" onclick="openAddAnyWay()">自定义排号</button>
					</c:if>
				</label>
<%--				<c:if test="${childBaseinfo.remarks!=''}"><em style="font-size: 1.3em;">备注：${childBaseinfo.remarks}</em></c:if>
 				<sys:message content="${lastLiveDaysTips}" type="primary" /> --%>
				<sys:message content="${message}" />
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr class="finished">
							<th style="color: black;">疫苗种类</th>
							<th style="color: black;">针次</th>
							<th style="color: black;">月龄</th>
							<th style="color: black;">价格</th>
							<th style="color: black;">库存</th>
						 <shiro:hasPermission name="child:cord:edit">
								<th style="color: black;">操作</th>
							</shiro:hasPermission> 
						</tr>
					</thead>
					<tbody id="recordTbody">
						<c:forEach items="${VaccList}" var="bsManageVaccinenum">
									<tr data-rid='${bsManageVaccinenum.rid}'
										<c:if test="${bsManageVaccinenum.stock > 0 }">
											ondblclick="preQueue('${bsManageVaccinenum.group}','${bsManageVaccinenum.pin}','${bsManageVaccinenum.id}','${bsManageVaccinenum.price}')"
										</c:if>
										<c:if test="${not empty bsManageVaccinenum.remindDate }">
											style="color:#FA2C40;"
										</c:if>
										 >
										<td>${bsManageVaccinenum.name} 
											<c:if test="${bsManageVaccinenum.vaccTypeFromNid != '1' }"><span style="display: inline-block;">【${fns:getDictLabel(bsManageVaccinenum.vaccTypeFromNid, 'vacctype', '')}】</span></c:if>
											<c:if test="${not empty bsManageVaccinenum.remindDate }">
												<br/>预约:<fmt:formatDate value="${bsManageVaccinenum.remindDate }" pattern="yyyy-MM-dd"/>
											</c:if>
										</td>
										<td style="font-weight: bold;"><span class="text-circle">${bsManageVaccinenum.pin}</span></td>
										<td class="text-center">${bsManageVaccinenum.mouage}</td>
										<td class="text-center"
										<%-- <c:if test="${bsManageVaccinenum.minPrice>0}">style="color: red"</c:if>  --%>
										<%-- <c:if test="${bsManageVaccinenum.minPrice==0 ||bsManageVaccinenum.maxPrice>0}">style="color: red"</c:if>  --%>
										>
										<c:choose>
									<c:when test="${bsManageVaccinenum.minPrice>0.0}" >
									<span class="label label-danger">自费</span>
									</c:when>
									<c:when test="${bsManageVaccinenum.minPrice==0.0 &&bsManageVaccinenum.maxPrice>0.0}" >
									 <span class="label label-deafult">免费</span>&nbsp;<br><span class="label label-danger">自费</span>
									</c:when>
									<c:otherwise>
									<span class="label label-deafult">免费</span>
									</c:otherwise>
									</c:choose>
										</td>
										<td>${bsManageVaccinenum.stock}</td>
										 <td class="text-center">
											 <c:if test="${bsManageVaccinenum.stock > 0 }">
											 <button type="button" onclick="preQueue('${bsManageVaccinenum.group}','${bsManageVaccinenum.pin}','${bsManageVaccinenum.id}','${bsManageVaccinenum.rid}')" class="btn btn-primary confirmVacc btn-quene">
											 	 ${queueType eq 'quick'?"登记":"排号" }
											 </button>
											 </c:if> 
											 <c:if test="${bsManageVaccinenum.stock == 0 }">
											 <a  class="confirmVacc">暂无库存</a>
											 </c:if> 
											
										</td> 
									</tr>
						</c:forEach>
					</tbody>
				</table>								
				<div class="remindList">	 						
				</div>
				
			</div>
		</div>
	</div>
	<div>		
	</div>
	<div class="pull-right fix">
<%-- 		<a href="${ctx}/manage_stock_in/manageStockIn1" class="btn  btn-primary">疫苗管理</a>&emsp;&emsp; --%>
<!-- 		<button class="btn  btn-primary" onclick="openRemind()">预约登记</button>&emsp;&emsp; -->
		<a href="${ctx}/remind/vacChildRemind/remindAllList" class="btn  btn-primary" >预约管理</a>&emsp;&emsp;
		<a href="${ctx}/child_baseinfo/documentStatistics" class="btn  btn-primary" >日常管理</a>&emsp;&emsp;
		<a href="${ctx}/reconciliation/reconciliationStock/reconciliationStock" class="btn  btn-primary" >对帐管理</a>&emsp;&emsp;
		<%-- <a href="${ctx}/rabiesvaccine/bsRabiesCheckin" class="btn  btn-primary">狂犬疫苗</a>&emsp;&emsp; --%>
		<a href="${ctx}/export/export/exportlist" class="btn  btn-primary">报表管理</a>&emsp;&emsp;
		<a href="javascript:void(0)" class="btn  btn-primary" onclick="alertSetting()">设置</a>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
	</div>
</body>
</html>