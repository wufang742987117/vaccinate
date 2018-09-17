<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>对帐管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/js/echarts.min.js"></script>
	<c:if test="${not empty arg }">
		<script type="text/javascript">			
			$(function(){
				layer.msg('你输入的年或者季度有误',{icon: 2});
			})
		</script>		
	</c:if>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
        
        $(function(){
			setBar();
			setPei();
		})
		
     function setBar() {
		var bar = echarts.init(document.getElementById('bar'));
		var barData = [${count0},${count1},${count2}];
		var category = ['登记台','微信','一体机'];
		
		var dateYear = new Date;
 		var year = dateYear.getFullYear(); 
 		var month = dateYear.getMonth()+1;
 		if("${reconciliationStock.timeYear}"){
 			year = "${reconciliationStock.timeYear}";
 		}
 		if("${reconciliationStock.timeMoon}"){
 			month = "${reconciliationStock.timeMoon}";
 		}
		option = {
			title: {
                text: '大类疫苗'+ year +'年'+ month +'月收入柱状图',
                x:'center',
                textStyle: {
                    color: 'black',
                    fontSize: 20,
                    fontStyle:'normal'
                }
            },
			xAxis: {
				data: category,
				axisLine: {
					lineStyle: {
						color: 'black',
						width: 1
					}
				},
				axisTick: {
					show: false
				},
				axisLabel:{
					textStyle:{
						fontSize:20
					}
				}
			},
			yAxis: [{
	            type: 'value',
	            name: '',
			    axisLabel: {
	                formatter: '{value}% ',
	                lineStyle: {
						color: 'black',
						width: 1
					}
	            },
	            axisTick: {
						show: true
					},
				axisLabel:{
					formatter:'{value}',
					textStyle:{
						color:'black',
						fontSize:20
					}
				},
				axisLine: {
					lineStyle: {
						color: 'black',
						width: 1
					}
				},
	        }],
			series: [{
				name: '疫苗每月收入柱状图',
				type: 'bar',
				barWidth: 20,
				label:{
					normal:{
						show:'true',
						position:"top",
						offset:[0,-5],
						formatter: '{c}',
						textStyle:{
							color:'black',
							fontSize:15
						}
					}
				},
				itemStyle: {
					normal: {
						color: '#4be8c6'
					}
				},
				data: barData
			}]
		};
		bar.setOption(option);
	}
	
	function setPei() {
        var pei = echarts.init(document.getElementById('top-pei'));
        var data = ${mapKey};
        if(data.length == 0){
        	data = [{value:1,name:'无'}];
        }
        var ndata = ${mapName};
        if(ndata.length == 0){
        	ndata = [{name:'无'}];
        }
        
        var dateYear = new Date;
 		var year = dateYear.getFullYear(); 
 		var month = dateYear.getMonth()+1;
		if("${reconciliationStock.timeYear}"){
 			year = "${reconciliationStock.timeYear}";
 		}
 		if("${reconciliationStock.timeMoon}"){
 			month = "${reconciliationStock.timeMoon}";
 		}
        option = {
        	title: {
                text: '大类疫苗'+ year +'年'+ month +'月消耗饼状图',
                x:'center',
                textStyle: {
                    color: 'black',
                    fontSize: 20,
                    fontStyle:'normal'
                }
            },
            legend: { /*图例*/
                itemGap: 10,
                orient: 'vertical',
                y: 'bottom',
                orient: 'horizontal',
                icon: 'circle',
                itemHeight: '20',
                itemGap: 10,
                selectedMode:false,
                textStyle: {
                    color: 'black',
                    fontSize: '12'
                },
                data: ndata
            },
            tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    visualMap: {
		        show: false,
		        min: 80,
		        max: 600,
		        inRange: {
		            colorLightness: [0, 1]
		        }
		    },
            series: [{
            	name:'大类名称',
                type: 'pie',
                radius: ['0%', '50%'],
                center:['50%', '50%'],
                label: {
                    normal: {
                    	position: 'outside',
                        formatter: '{d}%',
                        textStyle: {
                            color: 'black',
                            fontWeight: 'bold',
                            fontSize: 10,
                        }
                    }
                },
                data: data
            }]
        };

        pei.setOption(option);
    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="">对帐管理</a></li>
		<div class=" row pull-right">
			<a href="${ctx}/child_vaccinaterecord/childVaccinaterecord1" class="btn btn-success" ><span class="glyphicon glyphicon-arrow-left"></span>返回上一页</a>
		</div>
	</ul>
	<sys:message content="${message}"/>
	<div class="ibox">
		<div >
			<form:form id="searchForm" modelAttribute="reconciliationStock" action="${ctx}/reconciliation/reconciliationStock/reconciliationStock" method="post" class="form-inline mt20">
				<div class = "col-xs-11">
					<div class="form-group">
						<div class="input-group" >
							<form:input path="timeYear" placeholder="如2017" class="form-control number mr0 w100"/>
							<span style="font-size: 14px;line-height: 34px;margin-left: 5px;margin-right: 10px;">年</span>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group" >
							<form:select path="timeMoon" class="form-control mr0 w100">
								<form:option value="1">1</form:option>
								<form:option value="2">2</form:option>
								<form:option value="3">3</form:option>
								<form:option value="4">4</form:option>
								<form:option value="5">5</form:option>
								<form:option value="6">6</form:option>
								<form:option value="7">7</form:option>
								<form:option value="8">8</form:option>
								<form:option value="9">9</form:option>
								<form:option value="10">10</form:option>
								<form:option value="11">11</form:option>
								<form:option value="12">12</form:option>
							</form:select>
							<span style="font-size: 14px;line-height: 34px;margin-left: 5px;margin-right: 10px;">月</span>
						</div>	
					</div>
					<div class="form-group">
						<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" />
						<!-- <input id="btnExport" class="btn btn-primary" style="margin-left: 50px" type="button" value="导出报表"/> -->
					</div>
				</div>
			</form:form>
			<sys:message content="${message}"/>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<th>疫苗名称</th>
						<th>登记台接种总金额</th>
						<th>微信接种总金额</th>
						<th>一体机接种总金额</th>
						<th>接种总金额</th>
					</tr>
				</thead>
				<tbody>
				<c:forEach items="${list}" var="reconciliationStock">
					<tr>
						<td>
							${reconciliationStock.gname}
						</td>
						<td>
							${reconciliationStock.price}
						</td>
						<td>
							${reconciliationStock.price1}
						</td>
						<td>
							${reconciliationStock.price2}
						</td>
						<td>
							${reconciliationStock.pricesum}
						</td>
					</tr>
				</c:forEach>
					<tr>
						<td><strong>合计</strong></td>
						<td>${count0}</td>
						<td>${count1}</td>
						<td>${count2}</td>
						<td>${count3}</td>
					</tr>
				</tbody>
			</table>
		</div>
		<div>
			<div id="bar" style="height: 400px;width: 400px;display: inline-block;"></div>
			<div id="top-pei" style="height: 400px;width: 400px;display: inline-block;"></div>
		</div>
	</div>
</body>
</html>