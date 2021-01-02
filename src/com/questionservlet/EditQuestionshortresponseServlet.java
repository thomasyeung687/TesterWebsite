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
 * Servlet implementation class EditQuestionshortresponseServlet
 */
@WebServlet("/EditQuestionshortresponseServlet")
public class EditQuestionshortresponseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Connection con = DBConnection.getDBConnection();
			Statement st = con.createStatement();
			String idquestion = request.getParameter("idquestion");
			
			String query = "UPDATE questionsdatabase.shortanswer SET questiontitle = '"+request.getParameter("questiontitle")+"', question = '"+request.getParameter("questiontext")+"', pointsworth = '"+request.getParameter("pointsworth")+"' WHERE idquestion = '"+idquestion+"';"; 
			System.out.println(query);
			st.execute(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.sendRedirect("TestLayout.jsp");
	}

}
