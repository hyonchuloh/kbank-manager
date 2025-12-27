<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP CalFinder</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<style>
tr:hover {
	background: #E6EBF5 !important;
}
</style>
</head>
<body>
<h2>FEP 오픈캘린더 검색</h2>
<form name="frm" action="calfind">
<ul>
	<li>
		<b>[필수값입력]</b>
		사용자명 <input type="text" name="name" value="${name}" style="width: 80px;" />
		년도검색 <input type="text" name="year" value="${year}" style="width: 80px;" />
		검색어 <input type="text" name="searchkey" value="${searchkey}" style="width: 150px;" />
		<input type="submit" value="SUBMIT" />
	</li>
</ul>
</form>
<table style="width: 100%; table-layout:fixed;" border=1>
<tr>
	<th style="with: 100px;">key</th>
	<th>value</th>
</tr>
<c:forEach var="row" items="${result}">
<tr>
	<td>${row.key}</td>
	<td>${row.value}</td>
</tr>
</c:forEach>
</table>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>