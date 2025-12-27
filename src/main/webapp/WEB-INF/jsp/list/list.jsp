<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>FEP 온라인거래조회(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<style>
tr:hover {
	background: #F0FFF0 !important;
}
pre { font-family: d2coding; font-size: 10pt; }
textarea { font-family: d2coding; font-size: 10pt; }
</style>
<script>
function pageNavi(index) {
	document.frm.currentPage.value=index;
	document.frm.submit();
}
function onSubmit(isError) {
	document.frm.logId.value="";
	document.frm.guid.value="";
	if ( isError === "true" )
		document.frm.isErr.value = "true";
	document.frm.currentPage.value=1;
	if ( document.frm.procDate.value === "" ) 
		alert("거래날짜 입력");
	else if ( document.frm.procMtime.value === "" ) 
		alert("기준시간 입력");
	else if ( document.frm.rangeMinute.value === "" ) 
		alert("조회분 입력");
	else {
		disableInput();
		document.frm.submit();
	}
}
function disableInput() {
	var input = document.querySelectorAll("input[type=button]");
	for ( var i=0; i<input.length; i++) {
		input[i].disabled='disabled';
	}
}
function onLastMinSubmit(min) {
	var date = new Date();
	date.setMinutes(date.getMinutes()-min);
	document.frm.currentPage.value=1;
	document.frm.rangeMinute.value=min+1;
	document.frm.procDate.value=formatDate(date);
	document.frm.procMtime.value=formatTime(date);
	document.frm.guid.value="";
	document.frm.logId.value="";
	disableInput();
	document.frm.submit();
}

function logIdSubmit2(logId) {
	document.frm.currentPage.value=1;
	document.frm.logId.value=document.frm.logId.value + ";" +logId;
	document.frm.instCode.value="";
	document.frm.applCode.value="";
	document.frm.procDate.value="";
	document.frm.procMtime.value="";
}
function logIdSubmit() {
	document.frm.currentPage.value=1;
	document.frm.instCode.value="";
	document.frm.applCode.value="";
	document.frm.procDate.value="";
	document.frm.procMtime.value="";
	document.frm.guid.value="";
	if ( document.frm.logId.value === "" )
		alert("로그ID 값을 입력");
	else  {
		disableInput();
		document.frm.submit();
	}
}
function guidSubmit() {
	document.frm.currentPage.value=1;
	document.frm.instCode.value="";
	document.frm.applCode.value="";
	document.frm.logId.value="";
	if ( document.frm.procDate.value === "" ) {
		alert("날짜가 없는경우 오늘날짜로 조회합니다.");
	}
	document.frm.procMtime.value="";
	if ( document.frm.guid.value === "" ) {
		alert("GUID 값 30자리를 입력");
	} else if ( document.frm.guid.value.length > 30 ) {
	 	document.frm.guid.value =  document.frm.guid.value.substr(0,30);
	 	disableInput();
		document.frm.submit();
	} else  {
		disableInput();
		document.frm.submit();
	}
}

/* 날짜 포맷 리턴 */
function formatDate(date) {
	var month = '' + (date.getMonth() + 1);
	var day = '' + date.getDate();
	var year = date.getFullYear();
	if ( month.length < 2 ) 
		month = '0' + month;
	if ( day.length < 2 ) 
		day = '0' + day;
	return [year, month, day].join('');
}
/* 시간 포맷 리턴 */
function formatTime(date) {
	var hour = '' + date.getHours();
	var min = '' + date.getMinutes();
	var sec = '' + date.getSeconds();
	var mSec = '' + date.getMilliseconds();
	if ( hour.length < 2 ) 
		hour = '0' + hour;
	if ( min.length < 2 ) 
		min = '0' + min;
	if ( sec.length < 2 ) 
		sec = '0' + sec;
	if ( mSec.length  < 3 ) {
		if ( mSec.length < 2 ) 
			mSec = '00' + mSec;
		else 
			mSec = '0' + mSec;
	}
	return [hour, min, sec, mSec].join('');
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
<h1  style="font-family: se-nanumsquare;"><span onclick="location.href='list'" style="cursor:pointer;">FEP 온라인거래조회(${env})</span></h1>
<form name="frm" action="list" method="GET" autocomplete="off">
<ul>
	<li>
		<b>[필수값입력]</b> 
		거래날짜 <input type="text" pattern="[0-9]{8}" name="procDate" value="${ procDate }" style="width: 80px;"/> |
		기준시간 <input type="text" pattern="[0-9]{6}" name="procMtime" value="${ procMtime }" style="width: 60px;"/> | 
		조회분 <input type="text" pattern="[0-9]+" name="rangeMinute" value="${ rangeMinute }" style="width: 30px;"/> | 
		LOGID <input type="text" pattern="[0-9A-Za-z]+" name="logId" value="${ logId }" style="width: 250px;"/>
		<input type="button" value="로그ID검색" onclick="logIdSubmit();" /> | 
		한페이지 Record 수 <input type="text" pattern="[0-9]+" name="recordPerPage" value="${ recordPerPage }" style="width: 40px;"/> | 
		GUID <input type="text" pattern="[0-9A-Za-z]+" name="guid" value="${guid}" style="width: 250px;"/>
		<input type="button" value="GUID검색" onclick="guidSubmit();" />
		<font color=red>${ errMessage }</font>
	</li>
	<li>
		<b>[옵션값입력]</b> 
		기관코드 <input type="text" pattern="[0-9]{3}" name="instCode" value="${ instCode }" style="width: 40px;"/> | 
		업무코드 <input type="text" pattern="[0-9A-Za-z]+" name="applCode" value="${ applCode }" style="width: 60px;" title="숫자만 입력가능 ex) 012" /> | 
		종별코드 <input type="text" pattern="[0-9A-Za-z]+" name="kindCode" value="${ kindCode }" style="width: 30px;"/> | 
		거래코드 <input type="text" pattern="[0-9A-Za-z]+" name="txCode" value="${ txCode }" style="width: 60px;"/> | 
		순서 <input type="text" pattern="[0-9]" name="logPoint" value="${ logPoint }" style="width: 20px;"/> | 
		<input type="button" value="조회" onclick="onSubmit(false);this.disabled='disabled'" />
		<input type="button" value="최근5분검색" onclick="onLastMinSubmit(5);" />
		<input type="button" value="최근10분검색" onclick="onLastMinSubmit(10);" />
		<input type="button" value="최근30분검색" onclick="onLastMinSubmit(30);" />
		<input type="button" value="최근1시간검색" onclick="onLastMinSubmit(60);" />
		<input type="button" value="에러만검색"  onclick="onSubmit('true');" />
		<input type="hidden" name="currentPage" id="currentPage" value="${ currentPage }" />
		<input type="hidden" name="pageBlock" value="${ pageBlock }"	/>
		<input type="hidden" name="isErr" value="false" />
		<input type="checkbox" name="reload" />RELOAD ${reloadMsg} 
		<input type="button" onclick="document.body.scrollIntoView(false);" value="맨아래로" />
	</li>
</ul>
</form>
<table style="width: 100%;">
<tr>
	<!-- th>거래일</th -->
	<th>거래시간</th>
	<th>기관코드</th>
	<th>업무코드</th>
	<!-- th>GUID</th -->
	<th>종별코드</th>
	<th>거래코드</th>
	<th>INTFID</th>
	<th>GID/SEQ-로그ID</th>
	<th>거래고유번호</th>
	<th>응답코드</th>
	<th>순서</th>
	<!-- th>메시지</th -->
	<th>헤더사이즈</th>
	<th>TX_UID</th>
	<th>ERR</th>
	<th>걸린시간</th>
	<th>비고</th>
	<!-- 
	<th>응답FLAG</th>
	<th>시간</th>
	<th>XID</th>
	<th>거래상태</th>
	<th>세션</th>
	<th>헤더매핑</th>
	<th>바디매핑</th>
	-->
	<th>상세보기</th>
	 
</tr>
<c:forEach var="row" items="${list}">
<tr align="center" style="line-height: 120%;">
	<td>
		<font color=#666666>
			${fn:substring(row.procDate,0,4)}-${fn:substring(row.procDate,4,6)}-${fn:substring(row.procDate,6,8)}
		</font><br/>
			${fn:substring(row.procMtime,0,2)}:${fn:substring(row.procMtime,2,4)}:${fn:substring(row.procMtime,4,6)}.${fn:substring(row.procMtime,6,9)}
	</td>
	<td>${row.instCode}</td>
	<td>${row.applCode}</td>
	<td>${row.kindCode}</td>
	<td>${row.txCode}</td>
	<td>
		${row.txName}<br/>
		<font color=#cccccc style="font-size: 8pt;">
			${fn:substring(row.msgDataStr,277,300)}
		</font>
	</td>
	<td>
		${fn:substring(row.msgDataStr,12,42)}/${fn:substring(row.msgDataStr,42,44)}<br/>
		<font color="#cccccc" style="font-size: 8pt;cursor:pointer;" onclick="logIdSubmit2('${row.logId}');">${row.logId}</font>
	</td>
	<td>${row.trxSeqNum}</td>
	<td>${row.trxRespCode}</td>
	<c:choose>
		<c:when test="${row.logPoint eq '2'}" ><td style="background-color: #ffffdd;">${row.logPoint}</td></c:when>
		<c:when test="${row.logPoint eq '3'}" ><td style="background-color: #eeeecc;">${row.logPoint}</td></c:when>
		<c:when test="${row.logPoint eq '4'}" ><td style="background-color: #ddddbb;">${row.logPoint}</td></c:when>
		<c:when test="${row.logPoint eq '6'}" ><td style="background-color: #ddffee;">${row.logPoint}</td></c:when>
		<c:when test="${row.logPoint eq '7'}" ><td style="background-color: #cceedd;">${row.logPoint}</td></c:when>
		<c:when test="${row.logPoint eq '8'}" ><td style="background-color: #bbddcc;">${row.logPoint}</td></c:when>
		<c:when test="${row.logPoint eq '9'}" ><td style="background-color: #ddeeff;">${row.logPoint}</td></c:when>
		<c:otherwise><td>${row.logPoint}</td></c:otherwise>
	</c:choose>
	<td>${row.headerSize}</td>
	<td>${row.txUid}</td>
	<c:choose>
		<c:when test="${row.errCode eq '0'}"><td>&nbsp;</td></c:when>
		<c:otherwise>
			<td style="background-color: #ffafd8;">${row.errCode}</td>
		</c:otherwise>
	</c:choose>
	<c:choose>
		<c:when test="${row.txTime > 2000 }">
			<td style="background-color: #f2cfa5;">${row.txTime}</td>
		</c:when>
		<c:otherwise>
			<td>${row.txTime}</td>
		</c:otherwise>
	</c:choose>
	<td>${row.resFlag}/${row.procHour}/${row.xid}/${row.txState}/${row.sessionIndex}/${row.headMappingType}/${row.bodyMappingType}
	</td>
	<td><input type="button" value="상세보기" onclick="window.open('detail?logId=${row.logId}&logPoint=${row.logPoint}','${row.logId}_${row.logPoint}','width=700, height=850, scrollbars=yes');" /></td>
</tr>
</c:forEach>
</table>

<!-- 페이징 네비게이션 -->
<c:set var="currentPage">${currentPage}</c:set>
<c:set var="pageBlock">${pageBlock}</c:set>
<c:set var="totalPage">${totalPage}</c:set>
<fmt:parseNumber var="currentBlock" integerOnly="true" value="${currentPage/pageBlock }" />
<fmt:parseNumber var="lastBlock" integerOnly="true" value="${totalPage/pageBlock }" />
<c:set var="startNum" value="${currentBlock*pageBlock+1 }" />
<c:set var="endNum" value="${currentBlock*pageBlock+pageBlock}" />

<c:if test="${endNum > totalPage}">
	<c:set var="endNum" value="${totalPage}" />
</c:if>

총 ${totalCnt}건 검색 | 
<c:if test="${currentBlock != 0 }">
	<a href=# onclick="pageNavi('1');" >[맨처음]</a>
	<a href=# onclick="pageNavi('${currentPage-1}');" >[이전]</a> | 
</c:if>
<c:forEach var="index" begin="${startNum}" end="${endNum}" varStatus="status">
	<c:choose>
		<c:when test="${index == currentPage}">
			[${index}]
		</c:when>
		<c:when test="${index > endNum}">&nbsp;</c:when>
		<c:otherwise><a href=# onclick="pageNavi('${index}');" >${index}</a></c:otherwise>
	</c:choose>
	<c:if test="${!status.last}"> | </c:if>
</c:forEach>
<c:if test="${currentBlock != lastBlock}">
	 | <a href=# onclick="pageNavi('${endNum+1}');" >[이후]</a>
	 <a href=# onclick="pageNavi('${totalPage}');" >[맨끝]</a>
</c:if>
<!-- 페이징 네비게이션 -->
 <input type="button" onclick="document.body.scrollIntoView(true);" value="맨위로" />
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>