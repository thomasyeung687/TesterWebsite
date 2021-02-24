package StudentServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;

import com.testersite.model.CheckAllQuestion;
import com.testersite.model.FillInMultipleBlankQuestion;
import com.testersite.model.FillInTheBlankQuestion;
import com.testersite.model.MultipartQuestion;
import com.testersite.model.MultipleChoiceQuestion;
import com.testersite.model.Question;
import com.testersite.model.ShortResponseQuestion;
import com.testersite.model.TFQuestion;
import com.testersite.model.Test;

/**
 * Servlet implementation class TestSubmitServlet
 */
@WebServlet("/TestSubmitServlet")
public class TestSubmitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Test test = (Test) session.getAttribute("thistest");
		String studentid = ((String) session.getAttribute("studentid")).trim();
		String idtest = test.getTestId();
		int attemptnum = 0;
		if(test.getAttempts().size()==0) {
			attemptnum = 1; // when implemented. change to request.parameter object
		}else {
			attemptnum = test.getAttempts().get(0).getAttemptNumber()+1; //attemptnum = prevAttemptNum+1
		}
		System.out.println(studentid);
		for(int i = 0; i < test.getQuestionArray().size(); i++) {
			Question question = test.getQuestionArray().get(i);
			System.out.println();
			System.out.println(test.getQuestionArray().get(i).getQuestionType());
			
			if(question instanceof MultipleChoiceQuestion){ 
				MultipleChoiceQuestion mc = (MultipleChoiceQuestion) question;
				String answerChosen = request.getParameter("q"+(i+1));

				mc.setAnswerChosen(answerChosen);
				
				System.out.println("pts received: "+mc.calculatePtsReceived());
				test.setQuestionInQuestionsArray(i, mc);
				
			}else if(question instanceof TFQuestion){
				TFQuestion tf = (TFQuestion) question;
				String answerChosen = request.getParameter("q"+(i+1));
				
				tf.setAnswerChosen(answerChosen);
				
				System.out.println("pts received: "+tf.calculatePtsReceived());
				test.setQuestionInQuestionsArray(i, tf);
				
			}else if(question instanceof ShortResponseQuestion){
				ShortResponseQuestion sr = (ShortResponseQuestion) question;
				String answerChosen = request.getParameter("q"+(i+1));

				sr.setAnswerChosen(answerChosen);
				
				System.out.println("pts received: "+sr.calculatePtsReceived());
				test.setQuestionInQuestionsArray(i, sr);
				
			}else if(question instanceof CheckAllQuestion){
				CheckAllQuestion ca = (CheckAllQuestion) question;
				String[] answersArray = request.getParameterValues("q"+(i+1));
				List<String> answersList = Arrays.asList(answersArray);
				String answerChosen = "";
				for(String ans : answersList) {
					answerChosen += ans.trim()+"~";
				}
				ca.setAnswerChosen(answerChosen);
				
				System.out.println("pts received: "+ca.calculatePtsReceived());
				test.setQuestionInQuestionsArray(i, ca);

			}else if(question instanceof FillInTheBlankQuestion){
				FillInTheBlankQuestion fib = (FillInTheBlankQuestion) question;
				String answerChosen = request.getParameter("q"+(i+1));
				fib.setAnswerChosen(answerChosen);
				
				System.out.println("pts received: "+fib.calculatePtsReceived());
				test.setQuestionInQuestionsArray(i, fib);
				
			}else if(question instanceof FillInMultipleBlankQuestion){
				FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) question;
				ArrayList<String> blanks = fimb.getBlank();
				//ArrayList<String> answers = new ArrayList<String>();
				String answerChosen = "";
				System.out.println("ID:"+fimb.getQuestionid());
				for(int j = 0; j<blanks.size(); j++) {
					answerChosen += request.getParameter("q"+(i+1)+blanks.get(j)).trim()+"~"; //forming answerchosen string for fimb question
					//answers.add(request.getParameter("q"+(i+1)+blanks.get(j))); //getting the answer user gave in each blank
				}
				fimb.setAnswerChosen(answerChosen);
				System.out.println("pts received: "+fimb.calculatePtsReceived());
				test.setQuestionInQuestionsArray(i, fimb);

			}else if(question instanceof MultipartQuestion){
				MultipartQuestion multi = (MultipartQuestion) question;
				ArrayList<Question> questions = multi.getQuestions();
				System.out.println("ID:"+multi.getQuestionid());
				int questionComponentNumber = 1; //questionComponent number. 
				for(int j = 0; j < multi.getQuestions().size(); j++){ //for each question in the multipart question, display it properly. questionComponent is basiscally a question in the multipart question
					String questionNum = (i+1)+"."+questionComponentNumber++; //(i+1) = n
					Question questionComponent = multi.getQuestions().get(j);
					System.out.println(questionComponent.getQuestionType());
					if(questionComponent instanceof MultipleChoiceQuestion){ 
						MultipleChoiceQuestion mc = (MultipleChoiceQuestion) questionComponent;
						mc.setAnswerChosen(request.getParameter("q"+questionNum));
							System.out.println("q"+questionNum +" "+ request.getParameter("q"+questionNum));
						multi.setQuestionInQuestionsArray(j, mc);

					}else if(questionComponent instanceof TFQuestion){
						TFQuestion tf = (TFQuestion) questionComponent;
						tf.setAnswerChosen(request.getParameter("q"+questionNum));
							System.out.println("q"+questionNum +" "+ request.getParameter("q"+questionNum));
						multi.setQuestionInQuestionsArray(j, questionComponent);

					}else if(questionComponent instanceof ShortResponseQuestion){
						ShortResponseQuestion sr = (ShortResponseQuestion) question;
						sr.setAnswerChosen(request.getParameter("q"+questionNum));
							System.out.println("q"+questionNum +" "+ request.getParameter("q"+questionNum));
						multi.setQuestionInQuestionsArray(j, sr);

					}else if(questionComponent instanceof CheckAllQuestion){
						CheckAllQuestion ca = (CheckAllQuestion) questionComponent;
						String[] answersArray = request.getParameterValues("q"+questionNum);
						System.out.println("q"+questionNum +" "+ request.getParameterValues("q"+questionNum));
						
						List<String> answersList = Arrays.asList(answersArray);
						String answerChosen = "";
						for(String ans : answersList) {
							answerChosen += ans.trim()+"~";
						}
						ca.setAnswerChosen(answerChosen);
						
						multi.setQuestionInQuestionsArray(j, ca);

					}else if(questionComponent instanceof FillInTheBlankQuestion){
						FillInTheBlankQuestion fib = (FillInTheBlankQuestion) questionComponent;
						String answerChosen = request.getParameter("q"+questionNum);
						fib.setAnswerChosen(answerChosen);
						System.out.println("q"+questionNum +" "+ request.getParameterValues("q"+questionNum));
						multi.setQuestionInQuestionsArray(j, fib);

					}else if(questionComponent instanceof FillInMultipleBlankQuestion){
						FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) questionComponent;
						ArrayList<String> blanks = fimb.getBlank();
						String answerChosen = "";
						//System.out.println("ID:"+fimb.getQuestionid());
						//System.out.println("blanks: "+blanks);
						for(int k = 0; k<blanks.size(); k++) {
							String inputname = questionNum+blanks.get(k);
							//System.out.println(inputname);
							answerChosen += request.getParameter("q"+inputname)+"~"; //getting the answer user gave in each blank
						}
						fimb.setAnswerChosen(answerChosen);
						multi.setQuestionInQuestionsArray(j, fimb);
						
					}
					test.setQuestionInQuestionsArray(i, multi);
				}
			}	
		}
		System.out.println(test.scoreTest());
		Connection connection = DBConnection.getDBConnection();
		try {
			System.out.println("putting test on DB.......");
			Statement st = connection.createStatement();
			String createAttemptQuery = "INSERT INTO testersitedatabase.attemptbook (attemptNumber,idstudentprofiles,idtest,grade, gradeOutOf) VALUES ('"+attemptnum+"','"+studentid+"','"+test.getTestId()+"','"+test.getTotalPtsReceived()+"','"+test.getTotalPts()+"');";
			System.out.println(createAttemptQuery);
			st.execute(createAttemptQuery);
			ResultSet rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
			rSet.next();
			String idattempt = rSet.getString("LAST_INSERT_ID()");
			for(Question question : test.getQuestionArray()) {
				if(question instanceof MultipartQuestion) {
					MultipartQuestion mpq = (MultipartQuestion) question;
					for(Question questionComponent : mpq.getQuestions()) {
						String insertAnswerChoiceQuery = "INSERT INTO testersitedatabase.attempt_answer_choice (idattempt,idquestion,answerGiven,ptsGiven) VALUES ('"+idattempt+"','"+questionComponent.getQuestionid()+"','"+questionComponent.getAnswerChosen()+"','"+questionComponent.getPointsReceived()+"');";
						System.out.println(insertAnswerChoiceQuery);
						st.execute(insertAnswerChoiceQuery);
					}
				}else {
					String insertAnswerChoiceQuery = "INSERT INTO testersitedatabase.attempt_answer_choice (idattempt,idquestion,answerGiven,ptsGiven) VALUES ('"+idattempt+"','"+question.getQuestionid()+"','"+question.getAnswerChosen()+"','"+question.getPointsReceived()+"');";
					System.out.println(insertAnswerChoiceQuery);
					st.execute(insertAnswerChoiceQuery);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getLocalizedMessage());
		}
		session.setAttribute("thistest", test);
		
		if(!test.isAllowSeeAttempt()) {
			RequestDispatcher rd = request.getRequestDispatcher("SGradeUnreleasedPage.jsp");
			rd.forward(request, response);
		}
		RequestDispatcher rd = request.getRequestDispatcher("AfterTestPage.jsp");
		rd.forward(request, response);
		//here all answers given by student should be entered into test object. All we have to do now is to tally everything up and submit to the database
		
		
	}
}
