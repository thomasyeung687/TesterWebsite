package StudentServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.Session;
import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class joinClassServlet
 */
@WebServlet("/joinClassServlet")
public class joinClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Connection connection = DBConnection.getDBConnection();
		String studentid = ((String) session.getAttribute("idstudentprofiles")).trim();
		session.removeAttribute("joinclassSuccess");
		session.removeAttribute("joinclasserror");
		try {
			Statement st = connection.createStatement();
			String query = "SELECT * FROM testersitedatabase.allclasses WHERE classcode = '"+request.getParameter("coursecode")+"';";
			ResultSet rset = st.executeQuery(query);
			if(rset.next()) {
				String classid = rset.getString("idclass");
				rset = st.executeQuery("SELECT * FROM testersitedatabase.studenttoclass WHERE idstudentprofiles = '"+studentid+"' and classid = '"+classid+"';"); //studentid attached in login servlet.
				if(rset.next()) {
					//the student is already in the class.
					//System.out.println("student is in class");
					session.setAttribute("joinclasserror", "You are already in this class!");
					response.sendRedirect("SClasses.jsp");
					return;
				}else {
					//add a new row in studenttoclass
					//System.out.println("adding student "+studentid+" to class "+ classid);
					st.execute("INSERT INTO `testersitedatabase`.`studenttoclass` (`idstudentprofiles`,`classid`) VALUES ('"+studentid+"', '"+classid+"');");
					session.setAttribute("joinclassSuccess", "Joined class sucessfully!");
					response.sendRedirect("SClasses.jsp");
					return;
				}
			}else {
				//classcode does not exist.
				session.setAttribute("joinclasserror", "Invalid Class Code!");
				response.sendRedirect("SClasses.jsp");
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.setAttribute("joinclasserror", "SQL error!");
			response.sendRedirect("SClasses.jsp");
			return;
		}
	}

}
