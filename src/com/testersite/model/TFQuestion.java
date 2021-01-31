package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TFQuestion extends Question{
	public TFQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String correctans) {
		super(questionid, questiontitle,"TFQuestion", pointsWorth, question);
		setCorrectAnswerString(correctans);
	}
	public TFQuestion(int questionid,  int pointsWorth,String questiontitle, String question,  String correctans, String answerChosen) {
		super(questionid, questiontitle,"TFQuestion", pointsWorth, question);
		setAnswerChosen(answerChosen);
		setCorrectAnswerString(correctans);
	}
	public static void main(String[] args) {
		TFQuestion mc1 = new TFQuestion(1, 10,"1", "Pick True", "True", "True");
		mc1.calculatePtsReceived();
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getCorrectAnswerString()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
	@Override
	public double calculatePtsReceived() {
		System.out.println("Question: "+this.getQuestion());
		System.out.println("Answer  : "+this.getCorrectAnswerString());
		if(getAnswerChosen() == null) {
			System.out.println("There is no answer given. ID = " +this.getQuestionid());
			System.out.println("Points Received: -1");
			return -1;
		}
		System.out.println("Answer Chosen: "+this.getAnswerChosen());
		if(getAnswerChosen().trim().equals(getCorrectAnswerString().trim())) {
			//System.out.println(getAnswerChosen() +"=="+ getCorrectAnswerString());
			System.out.println("Points Received: "+this.getPointsWorth());
			return setPointsReceived(getPointsWorth()); //sets ptsreceived to ptsworth and returns ptsreceived
		}else {
			//System.out.println(getAnswerChosen() +"!="+ getCorrectAnswerString());
			System.out.println("Points Received: 0");
			return setPointsReceived(0);//sets ptsreceived to 0 and returns 0
		}
	}
}
