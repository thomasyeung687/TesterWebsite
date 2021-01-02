package com.questionservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Redirect;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class EditQuestionfillinmultipleblankServlet
 */
@WebServlet("/EditQuestionfillinmultipleblankServlet")
public class EditQuestionfillinmultipleblankServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("idquestion="+request.getParameter("idquestion").trim());
		System.out.println("questiontitle="+request.getParameter("questiontitle"));
		System.out.println("questiontext="+request.getParameter("questiontext"));
		System.out.println("pointsworth="+request.getParameter("pointsworth"));
		System.out.println("casesensistive="+request.getParameter("casesensistive"));
		System.out.println("allowPartialCredit="+request.getParameter("allowPartialCredit"));
		System.out.println("blank="+request.getParameter("blank"));
		String idquestion = request.getParameter("idquestion").trim();
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
		System.out.println("Correctanswers:" +correctanswers);
		String correctanswerString = ""; //this is the all the correct answers formated so we can add it to sql database. it will be ansto1stblk|ansto2ndblk|ansto3rdblk|etc
		for (int i = 0; i < correctanswers.size(); i++) {
			String[] correctanssplit =  correctanswers.get(i).split("~");
			if(correctanssplit.length > aQuestionVariables.AMOUNT_OF_ANS_IN_FILLINTHEBLANKQUESTION) { //checking if there are too many possible answers
				request.setAttribute("error", "Too many possible answers");
				request.setAttribute("idquestion", idquestion);
				request.getRequestDispatcher("QuestionzEditfillinmultipleblank.jsp").forward(request, response);
			}
			String correctans = "";
			for(int j = 0; j<correctanssplit.length; j++) {
				 correctanssplit[j] = correctanssplit[j].trim();
				 correctans += correctanssplit[j]+"~";
			}
			correctanswerString += correctans+"|";
		}
		//here we should have the answers for each formated corrected with all extra spaces trimmed away in correctanswers.
		System.out.println("formatedcorrectanswerString:" +correctanswerString);
		
		if(casesensistive == null) { //now we can insert casesensitive into sql
			casesensistive = "0";
		}else {
			casesensistive = "1";
		}
		if(allowPartialCredit == null) { //now we can insert allowPartialCredit into sql
			allowPartialCredit = "0";
		}else {
			allowPartialCredit = "1";
		}
		try {
			Connection con = DBConnection.getDBConnection();
			Statement st = con.createStatement();
			String query = "UPDATE `questionsdatabase`.`fillinmultipleblank` SET questiontitle = '"+questiontitle+"', question = '"+questiontext+"', correctanswer = '"+correctanswerString+"', casesensitive = b'"+casesensistive+"', partialcredit = b'"+allowPartialCredit+"', pointsworth = '"+pointsworth+"' WHERE idquestion = "+idquestion+";";
			System.out.println(query);
			st.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("TestLayout.jsp");
	}
}
