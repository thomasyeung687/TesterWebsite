package com.testservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import com.testersite.model.Question;

/**
 * Servlet implementation class AnsweredQuestionServlet
 */
@WebServlet("/AnsweredQuestionServlet")
public class AnsweredQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { HttpSession session =
	 * request.getSession(); ArrayList<Question> test =
	 * (ArrayList<Question>)session.getAttribute("test"); int currentnum =
	 * (int)session.getAttribute("currentnum");
	 * test.get(currentnum).setPickedString(request.getParameter("pickedans")); //
	 * <-- sets the pickedString variable in question of
	 * System.out.println("S:"+test.get(currentnum).getPickedString()+" "+session.
	 * getAttribute("studenttestrecordidentifier")); //test[currentnum] to the
	 * answer picked by student currentnum = currentnum+1;
	 * System.out.println("S:"+currentnum+"this is currentnumber incremented!");
	 * session.setAttribute("test", test); //refreshing array to be sent back to
	 * test page session.setAttribute("currentnum", currentnum); //incrementing
	 * current question
	 * request.getRequestDispatcher("TestPage.jsp").forward(request, response); }
	 */

}
