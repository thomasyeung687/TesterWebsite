package com.testersite.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import com.testersite.dao.DBConnection;

public class Professor {
	String idprofessorprofiles;
	String name;
	String email;
	String username;
	String password;
	ArrayList<TesterClass> testerClasses = new ArrayList<TesterClass>();

	/**
	 * will use the profid parameter to fetch the professors information from the db and use that info to create a
	 * Professor obj
	 * @param profid the id of the professor to fetch from the db
	 * @throws Exception if error was thrown
	 */
	public Professor(String profid) throws Exception {
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rset = st.executeQuery("SELECT * FROM testersitedatabase.professorprofiles WHERE idprofessorprofiles = '"+profid+"'");
			if(rset.next()) {
				this.idprofessorprofiles = rset.getString("idprofessorprofiles");
				this.name = rset.getString("name");
				this.email = rset.getString("email");
				this.username = rset.getString("username");
				this.password = rset.getString("password");
			}else {
				throw new Exception("Professor account with that id does not exist!");
			}
			this.testerClasses = geTesterClassesStaticMethod(this.idprofessorprofiles);
		} catch (Exception e) {
			System.out.print("Professor.java addNewProfessor(), error: "+e.getMessage());
			throw e;
		}
	}

	/**
	 * will use the ResultSet obj provided to pull the professors information from to create a Professor obj
	 * @param rset rset to extract professor information from
	 * @throws Exception if error was thrown
	 */
	public Professor(ResultSet rset) throws Exception {
		try {
			this.idprofessorprofiles = rset.getString("idprofessorprofiles");
			this.name = rset.getString("name");
			this.email = rset.getString("email");
			this.username = rset.getString("username");
			this.testerClasses = geTesterClassesStaticMethod(this.idprofessorprofiles);
		} catch (Exception e) {
			System.out.print("Professor.java addNewProfessor(), error: "+e.getMessage());
			throw e;
		}
	}

	/**
	 * Creates a new professor obj and saves the information to the db
	 * @param username username of new professor
	 * @param password password of new professor
	 * @param email email of new professor
	 * @param name name of new professor
	 * @param adminid admin that created this new professor
	 * @return the professor obj created
	 * @throws Exception if error was thrown
	 */
	public static String addNewProfessor(String username, String password, String email, String name, String adminid) throws Exception {
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			ResultSet rset = st.executeQuery("SELECT * FROM testersitedatabase.professorprofiles WHERE username = '"+username+"'");
			if(rset.next()) {
				throw new Exception("Account with username already exists!");
			}else {
				String query ="INSERT INTO testersitedatabase.professorprofiles (username, password, email, name, idadminprofiles) "
						+ "VALUES ('"+username+"','"+password+"','"+email+"', '"+name+"', '"+adminid+"')";
				st.execute(query);
				rset = st.executeQuery("SELECT LAST_INSERT_ID();");
				rset.next();
				return rset.getString("LAST_INSERT_ID()");
			}
		} catch (Exception e) {
			System.out.print("Professor.java addNewProfessor(), error: "+e.getMessage());
			throw e;
		}
	}

	/**
	 * gets the classes of the professor whos id is indicated by idprofessorprofiles
	 * @param idprofessorprofiles gets the classes of the professor whos id is indicated by idprofessorprofiles
	 * @return an ArrayList<TesterClass>
	 * @throws Exception if error was thrown
	 */
	public ArrayList<TesterClass> geTesterClassesStaticMethod(String idprofessorprofiles) throws Exception{
		return TesterClass.geTesterClasses(idprofessorprofiles);
	}
	public void addClass(TesterClass class1) {
		this.testerClasses.add(class1);
	}
	public void updateInfoChangesToDB() throws Exception {
		Connection con = DBConnection.getDBConnection();
		try {
			Statement st = con.createStatement();
			String query ="UPDATE testersitedatabase.professorprofiles SET username = '"+this.username+"', password = '"+this.password+"', email  = '"+this.email+"', "
					+ "name = '"+this.name+"' WHERE idprofessorprofiles = "+idprofessorprofiles+";";
			st.execute(query);
		} catch (Exception e) {
			System.out.print("Professor.java updateInfoChangesToDB(), error: "+e.getMessage());
			throw e;
		}
	}
	
	public String toString() {
		String str = "idprofessorprofiles: "+ idprofessorprofiles + " name: "+ name + " username: "+ username +"\n"+
				"testerClasses:\n";
		for(TesterClass tClass : testerClasses) {
			str = str + tClass.toString()+'\n';
		}
		return str;
	}
	
	public static void main(String[] args) {
		Professor prof;
		try {
			prof = new Professor("7");
			System.out.println(prof.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getIdprofessorprofiles() {
		return idprofessorprofiles;
	}

	public void setIdprofessorprofiles(String idprofessorprofiles) {
		this.idprofessorprofiles = idprofessorprofiles;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<TesterClass> getTesterClasses() {
		return testerClasses;
	}

	public void setTesterClasses(ArrayList<TesterClass> testerClasses) {
		this.testerClasses = testerClasses;
	}
}
