<!-- links: 
	ClassTest.jsp -> ShowExistingTestServlet->TestShow.jsp
	//TODO
	/* Professor problem: Student joins late but gets the same amount of time as the student who joins on time.
	/* open time where students can start to join. close time where student can no longer join the test.
	//show all questions/show one by one
	//scramble, randomize
	//prevent back button
	matching Question
	Multipart Questions
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
	//System.out.println("idtest="+request.getAttribute("idtest"));
	//System.out.println("questiontitle="+request.getAttribute("questiontitle"));
	//System.out.println("questiontext="+request.getAttribute("questiontext"));
	//System.out.println("pointsworth="+request.getAttribute("pointsworth"));
	//System.out.println("casesensistive="+request.getAttribute("casesensistive"));
	//System.out.println("allowPartialCredit="+request.getAttribute("allowPartialCredit"));
	//System.out.println("blank="+request.getAttribute("blank"));
	ArrayList<String> blank = (ArrayList<String>) request.getAttribute("blank"); //arraylist with all the blank variables aka the x in [x] or the blank in [blank]
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
                    <h1></h1>
                     <p style="font-size:25px;">Add Fill In Multiple Blanks Question</p> <a href="">help</a>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr /> <!-- adds line -->
                  <span style="color: red;">${error}</span>
					<form action = "QuestionfillinmultipleblankServlet" method="post">
						Question Title: ${questiontitle}    Pts Worth:${pointsworth }  <br>
						Question text: <% out.println(request.getAttribute("questiontext")); %>
					<hr />
						*1 indicates True, 0 indicates False* <br>
						Case Sensitive: ${casesensistive}<br>
						Allow Partial Credit: ${allowPartialCredit} <br>
					<hr />
					Answers for <% out.println(blank.toString()); %>: (Separate answers by the '~' char and no spaces ex. Apples~Oranges~Grapes)<br> 
					<% String blankstring = "";
					for(String x : blank){
						blankstring += x+",";
						out.println("Answers for "+x+":<br>");
						%>
						<textarea name="<%=x%>" cols="100" rows="2" required>${x}</textarea> <br>
						<%
					}
					%>
					<%-- <textarea name="a" cols="100" rows="2">${x}</textarea> <br> --%>
					<input type="hidden" name="idtest" value="${idtest}">
					<input type="hidden" name="questiontitle" value="${questiontitle}">
					<input type="hidden" name="questiontext" value="${questiontext}">
					<input type="hidden" name="pointsworth" value="${pointsworth}">
					<input type="hidden" name="casesensistive" value="${casesensistive}">
					<input type="hidden" name="allowPartialCredit" value="${allowPartialCredit}">
					<input type="hidden" name="blank" value="<%out.println(blankstring);%>">
					<button name="action" value="another">Submit and Create Another</button> 
					<button name="action" value="submit">Submit</button> 
					</form>
				  <form action="MultipleChoiceBack" method="get">
				  	<button name="action" value="back">Back</button> 
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
