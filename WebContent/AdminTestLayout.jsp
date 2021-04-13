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
<%@page import="com.testersite.model.TesterClass"%>
<%@page import="com.testersite.model.MultipartQuestion"%>
<%@page import="com.testersite.model.FillInMultipleBlankQuestion"%>
<%@page import="com.testersite.model.FillInTheBlankQuestion"%>
<%@page import="com.testersite.model.CheckAllQuestion"%>
<%@page import="com.testersite.model.ShortResponseQuestion"%>
<%@page import="com.testersite.model.TFQuestion"%>
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
<script type="text/javascript">
	if ( window.history.replaceState ) {
	  window.history.replaceState( null, null, window.location.href );
	}
	 function changeDropdown(){
		var state=document.getElementById("questiontype").value;
		if(state=="multiplechoice"){
			document.getElementById("amtofquestions").style.visibility="visible";
			document.getElementById("numbertext").innerHTML="Enter Amount of Answer Choices"
		}else if(state=="checkall"){
			document.getElementById("amtofquestions").style.visibility="visible";
			document.getElementById("numbertext").innerHTML="Enter Amount of Answer Choices"
		}else{
			document.getElementById("amtofquestions").style.visibility="hidden";
			document.getElementById("numbertext").innerHTML=""
		}
	} 
	function confirmButton(){
		var r = prompt("Type 'DELETE THIS TEST' to delete this test. (All caps and leave out '')")
		if(r != "DELETE THIS TEST"){
			alert("Incorrect String.");
		}else{
			deletetest.submit();
		}
	}
