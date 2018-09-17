	//所有产品信息
	var products = new Array();
	//提醒列表
	var remindList = new Array();
	//是否打印小票
	var printOption = true;
	var uui = 0;
	
	
	$(document).ready(function() {
		$(document).on("click", ".nextVaccDiv li",function(){
			
			//点击大类 加载
				if($(this).hasClass("selected")){
					$(this).removeClass("selected");
					popRemind(this);
				}else{
					$(this).addClass("selected");
					pushRemind(this);
					//单选
					var uuid = $(this).attr("data-uui");
					$(".nextVaccDiv li[data-mnum=" + $(this).attr("data-mnum") + "]").each(function(i,t){
						if($(t).attr("data-uui")!= uuid){
							$(t).removeClass("selected");
							popRemind(t);
						}	
					});
				/*}*/
			}
		});
		//清空
		$(".nextVaccDiv .title").click(function(){
			$(this).next().find("li").each(function(i,t){
				$(this).removeClass("selected")
				popRemind(this);
			});
		});
		
		$("#btnRemind").click(function(){
			if(!$("#nextTimeInput").val()){
				layer.msg("请选择下次预约接种的时间",{"icon":7});
				return false;
			}
			if(remindList.length == 0){
				layer.msg("请选择下次预约接种的疫苗",{"icon":7});
				return false;
			}
			if(!baseinfo.childcode){
				toastrMsg("未选择儿童信息","error");
			}
			layer.load();
			$.ajax({
				url: ctx + "/remind/vacChildRemind/saveRemind",
				data : {
					"childcode" : baseinfo.childcode,
					"remindDate" : $("#nextTimeInput").val(),
					"remindVacc" : JSON.stringify(remindList)
				},
				success:function(data){
					layer.closeAll();
					if(data.code == "200"){
						//是否打印
						if(printOption){
							if(receiptType){
								if(printRemind(data)){
									success("预约成功");
									setTimeout(function() {
										parent.location.reload();
										parent.layer.closeAll();
									}, 1000);
								}else{
									parent.warning("预约成功，小票打印失败,请检查本地打印控件是否安装或关闭打印配置");
									parent.layer.close(parent.layer.getFrameIndex(window.name));
								}
							}else{
								success("预约成功");
								setTimeout(function() {
									parent.location.reload();
									parent.layer.closeAll();
								}, 1000);
							}
						}else{
							success("预约成功"); 
							loadChildRemind(baseinfo.childcode);
						}
					}else{
						toastrMsg(data.msg,"error");
					}
				},
				error:function(){
					layer.closeAll();
					toastrMsg("预约失败","error");
				}
			});
			
		});
		
		$(document).on("click", ".short-age button",function(){
			var offset = $(this).attr("data-offset");
			var nextd=new Date(addMouth(new Date(baseinfo.birthday),$(this).attr("data-offset")));
			nextd=getNextworkDay(nextd);
			//nextd = (nextd.getMonth()+1)+"/"+nextd.getDate()+"/"+nextd.getFullYear();
			var navigatorName = "Microsoft Internet Explorer"; 
			if( navigator.appName == navigatorName ){ 
				$('#nextDatePicker').datepicker('setDate',addMouth(new Date(baseinfo.birthday),$(this).attr("data-offset")));
			}else{
				$('#nextDatePicker').datepicker('setDate',nextd);
			}
			$('#nextDatePicker .ui-state-active').click();
		});
		$(document).on("click",".choose",function(){
			//$(this).hasClass("choosed")?$(this).removeClass("choosed"):$(this).addClass("choosed");
			if($(this).hasClass("choosed")){
				$(this).removeClass("choosed");
				
			}else{
				$(this).addClass("choosed");
				$(this).parents("li").siblings().find(".choose").removeClass("choosed");
			}
		});
		$(document).on("click",".timeOf",function(){
			if($(this).siblings(".choose").hasClass("choosed")){
				$(this).siblings(".choose").removeClass("choosed");
			}else{
				$(this).siblings(".choose").addClass("choosed");
				$(this).parents("li").siblings().find(".choose").removeClass("choosed");
			}
		})
		$("#vaccName").change();
		
		//展示datepicker
		makeDatePicker();
		showShortAge()
    	$('#nextDatePicker').datepicker('setDate',nextTime);
		var idx = layer.load();
		setTimeout(function() {
			layer.close(idx);
			$('#nextDatePicker .ui-state-active').click();
		}, 500);
	});
	
	function isHoliday(dd){
		for (var i = 0; i < weekdays.length; i++) {
		    if ((dd.getDay() == (weekdays[i]["dateDay"]-1))&&dd>new Date()) {
				return true;
		    }
		}
		for (i = 0; i < holidays.length; i++) {
			if (dd.getFullYear() == holidays[i]["dateTime"].substring(0,4) && (dd.getMonth()+1) == parseInt(holidays[i]["dateTime"].substring(5,7))&& dd.getDate() == parseInt(holidays[i]["dateTime"].substring(8,10))) {
				return true
			}
		}
		return false;
	}
	
	function getNextworkDay(dd){
		while(isHoliday(dd)){
			dd=addDate(dd,1);
		}
		return dd;
	}
	
	function showShortAge(){
		$(".short-age").html("");
		if(!baseinfo){
			return ;
		}
		//快速选取时间
		var html = '<li> <button class="btn btn-xs btn-success" type="button" data-offset=0 >今天</button> </li>';
		var count=0;
		for(ag in agelist){
			if(agelist[ag].age <= baseinfo.mouthAgeInt){
				continue;
			}
			count++;
			html += '<li> <button class="btn btn-xs btn-success" type="button" data-offset=' + agelist[ag].age + ' >' + agelist[ag].text + '</button> </li>';
			if(count>=5){
				break;
			}
		}
		$(".short-age").html(html);
	}
		
	//将pid放入提醒集合
	function pushRemind(thi){
		var _this = $(thi);
		var remind = new Object();
		remind["vaccId"] = _this.attr("data-vid");
		if(_this.find("span").html().indexOf("暂无库存") > -1){
			remind["price"] = '0';
		}else{
			remind["price"] = _this.attr("data-price");
		}
		remind["remindVacc"] = _this.find("span").html().replace('<em style="color:red">暂无库存</em>', "");
		remind["selectTime"] = $(".choose-time .choosed").attr("data-time");
		if(!remind["selectTime"]){
			error("请选择时段");
			$(thi).removeClass("selected");
			return;
		}
		
		var hasPid = false;
		for(var i = 0; i < remindList.length; i++){
			if(remindList[i]["vaccId"] == _this.attr("data-vid") && remindList[i]["price"] == _this.attr("data-price")){
				hasPid = true;
			}
		}
		if(!hasPid){
			remindList.push(remind);
		}
	}

	//将pid移除提醒集合
	function popRemind(thi){
		var _this = $(thi);
		for(var i = 0; i < remindList.length; i++){
			if(remindList[i]["vaccId"] == _this.attr("data-vid") && remindList[i]["price"] == _this.attr("data-price")){
				removeByIdx(remindList, i--);
			}
		}
	}
	
	function makeDatePicker(){
		var now = new Date();
		var defaultDate = new Date();
		$("#nextDatePicker").datepicker({
			numberOfMonths: 1,
			showMonthAfterYear:false,
			showOtherMonths: true,
			selectOtherMonths: false,
			//showButtonPanel: true,
			minDate: 0,  //当前日期之前的0天
			hideIfNoPrevNext: true , //按钮隐藏
			defaultDate : defaultDate, //预设默认选定日期 
			dateFormat: 'yy-mm-dd',
			changeYear: true,
			changeMonth: true,
			yearRange:now.getFullYear() + ":" + (now.getFullYear() + 6),
			//选择日期时回调方法
  			onSelect: function (dateText, inst) {
	        	$("#nextTimeInput").val(dateText);
	        	$("#nextTimeInput").blur();
	        	$("#nextVaccInput").val("");
	        	$("#sa1").html("");
	        	$("#sa2").html("");
	        	//getDay(dateText);
	        	$(".day").remove();
	        	$("#nextDatePicker").append(getDay(dateText));
	        	$.ajax({
					type : "POST",
					url : ctx + "/child_vaccinaterecord/childVaccinaterecord1/daypick",
					data : {datef:dateText,"childcode":baseinfo.childcode,nid:$("#nid").val(),productid:$("#productid").val()},
					success : function(data) {
						var tempvaccs = new Array();
						remindList = new Array();
						products = data.products;
						$("#mouthAge").text("（"+data.mouthAge+"）");
						//时间段选择
						//var chooseTime = '</div>';
						// var datas = {
						// 	workHours: [
						// 	{
						// 		id: 1,
						// 		timeSlice: "8:00-8:30",
						// 		num: 12,
						// 		maximum: 20,
						// 		percent: 0.7
						// 	},
						// 	{
						// 		id: 1,
						// 		timeSlice: "8:00-8:30",
						// 		num: 12,
						// 		maximum: 20,
						// 		percent: 0.7
						// 	}
						// 	]
						// }
						var datas = data.workHours;
						var timeChild = '';
						$(".choose-time").remove();
						timeChild = timeChild + '<div class="choose-time"><ul>';
						var a=[];

						for(i = 0;i<datas.length;i++){
							a.push(datas[i].percent);
							timeChild = timeChild + '<li><label><span class="choose" data-id='+datas[i].id+' data-time=' + datas[i].timeSlice + '></span><span class="timeOf">'
							+datas[i].timeSlice+'</span></label><span class="pepNum">'
							+datas[i].num + '/' + datas[i].maximum +'</span><p class="process"><span></span></p></li>'
						}
						timeChild = timeChild +"</ul></div>";
						//$(".choose-time").html(timeChild);
						$("#nextDatePicker").append(timeChild);
						defaults(a);
						process(a);
						//默认选择第一个

						var	html="";
						$.each(data.vaccs1,function(i,t){
							var tempPros = new Array();
							var tempvacids = new Array();
							for(var j in products){
								if(products[j].vaccinate.mNum == t.group && tempvacids.indexOf(products[j].vaccineid+products[j].sellprice) < 0){
									if(products[j].sellprice==0){
										tempvacids.push(products[j].vaccineid+products[j].sellprice);
										tempPros.push(products[j]);
									}
								}
							}
							if(tempvacids.length == 0){
								html += "<li data-intype=" + t.intype
								+" data-mNum=" + t.group + " data-vid='" + t.group + "' data-uui='" + (uui++) +"' data-price='0' data-role='group'><i></i>"
								+"<span>" + t.name+ "(免费)</span> "
								+"</li>";
							}else{
								for(var j in tempvacids){
									html += "<li data-intype=" + t.intype
									+" data-mNum=" + t.group + " data-vid='" + tempPros[j].vaccineid + "' data-uui='" + (uui++) +"' data-price='0' data-role='group'><i></i>"
									+"<span>" + tempPros[j].name+ "(免费)</span> "
									+"</li>";
								}
							}
						});
						$("#sa1").html(html);
						$("#sa1").change(); 
						var	html="";
						
						$.each(data.vaccs2,function(i,t){
							var tempPros = new Array();
							var tempvacids = new Array();
							for(var j in products){
								if(products[j].vaccinate.mNum == t.group && tempvacids.indexOf(products[j].vaccineid+products[j].sellprice) < 0){
									tempvacids.push(products[j].vaccineid+products[j].sellprice);
									tempPros.push(products[j])
								}
							}
							if(tempvacids.length == 0){
								html += "<li data-intype=" + t.intype
								+" data-mNum=" + t.group + " data-vid='" + t.group + "' data-uui='" + (uui++) +"' data-price='1' data-role='group'><i></i>"
								+"<span>" + t.name+ "(自费)<em style='color:red'>暂无库存</em></span> "
								+"</li>";
							}else{
								for(var j in tempvacids){
									html += "<li data-intype=" + t.intype
									+" data-mNum=" + t.group + " data-vid='" + tempPros[j].vaccineid + "' data-uui='" + (uui++) +"' data-price='" + tempPros[j].sellprice + "' data-role='group'><i></i>"
									+"<span>" + tempPros[j].name+ "(￥" + tempPros[j].sellprice +")</span> "
									+"</li>";
								}
							}
						});
						
						$("#sa2").html(html);
						$("#sa2").change(); 
					}
				});
	        },
	        //设置特殊日期
			beforeShowDay: function nationalDays(date) {
				if(weekdays){
					for (var i = 0; i < weekdays.length; i++) {
						if ((date.getDay() == (weekdays[i]["dateDay"]-1))&&date>new Date()) {
							return [true, 'weekday' ];
						}
					}
				}
				if(holidays){
					for (i = 0; i < holidays.length; i++) {
						if (date.getFullYear() == holidays[i]["dateTime"].substring(0,4) && (date.getMonth()+1) == parseInt(holidays[i]["dateTime"].substring(5,7))
								&& date.getDate() == parseInt(holidays[i]["dateTime"].substring(8,10))) {
							return [true, 'weekday' ];
						}
					}
				}
				return [ true, '' ];
				}
			});
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
					reloadRemind();
				}else{
					layer.msg("取消失败",{"icon":2});	
				}
			},
			error:function(){
				layer.msg("取消失败",{"icon":2});	
			}
		});
	}
	function process(a){
		$(".process").each(function(i,$this){
			$(this).children("span").animate({"width":a[i]*100},300);
		});		
	}
	function defaults(a){
		if(a.length == 0){
			return false;
		}else{
			var choose = $(".choose");
			for(i=0;i<a.length;i++){
				if(a[i] != 1){
					$(choose[i]).addClass("choosed");
					return false;
				}
			}
		}
	}
	function getDay(date){
		var now = new Date();
		var chooseDay = new Date(date);
		var day = (chooseDay.getTime()-now.getTime())/(24*60*60*1000);

		var dayBox = '<div class="day">距离今天：'+parseInt(day)+'天</div>';
		return dayBox;
	}
	