<%-- <!DOCTYPE html>
<%@page import="java.util.Date"%>
<%@page import="com.testersite.model.ClassObject"%>
<%@page import="java.util.*"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="java.sql.* "%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
</head>
<body>
    <%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		//System.out.println(session.getAttribute("username"));
		if(session.getAttribute("username")==null || session.getAttribute("student")==null){
			response.sendRedirect("LoginStudent.jsp");
			return;
		}
	%>
    <%
    	Connection connection = DBConnection.getDBConnection();
    	String classid = ((String) session.getAttribute("classid")).trim();
    	ClassObject thisclass = new ClassObject();
    	try {
			Statement st = connection.createStatement();
			ResultSet rSet;
			rSet = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+classid+"'"); //getting the test ids from db
			rSet.next();
			
			Statement st1 = connection.createStatement(); 
			ResultSet rSet1;
			
			String professorid = rSet.getString("idprofessorprofiles");
			rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.professorprofiles WHERE idprofessorprofiles = '"+professorid+"';");
			rSet1.next();
			
			String coursename = rSet.getString("courseprefix")+rSet.getString("coursenumber")+"|"+rSet.getString("coursename");
			String professorname = rSet1.getString("name");
			
			
			thisclass.setClassid(classid);
			thisclass.setCoursename(coursename);
			thisclass.setInstructorName(professorname);
			thisclass.setdateStart(rSet.getString("datestart"));
			thisclass.setdateEnd(rSet.getString("dateend"));
			
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("STests.jsp SQL ERROR");
			out.print("STests.jsp SQL ERROR");
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
						<button type="submit" name="logoutfrom" value="student">Log Out</button>
				  </form>
                </span>
            </div>
        </div>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li >
                        <a href="HomeStudent.jsp" ><i class="fa fa-desktop "></i>Home <!-- <span class="badge">Included</span> --></a>
                    </li>
                
                    <li>
                        <a href="SClasses.jsp" ><i class="fa fa-desktop "></i>Classes <!-- <span class="badge">Included</span> --></a>
                    </li>
                    
                    <li>
                        <a href="SShowClass.jsp" ><i class="fa fa-desktop "></i><%out.println(thisclass.getCoursename());%> <!-- <span class="badge">Included</span> --></a>
                    </li>
                    
                    <li class="active-link">
                        <a href="STests.jsp" ><i class="fa fa-desktop "></i>Tests <!-- <span class="badge">Included</span> --></a>
                    </li>
                    
                    <li class="link-of-link">
                        <a href="SGrades.jsp" ><i class="fa fa-desktop "></i>Grades <!-- <span class="badge">Included</span> --></a>
                    </li>
                </ul>
            </div>

        </nav>
        <div id="page-wrapper" >
        	<div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2><%out.println(thisclass.getCoursename()); %></h2>
					<hr>
					<h3>Tests</h3>
					<hr>
                    </div>
                </div>          
            </div>    
        </div>
        
    <div class="footer">
      
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
			Statement st = connection.createStatement();
			ResultSet rSet;
			rSet = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+classid+"'"); //getting all tests in testdns 
			rSet.next();

			Statement st1 = connection.createStatement(); //used to get the actual test information using testid from rSet
			ResultSet rSet1;
			
			
			
			while(rSet.next()){
				String idtest = rSet.getString("idtest");
				rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.testprofiles where idtest = '"+idtest+"'");
				rSet1.next();
				if(rSet1.getBoolean("availibility")){ //if avalible, creates test object with information and adds to test arraylist availibleTests
					Test test = new Test();
					test.setTestId(rSet1.getString("idtest"));
					test.setDisplaystart(rSet1.getString("displaystart")); //display start and end can be used later on with conjunction with a function in test that determins whether test should be displayed or not.
					test.setDisplaystart(rSet1.getString("displayend"));
					test.setTestName(rSet1.getString("testname"));
					test.setTestDescription(rSet1.getString("testdescription"));
					test.settestDateEnd(rSet1.getString("testdateend"));
					availibleTests.add(test);
				}
			}
 --%>
 
 <!DOCTYPE html>
