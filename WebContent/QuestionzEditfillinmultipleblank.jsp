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
<%@page import="com.testersite.model.FillInMultipleBlankQuestion"%>
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
	FillInMultipleBlankQuestion fimb= null;
	try{
		Statement st = con.createStatement();
		rset2 = st.executeQuery("SELECT * FROM questionsdatabase.fillinmultipleblank WHERE idquestion = '"+idquestion+"';");
		if(rset2.next()){
			fimb = new FillInMultipleBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"),rset2.getBoolean("partialcredit"));
		}else{
			System.out.println("Failed to get fimb question! in QuestionzEditfillinmultipleblank.jsp");
		}
	}catch(Exception exception){
		System.out.println(exception.getMessage());
	}
	System.out.println(fimb.toString());
	int pointsworth = fimb.getPointsWorth();
	ArrayList<String> blanks = fimb.getBlank(); //this is the amount of blanks there are.
	List<String> correctans = fimb.getEachBlanksCorrectAnswers(); //this is the nested list list that contains the correct answers for each element in blank
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
                     <p style="font-size:25px;">Edit Fill In Multiple Blanks Question</p> <a href="">help</a>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr /> <!-- adds line -->
                  <span style="color: red;">${error}</span>
					<form action = "EditQuestionfillinmultipleblankServlet" method="post">
						Question Title <input type="text" name="questiontitle" value="<%= fimb.getQuestiontitle() %>" required > Pts Worth <input type="number" name="pointsworth" value="<%= pointsworth %>" min='1' required> </br>
						<!-- Question  <input type="text" name="questiontext" required style="height:100px; width:350px;"> -->
						Question text: (use [x] to indicate where to put the blank. ex. The [x] cat ate its food.)<br> 
						<textarea name="questiontext" cols="100" rows="5" ><%= fimb.getQuestion() %></textarea>
					<hr />
					Answers for <% out.println(blanks.toString()); %>: (Separate answers by the '~' char and no spaces ex. Apples~Oranges~Grapes)<br> 
					<% String blankstring = "";
					for(int i = 0; i < blanks.size() ; i++){
						String x = blanks.get(i);
						blankstring += x+",";
						out.println("Answers for "+x+":<br>");
						%>
						<textarea name="<%=x%>" cols="100" rows="2" required><%out.println(correctans.get(i)); %></textarea> <br>
						<%
					}
					%>
					*1 indicates True, 0 indicates False* <br>
						Case Sensitive <input type="checkbox" name="casesensistive" <%if(fimb.isCasesensitive()){out.println("checked");} %>> <br>
						Allow Partial Credit <input type="checkbox" name="allowPartialCredit" <%if(fimb.isPartialcredit()){out.println("checked");} %>> <br>
					<hr />
					<%-- <textarea name="a" cols="100" rows="2">${x}</textarea> <br> --%>
					<input type="hidden" name="idquestion" value="<%=fimb.getQuestionid() %>">
					<input type="hidden" name="blank" value="<%out.println(blankstring);%>">
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
