package com.testersite.model;

public class ShortResponseQuestion extends Question{
	/**
	 * ShortResponseQuestion constructor
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 */
	public ShortResponseQuestion( int questionid,  int pointsWorth,String questiontitle, String question) {
		super(questionid, questiontitle,"ShortResponseQuestion", pointsWorth, question);
	}

	/**
	 * ShortResponseQuestion constructor with answer provided
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 * @param answerChosen answer provided
	 */
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
	/**
	 * ShortResponseQuestion cannot be automatically graded so this would always return 0.
	 */
	public double calculatePtsReceived() {
		return 0;
	}
}
 