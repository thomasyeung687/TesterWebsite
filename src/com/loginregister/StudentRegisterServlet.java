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

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class StudentRegisterServlet
 */
@WebServlet("/StudentRegisterServlet")
public class StudentRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classcode = request.getParameter("classcode");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String confirmemail = request.getParameter("confirmemail");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmpassword = request.getParameter("confirmpassword");
		String ccQuery = "SELECT * FROM testersitedatabase.professorprofiles WHERE classcode = '"+classcode+"'";
		
		if(email.equals(confirmemail)) {
			if(password.equals(confirmpassword)) {
				//if(pattern.matches(regex, input)) {
					try {
						Connection connection = DBConnection.getDBConnection();
						Statement st = connection.createStatement();
						ResultSet rSet = st.executeQuery(ccQuery);
						if(rSet.next()) {
							String insertquery ="INSERT INTO testersitedatabase.studentprofiles (name,username, password, email, classcode) "
									+ "VALUES ('"+name+"','"+username+"','"+password+"','"+email+"','"+classcode+"')";
							st.execute(insertquery);
							HttpSession session = request.getSession();
							session.setAttribute("username", name);
							session.setAttribute("student", "yes");
							response.sendRedirect("HomeStudent.jsp");// if there was no errors then it will send student to homepage.
						}else {
							RequestDispatcher rd = request.getRequestDispatcher("RegisterStudent.jsp");
							request.setAttribute("error", "Classcode does not exist");
							rd.forward(request, response);
						}
					}catch (Exception e) {
						System.out.println(e.getMessage());
						response.sendRedirect("RegisterStudent.jsp");
					}
				//}else {
				//	System.out.println("invalid email!");
				//}
			}else {
				System.out.println("Passwords not the same!");
			}
		}else {
			System.out.println("Emails not the same!");
		}
	}

}
