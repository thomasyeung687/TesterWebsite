package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CheckAllQuestion extends Question{
	private List<String> answers = new ArrayList<String>(); //choices in the checkall choice problem
	private List<String> correctanswers = new ArrayList<String>();
	private List<String> answersChosen = new ArrayList<String>();
	private String gradingType; //GRADING TYPES BELOW
	//AON (ALL OR NOTHING =  Users receive full points for the question only if they select all the correct answers and none of the incorrect answers. Users receive zero points for the entire question if they miss any correct answers or select any incorrect answers.), 
	//RMW (RIGHT MINUS WRONG = Users receive points equal to the number of right answers they choose minus the number of incorrect answers they choose. Users can receive a minimum of zero on a question), 
	//CA (CORRECT ANSWERS = Users receive points for each correct answer they select and for each incorrect answer they leave blank.)
	//CA NOT IMPLEMENTED
	private final String DEFAULT_GRADING_TYPE = "AON";
	public CheckAllQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans) {
		super(questionid, questiontitle,"CheckAllQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.setCorrectAnswerString(correctans);
		this.answers = Arrays.asList(answers.split("~"));
		this.correctanswers = Arrays.asList(correctans.split("~"));
	}
	public CheckAllQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans, String answersChosen, String gradingType) {
		super(questionid, questiontitle,"CheckAllQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.setCorrectAnswerString(correctans);
		this.setAnswerChosen(answersChosen);
		this.answers = Arrays.asList(answers.split("~"));
		this.correctanswers = Arrays.asList(correctans.split("~"));
		this.answersChosen = Arrays.asList(answersChosen.split("~"));
		if(gradingType.equals("AON")||gradingType.equals("RMW")||gradingType.equals("CA")) {
			this.gradingType = gradingType;
		}else {
			//default grading type here.
			this.gradingType = DEFAULT_GRADING_TYPE;
		}
	}
	public List<String> getCorrectanswers() {
		return correctanswers;
	}
	public List<String> getAnswers() {
		return answers;
	}
	public String toString() {
		String tostring = "";
		tostring += getQuestionid()+" ";
		tostring += getQuestiontitle()+" ";
		tostring += getQuestion()+" ";
		tostring += getAnswers()+" ";
		tostring += getCorrectanswers()+" ";
		tostring += getPointsWorth();
		return tostring;
	}
	public static void main(String[] args) {
		CheckAllQuestion mc1 = new CheckAllQuestion(1, 10,"1", "helloo", "asdad~1 3 6 9~asd~", "asdad~1 3 6 9~", "asdad~1 3 6 9~asd", "RMW");
		List<String> anschoicess = mc1.getAnswers();
		List<String> correctans = mc1.getCorrectanswers();
		System.out.println(anschoicess.toString());
		if(correctans.contains(anschoicess.get(1))) {
			System.out.println(anschoicess.get(1));
		}
		System.out.println(mc1.toString());
		System.out.println(mc1.calculatePtsReceived());
		
	}
	@Override
	public double calculatePtsReceived() {
		if(getAnswerChosen() == null) {
			System.out.println("There is no answer given. ID = " +this.getQuestionid());
			return -1;
		}
		if(gradingType.equals("AON")) { //AON (ALL OR NOTHING =  Users receive full points for the question only if they select all the correct answers and none of the incorrect answers. Users receive zero points for the entire question if they miss any correct answers or select any incorrect answers.), 
			Collections.sort(correctanswers);
			Collections.sort(answersChosen);
			if(correctanswers.equals(answersChosen)) {
				return setPointsReceived(getPointsWorth());
			}else {
				return setPointsReceived(0);
			}
		}else if(gradingType.equals("RMW")) { //RMW (RIGHT MINUS WRONG = Users receive points equal to the number of right answers they choose minus the number of incorrect answers they choose. Users can receive a minimum of zero on a question), 
			//To calculate how much each answer is worth, the system takes the total number of points assigned to the question and divides it by the total number of answer choices.
			double ptsWorthEachQuestion = (double)getPointsWorth()/answers.size();
			Collections.sort(correctanswers);
			Collections.sort(answersChosen);
			if(correctanswers.equals(answersChosen)) {
				return setPointsReceived(getPointsWorth());
			}else {
				ArrayList<String> incorrectAnswersChosen =  new ArrayList<String>();
				for( String ans : answersChosen) {
					incorrectAnswersChosen.add(ans);
				}
				incorrectAnswersChosen.removeAll(correctanswers); //this will give us the amount of answers that were given but not correct.
				//amt correct is answersChosen.size() - incorrectAnswersChosen.size()   amt incorrect is incorrectAnswersChosen.size();
				int amtCorrect = answersChosen.size() - incorrectAnswersChosen.size();
				int amtIncorrect = incorrectAnswersChosen.size();
//				System.out.println(incorrectAnswersChosen);
//				System.out.println(answersChosen);
//				System.out.println("amtCorrect "+amtCorrect);
//				System.out.println("amtIncorrect "+amtIncorrect);
				return setPointsReceived(amtCorrect*ptsWorthEachQuestion - amtIncorrect*ptsWorthEachQuestion);
			}
		}
//		else if(gradingType.equals("CA")) { //CA (CORRECT ANSWERS = Users receive points for each correct answer they select and for each incorrect answer they leave blank.)
//			
//		}
		//dunno if this is needed.
		System.out.println("Invalid gradingType!!");
		return -1;
	}
	
}
