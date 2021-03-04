package com.loginregister;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;
import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class LoginStudent
 */
@WebServlet("/LoginStudent")
public class LoginStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sql = "SELECT * FROM testersitedatabase.studentprofiles WHERE username = ? AND password = ?";
		HttpSession session = request.getSession();
		try {
			Connection connection = DBConnection.getDBConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rset = ps.executeQuery();
			if(rset.next()) {
				session.setAttribute("username", username);
				session.setAttribute("idstudentprofiles", rset.getString("idstudentprofiles"));
				session.setAttribute("studentname", rset.getString("name"));
				response.sendRedirect("HomeStudent.jsp");
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("LoginStudent.jsp");
				request.setAttribute("error", "Invalid credentials");
				rd.forward(request, response);
			}
		}catch (Exception e) {
			System.out.println("Error in LoginStudentServlet");
			System.out.println(e.getMessage());
		}
	}
}
