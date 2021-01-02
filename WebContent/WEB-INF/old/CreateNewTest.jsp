<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="CreateTestServlet">	
		Test name: <input type="text" name="testname" required>
		<input type="submit">
	</form>
	<br>
	<a href="index.jsp">Home</a>
	<form action="LogoutServlet">
		<input type="submit" value="Logout">
	</form>
</body>
</html>