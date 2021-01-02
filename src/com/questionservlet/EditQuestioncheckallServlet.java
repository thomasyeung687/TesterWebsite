package com.questionservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class EditQuestioncheckallServlet
 */
@WebServlet("/EditQuestioncheckallServlet")
public class EditQuestioncheckallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Connection con = DBConnection.getDBConnection();
			Statement st = con.createStatement();
			String idquestion = request.getParameter("idquestion");
			String answerString="";
			String correctString="";
			for(int i = 0; i < Integer.parseInt(request.getParameter("amtofquestions").trim()); i++) {
				String ans = request.getParameter("ans"+i);
				//System.out.println(ans);
				//System.out.println(request.getParameter("check"+i));
				if(request.getParameter("check"+i) != null) {
					correctString += ans+"~"; //if not null meaning it was checked, then we add it to the correct string
				}
				if(ans.contains("~")) {
					System.out.println("contains delimiter");
					request.setAttribute("idquestion", idquestion);
					request.setAttribute("error", "contains delimiter");
					request.getRequestDispatcher("QuestionzEditcheckall.jsp").forward(request, response);
					return;
				}else {
					answerString += ans+"~";
				}
			}
			if(correctString.equals("")) {
				//Here we can install all the attributes aka correct ans ptsworth so that user wont have to retype everything when they get sent back
				request.setAttribute("idquestion", idquestion);
				request.setAttribute("error", "No checkbox was checked. Please check atleast one.");
				request.getRequestDispatcher("QuestionzEditcheckall.jsp").forward(request, response);
				return;
			}
			
			String query = "UPDATE questionsdatabase.checkall SET questiontitle = '"+request.getParameter("questiontitle")+"', question = '"+request.getParameter("questiontext")+"',answerstring = '"+answerString+"',correctstring = '"+correctString+"', pointsworth = '"+request.getParameter("pointsworth")+"' WHERE idquestion = '"+idquestion+"';"; 
			System.out.println(query);
			st.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("TestLayout.jsp");
	}

}
