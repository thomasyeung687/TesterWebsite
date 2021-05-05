package com.testersite.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.testersite.dao.DBConnection;

public class Test{
	//default availibility false
	//default forcedCompletion false (Unlimited Attempts)
	private String testid = null;
	private String testName = null;
	private String testDescription = null;
	private String testInstructions = null;
	private String testDateStart= null;
	private String displaystart=null;
	private String displayend=null;
	private String testDateEnd= null;
	private boolean availibility = false; 
	private boolean forcedCompletion = false; 
	private boolean allowBackButton = false;
	private boolean scrambleTest = false;
	private boolean showQuestionOnebyOne = false;//default is false so it will show the whole test as default.
	private int timelimit = 0; 
	private int amtOfAttempts = 1;
	private ArrayList<Question> questions = new ArrayList<Question>();
	private int totalPtsReceived = 0;
	private ArrayList<TestAttemptObject> attempts = new ArrayList<TestAttemptObject>();
	//properties for after test taken by student
	private boolean releaseGrade = false;
	private boolean allowSeeAttempt = false;
	private boolean showcorrectans = false;
	
	
	public Test() {}

	/**
	 *
	 * @param testid
	 */
	public Test(String testid) {
		Connection connection = DBConnection.getDBConnection();
		try {
			Statement st = connection.createStatement();
			
		}catch (Exception e) {
			System.out.println("Exception in test.java");
			System.out.println(e.getLocalizedMessage());
		}
	}

//	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions) {
//		this.testName = testName;
//		this.testDateStart = testDateStart;
//		this.testDateEnd = testDateEnd;
//		this.testid = testid;
//		this.setTestDescription(testDescription);
//		this.setTestInstructions(testInstructions);
//	}
//	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions, boolean availibility) {
//		this.testName = testName;
//		this.testDateStart = testDateStart;
//		this.testDateEnd = testDateEnd;
//		this.testid = testid;
//		this.setTestDescription(testDescription);
//		this.setTestInstructions(testInstructions);
//		this.setAvailibility(availibility);
//	}
//	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions, boolean availibility, int timelimit) {
//		this.testName = testName;
//		this.testDateStart = testDateStart;
//		this.testDateEnd = testDateEnd;
//		this.testid = testid;
//		this.setTestDescription(testDescription);
//		this.setTestInstructions(testInstructions);
//		this.setAvailibility(availibility);
//		this.setTimelimit(timelimit);
//	}
//	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions, boolean availibility, int timelimit, int amtOfAttempts) {
//		this.testName = testName;
//		this.testDateStart = testDateStart;
//		this.testDateEnd = testDateEnd;
//		this.testid = testid;
//		this.setTestDescription(testDescription);
//		this.setTestInstructions(testInstructions);
//		this.setAvailibility(availibility);
//		this.setTimelimit(timelimit);
//		this.amtOfAttempts = amtOfAttempts;
//	}
	
	/**
	 * constructor of a Test object
	 * @param testName name of test
	 * @param testDateStart start date of the test
	 * @param displaystart when should this test be displated on the start date
	 * @param displayend  when should this test be displayed on the end date
	 * @param testDateEnd end date of the test
	 * @param testid id of this test in the db
	 * @param testDescription breif description of the test
	 * @param testInstructions breif instructions about this test
	 * @param availibility is the test visible currently
	 * @param forcedComplete will this test have to be completed in one sitting
	 * @param allowBackButton only used for when showQuestionOnebyOne is true. if true, user will not have option to revisit past questions
	 * @param scrambleTest should the test questions be scrambled
	 * @param showQuestionOnebyOne when user takes test, will the test questions be shown one by one
	 * @param timelimit time to take the exam
	 * @param amtOfAttempts the number of attempts for this exam
	 */
	public Test(String testName, String testDateStart,String displaystart, String displayend,  String testDateEnd, String testid, String testDescription, String testInstructions, boolean availibility, boolean forcedComplete, boolean allowBackButton,boolean scrambleTest,boolean showQuestionOnebyOne,int timelimit, int amtOfAttempts) {
		this.testName = testName;
		this.testDateStart = testDateStart;
		this.displaystart = displaystart;
		this.displayend = displayend;
		this.testDateEnd = testDateEnd;
		this.testid = testid;
		this.setTestDescription(testDescription);
		this.setTestInstructions(testInstructions);
		this.setAvailibility(availibility);
		this.setTimelimit(timelimit);
		this.allowBackButton = allowBackButton;
		this.scrambleTest = scrambleTest;
		this.showQuestionOnebyOne = showQuestionOnebyOne;
		this.amtOfAttempts = amtOfAttempts;
		this.forcedCompletion = forcedComplete;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testname) {
		this.testName =  testname;
	}
	