<%@page import="com.testersite.model.Question"%>
<%@page import="com.testersite.model.Test"%>
<%@page import="java.util.Date"%>
<%@page import="com.testersite.model.ClassObject"%>
<%@page import="java.util.*"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="java.sql.* "%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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
</head>
<body>
    <%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		//System.out.println(session.getAttribute("username"));
		if(session.getAttribute("username")==null || session.getAttribute("student")==null){
			response.sendRedirect("LoginStudent.jsp");
			return;
		}
	%>
    <%
    	Connection connection = DBConnection.getDBConnection(); 
   	 	ClassObject thisclass = (ClassObject) session.getAttribute("thisclass"); //the class object created in ShowClassServlet
    	Test thisTest = (Test) session.getAttribute("thistest"); //the test object created in TakeTestServlet
    	
    	ArrayList<Question> testQuestions = new ArrayList<>(); //arraylist of test objects of available tests.
    	
    	/* try {
			Statement st = connection.createStatement();
			ResultSet rSet;
			rSet = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+classid+"'"); //getting all tests in testdns 

			Statement st1 = connection.createStatement(); //used to get the actual test information using testid from rSet
			ResultSet rSet1;
			
			
			
			while(rSet.next()){
				
				String idtest = rSet.getString("idtest");
				
				//System.out.println("tests?id = "+idtest);
				
				rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.testprofiles where idtest = '"+idtest+"'");
				rSet1.next();
				if(rSet1.getBoolean("availibility")){ //if avalible, creates test object with information and adds to test arraylist availibleTests
					Test test = new Test();
					test.setTestId(rSet1.getString("idtest"));
					test.setDisplaystart(rSet1.getString("displaystart")); //display start and end can be used later on with conjunction with a function in test that determins whether test should be displayed or not.
					test.setDisplaystart(rSet1.getString("displayend"));
					test.setTestName(rSet1.getString("testname"));
					test.setTestDescription(rSet1.getString("testdescription"));
					test.settestDateEnd(rSet1.getString("testdateend"));
					availibleTests.add(test);
				}
			}
			//all availible tests have been added to availibleTests
			
			
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SShowClass.jsp SQL ERROR");
			out.print("SShowClass.jsp SQL ERROR");
		} */
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
						<button type="submit" name="logoutfrom" value="student">Log Out</button>
				  </form>
                </span>
            </div>
        </div>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                    <li >
                        <a href="HomeStudent.jsp" ><i class="fa fa-desktop "></i>Home <!-- <span class="badge">Included</span> --></a>
                    </li>
                
                    <li>
                        <a href="SClasses.jsp" ><i class="fa fa-desktop "></i>Classes <!-- <span class="badge">Included</span> --></a>
                    </li>
                    
                    <li>
                        <a href="SShowClass.jsp" ><i class="fa fa-desktop "></i><%out.println(thisclass.getCoursename());%> <!-- <span class="badge">Included</span> --></a>
                    </li>
                    
                    <li class="link-of-link">
                        <a href="STests.jsp" ><i class="fa fa-desktop "></i>Tests <!-- <span class="badge">Included</span> --></a>
                    </li>
                    
                    <li class="link-of-linkcenter">
                        <a href="SGrades.jsp" ><i class="fa fa-desktop "></i>Grades <!-- <span class="badge">Included</span> --></a>
                    </li>
                </ul>
            </div>

        </nav>
        <div id="page-wrapper" >
        	<div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                    <h2><%out.println(thisclass.getCoursename()); %></h2>
					<hr>
					<div class="SShowTestText">
						<h3>Test name: <%out.println(thisTest.getTestName()); %></h3>
						<h4>Test description: <%out.println(thisTest.getTestDescription()); %></h4>
						<h4>Test instructions: <%out.println(thisTest.getTestInstructions()); %></h4>
						<hr>
						
						<% if(thisTest.isForcedCompletion()){out.println("Once you start this exam, you have to complete it in one sitting.");} %> <br>
						<% if(!(thisTest.getTimelimit() == 0)){
								out.println("You will have "+thisTest.getTimelimit()+" minutes to complete this exam.");
							}else{
								out.println("You have unlimited time to complete this exam.");
							}%> <br>
						<% if(thisTest.getAllowBackButton()){
								out.println("Going back to previous questions will not be permitted in this exam.");
							}else{
								out.println("You may traverse to previous questions in this exam.");
							}%> <br>
							
						<!-- implement amt of attempts checker -->
						
						<br>
					</div>
					<form action="StartTestServlet" method="get">
						<button class="SShowTestbutton">Start Test</button>
					</form>
                    </div>
                </div>          
            </div>    
        </div>
        
    <div class="footer">
      
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
 