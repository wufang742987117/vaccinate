<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<style>
			*{
				box-sizing: border-box;
			}
			table{
				width: 100%;
			}
			td{
				height: 30px;
				text-align: center;
			}
			.grid{
				width: 420px;
				vertical-align: top;
				float: left;
			}
			.grid-split{
				width: 10px;
				height: 10px;
			}
			.clr{
				clear: both;
			}
			.nameInfoContainer{
				position: relative;
				margin-top: 10px;
			}
			.nameInfo{
				
				width: 200px;
				position: absolute;
				right: 100px;
				top: 0;
			}
			.title-1{
			line-height: 40px;}
		</style>
	</head>
<script type="text/javascript">

</script>
	<body>
		<div style="width: 860px;margin: 0 auto;">
			<h1 style="text-align: center;">
			淮北市儿童入园、入学预防接种证查验证明
			</h1>
			<div style="font-size: 150%;text-align: center;">
				姓名：${childBaseinfo.childname }&emsp;${fns:getDictLabel(childBaseinfo.gender, 'sex', '')}&emsp;<fmt:formatDate
						value="${childBaseinfo.birthday}" pattern="yyyy-MM-dd" />&emsp;父/母姓名：
						<c:if test="${childBaseinfo.father!=null }">
						${childBaseinfo.father }/
						</c:if>${childBaseinfo.guardianname}
						
			</div>
			<br>
			<div style="font-size: 110%;text-align: center;">
			经检查接种记录,已完成以下疫苗接种(见表一，表二)，还有以下疫苗未完成接种(见表三)，特此证明。
			</div>
			<br>
			<div>
				<div class="grid">
					<div class="title-1">一类疫苗：&emsp;&emsp;(表一)</div>
					<table cellpadding="0" cellspacing="0" border="1">
						<tr>
							<td width="20%">接种疫苗</td>
							<td>剂次</td>
							<td>接种日期</td>
							<td>备注</td>
						</tr>
							<c:forEach items="${list}" var="Nursery">
								<c:forEach items="${Nursery}" var="Nursery1">
									<tr>
									<c:if test="${Nursery1.size!=0}">
										<td rowspan="${Nursery1.size}">${Nursery1.name }</td>
										</c:if>
										<td>${Nursery1.pin}</td>
										<td>${Nursery1.vaccinatedate}</td>
										<td>${Nursery1.vaccname}</td>
									</tr>
								</c:forEach>
							</c:forEach>
					</table>
				</div>
				<div class="grid grid-split"></div>
				<div class="grid" >
					<div class="title-1">二类疫苗：&emsp;&emsp;(表二)</div>
					<table cellpadding="0" cellspacing="0" border="1">
						<tr>
							<td width="20%">接种疫苗</td>
							<td>剂次</td>
							<td>接种日期</td>
							<td>备注</td>
						</tr>
						<c:forEach items="${list1}" var="Nursery">
								<c:forEach items="${Nursery}" var="Nursery1">
									<tr>
									<c:if test="${Nursery1.size!=0}">
										<td rowspan="${Nursery1.size}">${Nursery1.name }</td>
										</c:if>
										<td>${Nursery1.pin}</td>
										<td>${Nursery1.vaccinatedate}</td>
										<td>${Nursery1.vaccname}</td>
									</tr>
								</c:forEach>
							</c:forEach>
					</table>
				</div>
				<div class="clr"></div>
			</div>
			<br>
			<div class="title-1">需补种疫苗：&emsp;&emsp;(表三)</div>
			<table cellpadding="0" cellspacing="0" border="1">
				<tr>
					<td>需补种疫苗</td>
					<td>剂次</td>
					<td>预约时间</td>
					<td>补种时间</td>
					<td>疫苗批号</td>
					<td>接种者签名</td>
				</tr>
				<c:forEach items="${list2}" var="Nursery">
								<c:forEach items="${Nursery}" var="Nursery1">
									<tr>
									<c:if test="${Nursery1.size!=0}">
										<td rowspan="${Nursery1.size}">${Nursery1.name }</td>
										</c:if>
										<td>${Nursery1.pin}</td>
										<td><fmt:formatDate value="${Nursery1.remindDate }" pattern="yyyy-MM-dd"/> </td>
										<td></td>
										<td></td>
										<td></td>
									</tr>
								</c:forEach>
							</c:forEach>
			</table>
			<div class="nameInfoContainer">
				<div class="nameInfo" >
					<div>验证人(签名):${fns:getUser().name}</div>
					<br>
					<div>验证单位(盖章):</div>
					<br>
					<div>验证日期：${date}</div>
				</div>
			</div>
		</div>
	</body>
<script>
	  window.print(); 
	  setTimeout("top.opener=null;window.close()",1);  
</script>
</html>