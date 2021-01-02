package com.questionservlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QuestionfillinmultipleblanknextServlet
 */
@WebServlet("/MultipartAddQuestionfillinmultipleblanknextServlet")
public class MultipartAddQuestionfillinmultipleblanknextServlet extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String questiontitle = request.getParameter("questiontitle");
		String questiontext = request.getParameter("questiontext");
		String ptsworth = request.getParameter("pointsworth");
		String casesensistive = request.getParameter("casesensistive");
		String idmultiquestion = request.getParameter("idmultiquestion");
		if(casesensistive == null) { //now we can insert casesensitive into sql
			casesensistive = "0";
		}else {
			casesensistive = "1";
		}
		String allowPartialCredit = request.getParameter("allowPartialCredit");
		if(allowPartialCredit == null) { //now we can insert allowPartialCredit into sql
			allowPartialCredit = "0";
		}else {
			allowPartialCredit = "1";
		}
		//System.out.println("id="+idtest+" "+questiontitle+" "+questiontext+" "+ptsworth+" "+casesensistive+" "+allowPartialCredit);
		
		ArrayList<String> blank = new ArrayList<String>(); 
		int start = 0;
		int amtofblanks = 0;
		while(questiontext.indexOf("[",start)> -1) {
			//System.out.println(start);
			int startIndex = questiontext.indexOf("[", start)+1;
			int endIndex = questiontext.indexOf("]", start);
			//System.out.println(startIndex);
			//System.out.println(endIndex);
			String blankString = questiontext.substring(startIndex, endIndex); //this will contain x for [x] in question.
			if(blankString.equals("")) {
				//System.out.println("blank encountered:"+blankString+"| ");
				request.setAttribute("questiontext", questiontext);
				request.setAttribute("questiontitle", questiontitle);
				request.setAttribute("error", "Blank indicator does not contain variable. [] should be [x] where x can be anything");
				request.getRequestDispatcher("Questionfillinmultipleblank.jsp").forward(request, response);
			}else if(blankString.contains(",")) {
				request.setAttribute("questiontext", questiontext);
				request.setAttribute("questiontitle", questiontitle);
				request.setAttribute("error", "Blank indicator cannot contain a comma ','");
				request.getRequestDispatcher("Questionfillinmultipleblank.jsp").forward(request, response);
			}
			//System.out.println(blankString);
			blank.add(blankString);
			start = endIndex+1; //so we dont find the same ]
			amtofblanks++;
		}
		//System.out.println(blank);
		//System.out.println(amtofblanks);
		if(amtofblanks == 0) {
			request.setAttribute("questiontext", questiontext);
			request.setAttribute("questiontitle", questiontitle);
			request.setAttribute("error", "Question text does not contain [x]");
			request.getRequestDispatcher("Questionfillinmultipleblank.jsp").forward(request, response);
		}else if(amtofblanks == 1){
			String blankx = blank.get(0);
			questiontext = questiontext.replace("["+blankx, "[x");
			//System.out.println(questiontext);
			request.setAttribute("idmultiquestion", idmultiquestion);
			request.setAttribute("questiontext", questiontext);
			request.setAttribute("questiontitle", questiontitle);
			request.setAttribute("error", "You only had one blank so you were redirected to Create fillintheblank");
			request.getRequestDispatcher("MultipartAddQuestionfillintheblank.jsp").forward(request, response);
		}
		//here then we need to redirect to the page to ask for correct answers from professor.
		request.setAttribute("questiontext", questiontext);
		request.setAttribute("questiontitle", questiontitle);
		request.setAttribute("pointsworth", ptsworth);
		request.setAttribute("casesensistive", casesensistive);
		request.setAttribute("allowPartialCredit", allowPartialCredit);
		request.setAttribute("idmultiquestion", idmultiquestion);
		request.setAttribute("blank", blank);
		request.getRequestDispatcher("MultipartAddQuestionfillinmultipleblank2.jsp").forward(request, response);
	}

}
