package com.testersite.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import com.testersite.dao.DBConnection;

public class Student {
	String name;
	String profileid;
	ArrayList<Integer> testids;
	HashMap<Integer, TestAttemptObject>  attempts = new HashMap<Integer, TestAttemptObject>();
	
	public Student(String name, String profid){
		this.name = name;
		this.profileid = profid;
		
	}
	public Student(String name, String profid, ArrayList<Integer> testids){ //this student constructor can be provided with arraylist of testids in order to use fetch attempts method
		this.name = name;
		this.profileid = profid;
		this.testids = testids;
		
	}
	public String getname(){return name;}
	public String getprofid(){return profileid;}
	
	public HashMap<Integer, TestAttemptObject> getAttemptsFromDB() throws Exception{
		if(testids == null) {
			throw new Exception("Student object was not provided with ArrayList<Integer> testids!");
		}
		else {
			try {
				Connection connection = DBConnection.getDBConnection();
				Statement st = connection.createStatement();
				String idtestString = "";
				for(int testid : testids) {
					idtestString += testid + ",";
				}
				idtestString = idtestString.substring(0, idtestString.length()-1);//removeing last comma;
				
				String query = "SELECT * FROM testersitedatabase.attemptbook WHERE idstudentprofiles = "+this.profileid+" AND idtest in ("+idtestString+");";
				System.out.println(query);
				ResultSet rSet = st.executeQuery(query);
				
				if(rSet.next()){
					TestAttemptObject tao = new TestAttemptObject(rSet.getInt("idattempt"), rSet.getInt("attemptNumber"), rSet.getInt("idstudentprofiles"), rSet.getInt("idtest") ,rSet.getInt("grade"), rSet.getInt("gradeOutOf"));
					System.out.println(tao.getPercentageScore());
					this.attempts.put(rSet.getInt("idtest"),tao);
				}
				
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return attempts;
	}
	public HashMap<Integer, TestAttemptObject> getAttempts(){
		return attempts;
	}
	public TestAttemptObject getAttemptFromAttemptsHashMap(int testid) {
		
		TestAttemptObject tao = attempts.get(testid);
		if(tao == null) {
			System.out.println("Failed to get attempt with testid = "+testid);
			return null;
		}else {
			System.out.println("returning tao: "+tao.toString());
			return tao;
		}
	}
}
