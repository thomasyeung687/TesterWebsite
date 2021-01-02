package com.questionservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
 * Servlet implementation class QuestiontruefalseServlet
 */
@WebServlet("/MultipartAddQuestiontruefalseServlet")
public class MultipartAddQuestiontruefalseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				String correctans = request.getParameter("correctans");
				ResultSet rSet = null;
				String query = "INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('-1', 'questionsdatabase.truefalse');";

				st.execute(query);
				rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
				String idquestion = null;
				
				if(rSet.next()) { 
					idquestion = rSet.getString("LAST_INSERT_ID()");
				}else {
					System.out.println("Failed to get LAST_INSERTED_ID MultipartAddQuestiontruefalseServlet");
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID");
					request.getRequestDispatcher("MultipartAddQuestiontruefalse.jsp").forward(request, response);
				}
				 
				query = "INSERT INTO `questionsdatabase`.`truefalse`(`idquestion`,`questiontitle`,`question`,`correctanswer`, `pointsworth`) VALUES "
						+ "('" + idquestion + "','" + request.getParameter("questiontitle") + "','"
						+ request.getParameter("questiontext") + "','" + correctans + "','"
						+ request.getParameter("pointsworth") + "');";
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
					System.out.println("Failed to get multipartquestion MultipartAddQuestionmultiplechoiceServlet");
					//perhaps we should delete the question we have created earlier if we end up in this else statement
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID");
					request.getRequestDispatcher("MultipartAddQuestiontruefalse.jsp").forward(request, response);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
