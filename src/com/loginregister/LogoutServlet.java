package com.loginregister;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("username");
		session.invalidate();
		if(request.getParameter("logoutfrom") == null) {
			response.sendRedirect("LoginProf.jsp");
		}else if(request.getParameter("logoutfrom").equals("student")){
			response.sendRedirect("LoginStudent.jsp");
		}else if(request.getParameter("logoutfrom").equals("admin")){
			response.sendRedirect("LoginAdmin.jsp");
		}else {
			response.sendRedirect("index.jsp");
		}
	}


}
