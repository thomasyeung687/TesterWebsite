package linkers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddQuestionLinker
 */
@WebServlet("/AddQuestionLinker")
public class AddQuestionLinker extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("TestLayout.jsp");
		if(request.getParameter("questiontype").equals("multiplechoice")) {
			rd = request.getRequestDispatcher("Questionmultiplechoice.jsp");
			
		}else if(request.getParameter("questiontype").equals("checkall")) {
			rd = request.getRequestDispatcher("Questioncheckall.jsp");
			
		}else if(request.getParameter("questiontype").equals("fillintheblank")) {
			rd = request.getRequestDispatcher("Questionfillintheblank.jsp");
			
		}else if(request.getParameter("questiontype").equals("fillinmultipleblank")) {
			rd = request.getRequestDispatcher("Questionfillinmultipleblank.jsp");
			
		}else if(request.getParameter("questiontype").equals("truefalse")) {
			rd = request.getRequestDispatcher("Questiontruefalse.jsp");
			
		}else if(request.getParameter("questiontype").equals("shortresponse")) {
			rd = request.getRequestDispatcher("Questionshortresponse.jsp");
			
		}else if(request.getParameter("questiontype").equals("multipart")) {
			rd = request.getRequestDispatcher("Questionmultipart.jsp");
			
		}else if(request.getParameter("questiontype").equals("matching")) {
			rd = request.getRequestDispatcher("Questionmatching.jsp");
		}
		rd.forward(request, response);
	}
}
