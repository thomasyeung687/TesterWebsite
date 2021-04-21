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

//import com.mysql.cj.Session;
import com.testersite.dao.DBConnection;
import com.testersite.model.Test;
import com.testersite.model.TestAttemptObject;

/**
 * Servlet implementation class TakeTestServlet
 */
@WebServlet("/TakeTestServlet")
public class TakeTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String idTest = request.getParameter("testid");
		String studentid = ((String) session.getAttribute("idstudentprofiles")).trim();
		System.out.println(idTest+" "+studentid);
		try {
			Test thistest = Test.getTestFromDB(idTest);
			TestAttemptObject tao = TestAttemptObject.getAttemptFromDB(studentid, idTest);
			if(tao != null) {
				thistest.addAttemptObject(tao);
			}
			session.setAttribute("thistest", thistest);//session attribute that contains all this tests information
		} catch (Exception e) {
			System.out.println("StudentServlets/TakeTestServlet.java "+e.getLocalizedMessage());
		}
		response.sendRedirect("SShowTest.jsp");
	}


}