	public String getTestDateStart() {
		return testDateStart;
	}
	public void setTestDateStart(String TestDateStart) {
		this.testDateStart = TestDateStart;
	}
	
	public String gettestDateEnd() {
		return testDateEnd;
	}
	public void settestDateEnd(String testdateend) {
		this.testDateEnd = testdateend;
	}
	
	public String getTestId() {
		return testid;
	}
	public void setTestId(String id) {
		this.testid = id;
	}
	
	public void addQuestionToQuestions(Question newQuestion) {
		this.questions.add(newQuestion);
	}
	public void setQuestionInQuestionsArray(int index, Question question) {
		questions.set(index, question);
	}
	public ArrayList<Question> getQuestionArray(){
		return questions;
	}
	
	public String getTestDescription() {
		return testDescription;
	}
	public void setTestDescription(String testDescription) {
		this.testDescription = testDescription;
	}
	
	public String getTestInstructions() {
		return testInstructions;
	}
	public void setTestInstructions(String testInstructions) {
		this.testInstructions = testInstructions;
	}
	
	public boolean isAvailibility() {
		return availibility;
	}
	public void setAvailibility(boolean availibility) {
		this.availibility = availibility;
	}
	
	public int getTimelimit() {
		return timelimit;
	}
	public void setTimelimit(int timelimit) {
		this.timelimit = timelimit;
	}
	
	public int getAmtOfAttempts() {
		return amtOfAttempts;
	}
	public void setAmtOfAttempts(int amtOfAttempts) {
		this.amtOfAttempts = amtOfAttempts;
	}
	
	public boolean isForcedCompletion() {
		return forcedCompletion;
	}
	public void setForcedCompletion(boolean forcedCompletion) {
		this.forcedCompletion = forcedCompletion;
	}

	public boolean getAllowBackButton() {
		return allowBackButton;
	}
	public void setAllowBackButton(boolean allowBackButton) {
		this.allowBackButton = allowBackButton;
	}

	public boolean isScrambleTest() {
		return scrambleTest;
	}
	public void setScrambleTest(boolean scrambleTest) {
		this.scrambleTest = scrambleTest;
	}

	public boolean isShowQuestionOnebyOne() {
		return showQuestionOnebyOne;
	}
	public void setShowQuestionOnebyOne(boolean showQuestionOnebyOne) {
		this.showQuestionOnebyOne = showQuestionOnebyOne;
	}
	
	public String toString() {
		String tostring = "";
		tostring += testid+" ";
		tostring += testName+" ";
		tostring += testDescription+" ";
		tostring += testInstructions+" ";
		tostring += testDateStart+" ";
		tostring += displaystart+" ";
		tostring += displayend+" ";
		tostring += testDateEnd+" ";
		tostring += availibility+" ";
		tostring += allowBackButton+" ";
		tostring += scrambleTest+" ";
		tostring += forcedCompletion+" ";
		tostring += showQuestionOnebyOne+" ";
		tostring += timelimit+" ";
		tostring += amtOfAttempts+" ";
		return tostring;
	}
	public String getDisplayend() {
		return displayend;
	}
	public void setDisplayend(String displayend) {
		this.displayend = displayend;
	}
	public String getDisplaystart() {
		return displaystart;
	}
	public void setDisplaystart(String displaystart) {
		this.displaystart = displaystart;
	}
	
	/**
	 * calculates the points received for each question in this test and returns the sum
	 * @return the sum of all the questions in this test
	 */
	public double scoreTest() {
		int totalPtsReceived = 0;
		for(Question question: questions) { //for each question in this multipart question, calculate pts received.
			totalPtsReceived += question.calculatePtsReceived(); //accumulating the ptsreceived of each question.
		}
		this.totalPtsReceived = totalPtsReceived;
		return totalPtsReceived;
	}
	
	/**
	 * returns totalPtsReceived. if totalPtsReceived == 0, scoresTest then returns totalPtsReceived
	 * @return totalPtsReceived
	 */
	public int getTotalPtsReceived() {
		if(totalPtsReceived == 0) {
			this.scoreTest();
		}
		return totalPtsReceived;
	}
	
