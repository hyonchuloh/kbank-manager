<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TELNET TEST</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
</head>
<body>
<div style="float: right; padding-right: 15px; padding-top:12px;">
	<span style="color: #FFF;">
		<b style="cursor: pointer;" onclick="location.href='/logout';">${sessionUserName }님 안녕하세요</b> | 
		Make Money
	</span>
	<img src="/images/kbank_logo.gif" height="30px" style="vertical-align: middle;" />
</div>
<h1>TELNET TEST</h1>
<form name="frm" action="/manager/telnet" method="POST">
<ul>
	<li>
		IP : <input type="text" name="test_ip" value="${test_ip }" />
		PORT : <input type="text" name="test_port" value="${test_port }" />
		<input type="hidden" name="history" value="${history }" />
		<input type="submit" value="SUBMIT" />
	</li>
	<li>${result }</li>
</ul>
</form>
${history }
</body>
</html>