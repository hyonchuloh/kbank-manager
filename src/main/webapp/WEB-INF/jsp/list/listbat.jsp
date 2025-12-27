<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR" %>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>FEP ��ġ�ŷ���ȸ(${env})</title>
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
function onSubmit() {
	document.frm.currentPage.value=1;
	if ( document.frm.strtDate.value === "" ) 
		alert("�ŷ���¥ �Է�");
	else if ( document.frm.strtTime.value === "" ) 
		alert("���ؽð� �Է�");
	else if ( document.frm.rangeMinute.value === "" ) 
		alert("��ȸ�� �Է�");
	else
		document.frm.submit();
}
function onLastMinSubmit(min) {
	document.frm.currentPage.value=1;
	document.frm.rangeMinute.value=min+1;

	var date = new Date();
	date.setMinutes(date.getMinutes()-min);
	document.frm.strtTime.value=formatTime(date);
	if ( min == 1439 ) {
		document.frm.strtTime.value='000000';
	} else {
		document.frm.strtDate.value=formatDate(date);
	}
	document.frm.submit();
}
/* ��¥ ���� ���� */
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
/* �ð� ���� ���� */
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
		<b style="cursor: pointer;" onclick="location.href='/logout';">${sessionUserName }�� �ȳ��ϼ���</b> | 
		<a href="/log/list">�¶��� �α���ȸ</a> | 
		<a href="/log/listbat">��ġ �α���ȸ</a> | 
	</span>
	<img src="/images/kbank_logo.gif" height="30px" style="vertical-align: middle;" />
</div>
<h1  style="font-family: se-nanumsquare;"><span onclick="location.href='listbat'" style="cursor:pointer;">FEP ��ġ�ŷ���ȸ(${env})</span></h1>
<form name="frm" action="listbat" method="get" autocomplete="off">
<ul>
	<li>
		<b>[�ʼ����Է�]</b> 
		�ŷ���¥ <input type="text" pattern="[0-9]{8}" name="strtDate" value="${ strtDate }" style="width: 80px;"/> | 
		���ؽð� <input type="text" pattern="[0-9]{6}" name="strtTime" value="${ strtTime }" style="width: 60px;"/> | 
		��ȸ�� <input type="text" pattern="[0-9]+" name="rangeMinute" value="${ rangeMinute }" style="width: 30px;"/> | 
		�������� Record �� <input type="text" pattern="[0-9]+" name="recordPerPage" value="${ recordPerPage }" style="width: 20px;"/>
	</li>
	<li>
		<b>[�ɼǰ��Է�]</b> 
		����ڵ� <input type="text" pattern="[0-9]{3}" name="instCode" value="${ instCode }" style="width: 50px;"/> | 
		�����ڵ� <input type="text" pattern="[0-9A-Za-z]+" name="applCode" value="${ applCode }" style="width: 50px;"/> |
		<!-- input type="checkbox" name="srFlagS" ${ checkedSrFlagS }/>�۽� 
		 <input type="checkbox" name="srFlagR" ${ checkedSrFlagR }/>���� -->
		 <input type="checkbox" name="isErr" ${ checkedIsErr } />631����
		<input type="button" value="��ȸ" onclick="onSubmit();" />
		<input type="button" value="�ֱ�30�а˻�" onclick="onLastMinSubmit(30);" />
		<input type="button" value="�ֱ�1�ð��˻�" onclick="onLastMinSubmit(60);" />
		<input type="button" value="�ֱ�3�ð��˻�" onclick="onLastMinSubmit(180);" />
		<input type="button" value="�Ϸ�˻�" onclick="onLastMinSubmit(1439);" />
		<input type="hidden" name="currentPage" id="currentPage" value="${ currentPage }" />
		<input type="hidden" name="pageBlock" value="${ pageBlock }"	/> | 
		<c:choose>
			<c:when test="${env eq 'prod' }">
				<input type="button" value="EAI WEB ADMIN" onclick="window.open('http://samplewithu.com','_blank').focus();" />
			</c:when>
			<c:when test="${env eq 'stag' }">
				<input type="button" value="EAI WEB ADMIN" onclick="window.open('http://samplewithu.com','_blank').focus();" />
			</c:when>
			<c:otherwise>
				<input type="button" value="EAI WEB ADMIN" onclick="window.open('http://samplewithu.com','_blank').focus();" />
			</c:otherwise>
		
		</c:choose>
	</li>
