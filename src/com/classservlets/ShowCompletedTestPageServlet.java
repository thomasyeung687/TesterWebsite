package com.classservlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.model.Test;
import com.testersite.model.TestAttemptObject;

/**
 * Servlet implementation class ShowCompletedTestPageServlet
 */
@WebServlet("/ShowCompletedTestPageServlet")
public class ShowCompletedTestPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		String idattempt = request.getParameter("idattempt");
		out.println(idattempt);
		
		TestAttemptObject tao = TestAttemptObject.getAttemptFromDB(idattempt);
		
		String idStudent = tao.getidstudentprofiles()+"";
		
		out.println(idStudent);
		
		Test test = Test.getCompletedTestFromDB(idStudent, tao.getidtest()+"");
		
		session.setAttribute("thistest", test);
		
		RequestDispatcher rd = request.getRequestDispatcher("ShowCompletedTestPage.jsp");
		rd.forward(request, response);
	}
}
