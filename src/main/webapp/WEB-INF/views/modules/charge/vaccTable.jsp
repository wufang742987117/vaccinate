<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>划价收费系统</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${ctxBase}/static/common/jeesite.js"></script>
<script src="${ctxStatic}/js/fangwu/template.js"></script>
</head>
<style>
	table {
		width:100%!important;
	}
</style>
<body>
	<div id="printVaccine">
		
	<!-- 	<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th>疫苗名称</th>
					<th>疫苗批次</th>
					<th>疫苗厂商</th>
					<th>使用数量</th>
					<th>单价</th>
					<th>时间</th>
				</tr>
			</thead>
		</table> -->
	</div>
			<script id="modInform" type="text/html">
				<div id="printVaccine">
				<h3 style="text-align:center;">
					划价收费系统疫苗详情报表
				</h3>
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>疫苗名称</th>
							<th>疫苗批次</th>
							<th>疫苗厂商</th>
							<th>使用数量</th>
							<th>单价</th>
							<th>时间</th>
						</tr>
					</thead>
				    <tbody>
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
						
		
				    </tbody>
				</table>
				<div>查询日期：{{$data.beg}}~{{$data.end}}</div>
				</script>

	<script>
	var beg = sessionStorage.getItem("beg");
	var end = sessionStorage.getItem("end");
	var type = sessionStorage.getItem("type")
	function detailTab () {
		$.ajax({
            url:'${ctx}/charge/findChargeVaccine',
            type: 'POST',
            dataType: 'json',
            data:{
            	"beg":beg,
            	"end":end,
            	"type":0
            },
            success:function(data) {
            	var html = template('modInform', data);
                document.getElementById('printVaccine').innerHTML = html; 
            }
        })
	}
	
	detailTab();
	
	setTimeout(function(){
		window.print();
		setTimeout("top.opener=null;var index_ss = parent.layer.getFrameIndex(window.name);parent.layer.close(index_ss);",100);
	}, 1000);
	
	$(document).keyup(function(event){
	  if(event.keyCode ==13){
		  window.print();
	  }
	});
	
</script>
</body>
	
</html>
