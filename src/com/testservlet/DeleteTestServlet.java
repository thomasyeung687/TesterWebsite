package com.testservlet;

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

import com.testersite.dao.DBConnection;

/**
 * Servlet implementation class DeleteTestServlet
 */
@WebServlet("/DeleteTestServlet")
public class DeleteTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String testid = request.getParameter("idtest");
		//System.out.println(testid);
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			st.execute("DELETE FROM testersitedatabase.testprofiles where idtest='"+testid+"';");
			st.execute("DELETE FROM testersitedatabase.testdns where idtest='"+testid+"';");
			ResultSet rSet = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable where idtest='"+testid+"';");
			while(rSet.next()) {
				String table = rSet.getString("tablename");
				String idquestion = rSet.getString("idquestion");
				System.out.println("DELETE FROM "+table+" where idquestion='"+idquestion+"';");
				st1.execute("DELETE FROM "+table+" where idquestion='"+idquestion+"';");
			}
			st.execute("DELETE FROM questionsdatabase.allquestiontable where idtest='"+testid+"';");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("ClassOptions.jsp");
		rd.forward(request, response);
	}
}
