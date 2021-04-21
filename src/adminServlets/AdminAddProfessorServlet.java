package adminServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;
import com.testersite.model.Professor;

/**
 * Servlet implementation class AdminAddProfessorServlet
 */
@WebServlet("/AdminAddProfessorServlet")
public class AdminAddProfessorServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		HttpSession session =  request.getSession();
		String password = request.getParameter("password1");
		String adminid = (String) session.getAttribute("idadminprofiles");
		if(!password.equals(request.getParameter("password2"))){
			session.setAttribute("error", "pasword mismatch!");
			RequestDispatcher rd = request.getRequestDispatcher("AdminAddProfessor.jsp");
			rd.forward(request, response);
		}
		try {
			String id = Professor.addNewProfessor(username, password, email, name, adminid);//method checks if username exists in db already
			System.out.print(id);
			Professor prof = new Professor(id);
			System.out.print("new prof: "+prof.toString());
			session.setAttribute("professorObj", prof);
			RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
			rd.forward(request, response);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			session.setAttribute("error", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("AdminAddProfessor.jsp");
			rd.forward(request, response);
		}
	}
}
