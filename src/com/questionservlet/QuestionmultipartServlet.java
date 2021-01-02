package com.questionservlet;

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
 * Servlet implementation class QuestionmultipartServlet
 */
@WebServlet("/QuestionmultipartServlet")
public class QuestionmultipartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Connection con = DBConnection.getDBConnection();
			Statement st = con.createStatement();
			String query ="INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('"+request.getParameter("idtest")+"', 'questionsdatabase.multipartquestion');";
			st.execute(query);
			ResultSet rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
			String idquestion = null;
			if(rSet.next()) {
				idquestion = rSet.getString("LAST_INSERT_ID()");
			}else {
				System.out.println("Failed to get LAST_INSERTED_ID");
			}
			query = "INSERT INTO `questionsdatabase`.`multipartquestion`(`idquestion`,`questiontitle`,`question`,`questioncomponentids`, `pointsworth`) VALUES "
					+ "('"+idquestion+"','"+request.getParameter("questiontitle")+"','"+request.getParameter("questiontext")+"','','0');";
			System.out.println(query);
			st.execute(query);
			request.setAttribute("idquestion", idquestion);
			RequestDispatcher rd = request.getRequestDispatcher("QuestionzEditmultipart.jsp");
			rd.forward(request, response);
			return;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//response.sendRedirect("TestLayout.jsp");
		response.sendRedirect("TestLayout.jsp");
	}

}
