package com.testersite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CheckAllQuestion extends Question{
	private List<String> answers = new ArrayList<String>(); //choices in the checkall choice problem
	private List<String> correctanswers = new ArrayList<String>();
	private List<String> answersGiven = new ArrayList<String>();
	private String gradingType; //GRADING TYPES BELOW
	//AON (ALL OR NOTHING =  Users receive full points for the question only if they select all the correct answers and none of the incorrect answers. Users receive zero points for the entire question if they miss any correct answers or select any incorrect answers.), 
	//RMW (RIGHT MINUS WRONG = Users receive points equal to the number of right answers they choose minus the number of incorrect answers they choose. Users can receive a minimum of zero on a question), 
	//CA (CORRECT ANSWERS = Users receive points for each correct answer they select and for each incorrect answer they leave blank.)
	//CA NOT IMPLEMENTED
	private final String DEFAULT_GRADING_TYPE = "AON";

	/**
	 * the methoc creates a new checkallquestion and it splits the answers and the correctans param and stores it into the answers or the correctans list
	 *  respectively
	 * @param questionid id of question in sql db
 	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 * @param answers the answers given as a single string seperated with "~"
	 * @param correctans the answer choice that is correct
	 */
	public CheckAllQuestion(int questionid,  int pointsWorth, String questiontitle, String question, String answers, String correctans) {
		super(questionid, questiontitle,"CheckAllQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.setCorrectAnswerString(correctans);
		this.answers = Arrays.asList(answers.split("~"));
		this.correctanswers = Arrays.asList(correctans.split("~"));
	}

	/**
	 * the method creates a new checkallquestion and it splits the answers and the correctans param and stores it into the answers or the correctans list
	 *  respectively
	 *  This one allows the user to specify the grading type of this checkallquestion
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 * @param answers the answers given as a single string seperated with "~"
	 * @param correctans the answer choice that is correct
	 * @param gradingType //AON (ALL OR NOTHING =  Users receive full points for the question only if they select all the correct answers and none of the incorrect answers. Users receive zero points for the entire question if they miss any correct answers or select any incorrect answers.),
	 * 	//RMW (RIGHT MINUS WRONG = Users receive points equal to the number of right answers they choose minus the number of incorrect answers they choose. Users can receive a minimum of zero on a question),
	 * 	//CA (CORRECT ANSWERS = Users receive points for each correct answer they select and for each incorrect answer they leave blank.)
	 * 	//CA NOT IMPLEMENTED
	 */
	public CheckAllQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans,String gradingType) {
		super(questionid, questiontitle,"CheckAllQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.setCorrectAnswerString(correctans);
		this.answers = Arrays.asList(answers.split("~"));
		this.correctanswers = Arrays.asList(correctans.split("~"));
		if(gradingType.equals("AON")||gradingType.equals("RMW")||gradingType.equals("CA")) {
			this.gradingType = gradingType;
		}else {
			//default grading type here.
			this.gradingType = DEFAULT_GRADING_TYPE;
		}
	}

	/**
	 * the method creates a new checkallquestion and it splits the answers and the correctans param and stores it into the answers or the correctans list
	 *  respectively
	 *  This one allows specification of the grading type of this checkallquestion
	 *  as well as the answers chosen for this question
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param question the actual question text displayed when test is taken
	 * @param answers the answers given as a single string seperated with "~"
	 * @param correctans the answer choice that is correct
	 * @param gradingType //AON (ALL OR NOTHING =  Users receive full points for the question only if they select all the correct answers and none of the incorrect answers. Users receive zero points for the entire question if they miss any correct answers or select any incorrect answers.),
	 * 	//RMW (RIGHT MINUS WRONG = Users receive points equal to the number of right answers they choose minus the number of incorrect answers they choose. Users can receive a minimum of zero on a question),
	 * 	//CA (CORRECT ANSWERS = Users receive points for each correct answer they select and for each incorrect answer they leave blank.)
	 * 	//CA NOT IMPLEMENTED
	 * @param answersChosen the answers given. usually used in result of a user taking the test and answering this question
	 */
	public CheckAllQuestion(int questionid,  int pointsWorth,String questiontitle, String question, String answers, String correctans, String gradingType, String answersChosen) {
		super(questionid, questiontitle,"CheckAllQuestion", pointsWorth, question); //default #question title is the num answers there are.
		this.setCorrectAnswerString(correctans);
		
		this.setAnswerChosen(answersChosen); // does this as well: this.answersGiven = Arrays.asList(answersChosen.split("~"));
		
		this.answers = Arrays.asList(answers.split("~"));
		this.correctanswers = Arrays.asList(correctans.split("~"));
		
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
	public void setAnswerChosen(String answersChosen) {
		super.setAnswerChosen(answersChosen);
		answersGiven = Arrays.asList(answersChosen.split("~"));
	}
	public List<String> getAnswersGiven(){
		return answersGiven;
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
		CheckAllQuestion mc1 = new CheckAllQuestion(1, 10,"1", "helloo", "asdad~1 3 6 9~asd~", "asdad~1 3 6 9~", "RMW");
		mc1.setAnswerChosen("asdad~1 3 6 9~asd");
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
	/**
	 * This method will be able to score this checkallquestion based on the grading type.
	 */
	public double calculatePtsReceived() {
		System.out.println("Question: "+this.getQuestion());
		System.out.println("Answer  : "+this.getCorrectAnswerString());
		if(getAnswerChosen() == null) {
			System.out.println("There is no answer given. ID = " +this.getQuestionid());
			System.out.println("Points Received: -1");
			return -1;
		}
		System.out.println("Answer Chosen: "+this.answersGiven);
		if(gradingType == null || gradingType.equals("AON")) { //AON (ALL OR NOTHING =  Users receive full points for the question only if they select all the correct answers and none of the incorrect answers. Users receive zero points for the entire question if they miss any correct answers or select any incorrect answers.), 
			Collections.sort(correctanswers);
			Collections.sort(answersGiven);
			if(correctanswers.equals(answersGiven)) {
				System.out.println("Points Received: "+this.getPointsWorth());
				return setPointsReceived(getPointsWorth());
			}else {
				System.out.println("Points Received: 0");
				return setPointsReceived(0);
			}
		}else if(gradingType.equals("RMW")) { //RMW (RIGHT MINUS WRONG = Users receive points equal to the number of right answers they choose minus the number of incorrect answers they choose. Users can receive a minimum of zero on a question), 
			//To calculate how much each answer is worth, the system takes the total number of points assigned to the question and divides it by the total number of answer choices.
			double ptsWorthEachQuestion = (double)getPointsWorth()/answers.size();
			Collections.sort(correctanswers);
			Collections.sort(answersGiven);
			if(correctanswers.equals(answersGiven)) {
				System.out.println("Points Received: "+this.getPointsWorth());
				return setPointsReceived(getPointsWorth());
			}else {
				ArrayList<String> incorrectAnswersChosen =  new ArrayList<String>();
				for( String ans : answersGiven) {
					incorrectAnswersChosen.add(ans);
				}
				incorrectAnswersChosen.removeAll(correctanswers); //this will give us the amount of answers that were given but not correct.
				//amt correct is answersChosen.size() - incorrectAnswersChosen.size()   amt incorrect is incorrectAnswersChosen.size();
				int amtCorrect = answersGiven.size() - incorrectAnswersChosen.size();
				int amtIncorrect = incorrectAnswersChosen.size();
//				System.out.println(incorrectAnswersChosen);
//				System.out.println(answersChosen);
//				System.out.println("amtCorrect "+amtCorrect);
//				System.out.println("amtIncorrect "+amtIncorrect);
				double ptsReceived = amtCorrect*ptsWorthEachQuestion - amtIncorrect*ptsWorthEachQuestion;
				String ptsWorthEachQuestionString = String.format("%.2f", ptsWorthEachQuestion);
				String ptsReceivedString = String.format("%.2f", ptsReceived);
				
				System.out.println("Points Received: amtCorrect ("+amtCorrect+")*ptsWorthEachQuestion ("+ptsWorthEachQuestionString+")- amtIncorrect ("+amtIncorrect+")*ptsWorthEachQuestion ("+ptsWorthEachQuestionString+") = "+ ptsReceivedString);
				return setPointsReceived(ptsReceived);
			}
		}
//		else if(gradingType.equals("CA")) { //CA (CORRECT ANSWERS = Users receive points for each correct answer they select and for each incorrect answer they leave blank.)
//			
//		}
		//dunno if this is needed.
		System.out.println("Invalid gradingType!!");
		System.out.println("Points Received: -1");
		return -1;
	}
	
}
