 <!DOCTYPE html>
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
    	String classid = ((String) session.getAttribute("classid")).trim();
    	ClassObject thisclass = (ClassObject) session.getAttribute("thisclass"); //the class object created in ShowClassServlet
    	ArrayList<Test> availibleTests = new ArrayList<>(); //arraylist of test objects of available tests.
    	try {
			Statement st = connection.createStatement();
			ResultSet rSet;
			rSet = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+classid+"'"); //getting all tests in testdns 
			while(rSet.next()){
				Test test = Test.getTestFromDB(rSet.getString("idtest"));
				if(test.isAvailibility()){ //adds test only if its available
					availibleTests.add(test);
				}
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
					<h3>Tests</h3>
					<hr>
					<form action="TakeTestServlet" method="get"> <!-- Folder: StudentServlets -->
						<%for(Test test : availibleTests){
							%>
								<button class="STestsbutton" name="testid" value = "<%out.println(test.getTestId());%>"> 
									<%out.println(test.getTestName());%><br>
									Test description: <%out.println(test.getTestDescription());%><br>
									Due Date: <%out.println(test.gettestDateEnd());%><br>
								</button> <br>
							<%} %>
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
 