<%@page import="Random.RandomString"%>
<%@page import="com.testersite.dao.DBConnection"%>
<%@page import="com.testersite.model.Question"%>
<%@page import="java.util.*"%>
<%@page import="java.sql.*"%>
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
		System.out.println(session.getAttribute("username"));
		if(session.getAttribute("username")==null || session.getAttribute("professor")==null){
			response.sendRedirect("LoginProf.jsp");
		}
	%>
	<%
	System.out.println("ClassManageStudents.jsp");
	Connection con = DBConnection.getDBConnection();
	//System.out.println(session.getAttribute("username"));
	ResultSet rset;
	ArrayList<String> students = new ArrayList<>();
	int idclass=0;
	String courseprefix="";
	String coursenumber="";
	String coursename="";
	String datestart="";
	String dateend="";
	String classcode="";

	//System.out.println(session.getAttribute("classid"));
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
		
		//System.out.println(idclass);
		//System.out.println(courseprefix + coursenumber + " " + coursename +" "+ datestart +" "+ dateend+" "+classcode);
		
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
                     <h2>Create Student Login From File And Add them to <%out.println(courseprefix + coursenumber); %> : <%out.println(coursename); %></h2>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                 <h3>Class Code: <%out.println(session.getAttribute("classid")); %></h3>
                  <hr />
              	<form action="StudentFromFile" enctype="multiport/form-data" method= "post">
              		<!-- <input type="text" name="filename"><br> -->
              		Enter path to file of roster:<input type="text" name="file" />
              		<br>
              		<input type="submit" value="upload" />
              	
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
