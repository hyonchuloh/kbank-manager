<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FEP SERVER VIEWER(${env})</title>
<link rel="stylesheet" type="text/css" href="/css/nanumsquare.css" />
<script>
function send(path) {
	document.frm.path.value=path;
	document.frm.submit();
}
</script>
</head>
<body>
<h1>FEP SERVER VIEWER(${env})</h1>
<form name="frm" action="/manager/ftp" method="post">
<ul>
	<li>
		BASE PATH : <input type="text" value="${path}" name="path"> <input type="submit" value="SUBMIT" />
		<a href="#" onclick="send('/kbkdat')">/kbkdat</a> | <a href="#" onclick="send('/kbklog')">/kbklog</a> | 
		<input type="checkbox" name="checkdown"> Download
	</li>
</ul>
<blockquote>
<table>
<tr>
	<th style="width: 30%;">FILE TREE</th>
	<th>VIEWER</th>
</tr>
<tr style="height: 800px;">
	<td>
		<img src="/images/folder1.png" style="width: 16px; height: 16px;" /> <a href="#" onclick="send('${pathback}');">${path}</a><br/>
		<c:forEach var="folder" items="${folderlist}">
			&nbsp;&nbsp;<img src="/images/folder1.png" style="width: 16px; height: 16px;" /> <a href="#" onclick="send('${path}/${folder.key}');">${folder.key}</a> (${folder.value }) <br/>
		</c:forEach>
		<c:forEach var="file" items="${filelist}">
			&nbsp;&nbsp;<img src="/images/file1.png" style="width: 16px; height: 16px;" /> <a href="#" onclick="frm.pathview.value='${file.key}'; frm.submit();">${file.key}</a> (${file.value }) <br/>
		</c:forEach>
	</td>
	<td>
		<textarea>${viewtext}</textarea>
	</td>
</tr>
</table>
</blockquote>
<input type="hidden" name="pathview" />
</form>
</body>
</html>