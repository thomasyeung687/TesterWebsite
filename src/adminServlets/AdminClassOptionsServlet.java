package adminServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminClassOptionsServlet
 */
@WebServlet("/AdminClassOptionsServlet")
public class AdminClassOptionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("Edit Class")) {
			RequestDispatcher rd = request.getRequestDispatcher("AdminClassEdit.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("action").equals("Manage Students")) {
			RequestDispatcher rd = request.getRequestDispatcher("AdminClassSeeStudents.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("action").equals("Manage Tests")) {
			RequestDispatcher rd = request.getRequestDispatcher("AdminClassTests.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("action").equals("Manage Announcements")) {
			RequestDispatcher rd = request.getRequestDispatcher("ClassManageAnnouncements.jsp");
			rd.forward(request, response);
		}else if(request.getParameter("action").equals("Delete")) {
			System.out.println("deletion implementation is left open!");
			RequestDispatcher rd = request.getRequestDispatcher("AdminSeeClass.jsp");
			rd.forward(request, response);
		}
	}
}
