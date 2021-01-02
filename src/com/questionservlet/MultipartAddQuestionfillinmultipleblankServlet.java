package com.questionservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class QuestionfillinmultipleblankServlet
 */
@WebServlet("/MultipartAddQuestionfillinmultipleblankServlet")
public class MultipartAddQuestionfillinmultipleblankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println("idmultiquestion="+request.getParameter("idmultiquestion").trim());
			System.out.println("questiontitle="+request.getParameter("questiontitle"));
			System.out.println("questiontext="+request.getParameter("questiontext"));
			System.out.println("pointsworth="+request.getParameter("pointsworth"));
			System.out.println("casesensistive="+request.getParameter("casesensistive"));
			System.out.println("allowPartialCredit="+request.getParameter("allowPartialCredit"));
			System.out.println("blank="+request.getParameter("blank"));
			String idmultiquestion = request.getParameter("idmultiquestion");
			String questiontitle = request.getParameter("questiontitle");
			String questiontext = request.getParameter("questiontext");
			String pointsworth = request.getParameter("pointsworth");
			String casesensistive = request.getParameter("casesensistive");
			String allowPartialCredit = request.getParameter("allowPartialCredit");
			String blankstring = request.getParameter("blank");
			String[] blanks = blankstring.split(",");
			ArrayList<String> blank = new ArrayList<String>(); //arraylist format which we use to pass to previous page if error occurs
			for(int i = 0; i<blanks.length-1;i++) {
				blank.add(blanks[i]);
			}
			System.out.println("Blank: "+blank);
			
			ArrayList<String> correctanswers = new ArrayList<String>(); //arraylist of correct answers [ansforfirstblank, ansforsecondblank, ...]
			for(String x : blanks) {
				correctanswers.add(request.getParameter(x));
			}
			correctanswers.remove(blanks.length-1);
			//System.out.println("Correctanswers:" +correctanswers);
			String correctanswerString = ""; //this is the all the correct answers formated so we can add it to sql database. it will be ansto1stblk|ansto2ndblk|ansto3rdblk|etc
			for (int i = 0; i < correctanswers.size(); i++) {
				String[] correctanssplit =  correctanswers.get(i).split("~");
				if(correctanssplit.length > 25) { //checking if there are too many possible answers
					request.setAttribute("idmultiquestion", idmultiquestion);
					request.setAttribute("questiontitle", questiontitle);
					request.setAttribute("questiontext", questiontext);
					request.setAttribute("questiontitle", questiontitle);
					request.setAttribute("pointsworth", pointsworth);
					request.setAttribute("casesensistive", casesensistive);
					request.setAttribute("allowPartialCredit", allowPartialCredit);
					request.setAttribute("blank", blank);
					request.setAttribute("error", "Too many possible answers");
					request.getRequestDispatcher("Questionfillintheblank2.jsp").forward(request, response);
				}
				String correctans = "";
				for(int j = 0; j<correctanssplit.length; j++) {
					 correctanssplit[j] = correctanssplit[j].trim();
					 correctans += correctanssplit[j]+"~";
				}
				correctanswerString += correctans+"|";
			}
			//here we should have the answers for each formated corrected with all extra spaces trimmed away in correctanswers.
			System.out.println("correctanswerString:" +correctanswerString);
			
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				ResultSet rSet;
				
				String query ="INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('-1', 'questionsdatabase.fillinmultipleblank');";
				st.execute(query);
				rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
				String idquestion = null;
				if(rSet.next()) {
					idquestion = rSet.getString("LAST_INSERT_ID()");
				}else {
					System.out.println("Failed to get LAST_INSERTED_ID");
					request.setAttribute("idmultiquestion", idmultiquestion);
					request.setAttribute("questiontitle", questiontitle);
					request.setAttribute("questiontext", questiontext);
					request.setAttribute("questiontitle", questiontitle);
					request.setAttribute("pointsworth", pointsworth);
					request.setAttribute("casesensistive", casesensistive);
					request.setAttribute("allowPartialCredit", allowPartialCredit);
					request.setAttribute("blank", blank);
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID");
					request.getRequestDispatcher("Questionfillintheblank2.jsp").forward(request, response);
				}
				query = "INSERT INTO `questionsdatabase`.`fillinmultipleblank`(`idquestion`,`questiontitle`,`question`,`correctanswer`,`casesensitive`,`partialcredit`, `pointsworth`) VALUES "
						+ "('"+idquestion+"','"+questiontitle+"','"+questiontext+"','"+correctanswerString+"', b'"+casesensistive+"',b'"+allowPartialCredit+"','"+pointsworth+"');";
				System.out.println(query);
				st.execute(query);
				
				//here we have to add the id of the question we made above into the questioncomponentid string in multipartquestion as well as increment the points so we get multipart from the database first.
				rSet = st.executeQuery("SELECT * FROM questionsdatabase.multipartquestion WHERE idquestion = '"+request.getParameter("idmultiquestion")+"';");
				if(rSet.next()) { //we got the multipart question here.
					String questioncomponentids = rSet.getString("questioncomponentids");
					int multipointsworth = rSet.getInt("pointsworth");
					questioncomponentids += idquestion+","; //adding the new checkall question's id into the questioncomponentidstring.
					multipointsworth += Integer.parseInt(request.getParameter("pointsworth").trim());  //this might throw error.
					System.out.println("new questioncompentid: "+questioncomponentids+". New ptsworth: "+multipointsworth);
					
					query = "UPDATE `questionsdatabase`.`multipartquestion` SET questioncomponentids='"+questioncomponentids+"', pointsworth='"+multipointsworth+"'  WHERE idquestion = "+request.getParameter("idmultiquestion")+";"; 
					st.execute(query);
					request.setAttribute("idquestion", request.getParameter("idmultiquestion")); //this is so that the QuestionzEditmultipart page can get the id of the multipart question
					request.getRequestDispatcher("QuestionzEditmultipart.jsp").forward(request, response);
				}else {
					System.out.println("Failed to get multipartquestion MultipartAddQuestioncheckallServlet");
					//perhaps we should delete the question we have created earlier if we end up in this else statement
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID");
					request.getRequestDispatcher("MultipartAddQuestionfillinmultipleblank2.jsp").forward(request, response);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}

}
