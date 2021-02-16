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
 * Servlet implementation class TakeTest1By1Servlet
 */
@WebServlet("/TakeTest1By1Servlet")
public class TakeTest1By1Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Test test = (Test) session.getAttribute("thistest");
		String studentid = ((String) session.getAttribute("studentid")).trim();
		String idtest = test.getTestId();
		int currentQuestion = (int) session.getAttribute("currentQuestion");
		
		int attemptnum = 1; // when implemented. change to request.parameter object
		String action = request.getParameter("action");
		System.out.println();
		System.out.println(studentid+" action:"+action);
		
		
		
		Question question = test.getQuestionArray().get(currentQuestion); //getting the currentQuestion
		System.out.println(question.getQuestionType());
		
		if(question instanceof MultipleChoiceQuestion){ 
			MultipleChoiceQuestion mc = (MultipleChoiceQuestion) question;
			String answerChosen = request.getParameter("answer");

			mc.setAnswerChosen(answerChosen);
			
			test.setQuestionInQuestionsArray(currentQuestion, mc);
			
		}else if(question instanceof TFQuestion){
			TFQuestion tf = (TFQuestion) question;
			String answerChosen = request.getParameter("answer");
			
			tf.setAnswerChosen(answerChosen);
			
			test.setQuestionInQuestionsArray(currentQuestion, tf);
			
		}else if(question instanceof ShortResponseQuestion){
			ShortResponseQuestion sr = (ShortResponseQuestion) question;
			String answerChosen = request.getParameter("answer");

			sr.setAnswerChosen(answerChosen);
			
			test.setQuestionInQuestionsArray(currentQuestion, sr);
			
		}else if(question instanceof CheckAllQuestion){
			CheckAllQuestion ca = (CheckAllQuestion) question;
			String[] answersArray = request.getParameterValues("answer");
			List<String> answersList = Arrays.asList(answersArray);
			String answerChosen = "";
			for(String ans : answersList) {
				answerChosen += ans.trim()+"~";
			}
			ca.setAnswerChosen(answerChosen);
			
			test.setQuestionInQuestionsArray(currentQuestion, ca);

		}else if(question instanceof FillInTheBlankQuestion){
			FillInTheBlankQuestion fib = (FillInTheBlankQuestion) question;
			String answerChosen = request.getParameter("answer");
			fib.setAnswerChosen(answerChosen);
			
			test.setQuestionInQuestionsArray(currentQuestion, fib);
			
		}else if(question instanceof FillInMultipleBlankQuestion){
			FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) question;
			ArrayList<String> blanks = fimb.getBlank();
			//ArrayList<String> answers = new ArrayList<String>();
			String answerChosen = "";
			System.out.println("ID:"+fimb.getQuestionid());
			for(int j = 0; j<blanks.size(); j++) {
				answerChosen += request.getParameter(blanks.get(j)).trim()+"~"; //forming answerchosen string for fimb question
				//answers.add(request.getParameter("q"+(i+1)+blanks.get(j))); //getting the answer user gave in each blank
			}
			fimb.setAnswerChosen(answerChosen);
			test.setQuestionInQuestionsArray(currentQuestion, fimb);

		}else if(question instanceof MultipartQuestion){
			MultipartQuestion multi = (MultipartQuestion) question;
			ArrayList<Question> questions = multi.getQuestions();
			System.out.println("ID:"+multi.getQuestionid());
			int questionComponentNumber = 1; //questionComponent number. 
			for(int j = 0; j < multi.getQuestions().size(); j++){ //for each question in the multipart question, display it properly. questionComponent is basiscally a question in the multipart question
				String inputname = "answer"+questionComponentNumber++; //(i+1) = n
				System.out.println("input name:"+inputname);
				Question questionComponent = multi.getQuestions().get(j);
				System.out.println(questionComponent.getQuestionType());
				if(questionComponent instanceof MultipleChoiceQuestion){ 
					MultipleChoiceQuestion mc = (MultipleChoiceQuestion) questionComponent;
					mc.setAnswerChosen(request.getParameter(inputname));
						System.out.println(inputname +" "+ request.getParameter(inputname));
					multi.setQuestionInQuestionsArray(j, mc);

				}else if(questionComponent instanceof TFQuestion){
					TFQuestion tf = (TFQuestion) questionComponent;
					tf.setAnswerChosen(request.getParameter(inputname));
						System.out.println(inputname +" "+ request.getParameter(inputname));
					multi.setQuestionInQuestionsArray(j, questionComponent);

				}else if(questionComponent instanceof ShortResponseQuestion){
					ShortResponseQuestion sr = (ShortResponseQuestion) question;
					sr.setAnswerChosen(request.getParameter(inputname));
						System.out.println(inputname +" "+ request.getParameter(inputname));
					multi.setQuestionInQuestionsArray(j, sr);

				}else if(questionComponent instanceof CheckAllQuestion){
					CheckAllQuestion ca = (CheckAllQuestion) questionComponent;
					String[] answersArray = request.getParameterValues(inputname);
					System.out.println(inputname +" "+ request.getParameterValues(inputname));
					
					List<String> answersList = Arrays.asList(answersArray);
					String answerChosen = "";
					for(String ans : answersList) {
						answerChosen += ans.trim()+"~";
					}
					ca.setAnswerChosen(answerChosen);
					
					multi.setQuestionInQuestionsArray(j, ca);

				}else if(questionComponent instanceof FillInTheBlankQuestion){
					FillInTheBlankQuestion fib = (FillInTheBlankQuestion) questionComponent;
					String answerChosen = request.getParameter(inputname);
					fib.setAnswerChosen(answerChosen);
					System.out.println(inputname +" "+ request.getParameterValues(inputname));
					multi.setQuestionInQuestionsArray(j, fib);

				}else if(questionComponent instanceof FillInMultipleBlankQuestion){
					FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) questionComponent;
					ArrayList<String> blanks = fimb.getBlank();
					String answerChosen = "";
					//System.out.println("ID:"+fimb.getQuestionid());
					//System.out.println("blanks: "+blanks);
					for(int k = 0; k<blanks.size(); k++) {
						String inputnamefimb = inputname+blanks.get(k);
						//System.out.println(inputname);
						answerChosen += request.getParameter(inputnamefimb)+"~"; //getting the answer user gave in each blank
					}
					fimb.setAnswerChosen(answerChosen);
					multi.setQuestionInQuestionsArray(j, fimb);
					
				}
				test.setQuestionInQuestionsArray(currentQuestion, multi);
			}
		}
		System.out.println("Answer Chosen: "+ question.getAnswerChosen());
		
		
		if(action.equals("save")){
			System.out.println("not implemented yet");
			response.sendRedirect("SShowTest");
		}else if(action.equals("prev")) {
			session.setAttribute("currentQuestion",currentQuestion-1);
		}else if(action.equals("next")) {
			session.setAttribute("currentQuestion",currentQuestion+1);
		}else if(action.equals("Submit")) {
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
				for(Question question1 : test.getQuestionArray()) {
					if(question1 instanceof MultipartQuestion) {
						MultipartQuestion mpq = (MultipartQuestion) question1;
						for(Question questionComponent : mpq.getQuestions()) {
							String insertAnswerChoiceQuery = "INSERT INTO testersitedatabase.attempt_answer_choice (idattempt,idquestion,answerGiven,ptsGiven) VALUES ('"+idattempt+"','"+questionComponent.getQuestionid()+"','"+questionComponent.getAnswerChosen()+"','"+questionComponent.getPointsReceived()+"');";
							System.out.println(insertAnswerChoiceQuery);
							st.execute(insertAnswerChoiceQuery);
						}
					}else {
						String insertAnswerChoiceQuery = "INSERT INTO testersitedatabase.attempt_answer_choice (idattempt,idquestion,answerGiven,ptsGiven) VALUES ('"+idattempt+"','"+question1.getQuestionid()+"','"+question1.getAnswerChosen()+"','"+question1.getPointsReceived()+"');";
						System.out.println(insertAnswerChoiceQuery);
						st.execute(insertAnswerChoiceQuery);
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getLocalizedMessage());
			}
			RequestDispatcher rd = request.getRequestDispatcher("AfterTestPage.jsp");
			rd.forward(request, response);
		}
		session.setAttribute("thistest", test);
		RequestDispatcher rd = request.getRequestDispatcher("TestPage1By1.jsp");
		rd.forward(request, response);
	}
}
