package com.testersite.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import com.testersite.dao.DBConnection;

import Random.RandomString;

public class TesterClass {
	String idclass = "not set";
	String idprofessorprofiles = "not set";
	String professorName = "not set";
	String courseprefix = "not set";
	String coursenumber = "not set";
	String coursename = "not set";
	String datestart = "not set";
	String dateend = "not set";
	String classcode = "not set";
	String semester = "not set";
	ArrayList<Student> students = new ArrayList<Student>();


	/**
	 * will use the idclass param to find all the information of the specified class and create a TesterClass obj
	 * @param idclass id of class to fetch from db
	 * @throws Exception
	 */
	public TesterClass(String idclass) throws Exception {
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rset = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE idclass = '"+idclass+"'");
			if(rset.next()) {
				this.idclass = idclass;
				this.idprofessorprofiles = rset.getString("idprofessorprofiles");
				this.courseprefix = rset.getString("courseprefix");
				this.coursenumber = rset.getString("coursenumber");
				this.coursename = rset.getString("coursename");
				this.datestart = rset.getString("datestart");
				this.dateend = rset.getString("dateend");
				this.classcode = rset.getString("classcode");
				this.semester = rset.getString("semester");
				this.students = geStudents(this.idclass); //gets students in the class
			}else {
				throw new Exception("Class with that id does not exist!");
			}
		} catch (Exception e) {
			System.out.print("TesterClass.java error: "+e.getMessage());
			throw e;
		}
	}

	/**
	 * method that creates a testClass object from a rset of rows of testclass information from the table testersitedatabase.allclasses
	 * @param rSet of a query of table testersitedatabase.allclasses
	 * @throws Exception whenever an exception arises it will throw it to the caller (usually will be an sql exception)
	 */
	public TesterClass(ResultSet rSet) throws Exception {
		this.idclass = rSet.getString("idclass");
		this.idprofessorprofiles = rSet.getString("idprofessorprofiles");
		this.courseprefix = rSet.getString("courseprefix");
		this.coursenumber = rSet.getString("coursenumber");
		this.coursename = rSet.getString("coursename");
		this.datestart = rSet.getString("datestart");
		this.dateend = rSet.getString("dateend");
		this.semester = rSet.getString("semester").equals("") ? "Not Set" : rSet.getString("semester"); // if not "" then sets semester as semester
		this.classcode = rSet.getString("classcode");
		this.students = geStudents(this.idclass); //gets students in the class
	}
	
	/**
	 * querys the sb for all classes of professor profid
	 * @param profid professor primary key
	 * @return an arraylist of testerclass objects
	 * @throws Exception whenever an exception arises it will throw it to the caller (usually will be an sql exception)
	 */
	public static ArrayList<TesterClass> geTesterClasses(String profid) throws Exception{
		ArrayList<TesterClass> testerClasses = new ArrayList<TesterClass>();
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rset = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE idprofessorprofiles = "+profid+";");
			while(rset.next()) {
				testerClasses.add(new TesterClass(rset));
			}
		} catch (Exception e) {
			System.out.print("TesterClass.java error: "+e.getMessage());
			throw e;
		}
		return testerClasses;
	}
	
	/**
	 * querys db for all students in the class idclass
	 * @param idclass id of class we want to get students of
	 * @return  an arraylist of students.
	 * @throws Exception whenever an exception arises it will throw it to the caller (usually will be an sql exception)
	 */
	public static ArrayList<Student> geStudents(String idclass) throws Exception{
		ArrayList<Student> students = new ArrayList<Student>();
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			String innerJoin = "SELECT *\r\n" + 
					"FROM testersitedatabase.studenttoclass\r\n" + 
					"INNER JOIN testersitedatabase.studentprofiles\r\n" + 
					"ON testersitedatabase.studenttoclass.idstudentprofiles = testersitedatabase.studentprofiles.idstudentprofiles WHERE classid = "+idclass+";";
			ResultSet rset = st.executeQuery(innerJoin);
			while(rset.next()) {
				students.add(new Student(rset));
			}
		} catch (Exception e) {
			System.out.print("TesterClass.java error: "+e.getMessage());
			throw e;
		}
		return students;
	}
	
	/**
	 * 
	 * @param idprofessorprofiles the id of professor that created this test
	 * @param courseprefix ex CSE
	 * @param coursenumber ex 214
	 * @param coursename ex Introduction to Computer Science
	 * @param datestart start date of class
	 * @param dateend end date of class
	 * @param semester semester that this class will take place
	 * @param newClasscode new class code- if its "" then the method will generate a unique one using 
	 * @param idadminprofiles
	 * @return String that is the id of the new class created "null" if class was not created.
	 * @throws Exception whenever an exception arises it will throw it to the caller (usually will be an sql exception)
	 */
	public static String createNewClass(String idprofessorprofiles, String courseprefix, String coursenumber, String coursename, String datestart, String dateend, String semester, String newClasscode, String idadminprofiles) throws Exception{
		if(newClasscode.replace(" ", "").equals("")) {
			newClasscode = generateUniqueClassCode();
		}else if(TesterClass.checkIfClassCodeInUse(newClasscode)){
			newClasscode = generateUniqueClassCode();
		}
		String query;
		if(datestart.equals("")||dateend.equals("")){
			query = "INSERT INTO `testersitedatabase`.`allclasses`" + 
					"(`idprofessorprofiles`,`courseprefix`,`coursenumber`,`coursename`,`classcode`, `semester`, `idadminprofiles`)" + 
					"VALUES" + 
					"('"+idprofessorprofiles+"','"+courseprefix+"','"+coursenumber+"','"+coursename+"','"+newClasscode+"', '"+semester+"', '"+idadminprofiles+"');";
		}else {
			query = "INSERT INTO `testersitedatabase`.`allclasses`" + 
					"(`idprofessorprofiles`,`courseprefix`,`coursenumber`,`coursename`,`datestart`,`dateend`,`classcode`, `semester`, `idadminprofiles`)" + 
					"VALUES" + 
					"('"+idprofessorprofiles+"','"+courseprefix+"','"+coursenumber+"','"+coursename+"','"+datestart+"','"+dateend+"','"+newClasscode+"', '"+semester+"', '"+idadminprofiles+"');";
		}
		
		//inserting into db
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			st.execute(query); //inserting new class into db
			ResultSet rset = st.executeQuery("SELECT LAST_INSERT_ID();");
			rset.next();
			return rset.getString("LAST_INSERT_ID()"); //returning new class's id
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return "null";
	}

	/**
	 * updates the class with idclass in db
	 * @param idclass new idclass
	 * @param courseprefix new courseprefix
	 * @param coursenumber new coursenumber
	 * @param coursename new coursename
	 * @param datestart new datestart
	 * @param dateend new dateend
	 * @param semester new semester
	 * @throws Exception if error is encounterd
	 */
	public static void editTesterClass(String idclass, String courseprefix, String coursenumber, String coursename, String datestart, String dateend, String semester) throws Exception {
		String query;
		query = "UPDATE testersitedatabase.allclasses SET courseprefix = '"+courseprefix+"', coursenumber = '"+coursenumber+"', coursename = '"+coursename+"', datestart = '"+datestart+"', dateend = '"+dateend+"' , semester = '"+semester+"' WHERE idclass = '"+idclass+"';"; 
		
		//inserting into db
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			st.executeUpdate(query); //updating new class into db
			System.out.println("Updated TesterClass");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 
	 * @param classCode the classCode to check.
	 * @return true if is in use, false if the classcode is unique
	 */
	public static boolean checkIfClassCodeInUse(String classCode) {
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rset = st.executeQuery("SELECT * FROM testersitedatabase.allclasses WHERE classcode = '"+classCode+"';");
			if(rset.next()) {
				return true;
			}else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	/**
	 * 
	 * @return a random 5 char string.
	 */
	public static String generateUniqueClassCode() {
		RandomString random = new RandomString(5,ThreadLocalRandom.current());
		String newClasscode = random.randString();
		while(TesterClass.checkIfClassCodeInUse(newClasscode) == true) {
			System.out.println("class code "+newClasscode+" already exist. Generating new one!");
			newClasscode = random.randString(); //if classcode is in use generate another one.
		}
		return newClasscode;
	}
	
	public String toString() {
		String str = "idclass: "+ idclass + " idprofessorprofiles: "+ idprofessorprofiles + " courseName: "+ (courseprefix+coursenumber) + " datestart: "+ datestart + " dateend: "+ dateend + " classcode: "+ classcode+"\n";
		for(Student student : this.students) {
			str += "	"+student.toString();
		}
		return str;
	}
	public String getIdclass() {
		return idclass;
	}
	public void setIdclass(String idclass) {
		this.idclass = idclass;
	}
	public String getIdprofessorprofiles() {
		return idprofessorprofiles;
	}
	public void setIdprofessorprofiles(String idprofessorprofiles) {
		this.idprofessorprofiles = idprofessorprofiles;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	public String getCourseprefix() {
		return courseprefix;
	}
	public void setCourseprefix(String courseprefix) {
		this.courseprefix = courseprefix;
	}
	public String getCoursenumber() {
		return coursenumber;
	}
	public void setCoursenumber(String coursenumber) {
		this.coursenumber = coursenumber;
	}
	public String getCoursename() {
		return coursename;
	}
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	public String getDatestart() {
		return datestart;
	}
	public void setDatestart(String datestart) {
		this.datestart = datestart;
	}
	public String getDateend() {
		return dateend;
	}
	public void setDateend(String dateend) {
		this.dateend = dateend;
	}
	public String getClasscode() {
		return classcode;
	}
	public void setClasscode(String classcode) {
		this.classcode = classcode;
	}
	public ArrayList<Student> getStudents() {
		return students;
	}
	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}
	public String getCoursePreNNum() {
		return this.courseprefix + this.coursenumber;
	}
	
	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
	
}
