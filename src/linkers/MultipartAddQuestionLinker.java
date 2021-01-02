package linkers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MultipartAddQuestionLinker
 */
@WebServlet("/MultipartAddQuestionLinker")
public class MultipartAddQuestionLinker extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("TestLayout.jsp");
		if(request.getParameter("questiontype").equals("multiplechoice")) {
			rd = request.getRequestDispatcher("MultipartAddQuestionmultiplechoice.jsp");
		}else if(request.getParameter("questiontype").equals("checkall")) {
			rd = request.getRequestDispatcher("MultipartAddQuestioncheckall.jsp");
			
		}else if(request.getParameter("questiontype").equals("fillintheblank")) {
			rd = request.getRequestDispatcher("MultipartAddQuestionfillintheblank.jsp");
			
		}else if(request.getParameter("questiontype").equals("fillinmultipleblank")) {
			rd = request.getRequestDispatcher("MultipartAddQuestionfillinmultipleblank.jsp");
			
		}else if(request.getParameter("questiontype").equals("truefalse")) {
			rd = request.getRequestDispatcher("MultipartAddQuestiontruefalse.jsp");
		}
		rd.forward(request, response);
	}
}
