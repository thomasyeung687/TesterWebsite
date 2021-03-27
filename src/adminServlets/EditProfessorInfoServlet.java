package adminServlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.model.Professor;

/**
 * Servlet implementation class EditProfessorInfoServlet
 */
@WebServlet("/EditProfessorInfoServlet")
public class EditProfessorInfoServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		Professor prof = (Professor) session.getAttribute("professorObj");
		session.removeAttribute("message");
		session.removeAttribute("error");
		if(action.equals("Edit Info")) {
			String name = request.getParameter("name");
			String username = request.getParameter("username");
			String email = request.getParameter("email");
			prof.setName(name);
			prof.setEmail(email);
			prof.setUsername(username);
			try {
				prof.updateInfoChangesToDB();
				session.setAttribute("professorObj", new Professor(prof.getIdprofessorprofiles()));
				session.setAttribute("message", "Successfully updated professor information.");
				RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute("error",e.getMessage());
				RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
				rd.forward(request, response);
			}
		}else if(action.equals("Change Password")) {
			String password = request.getParameter("password1");
			if(!password.equals(request.getParameter("password2"))){
				session.setAttribute("error", "pasword mismatch!");
				RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
				rd.forward(request, response);
			}
			//here you can add additional checks like passworkd strength.
			prof.setPassword(password);
			try {
				prof.updateInfoChangesToDB();
				session.setAttribute("professorObj", new Professor(prof.getIdprofessorprofiles()));
				session.setAttribute("message", "Successfully updated professor password.");
				RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				session.setAttribute("error",e.getMessage());
				RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
				rd.forward(request, response);
			}
		}else {
			System.out.println("Unknown action given to EditProfessorInfoServlet");
			session.setAttribute("error","Unknown action given to EditProfessorInfoServlet");
			RequestDispatcher rd = request.getRequestDispatcher("AdminSeeProfessorPage.jsp");
			rd.forward(request, response);
		}
	}

}
