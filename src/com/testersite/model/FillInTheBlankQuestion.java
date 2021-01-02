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
	public FillInTheBlankQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String correctans, boolean casesensitive) {
		super(questionid, questiontitle,"FillInTheBlankQuestion", pointsWorth, question); //default #question title is the num answers there are.
		String[] questionstrings= question.split("[x]");
		System.out.println(questionstrings[0]);
		this.str1 = questionstrings[0];
		this.str2 = questionstrings[1];
		this.correctans = Arrays.asList(correctans.split("~"));
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
}
