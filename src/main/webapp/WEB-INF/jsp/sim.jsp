<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<script>
/* JSTL 특성상 코드내 달러와 중괄호를 함께쓰면 컴파일 오류 발생 */
function loadRule() {
	var rules = document.getElementById("rule").value.split("$");
	var constMaps = document.getElementsByName("constMap");
	for (var i=0; i<constMaps.length; i++) {
		if ( typeof rules[i+1] === "undefined") {
			continue;
		}
		if ( rules[i+1].indexOf("'") > 0 ) {
			constMaps[i].value = rules[i+1].substr(2).replace("'}","");
		} else {
			constMaps[i].value = "$" + rules[i+1];
		}
	}
	/* add size */
	var reqColSizes = document.getElementsByName("reqColSize");
	var resColSizes = document.getElementsByName("resColSize");
	var index = 0;
	var numberValue = 0;
	for (var i=0; i<reqColSizes.length; i++) {
		numberValue = reqColSizes[i].value*1;
		index = index + numberValue;
		reqColSizes[i].value = index;
		
	}
	index = 0;
	for (var i=0; i<resColSizes.length; i++) {
		numberValue = resColSizes[i].value*1;
		index = index + numberValue;
		resColSizes[i].value = index;
	}
}
function generateRule() {
	var reqMaps = document.getElementsByName("reqMap");
	var constMaps = document.getElementsByName("constMap");
	var reqColSizes = document.getElementsByName("reqColSize");	// 요청 누적 컬럼 리스트
	var resColSizes = document.getElementsByName("resColSize"); // 응답 누적 컬럼 리스트
	var index = 0;
	for (var i=0; i<resColSizes.length; i++) {
		/* CONST MAP 에 상수가 있는경우 reqMap 컬럼값 삭제 */
		if ( constMaps[i].value.length > 0 && !constMaps[i].value.startsWith("$")) {
			reqMaps[i].value ="";
		}
		/* reqMap 컬럼값에 따른 액션을 정의 */
		if ( reqMaps[i].value == "" ) {
			constMaps[i].disabled = null;		// reqMap 컬럼값이 없는 경우 상수매핑 OPEN
		} else if ( reqMaps[i].value != "" ) {
			if ( reqMaps[i].value == 1 ) {		// reqMap[i] == 1 인경우 {0, ~} 세팅
				constMaps[i].value = "$"+"{0,"+reqColSizes[reqMaps[i].value-1].value+"}";
			} else if ( reqMaps[i].value > reqColSizes.length) {
				constMaps[i].value = "";		// 지정된 값이 요청컬럼개수보다 많은경우 빈값세팅
			} else {
				constMaps[i].value = "$"+"{"+reqColSizes[reqMaps[i].value-2].value+","+reqColSizes[reqMaps[i].value-1].value+"}";
			}
			constMaps[i].disabled="true";
		}
	}
	generate()
}
function generate() {
	var inputObj = document.getElementById("inputData");
	var constMaps = document.getElementsByName("constMap");
	var finalData = "";
	var saveData = "";
	var startIndex = 0;
	var endIndex = 0;
	for (var i=0; i<constMaps.length; i++) {
		if ( constMaps[i].value.startsWith("$")) {
			var words = constMaps[i].value.split(",");
			startIndex = words[0].substr(2);
			endIndex = words[1].substr(0,words[1].indexOf("}"));
			finalData += inputObj.value.substr(startIndex, endIndex-startIndex);
			saveData += constMaps[i].value;
		} else {
			finalData += constMaps[i].value;
			saveData += "$"+"{'" + constMaps[i].value + "'}";
		}
	}
	document.getElementById("expectData").value = finalData;
	document.getElementById("rule").value = saveData;
}
function changeData() {
	var inputObj = document.getElementById("inputData");		//원본데이터
	var reqCols = document.getElementsByName("reqCol");			//컬럼길이
	var reqColSizes = document.getElementsByName("reqColSize");	//누적길이
	var index = 0;
	var tempValue = "";
	for (var i = 0; i < reqCols.length; i++) {
		reqCols[i].value = inputObj.value.substr(index,reqColSizes[i].value-index);
		index = reqColSizes[i].value;
	}
	generate();
}
</script>
<meta charset="UTF-8">
<title>FEP 시뮬룰 EDITOR - ${msgname}(${msg})</title>
</head>
<body onload="loadRule(); generateRule();">
<h1>FEP SIMULATOR RULE EDITOR - ${msgname}(${msg})</h1>
<h3>SELECT TX</h3>
<ul>
	<li>SELECT APPL : 
		<c:forEach var="row" items="${applist}">
			<a href="/sim/list/${row}">${row}</a> |
		</c:forEach>
	</li>
	<li>SELECT MSG : 
		<c:forEach var="row" items="${msglist}">
			<a href="/sim/list/${app}/${row}">${row}</a> |
		</c:forEach>
	</li>
	<li>WORKING THREADS : ${threadlist}</li>
