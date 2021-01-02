<%@page import="com.testservlet.TestTakerInfoHandler"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		System.out.println(session.getAttribute("username"));
		if(session.getAttribute("username")==null || session.getAttribute("professor")==null){
			response.sendRedirect("LoginProf.jsp");
		}
	%>
	<h3>Welcome Professor ${username} !</h3><br>
	<a href ="Class.jsp">Class</a><br>
	<br>
	<a href ="CreateNewTest.jsp">Create New Test</a><br>
	<br>
	<a href ="ShowExistingTests.jsp">See All Existing Test</a><br>
	<br>
	<a href="index.jsp">Home</a>
	
	<form action="LogoutServlet">
		<input type="submit" value="Logout">
	</form>
</body>
</html>