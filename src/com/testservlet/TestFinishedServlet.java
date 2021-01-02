package com.testservlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.testersite.dao.DBConnection;
import com.testersite.model.Question;

/**
 * Servlet implementation class TestFinishedServlet
 */
@WebServlet("/TestFinishedServlet")
public class TestFinishedServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/*
	 * protected void doPost(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException { HttpSession session =
	 * request.getSession(); ArrayList<Question> test =
	 * (ArrayList<Question>)session.getAttribute("test"); String
	 * studenttestrecordidentifier = (String)
	 * session.getAttribute("studenttestrecordidentifier"); int numofcorrectans = 0;
	 * double score =0; Connection con = DBConnection.getDBConnection(); try {
	 * Statement st = con.createStatement(); String query =
	 * "  CREATE TABLE `db"+session.getAttribute("testname")+"`.`"+
	 * studenttestrecordidentifier+"` (\r\n" +
	 * "  `idquestionsAndAns` INT NOT NULL AUTO_INCREMENT,\r\n" +
	 * "  `question` VARCHAR(300) NULL,\r\n" + "  `ans1` VARCHAR(45) NULL,\r\n" +
	 * "  `ans2` VARCHAR(45) NULL,\r\n" + "  `ans3` VARCHAR(45) NULL,\r\n" +
	 * "  `ans4` VARCHAR(45) NULL,\r\n" + "  `correctans` VARCHAR(45) NULL,\r\n" +
	 * "  `pickedans` VARCHAR(45) NULL,\r\n" +
	 * "  PRIMARY KEY (`idquestionsAndAns`),\r\n" +
	 * "  UNIQUE INDEX `idquestionsAndAns_UNIQUE` (`idquestionsAndAns` ASC) VISIBLE);\r\n"
	 * ; st.executeUpdate(query);//creates table in test DB for(int questionnum = 0;
	 * questionnum < test.size(); questionnum++) {
	 * if(test.get(questionnum).getPickedString().equals(test.get(questionnum).
	 * getCorrecString())) {// if picked ans = correct ans then increment
	 * numofcorrectans numofcorrectans++; } String question =
	 * test.get(questionnum).getQuestionString(); String ans1 =
	 * test.get(questionnum).getAns1(); String ans2 =
	 * test.get(questionnum).getAns2(); String ans3 =
	 * test.get(questionnum).getAns3(); String ans4 =
	 * test.get(questionnum).getAns4(); String correctans =
	 * test.get(questionnum).getCorrecString(); String pickedans =
	 * test.get(questionnum).getPickedString();
	 * 
	 * String queryques = "INSERT INTO `db"+session.getAttribute("testname")+"`.`"+
	 * studenttestrecordidentifier+"` (question, ans1, ans2, ans3, ans4, correctans,pickedans)"
	 * + "VALUES ('"+question+"','"+ans1+"','"+ans2+"','"+ans3+"','"+ans4+"','"+
	 * correctans+"','"+pickedans+"')"; System.out.println(queryques);
	 * st.executeUpdate(queryques); }
	 * 
	 * score = ((double)numofcorrectans*100)/((double)test.size()); score =
	 * Math.round(score);
	 * System.out.println("num correct="+numofcorrectans+" / testsize="+test.size())
	 * ; System.out.println("="+score); session.setAttribute("score", score);
	 * request.getRequestDispatcher("FinishedTest.jsp").forward(request, response);
	 * }catch (Exception e) {
	 * System.out.println("Exception occured in TestFinishedServlet"); } }
	 */
}
