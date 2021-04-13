package adminServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.model.TesterClass;

/**
 * Servlet implementation class AdminEditClassServlet
 */
@WebServlet("/AdminEditClassServlet")
public class AdminEditClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session= request.getSession();
		TesterClass classObj = (TesterClass) session.getAttribute("classObj");
		String courseprefix = request.getParameter("courseprefix");
		String coursenumber = request.getParameter("coursenumber");
		String coursename = request.getParameter("coursename");
		String datestart = request.getParameter("datestart");
		String dateend = request.getParameter("dateend");
		String semester = request.getParameter("semester");
		try {
			TesterClass.editTesterClass(classObj.getIdclass(), courseprefix, coursenumber, coursename, datestart, dateend, semester);
			session.setAttribute("classObj", new TesterClass(classObj.getIdclass())); //retrieving new updated testerclass
			response.sendRedirect("AdminSeeClass.jsp");
		} catch (Exception e) {
			System.out.println("AdminEditClassServlet"+e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("AdminClassEdit.jsp");
			session.setAttribute("EditClassError", "e.getMessage()");
			rd.forward(request, response);
		}
	}

}
