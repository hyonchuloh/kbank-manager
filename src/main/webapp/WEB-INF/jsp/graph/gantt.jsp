<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP 간트차트(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<style>
a:link { color: #000; }
a:visited { color: #000; }
a:hover { color: #CCCCCC; }
</style>
<script>
function openDetail(seqNo) {
	window.open('/manager/gantt/detail?seqNo=' + seqNo,'FEP 상세보기 ['+seqNo+']','width=650, height=700, scrollbars=yes');
}
function openNew() {
	window.open('/manager/gantt/detail','FEP 프로젝트입력','width=650, height=700, scrollbars=yes');
}
</script>
</head>
<body>
<div style="float: right; padding-right: 15px; padding-top:12px;">
	<span style="color: #FFF;">
		<b style="cursor: pointer;" onclick="location.href='/logout';">${sessionUserName }님 안녕하세요</b> | 
		Make Money
	</span>
	<img src="/images/kbank_logo.gif" height="30px" style="vertical-align: middle;" />
</div>
<h1>FEP 간트차트(${env})</h1>
<form name="frm" method="get" action="/manager/gantt">
<ul>
	<li>
		조회기간 : 
		<input type="text" pattern="[0-9]{8}" name="sel_start_date" value="${ sel_start_date }" style="width: 80px;"/> ~ 
		<input type="text" pattern="[0-9]{8}" name="sel_end_date" value="${ sel_end_date }" style="width: 80px;"/>
		<input type="submit" value="SUBMIT" /> | 
		<input type="button" value="ADD PROJECT" onclick="openNew();" /> | 
		범례 : ①형상관리 →②개발통신정책 →③프로그램개발 →④스테이징반영 →⑤테스트 →⑥보안성심의 →⑦운영통신정책 →⑧운영반영 →⑨패밀리오픈 →⑩대고객오픈
	</li>
</ul>
</form>
<c:set var="today_style_head">border-left: 3px solid red; border-right: 3px solid red; border-top: 3px solid red;</c:set>
<c:set var="today_style_body">border-left: 3px solid red; border-right: 3px solid red;</c:set>
<c:set var="today_style_tail">border-left: 3px solid red; border-right: 3px solid red; border-bottom: 3px solid red;</c:set>
<table style="width: 100%; table-layout:fixed;">
<tr>
	<th rowspan="3" style="width: 30px;">순<br/>서</th>
	<th rowspan="3" style="width: 300px;">개발/프로젝트 명</th>
	<th colspan="${day_count}">날짜</th>
</tr>
<tr style="text-align: left;font-family: d2coding;">
	<c:set var="mergeCount" value="0"/>
	<c:set var="currentYYYYMM" />
	<c:forEach var="item" items="${day_list}" varStatus="idx">
		<c:if test="${idx.first}">
			<c:set var="currentYYYYMM">${fn:substring(item,0,6)}</c:set>
			<!-- 셀머지를 몇개나해야하는지 조사 -->
			<c:forEach var="day" items="${day_list}">
				<c:set var="tempYYYYMM" value="${fn:substring(day,0,6)}" />
				<c:if test="${tempYYYYMM eq currentYYYYMM}">
					<c:set var="mergeCount" value="${mergeCount+1}" />
				</c:if>
			</c:forEach>
			<!-- 셀머지를 몇개나해야하는지 조사 -->
			<td colspan="${mergeCount}">&nbsp;${fn:substring(item,0,4)}-${fn:substring(item,4,6)}</td>
		</c:if>
		<c:set var="mergeCount" value="0"/>
		<c:if test="${fn:substring(item,0,6) ne currentYYYYMM}">
			<c:set var="currentYYYYMM">${fn:substring(item,0,6)}</c:set>
			<!-- 셀머지를 몇개나해야하는지 조사 -->
			<c:forEach var="day" items="${day_list}">
				<c:set var="tempYYYYMM" value="${fn:substring(day,0,6)}" />
				<c:if test="${tempYYYYMM eq currentYYYYMM}">
					<c:set var="mergeCount" value="${mergeCount+1}" />
				</c:if>
			</c:forEach>
			<!-- 셀머지를 몇개나해야하는지 조사 -->
			<td colspan="${mergeCount}">&nbsp;${fn:substring(item,0,4)}-${fn:substring(item,4,6)}</td>
		</c:if>
	</c:forEach>
</tr>
<!-- 날짜 작성부 -->
<tr style="text-align: center;font-family: d2coding;">
	<c:forEach var="item" items="${day_list}" varStatus="idx">
		<c:choose>
		<c:when test="${current_date eq item}">
			<td style="${today_style_head}">${fn:substring(item,6,8) }</td>
		</c:when>
		<c:otherwise>
			<td>${fn:substring(item,6,8) }</td>
		</c:otherwise>
		</c:choose>
	</c:forEach>
</tr>
<!-- 비즈니스 row 반복부 -->
<c:forEach var="row" items="${biz_list }" varStatus="biz_idx">
<tr>
	<th>${biz_idx.index+1}</th>
	<td><b><a href="#" onclick="openDetail('${row.seqNo}');">${row.prjName }</a></b></td>
	<c:set var="row_start_date" value="" />
	<c:set var="td_style2" value="" />
	<c:forEach var="item" items="${day_list}" varStatus="day_idx">
	
		<!-- 오늘 날짜를 표식해주는 코드 -->
		<c:set var="td_style" value="" />
		<c:choose>
		<c:when test="${current_date eq item}">
			<c:choose>
			<c:when test="${biz_idx.last}">
				<c:set var="td_style" value="${today_style_tail}" />
			</c:when>
			<c:otherwise>
				<c:set var="td_style" value="${today_style_body}" />
			</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			<c:set var="td_style" value="" />
		</c:otherwise>
		</c:choose>
		<!-- 오늘 날짜를 표식해주는 코드 -->
		
		<!-- 담당자별 업무를 표식해주는 코드 -->
		<c:if test="${day_idx.first}">
			<fmt:parseDate value="${item}" pattern="yyyyMMdd" var="item_fmt" />
			<fmt:formatDate value="${item_fmt}" pattern="yyyyMMdd" var="_item" />
			<fmt:parseDate value="${row.startDate}" pattern="yyyyMMdd" var="startDate_fmt" />
			<fmt:formatDate value="${startDate_fmt}" pattern="yyyyMMdd" var="_startDate" />
			<fmt:parseDate value="${row.endDate}" pattern="yyyyMMdd" var="endDate_fmt" />
			<fmt:formatDate value="${endDate_fmt}" pattern="yyyyMMdd" var="_endDate" />
			<c:choose>
			<c:when test="${_item > _endDate}">	<!-- 표시일이 프로젝트 종료일보다 작은경우 -->
				<c:set var="td_style2" value="background-color: #ffffff" />
			</c:when>
			<c:when test="${_item > _startDate}" > <!-- 표시일이 프로젝트 시작일보다 큰경우 -->
				<c:choose>
				<c:when test="${row.developer eq '김성아' }" >
					<c:set var="td_style2" value="background-color: #ffeedd;" />
				</c:when>
				<c:otherwise>
					<c:set var="td_style2" value="background-color: #ddeeff;" />
				</c:otherwise>
				</c:choose>
			</c:when>
			</c:choose>
		</c:if>
		<c:if test="${item eq row.startDate}" >
			<c:choose>
			<c:when test="${row.developer eq '김성아' }" >
				<c:set var="td_style2" value="background-color: #ffeedd;" />
			</c:when>
			<c:when test="${row.developer eq '이태희' }" >
				<c:set var="td_style2" value="background-color: #ddffdd;" />
			</c:when>
			<c:otherwise>
				<c:set var="td_style2" value="background-color: #ddeeff;" />
			</c:otherwise>
			</c:choose>
		</c:if>
		<!-- 담당자별 업무를 표식해주는 코드 -->
		<td style="${td_style}${td_style2}" align="center">
			<c:choose>
			<c:when test="${item eq row.check10}">⑩</c:when>
			<c:when test="${item eq row.check9}">⑨</c:when>
			<c:when test="${item eq row.check8}">⑧</c:when>
			<c:when test="${item eq row.check7}">⑦</c:when>
			<c:when test="${item eq row.check6}">⑥</c:when>
			<c:when test="${item eq row.check5}">⑤</c:when>
			<c:when test="${item eq row.check4}">④</c:when>
			<c:when test="${item eq row.check3}">③</c:when>
			<c:when test="${item eq row.check2}">②</c:when>
			<c:when test="${item eq row.check1}">①</c:when>
			<c:otherwise>&nbsp;</c:otherwise>
			</c:choose>
		</td>
		<c:if test="${item eq row.endDate}" >
			<c:set var="td_style2" value="" />
		</c:if>
	</c:forEach>
</tr>
</c:forEach>


</table>
</body>
</html>