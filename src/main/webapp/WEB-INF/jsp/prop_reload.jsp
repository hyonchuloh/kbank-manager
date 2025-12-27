<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ page import="java.util.ResourceBundle, java.util.Set, java.util.Iterator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<%
	String reload = request.getParameter("reload");
	if ( reload == null ) reload = "";
	
	String errMsg = "";
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	ResourceBundle bundle = null;
	
	try {
		if ( reload.trim().length() > 0 )
			bundle = ResourceBundle.getBundle(reload);
	} catch ( Exception e ) {
		e.printStackTrace();
		errMsg = e.getMessage();
	}
	
%>
<body>
<h1>Prop Reload</h1>
<form name="frm" action="prop_reload.jsp" method="get">
<input type="text" name="reload" value="" />
<input type="submit" value="RELAOD" />
</form>
<h3><%= reload %></h3>
<h3>result : <%= errMsg %></h3>
</body>
</html>