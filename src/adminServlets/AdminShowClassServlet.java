package adminServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.model.TesterClass;

/**
 * Servlet implementation class AdminShowClassServlet
 */
@WebServlet("/AdminShowClassServlet")
public class AdminShowClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String classid = request.getParameter("classid");
		try {
			session.setAttribute("classObj", new TesterClass(classid));
			response.sendRedirect("AdminSeeClass.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("error", e.getMessage());
			response.sendRedirect("AdminSeeProfessorPage.jsp");
		}
	}

}
