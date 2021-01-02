package com.testservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class DeleteOrEditQuestionServlet
 */
@WebServlet("/DeleteOrEditQuestionServlet")
public class DeleteOrEditQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if(action.equals("edit")) {
			String link ="TestLayout.jsp";
			Connection con = DBConnection.getDBConnection();
			try {
				String questionid = request.getParameter("selectedquestion");
				Statement st = con.createStatement();
				//System.out.println(questionid);
				ResultSet rSet = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable WHERE idquestion='"+questionid+"';");
				request.setAttribute("idquestion", questionid); //so that in the edit page we can use idquestion to fetch question data from DB
				String table ="";
				if(rSet.next()) {
					table = rSet.getString("tablename");
				}else {
					System.out.println("Failed to get question by Questionid in DELETE OF EDIT servlet");
				}
				if(table.equals("questionsdatabase.multiplechoice")) {
					link = "QuestionzEditMultipleChoice.jsp";
				}else if(table.equals("questionsdatabase.truefalse")) {
					link = "QuestionzEditTF.jsp";
				}else if(table.equals("questionsdatabase.shortanswer")) {
					link = "QuestionzEditshortresponse.jsp";
				}else if(table.equals("questionsdatabase.checkall")) {
					link = "QuestionzEditcheckall.jsp";
				}else if(table.equals("questionsdatabase.fillintheblank")) {
					link = "QuestionzEditfillintheblank.jsp";
				}else if(table.equals("questionsdatabase.fillinmultipleblank")) {
					link = "QuestionzEditfillinmultipleblank.jsp";
				}else if(table.equals("questionsdatabase.multipartquestion")) {
					link = "QuestionzEditmultipart.jsp";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			RequestDispatcher rd = request.getRequestDispatcher(link);
			rd.forward(request, response);
		}else {
			Connection con = DBConnection.getDBConnection();
			try {
				Statement st = con.createStatement();
				if(action.equals("Delete")) {
					ResultSet rSet = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable WHERE idquestion = '"+request.getParameter("selectedquestion")+"';");
					String tablename = "";
					if(rSet.next()) {
						tablename = rSet.getString("tablename");
					}else {
						System.out.println("Couldnt get table name for question "+request.getParameter("selectedquestion"));
					}
					System.out.println("DELETE FROM "+tablename+" WHERE idquestion = '"+request.getParameter("selectedquestion")+"';");
					st.executeUpdate("DELETE FROM questionsdatabase.allquestiontable WHERE idquestion = '"+request.getParameter("selectedquestion")+"';");
					st.executeUpdate("DELETE FROM "+tablename+" WHERE idquestion = '"+request.getParameter("selectedquestion")+"';");
					RequestDispatcher rd = request.getRequestDispatcher("TestLayout.jsp");
					rd.forward(request, response);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
