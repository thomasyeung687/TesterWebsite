package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipartQuestion extends Question {
	private List<Question> questions = new ArrayList<Question>();
	String questioncomponentids; // this will contain a string with the idquestion of the Question instances that make up this question
	public MultipartQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String questioncomponentids) {
		super(questionid, questiontitle,"MultipartQuestion", pointsWorth , question);
		this.questioncomponentids = questioncomponentids;
	}
	
	/**
	 * This method is used to add the question instances that make up this multipartquestion.
	 * @param question a Question instance that is not a multipart question.
	 */
	public void addQuestion(Question question) {
		questions.add(question);
	}
	
	public List<Question> getQuestions(){
		return questions;
	}
	public String getQuestionCompoentids(){
		return questioncomponentids;
	}
	public String toString() {
		String tostring = "";
		tostring += "Questionid ="+getQuestionid()+" \n";
		tostring += "Question Title ="+getQuestiontitle()+" \n";
		tostring += "Question text ="+getQuestion()+" \n";
		tostring += "questioncomponentids ="+getQuestionCompoentids()+" \n";
		tostring += "Points Worth ="+getPointsWorth();
		return tostring;
	}
}
