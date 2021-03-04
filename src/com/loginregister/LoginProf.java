package com.loginregister;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginProf extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sql = "SELECT * FROM testersitedatabase.professorprofiles WHERE username = ? AND password = ?";
		HttpSession session = request.getSession();
		try {
			Connection connection = DBConnection.getDBConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rset = ps.executeQuery();
			if(rset.next()) {
				session.setAttribute("username", username);
				session.setAttribute("idprofessorprofiles", rset.getString("idprofessorprofiles"));
				session.setAttribute("classcode", rset.getObject("classcode")); //getting classcode from db and putting it as attribute for tests creation etc
				response.sendRedirect("CreatorOptions.jsp");
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("LoginProf.jsp");
				request.setAttribute("error", "Invalid credentials");
				rd.forward(request, response);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
//4:17 Servlet & JSP Tutorial | Full Course