
package com.testservlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.testersite.dao.DBConnection;
import com.testersite.model.Question;

/**
 * Servlet implementation class TestTakerInfoHandler
 */
@WebServlet("/TestTakerInfoHandler")
public class TestTakerInfoHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { Connection con =
	 * DBConnection.getDBConnection(); HttpSession session = request.getSession();
	 * ArrayList<Question> test = new ArrayList<Question>(); try { Statement st =
	 * con.createStatement(); String studenttestrecordidentifier =
	 * request.getParameter("testtakername").toLowerCase().replace(" ",
	 * ".")+request.getParameter("testtakerid").replace(" ", ""); ResultSet rSet =
	 * st.executeQuery("select*from tester."+session.getAttribute("testname"));
	 * while(rSet.next()) { Question newque = new Question();
	 * newque.setQuestionString(rSet.getString("question"));
	 * newque.setAns1(rSet.getString("ans1"));
	 * newque.setAns2(rSet.getString("ans2"));
	 * newque.setAns3(rSet.getString("ans3"));
	 * newque.setAns4(rSet.getString("ans4"));
	 * newque.setCorrecString(rSet.getString("correctans")); test.add(newque); }
	 * session.setAttribute("test", test); session.setAttribute("currentnum", 0);
	 * session.setAttribute("studenttestrecordidentifier",
	 * studenttestrecordidentifier); }catch (Exception e) {
	 * System.out.println("Exception occured in testtakerinfohandler"); }
	 * System.out.println(session.getAttribute("testname")+" "+request.getParameter(
	 * "testtakername")+" "+request.getParameter("testtakerid"));
	 * request.getRequestDispatcher("TestPage.jsp").forward(request, response); }
	 */

}
