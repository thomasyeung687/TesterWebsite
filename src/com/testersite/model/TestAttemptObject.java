package com.testersite.model;

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
}
