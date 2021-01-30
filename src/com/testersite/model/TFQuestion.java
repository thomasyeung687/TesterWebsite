package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TFQuestion extends Question{
	public TFQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String correctans) {
		super(questionid, questiontitle,"TFQuestion", pointsWorth, question);
		setCorrectAnswerString(correctans);
	}
	public TFQuestion(int questionid,  int pointsWorth,String questiontitle, String question,  String answerChosen, String correctans) {
		super(questionid, questiontitle,"TFQuestion", pointsWorth, question);
		setAnswerChosen(answerChosen);
		setCorrectAnswerString(correctans);
	}
	public static void main(String[] args) {
		TFQuestion mc1 = new TFQuestion(1, 10,"1", "helloo", "True");
		System.out.println(mc1.calculatePtsReceived());
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
