package com.classservlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;
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
		String idAttempt = tao.getIdAttempt()+"";
		System.out.println("\nGRAAAADDDDDDIIIINNNNNGGGGGG:\n idAttempt: "+idAttempt);
		if(request.getParameter("action").equals("Back")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassManageStudents.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("action").equals("Submit Grade")){
			String notes = request.getParameter("notes");
			System.out.println("notes: "+notes);
			double sum = 0.0;
			String query ="";
			for(int n = 1; n<thisTest.getQuestionArray().size()+1; n++){ //going through questions array.
				Question question = thisTest.getQuestionArray().get(n-1);
				if(!(question instanceof MultipartQuestion)) {
					String questionPts = request.getParameter(question.getQuestionid()+""); //getting the points with id as the pts input's name is the id of the question
					sum += Double.parseDouble(questionPts);
					int id = question.getQuestionid();
					System.out.println(question.getQuestionType()+" id: "+id+" pts: "+questionPts);
					query += "UPDATE testersitedatabase.attempt_answer_choice SET ptsGiven = "+questionPts+" WHERE idattempt = "+idAttempt+" AND idquestion = "+id+";\n";//query too update the amount of pts given for this question
				}else {
					MultipartQuestion multi = (MultipartQuestion) question;
					String questionids = multi.getQuestionCompoentids(); //gets all the ids of the questions making up this multipart in form id,id,id,id,
					ArrayList<Question> questions = multi.getQuestions();
					for(Question questionComponent : questions){ //for each question in the multipart question get each question's pts
						String questionPts = request.getParameter(questionComponent.getQuestionid()+"");
						sum += Double.parseDouble(questionPts);
						int id = questionComponent.getQuestionid();
						System.out.println("Multipart:"+ question.getQuestionType()+" id: "+id+" pts: "+questionPts);
						query += "UPDATE testersitedatabase.attempt_answer_choice SET ptsGiven = "+questionPts+" WHERE idattempt = "+idAttempt+" AND idquestion = "+id+";\n"; //query too update the amount of pts given for this question
					}
				}
			}
			System.out.println("Total Pts :"+sum);
			query += "UPDATE testersitedatabase.attemptbook SET grade = "+sum+", notes = '"+notes+"' WHERE idattempt = "+idAttempt+";\n"; //query to update the raw grade in attemptbook
			System.out.println("QUERYYY:\n"+query);
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				st.executeUpdate(query);
			}catch (Exception e) {
				System.out.println("EXCEPPTIOOOON: "+e.getMessage());
			}
			RequestDispatcher rd = request.getRequestDispatcher("ClassManageStudents.jsp");
			rd.forward(request, response);
			return;
		}else if(request.getParameter("action").equals("Next Student")){
			
		}else if(request.getParameter("action").equals("Prev Student")){
			
		}
	}
}
