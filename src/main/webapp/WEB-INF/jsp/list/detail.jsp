<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>FEP 온라인상세조회(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<style>
.headercolumn { border: 0px; width: 99%; color: blue; font-family: d2coding;}
textarea {font-family: d2coding;}
</style>
<script>
function changeData(checkbox) {

	var flatData = "${item.flatData}";
	var utf8Data = "${item.utf8Data}";
	
	if ( checkbox.checked ) {
		document.getElementById("dataArea").value=utf8Data;
	} else {
		document.getElementById("dataArea").value=flatData;
	}
}
function toggleView() {
	var area = document.getElementById("toggle");
	if ( area.style.display == "" || area.style.display == "block" ) {
		area.style.display = "none";
	} else {
		area.style.display = "block";
	}
}
function toggleView2() {
	var area = document.getElementById("toggle2");
	if ( area.style.display == "" || area.style.display == "block" ) {
		area.style.display = "none";
	} else {
		area.style.display = "block";
	}
}
function init() {
	var input="${fn:substring(item.msgDataStr,0,500)}";
	var colsize = [8,3,1,14,8,8,2,32,1,6,3,1,12,2,2,1,1,1,16,13,13,15,1,1,1,1,1,3,32,12,1,1,32,3,3,2,10,10,23,20,1,15,5,15,14,15,5,14,1,1,2,92];
	var pointer = 0;
	var targetcol = document.getElementsByName("comHeadercols");
	for ( var i=0; i< targetcol.length; i++) {
		targetcol[i].value = "["+input.substr(pointer, colsize[i])+"]";
		pointer += colsize[i];
	}
	document.getElementById("toggle").style.display = "none";
	document.getElementById("toggle2").style.display = "none";
}
function user_parsing() {
	var input=document.getElementById("stdtx500").value;
	var colsize = [8,3,1,14,8,8,2,32,1,6,3,1,12,2,2,1,1,1,16,13,13,15,1,1,1,1,1,3,32,12,1,1,32,3,3,2,10,10,23,20,1,15,5,15,14,15,5,14,1,1,2,92];
	var pointer = 0;
	var targetcol = document.getElementsByName("comHeadercols");
	for ( var i=0; i< targetcol.length; i++) {
		targetcol[i].value = "["+input.substr(pointer, colsize[i])+"]";
		pointer += colsize[i];
	}
}
</script>
</head>
<body onload="init();">
<H2 style="font-family: se-nanumsquare;">FEP 온라인상세조회(${env}) - ${item.instCode}/${item.applCode}</H2>
<table style="width: 100%;">
<tr>
	<th colspan="8">거래 기본 정보</th>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">기관코드</th>
	<td>${item.instName}</td>
	<th style="background-color: rgb(232,228,235);">업무코드</th>
	<td>${item.applName}</td>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">전문종별코드</th>
	<td>${item.kindCode}</td>
	<th style="background-color: rgb(232,228,235);">거래구분코드</th>
	<td>${item.txCode}</td>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">거래명</th>
	<td><b>${item.txName}</b></td>
	<th style="background-color: rgb(232,228,235);">거래고유번호</th>
	<td>${item.trxSeqNum}</td>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">거래날짜</th>
	<td>${fn:substring(item.procDate,0,4)}-${fn:substring(item.procDate,4,6)}-${fn:substring(item.procDate,6,8)}</td>
	<th style="background-color: rgb(232,228,235);">거래시간</th>
	<td>${fn:substring(item.procMtime,0,2)}:${fn:substring(item.procMtime,2,4)}:${fn:substring(item.procMtime,4,6)}.${fn:substring(item.procMtime,6,9)}</td>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">에러코드</th>
	<td>${item.trxRespCode }</td>
	<th style="background-color: rgb(232,228,235);">txTime</th>
	<td>${item.txTime}</td>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">헤더사이즈</th>
	<td>${item.headerSize}</td>
	<th style="background-color: rgb(232,228,235);">txUid</th>
	<td>${item.txUid}</td>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">기타정보</th>
	<td colspan="3">${item.resFlag}/${item.procHour}/${item.xid}/${item.txState}/${item.sessionIndex}/${item.headMappingType}/${item.bodyMappingType}
	<c:if test="${item.txCode eq '300000'}">
		수취은행코드:${fn:substring(item.flatData,73,76)}
	</c:if>
	</td>
</tr>
<tr>
	<th style="background-color: rgb(232,228,235);">오류조치</th>
	<td colspan="3">[${item.errCode}] ${errMsg}</td>
</tr>
<tr>
	<th colspan="8">헤더 정보 500bytes <input type="button" onclick="toggleView();" value="파싱보기" /></th>
</tr>
<tr>
	<td colspan="8">
		<textarea rows="5" style="width: 100%;" id="stdtx500">${fn:substring(item.msgDataStr,0,500)}</textarea>
		<blockquote id="toggle">
		<h4>■ 표준전문헤더 파싱결과 - <input type="button" value="다시파싱하기" onclick="user_parsing();" /></h4>
		<table style="width: 100%; font-size: 9pt; line-height: 130%">
			<tr><th>tlgrLen</th>		<td>전문길이(8)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>strd_tlgr_vrsn</th>	<td>전문버전(3)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>ecryApplctnYn</th>	<td>암호화여부(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('0'=암호화 미적용, '1'=암호화 적용)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>tlgrWrtnDt</th>		<td>전문작성 일시(14)<br/><font color=#CCCCCC style="font-size: 8pt;">(YYYYMMDDhhmmss)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>tlgrCrtnSysNm</th>	<td>전문생성 시스템(8)<br/><font color=#CCCCCC style="font-size: 8pt;">(메타 등록된 시스템명)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>tlgrSrlNo</th>		<td>전문 일련번호(8)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>tlgrPrgrsNo</th>	<td>거래연속번호(2)<br/><font color=#CCCCCC style="font-size: 8pt;">(최초='01', 최대='99')</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>orgnlTx</th>		<td>원거래 Global ID(32)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>ttlUseYn</th>		<td>TTL 사용 여부(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('0'=TTL 미사용, '1'=TTL 사용)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>frstStartTm</th>	<td>최초 시작시간(6)<br/><font color=#CCCCCC style="font-size: 8pt;">(HHMMSS)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>mntcTm</th>			<td>유지 시간(3)<br/><font color=#CCCCCC style="font-size: 8pt;">(001~999)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>msgTpDscd</th>		<td>메시지 유형 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('0'=일반거래, '1'=브로드캐스팅)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>scrnId</th>			<td>scrnId(12) </td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>natCd</th>			<td>natCd(2) </td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>lngDscd</th>		<td>lngDscd(2) </td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>crctnCnclDscd</th>	<td>정정취소 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('1'=정상, '2'=취소, '3'=정정)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>bizOpngYn</th>		<td>업무개시 여부(1)<br/><font color=#CCCCCC style="font-size: 8pt;">(0=업무 미개시, 1=업무 개시)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>clsgPstBizYn</th>	<td>마감후거래 여부(1)<br/><font color=#CCCCCC style="font-size: 8pt;">(0=정상 거래, 1=마감후 거래)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>mciIntfId</th>		<td>MCI 인터페이스 ID(16)<br/><font color=#CCCCCC style="font-size: 8pt;">(서비스코드(13)+'V'+일련번호(2))</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>rcvSrvcCd</th>		<td>수신 서비스 코드(13)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>rsltRcvSrvcCd</th>	<td>결과 수신 서비스 코드(13)<br/><font color=#CCCCCC style="font-size: 8pt;">(sync이면 space(13), <br/>async이면 응답수신시스템의 수신서비스)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>eaiIntfId</th>		<td>EAI 인터페이스 ID(15)<br/><font color=#CCCCCC style="font-size: 8pt;">(소스시스템(3), 타겟시스템(3), 일련번호(5))</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>syncDscd</th>		<td>동기 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('S'=sync, 'A'=async)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>asyncAtrbtDscd</th>	<td>Async 속성 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('0'=단방향, '1'=양방향)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>reqRspnsDscd</th>	<td>요청 응답 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('S'=요청, 'R'=응답)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>xaTxDscd</th>		<td>XA거래 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('0'=non XA, 'T'=XA)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>exctnModeDscd</th>	<td>실행모드 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('P'=운영, 'T'=스테이징, 'D'=개발, 'R'=DR)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>chnlDscd</th>		<td>전문생성채널구분코드(3)<br/><font color=#CCCCCC style="font-size: 8pt;">('COR'=계정, 'CAS'=콜센터, 'INB'=인뱅,<br/>'SMB'=스뱅, 'TEB'=텔레뱅킹, 'UMS')</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>ipAddr</th>			<td>IP 주소(32)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>macAddr</th>		<td>MAC 주소(12)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>mciNodNo</th>		<td>MCI 노드번호(1)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>mciInstcNo</th>		<td>MCI 인스턴스번호(1)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>mciSssidNo</th>		<td>MCI 세션ID 번호(32)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>extrInstCd</th>		<td>대외기관 코드(3)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>extrBizDscd</th>	<td>대외업무 구분코드(3)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>extrBizDtlsDscd</th>	<td>대외업무 세부구분코드(2)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>jntntClsCd</th>		<td>공동망 종별코드(10)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>jntntTxDscd</th>	<td>공동망 거래구분코드(10)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>sysIntfId</th>		<td>시스템인터페이스 식별자(23)<br/><font color=#CCCCCC style="font-size: 8pt;">('Ext'+기관(3)+업무구분(3)+취급개설(1)+<br/>정상취소(1)+거래(2)+일련(4)+'Intrfc'(6))</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>linkTlgrTraceNo</th>	<td>연계전문 추적번호(20)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>linkSnrcDscd</th>	<td>연계 송신수신 구분코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('1'=취급요청, '2'=취급응답,<br/>'3'=개설요청, '4'=개설응답)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>custId</th>			<td>고객 식별자(15)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>filler</th>			<td>Filler(5)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>custRprsnId</th>	<td>고객관련인 식별자(15)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>deptId</th>			<td>부서 식별자(14)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>staffId</th>		<td>스태프 식별자(15)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>filler</th>			<td>Filler(5)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>tlgrRspnsDttm</th>	<td>전문응답 일시(14)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>prcsRsltDscd</th>	<td>처리결과 구분코드(1)</td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>outpTlgrTpCd</th>	<td>출력전문 유형코드(1)<br/><font color=#CCCCCC style="font-size: 8pt;">('0'=정상, '1'=에러)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>tlgrCtntNo</th>		<td>전문 연속번호(2)<br/><font color=#CCCCCC style="font-size: 8pt;">('1'=단전문, '2'=장전문시작, '3'=장전문중간,<br/>'4'=장전문종료, '9'=Dummy Return)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
			<tr><th>filler</th>			<td>전체 Filler(92)<br/><font color=#CCCCCC style="font-size: 8pt;">(FEP 내부에서 사용-[408,4]회선정보,<br/>[412,8]기관업무유일키,[420,3]에러코드,<br/>[423,50]에러메시지)</font></td>	<td><input type="text" name="comHeadercols" class="headercolumn" /></td></tr>
		</table>
		</blockquote>
	</td>
</tr>
<tr>
	<th colspan="8">개별부 정보 ${item.flatDataLength }bytes <input type="checkbox" onclick="changeData(this);" /> UTF-8 <input type="button" onclick="toggleView2();" value="파싱보기" /></th>
</tr>
<tr>
	<td colspan="8">
		<textarea rows="10" style="width: 100%;" id="dataArea">${item.flatData}</textarea>
		<blockquote id="toggle2">
			<h4>■ 개별전문 파싱결과</h4>
			${parseData}
		</blockquote>
	</td>
</tr>
<tr>
	<th colspan="8">HEXA DATA</th>
</tr>
<tr>
	<td colspan="8">
		<textarea rows="10" style="width: 97%;" >${item.hexaData}</textarea>
	</td>
</tr>
<tr>
</tr>
</table>
<p align="center">
	<input type="button" value="CLOSE" onclick="window.open('','_self').close();" />
</p>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>