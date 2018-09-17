<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>接种台</title>
<link href="${ctxStatic}/plugins/datepicker/jquery-ui.min.css" rel="stylesheet" />
<script type="text/javascript" src="${ctxStatic}/plugins/datepicker/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/plugins/datepicker/jquery.ui.datepicker-zh-CN.js"></script>
<script id="js-childRemind" src="${ctxStatic}/js/childRemind.js?v=3"></script>
<style type="text/css">

.nextVaccTable {width: 100%; } .ui-state-highlight, .ui-widget-content .ui-state-highlight, .ui-widget-header .ui-state-highlight {border: 1px solid #03bbdc; font-weight: bold; } .ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active, a.ui-button:active, .ui-button:active, .ui-button.ui-state-active:hover {border: 1px solid #1cb394; background: #1cb394; font-weight: bold; color: #fff; } td.highlight {border: none !important; padding: 1px 0 1px 1px !important; background: none !important; overflow: hidden; } td.highlight a {background: rgb(91, 195, 167) !important; border: 1px #ffffff solid !important; border-radius: 8px !important; color: #000 !important; } .ui-datepicker {width: 30em !important; padding: .2em .2em 0; font-size: 14px; margin: 0px; } .nextVaccDiv {width: 90%; margin: 0 5% 10px 5%; background: rgba(255, 255, 255, 0.3); } .nextVaccDiv .title {text-align: center; font-weight: bold; line-height: 30px; margin: 0; background: #ccc; border-radius: 5px; border: #999 solid 1px; cursor: pointer; } .nextVaccDiv ul {list-style: none; margin: 2px; padding: 5px; } .nextVaccDiv ul li {margin: 0; padding: 2px 0 2px 24px; border-bottom: #fff 1px solid; position: relative; font-size: 16px; cursor: pointer; } .nextVaccDiv .selected i {display: block; height: 18px; width: 18px; position: absolute; left: 4px; top: 4px; background: url("${ctxStatic}/img/fx2.png") 0 0 no-repeat; background-size: cover; } .nextVaccDiv .selected {background: rgba(175, 217, 223, 0.7); font-weight: bold; } #nextTimeInput {width: 100px; } #nextVaccInput {width: 220px; } .inputNull {background: rgba(255, 255, 255, 0); border: none; margin: 10px 0 10px 0; font-size: 18px; width: 100px; } .text-circle {display: inline-block; margin: 0 auto; width: 15px; height: 18px; border: 1px #000 solid; border-radius: 15px; font-size: .8em; text-align: center; } .remindList table td {text-align: center; } .remindList ul li {font-size: 16px; line-height: 26px; } .short-age {list-style: none; padding: 0; margin: 4px 10px 0 10px; } .short-age li {display: inline-block; margin: 4px 4px 0 4px; } .national span {background: #fac1c7 !important; } .weekday a {background: #bcc7ff !important; } .datapicker-tips {position: absolute; top: 24px; left: 12px; display: block; } .sub-li {background: #fff; } .numbertitle span {font-size: 28px !important; margin: 10px !important; } .ml2 {margin-left: 2px; } .mr100 {margin-right: 100px; } /* .finished{color:#999; } */ .chooseed {color: #ef0000; font-weight: bold; background: rgba(29, 132, 198, 0.2) !important; } .chooseed>td {font-size: 1.4em !important; } .normal {color: black; } .scroll {height: 500px; overflow: auto; } .rechoosed {background: rgba(131, 102, 241, 0.32) !important; } .tips {font-size: 20px; } .btn-top {margin-top: 14px; } .btn-top .btn {margin-right: 15px; float: right; } .text-circle {display: block; margin: 0 auto; width: 18px; height: 18px; border: 1px #000 solid; border-radius: 9px; font-size: .8em; text-align: center; } /* contextMenu */ .dropdown-menu {font-size: 16px; } .dropdown-menu>li>a {line-height: 20px; } .dropdown-menu>li>a {padding: 2px 5px; } .nav-header {padding: 2px 5px; background: #ccc; } /* end contextMenu */ table th, #contentTableD td {word-break: keep-all; white-space: nowrap; padding: 2px; } .table-title {font-size: 1.4em; text-align: center; font-weight: bold; } .ibox-content {padding: 0; } .curIdx{font-size: 18px; font-weight: bold; margin-right: 6px; }
</style>
<meta name="decorator" content="default" />
<script >
	var baseinfo;
	var holidays = JSON.parse('${holidays}');
	var weekdays = JSON.parse('${weekdays}');
	var nid = '';
	var list = JSON.parse('${list}');
	var curIdx = ${empty curIdx?'0':curIdx};
	
</script>
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	$(function(){
		autoLoad(curIdx);
		printOption = false;
	})
	
	function goToPrev(){
		if(curIdx == 0){
			success("当前是第一个宝宝");
		}else{
			autoLoad(--curIdx);
		}
	}
	
	function goToNext(){
		if(curIdx == list.length -1){
			success("当前是最后一个宝宝");
		}else{
			autoLoad(++curIdx);
		}
	}
	
	function autoLoad(idx){
		initDatePicker();
		$(".curIdx").html((idx+1) + "/" + list.length);
		var info = list[idx];
		if(info){
			loadChildBaseInfo(info.id);
			loadChildRecord(info.id);
			loadChildRemind(info.childcode);
		}
		
	}
	
	//加载儿童信息
	function loadChildBaseInfo(childid){
		$.ajax({
			url:ctx + "/child_baseinfo/childBaseinfo/getApi",
			data:{"id":childid, "localCode":'${localCode}'},
			success:function(data){
				if(data){
					baseinfo = data;
					//加载预约js
					var childTpl = $("#childTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
					html = Mustache.render(childTpl, data);
					$(".baseinfo-table").html(html);
					showShortAge();
					$('#nextDatePicker').datepicker('setDate',new Date());
				}
			},
			error:function(a,b,c){
				console.error("儿童信息加载失败",a)
			}
		});
	}
	
	//加载接种记录
	function loadChildRecord(childid){
		$.ajax({
			url:ctx + "/child_vaccinaterecord/childVaccinaterecord/findShowListApi",
			data:{"childid":childid},
			success:function(data){
				var html = '';
				if(data){
					$.each(data,function(i,t){
						html += '<tr>';
						if(t.size){
							html += '<td rowspan="' + t.size + '">' + t.vaccName + '</td>';
						}
						html +=							
// 							'<td>' + t.vaccName + '</td>'+
							'<td><span class="text-circle">' + t.dosage + '</span></td>'+
							'<td class="text-center">' + t.dicts.vacctype + '</td>'+
							'<td class="recDate">' + t.vaccinatedate + '</td>'+
							'<td>' + (t.manufacturer?t.manufacturer:"") + '</td>'+
							'<td>' + (t.batch?t.batch:"") + '</td>';
// 							'<td>' + t.dicts.bodypart +'</td>'+
// 							'<td>' + t.office + '</td>';
// 						if(!t.price){
// 							html += '<td class="pay">免费</td> ';
// 						}else{
// 							html += '<td class="pay">' + t.dicts.paystatus + '</td>';
// 						}
						html += '</tr>';
					});
					$(".record-table").html(html);
					showDays();
				}
			},
			error:function(a,b,c){
				console.error("接种记录加载失败",a)
			}
		});
	}
	
	/* 加载预约记录 */
	function loadChildRemind(childcode){
		
		if(childcode){
			$.ajax({
				url:ctx + "/remind/vacChildRemind/findListApi",
				data:{"childcode":childcode, "status":'0'}, 
				success:function(remindDate){
					var ul = "<table class='table table-bordered'><thead><tr><th>疫苗</th><th>预约时间</th><th>操作</th></tr></thead><tbody>";
					if(remindDate.length){
						for(i in remindDate){
							ul += '<tr>'+
									'<td>' + remindDate[i].remindVacc + '</td>'+
									'<td class="text-center">' + remindDate[i].remindDate + '</td>'+
// 									'<td class="text-center">' + remindDate[i].selectDate + '</td>'+
									'<td class="text-center"><button class="btn btn-xs btn-success mr10" type="button" data-id="' + remindDate[i].id + '" onclick="cancelRemind(this)">取消</button></td>'+
									'</tr>';
						}
					}
					ul += "</tbody></table>";
					$(".remindList").html(ul);
				},
				error:function(a,b,c){
					console.error("加载预约记录失败",a,b,c);
					var ul = "<table class='table table-bordered'><thead><tr><th>疫苗</th><th>预约时间</th><th>操作</th></tr></thead><tbody></tbody></table>";
					$(".remindList").html(ul);
				}
			});
		}
		
		
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

	//初始化预约列表
	function initDatePicker(){
		$("#sa1").html("");
		$("#sa2").html("");
		$("#mouthAge").html("");
	}
	
	//根据时间显示最后一次接种时间和今日接种记录
	function showDays(){
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
	}
</script>
</head>
<body>
	<div class="ibox">
		<div class="row col-sm-12">
			<div class="col-xs-12" style="padding-left: 0px;">
				<div class="form-group col-md-3 col-xs-6" style="padding-left: 0px;">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right ">编码&nbsp;/&nbsp;生日:</span>
						<input id="childname" name="childname" placeholder="4位排号 / 6位儿童编码 / 6位生日" class="form-control" type="text" value="" maxlength="20">
					</div>
				</div>
				<div class="form-group col-md-9 col-xs-12">
					<span class="curIdx"></span>
					<button class="btn btn-primary w50" type="button" onclick="autoLoad(0)"><span class="glyphicon glyphicon-arrow-left"></span></button>
					<button class="btn btn-primary w100" type="button" onclick="goToPrev()"><span class="glyphicon glyphicon-arrow-left">上一位</span></button>
					<button class="btn btn-primary w100" type="button" onclick="goToNext()"><span class="glyphicon glyphicon-arrow-right"/>下一位</span></button>
					<button class="btn btn-primary w50" type="button" onclick="autoLoad(list.length -1)"><span class="glyphicon glyphicon-arrow-right"/></span></button>
					<button class="btn btn-success w100 pull-right" type="button" onclick="history.go(-1)"><span class="glyphicon glyphicon-arrow-left"></span>返回</span></button>
				</div>
			</div>
		<div class="row ibox-content" >
			<div class=" col-sm-3">
				<p class="table-title">儿童基本信息:</p>
				<table id="contentTable" class="table table-striped table-bordered table-condensed baseinfo-table table-hover">
				</table>
			</div>
			<!-- 已接种记录 -->
			<div class="col-sm-5">
				<p class="table-title">预约记录:</p>
				<div class="remindList"></div>
				<p class="table-title">已接种疫苗:</p>
				<table id="contentTableD" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
<!-- 							<th class="text-center">种类</th> -->
							<th class="text-center">名称</th>
							<th class="text-center">剂次</th>
							<th class="text-center">接种类型</th>
							<th class="text-center">接种日期</th>
							<th class="text-center">厂家</th>
							<th class="text-center">批次</th>
<!-- 							<th class="text-center">接种部位</th> -->
<!-- 							<th class="text-center">接种单位</th> -->
<!-- 							<th class="text-center">付款状态</th> -->
<!-- 							<th class="text-center">签字状态</th> -->
						</tr>
					</thead>
					<tbody id="recordTbody" class="record-table">
					</tbody>
				</table>

			</div>
			<!-- 预约列表 -->
			<div class="col-sm-4" style="padding: 0px">
				<div class="remindForm">
					<!-- 下次接种时间 -->
					<label class="col-sm-12 control-label text-left nextEl" style="font-size: 16px;">预约下一针时间：<span id="mouthAge" style="color: red; font-weight: bold;">（）</span></label>
					<div class="col-sm-12"></div>
					<div class="next form-group">
			 			<div id="nextTime" class="form-group col-sm-12">
			 				<div id="nextDatePicker" class="nextEl"></div>
			 				<div class="nextEl">
			 					<ul class="short-age">
			 						
			 					</ul>
			 				</div>
			 				<div class="">
			 					<label class="ft16" for="nextTimeInput" style="display: none" >下一针时间：</label>
			 					<input id="nextTimeInput" class="inputNull" name="nextTime" type="hidden"/>
			 					<input id="nextVaccInput" class="inputNull" name="nextVacc" type="hidden"/>
			 					<input id="nextVaccGroup" class="inputNull" name="nextVaccGroup" type="hidden"/>
			 					<!-- <div class="remindList">
			 						
			 					</div> -->
			 					<button id="btnRemind" class="btn btn-primary w100 mt10" type="button" style="margin-left: 14px;"  data-stat="list" >预约</button>
			 				</div>
			 			</div>
						<div id="nextVacc" class="form-group col-xs-12 nextEl">
			 				<div class="nextVaccDiv fl clearfix">
			 					<p class="title">一类苗</p>
			 					<ul id="sa1" data-role="1">
				 				</ul>
			 				</div>
			 				<div class="nextVaccDiv fl clearfix">
			 					<p class="title">二类苗</p>
			 					<ul id="sa2" data-role="2">
			 					</ul>
			 				</div>
			 			</div>
					</div>
				</div>				

			</div>
		</div>
	</div>
	<script type="text/template" id="childTpl">
		<thead>
			<tr>
				<td style="width: 100px" class="text-right">儿童编码</td>
				<td>{{childcode}}</td>
			</tr>
			<tr>
				<td class="text-right">出生证号</td>
				<td>{{birthcode}}</td>
			</tr>
			<tr>
				<td class="text-right">儿童姓名</td>
				<td>{{childname}}<span style="color: red; font-weight: bold;">（{{mouage}}）</span></td>
			</tr>
			<tr>
				<td class="text-right">性&emsp;&emsp;别</td>
				<td>{{dicts.sex}}</td>
			</tr>
			<tr>
				<td class="text-right">出生日期</td>
				<td>{{birthday}}</td>
			</tr>
			<tr>
				<td class="text-right">出生医院</td>
				<td>{{birthhostipal}}</td>
			</tr>
			<tr>
				<td class="text-right">出生体重</td>
				<td>{{birthweight}}&nbsp;克</td>
			</tr>
			<tr>
				<td class="text-right">区域划分</td>
				<td>{{area}}</td>
			</tr>
			<tr>
				<td class="text-right">母亲姓名</td>
				<td>{{guardianname}}</td>
			</tr>
			<tr>
				<td class="text-right">手机号码</td>
				<td>{{guardianmobile}}</td>
			</tr>
			<tr>
				<td class="text-right">母亲身份证号</td>
				<td>{{guardianidentificationnumber}}</td>
			</tr>
			<tr>
				<td class="text-right">家庭住址</td>
				<td>{{homeaddress}}</td>
			</tr>
			<tr>
				<td class="text-right">户籍地址</td>
				<td>{{registryaddress}}</td>
			</tr>
			<tr>
				<td class="text-right">接种单位</td>
				<td>{{officeinfo}}</td>
			</tr>
			<tr>
				<td class="text-right">在册情况</td>
				<td>{{dicts.situation}}</td>
			</tr>
			<tr>
				<td class="text-right">备注</td>
				<td>{{remarks}}</td>
			</tr>
		</thead>
	</script>

</body>
</html>