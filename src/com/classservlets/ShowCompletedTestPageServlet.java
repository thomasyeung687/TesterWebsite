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
		String testid = request.getParameter("testid");
		if(testid == null) { //then user clicked on a student's attempt and wants to see or grade the student's attempt
			String idattempt = request.getParameter("idattempt");
			TestAttemptObject tao = TestAttemptObject.getAttemptFromDB(idattempt);
			String idStudent = tao.getidstudentprofiles()+"";
			Test test = Test.getCompletedTestFromDB(idStudent, tao.getidtest()+"");
			session.setAttribute("thistest", test);
			RequestDispatcher rd = request.getRequestDispatcher("ShowCompletedTestPage.jsp");
			rd.forward(request, response);
		}else {//here then user clicked on test button so we should redirect to testlayout.jsp
			System.out.println(testid);
			session.setAttribute("idtest", testid);//setting istest to testid so that testlayout would be able to get the test from db.
			RequestDispatcher rd = request.getRequestDispatcher("TestLayout.jsp");
			rd.forward(request, response);
		}
	}
}
