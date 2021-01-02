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
		if(session.getAttribute("username")==null || session.getAttribute("student")==null){
			response.sendRedirect("LoginStudent.jsp");
		}
	%>
	<h2>Welcome Student <%out.println(session.getAttribute("username")); %></h2>
</body>
</html>