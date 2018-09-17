<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="renderer" content="webkit">
	<meta http-equiv="Cache-Control" content="no-siteapp" />

	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<meta name="author" content="http://jeesite.com/"/>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10" />
	<meta http-equiv="Expires" content="0">
	<meta http-equiv="Cache-Control" content="no-cache">

	<%@include file="/WEB-INF/views/include/head1.jsp" %>

	<!--[if lt IE 9]>
	<meta http-equiv="refresh" content="0;ie.html" />
	<![endif]-->
	<title>${fns:getConfig('productName')}</title>
	<c:set var="tabmode" value="${empty cookie.tabmode.value ? '1' : cookie.tabmode.value}"/>


</head>
<body class="fixed-sidebar full-height-layout gray-bg text-right" style="overflow:hidden">
<div id="wrapper">
	<!--左侧导航开始-->
	<nav class="navbar-default navbar-static-side" role="navigation">
		<div class="nav-close"><i class="fa fa-times-circle"></i>
		</div>
		<div class="sidebar-collapse">
			<ul class="nav" id="side-menu">
				<li class="nav-header" style="background:url(''); ">
					<div class="dropdown profile-element">
						<span><img alt="image" class="img-circle" src="${fns:getUser().photo}" style="width: 100px;height: 100px"/></span>
						<a data-toggle="dropdown" class="dropdown-toggle" href="#">
							<span class="clear">
						    <span class="block m-t-xs"><strong class="font-bold">${fns:getUser().name}</strong></span>
							<span class="text-muted text-xs block">${fns:getUser().name}<b class="caret"></b></span>
							</span>
						</a>
						<ul class="dropdown-menu animated fadeInRight m-t-xs">
							<li><a class="J_menuItem" href="${ctx}/sys/user/info">个人资料</a>
							</li>
							<li><a class="J_menuItem" href="${ctx}/sys/user/modifyPwd">修改密码</a>
							</li>
							<li><a class="J_menuItem" href="${ctx}/oa/oaNotify/self">我的通知</a>
							</li>
							<li class="divider"></li>
							<li><a href="${ctx}/logout">安全退出</a>
							</li>
						</ul>
					</div>
					<div class="logo-element">YL
					</div>
				</li>
				<c:set var="menuList" value="${fns:getMenuList()}"/>
				<c:set var="num" value="0"/>
				<c:set var="firstUrl" value="/404"/>

				<c:forEach items="${menuList}" var="menu" varStatus="idxStatus">
					<c:if test="${menu.parent.id eq '1'&& menu.isShow eq '1'}">
						<c:set var="num" value="${num+1}"></c:set>
						<c:if test="${num == 1}">
							<li class="active">
						</c:if>
						<c:if test="${num != 1}">
							<li>
						</c:if>
							<c:if test="${empty menu.href}">
								<a href="javascript:void(0);">
									<i class="fa fa-${menu.icon}"></i>
									<span class="nav-label">${menu.name}</span>
									<span class="fa arrow"></span>
								</a>
								<ul class="nav nav-second-level">
									<c:set var="num1" value="0"/>
									<c:forEach items="${menuList}" var="menu1" varStatus="menu1Status">
										<c:if test="${menu1.parent.id eq (not empty menu.id ? menu.id:1) && menu1.isShow eq '1'}">
											<c:set var="num1" value="${num1+1}"></c:set>
											<c:choose>
												<c:when test="${num ==1 && num1 == 1}">
													<li class="active">
													<c:set var="firstUrl" value="${fn:indexOf(menu1.href, '://') eq -1 ? ctx : ''}${not empty menu1.href ? menu1.href : '/404'}"></c:set>
												</c:when>
												<c:otherwise>
													<li>
												</c:otherwise>
											</c:choose>
											<c:if test="${empty menu1.href}">
												<a href="javascript:void(0);">
													<i class="fa fa-${menu1.icon}"></i>
														<%--${menu1.name}--%>
													<span class="nav-label">${menu1.name}</span>
													<span class="fa arrow"></span>
												</a>
												<ul class="nav nav-third-level">
													<c:set var="num2" value="0"/>
													<c:forEach items="${menuList}" var="menu2">
														<c:if test="${menu2.parent.id eq (not empty menu1.id ? menu1.id:1)&&menu2.isShow eq '1'}">
															<c:set var="num2" value="${num2+1}"></c:set>
															<c:choose>
																<c:when test="${num ==1 && num1 == 1 && num2 ==1}">
																	<c:set var="firstUrl" value="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}"></c:set>
																</c:when>
															</c:choose>
															<li>
																<a class="J_menuItem" href="${fn:indexOf(menu2.href, '://') eq -1 ? ctx : ''}${not empty menu2.href ? menu2.href : '/404'}">
																	<%--${menu2.name}--%>
																	<i class="fa fa-${menu2.icon}"></i>
																	<span class="nav-label">${menu2.name}</span>
																</a>
															</li>
														</c:if>
													</c:forEach>
												</ul>
											</c:if>
											<c:if test="${not empty menu1.href}">
												<a class="J_menuItem" href="${fn:indexOf(menu1.href, '://') eq -1 ? ctx : ''}${not empty menu1.href ? menu1.href : '/404'}">
													<i class="fa fa-${menu1.icon}"></i> <span class="nav-label">${menu1.name}</span>
												</a>
											</c:if>
											</li>
										</c:if>
									</c:forEach>
								</ul>
							</c:if>
							<c:if test="${not empty menu.href}">
								<a class="J_menuItem" href="${fn:indexOf(menu.href, '://') eq -1 ? ctx : ''}${menu.href}">
									<i class="fa fa-${menu.icon}"></i>
									<span class="nav-label">${menu.name}</span>
								</a>
							</c:if>
						</li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</nav>
	<!--左侧导航结束-->
	<!--右侧部分开始-->
	<div id="page-wrapper" class="gray-bg text-right dashbard-1">
		<div class="row border-bottom">
			<nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
				<div class="navbar-header"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
				</div>
				<ul class="nav navbar-top-links navbar-right">
					<li class="dropdown">
						<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
							<i class="fa fa-envelope"></i> <span class="label label-warning">0</span>
						</a>
						<ul class="dropdown-menu dropdown-messages">
							<li class="m-t-xs">
								<div class="dropdown-messages-box">
									<a href="#" class="pull-left">
										<img alt="image" class="img-circle" src="${ctxStatic}/img/a7.jpg">
									</a>
									<div class="media-body">
										<small class="pull-right">46小时前</small>
									</div>
								</div>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
							<i class="fa fa-bell"></i> <span class="label label-primary">0</span>
						</a>
					</li>
					<li class="dropdown hidden-xs">
						<a class="right-sidebar-toggle" aria-expanded="false">
							<i class="fa fa-tasks"></i> 主题
						</a>
					</li>
				</ul>
			</nav>
		</div>
		<div class="row content-tabs">
			<button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
			</button>
			<nav class="page-tabs J_menuTabs">
				<div class="page-tabs-content">
					<a href="javascript:;" class="active J_menuTab" data-id="firstMenu">首页</a>
				</div>
			</nav>
			<button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
			</button>
			<div class="btn-group roll-nav roll-right">
				<button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span></button>
				<ul role="menu" class="dropdown-menu dropdown-menu-right">
					<li class="J_tabShowActive"><a>定位当前选项卡</a>
					</li>
					<li class="divider"></li>
					<li class="J_tabCloseAll"><a>关闭全部选项卡</a>
					</li>
					<li class="J_tabCloseOther"><a>关闭其他选项卡</a>
					</li>
				</ul>
			</div>
			<a href="${ctx}/logout" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
		</div>
		<div class="row J_mainContent" id="content-main">
			<iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="${firstUrl}" frameborder="0" data-id="firstMenu" seamless></iframe>
		</div>
		<div class="footer">
			<div class="pull-right">&copy; 2014-2015 <a href="" target="_blank"></a>
			</div>
		</div>
	</div>
	<!--右侧部分结束-->
	<!--右侧边栏开始-->
	<div id="right-sidebar">
		<div class="sidebar-container">

			<ul class="nav nav-tabs navs-3">

				<li class="active">
					<a data-toggle="tab" href="#tab-1">
						<i class="fa fa-gear"></i> 主题
					</a>
				</li>
				<li class=""><a data-toggle="tab" href="#tab-2">
					通知
				</a>
				</li>
				<li><a data-toggle="tab" href="#tab-3">
					项目进度
				</a>
				</li>
			</ul>

			<div class="tab-content">
				<div id="tab-1" class="tab-pane active">
					<div class="sidebar-title">
						<h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
						<small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
					</div>
					<div class="skin-setttings">
						<div class="title">主题设置</div>
						<div class="setings-item">
							<span>收起左侧菜单</span>
							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
									<label class="onoffswitch-label" for="collapsemenu">
										<span class="onoffswitch-inner"></span>
										<span class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
							<span>固定顶部</span>

							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
									<label class="onoffswitch-label" for="fixednavbar">
										<span class="onoffswitch-inner"></span>
										<span class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="setings-item">
                                <span>
                        固定宽度
                    </span>

							<div class="switch">
								<div class="onoffswitch">
									<input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
									<label class="onoffswitch-label" for="boxedlayout">
										<span class="onoffswitch-inner"></span>
										<span class="onoffswitch-switch"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="title">皮肤选择</div>
						<div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="#" class="s-skin-0">
							 默认皮肤
						 </a>
                    </span>
						</div>
						<div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-1">
							蓝色主题
						</a>
                    </span>
						</div>
						<div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-3">
							黄色/紫色主题
						</a>
                    </span>
						</div>
					</div>
				</div>
				<div id="tab-2" class="tab-pane">

				</div>
				<div id="tab-3" class="tab-pane">

				</div>
			</div>

		</div>
	</div>
	<!--右侧边栏结束-->


</div>

</body>
</html>