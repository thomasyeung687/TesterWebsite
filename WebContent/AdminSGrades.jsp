<!DOCTYPE html>
<%@page import="com.testersite.model.TestAttemptObject"%>
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
    <link href="assets/css/grades.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
</head>
<body>
    <%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		//System.out.println(session.getAttribute("username"));
		if(session.getAttribute("idstudentprofiles")==null){
			response.sendRedirect("LoginStudent.jsp");
			return;
		}
	%>
    <%
	Connection connection = DBConnection.getDBConnection();
	String classid = ((String) session.getAttribute("classid")).trim();
	String studentid = (String) session.getAttribute("idstudentprofiles");
/* 	ClassObject thisclass = (ClassObject) session.getAttribute("thisclass"); //the class object created in ShowClassServlet */
	ArrayList<Test> availibleTests = new ArrayList<>(); //arraylist of test objects of available tests.
/* 	if(thisclass == null){
		response.sendRedirect("SClasses.jsp");
		return;
	} */
	try {
		Statement st = connection.createStatement();
		ResultSet rSet;
		rSet = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+classid+"'"); //getting all tests in testdns 

		Statement st1 = connection.createStatement(); //used to get the actual test information using testid from rSet
		ResultSet rSet1;
		
		
		
		while(rSet.next()){
			Test test = Test.getTestWithOnlyPreferences(rSet.getString("idtest"));
			test.addAttemptObject(TestAttemptObject.getAttemptFromDB(studentid, test.getTestId()));
			availibleTests.add(test);
		}
		//all availible tests have been added to availibleTests
		
		
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("SShowClass.jsp SQL ERROR");
		out.print("SShowClass.jsp SQL ERROR");
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
                    
                    <li >
                        <a href="AManageProfessors.jsp"><i class="fa fa-edit "></i>Manage Professors<span class="badge"></span></a>
                    </li>
                    
                    <li class="active-link">
                        <a href="AManageStudent.jsp"><i class="fa fa-edit "></i>Manage Students<span class="badge"></span></a>
                    </li>
                </ul>
                            </div>

        </nav>
        <div id="page-wrapper" >
        	<div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>Manage Student</h2>
					<hr>
					<h3>Grades</h3>
					<hr>
                    </div>
                </div>
               		<div>
               		<form action="SShowCompletedTestServlet" method="get">
		                <% for(Test test: availibleTests){ 
		                    	if(test.getAttempts().get(0) == null){
		                    %>
			                     <div class="flexbox">
			                    	<span><% out.println(test.getTestName()); %></span>
			                    	<span> Grade: incomplete</span>
			                     </div>
	                    		<%
	                    		}else if(test.isReleaseGrade()){
	                    			TestAttemptObject tao = test.getAttempts().get(0);
	                    			if(test.isAllowSeeAttempt()){
		                    	%>
			                    	<div class="flexbox">
				                    	<span><% out.println(test.getTestName()); %></span>
				                    	<div>
				                    		Raw Grade: <button type="submit" value="<%=tao.getIdAttempt()%>" name="idattempt"><%out.println(tao.getgrade()+"/"+tao.getgradeOutOf()); %></button><br>
				                    		Calculated: <%out.println(tao.getPercentageScore()+"%"); %>
				                    	</div>
				                    </div>
	                    	<%		}else{%>
	                    				<div class="flexbox">
				                    	<span><% out.println(test.getTestName()); %></span>
				                    	<div>
				                    		Raw Grade: <%out.println(tao.getgrade()+"/"+tao.getgradeOutOf()); %><br>
				                    		Calculated: <%out.println(tao.getPercentageScore()+"%"); %>
				                    	</div>
				                    	</div>
	                    		  <%}
	                    	}else if(!test.isReleaseGrade()){ 
	                    		TestAttemptObject tao = test.getAttempts().get(0);
	                    	%>
		                    	<div class="flexbox">
			                    	<span><% out.println(test.getTestName()); %></span>
			                    	<div>
			                    		<span>Being Graded</span>
			                    	</div>
			                    </div>
                    		<%}
	                    } %>
                    </form>
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
