package com.testservlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * Servlet implementation class UpdatetestPreferencesServlet
 */
@WebServlet("/UpdatetestPreferencesServlet")
public class UpdatetestPreferencesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String testid = (String)session.getAttribute("idtest");
		System.out.println("testid: "+testid);
		String testName = request.getParameter("updatedtestname");
		String testDescription = request.getParameter("updatedtestdescription");
		String testInstructions = request.getParameter("updatedtestinstructions");
		String testDateStart= request.getParameter("updatedtestdatestart");
		String displaystart=request.getParameter("updateddisplaystart");;
		String displayend=request.getParameter("updateddisplayend");;
		String testDateEnd= request.getParameter("updatedtestdateend");
		String availibility = request.getParameter("updatedavailability"); 
		String forcedCompletion = request.getParameter("updatedforcecomplete"); 
		String allowBackButton = request.getParameter("updatedallowbackbutton");
		String scrambleTest = request.getParameter("updatedscrambletest");
		String showQuestionOnebyOne = request.getParameter("updatedshowquestions1b1");//default is false so it will show the whole test as default.
		String timelimit = request.getParameter("updatedtimelimit"); 
		String amtOfAttempts = request.getParameter("updatedamountofattempts");
		//System.out.println("Testname: "+testName+"\n testDescription: "+testDescription+"\n testInstructions: "+testInstructions+"\n testDateStart: "+testDateStart+"\n displaystart: "+displaystart+"\n displayend: "+displayend+"\n testDateEnd: "+testDateEnd+"\n availibility: "+availibility+"\n forcedCompletion: "+forcedCompletion+"\n allowBackButton: "+allowBackButton+"\n scrambleTest: "+scrambleTest+"\n showQuestionOnebyOne: "+showQuestionOnebyOne+"\n timelimit: "+timelimit+"\n amtOfAttempts: "+amtOfAttempts);
		Connection connection = DBConnection.getDBConnection();
		try {
			Statement st = connection.createStatement();
			String sql = "UPDATE testersitedatabase.testprofiles SET testname = '"+testName+"',testdescription = '"+testDescription+"',testinstructions = '"+testInstructions+"',testdatestart = '"+testDateStart+"',displaystart = '"+displaystart+"',displayend = '"+displayend+"',testdateend = '"+testDateEnd+"',"
					+ "availibility = b'"+availibility+"',forcedCompletion = b'"+forcedCompletion+"',allowbackbutton = b'"+allowBackButton+"',scrambletest = b'"+scrambleTest+"',showquestiononebyone = b'"+showQuestionOnebyOne+"',timelimit = '"+timelimit+"',amtofattempts = '"+amtOfAttempts+"' WHERE idtest = "+testid+";";
			System.out.println(sql);
			st.executeUpdate(sql);
			RequestDispatcher rd = request.getRequestDispatcher("TestPreferences.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			request.setAttribute("error", e.getLocalizedMessage());
			RequestDispatcher rd = request.getRequestDispatcher("TestPreferences.jsp");
			rd.forward(request, response);
		}
		//out.println(request.getParameter("updatedtestname")+" \n"+request.getParameter("updatedtestdescription")+" \n"+request.getParameter("updatedtestinstructions")+" \n"+request.getParameter("updatedtestdatestart")+" \n"+request.getParameter("updatedtestdateend")+" \n"+request.getParameter("updatedavailability")+" \n"+request.getParameter("updatedforcecomplete")+" \n"+request.getParameter("updatedtimelimit")+" \n"+request.getParameter("updatedamountofattempts"));
	}
}
