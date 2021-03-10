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
</script>
	
	<%
	System.out.println("idquestion="+request.getAttribute("idquestion"));
	String idquestion = (String) request.getAttribute("idquestion");
	ResultSet rset;
	Connection con = DBConnection.getDBConnection();
	MultipartQuestion multi= null;
	try{
		Statement st = con.createStatement();
		Statement st2 = con.createStatement();
		rset = st.executeQuery("SELECT * FROM questionsdatabase.multipartquestion WHERE idquestion = '"+idquestion+"';");
		if(rset.next()){
			multi = new MultipartQuestion(rset.getInt("idquestion"),rset.getString("questiontitle"), rset.getString("question"), rset.getString("questioncomponentids"));
		}else{
			System.out.println("QuestionzEditmultipart. Failed to get multipart question!");
		}
	}catch(Exception exception){
		System.out.println("QuestionzEditmultiparterror: ");
		exception.printStackTrace();
	}
	System.out.println(multi.toString());
	int pointsworth = multi.getPointsWorth();
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
                    <h1></h1>
                     <p style="font-size:25px;">Edit Multipart Question </p> 
                    </div>
                </div>              
                 <!-- /. ROW  -->
                  <hr /> <!-- adds line -->
                  <form action = "MultipartAddQuestionLinker" method="get">
				  	<button type="submit" name="idmultiquestion" value="<%out.println(multi.getQuestionid());%>">Add Question</button> 
				  	<select id="questiontype" name="questiontype" onchange="changeDropdown(this.value);">
				  		<option value="multiplechoice">Multiple Choice</option>
				  		<option value="checkall">Check All</option>
				  		<option value="fillintheblank">Fill in the Blank </option>
				  		<option value="fillinmultipleblank">Fill in Multiple Blanks </option>
				  		<option value="truefalse">True/False </option>
				  		<!--  <option value="matching">Matching Question</option> -->
				  	</select>
				  	</br>
				  	<span id="numbertext">Enter Amount of Answer Choices</span>
				  	<input type="number" id="amtofquestions" name="amtofquestions" min="2" max="10" value="3" style="width: 7em" > 
				  </form>
                  <hr /> <!-- adds line -->
                  <span style="font-size: 150%"><%out.println(multi.getQuestionType()); %>: <%out.println(multi.getQuestiontitle()); %></span>
				  <span>Points: <%out.println(multi.getPointsWorth()); %></span> <br>
				  <span style="font-size: 150%; white-space: pre-wrap;">Question: <%out.println(multi.getQuestion()); %></span></br>
                  <hr />
                  <hr />
				  <form action="DeleteOrEditQuestionServlet" method="post">
				  <% 
				  	List<Question> questions = multi.getQuestions(); //this is getting the list of questions that make up this multipart question.
				  	  for(int n = 0; n<questions.size(); n++){ 
					  Question question = questions.get(n);
					  //System.out.println(question.getQuestion());
				  %>
				  
				  		<input type="radio" name="selectedquestion" value="<%out.println(question.getQuestionid());%>" required>
				  		<span style="font-size: 150%"><%out.println(question.getQuestionType()); %>: <%out.println(question.getQuestiontitle()); %></span>
				  		<span>Points: <%out.println(question.getPointsWorth()); %></span> <br>
						<span style="font-size: 150%">Question: <%out.println(question.getQuestion()); %></span></br>
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
							List<String> anschoices = Arrays.asList("True","False");
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
						}
						//here check for other types of questions %>
				  		<hr />
				  <%} %>
				  	<button name="action" value="edit">Edit Selected</button> 
					<button name="action" value="Delete">Delete Selected</button> 
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
