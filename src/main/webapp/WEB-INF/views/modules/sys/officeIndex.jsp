<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>机构管理</title>
    <%--<meta name="decorator" content="blank"/>--%>
    <%@include file="/WEB-INF/views/include/head1.jsp" %>

    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
</head>
<body>
<sys:message content="${message}"/>

<div class="row animated fadeInRight">
    <div class="col-sm-3" >
        <div class="form-group" style="padding: 10px 0 0 10px;">
            <a>组织机构<i class="fa fa-refresh" onclick="refreshTree();"></i></a>
        </div>
        <div id="ztree" class="ztree"></div>
    </div>
    <div class="col-sm-9" >
        <iframe id="officeContent" src="${ctx}/sys/office/list?id=&parentIds=" width="100%" height="91%" frameborder="0"></iframe>
    </div>
</div>

<script type="text/javascript">
    var setting = {data:{simpleData:{enable:true,idKey:"id",pIdKey:"pId",rootPId:'0'}},
        callback:{onClick:function(event, treeId, treeNode){
            var id = treeNode.pId == '0' ? '' :treeNode.pId;
            $('#officeContent').attr("src","${ctx}/sys/office/list?id="+id+"&parentIds="+treeNode.pIds);
        }
        }
    };

    function refreshTree(){
        $.getJSON("${ctx}/sys/office/treeData",function(data){
            $.fn.zTree.init($("#ztree"), setting, data).expandAll(true);
        });
    }
    refreshTree();
</script>
</body>
</html>

