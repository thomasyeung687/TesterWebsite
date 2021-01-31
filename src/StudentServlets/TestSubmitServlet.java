package StudentServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
						
						multi.setQuestionInQuestionsArray(j, mc);
					}else if(questionComponent instanceof TFQuestion){
						TFQuestion tf = (TFQuestion) questionComponent;
						tf.setAnswerChosen(request.getParameter("q"+questionNum));
						
						multi.setQuestionInQuestionsArray(j, questionComponent);
					}else if(questionComponent instanceof ShortResponseQuestion){
						ShortResponseQuestion sr = (ShortResponseQuestion) question;
						sr.setAnswerChosen(request.getParameter("q"+questionNum));
						
						multi.setQuestionInQuestionsArray(j, sr);
					}else if(questionComponent instanceof CheckAllQuestion){
						CheckAllQuestion ca = (CheckAllQuestion) questionComponent;
						String[] answersArray = request.getParameterValues("q"+questionNum);
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
						
						multi.setQuestionInQuestionsArray(j, fib);
					}else if(questionComponent instanceof FillInMultipleBlankQuestion){
						FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) questionComponent;
						ArrayList<String> blanks = fimb.getBlank();
						String answerChosen = "";
						//System.out.println("ID:"+fimb.getQuestionid());
						System.out.println("blanks: "+blanks);
						for(int k = 0; k<blanks.size(); k++) {
							String inputname = questionNum+blanks.get(k);
							System.out.println(inputname);
							answerChosen += request.getParameter("q"+inputname); //getting the answer user gave in each blank
						}
						fimb.setAnswerChosen(answerChosen);
						multi.setQuestionInQuestionsArray(j, fimb);
					}
				}
				multi.calculatePtsReceived();
			}
		}
	}
}
