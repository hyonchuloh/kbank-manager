<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>
body { font-family: Malgun-Gothic Semilight; font-size: 10pt; }
table { border-collapse: collapse; border-style: solid; border-top: #6AB4CC 2px solid;
		border-left:#EFEFEF 1px solid; border-right: #EFEFEF 1px solid; 
		border-bottom: #BDBCBD 1px solid; margin-bottom: 5px; margin-top: 5px; font-size: 9pt;}
th { height: 24px; border-bottom: #E0DFE0 1px dotted; border-right: #E0DFE0 1px dotted;
	 border-left: #E0DFE0 1px dotted; background: #E7F3F7; font-size: 9pt;}
td { height: 24px; color: #272727; border-bottom: #E0DFE0 1px dotted;
	 border-left:#EFEFEF 1px solid; border-right: #EFEFEF 1px solid; 
	 padding-left: 5px; padding-right: 5px; font-size: 9pt;}
tr:hover {
	background: #f6ffdd !important;
}
</style>
</head>
<body>
<h1>TEST</h1>
<form name="frm" action="/eai" method="POST">
<ul>
	<li>URL : <input type="text" name="url" value="${url}" style="width: 250px;"/></li>
	<li>DATA : <textarea name="data" cols="200" rows="20" >${data}</textarea></li>
	<li><input type="submit" value="SEND" />
</ul>
</form>
</body>
</html>