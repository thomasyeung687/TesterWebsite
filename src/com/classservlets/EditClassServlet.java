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
@WebServlet("/EditClassServlet")
public class EditClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		String courseprefix = request.getParameter("courseprefix");
		String coursenumber = request.getParameter("coursenumber");
		String coursename = request.getParameter("coursename");
		String datestart = request.getParameter("datestart");
		String dateend = request.getParameter("dateend");
		
		try {
			Connection connection = DBConnection.getDBConnection();
			Statement st = connection.createStatement();
			String query = "UPDATE testersitedatabase.allclasses SET courseprefix = '"+courseprefix+"', coursenumber = '"+coursenumber+"', coursename = '"+coursename+"', datestart = '"+datestart+"', dateend = '"+dateend+"' WHERE idclass = '"+session.getAttribute("classid")+"';"; 
			st.executeUpdate(query); //adds new class to the database.
			System.out.println(query);
			//System.out.println(request.getParameter("datestart"));
			response.sendRedirect("ClassOptions.jsp");
		}catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
			RequestDispatcher rd = request.getRequestDispatcher("ClassEdit.jsp");
			request.setAttribute("createclasserror", e.getMessage());
			rd.forward(request, response);
		}
	}

}