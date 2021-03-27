<!DOCTYPE html>
<%@page import="com.testersite.model.TesterClass"%>
<%@page import="com.testersite.model.Professor"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="java.sql.Connection"%>
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
   <link href="assets/css/tables.css" rel="stylesheet" /> <!-- styling for table element -->
</head>
<body>
    <%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		if(session.getAttribute("idadminprofiles")==null){
			response.sendRedirect("LoginAdmin.jsp");
		}
		if(session.getAttribute("professorObj")==null){
			System.out.println("AdminSeeProfessorPage session attribute professorObj is null");
			response.sendRedirect("AManageProfessors.jsp");
		}
		Professor prof = (Professor)session.getAttribute("professorObj");
		ArrayList<TesterClass> classes = prof.getTesterClasses();
		System.out.println("professor aspp.jsp: "+prof.toString());
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
                    
                    <li class="active-link">
                        <a href="AManageProfessors.jsp"><i class="fa fa-edit "></i>Manage Professors<span class="badge"></span></a>
                    </li>
                    
                    <li>
                        <a href="AManageStudent.jsp"><i class="fa fa-edit "></i>Manage Students<span class="badge"></span></a>
                    </li>
                </ul>
                            </div>

        </nav>
        <div id="page-wrapper" >
        	<div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>Manage Professors</h2>
                    </div>
                    <span style = "color: red;" > ${error} </span>
                    <span style = "color: red;" > ${message} </span>
                    <form action="EditProfessorInfoServlet" method="post">
                    	<div>Professor Information</div><br>
                    	<input type="text" name="name" value="<%=prof.getName()%>" required><br>
                    	<input type="text" name="email" value="<%=prof.getEmail()%>" required><br>
                    	<input type="text" name="username" value="<%=prof.getUsername()%>" required><br>
                    	<input type="submit" name="action" value="Edit Info"><br>
                    </form>
                    <form action="EditProfessorInfoServlet" method="post">
                    	<div>Change Password</div>
                    	New Password: <input type="text" name="password1" placeholder="Change password?" required><br>
                    	Retype Password: <input type="text" name="password2" placeholder="Change password?" required><br>
                    	<input type="submit" name="action" value="Change Password"><br>
                    </form>
                  <hr /> <!-- adds line -->
              	 <form action="AdminCreateNewClassServlet" method="post">
						<span style =  "color: red;"> ${createclasserror} </span><br>
						<input type="text" name="coursecode" placeholder="Course Code"> optional (if none provided or if code in use, a new code will be auto generated)<br>
						<input type="text" name="courseprefix" placeholder="Course prefix" required> 
						<input type="text" name="coursenumber" placeholder="Course number" required><br>
						<input type="text" name ="coursename" placeholder="Course name" required><br>
						<input type="text" name ="semester" placeholder="Semester" required><br>
						<input type="date" name="datestart" placeholder="Date Start"><input type="date" name="dateend" placeholder="Date End"> optional <br>
						<input type="submit" value="create class">
				 </form>
				 <h3>Classes: </h3>
             	<form action="ShowClassServlet" method="get">
				<%
					for(int i = classes.size()-1; i>-1; i--){
						TesterClass tClass = classes.get(i);
						System.out.println(tClass.getCoursename());
				%>
					<button name="classid" value="<%=tClass.getIdclass()%>" > <%out.println(tClass.getCoursePreNNum()); %></button> <br> <%//creates buttons with the class name on them and classid as the value passed through request %>
				<%	
					}
				%>
				</form>
					
                    <hr/>
					<form method="get" action="AdminBackButtons">
						<button name="pageName" value="AdminSeeProfessorPage">Back</button> <br>
					</form>
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
