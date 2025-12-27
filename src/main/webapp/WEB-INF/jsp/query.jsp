<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP QUERY UTIL(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<style>
textarea {font-family: d2coding; }
</style>
<script>
function submitDefault() {
	frm.action="query";
	frm.submit();
}
function submitQuery() {
	frm.action="query-excel";
	frm.submit();
}
</script>
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
<H1>FEP QUERY UTIL(${env})</H1>
<table style="width: 100%;">
<tr>
	<th style="width: 400px;">INPUT</th>
	<th>OUTPUT</th>
</tr>
<tr>
	<td valign="top">
	<h3>INPUT SQL</h3>
	<form name=frm action="query" method="POST">
		<textarea cols=60 rows=15 name="sql">${sql}</textarea>
		<input type="button" value="EXECUTE" onclick="submitDefault();"/> 
		<input type="button" value="EXCEL" onclick="submitQuery();"/>
	</form>
	<h3>EXAMPLES</h3>
	<blockquote>
		-- 테이블별 사용량 조사<br/>
		SELECT TABLE_NAME, TABLE_ROWS, ROUND(DATA_LENGTH/(1024*1024),2) AS DATA_SIZE, ROUND(INDEX_LENGTH/(1024*1024),2) AS INDEX_SIZE <br/>
		FROM INFORMATION_SCHEMA.TABLES <br/>
		WHERE TABLE_SCHEMA='DFEP' <br/>
		ORDER BY DATA_LENGTH DESC <br/>
		LIMIT 20 <br/>
	</blockquote>
	<blockquote>
		-- 인터페이스ID 매핑값 조회 <br/>
		SELECT *  <br/>
		FROM ALTXMAPFLD  <br/>
		WHERE SRC_FLD_INFO='sysIntfId'  <br/>
		AND CLASS_NAME LIKE '%773%'  <br/>
		LIMIT 100  <br/>
	</blockquote>
	<blockquote>
		-- 상수매핑 값 찾기 <br/>
		SELECT * <br/>
		FROM ALTXMAPFLD <br/>
		WHERE MAPPING_INFO LIKE '%Ext20110100010112Intrfc%' <br/>
		LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		-- 컬럼 목록 가져오는 쿼리 <br/>
		SELECT * FROM INFORMATION_SCHEMA.COLUMNS  <br/>
		WHERE TABLE_NAME='alstics' LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		-- 테이블 목록 가져오는 쿼리 <br/>
		SELECT * <br/>
		FROM INFORMATION_SCHEMA.TABLES <br/>
		WHERE TABLE_SCHEMA='DFEP' LIMIT 10 <br/>
	</blockquote>
	<blockquote>
		-- 초당 거래량 조회 쿼리 <br/>
		SELECT SUBSTR(PROC_MTIME,1,6), INST_CODE, APPL_CODE, COUNT(*) <br/>
		FROM almstlog <br/>
		WHERE PROC_DATE='20200612' <br/> 
		AND PROC_MTIME BETWEEN '110000000' AND '120000000' <br/>
		AND LOG_POINT='1'
		AND INST_CODE='171' AND APPL_CODE IN ('771I', '771O') <br/>
		GROUP BY SUBSTR(PROC_MTIME,1,6), INST_CODE, APPL_CODE <br/>
		LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		-- 온라인거래 조회 쿼리 <br/>
		SELECT * <br/>
		FROM almstlog <br/>
		WHERE PROC_DATE='20200612' <br/> 
		AND PROC_MTIME BETWEEN '110000000' AND '120000000' <br/>
		AND INST_CODE='171' AND APPL_CODE IN ('771I', '771O') <br/>
		ORDER BY LOG_ID, LOG_POINT <br/>
		LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		-- 배치거래 조회 쿼리<br/>
		SELECT * <br/>
		FROM ALBATSTAT <br/>
		WHERE INST_CODE='160' AND STRT_DATE > '20200301'<br/>
		LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		-- 업무별 전문종별 거래코드 및 매핑 클래스 리스트 <br/>
		SELECT APPL_CODE, TX_CODE, REP_KIND_CODE, NAME, REQ_HEAD_MAPP_CLASS, REQ_USER_CLASS, REQ_BODY_MAPP_CLASS <br/>
		FROM ALTX <br/>
		WHERE 	APPL_CODE='667I' <br/>
		LIMIT 1000 <br/>
	</blockquote>
	<blockquote>
		-- 매핑클래스 내부 REQ 클래스 조회 <br/>
		SELECT *  <br/>
		FROM ALTXMAP <br/>
		WHERE CLASS_NAME='Comm_667I_0200_4100_Req_Map' <br/>
		LIMIT 1000 <br/>
	</blockquote>
	<blockquote>
		-- 전문 레이아웃정보 <br/>
		SELECT FLD_NAME, FLD_KOR_NAME, LEN <br/>
		FROM ALMSGFLD <br/>
		WHERE CLASS_NAME='i167.a667.Comm_167_667I_ReqIn_Object' <br/>
		&nbsp;&nbsp;&nbsp;&nbsp;AND VERSION_NO=(SELECT MAX(VERSION_NO) FROM ALMSGFLD <br/>
		&nbsp;&nbsp;&nbsp;&nbsp;WHERE CLASS_NAME='i167.a667.Comm_167_667I_ReqIn_Object')
		ORDER BY SEQNO ASC <br/>
		LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		-- 계정계 default 매핑값 보기 <br/>
		SELECT * <br/>
		FROM ALTXMAPFLD <br/>
		WHERE CLASS_NAME='Comm_667I_0200_4100_Req_Map' <br/>
		LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		-- 전체 업무 현황 <br/>
		SELECT *  <br/>
		FROM ALAPPL <br/>
		LIMIT 1000 <br/>
	</blockquote>
	<blockquote>
		-- 거래건수 통계 <br/>
		SELECT INST_CODE, APPL_CODE, COUNT(*) <br/>
		FROM almstlog  <br/>
		WHERE PROC_DATE='20200703'  <br/>
		AND PROC_MTIME BETWEEN '000000000' AND '235959000'  <br/>
		AND LOG_POINT='1' <br/>
		AND INST_CODE IN ('099','168') <br/>
		GROUP BY INST_CODE, APPL_CODE <br/>
		ORDER BY INST_CODE, APPL_CODE <br/>
		LIMIT 1000 <br/>
	</blockquote>
	<blockquote>
		--- 파라메터 정보 비교<br/>
		SELECT * <br/>
		FROM ALTXPARM <br/>
		WHERE APPL_CODE LIKE '50%' <br/>
		ORDER BY TX_CODE, REP_KIND_CODE <br/>
		LIMIT 100 <br/>
	</blockquote>
	<blockquote>
		--- 암호화 증적용<br/>
		SELECT CAST(MSG_DATA AS CHAR(1000))<br/>
		FROM ALMSTLOG<br/>
		WHERE PROC_DATE='20210914'<br/>
		AND PROC_MTIME BETWEEN '113138000' AND '113145000'<br/>
		AND INST_CODE='510'<br/>
		ORDER BY PROC_MTIME DESC, LOG_ID, LOG_POINT DESC<br/>
		LIMIT 100<br/>
	</blockquote>
	<blockquote>
		--- 일자별 건수 조사<br/>
		SELECT PROC_DATE, SUM(LOG_COUNT)<br/>
		FROM ALSTICS<br/>
		WHERE PROC_DATE BETWEEN '20210301' AND '20210331'<br/>
		AND REP_KIND_CODE=''<br/>
		GROUP BY PROC_DATE<br/>
		LIMIT 100<br/>
	</blockquote>
	<blockquote>
		--- 기관개수<br/>
		SELECT COUNT(*)<br/>
		FROM alinst<br/>
		WHERE INST_CODE REGEXP '[0-8]..' AND INST_CODE <> '089' <br/>
		LIMIT 100<br/>
	</blockquote>
	<blockquote>
		-- 온라인업무개수 107<br/>
		SELECT COUNT(DISTINCT(SUBSTR(APPL_CODE,1,3)))<br/>
		FROM 	alappl<br/>
		WHERE APPL_CODE REGEXP '[0-8]...' AND<br/>
		APPL_CODE NOT REGEXP '16[2-9].' AND<br/>
		APPL_CODE NOT REGEXP '761.'<br/>
		LIMIT 300<br/>
	</blockquote>
	<blockquote>
		-- 배치업무개수 48<br/>
		SELECT count(DISTINCT(SUBSTR(APPL_CODE,1,3)))<br/>
		FROM 	albatappl<br/>
		WHERE APPL_CODE REGEXP '[0-8]..'<br/>
		LIMIT 300<br/>
	</blockquote>
	<blockquote>
		-- 1분당 거래 종류 분석<br/>
		SELECT SUBSTR(PROC_MTIME,1,4), KIND_CODE, TX_CODE, COUNT(*)<br/>
		FROM almstlog <br/>
		WHERE PROC_DATE='20211125' <br/>
		AND PROC_MTIME BETWEEN '081000000' AND '082000999' <br/>
		AND LOG_POINT='1' <br/>
		AND INST_CODE='099' AND APPL_CODE='012I'<br/>
		GROUP BY SUBSTR(PROC_MTIME,1,4), KIND_CODE, TX_CODE<br/>
		LIMIT 1000 <br/>
	</blockquote>
	<blockquote>
		-- 주윤정 펌뱅킹 인터페이스 등록현황<br/>
		SELECT APPL_CODE, REP_KIND_CODE, TX_CODE, NAME, REQ_HEAD_MAPP_CLASS,<br/>
		(SELECT MAPPING_INFO FROM ALTXMAPFLD WHERE SRC_FLD_INFO='sysIntfId' AND CLASS_NAME=A.REQ_HEAD_MAPP_CLASS) AS INTF_ID<br/>
		FROM ALTX A<br/>
		WHERE APPL_CODE IN ('761I', '762I', '763I', '764I', '766I', '767I', '768I', '771I', '772I', '773I', '732I', '713I')<br/>
		ORDER BY APPL_CODE, REP_KIND_CODE, TX_CODE<br/>
		LIMIT 10000<br/>
	</blockquote>
	<blockquote>
		-- MYSQL PROCESS LIST<br/>
		SELECT * FROM INFORMATION_SCHEMA.PROCESSLIST LIMIT 100 <br/>
	</blockquote>
	</td>
		<td valign="top">
			<h3>RESULT SET ${affect}</h3>
		<table>
		<c:forEach var="rows" items="${result}" varStatus="status">
		<tr>
			<c:forEach var="cols" items="${rows }" >
			<c:choose>
				<c:when test="${status.index == 0}"><th><c:out value="${cols }" /></th></c:when>
				<c:otherwise><td style="font-family: d2coding;"><c:out value="${cols }" /></td></c:otherwise>
			</c:choose>
			</c:forEach>
		</tr>
		</c:forEach>
		</table>
	</td>
</tr>
</table>
<span style="font-weight: bold; color: #BFFF00; background-color: #000080; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>