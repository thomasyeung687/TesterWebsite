package adminServlets;

import java.io.IOException;
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
		if(profid != null) {
			HttpSession session = request.getSession();
					
		}
	}
}
