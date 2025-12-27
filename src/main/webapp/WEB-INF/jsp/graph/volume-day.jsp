<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR" http-equiv="refresh" content="1200">
<title>FEP 일별 통계(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<link rel="stylesheet" type="text/css" href="/js/jquery.jqplot.min.css" />
<style>
@font-face { font-family: se-nanumsquare-bold; font-weight:700;
	src: 	url('/font/se-nanumsquare-bold.woff') format('woff'),
			url('/font/se-nanumsquare-bold.woff2') format('woff2'),
			url('/font/se-nanumsquare-bold.ttf') format('truetype'); 
}
.jqplot-point-label {
	background-color: #CCCCCC;
}
</style>
<script type="text/javascript" src="/js/excanvas.js"></script>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="/js/jqplot.highlighter.js"></script>
<script type="text/javascript" src="/js/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="/js/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="/js/jqplot.pointLabels.min.js"></script>
<script type="text/javascript">
$(document).ready(
	function() {
		$.jqplot.config.enablePlugins = true;
		
		$.jqplot('chartdiv', [${chart_data}], {
			title: {text: ${title}, fontFamily: 'se-nanumsquare-bold', textColor: 'black'}
			,seriesDefaults:{
				renderer: $.jqplot.BarRenderer,
				pointLabels: { show: true }
			}
			,axes:{
				xaxis:{
					renderer:$.jqplot.CategoryAxisRenderer,
					ticks: ${ticks_data}
				}
			}
			,highlighter: {show: false}
			,cursor: {zoom:true}
		});
	}
);
function toggleView() {
	var area = document.getElementById("top_area");
	if ( area.style.display == "" || area.style.display == "block" ) {
		area.style.display = "none";
	} else {
		area.style.display = "block";
	}
}
</script>

</head>
<body>
<div style="float: right; padding-right: 15px; padding-top:12px;">
	<span style="color: #FFF;">
		<a href="/stics/volume">거래량</a> | 
		<a href="/stics/rank">랭킹</a> | 
		<a href="/stics/peak">피크</a> | 
		<a href="/stics/volume-day">일별</a> | 
	</span>
	<img src="/images/kbank_logo.gif" height="30px" style="vertical-align: middle;" />
</div>
<h1 style="font-family: se-nanumsquare;" ondblclick="toggleView();">FEP ${title}</h1>
<form name="frm" action="/stics/volume-day" method="get">
<ul>
	<li>
		<b>[INPUT PARAMS]</b>
		시작날짜 : <input type="text" pattern="[0-9]+" name="start_date" value="${start_date}" style="width: 80px;"/> |
	    종료날짜 : <input type="text" pattern="[0-9]+" name="end_date" value="${end_date}" style="width: 80px;"/> |
		기관코드 : <input type="text" pattern="[0-9]+" name="inst_code" value="${inst_code}" style="width: 40px;" /> |
		업무코드 : <input type="text" pattern="[0-9A-Za-z]+" name="appl_code" value="${appl_code}" style="width: 40px;" />
		<input type="submit" value="SUBMIT"	/>
	</li>
	<li> <b>[OPTIONS]</b> YAXIS_MAX : <input type="text" pattern="[0-9]+" name="yaxis_max" value="${yaxis_max}" style="width: 40px;"/> | 
	CHART_HIGHT : <input type="text" pattern="[0-9]+" name="chart_height" value="${chart_height}" style="width: 40px;"/> |
</ul>
</form>
<blockquote>
	<div id="chartdiv" style="width: 100%; height: ${chart_height}px;"></div>
</blockquote>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;" ondblclick="toggleView();">Kbank</span>
</body>
</html>