package com.testservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.*;
import com.testersite.model.TesterClass;
/**
 * Servlet implementation class CreateTestServlet
 */

@WebServlet("/CreateTestServlet")
public class CreateTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String testname = request.getParameter("testname");
		String description = request.getParameter("description");
		String instruction = request.getParameter("instruction");
		
		String idclass;
		String from = (String) session.getAttribute("from");
		if(from.equals("admin")){
			TesterClass tclass = (TesterClass) session.getAttribute("classObj");
			idclass = tclass.getIdclass();
		}else{
			idclass = (String) session.getAttribute("classid");
		}
		try {
			Connection con = DBConnection.getDBConnection();
			Statement st = con.createStatement();
			String query = "INSERT INTO `testersitedatabase`.`testprofiles` (`testname`,`testdescription`,`testinstructions`) VALUES ('"+testname+"','"+description+"','"+instruction+"');";
			//System.out.println(query);
			st.execute(query); //creates new test in database
			ResultSet rset = st.executeQuery("SELECT * FROM testersitedatabase.testprofiles WHERE testname = '"+testname+"' AND testdescription = '"+description+"' AND testinstructions = '"+instruction+"';");
			rset.next();
			int testid = rset.getInt("idtest");
			query = "INSERT INTO `testersitedatabase`.`testdns`(`idtest`,`idclass`) VALUES ('"+testid+"','"+idclass+"');";
			System.out.println(query);
			//System.out.println("test id is "+testid);
			st.execute(query);
			
		}catch(Exception e) {
			System.out.println("Exception occured in createtestservlet!"+e.getMessage());
		}
		//System.out.print("Successfully added test");
		RequestDispatcher rd = from.equals("admin")? request.getRequestDispatcher("AdminClassTests.jsp") :request.getRequestDispatcher("ClassTests.jsp");
		rd.forward(request, response);
	}

}