	public void setTotalPtsReceived(int totalPtsReceived) {
		this.totalPtsReceived = totalPtsReceived;
	}
	
	/**
	 * calculates the total amount of points that can be gotten on this exam
	 * @return the total amount of points that can be gotten on this exam
	 */
	public int getTotalPts() {//total pts of this test by adding all the ptsworth of each question in questions list
		int sumOfPts = 0;
		for(Question question : questions) {
			sumOfPts += question.getPointsWorth();
		}
		return sumOfPts;
	}
	public ArrayList<TestAttemptObject> getAttempts() {
		return attempts;
	}
	public void addAttemptObject(TestAttemptObject attempt) {
		attempts.add(attempt);
	}
	
	/**
	 * retreived only the preferences of the test with the id idTest
	 * @param idtest id of test we are trying to get from db
	 * @return a Test obj with just the preference information retreived from db
	 */
	public static Test getTestWithOnlyPreferences(String idtest) {
		Connection con = DBConnection.getDBConnection();
		Test test = null;
		try { //gets the test preferences of the test with the corresponding idtest from db.
			Statement st = con.createStatement();
			String query = "SELECT * FROM testersitedatabase.testprofiles WHERE idtest = '"+idtest+"';";
			ResultSet rset = st.executeQuery(query); //getting the test profile of the test that was clicked on ClassTests.jsp
			if(rset.next()) {
				String testid = rset.getString("idtest");
				String testName = rset.getString("testname");
				String testDescription = rset.getString("testdescription");
				String testInstructions = rset.getString("testinstructions");
				String testDateStart = rset.getString("testdatestart");
				String displaystart=rset.getString("displaystart");;
				String displayend=rset.getString("displayend");;
				String testDateEnd = rset.getString("testdateend");
				boolean availibility = rset.getBoolean("availibility");
				boolean forcedComplete = rset.getBoolean("forcedCompletion");
				boolean allowBackButton =rset.getBoolean("allowbackbutton");
				boolean scrambleTest = rset.getBoolean("scrambletest");
				boolean showQuestionOnebyOne = rset.getBoolean("showquestiononebyone");
				int timelimit = rset.getInt("timelimit");
				int amtOfAttempts = rset.getInt("amtofattempts");
				boolean releaseGrade = rset.getBoolean("releasegrade");
				boolean allowSeeAttempt = rset.getBoolean("allowseeattempt");
				boolean showcorrectans = rset.getBoolean("showcorrectans");
				test = new Test(testName, testDateStart,displaystart, displayend,  testDateEnd, testid, testDescription, testInstructions,  availibility,  forcedComplete,allowBackButton,scrambleTest,showQuestionOnebyOne,  timelimit,  amtOfAttempts);
				
				test.setReleaseGrade(releaseGrade);
				test.setAllowSeeAttempt(allowSeeAttempt);
				test.setShowcorrectans(showcorrectans);
				
				//System.out.println(test.toString());
				//request.setAttribute("testobject", test);
			}else {
				System.out.println("getTestWithOnlyPreferences: TEST NOT FOUND");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("First trycatch block in getTestWithOnlyPreferences method");
			e.printStackTrace();
		}
		return test;
	}
	
	/**
	 * gets the completed Test from db which includes the test preferences, the questions, as well as the answers
	 * @param idstudentprofiles student who's test results we are fetching
	 * @param idtest the id of test taken by student
	 * @return completed Test from db which includes the test preferences, the questions, as well as the answers
	 */
	public static Test getCompletedTestFromDB(String idstudentprofiles, String idtest) {
		System.out.println("getCompletedTestFromDB idtest="+idtest);
		TestAttemptObject tao = TestAttemptObject.getAttemptFromDB(idstudentprofiles, idtest); //getting TestAttemptObject
		Test test = getTestWithOnlyPreferences(idtest); //getting Test object with only preferences.
		test.addAttemptObject(tao); //adding TAO object to test.
		test.setTotalPtsReceived(tao.getgrade());
		
		Connection con = DBConnection.getDBConnection();
		ResultSet rset;//gets test and its questions
		try{ //here we try to get each questions as well as the answer that the student gave from the DB.
			Statement st = con.createStatement();
			rset = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable WHERE idtest = '"+test.getTestId()+"';");
			while(rset.next()){ //while there are questions from allquestionstable with that idtest. we go into each row and get the table and questionid and find that question
				//System.out.println(rset.getString("answerstring"));
				String idquestion = rset.getString("idquestion");
				String tablename = rset.getString("tablename");
				
				if(tablename.equals("questionsdatabase.multipartquestion")){ //if is a multipart question. we do stuff a little differently
					Statement st2 = con.createStatement();
					ResultSet rset2 = st2.executeQuery("SELECT * FROM "+tablename+" WHERE idquestion='"+idquestion+"';");
					rset2.next();
					System.out.println("Adding new multipartquestion question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
					System.out.println("Question:"+rset2.getString("question"));
					MultipartQuestion newq = new MultipartQuestion(rset2.getInt("idquestion"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("questioncomponentids"), tao.getIdAttempt()+"");
					//System.out.println(newfib.toString());
					test.addQuestionToQuestions(newq);
				}else { //if not a multipart question
					String query = "Select * FROM "+tablename+" INNER JOIN testersitedatabase.attempt_answer_choice "
							+ "on "+tablename+".idquestion = testersitedatabase.attempt_answer_choice.idquestion WHERE "+tablename+".idquestion = "+idquestion+" AND idattempt = "+tao.getIdAttempt()+";";
					System.out.println(query);
					Statement st2 = con.createStatement();
					ResultSet rset2 = st2.executeQuery(query);
					if(rset2.next()){
						if(tablename.equals("questionsdatabase.multiplechoice")){
							System.out.println("Adding new mc question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
							MultipleChoiceQuestion newq = new MultipleChoiceQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"),rset2.getString("answerstring"), rset2.getString("correctanswer"));
							//System.out.println(newmcq.getAnswers().toString());
							newq.setAnswerChosen(rset2.getString("answerGiven"));
							newq.setPointsReceived(rset2.getDouble("ptsGiven"));
							newq.setNotes(rset2.getString("notes"));
							
							test.addQuestionToQuestions(newq);
						}else if(tablename.equals("questionsdatabase.truefalse")){
							System.out.println("Adding new TF question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
							TFQuestion newq = new TFQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"));
							newq.setAnswerChosen(rset2.getString("answerGiven"));
							newq.setPointsReceived(rset2.getDouble("ptsGiven"));
							newq.setNotes(rset2.getString("notes"));
							
							test.addQuestionToQuestions(newq);
						}else if(tablename.equals("questionsdatabase.shortanswer")){
							System.out.println("Adding new SR question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
							ShortResponseQuestion newq = new ShortResponseQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"));
							newq.setAnswerChosen(rset2.getString("answerGiven"));
							newq.setPointsReceived(rset2.getDouble("ptsGiven"));
							newq.setNotes(rset2.getString("notes"));
							
							test.addQuestionToQuestions(newq);
						}else if(tablename.equals("questionsdatabase.checkall")){
							System.out.println("Adding new CheckAll question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
							CheckAllQuestion newq = new CheckAllQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("answerstring"), rset2.getString("correctstring"));
							newq.setAnswerChosen(rset2.getString("answerGiven"));
							newq.setPointsReceived(rset2.getDouble("ptsGiven"));
							newq.setNotes(rset2.getString("notes"));
	
							test.addQuestionToQuestions(newq);
						}else if(tablename.equals("questionsdatabase.fillintheblank")){
							System.out.println("Adding new fillintheblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
							FillInTheBlankQuestion newq = new FillInTheBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"));
							newq.setAnswerChosen(rset2.getString("answerGiven"));
							newq.setPointsReceived(rset2.getDouble("ptsGiven"));
							newq.setNotes(rset2.getString("notes"));
							
							System.out.println(newq.toString());
							test.addQuestionToQuestions(newq);
						}else if(tablename.equals("questionsdatabase.fillinmultipleblank")){
							System.out.println("Adding new fillinmultipleblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
							System.out.println("Question:"+rset2.getString("question"));
							FillInMultipleBlankQuestion newq = new FillInMultipleBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"),rset2.getBoolean("partialcredit"));
							newq.setAnswerChosen(rset2.getString("answerGiven"));
							newq.setPointsReceived(rset2.getDouble("ptsGiven"));
							newq.setNotes(rset2.getString("notes"));
							
							//System.out.println(newfib.toString());
							test.addQuestionToQuestions(newq);
						}
						//here you can add it for others.
					}else{
						System.out.println("Test.java getCompletedTestFromDB Couldn't get question "+idquestion);
					}
				}
			}
			//rset for other questiontypes V
		}catch(Exception exception){
			System.out.println("Test.java Second trycatch block in getCompletedTestFromDB method");
			System.out.println(exception.getLocalizedMessage());
		}
		
		//here test should have been built completed with all the needed information.
		return test;
	}
	
	/**
	 * gets the completed Test from db which includes the test preferences, the questions
	 * @param idtest the id of test we want from the db
	 * @return completed Test from db which includes the test preferences, the questions
	 */
	public static Test getTestFromDB(String idtest) {
		Connection con = DBConnection.getDBConnection();
		System.out.println("idtest="+idtest);
		Test test = Test.getTestWithOnlyPreferences(idtest); //getting test with only preferences.
		
		ResultSet rset;//gets test and its questions
		try{
			Statement st = con.createStatement();
			rset = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable WHERE idtest = '"+test.getTestId()+"';");
			while(rset.next()){ //while there are questions from allquestionstable with that idtest. we go into each row and get the table and questionid and find that question
				//System.out.println(rset.getString("answerstring"));
				String idquestion = rset.getString("idquestion");
				String tablename = rset.getString("tablename");
				Statement st2 = con.createStatement();
				ResultSet rset2 = st2.executeQuery("SELECT * FROM "+tablename+" WHERE idquestion='"+idquestion+"';");
				if(rset2.next()){
					if(tablename.equals("questionsdatabase.multiplechoice")){
						System.out.println("Adding new mc question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						MultipleChoiceQuestion newmcq = new MultipleChoiceQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"),rset2.getString("answerstring"), rset2.getString("correctanswer"));
						//System.out.println(newmcq.getAnswers().toString());
						test.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.truefalse")){
						System.out.println("Adding new TF question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						TFQuestion newmcq = new TFQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"));
						test.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.shortanswer")){
						System.out.println("Adding new SR question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						ShortResponseQuestion newmcq = new ShortResponseQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"));
						test.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.checkall")){
						System.out.println("Adding new CheckAll question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						CheckAllQuestion newmcq = new CheckAllQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("answerstring"), rset2.getString("correctstring"));
						System.out.println(newmcq.toString());
						test.addQuestionToQuestions(newmcq);
					}else if(tablename.equals("questionsdatabase.fillintheblank")){
						System.out.println("Adding new fillintheblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						FillInTheBlankQuestion newfib = new FillInTheBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"));
						System.out.println(newfib.toString());
						test.addQuestionToQuestions(newfib);
					}else if(tablename.equals("questionsdatabase.fillinmultipleblank")){
						System.out.println("Adding new fillinmultipleblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						System.out.println("Question:"+rset2.getString("question"));
						FillInMultipleBlankQuestion newfib = new FillInMultipleBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"),rset2.getBoolean("partialcredit"));
						//System.out.println(newfib.toString());
						test.addQuestionToQuestions(newfib);
					}else if(tablename.equals("questionsdatabase.multipartquestion")){
						System.out.println("Adding new multipartquestion question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						System.out.println("Question:"+rset2.getString("question"));
						MultipartQuestion multi = new MultipartQuestion(rset2.getInt("idquestion"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("questioncomponentids"));
						//System.out.println(newfib.toString());
						test.addQuestionToQuestions(multi);
					}
					//here you can add it for others.
				}else{
					System.out.println("Test.java getTestFromDb method else {} Couldn't get question "+idquestion);
				}
			}
			//rset for other questiontypes V
		}catch(Exception exception){
			System.out.println("Test.java Second trycatch block in getTestFromDb method");
			System.out.println(exception.getLocalizedMessage());
		}
		return test;
	}
	public boolean isReleaseGrade() {
		return releaseGrade;
	}
	public void setReleaseGrade(boolean releaseGrade) {
		this.releaseGrade = releaseGrade;
	}
	public boolean isAllowSeeAttempt() {
		return allowSeeAttempt;
	}
	public void setAllowSeeAttempt(boolean allowSeeAttempt) {
		this.allowSeeAttempt = allowSeeAttempt;
	}
	public boolean isShowcorrectans() {
		return showcorrectans;
	}
	public void setShowcorrectans(boolean showcorrectans) {
		this.showcorrectans = showcorrectans;
	}
}