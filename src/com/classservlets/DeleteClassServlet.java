package com.classservlets;

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
 * Servlet implementation class DeleteClassServlet
 */
@WebServlet("/DeleteClassServlet")
public class DeleteClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classid = request.getParameter("classid");
		System.out.println(classid);
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();
			Statement st2 = con.createStatement();
			st.execute("DELETE FROM testersitedatabase.allclasses WHERE idclass='"+classid+"';");
			st.execute("DELETE FROM testersitedatabase.studenttoclass WHERE classcode="+classid+";"); 
			ResultSet rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.testdns where idclass = '"+classid+"';");
			while(rSet1.next()) {
				String testid = rSet1.getString("idtest");
				System.out.println(testid);
				st.execute("DELETE FROM testersitedatabase.testprofiles where idtest='"+testid+"';");
				st.execute("DELETE FROM testersitedatabase.testdns where idtest='"+testid+"';");
				ResultSet rSet = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable where idtest='"+testid+"';");
				while(rSet.next()) {
					String table = rSet.getString("tablename");
					String idquestion = rSet.getString("idquestion");
					System.out.println("DELETE FROM "+table+" where idquestion='"+idquestion+"';");
					st2.execute("DELETE FROM "+table+" where idquestion='"+idquestion+"';");
				}
				st.execute("DELETE FROM questionsdatabase.allquestiontable where idtest='"+testid+"';");
			}
			st1.execute("DELETE FROM testersitedatabase.testdns where idclass = '"+classid+"';");
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		RequestDispatcher rd = request.getRequestDispatcher("YourClasses.jsp");
		rd.forward(request, response);
	}
}
