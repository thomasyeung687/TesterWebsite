package com.testersite.model;

public class ClassObject {
	//deprecated class
	String InstructorName;
	String Coursename;
	String Classid; //one in the sql db
	String dateStart;
	String dateEnd;

	/**
	 * This class is deprecated, should use TesterClass instead. The web app has yet to fully rid of ClassObjects
	 */
	public ClassObject() {
	}
	public String getInstructorName() {
		return InstructorName;
	}
	public void setInstructorName(String name) {
		this.InstructorName = name;
	}
	public String getCoursename() {
		return Coursename;
	}
	public void setCoursename(String Coursename) {
		this.Coursename = Coursename;
	}
	public String getClassid() {
		return Classid;
	}
	public void setClassid(String Classid) {
		this.Classid = Classid;
	}
	public String getdateStart() {
		return dateStart;
	}
	public void setdateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getdateEnd() {
		return dateEnd;
	}
	public void setdateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
}
