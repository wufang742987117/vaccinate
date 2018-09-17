<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>
<i id="${id}Icon" class="icon-${not empty value?value:' hide'}"></i>&nbsp;<label id="${id}IconLabel">${not empty value?value:'无'}</label>&nbsp;
<input id="${id}" name="${name}" type="hidden" value="${value}"/><a id="${id}Button" href="javascript:" class="btn">选择</a>&nbsp;&nbsp;
<script type="text/javascript">
	$("#${id}Button").click(function(){
		layer.open({
			type: 2 //此处以iframe举例
			,title: '选择图标'
			,area: ['700px', $(top.document).height()-180+'px']
			,shade: 0.3
			,content: '${ctx}/tag/iconselect?value='+$("#${id}").val()
			,btn: ['确定','关闭']
			,yes: function(index){
				var icon = window.frames[0].$("#icon").val();
				icon = $.trim(icon);
				$("#${id}Icon").attr("class", "icon-"+icon);
				$("#${id}IconLabel").text(icon);
				$("#${id}").val(icon);
				layer.close(index);
			}
			,zIndex: layer.zIndex //重点1
			,success: function(layero){
				layer.setTop(layero); //重点2
			}
		});
	});
</script>
