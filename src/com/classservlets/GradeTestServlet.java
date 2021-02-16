package com.classservlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GradeTestServlet
 */
@WebServlet("/GradeTestServlet")
public class GradeTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("Back")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassManageStudents.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("action").equals("Submit Grade")){
			
		}else if(request.getParameter("action").equals("Next Student")){
			
		}else if(request.getParameter("action").equals("Prev Student")){
			
		}
	}
}
