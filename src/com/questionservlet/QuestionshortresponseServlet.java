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
 * Servlet implementation class QuestionshortresponseServlet
 */
@WebServlet("/QuestionshortresponseServlet")
public class QuestionshortresponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("submit")||request.getParameter("action").equals("another")){
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				ResultSet rSet = null;
				String query = "INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('"
						+ request.getParameter("idtest") + "', 'questionsdatabase.shortanswer');";

				st.execute(query);
				rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
				String idquestion = null;
				
				if(rSet.next()) { 
					idquestion = rSet.getString("LAST_INSERT_ID()");
				}else {
					System.out.println("Failed to get LAST_INSERTED_ID SR"); 
				}
				query = "INSERT INTO `questionsdatabase`.`shortanswer`(`idquestion`,`questiontitle`,`question`, `pointsworth`) VALUES "
						+ "('" + idquestion + "','" + request.getParameter("questiontitle") + "','"
						+ request.getParameter("questiontext") + "','"
						+ request.getParameter("pointsworth") + "');";
				System.out.println(query);
				st.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(request.getParameter("action").equals("another")) {
				request.getRequestDispatcher("Questionshortresponse.jsp").forward(request, response);
			}else {
				//request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
				response.sendRedirect("TestLayout.jsp");
			}
		}else if(request.getParameter("action").equals("back")) {
			//request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
			response.sendRedirect("TestLayout.jsp");
		}
	}
}
