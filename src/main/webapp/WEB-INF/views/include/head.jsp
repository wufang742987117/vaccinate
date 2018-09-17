<%@ page contentType="text/html;charset=UTF-8" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta name="author" content="http://jeesite.com/"/>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=8,IE=9,IE=10,IE=11,IE=edge" />
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Cache-Control" content="no-store">
<!--初始化移动浏览显示，可视化窗口，视口的宽度等于物理设备上真实的分辨率，初始缩放比例为1-->
<meta name="viewport" content="width=device-width,initial-scale=1">
<script src="${ctxStatic}/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<link href="${ctxStatic}/bootstrap/2.3.1/css_${not empty cookie.theme.value ? cookie.theme.value : 'cerulean'}/bootstrap.min.css" type="text/css" rel="stylesheet" />
 
 
 
 <%--<script src="${ctxStatic}/bootstrap/2.3.1/js/bootstrap.min.js" type="text/javascript"></script>--%>
 <%--<link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome.min.css" type="text/css" rel="stylesheet" />--%>
<!-- <!--[if lte IE 7]><link href="${ctxStatic}/bootstrap/2.3.1/awesome/font-awesome-ie7.min.css" type="text/css" rel="stylesheet" /><![endif]-->
<!-- <!--[if lte IE 6]><link href="${ctxStatic}/bootstrap/bsie/css/bootstrap-ie6.min.css" type="text/css" rel="stylesheet" /> -->
<!-- <script src="${ctxStatic}/bootstrap/bsie/js/bootstrap-ie.min.js" type="text/javascript"></script><![endif]-->
 <link href="${ctxStatic}/jquery-select2/3.4/select2.min.css" rel="stylesheet" />
 <script src="${ctxStatic}/jquery-select2/3.4/select2.min.js" type="text/javascript"></script>
 <%--<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />--%>
 <%--<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>--%>
 <%--<link href="${ctxStatic}/jquery-jbox/2.3/Skins/Bootstrap/jbox.min.css" rel="stylesheet" />--%>
 <%--<script src="${ctxStatic}/jquery-jbox/2.3/jquery.jBox-2.3.min.js" type="text/javascript"></script>--%>
 <%--<script src="${ctxStatic}/My97DatePicker/WdatePicker.js" type="text/javascript"></script>--%>
 <%--<script src="${ctxStatic}/common/mustache.min.js" type="text/javascript"></script>--%>
 <%--<link href="${ctxStatic}/common/jeesite.css" type="text/css" rel="stylesheet" />--%>
<!--[if it IE 8]>
<script type="text/javascript" src="${ctxStatic}/bootstrap/2.3.1/docs/assets/js/html5shiv.js"></script>
<script type="text/javascript" src="${ctxStatic}/bootstrap/2.3.1/docs/assets/js/respond.js"></script>
<![end if]-->
<!--[if it IE 9]>
<script type="text/javascript" src="${ctxStatic}/bootstrap/2.3.1/docs/assets/js/html5shiv.js"></script>
<script type="text/javascript" src="${ctxStatic}/bootstrap/2.3.1/docs/assets/js/respond.js"></script>
<![end if]-->
<script type="text/javascript">var ctx = '${ctx}', ctxStatic='${ctxStatic}';</script>

