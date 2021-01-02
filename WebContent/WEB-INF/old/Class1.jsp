<%@page import="Random.RandomString"%>
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
	<%
	Connection con = DBConnection.getDBConnection();
	System.out.println(session.getAttribute("username"));
	String classcode = "";
	ResultSet rset;
	ArrayList<String> students = new ArrayList<>();
	try {
		Statement st = con.createStatement();
		rset = st.executeQuery("SELECT classcode FROM testersitedatabase.professorprofiles WHERE username = '"+session.getAttribute("username")+"';");
		if(rset.next()){
			classcode = rset.getString("classcode"); 
		}else{// means no classcode in database. Creates a class code.
			System.out.println("no class code!");
		}
		rset = st.executeQuery("SELECT name FROM testersitedatabase.studentprofiles WHERE classcode = '"+ classcode +"';");
		while(rset.next()){
			students.add(rset.getString("name"));
		}
		System.out.println(classcode);
		System.out.println(students);
		
	}catch(Exception e){
		System.out.println("Exception in Class.jsp!");
	}
	%>
	Class Code:<%out.println(classcode); %> <br>
	Students:
	<form action="ShowStudentServlet" method="post">
	<%
		for(int i = students.size()-1; i>-1; i--){
	%>
	<input type="submit" name="student" value ="<% out.print(students.get(i));%>"> <br>
	<%
		}
	%>
	</form>
	
</body>
</html>