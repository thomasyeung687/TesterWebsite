<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="assets/css/loginregister.css">
</head>
<body>
	<div class="center">
	<h2>Professor Login</h2>
	<form action="LoginServlet" method="post">
		<span style =  "color: red;"> ${error} </span><br>
		<div class="text_field">
			<input type="text" name="username" placeholder="username" required><br>
		</div>
		<div class="text_field">
			<input type="password" name="password" placeholder="password" required><br>
		</div>
		<div class="pass"><a href="#">Forgot Password?</a> </div>
		<input type ="submit" value="Login"><br>
	</form>
	<div class="register">Not a member? <a href="RegisterProfesor.jsp">Register here</a> </div>
	<div class="loginStudent">Are you a student? <a href="LoginStudent.jsp">Login here</a></div>
	</div>
</body>
</html>