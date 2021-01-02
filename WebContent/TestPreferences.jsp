<!-- links: 
	ClassTest.jsp -> ShowExistingTestServlet->TestShow.jsp
	//TODO
	/* Professor problem: Student joins late but gets the same amount of time as the student who joins on time.
	/* open time where students can start to join. close time where student can no longer join the test.
	//show all questions/show one by one
	//scramble, randomize
	//prevent back button
	//matching Question
	//Multipart Questions
	The display after and until need to be changed in Test and the sql and so on.
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
<script type="text/javascript">

</script>
<body>
	<%
		//response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		//System.out.println(session.getAttribute("username"));
		//if(session.getAttribute("username")==null || session.getAttribute("professor")==null){
		//	response.sendRedirect("LoginProf.jsp");
		//}
	%>
	<%Connection con = DBConnection.getDBConnection();
	String idtest = (String)session.getAttribute("idtest");
	System.out.println("idtest in testpreferences: "+idtest);
	Test test = null;
	try {
		Statement st = con.createStatement();
		String query = "SELECT * FROM testersitedatabase.testprofiles WHERE idtest = '"+idtest+"';";
		ResultSet rset = st.executeQuery(query); //getting the test profile of the test that was clicked on ClassTests.jsp
		if(rset.next()) {
			String testid = rset.getString("idtest");
			String testName = rset.getString("testname");
			String testDescription = rset.getString("testdescription");
			String testInstructions = rset.getString("testinstructions");
			String testDateStart = rset.getString("testdatestart");
			String displaystart = rset.getString("displaystart");
			String displayend = rset.getString("displayend");
			String testDateEnd = rset.getString("testdateend");
			boolean availibility = rset.getBoolean("availibility");
			boolean forcedComplete = rset.getBoolean("forcedCompletion");
			boolean allowBackButton =rset.getBoolean("allowbackbutton");
			boolean scrambleTest = rset.getBoolean("scrambletest");
			boolean showQuestionOnebyOne = rset.getBoolean("showquestiononebyone");
			int timelimit = rset.getInt("timelimit");
			int amtOfAttempts = rset.getInt("amtofattempts");
			test = new Test(testName, testDateStart,displaystart, displayend,  testDateEnd, testid, testDescription, testInstructions,  availibility,  forcedComplete,allowBackButton,scrambleTest,showQuestionOnebyOne,  timelimit,  amtOfAttempts);
			System.out.println(test.toString());
			//request.setAttribute("testobject", test);
		}else {
			System.out.println("ShowExistingTestServlet: TEST NOT FOUND");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}%>
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
                    <h1></h1>
                     <p style="font-size:25px;">Update Test Preferences : <%out.println(test.getTestName()); %></p> <a href>help</a>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr /> <!-- adds line -->
                  <h4>To add questions, go back.</h4>
					<form action="TestPreferencesBack" method="get">
				  		<button name="action" value="back">Back</button> 						
					</form>
                  <hr /> <!-- adds line -->
				  <form action = "UpdatetestPreferencesServlet" method="post">
                  <span style =  "color: red;"> ${error} </span><br>
				  	Test Name: <input type="text" value="<%out.println(test.getTestName()); %>" name="updatedtestname"> <br>
				  	<hr /> <!-- adds line -->
				  	Test Description: <textarea rows="2" cols="75" name="updatedtestdescription"><%out.println(test.getTestDescription()); %></textarea> <br>
				  	<hr /> <!-- adds line -->
				  	Test Instructions: <textarea rows="2" cols="75"  name="updatedtestinstructions"><%out.println(test.getTestInstructions()); %> </textarea> <br>
				  	<hr /> <!-- adds line -->
				  	Date Start: <input type="date" value="<%out.print(test.getTestDateStart()); %>" name="updatedtestdatestart"> <br>
				  	Display after : <input type="time" value="<%out.print(test.getDisplaystart()); %>" name="updateddisplaystart"> *12:00AM is the start of date start*<br>
				  	Display until : <input type="time" value="<%out.print(test.getDisplayend()); %>" name="updateddisplayend"> *11:59PM is the end of date start*<br>
				  	Date End: <input type="date" value="<%out.print(test.gettestDateEnd()); %>" name="updatedtestdateend"> <br>
				  	<hr /> <!-- adds line -->
				  	Availability: <% if(test.isAvailibility()){%>
				  	<select name="updatedavailability">
				  		<option value="1">Available</option>
				  		<option value="0">Unavailable</option>
				  	</select>
				  	<%}else{%>
				  	<select name="updatedavailability">
				  		<option value="0">Unavailable</option>
				  		<option value="1">Available</option>
				  	</select>
				  	<%}%> 
				  	
				  	<br>
				  	Forced Completion: <% if(test.isForcedCompletion()){%>
				  	<select name="updatedforcecomplete">
				  		<option value="1">True</option>
				  		<option value="0">False</option>
				  	</select>
				  	<%}else{%>
				  	<select name="updatedforcecomplete">
				  		<option value="0">False</option>
				  		<option value="1">True</option>
				  	</select>
				  	<%}%> 
				  	<br>
				  	Allow Back Button: <% if(test.getAllowBackButton()){%>
				  	<select name="updatedallowbackbutton">
				  		<option value="1">True</option>
				  		<option value="0">False</option>
				  	</select>
				  	<%}else{%>
				  	<select name="updatedallowbackbutton">
				  		<option value="0">False</option>
				  		<option value="1">True</option>
				  	</select>
				  	<%}%> 
				  	<br>
				  	Scramble Questions: <% if(test.isScrambleTest()){%>
				  	<select name="updatedscrambletest">
				  		<option value="1">True</option>
				  		<option value="0">False</option>
				  	</select>
				  	<%}else{%>
				  	<select name="updatedscrambletest">
				  		<option value="0">False</option>
				  		<option value="1">True</option>
				  	</select>
				  	<%}%> 
				  	<br>
				  	Show Questions One by One: <% if(test.isShowQuestionOnebyOne()){%>
				  	<select name="updatedshowquestions1b1">
				  		<option value="1">True</option>
				  		<option value="0">False</option>
				  	</select>
				  	<%}else{%>
				  	<select name="updatedshowquestions1b1">
				  		<option value="0">False</option>
				  		<option value="1">True</option>
				  	</select>
				  	<%}%> 
				  	<br>
				  	
				  	
				  	<hr /> <!-- adds line -->
				  	Time Limit (minutes): <input type="text" value="<%out.println(test.getTimelimit()); %>" name="updatedtimelimit"> <br>
				  	Amount of Attempts: <input type="text" value="<%out.println(test.getAmtOfAttempts()); %>" name="updatedamountofattempts"> <br>
				  	<input type="submit" value="Save Preferences">
				  </form>
				  
				  <hr /> <!-- adds line -->
				  
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
