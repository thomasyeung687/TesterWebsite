<%@page import="javax.websocket.Decoder.Text"%>
<%@page import="com.testersite.model.*"%>
<%@page import="com.testersite.model.Student"%>
<%@page import="Random.RandomString"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="com.testersite.model.Question"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
  	<meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Simple Responsive Admin</title>
	<!-- BOOTSTRAP STYLES-->
    <link href="assets/css/bootstrap.css" rel="stylesheet" />
     <!-- FONTAWESOME STYLES-->
    <link href="assets/css/font-awesome.css" rel="stylesheet" />
        <!-- CUSTOM STYLES-->
    <link href="assets/css/custom.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
    <!-- GRADING STYLING -->
   <link href="assets/css/grades.css" rel="stylesheet" />
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		System.out.println(session.getAttribute("idprofessorprofiles"));
		if(session.getAttribute("idprofessorprofiles")==null){
			response.sendRedirect("LoginProf.jsp");
			return;
		}
	%>
	<%
	System.out.println("ClassManageStudents.jsp");
	Connection con = DBConnection.getDBConnection();
	//System.out.println(session.getAttribute("username"));
	ResultSet rset;
	int idclass=0;
	String courseprefix="";
	String coursenumber="";
	String coursename="";
	String datestart="";
	String dateend="";
	String classcode="";
	ArrayList<String> idStudentProfiles = new ArrayList<String>();
	ArrayList<Student> students = new ArrayList<Student>();//arraylist of students
	ArrayList<Test> tests = new ArrayList<Test>();
	try {
		Statement st = con.createStatement();
		rset = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE idclass = '"+session.getAttribute("classid")+"';");
		rset.next(); 
		
		idclass = rset.getInt("idclass");
		courseprefix = rset.getString("courseprefix");
		coursenumber = rset.getString("coursenumber");
		coursename = rset.getString("coursename");
		datestart = rset.getString("datestart");
		dateend = rset.getString("dateend");
		classcode = rset.getString("classcode");
		
		rset = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = "+idclass+";");
		ArrayList<Integer> testids = new ArrayList<Integer>();
		while(rset.next()){
			testids.add(rset.getInt("idtest")); //building testids arraylist
		}
		
		rset = st.executeQuery("SELECT * FROM testersitedatabase.studenttoclass WHERE classid = '"+idclass+"';"); //gets the idstudentprofile of students in this class
		while(rset.next()){//while there are more students.
			idStudentProfiles.add(rset.getString("idstudentprofiles"));
		}
		System.out.println(idStudentProfiles);
		
		for(String id:idStudentProfiles){	
			ResultSet studentinfo = st.executeQuery("SELECT * FROM testersitedatabase.studentprofiles WHERE idstudentprofiles = '"+id+"';");//looking for that particular student and getting their info 
			if(studentinfo.next()){//if found we do
				Student stud = new Student(studentinfo.getString("name"), studentinfo.getString("idstudentprofiles"), testids);
				students.add(stud);
				/* stud.getAttemptsFromDB(); */
			}else{//student with id = idStudentprofile not found????
				System.out.println(id+" This student not found!");
			}
		}
		
		for(int id : testids){	
			ResultSet testinfo = st.executeQuery("SELECT * FROM testersitedatabase.testprofiles WHERE idtest = '"+id+"';");//looking for that particular test and getting its info 
			//System.out.println("2");
			tests.add(Test.getTestWithOnlyPreferences(id+""));
		}
		
	}catch(Exception e){
		System.out.println(e.getMessage());
	}
	%>	

    <div id="wrapper">
         <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="adjust-nav">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">
                        <img src="assets/img/logo.png" />
                    </a>
                </div>
              
                 <span class="logout-spn" >
                  <form action="LogoutServlet">
						<input type="submit" value="Logout">
				  </form>

                </span>
            </div>
        </div>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                 

 					<li >
                        <a href="CreatorOptions.jsp" ><i class="fa fa-desktop "></i>Dashboard <span class="badge"></span></a>
                    </li>
                    
                    <li class="active-link">
                        <a href="YourClasses.jsp"><i class="fa fa-edit "></i>Class  <span class="badge"></span></a>
                    </li>
					<li >
                        <a href="Testsnew.jsp" ><i class="fa fa-edit "></i>Tests <span class="badge"></span></a>
                    </li>
                    <li>
                        <a href="ui.jsp"><i class="fa fa-table "></i>UI Elements  <span class="badge"></span></a>
                    </li>
                    <li>
                        <a href="blank.html"><i class="fa fa-edit "></i>Blank Page  <span class="badge"></span></a>
                    </li>



                 <li>
                        <a href="#"><i class="fa fa-qrcode "></i>My Link One</a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-bar-chart-o"></i>My Link Two</a>
                    </li>

                    <li>
                        <a href="#"><i class="fa fa-edit "></i>My Link Three </a>
                    </li>
                    <li>
                        <a href="#"><i class="fa fa-table "></i>My Link Four</a>
                    </li>
                     <li>
                        <a href="#"><i class="fa fa-edit "></i>My Link Five </a>
                    </li>
                </ul>
            </div>
        </nav>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2><%out.println(courseprefix + coursenumber); %> </h2>   
                     <h3><%out.println(coursename); %></h3>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                 <h3>Class Code: <%out.println(classcode); %></h3>  <h3>    Add students from .txt file: <a href= FileUpload.jsp>here</a></h3>
                  <form action="BackToClassOptions" method="get">
				  	<button name="action" value="back">Back</button> 
				  </form>
                  <hr /> <!-- adds line -->
              	 <form action="ShowCompletedTestPageServlet" method="get">
					<table>
						<tr>
							<th>Student</th>
							<%
							for(int j = 0; j< tests.size(); j++){
							%> 
								<th><button type="submit" name="testid" value="<%out.println(tests.get(j).getTestId()); %>"><%out.println(tests.get(j).getTestName()); %></button></th>
							<%
							}
							%>
						</tr>
						<%
						for(int i = 0; i<students.size(); i++){
							Student stud = students.get(i);
						%>
						<tr>
							<td>
							 <button type="submit" name="studprofid" value="<%out.println(stud.getprofid());%>"><%out.println(stud.getname()); %></button> <br> 
							</td>
							<%
							for(int j = 0; j< tests.size(); j++){
								TestAttemptObject tao = TestAttemptObject.getAttemptFromDB(stud.getprofid(), tests.get(j).getTestId());
								if(tao != null){
							%> 
								<td><button type="submit" name=idattempt value="<%out.println(tao.getIdAttempt()); %>"><%out.println(tao.getgrade()+"/"+tao.getgradeOutOf()); %></button></td>
							<%
								}else{
							%> 
								<td><%out.println("incomplete"); %></td>
							<%
								}
							}
							%>
						</tr>							
						<%
							}
						%>
					</table>
				</form>
                 <!-- /. ROW  -->           
    </div>
             <!-- /. PAGE INNER  -->
            </div>
         <!-- /. PAGE WRAPPER  -->
        </div>
    <div class="footer">
      
        </div>
        </div>
          

     <!-- /. WRAPPER  -->
    <!-- SCRIPTS -AT THE BOTOM TO REDUCE THE LOAD TIME-->
    <!-- JQUERY SCRIPTS -->
    <script src="assets/js/jquery-1.10.2.js"></script>
      <!-- BOOTSTRAP SCRIPTS -->
    <script src="assets/js/bootstrap.min.js"></script>
      <!-- CUSTOM SCRIPTS -->
    <script src="assets/js/custom.js"></script>
    
   
</body>
</html>