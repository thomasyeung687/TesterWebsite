package com.testersite.model;

import java.util.ArrayList;

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
	
	public Test() {}
	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions) {
		this.testName = testName;
		this.testDateStart = testDateStart;
		this.testDateEnd = testDateEnd;
		this.testid = testid;
		this.setTestDescription(testDescription);
		this.setTestInstructions(testInstructions);
	}
	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions, boolean availibility) {
		this.testName = testName;
		this.testDateStart = testDateStart;
		this.testDateEnd = testDateEnd;
		this.testid = testid;
		this.setTestDescription(testDescription);
		this.setTestInstructions(testInstructions);
		this.setAvailibility(availibility);
	}
	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions, boolean availibility, int timelimit) {
		this.testName = testName;
		this.testDateStart = testDateStart;
		this.testDateEnd = testDateEnd;
		this.testid = testid;
		this.setTestDescription(testDescription);
		this.setTestInstructions(testInstructions);
		this.setAvailibility(availibility);
		this.setTimelimit(timelimit);
	}
	public Test(String testName, String testDateStart, String testDateEnd, String testid, String testDescription, String testInstructions, boolean availibility, int timelimit, int amtOfAttempts) {
		this.testName = testName;
		this.testDateStart = testDateStart;
		this.testDateEnd = testDateEnd;
		this.testid = testid;
		this.setTestDescription(testDescription);
		this.setTestInstructions(testInstructions);
		this.setAvailibility(availibility);
		this.setTimelimit(timelimit);
		this.amtOfAttempts = amtOfAttempts;
	}
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
}