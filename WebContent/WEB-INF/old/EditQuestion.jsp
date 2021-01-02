<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="com.testersite.model.Question"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%  out.println(request.getParameter("questionid"));
		Connection con = DBConnection.getDBConnection();
		try{
			Statement st = con.createStatement();
			String query = "SELECT * FROM tester."+request.getParameter("testname")+"WHERE idquestionsAndAns="+request.getParameter("questionid");
			System.out.println(query);
			ResultSet rSet = st.executeQuery(query);
			rSet.next();%>
			
			Select part you would like to change:<br>
			<form action="EditQuestionServlet" method="post">
				<input type="hidden" name="testname" value = "<%out.println(request.getParameter("testname")); %>">
				<input type="hidden" name="questionid" value = "<%out.println(request.getParameter("questionid")); %>">
				<input type="radio" name="change" value="question" > Question: <%out.print(rSet.getString("question"));%> <br>
				<input type="radio" name="change" value="ans1" > ans1: <%out.print(rSet.getString("ans1"));%> <br>
				<input type="radio" name="change" value="ans2" > ans2: <%out.print(rSet.getString("ans2"));%> <br>
				<input type="radio" name="change" value="ans3" > ans3: <%out.print(rSet.getString("ans3"));%> <br>
				<input type="radio" name="change" value="ans4" > ans4: <%out.print(rSet.getString("ans4"));%> <br>
				<input type="radio" name="change" value="correctans" > Correct answer: <%out.print(rSet.getString("correctans"));%> <br>
				New Text: <input type="text" name="changeto" ><br><br>
				<button name="action" value="Edit Question">Edit Question</button> 
				<br>
				<br>
				<button name="action" value="Back">Back</button> 
				<button name="action" value="Home">Home</button> 	
			</form>
	  <%}catch(Exception e){
			System.out.println("Exception occured in EditQuestion!");
		}
	%>	

</body>
</html>