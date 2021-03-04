package com.classservlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.model.MultipartQuestion;
import com.testersite.model.Question;
import com.testersite.model.Test;
import com.testersite.model.TestAttemptObject;

/**
 * Servlet implementation class GradeTestServlet
 */
@WebServlet("/GradeTestServlet")
public class GradeTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Test thisTest = (Test) session.getAttribute("thistest");
		TestAttemptObject tao = thisTest.getAttempts().get(0);
		String idAttempt = tao.getAttemptNumber()+"";
		System.out.println("idAttempt: "+idAttempt);
		if(request.getParameter("action").equals("Back")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassManageStudents.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("action").equals("Submit Grade")){
			System.out.println("notes: "+request.getParameter("notes"));
			double sum = 0.0;
			for(int n = 1; n<thisTest.getQuestionArray().size()+1; n++){
				Question question = thisTest.getQuestionArray().get(n-1);
				if(!(question instanceof MultipartQuestion)) {
					String questionPts = request.getParameter(question.getQuestionid()+"");
					sum += Double.parseDouble(questionPts);
					int id = question.getQuestionid();
					System.out.println(question.getQuestionType()+" id: "+id+" pts: "+questionPts);
				}else {
					MultipartQuestion multi = (MultipartQuestion) question;
					String questionids = multi.getQuestionCompoentids();
					ArrayList<Question> questions = multi.getQuestions();
					for(Question questionComponent : questions){ //for each question in the multipart question get each question's pts
						String questionPts = request.getParameter(question.getQuestionid()+"");
						sum += Double.parseDouble(questionPts);
						int id = question.getQuestionid();
					}
				}
			}
			System.out.println("Total Pts :"+sum);
		}else if(request.getParameter("action").equals("Next Student")){
			
		}else if(request.getParameter("action").equals("Prev Student")){
			
		}
	}
}
