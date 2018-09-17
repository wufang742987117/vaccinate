<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接种台确认</title>
	<meta name="decorator" content="default"/>
	<style >
		.warning{
			margin: 15px;
			padding: 4px;
			width:75%;
			border: 3px orange solid;
			background: yellow;
			color: red;
			position: absolute;
			bottom: -30px;
			font-size: 16px;
		}
	</style>
	<script type="text/javascript">
		/******** app 语音触发叫号功能  开始*******/
		function btnFinishConfirmTrueApp(){
			$("#btn").attr("disabled", true);
			if(op == 1){
				console.info("完成" + op++);					
				parent.confirmp($(".position").val(),$("#pid").val(),$("input[name=isnew]:checked").val());
			}
		}
		
		/* 从0开始  i-第几个select */
		function openSelect(i,title){
			var ol = $("select").eq(i).children();
			var html = "<ol>";
			for(var i = 0; i < ol.length; i ++){
				html += "<li data-role='altItems' data-value='" + $(ol[i]).val() + "'>" + $(ol[i]).html() + "</li>";
			}
			html += "</ol>";
			var index = layer.open({
			  content: html,
			  title:title,
			  btn:[]
			});
		}
		
		/* 从0开始 i-第几个select  j-第几个选项*/
		function selSelect(i,j){
			var li = $("li[data-role='altItems']");
			var select = $("select").eq(i);
			select.val($(li[j]).attr("data-value"));
			layer.closeAll();
		}
		
		/******** app 语音触发叫号功能  结束*******/
		
		var ls ,lsn, lsm;
		var op = 1;
		
        function reCallNum(url){
        	parent.reCallNum(url);
        }
        $(function(){
        	$(".position").focus();

	        /* 点击ESC 关闭 */
			$(document).keydown(function(event){ 
				if(event.keyCode == 27){ 
					parent.closeAll();
				}
			}); 

			$("#btn").click(function(){
				$(this).attr("disabled", true);
				if(op == 1){
					console.info("完成" + op++);					
					parent.confirmp($(".position").val(),$("#pid").val(),$("input[name=isnew]:checked").val());
				}
			});

			$("#cancel").click(function(){		
				parent.closeAll();
			});		
			
			var bodypart = "${quene.bodypart}";
			if(bodypart){
				$(".position").val(bodypart);
			}else{
				$(".position").val(preference.inPosition);
			}	
        })
        
        
        var productdata;
        var initbatch;
        $(function(){
        //通过疫苗小类id查询所有产品信息
		$("#name").change(function() {		
			var vaccid = $(this).val(); 
			if(!vaccid){
				return;
			}
			$.ajax({
				type : "POST",
				url : "${ctx}/product/bsManageProduct/findQueueViewListApi",
				data : {"vaccineid" : vaccid, "storenumIsNull":true,"showAll":"1"},
				dataType : "json",
				success : function(data) {
					 if(data){
					 	productdata = data;
					 }
					var	html1="";
					var	html2="";
					$.each(data, function(idx, item) { //循环对象取值
						html1 += "<option value='" + item.batchno + "'>"+ item.batchno + "(" + item.manufacturer + ")" + "</option>";
						html2 += "<option value='" + item.manufacturer + "'>"+ item.manufacturer + "</option>";
						if(item.id == "${not empty pid?pid:''}"){
							initbatch = item.batchno;
						}
					});
				$("#batch").html(html1);
				$("#manufacturer").html(html2);		
				if(initbatch){
					$("#batch").val(initbatch);	
				}
				$("#batch").change(); 
				}
			}); 
		});
			
		//加载疫苗产品
		$("#name").val("${vacc}");
		$("#name").change();
				
		/* 使用新苗 */
		function selectNew(){
			$("#isnew1").prop("checked",true);
			$("#isnew1").parent().addClass("checked");
			$("#isnew0").attr("checked",false);
			$("#isnew0").parent().removeClass("checked");
		}
		
		/* 使用旧苗 */
		function selectOld(){
			$("#isnew0").prop("checked",true);
			$("#isnew0").parent().addClass("checked");
			$("#isnew1").attr("checked",false);
			$("#isnew1").parent().removeClass("checked");
		}
				
		//选择疫苗批次
		$("#batch").change(function(){
			var batchno = $(this).val();
			if(initbatch && batchno != initbatch){
// 				alert("批号不同");
				$("#changeBatch").show();
			}else{
				$("#changeBatch").hide();
			}
			var mouthAge = "${mouthAge}";
			/* 初始化 */
			selectNew();
			$.each(productdata,function(i,t){
				if(batchno == t.batchno){
					$("#manufacturer").val(t.manufacturer);
					$("#pid").val(t.id);
					$("#price").html("￥" + (t.sellprice == 0?"免费":t.sellprice));
					if(t.spec > 1){
						if(t.rest > 0){
							if(localStorage.getItem("ignore" + t.id)){
								selectOld();
							}else{
								 var index = layer.confirm("该疫苗未用完，是否使用旧苗", {
							        btn: ['旧苗','新苗','使用旧苗不再提示'], //按钮
							        shade: true, //不显示遮罩
							        icon : 3,
							        title : t.batchno + "[" + t.manufacturer + "]",
							        yes : function(){
								        layer.close(index);     	
								    	selectOld();
							        },
							        btn2:function(){
							        	layer.close(index);  
							        },
							        btn3:function(){
							        	layer.close(index);  
							        	selectOld()
							        	localStorage.setItem("ignore" + t.id, 1);
							        }
							    });
							}
						}
						$("#isnewDiv").show();
					}else{
						$("#isnewDiv").hide();
					}
					//验证疫苗适用月龄
					if(t.applicableMax != '0' && mouthAge > t.applicableMax){
						layer.msg("儿童月龄超过该疫苗适用年龄<br>(" + t.applicableMin + " - " + t.applicableMax + "个月)",{"icon":2, "time":2500});
					}
					if(t.applicableMin != '0' && mouthAge < t.applicableMin){
						layer.msg("儿童月龄小于该疫苗适用年龄<br>(" + t.applicableMin + " - " + t.applicableMax + "个月)",{"icon":2,"time":2500});
					}
				}
			})
		});
	})
	</script>

