<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% pageContext.setAttribute("newLineChar", "\n"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP회선대장 - ${searchKey}</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<script>
function saveItem() {
	document.frm_insert.seqNo.value="-1";
	document.frm_insert.extCd.value=document.getElementById("insert_extCd").innerText;
	document.frm_insert.extNm.value=document.getElementById("insert_extNm").innerText;
	document.frm_insert.bizCd.value=document.getElementById("insert_bizCd").innerText;
	document.frm_insert.bizNm.value=document.getElementById("insert_bizNm").innerText;
	document.frm_insert.bizType.value=document.getElementById("insert_bizType").innerText;
	document.frm_insert.bizClcd.value=document.getElementById("insert_bizClcd").innerText;
	document.frm_insert.nwLine.value=document.getElementById("insert_nwLine").innerText;
	document.frm_insert.nwRouter.value="";
	document.frm_insert.fwVpn.value="";
	document.frm_insert.devClcd.value=document.getElementById("insert_devClcd").innerText;
	document.frm_insert.kbkIp.value=document.getElementById("insert_kbkIp").innerText;
	document.frm_insert.kbkNatIp.value=document.getElementById("insert_kbkNatIp").innerText;
	document.frm_insert.kbkPort.value=document.getElementById("insert_kbkPort").innerText;
	document.frm_insert.extIp.value=document.getElementById("insert_extIp").innerText;
	document.frm_insert.extPort.value=document.getElementById("insert_extPort").innerText;
	document.frm_insert.srType.value=document.getElementById("insert_srType").innerText;
	document.frm_insert.extUser.value=document.getElementById("insert_extUser").innerText;
	document.frm_insert.history.value=document.getElementById("insert_history").innerText;
	document.frm_insert.searchKey.value=document.frm_select.searchKey.value;
	document.frm_insert.submit();
}
function deleteItem(seqNo) {
	if ( confirm("삭제하시겠습니까? " + seqNo) ) {
		document.frm_delete.seqNo.value=seqNo;
		document.frm_delete.submit();
	}
}
function editItem(seqNo) {
	document.frm_edit.seqNo.value=seqNo;
	document.frm_edit.extCd.value=document.getElementById("edit_extCd_"+seqNo).innerText;
	document.frm_edit.extNm.value=document.getElementById("edit_extNm_"+seqNo).innerText;
	document.frm_edit.bizCd.value=document.getElementById("edit_bizCd_"+seqNo).innerText;
	document.frm_edit.bizNm.value=document.getElementById("edit_bizNm_"+seqNo).innerText;
	document.frm_edit.bizType.value=document.getElementById("edit_bizType_"+seqNo).innerText;
	document.frm_edit.bizClcd.value=document.getElementById("edit_bizClcd_"+seqNo).innerText;
	document.frm_edit.nwLine.value=document.getElementById("edit_nwLine_"+seqNo).innerText;
	document.frm_edit.nwRouter.value="";
	document.frm_edit.fwVpn.value="";
	document.frm_edit.devClcd.value=document.getElementById("edit_devClcd_"+seqNo).innerText;
	document.frm_edit.kbkIp.value=document.getElementById("edit_kbkIp_"+seqNo).innerText;
	document.frm_edit.kbkNatIp.value=document.getElementById("edit_kbkNatIp_"+seqNo).innerText;
	document.frm_edit.kbkPort.value=document.getElementById("edit_kbkPort_"+seqNo).innerText;
	document.frm_edit.extIp.value=document.getElementById("edit_extIp_"+seqNo).innerText;
	document.frm_edit.extPort.value=document.getElementById("edit_extPort_"+seqNo).innerText;
	document.frm_edit.srType.value=document.getElementById("edit_srType_"+seqNo).innerText;
	document.frm_edit.extUser.value=document.getElementById("edit_extUser_"+seqNo).innerText;
	document.frm_edit.history.value=document.getElementById("edit_history_"+seqNo).innerText;
	document.frm_edit.searchKey.value=document.frm_select.searchKey.value;
	document.frm_edit.submit();
}
function inputValues() {
	var input = document.getElementById("insert_devClcd").innerText;
	if ( input === "D" ) {
		document.getElementById("insert_kbkIp").innerText = "127.0.0.1";
		document.getElementById("insert_kbkNatIp").innerText = "127.0.0.1";
	} else if ( input === "P" ) {
		document.getElementById("insert_kbkIp").innerText = "127.0.0.1\n(127.0.0.1~127.0.0.1)";
		document.getElementById("insert_kbkNatIp").innerText = "127.0.0.1";
	}
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
<h1 style="cursor: hand;" ondblclick="location.href='/admin/line2';">FEP/API 대외기관 통신회선대장</h1>
<form name="frm_select" action="line2" method="GET" autocomplete="off">
<ul>
	<li>
		기관코드 : <input type="text" pattern="[0-9A-Za-z]+" name="searchExtCd" value="${searchExtCd }" onchange="frm_edit.searchExtCd.value=this.value; frm_insert.searchExtCd.value=this.value; frm_delete.searchExtCd.value=this.value;" style="width: 50px;"/> | 
		업무코드 : <input type="text" pattern="[0-9A-Za-z]+" name="searchBizCd" value="${searchBizCd }" onchange="frm_edit.searchBizCd.value=this.value; frm_insert.searchBizCd.value=this.value; frm_delete.searchBizCd.value=this.value;" style="width: 50px;"/> | 
		온라인/배치구분(O/B) : <input type="text" pattern="[A-Z]+" name="searchBizType" value="${searchBizType }" onchange="frm_edit.searchBizType.value=this.value; frm_insert.searchBizType.value=this.value; frm_delete.searchBizType.value=this.value;" style="width: 30px;"/> | 
		목적지업무코드 : <input type="text" pattern="[A-Z]+" name="searchBizClcd" value="${searchBizClcd }" onchange="frm_edit.searchBizClcd.value=this.value; frm_insert.searchBizClcd.value=this.value; frm_delete.searchBizClcd.value=this.value;" style="width: 30px;"/> | 
		개발/운영구분(D/S/P) : <input type="text" pattern="[A-Z]+" name="searchDevClcd" value="${searchDevClcd }" onchange="frm_edit.searchDevClcd.value=this.value; frm_insert.searchDevClcd.value=this.value; frm_delete.searchDevClcd.value=this.value;"  style="width: 30px;"/> | 
		검색어 : <input type="text" name="searchKey" value="${searchKey }" onchange="frm_edit.searchKey.value=this.value; frm_insert.searchKey.value=this.value; frm_delete.searchKey.value=this.value;" style="width: 150px;"/> 
		<input type="submit" value="SUBMIT" /> <input type="button" value="EXCEL" onclick="location.href='/admin/line2-excel';" />
		<input type="button" value="방화벽정책신청서" onclick="window.open('/admin/line2-excel-firewall?searchExtCd=${searchExtCd }&searchBizCd=${searchBizCd }&searchBizType=${searchBizType }&searchBizClcd=${searchBizClcd }&searchDevClcd=${searchDevClcd }&searchKey=${searchKey }');" />
		<input type="button" value="L4_SLB신청서" onclick="window.open('/admin/line2-excel-l4?searchExtCd=${searchExtCd }&searchBizCd=${searchBizCd }&searchBizType=${searchBizType }&searchBizClcd=${searchBizClcd }&searchDevClcd=${searchDevClcd }&searchKey=${searchKey }');" />
		<input type="button" value="라우팅신청서" onclick="window.open('/admin/line2-excel-l3?searchExtCd=${searchExtCd }&searchBizCd=${searchBizCd }&searchBizType=${searchBizType }&searchBizClcd=${searchBizClcd }&searchDevClcd=${searchDevClcd }&searchKey=${searchKey }');" />
		<input type="button" value="단위테스트" onclick="window.open('/admin/line2-excel-test1?searchExtCd=${searchExtCd }&searchBizCd=${searchBizCd }&searchBizType=${searchBizType }&searchBizClcd=${searchBizClcd }&searchDevClcd=${searchDevClcd }&searchKey=${searchKey }');" />
		<input type="button" value="통합테스트" onclick="window.open('/admin/line2-excel-test2?searchExtCd=${searchExtCd }&searchBizCd=${searchBizCd }&searchBizType=${searchBizType }&searchBizClcd=${searchBizClcd }&searchDevClcd=${searchDevClcd }&searchKey=${searchKey }');" />
	</li>
</ul>
</form>
<table style="width: 100%;">
<thead>
	<!-- col width="10px" / -->
	<col width="20px" />
	<col width="150px" />
	
	<col width="20px" />
	<col width="150px" />
	<col width="10px" /> <!-- 타입 -->
	<col width="40px" />
	
	<col width="130px" />
	<!-- col width="100px" />
	<col width="100px" / -->
	
	<col width="20px" />
	<col width="100px" />
	<col width="100px" />
	<col width="50px" />
	<col width="100px" />
	<col width="50px" />
	<col width="50px" />
	
	<col width="200px" />
	<col width="200px" />
	<col width="100px" />
	<tr>
		<!-- th >NO</th -->
		<th>기관<br/>코드</th>
		<th>기관명</th>
		<th>업무<br/>코드</th>
		<th>업부명</th>
		<th>타<br/>입</th>
		<th>상대<br/>업무</th>
		<th>전용회선정보</th>
		<!-- th>nwRouter</th>
		<th>fwVpn</th -->
		<th>환<br/>경</th>
		<th>Kbank<br/>로컬IP</th>
		<th>Kbank<br/>공인IP</th>
		<th>Kbank<br/>PORT</th>
		<th>대외기관<br/>IP</th>
		<th>대외기관<br/>PORT</th>
		<th>S/R</th>
		<th>담당자정보</th>
		<th>변경이력</th>
		<th>-</th>
	</tr>
</thead>
<tr align='center'>
	<td contenteditable='true' id="insert_extCd" ></td>
	<td contenteditable='true' id="insert_extNm" ></td>	
	<td contenteditable='true' id="insert_bizCd" ></td>
	<td contenteditable='true' id="insert_bizNm" ></td>	
	<td contenteditable='true' id="insert_bizType" ></td>
	<td contenteditable='true' id="insert_bizClcd" ></td>	
	<td contenteditable='true' id="insert_nwLine" ></td>
	<!-- td contenteditable='true' id="insert_nwRouter" ></td>	
	<td contenteditable='true' id="insert_fwVpn" ></td -->
	<td contenteditable='true' id="insert_devClcd" onkeyup="inputValues();" ></td>	
	<td contenteditable='true' id="insert_kbkIp" ></td>
	<td contenteditable='true' id="insert_kbkNatIp" ></td>	
	<td contenteditable='true' id="insert_kbkPort" ></td>
	<td contenteditable='true' id="insert_extIp" ></td>	
	<td contenteditable='true' id="insert_extPort" ></td>
	<td contenteditable='true' id="insert_srType" ></td>	
	<td  align='left' contenteditable='true' id="insert_extUser" ></td>
	<td  align='left' contenteditable='true' id="insert_history" ></td>	
	<td><input type="button" value="ADD" onclick="saveItem();"></td>
</tr>
<c:forEach var="row" items="${list}">
<c:set var="tr_color" value="#ffffff" />
<c:set var="tr_font_style" value="normal" />
<c:choose>
	<c:when test="${row.srType eq '일몰'}">
		<c:set var="tr_color" value="#C5C6D0" />
		<c:set var="tr_font_style" value="italic" />
	</c:when>
	<c:when test="${row.devClcd eq 'P'}">
		<c:set var="tr_color" value="#F0FFF0" />
	</c:when>
</c:choose>
<tr align="center" style="background-color: ${tr_color}; font-style: ${tr_font_style}">
	<!-- td  >${row.seqNo }</td> -->
	<td contenteditable='true' id='edit_extCd_${row.seqNo}'><b>${row.extCd }</b></td>
	<td contenteditable='true' id='edit_extNm_${row.seqNo}'>${fn:replace(row.extNm, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_bizCd_${row.seqNo}'><b>${row.bizCd }</b></td>
	<td contenteditable='true' id='edit_bizNm_${row.seqNo}'>${fn:replace(row.bizNm, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_bizType_${row.seqNo}'>${fn:replace(row.bizType, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_bizClcd_${row.seqNo}'>${fn:replace(row.bizClcd, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_nwLine_${row.seqNo}'>${fn:replace(row.nwLine, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_devClcd_${row.seqNo}'>${fn:replace(row.devClcd, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_kbkIp_${row.seqNo}'><font style="color: #CCC;">${fn:replace(row.kbkIp, newLineChar, "<br/>") }</font></td>
	<td contenteditable='true' id='edit_kbkNatIp_${row.seqNo}'>${fn:replace(row.kbkNatIp, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_kbkPort_${row.seqNo}'>${fn:replace(row.kbkPort, newLineChar, "<br/>") }</td>
	<td contenteditable='true' id='edit_extIp_${row.seqNo}'><b>${fn:replace(row.extIp, newLineChar, "<br/>") }</b></td>
	<td contenteditable='true' id='edit_extPort_${row.seqNo}'><b>${fn:replace(row.extPort, newLineChar, "<br/>") }</b></td>
	<td contenteditable='true' id='edit_srType_${row.seqNo}'><font style="color: #CCC;">${fn:replace(row.srType, newLineChar, "<br/>") }</font></td>
	<td contenteditable='true' align='left' id='edit_extUser_${row.seqNo}' >${fn:replace(row.extUser, newLineChar, "<br/>")}</td>
	<td contenteditable='true' align='left' id='edit_history_${row.seqNo}' >${fn:replace(row.history, newLineChar, "<br/>")}</td>
	<td>
		<input type="button" value="수정" onclick="editItem('${row.seqNo}');" /><br/>
		<input type="button" value="삭제" onclick="deleteItem('${row.seqNo}');"/>
	</td>
</tr>
</c:forEach>
</tbody>
</table>
<form name="frm_insert" action="line2-insert" method="POST">
	<input type="hidden" name="seqNo" value="" />
	<input type="hidden" name="extCd" value="" />
	<input type="hidden" name="extNm" value="" />
	<input type="hidden" name="bizCd" value="" />
	<input type="hidden" name="bizNm" value="" />
	<input type="hidden" name="bizType" value="" />
	<input type="hidden" name="bizClcd" value="" />
	<input type="hidden" name="nwLine" value="" />
	<input type="hidden" name="nwRouter" value="" />
	<input type="hidden" name="fwVpn" value="" />
	<input type="hidden" name="devClcd" value="" />
	<input type="hidden" name="kbkIp" value="" />
	<input type="hidden" name="kbkNatIp" value="" />
	<input type="hidden" name="kbkPort" value="" />
	<input type="hidden" name="extIp" value="" />
	<input type="hidden" name="extPort" value="" />
	<input type="hidden" name="srType" value="" />
	<input type="hidden" name="extUser" value="" />
	<input type="hidden" name="history" value="" />
	<input type="hidden" name="searchExtCd" value="${searchExtCd }" />
	<input type="hidden" name="searchBizCd" value="${searchBizCd }" />
	<input type="hidden" name="searchBizClcd" value="${searchBizClcd }" />
	<input type="hidden" name="searchBizType" value="${searchBizType }" />
	<input type="hidden" name="searchDevClcd" value="${searchDevClcd }" />
	<input type="hidden" name="searchKey" value="${searchKey }" />
</form>
<form name="frm_delete" action="line2-delete" method="POST">
	<input type="hidden" name="seqNo" value="" />
	<input type="hidden" name="searchExtCd" value="${searchExtCd }" />
	<input type="hidden" name="searchBizCd" value="${searchBizCd }" />
	<input type="hidden" name="searchBizClcd" value="${searchBizClcd }" />
	<input type="hidden" name="searchBizType" value="${searchBizType }" />
	<input type="hidden" name="searchDevClcd" value="${searchDevClcd }" />
	<input type="hidden" name="searchKey" value="${searchKey }" />
</form>
<form name="frm_edit" action="line2-update" method="POST">
	<input type="hidden" name="seqNo" value="" />
	<input type="hidden" name="extCd" value="" />
	<input type="hidden" name="extNm" value="" />
	<input type="hidden" name="bizCd" value="" />
	<input type="hidden" name="bizNm" value="" />
	<input type="hidden" name="bizType" value="" />
	<input type="hidden" name="bizClcd" value="" />
	<input type="hidden" name="nwLine" value="" />
	<input type="hidden" name="nwRouter" value="" />
	<input type="hidden" name="fwVpn" value="" />
	<input type="hidden" name="devClcd" value="" />
	<input type="hidden" name="kbkIp" value="" />
	<input type="hidden" name="kbkNatIp" value="" />
	<input type="hidden" name="kbkPort" value="" />
	<input type="hidden" name="extIp" value="" />
	<input type="hidden" name="extPort" value="" />
	<input type="hidden" name="srType" value="" />
	<input type="hidden" name="extUser" value="" />
	<input type="hidden" name="history" value="" />
	<input type="hidden" name="searchExtCd" value="${searchExtCd }" />
	<input type="hidden" name="searchBizCd" value="${searchBizCd }" />
	<input type="hidden" name="searchBizClcd" value="${searchBizClcd }" />
	<input type="hidden" name="searchBizType" value="${searchBizType }" />
	<input type="hidden" name="searchDevClcd" value="${searchDevClcd }" />
	<input type="hidden" name="searchKey" value="${searchKey }" />
</form>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>