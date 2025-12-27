<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR" http-equiv="refresh" content="600">
<title>FEP 거래량 피크(${env})</title>
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
<script type="text/javascript" src="/js/jqplot.dateAxisRenderer.js"></script>
<script type="text/javascript" src="/js/jqplot.highlighter.js"></script>
<script type="text/javascript" src="/js/jqplot.enhancedLegendRenderer.js"></script>
<script type="text/javascript" src="/js/jqplot.pointLabels.min.js"></script>
<script type="text/javascript" src="/js/jqplot.cursor.js"></script>
<script type="text/javascript">
$(document).ready(
	function() {
		$.jqplot('chartdiv', ${chart_data}, {
			title: {text: ${title}, fontFamily: 'se-nanumsquare-bold', textColor: 'black'}
			,seriesDefaults:{markerOptions: {size:3}}
			,series:[
				${series_data}
			]
			,pointLabels:{show: true}
			,legend:{
				renderer:$.jqplot.EnhancedLegendRenderer
				, show:true, location:'s', border: 'none'
				, rendererOptions: { numberRows: 1 }
				, placement:'outside'
				, marginTop: '15px'
			}
			,axes:{
				xaxis:{
					renderer:$.jqplot.DateAxisRenderer
					,tickOptions:{formatString:'%H:%M'}
					,min : '2000-01-01 00:00'
					,max : '2000-01-01 24:00'
					,numberTicks : 25
				}
				,yaxis:{tickOptions:{formatString:"%'d"}, min : 0, numberTicks : 10 ${yaxis_str} }
			}
			,highlighter: {show: true}
			,cursor: {zoom:true}
			,grid:{
				background: '#474747',
				gridLineColor: '#5D5D5D'
			}
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
<form name="frm" action="/stics/peak" method="get">
<ul>
	<li>
		<b>[INPUT PARAMS]</b>
		대상날짜(생략=오늘) : <input type="text" pattern="[0-9]+" name="target_date" value="${target_date}" style="width: 80px;"/> |
		역대최고 : <input type="text" pattern="[0-9]+" name="history_date" value="${history_date}" style="width: 80px;"/> |
		역대피크 : <input type="text" pattern="[0-9]+" name="peak_date" value="${peak_date}" style="width: 80px;"/> |
		기관코드 : <input type="text" pattern="[0-9]+" name="inst_code" value="${inst_code}" style="width: 40px;" /> |
		업무코드 : <input type="text" pattern="[0-9A-Za-z]+" name="appl_code" value="${appl_code}" style="width: 40px;" />
		<input type="submit" value="SUBMIT"	/>
	</li>
	<li>
		<b>[OPTIONS]</b> YAXIS_MAX : <input type="text" pattern="[0-9]+" name="yaxis_max" value="${yaxis_max}" style="width: 40px;"/> |
		CHART_HIGHT : <input type="text" pattern="[0-9]+" name="chart_height" value="${chart_height}" style="width: 40px;"/> |
	</li> 
</ul>
</form>
<blockquote>
	<div id="chartdiv" style="width: 100%; height: ${chart_height}px;"></div>
</blockquote>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;" ondblclick="toggleView();">Kbank</span>
</body>
</html>