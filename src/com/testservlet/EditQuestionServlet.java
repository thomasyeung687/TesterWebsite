package com.testservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class EditQuestionServlet
 */
@WebServlet("/EditQuestionServlet")
public class EditQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("Edit Question")){
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				String query = "UPDATE tester."+request.getParameter("testname")+" SET "+request.getParameter("change")+"='"+request.getParameter("changeto")+"' WHERE idquestionsAndAns="+request.getParameter("questionid");
				System.out.println(query);
				st.executeUpdate(query);
			}catch (Exception e) {
				System.out.println("Exception occured in EditQuestionServlet!"+e.getMessage());
			}
			System.out.print("Successfully updated question");
			request.getRequestDispatcher("TestEditor.jsp").forward(request, response);
		}else if(request.getParameter("action").equals("Back")) {
			request.getRequestDispatcher("TestEditor.jsp").forward(request, response);
		}else if(request.getParameter("action").equals("Back to All Tests")) {
			request.getRequestDispatcher("Testsnew.jsp").forward(request, response);;
		}
	}
}

