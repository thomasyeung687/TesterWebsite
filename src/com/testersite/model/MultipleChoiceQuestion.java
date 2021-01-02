package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class MultipleChoiceQuestion extends Question{
	private List<String> answers = new ArrayList<String>();
	private String correctans;
	public MultipleChoiceQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans) {
		super(questionid, questiontitle,"MultipleChoiceQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.answers = Arrays.asList(answers.split("~"));
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
		MultipleChoiceQuestion mc1 = new MultipleChoiceQuestion(1, 10,"1", "helloo", "asdad~1 3 6 9~asd~", "asdad");
		List<String> anschoicess = mc1.getAnswers();
		System.out.println(anschoicess.toString());
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
