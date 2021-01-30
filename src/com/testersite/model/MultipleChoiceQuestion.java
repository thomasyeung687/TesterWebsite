package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

public class MultipleChoiceQuestion extends Question{
	private List<String> answers = new ArrayList<String>(); //choices in the multiple choice problem
	
	public MultipleChoiceQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans) {
		super(questionid, questiontitle,"MultipleChoiceQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.answers = Arrays.asList(answers.split("~"));
		setCorrectAnswerString(correctans);
	}
	public MultipleChoiceQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans, String answerChosen) {
		super(questionid, questiontitle,"MultipleChoiceQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.answers = Arrays.asList(answers.split("~"));
		setCorrectAnswerString(correctans);
		setAnswerChosen(answerChosen);
	}
	public void addAnswers(String ans) {
		answers.add(ans);
	}
	public List<String> getAnswers(){
		return answers;
	}
	public static void main(String[] args) {
		MultipleChoiceQuestion mc1 = new MultipleChoiceQuestion(1, 10,"1", "helloo", "asdad~1 3 6 9~asd~", "asdad");
		List<String> anschoicess = mc1.getAnswers();
		System.out.println(anschoicess.toString());
		System.out.println(mc1.calculatePtsReceived());
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getAnswers()+" ";
		tostring += getCorrectAnswerString()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
	@Override
	public double calculatePtsReceived() {
		if(getAnswerChosen() == null) {
			System.out.println("There is no answer given. ID = " +this.getQuestionid());
			return -1;
		}
		if(getAnswerChosen().trim().equals(getCorrectAnswerString().trim())) {
			//System.out.println(getAnswerChosen() +"=="+ getCorrectAnswerString());
			return setPointsReceived(getPointsWorth()); //sets ptsreceived to ptsworth and returns ptsreceived
		}else {
			//System.out.println(getAnswerChosen() +"!="+ getCorrectAnswerString());
			return setPointsReceived(0);//sets ptsreceived to 0 and returns 0
		}
	}
}
