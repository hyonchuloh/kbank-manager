<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP OUTBOUNT TEST(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<style>
textarea {font-family: d2coding; }
</style>
</head>
<body>
<div style="float: right; padding-right: 15px; padding-top:12px;">
	<span style="color: #FFF;">
		<b style="cursor: pointer;" onclick="location.href='/logout';">${sessionUserName }님 안녕하세요</b> | 
		<a href="/log/list">온라인 로그조회</a> | 
		<a href="/log/listbat">배치 로그조회</a> | 
	</span>
	<img src="/images/kbank_logo.gif" height="30px" style="vertical-align: middle;" />
</div>
<h1 style="font-family: se-nanumsquare;">FEP 아웃바운드 테스트(${env})</h1>
<ul>
	<li>계정계 데이터를 이용하여 기관쪽으로 Outbound 테스트를 실시함</li>
</ul>
<h3 style="font-family: se-nanumsquare;">INPUT DATA</h3>
<blockquote>
<form name="frm" action="test-out" method="POST">
<table style="width: 100%;">
<tr>
	<th colspan="2">FEP OUTBOUND TEST</th>
</tr>
<tr>
	<th style="width: 150px;">HOST INBOUND URL</th>
	<td><input type="text" name="url" value="${url}" style="width: 250px;"/> *표준헤더500을 제외하고 자동으로 암호화하여 던지기 때문에 암호화여부 필드가 반드시 '1'이어야 합니다.</td>
</tr>
<tr>
	<th>SEND DATA</th>
	<td><textarea name="data" style="width: 100%;" rows="20" >${data}</textarea></td>
</tr>
<tr>
	<th>SUBMIT</th>
	<td><input type="submit" value="SEND" /></td>
</tr>
<tr>
	<th>RESPONSE CODE</th>
	<td>${result}</td>
</tr>
</table>
</form>
</blockquote>
<ul>
	<li>DEV URL SAMPLE : [http://0.0.0.0/a503503]</li>
	<li>STG URL SAMPLE : [http://0.0.0.0/a503503]</li>
	<li>CRD URL SAMPLE : [http://0.0.0.0/s089crd]</li>
</ul>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>