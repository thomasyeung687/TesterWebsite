package com.testersite.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;

public class MultipartQuestion extends Question {
	private ArrayList<Question> questions = new ArrayList<Question>();
	String questioncomponentids; // this will contain a string with the idquestion of the Question instances that make up this question

	/**
	 *
	 * @param questionid id of question in sql db
	 * @param pointsWorth num of points worth
	 * @param questiontitle title of question
	 * @param questioncomponentids a string with all the question components seperated by commas
	 */
	public MultipartQuestion(int questionid,String questiontitle, String question, String questioncomponentids) {
		super(questionid, questiontitle,"MultipartQuestion" , question);
		this.questioncomponentids = questioncomponentids;
		GetQuestions();
		this.getPointsWorth();
	}
	public MultipartQuestion(int questionid,String questiontitle, String question, String questioncomponentids, String idAttempt) {//gets questions with the answers given by student that created attempt with idAttempt
		super(questionid, questiontitle,"MultipartQuestion", question);
		this.questioncomponentids = questioncomponentids;
		GetQuestions(idAttempt); //gets questions with the answers given by student that created attempt with idAttempt
		this.getPointsWorth();
	}
	
	/**
	 * This method is used to add the question instances that make up this multipartquestion.
	 * @param question a Question instance that is not a multipart question.
	 */
	public void addQuestion(Question question) {
		questions.add(question);
	}

	/**
	 * sets a question at a certain index in the array
	 * @param index position of question to change
	 * @param question question to replace question at index with
	 */
	public void setQuestionInQuestionsArray(int index, Question question) {
		questions.set(index, question);
	}

