package StudentServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;
import com.testersite.model.*;

/**
 * Servlet implementation class StartTestServlet
 */
@WebServlet("/StartTestServlet")
public class StartTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = DBConnection.getDBConnection();
		HttpSession session = request.getSession();
		Test thisTest = (Test)session.getAttribute("thistest");
		System.out.println("testid = "+thisTest.getTestId());
		try{
			Statement st = con.createStatement();
			ResultSet rset = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable WHERE idtest = '"+thisTest.getTestId()+"';");
			//System.out.println("out");
			while(rset.next()){ //while there are questions from allquestionstable with that idtest. we go into each row and get the table and questionid and find that question
				//System.out.println("in");
				
				
				String idquestion = rset.getString("idquestion");
				String tablename = rset.getString("tablename");
				Statement st2 = con.createStatement();
				ResultSet rset2 = st2.executeQuery("SELECT * FROM "+tablename+" WHERE idquestion='"+idquestion+"';");
				if(rset2.next()){
					if(tablename.equals("questionsdatabase.multiplechoice")){
						System.out.println("Adding new mc question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						MultipleChoiceQuestion newmcq = new MultipleChoiceQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"),rset2.getString("answerstring"), rset2.getString("correctanswer"));
						//System.out.println(newmcq.getAnswers().toString());
						thisTest.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.truefalse")){
						System.out.println("Adding new TF question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						TFQuestion newmcq = new TFQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"));
						thisTest.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.shortanswer")){
						System.out.println("Adding new SR question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						ShortResponseQuestion newmcq = new ShortResponseQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"));
						thisTest.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.checkall")){
						System.out.println("Adding new CheckAll question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						CheckAllQuestion newmcq = new CheckAllQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("answerstring"), rset2.getString("correctstring"));
						System.out.println(newmcq.toString());
						thisTest.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.fillintheblank")){
						System.out.println("Adding new fillintheblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						FillInTheBlankQuestion newfib = new FillInTheBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"));
						System.out.println(newfib.toString());
						thisTest.addQuestionToQuestions(newfib);
					}else if(tablename.equals("questionsdatabase.fillinmultipleblank")){
						System.out.println("Adding new fillinmultipleblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						System.out.println("Question:"+rset2.getString("question"));
						FillInMultipleBlankQuestion newfib = new FillInMultipleBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"),rset2.getBoolean("partialcredit"));
						//System.out.println(newfib.toString());
						thisTest.addQuestionToQuestions(newfib);
					}else if(tablename.equals("questionsdatabase.multipartquestion")){
						System.out.println("Adding new multipartquestion question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						System.out.println("Question:"+rset2.getString("question"));
						MultipartQuestion multi = new MultipartQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("questioncomponentids"));
						//System.out.println(newfib.toString());
						thisTest.addQuestionToQuestions(multi);
						System.out.println("Question ids: "+multi.getQuestionCompoentids());
					}
					//here you can add it for others.
				}else{
					System.out.println("Couldn't get question "+idquestion);
				}
			}
			session.setAttribute("thistest", thisTest);
			//rset for other questiontypes V
		}catch(Exception exception){
			System.out.println("StartTestServlet Exception");
			System.out.println(exception.getLocalizedMessage());
		}
		
		response.sendRedirect("TestPage.jsp"); 
	}
}
