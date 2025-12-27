<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP/API 회선대장</title>
<link rel="stylesheet" type="text/css" href="/css/default.css" />
<style>
body { font-family: d2coding; font-size: 9pt; }
input { font-family: d2coding; font-size: 9pt; }
table { border-collapse: collapse; border-style: solid; border-top: #6AB4CC 2px solid;
		border-left:#EFEFEF 1px solid; border-right: #EFEFEF 1px solid; 
		border-bottom: #BDBCBD 1px solid; margin-bottom: 5px; margin-top: 5px; }
th { height: 24px; border-bottom: #E0DFE0 1px dotted; border-right: #E0DFE0 1px dotted;
	 border-left: #E0DFE0 1px dotted; background: #E7F3F7; 
	 font-size: 10pt; font-family: se-nanumsquare; font-weight: bold; }
td { height: 24px; color: #272727; border-bottom: #E0DFE0 1px dotted;
	 border-left:#EFEFEF 1px solid; border-right: #EFEFEF 1px solid; 
	 padding-left: 5px; padding-right: 5px; 
	 font-family: d2coding; font-size: 9pt;}
textarea {  font-family: d2coding; font-size: 10pt; }
pre { font-family: d2coding; font-size: 9pt; }
textarea { font-family: d2coding; font-size: 9pt; }
</style>
<script>
function editItem(seqNo) {
	document.frm.seqNo.value=seqNo;
	document.frm.extCd.value=document.getElementById("edit_extCd_"+seqNo).innerText;
	document.frm.extNm.value=document.getElementById("edit_extNm_"+seqNo).innerText;
	document.frm.bizCd.value=document.getElementById("edit_bizCd_"+seqNo).innerText;
	document.frm.bizNm.value=document.getElementById("edit_bizNm_"+seqNo).innerText;
	document.frm.bizType.value=document.getElementById("edit_bizType_"+seqNo).innerText;
	document.frm.bizClcd.value=document.getElementById("edit_bizClcd_"+seqNo).innerText;
	document.frm.nwLine.value=document.getElementById("edit_nwLine_"+seqNo).innerText;
	document.frm.nwRouter.value="";
	document.frm.fwVpn.value="";
	document.frm.devClcd.value=document.getElementById("edit_devClcd_"+seqNo).innerText;
	document.frm.kbkIp.value=document.getElementById("edit_kbkIp_"+seqNo).innerText;
	document.frm.kbkNatIp.value=document.getElementById("edit_kbkNatIp_"+seqNo).innerText;
	document.frm.kbkPort.value=document.getElementById("edit_kbkPort_"+seqNo).innerText;
	document.frm.extIp.value=document.getElementById("edit_extIp_"+seqNo).innerText;
	document.frm.extPort.value=document.getElementById("edit_extPort_"+seqNo).innerText;
	document.frm.srType.value=document.getElementById("edit_srType_"+seqNo).innerText;
	document.frm.extUser.value=document.getElementById("edit_extUser_"+seqNo).innerText;
	document.frm.history.value=document.getElementById("edit_history_"+seqNo).innerText;

	document.frm.devFilter.value=document.getElementById("devFilter").value;
	document.frm.stgFilter.value=document.getElementById("stgFilter").value;
	document.frm.prdFilter.value=document.getElementById("prdFilter").value;
	document.frm.innFilter.value=document.getElementById("innFilter").value;
	
	document.frm.submit();
}
function saveItem() {
	document.frm.seqNo.value="-1";
	document.frm.extCd.value=document.getElementById("insert_extCd").innerText;
	document.frm.extNm.value=document.getElementById("insert_extNm").innerText;
	document.frm.bizCd.value=document.getElementById("insert_bizCd").innerText;
	document.frm.bizNm.value=document.getElementById("insert_bizNm").innerText;
	document.frm.bizType.value=document.getElementById("insert_bizType").innerText;
	document.frm.bizClcd.value=document.getElementById("insert_bizClcd").innerText;
	document.frm.nwLine.value=document.getElementById("insert_nwLine").innerText;
	document.frm.nwRouter.value="";
	document.frm.fwVpn.value="";
	document.frm.devClcd.value=document.getElementById("insert_devClcd").innerText;
	document.frm.kbkIp.value=document.getElementById("insert_kbkIp").innerText;
	document.frm.kbkNatIp.value=document.getElementById("insert_kbkNatIp").innerText;
	document.frm.kbkPort.value=document.getElementById("insert_kbkPort").innerText;
	document.frm.extIp.value=document.getElementById("insert_extIp").innerText;
	document.frm.extPort.value=document.getElementById("insert_extPort").innerText;
	document.frm.srType.value=document.getElementById("insert_srType").innerText;
	document.frm.extUser.value=document.getElementById("insert_extUser").innerText;
	document.frm.history.value=document.getElementById("insert_history").innerText;

	document.frm.devFilter.value=document.getElementById("devFilter").value;
	document.frm.stgFilter.value=document.getElementById("stgFilter").value;
	document.frm.prdFilter.value=document.getElementById("prdFilter").value;
	document.frm.innFilter.value=document.getElementById("innFilter").value;
	
	document.frm.submit();
}
function copyData(seqNo) {
	document.getElementById("insert_extCd").innerText=document.getElementById("edit_extCd_"+seqNo).innerText;
	document.getElementById("insert_extNm").innerText=document.getElementById("edit_extNm_"+seqNo).innerText;
	document.getElementById("insert_bizCd").innerText=document.getElementById("edit_bizCd_"+seqNo).innerText;
	document.getElementById("insert_bizNm").innerText=document.getElementById("edit_bizNm_"+seqNo).innerText;
	document.getElementById("insert_bizType").innerText=document.getElementById("edit_bizType_"+seqNo).innerText;
	document.getElementById("insert_bizClcd").innerText=document.getElementById("edit_bizClcd_"+seqNo).innerText;
	document.getElementById("insert_nwLine").innerText=document.getElementById("edit_nwLine_"+seqNo).innerText;
	//document.getElementById("insert_nwRouter").innerText="";
	//document.getElementById("insert_fwVpn").innerText="";
	document.getElementById("insert_devClcd").innerText=document.getElementById("edit_devClcd_"+seqNo).innerText;
	document.getElementById("insert_kbkIp").innerText=document.getElementById("edit_kbkIp_"+seqNo).innerText;
	document.getElementById("insert_kbkNatIp").innerText=document.getElementById("edit_kbkNatIp_"+seqNo).innerText;
	document.getElementById("insert_kbkPort").innerText=document.getElementById("edit_kbkPort_"+seqNo).innerText;
	document.getElementById("insert_extIp").innerText=document.getElementById("edit_extIp_"+seqNo).innerText;
	document.getElementById("insert_extPort").innerText=document.getElementById("edit_extPort_"+seqNo).innerText;
	document.getElementById("insert_srType").innerText=document.getElementById("edit_srType_"+seqNo).innerText;
	document.getElementById("insert_extUser").innerText=document.getElementById("edit_extUser_"+seqNo).innerText;
	//document.getElementById("insert_history").innerText=document.getElementById("edit_history_"+seqNo).innerText;
	document.getElementById("insert_extCd").focus();
}
function deleteItem(seqNo) {
	if ( confirm("삭제하시겠습니까? " + seqNo) ) {
		document.frm.seqNo.value=seqNo;
		document.frm.deleteYn.value="Y";
		document.frm.submit();
	}
}
function filterSearch() {
	var key = document.getElementById("searchKey").value;
	document.frm.key.value=key;
	
	var devCheck = document.getElementById("devFilter").checked;
	var stgCheck = document.getElementById("stgFilter").checked;
	var realCheck = document.getElementById("prdFilter").checked;
	var innerCheck = document.getElementById("innFilter").checked;
	var table = document.getElementById("mainTable");
	var trs = table.getElementsByTagName("tr");
	
	for ( var i=2; i<trs.length; i++) {
		var tds = trs[i].getElementsByTagName("td");
		
		var isContain = false;
		if ( tds[8].innerText === "D" && devCheck == false) {
			isContain = false;
		} else if ( tds[8].innerText === "S" && stgCheck == false) {
			isContain = false;
		} else if ( tds[8].innerText === "P" && realCheck == false) {
			isContain = false;
		} else if ( tds[1].innerText === "089" && innerCheck == false) {
			isContain = false;
		} else {
			for ( var j=1; j<tds.length; j++) {
				if ( tds[j].innerText.indexOf(key) >= 0 ) {
					isContain = true;
					break;
				}
			}
		}
		
		if ( isContain == false ) {
			trs[i].style.display = "none";
		} else {
			trs[i].style.display = "table-row";
		}
	}
}
function sortTable(num) {
	var table, rows, switching, i, x, y, shouldSwitch;
	table = document.getElementById("mainTable");
	switching = true;
	
	while ( switching ) {
		switching = false;
		rows = table.rows;
		
		for ( i=1; i< (rows.length -1); i++) {
			shouldSwitch =false;
			x = rows[i].getElementsByTagName("td")[num];
			y = rows[i+1].getElementsByTagName("td")[num];
			
			if ( x.innerText.toLowerCase() > y.innerText.toLowerCase() ) {
				shouldSwitch = true;
				break;
			}
		}
		
		if ( shouldSwitch ) {
			rows[i].parentNode.insertBefore(rows[i+1], rows[i]);
			switching = true;
		}
	}
}
function inputValues() {
	var input = document.getElementById("insert_devClcd").innerText;
	if ( input === "D" ) {
		document.getElementById("insert_kbkIp").innerText = "127.0.0.1";
		document.getElementById("insert_kbkNatIp").innerText = "127.0.0.1";
	} else if ( input === "P" ) {
		document.getElementById("insert_kbkIp").innerText = "127.0.0.1\n127.0.0.1";
		document.getElementById("insert_kbkNatIp").innerText = "127.0.0.1";
	}
}
function toExcel() {
	window.open("line-excel");
}
function init(key) {
	//sortTable(1);
	if ( key != "" ) {
		document.getElementById("searchKey").value = key;
		filterSearch();
	}
}
function onSearch(value) {
	document.getElementById("searchKey").value = value;
	filterSearch();
}
</script>	
</head>
<body onload="init('${key}'); filterSearch();">
<div style="float: right; padding-right: 15px; padding-top:12px;">
	<span style="color: #FFF;">
		<a href="/log/list">온라인 로그조회</a> | 
		<a href="/log/listbat">배치 로그조회</a> | 
	</span>
	<img src="/images/kbank_logo.gif" height="30px" style="vertical-align: middle;" />
</div>
<h1 style="font-family: se-nanumsquare;">FEP/API 대외기관 통신회선대장</h1>
<font style="font-size: 10pt;">*FILTER : <input type="text" id="searchKey" onchange="filterSearch();"/>
<input type="checkbox" id="devFilter" onchange="filterSearch();" ${devFilter} /> DEV | 
<input type="checkbox" id="stgFilter" onchange="filterSearch();" ${stgFilter} /> STG | 
<input type="checkbox" id="prdFilter" onchange="filterSearch();" ${prdFilter} /> PRD | 
<input type="checkbox" id="innFilter" onchange="filterSearch();" ${innFilter} /> 089 | 
<input type="button" onclick="toExcel();" value="EXCEL" />
</font>
<table style="width: 100%;" id="mainTable">
<thead>
	<col width="10px" />
	<col width="20px" />
	<col width="100px" />
	
	<col width="20px" />
	<col width="150px" />
	<col width="10px" />
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
		<th ondblclick="sortTable(0);" >NO</th>
		<th ondblclick="sortTable(1);" >extCd</th>
		<th ondblclick="sortTable(2);">extNm</th>
		<th ondblclick="sortTable(3);">bizCd</th>
		<th ondblclick="sortTable(4);">bizNm</th>
		<th ondblclick="sortTable(5);">bizT</th>
		<th ondblclick="sortTable(6);">bizClcd</th>
		<th ondblclick="sortTable(7);">nwLine</th>
		<!-- th>nwRouter</th>
		<th>fwVpn</th -->
		<th ondblclick="sortTable(8);">devClcd</th>
		<th ondblclick="sortTable(9);">kbkIp</th>
		<th ondblclick="sortTable(10);">kbkNatIp</th>
		<th ondblclick="sortTable(11);">kbkPort</th>
		<th ondblclick="sortTable(12);">extIp</th>
		<th ondblclick="sortTable(13);">extPort</th>
		<th ondblclick="sortTable(14);">srType</th>
		<th>extUser</th>
		<th>history</th>
		<th>EDIT</th>
	</tr>
</thead>
<tr align='center'>
	<td>신규</td>
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
	<td><input type="button" value="ADD" onclick="alert('/admin/line2 에서 수행')" ></td>
</tr>
<c:forEach var="row" items="${list}">
<c:set var="tr_color" value="#ffffff" />
<c:choose>
	<c:when test="${row.devClcd eq 'P'}">
		<c:set var="tr_color" value="#ffffdd" />
	</c:when>
</c:choose>
<tr align="center" style="background-color: ${tr_color};">
	<td ondblclick="copyData('${row.seqNo}');" >${row.seqNo }</td>
	<td contenteditable='true' id='edit_extCd_${row.seqNo}'>${row.extCd }</td>
	<td contenteditable='true' id='edit_extNm_${row.seqNo}' ondblclick="onSearch('${row.extNm }');">${row.extNm }</td>
	<td contenteditable='true' id='edit_bizCd_${row.seqNo}'>${row.bizCd }</td>
	<td contenteditable='true' id='edit_bizNm_${row.seqNo}'>${row.bizNm }</td>
	<td contenteditable='true' id='edit_bizType_${row.seqNo}'>${row.bizType }</td>
	<td contenteditable='true' id='edit_bizClcd_${row.seqNo}'>${row.bizClcd }</td>
	<td contenteditable='true' id='edit_nwLine_${row.seqNo}'>${row.nwLine }</td>
	<td contenteditable='true' id='edit_devClcd_${row.seqNo}'>${row.devClcd }</td>
	<td contenteditable='true' id='edit_kbkIp_${row.seqNo}'>${row.kbkIp }</td>
	<td contenteditable='true' id='edit_kbkNatIp_${row.seqNo}'>${row.kbkNatIp }</td>
	<td contenteditable='true' id='edit_kbkPort_${row.seqNo}'>${row.kbkPort }</td>
	<td contenteditable='true' id='edit_extIp_${row.seqNo}'>${row.extIp }</td>
	<td contenteditable='true' id='edit_extPort_${row.seqNo}'>${row.extPort }</td>
	<td contenteditable='true' id='edit_srType_${row.seqNo}'>${row.srType }</td>
	<td contenteditable='true' align='left' id='edit_extUser_${row.seqNo}' >${row.extUser }</td>
	<td contenteditable='true' align='left' id='edit_history_${row.seqNo}' >${row.history }</td>
	<td>
		<input type="button" value="EDIT" onclick="alert('/admin/line2 에서 수행')" />
		<input type="button" value="DEL" onclick="alert('/admin/line2 에서 수행')" />
	</td>
</tr>
</c:forEach>
</tbody>
</table>
<form name="frm" action="line" method="POST">
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
	<input type="hidden" name="deleteYn" value="N" />
	<input type="hidden" name="key" value="" />
	<input type="hidden" name="devFilter" value="" />
	<input type="hidden" name="stgFilter" value="" />
	<input type="hidden" name="prdFilter" value="" />
	<input type="hidden" name="innFilter" value="" />
</form>
<span style="font-weight: bold; color: #C8F03C; background-color: #0F005F; font-family: se-nanumsquare; font-size: 8pt;">Kbank</span>
</body>
</html>