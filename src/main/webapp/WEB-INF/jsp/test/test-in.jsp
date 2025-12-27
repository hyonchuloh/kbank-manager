<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP INBOUND TEST(${env})</title>
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
<h1 style="font-family: se-nanumsquare;">FEP 인바운드 테스트(${env})</h1>
<ul>
	<li>INPUT 데이터를 가지고 단순 1건 Inbound 테스트를 실시함</li>
</ul>
<form method="post" action="test-in">

<h3 style="font-family: se-nanumsquare;">STEP1. SEND INFO</h3>
<ul>
	<li>TARGET IP : <input type="text" name="targetIp" value="${ targetIp }" /></li>
	<li>TARGET PORT : <input type="text" name="targetPort" value="${ targetPort }" /> ex) 163763=56015</li>
	<li>LENGTH STYLE : <input type="text" name="lengthStyle" value="${ lengthStyle }" /> ex) 4byte길이=0000, 5byte길이=00000</li>
	<li>Encoding : <input type="text" name="charEncoding" value="${charEncoding }" /> ex) EUC-KR(기본), UTF-8(공공마이데이터)</li>
	<li>SEND START : <input type="submit" value="SEND" />
</ul>


<h3 style="font-family: se-nanumsquare;">STEP2. INPUT DATA</h3>
<blockquote>
	<textarea style="width: 100%;" rows="10" name="inputData" >${ inputData }</textarea>
</blockquote>
</form>


<h3 style="font-family: se-nanumsquare;">STEP3. OUTPUT DATA</h3>
<blockquote>
	<textarea style="width: 100%;" rows="10">${ outputData }</textarea>
</blockquote>


<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>