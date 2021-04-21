package linkers.backbuttons;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ProfessorBackButtons
 */
@WebServlet("/ProfessorBackButtons")
public class ProfessorBackButtons extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageName = request.getParameter("pageName");
		RequestDispatcher rd = request.getRequestDispatcher("AdminOptions.jsp");
		if(pageName.equals("CreateTest")) {
			rd = request.getRequestDispatcher("ClassTests.jsp");
		}
//		}else if(pageName.equals("AManageProfessors")) {
//			rd = request.getRequestDispatcher("AdminOptions.jsp");
//		}else if(pageName.equals("AdminSeeClass")) {
//			rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
//		}else if(pageName.equals("AdminClassEdit")) {
//			rd = request.getRequestDispatcher("AdminSeeClass.jsp");
//		}else if(pageName.equals("AdminClassSeeStudents.jsp")){
//			rd = request.getRequestDispatcher("AdminClassTests.jsp");
//		}else if(pageName.equals("AdminShowCompletedTestPage.jsp")){
//			rd = request.getRequestDispatcher("AdminClassTests.jsp");
//		}
		rd.forward(request, response);
	}
}
