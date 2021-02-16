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
	
	public TestAttemptObject(int idattempt, int attemptNumber,int idstudentprofiles, int idtest,int grade, int gradeOutOf) {
		this.idattempt = idattempt;
		this.attemptNumber = attemptNumber;
		this.idstudentprofiles = idstudentprofiles;
		this.idtest = idtest;
		this.grade = grade;
		this.gradeOutOf = gradeOutOf;
		
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
	public String toString() {
		return "idattempt: "+idattempt+", attemptNumber: "+attemptNumber+", idstudentprofiles: "+idstudentprofiles+", idtest: "+idtest+", grade: "+grade+", gradeOutOf: "+gradeOutOf;
	}
	public static TestAttemptObject getAttemptFromDB(String idstudentprofiles, String idtest) {
		Connection connection = DBConnection.getDBConnection();
		TestAttemptObject tao = null;
		try {
			Statement st1 = connection.createStatement();
			ResultSet rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.attemptbook WHERE idtest = "+idtest+" AND idstudentprofiles= "+idstudentprofiles+";");
			if(rSet1.next()){
				tao = new TestAttemptObject(rSet1.getInt("idattempt"), rSet1.getInt("attemptNumber"), rSet1.getInt("idstudentprofiles"), rSet1.getInt("idtest") ,rSet1.getInt("grade"), rSet1.getInt("gradeOutOf"));
				System.out.println("TAO method % score:"+tao.getPercentageScore());
			}else {
				throw new SQLException("Couldnt find attempt from attemptbook");
			}
		} catch (Exception e) {
			System.out.print("TAO method "+ e.getLocalizedMessage());
		}
		return tao;
	}
	
	public static TestAttemptObject getAttemptFromDB(String idattempt) {
		Connection connection = DBConnection.getDBConnection();
		TestAttemptObject tao = null;
		try {
			Statement st1 = connection.createStatement();
			ResultSet rSet1 = st1.executeQuery("SELECT * FROM testersitedatabase.attemptbook WHERE idattempt = "+idattempt+";");
			if(rSet1.next()){
				tao = new TestAttemptObject(rSet1.getInt("idattempt"), rSet1.getInt("attemptNumber"), rSet1.getInt("idstudentprofiles"), rSet1.getInt("idtest") ,rSet1.getInt("grade"), rSet1.getInt("gradeOutOf"));
				System.out.println("TAO method % score:"+tao.getPercentageScore());
			}else {
				throw new SQLException("Couldnt find attempt from attemptbook");
			}
		} catch (Exception e) {
			System.out.print("TAO method "+ e.getLocalizedMessage());
		}
		return tao;
	}
}
