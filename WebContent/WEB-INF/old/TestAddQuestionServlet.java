package com.testservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/TestAddQuestionServlet")
public class TestAddQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String url="";
		String uname="root";
		String pass="";
		out.print("This is insertpage");
		out.print("Doing actions.....");
		if(request.getParameter("action").equals("Add New Question")){
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tester","root","pass");
				Statement st = connection.createStatement();
				String question, ans1, ans2, ans3, ans4, correctans;
				question = request.getParameter("question");
				ans1 = request.getParameter("ans1");
				ans2 = request.getParameter("ans2");
				ans3 = request.getParameter("ans3");
				ans4 = request.getParameter("ans4");
				correctans = request.getParameter("correctans");
				System.out.println(question+" "+ans1+" "+ans2+" "+ans3+" "+ans4+" "+correctans);
				String tablename = "`"+request.getParameter("testname").trim()+"`";
				String queryString = "INSERT INTO `tester`."+ tablename+"(question, ans1, ans2, ans3, ans4, correctans) "
						+ "VALUES ('"+question+"','"+ans1+"','"+ans2+"','"+ans3+"','"+ans4+"','"+correctans+"')";
				System.out.println(queryString);
				
				st.executeUpdate(queryString);
			}catch (ClassNotFoundException e) {
				System.out.println("ClassNotFoundException caught!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("TestAddQuestion.jsp").forward(request, response);
		}else if(request.getParameter("action").equals("Back")) {
			request.getRequestDispatcher("TestEditor.jsp").forward(request, response);
		}else if(request.getParameter("action").equals("Back to All Tests")) {
			request.getRequestDispatcher("Testsnew.jsp").forward(request, response);;
		}
	}

}
