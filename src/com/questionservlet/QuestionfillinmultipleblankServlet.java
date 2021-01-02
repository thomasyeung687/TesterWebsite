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
@WebServlet("/QuestionfillinmultipleblankServlet")
public class QuestionfillinmultipleblankServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("submit")||request.getParameter("action").equals("another")){
			System.out.println("idtest="+request.getParameter("idtest").trim());
			System.out.println("questiontitle="+request.getParameter("questiontitle"));
			System.out.println("questiontext="+request.getParameter("questiontext"));
			System.out.println("pointsworth="+request.getParameter("pointsworth"));
			System.out.println("casesensistive="+request.getParameter("casesensistive"));
			System.out.println("allowPartialCredit="+request.getParameter("allowPartialCredit"));
			System.out.println("blank="+request.getParameter("blank"));
			String idtest = request.getParameter("idtest").trim();
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
					request.setAttribute("idtest", idtest);
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
				
				String query ="INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('"+request.getParameter("idtest")+"', 'questionsdatabase.fillinmultipleblank');";
				st.execute(query);
				rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
				String idquestion = null;
				if(rSet.next()) {
					idquestion = rSet.getString("LAST_INSERT_ID()");
				}else {
					System.out.println("Failed to get LAST_INSERTED_ID");
					request.setAttribute("idtest", idtest);
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(request.getParameter("action").equals("another")) {
				request.getRequestDispatcher("Questionfillinmultipleblank.jsp").forward(request, response);
			}else {
				response.sendRedirect("TestLayout.jsp");
			}
		}
	}

}
