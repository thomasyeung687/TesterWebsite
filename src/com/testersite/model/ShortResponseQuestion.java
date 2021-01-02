package com.testersite.model;

public class ShortResponseQuestion extends Question{
	public ShortResponseQuestion( int questionid,  int pointsWorth,String questiontitle, String question) {
		super(questionid, questiontitle,"ShortResponseQuestion", pointsWorth, question);
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
}
 