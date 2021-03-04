package StudentServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;
import com.testersite.model.*;

/**
 * Servlet implementation class StartTestServlet
 */
@WebServlet("/StartTestServlet")
public class StartTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = DBConnection.getDBConnection();
		HttpSession session = request.getSession();
		Test thisTest = (Test)session.getAttribute("thistest");
		String idTest = thisTest.getTestId();
		String studentid = ((String) session.getAttribute("idstudentprofiles")).trim();
		System.out.println("testid = "+thisTest.getTestId());
		System.out.println(idTest+" "+studentid);
		
		String action = request.getParameter("action");
		if(action.equals("start")) {
			if(thisTest.isShowQuestionOnebyOne()) {
				session.setAttribute("currentQuestion", 0);
				response.sendRedirect("TestPage1By1.jsp"); 
				return;
			}else {
				response.sendRedirect("TestPage.jsp"); 
				return;
			}
		}else if(action.equals("seeAttempt")) {
			thisTest = Test.getCompletedTestFromDB(studentid, idTest);
			session.setAttribute("thistest", thisTest);
			response.sendRedirect("SShowCompletedTestPage.jsp");
		}else if(action.equals("back")) {
			response.sendRedirect("STests.jsp");
		}
	}
}
