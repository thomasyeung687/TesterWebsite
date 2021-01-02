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
	
	<h3>Editing: <% out.println(request.getParameter("testname")); %></h3>
	
	<form action="InsertQuestionPage.jsp">
		<p style="color:#808080">Click button below to add questions to this test</p>
		<button name="testname" value="<%out.println(request.getParameter("testname"));%>">Add questions</button>
	</form>
	<br>
	Test Questions: (Choose which question you wish to edit or delete)<br>
	<form method= "post" action="TestEditorServlet">
	<% Connection con = DBConnection.getDBConnection();
		try{
		Statement st = con.createStatement();
		String query ="select*from tester."+request.getParameter("testname");
		ResultSet rSet = st.executeQuery(query);
		while(rSet.next()){ 
			String questiondata = rSet.getString("question")+"\n "+rSet.getString("ans1")+" "+rSet.getString("ans2")+" "+rSet.getString("ans3")+" "+
					rSet.getString("ans4")+" "+rSet.getString("correctans");
		%>
			<input type="radio" name="questionid" value=<%out.print(rSet.getString("idquestionsAndAns")); %> required> <%out.println(questiondata); %><br>
	  <%}
		}catch(Exception e){
			System.out.println("Exception occured in TestEditor!");
		}
	%>	
		<input type="hidden" value="<%out.println(request.getParameter("testname"));%>" name ="testname">
		<br>
		<button name="action" value="delete">Delete Question</button> 
		<button name="action" value="edit">Edit Question</button>
	</form>
	<br>
	<a href="ShowExistingTests.jsp">Back</a>   <a href="index.jsp">Home</a>
</body>
</html>