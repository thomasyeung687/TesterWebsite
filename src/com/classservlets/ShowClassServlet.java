package com.classservlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowClassServlet
 */
@WebServlet("/ShowClassServlet")
public class ShowClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("from the servlet: "+ request.getParameter("classid").toString());
		HttpSession session = request.getSession();
		session.setAttribute("classid", request.getParameter("classid"));
		RequestDispatcher rd = request.getRequestDispatcher("ClassOptions.jsp");
		rd.forward(request, response);
	}


}
