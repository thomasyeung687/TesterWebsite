package adminServlets;

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

import com.testersite.dao.DBConnection;
import com.testersite.model.ClassObject;

/**
 * Servlet implementation class AdminSGrades
 */
@WebServlet("/AdminSGradesServlet")
public class AdminSGradesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//add a classObject to the session so pages after it can use it to display coursename and prof id and such
//		
//		String classid = request.getParameter("classid");
//		System.out.println("hello"+classid);
//		HttpSession session = request.getSession();
//		
//		
//		Connection connection = DBConnection.getDBConnection();
//    	ClassObject thisclass = new ClassObject();
//    	try { //getting information of the class student is accessing from db and putting it into an object to be passed via session
//			Statement st = connection.createStatement();
//			ResultSet rSet;
//			rSet = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE idclass = '"+classid+"'"); //getting the row in allclasses in db (basically the class information)
//			rSet.next();
//			
//			Statement st1 = connection.createStatement(); 
//			ResultSet rSet1;
//			
//			String professorid = rSet.getString("idprofessorprofiles");
//			rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.professorprofiles WHERE idprofessorprofiles = '"+professorid+"';");
//			rSet1.next();
//			
//			String coursename = rSet.getString("courseprefix")+rSet.getString("coursenumber")+"|"+rSet.getString("coursename");
//			String professorname = rSet1.getString("name");
//			
//			
//			thisclass.setClassid(classid);
//			thisclass.setCoursename(coursename);
//			thisclass.setInstructorName(professorname);
//			thisclass.setdateStart(rSet.getString("datestart"));
//			thisclass.setdateEnd(rSet.getString("dateend"));
//			
//    	}catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("SShowClass.jsp SQL ERROR");
//		}catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			System.out.println("ShowClassServletStudent.jsp "+e.getCause()+" ERROR");
//		}
//    	session.setAttribute("classid", classid);
//    	session.setAttribute("thisclass", thisclass); 
//    	
//		response.sendRedirect("SShowClass.jsp");
		
		String classid = request.getParameter("classid");
		HttpSession session = request.getSession();
		session.setAttribute("classid", classid);
		RequestDispatcher rd = request.getRequestDispatcher("AdminSGrades.jsp");
		rd.forward(request, response);
	}

}
