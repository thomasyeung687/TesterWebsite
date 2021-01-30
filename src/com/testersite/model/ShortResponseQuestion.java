package com.testersite.model;

public class ShortResponseQuestion extends Question{
	public ShortResponseQuestion( int questionid,  int pointsWorth,String questiontitle, String question) {
		super(questionid, questiontitle,"ShortResponseQuestion", pointsWorth, question);
	}
	public ShortResponseQuestion( int questionid,  int pointsWorth,String questiontitle, String question, String answerChosen) {
		super(questionid, questiontitle,"ShortResponseQuestion", pointsWorth, question);
		setAnswerChosen(answerChosen);
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
	@Override
	public double calculatePtsReceived() {
		return 0;
	}
}
 