</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">接种部位：</label>
	                <div class="col-sm-8">
	                	<select class="form-control valid position">
						<c:forEach items="${fns:getDictList('position')}" var="lab">
							<option value="${lab.value}">${lab.label}</option>
						</c:forEach>
						</select>
					</div>
	                
			</div>
			<input type="hidden" id="pid">
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">疫苗种类：</label>
	            <div class="col-sm-8">
                	<select class="form-control valid " id="name">
						<c:forEach items="${vacclist}" var="vac">
							<option value="${vac.id}" >${vac.vaccName}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-4 control-label text-right">批次厂家：</label>
	             <div class="col-sm-8">
                	<select class="form-control valid " id="batch">
					
					</select>
				</div>
			</div>
			<div class="form-group" style="display: none">
				<label class="col-sm-4 control-label text-right">厂家：</label>
	            <div class="col-sm-8">
                	<select class="form-control valid " id="manufacturer">
					
					</select>
				</div>
			</div>
			<div class="form-group" id="isnewDiv" style="display: none;">
				<label class="col-sm-4 control-label text-right">是否使用旧苗：</label>
	            <div class="col-sm-8">
	           		<input type="radio" name="isnew" id="isnew0" value="0"  class="i-checks " ><label for="isnew0">使用旧苗&nbsp;&nbsp;</label>
	           		<input type="radio" name="isnew" id="isnew1" value="1" checked="checked" class="i-checks " ><label for="isnew1">使用新苗&nbsp;&nbsp;</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-xs-4 control-label text-right">疫苗价格：</label>
	            <div class="col-sm-2">
                	<label id="price" style="font-weight: bold;"></label>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-12 text-right" >
					<input type="button" id="btn" value="确认完成" class="btn btn-success w100" >
					<input type="button" id="cancel" value="取消完成" class="btn btn-default w100" >
				</div>
			</div>
			<div class="warning" id="changeBatch" style="display: none;">
				完成批号修改，请修改小票批号信息
			</div>
		</div>
	</div>
</body>
</html>