 <!-- This page is the page Professor accounts use to access the grades of his students. (grading  --> 
  <!DOCTYPE html>
<%@page import="com.testersite.model.*"%>
<%@page import="com.testersite.model.Question"%>
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
    <link href="assets/css/ShowCompletedTestPage.css" rel="stylesheet" />
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   
  	<link rel="stylesheet" href="assets/css/questions.css">
</head>
<body>
    <%
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");//this prevents backbutton hack
		System.out.println(session.getAttribute("username"));
		if(session.getAttribute("username")==null || session.getAttribute("professor")==null){
			response.sendRedirect("LoginProf.jsp");
			return;
		}
	%>
    <%
    	Connection connection = DBConnection.getDBConnection(); 
   	 	ClassObject thisclass = (ClassObject) session.getAttribute("thisclass"); //the class object created in ShowClassServlet
    	Test thisTest = (Test) session.getAttribute("thistest");
    	
    	ArrayList<Question> testQuestions = new ArrayList<>(); //arraylist of test objects of available tests.
    	
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
                        <a href="CreatorOptions.jsp" ><i class="fa fa-desktop "></i>Dashboard <span class="badge"></span></a>
                    </li>
                    
                    <li class="active-link">
                        <a href="YourClasses.jsp"><i class="fa fa-edit "></i>Class  <span class="badge"></span></a>
                    </li>
                    
					<!-- <li >
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
                    </li> -->
                </ul>
            </div>
        </nav>
        <div id="page-wrapper" >
        	<div id="page-inner">
                <div class="row">
                    <div class="col-md-12">
                    <h2><%out.println(thisTest.getTestName()); %></h2>
					<hr>
					<div class="descriptionWrapper">
						Description: <%out.println(thisTest.getTestDescription()); %> </br>
						<hr/>
	                  	Instructions: <%out.println(thisTest.getTestInstructions()); %> </br>
	                  	<hr/>
	                  	Amount of Questions: <%out.println(thisTest.getQuestionArray().size()); %>
	                  	<hr/>
	                  	Grade Received: <%out.println(thisTest.getTotalPtsReceived()+"/"+thisTest.getTotalPts()); %>
                  	</div>
                  	<hr/>
					<div class="TestPageTestQuestionsDiv">
					
					
					<form action="GradeTestServlet" method="post">
					  <% for(int n = 1; n<thisTest.getQuestionArray().size()+1; n++){
						  Question question = thisTest.getQuestionArray().get(n-1); //no idea why this works
						  //System.out.println(question.getQuestion());
					  %>
					  
					  		<%-- <input type="radio" name="selectedquestion" value="<%out.println(question.getQuestionid());%>" required> --%>
					  		<%-- <span style="font-size: 150%"><%out.println(question.getQuestionType()); %>: <%out.println(question.getQuestiontitle()); %></span> --%>
					  		
					  		<div class="questionWrapper">
					  			<div>
						  			<div class="questionTextWrapper">
										<span style="white-space: pre-wrap; "><%out.print(n); %>) <%out.print(question.getQuestion().trim()); %> </span> <!-- allows for space to be preserved for say if the question is a clump of code -->
									</div>
									<div class="ptsWorthWrapper">
										<% if(question instanceof MultipartQuestion){ %>
											Points:  <input class="ptsInput" type="number" value="<%=question.calculatePtsReceived()%>" name="<%=question.getQuestionid()%>" disabled>
										<%}else{ %>
											Points:  <input class="ptsInput" type="number" value="<%=question.calculatePtsReceived()%>" name="<%=question.getQuestionid()%>">
										<%} %>
										<span style="">/<%out.print(question.getPointsWorth()); %></span>
									</div>
								</div>
							
							<%if(question instanceof MultipleChoiceQuestion){ 
								MultipleChoiceQuestion mc = (MultipleChoiceQuestion) question;
								//System.out.println(mc.getAnswers().toString());
								List<String> anschoices = mc.getAnswers();
								for(String s : anschoices){
									//System.out.println(mc.getCorrectAns());
									if(s.equals(question.getAnswerChosen())){
										%> <input type="radio" name="q<%=n %>" value="<%out.println(s); %>" required checked readonly> <%out.println(s); %><br> <%
									}else{
									%> <input type="radio" name="q<%=n %>" value="<%out.println(s); %>" required disabled> <%out.println(s); %><br> <%
									}
								}
							}else if(question instanceof TFQuestion){
								TFQuestion tf = (TFQuestion) question;
								//System.out.println(tf.getAnswers().toString());]
								if(tf.getAnswerChosen().equals("True")){
									%> 	<input type="radio" name="q<%=n %>" value="True" required disabled checked> True<br> 
										<input type="radio" name="q<%=n %>" value="False" required disabled> False<br> 
									<%
								}else{
									%> 	<input type="radio" name="q<%=n %>" value="True" required disabled> True<br> 
										<input type="radio" name="q<%=n %>" value="False" required disabled checked> False<br> 
									<%
								}
								
							}else if(question instanceof ShortResponseQuestion){
								ShortResponseQuestion sr = (ShortResponseQuestion) question;
								//do stuff if its a shortresponse.
								%> <textarea class="shortresponsequestion" name="q<%=n %>"><%out.println(question.getAnswerChosen()); %></textarea> <br> <%
							}else if(question instanceof CheckAllQuestion){
								CheckAllQuestion ca = (CheckAllQuestion) question;
								List<String> anschoices = ca.getAnswers();
								List<String> correctans = ca.getCorrectanswers();
								for(String s: anschoices){
									//System.out.println(tf.getCorrectAns());
									if(ca.getAnswersGiven().contains(s)){
										%> <input type="checkbox" name="q<%=n %>" value="<%out.println(s); %>" required checked disabled> <%out.println(s); %><br> <%
									}else{
										%> <input type="checkbox" name="q<%=n %>" value="<%out.println(s); %>" required disabled> <%out.println(s); %><br> <%
									}
								}
							}else if(question instanceof FillInTheBlankQuestion){
								FillInTheBlankQuestion fib = (FillInTheBlankQuestion) question;
								List<String> correctans = fib.getCorrectans();
								out.println(fib.getStr1());%>
								<input type="text" class="fiblank" name="q<%=n %>" placeholder="<%out.println(fib.getAnswerChosen()); %>" readonly>
								<% out.println(fib.getStr2() +"<br>");
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
								ArrayList<String> blank = fimb.getBlank();
								for(int i = 0; i < strings.size()-1; i++){
									out.println(strings.get(i));
									%><input type="text" class="fiblank" name="q<%=n+blank.get(i)%>" placeholder="<%out.println(fimb.getAnswersGiven().get(i));%>" readonly> <%
								}
								out.println(strings.get(strings.size()-1)+"<br>");
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
								/* String questionids = multi.getQuestions().toString(); */
								String questionids = multi.getQuestionCompoentids();
								
								ArrayList<Question> questions = multi.getQuestions();
								int questionComponentNumber = 1; //questionComponent number. 
								
								for(Question questionComponent : questions){ //for each question in the multipart question, display it properly. questionComponent is basiscally a question in the multipart question
									String questionNum = n+"."+questionComponentNumber++; //will be used as the name of the form input of the question in the request to retreive the ans choices to the question components in the multi question
									%>
									<div class="multipartQuestionComponentWrapper">
										<div>
								  			<div class="questionTextWrapper">
												<span style=" "><%out.print(questionNum); %>) <%out.print(questionComponent.getQuestion().trim()); %> </span> <!-- allows for space to be preserved for say if the question is a clump of code -->
											</div>
											<div class="ptsWorthWrapper">
												Points:  <input class="ptsInput" type="number" step="0.01" value="<%=questionComponent.calculatePtsReceived()%>" name="<%=question.getQuestionid()%>">
												<span style="">/<%out.print(questionComponent.getPointsWorth()); %></span>
											</div>
										</div>
									
									
									<%
									if(questionComponent instanceof MultipleChoiceQuestion){ 
										MultipleChoiceQuestion mc = (MultipleChoiceQuestion) questionComponent;
										//System.out.println(mc.getAnswers().toString());
										List<String> anschoices = mc.getAnswers();
										for(String s : anschoices){
											//System.out.println(mc.getCorrectAns());
											if(s.equals(mc.getAnswerChosen())){
												%> <input type="radio" name="q<%=questionNum %>" value="<%out.println(s); %>" required checked disabled> <%out.println(s); %><br> <%
											}else{
											%> <input type="radio" name="q<%=questionNum %>" value="<%out.println(s); %>" required disabled> <%out.println(s); %><br> <%
											}
										}
									}else if(questionComponent instanceof TFQuestion){
										TFQuestion tf = (TFQuestion) questionComponent;
										//System.out.println(tf.getAnswers().toString());
										if(tf.getAnswerChosen().equals("True")){
											%> 	<input type="radio" name="q<%=questionNum %>" value="True" required disabled checked> True<br> 
												<input type="radio" name="q<%=questionNum %>" value="False" required disabled> False<br> 
											<%
										}else{
											%> 	<input type="radio" name="q<%=questionNum %>" value="True" required disabled> True<br> 
												<input type="radio" name="q<%=questionNum %>" value="False" required disabled checked> False<br> 
											<%
										}
										
									}else if(questionComponent instanceof ShortResponseQuestion){
										ShortResponseQuestion sr = (ShortResponseQuestion) question;
										//do stuff if its a shortresponse.
										%> <textarea class="shortresponsequestion" name="q<%=n %>"><%out.println(sr.getAnswerChosen()); %></textarea> <br> <%
									}else if(questionComponent instanceof CheckAllQuestion){
										CheckAllQuestion ca = (CheckAllQuestion) questionComponent;
										List<String> anschoices = ca.getAnswers();
										List<String> correctans = ca.getCorrectanswers();
										for(String s: anschoices){
											//System.out.println(tf.getCorrectAns());
											if(s.equals(ca.getAnswerChosen())){
												%> <input type="checkbox" name="q<%=questionNum %>" value="<%out.println(s); %>" required checked disabled> <%out.println(s); %><br> <%
											}else{
												%> <input type="checkbox" name="q<%=questionNum %>" value="<%out.println(s); %>" required disabled> <%out.println(s); %><br> <%
											}
										}
									}else if(questionComponent instanceof FillInTheBlankQuestion){
										FillInTheBlankQuestion fib = (FillInTheBlankQuestion) questionComponent;
										List<String> correctans = fib.getCorrectans();
										out.println(fib.getStr1());%>
										<input type="text" class="fiblank" name="q<%=questionNum %>" placeholder="<%out.println(fib.getAnswerChosen()); %>" readonly>
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
									}else if(questionComponent instanceof FillInMultipleBlankQuestion){
										FillInMultipleBlankQuestion fimb = (FillInMultipleBlankQuestion) questionComponent;
										List<List<String>> correctans = fimb.getCorrectans();
										ArrayList<String> blank = fimb.getBlank();
										ArrayList<String> strings = fimb.getStrings();
										for(int i = 0; i < strings.size()-1; i++){
											out.println(strings.get(i));
											String inputname = questionNum+blank.get(i);
											%><input type="text" class="fiblank" name="q<%=inputname%>" placeholder="<%out.println(fimb.getAnswersGiven().get(i));%>" readonly> <%
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
									}
									%>
									</div> <!-- ending div for multipartQuestionComponentWrapper -->
									<%
								}
								
							}
							//here check for other types of questions %>
					  		</div>
					  <%} %>
					  	<input type="submit" class="SShowTestbutton" name="action" value="Prev Student">
					  	<input type="submit" class="SShowTestbutton" name="action" value="Back">
					  	<input type="submit" class="SShowTestbutton" name="action" value="Submit Grade">
					  	<input type="submit" class="SShowTestbutton" name="action" value="Next Student">
					  </form>
					  
					  
					  
					  
					  
					  
					  <!-- These divs are closings for the first 5 divs -->
					  </div> <!-- <div class="TestPageTestQuestionsDiv"> closing div -->
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
 