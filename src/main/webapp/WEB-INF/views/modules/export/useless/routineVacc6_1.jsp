<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA_Compatible" content="IE=edge"> 
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<title>国家免疫规划疫苗常规接种情况报表6-1</title>
		<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
		<script src="js/jquery-3.2.1.min.js"></script>
	
		<style type="text/css">
			body{
				padding: 30px;
			}
			.form-horizontal .control-label{
				padding-top: 0;
				line-height: 34px;
			}
			.form-horizontal .col-sm-1 , .form-horizontal .col-sm-2{
				padding: 0;
			}
			label{
				margin: 0 10px;
			}
			th{
				text-align: left;
				vertical-align: middle!important;
			}
			.first-row th{
				text-align: center;
			}
			td{
				text-align: center;
				vertical-align: middle!important;
			}
			
		</style>
		<script  type="text/javascript">
			 function print1(){
			 	$("#form").css("display","none");
			 	window.print();
			 }
		</script>
	</head>
	<body>
	
		<form class="form-horizontal" id="form">
		  <div class="form-group">
		    <label for="" class="col-sm-1 control-label"><span style="color: red;">*</span>报告年份</label>
		    <div class="col-sm-1">
		      <select class="form-control">
		      	<option>2017</option>
		      	<option>2018</option>
		      </select>
		    </div>
		    <label for="" class="col-sm-1 control-label"><span style="color: red;">*</span>报告月份</label>
		    <div class="col-sm-1">
		      <select class="form-control">
		      	<option>8</option>
		      	<option>9</option>
		      </select>
		    </div>
		    <label for="" class="col-sm-1 control-label">报告单位</label>
		    <div class="col-sm-2">
		      <input type="text" class="form-control" id="" value="淮海路门诊" disabled>
		    </div>
		    <label for="" class="col-sm-1 control-label"><span style="color: red;">*</span>单位编码</label>
		    <div class="col-sm-2">
		      <input type="text" class="form-control" id="" value="3406030301" disabled>
		    </div>
		  </div>
		  <button onclick="print1()">打印</button>
		</form>
		
		<table border="1" style="border:solid 1px black;border-collapse:collapse;" width="100%">
			<tr class="first-row">
				<th colspan="2" rowspan="2">疫苗</th>
				<th colspan="2">本地</th>
				<th colspan="2">流动</th>
			</tr>
			<tr class="first-row">
				<th>应用剂次数</th>
				<th>实种剂次数</th>
				<th>应种剂次数</th>
				<th>实种剂次数</th>
			</tr>
			<tr>
				<th rowspan="4">乙肝疫苗</th>
				<td>1</td>
				<td>140</td>
				<td>140</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>1(及时)</td>
				<td>--</td>
				<td>128</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>158</td>
				<td>155</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>3</td>
				<td>135</td>
				<td>132</td>
				<td>3</td>
				<td>3</td>
			</tr>
			<tr>
				<th colspan="2">卡介苗</th>
				<td>138</td>
				<td>138</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<th rowspan="4">脊灰疫苗</th>
				<td>1</td>
				<td>171</td>
				<td>168</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>175</td>
				<td>172</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>3</td>
				<td>206</td>
				<td>202</td>
				<td>3</td>
				<td>1</td>
			</tr>
			<tr>
				<td>4</td>
				<td>145</td>
				<td>140</td>
				<td>1</td>
				<td>0</td>
			</tr>
			<tr>
				<th rowspan="4">百白破疫苗</th>
				<td>1</td>
				<td>124</td>
				<td>121</td>
				<td>1</td>
				<td>1</td>
			</tr>
			<tr>
				<td>2</td>
				<td>156</td>
				<td>153</td>
				<td>1</td>
				<td>0</td>
			</tr>
			<tr>
				<td>3</td>
				<td>157</td>
				<td>155</td>
				<td>1</td>
				<td>1</td>
			</tr>
			<tr>
				<td>4</td>
				<td>146</td>
				<td>143</td>
				<td>2</td>
				<td>2</td>
			</tr>
			<tr>
				<th colspan="2">百白破疫苗</th>
				<td>140</td>
				<td>139</td>
				<td>1</td>
				<td>0</td>
			</tr>
			<tr>
				<th rowspan="2">麻风疫苗</th>
				<td>1</td>
				<td>145</td>
				<td>144</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>--</td>
				<td>0</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<th rowspan="2">麻腮风疫苗</th>
				<td>1</td>
				<td>--</td>
				<td>0</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>147</td>
				<td>145</td>
				<td>3</td>
				<td>2</td>
			</tr>
			<tr>
				<th rowspan="2">麻腮疫苗</th>
				<td>1</td>
				<td>--</td>
				<td>0</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>--</td>
				<td>0</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<th rowspan="2">麻疹疫苗</th>
				<td>1</td>
				<td>--</td>
				<td>0</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>--</td>
				<td>0</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<th rowspan="2">A群流脑疫苗</th>
				<td>1</td>
				<td>117</td>
				<td>114</td>
				<td>3</td>
				<td>1</td>
			</tr>
			<tr>
				<td>2</td>
				<td>140</td>
				<td>137</td>
				<td>1</td>
				<td>1</td>
			</tr>
			<tr>
				<th rowspan="2">A+C群流脑疫苗</th>
				<td>1</td>
				<td>163</td>
				<td>160</td>
				<td>4</td>
				<td>4</td>
			</tr>
			<tr>
				<td>2</td>
				<td>138</td>
				<td>137</td>
				<td>1</td>
				<td>0</td>
			</tr>
			<tr>
				<th rowspan="2">乙脑减毒疫苗</th>
				<td>1</td>
				<td>175</td>
				<td>170</td>
				<td>1</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>114</td>
				<td>113</td>
				<td>1</td>
				<td>1</td>
			</tr>
			<tr>
				<th rowspan="4">乙脑灭活疫苗</th>
				<td>1</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>3</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<td>4</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
				<td>0</td>
			</tr>
			<tr>
				<th colspan="2">甲肝减毒活疫苗</th>
				<td>108</td>
				<td>106</td>
				<td>1</td>
				<td>1</td>
			</tr>
			<tr>
				<th rowspan="2">甲肝灭活疫苗</th>
				<td>1</td>
				<td>--</td>
				<td>2</td>
				<td>--</td>
				<td>0</td>
			</tr>
			<tr>
				<td>2</td>
				<td>13</td>
				<td>13</td>
				<td>1</td>
				<td>0</td>
			</tr>
		</table>
	</body>
</html>