<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP OUTBOUND TEST for HTTP(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
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
<h1>FEP 아웃바운드테스트 for HTTP(${env})</h1>
<form name="frm" action="./test-out-http" method="POST">
<h3>INPUT DATA</h3>
<ul>
	<li>요청 메시지 작성 : 각각의 키벨류 셋은 | 파이크로 구분하고, 키와 값은 :로 구분합니다. ex) key:value|key1:value1|... </li>
</ul>
<blockquote>
<table style="border: 0px none white; width: 100%" >
<tr valign="top">
	<td style="width: 50%">
		<table style="width: 100%;">
		<tr>
			<th colspan="2">SOURCE MESSAGE</th>
		</tr>
		<tr>
			<td style="width: 20%;">TARGET URL</td>
			<td><input type="text" name="req_url" style="width: 95%;" value="${req_url}"/></td>
		</tr>
		<tr>
			<td>HEADERS(key:value|key1:value1...)</td>
			<td><input type="text" name="req_headers" style="width: 95%;" value="${req_headers}"/></td>
		</tr>
		<tr>
			<td>PARAMS(key:value|key1:value1...)</td>
			<td><input type="text" name="req_params" style="width: 95%;" value="${req_params}"/></td>
		</tr>
		<tr>
			<td>BODY</td>
			<td><textarea style="width: 95%;" rows="20" name="req_body">${req_body}</textarea></td>
		</tr>
		</table>
	</td>
	<td style="width: 50%">
		<table style="width: 100%;">
		<tr>
			<th colspan="2">RESPONSE MESSAGE</th>
		</tr>
		<tr>
			<td style="width: 20%;">RESPONSE CODE</td>
			<td>${res_code}</td>
		</tr>
		<tr>
			<td>HEADERS</td>
			<td rowspan="2">${res_headers}</td>
		</tr>
		<tr>
			<td style="width: 20%;">&nbsp;</td>
		</tr>
		<tr>
			<td>BODY<br/>
			</td>
			<td><textarea style="width: 95%;" rows="20" name="res_body">${res_body}</textarea></td>
		</tr>
		</table>
	</td>
</tr>
</table>
</blockquote>
<ul>
	<li>
		전송 : 
		<input type="button" value="GET SEND" onclick="frm.req_method.value='get';frm.submit();" />
		<input type="button" value="POST SEND" onclick="frm.req_method.value='post';frm.submit();" />
		<input type="hidden" name="req_method"/>
	</li>
</ul>
</form>
<h3>APPENDIX. SAMPLE TEXT</h3>
<ul>
	<li>URL : http://127.0.0.1/v2.0/cards</li>
	<li>HEADER : Authorization:Bearer test</li>
	<li>PARAMETERS : bank_tran_id:test|user_seq_no:test|bank_code_std:test|member_bank_code:test</li>
	<li>BODY : {"dummy":""}</li>
</ul>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>