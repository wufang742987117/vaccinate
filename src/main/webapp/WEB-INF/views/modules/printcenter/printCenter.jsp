<%--
  Created by IntelliJ IDEA.
  User: wcy
  Date: 2016/8/2
  Time: 9:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>打印中心</title>
    <meta name="decorator" content="default" />
    <script type="text/javascript">
        function printC(v) {
            var plan = $("#planid").val();
            alert(v);
            if (plan==null||plan=="0"){
                layer.confirm("确认要导出用户数据吗？", {
                    btn: ['返回'], //按钮
                    shade: true, //不显示遮罩
                    icon : 0
                },function(index)
                {
                    layer.close(index);
                });
            }else{
                alert(plan);
                var url = "${ctx}/printcenter/printcenter/printbyid?printid="+v;
                $.ajax({
                    type: "get",
                    url: url,
                    cache:false,
                    async:false,
                    dataType: ($.browser.msie) ? "text" : "xml",
                    success: function(xmlobj){
                    }
                });
            }
        }
    </script>
</head>
<body>
<div class="form-group" style="width: 40%">
    <div class="input-group">
        <span class="input-group-addon gray-bg text-right">
            任务计划：
        </span>
        <select id="planid" name="type" class="form-control">
            <option value="0">请选择</option>
            <option value="1">南京承臻</option>
        </select>
    </div>
</div>
<table id="contentTable" class="table table-bordered table-condensed">
    <thead>
    <tr>
        <th>序号</th>
        <th>文书名称</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody>
        <tr value="1">
            <td>1</td>
            <td>首次/末次会议签到表</td>
            <td>
                <button class="btn btn-primary" type="button" onclick="printC(1)">打印</button>
            </td>
        </tr>
        <tr>
            <td>2</td>
            <td>审查计划表</td>
            <td>
                <button class="btn btn-primary" type="button">打印</button>
            </td>
        </tr>
        <tr>
            <td>3</td>
            <td>工业产品生产许可现场核查通知书</td>
            <td>
                <button class="btn btn-primary" type="button">套打</button>
            </td>
        </tr>
        <tr>
            <td>4</td>
            <td>证明性文件确认单</td>
            <td>
                <button class="btn btn-primary" type="button">打印</button>
            </td>
        </tr>
        <tr>
            <td>5</td>
            <td>生产许可证企业实地核查报告</td>
            <td>
                <button class="btn btn-primary" type="button">打印</button>
            </td>
        </tr>
        <tr>
            <td>6</td>
            <td>企业实地核查轻微缺陷项汇总表</td>
            <td>
                <button class="btn btn-primary" type="button">打印</button>
            </td>
        </tr>
        <tr>
            <td>7</td>
            <td>不合格项整改通知书</td>
            <td>
                <button class="btn btn-primary" type="button">套打</button>
            </td>
        </tr>
        <tr>
            <td>8</td>
            <td>产品生产许可证抽样单</td>
            <td>
                <button class="btn btn-primary" type="button">套打</button>
            </td>
        </tr>
    </tbody>
</table>
</body>
</html>
