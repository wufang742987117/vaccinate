<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>选择文章</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("input[name=id]").each(function(){
				var articleSelect = window.parent.articleSelect;
				if(articleSelect != null && articleSelect.length > 0)
				{
					for (var i=0; i<articleSelect.length; i++)
					{
						if (articleSelect[i][0]==$(this).val()){
							this.checked = true;
						}
					}
				}
				$(this).click(function()
				{
					var id = $(this).val(), title = $(this).attr("title");
					window.parent.articleSelectAddOrDel(id, title);
				});
			});
		});
		function view(href){
			layer.open({
				type: 2 //此处以iframe举例
				,title: '查看文章'
				,area: [$(document).width()-220+'px',$(document).height()-120+'px']
				,shade: 0.3
				,content: href
				,btn: ['关闭']
				,maxmin: true //开启最大化最小化按钮
				,btn3 : function(index)
				{
					layer.close(index);
				}
				,zIndex: layer.zIndex //重点1
				,success: function(layero){
					layer.setTop(layero); //重点2
				}
			});
//			top.$.jBox.open('iframe:'+href,'查看文章',$(top.document).width()-220,$(top.document).height()-120,{
//				buttons:{"关闭":true},
//				loaded:function(h){
//					$(".jbox-content", top.document).css("overflow","visible");
//					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
//				}
//			});
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<div class="ibox">
		<div class="ibox-content">
			<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/selectList" method="post" class="form-inline">
				<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">栏目：</span>
						<sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
										title="栏目" url="/cms/category/treeData" module="article" cssClass="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon gray-bg text-right">标题：</span>
						<form:input path="title" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="form-group">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				</div>
			</form:form>
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr><th style="text-align:center;">选择</th><th>栏目</th><th>标题</th><th>权重</th><th>点击数</th><th>发布者</th><th>更新时间</th></tr></thead>
				<tbody>
				<c:forEach items="${page.list}" var="article">
					<tr>
						<td style="text-align:center;"><input type="checkbox" name="id" value="${article.id}" title="${fns:abbr(article.title,40)}" /></td>
						<td>
							<%--<a href="javascript:" onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">--%>
							${article.category.name}
							<%--</a>--%>
						</td>
						<td>
							<%--<a href="${ctx}/cms/article/form?id=${article.id}" title="${article.title}" onclick="return view(this.href);">--%>
							${fns:abbr(article.title,40)}
							<%--</a>--%>
						</td>
						<td>${article.weight}</td>
						<td>${article.hits}</td>
						<td>${article.createBy.name}</td>
						<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="page">${page}</div>
			</div>
		</div>
	</div>
</body>
</html>