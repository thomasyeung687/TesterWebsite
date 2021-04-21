package com.loginregister;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;

import Random.RandomString;
import java.util.concurrent.*;
import java.util.regex.Matcher; 
import java.util.regex.Pattern; 
/**
 * Servlet implementation class ProfRegisterServlet
 */
@WebServlet("/ProfRegisterServlet")
public class ProfRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String emailregex = "^[a-zA-Z0-9_+&*-] + (?:\\\\.[a-zA-Z0-9_+&*-]\r\n" + 
		// "+ )*@(?:[a-zA-Z0-9-]+\\\\.) + [a-zA-Z]{2, 7}$";
		//Pattern pattern = Pattern.compile(emailregex);
		String email = request.getParameter("email");
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmemail = request.getParameter("confirmemail");
		String confirmpassword = request.getParameter("confirmpassword");
		
		RandomString random = new RandomString(5,ThreadLocalRandom.current());
		String newClasscode = random.randString();
		System.out.println(newClasscode);
		
		if(email.equals(confirmemail)) {
			if(password.equals(confirmpassword)) {
				//if(pattern.matches(regex, input)) {
					try {
						Connection connection = DBConnection.getDBConnection();
						Statement st = connection.createStatement();
						String query ="INSERT INTO testersitedatabase.professorprofiles (username, password, email, classcode, name) "
								+ "VALUES ('"+username+"','"+password+"','"+email+"', '"+newClasscode+"', '"+name+"')";
						st.execute(query);
						HttpSession session = request.getSession();
						session.setAttribute("username", username);
						session.setAttribute("professor", "yes");
						session.setAttribute("classcode", newClasscode); //getting classcode for test creation
						response.sendRedirect("CreatorOptions.jsp");// if there was no errors then it will send professor to login page.
					}catch (Exception e) {
						System.out.println(e.getMessage());
						response.sendRedirect("RegisterProfesor.jsp");//if there was an error, you will have to start over.
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
