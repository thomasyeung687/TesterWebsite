package com.questionservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class EditQuestionfillintheblankServlet
 */
@WebServlet("/EditQuestionfillintheblankServlet")
public class EditQuestionfillintheblankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			String idquestion = request.getParameter("idquestion");
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
			System.out.println(questiontitle+" "+questiontext+" "+correctans+" "+ptsworth+" "+casesensistive + "This is id:"+idquestion);
			if(!questiontext.contains("[x]")) { // checking if the questiontext has a blank
				request.setAttribute("error", "Question text does not contain [x]");
				request.getRequestDispatcher("QuestionzEditfillintheblank.jsp").forward(request, response);
			}
			//perhaps a check for multiple blanks inserted here.
			//formating the answers.
			
			String[] correctanssplit =  correctans.split("~");
			if(correctanssplit.length > aQuestionVariables.AMOUNT_OF_ANS_IN_FILLINTHEBLANKQUESTION) { //checking if there are too many possible answers
				request.setAttribute("error", "Too many possible answers. Limit is "+aQuestionVariables.AMOUNT_OF_ANS_IN_FILLINTHEBLANKQUESTION);
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
				String query = "UPDATE `questionsdatabase`.`fillintheblank` SET questiontitle = '"+questiontitle+"', question = '"+questiontext+"', correctanswer = '"+correctans+"', casesensitive = b'"+casesensistive+"', pointsworth = '"+ptsworth+"' WHERE idquestion = "+idquestion+";";
				System.out.println(query);
				st.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			response.sendRedirect("TestLayout.jsp");
	}
}
