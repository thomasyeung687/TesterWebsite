package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TFQuestion extends Question{
	private List<String> answers = new ArrayList<String>();
	private String correctans;
	public TFQuestion(int questionid,  int pointsWorth,String questiontitle, String question,  String correctans) {
		super(questionid, questiontitle,"TFQuestion", pointsWorth, question);
		this.answers = Arrays.asList("True", "False");
		this.correctans = correctans;
	}
	public void addAnswers(String ans) {
		answers.add(ans);
	}
	public List<String> getAnswers(){
		return answers;
	}
	public String getCorrectAns(){
		return correctans;
	}
	public static void main(String[] args) {
		TFQuestion mc1 = new TFQuestion(1, 10,"1", "helloo", "True");
		List<String> anschoicess = mc1.getAnswers();
		System.out.println(anschoicess.toString());
		System.out.println(mc1.toString());
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getAnswers()+" ";
		tostring += getCorrectAns()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
}