</script>
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
	<%
	Connection con = DBConnection.getDBConnection();
	String idtest = (String)session.getAttribute("idtest");
	System.out.println(idtest);
	Test test = Test.getTestFromDB(idtest);
	ArrayList<Question> questions = test.getQuestionArray();
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
        <!-- /. NAV SIDE  -->
        <div id="page-wrapper" >
            <div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                    <h1></h1>
                     <p style="font-size:25px;">Test Layout : <%out.println(test.getTestName()); %></p> 
                     <!-- <form action="DeleteTestServlet" method="post" name="deletetest"> -->
                     <form action="" method="post" name="deletetest">
                     	<input type="hidden" name="idtest" value="<%out.println(test.getTestId());%>">
                     	<button type="button"  onclick="confirmButton()">Delete This Test</button>
                     </form>
                     <a href>help</a>
                    </div>
                </div>              
                 <!-- /. ROW  -->
                 <hr /> <!-- adds line -->
                  Description: <%out.println(test.getTestDescription()); %> </br>
                  Instructions: <%out.println(test.getTestInstructions()); %> </br>
                  Amount of Questions: <%out.println(test.getQuestionArray().size()); %>
                  <form action="UpdateTestPreferencesLinker" method="get">
                  	<button type="submit" name="idtest" value="<%out.println(test.getTestId());%>">Edit Test Preferences</button> 
                  </form>
                  <hr /> <!-- adds line -->
                  <form action = "AddQuestionLinker" method="get">
				  	<button type="submit" name="idtest" value="<%out.println(test.getTestId());%>">Add Question</button> 
				  	<select id="questiontype" name="questiontype" onchange="changeDropdown(this.value);">
				  		<option value="multiplechoice">Multiple Choice</option>
				  		<option value="checkall">Check All</option>
				  		<option value="fillintheblank">Fill in the Blank </option>
				  		<option value="fillinmultipleblank">Fill in Multiple Blanks </option>
				  		<option value="truefalse">True/False </option>
				  		<option value="shortresponse">Short Response</option>
 				  		<option value="multipart">Multipart Question</option>
				  		<!--  <option value="matching">Matching Question</option> -->
				  	</select>
				  	</br>
				  	<span id="numbertext">Enter Amount of Answer Choices</span>
				  	<input type="number" id="amtofquestions" name="amtofquestions" min="2" max="10" value="3" style="width: 7em" > 
				  </form>
                  <hr />
				  <form action="DeleteOrEditQuestionServlet" method="post">
				  <% for(int n = 0; n<questions.size(); n++){ 
					  Question question = questions.get(n);
					  //System.out.println(question.getQuestion());
				  %>
				  
				  		<input type="radio" name="selectedquestion" value="<%out.println(question.getQuestionid());%>" required>
				  		<span style="font-size: 150%"><%out.println(question.getQuestionType()); %>: <%out.println(question.getQuestiontitle()); %></span>
				  		<span>Points: <%out.println(question.getPointsWorth()); %></span> <br>
						<span style="font-size: 150%; white-space: pre-wrap;">Question: <%out.println(question.getQuestion()); %></span></br>
						<%if(question instanceof MultipleChoiceQuestion){ 
							MultipleChoiceQuestion mc = (MultipleChoiceQuestion) question;
							//System.out.println(mc.getAnswers().toString());
							List<String> anschoices = mc.getAnswers();
							for(String s : anschoices){
								//System.out.println(mc.getCorrectAns());
								if(s.equals(mc.getCorrectAnswerString())){%>
									<span>&#10004; <%out.println(s); %></span></br>
								<%}else{%>
									<span><%out.println(s); %></span></br>
								<%}
							}
						}else if(question instanceof TFQuestion){
							TFQuestion tf = (TFQuestion) question;
							//System.out.println(tf.getAnswers().toString());
							List<String> anschoices = Arrays.asList("True", "False");
							for(String s: anschoices){
								//System.out.println(tf.getCorrectAns());
								if(s.equals(tf.getCorrectAnswerString())){%>
									<span>&#10004; <%out.println(s); %></span></br>
								<%}else{%>
									<span><%out.println(s); %></span></br>
								<%}
							}
							
						}else if(question instanceof ShortResponseQuestion){
							ShortResponseQuestion sr = (ShortResponseQuestion) question;
							//do stuff if its a shortresponse.
						}else if(question instanceof CheckAllQuestion){
							CheckAllQuestion ca = (CheckAllQuestion) question;
							List<String> anschoices = ca.getAnswers();
							List<String> correctans = ca.getCorrectanswers();
							for(String s: anschoices){
								//System.out.println(tf.getCorrectAns());
								if(correctans.contains(s)){%>
									<span>&#10004; <%out.println(s); %></span></br>
								<%}else{%>
									<span><%out.println(s); %></span></br>
								<%}
							}
						}else if(question instanceof FillInTheBlankQuestion){
							FillInTheBlankQuestion fib = (FillInTheBlankQuestion) question;
							List<String> correctans = fib.getCorrectans();
							out.println(fib.getStr1());%>
							<input type="text">
							<% out.println(fib.getStr2() +"<br>");
							for(String s: correctans){
								//System.out.println(tf.getCorrectAns());%>
									<span>&#10004; <%out.println(s); %></span>
							<%
							}
							if(fib.isCasesensitive()){%>
								</br>
								<span>Case Sensitive: &#10004;</span>	
							<%}else{%>
								</br>
								<span>Case Sensitive: </span>
							<%}
						}else if(question instanceof FillInMultipleBlankQuestion){
							FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) question;
							List<List<String>> correctans = fimb.getCorrectans();
							ArrayList<String> strings = fimb.getStrings();
							for(int i = 0; i < strings.size()-1; i++){
								out.println(strings.get(i));
								%><input type="text"> <%
							}
							out.println(strings.get(strings.size()-1)+"<br>");
							int blankindex =0; 
							for(List<String> lst : correctans){
								 out.println("Correct answers for "+ fimb.getBlank().get(blankindex++)+": </br>");
								for(String s: lst){
									//System.out.println(tf.getCorrectAns());%>
										<span>&#10004; <%out.println(s); %></span>
								<%
								}
								out.println("<br>");
							}
							if(fimb.isCasesensitive()){%>
							</br>
							<span>Case Sensitive: &#10004;</span>	
						<%}else{%>
							</br>
							<span>Case Sensitive: </span>
						<%}
							if(fimb.isPartialcredit()){%>
							</br>
							<span>Partial Credit: &#10004;</span>	
						<%}else{%>
							</br>
							<span>Partial Credit: </span>
						<%}
						}else if(question instanceof MultipartQuestion){
							MultipartQuestion multi = (MultipartQuestion) question;
							String questionids = multi.getQuestions().toString();
							%>
							<%-- <span><%out.print(questionids); %> </span> --%>
						<%}
						//here check for other types of questions %>
				  		<hr />
				  <%} %>
				  	<button name="action" value="edit">Edit Selected</button> 
					<button name="action" value="Delete">Delete Selected</button> 
				  </form>
				  <form method="get" action="AdminBackButtons">
						<button name="pageName" value="AdminShowCompletedTestPage">Back</button> 
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
