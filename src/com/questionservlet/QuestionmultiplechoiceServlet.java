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
 * Servlet implementation class QuestionmultiplechoiceServlet
 */
@WebServlet("/QuestionmultiplechoiceServlet")
public class QuestionmultiplechoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action").equals("submit")||request.getParameter("action").equals("another")){
			try {
				Connection con = DBConnection.getDBConnection();
				Statement st = con.createStatement();
				ResultSet rSet;
				String answerString="";
				for(int i = 0; i < Integer.parseInt(request.getParameter("amtofquestions").trim()); i++) {
					String ans = request.getParameter("ans"+i);
					//System.out.println(ans);
					if(ans.contains("~")) {
						System.out.println("contains delimiter");
						request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
					}else {
						answerString += ans+"~";
					}
				}
				System.out.println(answerString);
				//creating a new row in allquestiontable
				String query ="INSERT INTO questionsdatabase.allquestiontable(`idtest`, `tablename`) VALUES ('"+request.getParameter("idtest")+"', 'questionsdatabase.multiplechoice');";
				st.execute(query);
				rSet = st.executeQuery("SELECT LAST_INSERT_ID();");
				String idquestion = null;
				if(rSet.next()) {
					idquestion = rSet.getString("LAST_INSERT_ID()");
				}else {
					System.out.println("Failed to get LAST_INSERTED_ID");
				}
				query = "INSERT INTO `questionsdatabase`.`multiplechoice`(`idquestion`,`questiontitle`,`question`,`answerstring`, `correctanswer`, `pointsworth`) VALUES "
						+ "('"+idquestion+"','"+request.getParameter("questiontitle")+"','"+request.getParameter("questiontext")+"','"+answerString+"','"+request.getParameter(request.getParameter("correctans"))+"','"+request.getParameter("pointsworth")+"');";
				System.out.println(query);
				st.execute(query);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(request.getParameter("action").equals("another")) {
				request.getRequestDispatcher("Questionmultiplechoice.jsp").forward(request, response);
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
