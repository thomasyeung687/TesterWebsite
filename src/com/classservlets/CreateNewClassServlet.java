package com.classservlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;

import Random.RandomString;

/**
 * Servlet implementation class CreateNewClassServlet
 */
@WebServlet("/CreateNewClassServlet")
public class CreateNewClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String username = (String) session.getAttribute("username"); //unique username which will be used to get idprofessorprofiles which is used as a foreign key in the classes table
		int idprofessorprofiles = 0;
		String courseprefix = request.getParameter("courseprefix");
		String coursenumber = request.getParameter("coursenumber");
		String coursename = request.getParameter("coursename");
		String datestart = request.getParameter("datestart");
		String dateend = request.getParameter("dateend");
		
		RandomString random = new RandomString(5,ThreadLocalRandom.current());
		String newClasscode = random.randString();
		try {
			Connection connection = DBConnection.getDBConnection();
			Statement st = connection.createStatement();
			ResultSet rset = st.executeQuery("SELECT idprofessorprofiles from testersitedatabase.professorprofiles where username = '"+username+"';"); //gets the id for the your account which we use as a foreign key in the allclasses table
			rset.next(); //goes to the result
			idprofessorprofiles = rset.getInt("idprofessorprofiles");
			String query = "INSERT INTO `testersitedatabase`.`allclasses`" + 
					"(`idprofessorprofiles`,`courseprefix`,`coursenumber`,`coursename`,`datestart`,`dateend`,`classcode`)" + 
					"VALUES" + 
					"('"+idprofessorprofiles+"','"+courseprefix+"','"+coursenumber+"','"+coursename+"','"+datestart+"','"+dateend+"','"+newClasscode+"');";
			st.execute(query); //adds new class to the database.
			System.out.println(query);
			System.out.println(username);
			//System.out.println(request.getParameter("datestart"));
			response.sendRedirect("YourClasses.jsp");
		}catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
			RequestDispatcher rd = request.getRequestDispatcher("CreateClass.jsp");
			request.setAttribute("createclasserror", "e.getMessage()");
			rd.forward(request, response);
		}
	}

}
