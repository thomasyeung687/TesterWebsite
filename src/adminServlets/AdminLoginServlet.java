package adminServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class AdminLoginServlet
 */
@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sql = "SELECT * FROM testersitedatabase.adminprofiles WHERE username = ? AND password = ?";
		HttpSession session = request.getSession();
		try {
			Connection connection = DBConnection.getDBConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rset = ps.executeQuery();
			if(rset.next()) {
				session.setAttribute("username", username);
				session.setAttribute("idadminprofiles", rset.getString("idadminprofiles"));
				System.out.print(session.getAttribute("username")+" "+session.getAttribute("idadminprofiles"));
				response.sendRedirect("AdminOptions.jsp");
			}else {
				RequestDispatcher rd = request.getRequestDispatcher("LoginAdmin.jsp");
				System.out.println("invalid credentials");
				request.setAttribute("error", "Invalid credentials");
				rd.forward(request, response);
			}
		}catch (Exception e) {
			System.out.println(e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("LoginAdmin.jsp");
			request.setAttribute("error", e.getMessage());
			rd.forward(request, response);
		}
	}
}
