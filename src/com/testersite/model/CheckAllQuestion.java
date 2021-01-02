package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckAllQuestion extends Question{
	private List<String> answers = new ArrayList<String>();
	private List<String> correctanswers = new ArrayList<String>();
	public CheckAllQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans) {
		super(questionid, questiontitle,"CheckAllQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.answers = Arrays.asList(answers.split("~"));
		this.correctanswers = Arrays.asList(correctans.split("~"));
	}
	public List<String> getCorrectanswers() {
		return correctanswers;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getAnswers()+" ";
		tostring += getCorrectanswers()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
	public static void main(String[] args) {
		CheckAllQuestion mc1 = new CheckAllQuestion(1, 10,"1", "helloo", "asdad~1 3 6 9~asd~", "asdad~1 3 6 9~");
		List<String> anschoicess = mc1.getAnswers();
		List<String> correctans = mc1.getCorrectanswers();
		System.out.println(anschoicess.toString());
		if(correctans.contains(anschoicess.get(1))) {
			System.out.println(anschoicess.get(1));
		}
		System.out.println(mc1.toString());
		
	}
}
