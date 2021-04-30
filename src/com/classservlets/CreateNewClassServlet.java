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
import com.testersite.model.Professor;
import com.testersite.model.TesterClass;

import Random.RandomString;

/**
 * Servlet implementation class CreateNewClassServlet
 */
@WebServlet("/CreateNewClassServlet")
public class CreateNewClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		
		String courseprefix = request.getParameter("courseprefix");
		String coursenumber = request.getParameter("coursenumber");
		String coursename = request.getParameter("coursename");
		String semester = request.getParameter("semester");
		String datestart = request.getParameter("datestart");
		String dateend = request.getParameter("dateend");
		String profid = (String) session.getAttribute("idprofessorprofiles");
		String adminid = (String) session.getAttribute("idadminprofiles");
		String newClasscode = request.getParameter("coursecode");
		try {
			String newClassId = TesterClass.createNewClass(profid, courseprefix, coursenumber, coursename, datestart, dateend, semester, newClasscode, "0");//0 is passed in for adminid becuase this class is created on professor side.
			if(newClassId.equals("null")) {
				RequestDispatcher rd = request.getRequestDispatcher("CreateClass.jsp");
				session.setAttribute("createclasserror", "Couldnt get newClassId from TesterClass.createNewClass. received 'null'");
				rd.forward(request, response);
			}else {
				response.sendRedirect("YourClasses.jsp");
//				RequestDispatcher rd = request.getRequestDispatcher("AdminSeeClass.jsp");
//				rd.forward(request, response);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			// TODO: handle exception
			RequestDispatcher rd = request.getRequestDispatcher("CreateClass.jsp");
			session.setAttribute("createclasserror", "e.getMessage()");
			rd.forward(request, response);
		}
	}

}
