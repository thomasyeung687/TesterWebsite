package StudentServlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowClassServletStudent
 */
@WebServlet("/ShowClassServletStudent")
public class ShowClassServletStudent extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classid = request.getParameter("classid");
		System.out.println("hello"+classid);
		HttpSession session = request.getSession();
		session.setAttribute("classid", classid);
		response.sendRedirect("SShowClass.jsp");
	}
}
