package StudentServlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;
import com.testersite.model.Test;

/**
 * Servlet implementation class TakeTestServlet
 */
@WebServlet("/TakeTestServlet")
public class TakeTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idTest = request.getParameter("testid");
		System.out.println(idTest);
		try {
			Connection connection = DBConnection.getDBConnection();
			Statement st = connection.createStatement();
			ResultSet resultSet = st.executeQuery("SELECT * FROM testersitedatabase.testprofiles WHERE idtest = '"+idTest+"';");
			HttpSession session = request.getSession();
			if(resultSet.next()) {
				Test thistest = new Test();
				thistest.setTestId(resultSet.getString("idtest"));
				thistest.setTestName(resultSet.getString("testname"));
				thistest.setTestDescription(resultSet.getString("testdescription"));
				thistest.setTestInstructions(resultSet.getString("testinstructions"));
				thistest.setTestDateStart("testdatestart");
				thistest.settestDateEnd(resultSet.getString("testdateend"));
				thistest.setDisplaystart(resultSet.getString("displaystart")); //display start and end can be used later on with conjunction with a function in test that determins whether test should be displayed or not.
				thistest.setDisplaystart(resultSet.getString("displayend"));
				
				thistest.setForcedCompletion(resultSet.getBoolean("forcedCompletion"));
				thistest.setAllowBackButton(resultSet.getBoolean("allowbackbutton"));
				thistest.setScrambleTest(resultSet.getBoolean("scrambletest"));
				thistest.setShowQuestionOnebyOne(resultSet.getBoolean("showquestiononebyone"));
				
				thistest.setTimelimit(resultSet.getInt("timelimit"));
				thistest.setAmtOfAttempts(resultSet.getInt("amtofattempts"));
				
				session.setAttribute("thistest", thistest);//session attribute that contains all this tests information
			}else {
				System.out.print("Test Unavailable");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		response.sendRedirect("SShowTest.jsp");
	}


}
