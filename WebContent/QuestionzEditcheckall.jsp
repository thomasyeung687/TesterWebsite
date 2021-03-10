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
<%@page import="com.testersite.model.CheckAllQuestion"%>
<%@page import="com.testersite.model.MultipleChoiceQuestion"%>
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
	System.out.println("idquestion="+request.getAttribute("idquestion"));
	String idquestion = (String) request.getAttribute("idquestion");
	if(idquestion == null){
		System.out.println("getting from attribute");
		idquestion = (String) request.getAttribute("idquestion");
	}
	ResultSet rset2;
	Connection con = DBConnection.getDBConnection();
	CheckAllQuestion ca= null;
	try{
		Statement st = con.createStatement();
		rset2 = st.executeQuery("SELECT * FROM questionsdatabase.checkall WHERE idquestion = '"+idquestion+"';");
		if(rset2.next()){
			ca = new CheckAllQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"),rset2.getString("answerstring"), rset2.getString("correctstring"));
		}else{
			System.out.println("Failed to get ca question!");
		}
		//rset for other questiontypes V
	}catch(Exception exception){
		System.out.println(exception.getMessage());
	}
	System.out.println(ca.toString());
	int pointsworth = ca.getPointsWorth();
	int amtofquestions = ca.getAnswers().size();
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
                     <p style="font-size:25px;">Edit Check All Question</p> <a href="">help</a>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr /> <!-- adds line -->
                  <span style = "color: red"> ${error} </span>
					<form action = "EditQuestioncheckallServlet" method="post">
						Question Title <input type="text" name="questiontitle" value="<%out.println(ca.getQuestiontitle());%>" required>
						Points Worth  <input type="number" name="pointsworth" value="<%= pointsworth %>" required><br>
						Question<br>  <textarea name="questiontext" cols="100" rows="2" required><%out.println(ca.getQuestion());%></textarea> <br>
					<hr />
						<% List<String> answers = ca.getAnswers();
						for(int i = 0; i<answers.size();i++){
							String answer = answers.get(i);
							if(ca.getCorrectanswers().contains(answer)){%>
								<input type="checkbox" name="<%= "check"+i%>" value="<%= "ans"+i%>" checked>
								Answer <%out.println(i+1);%><input type="text" name="<%= "ans"+i%>" required style="width: 50em" value="<% out.println(answer);%>"> <br>
							<%}else{ %>
								<input type="checkbox" name="<%= "check"+i%>" value="<%= "ans"+i%>" >
								Answer <%out.println(i+1);%><input type="text" name="<%= "ans"+i%>" required style="width: 50em" value="<% out.println(answer);%>"> <br>
							<%}
						}
						%>
						<input type="hidden" name="amtofquestions" value="<%=amtofquestions%>">
 						<input type="hidden" name="idquestion" value="<%out.println(ca.getQuestionid());%>">
						 <button name="action" value="submit">Edit Question</button>
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
