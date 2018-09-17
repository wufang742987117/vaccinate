<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>内容管理</title>
	<%@include file="/WEB-INF/views/include/head1.jsp" %>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
	<div class="row animated fadeInRight">
		<div class="col-sm-3" >
			<div class="form-group" style="padding: 10px 0 0 10px;">
			<a>栏目列表</a>
			</div>
			<div id="ztree" class="ztree"></div>

		</div>
		<div class="col-sm-9" >
			<iframe id="officeContent" src="${ctx}/cms/none" width="100%" height="91%" frameborder="0"></iframe>
		</div>
	</div>
	<script type="text/javascript">
		var setting = {view:{selectedMulti:false},data:{simpleData:{enable:true}},
			callback:{onClick:function(event, treeId, treeNode)
			{
				var module = treeNode.module?treeNode.module:'none';
				$('#officeContent').attr("src","${ctx}/cms/"+module+"/?category.id="+treeNode.id);
			}
			}
		};
		function aa (){
			$.getJSON("${ctx}/cms/treeData",function(data) {

				$.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
			});
		}
		 aa();



	</script>
</body>
</html>