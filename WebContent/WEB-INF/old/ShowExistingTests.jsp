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
	<h3>Your Tests:</h3>
	<form action="ShowExistingTestServlet" method="post">
	<%
		Connection con = DBConnection.getDBConnection();
		try{
			Statement st = con.createStatement();
			ResultSet rSet = st.executeQuery("SHOW TABLES IN tester");
			
			while(rSet.next()){
				String s = rSet.getString("Tables_in_tester");
				System.out.println(s);
	%>
		<input type="radio" name ="testname" value="<%out.print(s);%>" required> <%out.print(rSet.getString("Tables_in_tester")); %> <br>
		<%  } %>
		<br>
		<button name="action" value="delete">Delete Test</button> 
		<button name="action" value="edit">Edit Test</button>
		<button name="action" value="onboard">On-board Test</button><br>
	</form>
	<%  }catch(Exception e){
		 	System.out.println("exception occured in ShowExistingTests!");
	    }
		try{
			Statement st = con.createStatement();
			ResultSet rSet2 = st.executeQuery("SELECT * FROM onboardedtest.onboardedtest WHERE idonboardedtest=1");
			rSet2.next(); %>
			Currently On-boarded Test : <% out.println(rSet2.getString("onboardedtest")); 
		}catch(Exception e){
			System.out.println("Exception occured in 2nd try catch of ShowExistingTests!");
		}
	%>
	<br>
	<a href="CreatorOptions.jsp">Back</a>   <a href="index.jsp">Home</a>
</body>
</html>