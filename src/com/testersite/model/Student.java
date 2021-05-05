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
	String username;
	String email;
	ArrayList<Integer> testids; //arraylist of test ids.
	HashMap<Integer, TestAttemptObject>  attempts = new HashMap<Integer, TestAttemptObject>(); //hashmaps of attempts with key = testid and value = TestAttemptObject

	/**
	 * Student object constructor
	 * @param name of student
  	 * @param profid id of student in the db
	 */
	public Student(String name, String profid){
		this.name = name;
		this.profileid = profid;
		
	}

	/**
	 *
	 * Student object constructor
	 * @param name of student
	 * @param profid id of student in the db
	 * @param testids an arraylist of the ids of tests in the db
	 */
	public Student(String name, String profid, ArrayList<Integer> testids){ //this student constructor can be provided with arraylist of testids in order to use fetch attempts method
		this.name = name;
		this.profileid = profid;
		this.testids = testids;
		
	}

	/**
	 * will use the ResultSet obj provided to pull the Student information from to create a Student obj
	 * @param rset rset to extract Student information from
	 * @throws Exception if error was thrown
	 */
	public Student(ResultSet rSet) throws Exception {
		Connection con = DBConnection.getDBConnection();
		try {
			this.name = rSet.getString("name");
			this.profileid = rSet.getString("idstudentprofiles");
			this.username = rSet.getString("username");
			this.email = rSet.getString("email");
		} catch (Exception e) {
			System.out.print("Student.java error: "+e.getMessage());
			throw e;
		}
	}
	public String getname(){return name;}
	public String getprofid(){return profileid;}

	/**
	 *  used to get a students attempts for every test
	 * @return a hashmap of key = testid and value = TestAttemptObject
	 * @throws Exception if error was thrown
	 */
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
				TestAttemptObject tao = TestAttemptObject.getAttemptFromDB(this.profileid, idtestString);
				System.out.println(tao.getPercentageScore());
				this.attempts.put(tao.idtest,tao);
				
			}catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return attempts;
	}
	public HashMap<Integer, TestAttemptObject> getAttempts(){
		return attempts;
	}

	/**
	 * fetches a TestAttemptObject from the hashmap of TestAttemptObjects
	 * @param testid the key used to fetch the TestAttemptObject associated with it from the hashmap
	 * @return TestAttemptObject
	 */
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
	public String toString() {
		String str = "profileid: "+ profileid + " name: "+ name + " username: "+ username + " email: "+ email + " testids: "+ testids +"\n";
		return str;
	}
}
