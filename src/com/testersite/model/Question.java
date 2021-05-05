package com.testersite.model;

public abstract class Question {
	private int questionid;
	private String questionType;
	private String question;
	private String questiontitle;
	private String notes; //used for giving feedback on a question
	
	private int pointsWorth; //num of points this question is worth.
	private double pointsReceived; //amount of points received for this question

	private String correctAnswerString; //taken from DB
	private String answerChosen; //answer given by student

	/**
	 * question constructor
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 */
	public Question(int questionid, String questiontitle, String questionType, String question) {
		this.questionid = questionid;
		this.questiontitle = questiontitle;
		this.questionType = questionType;
		this.pointsWorth = pointsWorth;
		this.question = question;
	}

	/**
	 * question constructor
	 * @param questionType
	 * @param pointsWorth
	 * @param question
	 */
	public Question(String questionType, int pointsWorth, String question) {
		this.questionType = questionType;
		this.pointsWorth = pointsWorth;
		this.question = question;
	}

	/**
	 * question constructor
	 * @param questionid
	 * @param questiontitle
	 * @param questionType
	 * @param pointsWorth
	 * @param question
	 */
	public Question(int questionid, String questiontitle, String questionType, int pointsWorth, String question) {
		this.questionid = questionid;
		this.questiontitle = questiontitle;
		this.questionType = questionType;
		this.pointsWorth = pointsWorth;
		this.question = question;
	}

	/**
	 * question constructor
	 * @param questionid
	 * @param questiontitle
	 * @param questionType
	 * @param pointsWorth
	 * @param question
	 * @param answerChosen
	 */
	public Question(int questionid, String questiontitle, String questionType, int pointsWorth, String question, String answerChosen) {
		this.questionid = questionid;
		this.questiontitle = questiontitle;
		this.questionType = questionType;
		this.pointsWorth = pointsWorth;
		this.question = question;
		this.answerChosen = answerChosen.trim();
	}

	/**
	 * question constructor
	 * @param questionid
	 * @param questiontitle
	 * @param questionType
	 * @param pointsWorth
	 * @param question
	 * @param answerChosen
	 * @param correctAnswerString
	 */
	public Question(int questionid, String questiontitle, String questionType, int pointsWorth, String question, String answerChosen, String correctAnswerString) {
		this.questionid = questionid;
		this.questiontitle = questiontitle;
		this.questionType = questionType;
		this.pointsWorth = pointsWorth;
		this.question = question;
		this.answerChosen = answerChosen.trim();
		this.correctAnswerString = correctAnswerString.trim();
	}
	public String getQuestionType() {
		return questionType;
	}
	public int getPointsWorth() {
		return pointsWorth;
	}
	public void setPointsWorth(int ptsworth) {
		this.pointsWorth = ptsworth;
	}
	
	public double setPointsReceived(double ptsreceived) {
		this.pointsReceived = ptsreceived;
		return ptsreceived;
	}
	
	public double getPointsReceived() {
		return pointsReceived;
	}
	
	public String getQuestion() {
		return question;
	}
	public String getQuestiontitle() {
		return questiontitle;
	}
//	public void setQuestiontitle(String questiontitle) {
//		this.questiontitle = questiontitle;
//	}
	public int getQuestionid() {
		return questionid;
	}
	
	public String getCorrectAnswerString() {
		return correctAnswerString;
	}
	public void setCorrectAnswerString(String CAnString) {
		this.correctAnswerString = CAnString.trim();
	}
	
	public String getAnswerChosen() { 
		return answerChosen;
	}
	public void setAnswerChosen(String AnswerChosen) {
		this.answerChosen = AnswerChosen.trim();
	}
	
	public abstract double calculatePtsReceived() ;
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	/*
	 * public void setQuestionid(int questionid) { this.questionid = questionid; }
	 */
}
