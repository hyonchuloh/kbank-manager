<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP MANAGER PORTAL - ${name}</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
</head>
<body onload="frm.submit();">
<div style="float: right; padding-right: 15px; padding-top:12px;">
	<span style="color: #FFF;">
		Make Money
	</span>
	<img src="/images/kbank_logo.gif" height="30px" style="vertical-align: middle;" />
</div>
<h1  style="font-family: se-nanumsquare;">FEP MANAGER PORTAL - LOGIN(${env})</h1>

<form name="frm" method="POST" action="login">
<ul>
	<li>
		FEP 관리포탈에 로그인하시기 바랍니다.
	</li>
	<li>
		USER ID : <input type="text" name="userId" value="${userId }"/>
		<c:choose>
			<c:when test="${env eq 'stag' }" >
				(사번 ex. 40160521) --> 스테이징 IM에 FEP 권한등록이 필요합니다.(등록 후 D+1 반영)
			</c:when>
			<c:when test="${env eq 'dev' }" >
				(사번 ex. 20160521) --> 개발계정 추가는 별도 문의바랍니다.
			</c:when>
			<c:otherwise>
				(사번 ex. 20160521) --> 운영 IM에 FEP 권한등록이 필요합니다.(등록 후 D+1 반영)
			</c:otherwise>
		</c:choose>
	</li>
	<li>
		USER PW : <input type="password" name="userPw" value="${userPw }"/>(LDAP 비번) --> 테스트 중이라 아무 패스워드나 입력가능
	</li>
	<li>
		<input type="submit" value="LOGIN" />
	</li>
	<li>
		문의 : 
	</li>
</ul>
<input type="hidden" name="history" value="${history }" />
</form>
</body>
</html>