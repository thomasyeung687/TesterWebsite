<%@page import="com.testersite.model.Professor"%>
<%@page import="com.testersite.model.TesterClass"%>
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
	<!-- ClassOptionServlet -->
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
		System.out.println(session.getAttribute("idprofessorprofiles"));
		if(session.getAttribute("idadminprofiles")==null){
			response.sendRedirect((String)session.getAttribute("loginPage"));
			return;
		}
		if(session.getAttribute("professorObj")==null){
			System.out.println("AdminClassEdit session attribute professorObj is null");
			response.sendRedirect("AManageProfessors.jsp");
			return;
		}
		if(session.getAttribute("classObj")==null){
			System.out.println("AdminClassEdit session attribute classObj is null");
			response.sendRedirect("AManageProfessors.jsp");
			return;
		}
		
		Professor prof = (Professor) session.getAttribute("professorObj");
		TesterClass classObj = (TesterClass) session.getAttribute("classObj");
		System.out.println("professor AdminClassEdit.jsp: "+prof.toString());
		System.out.println("Classobj AdminClassEdit.jsp: "+classObj.toString());
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
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                     <h2>Manage Your Classes </h2>   
                    </div>
                </div>              
                 <!-- /. ROW  -->
                 <h3>Edit Class </h3>            
                  <hr /> <!-- adds line -->
              	<form action="AdminEditClassServlet" method="post">
              		<span style =  "color: red;"> ${EditClassError} </span><br>
              		<%-- <input type="hidden" name="classid" value="<%out.print(request.getParameter("classid"));%>">--%> <%//this passes the classid to the servlet which will use to idenify the class to edit %>
					Course Prefix: <input type="text" name="courseprefix" value="<%out.print(classObj.getCourseprefix()); %>" > 
					Course Number: <input type="text" name="coursenumber" value="<%out.print(classObj.getCoursenumber()); %>"> <br>
					Course Name: <input type="text" name="coursename" value ="<%out.print(classObj.getCoursename()); %>"> <br>
					Term start: <input type="date" name="datestart" value="<%out.print(classObj.getDatestart()); %>"><br>
					Term end: <input type= "date" name ="dateend" value="<%out.print(classObj.getDateend()); %>"><br>
					Term end: <input type= "text" name ="semester" value="<%out.print(classObj.getSemester()); %>"><br>
					<input type="submit" value="Edit Class">
				</form>
				<form method="get" action="AdminBackButtons">
					<button name="pageName" value="AdminClassEdit">Back</button> 
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