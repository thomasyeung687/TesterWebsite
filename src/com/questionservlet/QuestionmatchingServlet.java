package com.questionservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class QuestionmatchingServlet
 */
@WebServlet("/QuestionmatchingServlet")
public class QuestionmatchingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("submit")||request.getParameter("action").equals("another")){
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(request.getParameter("action").equals("another")) {
				request.getRequestDispatcher("Questionmatching.jsp").forward(request, response);
			}else {
				//request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
				response.sendRedirect("TestLayout.jsp");
			}
		}else if(request.getParameter("action").equals("back")) {
			//request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
			response.sendRedirect("TestLayout.jsp");
		}
	}

}