	public ArrayList<Question> getQuestions(){
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

	/**
	 * THis method retreives the questions that make up this multipart question from the databse using the
	 * questions components variable
	 */
	private void GetQuestions() { //gets the questions from the DB using the questioncompoentids string.
		String questioncomponents = getQuestionCompoentids();
		String[] questionids = questioncomponents.split(",");
		
//		System.out.println("MPQ ids = ");
//		for(int i = 0; i < questionids.length; i++) {
//			System.out.print(questionids[i]+" "); //check to see if we get the question ids properly
//		}
		
		Connection con = DBConnection.getDBConnection();
		try{
			Statement st = con.createStatement();
			String query = "SELECT * FROM questionsdatabase.allquestiontable WHERE idquestion in ("+questioncomponentids.substring(0,questioncomponentids.length()-1)+");";
			ResultSet rset = st.executeQuery("SELECT * FROM questionsdatabase.allquestiontable WHERE idquestion in ("+questioncomponentids.substring(0,questioncomponentids.length()-1)+");"); //substring gets rid of last comma which causes sql error
			System.out.println("MPQ query "+ query);
			while(rset.next()){ //while there are questions from allquestionstable with that idtest. we go into each row and get the table and questionid and find that question
				//System.out.println("in");
				
				
				String idquestion = rset.getString("idquestion");
				String tablename = rset.getString("tablename");
				Statement st2 = con.createStatement();
				ResultSet rset2 = st2.executeQuery("SELECT * FROM "+tablename+" WHERE idquestion='"+idquestion+"';");
				if(rset2.next()){
					if(tablename.equals("questionsdatabase.multiplechoice")){
						System.out.println("Adding new mc question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						MultipleChoiceQuestion newmcq = new MultipleChoiceQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"),rset2.getString("answerstring"), rset2.getString("correctanswer"));
						//System.out.println(newmcq.getAnswers().toString());
						questions.add(newmcq);
					}else if(tablename.equals("questionsdatabase.truefalse")){
						System.out.println("Adding new TF question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						TFQuestion newmcq = new TFQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"));
						questions.add(newmcq);
					}else if(tablename.equals("questionsdatabase.shortanswer")){
						System.out.println("Adding new SR question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						ShortResponseQuestion newmcq = new ShortResponseQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"));
						questions.add(newmcq);
					}else if(tablename.equals("questionsdatabase.checkall")){
						System.out.println("Adding new CheckAll question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						CheckAllQuestion newmcq = new CheckAllQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("answerstring"), rset2.getString("correctstring"));
						System.out.println(newmcq.toString());
						questions.add(newmcq);
					}else if(tablename.equals("questionsdatabase.fillintheblank")){
						System.out.println("Adding new fillintheblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						FillInTheBlankQuestion newfib = new FillInTheBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"));
						System.out.println(newfib.toString());
						questions.add(newfib);
					}else if(tablename.equals("questionsdatabase.fillinmultipleblank")){
						System.out.println("Adding new fillinmultipleblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth"));
						System.out.println("Question:"+rset2.getString("question"));
						FillInMultipleBlankQuestion newfib = new FillInMultipleBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"),rset2.getBoolean("partialcredit"));
						//System.out.println(newfib.toString());
						questions.add(newfib);
					}
					//here you can add it for others.
				}else{
					System.out.println("Couldn't get question "+idquestion);
				}
			}
			//rset for other questiontypes V
		}catch(Exception exception){
			System.out.println("MultipartQuestion Exception trying to fetch question components");
			System.out.println(exception.getLocalizedMessage());
		}
	}

	/**
	 * used in Test.getCompletedTestFromDB() when we encounter a multipart question
	 * @param idAttempt this will go into the db and look for an attempt with this id.
	 *                  it will then get the multipart question using the resultset of that search
	 */
	private void GetQuestions(String idAttempt) {//used in Test.getCompletedTestFromDB() when we encounter a multipart question 
		String questioncomponents = getQuestionCompoentids();
		String[] questionids = questioncomponents.split(",");
		
//		System.out.println("MPQ ids = ");
//		for(int i = 0; i < questionids.length; i++) {
//			System.out.print(questionids[i]+" "); //check to see if we get the question ids properly
//		}
		
		Connection con = DBConnection.getDBConnection();
		try{
			Statement st = con.createStatement();
			String query = "SELECT * FROM questionsdatabase.allquestiontable WHERE idquestion in ("+questioncomponentids.substring(0,questioncomponentids.length()-1)+");";
			ResultSet rset = st.executeQuery(query); //substring gets rid of last comma which causes sql error ^^^^^
			System.out.println("MPQ query "+ query);
			while(rset.next()){ //while there are questions from allquestionstable with that idtest. we go into each row and get the table and questionid and find that question
				//System.out.println("in");
				
				
				String idquestion = rset.getString("idquestion");
				String tablename = rset.getString("tablename");
				String query2 = "Select * FROM "+tablename+" INNER JOIN testersitedatabase.attempt_answer_choice "
						+ "on "+tablename+".idquestion = testersitedatabase.attempt_answer_choice.idquestion WHERE idattempt = "+idAttempt+" AND testersitedatabase.attempt_answer_choice.idquestion = "+idquestion+";";
				System.out.println("MPQ query2 "+ query2);
				Statement st2 = con.createStatement();
				ResultSet rset2 = st2.executeQuery(query2);
				if(rset2.next()){
					if(tablename.equals("questionsdatabase.multiplechoice")){
						System.out.println("Adding new mc question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth")+" pts received "+rset2.getDouble("ptsGiven"));
						MultipleChoiceQuestion newq = new MultipleChoiceQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"),rset2.getString("answerstring"), rset2.getString("correctanswer"));
						newq.setAnswerChosen(rset2.getString("answerGiven"));
						newq.setPointsReceived(rset2.getDouble("ptsGiven"));
						newq.setNotes(rset2.getString("notes"));
						System.out.println("MPQ"+newq.toString());
						questions.add(newq);
					}else if(tablename.equals("questionsdatabase.truefalse")){
						System.out.println("Adding new TF question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth")+" pts received "+rset2.getDouble("ptsGiven"));
						TFQuestion newq = new TFQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"));
						newq.setAnswerChosen(rset2.getString("answerGiven"));
						newq.setPointsReceived(rset2.getDouble("ptsGiven"));
						newq.setNotes(rset2.getString("notes"));
						System.out.println("MPQ"+newq.toString());
						questions.add(newq);
					}else if(tablename.equals("questionsdatabase.shortanswer")){
						System.out.println("Adding new SR question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth")+" pts received "+rset2.getDouble("ptsGiven"));
						ShortResponseQuestion newq = new ShortResponseQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"));
						newq.setAnswerChosen(rset2.getString("answerGiven"));
						newq.setPointsReceived(rset2.getDouble("ptsGiven"));
						newq.setNotes(rset2.getString("notes"));
						System.out.println("MPQ"+newq.toString());
						questions.add(newq);
					}else if(tablename.equals("questionsdatabase.checkall")){
						System.out.println("Adding new CheckAll question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth")+" pts received "+rset2.getDouble("ptsGiven"));
						CheckAllQuestion newq = new CheckAllQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("answerstring"), rset2.getString("correctstring"));
						newq.setAnswerChosen(rset2.getString("answerGiven"));
						newq.setPointsReceived(rset2.getDouble("ptsGiven"));
						newq.setNotes(rset2.getString("notes"));
						System.out.println("MPQ"+newq.toString());
						questions.add(newq);
					}else if(tablename.equals("questionsdatabase.fillintheblank")){
						System.out.println("Adding new fillintheblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth")+" pts received "+rset2.getDouble("ptsGiven"));
						FillInTheBlankQuestion newq = new FillInTheBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"));
						newq.setAnswerChosen(rset2.getString("answerGiven"));
						newq.setPointsReceived(rset2.getDouble("ptsGiven"));
						newq.setNotes(rset2.getString("notes"));
						System.out.println("MPQ"+newq.toString());
						questions.add(newq);
					}else if(tablename.equals("questionsdatabase.fillinmultipleblank")){
						System.out.println("Adding new fillinmultipleblank question "+rset.getInt("idquestion")+" points worth "+rset2.getInt("pointsworth")+" pts received "+rset2.getDouble("ptsGiven"));
						System.out.println("Question:"+rset2.getString("question"));
						FillInMultipleBlankQuestion newq = new FillInMultipleBlankQuestion(rset2.getInt("idquestion"), rset2.getInt("pointsworth"),rset2.getString("questiontitle"), rset2.getString("question"), rset2.getString("correctanswer"), rset2.getBoolean("casesensitive"),rset2.getBoolean("partialcredit"));
						newq.setAnswerChosen(rset2.getString("answerGiven"));
						newq.setPointsReceived(rset2.getDouble("ptsGiven"));
						newq.setNotes(rset2.getString("notes"));
						System.out.println("MPQ"+newq.toString());
						questions.add(newq);
					}
					//here you can add it for others.
				}else{
					System.out.println("MPQ Couldn't get question "+idquestion);
				}
			}
			this.calculatePtsReceived();//calculates the pts received for this multipart.
			//rset for other questiontypes V
		}catch(Exception exception){
			System.out.println("MultipartQuestion Exception trying to fetch question components");
			System.out.println(exception.getLocalizedMessage());
		}
	}

	@Override
	/**
	 * This method iterates through every question in the questions arraylist and calls getPointsRecevied() on each
	 * one summing it up and returning it.
	 */
	public double calculatePtsReceived() {
		int totalPtsReceived = 0;
		for(Question question: questions) { //for each question in this multipart question, calculate pts received.
			totalPtsReceived += question.getPointsReceived(); //accumulating the ptsreceived of each question.
		}
		super.setPointsReceived(totalPtsReceived); //setting pts received in the parent class
		return totalPtsReceived;
	}

	/**
	 * This method goes through each question in the questions array and returns the total number of pts this mpq is worth.
	 * It also sets this ptsworth to the total.
	 */
	public int getPointsWorth() {
		int sum = 0;
		for(Question q : questions) {
			sum += q.getPointsWorth();
		}
		super.setPointsWorth(sum);
		return sum;
	}
}