</ul>
</form>
<table style="width: 100%;">
<tr>
	<th>���۳�¥</th>
	<th>���۽ð�/����ð�</th>
	<th>�ɸ��ð�(ms)</th>
	
	<th>����ڵ�</th>
	<th>�����ڵ�</th>
	<th>��/����</th>
	<th>�����ڵ�</th>
	
	<th>���ϰ��</th>
	<th>���س�¥</th>
	
	<th>����ܰ�</th>
	<th>��������</th>
	<th>�����ڵ�</th>
	<th>���ۼ۽�Ÿ��</th>
	
	<th>�����ϰ���</th>
	<th>�����ڵ�</th>
	<!-- 
	<th>�ʷ�1</th>
	<th>�ʷ�2</th>
	 -->
	 <th>�α�</th>
</tr>
<c:forEach var="row" items="${list}">
<c:set var="backColor">#ffffff</c:set>
<c:choose>
	<c:when test="${row.failCode eq '631'}">
		<c:set var="backColor">#fafafa</c:set>
	</c:when>
	<c:when test="${row.failCode > 0 }">
		<c:set var="backColor">#FFCFDA</c:set>
	</c:when>
</c:choose>
<tr align="center" style="background-color: ${backColor}; line-height: 120%;">
	<td>${row.strtDate}</td>
	<td>
		${fn:substring(row.strtTime,0,2)}:${fn:substring(row.strtTime,2,4)}:${fn:substring(row.strtTime,4,6)}.${fn:substring(row.strtTime,6,9)}<br/>
		${fn:substring(row.endTime,0,2)}:${fn:substring(row.endTime,2,4)}:${fn:substring(row.endTime,4,6)}.${fn:substring(row.endTime,6,9)}
	</td>
	<td>${row.elapsTime}</td>
	<td>${row.instCode}</td>
	<td>${row.applCode}</td>
	<td>${row.srFlag}</td>
	<td>${row.fileCode}<br/><font color="#cccccc" style="font-size: 8pt;">${row.eaiIntfId}</font></td>
	<td>${row.fileName}</td>
	<td>${row.endDate}</td>
	<td>${row.extProcFlag}</td>
	<td>${row.extFinNo}</td>
	<td title="${row.errMsg}">${row.failCode}</td>
	<td>${row.strtSendType}</td>
	<td>${row.totFileNo}</td>
	<td>${row.resCode}</td>
	<td><input type="button" value="����α�" onclick="window.open('list?procDate=${row.strtDate}&procMtime=${fn:substring(row.endTime,0,6)}&rangeMinute=1&logId=&recordPerPage=20&guid=&instCode=089&currentPage=1&pageBlock=10&isErr=false','_blank').focus();" /></td>
</tr>
</c:forEach>
</table>

<!-- ����¡ �׺���̼� -->
<c:set var="currentPage">${currentPage}</c:set>
<c:set var="pageBlock">${pageBlock}</c:set>
<c:set var="totalPage">${totalPage}</c:set>
<fmt:parseNumber var="currentBlock" integerOnly="true" value="${currentPage/pageBlock}" />
<fmt:parseNumber var="lastBlock" integerOnly="true" value="${totalPage/pageBlock}" />
<c:set var="startNum" value="${currentBlock*pageBlock+1 }" />
<c:set var="endNum" value="${currentBlock*pageBlock+pageBlock}" />

<c:if test="${endNum > totalPage}">
	<c:set var="endNum" value="${totalPage}" />
</c:if>

�� ${totalCnt}�� �˻� | 
<c:if test="${currentBlock != 0 }">
	<a href=# onclick="pageNavi('1');" >[��ó��]</a>
	<a href=# onclick="pageNavi('${currentPage-1}');" >[����]</a> | 
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
	 | <a href=# onclick="pageNavi('${endNum+1}');" >[����]</a>
	 <a href=# onclick="pageNavi('${totalPage}');" >[�ǳ�]</a>
</c:if>
<!-- ����¡ �׺���̼� -->
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>