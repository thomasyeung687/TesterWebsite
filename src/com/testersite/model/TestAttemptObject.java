package com.testersite.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.testersite.dao.DBConnection;

public class TestAttemptObject {
	int idattempt = 0;
	int attemptNumber = 0;
	int idstudentprofiles = 0;
	int idtest = 0;
	int grade = 0;
	int gradeOutOf = 0;
	double percentageScore = 0;
	String notes;

	/**
	 *
	 * @param idattempt the id of the attempt in the db
	 * @param attemptNumber the count of the number of times the test was taken (attempt number)
 	 * @param idstudentprofiles student who this attempt belongs to
	 * @param idtest the id of the test taken in this attempt
	 * @param grade the number of points received in this attempt
	 * @param gradeOutOf the number of points that was possible to get on the test
	 * @param notes notes that were given by professor after grading the exam
	 */
	public TestAttemptObject(int idattempt, int attemptNumber,int idstudentprofiles, int idtest,int grade, int gradeOutOf,String notes) {
		this.idattempt = idattempt;
		this.attemptNumber = attemptNumber;
		this.idstudentprofiles = idstudentprofiles;
		this.idtest = idtest;
		this.grade = grade;
		this.gradeOutOf = gradeOutOf;
		this.notes = notes;
		
		percentageScore = (double)grade / (double) gradeOutOf;
		percentageScore = percentageScore * 100*100; //first 100 is to make the percentage score out of 100 percent. the second 100 is process of getting decimal to 2 places
		percentageScore = Math.round(percentageScore);
		percentageScore = percentageScore/100; //2 decimal places
	}
	
	public int getIdAttempt() {
		return idattempt;
	}
	public int getAttemptNumber() {
		return attemptNumber;
	}
	public int getidstudentprofiles() {
		return idstudentprofiles;
	}
	public int getidtest() {
		return idtest;
	}
	public int getgrade() {
		return grade;
	}
	public int getgradeOutOf() {
		return gradeOutOf;
	}
	public double getPercentageScore() {
		return percentageScore;
	}
	public String getNotes() {
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String toString() {
		return "idattempt: "+idattempt+", attemptNumber: "+attemptNumber+", idstudentprofiles: "+idstudentprofiles+", idtest: "+idtest+", grade: "+grade+", gradeOutOf: "+gradeOutOf+", notes: "+notes;
	}
	
	/**
	 * retreived attempt information using idstudentprofiles and idtest by finding a row in the attempts table with those two value
	 * @param idstudentprofiles Student profile id in DB
	 * @param idtest test id in DB
	 * @return TestAttemptObject if TAO is found in DB. Else returns a null;
	 */
	public static TestAttemptObject getAttemptFromDB(String idstudentprofiles, String idtest) {
		Connection connection = DBConnection.getDBConnection();
		TestAttemptObject tao = null;
		try {
			Statement st1 = connection.createStatement();
			String querString = "SELECT * FROM testersitedatabase.attemptbook WHERE idtest = "+idtest+" AND idstudentprofiles= "+idstudentprofiles+";";
			System.out.println("tao78 "+querString);
			ResultSet rSet1 = st1.executeQuery(querString);
			
			if(rSet1.next()){
				tao = new TestAttemptObject(rSet1.getInt("idattempt"), rSet1.getInt("attemptNumber"), rSet1.getInt("idstudentprofiles"), rSet1.getInt("idtest") ,rSet1.getInt("grade"), rSet1.getInt("gradeOutOf"), rSet1.getString("notes"));
				System.out.println("TAO method % score:"+tao.getPercentageScore()+"TAO:"+tao.toString());
			}else {
				System.out.println("TAO not found!");
			}
		} catch (Exception e) {
			System.out.print("TAO method "+ e.getLocalizedMessage());
		}
		return tao;
	}
	
	/**
	 * gets the attempt with the id of parameter idattempt
	 * @param idattempt primary key for an attempt stored in DB
	 * @return TestAttemptObject if TAO is found in DB. Else returns a null;
	 */
	public static TestAttemptObject getAttemptFromDB(String idattempt) {
		Connection connection = DBConnection.getDBConnection();
		TestAttemptObject tao = null;
		try {
			Statement st1 = connection.createStatement();
			ResultSet rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.attemptbook WHERE idattempt = "+idattempt+";");
			if(rSet1.next()){
				tao = new TestAttemptObject(rSet1.getInt("idattempt"), rSet1.getInt("attemptNumber"), rSet1.getInt("idstudentprofiles"), rSet1.getInt("idtest") ,rSet1.getInt("grade"), rSet1.getInt("gradeOutOf"), rSet1.getString("notes"));
				System.out.println("TAO method % score:"+tao.getPercentageScore()+"TAO:"+tao.toString());
			}else {
				System.out.println("TAO not found!");
			}
		} catch (Exception e) {
			System.out.print("TAO method "+ e.getLocalizedMessage());
		}
		return tao;
	}
}
