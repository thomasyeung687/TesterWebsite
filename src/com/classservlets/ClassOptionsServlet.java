package com.classservlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ClassOptionsServlet
 */
@WebServlet("/ClassOptionsServlet")
public class ClassOptionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("COaction").equals("Edit Class")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassEdit.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("COaction").equals("Manage Students")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassManageStudents.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("COaction").equals("Manage Tests")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassTests.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("COaction").equals("Manage Announcements")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassManageAnnouncements.jsp");
			rd.forward(request, response);
		}
	}
}
