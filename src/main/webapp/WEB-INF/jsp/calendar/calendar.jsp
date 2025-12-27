<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP Calendar</title>
<link rel="stylesheet" type="text/css" href="/css/default.css" />
<style>
body { font-family: d2coding; font-size: 9pt; }
input { font-family: d2coding; font-size: 9pt; }
table { border-collapse: collapse; border-style: solid; border-top: #6AB4CC 2px solid;
		border-left:#EFEFEF 1px solid; border-right: #EFEFEF 1px solid; 
		border-bottom: #BDBCBD 1px solid; margin-bottom: 5px; margin-top: 5px; }
th { height: 24px; border-bottom: #E0DFE0 1px dotted; border-right: #E0DFE0 1px dotted;
	 border-left: #E0DFE0 1px dotted; background: #E7F3F7; 
	 font-size: 10pt; font-family: se-nanumsquare; font-weight: bold; }
td { height: 24px; color: #272727; border-bottom: #E0DFE0 1px dotted;
	 border-left:#EFEFEF 1px solid; border-right: #EFEFEF 1px solid; 
	 padding-left: 5px; padding-right: 5px; 
	 font-family: d2coding; font-size: 9pt;}
textarea {  font-family: d2coding; font-size: 10pt; }
pre { font-family: d2coding; font-size: 9pt; }
textarea { font-family: d2coding; font-size: 9pt; }
td:hover {
	background: #E6EBF5 !important;
}
</style>
<script>
function saveItem(key, value) {
	if ( window.event.keyCode == 9 ) {
		document.frm.key.value = key;
		document.frm.value.value = value;
		document.frm.submit();
	}
}
function openSearch(name, year) {
	var key = document.getElementById("searchkey").value;
	window.open('/manager/calfind?name=' + name + '&year=' + year + '&searchkey=' + key,'FEP CALENDER SEARCH','width=700, height=850');
}
</script>
</head>
<body>
<h1 style="font-family: se-nanumsquare;">FEP 오픈캘린더(${name}) - ${yearInt }.${monthInt }</h1>
<form name="frm2" action="/manager/calfind">
<span style="font-family: se-nanumsquare; font-size: 11pt;">
<a href="/manager/calendar/${name}?year=${yearInt }&month=${monthInt-1 }&key=&value=">Before Month</a> |
<a href="/manager/calendar/${name}?year=${yearInt }&month=${monthInt+1 }&key=&value=">Next Month</a> |
| 달력검색 : <input type="text" id="searchkey"  style="width: 150px;" autocomplete="off"/> <input type="button" value="SEARCH" onclick="openSearch('${name}', '${yearInt}');" />
</span>
</form>
<table style="width: 100%; table-layout:fixed;" border=1>
<tr>
	<th style="width: 10%;">일</th>
	<th style="width: 16%;">월</th>
	<th style="width: 16%;">화</th>
	<th style="width: 16%;">수</th>
	<th style="width: 16%;">목</th>
	<th style="width: 16%;">금</th>
	<th style="width: 10%;">토</th>
</tr>
<c:set var="isContinue" value="true"/>
<c:forEach var="row" items="${dayTable}" varStatus="row_status">
<c:if test="${isContinue eq 'true'}">
	<tr style="height: 150px;">
		<c:forEach var="col" items="${row}" varStatus="cal_status">
		<c:choose>
			<c:when test="${col > 0}">
				<td valign="top">
					<b>${col}</b>
					<c:if test="${col == dayInt}"><font color="blue"> Today</font></c:if>
					<br/>
					<div contenteditable='true' onkeydown="saveItem('CAL.${yearInt}.${monthInt}.${col}', this.innerHTML);">
						<c:set var="tempKey">CAL.${yearInt}.${monthInt}.${col}</c:set>
						${contents[tempKey]}
					</div>
				</td>
			</c:when>
			<c:otherwise>
				<td style="background-color: #CCCCCC">
					&nbsp;
				</td>
				<c:if test="${row_status.index > 1}">
					<c:set var="isContinue" value="false"/>
				</c:if>
			</c:otherwise>
		</c:choose>
		</c:forEach>
	</tr>
</c:if>
</c:forEach>
</table>
<form name="frm" action="/manager/calendar/${name}" method="POST">
	<input type="hidden" name="key" value="" />
	<input type="hidden" name="value" value="" />
	<input type="hidden" name="year" value="${yearInt}" />
	<input type="hidden" name="month" value="${monthInt}" />
</form>
<span style="font-weight: bold; color: #BFFF00; background-color: #000080; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>