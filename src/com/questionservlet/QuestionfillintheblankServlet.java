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
@WebServlet("/QuestionfillintheblankServlet")
public class QuestionfillintheblankServlet extends HttpServlet {
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
				
				String query ="INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('"+request.getParameter("idtest")+"', 'questionsdatabase.fillintheblank');";
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
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID");
					request.getRequestDispatcher("Questionfillintheblank.jsp").forward(request, response);
				}
				query = "INSERT INTO `questionsdatabase`.`fillintheblank`(`idquestion`,`questiontitle`,`question`,`correctanswer`,`casesensitive`, `pointsworth`) VALUES "
						+ "('"+idquestion+"','"+questiontitle+"','"+questiontext+"','"+correctans+"', b'"+casesensistive+"','"+ptsworth+"');";
				System.out.println(query);
				st.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(request.getParameter("action").equals("another")) {
				request.getRequestDispatcher("Questionfillintheblank.jsp").forward(request, response);
			}else {
				//request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
				response.sendRedirect("TestLayout.jsp");
			}
	}
}
