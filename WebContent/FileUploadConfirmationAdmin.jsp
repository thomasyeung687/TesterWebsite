<%//only show messages that are needed. %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
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
                  <a href="#" style="color:#fff;">LOGOUT</a>  

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
                     <h2><span style =  "color: red;"> ${error} </span><br> </h2>   
                     <h2><span style =  "color: green;"> ${msg} </span><br> </h2>   
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr />
                <% if(request.getAttribute("accountcreatedNaddedTF").equals("true")){ %>
                <h4>Student accounts that were created and added to your class:</h4>
                <% } %>
                <%
                	String accountcreatedNadded = (String) request.getAttribute("accountcreatedNadded"); 
                	accountcreatedNadded = accountcreatedNadded.replace("]", "]<br/>");//so the results print on a separate line
                	out.println(accountcreatedNadded);
                %>
                <% if(request.getAttribute("accountaddedTF").equals("true")){ %>
                <h4>Student accounts that already existed and were added to your class:</h4>
                <% } %>
                <%String accountadded = (String) request.getAttribute("accountadded"); 
                accountadded = accountadded.replace("]", "]<br/>");//so the results print on a separate line
                out.println(accountadded);
                %>
                <% if(request.getAttribute("studentalreadyclassTF").equals("true")){ %>
                <h4>Student accounts that already are in your class:</h4>
                <%} %>
                <%String studentalreadyclass = (String) request.getAttribute("studentalreadyclass");
                studentalreadyclass = studentalreadyclass.replace("]", "]<br/>"); //so the results print on a separate line
                out.println(studentalreadyclass);
                %>
                <br>
                <br>
				<a href= "AdminClassSeeStudents.jsp">back</a>
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
