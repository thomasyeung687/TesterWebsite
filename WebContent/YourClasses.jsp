
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
</head>
<body>
	<%
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		System.out.println(session.getAttribute("idprofessorprofiles"));
		if(session.getAttribute("idprofessorprofiles")==null){
			response.sendRedirect("LoginProf.jsp");
		}
	%>
	<%
	Connection con = DBConnection.getDBConnection();
	//System.out.println(session.getAttribute("username"));
	ResultSet rset;
	ArrayList<String> classes = new ArrayList<>(); //stores the name of the class
	ArrayList<Integer> classids = new ArrayList<>(); //stores the id of the class
	try {
		Statement st = con.createStatement();
		int idprofessorprofiles = Integer.parseInt((String) session.getAttribute("idprofessorprofiles")); //this is the foreign key which we can use to get the classes from this account
		
		rset = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE idprofessorprofiles = '"+idprofessorprofiles+"';"); //gets all the classes who's idprofessorprofiles is the one of this account
		
		while(rset.next()){
			classes.add(rset.getString("courseprefix")+rset.getString("coursenumber"));
			classids.add(rset.getInt("idclass"));
		}
		//System.out.println(classcode);
		//System.out.println(students);
		
	}catch(Exception e){
		System.out.println("Exception in YourClasses.jsp!");
		System.out.println(e.getLocalizedMessage());
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
                 <form action="CreateClassLinker">
						<input type="submit" value="Create Class" >
				  </form>
                  <hr /> <!-- adds line -->
              	 <h3>Classes: </h3>
              	 <form action="ShowClassServlet" method="get">
				<%
					for(int i = classes.size()-1; i>-1; i--){
				%>
					<button name="classid" value="<% out.println(classids.get(i)); %>" > <%out.println(classes.get(i)); %></button> <br> <%//creates buttons with the class name on them and classid as the value passed through request %>
				<%
					}
				%>
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