</ul>
<h3>MAPPING DATA</h3>
<blockquote>
<table style="width: 100%;">
<tr>
	<th style="width: 40%">${msgname}(${msg}) 요청</th>
	<th style="width: 60%">${msgname}(${msg}) 응답</th>
</tr>
<tr>
	<td valign="top">
		<table style="width: 100%;">
		<tr>
			<th style="background: #f9f9f9;">SEQ</th>
			<th style="background: #f9f9f9;">KOR NAME</th>
			<th style="background: #f9f9f9;">TYPE</th>
			<th style="background: #f9f9f9;">SIZE</th>
			<th style="background: #f9f9f9;">INPUT</th>
		</tr>
		<c:forEach var="reqcol" items="${fn:split(reqcols, '$') }" varStatus="reqStatus">
		<tr>
			<td>${reqStatus.count}</td>
			<c:forEach var="col" items="${fn:split(reqcol, ';') }" varStatus="colStatus">
				<c:choose>
				<c:when test="${colStatus.index == 0}">
					<td>${fn:replace(col,'{','')}</td>
				</c:when>
				<c:when test="${colStatus.index == 1}">
					<td>${col}</td>
				</c:when>
				<c:when test="${colStatus.index == 2}">
					<td>${fn:replace(col,'}','')}
						<input type="hidden" name="reqColSize" value="${fn:replace(col,'}','')}" />
					</td>
				</c:when>
				</c:choose>
			</c:forEach>
			<td style="padding: 0px;"><input type="text" name="reqCol" style="width: 100px;" /></td>
		</tr>
		</c:forEach>
		</table>
	</td>
	<td valign="top">
		<table style="width: 100%;">
		<tr>
			<th style="background: #f9f9f9;">SEQ</th>
			<th style="background: #f9f9f9;">KOR NAME</th>
			<th style="background: #f9f9f9;">TYPE</th>
			<th style="background: #f9f9f9;">SIZE</th>
			<th style="background: #f9f9f9;">REQ-MAP</th>
			<th style="background: #f9f9f9;">CONST-MAP</th>
		</tr>
		<c:forEach var="rescol" items="${fn:split(rescols, '$') }" varStatus="resStatus">
		<tr>
			<td>${resStatus.count}</td>
			<c:forEach var="col" items="${fn:split(rescol, ';') }" varStatus="colStatus">
			<c:choose>
				<c:when test="${colStatus.index == 0}">
					<td>${fn:replace(col,'{','')}</td>
				</c:when>
				<c:when test="${colStatus.index == 1}">
					<td>${col}</td>
				</c:when>
				<c:when test="${colStatus.index == 2}">
					<td>${fn:replace(col,'}','')}
						<input type="hidden" name="resColSize" value="${fn:replace(col,'}','')}" />
					</td>
				</c:when>
			</c:choose>
			</c:forEach>
			<td style="padding: 0px;"><input type="text" style="width: 20px; text-align: center" name="reqMap" onkeyup="generateRule() ;" value="${resStatus.count}"/></td>
			<td style="padding: 0px;"><input type="text" style="width: 90%;" name="constMap" onkeyup="generateRule() ;" /></td>
		</tr>
		</c:forEach>
		</table>
	</td>
</tr>
</table>
</blockquote>
<h3>TEST DATA <input type="button" value="WORK TEST" onclick="changeData(); " /> (화면에서는 한글데이터 테스트 안됌)</h3>
<ul>
	<li>REQUEST- DATA : <input type="text" style="width: 80%;" id="inputData" onkeyup="changeData();" value="${input}"/></li>
	<li>RESPONSE DATA : <input type="text" style="width: 80%;" id="expectData" value="" /></li>
</ul>
<form name="frm" action="/sim/list/${app}/${msg}/save" method="POST">
<h3>SAVE DATA <input type="button" value="GENERATE VALUE" onclick="generateRule();"/> <input type="submit" value="SAVE RULE"/> *세이브 하고 종료할것</h3>
<ul>
	<li>SAVE NAME DATA : <input type="text" value="${msgname}" style="width: 80%;" name="msgname"/></li>
	<li>SAVE REQS DATA : <input type="text" value="${reqcols}" style="width: 80%;" name="reqcols"/></li>
	<li>SAVE RESP DATA : <input type="text" value="${rescols}" style="width: 80%;" name="rescols"/></li>
	<li>SAVE RULE DATA : <input type="text" value="${rule}" style="width: 80%;" id="rule" name="rule"/></li>
	<li>SAVE LENG DATA : <input type="text" value="${lengstl}" style="width: 80%;" name="lengstl"/></li>
	<li>SAVE PORT DATA : <input type="text" value="${port}" style="width: 80%;" name="port"/></li>
	<li><input type="checkbox" name="iswork" /> RUN WORK THREAD
		<input type="checkbox" name="isdown" /> STOP WORK THREAD</li>
</ul>
</form>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>
