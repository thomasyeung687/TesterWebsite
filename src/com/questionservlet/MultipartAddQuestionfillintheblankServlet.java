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
 * Servlet implementation class QuestionfillintheblankServlet
 */
@WebServlet("/MultipartAddQuestionfillintheblankServlet")
public class MultipartAddQuestionfillintheblankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String questiontitle = request.getParameter("questiontitle");
			String questiontext = request.getParameter("questiontext");
			String correctans =  request.getParameter("correctans");
			String ptsworth = request.getParameter("pointsworth");
			String casesensistive = request.getParameter("casesensistive");
			if(casesensistive == null) { //now we can insert casesensitive into sql
				casesensistive = "0";
			}else {
				casesensistive = "1";
			}
			System.out.println(questiontitle+" "+questiontext+" "+correctans+" "+ptsworth+" "+casesensistive);
			if(!questiontext.contains("[x]")) { // checking if the questiontext has a blank
				request.setAttribute("pointsworth", ptsworth);
				request.setAttribute("questiontext", questiontext);
				request.setAttribute("correctans", correctans);
				request.setAttribute("questiontitle", questiontitle);
				request.setAttribute("error", "Question text does not contain [x]");
				request.getRequestDispatcher("Questionfillintheblank.jsp").forward(request, response);
			}
			//formating the answers.
			String[] correctanssplit =  correctans.split("~");
			if(correctanssplit.length > aQuestionVariables.AMOUNT_OF_ANS_IN_FILLINTHEBLANKQUESTION) { //checking if there are too many possible answers
				request.setAttribute("pointsworth", ptsworth);
				request.setAttribute("questiontext", questiontext);
				request.setAttribute("correctans", correctans);
				request.setAttribute("questiontitle", questiontitle);
				request.setAttribute("error", "Too many possible answers. Limit is "+aQuestionVariables.AMOUNT_OF_ANS_IN_FILLINTHEBLANKQUESTION );
				request.getRequestDispatcher("Questionfillintheblank.jsp").forward(request, response);
			}
			correctans = "";
			for(int i = 0; i<correctanssplit.length; i++) {
				 correctanssplit[i] = correctanssplit[i].trim();
				 correctans += correctanssplit[i]+"~";
			}
			System.out.println(correctans);
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				ResultSet rSet;
				
				String query ="INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('-1', 'questionsdatabase.fillintheblank');";
				st.execute(query);
				rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
				String idquestion = null;
				if(rSet.next()) {
					idquestion = rSet.getString("LAST_INSERT_ID()");
				}else {
					System.out.println("Failed to get LAST_INSERTED_ID");
					request.setAttribute("pointsworth", ptsworth);
					request.setAttribute("questiontext", questiontext);
					request.setAttribute("correctans", correctans);
					request.setAttribute("questiontitle", questiontitle);
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID MultipartAddQuestionfillintheblankServlet.java");
					request.getRequestDispatcher("MultipartAddQuestionfillintheblank.jsp").forward(request, response);
				}
				query = "INSERT INTO `questionsdatabase`.`fillintheblank`(`idquestion`,`questiontitle`,`question`,`correctanswer`,`casesensitive`, `pointsworth`) VALUES "
						+ "('"+idquestion+"','"+questiontitle+"','"+questiontext+"','"+correctans+"', b'"+casesensistive+"','"+ptsworth+"');";
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
					System.out.println("Failed to get multipartquestion MultipartAddQuestionfillintheblankServlet.java");
					//perhaps we should delete the question we have created earlier if we end up in this else statement
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID");
					request.getRequestDispatcher("MultipartAddQuestionfillintheblank.jsp").forward(request, response);
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
