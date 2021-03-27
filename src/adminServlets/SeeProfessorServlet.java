package adminServlets;

import java.io.IOException;
import java.util.HashMap;

import com.testersite.model.Professor;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SeeProfessorServlet
 */
@WebServlet("/SeeProfessorServlet")
public class SeeProfessorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String profid = request.getParameter("profid");
		String profidradio = request.getParameter("profidradio");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		HashMap<String, Professor> professorsMap = (HashMap<String, Professor>) session.getAttribute("professorsMap");
		System.out.println(action);
		if(action==null) {
			session.setAttribute("professorObj", professorsMap.get(profid));
			RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
			rd.forward(request, response);
			return;
		}
		if(action.equals("Add New Professor")) {
			RequestDispatcher rd = request.getRequestDispatcher("AdminAddProfessorPage.jsp");
			rd.forward(request, response);
			return;
		}
	}
}
