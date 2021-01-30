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
			System.out.println(test.getQuestionArray().get(i).getQuestionType());
			
			if(question instanceof MultipleChoiceQuestion){ 
				MultipleChoiceQuestion mc = (MultipleChoiceQuestion) question;
				System.out.println("ID:"+mc.getQuestionid());
				System.out.println(request.getParameter("q"+(i+1)));
				System.out.println();
				
			}else if(question instanceof TFQuestion){
				TFQuestion tf = (TFQuestion) question;
				System.out.println("ID:"+tf.getQuestionid());
				System.out.println(request.getParameter("q"+(i+1)));
				System.out.println();
				
			}else if(question instanceof ShortResponseQuestion){
				ShortResponseQuestion sr = (ShortResponseQuestion) question;
				System.out.println("ID:"+sr.getQuestionid());
				System.out.println(request.getParameter("q"+(i+1)));
				System.out.println();
				
			}else if(question instanceof CheckAllQuestion){
				CheckAllQuestion ca = (CheckAllQuestion) question;
				String[] answersArray = request.getParameterValues("q"+(i+1));
				List<String> answersList = Arrays.asList(answersArray);
				System.out.println("ID:"+ca.getQuestionid());
				System.out.println(answersList);
				System.out.println();
				
			}else if(question instanceof FillInTheBlankQuestion){
				FillInTheBlankQuestion fib = (FillInTheBlankQuestion) question;
				System.out.println("ID:"+fib.getQuestionid());
				System.out.println(request.getParameter("q"+(i+1)));
				System.out.println();
				
			}else if(question instanceof FillInMultipleBlankQuestion){
				FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) question;
				ArrayList<String> blanks = fimb.getBlank();
				ArrayList<String> answers = new ArrayList<String>();
				System.out.println("ID:"+fimb.getQuestionid());
				for(int j = 0; j<blanks.size(); j++) {
					answers.add(request.getParameter("q"+(i+1)+blanks.get(j))); //getting the answer user gave in each blank
				}
				System.out.println(answers);//answer arraylist
				System.out.println();
				
			}else if(question instanceof MultipartQuestion){
				MultipartQuestion multi = (MultipartQuestion) question;
				ArrayList<Question> questions = multi.getQuestions();
				System.out.println("ID:"+multi.getQuestionid());
				int questionComponentNumber = 1; //questionComponent number. 
				for(Question questionComponent : questions){ //for each question in the multipart question, display it properly. questionComponent is basiscally a question in the multipart question
					String questionNum = (i+1)+"."+questionComponentNumber++; //(i+1) = n
					System.out.println(questionComponent.getQuestionType());
					if(questionComponent instanceof MultipleChoiceQuestion){ 
						MultipleChoiceQuestion mc = (MultipleChoiceQuestion) questionComponent;
						System.out.println("ID:"+mc.getQuestionid());
						System.out.println(request.getParameter("q"+questionNum));
						
					}else if(questionComponent instanceof TFQuestion){
						TFQuestion tf = (TFQuestion) questionComponent;
						System.out.println("ID:"+tf.getQuestionid());
						System.out.println(request.getParameter("q"+questionNum));
						
					}else if(questionComponent instanceof ShortResponseQuestion){
						ShortResponseQuestion sr = (ShortResponseQuestion) question;
						System.out.println("ID:"+sr.getQuestionid());
						System.out.println(request.getParameter("q"+questionNum));
						
					}else if(questionComponent instanceof CheckAllQuestion){
						CheckAllQuestion ca = (CheckAllQuestion) questionComponent;
						String[] answersArray = request.getParameterValues("q"+questionNum);
						List<String> answersList = Arrays.asList(answersArray);
						System.out.println("ID:"+ca.getQuestionid());
						System.out.println(answersList);
						
					}else if(questionComponent instanceof FillInTheBlankQuestion){
						FillInTheBlankQuestion fib = (FillInTheBlankQuestion) questionComponent;
						System.out.println("ID:"+fib.getQuestionid());
						System.out.println(request.getParameter("q"+questionNum));
						
					}else if(questionComponent instanceof FillInMultipleBlankQuestion){
						FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) questionComponent;
						ArrayList<String> blanks = fimb.getBlank();
						ArrayList<String> answers = new ArrayList<String>();
						System.out.println("ID:"+fimb.getQuestionid());
						for(int j = 0; j<blanks.size(); j++) {
							String inputname = questionNum+blanks.get(j);
							answers.add(request.getParameter("q"+inputname)); //getting the answer user gave in each blank
						}
						System.out.println(answers);//answer arraylist
						System.out.println();

						if(fimb.isCasesensitive()){
							
						}else{
							
						}
						
						if(fimb.isPartialcredit()){
							
						}else{
							
						}
					}
				}
			}
		}
	}
}
