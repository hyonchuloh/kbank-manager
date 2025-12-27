<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드계 U2L - FEP실회선 전환(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<script>
function deployValues(gwname, symbname, linename, luname) {
	frm.gwname.value = gwname;
	frm.symbname.value = symbname;
	frm.linename.value = linename;
	frm.luname.value = luname;
}
</script>
</head>
<body>
<h1>카드계 U2L - FEP실회선 전환(${env}) - </h1>
<form name="frm" action="u2lcrd" method="POST">
	<ul>
		<li>카드계 구노드와 신노드 간의 FEP 실회선 통신정보 변경 프로그램</li>
		<li>변경할 정보 입력 : <input type="text" name="linename" style="width: 250px;" /> <input type="submit" value="SUBMIT" /></li>
		<li>조작자 IP : ${requestip}</li>
	</ul>
	<input type="hidden" name="gwname" readonly/>
	<input type="hidden" name="symbname" readonly/>
	<input type="hidden" name="luname" readonly/>
</form>
<table style="border: 0px none white; width: 100%">
<tr valign="top">
	<td style="width: 50%;">
		<h3>현재 설정된 정보 - GATEWAY (LINENAME부분만 변경 가능)</h3>
		<blockquote>
		<table style="width: 100%;">
		<tr>
			<th>SEL</th>
			<th>GWNAME</th>
			<th>SYMBNAME</th>
			<th>LINENAME</th>
			<th>LUNAME</th>
			<th>STATYPE</th>
		</tr>
		<c:forEach items="${gwlist}" var="row">
		<tr align="center">
			<td><input type="radio" name="sel" onclick="deployValues('${row.gwname}','${row.symbname}','${row.linename}','${row.luname}');"/></td>
			<td>${row.gwname}</td>
			<td>${row.symbname}</td>
			<td><b>${row.linename}</b></td>
			<td>${row.luname}</td>
			<td>${row.statype}</td>
		</tr>
		</c:forEach>
		</table>
		</blockquote>
		<h3>현재 설정된 정보 - URL</h3>
		<blockquote>
		<table style="width: 100%;">
		<tr>
			<th>SEL</th>
			<th>GWNAME</th>
			<th>SYMBNAME</th>
			<th>LINENAME</th>
			<th>LUNAME</th>
			<th>STATYPE</th>
		</tr>
		<tr align="center">
			<td><input type="radio" name="sel" onclick="deployValues('Servlet','URL','${url}','');"/></td>
			<td>Servlet</td>
			<td colspan="3"><b>${url}</b></td>
			<td>1</td>
		</tr>
		</table>
		</blockquote>
	</td>
	<td style="width: 50%;">
		<h3>변경 이력(역순)</h3>
		<blockquote>
		<table style="width: 100%;">
		<tr>
			<th>UPDATE</th>
			<th>GWNAME</th>
			<th>SYMBNAME</th>
			<th>LINENAME</th>
			<th>LUNAME</th>
			<th>IP</th>
		</tr>
		<c:forEach items="${historylist}" var="row">
		<tr align="center">
			<c:choose>
			<c:when test="${row.gwname eq 'Servlet'}">
				<td>${row.updateTime}</td>
				<td>${row.gwname}</td>
				<td colspan="3">${row.linename}</td>
				<td>${row.ip}</td>
			</c:when>
			<c:otherwise>
				<td>${row.updateTime}</td>
				<td>${row.gwname}</td>
				<td>${row.symbname}</td>
				<td>${row.linename}</td>
				<td>${row.luname}</td>
				<td>${row.ip}</td>
			</c:otherwise>
			</c:choose>
		</tr>
		</c:forEach>
		</table>
		</blockquote>
	</td>
</tr>
</table>
<h3>APPENDIX</h3>
<ul>
	<li>STATYPE(상태정보컬럼) : 1 (정상), 8(중지), 9(삭제)</li>
</ul>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>