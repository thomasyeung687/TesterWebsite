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
		if(session.getAttribute("idstudentprofiles")==null){
			response.sendRedirect("LoginStudent.jsp");
			return;
		}
	%>
    <%
    	Connection connection = DBConnection.getDBConnection(); 
   	 	ClassObject thisclass = (ClassObject) session.getAttribute("thisclass"); //the class object created in ShowClassServlet
    	Test thisTest = (Test) session.getAttribute("thistest"); //the test object created in TakeTestServlet
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
					<% if(thisTest.getAttempts().size() == 0 ){ //test has not been attempted yet. %>
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
								out.println("You may traverse to previous questions in this exam.");
							}else{
								out.println("Going back to previous questions will not be permitted in this exam.");
							}%> <br>
							
						<!-- implement amt of attempts checker -->
						
						<br>
					</div>
					<form action="StartTestServlet" method="get"> <!-- Folder: StudentServlets -->
						<button class="SShowTestbutton" name="action" value="start">Start Test</button>
					</form>
					<%}else if(thisTest.getAmtOfAttempts() > thisTest.getAttempts().get(0).getAttemptNumber()){ %>
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
								out.println("You may traverse to previous questions in this exam.");
							}else{
								out.println("Going back to previous questions will not be permitted in this exam.");
							}%> <br>
							
						<!-- implement amt of attempts checker -->
						<br>
						<form action="StartTestServlet" method="get"> <!-- Folder: StudentServlets -->
							<span>You have attempted this test <%out.println(thisTest.getAttempts().get(0).getAttemptNumber()); %> time(s)</span> <br>
							<span>This test allows <%out.println(thisTest.getAmtOfAttempts()); %> attempt(s)</span><br>
							<hr>
							
							<button class="SShowTestbutton" name="action" value="start">Start attempt <%out.println(thisTest.getAttempts().get(0).getAttemptNumber()+1); %></button>
							<% if(thisTest.isAllowSeeAttempt()){ %>
							<button class="SShowTestbutton" name="action" value="seeAttempt">Review Previous Attempt</button>
							<%} %>
						</form>
					<%}else{ %>
						<h3>Test name: <%out.println(thisTest.getTestName()); %></h3>
						<h4>Test description: <%out.println(thisTest.getTestDescription()); %></h4>
						<h4>Test instructions: <%out.println(thisTest.getTestInstructions()); %></h4>
						<hr>
						<span>This test allows <%out.println(thisTest.getAmtOfAttempts()); %> attempt(s)</span>
						<br>
						<form action="StartTestServlet" method="get"> <!-- Folder: StudentServlets -->
							<span>You have attempted this test <%out.println(thisTest.getAttempts().get(0).getAttemptNumber()); %> time(s)</span> <br>
							<% if(thisTest.isAllowSeeAttempt()){ %>
							<button class="SShowTestbutton" name="action" value="seeAttempt">Review Previous Attempt</button>
							<%} %>
							<button class="SShowTestbutton" name="action" value="back">Back</button>
						</form>
					<%} %>
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
 