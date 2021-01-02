<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Adding Question to: <%out.print(request.getParameter("testname")); %></h3>
	<form method="post" action="InsertQuestionServlet">
		<input type="hidden" name="testname" value="<%out.print(request.getParameter("testname"));%>" /> 
		Question : <textarea name="question" rows= "3" cols = "25" required></textarea><br>
		Answer 1 : <input type="text" name="ans1" required><br>
		Answer 2 : <input type="text" name="ans2" required><br>
		Answer 3 : <input type="text" name="ans3" required><br>
		Answer 4 : <input type="text" name="ans4" required><br>
		Correct Answer : <input type="text" name="correctans" required><br>
		<br>
		<input type ="submit">
	</form>	
	<br>
	<a href="index.jsp">Home</a>
</body>
</html>