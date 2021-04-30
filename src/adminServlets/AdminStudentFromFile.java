package adminServlets;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;
import com.testersite.model.TesterClass;


/**
 * Servlet implementation class StudentFromFile
 */
@WebServlet("/AdminStudentFromFile")
public class AdminStudentFromFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String filepath = request.getParameter("file");
		System.out.println(filepath);
		System.out.println("here4!");
		File file = new File(filepath);
		System.out.println("is exist?: "+file.exists());
		System.out.println("is readable?: "+file.canRead());
		
		System.out.println("The path is '" + file.getAbsolutePath() + "'");
		System.out.println("here3!");
		TesterClass cObject = (TesterClass) session.getAttribute("classObj");
		try {
			Connection connection = DBConnection.getDBConnection();
			System.out.println("here2!");
			Statement st = connection.createStatement();
			Scanner scanner = new Scanner(file);
//			System.out.println("here!");
			String classid = cObject.getIdclass().trim();
			
			String accountcreatedNadded ="";
			String accountadded="";
			String studentalreadyclass="";
			int count = 0;
			while(scanner.hasNextLine()) {
				String line = scanner.nextLine(); //gets line from txt file.
				line= line.replace("'", "");// removes "'" from names which ruin sql syntax
				String[] array = line.split(" "); //turns line into array split by " " essentially making an array with studentID, netId, lastname, firstname, email in positions 0,1,2,3,4
				String name = array[3]+" "+array[2];
				ArrayList<String> student = new ArrayList<String>();
				for(String text:array) {
					student.add(text);
				}
				System.out.println(count++);
				ResultSet rset = st.executeQuery("SELECT idstudentprofiles from testersitedatabase.studentprofiles where username = '"+array[1]+"';"); //this checks if the netid is already a username of a student profile
				if(rset.next()) {//which means there exists a student user who already has an account with that username, then we just add them to the studenttoclass db
					System.out.println("found");
					String idstudentprofiles = rset.getString("idstudentprofiles");
					
					rset = st.executeQuery("SELECT * FROM testersitedatabase.studenttoclass WHERE idstudentprofiles = '"+idstudentprofiles+"' and classid = '"+ classid +"';"); //if it already exists, we dont wanna add it again
					if(rset.next()) {//student is already in the class
						studentalreadyclass += student.toString()+"\n";
					}else {
						String addStudentToClass = "INSERT INTO testersitedatabase.studenttoclass (idstudentprofiles, classid) VALUES ('"+idstudentprofiles+"','"+ classid +"')";
						System.out.println(addStudentToClass);
						st.execute(addStudentToClass);
						accountadded += student.toString()+"\n";
					}
				}else { //account is not found to exist in the database, then we create the student profile and add it to the studenttoclass db
					
					System.out.println("not found");
					String insertquery ="INSERT INTO testersitedatabase.studentprofiles (name,username, password, email, idadminprofiles) VALUES ('"+name+"','"+array[1]+"','"+array[0]+"','"+array[4]+"','"+(String)session.getAttribute("idadminprofiles")+"')";
					System.out.println(insertquery);
					st.execute(insertquery); //creating new studentprofile
					rset = st.executeQuery("SELECT idstudentprofiles from testersitedatabase.studentprofiles where username = '"+array[1]+"';"); //regets the student profile we just created so we can fetch the idstudentprofile
					if(rset.next()) {
					String idstudentprofiles = rset.getString("idstudentprofiles");
					String addStudentToClass = "INSERT INTO testersitedatabase.studenttoclass (idstudentprofiles, classid) VALUES ('"+idstudentprofiles+"','"+ classid +"')";
					System.out.println(addStudentToClass);
					st.execute(addStudentToClass);
					accountcreatedNadded += student.toString()+"\n";
					}else {
						System.out.println("nothing in rset!");
					}
					rset.close();
				}
			}
			scanner.close();
			System.out.println("DONE");
			System.out.println("accountcreatedNadded:");
			System.out.println(accountcreatedNadded);
			System.out.println("accountadded:");
			System.out.println(accountadded);
			System.out.println("studentalreadyclass:");
			System.out.println(studentalreadyclass);
			
			request.setAttribute("accountcreatedNaddedTF", "true");
			request.setAttribute("accountaddedTF", "true");
			request.setAttribute("studentalreadyclassTF", "true");
			if(accountcreatedNadded.isEmpty()) {//if not empty
				request.setAttribute("accountcreatedNaddedTF", "false");
			}
			if(accountadded.isEmpty()) {//if not empty
				request.setAttribute("accountaddedTF", "false");
			}
			if(studentalreadyclass.isEmpty()) {//if not empty
				request.setAttribute("studentalreadyclassTF", "false");
			}
			request.setAttribute("accountcreatedNadded", accountcreatedNadded);
			request.setAttribute("accountadded", accountadded);
			request.setAttribute("studentalreadyclass", studentalreadyclass);
			
		}catch(FileNotFoundException e) {
			System.out.println("File not found");
			request.setAttribute("error", "Incorrect File Path");
			request.getRequestDispatcher("FileUploadConfirmation.jsp").forward(request, response);
			return;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			request.setAttribute("error", "SQL Connection Error occured");
			request.getRequestDispatcher("FileUploadConfirmation.jsp").forward(request, response);
			return;
		}
		request.setAttribute("msg", "Successful");
		request.getRequestDispatcher("FileUploadConfirmationAdmin.jsp").forward(request, response);
	}

}
