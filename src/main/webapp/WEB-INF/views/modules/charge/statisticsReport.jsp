<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>划价收费系统</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxBase}/static/common/jeesite.js"></script>
<script src="${ctxStatic}/js/fangwu/template.js"></script>
<style type="text/css">
.ml20 {
	margin-left:20px;
}
#contentVaccine input,#contentcharges input {
	width:50px;
}
input:-webkit-autofill,
input:-webkit-autofill:hover,
input:-webkit-autofill:focus {
box-shadow:0 0 0 60px #eee inset;
-webkit-text-fill-color: #878787;
}
.table>tbody>tr>td, .table>tbody>tr>th, .table>tfoot>tr>td, .table>tfoot>tr>th, .table>thead>tr>td, .table>thead>tr>th {
    font-size: 16px!important;
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a data-index="2">发票统计</a></li>
		<li style="display: none;"><a data-index="1">疫苗使用详情</a></li>
		<div class="row pull-right">
			<a id="quit" href="javascript:history.go(-1);" class="btn btn-success"><span class="glyphicon glyphicon-arrow-left"></span>返回</a>&emsp;&emsp;
		</div>
	</ul>
	
	<div>
        <div class="searchForm form-inline mt20  hide">
				<div class="form-group" >
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">创建时间(起)：</span>
						<input name="beginVaccinedate" id="beg" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">创建时间(止)：</span>
						<input name="endVaccinedate" id="end" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" />
					</div>
				</div>
				<div class="form-group">
					<input id="btnSearchVaccine" data-type="1" class="btn btn-primary btn-vaccine w100" readonly value="查询"/>
					<input id="prientVaccine" data-type="0" class="btn btn-success btn-vaccine w100 ml20" readonly value="打印"/>
				</div>
				
				<div id="contentVaccine">
				</div>
				<script id="modInform" type="text/html">
				<div id="printVaccine">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>疫苗名称</th>
							<th>疫苗批次</th>
							<th>疫苗厂商</th>
							<th>使用数量</th>
							<th>单价(元)</th>
							<th>时间</th>
						</tr>
					</thead>
				    <tbody>
					   {{if $data.isPrint=="false"}}
				       {{each $data.vaccines.list as value i}}
						<tr>
							<td>{{value.vaccName}}</td>
							<td>{{value.vaccBatchnum}}</td>
							<td>{{value.vaccManufacturer}}</td>
							<td>{{value.vaccCount}}</td>
							<td>{{value.vaccPrice}}</td>
							<td>{{value.createDate}}</td>
						</tr>
						{{/each}}
						{{else}}
						{{each $data.vaccines as value i}}
						<tr>
							<td>{{value.vaccName}}</td>
							<td>{{value.vaccBatchnum}}</td>
							<td>{{value.vaccManufacturer}}</td>
							<td>{{value.vaccCount}}</td>
							<td>{{value.vaccPrice}}</td>
							<td>{{value.createDate}}</td>
						</tr>
						{{/each}}
						{{/if}}
				    </tbody>
				</table>
				<div class="pages"></div>
				</div>
				</script>
		</div>
		
		<div class="chargeForm form-inline mt20">
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">收银员：</span>
						<select class="form-control" id="cashierList">
							<option value="0" data-id="0">全部</option>
							<script id="modInformcashier" type="text/html">
								<option value="0" data-id="0">全部</option>
								{{each $data.users as value i}}
									<option data-id={{value.id}}>{{value.name}}</option>
								{{/each}}
							</script>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">发票状态：</span>
						<select id="status" class="form-control" style="width:100px">
							<option data-status="10">全部</option>
							<option data-status="1">正常</option>
							<option data-status="0">作废</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">开票状态：</span>
						<select id="billingStatus" class="form-control" style="width:100px">
						    <option data-status="10">全部</option>
							<option data-status="1">已开票</option>
							<option data-status="0">未开票</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">业务流水号：</span>
						<input name="transaction" id="billNum"  type="text" onkeyup="this.value=this.value.replace(/\D/g,'')" class="form-control"/>
					</div>
				</div>
				<div class="form-group" >
					<div class="input-group" >
						<span class="input-group-addon gray-bg text-right">创建时间(起)：</span>
						<input name="beginChargedate" id="begCharge" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" />
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">创建时间(止)：</span>
						<input name="endChargedate" id="endCharge" onclick="laydate({istime: true, format: 'YYYY-MM-DD hh:mm:ss'})" class="laydate-icon form-control layer-date" />
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary w100 btn-charge" data-type="1" readonly value="查询"/>
					<input id="prientCharge" class="btn btn-success w100 ml20 btn-charge" data-type="0" readonly value="打印"/>
				</div>
				
				<div id="contentcharges">
				</div>
				<script id="modInformCharge" type="text/html">
				<table id="contentTable" class="table table-striped table-bordered table-condensed" style="text-align:center">
					<thead>
						<tr>
							<th>序号</th>
							<th>姓名</th>
							<th>业务流水号</th>
							<th>疫苗数量</th>
							<th>总额(元)</th>
							<th>缴费时间</th>
							<th>开票人</th>
							<th>状态</th>
							<th>开票</th>
							<th>备注</th>
						</tr>
					</thead>
				     <tbody>
						{{each $data.chargeLogs.list as value i}}
						<tr>
							<td>{{i+1}}</td>
							<td>{{value.patientName}}</td>
							<td>{{value.billNum}}</td>
							<td>{{value.vaccCount}}</td>
							<td>{{value.sumPrice}}</td>
							<td>{{value.createDate}}</td>
							<td>{{value.createByName }}</td>
							<td>
								{{if value.status==0}}
									<span style="color:red">作废<span>
								{{else}}
									<span style="color:green">正常<span>
								{{/if}}
							</td>
							<td>
								{{if value.billing==0}}
									<span style="color:red">未开票</span>
								{{else}}
									<span style="color:green">已开票</span>
								{{/if}}
							</td>
							<td>{{value.remarks}}</td>
						</tr>
						{{/each}}
				 </tbody>
			</table>
			</script>
		</div>
	</div>
	
	<script>
	
	var pageType=1;
	var begVaccine='';
	var endVaccine='';
	var begCharge='';
	var endCharge='';
	var selectId=0;
	var userID=0;
	var now='';
	var pageSize=20;
	
	$(".nav-tabs li a").click(function(){
		$(this).parent().addClass("active").siblings().removeClass('active');
		var type=$(this).attr("data-index");
		if(type==1) {
			$(".searchForm").removeClass("hide");
			$(".chargeForm").addClass("hide");
			detailVaccineShow();
		}else {
			$(".chargeForm").removeClass("hide")
			$(".searchForm").addClass("hide")
			list(); 
		}
	});
	
	$("#billNum").blur(function(){
		var len=$(this).val().length;
		 if(len>10) {
			 layer.msg('业务流水号为十位数', {icon: 7});
			 $("#billNum").val('');
			 $("#billNum").focus();
		 }
	});
	
	function detailVaccineShow() {
		$.ajax({
            url:'${ctx}/charge/findChargeVaccine',
            type: 'POST',
            dataType: 'json',
            success:function(data) {
            	  var html = template('modInform', data);
                  document.getElementById('contentVaccine').innerHTML = html; 
                  var pageContent=data.vaccines.html;
                  $('.pages').html(pageContent);
                  pageType=1;
            }
		})
	}
	
	var type=1;
	$(".btn-vaccine").click(function(){
		$window=window;
		var type=$(this).attr("data-type");
		var beg=$("input[name=beginVaccinedate]").val();
		var end=$("input[name=endVaccinedate]").val();
		begVaccine=beg;
		endVaccine=end;
		sessionStorage.setItem("beg", beg);
		sessionStorage.setItem("end", end); 
		sessionStorage.setItem("type", 0); 
		/* if(type==0) {
			var newstr = document.getElementById("printVaccine").innerHTML; 
			var oldstr = document.body.innerHTML; 
			document.body.innerHTML = newstr; 
			document.body.
			window.print(); 
			document.body.innerHTML = oldstr; 
			return false;
		} */
		$.ajax({
            url:'${ctx}/charge/findChargeVaccine',
            type: 'POST',
            dataType: 'json',
            data:{
            	"beg":beg,
            	"end":end,
            	"type":type,
            	"pageNo":1,
            	"pageSize":pageSize
            },
            success:function(data) {
            	
             /* var data={"data":data.vaccines}; */
             	if(type==1){
             		var html = template('modInform', data);
                    document.getElementById('contentVaccine').innerHTML = html; 
                    var pageContent=data.vaccines.html;
                    $('#contentVaccine').append(pageContent);
                    pageType=1;
             	}else {

             	}
            }
        })
	});
	$(".btn-charge").click(function(){
		detailShow();
	})
	
	function list() {
		$("#begCharge").val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
		$("#endCharge").val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));
		$.ajax({
			url:'${ctx}/charge/getUsers',
            type: 'POST',
            dataType: 'json',
            success:function(data) {
            	
            	userID=data.loginUser;
            	typeId=userID;
            	var html = template('modInformcashier', data);
                document.getElementById('cashierList').innerHTML = html;
            	
            	$('#cashierList option').each(function(){   
	               	if( $(this).attr("data-id")==userID){   
	               		$(this).attr("selected",true);
               		} 
                })
                detailShow();
            }
		})
	}
	list();
	
	function detailShow () {
		$("#beg").val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
		$("#end").val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));
		typeId=$("#cashierList>option:selected").attr("data-id");
		var billNum=$("input[name=transaction]").val();
		billNum=$.trim(billNum);
		var beginChargedate=$("input[name=beginChargedate]").val();
		var endChargedate=$("input[name=endChargedate]").val();
		selectId=typeId;
		sessionStorage.setItem("beg", beginChargedate);
		sessionStorage.setItem("end", endChargedate); 
		sessionStorage.setItem("type", 0); 
		sessionStorage.setItem("createById",typeId); 
		begCharge=beginChargedate;
		endCharge=endChargedate;
		var status=$("#status>option:selected").attr("data-status");
		var billingStatus=$("#billingStatus>option:selected").attr("data-status");
		var status= parseInt(status);
		var bsChargeLog={};
		bsChargeLog.status=status
		bsChargeLog.createById=typeId
		bsChargeLog.billNum=billNum
		bsChargeLog.beginChargedate=beginChargedate
		bsChargeLog.endChargedate=endChargedate
		bsChargeLog.billing=billingStatus
		bsChargeLog.pageSize=pageSize;
		bsChargeLog.pageNo=1;
		$.ajax({
            url:'${ctx}/charge/chargeReport',
            type: 'POST',
            dataType: 'json',
            data:bsChargeLog,
            success:function(data) {
            	userId=data.loginUser;
            	
            	
                
                var htmlCharge = template('modInformCharge', data);
                document.getElementById('contentcharges').innerHTML = htmlCharge;
                var pageContent=data.chargeLogs.html;
                $('#contentcharges').append(pageContent);
                
               $('#cashierList option').each(function(){   
	               	if( $(this).attr("data-id")==selectId){   
	               	$(this).attr("selected",true);
               		} 
                })
                pageType=2;
            }
        })
	}
	
	
	function page(n,s){
		var pages=n;
		if(pageType==1) {
			$.ajax({
				url:"${ctx}/charge/findChargeVaccine",
	            type: 'POST',
	            dataType: 'json',
	            data:{
	               	'pageNo':pages,
	               	'pageSize':pageSize,
	               	"beg":begVaccine,
	            	"end":endVaccine,
	            	"type":1
	            },
	           success:function(data) {
	        	   var html = template('modInform', data);
	               document.getElementById('contentVaccine').innerHTML = html; 
	               var pageContent=data.vaccines.html;
	               $('.pages').html(pageContent);
                   pageType=1;
	           }
			})
		}else if(pageType==2) {
			var billNum=$("input[name=transaction]").val();
			var status=$("#status>option:selected").attr("data-status");
			$.ajax({
				url:"${ctx}/charge/chargeReport",
	            type: 'POST',
	            dataType: 'json',
	            data:{
	               	'pageNo':pages,
	               	'pageSize':pageSize,
	               	"beginChargedate":begCharge,
	            	"endChargedate":endCharge,
	            	"type":1,
	            	"createById":typeId,
	            	"billNum":billNum,
	            	"status":status
	            	
	            },
	           
	           success:function(data) {
	               var htmlCharge = template('modInformCharge', data);
	               document.getElementById('contentcharges').innerHTML = htmlCharge;
	               var pageContent=data.chargeLogs.html;
	               $('#contentcharges').append(pageContent);
	               pageType=2;
	           }
			})
		}
		return false;
	}
	
	$('#prientVaccine').click(function(){
		layer.open({
		  type: 2,
		  title: '疫苗使用详情',
		  shadeClose: true,
		  shade: 0.8,
		  area: ['1000px', '90%'],
		  content: '${ctx}/charge/findVaccTable' //iframe的url
		}); 
	})
	
	$("#prientCharge").click(function(){
		layer.open({
			  type: 2,
			  title: '发票统计详情',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['650px', '650px'],
			  content: '${ctx}/charge/findChargeTable' //iframe的url
			}); 
		})

	$("#beg,#begCharge").val(laydate.now(0, 'YYYY-MM-DD 00:00:00'));
	$("#endbegCharge,#endCharge").val(laydate.now(0, 'YYYY-MM-DD 23:59:59'));

	/* $("#quit").click(function(){
		windows.clo
	}) */
</script>
</body>
</html>
