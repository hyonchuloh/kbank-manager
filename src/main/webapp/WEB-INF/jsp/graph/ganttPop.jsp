<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP 프로젝트 상세보기</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<script>
function deleteItem(seqNo) {
	if ( confirm("삭제하시겠습니까? " + seqNo) ) {
		document.frm.action="/manager/gantt/delete";
		document.frm.submit();
	}
}
function insert() {
	moveValues();
	document.frm.action="/manager/gantt/insert";
	document.frm.submit();
}
function edit(seqNo) {
	moveValues();
	document.frm.action="/manager/gantt/edit";
	document.frm.submit();
}
function moveValues() {
	document.frm.prjName.value=document.getElementById("prjName").innerText;
	document.frm.startDate.value=document.getElementById("startDate").innerText;
	document.frm.endDate.value=document.getElementById("endDate").innerText;
	document.frm.developer.value=document.getElementById("developer").innerText;
	document.frm.prjMemo.value=document.getElementById("prjMemo").innerText;
	document.frm.check1.value=document.getElementById("check1").innerText;
	document.frm.check2.value=document.getElementById("check2").innerText;
	document.frm.check3.value=document.getElementById("check3").innerText;
	document.frm.check4.value=document.getElementById("check4").innerText;
	document.frm.check5.value=document.getElementById("check5").innerText;
	document.frm.check6.value=document.getElementById("check6").innerText;
	document.frm.check7.value=document.getElementById("check7").innerText;
	document.frm.check8.value=document.getElementById("check8").innerText;
	document.frm.check9.value=document.getElementById("check9").innerText;
	document.frm.check10.value=document.getElementById("check10").innerText;
}
</script>
</head>
<body>
<h2>FEP 프로젝트 상세보기</h2>
<ul>
	<li>프로젝트 일정을 입력해주세요</li>
</ul>
<table style="width: 100%; table-layout:fixed;">
<tr>
	<th style="width: 180px;">연번</th><td>${item.seqNo}</td>
</tr><tr>
	<th>업무제목</th><td id="prjName" contenteditable='true' >${item.prjName}</td>
</tr><tr>
	<th>시작일</th><td id="startDate" contenteditable='true'>${item.startDate}</td>
</tr><tr>
	<th>종료일</th><td id="endDate" contenteditable='true'>${item.endDate}</td>
</tr><tr>
	<th>개발자(오현철/김성아)</th><td id="developer" contenteditable='true'>${item.developer}</td>
</tr><tr>
	<th>①형상관리</th><td id="check1" contenteditable='true'>${item.check1}</td>
</tr><tr>
	<th>②개발통신정책</th><td id="check2" contenteditable='true'>${item.check2}</td>
</tr><tr>
	<th>③프로그램개발</th><td id="check3" contenteditable='true'>${item.check3}</td>
</tr><tr>
	<th>④스테이징반영</th><td id="check4" contenteditable='true'>${item.check4}</td>
</tr><tr>
	<th>⑤테스트</th><td id="check5" contenteditable='true'>${item.check5}</td>
</tr><tr>
	<th>⑥보안성심의</th><td id="check6" contenteditable='true'>${item.check6}</td>
</tr><tr>
	<th>⑦운영통신정책</th><td id="check7" contenteditable='true'>${item.check7}</td>
</tr><tr>
	<th>⑧운영반영</th><td id="check8" contenteditable='true'>${item.check8}</td>
</tr><tr>
	<th>⑨패밀리오픈</th><td id="check9" contenteditable='true'>${item.check9}</td>
</tr><tr>
	<th>⑩대고객오픈</th><td id="check10" contenteditable='true'>${item.check10}</td>
</tr>
<tr>
	<th>업무메모</th><td id="prjMemo" contenteditable='true'>${item.prjMemo}</td>
</tr>
<tr>
	<th>최종수정일</th><td>${item.lastupdate}</td>
</tr>
</table>
<p align="center">
<c:choose>
<c:when  test="${item.seqNo == 0}">
	<input type="button" value="SUBMIT" onclick="insert();"/>
</c:when>
<c:otherwise>
	<input type="button" value="SUBMIT" onclick="edit(${item.seqNo});"/>
</c:otherwise>
</c:choose>
<input type="button" value="삭제" onclick="deleteItem(${item.seqNo});" />
</p>
<form name="frm" method="POST">
	<input type="hidden" name="seqNo" value="${item.seqNo}"/>
	<input type="hidden" name="prjName" />
	<input type="hidden" name="startDate" />
	<input type="hidden" name="endDate" />
	<input type="hidden" name="developer" />
	<input type="hidden" name="prjMemo" />
	<input type="hidden" name="check1" />
	<input type="hidden" name="check2" />
	<input type="hidden" name="check3" />
	<input type="hidden" name="check4" />
	<input type="hidden" name="check5" />
	<input type="hidden" name="check6" />
	<input type="hidden" name="check7" />
	<input type="hidden" name="check8" />
	<input type="hidden" name="check9" />
	<input type="hidden" name="check10" />
</form>
</body>
</html>