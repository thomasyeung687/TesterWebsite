package com.testservlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.testersite.model.Test;

/**
 * Servlet implementation class ShowExistingTestServlet
 */
@WebServlet("/ShowExistingTestServlet")
public class ShowExistingTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = DBConnection.getDBConnection();
		//System.out.println(request.getParameter("idtest"));
		String idtest = request.getParameter("idtest"); //idtest key in testprofiles
		HttpSession session = request.getSession();
		session.setAttribute("idtest", idtest);
		request.getRequestDispatcher("TestLayout.jsp").forward(request, response);
	}
}
