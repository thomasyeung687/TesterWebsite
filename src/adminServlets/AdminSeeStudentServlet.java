package adminServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminSeeStudentServlet
 */
@WebServlet("/AdminSeeStudentServlet")
public class AdminSeeStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String studid = request.getParameter("studid");
		HttpSession session = request.getSession();
		session.setAttribute("idstudentprofiles", studid);
		System.out.println(studid);
		RequestDispatcher rd = request.getRequestDispatcher("AdminStudentClasses.jsp");
		rd.forward(request, response);
	}
}
