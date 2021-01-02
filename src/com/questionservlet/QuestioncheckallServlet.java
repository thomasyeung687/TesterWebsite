package com.questionservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class QuestioncheckallServlet
 */
@WebServlet("/QuestioncheckallServlet")
public class QuestioncheckallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("submit")||request.getParameter("action").equals("another")){
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				ResultSet rSet;
				String answerString="";
				String correctString="";
				for(int i = 0; i < Integer.parseInt(request.getParameter("amtofquestions").trim()); i++) {
					String ans = request.getParameter("ans"+i);
					//System.out.println(ans);
					//System.out.println(request.getParameter("check"+i));
					if(request.getParameter("check"+i) != null) {
						correctString += ans+"~"; //if not null meaning it was checked, then we add it to the correct string
					}
					if(ans.contains("~")) {
						System.out.println("contains delimiter");
						request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
					}else {
						answerString += ans+"~";
					}
				}
				//System.out.println(answerString);
				//System.out.println(correctString);
				if(correctString.equals("")) {
					request.setAttribute("error", "No checkbox was checked. Please check atleast one.");
					request.getRequestDispatcher("Questioncheckall.jsp").forward(request, response);
				}
				String query ="INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('"+request.getParameter("idtest")+"', 'questionsdatabase.checkall');";
				st.execute(query);
				rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
				String idquestion = null;
				if(rSet.next()) {
					idquestion = rSet.getString("LAST_INSERT_ID()");
				}else {
					System.out.println("Failed to get LAST_INSERTED_ID");
					request.setAttribute("error", "Failed to get LAST_INSERTED_ID");
					request.getRequestDispatcher("Questioncheckall.jsp").forward(request, response);
				}
				query = "INSERT INTO `questionsdatabase`.`checkall`(`idquestion`,`questiontitle`,`question`,`answerstring`, `correctstring`, `pointsworth`) VALUES "
						+ "('"+idquestion+"','"+request.getParameter("questiontitle")+"','"+request.getParameter("questiontext")+"','"+answerString+"','"+correctString+"','"+request.getParameter("pointsworth")+"');";
				//System.out.println(query);
				st.execute(query);
			}catch (SQLException e) {
				e.printStackTrace();
			}
			if(request.getParameter("action").equals("another")) {
				request.getRequestDispatcher("Questioncheckall.jsp").forward(request, response);
			}else {
				//request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
				response.sendRedirect("TestLayout.jsp");
			}
		}else if(request.getParameter("action").equals("back")) {
			//request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
			response.sendRedirect("TestLayout.jsp");
		}
	}

}
