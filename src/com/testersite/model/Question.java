package com.testersite.model;

public class Question {
	private int questionid;
	private String questionType;
	private int pointsWorth;
	private String question;
	private String questiontitle;
	public Question(String questionType, int pointsWorth, String question) {
		this.questionType = questionType;
		this.pointsWorth = pointsWorth;
		this.question = question;
	}
	public Question(int questionid, String questiontitle, String questionType, int pointsWorth, String question) {
		this.questionid = questionid;
		this.questiontitle = questiontitle;
		this.questionType = questionType;
		this.pointsWorth = pointsWorth;
		this.question = question;
	}
	public String getQuestionType() {
		return questionType;
	}
	public int getPointsWorth() {
		return pointsWorth;
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
	/*
	 * public void setQuestionid(int questionid) { this.questionid = questionid; }
	 */
}
