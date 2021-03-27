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
		System.out.println(session.getAttribute("idprofessorprofiles"));
		if(session.getAttribute("idadminprofiles")==null){
			response.sendRedirect("LoginAdmin.jsp");
		}
		if(session.getAttribute("professorObj")==null){
			System.out.println("AdminSeeClass session attribute professorObj is null");
			response.sendRedirect("AManageProfessors.jsp");
		}
		if(session.getAttribute("classObj")==null){
			System.out.println("AdminSeeClass session attribute classObj is null");
			response.sendRedirect("AManageProfessors.jsp");
		}
		
		Professor prof = (Professor)session.getAttribute("professorObj");
		TesterClass classObj = (TesterClass)session.getAttribute("classObj");
		System.out.println("professor aspp.jsp: "+prof.toString());
		System.out.println("Classobj aspp.jsp: "+classObj.toString());
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
                    <hr/>
                    <div class="row">
	                    <div class="col-md-12">
	                     <h2><%out.print(classObj.getCourseprefix() + classObj.getCoursenumber() +" | "); out.println(classObj.getCoursename()+" | "+classObj.getSemester()); %>  </h2>   
	                     <h3>Class Code: <%out.println(classObj.getClasscode());%></h3> <h4 >Course start: <%out.println(classObj.getDatestart()); %></h4> <h4>Course end: <%out.println(classObj.getDateend()); %></h4> 
	                    </div>
                	</div>          
                  	<hr /> <!-- adds line -->
		            	<form action="AdminClassOptionsServlet" method="post">
		              	 	<%-- <input type="hidden" name="classid" value="<%out.print(session.getAttribute("classid"));%>"> --%>
							<input type="submit" name="action" value="Edit Class">
							<input type="submit" name="action" value="Manage Students">
							<input type="submit" name="action" value="Manage Tests">
						</form>
						<form action="DeleteClassServlet" method="post" name="deletetest">
		                     	<input type="hidden" name="classid" value="<%out.print(classObj.getIdclass());%>">
		                     	<button type="button"  onclick="confirmButton()">Delete This Class</button>
		                </form>
						<form method="get" action="AdminBackButtons">
							<button name="pageName" value="AdminSeeClass">Back</button> 
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
