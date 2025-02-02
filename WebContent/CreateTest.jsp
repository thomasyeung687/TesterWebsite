<!-- links: 
	
 -->
<%@page import="com.testersite.model.TesterClass"%>
<%@page import="Random.RandomString"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="com.testersite.model.Question"%>
<%@page import="com.testersite.model.Test"%>
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
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		/* System.out.println(session.getAttribute("username")); */
		String from = (String) session.getAttribute("from");
		if(from.equals("prof") && session.getAttribute("idprofessorprofiles")==null){
			response.sendRedirect("LoginProf.jsp");
		}
	%>
	<%
	System.out.println("CreateTest.jsp");
	Connection con = DBConnection.getDBConnection();
	//System.out.println(session.getAttribute("username"));
	ResultSet rset;
	String idclass= "0";
	String courseprefix="";
	String coursenumber="";
	String coursename="";
	String datestart="";
	String dateend="";
	String classcode="";
	ArrayList<String> idtestprofiles = new ArrayList<String>();
	ArrayList<Test> tests = new ArrayList<Test>();
	

	if(from.equals("admin")){
		TesterClass tclass = (TesterClass) session.getAttribute("classObj");
		idclass = tclass.getIdclass();
	}else{
		idclass = (String) session.getAttribute("classid");
	}
	//System.out.println("1");
	//System.out.println(session.getAttribute("classid"));
	try {
		Statement st = con.createStatement();
		rset = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE idclass = '"+idclass+"';");
		rset.next(); 
		
		idclass = rset.getString("idclass");
		courseprefix = rset.getString("courseprefix");
		coursenumber = rset.getString("coursenumber");
		coursename = rset.getString("coursename");
		datestart = rset.getString("datestart");
		dateend = rset.getString("dateend");
		classcode = rset.getString("classcode");
		
		rset = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+session.getAttribute("classid")+"';");
		System.out.println("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+session.getAttribute("classid")+"';");
		while(rset.next()){//while there are more tests.
			idtestprofiles.add(rset.getString("idtest"));
		}
		//System.out.println(idtestprofiles);
		for(String id:idtestprofiles){	
			ResultSet testinfo = st.executeQuery("SELECT * FROM testersitedatabase.studentprofiles WHERE idstudentprofiles = '"+id+"';");//looking for that particular student and getting their info 
			//System.out.println("2");
			if(testinfo.next()){//if found we do
				Test newtest = new Test();
				//System.out.println("3");
				tests.add(newtest);
				//System.out.println("4");
			}else{//student with id = idStudentprofile not found????
				System.out.println(id+" This test not found!");
			}
		}
		
	}catch(Exception e){
		System.out.println(e.getMessage());
	}
	%>
	
    <div id="wrapper">
    <%if(from.equals("admin")){ %>
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
						<button type="submit" name="logoutfrom" value="admin">Log Out</button>
				  </form>

                </span>
            </div>
        </div>
        <!-- /. NAV TOP  -->
        <nav class="navbar-default navbar-side" role="navigation">
            <div class="sidebar-collapse">
                <ul class="nav" id="main-menu">
                 


                    <li >
                        <a href="AdminOptions.jsp" ><i class="fa fa-desktop "></i>Dashboard <span class="badge"></span></a>
                    </li>
                    
                    <li class="active-link">
                        <a href="AManageProfessors.jsp"><i class="fa fa-edit "></i>Manage Professors<span class="badge"></span></a>
                    </li>
                    
                    <li>
                        <a href="AManageStudent.jsp"><i class="fa fa-edit "></i>Manage Students<span class="badge"></span></a>
                    </li>
                </ul>
                            </div>

        </nav>
         
        <%}else{ %>
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
<!-- 					<li >
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
                    </li> -->
                </ul>
                            </div>

        </nav>
        <%} %>
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>Test Information</h2>  
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr /> <!-- adds line -->
                  <form action="CreateTestServlet" method="post">
                  	Test Name<span style="padding-left:30px"><input type="text" name="testname" required></span> </br>
                  	Description<span style="padding-left:25px"><input type="text" name="description" > </span> </br>
                  	Instructions<span style="padding-left:23px"><input type="text" name="instruction" > </span> </br>
                  	<input type="submit">
                  </form>
                  <% if(from.equals("prof")){ %>
                 	 <form method="get" action="ProfessorBackButtons">
						<button name="pageName" value="CreateTest">Back</button> 
					</form>
                  <%}else if(from.equals("admin")){ %>
                  	<form method="get" action="AdminBackButtons">
						<button name="pageName" value="AdminShowCompletedTestPage">Back</button> 
					</form>
                  <%} %>
                   

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