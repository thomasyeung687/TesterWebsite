package com.testservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class TestEditorServlet
 */
@WebServlet("/TestEditorServlet")
public class TestEditorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("action"));
		System.out.println(request.getParameter("questionid"));
		
		Connection con = DBConnection.getDBConnection();
		PrintWriter out = response.getWriter();
		if(request.getParameter("action").equals("delete")) {
			System.out.println("Deleteing action selected");
			try {
				Statement st = con.createStatement();
				String query = "DELETE FROM tester."+request.getParameter("testname")+" WHERE idquestionsAndAns = "+request.getParameter("questionid");
				System.out.println(query);
				st.executeUpdate(query);
				out.println("Successfully deleted item");
				request.getRequestDispatcher("TestEditor.jsp").forward(request, response);
			}catch(Exception e) {
				System.out.println("Exception occured!");
			}
		}else if(request.getParameter("action").contentEquals("edit")) {
			System.out.println("Redirecting to edit.jsp");
			request.getRequestDispatcher("TestEditorQuestion.jsp").forward(request, response);
		}else {
			System.out.println("IDK what is happening");
		}
	}

}
