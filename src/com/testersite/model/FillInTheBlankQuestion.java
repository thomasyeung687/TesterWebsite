package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FillInTheBlankQuestion extends Question{
	String blank; //blank would be x in [x] the blank in the question string is marked by [x] or a set of square brackets
	private List<String> correctans = new ArrayList<String>();
	private boolean casesensitive;
	private String str1;
	private String str2;

	/**
	 * FillInTheBlankQuestion constructor
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 * @param correctans the answer choice that is correct
	 * @param casesensitive is this answer case sensitive?
	 */
	public FillInTheBlankQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String correctans, boolean casesensitive) {
		super(questionid, questiontitle,"FillInTheBlankQuestion", pointsWorth, question); //default #question title is the num answers there are.
		setCorrectAnswerString(correctans);
		ArrayList<String> strings = new ArrayList<String>();
		int start = 0;
		int endIndex = 0;
		while(question.indexOf("[",start)> -1) {
			//System.out.println(start);
			int startIndex = question.indexOf("[", start)+1;
			endIndex = question.indexOf("]", start);
			strings.add(question.substring(start, startIndex-1).trim());
			//System.out.println(startIndex);
			//System.out.println(endIndex);
			String blankString = question.substring(startIndex, endIndex); //this will contain x for [x] in question.
			//System.out.println(blankString);
			start = endIndex+1; //so we dont find the same ]
		}
		strings.add(question.substring(endIndex+1).trim());
		
		System.out.println("Arraylist length="+strings.size());
		
		str1 = strings.get(0);
		str2 = strings.get(1);
//		String[] questionstrings= question.split("[x]");
//		System.out.println(questionstrings[0]);
//		this.str1 = questionstrings[0];
//		this.str2 = questionstrings[1];
		if(!casesensitive) { //if not case sensitive then we tolowercase the correctans String
			this.correctans = Arrays.asList(correctans.toLowerCase().split("~"));
		}else {
			this.correctans = Arrays.asList(correctans.split("~"));
		}
		this.casesensitive = casesensitive;
	}

	/**
	 * FillInTheBlankQuestion constructor with an answer response provided.
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 * @param correctans the answer choice that is correct
	 * @param casesensitive is this answer case sensitive?
	 * @param answerChosen answer chosen by user.
	 */
	public FillInTheBlankQuestion(int questionid,  int pointsWorth,String questiontitle, String question,String answerChosen, String correctans, boolean casesensitive) {
		super(questionid, questiontitle,"FillInTheBlankQuestion", pointsWorth, question); //default #question title is the num answers there are.
		
		ArrayList<String> strings = new ArrayList<String>();
		int start = 0;
		int endIndex = 0;
		while(question.indexOf("[",start)> -1) {
			//System.out.println(start);
			int startIndex = question.indexOf("[", start)+1;
			endIndex = question.indexOf("]", start);
			strings.add(question.substring(start, startIndex-1).trim());
			//System.out.println(startIndex);
			//System.out.println(endIndex);
			String blankString = question.substring(startIndex, endIndex); //this will contain x for [x] in question.
			//System.out.println(blankString);
			start = endIndex+1; //so we dont find the same ]
		}
		strings.add(question.substring(endIndex+1).trim());
		
		System.out.println("Arraylist length="+strings.size());
		
		str1 = strings.get(0);
		str2 = strings.get(1);
//		String[] questionstrings= question.split("[x]");
//		System.out.println(questionstrings[0]);
//		this.str1 = questionstrings[0];
//		this.str2 = questionstrings[1];
		if(!casesensitive) { //if not case sensitive then we tolowercase the correctans String
			this.correctans = Arrays.asList(correctans.toLowerCase().split("~"));
			setAnswerChosen(answerChosen.toLowerCase());
		}else {
			this.correctans = Arrays.asList(correctans.split("~"));
			setAnswerChosen(answerChosen);
		}
		this.casesensitive = casesensitive;
		
	}
	
	public List<String> getCorrectans() {
		return correctans;
	}
	public void setCorrectans(List<String> correctans) {
		this.correctans = correctans;
	}
	public boolean isCasesensitive() {
		return casesensitive;
	}
	public void setCasesensitive(boolean casesensitive) {
		this.casesensitive = casesensitive;
	}
	public String getStr1() {
		return str1;
	}
	public void setStr1(String str1) {
		this.str1 = str1;
	}
	public String getStr2() {
		return str2;
	}
	public void setStr2(String str2) {
		this.str2 = str2;
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getCorrectans()+" ";
		tostring += isCasesensitive()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
	public static void main(String[] args) {
		FillInTheBlankQuestion fib = new FillInTheBlankQuestion(2, 5, "1", "Write two! [x]", "two", true);
		fib.setAnswerChosen("Two");
		fib.calculatePtsReceived();
	}
	@Override
	/**
	 * Calculates the points received by comparing the answerChosen and the correctans
	 */
	public double calculatePtsReceived() {
		System.out.println("Question: "+this.getQuestion());
		System.out.println("Correct Answer(s)  : "+this.correctans);
		String answerChosen = getAnswerChosen();
		if(getAnswerChosen() == null) {
			System.out.println("There is no answer given. ID = " +this.getQuestionid());
			System.out.println("Points Received: -1");
			return -1;
		}
		System.out.println("Answer Chosen: "+this.getAnswerChosen());
		if(!isCasesensitive()) {
			if(correctans.contains(answerChosen.toLowerCase())) { //if correct answer set contains the given answer(answer chosen)
				System.out.println("Points Received: "+this.getPointsWorth());
				return setPointsReceived(getPointsWorth()); //student gets full marks on this question
			}else {
				System.out.println("Points Received: 0");
				return setPointsReceived(0);
			}
		}else {
			if(correctans.contains(answerChosen)) { //if correct answer set contains the given answer(answer chosen)
				System.out.println("Points Received: "+this.getPointsWorth());
				return setPointsReceived(getPointsWorth()); //student gets full marks on this question
			}else {
				System.out.println("Points Received: 0");
				return setPointsReceived(0);
			}
		}
		
	}
}
