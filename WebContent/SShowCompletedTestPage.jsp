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
     <!-- GOOGLE FONTS-->
   <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css' />
   
  	<link rel="stylesheet" href="assets/css/questions.css">
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
   	 	ClassObject thisclass = (ClassObject) session.getAttribute("thisclass"); //the class object created in ShowClassServlet
    	Test thisTest = (Test) session.getAttribute("thistest"); //the test object created in TakeTestServlet
    	
    	ArrayList<Question> testQuestions = new ArrayList<>(); //arraylist of test objects of available tests.
    	
    	if(thisclass==null || thisTest==null){
			response.sendRedirect("SClasses.jsp");
			return;
		}
    	
    	/* try {
			Statement st = connection.createStatement();
			ResultSet rSet;
			rSet = st.executeQuery("SELECT * FROM testersitedatabase.testdns WHERE idclass = '"+classid+"'"); //getting all tests in testdns 

			Statement st1 = connection.createStatement(); //used to get the actual test information using testid from rSet
			ResultSet rSet1;
			
			
			
			while(rSet.next()){
				
				String idtest = rSet.getString("idtest");
				
				//System.out.println("tests?id = "+idtest);
				
				rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.testprofiles where idtest = '"+idtest+"'");
				rSet1.next();
				if(rSet1.getBoolean("availibility")){ //if avalible, creates test object with information and adds to test arraylist availibleTests
					Test test = new Test();
					test.setTestId(rSet1.getString("idtest"));
					test.setDisplaystart(rSet1.getString("displaystart")); //display start and end can be used later on with conjunction with a function in test that determins whether test should be displayed or not.
					test.setDisplaystart(rSet1.getString("displayend"));
					test.setTestName(rSet1.getString("testname"));
					test.setTestDescription(rSet1.getString("testdescription"));
					test.settestDateEnd(rSet1.getString("testdateend"));
					availibleTests.add(test);
				}
			}
			//all availible tests have been added to availibleTests
			
			
    	}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("SShowClass.jsp SQL ERROR");
			out.print("SShowClass.jsp SQL ERROR");
		} */
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
                    <h2><%out.println(thisTest.getTestName()); %></h2>
					<hr>
					<div class="descriptionWrapper">
						Description: <%out.println(thisTest.getTestDescription()); %> </br>
						<hr/>
	                  	Instructions: <%out.println(thisTest.getTestInstructions()); %> </br>
	                  	<hr/>
	                  	Amount of Questions: <%out.println(thisTest.getQuestionArray().size()); %>
	                  	<hr/>
	                  	Grade Received: <%out.println(thisTest.getTotalPtsReceived()); %> / <%out.println(thisTest.getTotalPts()); %> <br>
	                  	Calculated: <%out.println(thisTest.getAttempts().get(0).getPercentageScore()+"%"); %>
                  	</div>
                  	<hr/>
					<div class="TestPageTestQuestionsDiv">
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
									<span style="">Points: <%out.print(question.getPointsWorth()); %></span>
									<span style="">Points: <%out.print(question.calculatePtsReceived()); %></span>
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
											<span style="">Points: <%out.print(questionComponent.getPointsWorth()); %></span>
											<span style="">Points Received: <%out.print(questionComponent.calculatePtsReceived()); %></span>
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
				  </div>
				  <form action="AfterTestPageLinker" method="get">
						<input type="submit" class="SShowTestbutton" name="action" value="back">
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
 