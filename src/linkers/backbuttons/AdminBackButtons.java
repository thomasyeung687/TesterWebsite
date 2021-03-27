package linkers.backbuttons;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminBackButtons
 */
@WebServlet("/AdminBackButtons")
public class AdminBackButtons extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageName = request.getParameter("pageName");
		RequestDispatcher rd = request.getRequestDispatcher("AdminOptions.jsp");
		if(pageName.equals("AdminSeeProfessorPage")|| pageName.equals("AdminAddProfessorPage")) {
			rd = request.getRequestDispatcher("AManageProfessors.jsp");
		}else if(pageName.equals("AManageProfessors")) {
			rd = request.getRequestDispatcher("AdminOptions.jsp");
		}else if(pageName.equals("AdminSeeClass")) {
			rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
		}
		rd.forward(request, response);
	}
}
