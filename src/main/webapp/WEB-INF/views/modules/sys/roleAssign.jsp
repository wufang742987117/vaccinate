<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配角色</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/role/">角色列表</a></li>
		<li class="active"><a href="${ctx}/sys/role/assign?id=${role.id}"><shiro:hasPermission name="sys:role:edit">角色分配</shiro:hasPermission><shiro:lacksPermission name="sys:role:edit">人员列表</shiro:lacksPermission></a></li>
	</ul>
	<div class="ibox">
		<div class="ibox-content">
			<div class="form-group">
				<label class="control-label">角色名称:<b>${role.name}</b></label>
				<label class="control-label">归属机构:${role.name}</label>
				<label class="control-label">英文名称:${role.name}</label>
			</div>
			<div class="form-group">
				<label class="control-label">角色类型: ${role.roleType}</label>
				<c:set var="dictvalue" value="${role.dataScope}" scope="page" />
				<label class="control-label">数据范围: ${fns:getDictLabel(dictvalue, 'sys_data_scope', '')}</label>
			</div>

			<sys:message content="${message}"/>
			<div class="breadcrumb">
				<form id="assignRoleForm" action="${ctx}/sys/role/assignrole" method="post" class="hide">
					<input type="hidden" name="id" value="${role.id}"/>
					<input id="idsArr" type="hidden" name="idsArr" value=""/>
				</form>
				<div class="form-group">
					<input id="assignButton" class="btn btn-primary" type="submit" value="分配角色"/>
				</div>
				<script type="text/javascript">
					$("#assignButton").click(function(){

						layer.open({
							type: 2 //此处以iframe举例
							,title: '分配角色'
							,area: ['810px',$(top.document).height()-240 + 'px']
							,shade: 0.3
							,content: "${ctx}/sys/role/usertorole?id=" + ${role.id}
							,btn: ['确定分配','关闭','清除已选']
							,yes: function(index)
							{
								var pre_ids = window.frames[0].pre_ids;
								var ids = window.frames[0].ids;
								// 删除''的元素
								if(ids[0]==''){
									ids.shift();
									pre_ids.shift();
								}
								if(pre_ids.sort().toString() == ids.sort().toString()){
									toastrMsg("未给角色【${role.name}】分配新成员！", 'info');
									return false;
								};
								// 执行保存
								loading('正在提交，请稍等...');
								var idsArr = "";
								for (var i = 0; i<ids.length; i++) {
									idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
								}
								$('#idsArr').val(idsArr);
								$('#assignRoleForm').submit();
								layer.close(index);
							},
							btn3 : function(index,layero)
							{
								window[layero.find('iframe')[0]['name']].clearAssign();
							}
							,zIndex: layer.zIndex //重点1
							,success: function(layero){
								layer.setTop(layero); //重点2
							}
						});


						<%--top.$.jBox.open("iframe:${ctx}/sys/role/usertorole?id=${role.id}", "分配角色",810,$(top.document).height()-240,{--%>
							<%--buttons:{"确定分配":"ok", "清除已选":"clear", "关闭":true}, bottomText:"通过选择部门，然后为列出的人员分配角色。",submit:function(v, h, f){--%>
								<%--var pre_ids = h.find("iframe")[0].contentWindow.pre_ids;--%>
								<%--var ids = h.find("iframe")[0].contentWindow.ids;--%>
								<%--//nodes = selectedTree.getSelectedNodes();--%>
								<%--if (v=="ok"){--%>
									<%--// 删除''的元素--%>
									<%--if(ids[0]==''){--%>
										<%--ids.shift();--%>
										<%--pre_ids.shift();--%>
									<%--}--%>
									<%--if(pre_ids.sort().toString() == ids.sort().toString()){--%>
										<%--top.$.jBox.tip("未给角色【${role.name}】分配新成员！", 'info');--%>
										<%--return false;--%>
									<%--};--%>
									<%--// 执行保存--%>
									<%--loading('正在提交，请稍等...');--%>
									<%--var idsArr = "";--%>
									<%--for (var i = 0; i<ids.length; i++) {--%>
										<%--idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');--%>
									<%--}--%>
									<%--$('#idsArr').val(idsArr);--%>
									<%--$('#assignRoleForm').submit();--%>
									<%--return true;--%>
								<%--} else if (v=="clear"){--%>
									<%--h.find("iframe")[0].contentWindow.clearAssign();--%>
									<%--return false;--%>
								<%--}--%>
							<%--}, loaded:function(h){--%>
								<%--$(".jbox-content", top.document).css("overflow-y","hidden");--%>
							<%--}--%>
						<%--});--%>
					});
				</script>
			</div>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th>归属公司</th><th>归属部门</th><th>登录名</th><th>姓名</th><th>电话</th><th>手机</th><shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission></tr></thead>
				<tbody>
				<c:forEach items="${userList}" var="user">
					<tr>
						<td>${user.company.name}</td>
						<td>${user.office.name}</td>
						<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
						<td>${user.name}</td>
						<td>${user.phone}</td>
						<td>${user.mobile}</td>
						<shiro:hasPermission name="sys:role:edit"><td>
							<a href="${ctx}/sys/role/outrole?userId=${user.id}&roleId=${role.id}"
								onclick="return confirmx('确认要将用户<b>[${user.name}]</b>从<b>[${role.name}]</b>角色中移除吗？', this.href)">移除</a>
						</td></shiro:hasPermission>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>
