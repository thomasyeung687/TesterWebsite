package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FillInMultipleBlankQuestion extends Question{
	//blank would be x in [x] the blank in the question string is marked by [x] or a set of square brackets
	private ArrayList<String> strings = new ArrayList<String>(); // this is a list of strings. so if question = "the [x] cat" strings = ["the ", " cat"] used for being able to print onto jsp.
	private ArrayList<String> blank = new ArrayList<String>();
	private List<List<String>> correctans = new ArrayList<List<String>>();
	private List<String> answersGiven = new ArrayList<String>(); //list of answers given for each blank
	List<String> eachblankscorrectanswers = new ArrayList<String>();
	private boolean casesensitive;
	private boolean partialcredit;
	public FillInMultipleBlankQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String correctans, boolean casesensitive, boolean partialcredit) {
		super(questionid, questiontitle,"FillInTheBlankQuestion", pointsWorth, question); //default #question title is the num answers there are.
		if(!casesensitive) {
			setCorrectAnswerString(correctans.toLowerCase());
		}else {
			setCorrectAnswerString(correctans);
		}
		
		//System.out.println("helloworld");
		//String[] questionstrings= question.split("[x]");
		//System.out.println(questionstrings[0]);
		int start = 0;
		int endIndex = 0;
		while(question.indexOf("[",start)> -1) {
			//System.out.println(start);
			int startIndex = question.indexOf("[", start)+1;
			endIndex = question.indexOf("]", start);
			strings.add(question.substring(start, startIndex-1).trim());
			//System.out.println(startIndex);
			//System.out.println(endIndex);
			String blankString = question.substring(startIndex, endIndex); //this will contain x for [x] in question.
			//System.out.println(blankString);
			blank.add(blankString);
			start = endIndex+1; //so we dont find the same ]
		}
		strings.add(question.substring(endIndex+1).trim());
		if(strings.size() == blank.size()) { //that way if question string = The [a] cat ate its [b] food and [c] " " <- would be added. so the blanks are covered on both sides for every case.
			strings.add(" ");
		}
			
		/**/eachblankscorrectanswers= Arrays.asList(getCorrectAnswerString().split("\\|")); //fat~skinny~big~large~|smelly~disgusting~tasty~|napped~slept~coughed~| -> fat~skinny~big~large~,smelly~disgusting~tasty~,napped~slept~coughed~
		//System.out.println(eachblankscorrectanswers);
		List<List<String>> correctlyformatedcorrectans = new ArrayList<List<String>>();
		for(int i = 0; i < blank.size(); i++) {
			correctlyformatedcorrectans.add(Arrays.asList(eachblankscorrectanswers.get(i).split("~")));
			//System.out.println(correctlyformatedcorrectans);
		}
		this.correctans = correctlyformatedcorrectans;
		this.casesensitive = casesensitive;
		this.partialcredit = partialcredit;
	}
	public FillInMultipleBlankQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String correctans, boolean casesensitive, boolean partialcredit, String answerChosen) {
		super(questionid, questiontitle,"FillInTheBlankQuestion", pointsWorth, question); //default #question title is the num answers there are.
		if(!casesensitive) {
			setCorrectAnswerString(correctans.toLowerCase());
			answersGiven = Arrays.asList(answerChosen.toLowerCase().split("~")); //retrieving each blanks given answer
		}else {
			setCorrectAnswerString(correctans);
			answersGiven = Arrays.asList(answerChosen.split("~")); //retrieving each blanks given answer
		}
		setAnswerChosen(answerChosen);
		
		int start = 0;
		int endIndex = 0;
		while(question.indexOf("[",start)> -1) {
			//System.out.println(start);
			int startIndex = question.indexOf("[", start)+1;
			endIndex = question.indexOf("]", start);
			strings.add(question.substring(start, startIndex-1).trim());
			//System.out.println(startIndex);
			//System.out.println(endIndex);
			String blankString = question.substring(startIndex, endIndex); //this will contain x for [x] in question.
			//System.out.println(blankString);
			blank.add(blankString);
			start = endIndex+1; //so we dont find the same ]
		}
		strings.add(question.substring(endIndex+1).trim());
		if(strings.size() == blank.size()) { //that way if question string = The [a] cat ate its [b] food and [c] " " <- would be added. so the blanks are covered on both sides for every case.
			strings.add(" ");
		}
			
		/**/eachblankscorrectanswers= Arrays.asList(getCorrectAnswerString().split("\\|")); //fat~skinny~big~large~|smelly~disgusting~tasty~|napped~slept~coughed~| -> fat~skinny~big~large~,smelly~disgusting~tasty~,napped~slept~coughed~
		//System.out.println(eachblankscorrectanswers);
		List<List<String>> correctlyformatedcorrectans = new ArrayList<List<String>>();
		for(int i = 0; i < blank.size(); i++) {
			correctlyformatedcorrectans.add(Arrays.asList(eachblankscorrectanswers.get(i).split("~")));
			//System.out.println(correctlyformatedcorrectans);
		}
		this.correctans = correctlyformatedcorrectans;
		this.casesensitive = casesensitive;
		this.partialcredit = partialcredit;
		
	}
	public ArrayList<String> getBlank(){
		return blank;
	}
	public boolean isCasesensitive() {
		return casesensitive;
	}
	public void setCasesensitive(boolean casesensitive) {
		this.casesensitive = casesensitive;
	}
	public boolean isPartialcredit() {
		return partialcredit;
	}
	public void setPartialcredit(boolean partialcredit) {
		this.partialcredit = partialcredit;
	}
	public List<List<String>> getCorrectans() {
		return correctans;
	}
	public void setCorrectans(List<List<String>> correctans) {
		this.correctans = correctans;
	}
	public ArrayList<String> getStrings() {
		return strings;
	}
	public void setStrings(ArrayList<String> strings) {
		this.strings = strings;
	}
	public List<String> getEachBlanksCorrectAnswers(){
		return eachblankscorrectanswers;
	}
	public String toString() {
		String tostring = "";
		tostring += "Questionid ="+getQuestionid()+" \n";
		tostring += "Question Title ="+getQuestiontitle()+" \n";
		tostring += "Question text ="+getQuestion()+" \n";
		tostring += "Blanks list ="+getBlank()+" \n";
		tostring += "Strings list ="+getStrings()+" \n";
		tostring += "Eachblankscorrectanswers list ="+getEachBlanksCorrectAnswers()+" \n";
		tostring += "Correctans list ="+getCorrectans()+" \n";
		tostring += "Case Sensitive ="+isCasesensitive()+" \n";
		tostring += "Partial Credit ="+isPartialcredit()+" \n";
		tostring += "Points Worth ="+getPointsWorth();
		return tostring;
	}
	public static void main(String[] arg) {
		FillInMultipleBlankQuestion question = new FillInMultipleBlankQuestion(46, 10,"1", "The [a] cat ate its [b] food and [c]","Fat~skinny~big~large~|smelly~disgusting~tasty~|napped~slept~coughed~|", false, true, "fat~disgusting~napped~");
		System.out.println(question.toString());
		
		ArrayList<String> blank = question.getBlank();
		for(String blanks:blank) {
			System.out.println(blanks);
		}
		System.out.println(question.getCorrectans());
		System.out.println("pts worth:"+question.getPointsWorth()+" pts received:"+question.calculatePtsReceived());
	}
	@Override
	public double calculatePtsReceived() {
		double numberOfCorrectAnswers = 0;
		if(getAnswerChosen()==null) {
			System.out.println("There is no answer given. ID = " +this.getQuestionid());
			return -1;
		}
		if(answersGiven.size() != correctans.size()) {
			System.out.println("There are a different number of blanks and correct answers");
			return -1;
		}else {
			for(int i = 0; i < answersGiven.size() ; i++) {
				if(correctans.get(i).contains(answersGiven.get(i))) {
					numberOfCorrectAnswers++;
				}
			}
		}
		if(partialcredit) {
			return setPointsReceived((numberOfCorrectAnswers/answersGiven.size())*getPointsWorth());
		}else {
			if(numberOfCorrectAnswers != answersGiven.size()) {
				return setPointsReceived(0);
			}else {
				return setPointsReceived(getPointsWorth()); //if not every blank is correct, then return 0 else return full pts for this question
			}
		}
	}
}
