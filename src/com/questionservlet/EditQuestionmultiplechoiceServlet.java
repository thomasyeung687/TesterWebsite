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
 * Servlet implementation class EditQuestionmultiplechoiceServlet
 */
@WebServlet("/EditQuestionmultiplechoiceServlet")
public class EditQuestionmultiplechoiceServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Connection con = DBConnection.getDBConnection();
			Statement st = con.createStatement();
			String answerString="";
			for(int i = 0; i < Integer.parseInt(request.getParameter("amtofquestions").trim()); i++) {
				String ans = request.getParameter("ans"+i);
				//System.out.println(ans);
				if(ans.contains("~")) {
					System.out.println("contains delimiter");
					request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
				}else {
					answerString += ans+"~";
				}
			}
			System.out.println(answerString);
			//creating a new row in allquestiontable
			String idquestion = request.getParameter("idquestion");
			
			String query = "UPDATE questionsdatabase.multiplechoice SET questiontitle = '"+request.getParameter("questiontitle")+"', question = '"+request.getParameter("questiontext")+"', answerstring = '"+answerString+"', correctanswer = '"+request.getParameter(request.getParameter("correctans"))+"', pointsworth = '"+request.getParameter("pointsworth")+"' WHERE idquestion = '"+idquestion+"';"; 
			System.out.println(query);
			st.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("TestLayout.jsp");
	}

}
