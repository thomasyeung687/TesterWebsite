<!-- links: 
	ClassTest.jsp -> ShowExistingTestServlet->TestShow.jsp

 -->
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
		//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		//System.out.println(session.getAttribute("username"));
		//if(session.getAttribute("username")==null || session.getAttribute("professor")==null){
		//	response.sendRedirect("LoginProf.jsp");
		//}
	%>
	<%
	System.out.println("ClassManageStudents.jsp");
	Connection con = DBConnection.getDBConnection();
	//System.out.println(session.getAttribute("username"));
	ResultSet rset;
	int idclass=0;
	String courseprefix="";
	String coursenumber="";
	String coursename="";
	String datestart="";
	String dateend="";
	String classcode="";
	ArrayList<String> idtestprofiles = new ArrayList<String>();
	ArrayList<Test> tests = new ArrayList<Test>();
	//System.out.println("1");
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
		
		rset = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+session.getAttribute("classid")+"';");
		System.out.println("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+session.getAttribute("classid")+"';");
		while(rset.next()){//while there are more tests.
			idtestprofiles.add(rset.getString("idtest"));
		}
		System.out.println(idtestprofiles);
		for(String id:idtestprofiles){	
			ResultSet testinfo = st.executeQuery("SELECT * FROM testersitedatabase.testprofiles WHERE idtest = '"+id+"';");//looking for that particular test and getting its info 
			//System.out.println("2");
			if(testinfo.next()){//if found we do
				Test newtest = new Test();
				//System.out.println("3");
				newtest.setTestId(id);
				newtest.setTestName(testinfo.getString("testname"));
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
                     <h2><%out.println(courseprefix + coursenumber); %> | <%out.println(coursename); %></h2>   
                     <p style="font-size:25px;">Tests</p> <a href>help</a>
                    </div>
                </div>
                  <form action="BackToClassOptions" method="get">
				  	<button name="action" value="back">Back</button> 
				  </form>                              
                 <!-- /. ROW  -->
                  <hr /> <!-- adds line -->
                  <form action="CreateTestLinker">
						<input type="submit" value="Build Test" >
				  </form>
                  <hr /> <!-- adds line -->
                  
				  <form action = "ShowExistingTestServlet" method="get">
				  <%for(Test test: tests){ %>
				  	<button type="submit" name="idtest" value="<%out.println(test.getTestId());%>"><%out.println(test.getTestName()); %></button> <br> 
				  <%} %>
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